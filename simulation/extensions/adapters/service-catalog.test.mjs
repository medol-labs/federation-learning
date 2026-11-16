import {test} from 'node:test';
import assert from 'node:assert/strict';
import {deploymentCatalog} from './service-catalog.mjs';
import {planDeployment} from '../../src/runtime/deployment.mjs';

test('project deployment catalog can be reused without accumulating services', () => {
    const first = deploymentCatalog();
    const second = deploymentCatalog(first);
    assert.deepEqual(second, first);
    assert.notEqual(second.services, first.services);
    assert.equal(new Set(first.services.map(service => service.name)).size, first.services.length);
    const instance = {service: 'federation-learning-participant-console', replicas: 1};
    const config = {target: 'kubernetes', environment: 'local', platform: 'linux/arm64', instances: [instance]};
    assert.throws(() => planDeployment(config, first), /requires active service/);
    assert.doesNotThrow(() => planDeployment({...config, instances: [instance,
        {service: 'federation-learning-runtime-agent', replicas: 1}]}, second));
});
