#!/usr/bin/env node

import {copyFileSync, existsSync, mkdirSync, readdirSync, readFileSync, rmSync, statSync, writeFileSync} from 'node:fs';
import {dirname, join, relative, resolve} from 'node:path';
import {fileURLToPath} from 'node:url';
import {spawnSync} from 'node:child_process';

const scriptDir = dirname(fileURLToPath(import.meta.url));
const args = parseArgs(process.argv.slice(2));
const command = args._[0] ?? 'help';
const monorepoRoot = resolve(scriptDir, args.root ?? process.env.MONOREPO_ROOT ?? '../..');
const platform = String(args.platform ?? process.env.DOCKER_DEFAULT_PLATFORM ?? 'linux/amd64');
const imagePrefix = String(args.prefix ?? process.env.DOCKER_IMAGE_PREFIX ?? 'medol').replace(/\/+$/g, '');
const imageVersion = String(args.version ?? process.env.IMAGE_VERSION ?? '0.0.1-SNAPSHOT');
const outputRoot = resolve(scriptDir, args.output ?? process.env.IMAGE_BUNDLE_OUTPUT ?? '.work/image-bundle');
const packageArchive = resolve(scriptDir, args.archive ?? process.env.IMAGE_BUNDLE_ARCHIVE ?? `${outputRoot}.tar.gz`);
const dryRun = Boolean(args['dry-run']);
const clean = args.clean !== false && args.clean !== 'false';
const skipBackend = Boolean(args['skip-backend']);
const skipConsole = Boolean(args['skip-console']);
const skipRuntimeEngine = Boolean(args['skip-runtime-engine']);
const skipDependencies = Boolean(args['skip-dependencies']);
const projectRoots = resolveProjectRoots();
const appArchives = {
    backend: join(outputRoot, 'images', 'backend-images.tar'),
    console: join(outputRoot, 'images', 'console-images.tar'),
    runtimeEngine: join(outputRoot, 'images', 'runtime-engine-images.tar')
};
const dependencyArchive = join(outputRoot, 'images', 'dependency-images.tar');

switch (command) {
    case 'list':
        printPlan();
        break;
    case 'build':
        buildImages();
        break;
    case 'export':
        prepareOutput();
        exportImages();
        break;
    case 'import':
        importImages();
        break;
    case 'push':
    case 'push-registry':
        pushImages();
        break;
    case 'pull-dependencies':
    case 'pull':
    case 'download':
        pullDependencyImages();
        break;
    case 'export-dependencies':
        prepareOutput();
        exportDependencyImages();
        break;
    case 'import-dependencies':
        importDependencyImages();
        break;
    case 'push-dependencies':
        pushDependencyImages();
        break;
    case 'package':
        packageImages();
        break;
    case 'all':
        packageImages();
        break;
    default:
        printUsage();
        process.exit(command === 'help' || command === '--help' || command === '-h' ? 0 : 1);
}

function packageImages() {
    prepareOutput();
    buildImages();
    exportImages();
    if (!skipDependencies) {
        pullDependencyImages();
        exportDependencyImages();
    }
    collectComposeFiles();
    writeManifest();
    createPackageArchive();
}

function buildImages() {
    for (const project of selectedProjects()) {
        runNode(project.root, ['scripts/image-bundle.mjs', 'build', '--platform', platform, '--prefix', imagePrefix, '--version', imageVersion]);
    }
}

function exportImages() {
    for (const project of selectedProjects()) {
        runNode(project.root, [
            'scripts/image-bundle.mjs',
            'export',
            '--platform',
            platform,
            '--prefix',
            imagePrefix,
            '--version',
            imageVersion,
            '--output',
            appArchives[project.key]
        ]);
    }
}

function importImages() {
    for (const project of selectedProjects()) {
        runNode(project.root, ['scripts/image-bundle.mjs', 'import', '--file', appArchives[project.key]]);
    }
    if (!skipDependencies) importDependencyImages();
}

function pushImages() {
    for (const project of selectedProjects()) {
        runNode(project.root, ['scripts/image-bundle.mjs', 'push', '--platform', platform, '--prefix', imagePrefix, '--version', imageVersion]);
    }
}

function pullDependencyImages() {
    if (skipDependencies || !projectRoots.backend) return;
    runNode(projectRoots.backend, ['scripts/dependency-images.mjs', 'pull', '--platform', platform]);
}

function exportDependencyImages() {
    if (skipDependencies || !projectRoots.backend) return;
    runNode(projectRoots.backend, ['scripts/dependency-images.mjs', 'export', '--platform', platform, '--output', dependencyArchive]);
}

function importDependencyImages() {
    if (skipDependencies) return;
    if (!dryRun && !existsSync(dependencyArchive)) {
        fail(`Dependency image archive was not found: ${dependencyArchive}`);
    }
    run('docker', ['load', '-i', dependencyArchive], monorepoRoot);
}

function pushDependencyImages() {
    if (skipDependencies) return;
    for (const image of infrastructureImages()) {
        const target = dependencyRegistryImage(image);
        run('docker', ['tag', image, target], monorepoRoot);
        run('docker', ['push', target], monorepoRoot);
    }
}

function collectComposeFiles() {
    const composeRoot = join(outputRoot, 'compose');
    if (projectRoots.backend && existsSync(join(projectRoots.backend, 'scripts/collect-deployment-compose-files.mjs'))) {
        runNode(projectRoots.backend, [
            'scripts/collect-deployment-compose-files.mjs',
            '--output',
            join(composeRoot, 'backend'),
            '--clean'
        ]);
    } else {
        copyKnownFiles(projectRoots.backend, join(composeRoot, 'backend'), ['docker-compose.yml', '.env-example']);
    }
    copyKnownFiles(projectRoots.console, join(composeRoot, 'console'), ['docker-compose.yml', '.env-example']);
    copyKnownFiles(projectRoots.runtimeEngine, join(composeRoot, 'runtime-engine'), ['docker-compose.yml', '.env-example']);
}

function copyKnownFiles(sourceRoot, targetRoot, files) {
    if (!sourceRoot) return;
    for (const file of files) {
        const source = join(sourceRoot, file);
        if (!existsSync(source) || !statSync(source).isFile()) continue;
        const target = join(targetRoot, file);
        console.log(`[operations-images] copy ${relative(monorepoRoot, source)} -> ${relative(monorepoRoot, target)}`);
        if (dryRun) continue;
        mkdirSync(dirname(target), {recursive: true});
        copyFileSync(source, target);
    }
}

function writeManifest() {
    const manifest = {
        generatedAt: new Date().toISOString(),
        environment: 'dev',
        platform,
        imagePrefix,
        imageVersion,
        monorepoRoot,
        applications: [
        "console",
        "federation-learning-support",
        "federation-learning-platform",
        "federation-learning-runtime-agent"
],
        infrastructureImages: [
        "postgres:16",
        "umadb/umadb:0.7.8",
        "apache/apisix:3.13.0-debian"
],
        projects: Object.fromEntries(selectedProjects().map((project) => [project.key, project.root])),
        archives: {
            ...Object.fromEntries(selectedProjects().map((project) => [project.key, appArchives[project.key]])),
            ...(skipDependencies ? {} : {dependencies: dependencyArchive}),
            package: packageArchive
        }
    };
    const manifestPath = join(outputRoot, 'manifest.json');
    console.log(`[operations-images] write ${manifestPath}`);
    if (dryRun) return;
    mkdirSync(outputRoot, {recursive: true});
    writeFileSync(manifestPath, `${JSON.stringify(manifest, null, 2)}\n`);
}

function createPackageArchive() {
    if (args['skip-archive']) return;
    const outputParent = dirname(outputRoot);
    const outputName = outputRoot.split(/[\\/]/).pop();
    if (!outputName) fail(`Invalid output path: ${outputRoot}`);
    run('tar', ['-czf', packageArchive, '-C', outputParent, outputName], scriptDir);
}

function prepareOutput() {
    if (dryRun) {
        console.log(`[operations-images] would prepare ${outputRoot}`);
        return;
    }
    if (clean && existsSync(outputRoot)) {
        rmSync(outputRoot, {recursive: true, force: true});
    }
    mkdirSync(join(outputRoot, 'images'), {recursive: true});
    mkdirSync(join(outputRoot, 'compose'), {recursive: true});
}

function selectedProjects() {
    return [
        projectRoots.backend && !skipBackend ? {key: 'backend', root: projectRoots.backend} : undefined,
        projectRoots.console && !skipConsole ? {key: 'console', root: projectRoots.console} : undefined,
        projectRoots.runtimeEngine && !skipRuntimeEngine ? {key: 'runtimeEngine', root: projectRoots.runtimeEngine} : undefined
    ].filter(Boolean);
}

function resolveProjectRoots() {
    return {
        backend: explicitRoot('backend-root', 'BACKEND_ROOT') ?? findProjectRoot(['federation-learning-platform', 'backend'], isBackendProject),
        console: explicitRoot('console-root', 'CONSOLE_ROOT') ?? findProjectRoot(['federation-learning-console', 'console', 'frontend'], isConsoleProject),
        runtimeEngine: explicitRoot('runtime-engine-root', 'RUNTIME_ENGINE_ROOT') ?? findProjectRoot(['federation-learning-runtime-engine', 'runtime-engine'], isRuntimeEngineProject)
    };
}

function explicitRoot(argName, envName) {
    const value = args[argName] ?? process.env[envName];
    if (!value) return undefined;
    const root = resolve(monorepoRoot, value);
    return existsSync(root) ? root : fail(`Configured ${argName} does not exist: ${root}`);
}

function findProjectRoot(preferredNames, predicate) {
    for (const name of preferredNames) {
        const root = resolve(monorepoRoot, name);
        if (existsSync(root) && predicate(root)) return root;
    }
    for (const entry of readdirSync(monorepoRoot, {withFileTypes: true})) {
        if (!entry.isDirectory() || entry.name.startsWith('.') || ['node_modules', 'operations', 'volumes', 'tmp'].includes(entry.name)) continue;
        const root = join(monorepoRoot, entry.name);
        if (predicate(root)) return root;
    }
    return undefined;
}

function isBackendProject(root) {
    return existsSync(join(root, 'mvnw')) && existsSync(join(root, 'scripts/image-bundle.mjs'));
}

function isConsoleProject(root) {
    return existsSync(join(root, 'package.json')) && existsSync(join(root, 'Dockerfile')) && existsSync(join(root, 'scripts/image-bundle.mjs'));
}

function isRuntimeEngineProject(root) {
    return existsSync(join(root, 'docker/pytorch/Dockerfile')) && existsSync(join(root, 'scripts/image-bundle.mjs'));
}

function printPlan() {
    console.log('[operations-images] monorepo:', monorepoRoot);
    console.log('[operations-images] environment:', 'dev');
    console.log('[operations-images] platform:', platform);
    console.log('[operations-images] image prefix:', imagePrefix);
    console.log('[operations-images] image version:', imageVersion);
    console.log('[operations-images] output:', outputRoot);
    console.log('[operations-images] package:', packageArchive);
    console.log('[operations-images] projects:');
    for (const project of selectedProjects()) {
        console.log(`  - ${project.key}: ${relative(monorepoRoot, project.root) || '.'}`);
    }
    if (!skipDependencies && projectRoots.backend) {
        console.log('  - dependencies: backend compose infrastructure images');
    }
    const dependencyTargets = infrastructureImages().map((image) => `${image} -> ${dependencyRegistryImage(image)}`);
    if (dependencyTargets.length > 0) {
        console.log('[operations-images] dependency registry targets:');
        for (const target of dependencyTargets) {
            console.log(`  - ${target}`);
        }
    }
}

function infrastructureImages() {
    return [
        "postgres:16",
        "umadb/umadb:0.7.8",
        "apache/apisix:3.13.0-debian"
];
}

function dependencyRegistryImage(image) {
    const { repository, tag } = splitImage(image);
    return `${imagePrefix}/dependencies/${repository}:${tag}`;
}

function splitImage(image) {
    const parts = String(image).split('/');
    if (parts.length > 1 && (parts[0]?.includes('.') || parts[0]?.includes(':') || parts[0] === 'localhost')) {
        parts.shift();
    }
    const last = parts.pop() ?? 'image';
    const tagIndex = last.lastIndexOf(':');
    const name = tagIndex >= 0 ? last.slice(0, tagIndex) : last;
    const tag = tagIndex >= 0 ? last.slice(tagIndex + 1) : 'latest';
    const repository = [...parts, name].join('/') || name;
    return {
        repository: repository.includes('/') ? repository : `library/${repository}`,
        tag
    };
}

function runNode(cwd, commandArgs) {
    run(process.execPath, commandArgs, cwd);
}

function run(commandName, commandArgs, cwd) {
    console.log(`[operations-images] ${relative(monorepoRoot, cwd) || '.'}$ ${commandName} ${commandArgs.join(' ')}`);
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
    const result = {_: []};
    for (let index = 0; index < argv.length; index += 1) {
        const arg = argv[index];
        if (!arg.startsWith('--')) {
            result._.push(arg);
            continue;
        }
        const key = arg.slice(2);
        const next = argv[index + 1];
        const value = !next || next.startsWith('--') ? true : next;
        result[key] = value;
        if (value !== true) index += 1;
    }
    return result;
}

function printUsage() {
    console.log(`Usage:
  node operations/dev/images.mjs list [options]
  node operations/dev/images.mjs build [options]
  node operations/dev/images.mjs export [options]
  node operations/dev/images.mjs import [options]
  node operations/dev/images.mjs push --prefix <registry/namespace> [options]
  node operations/dev/images.mjs pull-dependencies [options]
  node operations/dev/images.mjs export-dependencies [options]
  node operations/dev/images.mjs import-dependencies [options]
  node operations/dev/images.mjs push-dependencies --prefix <registry/namespace> [options]
  node operations/dev/images.mjs package [options]
  node operations/dev/images.mjs all [options]

Commands:
  build                Build backend, frontend, and runtime-engine images.
  export               Save application images into per-project tar archives.
  import               Load application and dependency image archives.
  push                 Push application images to --prefix / DOCKER_IMAGE_PREFIX.
  pull-dependencies    Pull infrastructure images discovered from backend compose files.
  export-dependencies  Save infrastructure images into dependency-images.tar.
  import-dependencies  Load dependency-images.tar.
  push-dependencies    Retag known infrastructure images under <prefix>/dependencies and push them.
  package, all         Build, pull dependencies, export images, collect compose files, and tar the bundle.

Options:
  --prefix <name>                 Image prefix or registry namespace. Defaults to DOCKER_IMAGE_PREFIX or medol.
  --version <tag>                 Image tag. Defaults to IMAGE_VERSION or 0.0.1-SNAPSHOT.
  --platform <os/arch>            Docker platform. Defaults to DOCKER_DEFAULT_PLATFORM or linux/amd64.
  --output <dir>                  Bundle directory. Defaults to .work/image-bundle beside this script.
  --archive <file>                Package archive. Defaults to <output>.tar.gz.
  --root <dir>                    Monorepo root. Defaults to ../.. from this operations environment.
  --backend-root <dir>            Override backend project root.
  --console-root <dir>            Override frontend project root.
  --runtime-engine-root <dir>     Override runtime-engine project root.
  --skip-backend                  Skip backend image operations.
  --skip-console                  Skip frontend image operations.
  --skip-runtime-engine           Skip runtime-engine image operations.
  --skip-dependencies             Skip infrastructure dependency image operations.
  --skip-archive                  Leave package directory unpacked.
  --dry-run                       Print commands without running them.`);
}

function fail(message) {
    console.error(`[operations-images] ${message}`);
    process.exit(1);
}
