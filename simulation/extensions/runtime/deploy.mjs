import {readFileSync} from 'node:fs';
import {resolve} from 'node:path';
import {pathToFileURL} from 'node:url';
import {executeDeployment as executeInfrastructure, runDeployment} from '../../src/runtime/deployment.mjs';
import {createTrainingEnvironment} from '../adapters/training-environment.mjs';
const catalog = JSON.parse(readFileSync(new URL('../../../operations/model/services.json', import.meta.url), 'utf8'));
catalog.services.push({name: 'apisix'}, {name: 'federation-learning-aggregation-runtime-engine'});

export function deploymentPlan(config) {
    if (config.target !== 'kubernetes') throw new Error('This project adapter requires Kubernetes');
    if (!config.kubeconfigFile) throw new Error('kubeconfigFile is required; prepare the environment first');
    if (!config.instances?.length) throw new Error('Explicit instances are required');
    if (!/^[a-z][a-z0-9-]*$/.test(config.clusterName || '')) throw new Error('Explicit clusterName is required');
    if (!config.imagePrefix || !config.platform) throw new Error('imagePrefix and platform are required');
    if (!['linux/arm64', 'linux/amd64'].includes(config.platform)) throw new Error('Unsupported platform');
    return ['validateConnection', 'prepareImages', 'deploy', 'waitDeploymentReady'];
}

export async function executeDeployment(config, factory = createTrainingEnvironment, record = () => {}) {
    deploymentPlan(config);
    return executeInfrastructure({...config, environment: config.environment || 'local'},
        () => createAdapter(config, factory), {record, catalog});
}

function createAdapter(config, factory = createTrainingEnvironment) {
    return factory([
        '--environment', config.environment || 'local', '--cluster-name', config.clusterName,
        '--kubeconfig-file', config.kubeconfigFile, '--skip-cluster', 'true', '--image-source', 'registry',
        '--deployment-instances', JSON.stringify(config.instances),
        '--app-image-prefix', config.imagePrefix, '--rebuild-app-images', 'false', '--recreate', 'false',
        '--sync-default-kubeconfig', 'false', '--timeout', String(config.timeoutMs || 600000)
    ]);
}

if (process.argv[1] && import.meta.url === pathToFileURL(resolve(process.argv[1])).href) {
    const file = process.argv[2];
    try {
        if (!file) throw new Error('Usage: node simulation/extensions/runtime/deploy.mjs <config.json> [--plan]');
        const config = JSON.parse(readFileSync(file, 'utf8'));
        const steps = deploymentPlan(config);
        if (process.argv.includes('--plan')) console.log(JSON.stringify({config, steps}, null, 2));
        else {
            const result = await runDeployment({...config, environment: config.environment || 'local'},
                () => createAdapter(config), {catalog, record: entry => console.log(`[deployment] ${entry.step}: ${entry.status}`)});
            console.log(`Deployment ready: simulation/reports/deployments/${result.deploymentId}.json`);
        }
    } catch (error) { console.error(error.message); process.exitCode = 1; }
}
