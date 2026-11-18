import {readFileSync} from 'node:fs';
import {runHttpWorkflow} from '../../src/http-workflow.mjs';

const plan = JSON.parse(readFileSync(new URL('../../cases/approved/participant-bootstrap.json', import.meta.url)));
export async function bootstrapParticipant({platform, participant, runtimeAgentId, runtimeInfrastructureId, timeoutMs}, headers, fetchImpl = fetch) {
  const environment = {environment: 'participant-bootstrap', deploymentId: runtimeInfrastructureId, status:'ready', endpoints:{platform, participant}};
  const report = await runHttpWorkflow(plan, environment, {runtimeAgentId,runtimeInfrastructureId,timeoutMs},
    {endpoints:{platform:{anonymous:true},participant:{anonymous:true}},probes:[{endpoint:'participant',path:'/actuator/health'}]},
    {fetchImpl:(url, options)=>fetchImpl(url,{...options,headers:headers(options.headers,url.href)})});
  return report.cases[0].exports.endpoint;
}
