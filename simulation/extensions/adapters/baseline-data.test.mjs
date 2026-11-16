import test from 'node:test';
import assert from 'node:assert/strict';
import {mkdtempSync, readFileSync, writeFileSync, rmSync} from 'node:fs';
import {tmpdir} from 'node:os';
import {join} from 'node:path';
import {prepareBaseline} from './baseline-data.mjs';

const originalPlan = JSON.parse(readFileSync(new URL('../../data/baseline-plan.json', import.meta.url)));
function fixture() {
    const root = mkdtempSync(join(tmpdir(), 'baseline-'));
    writeFileSync(join(root, 'init.mjs'), '');
    writeFileSync(join(root, 'dictionaries.json'), JSON.stringify({dictionaries: [{dictionaryCode: 'EXAMPLE', values: [{valueCode: 'ONE'}]}]}));
    return {root, config: {envFile: '.env',
        auth: {endpoint: 'auth', setupPath: '/setup', loginPath: '/login', usernameEnv: 'TEST_USER', passwordEnv: 'TEST_PASSWORD', setupTokenEnv: 'TEST_SETUP'},
        dictionary: {endpoint: 'auth', script: 'init.mjs', dataFile: 'dictionaries.json', catalogPath: '/catalog', valuesPath: '/values'}},
        environment: {status: 'ready', deploymentId: 'existing', environment: 'test', target: 'docker-compose',
            endpoints: {auth: 'http://auth/api', support: 'http://auth/api', platform: 'http://other/api'}}};
}
test('baseline uses existing HTTP endpoints without kubeconfig, nodes or training adapter', async () => {
    const input = fixture(); let logins = 0, runs = 0;
    const env = {TEST_USER: 'operator', TEST_PASSWORD: 'private-password', TEST_SETUP: 'private-setup'};
    try {
        const result = await prepareBaseline({...input, plan: originalPlan}, {root: input.root}, {env,
            fetch: async (target, options) => {
                const url = new URL(target);
                assert.equal(options.redirect, 'error');
                if (url.pathname === '/api/login') return new Response(JSON.stringify({accessToken: 'private-token'}), {status: ++logins === 1 ? 401 : 200});
                if (url.pathname === '/api/setup') return new Response('{}', {status: 201});
                if (url.pathname === '/api/actuator/health') {
                    assert.equal(options.headers.Authorization, undefined);
                    return Response.json({status: 'UP'});
                }
                assert.equal(options.headers.Authorization, 'Bearer private-token');
                if (url.pathname === '/api/catalog') return Response.json({content: [{dictionaryCode: 'EXAMPLE', dictionaryId: 'one'}]});
                if (url.pathname === '/api/values') return Response.json({content: [{dictionaryId: 'one', valueCode: 'ONE'}]});
                assert.fail('Unexpected request');
            },
            run: async (_script, args, variables, signal) => {
                runs++; assert(!args.join(' ').includes('private-token'));
                assert.equal(variables.FL_API_TOKEN, 'private-token');
                assert.equal(signal.aborted, false);
            }});
        assert.equal(result.status, 'ready');
        assert.equal(result.records[0].dictionaries.valueCount, 1);
        assert.equal(runs, 1);
        assert(!JSON.stringify(result).includes('private-'));
    } finally { rmSync(input.root, {recursive: true, force: true}); }
});
test('unsupported operations and missing credentials cause no HTTP mutation', async () => {
    const input = fixture();
    const fetch = () => assert.fail('must not request');
    try {
        await assert.rejects(prepareBaseline({...input, plan: originalPlan}, {root: input.root}, {env: {}, fetch}), /credentials/);
        const plan = structuredClone(originalPlan);
        plan.steps[2].operation = 'Unknown';
        await assert.rejects(prepareBaseline({...input, plan}, {root: input.root}, {
            env: {TEST_USER: 'user', TEST_PASSWORD: 'pass'}, fetch}), /Unsupported adapter operation/);
    } finally { rmSync(input.root, {recursive: true, force: true}); }
});
test('an initializer failure cannot produce a ready baseline', async () => {
    const input = fixture();
    try {
        await assert.rejects(prepareBaseline({...input, plan: originalPlan}, {root: input.root}, {
            env: {TEST_USER: 'user', TEST_PASSWORD: 'pass'},
            fetch: async target => String(target).endsWith('/login') ? Response.json({accessToken: 'private-token'}) : Response.json({status: 'UP'}),
            run: async () => { throw new Error('initializer failed'); }
        }), /initializer failed/);
    } finally { rmSync(input.root, {recursive: true, force: true}); }
});
