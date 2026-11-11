export function createSimulationContext(options = {}) {
  const values = new Map();
  const events = new Map();
  const steps = new Map();
  const warnings = [];

  return {
    seed: options.seed,
    scenarioId: options.scenarioId,
    put(key, value) {
      values.set(key, value);
    },
    get(key) {
      return values.get(key);
    },
    putStep(stepId, value) {
      steps.set(stepId, value);
    },
    putEvent(eventName, value) {
      const list = events.get(eventName) || [];
      list.push(value);
      events.set(eventName, list);
    },
    eventValue(eventName, field) {
      const list = events.get(eventName) || [];
      const event = list[list.length - 1];
      return readPath(event, field);
    },
    findValue(field) {
      for (const [, value] of Array.from(values).reverse()) {
        const match = readPath(value, field);
        if (match !== undefined) return match;
      }
      for (const [, value] of Array.from(steps).reverse()) {
        const match = readPath(value, field);
        if (match !== undefined) return match;
      }
      return undefined;
    },
    warn(message) {
      warnings.push(message);
    },
    snapshot() {
      return {
        values: Object.fromEntries(values),
        warnings: [...warnings]
      };
    }
  };
}

export function readPath(source, path) {
  if (source == null || !path) return undefined;
  return String(path).split('.').reduce((current, part) => {
    if (current == null) return undefined;
    return current[part];
  }, source);
}
