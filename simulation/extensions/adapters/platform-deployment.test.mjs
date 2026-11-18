import {test} from 'node:test';
import assert from 'node:assert/strict';
import {execFileSync} from 'node:child_process';
import {mkdtempSync, mkdirSync, writeFileSync, cpSync, rmSync, readFileSync} from 'node:fs';
import {tmpdir} from 'node:os';
import {join, resolve} from 'node:path';
import {createPlatformDeployment} from './platform-deployment.mjs';

test('platform deploy renders Operations assets without training or K3d scripts', async () => {
    const root = mkdtempSync(join(tmpdir(), 'platform-package-'));
    try {
        cpSync(resolve('operations'), join(root, 'operations'), {recursive: true, filter: path => !path.includes('/.work') && !path.includes('private') && !path.includes('secrets.local')});
        const secret = join(root, 'operations/k3s/environments/local/secrets.local.yaml');
        mkdirSync(join(root, 'operations/images/local'), {recursive: true});
        writeFileSync(secret, '{"apiVersion":"v1","kind":"List","items":[]}');
        rmSync(join(root, 'operations/k3s/scripts'), {recursive: true, force: true});
        writeFileSync(join(root, 'kubeconfig'), 'test');
        const calls = []; let rendered;
        const config = {environment: 'local', namespace: 'federation-learning-platform', kubeconfigFile: 'kubeconfig',
            platform: 'linux/arm64', imagePrefix: 'registry.example/test', imageVersion: 'test', gatewayUrl: 'http://localhost:30080',
            bindings: {artifactPublicBaseUrl: 'https://gateway.example/api/support'},
            images: {'federation-learning-aggregation-runtime-engine': 'registry.example/test/engine:sklearn'},
            instances: ['postgres','umadb','apisix','federation-learning-platform','federation-learning-support','federation-learning-console','federation-learning-aggregation-runtime-engine'].map(service => ({service, replicas: 1}))};
        const execute = (command, args, input) => {
            calls.push([command, ...args]);
            if (command === 'docker') {
                if (args[0] === 'pull') return '';
                const repo = args.at(-1).replace(/:[^/:]+$/, '');
                return JSON.stringify([{Id: 'sha256:test', Os: 'linux', Architecture: 'arm64', RepoDigests: [repo + '@sha256:' + 'a'.repeat(64)]}]);
            }
            assert.equal(command, 'kubectl');
            const clean = args.slice(2);
            if (clean[0] === 'kustomize') return execFileSync('kubectl', clean, {encoding: 'utf8'});
            if (clean.includes('nodes')) return JSON.stringify({items: [{metadata: {name: 'node'}, status: {nodeInfo: {architecture: 'arm64'}, conditions: [{type: 'Ready', status: 'True'}]}}]});
            if (clean[0] === 'apply' && input?.includes('kind: Deployment')) rendered = input;
            return '';
        };
        const adapter = createPlatformDeployment(config, {root, execute, fetchImpl: async () => ({ok: true})});
        await adapter.validateConnection(); await adapter.prepareImages(); await adapter.deploy(); await adapter.waitDeploymentReady();
        assert.match(rendered, /registry.example\/test\/federation-learning-platform@sha256:/);
        assert.match(rendered, /federation-learning-platform-local-secret/);
        assert.match(rendered, /https:\/\/gateway.example\/api\/support/);
        assert.equal((rendered.match(/MEDOL_SECURITY_ALLOWED_ORIGINS: http:\/\/localhost:30080/g) || []).length, 2);
        assert.ok(calls.some(call => call[0] === 'docker' && call.includes('registry.example/test/engine:sklearn')));
        assert.doesNotMatch(rendered, /name: federation-learning-runtime-agent\n/);
        assert.ok(calls.every(call => ['docker', 'kubectl'].includes(call[0])));
        assert.ok(adapter.deploymentSnapshot().images.length > 0);
        assert.doesNotMatch(readFileSync(new URL('../runtime/deploy.mjs', import.meta.url), 'utf8'), /training-environment|skip-cluster|runK3d/);
    } finally { rmSync(root, {recursive: true, force: true}); }
});
