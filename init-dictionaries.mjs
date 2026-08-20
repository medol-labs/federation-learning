#!/usr/bin/env node

import {readFile} from 'node:fs/promises';
import {existsSync} from 'node:fs';
import {dirname, resolve} from 'node:path';
import {fileURLToPath} from 'node:url';
import {createHash} from 'node:crypto';

const scriptDir = dirname(fileURLToPath(import.meta.url));
const args = parseArgs(process.argv.slice(2));

if (args.help || args.h) {
    printHelp();
    process.exit(0);
}

const dataPath = resolve(process.cwd(), args.data ?? resolve(scriptDir, 'dictionaries.json'));
const baseUrl = trimSlash(args['base-url'] ?? process.env.DICTIONARY_API_URL ?? 'http://localhost:8080');
const dryRun = Boolean(args['dry-run']);
const force = Boolean(args.force);
const strict = Boolean(args.strict);
const headers = requestHeaders(args.header);
const endpoints = {
    registerDictionary: args['register-path'] ?? '/dictionary/registerdictionary',
    addDictionaryValue: args['add-value-path'] ?? '/dictionaryvalue/adddictionaryvalue',
    dictionaryCatalog: args['dictionary-catalog-path'] ?? '/dictionary/dictionarycatalog',
    dictionaryValueCatalog: args['value-catalog-path'] ?? '/dictionaryvalue/dictionaryvaluecatalog'
};

if (!existsSync(dataPath)) {
    fail(`Cannot find dictionary data file: ${dataPath}`);
}

if (typeof fetch !== 'function') {
    fail('This script requires Node.js 18 or newer because it uses global fetch.');
}

const dictionaries = normalizeDictionaryData(JSON.parse(await readFile(dataPath, 'utf8')));
let posted = 0;
let skipped = 0;
let failed = 0;

console.log(`[dict-init] baseUrl=${baseUrl}, data=${dataPath}`);

for (const dictionary of dictionaries) {
    const dictionaryCode = dictionary.dictionaryCode;
    const existingDictionary = dryRun || force ? undefined : await findDictionary(dictionaryCode);
    const dictionaryId = dictionary.dictionaryId ?? existingDictionary?.dictionaryId ?? stableUuid(`dictionary:${dictionaryCode}`);
    const registerPayload = registerDictionaryPayload(dictionary, dictionaryId);

    if (dryRun) {
        printDryRun('RegisterDictionary', endpoints.registerDictionary, registerPayload);
    } else if (existingDictionary && !force) {
        skipped += 1;
        console.log(`[dict-init] SKIP dictionary ${dictionaryCode}`);
    } else {
        await postCommand('RegisterDictionary', endpoints.registerDictionary, registerPayload);
    }

    const existingValues = dryRun || force ? [] : await findDictionaryValues(dictionaryCode);
    const existingValueCodes = new Set(existingValues.map((value) => normalizeCode(value.valueCode)));

    for (let index = 0; index < dictionary.values.length; index += 1) {
        const value = dictionary.values[index];
        const valueCode = value.valueCode;
        const addPayload = addDictionaryValuePayload(dictionary, value, dictionaryId, index);

        if (dryRun) {
            printDryRun('AddDictionaryValue', endpoints.addDictionaryValue, addPayload);
        } else if (existingValueCodes.has(normalizeCode(valueCode)) && !force) {
            skipped += 1;
            console.log(`[dict-init] SKIP value ${dictionaryCode}.${valueCode}`);
        } else {
            await postCommand('AddDictionaryValue', endpoints.addDictionaryValue, addPayload);
        }
    }
}

if (dryRun) {
    console.log('[dict-init] dry run complete');
} else {
    console.log(`[dict-init] complete: posted=${posted}, skipped=${skipped}, failed=${failed}`);
}

function parseArgs(argv) {
    const result = {};
    for (let index = 0; index < argv.length; index += 1) {
        const arg = argv[index];
        if (!arg.startsWith('--')) continue;
        const key = arg.slice(2);
        const next = argv[index + 1];
        const value = !next || next.startsWith('--') ? true : next;
        if (value !== true) index += 1;
        if (Object.hasOwn(result, key)) {
            result[key] = Array.isArray(result[key]) ? [...result[key], value] : [result[key], value];
        } else {
            result[key] = value;
        }
    }
    return result;
}

function normalizeDictionaryData(raw) {
    const dictionaries = Array.isArray(raw) ? raw : raw.dictionaries;
    if (!Array.isArray(dictionaries)) fail('Dictionary data must be an array or an object with a dictionaries array.');

    return dictionaries.map((dictionary, index) => {
        const dictionaryCode = dictionary.dictionaryCode ?? dictionary.code;
        if (!dictionaryCode) fail(`Dictionary at index ${index} is missing dictionaryCode.`);
        const values = dictionary.values ?? [];
        if (!Array.isArray(values)) fail(`Dictionary ${dictionaryCode} values must be an array.`);
        return {
            ...dictionary,
            dictionaryCode,
            dictionaryName: dictionary.dictionaryName ?? dictionary.name ?? humanize(dictionaryCode),
            ownerContext: dictionary.ownerContext ?? dictionary.context ?? 'DictionaryMaintenance',
            multiTenant: dictionary.multiTenant ?? false,
            values: values.map((value, valueIndex) => {
                const valueCode = value.valueCode ?? value.code;
                if (!valueCode) fail(`Dictionary ${dictionaryCode} value at index ${valueIndex} is missing valueCode.`);
                return {
                    ...value,
                    valueCode,
                    displayName: value.displayName ?? value.name ?? humanize(valueCode),
                    displayOrder: value.displayOrder ?? value.order ?? (valueIndex + 1) * 10,
                    active: value.active ?? true
                };
            })
        };
    });
}

function registerDictionaryPayload(dictionary, dictionaryId) {
    return {
        dictionaryId,
        dictionaryCode: dictionary.dictionaryCode,
        dictionaryName: dictionary.dictionaryName,
        ownerContext: dictionary.ownerContext,
        description: dictionary.description ?? null,
        multiTenant: dictionary.multiTenant
    };
}

function addDictionaryValuePayload(dictionary, value, dictionaryId) {
    return {
        dictionaryValueId: value.dictionaryValueId ?? stableUuid(`dictionary-value:${dictionary.dictionaryCode}:${value.valueCode}`),
        dictionaryId,
        dictionaryCode: dictionary.dictionaryCode,
        valueCode: value.valueCode,
        displayName: value.displayName,
        displayOrder: value.displayOrder,
        description: value.description ?? null,
        active: value.active,
        effectiveFrom: value.effectiveFrom ?? null,
        effectiveUntil: value.effectiveUntil ?? null
    };
}

async function findDictionary(dictionaryCode) {
    const query = `dictionaryCode.equals=${encodeURIComponent(dictionaryCode)}&size=100`;
    const result = await getJson(`${endpoints.dictionaryCatalog}?${query}`);
    return pageContent(result).find((item) => normalizeCode(item.dictionaryCode) === normalizeCode(dictionaryCode));
}

async function findDictionaryValues(dictionaryCode) {
    const query = `dictionaryCode.equals=${encodeURIComponent(dictionaryCode)}&size=500`;
    const result = await getJson(`${endpoints.dictionaryValueCatalog}?${query}`);
    return pageContent(result);
}

async function getJson(path) {
    const url = `${baseUrl}${path}`;
    let response;
    try {
        response = await fetch(url, {
            headers: {
                accept: 'application/json',
                ...headers
            }
        });
    } catch (error) {
        fail(`GET ${url} failed: ${errorSummary(error)}`);
    }
    const text = await response.text();
    if (!response.ok) fail(`GET ${url} failed: ${response.status} ${compact(text)}`);
    return text ? JSON.parse(text) : {};
}

async function postCommand(label, path, payload) {
    const url = `${baseUrl}${path}`;
    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'content-type': 'application/json',
                ...headers
            },
            body: JSON.stringify(payload)
        });
        const body = await response.text();
        if (!response.ok) {
            failed += 1;
            console.warn(`[dict-init] ${response.status} ${label}: ${compact(body)}`);
            if (strict) process.exitCode = 1;
            return;
        }
        posted += 1;
        console.log(`[dict-init] OK ${label}`);
    } catch (error) {
        failed += 1;
        console.warn(`[dict-init] ERROR ${label}: ${errorSummary(error)}`);
        if (strict) process.exitCode = 1;
    }
}

function requestHeaders(rawHeaders) {
    const result = {};
    const values = rawHeaders === undefined ? [] : Array.isArray(rawHeaders) ? rawHeaders : [rawHeaders];
    for (const value of values) {
        const text = String(value);
        const separator = text.indexOf(':');
        if (separator <= 0) fail(`Invalid --header value "${text}". Use "Header-Name: value".`);
        result[text.slice(0, separator).trim()] = text.slice(separator + 1).trim();
    }
    return result;
}

function pageContent(body) {
    if (Array.isArray(body)) return body;
    if (Array.isArray(body.content)) return body.content;
    if (Array.isArray(body.data)) return body.data;
    return [];
}

function printDryRun(label, path, payload) {
    console.log(`DRY ${label} -> ${baseUrl}${path}`);
    console.log(JSON.stringify(payload, null, 2));
}

function stableUuid(key) {
    const chars = createHash('sha1').update(String(key)).digest('hex').slice(0, 32).split('');
    chars[12] = '5';
    chars[16] = ((Number.parseInt(chars[16], 16) & 0x3) | 0x8).toString(16);
    const hex = chars.join('');
    return `${hex.slice(0, 8)}-${hex.slice(8, 12)}-${hex.slice(12, 16)}-${hex.slice(16, 20)}-${hex.slice(20)}`;
}

function normalizeCode(value) {
    return String(value ?? '').trim().toUpperCase();
}

function trimSlash(value) {
    return String(value).replace(/\/+$/g, '');
}

function humanize(value) {
    return String(value ?? '')
        .replace(/([a-z0-9])([A-Z])/g, '$1 $2')
        .replace(/[_-]+/g, ' ')
        .replace(/\s+/g, ' ')
        .trim()
        .toLowerCase()
        .replace(/\b\w/g, (letter) => letter.toUpperCase());
}

function compact(value) {
    return String(value ?? '').replace(/\s+/g, ' ').trim().slice(0, 500);
}

function errorSummary(error) {
    return error?.message ?? String(error);
}

function fail(message) {
    console.error(`[dict-init] ${message}`);
    process.exit(1);
}

function printHelp() {
    console.log(`Usage:
  node init-dictionaries.mjs --base-url http://localhost:8080

Options:
  --data <file>                       Dictionary JSON file. Defaults to ./dictionaries.json next to this script.
  --base-url <url>                    Dictionary backend base URL. Defaults to DICTIONARY_API_URL or http://localhost:8080.
  --dry-run                           Print commands without calling the API.
  --force                             Post commands without checking existing read models.
  --strict                            Set a non-zero exit code when a POST command fails.
  --header "Name: value"              Extra HTTP header. Repeatable.
  --register-path <path>              RegisterDictionary endpoint path.
  --add-value-path <path>             AddDictionaryValue endpoint path.
  --dictionary-catalog-path <path>    DictionaryCatalog read endpoint path.
  --value-catalog-path <path>         DictionaryValueCatalog read endpoint path.
`);
}
