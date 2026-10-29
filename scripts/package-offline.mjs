#!/usr/bin/env node
import {cpSync, existsSync, mkdtempSync, readdirSync, readFileSync, writeFileSync, rmSync} from 'node:fs';
import {tmpdir} from 'node:os';
import {dirname, join, resolve} from 'node:path';
import {fileURLToPath} from 'node:url';
import {packageOffline, parseArguments} from '../operations/scripts/package-offline.mjs';

const root = resolve(dirname(fileURLToPath(import.meta.url)), '..');
const argv = process.argv.slice(2);
const engineIndex = argv.indexOf('--engine-archive');
const engine = engineIndex >= 0 ? argv.splice(engineIndex, 2)[1] : undefined;
const temporaryDirectories = [];
try {
    const options = parseArguments(argv);
    if (options.help) {
        console.log('node scripts/package-offline.mjs --environment <name> [--site <network-topology-site>] [--image-source import|registry] [--engine-archive <engine-images.tar>] [--archive <site-images.tar> ...] [--output <new-directory>]');
    } else {
        if (!/^[a-zA-Z0-9_-]+$/.test(options.environment ?? '') || options.environment === 'local') throw new Error('Select a non-local --environment.');
        if (engineIndex >= 0 && (!engine || engine.startsWith('--'))) throw new Error('--engine-archive requires an archive path when specified.');
        const imageSource = options.imageSource ?? 'import';
        if (imageSource === 'registry' && engine) throw new Error('Registry delivery does not include a Runtime Engine archive; push the engine image to the Registry instead.');
        if (options.root || options.k3sRoot || options.components.length) throw new Error('This project wrapper owns the root, K3s source, and business component selection.');
        const model = JSON.parse(readFileSync(join(root, 'operations/model', options.environment, 'operations-model.json'), 'utf8'));
        const sites = model.networkTopology?.sites ?? [];
        const requestedSite = options.site ? sites.find((candidate) => candidate.name === options.site) : undefined;
        if (options.site && !requestedSite) throw new Error(`Unknown deployment site: ${options.site}. Available: ${sites.map((candidate) => candidate.name).join(', ')}`);
        const selectedSites = requestedSite ? [requestedSite] : sites;
        if (!selectedSites.length) throw new Error('The operations model does not define networkTopology.sites.');
        if (imageSource === 'registry' && options.archives.length) throw new Error('Registry delivery does not accept --archive.');
        if (imageSource === 'import' && !options.site && options.archives.length) throw new Error('Use --site when supplying explicit --archive values, or omit --archive to package every site automatically.');
        const archiveDirectory = join(root, 'operations/images', options.environment, '.work/image-bundle/images');
        const outputs = [];
        for (const site of selectedSites) {
            const archivePrefix = `${site.name.replace(/_/g, '-').toLowerCase()}-`;
            const archives = imageSource === 'registry' ? [] : options.archives.length ? options.archives : readdirSync(archiveDirectory)
                .filter((name) => name.startsWith(archivePrefix) && name.endsWith('.tar'))
                .map((name) => join(archiveDirectory, name));
            if (imageSource === 'import' && !archives.length) throw new Error(`Export images for ${site.name} before packaging: node operations/images/images.mjs export --environment ${options.environment} --site ${site.name}`);
            const temporary = mkdtempSync(join(tmpdir(), 'fl-offline-'));
            temporaryDirectories.push(temporary);
            const components = [];
            const componentRoot = join(root, 'operations/k3s/components/runtime-scheduling');
            const sourceComponent = join(componentRoot, site.name);
            if (existsSync(sourceComponent)) {
                const component = join(temporary, 'runtime-scheduling');
                cpSync(sourceComponent, component, {recursive: true});
                for (const entry of readdirSync(componentRoot, {withFileTypes: true})) {
                    if (entry.isFile() && entry.name !== 'kustomization.yaml' && /\.ya?ml$/.test(entry.name)) {
                        cpSync(join(componentRoot, entry.name), join(component, entry.name));
                    } else if (entry.isDirectory() && entry.name === 'patches') {
                        cpSync(join(componentRoot, entry.name), join(component, entry.name), {recursive: true});
                    }
                }
                for (const entry of readdirSync(component, {withFileTypes: true})) {
                    if (!entry.isFile()) continue;
                    const file = join(component, entry.name);
                    writeFileSync(file, readFileSync(file, 'utf8').replace(/\.\.\//g, ''));
                }
                components.push(component);
            }
            function adapt(directory) {
                for (const entry of readdirSync(directory, {withFileTypes: true})) {
                    const file = join(directory, entry.name);
                    if (entry.isDirectory()) adapt(file);
                    else if (/\.ya?ml$/.test(entry.name)) writeFileSync(file, readFileSync(file, 'utf8').replace(/-dev-(config|secret)\b/g, `-${options.environment}-$1`));
                }
            }
            for (const component of components) adapt(component);
            const output = options.output && selectedSites.length > 1 ? join(resolve(options.output), site.name) : options.output;
            outputs.push(await packageOffline({...options, output, site: site.name, root, components, archives: [...archives, ...(engine ? [engine] : [])]}));
        }
        for (const output of outputs) console.log(`Offline package: ${output}`);
        if (imageSource === 'import' && !engine) console.log('Runtime Engine archive not included. Ensure the required engine images are already available on eligible target nodes.');
        console.log('Review rendered image references, managed agent/engine configuration, node mounts, and Secret names before delivery.');
    }
} catch (error) {console.error(error.message); process.exitCode = 1;}
finally {for (const temporary of temporaryDirectories) rmSync(temporary, {recursive: true});}
