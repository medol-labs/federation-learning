#!/usr/bin/env node
import { spawnSync } from 'node:child_process';
import { existsSync, mkdirSync, readFileSync, writeFileSync } from 'node:fs';
import { dirname, join, resolve } from 'node:path';
import { fileURLToPath } from 'node:url';
import { legacySourceHash, sha256, readJson } from '../docs/document-support/document-traceability.mjs';

const repositoryRoot = resolve(dirname(fileURLToPath(import.meta.url)), '..');
const cliArgs = process.argv.slice(2);
const args = Object.fromEntries(cliArgs
  .map((arg, index) => arg.startsWith('--') && !arg.includes('=') && cliArgs[index + 1] && !cliArgs[index + 1].startsWith('--') ? `${arg}=${cliArgs[index + 1]}` : arg)
  .filter((arg) => arg.startsWith('--') && arg.includes('='))
  .map((arg) => {
    const [key, ...value] = arg.slice(2).split('=');
    return [key, value.join('=')];
  }));
const database = resolve(repositoryRoot, args.db ?? '../medol/data/medol-documents.sqlite');
const workspaceDatabase = resolve(repositoryRoot, args['workspace-db'] ?? '../medol/data/medol.sqlite');
const outputDirectory = resolve(repositoryRoot, args.out ?? 'docs/438c/sources');
const language = args.language ?? 'zh-CN';
const kinds = ['prd', 'software-design', 'database-design', 'test-outline', 'installation-manual', 'user-manual', 'model-translations'];
// PRD is refreshed from the current model; a stored document may be stale.
const selected = args.only ? [args.only] : kinds.filter((kind) => kind !== 'prd');

if (!existsSync(database)) fail(`Document database does not exist: ${database}`);
if (selected.some((kind) => !kinds.includes(kind))) fail(`Unsupported document kind: ${args.only}`);

const workspaceRows = query(`SELECT DISTINCT workspace_id FROM modeling_documents WHERE language = ${sql(language)}`);
const workspaceId = args['workspace-id'] ?? (workspaceRows.length === 1 ? workspaceRows[0].workspace_id : undefined);
if (!workspaceId) fail('Specify --workspace-id when the document database contains multiple workspaces.');

mkdirSync(outputDirectory, { recursive: true });
const manifestPath = join(outputDirectory, 'source-doc-manifest.json');
const manifest = existsSync(manifestPath) ? readJson(manifestPath) : { schemaVersion: 1, documents: {} };
if (manifest.schemaVersion !== 1 || !manifest.documents) fail('Unsupported source document manifest.');
for (const kind of selected) {
  const rows = query(`SELECT * FROM modeling_documents WHERE workspace_id = ${sql(workspaceId)} AND language = ${sql(language)} AND kind = ${sql(kind)} ORDER BY updated_at DESC LIMIT 1`);
  if (!rows[0]?.markdown) fail(`No ${language} document found for ${kind} in workspace ${workspaceId}.`);
  const output = join(outputDirectory, `${kind}.${language}.md`);
  const approvedMarkdown = rows[0].markdown
    .replace(/\n*<!-- em:section id="document\.aiReview" -->[\s\S]*$/, '')
    .trim();
  const exportedMarkdown = kind === 'software-design' && language === 'zh-CN'
    ? approvedMarkdown.split('\n').map((line) =>
      /[\p{Script=Han}]/u.test(line) ? line.replace(/。?\.$/u, '。') : line
    ).join('\n')
    : approvedMarkdown;
  const content = `${exportedMarkdown}\n`;
  writeFileSync(output, content, 'utf8');
  manifest.documents[`${kind}.${language}.md`] = {
    kind, language, documentId: rows[0].id ?? null, workspaceId,
    updatedAt: rows[0].updated_at, sha256: sha256(content),
    ...association(rows[0]),
  };
  console.log(`[438c] Exported ${kind} to ${output}`);
}
manifest.exportedAt = new Date().toISOString();
writeFileSync(manifestPath, `${JSON.stringify(manifest, null, 2)}\n`);

function association(document) {
  const tables = query("SELECT name FROM sqlite_master WHERE type='table'").map((row) => row.name);
  const revisions = document.id && tables.includes('modeling_document_versions')
    ? query(`SELECT * FROM modeling_document_versions WHERE document_id=${sql(document.id)} AND workspace_id=${sql(workspaceId)} ORDER BY rowid DESC`) : [];
  const latest = revisions.sort((left, right) => (right.revision_no ?? 0) - (left.revision_no ?? 0))[0];
  const revision = latest?.markdown === document.markdown
    && (latest.source_hash ?? null) === (document.source_hash ?? null)
    && (latest.title === undefined || latest.title === document.title) ? latest : undefined;
  const catalogAssociation = document.kind === 'model-translations' && document.source_association
    ? JSON.parse(document.source_association) : undefined;
  const sourceAssociation = revision ?? (catalogAssociation?.source_hash === document.source_hash
    ? catalogAssociation : undefined);
  const result = {
    sourceHash: document.source_hash ?? null,
    revision: revision ? { id: revision.id, revisionNo: revision.revision_no ?? null,
      origin: revision.origin ?? 'legacy', createdAt: revision.created_at,
      workspaceVersionId: revision.workspace_version_id ?? null,
      workspaceVersionNo: revision.workspace_version_no ?? null,
      workspaceReleaseLabel: revision.workspace_release_label ?? null,
    } : null,
    model: null,
  };
  const versionDatabase = tables.includes('modeling_workspace_versions') ? database : workspaceDatabase;
  const hasVersions = existsSync(versionDatabase) && query("SELECT name FROM sqlite_master WHERE type='table' AND name='modeling_workspace_versions'", versionDatabase).length;
  const versions = hasVersions
    ? query(`SELECT * FROM modeling_workspace_versions WHERE workspace_id=${sql(workspaceId)}`, versionDatabase) : [];
  const version = sourceAssociation?.workspace_version_id
    ? versions.find((row) => row.id === sourceAssociation.workspace_version_id)
    : undefined;
  if (version && matchesSource(document.source_hash, version.dsl)) {
    result.model = { source: { workspaceId, versionId: version.id, versionNo: version.version_no,
      sha256: sha256(version.dsl), ...(version.release_channel ? { release: {
        channel: version.release_channel, ...(version.release_label ? { label: version.release_label } : {}),
        ...(version.released_at ? { releasedAt: version.released_at } : {}),
      } } : {}) } };
  } else if (!version) {
    // Legacy fingerprints can establish source bytes, never a workspace revision/release.
    const sourcePath = resolve(repositoryRoot, args['model-source'] ?? '.medol/source.medol');
    if (existsSync(sourcePath)) {
      const source = readFileSync(sourcePath, 'utf8');
      if (matchesSource(document.source_hash, source)) result.model = { source: {
        workspaceId, sha256: sha256(source),
        ...(sourceAssociation?.workspace_version_id ? { versionId: sourceAssociation.workspace_version_id } : {}),
        ...(sourceAssociation?.workspace_version_no != null ? { versionNo: sourceAssociation.workspace_version_no } : {}),
      } };
    }
  }
  return result;
}

function matchesSource(hash, source) {
  return typeof source === 'string' && (hash === sha256(source) || hash === legacySourceHash(source));
}

function query(statement, databasePath = database) {
  const result = spawnSync('sqlite3', ['-readonly', '-json', databasePath, statement], {
    encoding: 'utf8',
    maxBuffer: 32 * 1024 * 1024,
  });
  if (result.error || result.status !== 0) fail(result.error?.message ?? result.stderr);
  return result.stdout.trim() ? JSON.parse(result.stdout) : [];
}

function sql(value) {
  return `'${String(value).replaceAll("'", "''")}'`;
}

function fail(message) {
  console.error(`[438c] ${message}`);
  process.exit(1);
}
