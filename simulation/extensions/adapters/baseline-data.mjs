import {readFileSync, realpathSync, existsSync} from 'node:fs';
import {resolve, relative} from 'node:path';
import {parseEnv} from 'node:util';
import {spawn} from 'node:child_process';
import {prepareData} from '../../src/prepare-data.mjs';

function localFile(root, path) {
    if (typeof path !== 'string' || !path) throw new Error('Explicit input file is required');
    const file = realpathSync(resolve(root, path)), rel = relative(realpathSync(root), file);
    if (!rel || rel === '..' || rel.startsWith('../') || rel.startsWith('/')) throw new Error('Baseline file escapes workspace');
    return file;
}
function endpoint(environment, key) {
    const value = environment.endpoints?.[key];
    if (typeof value !== 'string') throw new Error('Missing baseline endpoint');
    const url = new URL(value);
    if (!['http:', 'https:'].includes(url.protocol) || url.username || url.password || url.search || url.hash) throw new Error('Invalid baseline endpoint');
    return value.replace(/\/$/, '');
}
function api(base, path) {
    if (typeof path !== 'string' || !path.startsWith('/') || path.startsWith('//') || path.includes('..')) throw new Error('Invalid baseline API path');
    return base + path;
}
function runInitializer(script, args, env, signal) {
    return new Promise((resolve, reject) => {
        // Credentials stay in the process environment, never argv or reports.
        const child = spawn(process.execPath, [script, ...args], {env, signal, stdio: 'ignore'});
        child.once('error', () => reject(new Error('Dictionary initializer could not run')));
        child.once('close', code => code === 0 ? resolve() : reject(new Error('Dictionary initializer failed')));
    });
}
export async function prepareBaseline({environment, plan, config}, {root}, dependencies = {}) {
    const request = dependencies.fetch || fetch;
    const run = dependencies.run || runInitializer;
    const authBase = endpoint(environment, config.auth?.endpoint);
    const dictionaryBase = endpoint(environment, config.dictionary?.endpoint);
    if (dictionaryBase !== authBase) throw new Error('Dictionary initialization requires the configured authentication endpoint');
    const catalog = api(dictionaryBase, config.dictionary.catalogPath), values = api(dictionaryBase, config.dictionary.valuesPath);
    const script = localFile(root, config.dictionary.script), dataFile = localFile(root, config.dictionary.dataFile);
    const dictionaries = JSON.parse(readFileSync(dataFile, 'utf8')).dictionaries;
    if (!Array.isArray(dictionaries) || !dictionaries.length || dictionaries.some(d => !d.dictionaryCode || !Array.isArray(d.values)
        || d.values.some(v => !v.valueCode) || new Set(d.values.map(v => v.valueCode)).size !== d.values.length)
        || new Set(dictionaries.map(d => d.dictionaryCode)).size !== dictionaries.length) throw new Error('Invalid dictionary baseline data');
    const environmentValues = {...(existsSync(resolve(root, config.envFile)) ? parseEnv(readFileSync(localFile(root, config.envFile), 'utf8')) : {}), ...(dependencies.env || process.env)};
    const username = environmentValues[config.auth.usernameEnv], password = environmentValues[config.auth.passwordEnv], setupToken = environmentValues[config.auth.setupTokenEnv];
    if (!username || !password) throw new Error('Explicit baseline administrator credentials are required');
    let token;
    const authenticate = async (base, signal) => {
        const response = await request(api(base, config.auth.loginPath), {method: 'POST', redirect: 'error', signal,
            headers: {'content-type': 'application/json'}, body: JSON.stringify({username, password})});
        if (response.status === 401) return false;
        if (!response.ok) throw new Error('Baseline login failed with HTTP ' + response.status);
        const body = await response.json();
        if (!body.accessToken) throw new Error('Baseline login did not return a token');
        return body.accessToken;
    };
    const initializeAdministrator = async (base, signal) => {
        let accessToken = await authenticate(base, signal);
        if (!accessToken) {
            if (!setupToken) throw new Error('Administrator setup token is required for initialization');
            const response = await request(api(base, config.auth.setupPath), {method: 'POST', redirect: 'error', signal,
                headers: {'content-type': 'application/json'}, body: JSON.stringify({username, password, setupToken})});
            if (!response.ok && response.status !== 409) throw new Error('Administrator setup failed with HTTP ' + response.status);
            accessToken = await authenticate(base, signal);
            if (!accessToken) throw new Error('Administrator authentication failed after setup');
        }
        return accessToken;
    };
    const page = async (url, signal) => {
        if (!token) throw new Error('Initialize administrator before dictionary verification');
        const response = await request(url, {redirect: 'error', signal, headers: {Authorization: 'Bearer ' + token}});
        if (!response.ok) throw new Error('Dictionary query failed with HTTP ' + response.status);
        return response.json();
    };
    return prepareData('baseline', plan, environment, {fetch: request, adapter: {
        async headersFor({url}) {
            return token && (url === authBase || url.startsWith(authBase + '/')) ? {Authorization: 'Bearer ' + token} : {};
        },
        operations: {
            InitializeAdministrator: {kind: 'command', async execute(_step, {signal}) {
                token = await initializeAdministrator(authBase, signal);
                return {status: 'initialized'};
            }},
            InitializeParticipantAdministrators: {kind: 'command', async execute(_step, {signal}) {
                const participants = environment.participantDeployments || [];
                if (!config.participantAuth) {
                    if (participants.length) throw new Error('Participant authentication configuration is required');
                    return {status: 'not-applicable', count: 0};
                }
                for (const participant of participants) {
                    const address = endpoint({endpoints: {auth: participant[config.participantAuth.endpointField || 'consoleUrl']}}, 'auth');
                    const base = config.participantAuth.basePath ? api(address, config.participantAuth.basePath) : address;
                    const accessToken = await initializeAdministrator(base, signal);
                    const response = await request(api(base, config.participantAuth.probePath), {redirect: 'error', signal,
                        headers: {Authorization: 'Bearer ' + accessToken}});
                    if (!response.ok) throw new Error('Participant authenticated query failed with HTTP ' + response.status);
                }
                return {status: 'initialized', count: participants.length};
            }},
            InitializeDictionaries: {kind: 'command', async execute(_step, {signal}) {
                if (!token) throw new Error('Initialize administrator before dictionaries');
                await run(script, ['--base-url', dictionaryBase, '--data', dataFile],
                    {...environmentValues, FL_API_TOKEN: token}, signal);
                return {status: 'initialized'};
            }},
            CheckDictionaries: {kind: 'wait', async execute(_step, {signal}) {
                let valueCount = 0;
                for (const expected of dictionaries) {
                    const query = new URL(catalog);
                    query.search = new URLSearchParams({'dictionaryCode.equals': expected.dictionaryCode, size: '2'});
                    const result = await page(query, signal);
                    const rows = (result.content || []).filter(d => d.dictionaryCode === expected.dictionaryCode);
                    if (rows.length !== 1 || !rows[0].dictionaryId) return {ready: false};
                    for (const value of expected.values) {
                        const query = new URL(values);
                        query.search = new URLSearchParams({'dictionaryId.equals': rows[0].dictionaryId, 'valueCode.equals': value.valueCode, size: '2'});
                        const result = await page(query, signal);
                        if ((result.content || []).filter(v => v.dictionaryId === rows[0].dictionaryId && v.valueCode === value.valueCode).length !== 1) return {ready: false};
                        valueCount++;
                    }
                }
                return {ready: true, dictionaryCount: dictionaries.length, valueCount};
            }}
        },
        cleanup() { token = undefined; }
    }});
}
