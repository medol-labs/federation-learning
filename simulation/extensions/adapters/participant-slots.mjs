import {readFileSync} from 'node:fs';
import {instanceSlot, instanceOverlay, createKubernetesInstances} from '../../src/runtime/kubernetes-instances.mjs';

const bundle = JSON.parse(readFileSync(new URL('../../../operations/extensions/participant-package.json', import.meta.url), 'utf8'));

export const participantSlot = (namespace, slot, access) => instanceSlot(bundle, namespace, slot, access);
export const participantOverlay = options => instanceOverlay(bundle, options);
export const createParticipantSlots = options => createKubernetesInstances(bundle, options);
