export function createDataGenerator(seed, namespace) {
  const ns = namespace || '';

  return {
    value(input, key) {
      return generatedValue(input.typeInfo || { baseType: input.type || 'String' }, key, input.label || input.name);
    }
  };

  function generatedValue(typeInfo, key, label) {
    if (typeInfo.optional) return null;
    if (typeInfo.list) {
      return [generatedValue({ ...typeInfo, list: false }, key + '.item', label)];
    }
    if (typeInfo.kind === 'value-scalar') {
      return generatedValue(typeInfo.base || { baseType: 'String' }, key + '.value', label);
    }
    if (typeInfo.kind === 'value-object') {
      return Object.fromEntries((typeInfo.fields || []).map((field) => [
        field.name,
        generatedValue(field, key + '.' + field.name, field.name)
      ]));
    }
    if (typeInfo.kind === 'enum') {
      return (typeInfo.values || [slug(label || key).toUpperCase()])[0];
    }

    switch (String(typeInfo.baseType || typeInfo.type || 'String').toLowerCase()) {
      case 'uuid':
        return uuid(key);
      case 'int':
      case 'integer':
        return integer(key, 1, 999);
      case 'long':
        return integer(key, 1000, 999999);
      case 'double':
      case 'float':
      case 'number':
      case 'decimal':
      case 'bigdecimal':
        return Number((Number(positiveHash(key) % 10000n) / 100).toFixed(2));
      case 'boolean':
        return positiveHash(key) % 2n === 0n;
      case 'date':
      case 'localdate':
        return '2026-01-' + String(Number(positiveHash(key) % 28n) + 1).padStart(2, '0');
      case 'datetime':
      case 'localdatetime':
        return '2026-01-01T' + String(Number(positiveHash(key) % 24n)).padStart(2, '0') + ':00:00';
      case 'instant':
        return '2026-01-01T00:' + String(Number(positiveHash(key) % 60n)).padStart(2, '0') + ':00Z';
      case 'string':
      default:
        return String(label || key).toLowerCase().includes('email')
          ? 'sample-' + Number(positiveHash(key) % 10000n) + '@example.test'
          : slug(label || 'sample') + '-' + String(Number(positiveHash(key) % 999n) + 1).padStart(3, '0');
    }
  }

  function uuid(key) {
    const hex = positiveHash(key).toString(16).padStart(32, '0').slice(0, 32);
    return hex.slice(0, 8) + '-' + hex.slice(8, 12) + '-4' + hex.slice(13, 16) + '-8' + hex.slice(17, 20) + '-' + hex.slice(20, 32);
  }

  function integer(key, min, max) {
    return min + Number(positiveHash(key) % BigInt(max - min + 1));
  }

  function positiveHash(key) {
    let hash = BigInt(seed || 1001) + 1125899906842597n;
    const input = ns + ':' + key;
    for (let index = 0; index < input.length; index += 1) {
      hash = hash * 31n + BigInt(input.charCodeAt(index));
    }
    return hash < 0n ? -hash : hash;
  }

  function slug(value) {
    return String(value || 'sample')
      .replace(/([a-z0-9])([A-Z])/g, '$1-$2')
      .replace(/[^A-Za-z0-9]+/g, '-')
      .replace(/^-+|-+$/g, '')
      .toLowerCase() || 'sample';
  }
}
