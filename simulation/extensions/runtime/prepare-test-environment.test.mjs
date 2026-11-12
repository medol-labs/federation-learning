import test from 'node:test';
import assert from 'node:assert/strict';
import {mkdtempSync, readFileSync, writeFileSync, rmSync, statSync} from 'node:fs';
import {tmpdir} from 'node:os';
import {join} from 'node:path';
import {prepareTestEnvironment} from './prepare-test-environment.mjs';

test('generates consistent secrets, reuses credentials and protects files', () => {
    const root = mkdtempSync(join(tmpdir(), 'simulation-env-'));
    try {
        prepareTestEnvironment(root);
        const env = readFileSync(join(root, '.env'), 'utf8');
        const secrets = JSON.parse(readFileSync(join(root, 'operations/k3s/environments/local/secrets.local.yaml')));
        assert.equal(new Set(secrets.items.slice(1).map(s => s.stringData.MEDOL_SECURITY_INTERNAL_TOKEN)).size, 1);
        assert.equal(secrets.items[0].stringData.password, secrets.items[1].stringData.DB_PASSWORD);
        prepareTestEnvironment(root);
        assert.equal(readFileSync(join(root, '.env'), 'utf8'), env);
        assert.equal(statSync(join(root, '.env')).mode & 0o777, 0o600);
        writeFileSync(join(root, '.env'), env.replace('simulation-admin', 'changed'));
        assert.throws(() => prepareTestEnvironment(root), /differs/);
    } finally { rmSync(root, {recursive: true, force: true}); }
});

test('never replaces unmanaged credentials', () => {
    const root = mkdtempSync(join(tmpdir(), 'simulation-env-'));
    try {
        writeFileSync(join(root, '.env'), 'PASSWORD=existing');
        assert.throws(() => prepareTestEnvironment(root), /Refusing/);
        assert.equal(readFileSync(join(root, '.env'), 'utf8'), 'PASSWORD=existing');
    } finally { rmSync(root, {recursive: true, force: true}); }
});
