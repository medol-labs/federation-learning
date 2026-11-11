import { createServer } from 'node:http';
import { loadSimulationModel } from './runtime/model-loader.js';
import { createEventJournal, createEventObserver } from './runtime/event-observer.js';
import { runScenario } from './runtime/runner.js';

const model = loadSimulationModel();
const journal = createEventJournal();
const port = Number.parseInt(process.env.PORT || '3199', 10);
const host = process.env.HOST || '127.0.0.1';

const server = createServer(async (request, response) => {
  try {
    const url = new URL(request.url || '/', 'http://localhost');

    if (request.method === 'GET' && url.pathname === '/health') {
      return sendJson(response, 200, { status: 'ok', service: 'medol-simulation', scenarios: model.scenarios.length });
    }

    if (request.method === 'GET' && url.pathname === '/scenarios') {
      return sendJson(response, 200, {
        scenarios: model.scenarios.map((scenario) => ({
          id: scenario.id,
          name: scenario.name,
          kind: scenario.kind,
          context: scenario.context,
          steps: scenario.steps.length,
          unsupported: unsupportedReasons(scenario)
        }))
      });
    }

    const scenarioMatch = url.pathname.match(/^\/scenarios\/([^/]+)$/);
    if (request.method === 'GET' && scenarioMatch) {
      const scenario = findScenario(decodeURIComponent(scenarioMatch[1]));
      return scenario
        ? sendJson(response, 200, scenario)
        : sendJson(response, 404, { error: 'scenario_not_found' });
    }

    if (request.method === 'GET' && url.pathname === '/events') {
      return sendJson(response, 200, { events: journal.events() });
    }

    if (request.method === 'POST' && url.pathname === '/events') {
      const body = await readJson(request);
      const eventName = body.eventName || body.name || body.type || body.eventType;
      if (!eventName) {
        return sendJson(response, 400, { error: 'eventName is required' });
      }
      const event = journal.record(eventName, body.payload ?? body.event ?? body);
      return sendJson(response, 202, { accepted: true, event });
    }

    if (request.method === 'POST' && url.pathname === '/simulations') {
      const body = await readJson(request);
      return runAndSend(response, body.scenario || body.scenarioId, body);
    }

    const runMatch = url.pathname.match(/^\/simulations\/([^/]+)\/run$/);
    if (request.method === 'POST' && runMatch) {
      const body = await readJson(request);
      return runAndSend(response, decodeURIComponent(runMatch[1]), body);
    }

    return sendJson(response, 404, { error: 'not_found' });
  } catch (error) {
    return sendJson(response, 500, {
      error: 'simulation_service_error',
      message: error && error.message ? error.message : String(error)
    });
  }
});

server.listen(port, host, () => {
  console.log('Medol simulation service listening on ' + host + ':' + port);
  console.log('Business base URL: ' + (process.env.BUSINESS_BASE_URL || 'http://localhost:8080'));
});

async function runAndSend(response, requestedScenario, options) {
  const observer = createEventObserver({
    journal,
    remoteUrl: options.eventObserverUrl || process.env.EVENT_OBSERVER_URL,
    timeoutMs: Number.parseInt(String(options.eventTimeoutMs || process.env.EVENT_TIMEOUT_MS || '5000'), 10),
    strict: options.strictEvents ?? parseBoolean(process.env.STRICT_EVENTS),
    wait: options.waitForEvents ?? parseBoolean(process.env.WAIT_FOR_EVENTS)
  });
  const result = await runScenario(model, {
    scenarioId: requestedScenario,
    seed: options.seed,
    variables: options.variables || {},
    dryRun: Boolean(options.dryRun),
    strictEvents: options.strictEvents,
    waitForEvents: options.waitForEvents,
    businessBaseUrl: options.businessBaseUrl,
    contextBaseUrls: options.contextBaseUrls,
    businessHeaders: options.businessHeaders || options.headers,
    allowUnsupported: Boolean(options.allowUnsupported),
    observer
  });
  sendJson(response, result.success ? 200 : 422, result);
}

function findScenario(value) {
  return model.scenarios.find((scenario) =>
    scenario.id === value ||
    scenario.name === value ||
    scenario.className === value
  );
}

function unsupportedReasons(scenario) {
  return [
    ...(scenario.unsupported || []),
    ...(scenario.given || [])
      .filter((item) => !item.supported)
      .map((item) => item.title + ': ' + (item.reason || 'Unsupported preparation'))
  ];
}

function parseBoolean(value) {
  return ['1', 'true', 'yes', 'on'].includes(String(value || '').toLowerCase());
}

async function readJson(request) {
  const chunks = [];
  for await (const chunk of request) {
    chunks.push(chunk);
  }
  const text = Buffer.concat(chunks).toString('utf8').trim();
  return text ? JSON.parse(text) : {};
}

function sendJson(response, statusCode, body) {
  response.writeHead(statusCode, { 'content-type': 'application/json; charset=utf-8' });
  response.end(JSON.stringify(body, null, 2));
}
