import { readFileSync } from 'node:fs';
import { fileURLToPath } from 'node:url';
import path from 'node:path';

export function loadSimulationModel() {
  const currentFile = fileURLToPath(import.meta.url);
  const root = path.resolve(path.dirname(currentFile), '../..');
  const modelPath = path.join(root, 'simulation-model.json');
  return JSON.parse(readFileSync(modelPath, 'utf8'));
}
