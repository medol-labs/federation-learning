#!/usr/bin/env node
import { existsSync, mkdirSync, readFileSync, readdirSync, writeFileSync } from 'node:fs';
import { basename, dirname, extname, join, relative, resolve } from 'node:path';
import { fileURLToPath } from 'node:url';

const scriptDirectory = dirname(fileURLToPath(import.meta.url));
const repositoryRoot = resolve(scriptDirectory, '../..');
const args = parseArgs(process.argv.slice(2));
const configPath = resolve(repositoryRoot, args.config ?? 'docs/document-support/config/engineering-facts-sources.json');
const outputPath = resolve(repositoryRoot, args.out ?? 'docs/generated/facts/engineering-facts.json');

if (!existsSync(configPath)) {
  fail(`Engineering facts source config does not exist: ${configPath}`);
}

const config = JSON.parse(readFileSync(configPath, 'utf8'));
const operationsModelPath = resolve(repositoryRoot, config.operationsModel);
if (!existsSync(operationsModelPath)) {
  fail(`Operations model does not exist: ${operationsModelPath}`);
}

const operationsModel = JSON.parse(readFileSync(operationsModelPath, 'utf8'));
const facts = collectFacts(config, operationsModel);
mkdirSync(dirname(outputPath), { recursive: true });
writeFileSync(outputPath, `${JSON.stringify(facts, null, 2)}\n`, 'utf8');
console.log(`[document-support] Collected engineering facts in ${relative(repositoryRoot, outputPath)}`);

function collectFacts(config, model) {
  const applications = (model.applications ?? []).map((app) => ({
    name: app.name,
    title: app.title,
    kind: app.kind,
    image: app.imageName ? `${app.artifactName ?? app.name}:${model.imageTag ?? 'latest'}` : '',
    port: app.servicePort ?? null,
    gatewayPath: app.gatewayPath ?? '',
    contexts: app.contexts ?? [],
    dependencies: app.dependencies ?? [],
    healthCheck: normalizeHealthCheck(app.healthCheck),
    environmentVariables: normalizeEnvironment(app.environmentVariables),
  }));

  const infrastructure = (model.infrastructure ?? [])
    .filter((item) => item.enabled !== false)
    .map((item) => ({
      name: item.name,
      type: item.type,
      image: item.image,
      port: item.servicePort ?? item.managementPort ?? null,
      volumes: item.volumeNames ?? (item.volumeName ? [item.volumeName] : []),
      healthCheck: normalizeHealthCheck(item.healthCheck),
      environmentVariables: normalizeEnvironment(item.environmentVariables),
    }));

  const gateway = model.gateway?.enabled
    ? {
        provider: model.gateway.provider,
        image: model.gateway.image,
        port: model.gateway.servicePort,
        routes: (model.gateway.routes ?? []).map((route) => ({
          path: (route.paths ?? []).join(', '),
          upstream: route.upstreamId,
        })),
      }
    : null;

  const sourceFiles = [
    config.operationsModel,
    config.dockerCompose,
    ...(config.operationsReadmes ?? []),
    ...(config.applicationFiles ?? []),
  ].filter(Boolean);

  const fileInventory = sourceFiles
    .map((file) => {
      const path = resolve(repositoryRoot, file);
      if (!existsSync(path)) return null;
      return {
        path: file,
        kind: classifyFile(file),
        summary: summarizeFile(file, readFileSync(path, 'utf8')),
      };
    })
    .filter(Boolean);

  return {
    generatedAt: new Date().toISOString(),
    environment: config.environment ?? 'dev',
    imagePrefix: '',
    imageTag: model.imageTag ?? '',
    applications,
    technologyStacks: (config.technologyProjects ?? []).map(collectTechnologyStack).filter(Boolean),
    infrastructure,
    gateway,
    deploymentModes: deploymentModes(config),
    sourceFiles: fileInventory,
    operationsCommands: operationsCommands(config),
    securityNotes: securityNotes(applications),
    storageNotes: storageNotes(infrastructure, fileInventory),
  };
}

function collectTechnologyStack(project) {
  const manifestName = project.kind === 'backend' ? 'pom.xml' : 'package.json';
  const candidates = [];
  if (project.kind === 'backend') {
    for (const entry of readdirSync(repositoryRoot, { withFileTypes: true })) {
      if (entry.isDirectory() && !entry.name.startsWith('.')) {
        candidates.push(join(entry.name, project.name, manifestName));
      }
    }
  }
  candidates.push(join(project.name, manifestName));
  const manifest = candidates.find((candidate) => existsSync(resolve(repositoryRoot, candidate)));
  if (!manifest) return null;
  const source = readFileSync(resolve(repositoryRoot, manifest), 'utf8');
  const technologies = project.kind === 'backend' ? backendTechnologies(source) : frontendTechnologies(JSON.parse(source));
  return { application: project.name, kind: project.kind, manifest, technologies };
}

function backendTechnologies(pom) {
  const element = (source, name) => {
    const open = '<' + name + '>';
    const close = '</' + name + '>';
    const start = source.indexOf(open);
    const end = start < 0 ? -1 : source.indexOf(close, start + open.length);
    return end < 0 ? undefined : source.slice(start + open.length, end).trim();
  };
  const property = (name) => element(pom, name);
  const technologies = [];
  const add = (name, version) => technologies.push({ name, version: version ?? '' });
  add('Java', property('java.version'));
  add('Kotlin', property('kotlin.version'));
  add('Axon Framework', property('axon.version'));
  const parent = element(pom, 'parent') ?? '';
  if (parent.includes('<artifactId>spring-boot-starter-parent</artifactId>')) {
    add('Spring Boot', element(parent, 'version'));
  }
  if (pom.includes('<artifactId>spring-boot-starter-data-jpa</artifactId>')) add('Spring Data JPA', null);
  if (pom.includes('<artifactId>spring-boot-starter-security</artifactId>')) add('Spring Security', null);
  if (pom.includes('<artifactId>spring-cloud-starter-openfeign</artifactId>')) add('Spring Cloud OpenFeign', property('spring-cloud.version'));
  return technologies;
}

function frontendTechnologies(pkg) {
  const dependencies = { ...pkg.dependencies, ...pkg.devDependencies };
  const technologies = [
    ['React', 'react'],
    ['Refine', '@refinedev/core'],
    ['Vite', 'vite'],
    ['TypeScript', 'typescript'],
    ['Tailwind CSS', 'tailwindcss'],
    ['shadcn', 'shadcn'],
  ].filter(([, dependency]) => dependencies[dependency])
    .map(([name, dependency]) => ({ name, version: dependencies[dependency] }));
  if (pkg.packageManager) technologies.push({ name: 'Package manager', version: pkg.packageManager });
  return technologies;
}

function normalizeEnvironment(items = []) {
  return items.map((item) => ({
    name: item.name,
    value: redactValue(item.value ?? ''),
    required: /:\?/.test(String(item.value ?? '')),
  }));
}

function normalizeHealthCheck(healthCheck) {
  if (!healthCheck) return null;
  return {
    path: healthCheck.path ?? '',
    command: healthCheck.command ?? '',
    port: healthCheck.port ?? null,
    intervalSeconds: healthCheck.intervalSeconds ?? null,
    timeoutSeconds: healthCheck.timeoutSeconds ?? null,
    retries: healthCheck.retries ?? null,
  };
}

function redactValue(value) {
  const text = String(value);
  if (/PASSWORD|TOKEN|KEY|SECRET|CREDENTIAL/i.test(text)) {
    return text.replace(/:-[^}]+/g, ':-***').replace(/:\?[^}]+/g, ':?***');
  }
  return text
    .replace(/\$\{POSTGRES_USER:-medol\}/gu, '${POSTGRES_USER:-<db_user>}')
    .replace(/\$\{POSTGRES_DB:-medol\}/gu, '${POSTGRES_DB:-<db_name>}')
    .replace(/:-medol/gu, ':-<value>');
}

function classifyFile(file) {
  if (/docker-compose.*\.ya?ml$/u.test(file)) return 'docker-compose';
  if (/k3s/u.test(file)) return 'k3s';
  if (/kubernetes/u.test(file)) return 'kubernetes';
  if (/Dockerfile/u.test(file)) return 'dockerfile';
  if (/\.env-example$/u.test(file)) return 'environment-example';
  if (extname(file) === '.json') return 'json';
  if (extname(file) === '.md') return 'markdown';
  return 'file';
}

function summarizeFile(file, content) {
  if (/\.env-example$/u.test(file)) {
    const keys = content
      .split(/\r?\n/u)
      .map((line) => line.trim())
      .filter((line) => line && !line.startsWith('#') && line.includes('='))
      .map((line) => line.split('=')[0].trim())
      .filter(Boolean);
    return `${keys.length} environment variable(s): ${keys.slice(0, 12).join(', ')}${keys.length > 12 ? ', ...' : ''}`;
  }
  if (/Dockerfile/u.test(file)) {
    const from = content.match(/^FROM\s+(.+)$/mu)?.[1] ?? 'unknown base image';
    return `Docker image build file, base ${from}.`;
  }
  if (/docker-compose.*\.ya?ml$/u.test(file)) {
    const services = [...content.matchAll(/^\s{2}([a-zA-Z0-9_.-]+):\s*$/gmu)].map((match) => match[1]);
    return `Docker Compose file with service(s): ${services.slice(0, 12).join(', ')}${services.length > 12 ? ', ...' : ''}`;
  }
  const heading = content.match(/^#\s+(.+)$/mu)?.[1];
  return heading ? `Documentation: ${heading}.` : `${basename(file)} (${content.length} bytes).`;
}

function deploymentModes(config) {
  const files = [
    config.dockerCompose,
    ...(config.operationsReadmes ?? []),
    ...(config.applicationFiles ?? []),
  ].filter(Boolean);
  const modes = [];
  if (files.some((file) => /docker-compose/u.test(file))) modes.push('Docker Compose');
  if (files.some((file) => /k3s/u.test(file))) modes.push('K3s');
  if (files.some((file) => /kubernetes/u.test(file))) modes.push('Kubernetes');
  return [...new Set(modes)];
}

function operationsCommands(config) {
  const environment = config.environment ?? 'dev';
  return [
    `cp operations/${environment}/.env-example operations/${environment}/.env`,
    `docker compose --env-file operations/${environment}/.env -f operations/${environment}/docker-compose/docker-compose.yml up -d`,
    `node operations/${environment}/images.mjs list`,
    `node operations/${environment}/images.mjs all`,
    `kubectl apply -k operations/${environment}/kubernetes/environments/${environment}`,
  ];
}

function securityNotes(applications) {
  const envNames = new Set(applications.flatMap((app) => app.environmentVariables.map((env) => env.name)));
  const notes = [];
  if (envNames.has('MEDOL_SECURITY_INTERNAL_TOKEN')) {
    notes.push('Service-to-service calls may use an internal bearer token configured per environment.');
  }
  if ([...envNames].some((name) => /ALLOWED_ORIGINS/u.test(name))) {
    notes.push('Browser access is constrained by configured CORS allowed origins.');
  }
  if ([...envNames].some((name) => /ADMIN_BOOTSTRAP/u.test(name))) {
    notes.push('Initial administrator setup is controlled by bootstrap environment variables.');
  }
  return notes;
}

function storageNotes(infrastructure, files) {
  const notes = [];
  const volumes = infrastructure.flatMap((item) => item.volumes ?? []);
  if (volumes.length) notes.push(`Persistent volumes: ${[...new Set(volumes)].join(', ')}.`);
  const datasetFiles = files.filter((file) => /DATASET|datasets|RUNTIME_AGENT_LOCAL_RUNTIME_ENGINE_DATASET/u.test(file.summary));
  if (datasetFiles.length) notes.push('Runtime dataset directories are configured through deployment environment files.');
  return notes;
}

function parseArgs(argv) {
  const parsed = {};
  for (const arg of argv) {
    if (!arg.startsWith('--')) continue;
    const body = arg.slice(2);
    const [key, ...rest] = body.split('=');
    parsed[key] = rest.length ? rest.join('=') : 'true';
  }
  if (parsed.help) {
    console.log(`Usage: node docs/document-support/${basename(fileURLToPath(import.meta.url))} [options]

Options:
  --config=docs/document-support/config/engineering-facts-sources.json
  --out=docs/generated/facts/engineering-facts.json
`);
    process.exit(0);
  }
  return parsed;
}

function fail(message) {
  console.error(`[document-support] ${message}`);
  process.exit(1);
}
