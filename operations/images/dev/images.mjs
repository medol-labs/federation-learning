#!/usr/bin/env node

import {copyFileSync, existsSync, mkdirSync, readdirSync, readFileSync, rmSync, statSync, writeFileSync} from 'node:fs';
import {basename, dirname, join, relative, resolve} from 'node:path';
import {fileURLToPath} from 'node:url';
import {spawnSync} from 'node:child_process';
import {resolveImageConfig} from './image-config.mjs';
import {prepareRelease} from './release-runtime.mjs';
import {verifyArchiveManifest, verifyLoadedImages} from './image-archive-runtime.mjs';

const scriptDir = dirname(fileURLToPath(import.meta.url));
const args = parseArgs(process.argv.slice(2));
const command = args._[0] ?? 'help';
const monorepoRoot = resolve(scriptDir, args.root ?? process.env.MONOREPO_ROOT ?? '../../..');
const platform = String(args.platform ?? process.env.DOCKER_DEFAULT_PLATFORM ?? '').trim();
const dependencyPlatform = platform || hostDockerPlatform();
const imageConfig = resolveImageConfig(scriptDir, {prefix: args.prefix, version: args.version, mode: args['image-source'], operation: command});
const imagePrefix = imageConfig.buildPrefix;
const dependencyImagePrefix = String(args['dependency-prefix'] ?? process.env.DEPENDENCY_IMAGE_PREFIX ?? defaultDependencyImagePrefix(imagePrefix)).replace(/\/+$/g, '');
const imageVersion = imageConfig.version;
const outputRoot = resolve(scriptDir, args.output ?? process.env.IMAGE_BUNDLE_OUTPUT ?? '.work/image-bundle');
const packageArchive = resolve(scriptDir, args.archive ?? process.env.IMAGE_BUNDLE_ARCHIVE ?? `${outputRoot}.tar.gz`);
const dryRun = Boolean(args['dry-run']);
const release = ['build', 'export', 'push', 'push-registry', 'package', 'all'].includes(command)
    ? prepareRelease({root: monorepoRoot, version: imageVersion, target: 'operations',
        operation: command === 'push-registry' ? 'push' : ['all', 'package'].includes(command) ? 'build' : command, dryRun}) : undefined;
const clean = args.clean !== false && args.clean !== 'false';
const skipBackend = Boolean(args['skip-backend']);
const skipConsole = Boolean(args['skip-console']);
const skipDependencies = Boolean(args['skip-dependencies']);
const fastBuild = flagEnabled(args['fast-build'], process.env.OPERATIONS_FAST_IMAGE_BUILD);
const serviceIncludes = serviceFilterValues('service', 'include-service', 'only-service');
const serviceExcludes = serviceFilterValues('exclude-service', 'skip-service');
const applicationNames = [
    "federation-learning-console",
    "federation-learning-participant-console",
    "federation-learning-platform",
    "federation-learning-runtime-agent",
    "federation-learning-support"
];
const backendModuleNames = [
    "federation-learning-platform",
    "federation-learning-runtime-agent",
    "federation-learning-support"
];
const frontendApplications = [
    {
        "name": "federation-learning-console",
        "artifactName": "federation-learning-console",
        "frontendApplicationName": "FederationLearningConsole"
    },
    {
        "name": "federation-learning-participant-console",
        "artifactName": "federation-learning-participant-console",
        "frontendApplicationName": "FederationLearningParticipantConsole",
        "bundledWithBackend": "federation-learning-runtime-agent"
    }
];
const frontendArchives = {
    "federation-learning-console": "federation-learning-console-images.tar",
    "federation-learning-participant-console": "federation-learning-participant-console-images.tar"
};
const projectRoots = resolveProjectRoots();
const appArchives = {
    backend: join(outputRoot, 'images', 'backend-images.tar'),
    ...Object.fromEntries(Object.entries(frontendArchives).map(([name, file]) => [
        name,
        join(outputRoot, 'images', file)
    ]))
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
        writeAggregateManifest('export', true);
        prepareOutput();
        exportImages();
        break;
    case 'import':
        await importImages();
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
    release.claim();
    for (const project of selectedProjects()) {
        runNode(project.root, [
            'scripts/image-bundle.mjs',
            'build',
            ...platformArgs(),
            '--prefix',
            imagePrefix,
            '--version',
            imageVersion,
            ...projectImageArgs(project),
            ...projectBuildOptimizationArgs(project)
        ]);
    }
    writeAggregateManifest('build');
}

function exportImages() {
    writeAggregateManifest('export', true);
    for (const project of selectedProjects()) {
        runNode(project.root, [
            'scripts/image-bundle.mjs',
            'export',
            ...platformArgs(),
            '--prefix',
            imagePrefix,
            '--version',
            imageVersion,
            ...projectImageArgs(project),
            '--output',
            appArchives[project.key]
        ]);
    }
    writeAggregateManifest('export');
}

async function importImages() {
    const plans = [];
    for (const project of selectedProjects()) {
        const archiveFile = appArchives[project.key];
        const manifest = await verifyArchiveManifest({archiveFile, dryRun});
        if (manifest) {
            const requested = project.key === 'backend' ? project.modules : [project.application.name];
            const archived = manifest.images.map((image) => image.name.split('/').pop().replace(/:[^:]+$/, ''));
            if (archived.length !== requested.length || requested.some((name) => !archived.includes(name))) {
                fail(`Archive does not match the selected applications: ${project.key}`);
            }
        }
        plans.push({archiveFile, manifest});
    }
    if (!dryRun && !skipDependencies && !existsSync(dependencyArchive)) {
        fail(`Dependency image archive was not found: ${dependencyArchive}`);
    }
    for (const {archiveFile, manifest} of plans) {
        run('docker', ['load', '-i', archiveFile], monorepoRoot);
        if (!dryRun) verifyLoadedImages({manifest, root: monorepoRoot});
    }
    if (!skipDependencies) importDependencyImages();
}

function pushImages() {
    release.claim();
    writeAggregateManifest('push', true);
    for (const project of selectedProjects()) {
        runNode(project.root, [
            'scripts/image-bundle.mjs',
            'push',
            ...platformArgs(),
            '--prefix',
            imagePrefix,
            '--version',
            imageVersion,
            ...projectImageArgs(project)
        ]);
    }
    writeAggregateManifest('push');
}

function pullDependencyImages() {
    if (skipDependencies || !projectRoots.backend) return;
    runNode(projectRoots.backend, ['scripts/dependency-images.mjs', 'pull', '--platform', dependencyPlatform, '--image', infrastructureImages().join(',')]);
}

function exportDependencyImages() {
    if (skipDependencies || !projectRoots.backend) return;
    runNode(projectRoots.backend, ['scripts/dependency-images.mjs', 'export', '--platform', dependencyPlatform, '--output', dependencyArchive, '--image', infrastructureImages().join(',')]);
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
    const projects = selectedProjects();
    const backendProject = projects.find((project) => project.key === 'backend');
    const frontendProjects = projects.filter((project) => project.kind === 'frontend');
    if (backendProject && existsSync(join(backendProject.root, 'scripts/collect-deployment-compose-files.mjs'))) {
        runNode(backendProject.root, [
            'scripts/collect-deployment-compose-files.mjs',
            '--output',
            join(composeRoot, 'backend'),
            '--clean'
        ]);
    } else {
        copyKnownFiles(backendProject?.root, join(composeRoot, 'backend'), ['docker-compose.yml', '.env-example']);
    }
    for (const frontendProject of frontendProjects) {
        copyKnownFiles(frontendProject.root, join(composeRoot, frontendProject.key), ['docker-compose.yml', '.env-example']);
    }
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
        ...release.manifest,
        generatedAt: new Date().toISOString(),
        environment: 'dev',
        platform: platform || 'native',
        dependencyPlatform,
        imagePrefix,
        dependencyImagePrefix,
        imageVersion,
        monorepoRoot,
        applications: selectedApplicationNames(),
        infrastructureImages: [
        "postgres:16",
        "umadb/umadb:0.7.8",
        "apache/apisix:3.13.0-debian",
        "rancher/mirrored-pause:3.6",
        "rancher/local-path-provisioner:v0.0.31",
        "rancher/mirrored-library-busybox:1.36.1",
        "rancher/mirrored-coredns-coredns:1.12.3",
        "rancher/mirrored-metrics-server:v0.8.0"
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

function writeAggregateManifest(action, preflight = false) {
    const components = selectedProjects().map((project) => {
        const names = project.key === 'backend' ? project.modules : [project.application.name];
        const requestedImages = names.map((name) => `${imagePrefix}/${name}:${imageVersion}`);
        const file = action === 'export' && !preflight ? `${appArchives[project.key]}.manifest.json`
            : join(project.root, '.medol', action === 'push' && !preflight ? 'release-manifest.json' : 'build-manifest.json');
        if (dryRun) return {project: project.key, manifest: relative(monorepoRoot, file)};
        let component;
        if (preflight) {
            const session = prepareRelease({root: project.root, version: imageVersion,
                target: project.key === 'backend' ? 'axon5' : 'refine', operation: action});
            component = {...session.manifest, images: session.verifyImages(requestedImages)};
        } else {
            if (!existsSync(file)) fail(`Component manifest missing: ${file}`);
            component = JSON.parse(readFileSync(file, 'utf8'));
        }
        if (component.imageVersion !== imageVersion || component.generation?.model?.sha256 !== release.manifest.generation?.model?.sha256) {
            fail(`Component version/model hash mismatch: ${project.key}`);
        }
        if (!preflight && requestedImages.some((name) => !(component.images ?? []).some((image) => image.name === name))) {
            fail(`Component manifest does not contain the requested images: ${project.key}`);
        }
        return {project: project.key, manifest: relative(monorepoRoot, file), ...component,
            images: requestedImages.map((name) => component.images.find((image) => image.name === name))};
    });
    release.manifest.components = components;
    const images = components.flatMap((component) => (component.images ?? []).map((image) => image.name));
    if (preflight) release.verifyImages(images);
    else {
        release.writeManifest(join(outputRoot, 'release-manifest.json'), images, {requireRepoDigests: action === 'push'});
        if (action === 'build') release.writeManifest('.medol/build-manifest.json', images);
    }
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
    validateServiceFilters();
    const selectedBackendModules = backendModuleNames.filter((moduleName) => serviceMatches([moduleName]));
    const selectedFrontends = frontendApplications.filter((application) => !skipConsole && serviceMatches(frontendServiceAliases(application)));
    const missingFrontends = selectedFrontends.filter((application) => !projectRoots.frontends[application.name]);
    if (missingFrontends.length > 0) {
        fail(`Missing frontend project root(s): ${missingFrontends.map((application) => application.name).join(', ')}. Generate each frontend application directory or pass --<frontend-service>-root.`);
    }
    return [
        projectRoots.backend && !skipBackend && selectedBackendModules.length > 0
            ? {key: 'backend', root: projectRoots.backend, modules: selectedBackendModules}
            : undefined,
        ...selectedFrontends
            .map((application) => projectRoots.frontends[application.name]
                ? {
                    key: application.name,
                    kind: 'frontend',
                    root: projectRoots.frontends[application.name],
                    application
                }
                : undefined)
    ].filter(Boolean);
}

function selectedApplicationNames() {
    return [
        ...backendModuleNames.filter((moduleName) => serviceMatches([moduleName])),
        ...frontendApplications
            .filter((application) => projectRoots.frontends[application.name] && !skipConsole && serviceMatches(frontendServiceAliases(application)))
            .map((application) => application.name)
    ];
}

function projectImageArgs(project) {
    if (project.key === 'backend' && project.modules?.length > 0) {
        return ['--module', project.modules.join(',')];
    }
    if (project.kind === 'frontend') {
        return [
            '--image',
            project.application.name,
            ...(project.application.frontendApplicationName
                ? ['--build-arg', `VITE_FRONTEND_APP=${project.application.frontendApplicationName}`]
                : [])
        ];
    }
    return [];
}

function projectBuildOptimizationArgs(project) {
    if (!fastBuild || project.key !== 'backend') return [];
    return ['--skip-install', '--offline'];
}

function platformArgs(value = platform) {
    return value ? ['--platform', value] : [];
}

function resolveProjectRoots() {
    const frontends = Object.fromEntries(frontendApplications.map((application, index) => [
        application.name,
        resolveFrontendProjectRoot(application, index)
    ]));
    return {
        backend: explicitRoot('backend-root', 'BACKEND_ROOT') ?? findProjectRoot(['backend'], isBackendProject),
        frontends
    };
}

function resolveFrontendProjectRoot(application, index) {
    const explicit = explicitRoot(`${application.name}-root`, `${envName(application.name)}_ROOT`)
        ?? (index === 0 ? explicitRoot('console-root', 'CONSOLE_ROOT') : undefined);
    if (explicit) return explicit;
    const preferred = [
        application.name,
        application.artifactName,
        ...(index === 0 ? ['console', 'frontend'] : [])
    ].filter(Boolean);
    return index === 0
        ? findProjectRoot(preferred, isConsoleProject)
        : findNamedProjectRoot(preferred, isConsoleProject);
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

function findNamedProjectRoot(preferredNames, predicate) {
    for (const name of preferredNames) {
        const root = resolve(monorepoRoot, name);
        if (existsSync(root) && predicate(root)) return root;
    }
    return undefined;
}

function isBackendProject(root) {
    return existsSync(join(root, 'mvnw')) && existsSync(join(root, 'scripts/image-bundle.mjs'));
}

function isConsoleProject(root) {
    return existsSync(join(root, 'package.json')) && existsSync(join(root, 'Dockerfile')) && existsSync(join(root, 'scripts/image-bundle.mjs'));
}

function printPlan() {
    console.log('[operations-images] monorepo:', monorepoRoot);
    console.log('[operations-images] environment:', 'dev');
    console.log('[operations-images] application platform:', platform || 'native');
    console.log('[operations-images] dependency platform:', dependencyPlatform);
    console.log('[operations-images] fast build:', fastBuild ? 'enabled' : 'disabled');
    console.log('[operations-images] image prefix:', imagePrefix);
    console.log('[operations-images] deployment image prefix:', imageConfig.imagePrefix);
    console.log('[operations-images] image source:', imageConfig.mode);
    console.log('[operations-images] dependency image prefix:', dependencyImagePrefix);
    console.log('[operations-images] image version:', imageVersion);
    console.log('[operations-images] output:', outputRoot);
    console.log('[operations-images] package:', packageArchive);
    console.log('[operations-images] projects:');
    for (const project of selectedProjects()) {
        const moduleText = project.modules?.length ? ` (${project.modules.join(', ')})` : '';
        console.log(`  - ${project.key}${moduleText}: ${relative(monorepoRoot, project.root) || '.'}`);
    }
    if (!skipDependencies && projectRoots.backend) {
        console.log('  - dependencies: generated infrastructure and K3s system images');
    }
    const dependencyTargets = infrastructureImages().map((image) => `${image} -> ${dependencyRegistryImage(image)}`);
    if (dependencyTargets.length > 0) {
        console.log('[operations-images] dependency registry targets:');
        for (const target of dependencyTargets) {
            console.log(`  - ${target}`);
        }
    }
}

function serviceMatches(aliases) {
    const normalizedAliases = aliases.map(normalizeServiceName);
    const included = serviceIncludes.length === 0 || serviceIncludes.some((value) => normalizedAliases.includes(normalizeServiceName(value)));
    const excluded = serviceExcludes.some((value) => normalizedAliases.includes(normalizeServiceName(value)));
    return included && !excluded;
}

function validateServiceFilters() {
    const allAliases = [
        ...backendModuleNames,
        ...frontendApplications.flatMap(frontendServiceAliases)
    ].map(normalizeServiceName);
    const unknownIncludes = serviceIncludes.filter((value) => !allAliases.includes(normalizeServiceName(value)));
    if (unknownIncludes.length > 0) {
        fail(`Unknown service filter(s): ${unknownIncludes.join(', ')}. Available: ${[
            ...backendModuleNames,
            ...frontendApplications.map((application) => application.name)
        ].join(', ')}`);
    }
    const unknownExcludes = serviceExcludes.filter((value) => !allAliases.includes(normalizeServiceName(value)));
    if (unknownExcludes.length > 0) {
        console.log(`[operations-images] ignore unmanaged excluded service(s): ${unknownExcludes.join(', ')}`);
    }
}

function frontendServiceAliases(application) {
    const root = projectRoots.frontends[application.name];
    return unique([
        application.name,
        application.artifactName,
        application.frontendApplicationName,
        application.bundledWithBackend,
        'frontend',
        application.name === 'console' ? 'console' : undefined,
        root ? basename(root) : undefined
    ]);
}

function envName(value) {
    return String(value ?? '').trim().toUpperCase().replace(/[^A-Z0-9]+/g, '_');
}

function normalizeServiceName(value) {
    return String(value ?? '').trim().toLowerCase().replace(/_/g, '-');
}

function serviceFilterValues(...keys) {
    return keys
        .flatMap((key) => valuesOf(args[key]))
        .flatMap((value) => String(value).split(','))
        .map((value) => value.trim())
        .filter(Boolean);
}

function infrastructureImages() {
    return unique([
        ...[
        "postgres:16",
        "umadb/umadb:0.7.8",
        "apache/apisix:3.13.0-debian",
        "rancher/mirrored-pause:3.6",
        "rancher/local-path-provisioner:v0.0.31",
        "rancher/mirrored-library-busybox:1.36.1",
        "rancher/mirrored-coredns-coredns:1.12.3",
        "rancher/mirrored-metrics-server:v0.8.0"
],
        ...extraDependencyImages()
    ]);
}

function dependencyRegistryImage(image) {
    const { repository, tag } = splitImage(image);
    return `${dependencyImagePrefix}/${repository}:${tag}`;
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

function extraDependencyImages() {
    return String(args['dependency-image'] ?? process.env.EXTRA_DEPENDENCY_IMAGES ?? '')
        .split(',')
        .map((value) => value.trim())
        .filter(Boolean);
}

function defaultDependencyImagePrefix(prefix) {
    const first = String(prefix ?? '').split('/')[0];
    return isRegistryHost(first) ? first : `${prefix}/dependencies`;
}

function isRegistryHost(value) {
    return value?.includes('.') || value?.includes(':') || value === 'localhost';
}

function unique(values) {
    return Array.from(new Set(values.filter(Boolean)));
}

function valuesOf(value) {
    if (value == null) return [];
    return Array.isArray(value) ? value : [value];
}

function hostDockerPlatform() {
    if (process.platform === 'darwin' || process.platform === 'linux') {
        if (process.arch === 'arm64') return 'linux/arm64';
        if (process.arch === 'x64') return 'linux/amd64';
    }
    return '';
}

function flagEnabled(argValue, envValue) {
    return truthy(argValue) || truthy(envValue);
}

function truthy(value) {
    if (value == null || value === false) return false;
    const normalized = String(value).trim().toLowerCase();
    return normalized !== '' && normalized !== '0' && normalized !== 'false' && normalized !== 'no' && normalized !== 'off';
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
  node operations/images/dev/images.mjs list [options]
  node operations/images/dev/images.mjs build [options]
  node operations/images/dev/images.mjs export [options]
  node operations/images/dev/images.mjs import [options]
  node operations/images/dev/images.mjs push --prefix <registry/namespace> [options]
  node operations/images/dev/images.mjs pull-dependencies [options]
  node operations/images/dev/images.mjs export-dependencies [options]
  node operations/images/dev/images.mjs import-dependencies [options]
  node operations/images/dev/images.mjs push-dependencies --prefix <registry/namespace> [options]
  node operations/images/dev/images.mjs package [options]
  node operations/images/dev/images.mjs all [options]

Commands:
  build                Build backend and frontend images.
  export               Save application images into per-project tar archives.
  import               Load application and dependency image archives.
  push                 Push application images to --prefix / DOCKER_IMAGE_PREFIX.
  pull-dependencies    Pull generated infrastructure and K3s system images.
  export-dependencies  Save infrastructure images into dependency-images.tar.
  import-dependencies  Load dependency-images.tar.
  push-dependencies    Retag dependencies under a registry-root mirror path and push them.
  package, all         Build, pull dependencies, export images, collect compose files, and tar the bundle.

Options:
  --prefix <name>                 Image prefix or registry namespace. Defaults to DOCKER_IMAGE_PREFIX or fl.
  --image-source <import|registry> Image acquisition policy shared with deployment tools.
  --dependency-prefix <name>      Registry root for dependency mirrors. Defaults to DEPENDENCY_IMAGE_PREFIX, or the registry host from --prefix.
  --dependency-image <image[,..]> Add extra dependency images to pull/export/push.
  --version <tag>                 Image tag. Defaults to IMAGE_VERSION or 0.0.1-SNAPSHOT.
  --platform <os/arch>            Docker platform. Defaults to DOCKER_DEFAULT_PLATFORM or native for application builds.
  --output <dir>                  Bundle directory. Defaults to .work/image-bundle beside this script.
  --archive <file>                Package archive. Defaults to <output>.tar.gz.
  --root <dir>                    Monorepo root. Defaults to ../../.. from this image environment.
  --backend-root <dir>            Override backend project root.
  --console-root <dir>            Override the first frontend project root for legacy workspaces.
  --<frontend-service>-root <dir> Override one frontend project root, for example --participant-console-root ./participant-console.
  --service <name[,name]>         Only process selected application services. Can be repeated.
  --exclude-service <name[,name]> Skip selected application services. Can be repeated.
  --skip-service <name[,name]>    Alias for --exclude-service.
  --skip-backend                  Skip backend image operations.
  --skip-console                  Skip frontend image operations.
  --skip-dependencies             Skip infrastructure dependency image operations.
  --skip-archive                  Leave package directory unpacked.
  --fast-build                    Skip backend reactor install and run Maven offline. Only use when local artifacts are already compiled.
  --dry-run                       Print commands without running them.`);
}

function fail(message) {
    console.error(`[operations-images] ${message}`);
    process.exit(1);
}
