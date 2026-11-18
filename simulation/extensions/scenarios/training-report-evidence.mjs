import {createHash} from 'node:crypto';

export async function resolveTrainingEvidence({test, plan, data, deployment}, query) {
  const fingerprint = createHash('sha256').update(JSON.stringify(plan)).digest('hex');
  if (test.status !== 'passed' || test.planFingerprint !== fingerprint || data.casePlanFingerprint !== fingerprint ||
      data.deploymentId !== deployment.deploymentId) throw new Error('Report, plan and deployment bindings differ');
  const cases = test.cases.filter(c => c.id === 'participants-complete-training' && c.status === 'passed');
  const items = cases.length === 1 && cases[0].steps.find(s => s.id === 'rounds' && s.status === 'passed')?.output?.items;
  const participants = data.records[0]?.participants;
  if (!items?.length || !participants?.length || new Set(participants.map(p=>p.runtimeId)).size !== participants.length) throw new Error('Incomplete training evidence');
  const rounds = [], ids = new Set(), artifacts = new Set();
  let trainingJobId;
  for (const item of items) {
    const round = await query('platform', '/traininground/trainingroundprogress', {roundId:item.roundId});
    if (!round.trainingJobId || (trainingJobId && round.trainingJobId !== trainingJobId) || round.roundNumber !== rounds.length+1 ||
        round.aggregatedModelId !== item.modelId || round.acceptedModelUpdateCount !== participants.length ||
        String(round.state).toUpperCase() !== 'COMPLETED' || item.executions.length !== participants.length ||
        (rounds.length && round.baseModelId !== rounds.at(-1).round.aggregatedModelId)) throw new Error('Round does not match report');
    trainingJobId = round.trainingJobId;
    const model = await query('platform','/modelartifact/modelartifactcatalog',{modelId:item.modelId});
    const digest = String(model.modelArtifactDigest || '').replace(/^sha256:/i,'').toLowerCase();
    if (!item.artifact.verified || !/^[0-9a-f]{64}$/.test(digest) || 'sha256:'+digest !== item.artifact.digest) throw new Error('Model digest differs from report');
    const executions = [];
    for (const p of participants) {
      const e = await query(p.slot,'/roundexecution/roundexecutioncatalog',{trainingJobId,roundId:item.roundId,runtimeId:p.runtimeId}, {'updateArtifactId.specified':true});
      if (!e.roundExecutionId || !e.updateArtifactId || ids.has(e.roundExecutionId) || artifacts.has(e.updateArtifactId) ||
          !item.executions.some(x=>x.executionId===e.roundExecutionId && x.artifactId===e.updateArtifactId)) throw new Error('Participant execution differs from report');
      ids.add(e.roundExecutionId); artifacts.add(e.updateArtifactId);
      executions.push({...e,slot:p.slot, runtimeAgentId:p.runtimeAgentId, organizationId:p.organizationId});
    }
    rounds.push({round,model,executions,artifact:{...item.artifact,modelId:item.modelId}});
  }
  return {trainingJobId, rounds, participantCount:participants.length, completedRoundCount:rounds.length, acceptedExecutionCount:ids.size};
}
