import assert from 'node:assert/strict';
import test from 'node:test';
import { createModelTerminology } from './model-terminology.mjs';

test('translates longest model terms without changing code or paths', () => {
  const translate = createModelTerminology(`| 原文 | 译文 | 状态 |
| --- | --- | --- |
| Round Execution | 轮次执行 | 已翻译 |
| Round Execution Started | 轮次执行已启动 | 已翻译 |
| File Upload | 文件上传 | 已翻译 |
| Activation | 激活 | 已翻译 |`, {
    'organization Activation Lead Time': '组织激活耗时',
    'File Upload': '不应覆盖模型译名',
  });
  const input = [
    '| Round Execution Started | File Upload |',
    'Round Execution Started 后可以查看 File Upload。',
    'organization Activation Lead Time 需要项目补译。',
    '`Round Execution Started` 保持原样。',
    '/docs/File Upload/path 保持原样。',
    '```bash',
    'echo Round Execution Started',
    '```',
  ].join('\n');
  const output = translate(input);

  assert.match(output, /\| 轮次执行已启动 \| 文件上传 \|/u);
  assert.match(output, /轮次执行已启动 后可以查看 文件上传。/u);
  assert.match(output, /组织激活耗时 需要项目补译。/u);
  assert.match(output, /`Round Execution Started` 保持原样。/u);
  assert.match(output, /\/docs\/File Upload\/path 保持原样。/u);
  assert.match(output, /echo Round Execution Started/u);
});
