# Document Support

This directory contains reusable technical-document support assets generated for the project.

## GB/T 8567—2006 软件需求规格说明

在同级的 MEDOL 仓库运行以下命令，使用本项目的模型和需规资料生成第 7.11 节评审稿。

```sh
npm run docs:generate -- ../federation-learning/.medol/source.medol \
  --profile=gbt-8567-2006 --kind=srs \
  --materials=../federation-learning/docs/document-support/gbt-8567/srs.materials.json \
  --out=../federation-learning/docs/generated/gbt-8567/srs.md
```

为同一命令加 `--json` 并将输出文件改为 `srs.trace.json`，可保存文档、模型及用例的追溯和输入哈希。需求正文、国标条号和待确认事项维护在 `gbt-8567/srs.materials.json`。

## 联邦孪生平台需规与软设

平台需规 V0.8 与软件设计说明 V0.6 的全文 JSON、版式底稿及 Word 生成命令见 [平台文档维护](gbt-8567/platform-word/README.md)。

## 联邦学习模块 Word 修订复现

需规、软设和数据库设计的历史基线、修订顺序及运行命令见 [Word 修订复现](gbt-8567/word-replay/README.md)。

## Engineering Facts

Collect deployment and operations facts from the current project:

```bash
node docs/document-support/collect-engineering-facts.mjs
```

The default output is `docs/generated/facts/engineering-facts.json`.
Backend and frontend technology stacks are read from generated Maven `pom.xml` and frontend `package.json` files when this command runs.

## Internationalization

Reusable document labels live under `docs/document-support/i18n/`.
Add a project language file such as `ja-JP.json` when another output language is required.
Missing keys fall back to `en-US`, then `base`, then the key itself.
