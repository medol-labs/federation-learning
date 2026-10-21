#!/usr/bin/env node
import { spawnSync } from 'node:child_process';
import { existsSync, mkdirSync, readFileSync, writeFileSync } from 'node:fs';
import { homedir } from 'node:os';
import { basename, dirname, join, relative, resolve } from 'node:path';
import { fileURLToPath } from 'node:url';
import { createTranslator } from '../docs/document-support/i18n.mjs';
import { createModelTerminology } from '../docs/document-support/model-terminology.mjs';
import { loadBaseline, readJson, sha256, sourceAssociation, verifyDelivery } from '../docs/document-support/document-traceability.mjs';

const scriptDirectory = dirname(fileURLToPath(import.meta.url));
const repositoryRoot = resolve(scriptDirectory, '..');
const workspaceRoot = resolve(repositoryRoot, '..');
const defaultTemplateDirectory = resolve(workspaceRoot, 'tmp/438C文档模版');
const markdownToDocxScript = resolve(
  repositoryRoot,
  'docs/document-pack/federation-learning/markdown_to_docx.py'
);
const engineeringFactsCollectorScript = resolve(repositoryRoot, 'docs/document-support/collect-engineering-facts.mjs');
const bundledPython = resolve(
  homedir(),
  '.cache/codex-runtimes/codex-primary-runtime/dependencies/python/bin/python3'
);

const documentationKinds = new Set([
  'all',
  'software-requirements',
  'software-design',
  'database-design',
  'test-plan',
  'test-description',
  'installation-plan',
  'user-manual',
]);

const args = parseArgs(process.argv.slice(2));
const options = {
  sourceDirectory: resolve(repositoryRoot, args['source-dir'] ?? 'docs/438c/sources'),
  projectConfigPath: resolve(repositoryRoot, args.config ?? 'docs/438c/config/project.json'),
  engineeringFactsConfigPath: resolve(repositoryRoot, args['facts-config'] ?? 'docs/document-support/config/engineering-facts-sources.json'),
  engineeringFactsOutputPath: resolve(repositoryRoot, args['facts-out'] ?? 'docs/generated/facts/engineering-facts.json'),
  outputDirectory: resolve(repositoryRoot, args.out ?? 'docs/generated/438c'),
  templateDirectory: resolve(args['template-dir'] ?? defaultTemplateDirectory),
  manualDirectory: resolve(repositoryRoot, args.manual ?? 'docs/438c/manual'),
  format: args.format ?? 'all',
  language: args.language ?? 'zh-CN',
  only: args.only ?? 'all',
  modelPath: resolve(repositoryRoot, args.model ?? '.medol/codegen-model.json'),
  modelSourcePath: resolve(repositoryRoot, args['model-source'] ?? '.medol/source.medol'),
  releaseManifestPath: args['release-manifest'] ? resolve(repositoryRoot, args['release-manifest']) : null,
};

if (!['markdown', 'docx', 'all'].includes(options.format)) {
  fail(`Unsupported --format=${options.format}. Use markdown, docx, or all.`);
}

if (!documentationKinds.has(options.only)) {
  fail(`Unsupported --only=${options.only}. Use one of: ${[...documentationKinds].join(', ')}.`);
}

if (!existsSync(options.projectConfigPath)) {
  fail(`Project config does not exist: ${options.projectConfigPath}`);
}

const projectConfig = JSON.parse(readFileSync(options.projectConfigPath, 'utf8'));
const baseline = loadBaseline(options.modelPath, options.modelSourcePath);
const sourceManifestPath = join(options.sourceDirectory, 'source-doc-manifest.json');
const sourceManifest = existsSync(sourceManifestPath) ? readJson(sourceManifestPath) : null;
if (sourceManifest && (sourceManifest.schemaVersion !== 1 || !sourceManifest.documents)) fail('Unsupported source document manifest.');
const manifestPath = join(options.outputDirectory, 'document-manifest.json');
const manifest = existsSync(manifestPath) ? readJson(manifestPath) : { schemaVersion: 1, documents: {} };
if (manifest.schemaVersion !== 1 || !manifest.documents) fail('Unsupported document manifest.');
const t = createTranslator({
  language: options.language,
  fallbackLanguage: args['fallback-language'] ?? projectConfig.fallbackLanguage ?? 'en-US',
});
const generatedAt = new Date().toISOString();
const engineeringFacts = loadEngineeringFacts();
const documentSpecs = [
  {
    id: 'software-requirements',
    title: '软件需求规格说明',
    sourceKind: 'prd',
    template: '10-软件需求规格说明（438C）.doc',
    sections: [
      ['1 范围', ['1.1 标识', '1.2 系统概述', '1.3 文档概述']],
      ['2 引用文档'],
      ['3 需求', ['3.1 要求的状态和方式', '3.2 软件能力需求', '3.3 外部接口需求', '3.4 内部接口需求', '3.5 数据需求', '3.6 质量特性需求']],
      ['4 合格性规定'],
      ['5 需求可追踪性'],
      ['6 注释'],
    ],
  },
  {
    id: 'software-design',
    title: '软件设计说明书',
    sourceKind: 'software-design',
    template: '11-软件设计说明书(438C原始模板).docx',
    sections: [
      ['1 范围', ['1.1 标识', '1.2 系统概述', '1.3 文档概述']],
      ['2 引用文档'],
      ['3 CSCI级设计决策'],
      ['4 CSCI体系结构设计'],
      ['5 CSCI详细设计'],
      ['6 需求的可追踪性'],
      ['7 注释'],
    ],
  },
  {
    id: 'database-design',
    title: '数据库设计说明',
    sourceKind: 'database-design',
    template: '12-数据库设计说明（438C）.doc',
    sections: [
      ['1 范围', ['1.1 标识', '1.2 系统概述', '1.3 文档概述']],
      ['2 引用文档'],
      ['3 数据库级设计决策'],
      ['4 数据库详细设计'],
      ['5 数据库访问单元'],
      ['6 需求的可追踪性'],
      ['7 注释'],
    ],
  },
  {
    id: 'test-plan',
    title: '软件测试计划',
    sourceKind: 'test-outline',
    template: '04-软件测试计划模板438C.docx',
    sections: [
      ['1 范围', ['1.1 标识', '1.2 系统概述', '1.3 文档概述', '1.4 与其他计划的关系']],
      ['2 引用文档'],
      ['3 软件测试环境'],
      ['4 测试标识'],
      ['5 测试进度'],
      ['6 评价准则'],
      ['7 测试活动'],
      ['8 注释'],
    ],
  },
  {
    id: 'test-description',
    title: '软件测试说明',
    sourceKind: 'test-outline',
    template: '13-软件测试说明模板438C.docx',
    sections: [
      ['1 范围', ['1.1 标识', '1.2 系统概述', '1.3 文档概述']],
      ['2 引用文档'],
      ['3 测试准备'],
      ['4 测试说明'],
      ['5 需求的可追踪性'],
      ['6 注释'],
    ],
  },
  {
    id: 'installation-plan',
    title: '软件安装计划',
    sourceKind: 'installation-manual',
    template: '02-软件安装计划模板438C.docx',
    sections: [
      ['1 范围', ['1.1 标识', '1.2 系统概述', '1.3 文档概述', '1.4 与其他计划之间的关系']],
      ['2 引用文档'],
      ['3 安装概述', ['3.1 安装目标', '3.2 联络点', '3.3 保障材料', '3.4 培训', '3.5 任务', '3.6 人员', '3.7 保密性']],
      ['4 现场信息'],
      ['5 安装过程'],
      ['6 注释'],
    ],
  },
  {
    id: 'user-manual',
    title: '用户手册',
    sourceKind: 'user-manual',
    template: '17-用户手册模板（438C）.docx',
    sections: [
      ['1 范围', ['1.1 标识', '1.2 系统概述', '1.3 文档概述']],
      ['2 引用文档'],
      ['3 软件应用'],
      ['4 软件入门'],
      ['5 使用指南'],
      ['6 故障处理'],
      ['7 注释'],
    ],
  },
].filter((spec) => options.only === 'all' || spec.id === options.only);

const sourceMarkdownByKind = new Map();
const terminologyPath = join(options.sourceDirectory, `model-translations.${options.language}.md`);
const terminologyOverridesPath = join(repositoryRoot, 'docs/438c/i18n', `${options.language}.json`);
if (!existsSync(terminologyPath)) {
  fail(`Model terminology snapshot does not exist: ${terminologyPath}. Export the saved document snapshots first.`);
}
const translateModelTerms = createModelTerminology(
  readFileSync(terminologyPath, 'utf8'),
  existsSync(terminologyOverridesPath) ? JSON.parse(readFileSync(terminologyOverridesPath, 'utf8')) : {}
);
mkdirSync(options.outputDirectory, { recursive: true });
mkdirSync(join(options.outputDirectory, 'markdown'), { recursive: true });
if (options.format !== 'markdown') {
  mkdirSync(join(options.outputDirectory, 'word'), { recursive: true });
}

const readmeLines = [
  '# 438C 文档生成结果',
  '',
  `生成时间：${generatedAt}`,
  '',
  '## 文档清单',
  '',
  '| 文档 | Markdown | Word |',
  '| --- | --- | --- |',
];

for (const spec of documentSpecs) {
  const sourceMarkdown = getSourceMarkdown(spec.sourceKind);
  const sourcePaths = [join(options.sourceDirectory, `${spec.sourceKind}.${options.language}.md`), terminologyPath];
  const sources = sourcePaths.map((path) => ({
    path, sha256: sha256(readFileSync(path)),
    association: sourceManifest?.documents[basename(path)] ?? null,
    status: sourceAssociation(sourceManifest?.documents[basename(path)], readFileSync(path), baseline),
  }));
  const applicable = sources.every((source) => source.status === 'verified');
  const release = applicable ? baseline?.metadata.source.release : null;
  const softwareVersion = release
    ? release.label ?? (baseline.metadata.source.versionNo !== undefined ? `model-r${baseline.metadata.source.versionNo}` : null)
    : projectConfig.softwareVersion;
  if (release && !softwareVersion) fail('Released model needs a label or versionNo for the software version.');
  if (options.releaseManifestPath && !applicable) fail(`${spec.id}: source snapshots are stale or unverified; cannot claim delivery applicability.`);
  const delivery = options.releaseManifestPath
    ? verifyDelivery(options.releaseManifestPath, baseline) : null;
  const config = { ...projectConfig, softwareVersion };
  const status = delivery ? 'delivery-verified' : release ? 'released-model-baseline' : 'draft';
  const traceLines = [
    `- 文档版本：${config.documentVersion}`,
    `- 追溯状态：${status}`,
    ...(applicable ? [`- 模型来源 SHA256：${baseline.metadata.source.sha256}`] : []),
    ...(delivery ? [`- 适用交付：${delivery.deliveryId}`] : []),
  ].join('\n');
  const render = (timestamp) => render438cDocument(spec, sourceMarkdown, config, engineeringFacts, timestamp)
    .replace('## 修改记录', `${traceLines}\n\n## 修改记录`);
  // Exclude only volatile generation time and the derived revision counter.
  const contentSha256 = sha256(render('<generation-time>'));
  const key = `${spec.id}.${options.language}`;
  const previous = manifest.documents[key];
  const revision = previous?.contentSha256 === contentSha256 ? previous.revision : (previous?.revision ?? 0) + 1;
  const markdown = render(generatedAt).replace('## 修改记录', `- 文档修订：${revision}\n\n## 修改记录`);
  if (/\b(?:derived from|from from)\b|\b(?:onKind|fanOutSource|fanOutAlias|emits):/u.test(markdown)) {
    fail(`${spec.title} contains internal source or automation syntax.`);
  }
  if (spec.id === 'software-requirements') assertRequirementCoverage(sourceMarkdown, markdown);
  if (spec.id === 'database-design') assertChapterIdCoverage(sourceMarkdown, markdown, /FLPRM-DB-SECTION/u, 'database read models', true, /FLPRM-DB-SECTION/u);
  if (spec.id === 'user-manual') assertChapterIdCoverage(sourceMarkdown, markdown, /功能操作指南/u, 'user operations', false);
  const markdownPath = join(options.outputDirectory, 'markdown', `${spec.id}.md`);
  writeFileSync(markdownPath, markdown, 'utf8');

  let docxPath = '';
  if (options.format !== 'markdown') {
    const out = join(options.outputDirectory, 'word', `${spec.id}.docx`);
    convertMarkdownToDocx(markdownPath, out, spec);
    docxPath = out;
  }

  readmeLines.push(
    `| ${spec.title} | ${relative(options.outputDirectory, markdownPath)} | ${docxPath ? relative(options.outputDirectory, docxPath) : '未生成'} |`
  );
  manifest.documents[key] = {
    documentId: spec.id, language: options.language, documentVersion: config.documentVersion,
    revision, contentSha256, softwareVersion, status, generatedAt,
    applicableModel: applicable ? baseline.metadata : null,
    candidateModel: baseline,
    applicableDelivery: delivery,
    sources: sources.map(({ path, ...source }) => ({ path: relative(repositoryRoot, path), ...source })),
    artifacts: [markdownPath, ...(docxPath ? [docxPath] : [])].map((path) => ({
      filename: relative(options.outputDirectory, path), sha256: sha256(readFileSync(path)),
    })),
    history: previous?.revision === revision ? previous.history : [
      ...(previous?.history ?? []), { revision, contentSha256, documentVersion: config.documentVersion, generatedAt },
    ],
  };
}

writeFileSync(join(options.outputDirectory, 'README.md'), `${readmeLines.join('\n')}\n`, 'utf8');
writeFileSync(manifestPath, `${JSON.stringify(manifest, null, 2)}\n`, 'utf8');

console.log(`[438c] Generated ${documentSpecs.length} document(s) in ${relative(repositoryRoot, options.outputDirectory)}`);

function getSourceMarkdown(kind) {
  if (sourceMarkdownByKind.has(kind)) return sourceMarkdownByKind.get(kind);
  const sourcePath = join(options.sourceDirectory, `${kind}.${options.language}.md`);
  if (!existsSync(sourcePath)) {
    fail(`中文文档来源不存在：${sourcePath}。请先导出项目文档快照。`);
  }
  const markdown = readFileSync(sourcePath, 'utf8').trim();
  sourceMarkdownByKind.set(kind, markdown);
  return markdown;
}

function loadEngineeringFacts() {
  const factsPath = options.engineeringFactsOutputPath;
  if (existsSync(engineeringFactsCollectorScript) && existsSync(options.engineeringFactsConfigPath)) {
    const result = spawnSync(
      'node',
      [
        engineeringFactsCollectorScript,
        `--config=${relative(repositoryRoot, options.engineeringFactsConfigPath)}`,
        `--out=${relative(repositoryRoot, factsPath)}`,
      ],
      {
        cwd: repositoryRoot,
        encoding: 'utf8',
        maxBuffer: 16 * 1024 * 1024,
      }
    );
    if (result.status !== 0) {
      fail(`Engineering facts collection failed.\n${result.stderr || result.stdout}`);
    }
  }
  if (!existsSync(factsPath)) return null;
  return JSON.parse(readFileSync(factsPath, 'utf8'));
}

function render438cDocument(spec, sourceMarkdown, config, facts, timestamp) {
  const lines = [
    `# ${config.projectName} ${spec.title}`,
    '',
    '## 文档控制',
    '',
    `- 文档名称：${config.projectName} ${spec.title}`,
    `- 文档编号：${config.documentCodePrefix}-${spec.id.toUpperCase().replaceAll('-', '-')}`,
    `- 软件名称：${config.softwareName}`,
    `- 软件版本：${config.softwareVersion}`,
    `- 项目名称：${config.projectName}`,
    `- 子系统：${config.subsystemName}`,
    `- 密级：${config.classification}`,
    `- 编制单位：${config.authoringOrganization}`,
    `- 编制时间：${timestamp}`,
    '',
    '## 修改记录',
    '',
    '| 版本 | 日期 | 修改内容 | 修改人 |',
    '| --- | --- | --- | --- |',
    `| ${config.documentVersion} | ${config.documentDate} | 初始编制 | ${config.author} |`,
    '',
  ];

  for (const [section, children = []] of spec.sections) {
    lines.push(`## ${section}`);
    lines.push('');
    lines.push(renderSectionWithAppend(spec, section, sourceMarkdown, config, facts));
    lines.push('');
    for (const child of children) {
      lines.push(`### ${child}`);
      lines.push('');
      lines.push(renderSectionWithAppend(spec, child, sourceMarkdown, config, facts));
      lines.push('');
    }
  }

  return `${lines.join('\n').replace(/\n{3,}/g, '\n\n')}\n`;
}

function renderSectionWithAppend(spec, section, sourceMarkdown, config, facts) {
  return [
    renderSectionContent(spec, section, sourceMarkdown, config, facts),
    readManualSection(spec.id, section, '.append'),
  ].filter(Boolean).join('\n\n');
}

function renderSectionContent(spec, section, sourceMarkdown, config, facts) {
  const manual = readManualSection(spec.id, section);
  if (manual) return manual;

  if (/标识/.test(section)) {
    return [
      `本文档适用于${config.softwareName}（${config.subsystemName}）。`,
      `软件标识为 ${config.softwareIdentifier}，当前版本为 ${config.softwareVersion}。`,
    ].join('\n\n');
  }
  if (/系统概述/.test(section)) {
    return config.systemOverview;
  }
  if (/文档概述/.test(section)) {
    return `本文档按照 438C 文档结构组织，内容依据${config.softwareName}的需求、设计、测试、安装和使用说明资料编制，并结合项目配置和人工维护章节形成交付稿。`;
  }
  if (/引用文档/.test(section)) {
    return renderReferences(config.references);
  }
  if (spec.id === 'installation-plan' && /^3 安装概述$/u.test(section)) {
    return [
      renderInstallationOverview(config, facts),
      extractChapter(sourceMarkdown, /系统说明/u),
      extractChapter(sourceMarkdown, /部署架构/u),
      extractChapter(sourceMarkdown, /环境要求/u),
      extractChapter(sourceMarkdown, /网络与端口/u),
      extractChapter(sourceMarkdown, /安装前准备/u),
    ].filter(Boolean).join('\n\n');
  }
  if (spec.id === 'installation-plan' && /^5 安装过程$/u.test(section)) {
    return [
      renderInstallationProcedure(facts),
      extractChapter(sourceMarkdown, /安装步骤/u),
      extractChapter(sourceMarkdown, /配置说明/u),
      extractChapter(sourceMarkdown, /初始化/u),
      extractChapter(sourceMarkdown, /启动与停止/u),
      extractChapter(sourceMarkdown, /安装验证/u),
      extractChapter(sourceMarkdown, /升级与回滚/u),
      extractChapter(sourceMarkdown, /故障排查/u),
    ].filter(Boolean).join('\n\n');
  }
  if (spec.id === 'test-plan' && /软件测试环境/.test(section)) {
    return [renderTestEnvironment(facts), extractChapter(sourceMarkdown, /测试环境与测试数据/u)].filter(Boolean).join('\n\n');
  }
  if (spec.id === 'test-description' && /测试准备/.test(section)) {
    return [renderTestPreparation(facts), extractChapter(sourceMarkdown, /测试环境与测试数据/u)].filter(Boolean).join('\n\n');
  }
  if (spec.id === 'software-design' && /体系结构/.test(section)) {
    return [
      renderArchitectureFacts(facts),
      applyContextSummaries(extractChapter(sourceMarkdown, /概要设计/u, { deduplicateTables: true })),
      extractChapter(sourceMarkdown, /接口与集成设计/u),
    ].filter(Boolean).join('\n\n')
      || '本节依据系统架构、模块划分、数据结构和集成关系编制。';
  }
  if (spec.id === 'software-design' && /详细设计/.test(section)) {
    return extractChapter(sourceMarkdown, /详细设计/u);
  }
  if (spec.id === 'software-design' && /CSCI级设计决策/.test(section)) {
    return [
      extractChapter(sourceMarkdown, /异常与规则设计/u),
      extractChapter(sourceMarkdown, /质量属性、运维与可观测性设计/u),
    ].filter(Boolean).join('\n\n');
  }
  if (spec.id === 'software-design' && section === '7 注释') {
    return extractChapter(sourceMarkdown, /实现缺口/u);
  }
  if (spec.id === 'database-design') {
    if (section === '3 数据库级设计决策') {
      return [extractChapter(sourceMarkdown, /设计范围/u), extractChapter(sourceMarkdown, /数据库通用决策/u)].join('\n\n');
    }
    if (section === '4 数据库详细设计') {
      return chapterEntries(sourceMarkdown, /FLPRM-DB-SECTION/u)
        .map(({ heading, body }) => [
          scrubDeliveryText(cleanGeneratedMarkdown(heading.replace(/^##\s+/u, '#### '), { preserveIds: true })),
          scrubDeliveryText(demoteHeadings(cleanGeneratedMarkdown(body, { preserveIds: true }), 2)),
        ].filter(Boolean).join('\n\n'))
        .join('\n\n');
    }
    if (section === '5 数据库访问单元') {
      return [renderPersistenceFacts(facts), extractChapter(sourceMarkdown, /Read Model 清单/u)].filter(Boolean).join('\n\n');
    }
  }
  if (spec.id === 'user-manual') {
    if (section === '3 软件应用') {
      return [
        extractChapter(sourceMarkdown, /概述/u),
        extractChapter(sourceMarkdown, /角色与权限/u),
        extractChapter(sourceMarkdown, /数据视图与查询/u),
      ].join('\n\n');
    }
    if (section === '4 软件入门') return extractChapter(sourceMarkdown, /快速开始/u);
    if (section === '5 使用指南') {
      return [renderUserOperationFacts(facts), extractChapter(sourceMarkdown, /功能操作指南/u, { preserveIds: true })].filter(Boolean).join('\n\n');
    }
    if (section === '6 故障处理') {
      return [extractChapter(sourceMarkdown, /规则与异常/u), extractChapter(sourceMarkdown, /常见问题处理/u)].join('\n\n');
    }
  }
  if (spec.id === 'test-plan') {
    if (section === '4 测试标识') {
      return [extractChapter(sourceMarkdown, /测试对象/u), extractChapter(sourceMarkdown, /测试范围边界/u)].join('\n\n');
    }
    if (section === '5 测试进度') return extractChapter(sourceMarkdown, /回归与自动化计划/u);
    if (section === '6 评价准则') return extractChapter(sourceMarkdown, /准出标准/u);
    if (section === '7 测试活动') {
      return [
        /测试目标与范围/u, /测试策略/u, /测试类型/u, /功能测试范围/u,
        /测试场景覆盖/u, /业务规则与异常测试/u, /接口与集成测试/u,
        /数据与 Read Model 测试/u, /非功能测试范围/u,
      ].map((pattern) => extractChapter(sourceMarkdown, pattern)).join('\n\n');
    }
  }
  if (spec.id === 'test-description') {
    if (section === '4 测试说明') {
      return [
        /功能测试范围/u, /测试场景覆盖/u, /业务规则与异常测试/u,
        /接口与集成测试/u, /数据与 Read Model 测试/u, /非功能测试范围/u,
      ].map((pattern) => extractChapter(sourceMarkdown, pattern)).join('\n\n');
    }
    if (section === '5 需求的可追踪性') return extractChapter(sourceMarkdown, /准出标准/u);
  }
  if (spec.id === 'software-requirements') {
    if (section === '3.4 内部接口需求') {
      return '平台服务、支撑服务和运行时代理通过受控接口交换训练配置、运行状态和执行结果；具体协议及接口约束在软件设计说明书中维护。';
    }
    if (section === '3 需求') return extractChapter(sourceMarkdown, /产品背景与目标/u);
    if (section === '3.1 要求的状态和方式') {
      return [extractChapter(sourceMarkdown, /范围与边界/u), extractChapter(sourceMarkdown, /角色与权限矩阵/u)].join('\n\n');
    }
    if (section === '3.2 软件能力需求') {
      return [
        extractChapter(sourceMarkdown, /功能总览/u),
        extractChapter(sourceMarkdown, /功能需求/u, { all: true, preserveIds: true }),
      ].join('\n\n');
    }
    if (section === '3.3 外部接口需求') {
      return [
        extractChapter(sourceMarkdown, /用户体验与入口/u, { all: true, mergeTables: true }),
        extractChapter(sourceMarkdown, /自动化与集成/u),
      ].join('\n\n');
    }
    if (section === '3.5 数据需求') {
      return [extractChapter(sourceMarkdown, /业务对象概览/u), extractChapter(sourceMarkdown, /数据与业务规则/u)].join('\n\n');
    }
    if (section === '3.6 质量特性需求') return extractChapter(sourceMarkdown, /非功能需求/u);
    if (section === '4 合格性规定') return extractChapter(sourceMarkdown, /验收矩阵/u, { all: true, mergeTables: true });
    if (section === '5 需求可追踪性') return extractChapter(sourceMarkdown, /交付验收检查表/u, { all: true, deduplicateChecklist: true });
    if (section === '6 注释') return extractChapter(sourceMarkdown, /待确认事项/u);
  }
  if (/合格性规定|评价准则|需求的可追踪性|需求可追踪性/.test(section)) {
    return extractSections(sourceMarkdown, [/验收矩阵/u, /测试场景覆盖/u, /需求.*追踪/u, /交付验收/u])
      || '本节依据系统验收规格、测试范围和需求追踪关系维护。当前版本尚需补充追踪矩阵。';
  }
  if (/需求|能力|接口|数据|质量/.test(section)) {
    return extractSections(sourceMarkdown, [/功能需求/u, /功能总览/u, /数据与业务规则/u, /非功能需求/u, /接口/u])
      || '本节依据系统功能、接口、数据、规则和部署资料编制。';
  }
  if (/设计|体系结构|详细|数据库|访问单元/.test(section)) {
    return extractSections(sourceMarkdown, [/概要设计/u, /详细设计/u, /逻辑架构/u, /数据库/u, /Read Model/u, /接口与集成/u])
      || '本节依据系统架构、模块划分、数据结构和集成关系编制。';
  }
  if (/测试|环境|进度|活动|准备|说明/.test(section)) {
    return extractSections(sourceMarkdown, [/测试目标/u, /测试范围/u, /测试类型/u, /测试场景/u, /准出标准/u])
      || '本节依据系统功能、异常规则、集成关系和非功能范围编制，测试执行数据需在人工章节中补充。';
  }
  if (/安装|现场|联络|保障|培训|任务|人员|保密|过程/.test(section)) {
    return extractSections(sourceMarkdown, [/环境要求/u, /安装步骤/u, /配置说明/u, /部署架构/u, /启动与停止/u, /安装验证/u])
      || '本节依据系统部署、运行环境和操作说明编制，现场联络信息和保障材料需在人工章节中补充。';
  }
  if (/软件应用|软件入门|使用指南|故障处理/.test(section)) {
    return extractSections(sourceMarkdown, [/概述/u, /快速开始/u, /功能操作/u, /规则与异常/u, /常见问题/u])
      || '本节依据用户角色、功能入口、操作表单和查询视图编制。';
  }
  if (/注释/.test(section)) {
    return '无。';
  }
  return '本节暂无可自动整理内容。如需补充，请在对应人工章节目录中添加同名 Markdown 文件。';
}

function readManualSection(documentId, section, suffix = '') {
  const filename = section
    .replace(/^\d+(?:\.\d+)?\s*/u, '')
    .replace(/[\\/:*?"<>|]/g, '-')
    .replace(/\s+/g, '')
    .trim();
  const candidates = [
    join(options.manualDirectory, documentId, `${section}${suffix}.md`),
    join(options.manualDirectory, documentId, `${filename}${suffix}.md`),
  ];
  for (const candidate of candidates) {
    if (existsSync(candidate)) {
      return readFileSync(candidate, 'utf8').trim();
    }
  }
  return '';
}

function renderReferences(references = []) {
  if (!references.length) return '无。';
  return [
    '| 序号 | 文件名称 | 版本/编号 | 说明 |',
    '| --- | --- | --- | --- |',
    ...references.map((item, index) =>
      `| ${index + 1} | ${item.name} | ${item.version ?? ''} | ${item.description ?? ''} |`
    ),
  ].join('\n');
}

function renderInstallationOverview(config, facts) {
  if (!facts) return '';
  const lines = [
    joinLocalizedSentences(
      t('document.text.installationOverview'),
      t('document.text.deploymentEnvironment', { environment: facts.environment })
    ),
    '',
    `### ${t('document.sections.deploymentUnits')}`,
    '',
    renderApplicationsTable(facts.applications),
    '',
    `### ${t('document.sections.infrastructure')}`,
    '',
    renderInfrastructureTable(facts.infrastructure),
  ];
  if (facts.gateway) {
    lines.push('', `### ${t('document.sections.gateway')}`, '', renderGatewayTable(facts.gateway));
  }
  return lines.join('\n');
}

function renderInstallationProcedure(facts) {
  if (!facts) return '';
  return [
    t('document.text.installationProcedure'),
    '',
    `### ${t('document.sections.deploymentModes')}`,
    '',
    facts.deploymentModes?.length ? facts.deploymentModes.map((mode) => `- ${mode}`).join('\n') : '- Docker Compose',
    '',
    `### ${t('document.sections.referenceCommands')}`,
    '',
    '```bash',
    ...(facts.operationsCommands ?? []),
    '```',
    '',
    `### ${t('document.sections.configuration')}`,
    '',
    renderEnvironmentTable(facts.applications),
    '',
    `### ${t('document.sections.storageAndSecurity')}`,
    '',
    renderList([...(facts.storageNotes ?? []), ...(facts.securityNotes ?? [])]) || '现场部署时应按环境要求配置持久化卷、访问令牌和跨域来源。',
  ].join('\n');
}

function renderTestEnvironment(facts) {
  if (!facts) return '';
  return [
    t('document.text.testEnvironment'),
    '',
    renderApplicationsTable(facts.applications),
    '',
    renderInfrastructureTable(facts.infrastructure),
    '',
    '各服务健康检查通过后，方可执行接口、流程、训练任务和异常恢复测试。',
  ].join('\n');
}

function renderTestPreparation(facts) {
  if (!facts) return '';
  return [
    t('document.text.testPreparation'),
    '',
    '```bash',
    ...(facts.operationsCommands ?? []).slice(0, 4),
    '```',
    '',
    renderEnvironmentTable(facts.applications),
  ].join('\n');
}

function renderArchitectureFacts(facts) {
  if (!facts) return '';
  return [
    t('document.text.architectureFacts'),
    '',
    renderApplicationsTable(facts.applications),
    ...(facts.technologyStacks?.length ? ['', `### ${t('document.sections.technologyStack')}`, '', renderTechnologyStacks(facts.technologyStacks)] : []),
    facts.gateway ? `\n外部访问通过 ${facts.gateway.provider} 网关进入，各路由映射如下：\n\n${renderGatewayTable(facts.gateway)}` : '',
  ].filter(Boolean).join('\n');
}

function renderTechnologyStacks(stacks) {
  return [
    `| ${t('document.fields.name')} | ${t('document.fields.technology')} | ${t('document.fields.version')} |`,
    '| --- | --- | --- |',
    ...stacks.flatMap((stack) => stack.technologies.map((technology) =>
      `| ${stack.application} | ${options.language === 'zh-CN' && technology.name === 'Package manager' ? '包管理器' : technology.name} | ${technology.version || '-'} |`)),
  ].join('\n');
}

function renderPersistenceFacts(facts) {
  if (!facts) return '';
  return [
    t('document.text.persistenceFacts'),
    '',
    renderInfrastructureTable(facts.infrastructure),
    '',
    renderEnvironmentTable(facts.applications.filter((app) => app.kind === 'backend')),
  ].join('\n');
}

function renderUserOperationFacts(facts) {
  if (!facts) return '';
  return [
    t('document.text.userOperationFacts'),
    '',
    renderApplicationsTable(facts.applications.filter((app) => app.kind === 'frontend')),
    '',
    facts.gateway ? renderGatewayTable(facts.gateway) : '',
  ].filter(Boolean).join('\n');
}

function renderApplicationsTable(applications = []) {
  if (!applications.length) return `${t('document.values.none')}。`;
  return [
    `| ${t('document.fields.name')} | ${t('document.fields.type')} | ${t('document.fields.image')} | ${t('document.fields.port')} | ${t('document.fields.path')} | ${t('document.fields.dependencies')} | ${t('document.fields.healthCheck')} |`,
    '| --- | --- | --- | --- | --- | --- | --- |',
    ...applications.map((app) => `| ${app.name} | ${options.language === 'zh-CN' ? (app.kind === 'frontend' ? '前端' : app.kind === 'backend' ? '后端' : app.kind) : app.kind} | ${app.image || '-'} | ${app.port ?? '-'} | ${app.gatewayPath || '-'} | ${(app.dependencies ?? []).join(', ') || '-'} | ${formatHealthCheck(app.healthCheck)} |`),
  ].join('\n');
}

function renderInfrastructureTable(infrastructure = []) {
  if (!infrastructure.length) return `${t('document.values.none')}。`;
  return [
    `| ${t('document.fields.name')} | ${t('document.fields.type')} | ${t('document.fields.image')} | ${t('document.fields.port')} | ${t('document.fields.volume')} | ${t('document.fields.healthCheck')} |`,
    '| --- | --- | --- | --- | --- | --- |',
    ...infrastructure.map((item) => `| ${item.name} | ${item.type} | ${item.image || '-'} | ${item.port ?? '-'} | ${(item.volumes ?? []).join(', ') || '-'} | ${formatHealthCheck(item.healthCheck)} |`),
  ].join('\n');
}

function renderGatewayTable(gateway) {
  if (!gateway?.routes?.length) return `${t('document.values.none')}。`;
  return [
    t('document.text.gatewaySummary', { provider: gateway.provider, image: gateway.image, port: gateway.port }),
    '',
    `| ${t('document.fields.path')} | ${t('document.fields.upstream')} |`,
    '| --- | --- |',
    ...gateway.routes.map((route) => `| ${route.path || '-'} | ${route.upstream || '-'} |`),
  ].join('\n');
}

function renderEnvironmentTable(applications = []) {
  const rows = [];
  for (const app of applications) {
    for (const env of app.environmentVariables ?? []) {
      if (isInternalConfiguration(env.name)) continue;
      rows.push(`| ${app.name} | ${env.name} | ${env.required ? t('document.values.yes') : t('document.values.no')} | ${env.value || '-'} |`);
    }
  }
  if (!rows.length) return `${t('document.values.none')}。`;
  return [
    `| ${t('document.fields.service')} | ${t('document.fields.configurationKey')} | ${t('document.fields.required')} | ${t('document.fields.defaultOrExample')} |`,
    '| --- | --- | --- | --- |',
    ...rows,
  ].join('\n');
}

function renderList(items = []) {
  return items.filter(Boolean).map((item) => `- ${item}`).join('\n');
}

function joinLocalizedSentences(...sentences) {
  const separator = options.language.startsWith('zh') ? '' : ' ';
  return sentences.filter(Boolean).join(separator);
}

function formatHealthCheck(healthCheck) {
  if (!healthCheck) return '-';
  if (healthCheck.path) return `${healthCheck.path}${healthCheck.port ? `:${healthCheck.port}` : ''}`;
  if (healthCheck.command) return sanitizeDeliveryValue(healthCheck.command).replace(/\|/g, '\\|');
  return '-';
}

function isInternalConfiguration(name) {
  return /^MEDOL_/u.test(name);
}

function sanitizeDeliveryValue(value) {
  return String(value)
    .replace(/\$\{POSTGRES_USER:-medol\}/gu, '${POSTGRES_USER:-<db_user>}')
    .replace(/\$\{POSTGRES_DB:-medol\}/gu, '${POSTGRES_DB:-<db_name>}')
    .replace(/:-medol/gu, ':-<value>');
}

function extractSections(markdown, headingPatterns) {
  const sections = splitMarkdownSections(markdown);
  const matched = sections
    .filter((section) => headingPatterns.some((pattern) => pattern.test(section.heading)))
    .map((section) => section.content.trim())
    .filter(Boolean);
  return scrubDeliveryText(demoteHeadings(cleanGeneratedMarkdown(matched.join('\n\n')), 2));
}

function chapterEntries(markdown, headingPattern) {
  const lines = markdown.split(/\r?\n/u);
  const headings = lines.flatMap((line, index) => /^##\s+/u.test(line) ? [index] : []);
  return headings.flatMap((start, index) => headingPattern.test(lines[start])
    ? [{ heading: lines[start], body: lines.slice(start + 1, headings[index + 1]).join('\n') }]
    : []);
}

function chapterBodies(markdown, headingPattern) {
  return chapterEntries(markdown, headingPattern).map(({ body }) => body);
}

function extractChapter(markdown, headingPattern, {
  all = false,
  deduplicateTables = false,
  deduplicateChecklist = false,
  mergeTables = false,
  preserveIds = false,
} = {}) {
  const bodies = chapterBodies(markdown, headingPattern);
  if (bodies.length === 0) return '本节暂无可自动整理内容，可在对应人工章节中补充。';
  const cleaned = (all ? bodies : bodies.slice(0, 1))
    .map((body) => scrubDeliveryText(demoteHeadings(cleanGeneratedMarkdown(body, { preserveIds }), 1)))
    .filter(Boolean);
  if (mergeTables) return mergeRepeatedTables(cleaned);
  const seenChecklistItems = new Set();
  const content = cleaned.map((body) => {
    const text = deduplicateTables ? deduplicateTableRows(body) : body;
    if (!deduplicateChecklist) return text;
    return text.split('\n').filter((line) => {
      if (!line.startsWith('- ')) return true;
      if (seenChecklistItems.has(line)) return false;
      seenChecklistItems.add(line);
      return true;
    }).join('\n').trim();
  }).filter(Boolean).join('\n\n');
  return content || '本节暂无可自动整理内容，可在对应人工章节中补充。';
}

function mergeRepeatedTables(chapters) {
  const rows = [];
  const otherContent = [];
  const seen = new Set();
  for (const chapter of chapters) {
    for (const line of chapter.split('\n')) {
      if (!line.startsWith('|')) {
        if (line.trim()) otherContent.push(line);
        continue;
      }
      const row = line.trim();
      if (seen.has(row)) continue;
      seen.add(row);
      rows.push(row);
    }
  }
  return [rows.join('\n'), otherContent.join('\n')].filter(Boolean).join('\n\n');
}

function assertRequirementCoverage(sourceMarkdown, outputMarkdown) {
  const source = chapterBodies(sourceMarkdown, /功能需求/u).join('\n');
  const ids = new Set([...source.matchAll(/^#{3,6}\s+.+?\/([A-Z][A-Z0-9-]+)\s*$/gmu)].map((match) => match[1]));
  const missing = [...ids].filter((id) => !outputMarkdown.includes(`编号：${id}`));
  if (missing.length) fail(`Software requirements omitted ${missing.length} source requirements: ${missing.slice(0, 10).join(', ')}`);
}

function assertChapterIdCoverage(sourceMarkdown, outputMarkdown, chapterPattern, label, includeHeading = true, idPattern = /.*/u) {
  const ids = new Set(chapterEntries(sourceMarkdown, chapterPattern)
    .flatMap(({ heading, body }) => [...(includeHeading ? `${heading}\n${body}` : body).matchAll(/^#{2,6}\s+.+?\/([A-Z][A-Z0-9-]+)\s*$/gmu)])
    .map((match) => match[1])
    .filter((id) => idPattern.test(id)));
  const missing = [...ids].filter((id) => !outputMarkdown.includes(`编号：${id}`));
  if (missing.length) fail(`${label} omitted ${missing.length} source sections: ${missing.slice(0, 10).join(', ')}`);
}

function deduplicateTableRows(markdown) {
  const seenRows = new Set();
  return markdown.split('\n').filter((line) => {
    if (!line.startsWith('|')) {
      seenRows.clear();
      return true;
    }
    if (/^\|\s*:?-{3,}/u.test(line)) return true;
    const row = line.trim();
    if (seenRows.has(row)) return false;
    seenRows.add(row);
    return true;
  }).join('\n');
}

function applyContextSummaries(markdown) {
  const overridesPath = join(options.manualDirectory, 'software-design', 'context-summaries.json');
  if (!existsSync(overridesPath)) return markdown;
  const summaries = JSON.parse(readFileSync(overridesPath, 'utf8'));
  return markdown.split('\n').map((line) => {
    if (!line.startsWith('|')) return line;
    const cells = line.split('|');
    if (cells.length !== 6) return line;
    const summary = summaries[cells[1].trim()];
    if (!summary) return line;
    cells[4] = ` ${summary} `;
    return cells.join('|');
  }).join('\n');
}

function splitMarkdownSections(markdown) {
  const lines = markdown.split(/\r?\n/);
  const sections = [];
  let current = { heading: '', content: [] };
  for (const line of lines) {
    const heading = line.match(/^##+\s+(.+?)\s*$/u);
    if (heading) {
      if (current.content.length) sections.push({ heading: current.heading, content: current.content.join('\n') });
      current = { heading: heading[1], content: [line] };
      continue;
    }
    current.content.push(line);
  }
  if (current.content.length) sections.push({ heading: current.heading, content: current.content.join('\n') });
  return sections;
}

function cleanGeneratedMarkdown(markdown, { preserveIds = false } = {}) {
  return markdown
    .replace(/^(#{1,6}\s+.+?)\/([A-Z][A-Z0-9]*(?:-[A-Z0-9]+)*)\s*$/gmu,
      (_, heading, id) => preserveIds ? `${heading}（编号：${id}）` : heading)
    .replace(/^#{1,6}\s+目录[\s\S]*?(?=^#{1,6}\s+)/mu, '')
    .trim();
}

function scrubDeliveryText(markdown) {
  return translateModelTerms(markdown
    .replace(/<!--[\s\S]*?-->/g, '')
    .replace(/([\p{Script=Han}])（[A-Za-z][A-Za-z0-9 _/.-]*）/gu, '$1')
    .split(/\r?\n/)
    .filter((line) => !/\bmedol\b|MEDOL_/iu.test(line))
    .map(formatFieldSource)
    .map((line) => line
      .replace(/已建模页面入口/gu, '已定义页面入口')
      .replace(/已建模控制台/gu, '已定义控制台')
      .replace(/已建模集成点/gu, '已定义集成点')
      .replace(/已建模产品指标/gu, '已定义产品指标')
      .replace(/已建模 Read Model/gu, '已定义查询视图')
      .replace(/未建模业务流程/gu, '未覆盖业务流程')
      .replace(/超出已建模集成契约/gu, '超出已定义集成契约')
      .replace(/该能力尚未明确建模输入字段。/gu, '该能力尚未明确输入字段。')
      .replace(/通过已建模 Read Model 查看/gu, '通过系统查询视图查看')
      .replace(/以模型中的 Slice、Specification、事件、Projection、Automation 和集成为可追溯来源/gu, '以业务能力、业务规则、事件、查询视图、自动处理和外部集成为可追溯来源')
      .replace(/模型中已明确的 Slice、Command、Event、Specification、状态流转、Read Model 更新、Automation 触发和外部集成/gu, '系统已明确的业务能力、提交操作、事件、业务规则、状态流转、查询视图更新、自动处理触发和外部集成')
      .replace(/基于模型推导/gu, '基于业务规则推导')
      .replace(/Read Model/gu, '查询视图')
      .replace(/Specification/gu, '业务规则')
      .replace(/Projection/gu, '查询视图')
      .replace(/Automation/gu, '自动处理')
      .replace(/Slice/gu, '业务能力')
      .replace(/Command/gu, '提交操作')
      .replace(/Event/gu, '事件')
      .replace(/建模/g, '定义'))
    .map(formatAutomationRule)
    .join('\n')
    .replace(/\n{3,}/g, '\n\n')
    .trim());
}

function formatFieldSource(line) {
  if (!line.startsWith('|')) return line;
  const cells = line.split('|');
  if (cells.length !== 8 && cells.length !== 9) return line;
  const sourceCell = cells.length - 2;
  const source = cells[sourceCell].trim();
  const match = source.match(/^(derived from|from from)\s+(.+?)(?:\s+rule:\s*(.+))?$/u);
  if (!match) return line;
  const references = match[2].split(/\s*,\s*/u).map((reference) => {
    const parts = reference.match(/^([A-Za-z][\w]*)\.([A-Za-z][\w]*)$/u);
    if (!parts) return `${translateSourceIdentifier(reference)}字段`;
    return `${translateSourceIdentifier(parts[1])}中的${translateSourceIdentifier(parts[2])}`;
  });
  const origin = match[1] === 'derived from'
    ? `依据${references.join('，或')}确定`
    : `取自${references.join('，或')}`;
  cells[sourceCell] = ` ${origin}${match[3] ? `；${match[3].trim()}` : ''} `;
  return cells.join('|');
}

function translateSourceIdentifier(identifier) {
  const words = identifier
    .replace(/([a-z0-9])([A-Z])/gu, '$1 $2')
    .replace(/([A-Z])([A-Z][a-z])/gu, '$1 $2')
    .replace(/_/gu, ' ');
  const label = words[0].toUpperCase() + words.slice(1);
  return translateModelTerms(label);
}

function formatAutomationRule(line) {
  const cells = line.match(/^\|\s*(.*?)\s*\|\s*automation\s*\|\s*(.*?)\s*\|\s*$/u);
  if (!cells) return line.replace(/\| integration \|/u, '| 外部集成 |');

  const parts = Object.fromEntries(cells[2].split(';').map((part) => {
    const separator = part.indexOf(':');
    return separator < 0 ? [] : [part.slice(0, separator).trim(), part.slice(separator + 1).trim()];
  }).filter((part) => part.length === 2));
  const event = parts.on;
  const condition = parts.condition
    ?.replace(/（[^）]*）/gu, '')
    .replace(/\s*==\s*真$/u, '')
    .replace(/\s*==\s*假$/u, '为否')
    .replace(/\s*==\s*/u, '为')
    .replace(/\s*>=\s*/u, '不少于')
    .replace(/\s*<=\s*/u, '不多于')
    .replace(/\s*>\s*/u, '大于')
    .replace(/\s*<\s*/u, '小于');
  const subject = event
    ? parts.onKind === '待办' ? `根据${event}的待处理记录` : `在${event}后`
    : '';
  const circumstance = condition
    ? `${subject ? `${subject}，` : ''}当${condition}时`
    : subject;
  const fanOut = parts.fanOutSource?.split('.').at(-1);
  const action = fanOut
    ? `系统对${fanOut}逐一执行“${parts.emits}”操作`
    : `系统自动执行“${parts.emits}”操作`;
  const description = `${circumstance ? `${circumstance}，` : ''}${action}。`;
  return `| ${cells[1]} | 自动处理 | ${description} |`;
}

function demoteHeadings(markdown, levels) {
  const prefix = '#'.repeat(levels);
  return markdown.replace(/^(#{1,6})\s+/gmu, (_, hashes) => `${hashes}${prefix} `);
}

function convertMarkdownToDocx(markdownPath, outputPath, spec) {
  if (!existsSync(markdownToDocxScript)) {
    fail(`Markdown to DOCX converter is missing: ${markdownToDocxScript}`);
  }
  const python = process.env.PYTHON ?? (existsSync(bundledPython) ? bundledPython : 'python3');
  const templatePath = join(options.templateDirectory, spec.template);
  const args = [markdownToDocxScript, markdownPath, '--out', outputPath, '--title', `${projectConfig.projectName} ${spec.title}`];
  if (existsSync(templatePath) && templatePath.endsWith('.docx')) {
    args.push('--reference', templatePath);
  }
  const result = spawnSync(python, args, {
    cwd: repositoryRoot,
    encoding: 'utf8',
    stdio: 'pipe',
    maxBuffer: 64 * 1024 * 1024,
  });
  if (result.status !== 0) {
    fail(`DOCX conversion failed for ${spec.id}.\n${result.stderr || result.stdout}`);
  }
}

function parseArgs(argv) {
  const parsed = {};
  for (let index = 0; index < argv.length; index += 1) {
    const arg = argv[index];
    if (!arg.startsWith('--')) continue;
    const body = arg.slice(2);
    const [key, ...rest] = body.split('=');
    parsed[key] = rest.length ? rest.join('=') : argv[index + 1] && !argv[index + 1].startsWith('--') ? argv[++index] : 'true';
  }
  if (parsed.help) {
    console.log(`Usage: node scripts/${basename(fileURLToPath(import.meta.url))} [options]

Options:
  --source-dir=docs/438c/sources
  --config=docs/438c/config/project.json
  --facts-config=docs/document-support/config/engineering-facts-sources.json
  --facts-out=docs/generated/facts/engineering-facts.json
  --manual=docs/438c/manual
  --model=.medol/codegen-model.json
  --model-source=.medol/source.medol
  --release-manifest=path/to/built-delivery-manifest.json
  --out=docs/generated/438c
  --template-dir=../tmp/438C文档模版
  --format=markdown|docx|all
  --only=all|software-requirements|software-design|database-design|test-plan|test-description|installation-plan|user-manual
`);
    process.exit(0);
  }
  return parsed;
}

function fail(message) {
  console.error(`[438c] ${message}`);
  process.exit(1);
}
