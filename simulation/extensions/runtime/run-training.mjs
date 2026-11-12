import { readFileSync, mkdirSync, writeFileSync } from 'node:fs';
import { resolve, dirname } from 'node:path';
import { fileURLToPath } from 'node:url';
import { randomUUID } from 'node:crypto';
import { createTrainingEnvironment } from '../adapters/training-environment.mjs';

import { executeSteps, validateStages } from "../../src/runtime/stages.mjs";
export { executeSteps };

if (process.argv[1] && resolve(process.argv[1]) === fileURLToPath(import.meta.url)) {
  const simulationRoot = resolve(dirname(fileURLToPath(import.meta.url)), '../..');
  const reportDirectory = resolve(simulationRoot, 'reports', randomUUID());
  const report = { status: 'running', steps: [] };
  try {
    const scenario = JSON.parse(readFileSync(resolve(simulationRoot, 'extensions/scenarios/two-participant-training.json'), 'utf8'));
    const adapter = await createTrainingEnvironment(scenario.arguments);
    if (process.argv.includes('--plan')) {
      validateStages(scenario, adapter);
      console.log(JSON.stringify({ participantCount: adapter.participantCount, steps: scenario.steps }, null, 2));
      adapter.cleanup();
    } else {
      mkdirSync(reportDirectory, { recursive: true });
      await executeSteps(scenario, adapter, entry => {
        report.steps.push({ ...entry, at: new Date().toISOString() });
        writeFileSync(resolve(reportDirectory, 'scenario.json'), JSON.stringify(report, null, 2));
        console.log(`[simulation] ${entry.id}: ${entry.status}`);
      });
      report.status = 'passed';
    }
  } catch (error) {
    report.status = 'failed';
    console.error(error.message);
    process.exitCode = 1;
  } finally {
    if (!process.argv.includes('--plan')) {
      mkdirSync(reportDirectory, { recursive: true });
      writeFileSync(resolve(reportDirectory, 'scenario.json'), JSON.stringify(report, null, 2));
      console.log(`[simulation] report: ${reportDirectory}`);
    }
  }
}
