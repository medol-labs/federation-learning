import { createBusinessClient } from './business-client.js';
import { createSimulationContext } from './context.js';
import { createDataGenerator } from './data-generator.js';
import { createEventObserver } from './event-observer.js';

export async function runScenario(model, options = {}) {
  const seed = Number.parseInt(String(options.seed || model.defaultSeed || 1001), 10);
  const logs = [];
  let scenario;
  let context;

  try {
    scenario = findScenario(model, options.scenarioId);
    context = createSimulationContext({ seed, scenarioId: scenario.id });
    const data = createDataGenerator(seed, scenario.id);
    const client = createBusinessClient({
      dryRun: options.dryRun,
      baseUrl: options.businessBaseUrl,
      contextBaseUrls: options.contextBaseUrls,
      headers: options.businessHeaders || options.headers
    });
    const observer = options.observer || createEventObserver({
      strict: options.strictEvents,
      wait: options.waitForEvents
    });

    for (const [key, value] of Object.entries(options.variables || {})) {
      context.put(key, value);
    }

    log(logs, '[SIMULATION]');
    log(logs, 'Scenario: ' + scenario.name);
    log(logs, 'Seed: ' + seed);

    const unsupported = unsupportedReasons(scenario);
    if (unsupported.length > 0 && !options.allowUnsupported) {
      unsupported.forEach((reason) => log(logs, 'UNSUPPORTED ' + reason));
      return {
        success: false,
        status: 'unsupported',
        scenarioId: scenario.id,
        scenarioName: scenario.name,
        seed,
        message: 'Scenario has unsupported preparation. Pass allowUnsupported=true to run command steps anyway.',
        unsupported,
        logs,
        context: context.snapshot()
      };
    }

    for (let index = 0; index < scenario.steps.length; index += 1) {
      const step = scenario.steps[index];
      log(logs, '');
      log(logs, '[' + (index + 1) + '/' + scenario.steps.length + '] ' + step.title);

      const payload = resolvePayload(step, context, data);
      context.put(step.id + '.command', payload);

      if (step.execution === 'AUTOMATIC') {
        log(logs, 'AUTO waiting for ' + expectedEventNames(step).join(', '));
        await expectEvents(step, observer, context, logs);
        log(logs, 'PASS');
        continue;
      }

      if (step.expectedRejection) {
        await expectRejection(step, payload, client, context, logs);
        log(logs, 'PASS');
        continue;
      }

      log(logs, 'COMMAND ' + step.command + ' ' + (step.http?.method || 'POST') + ' ' + (step.http?.path || '/'));
      const result = await client.execute(step, payload);
      context.putStep(step.id, result.body);
      context.put(step.id + '.result', result.body);
      log(logs, 'HTTP ' + result.status);

      await expectEvents(step, observer, context, logs);
      log(logs, 'PASS');
    }

    log(logs, '');
    log(logs, 'Scenario PASSED');
    return {
      success: true,
      scenarioId: scenario.id,
      scenarioName: scenario.name,
      seed,
      logs,
      context: context.snapshot()
    };
  } catch (error) {
    log(logs, '');
    log(logs, 'Scenario FAILED');
    log(logs, error && error.message ? error.message : String(error));
    return {
      success: false,
      scenarioId: scenario?.id,
      scenarioName: scenario?.name,
      seed,
      message: error && error.message ? error.message : String(error),
      logs,
      context: context?.snapshot ? context.snapshot() : { values: {}, warnings: [] }
    };
  }
}

function unsupportedReasons(scenario) {
  return [
    ...(scenario.unsupported || []),
    ...(scenario.given || [])
      .filter((item) => !item.supported)
      .map((item) => item.title + ': ' + (item.reason || 'Unsupported preparation'))
  ];
}

function resolvePayload(step, context, data) {
  return Object.fromEntries((step.inputs || []).map((input) => {
    const value = resolveInput(step, input, context, data);
    context.put(step.id + '.' + input.name, value);
    return [input.name, value];
  }));
}

function resolveInput(step, input, context, data) {
  const source = input.source || { kind: 'Generated' };
  if (source.kind === 'Constant') {
    return source.value;
  }
  if (source.kind === 'PreviousStep') {
    const value = context.get(source.stepId + '.' + source.field) ?? context.findValue(source.field);
    if (value === undefined) {
      throw new Error('Cannot resolve previous step input: ' + step.id + '.' + input.name);
    }
    return value;
  }
  if (source.kind === 'PreviousEvent') {
    const value = context.eventValue(source.event, source.field) ?? context.findValue(source.field);
    if (value === undefined) {
      throw new Error('Cannot resolve previous event input: ' + source.event + '.' + source.field);
    }
    return value;
  }
  return data.value(input, step.id + '.' + input.name);
}

async function expectEvents(step, observer, context, logs) {
  for (const event of step.expectedEvents || []) {
    const payload = await observer.expect(event, (message) => log(logs, message));
    if (payload !== undefined) {
      context.putEvent(event.name, payload);
    }
  }
}

async function expectRejection(step, payload, client, context, logs) {
  log(logs, 'COMMAND ' + step.command + ' expecting rejection');
  try {
    const result = await client.execute(step, payload);
    context.putStep(step.id, result.body);
  } catch (error) {
    context.putStep(step.id, { rejected: true, status: error.status, body: error.body, message: error.message });
    log(logs, 'REJECTED ' + (error.status || 'error'));
    return;
  }
  throw new Error('Expected command rejection but command succeeded: ' + step.command);
}

function expectedEventNames(step) {
  return (step.expectedEvents || []).map((event) => event.name);
}

function findScenario(model, value) {
  if (!value) {
    const scenario = model.scenarios[0];
    if (!scenario) throw new Error('No simulation scenarios were generated.');
    return scenario;
  }
  const scenario = model.scenarios.find((item) =>
    item.id === value ||
    item.name === value ||
    item.className === value
  );
  if (!scenario) {
    throw new Error('Simulation scenario not found: ' + value);
  }
  return scenario;
}

function log(logs, message) {
  logs.push(message);
  console.log(message);
}
