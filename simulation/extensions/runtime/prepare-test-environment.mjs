import {readFileSync} from 'node:fs';
import {resolve} from 'node:path';
import {fileURLToPath} from 'node:url';
import {prepareTestCredentials} from '../../src/runtime/test-credentials.mjs';

const configuration = JSON.parse(readFileSync(new URL('../../../operations/extensions/test-credentials.json', import.meta.url), 'utf8'));
export const prepareTestEnvironment = (root, config = configuration) => prepareTestCredentials(config, {root});

if (process.argv[1] && resolve(process.argv[1]) === fileURLToPath(import.meta.url)) {
    try {
        if (!process.argv.includes('--auto-test')) throw new Error('Explicit --auto-test is required for test credential initialization');
        console.log(JSON.stringify(prepareTestEnvironment(process.cwd())));
    } catch { console.error('Test credential initialization failed; existing credentials were not replaced'); process.exitCode = 1; }
}
