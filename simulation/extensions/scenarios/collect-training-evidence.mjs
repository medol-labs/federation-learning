import {readFileSync, writeFileSync, mkdirSync, readdirSync, copyFileSync} from 'node:fs';
import {resolve, join} from 'node:path';
import {createHash} from 'node:crypto';
import {execFileSync} from 'node:child_process';
import {parseEnv} from 'node:util';

const [runId] = process.argv.slice(2);
if (!/^[a-zA-Z0-9_.-]+$/.test(runId || '')) throw new Error('Provide a simulation run ID');
const root = process.cwd(), directory = resolve(root, 'simulation/reports/workflows', runId);
const read = file => JSON.parse(readFileSync(join(directory, file), 'utf8'));
const run = read('run.json'), test = read('test.json'), deployment = run.deployment.manifest;
const verification = test.verification;
if (test.status !== 'passed' || !verification?.rounds?.length) throw new Error('Successful detailed round evidence is required');
const output = join(directory, 'evidence');
mkdirSync(output, {recursive: true});
const write = (file, value) => writeFileSync(join(output, file), typeof value === 'string' ? value : JSON.stringify(value, null, 2) + '\n');
const env = parseEnv(readFileSync(join(root, '.env'), 'utf8'));
if (!env.MEDOL_SECURITY_INTERNAL_TOKEN) throw new Error('Evidence collection requires configured service credentials');
const headers = {'X-MEDOL-INTERNAL-TOKEN': env.MEDOL_SECURITY_INTERNAL_TOKEN};
const fetchJson = async url => { const r = await fetch(url, {headers, redirect: 'error', signal: AbortSignal.timeout(30000)}); if (!r.ok) throw new Error(`Evidence query HTTP ${r.status}`); return r.json(); };
const kubectl = (...args) => JSON.parse(execFileSync('kubectl', ['--kubeconfig', deployment.kubeconfigFile, ...args, '-o', 'json'], {encoding: 'utf8', maxBuffer: 32 * 1024 * 1024}));
const nodes = kubectl('get', 'nodes').items.map(n => ({name: n.metadata.name, architecture: n.status.nodeInfo.architecture,
    cpu: n.status.capacity.cpu, memory: n.status.capacity.memory, ready: n.status.conditions.find(c => c.type === 'Ready')?.status}));
const pods = kubectl('get', 'pods', '-A').items.map(p => ({namespace: p.metadata.namespace, name: p.metadata.name,
    node: p.spec.nodeName, phase: p.status.phase, containers: (p.status.containerStatuses || []).map(c => ({name: c.name, ready: c.ready, image: c.image, imageID: c.imageID, restarts: c.restartCount}))}));
write('nodes.json', nodes); write('pods.json', pods);
write('deployment.json', deployment);
for (const file of ['run.json', 'execution-plan.json', 'baseline.json', 'participant-auth.json', 'data.json', 'test.json']) copyFileSync(join(directory, file), join(output, file));
for (const file of readdirSync(directory).filter(name => name.endsWith('.failure.json'))) copyFileSync(join(directory, file), join(output, file));
const jobs = await fetchJson(deployment.endpoints.platform + '/trainingjob/trainingjobdashboard?' + new URLSearchParams({'trainingJobId.equals': test.trainingJobId}));
write('training-job-api.json', jobs);
const job = jobs.content?.find(j => j.trainingJobId === test.trainingJobId);
if (String(job?.state).toUpperCase() !== 'COMPLETED') throw new Error('Job is not completed in the current API snapshot');
const configurations = await fetchJson(deployment.endpoints.platform + '/trainingrunconfiguration/trainingrunconfigurationcatalog?' + new URLSearchParams({'trainingRunConfigurationId.equals': job.trainingRunConfigurationId}));
write('training-configuration-api.json', configurations);
const configuration = configurations.content?.find(c => c.trainingRunConfigurationId === job.trainingRunConfigurationId);
if (configuration?.maxRounds !== verification.completedRoundCount || configuration?.minimumNodesPerRound !== verification.participantCount) throw new Error('Configuration does not match acceptance evidence');
const rows = [];
for (const item of verification.rounds) {
    const modelUrl = new URL(item.model.modelArtifactUri), support = new URL(deployment.endpoints.support);
    if (modelUrl.origin !== support.origin || !modelUrl.pathname.startsWith(support.pathname + '/')) throw new Error('Unexpected artifact origin');
    const response = await fetch(modelUrl, {headers, redirect: 'error', signal: AbortSignal.timeout(60000)});
    if (!response.ok) throw new Error('Cannot collect model artifact');
    const bytes = Buffer.from(await response.arrayBuffer());
    const digest = 'sha256:' + createHash('sha256').update(bytes).digest('hex');
    if (digest !== item.artifact.digest) throw new Error('Model digest mismatch');
    writeFileSync(join(output, `round-${item.round.roundNumber}-global-model.json`), bytes);
    for (const e of item.executions) rows.push({round: item.round.roundNumber, runtimeId: e.runtimeId, executionId: e.roundExecutionId,
        updateArtifactId: e.updateArtifactId, state: e.state, baseModelId: e.baseModelId, aggregatedModelId: item.round.aggregatedModelId});
}
write('participant-rounds.json', rows);
const columns = Object.keys(rows[0]);
write('participant-rounds.csv', [columns.join(','), ...rows.map(row => columns.map(key => JSON.stringify(String(row[key] ?? ''))).join(','))].join('\n') + '\n');
const summary = {collectedAt: new Date().toISOString(), runId, environmentId: run.environmentId, instanceId: run.instanceId,
    deploymentId: deployment.deploymentId, trainingJobId: test.trainingJobId, status: test.status,
    participantCount: verification.participantCount, rounds: verification.completedRoundCount, executions: verification.acceptedExecutionCount,
    uniqueRuntimeIds: new Set(rows.map(row => row.runtimeId)).size, infrastructure: {host: new URL(deployment.endpoints.gateway).hostname, platform: deployment.platform, workerNodes: run.environment.definition.workerNodes},
    limitations: ['Single physical host, independently deployed participant agents; not a cross-host performance benchmark.',
        'Each participant uses the same synthetic 32-row CSV fixture; this is functional capacity evidence, not real-world model quality evidence.',
        'The configuration enables the secure-aggregation workflow, but the development engine performs FED_AVG_JSON; this run does not certify cryptographic secure aggregation.',
        'The UI globalAccuracy of 0.9 is a fixed development evaluation-adapter value, not a measured holdout accuracy.',
        'Platform and agent ARM64 images were rebuilt for cross-round metadata and artifact URL fixes; engine, support and console images were reused.'], artifacts: verification.rounds.map(r => r.artifact)};
write('summary.json', summary);
const escape = value => String(value ?? '').replace(/[&<>"']/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
const table = (keys, items) => `<table><thead><tr>${keys.map(k => `<th>${escape(k)}</th>`).join('')}</tr></thead><tbody>${items.map(item => `<tr>${keys.map(k => `<td>${escape(item[k])}</td>`).join('')}</tr>`).join('')}</tbody></table>`;
write('report.html', `<!doctype html><html lang="en"><meta charset="utf-8"><title>Federated Training Evidence</title><style>body{font:14px Arial,sans-serif;color:#17242d;margin:40px;line-height:1.5}h1{font-size:28px}h2{margin-top:30px}table{border-collapse:collapse;width:100%;font-size:11px}td,th{border:1px solid #cbd5dc;padding:7px;text-align:left;overflow-wrap:anywhere}th{background:#edf4f3}code{overflow-wrap:anywhere}.pass{color:#007e64}@media print{body{margin:15px}tr{break-inside:avoid}h2{break-after:avoid}}</style><h1>Federated Learning: 20-Participant Acceptance Evidence</h1><p class="pass">Result: ${escape(summary.status.toUpperCase())}</p><p>${summary.participantCount} participants / ${summary.rounds} linked rounds / ${summary.executions} verified participant executions</p><p>Collected: ${escape(summary.collectedAt)}<br>Run: ${escape(runId)}<br>Job: <code>${escape(test.trainingJobId)}</code></p><h2>Scope and limitations</h2><ul>${summary.limitations.map(s=>`<li>${escape(s)}</li>`).join('')}</ul><h2>Verified rounds</h2>${table(['roundNumber','selectedRuntimeCount','acceptedModelUpdateCount','baseModelId','aggregatedModelId'],verification.rounds.map(r=>r.round))}<h2>Model integrity</h2>${table(['modelId','bytes','digest'],summary.artifacts)}<h2>Participant execution matrix</h2>${table(columns,rows)}<h2>Evidence files</h2><p>Raw API response, deployment/image identities, nodes/pods, test assertions, per-participant execution snapshots and downloaded model artifacts accompany this report. SHA256SUMS.json provides file hashes. This report is generated from collected evidence, not a screenshot of the application.</p></html>`);
const hashes = Object.fromEntries(readdirSync(output).filter(f => f !== 'SHA256SUMS.json').map(f => [f, createHash('sha256').update(readFileSync(join(output, f))).digest('hex')]));
write('SHA256SUMS.json', hashes);
console.log(JSON.stringify({output, summary}, null, 2));
