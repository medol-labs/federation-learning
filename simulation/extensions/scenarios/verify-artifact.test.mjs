import test from 'node:test';
import assert from 'node:assert/strict';
import {createHash} from 'node:crypto';
import {verifyArtifact} from './verify-artifact.mjs';

const data = Buffer.from('model');
const model = {modelId: 'model-id', modelArtifactUri: 'http://localhost:8080/api/files/model',
    modelArtifactDigest: `sha256:${createHash('sha256').update(data).digest('hex')}`};

test('streams artifact and verifies content digest', async () => {
    const result = await verifyArtifact(model, ['http://localhost:8080'], () => ({}), async () => new Response(data));
    assert.equal(result.bytes, 5);
});

test('rejects unrelated endpoints before attaching credentials', async () => {
    let calls = 0;
    await assert.rejects(verifyArtifact({...model, modelArtifactUri: 'http://untrusted.test/model'},
        ['http://localhost:8080'], () => { calls++; return {}; }), /configured/);
    assert.equal(calls, 0);
});

test('rejects empty and corrupt artifacts', async () => {
    await assert.rejects(verifyArtifact(model, ['http://localhost:8080'], () => ({}), async () => new Response('')), /empty/);
    await assert.rejects(verifyArtifact(model, ['http://localhost:8080'], () => ({}), async () => new Response('corrupt')), /mismatch/);
});
