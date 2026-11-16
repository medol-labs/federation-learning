import {readFileSync} from 'node:fs';
import {mergeServiceCatalog} from '../../src/runtime/deployment.mjs';

const read = path => JSON.parse(readFileSync(new URL(path, import.meta.url), 'utf8'));
export function deploymentCatalog(base = read('../../../operations/model/services.json')) {
    return mergeServiceCatalog(base, read('../../../operations/extensions/deployment-services.json'));
}
