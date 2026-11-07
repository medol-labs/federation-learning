#!/usr/bin/env node
import {cpSync, existsSync, readFileSync, writeFileSync} from 'node:fs';
import {basename, dirname, join, resolve} from 'node:path';
import {fileURLToPath} from 'node:url';
import {packageCompose, parseArguments} from '../operations/scripts/package-compose.mjs';

const root = resolve(dirname(fileURLToPath(import.meta.url)), '..');
const argv = process.argv.slice(2);
const index = argv.indexOf('--engine-archive');
const engine = index < 0 ? undefined : argv.splice(index, 2)[1];
try {
    if (index >= 0 && (!engine || engine.startsWith('--'))) throw new Error('--engine-archive requires a tar file.');
    const options = parseArguments(argv);
    if (options.help) {
        console.log('node scripts/package-compose.mjs --environment <env> [--site platform|participant] [--engine-archive <tar>] [--output <new-directory>]');
    } else {
        if (options.root) throw new Error('The project wrapper owns --root.');
        const archive = resolve(engine ?? join(root, 'operations/images', options.environment ?? '', '.work/image-bundle/images/runtime-engine-pytorch-vision.tar'));
        if (engine && (!archive.endsWith('.tar') || !existsSync(archive))) throw new Error(`Runtime Engine archive not found or not a tar file: ${archive}`);
        const includeEngineArchive = existsSync(archive);
        if (!includeEngineArchive) console.warn('Runtime Engine archive not found; packaging without it. Load or pull the engine image before installation.');
        const output = await packageCompose({...options, root}, async ({site, destination}) => {
            if (!['platform', 'participant'].includes(site.name)) throw new Error(`No runtime overlay for ${site.name}`);
            const overlay = join(root, 'operations/docker-compose/runtime', site.name + '.yml');
            cpSync(overlay, join(destination, 'runtime.yml'));
            if (includeEngineArchive) {
                const target = join(destination, 'images', basename(archive));
                if (existsSync(target)) throw new Error('Engine archive filename collides with a site archive.');
                cpSync(archive, target);
                if (existsSync(archive + '.manifest.json')) cpSync(archive + '.manifest.json', target + '.manifest.json');
            }
            const variables = site.name === 'platform'
                ? '\n# Replace registry, namespace and tag; for offline loading use the exact imported image tag.\nAGGREGATION_ENGINE_IMAGE=registry.example.com:5000/fl/federation-learning-runtime-engine:pytorch-vision-0.0.1-SNAPSHOT\nAGGREGATION_ENGINE_PLUGIN_PROFILE=pytorch-vision\n'
                : '\n# Replace registry, namespace and tag; for offline loading use the exact imported image tag.\nPARTICIPANT_ENGINE_IMAGE=registry.example.com:5000/fl/federation-learning-runtime-engine:pytorch-vision-0.0.1-SNAPSHOT\nPARTICIPANT_ENGINE_PLUGIN_PROFILE=pytorch-vision\nPARTICIPANT_DATASETS_DIR=\n';
            const env = join(destination, '.env-example');
            writeFileSync(env, readFileSync(env, 'utf8') + variables);
            writeFileSync(join(destination, 'RUNTIME.md'), '# Runtime Engine\n\n' + (includeEngineArchive
                ? 'The Runtime Engine archive is included. Set the engine image to the exact tag imported from the bundled archive. '
                : 'No Runtime Engine archive is included. Load or pull the required engine image on the deployment host before running install.sh, which uses --pull never. Set the engine image to that exact tag. ')
                + 'Set its plugin profile accordingly. Participant hosts need an existing absolute dataset directory, read access for containers, and reachable platform/support gateway URLs. Register dataset paths under /workspace/datasets. The agent image must support prestarted mode; the execution plan image must match the running engine. This deployment starts a standalone agent and a persistent engine, not Kubernetes-managed agents. Both communicating sites need matching internal tokens. Configure CORS and initial admin setup explicitly. Do not mix this new Compose stack with existing K3s database volumes.\n');
        });
        console.log(`Compose delivery: ${output}`);
    }
} catch (error) { console.error(error.message); process.exitCode = 1; }
