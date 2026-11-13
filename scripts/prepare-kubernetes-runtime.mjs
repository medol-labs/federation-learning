#!/usr/bin/env node
import {existsSync, mkdirSync, readFileSync, writeFileSync} from 'node:fs';
import {dirname, join, relative, resolve} from 'node:path';
import {fileURLToPath} from 'node:url';

const root = resolve(dirname(fileURLToPath(import.meta.url)), '..');
const args = {};
for (let i = 2; i < process.argv.length; i += 2) {
    const key = process.argv[i];
    if (!['--environment', '--site', '--node', '--engine-image', '--plugin-profile', '--dataset-dir', '--work-dir'].includes(key) || !process.argv[i + 1] || process.argv[i + 1].startsWith('--')) {
        throw new Error(`Invalid option: ${key}`);
    }
    args[key.slice(2)] = process.argv[i + 1];
}
const env = args.environment;
const site = args.site;
if (!/^[a-zA-Z0-9_-]+$/.test(env ?? '') || !['platform', 'participant'].includes(site) || !args.node || !args['engine-image']) {
    throw new Error('Usage: node scripts/prepare-kubernetes-runtime.mjs --environment <env> --site platform|participant --node <node-hostname-label> --engine-image <image:tag> [--plugin-profile pytorch-vision] [--dataset-dir /data/datasets] [--work-dir /data/runtime-engine]');
}
const source = join(root, 'operations/kubernetes/environments', env, 'sites', site);
const model = JSON.parse(readFileSync(join(root, 'operations/model', env, 'operations-model.json'), 'utf8'));
if (!existsSync(join(source, 'kustomization.yaml'))) throw new Error(`Generate Kubernetes site configuration first: ${source}`);
const output = join(root, 'operations/kubernetes/overlays/prestarted', env, site);
const participant = site === 'participant';
const app = participant ? 'federation-learning-runtime-agent' : 'federation-learning-platform';
const engine = participant ? 'training-engine' : 'aggregation-engine';
const configName = `${app}-${env}-config`;
const secretName = `${app}-${env}-secret`;
const datasetPath = args['dataset-dir'] ?? '/workspace/datasets';
const workPath = args['work-dir'] ?? `/workspace/tmp/${engine}`;
for (const path of [datasetPath, workPath]) if (!path.startsWith('/')) throw new Error('Node data and work paths must be absolute.');
const nodeSelector = {'kubernetes.io/hostname': args.node};
const volumes = [{name: 'runtime-work', hostPath: {path: workPath, type: 'Directory'}}];
const mounts = [{name: 'runtime-work', mountPath: '/workspace/tmp/runtime-engine'}];
if (participant) {
    volumes.push({name: 'runtime-datasets', hostPath: {path: datasetPath, type: 'Directory'}});
    mounts.push({name: 'runtime-datasets', mountPath: '/workspace/datasets', readOnly: true});
}
const envValues = participant ? {
    RUNTIME_AGENT_LOCAL_RUNTIME_ENGINE_ENABLED: 'true',
    RUNTIME_AGENT_LOCAL_RUNTIME_ENGINE_MODE: 'prestarted',
    RUNTIME_AGENT_LOCAL_RUNTIME_ENGINE_PRESTARTED_IMAGE: args['engine-image'],
    RUNTIME_AGENT_LOCAL_RUNTIME_ENGINE_ENDPOINT: `http://${engine}:8080`,
    RUNTIME_AGENT_LOCAL_RUNTIME_ENGINE_NODE_NAME: 'participant-runtime',
    RUNTIME_AGENT_LOCAL_RUNTIME_ENGINE_DATASET_HOST_ROOT: '/workspace/datasets',
    RUNTIME_AGENT_LOCAL_RUNTIME_ENGINE_DATASET_CONTAINER_ROOT: '/workspace/datasets',
    RUNTIME_AGENT_LOCAL_RUNTIME_ENGINE_RUNTIME_ROOT: '/workspace/tmp/runtime-engine',
    RUNTIME_AGENT_LOCAL_RUNTIME_ENGINE_RUNTIME_ROOT_HOST_ROOT: '/workspace/tmp/runtime-engine'
} : {
    FL_AGGREGATION_MODE: 'runtime-engine',
    FL_AGGREGATION_RUNTIME_ENGINE_ENDPOINT: `http://${engine}:8080`
};
const valueEnv = values => Object.entries(values).map(([name, value]) => ({name, value}));
const httpProbe = {httpGet: {path: '/healthz', port: 8080}, periodSeconds: 5, timeoutSeconds: 3, failureThreshold: 12};
const engineEnv = valueEnv({
    RUNTIME_ENGINE_HOST: '0.0.0.0', RUNTIME_ENGINE_PORT: '8080',
    RUNTIME_ENGINE_NODE_NAME: participant ? 'participant-runtime' : 'platform-aggregator',
    RUNTIME_ENGINE_ROOT: '/workspace/tmp/runtime-engine',
    RUNTIME_ENGINE_PLUGIN_PROFILE: args['plugin-profile'] ?? 'pytorch-vision'
});
engineEnv.push({name: 'MEDOL_SECURITY_INTERNAL_TOKEN', valueFrom: {secretKeyRef: {name: secretName, key: 'MEDOL_SECURITY_INTERNAL_TOKEN'}}});
engineEnv.push(participant
    ? {name: 'RUNTIME_ENGINE_FILE_ACCESS_BASE_URL', valueFrom: {configMapKeyRef: {name: configName, key: 'FEDERATION_LEARNING_SUPPORT_URL'}}}
    : {name: 'RUNTIME_ENGINE_FILE_ACCESS_BASE_URL', value: 'http://federation-learning-support:8080'});
const resources = [
    {apiVersion: 'apps/v1', kind: 'Deployment', metadata: {name: engine}, spec: {
        replicas: 1, strategy: {type: 'Recreate'}, selector: {matchLabels: {app: engine}},
        template: {metadata: {labels: {app: engine}}, spec: {
            automountServiceAccountToken: false, nodeSelector,
            ...(participant ? {tolerations: [{key: 'medol.dev/runtime-only', operator: 'Equal', value: 'true', effect: 'NoSchedule'}]} : {}),
            containers: [{name: engine, image: args['engine-image'], imagePullPolicy: 'IfNotPresent',
                ports: [{containerPort: 8080}], env: engineEnv, volumeMounts: mounts,
                readinessProbe: httpProbe, startupProbe: {...httpProbe, failureThreshold: 60}}], volumes
        }}
    }},
    {apiVersion: 'v1', kind: 'Service', metadata: {name: engine}, spec: {
        type: 'ClusterIP', selector: {app: engine}, ports: [{name: 'http', port: 8080, targetPort: 8080}]
    }}
];
const patch = {apiVersion: 'apps/v1', kind: 'Deployment', metadata: {name: app}, spec: {
    replicas: 1, strategy: {type: 'Recreate', rollingUpdate: null},
    template: {spec: {
        ...(participant ? {automountServiceAccountToken: false, serviceAccountName: 'default', nodeSelector,
            tolerations: [{key: 'medol.dev/runtime-only', operator: 'Equal', value: 'true', effect: 'NoSchedule'}], volumes} : {}),
        containers: [{name: app, env: valueEnv(envValues), ...(participant ? {volumeMounts: mounts} : {})}]
    }}
}};
mkdirSync(output, {recursive: true});
const write = (name, object) => writeFileSync(join(output, name), JSON.stringify(object, null, 2) + '\n');
write('engine.yaml', {apiVersion: 'v1', kind: 'List', items: resources});
write('agent-config.yaml', patch);
write('kustomization.yaml', {apiVersion: 'kustomize.config.k8s.io/v1beta1', kind: 'Kustomization',
    namespace: model.name,
    resources: [relative(output, source), 'engine.yaml'], patches: [{path: 'agent-config.yaml'}]});
console.log(`Prepared ${output}`);
console.log(`Create ${workPath}${participant ? ` and ${datasetPath}` : ''} on node ${args.node}; configure site Secrets and remote URLs before applying.`);
console.log(`kubectl apply -k ${relative(root, output)}`);
