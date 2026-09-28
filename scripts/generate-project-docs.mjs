#!/usr/bin/env node
import { spawnSync } from 'node:child_process';
import { existsSync } from 'node:fs';
import { homedir } from 'node:os';
import { dirname, resolve } from 'node:path';
import { fileURLToPath } from 'node:url';

const scriptDirectory = dirname(fileURLToPath(import.meta.url));
const repositoryRoot = resolve(scriptDirectory, '..');
const workspaceRoot = resolve(repositoryRoot, '..');
const medolRoot = resolve(workspaceRoot, 'medol');
const bundledPython = resolve(
  homedir(),
  '.cache/codex-runtimes/codex-primary-runtime/dependencies/python/bin/python3'
);
const generator = resolve(
  repositoryRoot,
  'docs/document-pack/federation-learning/generate.ts'
);
const tsxRegister = resolve(medolRoot, 'node_modules/tsx/dist/esm/index.mjs');

if (!existsSync(generator)) {
  console.error(`Project document generator not found: ${generator}`);
  process.exit(1);
}

if (!existsSync(tsxRegister)) {
  console.error(`tsx runtime is not available: ${tsxRegister}`);
  console.error('Run npm install in ../medol before generating project documents.');
  process.exit(1);
}

const result = spawnSync(
  process.execPath,
  ['--import', tsxRegister, generator, ...process.argv.slice(2)],
  {
    cwd: medolRoot,
    env: {
      ...process.env,
      PYTHON: process.env.PYTHON ?? (existsSync(bundledPython) ? bundledPython : 'python3')
    },
    stdio: 'inherit'
  }
);

process.exit(result.status ?? 1);
