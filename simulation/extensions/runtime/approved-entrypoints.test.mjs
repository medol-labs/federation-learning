import test from 'node:test';
import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {select} from '../../src/workflow-tasks.mjs';
import {prepare} from '../adapters/compose-training.mjs';
import {prepareCaseData} from './training-stages.mjs';
import {createHttpExecutor} from '../../src/http-case-executor.mjs';
import {bootstrapParticipant} from '../adapters/participant-bootstrap.mjs';

const read = path => JSON.parse(readFileSync(new URL('../../../'+path,import.meta.url)));
for (const file of ['tasks','compose-tasks']) test(file+' accepts selected approved training workflow in preparation and test', async()=>{
  const tasks=read('simulation/pipelines/'+file+'.json').tasks, inputs=tasks['select-cases'].inputs;
  const cases=select({library:read(inputs.library.path),selection:read(inputs.selection.path)},{});
  assert.equal(cases.cases[0].id,'participants-complete-training');
  assert.equal(tasks.test.module,'simulation/src/workflow-tasks.mjs');
  const config=read(tasks.test.inputs.config.path); delete config.envFile;
  await createHttpExecutor(config).validatePlan(cases);
  const environment={status:'ready',environment:'local',deploymentId:'deployment',target:'k3d',kubeconfigFile:'config',endpoints:{support:'http://support',platform:'http://platform'},participantDeployments:[{slot:1},{slot:2}]};
  const baseline={...environment,schemaVersion:1,mode:'baseline',records:[]};
  const stop=()=>{throw new Error('infrastructure boundary reached');};
  const action=file==='tasks'?()=>prepareCaseData(environment,read(tasks['prepare-case'].inputs.scenario.path),baseline,cases,stop)
    :()=>prepare({environment:{...environment,target:'docker-compose'},baseline,cases},{},stop);
  await assert.rejects(action,/infrastructure boundary reached/);
});

test('shared bootstrap sends one command and resumes against the projected endpoint',async()=>{
  const args={platform:'http://platform',participant:'http://agent',runtimeAgentId:'a',runtimeInfrastructureId:'i',timeoutMs:5000};
  let connected=false,writes=0;
  const fetcher=async(url,options)=>{
    if(url.pathname==='/actuator/health')return Response.json({status:'UP'});
    if(options.method==='POST'){assert.equal(url.origin,'http://agent');writes++;connected=true;return new Response(null,{status:202});}
    assert.equal(url.searchParams.get('runtimeAgentId.equals'),'a');assert.equal(url.searchParams.get('runtimeInfrastructureId.equals'),'i');
    return Response.json({content:connected?[{runtimeAgentEndpoint:'http://agent',runtimeId:'r'}]:[]});
  };
  for(let n=0;n<2;n++)assert.equal((await bootstrapParticipant(args,h=>h,fetcher)).runtimeId,'r');
  assert.equal(writes,1);
});
