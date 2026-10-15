# 438C 项目文档适配

本目录维护联邦学习项目的 438C 交付文档适配层。这里的生成脚本只读取本项目保存的中文来源文档和人工章节，不运行其他项目的生成脚本。

中文来源文档和术语表快照存放在 `docs/438c/sources/`。首次准备或更新快照时，从本地已保存的文档数据库导出：

```bash
node scripts/export-438c-source-docs.mjs
```

数据库默认路径为 `../medol/data/medol-documents.sqlite`。多工作区时指定 `--workspace-id=<id>`；也可通过 `--db=<path>` 和 `--out=<path>` 指定来源与输出。交付生成只读取这些快照；更新源文档后需重新导出。

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
```

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

`docs/generated/438c/` 是生成结果目录，已被 `.gitignore` 忽略，可以安全删除后重新生成。

```text
docs/generated/438c/
  facts/
  markdown/
  word/
  README.md
```

`facts/`、`markdown/` 和 `word/` 都是生成物，不作为人工修改源。需要长期保存的人工调整应回填到 `docs/438c/manual/`。
