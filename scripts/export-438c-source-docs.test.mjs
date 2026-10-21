import assert from 'node:assert/strict';
import { spawnSync } from 'node:child_process';
import { mkdtempSync, mkdirSync, readFileSync, rmSync, writeFileSync } from 'node:fs';
import { tmpdir } from 'node:os';
import { join } from 'node:path';
import { fileURLToPath } from 'node:url';
import test from 'node:test';
import { legacySourceHash, readJson, sha256, sourceAssociation } from '../docs/document-support/document-traceability.mjs';

test('default export preserves the current PRD and removes unapproved AI review', () => {
  const directory = mkdtempSync(join(tmpdir(), 'medol-438c-export-'));
  try {
    const database = join(directory, 'documents.sqlite');
    const kinds = [
      'software-design', 'database-design', 'test-outline',
      'installation-manual', 'user-manual', 'model-translations'
    ];
    const markdown = '# 正文\n\n已确认。.\n\n<!-- em:section id="document.aiReview" -->\n## AI 建议\n\n待审核。';
    const sql = [
      'CREATE TABLE modeling_documents (workspace_id TEXT, language TEXT, kind TEXT, markdown TEXT, updated_at TEXT);',
      ...kinds.map((kind) => `INSERT INTO modeling_documents VALUES ('demo', 'zh-CN', '${kind}', '${markdown.replaceAll("'", "''")}', '2026-01-01');`)
    ].join('\n');
    const setup = spawnSync('sqlite3', [database, sql], { encoding: 'utf8' });
    assert.equal(setup.status, 0, setup.stderr);
    const prdPath = join(directory, 'prd.zh-CN.md');
    writeFileSync(prdPath, '# 最新 PRD\n');
    const result = spawnSync('node', [
      'scripts/export-438c-source-docs.mjs', `--db=${database}`,
      `--out=${directory}`, '--workspace-id=demo', `--workspace-db=${directory}/absent.sqlite`
    ], { cwd: new URL('..', import.meta.url), encoding: 'utf8' });
    assert.equal(result.status, 0, result.stderr);
    assert.equal(readFileSync(prdPath, 'utf8'), '# 最新 PRD\n');
    assert.equal(readFileSync(join(directory, 'software-design.zh-CN.md'), 'utf8'), '# 正文\n\n已确认。\n');
    assert.equal(readFileSync(join(directory, 'database-design.zh-CN.md'), 'utf8'), '# 正文\n\n已确认。.\n');
    const manifest = readJson(join(directory, 'source-doc-manifest.json'));
    assert.equal(manifest.documents['prd.zh-CN.md'], undefined);
    assert.equal(manifest.documents['software-design.zh-CN.md'].model, null);
    assert.equal(manifest.documents['software-design.zh-CN.md'].sha256,
      sha256(readFileSync(join(directory, 'software-design.zh-CN.md'))));
  } finally {
    rmSync(directory, { recursive: true, force: true });
  }
});

test('exports exact document revision and historical model release without assigning current model to stale content', () => {
  const directory = mkdtempSync(join(tmpdir(), 'medol-438c-revisions-'));
  try {
    const database = join(directory, 'documents.sqlite');
    const oldSource = 'domain Old {}\n';
    const currentSource = 'domain Current {}\n';
    const body = '# Old model design\n';
    const sql = (value) => `'${value.replaceAll("'", "''")}'`;
    const schema = readFileSync(new URL('./fixtures/438c-traceability/documents.sql', import.meta.url), 'utf8');
    const statements = `${schema}
      INSERT INTO modeling_workspace_versions VALUES ('old-1','demo',1,${sql(oldSource)},${sql(legacySourceHash(oldSource))},'stable','v1.0.0','2026-01-01');
      INSERT INTO modeling_workspace_versions VALUES ('new-2','demo',2,${sql(currentSource)},${sql(legacySourceHash(currentSource))},'stable','v2.0.0','2026-02-01');
      INSERT INTO modeling_documents VALUES ('doc-1','demo','Design','software-design','zh-CN',${sql(body)},${sql(legacySourceHash(oldSource))},'2026-02-02');
      INSERT INTO modeling_document_versions VALUES ('rev-3','doc-1','demo',3,'manual',${sql(body)},${sql(legacySourceHash(oldSource))},'old-1','2026-02-02',1,'v1.0.0');`;
    assert.equal(spawnSync('sqlite3', [database, statements], { encoding: 'utf8' }).status, 0);
    writeFileSync(join(directory, 'source.medol'), currentSource);
    const run = () => spawnSync('node', ['scripts/export-438c-source-docs.mjs', `--db=${database}`,
      `--out=${directory}`, '--workspace-id=demo', '--only=software-design', `--model-source=${directory}/source.medol`,
      `--workspace-db=${directory}/workspace.sqlite`],
    { cwd: new URL('..', import.meta.url), encoding: 'utf8' });
    let result = run();
    assert.equal(result.status, 0, result.stderr);
    const entry = () => readJson(join(directory, 'source-doc-manifest.json')).documents['software-design.zh-CN.md'];
    assert.equal(entry().revision.revisionNo, 3);
    assert.equal(entry().revision.origin, 'manual');
    assert.equal(entry().revision.workspaceReleaseLabel, 'v1.0.0');
    assert.equal(entry().revision.workspaceVersionId, 'old-1');
    assert.equal(entry().model.source.sha256, sha256(oldSource));
    assert.equal(entry().model.source.release.label, 'v1.0.0');
    assert.equal(entry().model.source.versionId, 'old-1');
    const moveVersions = `ATTACH DATABASE ${sql(join(directory, 'workspace.sqlite'))} AS workspace;
      CREATE TABLE workspace.modeling_workspace_versions AS SELECT * FROM main.modeling_workspace_versions;
      DROP TABLE main.modeling_workspace_versions;`;
    assert.equal(spawnSync('sqlite3', [database, moveVersions], { encoding: 'utf8' }).status, 0);
    assert.equal(run().status, 0);
    assert.equal(entry().model.source.release.label, 'v1.0.0');
    assert.equal(spawnSync('sqlite3', [join(directory, 'workspace.sqlite'), 'DROP TABLE modeling_workspace_versions;'], { encoding: 'utf8' }).status, 0);
    assert.equal(run().status, 0);
    assert.equal(entry().model, null);
    assert.equal(entry().revision.workspaceReleaseLabel, 'v1.0.0');
    writeFileSync(join(directory, 'source.medol'), oldSource);
    assert.equal(run().status, 0);
    assert.equal(entry().model.source.sha256, sha256(oldSource));
    assert.equal(entry().model.source.versionId, 'old-1');
    assert.equal(entry().model.source.release, undefined);
    writeFileSync(join(directory, 'source.medol'), currentSource);
    // An edit without a matching stored revision must not inherit its revision/model identity.
    assert.equal(spawnSync('sqlite3', [database, "UPDATE modeling_documents SET markdown='# Unsaved revision';"], { encoding: 'utf8' }).status, 0);
    result = run();
    assert.equal(result.status, 0, result.stderr);
    assert.equal(entry().revision, null);
    assert.equal(entry().model, null);
    // A legacy FNV fingerprint can be checked against source bytes, but is not itself SHA256.
    assert.equal(spawnSync('sqlite3', [database, `UPDATE modeling_documents SET source_hash=${sql(legacySourceHash(currentSource))};`], { encoding: 'utf8' }).status, 0);
    assert.equal(run().status, 0);
    assert.equal(entry().model.source.sha256, sha256(currentSource));
    assert.equal(entry().model.source.versionId, undefined);
    assert.equal(entry().model.source.release, undefined);
  } finally { rmSync(directory, { recursive: true, force: true }); }
});

test('normal repository generation exports verified catalogs and documents with immutable stable snapshot bindings', () => {
  const directory = mkdtempSync(join(tmpdir(), 'medol-438c-repository-'));
  try {
    const database = join(directory, 'documents.sqlite');
    const workspaceDatabase = join(directory, 'workspace.sqlite');
    const medolRoot = fileURLToPath(new URL('../../medol/', import.meta.url));
    const source = 'domain CatalogExport {}';
    const kinds = ['software-design', 'database-design', 'test-outline', 'installation-manual', 'user-manual', 'model-translations'];
    const repositoryRun = (code) => {
      const result = spawnSync(process.execPath, ['--import', 'tsx', '--input-type=module', '-e', `
        const workspace = await import('./src/server/modelingWorkspaceRepository.ts');
        const documents = await import('./src/server/modelingDocumentRepository.ts');
        const { documentDatabase } = await import('./src/server/documentDatabase.ts');
        ${code}
      `], { cwd: medolRoot, encoding: 'utf8', env: { ...process.env,
        MEDOL_DB_PATH: workspaceDatabase, MEDOL_DOCUMENT_DB_PATH: database } });
      assert.equal(result.status, 0, result.stderr);
      return JSON.parse(result.stdout);
    };
    const initial = repositoryRun(`
      const model = workspace.createModelingWorkspace({ name: 'Export integration', dsl: ${JSON.stringify(source)} });
      const release = workspace.createModelingWorkspaceVersion(model.id, {
        dsl: model.dsl, releaseChannel: 'stable', releaseLabel: 'v1.0.0'
      }).version;
      for (const kind of ${JSON.stringify(kinds)}) {
        documents.createModelingDocument({ workspaceId: model.id, title: kind, kind, language: 'zh-CN',
          sourceHash: ${JSON.stringify(legacySourceHash(source))}, markdown: '# ' + kind + '\\n\\nGenerated body.' });
      }
      documentDatabase.prepare("INSERT INTO modeling_documents (id, workspace_id, title, kind, language, markdown) VALUES ('legacy', ?, 'Legacy', 'prd', 'zh-CN', '# Legacy')").run(model.id);
      documents.listModelingDocuments(model.id);
      documents.readModelingDocument('legacy');
      console.log(JSON.stringify({ workspaceId: model.id, release }));
    `);
    writeFileSync(join(directory, 'source.medol'), source);
    const exportRun = (only) => {
      const result = spawnSync(process.execPath, ['scripts/export-438c-source-docs.mjs', `--db=${database}`,
        `--workspace-db=${workspaceDatabase}`, `--out=${directory}`, `--workspace-id=${initial.workspaceId}`,
        `--model-source=${directory}/source.medol`, ...(only ? [`--only=${only}`] : [])],
      { cwd: new URL('..', import.meta.url), encoding: 'utf8' });
      assert.equal(result.status, 0, result.stderr);
      return readJson(join(directory, 'source-doc-manifest.json')).documents;
    };
    const baseline = (release) => ({ sourceVerified: true, metadata: { source: {
      workspaceId: initial.workspaceId, sha256: sha256(source), versionId: release.id,
      versionNo: release.versionNo, release: release.release
    } } });
    const check = (entries, release, expected) => {
      for (const kind of kinds) {
        const filename = `${kind}.zh-CN.md`;
        assert.equal(sourceAssociation(entries[filename], readFileSync(join(directory, filename)), baseline(release)), expected, kind);
      }
      assert.equal(entries['model-translations.zh-CN.md'].revision, null);
    };
    const documentStatus = (release) => {
      const model = readJson(new URL('./fixtures/438c-traceability/model.json', import.meta.url));
      model.metadata.source = baseline(release).metadata.source;
      writeFileSync(join(directory, 'model.json'), JSON.stringify(model));
      mkdirSync(join(directory, 'manual'), { recursive: true });
      const result = spawnSync(process.execPath, ['scripts/generate-438c-docs.mjs', '--format=markdown', '--only=test-plan',
        `--source-dir=${directory}`, `--manual=${directory}/manual`, `--out=${directory}/generated`,
        `--model=${directory}/model.json`, `--model-source=${directory}/source.medol`,
        `--facts-config=${directory}/absent.json`, `--facts-out=${directory}/absent-facts.json`],
      { cwd: new URL('..', import.meta.url), encoding: 'utf8' });
      assert.equal(result.status, 0, result.stderr);
      return readJson(join(directory, 'generated/document-manifest.json')).documents['test-plan.zh-CN'].status;
    };
    check(exportRun(), initial.release, 'verified');
    assert.equal(documentStatus(initial.release), 'released-model-baseline');
    const next = repositoryRun(`
      const release = workspace.createModelingWorkspaceVersion(${JSON.stringify(initial.workspaceId)}, {
        dsl: ${JSON.stringify(source)}, releaseChannel: 'stable', releaseLabel: 'v1.0.1'
      }).version;
      documents.listModelingDocuments(${JSON.stringify(initial.workspaceId)});
      console.log(JSON.stringify(release));
    `);
    const unchanged = exportRun();
    check(unchanged, initial.release, 'verified');
    check(unchanged, next, 'stale');
    assert.equal(documentStatus(next), 'draft');
    const legacy = exportRun('prd')['prd.zh-CN.md'];
    assert.equal(legacy.sourceHash, null);
    assert.equal(legacy.model, null);
    assert.equal(legacy.revision.workspaceVersionId, null);
    repositoryRun(`
      for (const kind of ${JSON.stringify(kinds)}) {
        documents.createModelingDocument({ workspaceId: ${JSON.stringify(initial.workspaceId)}, title: kind, kind,
          language: 'zh-CN', sourceHash: ${JSON.stringify(legacySourceHash(source))}, markdown: '# ' + kind + '\\n\\nGenerated body.' });
      }
      console.log('null');
    `);
    check(exportRun(), next, 'verified');
    assert.equal(documentStatus(next), 'released-model-baseline');
  } finally { rmSync(directory, { recursive: true, force: true }); }
});
