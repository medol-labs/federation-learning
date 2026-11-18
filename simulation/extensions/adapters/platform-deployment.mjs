import {readFileSync} from 'node:fs';
import {resolve} from 'node:path';
import {fileURLToPath} from 'node:url';
import {createKubernetesDeployment} from '../../src/runtime/kubernetes-deployment.mjs';
import {createParticipantSlots, participantSlot} from './participant-slots.mjs';
import {deploymentCatalog} from './service-catalog.mjs';
import {resolveImageConfig} from '../../../operations/images/image-config.mjs';

export function createPlatformDeployment(config, dependencies = {}) {
    const root = dependencies.root || fileURLToPath(new URL('../../../', import.meta.url));
    const bundle = JSON.parse(readFileSync(resolve(root, 'operations/extensions/platform-deployment.json'), 'utf8'));
    const environment = config.environment || 'local';
    const model = JSON.parse(readFileSync(resolve(root, 'operations/model', environment, 'operations-model.json'), 'utf8'));
    for (const [name, port] of Object.entries(bundle.servicePorts || {})) {
        if (model.applications?.find(app => app.name === name)?.servicePort !== port) throw new Error('Deployment package service port differs from Operations: ' + name);
    }
    const imageConfig = resolveImageConfig(resolve(root, 'operations/images', environment), {prefix: config.imagePrefix, version: config.imageVersion, mode: 'registry'});
    const namespace = config.namespace || bundle.namespace;
    const origins = [new URL(config.gatewayUrl).origin];
    if (config.participantAccess) for (let slot = 1; slot <= (config.participantSlots || 0); slot++) {
        origins.push(participantSlot(namespace, slot, config.participantAccess).consoleUrl);
    }
    const settings = {...config, environment, namespace, imageVersion: imageConfig.version,
        bindings: {...config.bindings, browserAllowedOrigins: [...new Set(origins)].join(',')}};
    const adapter = createKubernetesDeployment(bundle, settings, {root, catalog: deploymentCatalog(), ...dependencies});
    const slots = createParticipantSlots({root, environment, namespace: settings.namespace,
        kubeconfigFile: resolve(root, config.kubeconfigFile), count: config.participantSlots || 0,
        access: config.participantAccess, imagePrefix: config.imagePrefix, imageVersion: settings.imageVersion,
        timeoutMs: config.timeoutMs || 600000,
        ...(dependencies.execute ? {execute: (args, input) => dependencies.execute('kubectl', ['--kubeconfig', resolve(root, config.kubeconfigFile), ...args], input)} : {})});
    return {...adapter,
        async prepareImages() { await adapter.prepareImages(); slots.setImages(adapter.images()); },
        async deploy() { await adapter.deploy(); slots.deploy(); },
        async waitDeploymentReady() { await adapter.waitDeploymentReady(); slots.waitReady(); }
    };
}
