import {readFileSync, writeFileSync, renameSync} from 'node:fs';
import {resolve} from 'node:path';
import {pathToFileURL} from 'node:url';
import {executeDeployment as executeInfrastructure, runDeployment, planDeployment} from '../../src/runtime/deployment.mjs';
import {createPlatformDeployment} from '../adapters/platform-deployment.mjs';
import {participantSlot} from '../adapters/participant-slots.mjs';
import {deploymentCatalog} from '../adapters/service-catalog.mjs';
const catalog = deploymentCatalog();

export function deploymentPlan(config) {
    if (config.participantSlots !== undefined && (!Number.isInteger(config.participantSlots) || config.participantSlots < 0)) throw new Error('participantSlots must be a non-negative integer');
    if (config.participantSlots && config.participantAccess) participantSlot('validation', config.participantSlots, config.participantAccess);
    if (config.target !== 'kubernetes') throw new Error('This project adapter requires Kubernetes');
    if (!config.kubeconfigFile) throw new Error('kubeconfigFile is required; prepare the environment first');
    if (!config.instances?.length) throw new Error('Explicit instances are required');
    planDeployment({...config, environment: config.environment || 'local'}, catalog);
    if (!/^[a-z][a-z0-9-]*$/.test(config.clusterName || '')) throw new Error('Explicit clusterName is required');
    if (!config.imagePrefix || !config.platform) throw new Error('imagePrefix and platform are required');
    if (!['linux/arm64', 'linux/amd64'].includes(config.platform)) throw new Error('Unsupported platform');
    return ['validateConnection', 'prepareImages', 'deploy', 'waitDeploymentReady'];
}

export async function executeDeployment(config, factory = createPlatformDeployment, record = () => {}) {
    deploymentPlan(config);
    return attachParticipants(await executeInfrastructure({...config, environment: config.environment || 'local'},
        () => createAdapter(config, factory), {record, catalog}), config);
}

function attachParticipants(result, config) {
    return {...result, imagePrefix: config.imagePrefix, imageVersion: config.imageVersion, participantDeployments: Array.from({length: config.participantSlots || 0}, (_, index) =>
        participantSlot(result.namespace || 'federation-learning-platform', index + 1, config.participantAccess))};
}

export async function runProjectDeployment(config, factory = () => createAdapter(config), options = {}) {
    deploymentPlan(config);
    const result = attachParticipants(await runDeployment(config, factory, {...options, catalog}), config);
    const path = resolve(options.reportRoot || 'simulation/reports/deployments', `${result.deploymentId}.json`);
    writeFileSync(`${path}.tmp`, JSON.stringify(result, null, 2), {mode: 0o600});
    renameSync(`${path}.tmp`, path);
    return result;
}

export function createAdapter(config, factory = createPlatformDeployment) {
    return factory({...config, environment: config.environment || 'local'});
}

if (process.argv[1] && import.meta.url === pathToFileURL(resolve(process.argv[1])).href) {
    const file = process.argv[2];
    try {
        if (!file) throw new Error('Usage: node simulation/extensions/runtime/deploy.mjs <config.json> [--plan]');
        const config = JSON.parse(readFileSync(file, 'utf8'));
        const steps = deploymentPlan(config);
        if (process.argv.includes('--plan')) console.log(JSON.stringify({config, steps}, null, 2));
        else {
            const result = await runProjectDeployment({...config, environment: config.environment || 'local'},
                () => createAdapter(config), {catalog, record: entry => console.log(`[deployment] ${entry.step}: ${entry.status}`)});
            console.log(`Deployment ready: simulation/reports/deployments/${result.deploymentId}.json`);
        }
    } catch (error) { console.error(error.message); process.exitCode = 1; }
}
