import {resolve} from 'node:path';
import {readFileSync} from 'node:fs';
import {execFileSync} from 'node:child_process';
import {provisionFixtures} from '../../src/runtime/fixtures.mjs';
import {checkCases, checkApproval, selectCases} from '../../src/cases.mjs';
import {validateBaselineManifest} from '../../src/data-plan.mjs';
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
export const select = ({library, selection}, {root}) => selectCases(library, selection,
    selection.failedReport ? JSON.parse(readFileSync(resolve(root, selection.failedReport), 'utf8')) : undefined);
export const enroll = ({environment, scenario, baseline}) => enrollParticipants(environment, scenario, baseline);
export async function prepare({environment, scenario, baseline, cases}, {root}) {
    validateBaselineManifest(baseline, environment);
    checkCases(cases); checkApproval(cases);
    trainingDataPlan(scenario, cases);
    const fixtures = provisionFixtures(scenario.fixtures || [], environment, {root});
    const result = await prepareCaseData(environment, scenario, baseline, cases);
    return {...result, fixtures};
}
