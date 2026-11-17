import {readFileSync, writeFileSync, mkdirSync} from 'node:fs';
import {resolve, join} from 'node:path';
import {parseEnv} from 'node:util';
import {execFileSync} from 'node:child_process';
import {verifyArtifact} from './verify-artifact.mjs';

const [runId, trainingJobId] = process.argv.slice(2);
if (!/^[\w.-]+$/.test(runId || '') || !/^[a-f\d-]{36}$/i.test(trainingJobId || '')) {
    throw new Error('Usage: collect-training-snapshot.mjs <runId> <trainingJobId>');
}
const directory = resolve('simulation/reports/workflows', runId);
const run = JSON.parse(readFileSync(join(directory, 'run.json'), 'utf8'));
const deployment = run.deployment.manifest;
const output = join(directory, `snapshot-${trainingJobId}-${Date.now()}`);
mkdirSync(output, {recursive: true});
const env = parseEnv(readFileSync('.env', 'utf8'));
if (!env.MEDOL_SECURITY_INTERNAL_TOKEN) throw new Error('Service credentials are required');
const headers = {'X-MEDOL-INTERNAL-TOKEN': env.MEDOL_SECURITY_INTERNAL_TOKEN};
const write = (name, value) => writeFileSync(join(output, name), JSON.stringify(value, null, 2) + '\n');
async function query(base, path, criteria = {}) {
    const url = base + path + '?' + new URLSearchParams({...criteria, size: 100});
    const response = await fetch(url, {headers, redirect: 'error', signal: AbortSignal.timeout(30000)});
    if (!response.ok) throw new Error(`Snapshot query HTTP ${response.status}: ${path}`);
    return response.json();
}
const criteria = {'trainingJobId.equals': trainingJobId};
const jobs = await query(deployment.endpoints.platform, '/trainingjob/trainingjobdashboard', criteria);
const rounds = await query(deployment.endpoints.platform, '/traininground/trainingroundprogress', criteria);
write('jobs.json', jobs); write('rounds.json', rounds);
write('runtime-identities.json', await query(deployment.endpoints.platform, '/runtimeidentity/runtimeidentitycatalog'));
const participants = [];
for (const participant of deployment.participantDeployments) {
    const executions = await query(participant.consoleUrl + '/api/federation-learning-runtime-agent', '/roundexecution/roundexecutioncatalog', criteria);
    participants.push({slot: participant.slot, namespace: participant.namespace, executions: executions.content || []});
}
write('participants.json', participants);
const models = [];
for (const round of rounds.content || []) {
    if (!round.aggregatedModelId) continue;
    const page = await query(deployment.endpoints.platform, '/modelartifact/modelartifactcatalog', {'modelId.equals': round.aggregatedModelId});
    const model = page.content?.find(item => item.modelId === round.aggregatedModelId);
    if (!model) continue;
    const artifact = await verifyArtifact(model, [deployment.endpoints.platform, deployment.endpoints.support], () => headers);
    models.push({roundNumber: round.roundNumber, model, artifact});
    const response = await fetch(model.modelArtifactUri, {headers, redirect: 'error', signal: AbortSignal.timeout(30000)});
    if (!response.ok) throw new Error('Artifact download failed');
    writeFileSync(join(output, `round-${round.roundNumber}-model.json`), Buffer.from(await response.arrayBuffer()));
}
write('models.json', models);
const kube = ['--kubeconfig', deployment.kubeconfigFile];
const nodes = JSON.parse(execFileSync('kubectl', [...kube, 'get', 'nodes', '-o', 'json'], {encoding: 'utf8'}));
write('nodes.json', nodes.items.map(node => ({name: node.metadata.name, architecture: node.status.nodeInfo.architecture,
    ready: node.status.conditions.find(c => c.type === 'Ready')?.status})));
const summary = {capturedAt: new Date().toISOString(), runId, trainingJobId, environmentId: run.environmentId,
    instanceId: run.instanceId, status: 'diagnostic-snapshot-not-acceptance',
    jobState: jobs.content?.find(job => job.trainingJobId === trainingJobId)?.state,
    deployedParticipants: participants.length, verifiedModelArtifacts: models.length,
    rounds: rounds.content?.map(round => ({number: round.roundNumber, state: round.state,
        selected: round.selectedRuntimeCount, accepted: round.acceptedModelUpdateCount, reason: round.failureReason}))};
write('summary.json', summary);
console.log(JSON.stringify({output, summary}, null, 2));
