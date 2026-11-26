# 联邦孪生平台需规与软设维护

文档问题记录在 [TODO.md](TODO.md)。

需规中的相关图源为 [业务架构图](../../../438c/merge/figures/federated-twin-business-architecture.drawio)、[模块功能结构图](../../../438c/merge/figures/federation-learning-functional-structure.drawio) 和 [主要业务流程图](../../../438c/merge/figures/federation-learning-main-flow.drawio)。PNG 和 SVG 导出到 `docs/generated/diagrams/`。生成 Word 时使用版式包已嵌入的插图；更新图源后，还需同步替换 Word 中的插图并按下文重新提取正文包。

| 文档 | 提取基线 | 全文 JSON |
| --- | --- | --- |
| 软件需求规格说明 V0.8 | 联邦孪生平台-需求规范V0.8（3.1修订稿） | [srs/text.json](srs/text.json) |
| 软件设计说明 V0.6 | 国标版本中的 02联邦孪生平台-软件设计说明V0.6 | [sdd/text.json](sdd/text.json) |

两套目录各有一份 `text.json` 和配套的 `layout.docx`。JSON 保存正文、标题文字、表格单元格、页眉页脚等 Word 文字。版式文件保存表格结构、段落和字符样式、编号、域、链接和插图。插图中的文字随图保留。生成时将 JSON 中的文字填入版式，源 Word 无需留在机器上。

## 修改正文并生成 Word

需规图7和图8的可编辑图源分别为[临床数字孪生辅助诊疗流程图](../../../438c/merge/figures/clinical-digital-twin-care-flow.drawio)和[疾病风险动态评估预警流程图](../../../438c/merge/figures/dynamic-disease-risk-alert-flow.drawio)。两张图沿用联邦学习端到端业务流程图的阶段分区、节点与连线样式，导出文件位于 `docs/generated/diagrams/`。

编辑对应的 `text.json`。在 `parts` 下，`word/document.xml` 依文档顺序包含正文和表格文字，`word/header*.xml`、`word/footer*.xml` 对应页眉页脚。单个字符串可直接修改。遇到字符串数组，按原位置修改对应片段，它们共用一个段落，但各自保留字符样式、链接等 Word 格式。保留段落和片段的原有顺序及数量。

在 `federation-learning` 根目录执行，测试使用 Python 3.12。

```sh
python3 -m venv docs/generated/platform-word-venv
docs/generated/platform-word-venv/bin/python -m pip install \
  -r docs/document-support/gbt-8567/platform-word/requirements.txt

docs/generated/platform-word-venv/bin/python -B \
  docs/document-support/gbt-8567/platform-word/platform_word.py build \
  --bundle docs/document-support/gbt-8567/platform-word/srs \
  --output 'docs/generated/platform-word/联邦孪生平台-需求规范V0.8.docx'

docs/generated/platform-word-venv/bin/python -B \
  docs/document-support/gbt-8567/platform-word/platform_word.py build \
  --bundle docs/document-support/gbt-8567/platform-word/sdd \
  --output 'docs/generated/platform-word/联邦孪生平台-软件设计说明V0.6.docx'
```

每次生成指定新的输出路径。修改标题或正文导致分页变化时，在 Word 中更新目录及交叉引用并保存。

## 将 Word 中的手工修改回存到项目

需要增删段落、调整表格结构或替换图件时，从最新 JSON 生成一份 Word，再在 Word 中编辑并保存。如果已经在 Word 里修改过，先保存这份 Word，直接用它提取。将保存后的版本完整提取到新目录。

```sh
docs/generated/platform-word-venv/bin/python -B \
  docs/document-support/gbt-8567/platform-word/platform_word.py extract \
  --input 'docs/generated/platform-word/已经手工修改的需规.docx' \
  --bundle docs/generated/platform-word/srs-import
```

核对新旧 `text.json` 的差异，确认 Word 已包含要保留的变更后，将新提取的 `text.json` 和 `layout.docx` 一起更新到 `srs/`，软设同理。脚本会校验 JSON 与版式的配套关系。手工修改和重新生成均以新文件保存，便于比对。
