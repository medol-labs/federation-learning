import assert from 'node:assert/strict';
import { spawnSync } from 'node:child_process';
import { mkdtempSync, readFileSync, rmSync, writeFileSync } from 'node:fs';
import { tmpdir } from 'node:os';
import { join } from 'node:path';
import test from 'node:test';

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
      `--out=${directory}`, '--workspace-id=demo'
    ], { cwd: new URL('..', import.meta.url), encoding: 'utf8' });
    assert.equal(result.status, 0, result.stderr);
    assert.equal(readFileSync(prdPath, 'utf8'), '# 最新 PRD\n');
    assert.equal(readFileSync(join(directory, 'software-design.zh-CN.md'), 'utf8'), '# 正文\n\n已确认。\n');
    assert.equal(readFileSync(join(directory, 'database-design.zh-CN.md'), 'utf8'), '# 正文\n\n已确认。.\n');
  } finally {
    rmSync(directory, { recursive: true, force: true });
  }
});
