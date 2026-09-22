#!/usr/bin/env node

import {existsSync, readFileSync, writeFileSync} from 'node:fs';
import {dirname, resolve} from 'node:path';
import {fileURLToPath} from 'node:url';
import {spawnSync} from 'node:child_process';

if (typeof fetch !== 'function') {
    fail('This script requires Node.js 18 or newer because it uses global fetch.');
}

const scriptDir = dirname(fileURLToPath(import.meta.url));
const platformRoot = resolve(scriptDir, '..');
const repoRoot = resolve(platformRoot, '..');
const args = parseArgs(process.argv.slice(2));

if (args.help || args.h) {
    printUsage();
    process.exit(0);
}

loadEnvFiles([
    resolve(repoRoot, '.env'),
    resolve(platformRoot, '.env'),
    resolve(process.cwd(), '.env')
]);

const clusterName = stringArg('cluster-name', 'FL_K3D_CLUSTER_NAME', 'federation-learning-platform-dev');
const namespace = stringArg('namespace', 'FL_K3D_NAMESPACE', 'federation-learning-platform');
const gatewayUrl = trimSlash(stringArg('gateway-url', 'FL_K3D_GATEWAY_URL', 'http://localhost:30080'));
const platformUrl = trimSlash(stringArg('platform-url', 'FL_PLATFORM_URL', `${gatewayUrl}/api/federation-learning-platform`));
const supportUrl = trimSlash(stringArg('support-url', 'FL_SUPPORT_URL', `${gatewayUrl}/api/federation-learning-support`));
const runtimeAgentUrl = trimSlash(stringArg('runtime-agent-url', 'FL_RUNTIME_AGENT_URL', 'http://localhost:30082/api/runtime-agent'));
const scenario = stringArg('scenario', 'FL_TRAINING_SCENARIO', 'densenet');
const k3sDevEnvironmentDir = resolve(repoRoot, 'operations/dev/k3s/environments/dev');
const k3sDevSecretsFile = resolve(k3sDevEnvironmentDir, 'secrets.dev.yaml');
const k3sDevSecretsExampleFile = resolve(k3sDevEnvironmentDir, 'secrets.example.yaml');
const adminUsername = stringArg('admin-username', 'FL_K3D_ADMIN_USERNAME', 'admin');
const adminPassword = stringArg('admin-password', 'FL_K3D_ADMIN_PASSWORD', 'admin');
const adminSetupToken = stringArg(
    'admin-setup-token',
    'FL_K3D_ADMIN_SETUP_TOKEN',
    readYamlStringDataValue(k3sDevSecretsFile, 'MEDOL_SECURITY_ADMIN_BOOTSTRAP_SETUP_TOKEN') ?? 'local-dev-admin-setup-token'
);
const jwtSecret = stringArg('jwt-secret', 'FL_K3D_JWT_SECRET', 'local-dev-jwt-secret-local-dev-jwt-secret');
const timeoutMs = positiveInt(args.timeout ?? process.env.FL_K3D_FLOW_TIMEOUT_MS, 240000);
const pollIntervalMs = positiveInt(args['poll-interval'] ?? process.env.FL_K3D_FLOW_POLL_INTERVAL_MS, 3000);
const preparedNodeCount = positiveInt(args['prepared-node-count'] ?? process.env.FL_K3D_PREPARED_NODE_COUNT, 1);
const runtimeEngineImage = stringArg(
    'runtime-engine-image',
    'FL_RUNTIME_ENGINE_IMAGE',
    'medol/federation-learning-runtime-engine:0.0.1-SNAPSHOT'
);
const runtimeAgentImage = optionalString(args['runtime-agent-image'] ?? process.env.FL_RUNTIME_AGENT_IMAGE);
const participantConsoleImage = optionalString(args['participant-console-image'] ?? process.env.FL_PARTICIPANT_CONSOLE_IMAGE);
const imageVersion = stringArg('image-version', 'IMAGE_VERSION', '0.0.1-SNAPSHOT');
const k3dScript = resolve(repoRoot, 'operations/dev/k3s/scripts/federation-learning-dev.sh');
const kubeconfigFile = resolve(
    repoRoot,
    `operations/dev/k3s/.work/kubeconfig-${clusterName}.yaml`
);

const recreateCluster = booleanOption(args.recreate ?? process.env.FL_K3D_RECREATE, false);
const skipCluster = booleanOption(args['skip-cluster'] ?? process.env.FL_K3D_SKIP_CLUSTER, false);
const skipApply = booleanOption(args['skip-apply'] ?? process.env.FL_K3D_SKIP_APPLY, false);
const skipDictionary = booleanOption(args['skip-dictionary'] ?? process.env.FL_K3D_SKIP_DICTIONARY, false);
const skipRuntimeAgentData = booleanOption(args['skip-runtime-agent-data'] ?? process.env.FL_K3D_SKIP_RUNTIME_AGENT_DATA, false);
const skipTrainingJob = booleanOption(args['skip-training-job'] ?? process.env.FL_K3D_SKIP_TRAINING_JOB, false);
const dryRun = booleanOption(args['dry-run'], false);

console.log('[k3d-flow] configuration');
console.log(JSON.stringify({
    clusterName,
    namespace,
    gatewayUrl,
    platformUrl,
    supportUrl,
    runtimeAgentUrl,
    scenario,
    runtimeEngineImage,
    imageVersion,
    skipCluster,
    skipApply,
    skipDictionary,
    skipRuntimeAgentData,
    skipTrainingJob,
    dryRun
}, null, 2));

if (!existsSync(k3dScript)) {
    fail(`K3D helper script was not found: ${k3dScript}`);
}

if (dryRun) {
    console.log('[k3d-flow] dry run stops before creating cluster or calling APIs.');
    process.exit(0);
}

if (!skipCluster) {
    await ensureCluster();
}
if (!skipApply) {
    await waitForKubernetesApi();
    await waitForKubernetesNodesReady();
    ensureDevSecrets();
    runK3d('apply');
    await waitForDeployment('postgres');
    await waitForDeployment('umadb');
    await waitForDeployment('federation-learning-support');
    await waitForDeployment('federation-learning-platform');
    await waitForHttp(`${supportUrl}/actuator/health`, 'support health');
    await ensureAdminToken();
    await waitForHttp(`${platformUrl}/actuator/health`, 'platform health');
}
if (skipApply) {
    await ensureAdminToken();
}
if (!skipDictionary) {
    await initDictionaries();
}

const platformSeed = await runInitTraining('platform', {
    createJob: false,
    registerRuntimeInfrastructure: true,
    runtimeAgentUrl,
});
const runtimeInfrastructureId = platformSeed.runtimeInfrastructureId;
const organizationId = platformSeed.organizationId;
const runtimeAgentId = platformSeed.runtimeAgentId;

console.log(`[k3d-flow] organizationId=${organizationId}`);
console.log(`[k3d-flow] runtimeInfrastructureId=${runtimeInfrastructureId}`);
console.log(`[k3d-flow] runtimeAgentId=${runtimeAgentId}`);

await labelRuntimeNodes(runtimeInfrastructureId);
const runtimeInfrastructure = await waitForRuntimeInfrastructure(runtimeInfrastructureId, isRegisteredOrBeyond);
await confirmRuntimeInfrastructurePrepared(runtimeInfrastructure);

const verifiedInfrastructure = await waitForRuntimeInfrastructure(
    runtimeInfrastructureId,
    (item) => isStateAtLeast(item?.state, ['VERIFIED', 'AGENTREADY', 'CONNECTED']) || isFailureState(item?.state),
    'verified runtime infrastructure'
);
if (isFailureState(verifiedInfrastructure?.state)) {
    fail(`Runtime infrastructure failed before agent deployment: ${JSON.stringify(verifiedInfrastructure)}`);
}

await waitForManagedRuntimeAgent(runtimeAgentId);
await waitForHttp(`${runtimeAgentUrl}/actuator/health`, 'managed runtime-agent health');
await loadRuntimeAgentBootstrapConfiguration();

const endpoint = await waitForRuntimeAgentEndpoint(runtimeAgentId, runtimeInfrastructureId);
console.log(`[k3d-flow] platform runtime agent endpoint=${endpoint.runtimeAgentEndpoint ?? endpoint.endpoint ?? ''}`);

if (!skipRuntimeAgentData) {
    await runInitTraining('runtime-agent', {
        createJob: false,
        registerRuntimeInfrastructure: false,
        runtimeAgentUrl,
    });
}

let trainingSeed = null;
if (!skipTrainingJob) {
    trainingSeed = await runInitTraining('training-job', {
        createJob: true,
        registerRuntimeInfrastructure: false,
        runtimeAgentUrl,
    });
}

console.log('[k3d-flow] ready');
console.log(JSON.stringify({
    organizationId,
    runtimeInfrastructureId,
    runtimeAgentId,
    runtimeAgentUrl,
    trainingJobId: trainingSeed?.trainingJobId
}, null, 2));

async function ensureCluster() {
    if (recreateCluster) {
        runK3d('recreate');
        return;
    }
    const exists = commandOk('k3d', ['cluster', 'get', clusterName]);
    runK3d(exists ? 'kubeconfig' : 'create');
}

async function waitForKubernetesApi() {
    await waitForCondition('kubernetes API readiness', () =>
        commandOk('kubectl', ['get', '--raw=/readyz'], kubeEnv())
    );
}

async function waitForKubernetesNodesReady() {
    let lastSummary = '';
    await waitForCondition('kubernetes nodes ready', () => {
        const output = commandOutputOrNull('kubectl', ['get', 'nodes', '-o', 'json'], kubeEnv());
        if (!output) {
            lastSummary = 'kubectl get nodes did not return successfully';
            return false;
        }
        let nodes = [];
        try {
            nodes = JSON.parse(output).items ?? [];
        } catch (error) {
            lastSummary = `kubectl get nodes returned invalid JSON: ${errorSummary(error)}`;
            return false;
        }
        if (nodes.length === 0) {
            lastSummary = 'no nodes were returned';
            return false;
        }
        const notReady = nodes
            .filter((node) => !nodeReady(node))
            .map((node) => node.metadata?.name ?? '<unknown>');
        lastSummary = notReady.length > 0
            ? `not ready nodes: ${notReady.join(', ')}`
            : `${nodes.length} node(s) ready`;
        return notReady.length === 0;
    }, () => lastSummary);
}

function runK3d(command) {
    run(k3dScript, [command], {
        CLUSTER_NAME: clusterName,
        NAMESPACE: namespace,
        KUBECONFIG_FILE: kubeconfigFile,
        KUBECONFIG: kubeconfigFile,
        IMAGE_VERSION: imageVersion,
        FL_RUNTIME_ENGINE_IMAGE: runtimeEngineImage,
        ...(runtimeAgentImage ? {FL_RUNTIME_AGENT_IMAGE: runtimeAgentImage} : {}),
        ...(participantConsoleImage ? {FL_PARTICIPANT_CONSOLE_IMAGE: participantConsoleImage} : {})
    });
}

async function initDictionaries() {
    const dictionaryScript = resolve(repoRoot, 'dictionary-init/init-dictionaries.mjs');
    if (!existsSync(dictionaryScript)) {
        console.log(`[k3d-flow] skip missing dictionary init script: ${dictionaryScript}`);
        return;
    }
    run('node', [dictionaryScript, '--base-url', supportUrl], {});
}

function ensureDevSecrets() {
    if (existsSync(k3sDevSecretsFile)) {
        return;
    }
    if (!existsSync(k3sDevSecretsExampleFile)) {
        console.log(`[k3d-flow] skip dev secret generation; missing ${k3sDevSecretsExampleFile}`);
        return;
    }
    const content = readFileSync(k3sDevSecretsExampleFile, 'utf8')
        .replaceAll('MEDOL_SECURITY_ADMIN_BOOTSTRAP_SETUP_TOKEN: "change-me"', `MEDOL_SECURITY_ADMIN_BOOTSTRAP_SETUP_TOKEN: "${adminSetupToken}"`)
        .replaceAll('MEDOL_SECURITY_JWT_SECRET: "change-me-change-me-change-me-change-me"', `MEDOL_SECURITY_JWT_SECRET: "${jwtSecret}"`);
    writeFileSync(k3sDevSecretsFile, content);
    console.log(`[k3d-flow] generated local dev secrets: ${k3sDevSecretsFile}`);
}

async function ensureAdminToken() {
    if (process.env.FL_API_TOKEN?.trim()) {
        console.log('[k3d-flow] use existing FL_API_TOKEN');
        return process.env.FL_API_TOKEN.trim();
    }

    await setupAdmin();
    const token = await loginAdmin();
    process.env.FL_API_TOKEN = token;
    console.log(`[k3d-flow] authenticated ${adminUsername}; FL_API_TOKEN is available for this run`);
    return token;
}

async function setupAdmin() {
    const response = await postJson(`${supportUrl}/api/auth/setup-admin`, {
        setupToken: adminSetupToken,
        username: adminUsername,
        password: adminPassword
    }, 'setup admin', {allowStatuses: [200, 409]});

    if (response.status === 409) {
        console.log('[k3d-flow] admin account already initialized');
    } else {
        console.log(`[k3d-flow] admin account initialized: ${adminUsername}`);
    }
}

async function loginAdmin() {
    const response = await postJson(`${supportUrl}/api/auth/login`, {
        username: adminUsername,
        password: adminPassword
    }, 'login admin');
    const token = response.body?.accessToken;
    if (!token) {
        fail(`Admin login did not return accessToken. Response: ${JSON.stringify(response.body)}`);
    }
    return token;
}

async function runInitTraining(target, options) {
    const initScript = resolve(scriptDir, 'init-training-prerequisites.mjs');
    const output = runCapture('node', [
        initScript,
        '--target', target,
        '--scenario', scenario,
        '--platform-url', platformUrl,
        '--runtime-agent-url', options.runtimeAgentUrl,
        '--runtime-environment-type', 'K3S',
        '--agent-install-mode', 'PLATFORM_MANAGED',
        '--endpoint-scope', 'CLUSTER',
        '--runtime-engine-image', runtimeEngineImage,
        '--register-runtime-infrastructure', String(options.registerRuntimeInfrastructure),
        '--create-job', String(options.createJob),
        '--timeout', String(timeoutMs),
        '--poll-interval', String(Math.min(pollIntervalMs, 1000)),
        ...(dryRun ? ['--dry-run'] : [])
    ], {});
    process.stdout.write(output);
    return extractLastJsonObject(output);
}

async function labelRuntimeNodes(runtimeInfrastructureId) {
    const nodes = JSON.parse(runCapture('kubectl', ['get', 'nodes', '-o', 'json'], kubeEnv()));
    const runtimeNodes = nodes.items
        .filter((node) => !isControlPlaneNode(node))
        .map((node) => node.metadata?.name)
        .filter(Boolean);
    if (runtimeNodes.length === 0) {
        fail('No k3d agent node was found for runtime scheduling.');
    }
    for (const nodeName of runtimeNodes) {
        run('kubectl', [
            'label', 'node', nodeName,
            'medol.dev/node-role=runtime',
            `medol.dev/runtime-infrastructure-id=${runtimeInfrastructureId}`,
            '--overwrite'
        ], kubeEnv());
        run('kubectl', [
            'taint', 'node', nodeName,
            'medol.dev/runtime-only=true:NoSchedule',
            '--overwrite'
        ], kubeEnv());
    }
}

async function confirmRuntimeInfrastructurePrepared(runtimeInfrastructure) {
    if (isStateAtLeast(runtimeInfrastructure?.state, ['PREPARED', 'VERIFIED', 'AGENTREADY', 'CONNECTED'])) {
        console.log('[k3d-flow] runtime infrastructure already prepared');
        return;
    }
    await postCommand(platformUrl, '/runtimeinfrastructure/confirmruntimeinfrastructureprepared', {
        runtimeInfrastructureId: runtimeInfrastructure.runtimeInfrastructureId,
        runtimeInstallationPlanId: runtimeInfrastructure.runtimeInstallationPlanId,
        organizationId: runtimeInfrastructure.organizationId,
        organizationName: runtimeInfrastructure.organizationName ?? null,
        runtimeInfrastructurePackageId: runtimeInfrastructure.runtimeInfrastructurePackageId,
        runtimeInfrastructurePackageName: runtimeInfrastructure.runtimeInfrastructurePackageName ?? null,
        runtimeInfrastructurePackageVersion: runtimeInfrastructure.runtimeInfrastructurePackageVersion ?? null,
        runtimeEnvironmentType: runtimeInfrastructure.runtimeEnvironmentType ?? 'K3S',
        runtimeName: runtimeInfrastructure.runtimeName,
        agentInstallMode: runtimeInfrastructure.agentInstallMode ?? 'PLATFORM_MANAGED',
        expectedNodeCount: runtimeInfrastructure.expectedNodeCount ?? preparedNodeCount,
        runtimeAgentId: runtimeInfrastructure.runtimeAgentId,
        preparedNodeCount,
        preparationNotes: 'k3d-training-flow labeled k3d agent nodes for runtime scheduling.'
    }, 'confirm runtime infrastructure prepared');
}

async function loadRuntimeAgentBootstrapConfiguration() {
    await postCommand(
        runtimeAgentUrl,
        '/runtimeagentlifecycle/loadruntimeagentbootstrapconfiguration',
        {},
        'runtime agent bootstrap configuration load'
    );
}

async function waitForRuntimeInfrastructure(runtimeInfrastructureId, predicate, label = 'runtime infrastructure') {
    return waitForOne(platformUrl, '/runtimeinfrastructure/runtimeinfrastructureaccessview', {
        'runtimeInfrastructureId.equals': runtimeInfrastructureId,
        size: '20'
    }, label, predicate);
}

async function waitForRuntimeAgentEndpoint(runtimeAgentId, runtimeInfrastructureId) {
    return waitForOne(platformUrl, '/runtimeinfrastructure/runtimeagentendpointcatalog', {
        'runtimeAgentId.equals': runtimeAgentId,
        'runtimeInfrastructureId.equals': runtimeInfrastructureId,
        size: '20'
    }, 'runtime agent endpoint', (item) => Boolean(item?.runtimeAgentEndpoint ?? item?.endpoint), () =>
        runtimeAgentDiagnostics(runtimeAgentId)
    );
}

async function waitForManagedRuntimeAgent(runtimeAgentId) {
    const deploymentName = managedRuntimeAgentDeploymentName(runtimeAgentId);
    await waitForDeployment(deploymentName, 'managed runtime-agent');
    const consoleDeploymentName = `${deploymentName}-console`;
    if (commandOk('kubectl', ['-n', namespace, 'get', 'deploy', consoleDeploymentName], kubeEnv())) {
        await waitForDeployment(consoleDeploymentName, 'managed runtime-agent participant console');
    }
}

function managedRuntimeAgentDeploymentName(runtimeAgentId) {
    return `federation-learning-runtime-agent-managed-${String(runtimeAgentId).replaceAll('-', '').slice(0, 12)}`;
}

async function waitForDeployment(name, label = name) {
    await waitForCondition(label, () => commandOk('kubectl', [
        '-n', namespace,
        'rollout', 'status',
        `deploy/${name}`,
        '--timeout=5s'
    ], kubeEnv()));
}

async function waitForHttp(url, label) {
    await waitForCondition(label, async () => {
        try {
            const response = await fetch(url, {headers: requestHeaders()});
            return response.ok;
        } catch {
            return false;
        }
    });
}

async function waitForOne(baseUrl, path, query, label, predicate, timeoutDetails = () => '') {
    let lastItem = null;
    const item = await waitForCondition(label, async () => {
        const page = await getPage(baseUrl, path, query);
        lastItem = (page.content ?? [])[0] ?? lastItem;
        return (page.content ?? []).find(predicate) ?? false;
    }, () => [
        `Last item: ${JSON.stringify(lastItem)}`,
        timeoutDetails()
    ].filter(Boolean).join('\n'));
    return item;
}

async function waitForCondition(label, probe, timeoutDetails = () => '') {
    const startedAt = Date.now();
    while (Date.now() - startedAt <= timeoutMs) {
        const result = await probe();
        if (result) return result;
        await sleep(pollIntervalMs);
    }
    fail(`Timed out waiting for ${label}. ${timeoutDetails()}`.trim());
}

async function getPage(baseUrl, path, query) {
    const url = `${baseUrl}${path}?${new URLSearchParams(query).toString()}`;
    const response = await fetch(url, {headers: requestHeaders()});
    const body = await response.text();
    if (!response.ok) {
        fail(`GET ${url} failed: ${response.status} ${compact(body)}`);
    }
    return body ? JSON.parse(body) : {content: []};
}

async function postCommand(baseUrl, path, payload, label) {
    if (dryRun) {
        console.log(`[k3d-flow] DRY POST ${label}`);
        console.log(JSON.stringify(payload, null, 2));
        return;
    }
    const url = `${baseUrl}${path}`;
    const response = await fetch(url, {
        method: 'POST',
        headers: requestHeaders({'content-type': 'application/json'}),
        body: JSON.stringify(payload)
    });
    const body = await response.text();
    if (!response.ok) {
        fail(`POST ${label} failed: ${response.status} ${compact(body)}`);
    }
    console.log(`[k3d-flow] posted ${label}`);
}

async function postJson(url, payload, label, options = {}) {
    const allowStatuses = new Set(options.allowStatuses ?? [200]);
    const response = await fetch(url, {
        method: 'POST',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify(payload)
    });
    const text = await response.text();
    const body = text ? parseJsonOrText(text) : null;
    if (!allowStatuses.has(response.status)) {
        fail(`POST ${label} failed: ${response.status} ${compact(text)}`);
    }
    return {status: response.status, body};
}

function parseJsonOrText(text) {
    try {
        return JSON.parse(text);
    } catch {
        return text;
    }
}

function run(command, commandArgs, extraEnv) {
    if (dryRun) {
        console.log(`[k3d-flow] DRY RUN ${command} ${commandArgs.join(' ')}`);
        return '';
    }
    const result = spawnSync(command, commandArgs, {
        cwd: repoRoot,
        env: {...process.env, ...extraEnv},
        stdio: 'inherit'
    });
    if (result.status !== 0) {
        fail(`Command failed with exitCode=${result.status}: ${command} ${commandArgs.join(' ')}`);
    }
}

function runCapture(command, commandArgs, extraEnv) {
    if (dryRun) {
        console.log(`[k3d-flow] DRY RUN ${command} ${commandArgs.join(' ')}`);
        return '{}';
    }
    const result = spawnSync(command, commandArgs, {
        cwd: repoRoot,
        env: {...process.env, ...extraEnv},
        encoding: 'utf8'
    });
    if (result.stderr) process.stderr.write(result.stderr);
    if (result.status !== 0) {
        if (result.stdout) process.stdout.write(result.stdout);
        fail([
            `Command failed with exitCode=${result.status}: ${command} ${commandArgs.join(' ')}`,
            result.stdout ? `stdout tail: ${compactTail(result.stdout)}` : '',
            result.stderr ? `stderr tail: ${compactTail(result.stderr)}` : ''
        ].filter(Boolean).join('\n'));
    }
    return result.stdout ?? '';
}

function commandOk(command, commandArgs, extraEnv = {}) {
    const result = spawnSync(command, commandArgs, {
        cwd: repoRoot,
        env: {...process.env, ...extraEnv},
        stdio: 'ignore'
    });
    return result.status === 0;
}

function commandOutputOrNull(command, commandArgs, extraEnv = {}) {
    const result = spawnSync(command, commandArgs, {
        cwd: repoRoot,
        env: {...process.env, ...extraEnv},
        encoding: 'utf8'
    });
    if (result.status !== 0) {
        return null;
    }
    return result.stdout ?? '';
}

function runtimeAgentDiagnostics(runtimeAgentId) {
    const deploymentName = managedRuntimeAgentDeploymentName(runtimeAgentId);
    const podSelector = `app=${deploymentName}`;
    const serviceSelector = `app.kubernetes.io/name=${deploymentName}`;
    const parts = [
        diagnosticCommand('deployments', ['kubectl', ['-n', namespace, 'get', 'deploy', deploymentName, '-o', 'wide']]),
        diagnosticCommand('deployment description', ['kubectl', ['-n', namespace, 'describe', 'deploy', deploymentName]]),
        diagnosticCommand('pods', ['kubectl', ['-n', namespace, 'get', 'pods', '-l', podSelector, '-o', 'wide']]),
        diagnosticCommand('service', ['kubectl', ['-n', namespace, 'get', 'svc', deploymentName, '-o', 'wide']]),
        diagnosticCommand('services by label', ['kubectl', ['-n', namespace, 'get', 'svc', '-l', serviceSelector, '-o', 'wide']]),
        diagnosticCommand('recent runtime-agent logs', [
            'kubectl',
            ['-n', namespace, 'logs', `deploy/${deploymentName}`, '--tail=200']
        ])
    ];
    const consoleDeploymentName = `${deploymentName}-console`;
    if (commandOk('kubectl', ['-n', namespace, 'get', 'deploy', consoleDeploymentName], kubeEnv())) {
        parts.push(
            diagnosticCommand('participant console deployment', [
                'kubectl',
                ['-n', namespace, 'get', 'deploy', consoleDeploymentName, '-o', 'wide']
            ]),
            diagnosticCommand('recent participant console logs', [
                'kubectl',
                ['-n', namespace, 'logs', `deploy/${consoleDeploymentName}`, '--tail=100']
            ])
        );
    }
    return parts.filter(Boolean).join('\n');
}

function diagnosticCommand(label, commandSpec) {
    const [command, commandArgs] = commandSpec;
    const output = commandOutputOrNull(command, commandArgs, kubeEnv());
    if (!output) {
        return `${label}: unavailable`;
    }
    return `${label}:\n${compactLongTail(output)}`;
}

function kubeEnv() {
    return {
        KUBECONFIG: kubeconfigFile
    };
}

function requestHeaders(baseHeaders = {}) {
    const result = {...baseHeaders};
    const apiToken = process.env.FL_API_TOKEN?.trim();
    if (apiToken && !Object.hasOwn(result, 'Authorization')) {
        result.Authorization = apiToken.startsWith('Bearer ') ? apiToken : `Bearer ${apiToken}`;
    }
    const internalToken = args['internal-token'] ?? process.env.MEDOL_SECURITY_INTERNAL_TOKEN;
    if (internalToken && !Object.hasOwn(result, 'X-MEDOL-INTERNAL-TOKEN')) {
        result['X-MEDOL-INTERNAL-TOKEN'] = String(internalToken).trim();
    }
    return result;
}

function extractLastJsonObject(output) {
    const marker = '\n{';
    const start = output.lastIndexOf(marker);
    const jsonText = start >= 0 ? output.slice(start + 1).trim() : output.trim();
    const end = jsonText.lastIndexOf('}');
    if (!jsonText.startsWith('{') || end < 0) {
        fail(`Unable to parse init-training output JSON. Output tail: ${compact(output.slice(-1000))}`);
    }
    return JSON.parse(jsonText.slice(0, end + 1));
}

function isControlPlaneNode(node) {
    const labels = node.metadata?.labels ?? {};
    return Object.hasOwn(labels, 'node-role.kubernetes.io/control-plane') ||
        Object.hasOwn(labels, 'node-role.kubernetes.io/master');
}

function nodeReady(node) {
    return (node.status?.conditions ?? []).some((condition) =>
        condition.type === 'Ready' && condition.status === 'True'
    );
}

function isRegisteredOrBeyond(item) {
    return isStateAtLeast(item?.state, ['REGISTERED', 'PREPARED', 'VERIFIED', 'AGENTREADY', 'CONNECTED']);
}

function isStateAtLeast(value, states) {
    return states.map(normalize).includes(normalize(value));
}

function isFailureState(value) {
    return ['VERIFICATIONFAILED', 'RUNTIMEAGENTFAILED'].includes(normalize(value));
}

function parseArgs(argv) {
    const result = {};
    for (let i = 0; i < argv.length; i += 1) {
        const arg = argv[i];
        if (!arg.startsWith('--')) continue;
        const key = arg.slice(2);
        const next = argv[i + 1];
        if (!next || next.startsWith('--')) {
            result[key] = true;
        } else {
            result[key] = next;
            i += 1;
        }
    }
    return result;
}

function printUsage() {
    console.log(`Usage:
  node scripts/k3d-training-flow.mjs [options]

Runs the local K3D federation-learning smoke flow: cluster readiness wait,
manifest apply, local admin bootstrap/login, dictionary init, platform seed,
runtime-agent deployment wait, runtime-agent seed, and training job submission.

Options:
  --recreate                         Recreate the K3D cluster before running.
  --scenario <densenet|csv>           Select training seed scenario.
  --skip-cluster                      Skip cluster create/kubeconfig.
  --skip-apply                        Skip manifest apply and deployment waits.
  --skip-dictionary                   Skip dictionary bootstrap.
  --skip-runtime-agent-data           Skip runtime-agent dataset bootstrap.
  --skip-training-job                 Skip training job submission.
  --runtime-engine-image <image>      Runtime engine image used by managed agents.
  --admin-username <username>         Local admin username. Default: admin.
  --admin-password <password>         Local admin password. Default: admin.
  --admin-setup-token <token>         Local admin setup token.
  --dry-run                           Print configuration and exit before side effects.

Environment:
  FL_API_TOKEN                        Existing bearer token. When set, login is skipped.
  FL_K3D_ADMIN_USERNAME               Local admin username override.
  FL_K3D_ADMIN_PASSWORD               Local admin password override.
  FL_K3D_ADMIN_SETUP_TOKEN            Local admin setup token override.
  FL_K3D_CLUSTER_NAME                 K3D cluster name.
  FL_K3D_NAMESPACE                    Kubernetes namespace.
  FL_K3D_GATEWAY_URL                  Gateway URL. Default: http://localhost:30080.

The flow creates operations/dev/k3s/environments/dev/secrets.dev.yaml from
secrets.example.yaml when it is missing. For manual kubectl inspection, use the
same kubeconfig printed by the flow helper, for example:
  export KUBECONFIG=operations/dev/k3s/.work/kubeconfig-federation-learning-platform-dev.yaml
`);
}

function loadEnvFiles(paths) {
    const loaded = new Set();
    for (const path of paths) {
        if (loaded.has(path) || !existsSync(path)) continue;
        loaded.add(path);
        const text = readFileSync(path, 'utf8');
        for (const line of text.split(/\r?\n/)) {
            const trimmed = line.trim();
            if (!trimmed || trimmed.startsWith('#')) continue;
            const separator = trimmed.indexOf('=');
            if (separator <= 0) continue;
            const key = trimmed.slice(0, separator).trim();
            const rawValue = trimmed.slice(separator + 1).trim();
            if (!key || Object.hasOwn(process.env, key)) continue;
            process.env[key] = unquoteEnvValue(rawValue);
        }
    }
}

function unquoteEnvValue(value) {
    if ((value.startsWith('"') && value.endsWith('"')) || (value.startsWith("'") && value.endsWith("'"))) {
        return value.slice(1, -1);
    }
    return value;
}

function readYamlStringDataValue(file, key) {
    if (!existsSync(file)) return null;
    const text = readFileSync(file, 'utf8');
    const pattern = new RegExp(`^\\s*${escapeRegExp(key)}:\\s*["']?([^"'\\n]+)["']?\\s*$`, 'm');
    return text.match(pattern)?.[1]?.trim() || null;
}

function escapeRegExp(value) {
    return String(value).replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
}

function stringArg(name, envName, fallback) {
    return String(args[name] ?? process.env[envName] ?? fallback).trim();
}

function optionalString(value) {
    const text = String(value ?? '').trim();
    return text.length > 0 ? text : null;
}

function booleanOption(value, fallback = false) {
    if (value == null) return fallback;
    if (value === true) return true;
    const normalized = normalize(value);
    if (['TRUE', '1', 'YES', 'Y', 'ON'].includes(normalized)) return true;
    if (['FALSE', '0', 'NO', 'N', 'OFF'].includes(normalized)) return false;
    return fallback;
}

function positiveInt(value, fallback) {
    const parsed = Number.parseInt(value, 10);
    return Number.isFinite(parsed) && parsed > 0 ? parsed : fallback;
}

function normalize(value) {
    return String(value ?? '').replace(/[_\s-]/g, '').toUpperCase();
}

function trimSlash(value) {
    return String(value).replace(/\/+$/, '');
}

function compact(value) {
    return String(value ?? '').replace(/\s+/g, ' ').trim().slice(0, 1000);
}

function compactTail(value) {
    return String(value ?? '').split(/\r?\n/).slice(-20).join('\n').trim();
}

function compactLongTail(value) {
    return String(value ?? '').split(/\r?\n/).slice(-200).join('\n').trim().slice(-12000);
}

function sleep(ms) {
    return new Promise((resolve) => setTimeout(resolve, ms));
}

function fail(message) {
    console.error(`[k3d-flow] ${message}`);
    process.exit(1);
}
