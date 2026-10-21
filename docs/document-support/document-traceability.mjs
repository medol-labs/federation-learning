import { createHash } from 'node:crypto';
import { existsSync, readFileSync } from 'node:fs';
import { isDeepStrictEqual } from 'node:util';

export const sha256 = (content) => createHash('sha256').update(content).digest('hex');
export const readJson = (path) => JSON.parse(readFileSync(path, 'utf8'));

// This is the legacy Medol source fingerprint, not a SHA256 digest.
export function legacySourceHash(source) {
  let hash = 2166136261;
  for (let index = 0; index < source.length; index += 1) {
    hash ^= source.charCodeAt(index);
    hash = Math.imul(hash, 16777619);
  }
  return `fnv1a-${(hash >>> 0).toString(16).padStart(8, '0')}`;
}

export function loadBaseline(modelPath, sourcePath) {
  if (!existsSync(modelPath)) return null;
  const metadata = readJson(modelPath).metadata;
  if (!metadata) return null;
  if (metadata.schemaVersion !== 1 || typeof metadata.medolVersion !== 'string'
    || !/^[a-f0-9]{64}$/i.test(metadata.source?.sha256 ?? '')) {
    throw new Error('Invalid codegen model metadata (expected schemaVersion 1 and source SHA256).');
  }
  const verified = existsSync(sourcePath) && sha256(readFileSync(sourcePath)) === metadata.source.sha256;
  return { metadata, sha256: sha256(readFileSync(modelPath)), sourceVerified: verified };
}

export function sourceAssociation(entry, content, baseline) {
  if (!entry || entry.sha256 !== sha256(content)) return 'unverified';
  if (!entry.model?.source?.sha256 || !baseline?.sourceVerified) return 'unverified';
  const expected = baseline.metadata.source;
  if (entry.model.source.sha256 !== expected.sha256) return 'stale';
  for (const key of ['workspaceId', 'versionId', 'versionNo']) {
    if (expected[key] !== undefined) {
      if (entry.model.source[key] === undefined) return 'unverified';
      if (entry.model.source[key] !== expected[key]) return 'stale';
    }
  }
  if (expected.release) {
    if (!entry.model.source.release) return 'unverified';
    if (expected.release.label !== entry.model.source.release.label
      || expected.release.channel !== entry.model.source.release.channel) return 'stale';
  }
  return 'verified';
}

function normalizedMetadata(metadata) {
  if (!metadata?.source) return null;
  const source = Object.fromEntries(['sha256', 'workspaceId', 'versionId', 'versionNo', 'modelHash']
    .filter((key) => metadata.source[key] !== undefined).map((key) => [key, metadata.source[key]]));
  if (metadata.source.release) {
    source.release = Object.fromEntries(['channel', 'label', 'releasedAt']
      .filter((key) => metadata.source.release[key] !== undefined)
      .map((key) => [key, metadata.source.release[key]]));
  }
  return { schemaVersion: metadata.schemaVersion, medolVersion: metadata.medolVersion, source };
}

// Image IDs distinguish a completed release-runtime manifest from a build claim.
export function verifyDelivery(deliveryPath, baseline) {
  const delivery = readJson(deliveryPath);
  const hashPattern = /^[a-f0-9]{64}$/;
  const release = baseline?.metadata.source.release;
  if (delivery.schemaVersion !== 1 || !baseline?.sourceVerified || release?.channel !== 'stable'
    || !release.label || !/^v\d+\.\d+\.\d+$/.test(release.label)
    || !new RegExp(`^${release.label.replaceAll('.', '\\.')}\\.\\d+$`).test(delivery.imageVersion ?? '')
    || delivery.packageVersion !== release.label.slice(1)
    || !hashPattern.test(delivery.buildIdentity ?? '') || !hashPattern.test(delivery.codeSha256 ?? '')
    || delivery.generation?.schemaVersion !== 1
    || delivery.generation.model?.sha256 !== baseline.sha256
    || !isDeepStrictEqual(normalizedMetadata(delivery.generation.model.metadata), normalizedMetadata(baseline.metadata))
    || !Array.isArray(delivery.generation.inputs)
    || !Array.isArray(delivery.images) || !delivery.images.length
    || delivery.images.some((image) => typeof image.name !== 'string' || !image.name
      || !/^sha256:[a-f0-9]{64}$/.test(image.id ?? '') || !Array.isArray(image.repoDigests))) {
    throw new Error('Release manifest must contain built images, buildIdentity, and the matching stable model baseline/metadata.');
  }
  return { deliveryId: delivery.imageVersion, packageVersion: delivery.packageVersion,
    buildIdentity: delivery.buildIdentity, codeSha256: delivery.codeSha256,
    images: delivery.images, manifestSha256: sha256(readFileSync(deliveryPath)) };
}
