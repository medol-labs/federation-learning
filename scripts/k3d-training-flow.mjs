#!/usr/bin/env node

import {existsSync, readFileSync, writeFileSync} from 'node:fs';
import {dirname, resolve} from 'node:path';
import {fileURLToPath, pathToFileURL} from 'node:url';
import {spawn, spawnSync} from 'node:child_process';
import {createServer} from 'node:net';

if (typeof fetch !== 'function') {
    fail('This script requires Node.js 18 or newer because it uses global fetch.');
}

const scriptDir = dirname(fileURLToPath(import.meta.url));
const repoRoot = resolve(scriptDir, '..');
const platformRoot = resolve(repoRoot, 'federation-learning-platform');
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

const environment = stringArg('environment', 'FL_OPERATIONS_ENVIRONMENT', 'local');
if (!/^[a-z][a-z0-9-]*$/.test(environment)) fail(`Invalid operations environment: ${environment}`);
const imageDirectory = resolve(repoRoot, 'operations/images', environment);
const imageConfigFile = resolve(repoRoot, 'operations/images/image-config.mjs');
if (!existsSync(resolve(repoRoot, 'operations/model', environment, 'operations-model.json'))) {
    fail(`Operations environment ${environment} has not been generated. Generate operations with --environment ${environment} first.`);
}
const {resolveImageConfig, resolveImageArtifact} = await import(pathToFileURL(imageConfigFile).href);
const clusterName = stringArg('cluster-name', 'FL_K3D_CLUSTER_NAME', environment === 'dev' ? 'federation-learning-platform-dev' : `federation-learning-${environment}`);
const namespace = stringArg('namespace', 'FL_K3D_NAMESPACE', 'federation-learning-platform');
const gatewayUrl = trimSlash(stringArg('gateway-url', 'FL_K3D_GATEWAY_URL', 'http://localhost:30080'));
const platformUrl = trimSlash(stringArg('platform-url', 'FL_PLATFORM_URL', `${gatewayUrl}/api/federation-learning-platform`));
const supportUrl = trimSlash(stringArg('support-url', 'FL_SUPPORT_URL', `${gatewayUrl}/api/federation-learning-support`));
const configuredRuntimeAgentUrl = optionalString(args['runtime-agent-url'] ?? process.env.FL_RUNTIME_AGENT_URL);
const runtimeAgentPortForwardPort = positiveInt(args['runtime-agent-port-forward-port'] ?? process.env.FL_RUNTIME_AGENT_PORT_FORWARD_PORT, 18082);
let runtimeAgentUrl = trimSlash(configuredRuntimeAgentUrl ?? `http://127.0.0.1:${runtimeAgentPortForwardPort}`);
const scenario = stringArg('scenario', 'FL_TRAINING_SCENARIO', 'densenet');
const federationMode = normalizeFederationMode(args['federation-mode'] ?? process.env.FL_FEDERATION_MODE ?? 'global');
const trainingRunKey = optionalString(args['training-run-key'] ?? process.env.FL_TRAINING_RUN_KEY);
const waitForTraining = Boolean(args['wait-for-training']);
const k3sDevEnvironmentDir = resolve(repoRoot, 'operations/k3s/environments', environment);
const k3sDevSecretsFile = resolve(k3sDevEnvironmentDir, `secrets.${environment}.yaml`);
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
const participantCount = positiveInt(args['participant-count'] ?? process.env.FL_K3D_PARTICIPANT_COUNT, 2);
const preparedNodeCount = positiveInt(args['prepared-node-count'] ?? process.env.FL_K3D_PREPARED_NODE_COUNT, 1);
const imageConfig = resolveImageConfig(imageDirectory, {
    prefix: args['app-image-prefix'] ?? process.env.FL_APP_IMAGE_PREFIX,
    version: args['image-version'],
    mode: args['image-source'],
});
const imageVersion = imageConfig.version;
const runtimeEnginePluginProfile = stringArg('runtime-engine-plugin-profile', 'FL_RUNTIME_ENGINE_PLUGIN_PROFILE', defaultRuntimeEnginePluginProfile(scenario));
const appImagePrefix = imageConfig.imagePrefix;
const defaultRuntimeEngineImage = `${appImagePrefix}/federation-learning-runtime-engine:${runtimeEngineImageVersion(runtimeEnginePluginProfile, imageVersion)}`;
let runtimeEngineImage = stringArg(
    'runtime-engine-image',
    'FL_RUNTIME_ENGINE_IMAGE',
    defaultRuntimeEngineImage
);
const internalToken = optionalString(args['internal-token'] ?? process.env.MEDOL_SECURITY_INTERNAL_TOKEN) ?? 'local-dev-internal-token';
let runtimeAgentImage = optionalString(args['runtime-agent-image'] ?? process.env.FL_RUNTIME_AGENT_IMAGE);
let participantConsoleImage = optionalString(args['participant-console-image'] ?? process.env.FL_PARTICIPANT_CONSOLE_IMAGE);
const k3dScript = resolve(repoRoot, 'operations/k3s/scripts/federation-learning-dev.sh');
const kubeconfigFile = resolve(
    repoRoot,
    `operations/k3s/.work/kubeconfig-${clusterName}.yaml`
);
const portForwardProcesses = [];
const authTokensByBaseUrl = new Map();

process.on('exit', cleanupPortForwards);
process.on('SIGINT', () => {
    cleanupPortForwards();
    process.exit(130);
});
process.on('SIGTERM', () => {
    cleanupPortForwards();
    process.exit(143);
});

const recreateCluster = booleanOption(args.recreate ?? process.env.FL_K3D_RECREATE, false);
const rebuildAppImages = booleanOption(args['rebuild-app-images'] ?? process.env.FL_K3D_REBUILD_APP_IMAGES, false);
const skipCluster = booleanOption(args['skip-cluster'] ?? process.env.FL_K3D_SKIP_CLUSTER, false);
const skipApply = booleanOption(args['skip-apply'] ?? process.env.FL_K3D_SKIP_APPLY, false);
const skipDictionary = booleanOption(args['skip-dictionary'] ?? process.env.FL_K3D_SKIP_DICTIONARY, false);
const skipAdminSetup = booleanOption(args['skip-admin-setup'] ?? process.env.FL_K3D_SKIP_ADMIN_SETUP, false);
const skipRuntimeAgentData = booleanOption(args['skip-runtime-agent-data'] ?? process.env.FL_K3D_SKIP_RUNTIME_AGENT_DATA, false);
const skipTrainingJob = booleanOption(args['skip-training-job'] ?? process.env.FL_K3D_SKIP_TRAINING_JOB, false);
const syncDefaultKubeconfig = booleanOption(args['sync-default-kubeconfig'] ?? process.env.FL_K3D_SYNC_DEFAULT_KUBECONFIG, true);
const dryRun = booleanOption(args['dry-run'], false);

console.log('[k3d-flow] configuration');
console.log(JSON.stringify({
    environment,
    clusterName,
    namespace,
    gatewayUrl,
    platformUrl,
    supportUrl,
    runtimeAgentUrl,
    runtimeAgentPortForwardPort,
    adminUsername,
    adminSetupEnabled: !skipAdminSetup,
    scenario,
    federationMode,
    runtimeEnginePluginProfile,
    participantCount,
    trainingRunKey,
    waitForTraining,
    rebuildAppImages,
    appImagePrefix,
    buildImagePrefix: imageConfig.buildPrefix,
    imageSource: imageConfig.mode,
    runtimeEngineImage,
    internalTokenConfigured: Boolean(internalToken),
    imageVersion,
    skipCluster,
    skipApply,
    skipDictionary,
    skipRuntimeAgentData,
    skipTrainingJob,
    syncDefaultKubeconfig,
    dryRun
}, null, 2));

if (!existsSync(k3dScript)) {
    fail(`K3D helper script was not found: ${k3dScript}`);
}

verifyDeploymentPorts();

if (dryRun) {
    console.log('[k3d-flow] dry run stops before creating cluster or calling APIs.');
    process.exit(0);
}

if (rebuildAppImages) {
    rebuildApplicationImages();
}
if (!skipCluster) {
    await ensureCluster();
}
ensureKubeconfigUsable();
const resolvedRuntimeEngineImage = ensureRuntimeEngineImageAvailable();
const applicationImages = prepareApplicationImages();
if (!skipApply) {
    await waitForKubernetesApi();
    await waitForKubernetesNodesReady();
    prepareRuntimeSchedulingNodes();
    ensureDevSecrets();
    runK3d('apply');
    refreshVerifiedImageDeployments(applicationImages);
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
if (!skipAdminSetup) {
    await ensureAdminUser(supportUrl, 'support');
}

const participants = [];
for (let slot = 1; slot <= participantCount; slot += 1) {
    participants.push(await prepareParticipant(slot));
}

let trainingSeed = null;
if (!skipTrainingJob) {
    await waitForDeployment('federation-learning-aggregation-runtime-engine', 'aggregation runtime engine');
    trainingSeed = await runInitTraining('training-job', {
        createJob: true,
        registerRuntimeInfrastructure: false,
        runtimeAgentUrl: participants[0]?.runtimeAgentUrl ?? runtimeAgentUrl,
        participantKey: participantKeyForSlot(1),
    });
    if (waitForTraining) {
        await verifyTrainingResult(trainingSeed.trainingJobId, participants);
    }
}

console.log('[k3d-flow] ready');
console.log(JSON.stringify({
    participants,
    trainingJobId: trainingSeed?.trainingJobId
}, null, 2));
cleanupPortForwards();

async function ensureCluster() {
    if (recreateCluster) {
        runK3d('recreate');
        syncDefaultKubeconfigContext();
        return;
    }
    const exists = commandOk('k3d', ['cluster', 'get', clusterName]);
    runK3d(exists ? 'kubeconfig' : 'create');
    syncDefaultKubeconfigContext();
}

function ensureKubeconfigUsable() {
    const firstCheck = commandResult('kubectl', ['get', '--raw=/readyz'], kubeEnv());
    if (firstCheck.status === 0) {
        return;
    }
    if (!isUnknownAuthorityFailure(firstCheck)) {
        return;
    }

    console.log('[k3d-flow] kubectl reported an unknown-authority TLS error; refreshing k3d kubeconfig');
    runK3d('kubeconfig');
    syncDefaultKubeconfigContext();

    const secondCheck = commandResult('kubectl', ['get', '--raw=/readyz'], kubeEnv());
    if (secondCheck.status !== 0 && isUnknownAuthorityFailure(secondCheck)) {
        fail([
            'Kubernetes API TLS verification still fails after refreshing k3d kubeconfig.',
            `Dedicated kubeconfig: ${kubeconfigFile}`,
            'Try deleting and recreating the k3d cluster if the API server certificate changed while the server is still running.',
            secondCheck.stderr ? `stderr tail: ${compactTail(secondCheck.stderr)}` : ''
        ].filter(Boolean).join('\n'));
    }
}

function syncDefaultKubeconfigContext() {
    if (!syncDefaultKubeconfig) {
        return;
    }
    const expectedContext = `k3d-${clusterName}`;
    const expectedCluster = expectedContext;
    const expectedUser = `admin@${expectedContext}`;
    const currentContext = commandOutputOrNull('kubectl', ['config', 'current-context'])?.trim();
    if (currentContext && currentContext !== expectedContext) {
        return;
    }
    if (!commandOk('k3d', ['cluster', 'get', clusterName])) {
        return;
    }

    if (!mergeDefaultKubeconfig(expectedContext)) {
        return;
    }

    const check = commandResult('kubectl', ['--context', expectedContext, 'get', '--raw=/readyz']);
    if (check.status === 0) {
        console.log(`[k3d-flow] synced default kubeconfig context ${expectedContext}`);
        return;
    }
    if (!isUnknownAuthorityFailure(check)) {
        console.log([
            `[k3d-flow] warning: synced default kubeconfig context ${expectedContext}, but kubectl check failed`,
            check.stderr ? `stderr tail: ${compactTail(check.stderr)}` : ''
        ].filter(Boolean).join('\n'));
        return;
    }

    console.log(`[k3d-flow] stale default kubeconfig certificate detected for ${expectedContext}; rebuilding context entry`);
    commandResult('kubectl', ['config', 'delete-context', expectedContext]);
    commandResult('kubectl', ['config', 'delete-cluster', expectedCluster]);
    commandResult('kubectl', ['config', 'delete-user', expectedUser]);

    if (!mergeDefaultKubeconfig(expectedContext)) {
        return;
    }
    const rebuiltCheck = commandResult('kubectl', ['--context', expectedContext, 'get', '--raw=/readyz']);
    if (rebuiltCheck.status === 0) {
        console.log(`[k3d-flow] rebuilt default kubeconfig context ${expectedContext}`);
        return;
    }
    console.log([
        `[k3d-flow] warning: rebuilt default kubeconfig context ${expectedContext}, but kubectl check still failed`,
        rebuiltCheck.stderr ? `stderr tail: ${compactTail(rebuiltCheck.stderr)}` : ''
    ].filter(Boolean).join('\n'));
}

function mergeDefaultKubeconfig(expectedContext) {
    const args = ['kubeconfig', 'merge', clusterName, '--kubeconfig-merge-default', '--kubeconfig-switch-context'];
    const result = commandResult('k3d', args);
    if (result.status === 0) {
        return true;
    }
    console.log([
        `[k3d-flow] warning: failed to sync default kubeconfig context ${expectedContext}`,
        result.stderr ? `stderr tail: ${compactTail(result.stderr)}` : ''
    ].filter(Boolean).join('\n'));
    return false;
}

function isUnknownAuthorityFailure(result) {
    const output = `${result.stdout ?? ''}\n${result.stderr ?? ''}`;
    return output.includes('x509: certificate signed by unknown authority') ||
        output.includes('tls: failed to verify certificate');
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
        FL_OPERATIONS_ENVIRONMENT: environment,
        ENVIRONMENT_OVERLAY: k3sDevEnvironmentDir,
        REGISTRY_OVERLAY: `${k3sDevEnvironmentDir}-registry`,
        SECRETS_FILE: k3sDevSecretsFile,
        CLUSTER_NAME: clusterName,
        NAMESPACE: namespace,
        KUBECONFIG_FILE: kubeconfigFile,
        KUBECONFIG: kubeconfigFile,
        DOCKER_IMAGE_PREFIX: appImagePrefix,
        IMAGE_VERSION: imageVersion,
        MEDOL_SECURITY_ADMIN_BOOTSTRAP_SETUP_TOKEN: adminSetupToken,
        FL_RUNTIME_ENGINE_IMAGE: runtimeEngineImage,
        FL_AGGREGATION_RUNTIME_ENGINE_IMAGE: runtimeEngineImage,
        ...(runtimeAgentImage ? {FL_RUNTIME_AGENT_IMAGE: runtimeAgentImage} : {}),
        ...(participantConsoleImage ? {FL_PARTICIPANT_CONSOLE_IMAGE: participantConsoleImage} : {})
    });
}

async function prepareParticipant(slot) {
    const participantKey = participantKeyForSlot(slot);
    const participantRuntimeAgentUrl = configuredRuntimeAgentUrlForSlot(slot) ?? runtimeAgentUrlForSlot(slot);
    console.log(`[k3d-flow] prepare participant slot=${slot} key=${participantKey}`);
    const platformSeed = await runInitTraining('platform', {
        createJob: false,
        registerRuntimeInfrastructure: true,
        runtimeAgentUrl: participantRuntimeAgentUrl,
        participantKey,
    });
    const runtimeInfrastructureId = platformSeed.runtimeInfrastructureId;
    const organizationId = platformSeed.organizationId;
    const runtimeAgentId = platformSeed.runtimeAgentId;

    console.log(`[k3d-flow] participant ${slot} organizationId=${organizationId}`);
    console.log(`[k3d-flow] participant ${slot} runtimeInfrastructureId=${runtimeInfrastructureId}`);
    console.log(`[k3d-flow] participant ${slot} runtimeAgentId=${runtimeAgentId}`);

    let deploymentRetried = false;
    await labelRuntimeNode(runtimeInfrastructureId, slot);
    const runtimeInfrastructure = await waitForRuntimeInfrastructure(
        runtimeInfrastructureId,
        (item) => isRegisteredOrBeyond(item) || isRuntimeAgentFailureState(item?.state),
        `participant ${slot} runtime infrastructure`
    );
    if (isRuntimeAgentFailureState(runtimeInfrastructure?.state)) {
        await retryRuntimeAgentDeployment(runtimeInfrastructure);
        deploymentRetried = true;
    } else {
        await confirmRuntimeInfrastructurePrepared(runtimeInfrastructure);
    }

    const verifiedInfrastructure = await waitForRuntimeInfrastructure(
        runtimeInfrastructureId,
        (item) => isStateAtLeast(item?.state, ['VERIFIED', 'AGENTREADY', 'CONNECTED']) ||
            (!deploymentRetried && isFailureState(item?.state)),
        `participant ${slot} verified runtime infrastructure`
    );
    if (isFailureState(verifiedInfrastructure?.state)) {
        if (!isRuntimeAgentFailureState(verifiedInfrastructure?.state)) {
            fail(`Runtime infrastructure failed before agent deployment: ${JSON.stringify(verifiedInfrastructure)}`);
        }
        await retryRuntimeAgentDeployment(verifiedInfrastructure);
        deploymentRetried = true;
    }

    const deployedInfrastructure = await waitForRuntimeInfrastructure(
        runtimeInfrastructureId,
        (item) => isStateAtLeast(item?.state, ['AGENTREADY', 'CONNECTED']) ||
            (!deploymentRetried && isFailureState(item?.state)),
        `participant ${slot} runtime agent deployment`
    );
    if (isFailureState(deployedInfrastructure?.state)) {
        fail(`Runtime agent deployment failed: ${JSON.stringify(deployedInfrastructure)}`);
    }

    await waitForManagedRuntimeAgent(runtimeAgentId);
    const accessibleRuntimeAgentUrl = await ensureManagedRuntimeAgentAccess(runtimeAgentId, slot);
    await waitForHttp(`${accessibleRuntimeAgentUrl}/actuator/health`, `participant ${slot} managed runtime-agent health`);
    if (!skipAdminSetup) {
        await ensureAdminUser(accessibleRuntimeAgentUrl, `runtime-agent participant ${slot}`);
    }
    await loadRuntimeAgentBootstrapConfiguration(accessibleRuntimeAgentUrl);
    await recordManagedRuntimeAgentConnection(deployedInfrastructure);
    const endpoint = await waitForRuntimeAgentEndpoint(runtimeAgentId, runtimeInfrastructureId);
    console.log(`[k3d-flow] participant ${slot} platform runtime agent endpoint=${endpoint.runtimeAgentEndpoint ?? endpoint.endpoint ?? ''}`);

    if (!skipRuntimeAgentData) {
        await runInitTraining('runtime-agent', {
            createJob: false,
            registerRuntimeInfrastructure: false,
            runtimeAgentUrl: accessibleRuntimeAgentUrl,
            participantKey,
        });
    }

    return {
        slot,
        participantKey,
        organizationId,
        runtimeInfrastructureId,
        runtimeAgentId,
        runtimeAgentUrl: accessibleRuntimeAgentUrl,
    };
}

function rebuildApplicationImages() {
    const imagesScript = resolve(repoRoot, 'operations/images/images.mjs');
    if (!existsSync(imagesScript)) {
        fail(`Application image helper was not found: ${imagesScript}`);
    }
    run('node', [
        imagesScript,
        'build',
        '--environment', environment,
        '--prefix', imageConfig.buildPrefix,
        '--version', imageVersion,
        '--skip-console'
    ], {});
    if (imageConfig.mode === 'registry') run('node', [
        imagesScript,
        'push',
        '--environment', environment,
        '--push-prefix', imageConfig.pushPrefix,
        '--prefix', imageConfig.buildPrefix,
        '--version', imageVersion,
        '--skip-console'
    ], {});
}

function resolveConfiguredImage(image, aliases = []) {
    const artifact = resolveImageArtifact(imageConfig, image, {
        pull: (source) => run('docker', ['pull', source], {}),
        tag: (source, target) => run('docker', ['tag', source, target], {}),
        inspect: (source) => {
            const result = commandResult('docker', ['image', 'inspect', source]);
            return result.status === 0 ? JSON.parse(result.stdout)[0] : null;
        },
    });
    console.log(`[k3d-flow] verified image source=${artifact.source} deployment=${artifact.reference} id=${artifact.id}`);
    return {...artifact, aliases: [image, artifact.reference, ...aliases]};
}

function prepareApplicationImages() {
    if (skipApply) return new Map();
    const images = new Map();
    for (const application of imageConfig.applications) {
        const name = application.artifactName ?? application.name;
        let image = `${appImagePrefix}/${name}:${imageVersion}`;
        if (application.name === 'federation-learning-runtime-agent' && runtimeAgentImage) image = runtimeAgentImage;
        if (application.name === 'federation-learning-participant-console' && participantConsoleImage) image = participantConsoleImage;
        const resolved = resolveConfiguredImage(image, [
            `${application.imageName}:${imageVersion}`,
            `${application.imageName}:${application.imageTag ?? imageConfig.baseVersion}`,
        ]);
        resolved.applicationName = application.name;
        images.set(image, resolved);
        if (application.name === 'federation-learning-runtime-agent') runtimeAgentImage = resolved.reference;
        if (application.name === 'federation-learning-participant-console') participantConsoleImage = resolved.reference;
    }
    if (images.size && imageConfig.mode === 'import') {
        run('k3d', ['image', 'import', '--mode', 'direct', ...images.keys(), '--cluster', clusterName], {});
    }
    return images;
}

function refreshVerifiedImageDeployments(images) {
    const engine = resolvedRuntimeEngineImage;
    const resolvedImages = [...images.values(), engine];
    const repository = (image) => String(image).split('@')[0].replace(/:[^/:]+$/, '');
    const deployments = JSON.parse(runCapture('kubectl', ['-n', namespace, 'get', 'deployments', '-o', 'json'], kubeEnv()));
    for (const deployment of deployments.items ?? []) {
        const containers = deployment.spec.template.spec.containers
            .map((container) => ({container, image: resolvedImages.find((image) =>
                image.applicationName === deployment.metadata.name && image.applicationName === container.name
                || image.aliases.some((alias) => repository(alias) === repository(container.image)))}))
            .filter((entry) => entry.image);
        if (!containers.length) continue;
        const imageIds = containers.map(({container, image}) => `${container.name}=${image.id}`).sort().join(',');
        const annotation = 'medol.dev/local-image-ids';
        if (deployment.spec.template.metadata.annotations?.[annotation] === imageIds
            && containers.every(({container, image}) => container.image === image.reference && container.imagePullPolicy === 'IfNotPresent')) continue;
        console.log(`[k3d-flow] refreshing verified images for ${deployment.metadata.name}`);
        run('kubectl', ['-n', namespace, 'patch', 'deployment', deployment.metadata.name, '--type', 'strategic', '-p', JSON.stringify({
            spec: {template: {
                metadata: {annotations: {[annotation]: imageIds}},
                spec: {containers: containers.map(({container, image}) => ({
                    name: container.name, image: image.reference, imagePullPolicy: 'IfNotPresent'
                }))}
            }}
        })], kubeEnv());
    }
}

function ensureRuntimeEngineImageAvailable() {
    const resolved = resolveConfiguredImage(runtimeEngineImage);
    if (imageConfig.mode === 'import') {
        run('k3d', ['image', 'import', '--mode', 'direct', runtimeEngineImage, '--cluster', clusterName], {});
    }
    runtimeEngineImage = resolved.reference;
    return resolved;
}

async function initDictionaries() {
    const dictionaryScript = resolve(repoRoot, 'dictionary-init/init-dictionaries.mjs');
    if (!existsSync(dictionaryScript)) {
        console.log(`[k3d-flow] skip missing dictionary init script: ${dictionaryScript}`);
        return;
    }
    run('node', [
        dictionaryScript,
        '--base-url', supportUrl,
        ...(internalToken ? ['--internal-token', internalToken] : [])
    ], {FL_API_TOKEN: await ensureAdminToken()});
}

async function ensureAdminUser(baseUrl, label) {
    const url = `${baseUrl}/api/auth/setup-admin`;
    const response = await httpRequest(url, {
        method: 'POST',
        headers: internalRequestHeaders({'content-type': 'application/json'}),
        body: JSON.stringify({
            setupToken: adminSetupToken,
            username: adminUsername,
            password: adminPassword
        })
    });
    const body = await response.text();
    if (response.ok) {
        authTokensByBaseUrl.set(trimSlash(baseUrl), await loginAdmin(baseUrl, label));
        console.log(`[k3d-flow] initialized ${label} admin user ${adminUsername}`);
        return;
    }
    if (response.status === 409) {
        authTokensByBaseUrl.set(trimSlash(baseUrl), await loginAdmin(baseUrl, label));
        console.log(`[k3d-flow] ${label} admin user ${adminUsername} already exists`);
        return;
    }
    fail(`Initialize ${label} admin user failed: ${response.status} ${compact(body)}`);
}

async function loginAdmin(baseUrl, label) {
    const url = `${baseUrl}/api/auth/login`;
    const response = await httpRequest(url, {
        method: 'POST',
        headers: internalRequestHeaders({'content-type': 'application/json'}),
        body: JSON.stringify({
            username: adminUsername,
            password: adminPassword
        })
    });
    const body = await response.text();
    if (!response.ok) {
        fail(
            `${label} admin is already initialized, but ${adminUsername}/${adminPassword} login failed: ` +
            `${response.status} ${compact(body)}`
        );
    }
    const payload = body ? JSON.parse(body) : {};
    if (!payload.accessToken) {
        fail(`${label} admin login response did not include an access token.`);
    }
    return payload.accessToken;
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

    await ensureAdminUser(supportUrl, 'support');
    const token = authTokensByBaseUrl.get(trimSlash(supportUrl));
    if (!token) {
        fail('Support admin login did not provide an access token.');
    }
    console.log(`[k3d-flow] authenticated ${adminUsername}; support token is scoped to ${supportUrl}`);
    return token;
}

async function runInitTraining(target, options) {
    const initScript = resolve(scriptDir, 'init-training-prerequisites.mjs');
    const extraEnv = initTrainingEnvironment();
    console.log(
        `[k3d-flow] run init-training target=${target} authMode=${initTrainingAuthMode(extraEnv)} ` +
        `runtimeAgentUrl=${options.runtimeAgentUrl}`
    );
    const output = runCapture('node', [
        initScript,
        '--target', target,
        '--scenario', scenario,
        '--federation-mode', federationMode,
        ...(trainingRunKey ? ['--training-run-key', trainingRunKey] : []),
        ...(options.participantKey ? ['--participant-key', options.participantKey] : []),
        '--platform-url', platformUrl,
        '--support-url', supportUrl,
        '--runtime-agent-url', options.runtimeAgentUrl,
        '--skip-admin-setup',
        '--runtime-environment-type', 'K3S',
        '--agent-install-mode', 'PLATFORM_MANAGED',
        '--endpoint-scope', 'CLUSTER',
        '--runtime-engine-image', runtimeEngineImage,
        '--register-runtime-infrastructure', String(options.registerRuntimeInfrastructure),
        '--create-job', String(options.createJob),
        '--timeout', String(timeoutMs),
        '--poll-interval', String(Math.min(pollIntervalMs, 1000)),
        ...(dryRun ? ['--dry-run'] : [])
    ], extraEnv);
    process.stdout.write(output);
    return extractLastJsonObject(output);
}

function verifyDeploymentPorts() {
    const modelPath = resolve(repoRoot, 'operations/model', environment, 'operations-model.json');
    const schedulingPath = resolve(repoRoot, 'operations/k3s/components/runtime-scheduling/patches/runtime-scheduler-config.yaml');
    if (!existsSync(modelPath) || !existsSync(schedulingPath)) {
        return;
    }
    const model = JSON.parse(readFileSync(modelPath, 'utf8'));
    const applications = new Map((model.applications ?? []).map((application) => [application.name, application]));
    const scheduling = readFileSync(schedulingPath, 'utf8');
    const expected = [
        ['federation-learning-platform', 8081],
        ['federation-learning-runtime-agent', 8082],
        ['federation-learning-support', 8080]
    ];
    const mismatches = expected.flatMap(([name, port]) => {
        const actual = applications.get(name)?.servicePort;
        return actual === port ? [] : [`${name}: generated Service port ${actual ?? 'missing'}, runtime configuration expects ${port}`];
    });
    for (const [name, port] of expected.filter(([name]) => name !== 'federation-learning-runtime-agent')) {
        if (!scheduling.includes(`http://${name}:${port}`)) {
            mismatches.push(`${name}: runtime-scheduling config does not point to port ${port}`);
        }
    }
    if (mismatches.length > 0) {
        fail(`Deployment port mismatch before seeding:\n${mismatches.join('\n')}\nRegenerate operations from .medol/medol.yml, then re-apply the K3s manifests.`);
    }
}

function initTrainingEnvironment() {
    return {
        FL_API_TOKEN: '',
        FL_PLATFORM_API_TOKEN: process.env.FL_PLATFORM_API_TOKEN?.trim() ?? '',
        FL_SUPPORT_API_TOKEN: process.env.FL_SUPPORT_API_TOKEN?.trim() ?? '',
        FL_RUNTIME_AGENT_API_TOKEN: process.env.FL_RUNTIME_AGENT_API_TOKEN?.trim() ?? '',
        MEDOL_SECURITY_INTERNAL_TOKEN: internalToken ?? ''
    };
}

function initTrainingAuthMode(extraEnv) {
    if (extraEnv.MEDOL_SECURITY_INTERNAL_TOKEN?.trim()) {
        return 'internal-token';
    }
    if (
        extraEnv.FL_PLATFORM_API_TOKEN?.trim() ||
        extraEnv.FL_SUPPORT_API_TOKEN?.trim() ||
        extraEnv.FL_RUNTIME_AGENT_API_TOKEN?.trim()
    ) {
        return 'scoped-api-token';
    }
    if (extraEnv.FL_API_TOKEN?.trim()) {
        return 'api-token';
    }
    return 'none';
}

function participantKeyForSlot(slot) {
    const configured = optionalString(process.env[`FL_PARTICIPANT_${slot}_KEY`]);
    return configured ?? `participant-${slot}`;
}

function configuredRuntimeAgentUrlForSlot(slot) {
    return trimSlashOrNull(process.env[`FL_RUNTIME_AGENT_URL_${slot}`]) ??
        (slot === 1 ? trimSlashOrNull(configuredRuntimeAgentUrl) : null);
}

function runtimeAgentUrlForSlot(slot) {
    return trimSlash(`http://127.0.0.1:${runtimeAgentPortForwardPortForSlot(slot)}`);
}

function runtimeAgentPortForwardPortForSlot(slot) {
    return runtimeAgentPortForwardPort + slot - 1;
}

function defaultRuntimeEnginePluginProfile(value) {
    const normalized = String(value ?? '').trim().toLowerCase();
    if (['densenet', 'densenet121', 'tiny-imagenet', 'tiny-imagenet-densenet'].includes(normalized)) {
        return 'pytorch-vision';
    }
    if (normalized === 'csv') return 'sklearn';
    return 'full';
}

function runtimeEngineImageVersion(profile, version) {
    const safeProfile = dockerTagPart(profile);
    const safeVersion = String(version).trim();
    if (!safeProfile || safeVersion.startsWith(`${safeProfile}-`)) return safeVersion;
    return `${safeProfile}-${safeVersion}`;
}

function dockerTagPart(value) {
    return String(value ?? '')
        .trim()
        .toLowerCase()
        .replace(/[^a-z0-9_.-]+/g, '-')
        .replace(/^-+|-+$/g, '');
}

function runtimeNodeForSlot(slot) {
    const nodes = JSON.parse(runCapture('kubectl', ['get', 'nodes', '-o', 'json'], kubeEnv()));
    const runtimeNodes = nodes.items
        .filter((node) => !isControlPlaneNode(node))
        .map((node) => node.metadata?.name)
        .filter(Boolean);
    if (runtimeNodes.length === 0) {
        fail('No k3d agent node was found for runtime scheduling.');
    }
    const nodeName = runtimeNodes[slot - 1];
    if (!nodeName) {
        fail(`Participant ${slot} requires a k3d agent node, but only ${runtimeNodes.length} agent node(s) were found.`);
    }
    return nodeName;
}

function prepareRuntimeSchedulingNodes() {
    const nodeNames = Array.from({length: participantCount}, (_, index) => runtimeNodeForSlot(index + 1));
    for (const nodeName of nodeNames) {
        run('kubectl', ['label', 'node', nodeName, 'medol.dev/node-role=runtime', '--overwrite'], kubeEnv());
        run('kubectl', ['taint', 'node', nodeName, 'medol.dev/runtime-only=true:NoSchedule', '--overwrite'], kubeEnv());
    }
}

async function labelRuntimeNode(runtimeInfrastructureId, slot) {
    const nodeName = runtimeNodeForSlot(slot);
    console.log(`[k3d-flow] label participant ${slot} runtime node ${nodeName}`);
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

async function loadRuntimeAgentBootstrapConfiguration(targetRuntimeAgentUrl) {
    await postCommand(
        targetRuntimeAgentUrl,
        '/runtimeagentlifecycle/loadruntimeagentbootstrapconfiguration',
        {},
        'runtime agent bootstrap configuration load'
    );
}

async function retryRuntimeAgentDeployment(runtimeInfrastructure) {
    console.log('[k3d-flow] runtime agent deployment previously failed; retrying managed deployment');
    await postCommand(platformUrl, '/runtimeinfrastructure/retryruntimeagentdeployment', {
        runtimeInfrastructureId: runtimeInfrastructure.runtimeInfrastructureId,
        runtimeInstallationPlanId: runtimeInfrastructure.runtimeInstallationPlanId,
        runtimeAgentId: runtimeInfrastructure.runtimeAgentId,
        organizationId: runtimeInfrastructure.organizationId,
        organizationName: runtimeInfrastructure.organizationName ?? null,
        runtimeInfrastructurePackageId: runtimeInfrastructure.runtimeInfrastructurePackageId,
        runtimeInfrastructurePackageName: runtimeInfrastructure.runtimeInfrastructurePackageName ?? null,
        runtimeInfrastructurePackageVersion: runtimeInfrastructure.runtimeInfrastructurePackageVersion ?? null,
        runtimeEnvironmentType: runtimeInfrastructure.runtimeEnvironmentType ?? 'K3S',
        runtimeName: runtimeInfrastructure.runtimeName,
        agentInstallMode: runtimeInfrastructure.agentInstallMode ?? 'PLATFORM_MANAGED',
        expectedNodeCount: runtimeInfrastructure.expectedNodeCount ?? preparedNodeCount,
        retryReason: 'k3d-training-flow retry after applying managed runtime-agent prerequisites.'
    }, 'retry runtime agent deployment');
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

async function findRuntimeAgentEndpoint(runtimeAgentId, runtimeInfrastructureId) {
    const page = await getPage(platformUrl, '/runtimeinfrastructure/runtimeagentendpointcatalog', {
        'runtimeAgentId.equals': runtimeAgentId,
        'runtimeInfrastructureId.equals': runtimeInfrastructureId,
        size: '20'
    });
    return (page.content ?? []).find((item) => Boolean(item?.runtimeAgentEndpoint ?? item?.endpoint)) ?? null;
}

async function waitForManagedRuntimeAgent(runtimeAgentId) {
    const deploymentName = managedRuntimeAgentName(runtimeAgentId);
    await waitForDeployment(deploymentName, 'managed runtime-agent');
    const consoleDeploymentName = `${deploymentName}-console`;
    if (commandOk('kubectl', ['-n', namespace, 'get', 'deploy', consoleDeploymentName], kubeEnv())) {
        await waitForDeployment(consoleDeploymentName, 'managed runtime-agent participant console');
    }
}

async function ensureManagedRuntimeAgentAccess(runtimeAgentId, slot) {
    const configured = configuredRuntimeAgentUrlForSlot(slot);
    if (configured) {
        return configured;
    }
    const serviceName = managedRuntimeAgentName(runtimeAgentId);
    const localPort = await startPortForward(
        serviceName,
        runtimeAgentPortForwardPortForSlot(slot),
        8082,
        `participant ${slot} managed runtime-agent`
    );
    return trimSlash(`http://127.0.0.1:${localPort}`);
}

async function recordManagedRuntimeAgentConnection(runtimeInfrastructure) {
    const serviceName = managedRuntimeAgentName(runtimeInfrastructure.runtimeAgentId);
    const existingEndpoint = await findRuntimeAgentEndpoint(
        runtimeInfrastructure.runtimeAgentId,
        runtimeInfrastructure.runtimeInfrastructureId
    );
    if (existingEndpoint) {
        console.log('[k3d-flow] managed runtime agent connection already recorded');
        return;
    }

    const payload = {
        runtimeInfrastructureId: runtimeInfrastructure.runtimeInfrastructureId,
        runtimeAgentId: runtimeInfrastructure.runtimeAgentId,
        agentInstallMode: runtimeInfrastructure.agentInstallMode ?? 'PLATFORM_MANAGED',
        organizationId: runtimeInfrastructure.organizationId,
        organizationName: runtimeInfrastructure.organizationName ?? null,
        runtimeName: runtimeInfrastructure.runtimeName,
        runtimeAgentEndpoint: `http://${serviceName}:8082`,
        endpointScope: 'CLUSTER'
    };
    const result = await postCommandResult(
        platformUrl,
        '/runtimeinfrastructure/recordruntimeconnectionestablished',
        payload,
        'managed runtime agent connection'
    );
    if (result.ok) {
        console.log('[k3d-flow] posted managed runtime agent connection');
        return;
    }

    const endpoint = await waitForRuntimeAgentEndpoint(
        runtimeInfrastructure.runtimeAgentId,
        runtimeInfrastructure.runtimeInfrastructureId
    ).catch(() => null);
    if (endpoint) {
        console.log(`[k3d-flow] managed runtime agent connection was recorded by a concurrent report; ignoring POST ${result.status}`);
        return;
    }

    fail(`POST managed runtime agent connection failed: ${result.status} ${compact(result.body)}`);
}

async function startPortForward(serviceName, localPort, remotePort, label) {
    const availableLocalPort = await findAvailableLocalPort(localPort);
    if (availableLocalPort !== localPort) {
        console.log(`[k3d-flow] local port ${localPort} is not available; using ${availableLocalPort} for ${label}`);
    }
    const child = spawn('kubectl', [
        '-n', namespace,
        'port-forward',
        `svc/${serviceName}`,
        `${availableLocalPort}:${remotePort}`
    ], {
        env: {...process.env, ...kubeEnv()},
        stdio: ['ignore', 'pipe', 'pipe']
    });
    child.stdout.on('data', (chunk) => process.stdout.write(`[k3d-flow] ${label} port-forward: ${chunk}`));
    child.stderr.on('data', (chunk) => process.stderr.write(`[k3d-flow] ${label} port-forward: ${chunk}`));
    portForwardProcesses.push(child);
    await waitForCondition(`${label} port-forward`, async () => {
        if (child.exitCode !== null) {
            fail(`${label} port-forward exited with code ${child.exitCode}`);
        }
        return isHttpReachable(`http://127.0.0.1:${availableLocalPort}/actuator/health`);
    });
    return availableLocalPort;
}

async function isHttpReachable(url) {
    try {
        const response = await httpRequest(url);
        debugHttp(`${url} -> ${response.status}`);
        return response.ok;
    } catch (error) {
        debugHttp(`${url} -> ${error?.message ?? String(error)}`);
        return false;
    }
}

function debugHttp(message) {
    if (booleanOption(process.env.FL_K3D_FLOW_DEBUG_HTTP, false)) {
        console.log(`[k3d-flow] http ${message}`);
    }
}

async function findAvailableLocalPort(preferredPort) {
    for (let port = preferredPort; port < preferredPort + 100; port += 1) {
        if (!hasLocalTcpListener(port) && await isLocalPortAvailable(port)) {
            return port;
        }
    }
    fail(`No available local port found for runtime-agent port-forward starting at ${preferredPort}.`);
}

function hasLocalTcpListener(port) {
    return commandOk('lsof', ['-nP', `-iTCP:${port}`, '-sTCP:LISTEN']);
}

function isLocalPortAvailable(port) {
    return new Promise((resolve) => {
        const server = createServer();
        server.once('error', () => resolve(false));
        server.once('listening', () => {
            server.close(() => resolve(true));
        });
        server.listen({port, host: '127.0.0.1', exclusive: true});
    });
}

function managedRuntimeAgentName(runtimeAgentId) {
    return `federation-learning-runtime-agent-managed-${String(runtimeAgentId).replaceAll('-', '').slice(0, 12)}`;
}

async function waitForDeployment(name, label = name) {
    await waitForCondition(label, () => commandOk('kubectl', [
        '-n', namespace,
        'rollout', 'status',
        `deploy/${name}`,
        '--timeout=5s'
    ], kubeEnv()), () => [
        `Expected namespace: ${namespace}`,
        diagnosticCommand('deployment locations', ['kubectl', ['get', 'deploy', '-A', '--field-selector', `metadata.name=${name}`, '-o', 'wide']]),
        diagnosticCommand('deployment description', ['kubectl', ['-n', namespace, 'describe', 'deploy', name]]),
        diagnosticCommand('recent events', ['kubectl', ['-n', namespace, 'get', 'events', '--sort-by=.metadata.creationTimestamp']])
    ].join('\n'));
}

async function verifyTrainingResult(trainingJobId, participants) {
    const query = {'trainingJobId.equals': trainingJobId};
    let lastStatus = {};
    await waitForCondition('training job completion', async () => {
        const rounds = await getPage(platformUrl, '/traininground/trainingroundprogress', query);
        lastStatus.rounds = rounds.content ?? [];
        const failed = (rounds.content ?? []).find((round) => String(round.state).toUpperCase() === 'FAILED');
        if (failed) fail(`Training round ${failed.roundId} failed: ${failed.failureReason ?? 'unknown reason'}`);
        const jobs = await getPage(platformUrl, '/trainingjob/trainingjobdashboard', query);
        lastStatus.jobs = jobs.content ?? [];
        const failedJob = lastStatus.jobs.find((job) => ['FAILED', 'CANCELLED', 'CANCELED'].includes(String(job.state).toUpperCase()));
        if (failedJob) fail(`Training job ${trainingJobId} ended in ${failedJob.state}: ${failedJob.failureReason ?? 'no failure reason reported'}`);
        const completed = lastStatus.jobs.find((job) => String(job.state).toUpperCase() === 'COMPLETED');
        if (completed) return completed;
        const latestRound = [...lastStatus.rounds].sort((a, b) => Number(b.roundNumber) - Number(a.roundNumber))[0];
        lastStatus.participants = [];
        if (latestRound?.roundId) {
            for (const participant of participants) {
                const page = await getPage(participant.runtimeAgentUrl, '/roundexecution/roundexecutioncatalog', {
                    ...query, 'roundId.equals': latestRound.roundId,
                });
                lastStatus.participants.push({runtimeId: participant.runtimeAgentId,
                    executions: (page.content ?? []).map(({roundExecutionId, state, failureReason, rejectionReasons}) =>
                        ({roundExecutionId, state, failureReason, rejectionReasons}))});
            }
            const failures = lastStatus.participants.filter((participant) =>
                participant.executions.length > 0 && participant.executions.every((execution) =>
                    ['FAILED', 'STARTFAILED', 'STARTRETRYFAILED', 'RUNTIMERETRYFAILED'].includes(String(execution.state).toUpperCase()) ||
                    String(execution.state).toUpperCase() === 'RUNTIMEENGINERELEASEHANDLED' && Boolean(execution.failureReason)
                ));
            if (participants.length > 0 && failures.length === participants.length) {
                fail(`All participants failed in round ${latestRound.roundId}: ${JSON.stringify(failures)}`);
            }
        }
        return false;
    }, () => `Last training status: ${JSON.stringify(lastStatus)}`);
    if (federationMode === 'personalized') {
        await waitForCondition('runtime-local personalized models for all participants', async () => {
            const page = await getPage(platformUrl, '/personalizedmodelresult/personalizedmodelcatalog', {
                ...query, size: Math.max(20, participants.length)
            });
            const models = page.content ?? [];
            return participants.every((participant) => models.some((model) =>
                model.runtimeId === participant.runtimeAgentId &&
                model.personalizationTargetId === participant.runtimeAgentId &&
                model.personalizedModelStorageLocation === 'RUNTIME_LOCAL' &&
                model.personalizedModelArtifactDigest?.match(/^sha256:[a-f\d]{64}$/i) &&
                model.personalizedModelArtifactRef && !model.platformManagedArtifactRef &&
                model.personalizedAccuracy != null && model.personalizationLift != null
            ));
        });
    }
    console.log(`[k3d-flow] verified completed training job ${trainingJobId}; federationMode=${federationMode}, participants=${participants.length}`);
}

async function waitForHttp(url, label) {
    await waitForCondition(label, async () => {
        try {
            const response = await httpRequest(url);
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

function cleanupPortForwards() {
    for (const child of portForwardProcesses) {
        if (child.exitCode === null && !child.killed) {
            child.kill('SIGTERM');
        }
    }
}

async function getPage(baseUrl, path, query) {
    const url = `${baseUrl}${path}?${new URLSearchParams(query).toString()}`;
    const response = await httpRequest(url, {headers: requestHeaders({}, url)});
    const body = await response.text();
    if (!response.ok) {
        const authHint = response.status === 401 && internalToken
            ? ' Internal token was configured; verify MEDOL_SECURITY_INTERNAL_TOKEN in the target service secret and restart the deployment after changing it.'
            : '';
        fail(`GET ${url} failed: ${response.status} ${compact(body)}${authHint}`);
    }
    return body ? JSON.parse(body) : {content: []};
}

async function postCommand(baseUrl, path, payload, label) {
    const result = await postCommandResult(baseUrl, path, payload, label);
    if (!result.ok) {
        fail(`POST ${label} failed: ${result.status} ${compact(result.body)}`);
    }
    console.log(`[k3d-flow] posted ${label}`);
}

async function postCommandResult(baseUrl, path, payload, label) {
    if (dryRun) {
        console.log(`[k3d-flow] DRY POST ${label}`);
        console.log(JSON.stringify(payload, null, 2));
        return {ok: true, status: 200, body: ''};
    }
    const url = `${baseUrl}${path}`;
    const response = await httpRequest(url, {
        method: 'POST',
        headers: requestHeaders({'content-type': 'application/json'}, url),
        body: JSON.stringify(payload)
    });
    const body = await response.text();
    return {ok: response.ok, status: response.status, body};
}

async function postJson(url, payload, label, options = {}) {
    const allowStatuses = new Set(options.allowStatuses ?? [200]);
    const response = await httpRequest(url, {
        method: 'POST',
        headers: requestHeaders({'content-type': 'application/json'}, url),
        body: JSON.stringify(payload)
    });
    const text = await response.text();
    const body = text ? parseJsonOrText(text) : null;
    if (!allowStatuses.has(response.status)) {
        fail(`POST ${label} failed: ${response.status} ${compact(text)}`);
    }
    return {status: response.status, body};
}

async function httpRequest(url, options = {}) {
    if (isLocalHttpUrl(url)) {
        return curlHttpRequest(url, options);
    }
    try {
        return await fetch(url, options);
    } catch (error) {
        if (!shouldFallbackToCurl(error, url)) {
            throw error;
        }
        return curlHttpRequest(url, options);
    }
}

function isLocalHttpUrl(url) {
    const host = safeUrl(url)?.hostname;
    return ['localhost', '127.0.0.1', '::1'].includes(host);
}

function shouldFallbackToCurl(error, url) {
    if (!isLocalHttpUrl(url)) {
        return false;
    }
    const code = error?.cause?.code ?? error?.code;
    if (code === 'EPERM' || code === 'EACCES') {
        return true;
    }
    return String(error?.message ?? '').includes('fetch failed');
}

function curlHttpRequest(url, options = {}) {
    const method = String(options.method ?? 'GET').toUpperCase();
    const headers = options.headers ?? {};
    const body = options.body == null ? null : String(options.body);
    const marker = '__MEDOL_HTTP_STATUS__:';
    const commandArgs = [
        '-sS',
        '--max-time', '30',
        '-X', method
    ];
    for (const [name, value] of Object.entries(headers)) {
        commandArgs.push('-H', `${name}: ${value}`);
    }
    if (body !== null) {
        commandArgs.push('--data-binary', '@-');
    }
    commandArgs.push('-w', `\n${marker}%{http_code}`, url);
    const result = spawnSync('curl', commandArgs, {
        input: body ?? undefined,
        encoding: 'utf8'
    });
    if (result.status !== 0) {
        throw new Error(`curl HTTP request failed: ${compact(result.stderr || result.stdout)}`);
    }
    const output = result.stdout ?? '';
    const markerIndex = output.lastIndexOf(`\n${marker}`);
    if (markerIndex < 0) {
        throw new Error(`curl HTTP response missing status marker: ${compact(output)}`);
    }
    const text = output.slice(0, markerIndex);
    const status = Number.parseInt(output.slice(markerIndex + marker.length + 1).trim(), 10);
    return {
        status,
        ok: status >= 200 && status < 300,
        text: async () => text
    };
}

function safeUrl(url) {
    try {
        return new URL(url);
    } catch {
        return null;
    }
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

function commandResult(command, commandArgs, extraEnv = {}) {
    if (dryRun) {
        console.log(`[k3d-flow] DRY RUN ${command} ${commandArgs.join(' ')}`);
        return {status: 0, stdout: '', stderr: ''};
    }
    const result = spawnSync(command, commandArgs, {
        cwd: repoRoot,
        env: {...process.env, ...extraEnv},
        encoding: 'utf8'
    });
    return {
        status: result.status ?? 1,
        stdout: result.stdout ?? '',
        stderr: result.stderr ?? ''
    };
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
    const deploymentName = managedRuntimeAgentName(runtimeAgentId);
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

function requestHeaders(baseHeaders = {}, url = '') {
    const result = {...baseHeaders};
    const authToken = authTokenForUrl(url);
    if (authToken && !Object.hasOwn(result, 'Authorization')) {
        result.Authorization = `Bearer ${authToken}`;
    }
    if (!result.Authorization && internalToken && !Object.hasOwn(result, 'X-MEDOL-INTERNAL-TOKEN')) {
        result['X-MEDOL-INTERNAL-TOKEN'] = internalToken;
    }
    return result;
}

function internalRequestHeaders(baseHeaders = {}) {
    const result = {...baseHeaders};
    if (internalToken && !Object.hasOwn(result, 'X-MEDOL-INTERNAL-TOKEN')) {
        result['X-MEDOL-INTERNAL-TOKEN'] = internalToken;
    }
    return result;
}

function authTokenForUrl(url) {
    const text = String(url ?? '');
    return [...authTokensByBaseUrl.entries()]
        .sort(([left], [right]) => right.length - left.length)
        .find(([baseUrl]) => text === baseUrl || text.startsWith(`${baseUrl}/`))?.[1];
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

function isRuntimeAgentFailureState(value) {
    return normalize(value) === 'RUNTIMEAGENTFAILED';
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

function normalizeFederationMode(value) {
    const normalized = String(value ?? '').trim().toLowerCase().replace(/[_\s]/g, '-');
    if (['global', 'global-only', 'globalonly', 'federated', 'federation'].includes(normalized)) {
        return 'global';
    }
    if (['personalized', 'personalization', 'personal', 'participant-personalized'].includes(normalized)) {
        return 'personalized';
    }
    fail(`Unsupported federation mode: ${value}. Supported modes: global, personalized`);
}

function printUsage() {
    console.log(`Usage:
  node scripts/k3d-training-flow.mjs [options]

Runs the local K3D federation-learning smoke flow: cluster readiness wait,
manifest apply, local admin bootstrap/login, dictionary init, platform seed,
runtime-agent deployment wait, runtime-agent seed, and training job submission.

Options:
  --environment <name>               Operations environment. Default: local.
  --recreate                         Recreate the K3D cluster before running.
                                     Use after lifecycle/tag model changes to clear stale event/projection data.
  --rebuild-app-images               Rebuild backend images; push only in registry mode.
  --app-image-prefix <prefix>         Override the shared operations image prefix.
  --image-source <import|registry>    Import exact local images, or pull and pin registry digests.
  --scenario <densenet|csv>           Select training seed scenario.
  --federation-mode <global|personalized>
                                     Select global-only or participant-personalized federation config.
  --participant-count <count>          Number of reference participants to seed and deploy. Default: 2.
  --training-run-key <key>             Isolate a new training configuration/job while reusing participants.
  --wait-for-training                  Wait for completion and verify personalized model records.
  --skip-cluster                      Skip cluster create/kubeconfig.
  --skip-apply                        Skip manifest apply and deployment waits.
  --skip-dictionary                   Skip dictionary bootstrap.
  --skip-runtime-agent-data           Skip runtime-agent dataset bootstrap.
  --skip-training-job                 Skip training job submission.
  --runtime-engine-image <image>      Runtime engine image used by managed agents.
  --admin-username <username>         Local admin username. Default: admin.
  --admin-password <password>         Local admin password. Default: admin.
  --admin-setup-token <token>         Local admin setup token.
  --sync-default-kubeconfig <bool>    Sync ~/.kube/config for the k3d context. Default: true.
  --dry-run                           Print configuration and exit before side effects.

Environment:
  FL_OPERATIONS_ENVIRONMENT           Operations environment override.
  FL_API_TOKEN                        Existing bearer token. When set, login is skipped.
  FL_K3D_ADMIN_USERNAME               Local admin username override.
  FL_K3D_ADMIN_PASSWORD               Local admin password override.
  FL_K3D_SYNC_DEFAULT_KUBECONFIG      Set false to skip syncing the default kubeconfig.
  FL_K3D_ADMIN_SETUP_TOKEN            Local admin setup token override.
  FL_K3D_CLUSTER_NAME                 K3D cluster name.
  FL_K3D_NAMESPACE                    Kubernetes namespace.
  FL_K3D_GATEWAY_URL                  Gateway URL. Default: http://localhost:30080.

The flow creates operations/k3s/environments/<environment>/secrets.<environment>.yaml from
secrets.example.yaml when it is missing. For manual kubectl inspection, use the
same kubeconfig printed by the flow helper, for example:
  export KUBECONFIG=operations/k3s/.work/kubeconfig-federation-learning-local.yaml
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

function trimSlashOrNull(value) {
    const text = optionalString(value);
    return text ? trimSlash(text) : null;
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
