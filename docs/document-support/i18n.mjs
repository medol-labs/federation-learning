#!/usr/bin/env node
import {existsSync, readFileSync} from 'node:fs';
import {dirname, resolve} from 'node:path';
import {fileURLToPath} from 'node:url';

const scriptDirectory = dirname(fileURLToPath(import.meta.url));
const repositoryRoot = resolve(scriptDirectory, '../..');

export function createTranslator(options = {}) {
  const language = options.language ?? process.env.DOCUMENT_LANGUAGE ?? 'zh-CN';
  const fallbackLanguage = options.fallbackLanguage ?? process.env.DOCUMENT_FALLBACK_LANGUAGE ?? 'en-US';
  const dictionaries = [
    loadDictionary('base'),
    loadDictionary(fallbackLanguage),
    loadDictionary(language),
    options.messages ?? {}
  ];
  return function translate(key, variables = {}) {
    const template = findValue(dictionaries, key) ?? key;
    return String(template).replace(/\{([a-zA-Z0-9_.-]+)\}/g, (_, name) => variables[name] ?? '');
  };
}

function findValue(dictionaries, key) {
  for (let index = dictionaries.length - 1; index >= 0; index -= 1) {
    if (Object.prototype.hasOwnProperty.call(dictionaries[index], key)) {
      return dictionaries[index][key];
    }
  }
  return undefined;
}

function loadDictionary(language) {
  const path = resolve(repositoryRoot, 'docs/document-support/i18n', `${language}.json`);
  if (!existsSync(path)) return {};
  return JSON.parse(readFileSync(path, 'utf8'));
}
