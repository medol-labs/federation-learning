#!/usr/bin/env node
import { spawnSync } from 'node:child_process';
import { existsSync, mkdirSync, writeFileSync } from 'node:fs';
import { dirname, join, resolve } from 'node:path';
import { fileURLToPath } from 'node:url';

const repositoryRoot = resolve(dirname(fileURLToPath(import.meta.url)), '..');
const args = Object.fromEntries(process.argv.slice(2)
  .filter((arg) => arg.startsWith('--') && arg.includes('='))
  .map((arg) => {
    const [key, ...value] = arg.slice(2).split('=');
    return [key, value.join('=')];
  }));
const database = resolve(repositoryRoot, args.db ?? '../medol/data/medol-documents.sqlite');
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
for (const kind of selected) {
  const rows = query(`SELECT markdown FROM modeling_documents WHERE workspace_id = ${sql(workspaceId)} AND language = ${sql(language)} AND kind = ${sql(kind)} ORDER BY updated_at DESC LIMIT 1`);
  if (!rows[0]?.markdown) fail(`No ${language} document found for ${kind} in workspace ${workspaceId}.`);
  const output = join(outputDirectory, `${kind}.${language}.md`);
  const approvedMarkdown = rows[0].markdown
    .replace(/\n*<!-- em:section id="document\.aiReview" -->[\s\S]*$/, '')
    .trim();
  writeFileSync(output, `${approvedMarkdown}\n`, 'utf8');
  console.log(`[438c] Exported ${kind} to ${output}`);
}

function query(statement) {
  const result = spawnSync('sqlite3', ['-readonly', '-json', database, statement], {
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
