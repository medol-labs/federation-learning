export function createBusinessClient(options = {}) {
  const defaultBaseUrl = options.baseUrl || process.env.BUSINESS_BASE_URL || 'http://localhost:8080';
  const contextBaseUrls = options.contextBaseUrls || parseJsonEnv('CONTEXT_BASE_URLS') || {};
  const headers = {
    'content-type': 'application/json',
    ...(parseJsonEnv('BUSINESS_HEADERS') || {}),
    ...(options.headers || {})
  };

  return {
    async execute(step, payload) {
      const endpoint = step.http || {};
      const baseUrl = contextBaseUrls[step.context] || defaultBaseUrl;
      const url = new URL(endpoint.path || '/', normalizeBaseUrl(baseUrl));
      const method = endpoint.method || 'POST';

      if (options.dryRun) {
        return {
          ok: true,
          dryRun: true,
          status: 200,
          method,
          url: url.toString(),
          body: payload
        };
      }

      const response = await fetch(url, {
        method,
        headers,
        body: JSON.stringify(payload)
      });
      const text = await response.text();
      const body = parseBody(text);
      if (!response.ok) {
        const error = new Error('Business command failed: ' + method + ' ' + url + ' -> HTTP ' + response.status);
        error.status = response.status;
        error.body = body;
        throw error;
      }
      return {
        ok: true,
        status: response.status,
        method,
        url: url.toString(),
        body
      };
    }
  };
}

function normalizeBaseUrl(value) {
  return String(value || 'http://localhost:8080').replace(/\/+$/, '') + '/';
}

function parseBody(text) {
  if (!text) return null;
  try {
    return JSON.parse(text);
  } catch {
    return text;
  }
}

function parseJsonEnv(name) {
  const value = process.env[name];
  if (!value) return undefined;
  try {
    return JSON.parse(value);
  } catch (error) {
    throw new Error('Invalid JSON in ' + name + ': ' + error.message);
  }
}
