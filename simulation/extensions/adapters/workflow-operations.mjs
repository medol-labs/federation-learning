import {resolve} from 'node:path';
import {readFileSync} from 'node:fs';
import {execFileSync} from 'node:child_process';
import {provisionFixtures} from '../../src/runtime/fixtures.mjs';
import {prepareWithoutFixtures} from '../../src/workflow-tasks.mjs';
import {createAdapter, deploymentPlan, runProjectDeployment} from '../runtime/deploy.mjs';
import {prepareCaseData, enrollParticipants, trainingDataPlan} from '../runtime/training-stages.mjs';
import {prepareBaseline} from './baseline-data.mjs';
import {prepareTestEnvironment} from '../runtime/prepare-test-environment.mjs';

export async function configureTestEnvironment({config}, {root}) {
    return prepareTestEnvironment(root, config);
}

export async function deploy({config}) {
    deploymentPlan(config);
    return runProjectDeployment(config, async () => {
        const adapter = await createAdapter(config);
        const apply = adapter.deploy.bind(adapter);
        adapter.deploy = async () => {
            // This scheduling role belongs to this project's aggregation deployment.
            const env = {...process.env, KUBECONFIG: resolve(config.kubeconfigFile)};
            const nodes = JSON.parse(execFileSync('kubectl', ['get', 'nodes', '-o', 'json'], {env, encoding: 'utf8', timeout: 30000}));
            const workers = nodes.items.filter(node => !Object.hasOwn(node.metadata.labels || {}, 'node-role.kubernetes.io/control-plane')
                && !Object.hasOwn(node.metadata.labels || {}, 'node-role.kubernetes.io/master')).map(node => node.metadata.name);
            if (!workers.length) throw new Error('No worker node for aggregation');
            execFileSync('kubectl', ['label', 'node', ...workers, 'medol.dev/node-role=runtime', '--overwrite'], {env, timeout: 30000, stdio: 'inherit'});
            await apply();
        };
        return adapter;
    });
}

export const baseline = prepareBaseline;
export {select} from '../../src/workflow-tasks.mjs';
export const enroll = ({environment, scenario, baseline}) => enrollParticipants(environment, scenario, baseline);
export async function prepare({environment, fixtureScenarios, baseline, cases}, {root}) {
    const ready = prepareWithoutFixtures(environment, baseline, cases);
    if (ready) return ready;
    const files = new Set(cases.cases.filter(c => c.dataRequirements.length).map(c => fixtureScenarios[c.id]));
    if (files.size !== 1 || files.has(undefined)) throw new Error('Selection requires a configured, shared fixture scenario');
    const scenario = JSON.parse(readFileSync(resolve(root, [...files][0]), 'utf8'));
    trainingDataPlan(scenario, cases);
    const fixtures = provisionFixtures(scenario.fixtures || [], environment, {root});
    const result = await prepareCaseData(environment, scenario, baseline, cases);
    return {...result, fixtures};
}
