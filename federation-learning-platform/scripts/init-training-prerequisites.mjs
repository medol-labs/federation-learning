#!/usr/bin/env node

import {createHash} from 'node:crypto';
import {existsSync, readdirSync, readFileSync} from 'node:fs';
import {basename, resolve} from 'node:path';

if (typeof fetch !== 'function') {
    fail('This script requires Node.js 18 or newer because it uses global fetch.');
}

const args = parseArgs(process.argv.slice(2));
loadEnvFiles([resolve(import.meta.dirname, '../.env'), resolve(process.cwd(), '.env')]);
const dryRun = Boolean(args['dry-run']);
const strict = Boolean(args.strict);
const createJob = booleanOption(args['create-job'] ?? process.env.FL_CREATE_TRAINING_JOB, true);
const activateLifecycle = Boolean(args.activate);
const timeoutMs = positiveInt(args.timeout, 30000);
const pollIntervalMs = positiveInt(args['poll-interval'], 800);
const trainingScenario = normalizeScenario(args.scenario ?? args['training-scenario'] ?? process.env.FL_TRAINING_SCENARIO ?? 'densenet');

const platformUrl = trimSlash(args['platform-url'] ?? process.env.FL_PLATFORM_URL ?? 'http://localhost:8081');
const runtimeAgentUrl = trimSlash(args['runtime-agent-url'] ?? process.env.FL_RUNTIME_AGENT_URL ?? 'http://localhost:8082');
const runtimeEngineUrl = trimSlash(args['runtime-engine-url'] ?? process.env.FL_RUNTIME_ENGINE_URL ?? 'http://localhost:18080');
const runtimeEnvironmentType = canonicalRuntimeEnvironmentType(
    args['runtime-environment-type'] ?? process.env.FL_RUNTIME_ENVIRONMENT_TYPE ?? 'DOCKER_COMPOSE'
);
const runtimePackageName = String(
    args['runtime-package-name'] ??
    process.env.FL_RUNTIME_INFRASTRUCTURE_PACKAGE_NAME ??
    defaultRuntimePackageName(runtimeEnvironmentType)
);
const runtimePackageVersion = String(args['runtime-package-version'] ?? process.env.FL_RUNTIME_INFRASTRUCTURE_PACKAGE_VERSION ?? '0.0.1-SNAPSHOT');
const runtimeAgentInstallMode = String(args['agent-install-mode'] ?? process.env.FL_RUNTIME_AGENT_INSTALL_MODE ?? 'MANUAL').trim().toUpperCase();
const runtimeEndpointScope = String(args['endpoint-scope'] ?? process.env.FL_RUNTIME_ENDPOINT_SCOPE ?? 'LOCAL_DEV').trim().toUpperCase();
const expectedNodeCount = positiveInt(args['expected-node-count'] ?? process.env.FL_RUNTIME_EXPECTED_NODE_COUNT, 1);
const registerRuntimeInfrastructure = booleanOption(
    args['register-runtime-infrastructure'] ?? process.env.FL_REGISTER_RUNTIME_INFRASTRUCTURE,
    false
);

const workspaceRoot = resolve(import.meta.dirname, '../..');
const defaultDatasetPath = defaultDatasetPathForScenario(trainingScenario, workspaceRoot);
const datasetPath = resolve(args['dataset-path'] ?? process.env.FL_DEV_DATASET_PATH ?? defaultDatasetPath);
const runtimeDatasetPath = args['runtime-dataset-path'] ??
    process.env.FL_RUNTIME_DATASET_PATH ??
    defaultRuntimeDatasetPath(trainingScenario, datasetPath);

if (!existsSync(datasetPath)) {
    fail(`Dataset file does not exist: ${datasetPath}`);
}

const seed = buildSeed(trainingScenario, runtimeDatasetPath, runtimeAgentUrl, runtimeEngineUrl, {
    runtimeEnvironmentType,
    runtimePackageName,
    runtimePackageVersion,
    runtimeAgentInstallMode,
    runtimeEndpointScope,
    expectedNodeCount
});

console.log(`[init-training] platformUrl=${platformUrl}`);
console.log(`[init-training] runtimeAgentUrl=${runtimeAgentUrl}`);
console.log(`[init-training] runtimeEngineUrl=${runtimeEngineUrl}`);
console.log(`[init-training] runtimeEnvironmentType=${runtimeEnvironmentType}`);
console.log(`[init-training] runtimePackage=${runtimePackageName}:${runtimePackageVersion}`);
console.log(`[init-training] runtimeAgentInstallMode=${runtimeAgentInstallMode}`);
console.log(`[init-training] registerRuntimeInfrastructure=${registerRuntimeInfrastructure}`);
console.log(`[init-training] createAndSubmitTrainingJob=${createJob}`);
console.log(`[init-training] trainingScenario=${trainingScenario}`);
console.log(`[init-training] datasetPath=${datasetPath}`);
console.log(`[init-training] runtimeDatasetPath=${runtimeDatasetPath}`);

assertLocalDatasetMatchesSeed(trainingScenario, datasetPath, seed.featureSchema);
warnIfRuntimeAgentConfigurationLikelyMismatches(trainingScenario);

await ensurePlatformData();
await ensureRuntimeAgentData();
await ensureTrainingJob();

console.log('[init-training] ready');
console.log(JSON.stringify({
    organizationId: seed.organization.organizationId,
    federationId: seed.federation.federationId,
    featureSchemaId: seed.featureSchema.featureSchemaId,
    runtimeInfrastructurePackageId: seed.runtimeInfrastructurePackage.runtimeInfrastructurePackageId,
    runtimeInstallationPlanId: seed.runtimeInstallationPlan.runtimeInstallationPlanId,
    runtimeInfrastructureId: seed.runtime.runtimeInfrastructureId,
    runtimeId: seed.runtime.runtimeId,
    runtimeAgentId: seed.runtime.runtimeAgentId,
    datasetId: seed.dataset.datasetId,
    modelId: seed.model.modelId,
    trainingRunConfigurationId: seed.trainingRunConfiguration.trainingRunConfigurationId,
    trainingJobId: createJob ? seed.trainingJob.trainingJobId : undefined
}, null, 2));

async function ensurePlatformData() {
    const organization = await ensureOne({
        label: 'organization',
        baseUrl: platformUrl,
        queryPath: '/organization/organizationdirectory',
        query: {'organizationName.equals': seed.organization.organizationName},
        createPath: '/organization/registerorganization',
        createPayload: seed.organization
    });

    if (activateLifecycle && !isActive(organization?.state)) {
        await postCommand(platformUrl, '/organization/activateorganization', {
            organizationId: seed.organization.organizationId,
            organizationName: seed.organization.organizationName,
            activationNote: 'Development bootstrap'
        }, 'activate organization');
        await waitForOne(platformUrl, '/organization/organizationdirectory', {
            'organizationId.equals': seed.organization.organizationId,
            'state.equals': 'ACTIVE'
        }, 'active organization');
    } else if (!isActive(organization?.state)) {
        console.log('[init-training] skip organization activation; pass --activate when lifecycle transition tags are fixed');
    }

    const federation = await ensureOne({
        label: 'federation',
        baseUrl: platformUrl,
        queryPath: '/federation/federationoverview',
        query: {'federationName.equals': seed.federation.federationName},
        createPath: '/federation/createfederation',
        createPayload: seed.federation
    });

    if (activateLifecycle && !isActive(federation?.state)) {
        await postCommand(platformUrl, '/federation/activatefederation', {
            federationId: seed.federation.federationId,
            federationName: seed.federation.federationName,
            activationNote: 'Development bootstrap'
        }, 'activate federation');
        await waitForOne(platformUrl, '/federation/federationoverview', {
            'federationId.equals': seed.federation.federationId,
            'state.equals': 'ACTIVE'
        }, 'active federation');
    } else if (!isActive(federation?.state)) {
        console.log('[init-training] skip federation activation; pass --activate when lifecycle transition tags are fixed');
    }

    const membership = await findOne(platformUrl, '/federationmembership/federationmembershipdirectory', {
        'federationId.equals': seed.federation.federationId,
        'organizationId.equals': seed.organization.organizationId
    });
    if (!membership) {
        await postCommand(platformUrl, '/federationmembership/inviteparticipant', {
            federationId: seed.federation.federationId,
            organizationId: seed.organization.organizationId,
            invitationNote: 'Development bootstrap'
        }, 'invite participant');
        await waitForOne(platformUrl, '/federationmembership/federationmembershipdirectory', {
            'federationId.equals': seed.federation.federationId,
            'organizationId.equals': seed.organization.organizationId
        }, 'invited participant');
    }
    const joinedMembership = await findOne(platformUrl, '/federationmembership/federationmembershipdirectory', {
        'federationId.equals': seed.federation.federationId,
        'organizationId.equals': seed.organization.organizationId
    });
    if (!isJoinedMembership(joinedMembership)) {
        await postCommand(platformUrl, '/federationmembership/approveparticipant', {
            federationId: seed.federation.federationId,
            organizationId: seed.organization.organizationId,
            approvalNote: 'Development bootstrap'
        }, 'approve participant');
        await waitForOne(platformUrl, '/federationmembership/federationmembershipdirectory', {
            'federationId.equals': seed.federation.federationId,
            'organizationId.equals': seed.organization.organizationId
        }, 'joined participant', isJoinedMembership);
    }

    const featureSchema = await ensureOne({
        label: 'feature schema',
        baseUrl: platformUrl,
        queryPath: '/featureschema/featureschemacatalog',
        query: {
            'featureDomain.equals': seed.featureSchema.featureDomain,
            'version.equals': seed.featureSchema.version
        },
        createPath: '/featureschema/definefeatureschema',
        createPayload: seed.featureSchema
    });
    assertSchemaSnapshotMatchesSeed(featureSchema, seed.featureSchema, 'feature schema');
    if (!isPublished(featureSchema?.schemaStatus ?? featureSchema?.state)) {
        await postCommand(platformUrl, '/featureschema/publishfeatureschema', {
            featureSchemaId: seed.featureSchema.featureSchemaId,
            featureDomain: seed.featureSchema.featureDomain,
            version: seed.featureSchema.version,
            publishNote: 'Development bootstrap'
        }, 'publish feature schema');
        await waitForOne(platformUrl, '/featureschema/featureschemacatalog', {
            'featureSchemaId.equals': seed.featureSchema.featureSchemaId
        }, 'published feature schema', (item) => isPublished(item.schemaStatus ?? item.state));
    }

    await ensureOne({
        label: 'model artifact',
        baseUrl: platformUrl,
        queryPath: '/modelartifact/modelartifactcatalog',
        query: {
            'modelName.equals': seed.model.modelName,
            'modelVersion.equals': seed.model.modelVersion
        },
        createPath: '/modelartifact/registermodelartifact',
        createPayload: seed.model
    });

    await ensureRuntimeProvisioningData();

    await ensureOne({
        label: 'runtime identity',
        baseUrl: platformUrl,
        queryPath: '/runtimeidentity/runtimeidentitycatalog',
        query: {
            'runtimeId.equals': seed.runtime.runtimeId,
            'runtimeInfrastructureId.equals': seed.runtime.runtimeInfrastructureId
        },
        createPath: '/runtimeidentity/activateruntimeidentity',
        createPayload: seed.runtime
    });

    await ensureOne({
        label: 'runtime agent endpoint',
        baseUrl: platformUrl,
        queryPath: '/runtimeinfrastructure/runtimeagentendpointcatalog',
        query: {
            'runtimeAgentId.equals': seed.runtime.runtimeAgentId,
            'runtimeInfrastructureId.equals': seed.runtime.runtimeInfrastructureId
        },
        createPath: '/runtimeinfrastructure/recordruntimeconnectionestablished',
        createPayload: {
            runtimeInfrastructureId: seed.runtime.runtimeInfrastructureId,
            runtimeAgentId: seed.runtime.runtimeAgentId,
            agentInstallMode: seed.runtimeInstallationPlan.agentInstallMode,
            organizationId: seed.organization.organizationId,
            runtimeName: seed.runtime.runtimeName,
            runtimeAgentEndpoint: runtimeAgentUrl,
            endpointScope: seed.runtimeEndpointScope
        }
    });

    await ensureOne({
        label: 'training run configuration',
        baseUrl: platformUrl,
        queryPath: '/trainingrunconfiguration/trainingrunconfigurationcatalog',
        query: {'trainingRunConfigurationId.equals': seed.trainingRunConfiguration.trainingRunConfigurationId},
        createPath: '/trainingrunconfiguration/definetrainingrunconfiguration',
        createPayload: seed.trainingRunConfiguration
    });

}

async function ensureTrainingJob() {
    if (!createJob) return;

    const trainingJob = await ensureOne({
        label: 'training job',
        baseUrl: platformUrl,
        queryPath: '/trainingjob/trainingjobdashboard',
        query: {'trainingJobId.equals': seed.trainingJob.trainingJobId},
        createPath: '/trainingjob/createtrainingjob',
        createPayload: seed.trainingJob
    });

    if (isTrainingJobSubmittedOrBeyond(trainingJob?.state)) {
        console.log('[init-training] exists submitted training job');
        return;
    }

    await postCommand(platformUrl, '/trainingjob/submittrainingjob', {
        trainingJobId: seed.trainingJob.trainingJobId
    }, 'submit training job');
    await waitForOne(platformUrl, '/trainingjob/trainingjobdashboard', {
        'trainingJobId.equals': seed.trainingJob.trainingJobId
    }, 'submitted training job', (item) => isTrainingJobSubmittedOrBeyond(item.state));
}

async function ensureRuntimeProvisioningData() {
    const runtimePackage = await ensureOne({
        label: 'runtime infrastructure package catalog',
        baseUrl: platformUrl,
        queryPath: '/runtimeinfrastructurepackage/runtimeinfrastructurepackagecatalog',
        query: {
            'packageName.equals': seed.runtimeInfrastructurePackage.packageName,
            'packageVersion.equals': seed.runtimeInfrastructurePackage.packageVersion
        },
        createPath: '/runtimeinfrastructurepackage/registerruntimeinfrastructurepackage',
        createPayload: seed.runtimeInfrastructurePackage
    });
    const runtimeInfrastructurePackageId = runtimePackage?.runtimeInfrastructurePackageId ??
        seed.runtimeInfrastructurePackage.runtimeInfrastructurePackageId;
    seed.runtimeInfrastructurePackage.runtimeInfrastructurePackageId = runtimeInfrastructurePackageId;
    seed.runtimeInstallationPlan.runtimeInfrastructurePackageId = runtimeInfrastructurePackageId;

    const runtimeInstallationPlan = await ensureOne({
        label: 'runtime installation plan',
        baseUrl: platformUrl,
        queryPath: '/runtimeinstallationplan/runtimeinstallationplancatalog',
        query: {
            'organizationId.equals': seed.organization.organizationId,
            'runtimeInfrastructurePackageId.equals': runtimeInfrastructurePackageId,
            'runtimeName.equals': seed.runtime.runtimeName
        },
        createPath: '/runtimeinstallationplan/createruntimeinstallationplan',
        createPayload: seed.runtimeInstallationPlan
    });
    const runtimeInstallationPlanId = runtimeInstallationPlan?.runtimeInstallationPlanId ??
        seed.runtimeInstallationPlan.runtimeInstallationPlanId;
    const runtimeInfrastructureId = runtimeInstallationPlan?.runtimeInfrastructureId ??
        seed.runtime.runtimeInfrastructureId;
    seed.runtimeInstallationPlan.runtimeInstallationPlanId = runtimeInstallationPlanId;
    applyRuntimeInfrastructureId(runtimeInfrastructureId);

    await ensureRuntimeInfrastructurePlanned(runtimeInfrastructureId, runtimeInstallationPlanId);
    if (registerRuntimeInfrastructure) {
        await ensureRuntimeInfrastructureRegistered(runtimeInfrastructureId);
    }
}

async function ensureRuntimeInfrastructurePlanned(runtimeInfrastructureId, runtimeInstallationPlanId) {
    const existing = await findOne(platformUrl, '/runtimeinfrastructure/runtimeinfrastructureaccessview', {
        'runtimeInfrastructureId.equals': runtimeInfrastructureId
    });
    if (isRuntimeInfrastructurePlannedOrBeyond(existing?.state)) {
        console.log('[init-training] exists runtime infrastructure plan');
        return existing;
    }

    await postCommand(platformUrl, '/runtimeinfrastructure/planruntimeinfrastructure', {
        runtimeInfrastructureId,
        runtimeInstallationPlanId
    }, 'runtime infrastructure plan');
    return waitForOne(platformUrl, '/runtimeinfrastructure/runtimeinfrastructureaccessview', {
        'runtimeInfrastructureId.equals': runtimeInfrastructureId
    }, 'planned runtime infrastructure', (item) => isRuntimeInfrastructurePlannedOrBeyond(item.state));
}

async function ensureRuntimeInfrastructureRegistered(runtimeInfrastructureId) {
    const existing = await findOne(platformUrl, '/runtimeinfrastructure/runtimeinfrastructureaccessview', {
        'runtimeInfrastructureId.equals': runtimeInfrastructureId
    });
    if (isRuntimeInfrastructureRegisteredOrBeyond(existing?.state)) {
        console.log('[init-training] exists runtime infrastructure registration');
        return existing;
    }

    await postCommand(platformUrl, '/runtimeinfrastructure/registerruntimeinfrastructure', {
        runtimeInfrastructureId,
        runtimeAgentId: seed.runtime.runtimeAgentId
    }, 'runtime infrastructure registration');
    return waitForOne(platformUrl, '/runtimeinfrastructure/runtimeinfrastructureaccessview', {
        'runtimeInfrastructureId.equals': runtimeInfrastructureId
    }, 'registered runtime infrastructure', (item) => isRuntimeInfrastructureRegisteredOrBeyond(item.state));
}

async function ensureRuntimeAgentData() {
    await ensureOne({
        label: 'runtime node inventory',
        baseUrl: runtimeAgentUrl,
        queryPath: '/agentruntimenodeinventory/agentruntimenodeinventorycatalog',
        query: {'runtimeNodeInventoryReportId.equals': seed.node.runtimeNodeInventoryReportId},
        createPath: '/agentruntimenodeinventory/reportagentruntimenodeinventory',
        createPayload: seed.node
    });

    const datasetCapability = await ensureOne({
        label: 'dataset declaration',
        baseUrl: runtimeAgentUrl,
        queryPath: '/dataset/datasetcapability',
        query: {'datasetId.equals': seed.dataset.datasetId},
        createPath: '/dataset/declaredataset',
        createPayload: seed.dataset
    });
    assertSchemaSnapshotMatchesSeed(datasetCapability, seed.featureSchema, 'dataset capability');

    const existingBinding = await findOne(runtimeAgentUrl, '/runtimedatasetbinding/runtimedatasetbindingcatalog', {
        'runtimeId.equals': seed.binding.runtimeId,
        'datasetId.equals': seed.binding.datasetId
    });
    if (existingBinding) {
        console.log('[init-training] exists runtime dataset binding');
        if (existingBinding.filePath && existingBinding.filePath !== seed.binding.filePath) {
            console.warn(
                `[init-training] runtime dataset binding uses filePath=${existingBinding.filePath}; ` +
                `expected ${seed.binding.filePath}. Clear dev data or reconfigure the binding before testing container deployment.`
            );
        }
    } else {
        await postCommand(runtimeAgentUrl, '/runtimedatasetbinding/configureruntimedatasetbinding', seed.binding, 'runtime dataset binding');
        await waitForOne(runtimeAgentUrl, '/runtimedatasetbinding/runtimedatasetbindingcatalog', {
            'runtimeId.equals': seed.binding.runtimeId,
            'datasetId.equals': seed.binding.datasetId
        }, 'runtime dataset binding');
    }

    let capability = datasetCapability;
    if (seed.requiresCsvRuntimeAgentDatasetAdapters) {
        const accessValidation = await ensureAgentDatasetAccessValidation();
        if (!isAccessValidated(accessValidation?.validationStatus)) {
            fail(
                `Agent dataset access validation failed: ${accessValidation?.failureReason ?? accessValidation?.validationStatus ?? 'unknown failure'}. ` +
                `runtimeDatasetPath=${seed.binding.filePath}`
            );
        }

        capability = await waitForOne(runtimeAgentUrl, '/dataset/datasetcapability', {
            'datasetId.equals': seed.dataset.datasetId
        }, 'reported dataset capability', isDatasetCapabilityReadyOrFailed);
        if (!dryRun && !isDatasetCapabilityReady(capability)) {
            fail(`Dataset capability was not ready: ${JSON.stringify(capability)}`);
        }
    } else {
        console.log(
            `[init-training] skip runtime-agent CSV dataset access/profile/contract checks for ${seed.trainingScenario}; ` +
            'ImageFolder adapters are not available yet.'
        );
    }

    if (!isApproved(capability?.approvalStatus) && capability?.approved !== true) {
        try {
            await postCommand(runtimeAgentUrl, '/dataset/approvedatasetfortraining', {
                datasetId: seed.dataset.datasetId,
                organizationId: seed.organization.organizationId,
                featureSchemaId: seed.featureSchema.featureSchemaId,
                datasetName: seed.dataset.datasetName
            }, 'approve dataset for training');
            console.log('[init-training] dataset approval command posted; approval projection is currently non-blocking');
        } catch (error) {
            console.warn(`[init-training] dataset approval skipped: ${errorSummary(error)}`);
        }
    }

    await ensureOne({
        label: 'selectable platform runtime dataset metadata',
        baseUrl: platformUrl,
        queryPath: '/runtimedatasetmetadata/runtimedatasetmetadatacatalog',
        query: {
            'runtimeDatasetBindingId.equals': seed.binding.runtimeDatasetBindingId,
            'schemaCompatible.equals': 'true',
            'labelCompatible.equals': 'true'
        },
        createPath: '/runtimedatasetmetadata/recordruntimedatasetmetadata',
        createPayload: seed.platformMetadata
    });
}

async function ensureAgentDatasetAccessValidation() {
    if (dryRun) {
        await postCommand(
            runtimeAgentUrl,
            '/agentdatasetaccessvalidation/validateagentdatasetaccess',
            seed.accessValidation,
            'agent dataset access validation'
        );
        return {...seed.accessValidation, validationStatus: 'Checked'};
    }

    const existingQuery = {
        'runtimeDatasetBindingId.equals': seed.binding.runtimeDatasetBindingId,
        'datasetId.equals': seed.dataset.datasetId
    };
    const existingPage = await getPage(runtimeAgentUrl, '/agentdatasetaccessvalidation/agentdatasetaccessvalidationcatalog', {
        ...existingQuery,
        size: '50'
    });
    const existingItems = existingPage.content ?? [];
    const validated = existingItems.find((item) => isAccessValidated(item.validationStatus));
    if (validated) {
        console.log('[init-training] exists agent dataset access validation');
        return validated;
    }

    const failed = existingItems.find((item) => isFailed(item.validationStatus) || item.failureReason);
    const attempt = failed
        ? {
            ...seed.accessValidation,
            datasetAccessValidationId: stableUuid(
                `fl-dev:dataset-access-validation:${seed.binding.runtimeDatasetBindingId}:${Date.now()}`
            )
        }
        : seed.accessValidation;

    if (failed) {
        console.warn(
            `[init-training] retry agent dataset access validation after previous failure: ` +
            `${failed.failureReason ?? failed.validationStatus}`
        );
    }

    await postCommand(
        runtimeAgentUrl,
        '/agentdatasetaccessvalidation/validateagentdatasetaccess',
        attempt,
        'agent dataset access validation'
    );
    return waitForOne(
        runtimeAgentUrl,
        '/agentdatasetaccessvalidation/agentdatasetaccessvalidationcatalog',
        {'datasetAccessValidationId.equals': attempt.datasetAccessValidationId},
        'agent dataset access validation',
        isAccessValidationFinished
    );
}

async function ensureOne({label, baseUrl, queryPath, query, createPath, createPayload}) {
    const existing = await findOne(baseUrl, queryPath, query);
    if (existing) {
        console.log(`[init-training] exists ${label}`);
        return existing;
    }
    await postCommand(baseUrl, createPath, createPayload, label);
    if (dryRun) return createPayload;
    return waitForOne(baseUrl, queryPath, query, label);
}

async function findOne(baseUrl, path, query) {
    const page = await getPage(baseUrl, path, {...query, size: '20'});
    return (page.content ?? [])[0] ?? null;
}

async function waitForOne(baseUrl, path, query, label, predicate = () => true) {
    if (dryRun) return null;
    const startedAt = Date.now();
    let lastItem = null;
    while (Date.now() - startedAt <= timeoutMs) {
        const page = await getPage(baseUrl, path, {...query, size: '20'});
        const item = (page.content ?? []).find(predicate);
        if (item) return item;
        lastItem = (page.content ?? [])[0] ?? lastItem;
        await sleep(pollIntervalMs);
    }
    fail(`Timed out waiting for ${label}. Last item: ${JSON.stringify(lastItem)}`);
}

async function getPage(baseUrl, path, query) {
    const url = `${baseUrl}${path}?${new URLSearchParams(query).toString()}`;
    if (dryRun) return {content: []};
    const response = await fetch(url, {
        headers: requestHeaders()
    });
    const body = await response.text();
    if (!response.ok) {
        fail(`GET ${url} failed: ${response.status} ${compact(body)}`);
    }
    return body ? JSON.parse(body) : {content: []};
}

async function postCommand(baseUrl, path, payload, label) {
    const url = `${baseUrl}${path}`;
    if (dryRun) {
        console.log(`[init-training] DRY POST ${label} -> ${url}`);
        console.log(JSON.stringify(payload, null, 2));
        return;
    }
    const response = await fetch(url, {
        method: 'POST',
        headers: requestHeaders({'content-type': 'application/json'}),
        body: JSON.stringify(payload)
    });
    const body = await response.text();
    if (!response.ok) {
        const message = `POST ${label} failed: ${response.status} ${compact(body)}`;
        if (strict) fail(message);
        throw new Error(message);
    }
    console.log(`[init-training] posted ${label}`);
}

function applyRuntimeInfrastructureId(runtimeInfrastructureId) {
    seed.runtime.runtimeInfrastructureId = runtimeInfrastructureId;
    seed.node.runtimeInfrastructureId = runtimeInfrastructureId;
    seed.node.runtimeNodeInventoryReportId = stableUuid(
        `fl-dev:node-inventory:${runtimeInfrastructureId}:${seed.runtime.runtimeAgentId}`
    );
    seed.node.inventoryHash = stableUuid(`fl-dev:node-inventory:${runtimeInfrastructureId}:${runtimeEngineUrl}`);
}

function assertLocalDatasetMatchesSeed(scenario, localDatasetPath, expectedSchema) {
    if (scenario === 'densenet') {
        assertImageFolderDataset(localDatasetPath);
        return;
    }
    assertLocalDatasetHeaderMatchesSeed(localDatasetPath, expectedSchema);
}

function assertImageFolderDataset(localDatasetPath) {
    const root = resolve(localDatasetPath);
    const entries = safeReadDir(root);
    const classDirs = entries.filter((entry) => entry.isDirectory());
    if (classDirs.length < 2) {
        fail(`ImageFolder dataset requires at least two class directories: ${root}`);
    }
    const emptyClasses = classDirs
        .filter((entry) => !directoryContainsImage(resolve(root, entry.name)))
        .map((entry) => entry.name);
    if (emptyClasses.length > 0) {
        fail(`ImageFolder dataset classes have no image files: ${emptyClasses.join(', ')}`);
    }
}

function directoryContainsImage(directory) {
    const imageExtensions = new Set([
        '.jpg', '.jpeg', '.png', '.ppm', '.bmp', '.pgm', '.tif', '.tiff', '.webp', '.pnm', '.pbm'
    ]);
    return safeReadDir(directory).some((entry) => {
        const entryPath = resolve(directory, entry.name);
        if (entry.isDirectory()) {
            return directoryContainsImage(entryPath);
        }
        const extensionIndex = entry.name.lastIndexOf('.');
        const extension = extensionIndex >= 0 ? entry.name.slice(extensionIndex).toLowerCase() : '';
        return entry.isFile() && imageExtensions.has(extension);
    });
}

function safeReadDir(path) {
    try {
        return readdirSync(path, {withFileTypes: true});
    } catch (error) {
        fail(`Unable to read dataset directory ${path}: ${errorSummary(error)}`);
    }
}

function assertLocalDatasetHeaderMatchesSeed(localDatasetPath, expectedSchema) {
    const header = readFileSync(localDatasetPath, 'utf8')
        .split(/\r?\n/)
        .find((line) => line.trim().length > 0);
    if (!header) {
        fail(`Dataset file is empty: ${localDatasetPath}`);
    }

    const actualColumns = header.split(',').map((value) => value.trim().replace(/^"|"$/g, ''));
    const missingFeatures = missingNames(expectedSchema.features, actualColumns, 'featureName');
    const missingLabels = missingNames(expectedSchema.labels, actualColumns, 'labelName');
    if (missingFeatures.length > 0 || missingLabels.length > 0) {
        fail([
            `Dataset header does not match the seeded feature schema: ${localDatasetPath}.`,
            `Actual columns: [${actualColumns.join(', ')}].`,
            missingFeatures.length > 0 ? `Missing feature columns: [${missingFeatures.join(', ')}].` : '',
            missingLabels.length > 0 ? `Missing label columns: [${missingLabels.join(', ')}].` : ''
        ].filter(Boolean).join(' '));
    }
}

function assertSchemaSnapshotMatchesSeed(actualSnapshot, expectedSchema, label) {
    if (!actualSnapshot || !Array.isArray(actualSnapshot.features) || !Array.isArray(actualSnapshot.labels)) {
        return;
    }

    const missingFeatures = missingNames(expectedSchema.features, actualSnapshot.features, 'featureName');
    const missingLabels = missingNames(expectedSchema.labels, actualSnapshot.labels, 'labelName');
    const unexpectedLabels = missingNames(actualSnapshot.labels, expectedSchema.labels, 'labelName');
    if (missingFeatures.length > 0 || missingLabels.length > 0 || unexpectedLabels.length > 0) {
        const actualLabels = actualSnapshot.labels.map((item) => item?.labelName).filter(Boolean);
        const expectedLabels = expectedSchema.labels.map((item) => item?.labelName).filter(Boolean);
        fail([
            `Existing ${label} does not match init-training-prerequisites seed data.`,
            `Expected labels: [${expectedLabels.join(', ')}].`,
            `Actual labels: [${actualLabels.join(', ')}].`,
            missingFeatures.length > 0 ? `Missing feature columns: [${missingFeatures.join(', ')}].` : '',
            missingLabels.length > 0 ? `Missing label columns: [${missingLabels.join(', ')}].` : '',
            unexpectedLabels.length > 0 ? `Unexpected label columns: [${unexpectedLabels.join(', ')}].` : '',
            'Reset local dev data or bump the seed version before rerunning.'
        ].filter(Boolean).join(' '));
    }
}

function missingNames(expectedItems, actualItems, nameProperty) {
    const actualNames = new Set(actualItems.map((item) => normalizeName(typeof item === 'string' ? item : item?.[nameProperty])));
    return expectedItems
        .map((item) => typeof item === 'string' ? item : item?.[nameProperty])
        .filter((name) => name && !actualNames.has(normalizeName(name)));
}

function normalizeName(value) {
    return String(value ?? '').trim().replace(/^"|"$/g, '').replace(/[_\s-]/g, '').toLowerCase();
}

function buildSeed(scenario, datasetPathValue, runtimeAgentEndpoint, runtimeEngineEndpoint, runtimeOptions) {
    if (scenario === 'csv') {
        return buildCsvSeed(datasetPathValue, runtimeAgentEndpoint, runtimeEngineEndpoint, runtimeOptions);
    }
    return buildDensenetSeed(datasetPathValue, runtimeAgentEndpoint, runtimeEngineEndpoint, runtimeOptions);
}

function buildDensenetSeed(datasetPathValue, runtimeAgentEndpoint, runtimeEngineEndpoint, runtimeOptions) {
    const runtimeName = 'local-vision-runtime';
    const datasetName = 'Tiny ImageNet DenseNet ImageFolder';
    const organizationId = stableUuid('fl-dev:organization:local-vision-lab');
    const federationId = stableUuid('fl-dev:federation:tiny-imagenet-densenet');
    const featureSchemaId = stableUuid('fl-dev:feature-schema:tiny-imagenet-image-classification:v1');
    const runtimeInfrastructurePackageId = stableUuid(
        `fl-dev:runtime-infrastructure-package:${runtimeOptions.runtimePackageName}:${runtimeOptions.runtimePackageVersion}`
    );
    const runtimeInstallationPlanId = stableUuid(
        `fl-dev:runtime-installation-plan:${organizationId}:${runtimeOptions.runtimePackageName}:${runtimeOptions.runtimePackageVersion}:${runtimeName}`
    );
    const runtimeInfrastructureId = stableNameUuid(`runtime-infrastructure:${runtimeInstallationPlanId}`);
    const runtimeAgentId = stableUuid('fl-dev:runtime-agent:local-vision');
    const runtimeId = stableUuid('fl-dev:runtime:local-vision');
    const datasetId = stableUuid('fl-dev:dataset:tiny-imagenet-densenet-imagefolder');
    const runtimeDatasetBindingId = stableUuid('fl-dev:dataset-binding:tiny-imagenet-densenet-local-runtime');
    const datasetAccessValidationId = stableUuid('fl-dev:dataset-access-validation:tiny-imagenet-densenet-local-runtime');
    const modelId = stableUuid('fl-dev:model:torchvision-densenet121:v1');
    const trainingRunConfigurationId = stableUuid('fl-dev:training-run-configuration:tiny-imagenet-densenet:v1');
    const trainingJobId = stableUuid('fl-dev:training-job:tiny-imagenet-densenet');

    return {
        trainingScenario: 'densenet',
        requiresCsvRuntimeAgentDatasetAdapters: false,
        organization: {
            organizationId,
            organizationName: 'FL Dev Vision Lab',
            organizationType: 'LABORATORY',
            contactEmail: 'fl-dev@example.com'
        },
        federation: {
            federationId,
            federationName: 'Tiny ImageNet DenseNet Federation',
            description: 'Local development federation for DenseNet image classification smoke testing',
            minimumParticipantCount: 1
        },
        featureSchema: {
            featureSchemaId,
            featureDomain: 'tiny-imagenet-image-classification',
            version: 'v1',
            dataModality: 'IMAGE',
            features: [
                feature('image', 'FILE', true, false, 'Image sample file in ImageFolder layout'),
                feature('class_directory', 'STRING', true, false, 'ImageFolder class directory name')
            ],
            labels: [
                {
                    labelName: 'class',
                    dataType: 'STRING',
                    cardinality: 2,
                    classLabels: ['n01443537', 'n01629819'],
                    isMultilabel: false,
                    description: 'ImageFolder class label',
                    validationRules: [],
                    defaultValue: null
                }
            ]
        },
        runtime: {
            runtimeId,
            runtimeInfrastructureId,
            runtimeAgentId,
            organizationId,
            runtimeName
        },
        runtimeInfrastructurePackage: {
            runtimeInfrastructurePackageId,
            packageName: runtimeOptions.runtimePackageName,
            packageVersion: runtimeOptions.runtimePackageVersion,
            runtimeEnvironmentType: runtimeOptions.runtimeEnvironmentType
        },
        runtimeInstallationPlan: {
            runtimeInstallationPlanId,
            organizationId,
            runtimeInfrastructurePackageId,
            runtimeName,
            agentInstallMode: runtimeOptions.runtimeAgentInstallMode,
            expectedNodeCount: runtimeOptions.expectedNodeCount
        },
        runtimeEndpointScope: runtimeOptions.runtimeEndpointScope,
        node: {
            runtimeNodeInventoryReportId: stableUuid(`fl-dev:node-inventory:${runtimeInfrastructureId}:${runtimeAgentId}`),
            organizationId,
            runtimeInfrastructureId,
            runtimeAgentId,
            runtimeNodeName: runtimeName,
            infrastructureNodeId: 'local-dev-node',
            runtimeNodeRole: 'TRAINER',
            nodeReady: true,
            runtimeEngineVersion: 'local',
            containerEngineVersion: runtimeOptions.runtimeEnvironmentType,
            operatingSystem: process.platform,
            architecture: process.arch,
            inventoryHash: stableUuid(`fl-dev:node-inventory:${runtimeInfrastructureId}:${runtimeEngineEndpoint}`)
        },
        dataset: {
            datasetId,
            organizationId,
            featureSchemaId,
            datasetName,
            datasetUsage: 'TRAINING'
        },
        binding: {
            runtimeDatasetBindingId,
            datasetId,
            organizationId,
            featureSchemaId,
            datasetName,
            runtimeId,
            dataSourceType: 'FILE',
            host: null,
            port: null,
            url: null,
            databaseName: null,
            schemaName: null,
            tableName: null,
            filePath: datasetPathValue,
            objectBucket: null,
            objectPrefix: null,
            dataFormat: 'IMAGE_FOLDER',
            credentialSecretName: null
        },
        accessValidation: {
            datasetAccessValidationId,
            runtimeDatasetBindingId,
            datasetId,
            organizationId,
            featureSchemaId,
            datasetName,
            runtimeId,
            dataSourceType: 'FILE',
            host: null,
            port: null,
            url: null,
            databaseName: null,
            schemaName: null,
            tableName: null,
            filePath: datasetPathValue,
            objectBucket: null,
            objectPrefix: null,
            dataFormat: 'IMAGE_FOLDER',
            credentialSecretName: null
        },
        platformMetadata: {
            runtimeDatasetBindingId,
            metadataReportId: stableUuid('fl-dev:platform-metadata:tiny-imagenet-densenet-local-runtime-compatible'),
            datasetId,
            organizationId,
            runtimeId,
            featureSchemaId,
            datasetName,
            sampleCount: 40,
            featureCount: 2,
            schemaCompatible: true,
            labelCompatible: true,
            missingValueRate: 0.0,
            duplicateRate: 0.0,
            qualityScore: 1.0,
            nonIidScore: 0.0,
            classBalanceScore: 0.5
        },
        model: {
            modelId,
            modelName: 'PYTORCH_TORCHVISION_DENSENET121_CLASSIFIER',
            modelVersion: 'v1',
            modelDescription: 'Development DenseNet121 classifier for Tiny ImageNet ImageFolder smoke testing.',
            sourceType: 'BUILT_IN',
            fileId: null,
            modelFormat: 'PYTORCH_STATE_DICT'
        },
        trainingRunConfiguration: {
            trainingRunConfigurationId,
            configurationName: 'Tiny ImageNet DenseNet Local Dev',
            federationId,
            featureSchemaId,
            initialModelId: modelId,
            strategyName: 'LOCAL_DEV_DENSENET',
            aggregationAlgorithm: 'FED_AVG_PYTORCH_STATE_DICT',
            maxRounds: 1,
            minimumNodesPerRound: 1,
            roundTimeoutSeconds: 600,
            nodeResponseTimeoutSeconds: 300,
            localEpochs: 1,
            batchSize: 4,
            learningRate: 0.001,
            optimizer: 'ADAM',
            lossFunction: 'CROSS_ENTROPY',
            gradientClippingNorm: null,
            secureAggregationRequired: true,
            minimumAccuracy: 0.0,
            minimumFairnessScore: null
        },
        trainingJob: {
            trainingJobId,
            federationId,
            trainingRunConfigurationId,
            objective: 'Local Tiny ImageNet DenseNet smoke training'
        },
        runtimeAgentEndpoint
    };
}

function buildCsvSeed(datasetPathValue, runtimeAgentEndpoint, runtimeEngineEndpoint, runtimeOptions) {
    const runtimeName = 'local-medical-runtime';
    const datasetName = 'Hospital Readmission Risk CSV';
    const labelColumn = 'readmission_risk';
    const organizationId = stableUuid('fl-dev:organization:local-hospital');
    const federationId = stableUuid('fl-dev:federation:medical-readmission-risk');
    const featureSchemaId = stableUuid('fl-dev:feature-schema:hospital-readmission-risk:v1');
    const runtimeInfrastructurePackageId = stableUuid(
        `fl-dev:runtime-infrastructure-package:${runtimeOptions.runtimePackageName}:${runtimeOptions.runtimePackageVersion}`
    );
    const runtimeInstallationPlanId = stableUuid(
        `fl-dev:runtime-installation-plan:${organizationId}:${runtimeOptions.runtimePackageName}:${runtimeOptions.runtimePackageVersion}:${runtimeName}`
    );
    const runtimeInfrastructureId = stableNameUuid(`runtime-infrastructure:${runtimeInstallationPlanId}`);
    const runtimeAgentId = stableUuid('fl-dev:runtime-agent:local-medical');
    const runtimeId = stableUuid('fl-dev:runtime:local-medical');
    const datasetId = stableUuid('fl-dev:dataset:hospital-readmission-risk-csv');
    const runtimeDatasetBindingId = stableUuid('fl-dev:dataset-binding:hospital-readmission-risk-local-runtime');
    const datasetAccessValidationId = stableUuid('fl-dev:dataset-access-validation:hospital-readmission-risk-local-runtime');
    const modelId = stableUuid('fl-dev:model:hospital-readmission-logistic-regression:v1');
    const trainingRunConfigurationId = stableUuid('fl-dev:training-run-configuration:hospital-readmission-risk:v1');
    const trainingJobId = stableUuid('fl-dev:training-job:hospital-readmission-risk');

    return {
        trainingScenario: 'csv',
        requiresCsvRuntimeAgentDatasetAdapters: true,
        organization: {
            organizationId,
            organizationName: 'FL Dev Hospital',
            organizationType: 'HOSPITAL',
            contactEmail: 'fl-dev@example.com'
        },
        federation: {
            federationId,
            federationName: 'Medical Readmission Risk Federation',
            description: 'Local development federation for hospital readmission risk modeling',
            minimumParticipantCount: 1
        },
        featureSchema: {
            featureSchemaId,
            featureDomain: 'hospital-readmission-risk',
            version: 'v1',
            dataModality: 'TABULAR',
            features: [
                feature('id', 'STRING', true, false, 'Synthetic patient identifier', [], null, true),
                feature('age_years', 'INTEGER', true, false, 'Patient age in years'),
                feature('systolic_bp_mm_hg', 'INTEGER', true, false, 'Most recent systolic blood pressure in mmHg'),
                feature('fasting_glucose_mg_dl', 'INTEGER', true, false, 'Most recent fasting glucose in mg/dL')
            ],
            labels: [
                {
                    labelName: labelColumn,
                    dataType: 'INTEGER',
                    cardinality: 2,
                    classLabels: ['0', '1'],
                    isMultilabel: false,
                    description: 'Synthetic 30-day readmission risk label',
                    validationRules: [],
                    defaultValue: null
                }
            ]
        },
        runtime: {
            runtimeId,
            runtimeInfrastructureId,
            runtimeAgentId,
            organizationId,
            runtimeName
        },
        runtimeInfrastructurePackage: {
            runtimeInfrastructurePackageId,
            packageName: runtimeOptions.runtimePackageName,
            packageVersion: runtimeOptions.runtimePackageVersion,
            runtimeEnvironmentType: runtimeOptions.runtimeEnvironmentType
        },
        runtimeInstallationPlan: {
            runtimeInstallationPlanId,
            organizationId,
            runtimeInfrastructurePackageId,
            runtimeName,
            agentInstallMode: runtimeOptions.runtimeAgentInstallMode,
            expectedNodeCount: runtimeOptions.expectedNodeCount
        },
        runtimeEndpointScope: runtimeOptions.runtimeEndpointScope,
        node: {
            runtimeNodeInventoryReportId: stableUuid(`fl-dev:node-inventory:${runtimeInfrastructureId}:${runtimeAgentId}`),
            organizationId,
            runtimeInfrastructureId,
            runtimeAgentId,
            runtimeNodeName: runtimeName,
            infrastructureNodeId: 'local-dev-node',
            runtimeNodeRole: 'TRAINER',
            nodeReady: true,
            runtimeEngineVersion: 'local',
            containerEngineVersion: runtimeOptions.runtimeEnvironmentType,
            operatingSystem: process.platform,
            architecture: process.arch,
            inventoryHash: stableUuid(`fl-dev:node-inventory:${runtimeInfrastructureId}:${runtimeEngineEndpoint}`)
        },
        dataset: {
            datasetId,
            organizationId,
            featureSchemaId,
            datasetName,
            datasetUsage: 'TRAINING'
        },
        binding: {
            runtimeDatasetBindingId,
            datasetId,
            organizationId,
            featureSchemaId,
            datasetName,
            runtimeId,
            dataSourceType: 'FILE',
            host: null,
            port: null,
            url: null,
            databaseName: null,
            schemaName: null,
            tableName: null,
            filePath: datasetPathValue,
            objectBucket: null,
            objectPrefix: null,
            dataFormat: 'CSV',
            credentialSecretName: null
        },
        accessValidation: {
            datasetAccessValidationId,
            runtimeDatasetBindingId,
            datasetId,
            organizationId,
            featureSchemaId,
            datasetName,
            runtimeId,
            dataSourceType: 'FILE',
            host: null,
            port: null,
            url: null,
            databaseName: null,
            schemaName: null,
            tableName: null,
            filePath: datasetPathValue,
            objectBucket: null,
            objectPrefix: null,
            dataFormat: 'CSV',
            credentialSecretName: null
        },
        platformMetadata: {
            runtimeDatasetBindingId,
            metadataReportId: stableUuid('fl-dev:platform-metadata:hospital-readmission-risk-local-runtime-compatible'),
            datasetId,
            organizationId,
            runtimeId,
            featureSchemaId,
            datasetName,
            sampleCount: 6,
            featureCount: 5,
            schemaCompatible: true,
            labelCompatible: true,
            missingValueRate: 0.0,
            duplicateRate: 0.0,
            qualityScore: 1.0,
            nonIidScore: 0.0,
            classBalanceScore: 0.5
        },
        model: {
            modelId,
            modelName: 'linear.LogisticRegression',
            modelVersion: 'v1',
            modelDescription: 'Development baseline logistic regression model for synthetic hospital readmission risk training.',
            sourceType: 'BUILT_IN',
            fileId: null,
            modelFormat: 'JSON'
        },
        trainingRunConfiguration: {
            trainingRunConfigurationId,
            configurationName: 'Hospital Readmission Risk Local Dev',
            federationId,
            featureSchemaId,
            initialModelId: modelId,
            strategyName: 'LOCAL_DEV',
            aggregationAlgorithm: 'FED_AVG_JSON',
            maxRounds: 1,
            minimumNodesPerRound: 1,
            roundTimeoutSeconds: 600,
            nodeResponseTimeoutSeconds: 300,
            localEpochs: 1,
            batchSize: 32,
            learningRate: 0.1,
            optimizer: 'SGD',
            lossFunction: 'LOG_LOSS',
            gradientClippingNorm: null,
            secureAggregationRequired: true,
            minimumAccuracy: 0.0,
            minimumFairnessScore: null
        },
        trainingJob: {
            trainingJobId,
            federationId,
            trainingRunConfigurationId,
            objective: 'Local hospital readmission risk smoke training'
        },
        runtimeAgentEndpoint
    };
}

function feature(featureName, dataType, required, nullable, description, validationRules = [], defaultValue = null, isIdentifier = false) {
    return {
        featureName,
        dataType,
        required,
        nullable,
        description,
        validationRules,
        defaultValue,
        isIdentifier,
        isSensitive: false,
        encodingStrategy: null,
        featureTags: []
    };
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

function normalizeScenario(value) {
    const normalized = String(value ?? '').trim().toLowerCase().replace(/[_\s]/g, '-');
    if (['csv', 'logistic', 'logistic-regression'].includes(normalized)) {
        return 'csv';
    }
    if (['densenet', 'densenet121', 'tiny-imagenet', 'tiny-imagenet-densenet'].includes(normalized)) {
        return 'densenet';
    }
    fail(`Unsupported training scenario: ${value}. Supported scenarios: densenet, csv`);
}

function defaultDatasetPathForScenario(scenario, root) {
    if (scenario === 'csv') {
        return resolve(root, 'volumes/datasets/test.csv');
    }
    return resolve(root, 'volumes/datasets/tiny-imagenet-densenet-dev/train');
}

function defaultRuntimeDatasetPath(scenario, localDatasetPath) {
    if (scenario === 'csv') {
        return `/workspace/datasets/${basename(localDatasetPath)}`;
    }
    return '/workspace/datasets/tiny-imagenet-densenet-dev/train';
}

function warnIfRuntimeAgentConfigurationLikelyMismatches(scenario) {
    if (scenario !== 'densenet') {
        return;
    }
    console.log(
        '[init-training] DenseNet scenario expects runtime-agent local runtime engine configuration: ' +
        'runtime engine image/profile=pytorch-vision, dataset mounted at /workspace/datasets, ' +
        'and modelPlugin delivered through the participant execution plan.'
    );
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

function stableUuid(value) {
    const hex = createHash('sha1').update(value).digest('hex').slice(0, 32);
    return [
        hex.slice(0, 8),
        hex.slice(8, 12),
        `5${hex.slice(13, 16)}`,
        `${(Number.parseInt(hex.slice(16, 17), 16) & 0x3 | 0x8).toString(16)}${hex.slice(17, 20)}`,
        hex.slice(20)
    ].join('-');
}

function stableNameUuid(value) {
    const bytes = createHash('md5').update(value).digest();
    bytes[6] = (bytes[6] & 0x0f) | 0x30;
    bytes[8] = (bytes[8] & 0x3f) | 0x80;
    const hex = bytes.toString('hex');
    return [
        hex.slice(0, 8),
        hex.slice(8, 12),
        hex.slice(12, 16),
        hex.slice(16, 20),
        hex.slice(20)
    ].join('-');
}

function isActive(value) {
    return normalize(value) === 'ACTIVE';
}

function isTrainingJobSubmittedOrBeyond(value) {
    return ['SUBMITTED', 'RUNNING', 'PAUSED', 'CANCELED', 'COMPLETED'].includes(normalize(value));
}

function isJoined(value) {
    return ['JOINED', 'APPROVED', 'ACTIVE'].includes(normalize(value));
}

function isJoinedMembership(item) {
    return Boolean(item) && (isJoined(item.membershipStatus ?? item.state) || Boolean(item.approvalNote));
}

function isPublished(value) {
    return normalize(value) === 'PUBLISHED';
}

function isReported(value) {
    return ['REPORTED', 'METADATAREPORTED'].includes(normalize(value));
}

function isContractValidated(value) {
    return ['CONTRACTVALIDATIONCOMPLETED', 'VALIDATED', 'COMPLETED'].includes(normalize(value));
}

function isApproved(value) {
    return ['APPROVED', 'TRAININGAPPROVED'].includes(normalize(value));
}

function isAccessValidationFinished(item) {
    return Boolean(item) && (isAccessValidated(item.validationStatus) || isFailed(item.validationStatus) || Boolean(item.failureReason));
}

function isAccessValidated(value) {
    return ['CHECKED', 'VALIDATED', 'ACCESSVALIDATED'].includes(normalize(value));
}

function isDatasetCapabilityReadyOrFailed(item) {
    return isDatasetCapabilityReady(item) || isFailed(item?.metadataStatus) || isFailed(item?.contractStatus);
}

function isDatasetCapabilityReady(item) {
    return Boolean(item) && isReported(item.metadataStatus) && isContractValidated(item.contractStatus);
}

function isRuntimeInfrastructurePlannedOrBeyond(value) {
    return [
        'PLANNED',
        'REGISTERED',
        'VERIFIED',
        'AGENTREADY',
        'CONNECTED',
        'VERIFICATIONFAILED',
        'RUNTIMEAGENTFAILED'
    ].includes(normalize(value));
}

function isRuntimeInfrastructureRegisteredOrBeyond(value) {
    return [
        'REGISTERED',
        'VERIFIED',
        'AGENTREADY',
        'CONNECTED',
        'VERIFICATIONFAILED',
        'RUNTIMEAGENTFAILED'
    ].includes(normalize(value));
}

function isFailed(value) {
    return ['FAILED', 'REJECTED', 'UNAVAILABLE'].includes(normalize(value));
}

function normalize(value) {
    return String(value ?? '').replace(/[_\s-]/g, '').toUpperCase();
}

function booleanOption(value, fallback = false) {
    if (value == null) return fallback;
    if (value === true) return true;
    const normalized = normalize(value);
    if (['TRUE', '1', 'YES', 'Y', 'ON'].includes(normalized)) return true;
    if (['FALSE', '0', 'NO', 'N', 'OFF'].includes(normalized)) return false;
    return fallback;
}

function defaultRuntimePackageName(environmentType) {
    switch (normalize(environmentType)) {
        case 'K3S':
        case 'KUBERNETES':
            return 'local-k3s-runtime-agent';
        case 'DOCKERCOMPOSE':
        default:
            return 'local-docker-compose-runtime-agent';
    }
}

function canonicalRuntimeEnvironmentType(value) {
    switch (normalize(value)) {
        case 'DOCKERCOMPOSE':
            return 'DOCKER_COMPOSE';
        case 'KUBERNETES':
            return 'KUBERNETES';
        case 'K3S':
            return 'K3S';
        default:
            return String(value ?? '').trim().toUpperCase();
    }
}

function positiveInt(value, fallback) {
    const parsed = Number.parseInt(value, 10);
    return Number.isFinite(parsed) && parsed > 0 ? parsed : fallback;
}

function trimSlash(value) {
    return String(value).replace(/\/+$/, '');
}

function compact(value) {
    return String(value ?? '').replace(/\s+/g, ' ').trim().slice(0, 600);
}

function errorSummary(error) {
    return error?.message ?? String(error);
}

function sleep(ms) {
    return new Promise((resolve) => setTimeout(resolve, ms));
}

function fail(message) {
    console.error(`[init-training] ${message}`);
    process.exit(1);
}
