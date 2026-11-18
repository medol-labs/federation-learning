import test from 'node:test';
import assert from 'node:assert/strict';
import {createHash} from 'node:crypto';
import {readFileSync} from 'node:fs';
import {resolveTrainingEvidence} from './training-report-evidence.mjs';

test('joins by execution identity independent of participant order and rejects mismatched updates', async()=>{
  const plan={cases:[{id:'participants-complete-training'}]}, fp=createHash('sha256').update(JSON.stringify(plan)).digest('hex');
  const participants=[2,1].map(slot=>({slot,runtimeId:'r'+slot}));
  const digest='a'.repeat(64);
  const item={roundId:'round',modelId:'model',artifact:{verified:true,digest:'sha256:'+digest,bytes:130},
    executions:[1,2].map(slot=>({executionId:'e'+slot,artifactId:'a'+slot}))};
  const inputs={plan,data:{casePlanFingerprint:fp,deploymentId:'dep',records:[{participants}]},deployment:{deploymentId:'dep'},
    test:{status:'passed',planFingerprint:fp,cases:[{id:plan.cases[0].id,status:'passed',steps:[{id:'rounds',status:'passed',output:{items:[item]}}]}]}};
  const query=async(slot,path)=>path.endsWith('progress')?{trainingJobId:'job',roundNumber:1,aggregatedModelId:'model',acceptedModelUpdateCount:2,state:'Completed'}:
    path.endsWith('modelartifactcatalog')?{modelArtifactDigest:digest}:{runtimeId:'r'+slot,roundExecutionId:'e'+slot,updateArtifactId:'a'+slot};
  const evidence=await resolveTrainingEvidence(inputs,query);
  assert.deepEqual(evidence.rounds[0].executions.map(e=>e.roundExecutionId),['e2','e1']);
  item.executions[0].artifactId='unrelated';
  await assert.rejects(resolveTrainingEvidence(inputs,query),/execution differs/);
});

test('notification page size one asserts two total elements and the second page index',()=>{
  const plan=JSON.parse(readFileSync(new URL('../../cases/manual/system-notification.json',import.meta.url)));
  const c=plan.cases.find(c=>c.id==='system-notification-filter-and-pagination');
  const response={content:[{}],page:{number:1,size:1,totalPages:2,totalElements:2}};
  const second=c.steps.find(s=>s.id==='page-two');
  for(const [path,value] of Object.entries(second.request.expect)) assert.equal(path.split('.').reduce((o,k)=>o[k],response),value);
  assert.equal(c.steps.find(s=>s.id==='page').request.expect['page.totalPages'],2);
  assert.ok(c.assertions.some(a=>a.path==='page.totalElements' && a.expected===2));
});
