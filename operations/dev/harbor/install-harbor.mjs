#!/usr/bin/env node

import {copyFileSync, existsSync, mkdirSync, readFileSync, rmSync, writeFileSync} from 'node:fs';
import {dirname, resolve} from 'node:path';
import {fileURLToPath} from 'node:url';
import {spawnSync} from 'node:child_process';

const scriptDir = dirname(fileURLToPath(import.meta.url));
loadDotEnv(resolve(scriptDir, '.env'));

const installer = resolve(scriptDir, process.env.HARBOR_INSTALLER ?? '');
const workDir = resolve(scriptDir, '.work');
const extractDir = resolve(workDir, 'installer');
const configTemplate = resolve(scriptDir, 'harbor.yml-example');
const renderedConfig = resolve(scriptDir, 'harbor.yml');

if (!process.env.HARBOR_INSTALLER) {
    fail('Set HARBOR_INSTALLER in .env or the environment. Use the Harbor offline installer tarball.');
}
if (!existsSync(installer)) {
    fail(`Harbor installer was not found: ${installer}`);
}
if (!existsSync(configTemplate)) {
    fail(`Harbor config template was not found: ${configTemplate}`);
}

mkdirSync(workDir, {recursive: true});
rmSync(extractDir, {recursive: true, force: true});
mkdirSync(extractDir, {recursive: true});
renderTemplate(configTemplate, renderedConfig);

run('tar', ['-xzf', installer, '-C', extractDir]);

const harborDir = resolve(extractDir, 'harbor');
if (!existsSync(harborDir)) {
    fail(`Expected extracted Harbor directory was not found: ${harborDir}`);
}

copyFileSync(renderedConfig, resolve(harborDir, 'harbor.yml'));
run('./install.sh', [], harborDir);

console.log('[harbor] installed. Use docker compose in the extracted Harbor directory for lifecycle operations.');

function renderTemplate(source, target) {
    const text = readFileSync(source, 'utf8').replace(/\$\{([A-Z0-9_]+)(:-([^}]*))?}/g, (_, name, _fallbackExpression, fallback) =>
        process.env[name] ?? fallback ?? ''
    );
    writeFileSync(target, text);
}

function loadDotEnv(path) {
    if (!existsSync(path)) return;
    const lines = readFileSync(path, 'utf8').split(/\r?\n/);
    for (const line of lines) {
        const trimmed = line.trim();
        if (!trimmed || trimmed.startsWith('#') || !trimmed.includes('=')) continue;
        const index = trimmed.indexOf('=');
        const key = trimmed.slice(0, index).trim();
        const value = trimmed.slice(index + 1).trim().replace(/^['"]|['"]$/g, '');
        if (key && process.env[key] === undefined) {
            process.env[key] = value;
        }
    }
}

function run(command, args, cwd = scriptDir) {
    console.log(`[harbor] ${command} ${args.join(' ')}`);
    const result = spawnSync(command, args, {cwd, stdio: 'inherit'});
    if (result.status !== 0) {
        process.exit(result.status ?? 1);
    }
}

function fail(message) {
    console.error(`[harbor] ${message}`);
    process.exit(1);
}
