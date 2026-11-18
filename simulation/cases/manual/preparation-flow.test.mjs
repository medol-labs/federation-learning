import test from 'node:test';
import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {approveCases} from '../../src/cases.mjs';
import {executeCases} from '../../src/execute-cases.mjs';
import {createHttpExecutor} from '../../src/http-case-executor.mjs';

const read = name => JSON.parse(readFileSync(new URL(name, import.meta.url), 'utf8'));

test('registration, approval and submission run from approved cases and resume without writes', async () => {
  const runtime = {runtimeAgentId:'agent',runtimeInfrastructureId:'infra',runtimeName:'runtime'};
  const dataset = {datasetId:'d',organizationId:'org',featureSchemaId:'schema',datasetName:'demo'};
  const job = {trainingRunConfigurationId:'cfg',trainingJobId:'j'};
  const inputs = {runtime, organization:{organizationId:'org',organizationName:'Org'}, installMode:'MANUAL',scope:'CLUSTER',endpoint:'http://agent',
    dataset,requireReady:true,metadata:{runtimeDatasetBindingId:'binding'},job,timeoutMs:100,pollIntervalMs:1};
  const env = {status:'ready',environment:'env',deploymentId:'dep',endpoints:{platform:'http://platform', 'runtime-agent':'http://agent'}};
  const data = {schemaVersion:1,status:'ready',environment:'env',deploymentId:'dep',records:[],inputs};
  const rows = {dataset:{...dataset,metadataStatus:'Reported',contractStatus:'Validated'}, trainingrunconfiguration:{state:'Draft'}};
  const writes = [];
  const adapter = createHttpExecutor({endpoints:{platform:{anonymous:true},'runtime-agent':{anonymous:true}},probes:[{endpoint:'platform',path:'/health'}]},{
    fetchImpl:async(url,opts)=>{
      const [,resource,cmd] = url.pathname.split('/');
      if(opts.method==='POST'){
        writes.push(cmd);
        rows[resource]={...rows[resource],...JSON.parse(opts.body)};
        if(cmd==='activateruntimeidentity') rows[resource].runtimeId='platform-assigned';
        if(cmd==='approvedatasetfortraining') rows[resource].approved=true;
        if(cmd==='submittrainingjob'){rows[resource].state='Submitted';rows.trainingrunconfiguration.state='Locked';}
        return new Response(null,{status:202});
      }
      return Response.json({content:rows[resource]?[rows[resource]]:[]});
    }
  });
  for(const name of ['runtime-identity-registration','dataset-approval-metadata','training-job-submission']){
    const plan=read('../approved/'+name+'.json');
    assert.deepEqual(plan,approveCases(read('./'+name+'.json'),plan.cases[0].approval.reviewer));
    for(let n=0;n<2;n++){
      const count=writes.length, result=await executeCases(plan,env,data,adapter);
      assert.equal(result.status,'passed',JSON.stringify(result));
      if(n)assert.equal(writes.length,count);
      if(name==='runtime-identity-registration')assert.equal(result.cases[0].exports.runtimeId,'platform-assigned');
    }
  }
  assert.deepEqual(writes,['activateruntimeidentity','recordruntimeconnectionestablished','approvedatasetfortraining','recordruntimedatasetmetadata','createtrainingjob','submittrainingjob']);
});

test('enrollment, infrastructure registration and validation resume against their projections', async () => {
  const rows = new Map(), writes = [];
  const inputs = {activateLifecycle:true,register:true,organization:{organizationId:'o',organizationName:'Org'},federation:{federationId:'f',federationName:'Fed'},
    runtimeInfrastructurePackage:{runtimeInfrastructurePackageId:'p',packageName:'P',packageVersion:'1',runtimeEnvironmentType:'K3S'},
    runtimeInstallationPlan:{runtimeInstallationPlanId:'plan',agentInstallMode:'MANUAL',expectedNodeCount:1},
    runtime:{runtimeInfrastructureId:'seed-id',runtimeAgentId:'a',runtimeName:'Agent'},binding:{datasetId:'d',runtimeDatasetBindingId:'b'},
    accessValidation:{datasetAccessValidationId:'first',datasetId:'d',runtimeDatasetBindingId:'b'}, timeoutMs:100,pollIntervalMs:1};
  const states = {registerorganization:{state:'Registered'},activateorganization:{state:'Active'},createfederation:{state:'Suspended'},reactivatefederation:{state:'ACTIVE'},
    inviteparticipant:{state:'Invited'},approveparticipant:{membershipStatus:'Joined'},createruntimeinstallationplan:{runtimeInfrastructureId:'projected-id'},
    planruntimeinfrastructure:{state:'Planned'},registerruntimeinfrastructure:{state:'Registered'},validateagentdatasetaccess:{validationStatus:'Access_Validated'}};
  rows.set('agentdatasetaccessvalidation',{validationStatus:'Failed',datasetAccessValidationId:'first'});
  for (const name of ['participant-enrollment','infrastructure-registration','dataset-access-validation']) {
    const plan = read('../approved/'+name+'.json');
    assert.deepEqual(plan,approveCases(read('./'+name+'.json'),plan.cases[0].approval.reviewer));
    const endpoint = plan.cases[0].steps[0].request.endpoint;
    const env = {status:'ready',environment:'test',deploymentId:'one',endpoints:{[endpoint]:'http://test'}};
    const executor = createHttpExecutor({endpoints:{[endpoint]:{anonymous:true}},probes:[{endpoint,path:'/health'}]}, {fetchImpl:async(url,opts)=>{
      const [,resource,action] = url.pathname.split('/');
      if (opts.method==='POST') {
        const payload = JSON.parse(opts.body);
        if (resource==='runtimeinfrastructure') assert.equal(payload.runtimeInfrastructureId,'projected-id');
        if (action==='validateagentdatasetaccess') assert.notEqual(payload.datasetAccessValidationId,'first');
        writes.push(action); rows.set(resource,{...payload,...states[action]}); return new Response(null,{status:202});
      }
      return Response.json({content:rows.has(resource)?[rows.get(resource)]:[]});
    }});
    let count = writes.length;
    for(let i=0;i<2;i++){
      const result=await executeCases(plan,env,{schemaVersion:1,status:'ready',environment:'test',deploymentId:'one',records:[],inputs},executor);
      assert.equal(result.status,'passed',JSON.stringify(result));
      if(name==='infrastructure-registration') assert.equal(result.cases[0].exports.runtimeInfrastructureId,'projected-id');
      if(i===0) assert.ok(writes.length>count); else assert.equal(writes.length,count);
      count=writes.length;
    }
  }
});

for (const [name, endpoint, inputs, expectedWrites] of [
  ['training-configuration-preparation', 'platform', {runtimeEngineProfile: {runtimeEngineProfileId: 'profile'}, trainingRunConfiguration: {trainingRunConfigurationId: 'config'}}, 2],
  ['dataset-binding-preparation', 'runtime-agent', {binding: {runtimeId: 'node', datasetId: 'data', filePath: '/data/a'}}, 1],
]) test(`${name} waits for projections and resumes without duplicate writes`, async () => {
  const plan = read(`../approved/${name}.json`);
  assert.deepEqual(plan, approveCases(read(`./${name}.json`), plan.cases[0].approval.reviewer));
  const environment = {status:'ready', environment:'test', deploymentId:'one', endpoints:{[endpoint]:'http://api.test'}};
  const data = {schemaVersion:1, status:'ready', environment:'test', deploymentId:'one', records:[], inputs:{...inputs, timeoutMs:1000, pollIntervalMs:1}};
  const rows = new Map(), pending = new Set(); let writes = 0;
  const adapter = createHttpExecutor({endpoints:{[endpoint]:{anonymous:true}}, probes:[{endpoint,path:'/health'}]}, {fetchImpl:async(url, options) => {
    const key = url.pathname.split('/')[1];
    if (options.method === 'POST') { writes++; rows.set(key, JSON.parse(options.body)); pending.add(key); return new Response(null, {status:202}); }
    const row = pending.delete(key) ? null : rows.get(key);
    return Response.json({content:row ? [row] : []});
  }});
  for (let i = 0; i < 2; i++) {
    const report = await executeCases(plan,environment,data,adapter);
    assert.equal(report.status,'passed',JSON.stringify(report));
  }
  assert.equal(writes,expectedWrites);
  if (inputs.binding) {
    data.inputs.binding = {...inputs.binding, filePath:'/data/other'};
    const report = await executeCases(plan,environment,data,adapter);
    assert.equal(report.status,'failed'); assert.match(report.cases[0].error,/binding-ready/); assert.equal(writes,1);
  }
});
test('baseline configuration initializes once and resumes without duplicate commands', async () => {
  const plan = approveCases(read('./baseline.json'), 'test');
  const config = read('../../pipelines/preparation-http-executor.json');
  delete config.envFile;
  const environment = {status: 'ready', environment: 'test', deploymentId: 'deployment', endpoints: {support: 'http://support.test', platform: 'http://platform.test'}};
  const data = {schemaVersion: 1, status: 'ready', environment: 'test', deploymentId: 'deployment', records: [], inputs: {dictionaries: [{dictionaryCode: 'DEMO', dictionaryName: 'Demo', values: [{valueCode: 'VALUE', displayName: 'Value'}]}]}};
  const created = new Map(); let initialized = false, writes = 0;
  const adapter = createHttpExecutor(config, {env: {FL_K3D_ADMIN_USERNAME: 'test-user', FL_K3D_ADMIN_PASSWORD: 'secret-password', FL_K3D_ADMIN_SETUP_TOKEN: 'secret-setup'}, fetchImpl: async (url, options) => {
    let body, status = 200;
    if (url.pathname === '/actuator/health') body = {status: 'UP'};
    else if (url.pathname === '/api/auth/login') {status = initialized ? 200 : 401; body = initialized ? {accessToken: 'secret-token'} : {detail: 'Unauthorized'};}
    else if (url.pathname === '/api/auth/setup-admin') {initialized = true; body = {}; writes++;}
    else {
      assert.equal(options.headers.Authorization, 'Bearer secret-token');
      const resource = url.pathname.split('/')[1];
      if (options.method === 'POST') {body = JSON.parse(options.body); created.set(resource, body); writes++;}
      else {const row = created.get(resource); body = {page:{totalElements: row ? 1 : 0}, content: row ? [row] : []};}
    }
    return {ok: status < 300, status, json: async () => body};
  }});
  for (let i = 0; i < 2; i++) {
    const report = await executeCases(plan, environment, data, adapter);
    assert.equal(report.status, 'passed', JSON.stringify(report));
    assert.equal(report.cases[0].exports.authentication, 'initialized');
    assert.doesNotMatch(JSON.stringify(report), /secret-password|secret-setup|secret-token/);
  }
  assert.equal(writes, 4);
});

test('participant authentication iterates deployed endpoints without bespoke JS actions', async () => {
  const plan = approveCases(read('./participant-auth.json'), 'test');
  const config = read('../../pipelines/preparation-http-executor.json'); delete config.envFile;
  const environment = {status:'ready', environment:'test', deploymentId:'deployment', endpoints:{support:'http://support.test',platform:'http://platform.test'}, participantDeployments:[{slot:1,consoleUrl:'http://one.test'},{slot:2,consoleUrl:'http://two.test'}]};
  const endpoints = new Set();
  const adapter = createHttpExecutor(config, {env:{FL_K3D_ADMIN_USERNAME:'user',FL_K3D_ADMIN_PASSWORD:'password'},fetchImpl:async(url, options)=>{
    if (url.pathname.endsWith('/login')) endpoints.add(url.origin);
    return {ok:true,status:200,json:async()=>url.pathname.endsWith('/login')?{accessToken:'secret'}:url.pathname.endsWith('/health')?{status:'UP'}:{content:[]}};
  }});
  const report = await executeCases(plan, environment, {schemaVersion:1,status:'ready',environment:'test',deploymentId:'deployment',records:[]}, adapter);
  assert.equal(report.status,'passed',JSON.stringify(report));
  assert.deepEqual([...endpoints],['http://one.test','http://two.test']);
});
