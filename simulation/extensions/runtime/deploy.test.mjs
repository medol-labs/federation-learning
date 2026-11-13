import {test} from 'node:test';
import assert from 'node:assert/strict';
import {deploymentPlan, executeDeployment} from './deploy.mjs';

const config = {target: 'kubernetes', clusterName: 'test', kubeconfigFile: '/tmp/test-kubeconfig', platform: 'linux/arm64', imagePrefix: 'registry.example/test', instances: [{service: 'postgres', replicas: 1}]};
test('deployment only executes infrastructure actions and forwards capacity', async () => {
    const calls = [];
    const result = await executeDeployment(config, async args => {
        assert.equal(args[args.indexOf('--kubeconfig-file') + 1], '/tmp/test-kubeconfig');
        assert.equal(args.includes('--compute-nodes'), false);
        assert.equal(args.includes('--skip-cluster'), true);
        assert.equal(args.includes('--participant-count'), false);
        return Object.fromEntries([
            ...deploymentPlan(config).map(name => [name, async () => calls.push(name)]),
            ['cleanup', async () => calls.push('cleanup')],
            ['deploymentSnapshot', () => ({nodes: [{architecture: 'arm64'}]})]
        ]);
    });
    assert.equal(result.status, 'ready');
    assert.deepEqual(calls, [...deploymentPlan(config), 'cleanup']);
});
test('rejects unsupported targets and invalid capacity before execution', () => {
    assert.throws(() => deploymentPlan({...config, target: 'compose'}));
    assert.throws(() => deploymentPlan({...config, kubeconfigFile: ''}));
});
test('architecture mismatch aborts before deploying and cleans up', async () => {
    let cleaned = false;
    await assert.rejects(executeDeployment(config, async () => ({
        validateConnection: async () => {},
        prepareImages: async () => {}, deploy: async () => {}, waitDeploymentReady: async () => {},
        deploymentSnapshot: () => ({nodes: [{architecture: 'amd64'}]}),
        cleanup: async () => { cleaned = true; }
    })), /architecture/);
    assert.equal(cleaned, true);
});
test('failed deployment records its step and never proceeds to readiness', async () => {
    const progress = [];
    let cleaned = false;
    await assert.rejects(executeDeployment(config, async () => ({
        validateConnection: async () => {},
        deploymentSnapshot: () => ({nodes: [{architecture: 'arm64'}]}),
        prepareImages: async () => { throw new Error('Image unavailable'); },
        deploy: async () => assert.fail('must not deploy'),
        waitDeploymentReady: async () => assert.fail('must not check readiness'),
        cleanup: async () => { cleaned = true; }
    }), entry => progress.push(entry)), /Image unavailable/);
    assert.deepEqual(progress.map(({step, status}) => [step, status]), [
        ['validateConnection', 'running'], ['validateConnection', 'complete'],
        ['prepareImages', 'running'], ['prepareImages', 'failed']
    ]);
    assert.equal(cleaned, true);
});
