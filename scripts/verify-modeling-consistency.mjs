#!/usr/bin/env node
import { spawnSync } from 'node:child_process';
import { existsSync, mkdtempSync, readFileSync, rmSync, writeFileSync } from 'node:fs';
import { tmpdir } from 'node:os';
import { dirname, resolve } from 'node:path';
import { fileURLToPath } from 'node:url';

const repoRoot = resolve(dirname(fileURLToPath(import.meta.url)), '..');
const workspaceRoot = resolve(repoRoot, '..');
const medolRoot = resolve(workspaceRoot, 'medol');
const args = new Set(process.argv.slice(2));
const full = args.has('--full');
const skipFrontend = args.has('--skip-frontend');
const skipBackend = args.has('--skip-backend');

const checks = [];
let failed = false;

function step(name, fn) {
  checks.push({ name, fn });
}

function fail(message) {
  failed = true;
  console.error(`\n[verify] ${message}`);
}

function run(name, command, commandArgs, options = {}) {
  console.log(`[verify] ${name}`);
  const result = spawnSync(command, commandArgs, {
    cwd: options.cwd ?? repoRoot,
    encoding: 'utf8',
    maxBuffer: 128 * 1024 * 1024,
    stdio: options.capture ? ['ignore', 'pipe', 'pipe'] : 'inherit',
    env: { ...process.env, CI: process.env.CI ?? 'true' },
  });

  if (result.status !== 0) {
    const stderr = result.stderr?.trim();
    const stdout = result.stdout?.trim();
    throw new Error(
      [
        `${name} failed with exit code ${result.status}.`,
        stderr ? `stderr:\n${stderr}` : '',
        stdout ? `stdout:\n${stdout}` : '',
      ]
        .filter(Boolean)
        .join('\n'),
    );
  }

  return result.stdout ?? '';
}

function readText(relativePath) {
  return readFileSync(resolve(repoRoot, relativePath), 'utf8');
}

function assertIncludes(file, needle, description = needle) {
  const text = readText(file);
  if (!text.includes(needle)) {
    throw new Error(`${file} is missing ${description}.`);
  }
}

function assertNotMatches(file, pattern, description = String(pattern)) {
  const text = readText(file);
  if (pattern.test(text)) {
    throw new Error(`${file} still contains forbidden ${description}.`);
  }
}

function parseJsonFile(relativePath) {
  return JSON.parse(readText(relativePath));
}

function stableStringify(value) {
  if (Array.isArray(value)) {
    return `[${value.map(stableStringify).join(',')}]`;
  }
  if (value && typeof value === 'object') {
    return `{${Object.keys(value)
      .sort()
      .map((key) => `${JSON.stringify(key)}:${stableStringify(value[key])}`)
      .join(',')}}`;
  }
  return JSON.stringify(value);
}

function medolCli(script, inputPath, options = {}) {
  return run(
    `${script} ${inputPath}`,
    'npm',
    ['run', '--silent', script, '--', inputPath],
    { cwd: medolRoot, capture: options.capture },
  );
}

step('MEDOL source parses to model', () => {
  const output = medolCli('medol:to-model', resolve(repoRoot, '.medol/source.medol'), {
    capture: true,
  });
  JSON.parse(output);
});

step('Example federation-learning MEDOL parses to model', () => {
  const output = medolCli('medol:to-model', resolve(medolRoot, 'examples/fl/federation-learning.medol'), {
    capture: true,
  });
  JSON.parse(output);
});

step('Generated CodegenModel is up to date', () => {
  const output = medolCli('medol:to-codegen-model', resolve(repoRoot, '.medol/source.medol'), {
    capture: true,
  });
  const tmp = mkdtempSync(resolve(tmpdir(), 'fl-codegen-model-'));
  try {
    const generatedPath = resolve(tmp, 'codegen-model.json');
    writeFileSync(generatedPath, output);
    const generated = JSON.parse(output);
    const committed = parseJsonFile('.medol/codegen-model.json');
    if (stableStringify(generated) !== stableStringify(committed)) {
      throw new Error('.medol/codegen-model.json is not synchronized with .medol/source.medol.');
    }
  } finally {
    rmSync(tmp, { recursive: true, force: true });
  }
});

step('Dictionary seed remains valid and includes personalized storage locations', () => {
  const dictionaries = parseJsonFile('dictionary-init/dictionaries.json');
  const dictionary = dictionaries.dictionaries?.find(
    (item) => item.dictionaryCode === 'PERSONALIZED_MODEL_STORAGE_LOCATION',
  );
  if (!dictionary) {
    throw new Error('PERSONALIZED_MODEL_STORAGE_LOCATION dictionary is missing.');
  }
  const values = new Set(dictionary.values?.map((item) => item.valueCode));
  for (const value of ['RUNTIME_LOCAL', 'PLATFORM_MANAGED', 'EXTERNAL_REGISTRY']) {
    if (!values.has(value)) {
      throw new Error(`PERSONALIZED_MODEL_STORAGE_LOCATION is missing value ${value}.`);
    }
  }
});

step('Personalized federation model storage is explicit and policy-derived', () => {
  assertIncludes(
    '.medol/source.medol',
    'personalizedModelStorageLocation: String dictionary "PERSONALIZED_MODEL_STORAGE_LOCATION"',
    'PersonalizedModelResultRecorded storage location dictionary field',
  );
  assertIncludes(
    '.medol/source.medol',
    'personalizedModelStorageLocation: String derived',
    'derived personalizedModelStorageLocation command field',
  );
  assertIncludes(
    '.medol/source.medol',
    'platformManagedArtifactRef: String? derived',
    'derived platformManagedArtifactRef command field',
  );
  assertIncludes(
    '.medol/source.medol',
    'Default personalized model storage to RUNTIME_LOCAL',
    'runtime-local personalized model storage rule',
  );
  assertNotMatches(
    '.medol/source.medol',
    /personalizedModelStorageLocation: String generated technical/,
    'generated technical personalizedModelStorageLocation',
  );
  assertNotMatches(
    '.medol/source.medol',
    /platformManagedArtifactRef: String\? generated technical/,
    'generated technical platformManagedArtifactRef',
  );
});

step('Runtime monitoring and governance modeling stays tightened', () => {
  assertIncludes(
    '.medol/source.medol',
    'sync readmodel AgentRuntimeIdentityCatalog[] from RuntimeGovernance.RuntimeIdentityCatalog',
    'RuntimeGovernance source for agent runtime identity catalog',
  );
  assertNotMatches(
    '.medol/source.medol',
    /from RuntimeProvisioning\.RuntimeIdentityCatalog/,
    'RuntimeProvisioning runtime identity sync source',
  );
  assertNotMatches(
    '.medol/source.medol',
    /readmodel todo RuntimeTelemetryLatest/,
    'todo RuntimeTelemetryLatest readmodel',
  );
  assertNotMatches(
    '.medol/source.medol',
    /readmodel todo RuntimeNodeResourceLatest/,
    'todo RuntimeNodeResourceLatest readmodel',
  );
});

step('Removed ModelLifecycle surfaces do not return in core files', () => {
  for (const file of [
    'README.md',
    '.medol/source.medol',
    '.medol/codegen-model.json',
    'dictionary-init/dictionaries.json',
    'federation-learning-console/src/contexts/i18n/messages.ts',
  ]) {
    assertNotMatches(file, /ModelLifecycle|Model Lifecycle|modellifecycle/, 'ModelLifecycle residue');
    assertNotMatches(file, /MODEL_RELEASE_CHANNEL|MODEL_PRODUCTION_STAGE/, 'removed model lifecycle dictionary');
  }
});

step('Frontend static i18n includes personalized storage labels', () => {
  for (const value of [
    'dictionaries.PERSONALIZED_MODEL_STORAGE_LOCATION.RUNTIME_LOCAL',
    'dictionaries.PERSONALIZED_MODEL_STORAGE_LOCATION.PLATFORM_MANAGED',
    'dictionaries.PERSONALIZED_MODEL_STORAGE_LOCATION.EXTERNAL_REGISTRY',
  ]) {
    assertIncludes('federation-learning-console/src/contexts/i18n/messages.ts', value);
  }
});

if (full && !skipFrontend) {
  step('Federation Learning Console typecheck', () => {
    run('pnpm run typecheck', 'pnpm', ['run', 'typecheck'], {
      cwd: resolve(repoRoot, 'federation-learning-console'),
    });
  });
}

if (full && !skipBackend) {
  step('Backend reactor test-compile', () => {
    const mvnw = resolve(repoRoot, 'federation-learning-platform/mvnw');
    if (!existsSync(mvnw)) {
      throw new Error(`Missing Maven wrapper: ${mvnw}`);
    }
    run('./mvnw -q test-compile', './mvnw', ['-q', 'test-compile'], {
      cwd: resolve(repoRoot, 'federation-learning-platform'),
    });
  });
}

for (const check of checks) {
  try {
    await check.fn();
  } catch (error) {
    fail(`${check.name}: ${error.message}`);
  }
}

if (failed) {
  console.error('\n[verify] FAILED');
  process.exit(1);
}

console.log('\n[verify] OK');
