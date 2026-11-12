import {existsSync, readFileSync, writeFileSync, mkdirSync, chmodSync} from 'node:fs';
import {resolve, dirname} from 'node:path';
import {fileURLToPath} from 'node:url';
import {randomBytes} from 'node:crypto';

export function prepareTestEnvironment(root) {
    const envFile = resolve(root, '.env');
    const secretFile = resolve(root, 'operations/k3s/environments/local/secrets.local.yaml');
    const stateFile = resolve(root, 'simulation/test-environment.private.json');
    if (!existsSync(stateFile) && (existsSync(envFile) || existsSync(secretFile))) {
        throw new Error('Existing environment configuration found. Refusing to replace credentials; use a fresh test workDir or explicitly reconcile existing configuration.');
    }
    let credentials;
    if (existsSync(stateFile)) credentials = JSON.parse(readFileSync(stateFile, 'utf8'));
    else {
        credentials = Object.fromEntries(['database', 'internalToken', 'jwt', 'setupToken', 'adminPassword'].map(key => [key, randomBytes(32).toString('hex')]));
        mkdirSync(dirname(stateFile), {recursive: true});
        writeFileSync(stateFile, JSON.stringify(credentials), {mode: 0o600, flag: 'wx'});
    }
    for (const key of ['database', 'internalToken', 'jwt', 'setupToken', 'adminPassword']) {
        if (!/^[a-f0-9]{64}$/.test(credentials[key] || '')) throw new Error(`Invalid stored credential: ${key}`);
    }
    const values = {
        MEDOL_IMAGE_SOURCE: 'registry',
        FL_K3D_ADMIN_USERNAME: 'simulation-admin',
        FL_K3D_ADMIN_PASSWORD: credentials.adminPassword,
        FL_K3D_ADMIN_SETUP_TOKEN: credentials.setupToken,
        MEDOL_SECURITY_ADMIN_BOOTSTRAP_SETUP_TOKEN: credentials.setupToken,
        FL_K3D_JWT_SECRET: credentials.jwt,
        MEDOL_SECURITY_JWT_SECRET: credentials.jwt,
        MEDOL_SECURITY_INTERNAL_TOKEN: credentials.internalToken,
        RUNTIME_AGENT_DB_PASSWORD: credentials.database
    };
    const documents = [{name: 'postgres-secret', data: {username: 'medol', password: credentials.database}},
        ...['platform', 'runtime-agent', 'support'].map(module => ({
            name: `federation-learning-${module}-local-secret`, data: {
                DB_USERNAME: 'medol', DB_PASSWORD: credentials.database,
                MEDOL_SECURITY_INTERNAL_TOKEN: credentials.internalToken,
                MEDOL_SECURITY_JWT_SECRET: credentials.jwt,
                MEDOL_SECURITY_ADMIN_BOOTSTRAP_SETUP_TOKEN: credentials.setupToken, UMADB_API_KEY: ''
            }
        }))];
    const secret = JSON.stringify({apiVersion: 'v1', kind: 'List', items: documents.map(({name, data}) => ({
        apiVersion: 'v1', kind: 'Secret', metadata: {name}, type: 'Opaque', stringData: data
    }))}, null, 2) + '\n';
    if (existsSync(envFile)) {
        const entries = Object.fromEntries(readFileSync(envFile, 'utf8').split(/\r?\n/).filter(line => line && !line.startsWith('#')).map(line => {
            const index = line.indexOf('='); return [line.slice(0, index), line.slice(index + 1)];
        }));
        for (const [key, value] of Object.entries(values)) if (entries[key] !== value) throw new Error(`Environment value differs from managed test credentials: ${key}`);
    }
    if (existsSync(secretFile) && readFileSync(secretFile, 'utf8') !== secret) throw new Error('Existing Secret differs from managed test credentials; refusing overwrite');
    mkdirSync(dirname(secretFile), {recursive: true});
    if (!existsSync(secretFile)) writeFileSync(secretFile, secret, {mode: 0o600, flag: 'wx'});
    if (!existsSync(envFile)) writeFileSync(envFile, Object.entries(values).map(([k, v]) => `${k}=${v}`).join('\n') + '\n', {mode: 0o600, flag: 'wx'});
    for (const path of [envFile, secretFile, stateFile]) chmodSync(path, 0o600);
    return {status: 'ready', environment: 'local'};
}

if (process.argv[1] && resolve(process.argv[1]) === fileURLToPath(import.meta.url)) {
    try {
        if (!process.argv.includes('--auto-test')) throw new Error('Explicit --auto-test is required; only for a fresh disposable local test environment');
        console.log(JSON.stringify(prepareTestEnvironment(process.cwd())));
    } catch (error) { console.error(error.message); process.exitCode = 1; }
}
