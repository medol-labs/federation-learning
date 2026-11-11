import { loadSimulationModel } from './runtime/model-loader.js';
import { createEventJournal, createEventObserver } from './runtime/event-observer.js';
import { runScenario } from './runtime/runner.js';

const args = parseArgs(process.argv.slice(2));
const model = loadSimulationModel();
const observer = createEventObserver({
  journal: createEventJournal(),
  remoteUrl: args.eventObserverUrl || process.env.EVENT_OBSERVER_URL,
  timeoutMs: Number.parseInt(String(args.eventTimeoutMs || process.env.EVENT_TIMEOUT_MS || '5000'), 10),
  strict: args.strictEvents ?? parseBoolean(process.env.STRICT_EVENTS),
  wait: args.waitForEvents ?? parseBoolean(process.env.WAIT_FOR_EVENTS)
});

const result = await runScenario(model, {
  scenarioId: args.scenario || args._[0],
  seed: args.seed,
  variables: args.variables ? JSON.parse(args.variables) : {},
  dryRun: Boolean(args.dryRun),
  strictEvents: args.strictEvents,
  waitForEvents: args.waitForEvents,
  businessBaseUrl: args.businessBaseUrl,
  contextBaseUrls: args.contextBaseUrls ? JSON.parse(args.contextBaseUrls) : undefined,
  businessHeaders: args.businessHeaders ? JSON.parse(args.businessHeaders) : undefined,
  allowUnsupported: Boolean(args.allowUnsupported),
  observer
});

console.log(JSON.stringify(result, null, 2));
process.exit(result.success ? 0 : 1);

function parseArgs(values) {
  const result = { _: [] };
  for (let index = 0; index < values.length; index += 1) {
    const value = values[index];
    if (!value.startsWith('--')) {
      result._.push(value);
      continue;
    }
    const [rawName, inlineValue] = value.slice(2).split(/=(.*)/s, 2);
    const name = rawName.replace(/-([a-z])/g, (_, letter) => letter.toUpperCase());
    if (inlineValue !== undefined) {
      result[name] = coerce(inlineValue);
    } else if (values[index + 1] && !values[index + 1].startsWith('--')) {
      result[name] = coerce(values[index + 1]);
      index += 1;
    } else {
      result[name] = true;
    }
  }
  return result;
}

function coerce(value) {
  if (value === 'true') return true;
  if (value === 'false') return false;
  return value;
}

function parseBoolean(value) {
  return ['1', 'true', 'yes', 'on'].includes(String(value || '').toLowerCase());
}
