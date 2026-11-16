import {test} from 'node:test';
import assert from 'node:assert/strict';
import {mkdtempSync, writeFileSync, rmSync} from 'node:fs';
import {tmpdir} from 'node:os';
import {resolve} from 'node:path';
import {fileURLToPath} from 'node:url';
import {spawnSync} from 'node:child_process';
import {participantOverlay, participantSlot} from './participant-slots.mjs';

const root = fileURLToPath(new URL('../../../', import.meta.url));
const access = {publicUrl: 'http://192.168.50.2', hostPortStart: 31081, nodePortStart: 30081, capacity: 20};
test('participant gateways have independent bounded public ports', () => {
    const one = participantSlot('fl', 1, access), two = participantSlot('fl', 2, access);
    assert.equal(one.consoleUrl, 'http://192.168.50.2:31081');
    assert.equal(two.consoleUrl, 'http://192.168.50.2:31082');
    assert.equal(two.nodePort, 30082);
    assert.throws(() => participantSlot('fl', 21, access), /capacity/);
    assert.throws(() => participantSlot('fl', 1, {...access, nodePortStart: 29999}), /range/);
    const overlay = participantOverlay({root, environment: 'local', namespace: 'fl', slot: 2, imagePrefix: 'registry/fl', imageVersion: 'test', access});
    const servicePatch = JSON.parse(overlay.patches.find(p => p.target?.kind === 'Service').patch);
    assert.deepEqual(servicePatch.map(p => p.value), ['NodePort', 30082]);
    const config = overlay.patches.filter(p => !p.target && p.patch).map(p => JSON.parse(p.patch)).find(p => p.data?.MEDOL_SECURITY_ALLOWED_ORIGINS);
    assert.equal(config.data.MEDOL_SECURITY_ALLOWED_ORIGINS, two.consoleUrl);
});
test('slots have distinct storage namespaces and stable endpoints before registration', () => {
    assert.notEqual(participantSlot('fl', 1).namespace, participantSlot('fl', 2).namespace);
    assert.equal(participantSlot('fl', 2).endpoint, 'http://federation-learning-runtime-agent.fl-p2.svc.cluster.local:8082');
    assert.throws(() => participantSlot('fl', 0));
});
test('operations-based participant overlay renders without a cluster or business identity', () => {
    const dir = mkdtempSync(resolve(tmpdir(), 'participant-overlay-'));
    try {
        const overlay = participantOverlay({root, environment: 'local', namespace: 'fl', slot: 1, imagePrefix: 'registry/fl', imageVersion: 'test'});
        writeFileSync(resolve(dir, 'namespace.json'), JSON.stringify({apiVersion: 'v1', kind: 'Namespace', metadata: {name: 'fl-p1'}}));
        writeFileSync(resolve(dir, 'kustomization.yaml'), JSON.stringify(overlay));
        const result = spawnSync('kubectl', ['kustomize', dir, '--load-restrictor', 'LoadRestrictionsNone'], {encoding: 'utf8'});
        assert.equal(result.status, 0, result.stderr);
        assert.match(result.stdout, /namespace: fl-p1/);
        assert.match(result.stdout, /registry\/fl\/federation-learning-runtime-agent:test/);
        assert.match(result.stdout, /MANUAL_GUIDED/);
        assert.match(result.stdout, /participant-enrollment/);
        assert.match(result.stdout, /mountPath: \/workspace\/datasets/);
        assert.doesNotMatch(result.stdout, /nodePort:|RUNTIME_AGENT_ID:/);
    } finally { rmSync(dir, {recursive: true, force: true}); }
});
