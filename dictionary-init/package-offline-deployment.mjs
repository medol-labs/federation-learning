#!/usr/bin/env node

import {copyFileSync, existsSync, mkdirSync, rmSync, statSync, writeFileSync} from 'node:fs';
import {dirname, join, relative, resolve} from 'node:path';
import {fileURLToPath} from 'node:url';
import {spawnSync} from 'node:child_process';

const scriptDir = dirname(fileURLToPath(import.meta.url));
const federationRoot = resolve(scriptDir, '..');
const defaults = {
    backendRoot: resolve(federationRoot, 'federation-learning-platform'),
    consoleRoot: resolve(federationRoot, 'federation-learning-console'),
    runtimeEngineRoot: resolve(federationRoot, 'federation-learning-runtime-engine')
};

const args = parseArgs(process.argv.slice(2));
const platform = String(args.platform ?? process.env.DOCKER_DEFAULT_PLATFORM ?? 'linux/amd64');
const outputRoot = resolve(scriptDir, args.output ?? 'dist/offline-deployment-package');
const archivePath = resolve(scriptDir, args.archive ?? `${outputRoot}.tar.gz`);
const dryRun = Boolean(args['dry-run']);
const clean = args.clean !== false && args.clean !== 'false';
const skipBackend = Boolean(args['skip-backend']);
const skipConsole = Boolean(args['skip-console']);
const skipRuntimeEngine = Boolean(args['skip-runtime-engine']);
const skipDependencyImages = Boolean(args['skip-dependency-images']);
const skipArchive = Boolean(args['skip-archive']);

const packageDirs = {
    images: join(outputRoot, 'images'),
    compose: join(outputRoot, 'compose')
};

const manifest = {
    generatedAt: new Date().toISOString(),
    platform,
    outputRoot,
    archivePath,
    projects: {
        backend: defaults.backendRoot,
        console: defaults.consoleRoot,
        runtimeEngine: defaults.runtimeEngineRoot
    },
    artifacts: []
};

main();

function main() {
    console.log(`[offline-package] platform: ${platform}`);
    console.log(`[offline-package] output: ${outputRoot}`);
    console.log(`[offline-package] archive: ${archivePath}`);

    prepareOutput();

    if (!skipBackend) {
        packageBackend();
    }
    if (!skipConsole) {
        packageConsole();
    }
    if (!skipRuntimeEngine) {
        packageRuntimeEngine();
    }

    if (!skipArchive) {
        manifest.artifacts.push({type: 'archive', name: 'offline-deployment-package', path: archivePath});
    }

    writeManifest();

    if (!skipArchive) {
        createArchive();
    }

    console.log('[offline-package] done');
}

function prepareOutput() {
    if (dryRun) {
        console.log(`[offline-package] would prepare ${outputRoot}`);
        return;
    }
    if (clean && existsSync(outputRoot)) {
        rmSync(outputRoot, {recursive: true, force: true});
    }
    mkdirSync(packageDirs.images, {recursive: true});
    mkdirSync(packageDirs.compose, {recursive: true});
}

function packageBackend() {
    ensureProject(defaults.backendRoot, 'backend');
    const backendImageTar = join(packageDirs.images, 'backend-images.tar');
    runNode(defaults.backendRoot, ['scripts/image-bundle.mjs', 'all', '--platform', platform, '--output', backendImageTar]);
    manifest.artifacts.push({type: 'imageArchive', name: 'backend', path: relative(outputRoot, backendImageTar)});

    if (!skipDependencyImages) {
        const dependencyImageTar = join(packageDirs.images, 'dependency-images.tar');
        runNode(defaults.backendRoot, ['scripts/export-dependency-images.mjs', '--platform', platform, '--output', dependencyImageTar]);
        manifest.artifacts.push({type: 'imageArchive', name: 'dependencies', path: relative(outputRoot, dependencyImageTar)});
    }

    const backendComposeDir = join(packageDirs.compose, 'backend');
    runNode(defaults.backendRoot, ['scripts/collect-deployment-compose-files.mjs', '--output', backendComposeDir, '--clean']);
    manifest.artifacts.push({type: 'composeBundle', name: 'backend', path: relative(outputRoot, backendComposeDir)});
}

function packageConsole() {
    ensureProject(defaults.consoleRoot, 'console');
    const consoleImageTar = join(packageDirs.images, 'console-images.tar');
    runNode(defaults.consoleRoot, ['scripts/image-bundle.mjs', 'all', '--platform', platform, '--output', consoleImageTar]);
    manifest.artifacts.push({type: 'imageArchive', name: 'console', path: relative(outputRoot, consoleImageTar)});

    const consoleComposeDir = join(packageDirs.compose, 'console');
    collectFiles(defaults.consoleRoot, consoleComposeDir, [
        'docker-compose.yml',
        '.env-example'
    ]);
    manifest.artifacts.push({type: 'composeBundle', name: 'console', path: relative(outputRoot, consoleComposeDir)});
}

function packageRuntimeEngine() {
    ensureProject(defaults.runtimeEngineRoot, 'runtime-engine');
    const runtimeEngineImageTar = join(packageDirs.images, 'runtime-engine-images.tar');
    runNode(defaults.runtimeEngineRoot, ['scripts/image-bundle.mjs', 'all', '--platform', platform, '--output', runtimeEngineImageTar]);
    manifest.artifacts.push({type: 'imageArchive', name: 'runtime-engine', path: relative(outputRoot, runtimeEngineImageTar)});

    const runtimeEngineComposeDir = join(packageDirs.compose, 'runtime-engine');
    collectFiles(defaults.runtimeEngineRoot, runtimeEngineComposeDir, [
        'docker-compose.yml',
        '.env-example'
    ]);
    manifest.artifacts.push({type: 'composeBundle', name: 'runtime-engine', path: relative(outputRoot, runtimeEngineComposeDir)});
}

function writeManifest() {
    const path = join(outputRoot, 'manifest.json');
    if (dryRun) {
        console.log(`[offline-package] would write ${path}`);
        return;
    }
    writeFileSync(path, `${JSON.stringify(manifest, null, 2)}\n`);
}

function createArchive() {
    const archiveParent = dirname(archivePath);
    const outputParent = dirname(outputRoot);
    const outputName = outputRoot.split(/[\\/]/).pop();
    if (!outputName) {
        fail(`Invalid output path: ${outputRoot}`);
    }
    if (!dryRun) {
        mkdirSync(archiveParent, {recursive: true});
    }
    run('tar', ['-czf', archivePath, '-C', outputParent, outputName], scriptDir);
}

function collectFiles(sourceRoot, targetRoot, files) {
    for (const file of files) {
        const source = join(sourceRoot, file);
        if (!existsSync(source)) {
            console.warn(`[offline-package] skipped missing file: ${relative(sourceRoot, source)}`);
            continue;
        }
        if (!statSync(source).isFile()) {
            console.warn(`[offline-package] skipped non-file: ${relative(sourceRoot, source)}`);
            continue;
        }
        const target = join(targetRoot, file);
        console.log(`[offline-package] copy ${source} -> ${target}`);
        if (dryRun) continue;
        mkdirSync(dirname(target), {recursive: true});
        copyFileSync(source, target);
    }
}

function ensureProject(root, label) {
    if (!existsSync(root)) {
        fail(`${label} project was not found: ${root}`);
    }
}

function runNode(cwd, commandArgs) {
    run(process.execPath, commandArgs, cwd);
}

function run(commandName, commandArgs, cwd) {
    console.log(`[offline-package] ${relative(scriptDir, cwd) || '.'}$ ${commandName} ${commandArgs.join(' ')}`);
    if (dryRun) return;
    const result = spawnSync(commandName, commandArgs, {
        cwd,
        stdio: 'inherit',
        shell: process.platform === 'win32'
    });
    if (result.status !== 0) {
        process.exit(result.status ?? 1);
    }
}

function parseArgs(argv) {
    const result = {};
    for (let index = 0; index < argv.length; index += 1) {
        const item = argv[index];
        if (!item.startsWith('--')) continue;
        const key = item.slice(2);
        const next = argv[index + 1];
        if (!next || next.startsWith('--')) {
            result[key] = true;
            continue;
        }
        result[key] = next;
        index += 1;
    }
    return result;
}

function fail(message) {
    console.error(`[offline-package] ${message}`);
    process.exit(1);
}
