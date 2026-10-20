import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import vm from 'node:vm';
import test from 'node:test';

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
    const context = vm.createContext({namespace: 'test', console: {log() {}},
        resolvedRuntimeEngineImage: {aliases: []},
        runCapture: () => JSON.stringify({items: [deployment]}),
        run: (...args) => calls.push(args)});
    vm.runInContext(implementation, context);
    const images = new Map([['registry.example/team/app:v2', image]]);
    context.refreshVerifiedImageDeployments(images);
    assert.equal(calls.length, 1);
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
