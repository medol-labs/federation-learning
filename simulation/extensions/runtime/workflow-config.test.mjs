import test from 'node:test';
import assert from 'node:assert/strict';
import {readFileSync, existsSync} from 'node:fs';
import {resolve} from 'node:path';
import {fileURLToPath, pathToFileURL} from 'node:url';
import {extensionPath} from '../../src/task.mjs';
import {validateTransfer} from '../../src/worker.mjs';

const root = fileURLToPath(new URL('../../../', import.meta.url));
const read = name => JSON.parse(readFileSync(resolve(root, name), 'utf8'));

test('all declared project tasks resolve to explicit exports without executing business actions', async () => {
    const config = read('simulation/pipelines/tasks.json');
    for (const task of Object.values(config.tasks)) {
        const module = await import(pathToFileURL(extensionPath(root, task.module)).href);
        assert.equal(typeof module[task.export], 'function');
        for (const input of Object.values(task.inputs || {})) if (input.path) assert(existsSync(resolve(root, input.path)));
    }
});

test('current pipelines use generic task/transfer entries and consistent worker location', () => {
    const tasks = read('simulation/pipelines/tasks.json').tasks;
    const worker = read('simulation/pipelines/worker.json');
    const full = read('simulation/pipelines/full-remote.json');
    assert.deepEqual(full.hosts.k3d, validateTransfer(worker, root));
    for (const file of ['remote.json-example', 'full-remote.json', 'pipeline.json', 'training-remote.json-example', 'training-stages.json-example']) {
        const pipeline = read(`simulation/pipelines/${file}`);
        for (const step of pipeline.steps) {
            assert(!step.command.includes('simulation/extensions/runtime/run-training.mjs'));
            assert(!step.command.includes('simulation/extensions/runtime/workflow-stage.mjs'));
            assert(!step.command.includes('simulation/extensions/runtime/sync-worker.mjs'));
            assert(!step.command.includes('simulation/extensions/runtime/training-stages.mjs'));
            if (step.command[1] === 'simulation/src/task.mjs') assert(tasks[step.command[3]]);
            if (step.command[0] === 'node') assert(existsSync(resolve(root, step.command[1])));
        }
    }
});
