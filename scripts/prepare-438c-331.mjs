#!/usr/bin/env node
import { createHash } from 'node:crypto';
import { spawnSync } from 'node:child_process';
import { existsSync, mkdirSync, readFileSync, writeFileSync } from 'node:fs';
import { homedir } from 'node:os';
import { dirname, resolve } from 'node:path';
import { fileURLToPath } from 'node:url';

const root = resolve(dirname(fileURLToPath(import.meta.url)), '..');
const directory = resolve(root, 'docs/438c/merge');
const registerPath = resolve(directory, '3.3.1-function-register.json');
const draftPath = resolve(directory, '3.3.1-federation-learning.md');
const candidatePath = resolve(root, 'docs/generated/438c/merge/3.3.1-candidate.md');
const wordPath = resolve(root, 'docs/generated/438c/merge/3.3.1-federation-learning.docx');
const converterPath = resolve(root, 'docs/document-pack/federation-learning/markdown_to_docx.py');
const sourcePath = resolve(root, '.medol/source.medol');
const useCasePath = resolve(root, '.medol/prd-use-cases.json');
const model = JSON.parse(readFileSync(resolve(root, '.medol/codegen-model.json'), 'utf8'));
if (!existsSync(useCasePath)) throw new Error(`Missing PRD use-case snapshot: ${useCasePath}. Export it from Medol first.`);
const useCaseSnapshot = JSON.parse(readFileSync(useCasePath, 'utf8'));
if (useCaseSnapshot.schemaVersion !== 1 || !Array.isArray(useCaseSnapshot.useCases)) {
  throw new Error('Unsupported PRD use-case snapshot format. Export it from the current Medol version.');
}
const sourceHash = `fnv1a:${hashSource(readFileSync(sourcePath, 'utf8'))}`;
if (useCaseSnapshot.sourceHash !== sourceHash) {
  throw new Error('PRD use-case snapshot is stale relative to .medol/source.medol. Re-export it from Medol.');
}
const useCasesBySliceId = new Map(useCaseSnapshot.useCases.map((useCase) => {
  const codegenId = `slice-${useCase.id.match(/^UC-([0-9A-F]{8})$/)?.[1]?.toLowerCase()}`;
  if (codegenId === 'slice-undefined') throw new Error(`Invalid PRD use-case ID: ${useCase.id}`);
  return [codegenId, useCase];
}));
if (useCasesBySliceId.size !== useCaseSnapshot.useCases.length) {
  throw new Error('Duplicate PRD use-case IDs.');
}
const translations = model.translations?.['zh-CN'] ?? {};
const contextCodes = [
  ['OrganizationManagement', 'ORG'],
  ['FederationManagement', 'FED'],
  ['DictionaryMaintenance', 'DICT'],
  ['FileUpload', 'FILE'],
  ['RuntimeProvisioning', 'PROV'],
  ['DatasetGovernance', 'DATA'],
  ['ModelRepository', 'REPO'],
  ['TrainingOrchestration', 'TRAIN'],
  ['RuntimeMonitoring', 'MON'],
  ['RuntimeGovernance', 'GOV'],
  ['SecureAggregation', 'SEC'],
  ['RuntimeAgentOperations', 'AGENT'],
];
const includedContexts = new Set(contextCodes.map(([name]) => name));
const slices = model.slices.filter((slice) => includedContexts.has(slice.context));
for (const slice of slices) {
  if (((slice.commands ?? []).length || (slice.readmodels ?? []).length) && !useCasesBySliceId.has(slice.id)) {
    throw new Error(`PRD use-case snapshot does not cover ${slice.context}/${slice.name}.`);
  }
}
const conceptByKey = new Map(model.concepts.map((concept) => [`${concept.context}/${concept.name}`, concept]));
const digest = (value) => createHash('sha256').update(JSON.stringify(value)).digest('hex');
const translated = (title) => translations[title] ?? title;
const conceptName = (slice) => slice.concepts?.[0] ?? 'SharedCapability';
const conceptTitle = (context, name) => name === 'SharedCapability'
  ? '公共查询与协作'
  : translated(conceptByKey.get(`${context}/${name}`)?.title ?? name);
const nextNumber = (entries, pattern) => Math.max(0, ...entries.map(({ id }) => Number(id.match(pattern)?.[1] ?? 0))) + 1;

const current = new Map(slices.map((slice) => {
  const { index: _index, ...semanticSlice } = slice;
  return [slice.id, {
    sourceId: slice.id,
    sourceContext: slice.context,
    sourceName: slice.name,
    sourceConcept: conceptName(slice),
    title: translated(slice.title),
    sourceHash: digest({
      slice: semanticSlice,
      useCase: useCasesBySliceId.get(slice.id),
      labels: [
        translated(model.contexts.find((context) => context.name === slice.context)?.title ?? slice.context),
        conceptTitle(slice.context, conceptName(slice)),
        translated(slice.title),
        ...(slice.events ?? []).map((event) => translated(event.title)),
        ...(slice.readmodels ?? []).map((readmodel) => translated(readmodel.title)),
        ...(slice.specifications ?? []).map((spec) => translated(spec.rule)),
      ],
    }),
  }];
}));
if (current.size !== slices.length) throw new Error('Duplicate source slice IDs.');
for (const slice of slices) {
  if (!translations[slice.title]) throw new Error(`Missing Chinese translation for ${slice.title}.`);
}

const mode = process.argv[2] ?? '--check';
if (!['--init', '--sync', '--candidate', '--check', '--word'].includes(mode)) {
  throw new Error('Usage: node scripts/prepare-438c-331.mjs [--init|--sync|--candidate|--check|--word]');
}
if (mode === '--init' && existsSync(registerPath)) throw new Error('The function register already exists.');
if (mode !== '--init' && !existsSync(registerPath)) throw new Error('Initialize the function register first.');

const register = mode === '--init'
  ? { schemaVersion: 1, revision: '0.1-draft', sectionId: 'LBLS-GN-LBXX', source: '.medol/codegen-model.json', contexts: [], concepts: [], functions: [] }
  : JSON.parse(readFileSync(registerPath, 'utf8'));
const registeredFunctions = new Map(register.functions.map((item) => [item.sourceId, item]));
const added = [...current.values()].filter((item) => !registeredFunctions.has(item.sourceId));
const removed = register.functions.filter((item) => item.status !== 'retired' && !current.has(item.sourceId));
const changed = register.functions.filter((item) => item.status !== 'retired' && current.has(item.sourceId) && item.sourceHash !== current.get(item.sourceId).sourceHash);

if (mode === '--init' || mode === '--sync') {
  if (mode === '--sync') {
    report('New', added);
    report('No longer in model', removed);
    report('Changed', changed);
  }
  for (const [name, code] of contextCodes) {
    if (!model.contexts.some((context) => context.name === name)) throw new Error(`Missing context ${name}.`);
    const context = model.contexts.find((entry) => entry.name === name);
    const existing = register.contexts.find((entry) => entry.sourceName === name);
    if (existing) {
      existing.title = translated(context.title);
    } else {
      register.contexts.push({ id: `LBLS-GN-LBXX-${code}`, sourceId: context.id, sourceName: name, title: translated(context.title), sharedSupport: name === 'DictionaryMaintenance' || name === 'FileUpload' });
    }
  }
  for (const item of current.values()) {
    const conceptKey = `${item.sourceContext}/${item.sourceConcept}`;
    const existingConcept = register.concepts.find((concept) => concept.sourceKey === conceptKey);
    if (existingConcept) {
      existingConcept.title = conceptTitle(item.sourceContext, item.sourceConcept);
    } else {
      const number = nextNumber(register.concepts, /-B(\d+)$/);
      register.concepts.push({ id: `LBLS-GN-LBXX-B${String(number).padStart(3, '0')}`, sourceKey: conceptKey, title: conceptTitle(item.sourceContext, item.sourceConcept) });
    }
    const existing = registeredFunctions.get(item.sourceId);
    if (existing) {
      Object.assign(existing, item);
    } else {
      const number = nextNumber(register.functions, /-F(\d+)$/);
      register.functions.push({ id: `LBLS-GN-LBXX-F${String(number).padStart(4, '0')}`, ...item });
    }
  }
  mkdirSync(directory, { recursive: true });
  writeFileSync(registerPath, `${JSON.stringify(register, null, 2)}\n`);
  if (mode === '--init') writeFileSync(draftPath, render(register));
}

if (mode === '--candidate' || mode === '--sync') {
  if (mode === '--candidate' && (added.length || removed.length || changed.length)) {
    throw new Error('The register differs from the model. Review changes and run --sync first.');
  }
  mkdirSync(dirname(candidatePath), { recursive: true });
  writeFileSync(candidatePath, render(register));
  console.log(`Candidate: ${candidatePath}`);
}

if (mode === '--check' || mode === '--word') {
  const ids = register.functions.map((item) => item.id);
  const activeIds = register.functions.filter((item) => item.status !== 'retired').map((item) => item.id);
  if (new Set(ids).size !== ids.length) throw new Error('Duplicate LBLS function IDs.');
  if (register.functions.some((item) => item.status === 'retired' && current.has(item.sourceId))) {
    throw new Error('A retired function is still present in the current model.');
  }
  if (added.length || removed.length || changed.length) {
    report('New', added);
    report('No longer in model', removed);
    report('Changed', changed);
    throw new Error('Source model differs from the function register. Review it before --sync.');
  }
  const draft = readFileSync(draftPath, 'utf8');
  const rows = [...draft.matchAll(/^\| (\d+) \|[^\n]*?(LBLS-GN-LBXX-F\d+) \|/gm)];
  if (rows.length !== activeIds.length) throw new Error(`Draft has ${rows.length} function rows; register has ${activeIds.length} active functions.`);
  rows.forEach((match, index) => {
    if (Number(match[1]) !== index + 1) throw new Error(`Function row ${index + 1} has sequence ${match[1]}.`);
  });
  if (new Set(rows.map((match) => match[2])).size !== rows.length) throw new Error('Duplicate function IDs in draft.');
  if (activeIds.some((id) => !rows.some((match) => match[2] === id))) throw new Error('Draft omits registered function IDs.');
  const details = [...draft.matchAll(/^\*\*[^\n]*? \/ (LBLS-GN-LBXX-F\d+)\*\*$/gm)].map((match) => match[1]);
  if (details.length !== activeIds.length || new Set(details).size !== details.length || activeIds.some((id) => !details.includes(id))) {
    throw new Error('Detailed requirements do not match the function register.');
  }
  console.log(`3.3.1 check passed: ${register.contexts.length} first-level functions, ${register.concepts.length} second-level functions, ${activeIds.length} third-level functions.`);
}

if (mode === '--word') {
  mkdirSync(dirname(wordPath), { recursive: true });
  const bundledPython = resolve(homedir(), '.cache/codex-runtimes/codex-primary-runtime/dependencies/python/bin/python3');
  const python = process.env.PYTHON ?? (existsSync(bundledPython) ? bundledPython : 'python3');
  const conversion = spawnSync(python, [
    converterPath, draftPath, '--out', wordPath, '--landscape',
    '--heading-number-prefix', '3.3.1'
  ], {
    encoding: 'utf8',
    cwd: root,
  });
  if (conversion.status !== 0) {
    throw new Error(`Word export failed: ${conversion.stderr || conversion.stdout}`);
  }
  console.log(`Word chapter: ${wordPath}`);
}

function report(label, items) {
  if (items.length) {
    const sample = items.slice(0, 12).map((item) => `${item.sourceContext}/${item.sourceName}`).join(', ');
    console.error(`${label} (${items.length}): ${sample}${items.length > 12 ? ', ...' : ''}`);
  }
}

function render(data) {
  const contextByName = new Map(data.contexts.map((context) => [context.sourceName, context]));
  const conceptBySource = new Map(data.concepts.map((concept) => [concept.sourceKey, concept]));
  const functionsBySource = new Map(data.functions.map((item) => [item.sourceId, item]));
  const rows = slices.map((slice, index) => {
    const item = functionsBySource.get(slice.id);
    if (!item) return null;
    const context = contextByName.get(slice.context);
    const concept = conceptBySource.get(`${slice.context}/${conceptName(slice)}`);
    const note = context.sharedSupport ? '共享支撑能力；与系统管理章节避免重复定义' : slice.context === 'RuntimeAgentOperations' ? '参与方本地执行' : '';
    return `| ${index + 1} | ${context.title} / ${context.id} | ${concept.title} / ${concept.id} | ${item.title} / ${item.id} | ${note} |`;
  }).filter(Boolean);
  const detailSections = contextCodes.flatMap(([contextName]) => {
    const context = contextByName.get(contextName);
    const contextSlices = slices.filter((slice) => slice.context === contextName);
    if (!context || !contextSlices.length) return [];
    return [
      `## ${context.title} / ${context.id}`,
      '',
      ...contextSlices.flatMap((slice) => {
        const item = functionsBySource.get(slice.id);
        if (!item) return [];
        return renderRequirement(slice, item);
      }),
    ];
  });
  return [
    '# 3.3.1 联邦学习平台模块配置项功能需求 / LBLS-GN-LBXX',
    '',
    '本模块覆盖组织与联邦协作、运行时接入、数据集治理、模型管理、训练编排、安全聚合及参与方本地执行。原始训练数据保留在参与方侧。',
    '',
    '## 表 3 联邦学习模块的软件功能划分',
    '',
    '| 序号 | 一级功能名称/标识 | 二级功能名称/标识 | 三级功能名称/标识 | 备注 |',
    '| ---: | --- | --- | --- | --- |',
    ...rows,
    '',
    ...detailSections,
  ].join('\n');
}

function renderRequirement(slice, item) {
  const actors = [...new Set((slice.actors ?? []).map((actor) => translated(actor.title)))];
  const screens = [...new Set((slice.screens ?? []).map((screen) => translated(screen.title)))];
  const triggers = [...new Set((slice.processors ?? []).map((processor) => translated(processor.title)))];
  const commands = slice.commands ?? [];
  const events = slice.events ?? [];
  const views = slice.readmodels ?? [];
  const rules = [...new Map((slice.specifications ?? []).map((spec) => [spec.title, spec])).values()];
  const negativeAction = /^(Reject|Fail)/.test(slice.name);
  const success = events.filter((event) => negativeAction || !isFailure(event.name));
  const failure = negativeAction ? [] : events.filter((event) => isFailure(event.name));
  const result = [
    `**${item.title} / ${item.id}**`,
    '',
    `${commands.length ? `系统应支持${item.title}` : triggers.length ? `系统应自动执行${item.title}` : `系统应提供${item.title}`}，${actors.length ? `由${actors.join('、')}执行` : triggers.length ? '由业务过程触发' : '供相关用户查询'}。`,
    '',
  ];
  if (screens.length) result.push(`操作入口：${screens.join('、')}。`, '');
  if (triggers.length) result.push(`自动触发条件：${triggers.join('、')}。`, '');
  const useCase = useCasesBySliceId.get(slice.id);
  if (useCase) result.push(...renderUseCase(useCase));
  const automatedActions = [...new Set((slice.processors ?? []).flatMap((processor) => (processor.dependencies ?? [])
    .filter((dependency) => dependency.direction === 'OUTBOUND' && dependency.elementType === 'COMMAND')
    .map((dependency) => translated(dependency.title))))];
  if (automatedActions.length) result.push(`后续处理：系统发起${automatedActions.join('、')}。`, '');
  if (commands.length) {
    result.push('输入要求：', '');
    for (const command of commands) {
      if (commands.length > 1) result.push(`- ${translated(command.title)}：`);
      const fields = (command.fields ?? []).filter((field) => !field.generated && !field.technicalAttribute);
      if (fields.length) {
        result.push(...fields.map((field) => `- ${renderField(field)}`));
      } else {
        result.push('- 不要求用户填写业务字段，由系统关联已有业务记录。');
      }
    }
    result.push('');
  }
  if (rules.length) {
    const distinctRules = [...new Set(rules.map((spec) => {
      const rule = translated(spec.rule);
      return isDeliveryLanguage(rule) ? rule : translated(spec.title);
    }).filter((rule) => isDeliveryLanguage(rule)))];
    if (distinctRules.length) {
      result.push('业务约束：', '', ...distinctRules.map((rule) => `- ${endSentence(rule)}`), '');
    }
  }
  if (success.length) result.push(`成功结果：${[...new Set(success.map((event) => translated(event.title)))].join('、')}。`, '');
  if (slice.stateChange?.to) result.push(`状态变化：${translated(slice.stateChange.to)}。`, '');
  if (failure.length) result.push(`异常结果：${[...new Set(failure.map((event) => translated(event.title)))].join('、')}。`, '');
  const rejectionReasons = [...new Set(rules.flatMap((spec) => {
    const outcomes = Array.isArray(spec.then) ? spec.then : spec.then ? [spec.then] : [];
    return outcomes.filter((outcome) => outcome.outcome === 'REJECT' || outcome.outcome === 'ERROR')
      .map((outcome) => translated(outcome.description ?? outcome.title))
      .filter((reason) => hasChinese(reason));
  }))];
  if (rejectionReasons.length) result.push(`不满足约束时，应拒绝执行并说明原因，包括：${rejectionReasons.join('、')}。`, '');
  const scenarios = rules.map(renderScenario).filter(Boolean);
  if (scenarios.length) result.push('验收情形：', '', ...scenarios.map((scenario) => `- ${scenario}`), '');
  if (views.length) {
    result.push('查询与展示：', '');
    for (const view of views) {
      const visibleFields = (view.fields ?? []).filter((field) => !field.technicalAttribute);
      result.push(`- 应提供${translated(view.title)}${visibleFields.length ? `，包含${visibleFields.map(fieldTitle).join('、')}` : ''}。`);
    }
    result.push('');
  }
  if (!success.length && !views.length && !automatedActions.length) {
    result.push('待核对：当前业务定义未明确可验收的处理结果，合并前需确认该功能要求。', '');
  }
  return result;
}

function renderUseCase(useCase) {
  const display = (value) => translated(splitName(value));
  const lines = [`<!-- PRD use case ${useCase.id} -->`, ''];
  if (useCase.preconditions.length) {
    lines.push(`前提条件：${useCase.preconditions.map((condition) => display(condition.text)).join('；')}。`, '');
  } else {
    lines.push('前提条件：具体业务前提仍需评审确认。', '');
  }
  lines.push('主事件流：', '');
  for (const [index, step] of useCase.mainFlow.entries()) {
    const name = display(step.text);
    const actor = useCase.actor ? display(useCase.actor.text) : '发起方';
    const description = step.kind === 'readmodel'
      ? `系统提供${name}的查询结果。`
      : step.kind === 'command'
        ? useCase.initiation === 'automation'
          ? `系统响应${display(useCase.trigger.text)}，执行${name}。`
          : `${actor}提交${name}。`
        : `系统形成${name}的业务结果。`;
    lines.push(`${index + 1}. ${description}`);
  }
  lines.push('');
  if (useCase.alternateFlows.length) {
    lines.push('其他事件流（异常或扩展）：', '');
    for (const flow of useCase.alternateFlows) {
      const result = display(flow.outcome.text);
      const handling = flow.outcome.kind === 'rejection'
        ? `拒绝操作并说明${result}`
        : flow.outcome.kind === 'error'
          ? `报告${result}`
          : `形成${result}的业务结果`;
      lines.push(`- ${display(flow.name)}：${handling}；具体分支判定条件需评审确认。`);
    }
    lines.push('');
  } else if (useCase.openQuestions.some((question) => question.includes('exceptional'))) {
    lines.push('其他事件流（异常或扩展）：待评审确认。', '');
  }
  return lines;
}

function hashSource(value) {
  let hash = 2166136261;
  for (let index = 0; index < value.length; index += 1) {
    hash ^= value.charCodeAt(index);
    hash = Math.imul(hash, 16777619);
  }
  return (hash >>> 0).toString(16).padStart(8, '0');
}

function renderScenario(spec) {
  const title = translated(spec.title);
  if (!isDeliveryLanguage(title)) return null;
  const given = [...new Set((spec.given ?? []).map((item) => translated(item.title)).filter(isDeliveryLanguage))];
  const actions = [...new Set((spec.when ?? []).map((item) => translated(item.title)).filter(isDeliveryLanguage))];
  const outcomes = Array.isArray(spec.then) ? spec.then : spec.then ? [spec.then] : [];
  const expected = outcomes.map((outcome) => {
    const description = translated(outcome.description ?? outcome.title);
    if (!isDeliveryLanguage(description)) return null;
    return outcome.outcome === 'REJECT' || outcome.outcome === 'ERROR'
      ? `拒绝操作并说明${description}`
      : `产生${description}结果`;
  }).filter(Boolean);
  if (!actions.length || !expected.length) return null;
  return `${title}：${given.length ? `在${given.join('、')}的情况下，` : ''}执行${actions.join('、')}，应${expected.join('，并')}。`;
}

function renderField(field) {
  const type = {
    UUID: '标识', String: '文本', Text: '文本', Boolean: '布尔值', Int: '整数', Long: '整数',
    Decimal: '小数', DateTime: '日期时间',
  }[field.type] ?? translated(splitName(field.type.replaceAll('.', ' ')));
  const required = field.optional ? '可选' : '必填';
  const multiple = field.cardinality === 'Multiple' ? '，可多项' : '';
  return `${fieldTitle(field)}（${type}，${required}${multiple}）。`;
}

function fieldTitle(field) {
  return translated(splitName(field.name));
}

function splitName(name) {
  return name.replace(/([a-z\d])([A-Z])/g, '$1 $2')
    .replace(/([A-Z])([A-Z][a-z])/g, '$1 $2')
    .replace(/\b\w/g, (letter) => letter.toUpperCase());
}

function isFailure(name) {
  return /Failed|Failure|Rejected|Unavailable|Denied|Invalid|Error/.test(name);
}

function hasChinese(value) {
  return /[\u3400-\u9fff]/u.test(value ?? '');
}

function isDeliveryLanguage(value) {
  return hasChinese(value) && !/[A-Za-z]{4,}|[A-Za-z]+\.[A-Za-z]+|`/.test(value);
}

function endSentence(value) {
  return /[。！？]$/u.test(value) ? value : `${value}。`;
}
