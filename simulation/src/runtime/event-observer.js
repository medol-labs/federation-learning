export function createEventJournal() {
  const values = [];
  const waiters = new Set();

  return {
    record(eventName, payload) {
      const event = {
        eventName,
        payload,
        recordedAt: new Date().toISOString()
      };
      values.push(event);
      for (const waiter of waiters) {
        waiter();
      }
      return event;
    },
    find(eventName) {
      return [...values].reverse().find((event) => event.eventName === eventName);
    },
    events() {
      return [...values];
    },
    async waitFor(eventName, timeoutMs) {
      const existing = this.find(eventName);
      if (existing) return existing;
      const deadline = Date.now() + timeoutMs;
      while (Date.now() < deadline) {
        await new Promise((resolve) => {
          const timer = setTimeout(resolve, Math.min(250, Math.max(1, deadline - Date.now())));
          const wake = () => {
            clearTimeout(timer);
            waiters.delete(wake);
            resolve();
          };
          waiters.add(wake);
        });
        const found = this.find(eventName);
        if (found) return found;
      }
      return undefined;
    }
  };
}

export function createEventObserver(options = {}) {
  const journal = options.journal || createEventJournal();
  const timeoutMs = Number.isFinite(options.timeoutMs) ? options.timeoutMs : 5000;
  const strict = Boolean(options.strict);
  const wait = Boolean(options.wait);
  const remoteUrl = options.remoteUrl;

  return {
    async expect(event, log) {
      const observed = (strict || wait || remoteUrl)
        ? await waitForObservedEvent(journal, remoteUrl, event.name, timeoutMs)
        : undefined;
      if (observed) {
        log('EVENT observed ' + event.name + (observed.source === 'remote' ? ' remotely' : ''));
        return observed.payload;
      }

      const message = 'Expected event was not observed: ' + event.name;
      if (strict) {
        throw new Error(message);
      }
      log('EVENT observation skipped ' + event.name + ' (set STRICT_EVENTS=true or POST /events to enforce)');
      return undefined;
    }
  };
}

async function waitForObservedEvent(journal, remoteUrl, eventName, timeoutMs) {
  const deadline = Date.now() + timeoutMs;
  while (Date.now() < deadline) {
    const local = journal.find(eventName);
    if (local) {
      return { source: 'local', payload: local.payload };
    }
    if (remoteUrl) {
      const remote = await fetchRemoteEvent(remoteUrl, eventName);
      if (remote) {
        return {
          source: 'remote',
          payload: remote.payload ?? remote.event ?? remote
        };
      }
    }
    await new Promise((resolve) => setTimeout(resolve, 250));
  }
  return undefined;
}

async function fetchRemoteEvent(remoteUrl, eventName) {
  const url = new URL('/events', String(remoteUrl).replace(/\/+$/, '') + '/');
  url.searchParams.set('eventName', eventName);
  const response = await fetch(url).catch(() => undefined);
  if (!response || !response.ok) return undefined;
  const body = await response.json().catch(() => undefined);
  return selectEvent(body, eventName);
}

function selectEvent(body, eventName) {
  const events = Array.isArray(body) ? body : body?.events;
  if (!Array.isArray(events)) return undefined;
  return [...events].reverse().find((event) =>
    event.eventName === eventName ||
    event.name === eventName ||
    event.type === eventName
  );
}
