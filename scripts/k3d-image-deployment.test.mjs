import assert from 'node:assert/strict';
import {readFileSync, mkdtempSync, mkdirSync, cpSync, writeFileSync, rmSync} from 'node:fs';
import {tmpdir} from 'node:os';
import {join} from 'node:path';
import vm from 'node:vm';
import test from 'node:test';
import {spawnSync} from 'node:child_process';
import {fileURLToPath} from 'node:url';

test('local operations dry run resolves the new environment layout without Docker', () => {
    const result = spawnSync(process.execPath, [fileURLToPath(new URL('./k3d-training-flow.mjs', import.meta.url)),
        '--environment', 'local', '--image-source', 'import', '--dry-run'], {
        cwd: fileURLToPath(new URL('..', import.meta.url)), encoding: 'utf8',
    });
    assert.equal(result.status, 0, result.stderr);
    assert.match(result.stdout, /"environment": "local"/);
    assert.match(result.stdout, /"clusterName": "federation-learning-local"/);
    assert.match(result.stdout, /"imageSource": "import"/);
});

test('runtime component assembly uses local config and secret names', () => {
    const root = mkdtempSync(join(tmpdir(), 'k3d-local-'));
    try {
        const scripts = join(root, 'operations/k3s/scripts');
        mkdirSync(scripts, {recursive: true});
        cpSync(new URL('../operations/k3s/components', import.meta.url), join(root, 'operations/k3s/components'), {recursive: true});
        cpSync(new URL('../operations/k3s/scripts/federation-learning-dev.sh', import.meta.url), join(scripts, 'federation-learning-dev.sh'));
        writeFileSync(join(scripts, 'k3d-dev.sh'), '#!/bin/sh\nprintf "%s\\n" "$ENVIRONMENT_OVERLAY"\n', {mode: 0o755});
        const bin = join(root, 'bin');
        mkdirSync(bin);
        writeFileSync(join(bin, 'kubectl'), '#!/bin/sh\nexit 0\n', {mode: 0o755});
        const result = spawnSync('bash', [join(scripts, 'federation-learning-dev.sh'), 'apply'], {
            encoding: 'utf8', env: {PATH: `${bin}:${process.env.PATH}`, FL_OPERATIONS_ENVIRONMENT: 'local'},
        });
        assert.equal(result.status, 0, result.stderr);
        assert.match(result.stdout, /environments\/local/);
        const component = join(root, 'operations/k3s/.work/runtime-scheduling-component');
        for (const name of ['patches/runtime-scheduler-config.yaml', 'runtime-scheduler-images.yaml',
            'runtime-agent-managed-secret.yaml', 'platform-aggregation-runtime-engine.yaml']) {
            const content = readFileSync(join(component, name), 'utf8');
            assert.doesNotMatch(content, /-dev-(config|secret)/);
            assert.match(content, /-local-(config|secret)/);
        }
        assert.match(readFileSync(join(component, 'runtime-scheduler-images.yaml'), 'utf8'),
            /PLATFORM_RUNTIME_K3S_AGENT_SECURITY_SECRET_NAME: "federation-learning-runtime-agent-local-secret"/);
    } finally {
        rmSync(root, {recursive: true, force: true});
    }
});

test('dictionary initialization passes the support login token to its child process', async () => {
    const source = readFileSync(new URL('./k3d-training-flow.mjs', import.meta.url), 'utf8');
    const implementation = source.slice(source.indexOf('async function initDictionaries('), source.indexOf('async function ensureAdminUser('));
    const calls = [];
    const context = vm.createContext({repoRoot: '/repo', supportUrl: 'support', internalToken: 'internal',
        resolve: (...parts) => parts.join('/'), existsSync: () => true,
        ensureAdminToken: async () => 'support-access-token', run: (...args) => calls.push(args)});
    vm.runInContext(implementation, context);
    await context.initDictionaries();
    assert.equal(calls[0][2].FL_API_TOKEN, 'support-access-token');
});

test('k3d helper receives the selected environment and matching admin setup token', () => {
    const source = readFileSync(new URL('./k3d-training-flow.mjs', import.meta.url), 'utf8');
    const implementation = source.slice(source.indexOf('function runK3d('), source.indexOf('async function prepareParticipant('));
    const calls = [];
    const context = vm.createContext({k3dScript: 'helper', environment: 'local',
        k3sDevEnvironmentDir: 'environments/local', k3sDevSecretsFile: 'secrets.local.yaml',
        clusterName: 'local-cluster', namespace: 'test', kubeconfigFile: 'local-kubeconfig',
        appImagePrefix: 'fl', imageVersion: '1', adminSetupToken: 'setup-token',
        runtimeEngineImage: 'engine', runtimeAgentImage: 'agent', participantConsoleImage: 'console',
        run: (...args) => calls.push(args)});
    vm.runInContext(implementation, context);
    context.runK3d('apply');
    const env = calls[0][2];
    assert.equal(env.ENVIRONMENT_OVERLAY, 'environments/local');
    assert.equal(env.REGISTRY_OVERLAY, 'environments/local-registry');
    assert.equal(env.SECRETS_FILE, 'secrets.local.yaml');
    assert.equal(env.KUBECONFIG, 'local-kubeconfig');
    assert.equal(env.MEDOL_SECURITY_ADMIN_BOOTSTRAP_SETUP_TOKEN, 'setup-token');
});

function trainingVerificationContext(responses) {
    const source = readFileSync(new URL('./k3d-training-flow.mjs', import.meta.url), 'utf8');
    const implementation = source.slice(source.indexOf('async function verifyTrainingResult('), source.indexOf('async function waitForHttp('));
    const calls = [];
    const context = vm.createContext({platformUrl: 'platform', federationMode: 'global', console: {log() {}},
        fail: (message) => {throw new Error(message);},
        waitForCondition: async (label, probe, details) => {
            if (!await probe()) throw new Error(`timeout: ${details()}`);
        },
        getPage: async (url, path, query) => {
            calls.push({url, path, query});
            return {content: responses[path] ?? []};
        }});
    vm.runInContext(implementation, context);
    return {context, calls};
}

test('training verification reports all failed participants even after engine cleanup', async () => {
    const {context, calls} = trainingVerificationContext({
        '/traininground/trainingroundprogress': [{roundId: 'round-1', roundNumber: 1, state: 'Running'}],
        '/roundexecution/roundexecutioncatalog': [{state: 'RuntimeEngineReleaseHandled', failureReason: 'Personalized model missing'}],
    });
    await assert.rejects(context.verifyTrainingResult('job-1', [
        {runtimeAgentId: 'a', runtimeAgentUrl: 'agent-a'}, {runtimeAgentId: 'b', runtimeAgentUrl: 'agent-b'},
    ]), /All participants failed.*Personalized model missing/);
    for (const call of calls.filter((call) => call.url.startsWith('agent-'))) {
        assert.equal(call.query['trainingJobId.equals'], 'job-1');
        assert.equal(call.query['roundId.equals'], 'round-1');
    }
});

test('running training times out with actionable platform and participant status', async () => {
    const {context} = trainingVerificationContext({
        '/traininground/trainingroundprogress': [{roundId: 'round-2', roundNumber: 2, state: 'Running'}],
        '/roundexecution/roundexecutioncatalog': [{state: 'Running'}],
    });
    await assert.rejects(context.verifyTrainingResult('job-1', [{runtimeAgentId: 'a', runtimeAgentUrl: 'agent-a'}]),
        /Last training status:.*round-2.*participants.*Running/);
});

test('completed jobs do not require participant execution queries', async () => {
    const {context, calls} = trainingVerificationContext({
        '/trainingjob/trainingjobdashboard': [{state: 'Completed'}],
    });
    await context.verifyTrainingResult('job-1', []);
    assert.equal(calls.length, 2);
});

test('failed training jobs stop immediately', async () => {
    const {context} = trainingVerificationContext({
        '/trainingjob/trainingjobdashboard': [{state: 'Failed', failureReason: 'quorum lost'}],
    });
    await assert.rejects(context.verifyTrainingResult('job-1', []), /ended in Failed: quorum lost/);
});

test('runtime scheduling is prepared before deployments and validates all slots before labeling', () => {
    const source = readFileSync(new URL('./k3d-training-flow.mjs', import.meta.url), 'utf8');
    assert(source.indexOf('prepareRuntimeSchedulingNodes();') < source.indexOf("runK3d('apply');"));
    const implementation = source.slice(source.indexOf('function runtimeNodeForSlot('), source.indexOf('async function labelRuntimeNode('));
    const nodes = [{metadata: {name: 'server', controlPlane: true}}, {metadata: {name: 'agent-0'}}, {metadata: {name: 'agent-1'}}];
    const calls = [];
    const context = vm.createContext({participantCount: 2,
        isControlPlaneNode: (node) => Boolean(node.metadata.controlPlane),
        runCapture: () => JSON.stringify({items: nodes}), kubeEnv: () => ({}),
        fail: (message) => {throw new Error(message);},
        run: (...args) => calls.push(args)});
    vm.runInContext(implementation, context);
    context.prepareRuntimeSchedulingNodes();
    assert.equal(calls.length, 4);
    assert.equal(calls[0][1][2], 'agent-0');
    assert.equal(calls[2][1][2], 'agent-1');
    assert(calls[0][1].includes('medol.dev/node-role=runtime'));
    assert(calls[1][1].includes('medol.dev/runtime-only=true:NoSchedule'));
    calls.length = 0;
    nodes.pop();
    assert.throws(() => context.prepareRuntimeSchedulingNodes(), /only 1 agent/);
    assert.equal(calls.length, 0);
});

test('verified images replace base references with pinned artifacts and refresh only when needed', () => {
    const source = readFileSync(new URL('./k3d-training-flow.mjs', import.meta.url), 'utf8');
    const implementation = source.slice(source.indexOf('function refreshVerifiedImageDeployments('), source.indexOf('function ensureRuntimeEngineImageAvailable('));
    const image = {reference: 'registry.example/team/app@sha256:' + 'a'.repeat(64), id: 'sha256:new',
        aliases: ['team/app:v1', 'registry.example/team/app:v2']};
    const deployment = {metadata: {name: 'app'}, spec: {template: {metadata: {annotations: {}},
        spec: {containers: [{name: 'app', image: 'team/app:v1', imagePullPolicy: 'Always'}]}}}};
    const calls = [];
    const context = vm.createContext({namespace: 'test', console: {log() {}}, kubeEnv: () => ({KUBECONFIG: 'local-config'}),
        resolvedRuntimeEngineImage: {aliases: []},
        runCapture: (command, args, env) => {
            assert.equal(env.KUBECONFIG, 'local-config');
            return JSON.stringify({items: [deployment]});
        },
        run: (...args) => calls.push(args)});
    vm.runInContext(implementation, context);
    const images = new Map([['registry.example/team/app:v2', image]]);
    context.refreshVerifiedImageDeployments(images);
    assert.equal(calls.length, 1);
    assert.equal(calls[0][2].KUBECONFIG, 'local-config');
    const template = JSON.parse(calls[0][1].at(-1)).spec.template;
    assert.equal(template.spec.containers[0].image, image.reference);
    assert.equal(template.spec.containers[0].imagePullPolicy, 'IfNotPresent');
    Object.assign(deployment.spec.template, template);
    image.aliases.push(image.reference);
    context.refreshVerifiedImageDeployments(images);
    assert.equal(calls.length, 1);
    image.id = 'sha256:changed';
    context.refreshVerifiedImageDeployments(images);
    assert.equal(calls.length, 2);
    deployment.metadata.name = 'managed-app-123';
    deployment.spec.template.spec.containers[0].name = 'managed-container';
    deployment.spec.template.spec.containers[0].image = 'registry.example/team/app@sha256:' + 'b'.repeat(64);
    context.refreshVerifiedImageDeployments(images);
    assert.equal(calls.length, 3);
    assert.equal(JSON.parse(calls[2][1].at(-1)).spec.template.spec.containers[0].image, image.reference);
});
