export function createModelTerminology(markdown, overrides = {}) {
  const terms = new Map();
  for (const line of markdown.split(/\r?\n/u)) {
    const match = line.match(/^\|\s*(.*?)\s*\|\s*(.*?)\s*\|\s*(.*?)\s*\|\s*$/u);
    if (!match || match[3] !== '已翻译') continue;
    const source = match[1].trim();
    const target = match[2].trim();
    if (source && target && source !== target && !terms.has(source)) terms.set(source, target);
  }
  for (const [source, target] of Object.entries(overrides)) {
    if (source && target && !terms.has(source)) terms.set(source, target);
  }

  const sources = [...terms.keys()]
    .filter((source) => /[A-Za-z]/u.test(source))
    .sort((left, right) => right.length - left.length || left.localeCompare(right));
  const pattern = sources.length
    ? new RegExp(`(?<![A-Za-z0-9_./:-])(?:${sources.map(escapeRegExp).join('|')})(?![A-Za-z0-9_/-])`, 'gu')
    : null;

  return function translateMarkdown(content) {
    if (!pattern) return content;
    let fenced = false;
    return content.split(/\r?\n/u).map((line) => {
      if (/^\s*```/u.test(line)) {
        fenced = !fenced;
        return line;
      }
      if (fenced) return line;
      return line.split(/(`[^`]*`)/u).map((part) => {
        if (part.startsWith('`') && part.endsWith('`')) return part;
        return part.replace(pattern, (source, offset) => {
          const before = part.slice(0, offset);
          const after = part.slice(offset + source.length);
          if (/[A-Za-z0-9][ \t]+$/u.test(before) || /^[ \t]+[A-Za-z0-9]/u.test(after)) {
            return source;
          }
          return terms.get(source);
        });
      }).join('');
    }).join('\n');
  };
}

function escapeRegExp(value) {
  return value.replace(/[.*+?^${}()|[\]\\]/gu, '\\$&');
}
