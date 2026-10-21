import assert from 'node:assert/strict';
import { spawnSync } from 'node:child_process';
import { mkdtempSync, mkdirSync, readFileSync, writeFileSync, rmSync } from 'node:fs';
import { tmpdir } from 'node:os';
import { join } from 'node:path';
import test from 'node:test';
import { readJson, sha256, sourceAssociation, verifyDelivery } from '../docs/document-support/document-traceability.mjs';

const root = new URL('..', import.meta.url);
test('source-only legacy documents cannot claim a released snapshot', () => {
  const source = { sha256: sha256('source'), workspaceId: 'w', versionId: 'v', versionNo: 2,
    release: { channel: 'stable', label: 'v1.0.0' } };
  const baseline = { metadata: { source }, sourceVerified: true };
  const content = 'manual';
  const legacy = { sha256: sha256(content), model: { source: { sha256: source.sha256 } } };
  assert.equal(sourceAssociation(legacy, content, baseline), 'unverified');
  assert.equal(sourceAssociation({ ...legacy, model: { source } }, content, baseline), 'verified');
});

test('release notes do not break normalized generation metadata matching', () => {
  const dir = mkdtempSync(join(tmpdir(), '438c-release-notes-'));
  try {
    const metadata = { schemaVersion: 1, medolVersion: '0.1.0', source: { sha256: sha256('source'),
      release: { channel: 'stable', label: 'v1.0.0', notes: 'Release description' } } };
    const deliveredMetadata = structuredClone(metadata);
    delete deliveredMetadata.source.release.notes;
    const file = join(dir, 'delivery.json');
    writeFileSync(file, JSON.stringify({ schemaVersion: 1, imageVersion: 'v1.0.0.1', packageVersion: '1.0.0',
      buildIdentity: sha256('build'), codeSha256: sha256('code'), generation: {
        schemaVersion: 1, model: { sha256: sha256('model'), metadata: deliveredMetadata }, inputs: [] },
      images: [{ name: 'app:v1.0.0.1', id: `sha256:${sha256('image')}`, repoDigests: [] }] }));
    assert.equal(verifyDelivery(file, { metadata, sourceVerified: true, sha256: sha256('model') }).deliveryId, 'v1.0.0.1');
  } finally { rmSync(dir, { recursive: true, force: true }); }
});

test('Markdown tracks independent revisions and verifies released source and exact delivery inputs', () => {
  const dir = mkdtempSync(join(tmpdir(), '438c-trace-'));
  try {
    const sources = join(dir, 'sources');
    const manual = join(dir, 'manual');
    mkdirSync(sources);
    mkdirSync(join(manual, 'test-plan'), { recursive: true });
    const model = readJson(new URL('./fixtures/438c-traceability/model.json', import.meta.url));
    const source = 'domain Demo {}\n';
    model.metadata.source.sha256 = sha256(source);
    writeFileSync(join(dir, 'source.medol'), source);
    writeFileSync(join(dir, 'model.json'), JSON.stringify(model));
    const sourceManifest = { schemaVersion: 1, documents: {} };
    for (const name of ['test-outline.zh-CN.md', 'model-translations.zh-CN.md']) {
      const content = '# Test\n\n## 测试目标与范围\n\nApproved content.\n';
      writeFileSync(join(sources, name), content);
      sourceManifest.documents[name] = { sha256: sha256(content), model: model.metadata };
    }
    const saveSources = () => writeFileSync(join(sources, 'source-doc-manifest.json'), JSON.stringify(sourceManifest));
    saveSources();
    const config = readJson(new URL('../docs/438c/config/project.json', import.meta.url));
    config.documentVersion = 'doc-4';
    writeFileSync(join(dir, 'config.json'), JSON.stringify(config));
    const run = (...extra) => spawnSync('node', ['scripts/generate-438c-docs.mjs', '--format', 'markdown', '--only=test-plan',
      `--source-dir=${sources}`, `--manual=${manual}`, `--out=${dir}/out`, `--config=${dir}/config.json`,
      `--model=${dir}/model.json`, `--model-source=${dir}/source.medol`,
      `--facts-config=${dir}/absent.json`, `--facts-out=${dir}/absent-facts.json`, ...extra], { cwd: root, encoding: 'utf8' });
    const read = () => readJson(join(dir, 'out/document-manifest.json')).documents['test-plan.zh-CN'];
    let result = run();
    assert.equal(result.status, 0, result.stderr);
    assert.equal(read().softwareVersion, 'v2.3.0');
    assert.equal(read().documentVersion, 'doc-4');
    assert.equal(read().revision, 1);
    assert.equal(read().status, 'released-model-baseline');
    assert.equal(read().applicableDelivery, null);
    assert.equal(read().artifacts[0].sha256, sha256(readFileSync(join(dir, 'out/markdown/test-plan.md'))));
    assert.equal(run().status, 0);
    assert.equal(read().revision, 1);
    assert.equal(run('--only=test-description').status, 0);
    writeFileSync(join(manual, 'test-plan/2 引用文档.md'), 'Manual content-only revision.\n');
    assert.equal(run().status, 0);
    assert.equal(read().revision, 2);
    assert.equal(read().history.length, 2);
    assert.equal(run('--only=test-description').status, 0);
    assert.equal(readJson(join(dir, 'out/document-manifest.json')).documents['test-description.zh-CN'].revision, 1);
    assert.equal(read().revision, 2);
    const delivery = { schemaVersion: 1, imageVersion: 'v2.3.0.17', packageVersion: '2.3.0',
      buildIdentity: sha256('build-17'), codeSha256: sha256('code'), git: { revision: 'abc', dirty: false },
      generation: { schemaVersion: 1, model: { sha256: sha256(readFileSync(join(dir, 'model.json'))), metadata: model.metadata },
        inputs: [{ path: '.medol/source.medol', sha256: sha256(source) }] },
      images: [{ name: 'platform:v2.3.0.17', id: `sha256:${sha256('image')}`, repoDigests: [] }] };
    const deliveryPath = join(dir, 'delivery.json');
    const saveDelivery = () => writeFileSync(deliveryPath, JSON.stringify(delivery));
    saveDelivery();
    result = run('--release-manifest', deliveryPath);
    assert.equal(result.status, 0, result.stderr);
    assert.equal(read().applicableDelivery.deliveryId, 'v2.3.0.17');
    for (const mutate of [
      (value) => { value.images = []; },
      (value) => { value.images[0].id = null; },
      (value) => { delete value.buildIdentity; },
      (value) => { value.generation.model.sha256 = sha256('another model'); },
      (value) => { value.generation.model.metadata.source.versionId = 'other'; },
      (value) => { value.imageVersion = 'v9.0.0.1'; },
      (value) => { delete value.generation.model.metadata.source.release; },
    ]) {
      const invalid = structuredClone(delivery);
      mutate(invalid);
      writeFileSync(deliveryPath, JSON.stringify(invalid));
      assert.notEqual(run('--release-manifest', deliveryPath).status, 0);
    }
    saveDelivery();
    sourceManifest.documents['test-outline.zh-CN.md'].model = { source: { sha256: sha256('old') } };
    saveSources();
    assert.equal(run().status, 0);
    assert.equal(read().status, 'draft');
    assert.equal(read().applicableModel, null);
    assert.equal(read().softwareVersion, config.softwareVersion);
    assert.notEqual(run('--release-manifest', deliveryPath).status, 0);
    sourceManifest.documents['test-outline.zh-CN.md'].model = model.metadata;
    saveSources();
    writeFileSync(join(sources, 'test-outline.zh-CN.md'), '# Unexported edit\n');
    assert.equal(run().status, 0);
    assert.equal(read().status, 'draft');
    writeFileSync(join(dir, 'source.medol'), 'different source');
    assert.equal(run().status, 0);
    assert.equal(read().candidateModel.sourceVerified, false);
    writeFileSync(join(dir, 'model.json'), '{}');
    assert.equal(run().status, 0);
    assert.equal(read().status, 'draft');
    assert.equal(read().candidateModel, null);
    assert.notEqual(run('--release-manifest', deliveryPath).status, 0);
  } finally { rmSync(dir, { recursive: true, force: true }); }
});
