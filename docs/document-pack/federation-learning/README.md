# 联邦学习子系统项目文档生成

这个目录维护联邦学习子系统自己的项目文档生成逻辑。它复用 `medol` 的标准文档生成能力作为事实来源，再按根目录 `项目文档/*.docx` 的交付文档风格组织输出。

生成物默认写入 `docs/generated/`，该目录不提交。

## 生成命令

```bash
node scripts/generate-project-docs.mjs
```

常用参数：

- `--mode=standalone`：只生成联邦学习子系统独立文档。
- `--mode=merged`：只生成按项目 Word 文档结构组织的融合稿。
- `--mode=all`：同时生成独立文档和融合稿，默认值。
- `--out=docs/generated`：指定输出目录。
- `--model=.medol/source.medol`：指定 MEDOL 模型。
- `--translations=../medol/examples/fl/model-translations.zh-CN.json`：指定中文术语表。

## 输出结构

- `standalone/`：联邦学习子系统独立文档，适合子系统评审。
- `merged/`：保留项目级文档标题体系，并在对应章节补充“联邦学习子系统”内容，适合后续合并到总文档。
- `word/standalone/`：联邦学习子系统独立文档 Word 版。
- `word/merged/`：项目文档融合稿 Word 版。
- `README.md`：本次生成的输入、输出和章节说明。

## 原文档结构对应

- 需求规格说明：范围、引用文档、3.1-3.17 需求章节。
- 软件设计说明：范围、引用文档、CSCI级设计决策、CSCI体系结构设计、CSCI详细设计、需求可追踪性。
- 数据库设计说明：范围、引用文档、数据库级设计决策、数据库详细设计、数据库访问单元、需求可追踪性、注释、附录。
- 用户操作手册：范围、引用文档、软件应用、软件入门、使用指南。
- 安装部署手册：范围、系统要求、常用软件安装、系统部署安装。
- 测试记录：任务描述、目标、环境类型、云类型、功能测试用例设计、功能测试用例、测试结果。
- 测试大纲：范围、引用文档、测试依据、软件测试环境、测试标识、测试进度、测试终止条件、测试说明、需求可追踪性、注释、附录。

测试记录属于实际执行证据类文档，本生成器只输出待填写结构，不自动编造测试结论。

## 可选模型增强

默认生成是确定性的，不调用模型。需要统一项目文档语气或做轻量润色时，可以显式启用模型增强：

```bash
AGENT_PROVIDER=openai OPENAI_API_KEY=... OPENAI_MODEL=gpt-5.2 node scripts/generate-project-docs.mjs --enhance-with-model
```

也支持 MiniMax 兼容 `/responses` 接口：

```bash
AGENT_PROVIDER=minimax MINIMAX_API_KEY=... MINIMAX_MODEL=MiniMax-M3 node scripts/generate-project-docs.mjs --enhance-with-model
```

模型增强只能润色表达、调整衔接和统一交付文档语气，不能新增 MEDOL 模型中不存在的业务事实。
