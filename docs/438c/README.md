# 438C 项目文档适配

本目录维护联邦学习项目的 438C 交付文档适配层。这里的生成脚本只读取本项目保存的中文来源文档和人工章节，不运行其他项目的生成脚本。

中文来源文档和术语表快照存放在 `docs/438c/sources/`。PRD 从当前模型及翻译快照重新生成，其他文档和术语表从本地已保存的文档数据库导出。按以下顺序更新：

```bash
cd ../medol
npm run prd:generate -- examples/fl/federation-learning.medol --use-cases --out=../federation-learning/.medol/prd-use-cases.json
node --import tsx src/lib/generators/documentation/documentationCli.ts ../federation-learning/.medol/source.medol --kind=prd --language=zh-CN --translations=../federation-learning/.medol/codegen-model.json --out=../federation-learning/docs/438c/sources/prd.zh-CN.md
cd ../federation-learning
node scripts/export-438c-source-docs.mjs
```

先确保 `.medol/source.medol` 与 `medol/examples/fl/federation-learning.medol` 是同一版模型，且 `.medol/codegen-model.json` 包含已审核的翻译。数据库默认路径为 `../medol/data/medol-documents.sqlite`。多工作区时指定 `--workspace-id=<id>`；也可通过 `--db=<path>` 和 `--out=<path>` 指定来源与输出。默认数据库导出**不覆盖 PRD**；仅在明确需要旧数据库版本时可显式指定 `--only=prd`。更新源文档后需重新生成或导出相应快照。

`3.3.1` 功能需求章节另有受控合并流程：运行 `node scripts/prepare-438c-331.mjs --check` 核对模型、用例快照和稳定标识，确认后用 `--sync` 更新映射与候选稿。候选稿在 `docs/generated/438c/merge/`，人工稿在 `docs/438c/merge/`；脚本不会用候选稿覆盖人工稿。逐项审核、写入人工稿并记录变更后再执行 `--word`。详见 [合并说明](merge/README.md)。

Medol 页面生成各类文档时可调用当前配置的 AI 模型，在文末附上带来源的抽样审校建议。它们不是已批准内容；数据库来源导出会剔除 `document.aiReview` 章节，需人工确认并写回正文后才能进入 438C 交付文档。CLI 也可显式加 `--enhance-with-ai` 将审校稿输出到独立文件，不要直接覆盖 `sources/` 下的正式来源快照。

## 生成命令

```bash
node scripts/generate-438c-docs.mjs
```

默认输入：

- 中文来源文档：`docs/438c/sources/<kind>.zh-CN.md`
- 中文模型术语：`docs/438c/sources/model-translations.zh-CN.md`；只替换正文与表格中的术语，代码块和行内代码保留原文
- 项目配置：`docs/438c/config/project.json`
- 工程事实配置：`docs/document-support/config/engineering-facts-sources.json`
- 人工章节：`docs/438c/manual/`
- 438C 模板目录：`../tmp/438C文档模版`
- 输出目录：`docs/generated/438c/`

常用参数：

```bash
node scripts/generate-438c-docs.mjs --format=markdown
node scripts/generate-438c-docs.mjs --format=docx
node scripts/generate-438c-docs.mjs --only=software-requirements
node scripts/generate-438c-docs.mjs --source-dir=docs/438c/sources
node scripts/generate-438c-docs.mjs --facts-config=docs/document-support/config/engineering-facts-sources.json
node scripts/generate-438c-docs.mjs --template-dir=/path/to/438C文档模版
node scripts/generate-438c-docs.mjs --format markdown --only test-plan --release-manifest /path/to/delivery-manifest.json
```

## 文档版本与发布追溯

`project.json` 的 `documentVersion` 是独立的文档版本，不随模型或软件版本覆盖。生成器另外为每份文档维护整数 `revision`：人工章节、配置或实际输出正文变化都会递增，即使模型版本不变。相同正文重复生成不会递增；生成时间和修订计数本身不进入 `contentSha256`。最终文件的原始字节校验值在 `artifacts[].sha256`，与正文校验值分开保存。

源导出同时写入 `sources/source-doc-manifest.json`，每个 `<kind>.<language>.md` 条目记录导出后正文 SHA256、数据库文档 ID、原始 `sourceHash`、最新匹配正文的修订号/来源及其工作区版本 ID、版本号和 `workspaceReleaseLabel`。已有未导出的条目（包括默认保留的 PRD）保留；文件变化后旧校验值不再有效。源导出不会运行数据库迁移；旧库缺少修订表或字段时，缺项保持为空，不能据此推断发布。

文档数据库默认是 `../medol/data/medol-documents.sqlite`，工作区版本数据库默认是 `../medol/data/medol.sqlite`；分别通过 `--db` 和 `--workspace-db` 指定。测试或合库环境也支持工作区版本表位于文档数据库中。历史版本 DSL 按 SHA256 记录，并读取该版本的发布信息。旧 `fnv1a-*` 指纹仅用于与具体 DSL 字符串核对，绝不冒充 SHA256；无法查到历史版本时，可核对 `--model-source`（默认 `.medol/source.medol`），但不据此补造版本或发布标签。数据库修订快照中的发布标签独立保留。

438C 生成器只读 `.medol/codegen-model.json` 中的 `metadata: {schemaVersion: 1, medolVersion, source: {sha256, workspaceId?, versionId?, versionNo?, release?: {channel, label?, releasedAt?}}}`，并核对 `.medol/source.medol` 原始字节的 SHA256。`--model`、`--model-source` 可指定其他输入，不会修改输入文件。

只有基础 Markdown 与术语快照的导出校验值有效、模型源 SHA256 相同且已知工作区/版本标识不冲突，才记录 `applicableModel`。已发布模型采用 `release.label` 作为软件版本；无标签但有模型版本号时使用 `model-r<versionNo>`。`medolVersion` 是工具版本，不是软件版本。无发布元数据、缺少关联、源文件被编辑或关联过期时继续生成 `draft`，使用配置中的 `softwareVersion`，不会盖上当前模型的适用标记。当前基线仅记录为 `candidateModel`。现有独立 PRD 生成路径尚未写出源关联，因此未提供可验证 sidecar 的 PRD 保持草稿。

发布模型基线并不等于某次精确软件交付。`--release-manifest path` 显式读取 `release-runtime.mjs` 写出的已构建交付清单，验证失败则退出。v1 契约结构如下：

```json
{
  "schemaVersion": 1,
  "imageVersion": "v1.0.0.0",
  "packageVersion": "1.0.0",
  "buildIdentity": "<构建身份 SHA256>",
  "codeSha256": "<构建源码 SHA256>",
  "git": { "revision": "<commit>", "dirty": false },
  "generation": {
    "schemaVersion": 1,
    "model": {
      "sha256": "<codegen-model.json 原始字节 SHA256>",
      "metadata": { "schemaVersion": 1, "medolVersion": "<工具版本>", "source": { "sha256": "<DSL SHA256>", "release": { "channel": "stable", "label": "v1.0.0" } } }
    },
    "inputs": [{ "path": ".medol/source.medol", "sha256": "<DSL SHA256>" }]
  },
  "images": [
    { "name": "platform:v1.0.0.0", "id": "sha256:<镜像 SHA256>", "repoDigests": [] }
  ]
}
```

校验要求稳定发布标签、匹配该标签的四段 `imageVersion`、对应 `packageVersion`、非空有效 `buildIdentity`/`codeSha256`、带镜像 ID 的非空 `images`，以及 `generation.model` 的完整 JSON SHA256 和元数据都与已核验文档基线一致。源文档和术语快照另外通过 `source-doc-manifest.json` 核对同一模型 SHA256/版本；它们通常在构建后生成，不要求列入构建输入。`generation.inputs[].path` 相对生成输出根目录，文档工具不猜测该目录，也不将它们按仓库根目录解析或重新验证；构建运行时负责核验这些构建输入。

只有全部通过才输出 `delivery-verified` 和 `applicableDelivery`，其中 `deliveryId` 规范化为 `imageVersion`，并记录构建身份、源码 SHA256、镜像清单和交付清单 SHA256。嵌入应用的预构建清单、dry-run 清单或草稿标签不能用于声明交付。没有该参数时，最多输出 `released-model-baseline`。工程事实与人工内容仍是文档输入；这里的精确交付关联证明模型及基础快照一致，不代表软件行为或人工文字已经通过验收。

本追溯流程覆盖 `export-438c-source-docs.mjs` 和 `generate-438c-docs.mjs`。旧 `generate-project-docs`、`document-pack` 和受控合并工具尚未接入这些清单，其输出不能继承上述发布/交付标记。

## 当前覆盖文档

| 438C 文档 | 基础内容来源 |
| --- | --- |
| 软件需求规格说明 | `prd` |
| 软件设计说明书 | `software-design` |
| 数据库设计说明 | `database-design` |
| 软件测试计划 | `test-outline` |
| 软件测试说明 | `test-outline` |
| 软件安装计划 | `installation-manual` |
| 用户手册 | `user-manual` |

转换不会按字符数截断来源章节。需求说明保留详细功能需求及其来源编号，并合并重复快照中的页面、验收表格条目；数据库设计和用户手册同样纳入各自的详细章节。生成时会核对需求编号、数据库条目编号及用户操作编号；若来源章节有遗漏，命令直接失败。来源文件中的生成元数据和完全重复的表格行不进入交付文档，因此交付文件行数不必与来源文件完全一致。

## 工程事实维护

安装、测试、用户手册和设计类文档中的部署单元、镜像、端口、环境变量、健康检查、网关路由和部署命令来自：

```text
docs/document-support/config/engineering-facts-sources.json
```

生成时会先汇总为：

```text
docs/generated/facts/engineering-facts.json
```

该文件是生成物，不作为人工修改源。需要改变技术事实时，优先更新 `operations/`、各服务 `.env-example`、Docker Compose 或部署清单，再重新生成文档。
文档的镜像列只展示逻辑镜像名与版本，不记录本机或目标环境的镜像仓库地址；私有仓库前缀由部署时的环境配置或镜像脚本参数提供。
通用技术章节的标签和固定文案来自 `docs/document-support/i18n/`，可通过 `--language=<locale>` 选择输出语言；缺失翻译会回退到 `en-US` 和 `base`。
业务术语优先采用本地导出的 MEDOL 术语表；术语表缺项可在 `docs/438c/i18n/<locale>.json` 中维护项目级补译，不覆盖已有模型译名。

## 人工内容维护

生成脚本不会把最终交付版作为唯一源文件。需要人工维护的内容写入：

```text
docs/438c/manual/<document-id>/<section>.md
```

例如：

```text
docs/438c/manual/software-requirements/2 引用文档.md
docs/438c/manual/installation-plan/3.2 联络点.md
docs/438c/manual/test-plan/5 测试进度.md
```

生成时会优先读取人工章节；没有人工章节时，脚本会从中文来源文档整理相关内容，或输出可补充说明。人工文件替换对应章节的自动内容。

如需保留自动整理的章节并补充项目约束，可创建 `<章节名>.append.md`，生成时会接在该章节的自动内容或人工替换内容之后。例如 `manual/software-requirements/3.2 软件能力需求.append.md`。不要直接修改 `docs/generated/438c/` 下的交付文件。
软件设计概要中的上下文职责若在来源快照里仍为“尚未明确”，可在 `manual/software-design/context-summaries.json` 中按中文上下文名称提供项目级说明，生成时覆盖对应表格单元格。

## 输出约定

`docs/generated/438c/` 是生成结果目录，已被 `.gitignore` 忽略。需要延续修订历史时必须保留并归档 `document-manifest.json`；删除它会从修订 1 重新开始。`--only` 保留清单中其他文档的上次记录，每份记录有独立生成时间和适用基线。

```text
docs/generated/438c/
  facts/
  markdown/
  word/
  README.md
  document-manifest.json
```

`facts/`、`markdown/` 和 `word/` 都是生成物，不作为人工修改源。需要长期保存的人工调整应回填到 `docs/438c/manual/`。

清单按 `<document-id>.<language>` 保存文档版本、修订历史、正文 SHA256、模型与源关联、生成文件名及文件 SHA256。一次生成只登记本次实际产出的文件；仅 Markdown 生成不会将磁盘残留的 Word 文件列为本次产物。

无需 Word 环境的回归测试：

```bash
node --test scripts/generate-438c-docs.test.mjs scripts/export-438c-source-docs.test.mjs
```
