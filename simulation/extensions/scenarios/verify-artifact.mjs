import {createHash} from 'node:crypto';

export async function verifyArtifact(model, allowedBases, headersFor, fetcher = fetch) {
    if (!model?.modelArtifactUri || !/^sha256:[a-f0-9]{64}$/i.test(model.modelArtifactDigest || '')) {
        throw new Error('Aggregated model must expose an artifact URI and SHA-256 digest');
    }
    const url = new URL(model.modelArtifactUri);
    const allowed = allowedBases.some(base => {
        const target = new URL(base);
        const prefix = target.pathname.replace(/\/$/, '');
        return target.origin === url.origin && (url.pathname === prefix || url.pathname.startsWith(prefix + '/'));
    });
    if (!['http:', 'https:'].includes(url.protocol) || url.username || url.password || !allowed) {
        throw new Error('Artifact address is not a configured reachable service endpoint; configure a reachable artifact URL before verification');
    }
    const response = await fetcher(url, {headers: headersFor(url.href), redirect: 'error', signal: AbortSignal.timeout(120000)});
    if (!response.ok || !response.body) throw new Error(`Artifact download failed: HTTP ${response.status}`);
    const hash = createHash('sha256');
    let bytes = 0;
    for await (const chunk of response.body) { bytes += chunk.length; hash.update(chunk); }
    if (!bytes) throw new Error('Artifact is empty');
    if (`sha256:${hash.digest('hex')}` !== model.modelArtifactDigest.toLowerCase()) throw new Error('Artifact digest mismatch');
    return {modelId: model.modelId, bytes, digest: model.modelArtifactDigest};
}
