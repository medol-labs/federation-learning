# 联邦孪生平台需规与软设维护

文档问题记录在 [TODO.md](TODO.md)。

需规中的相关图源为 [业务架构图](../../../438c/merge/figures/federated-twin-business-architecture.drawio)、[模块功能结构图](../../../438c/merge/figures/federation-learning-functional-structure.drawio) 和 [主要业务流程图](../../../438c/merge/figures/federation-learning-main-flow.drawio)。PNG 和 SVG 导出到 `docs/generated/diagrams/`。生成 Word 时使用版式包已嵌入的插图；更新图源后，还需同步替换 Word 中的插图并按下文重新提取正文包。

| 文档 | 提取基线 | 全文 JSON |
| --- | --- | --- |
| 软件需求规格说明 V0.8 | 联邦孪生平台-需求规范V0.8（3.1修订稿） | [srs/text.json](srs/text.json) |
| 软件设计说明 V0.6 | 国标版本中的 02联邦孪生平台-软件设计说明V0.6 | [sdd/text.json](sdd/text.json) |

两套目录各有一份 `text.json` 和配套的 `layout.docx`。JSON 保存正文、标题文字、表格单元格、页眉页脚等 Word 文字。版式文件保存表格结构、段落和字符样式、编号、域、链接和插图。插图中的文字随图保留。生成时将 JSON 中的文字填入版式，源 Word 无需留在机器上。

两份 `layout.docx` 在本地使用，并由 Git 忽略。现有版本可由构建脚本根据 `text.json` 的 `layout_git_blob` 从已有 Git 历史读取，完整克隆无需额外下载。浅克隆需要获取旧历史，或将配套版式放到对应目录。无论版式从哪里读取，构建都校验 `layout_sha256`。

新版式应通过团队共享文档存储分发，以 `layout_sha256` 标识版本。仅提交新的 `text.json` 与可编辑图源。在其他机器构建新版时，将配套版式下载为对应目录下的 `layout.docx`。

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

3.1.1 的联邦学习输入/输出决策表维护在 `sdd-io-decisions.json`。对现有软设 Word 可运行 `platform_word.py revise-io-decisions --input 已有软设.docx --output 修订软设.docx`，只替换该节正文。

第 3 章其余联邦学习决策及共用表述的范围修正维护在 `sdd-chapter3-decisions.json`。对现有 Word 用 `platform_word.py revise-chapter3-decisions --input 已有软设.docx --output 修订软设.docx` 更新 3.2 至 3.4；3.1.1 与第 4 章以后的内容不受影响。

仅补充联邦学习行为和数据呈现决策时，使用 `platform_word.py revise-federation-decisions --input 已有软设.docx --output 修订软设.docx`。两节正文维护在 `sdd-federation-decisions.json`，重建软设时自动插入。

从已有软设 Word 中删除旧 3.3.1 联邦学习安全与保密设计决策及目录项时，使用 `platform_word.py remove-security-decisions --input 已有软设.docx --output 修订软设.docx`。3.3 的其他内容和 4.5 接口契约保留。

仅更新联邦学习 3.4.1 扩展性、可维护性与故障恢复时，使用 `platform_word.py revise-maintainability-decisions --input 已有软设.docx --output 修订软设.docx`。

联邦学习物理存储清单随软设生成，也可运行 `platform_word.py revise-physical-storage --input 已有软设.docx --output 修订软设.docx` 单独补充。清单按部署单元区分 PostgreSQL、UmaDB 和文件存储，完整表结构引用数据库设计说明 4.3.1；新增题注使用前一张表的子编号，不重排既有图表。

软设中的联邦学习接口在生成 Word 时单独填充到 4.5.3.4。首次构建或更新接口图后，先导出两张图：

```sh
python3 docs/diagrams/drawio.py export \
  docs/438c/merge/figures/federation-learning-interface-relations.drawio \
  docs/438c/merge/figures/federation-learning-sync-interface-relations.drawio
```

联邦学习接口采用原有接口发现、报文定义和资源契约分组逻辑，根据表 104 区分训练与聚合提供方。图中 G 编号对应清单的调用关系，清单 E 编号对应正文的一项资源契约。调整报文时维护接口声明或接口契约补充表；调整接口图与分组时同步核对 `sdd_interfaces.py`。如只需要还原原始版式包，构建时加 `--base-only`；接口章节不要从生成结果反向回存到版式包。

软设 4.5 按联邦孪生平台整体边界分类，表 137 只列内部、外部两类。4.5.2 为外部接口，4.5.3 为内部接口。联邦学习接口正文集中放在内部接口下；对外复用既有接口时引用 4.5.3.4 的实际契约编号。

目录仍全局收录到三级，4.5 的必要四、五级标题通过目录项域单独收录，逐项接口契约不列入目录。对已有 Word 可用 `platform_word.py revise-interface-outline --input 已有软设.docx --output 修订软设.docx`，只同步接口标题层级及目录项；最终分页后在 Word 中更新目录页码。

只统一 4.5 的图题、表格、分页和接口说明版式时，使用 `platform_word.py revise-interface-layout --input 已有软设.docx --output 修订软设.docx`。此命令沿用 Word 中已有的接口内容及图，不重新提取接口。

只把联邦学习接口说明按操作分栏时，使用 `platform_word.py revise-interface-descriptions --input 已有软设.docx --output 修订软设.docx`。此命令不修改接口清单、图或契约内容。

只补全联邦学习接口名称和操作标题的中文时，使用 `platform_word.py revise-interface-terms --input 已有软设.docx --output 修订软设.docx`。接口标识、路径、报文字段及枚举值不变。

将已有 Word 的联邦学习接口标识统一为 `LBLS-JK-NB-模块缩写-功能缩写` 时，使用 `platform_word.py revise-interface-ids --input 已有软设.docx --output 修订软设.docx`。功能缩写由 `sdd-interface-numbers.json` 显式登记；扩展接口时在对应模块列表末尾追加不同的用途缩写，不重排历史条目，以兼容早期序号版文档。

更新 5.1 联邦学习模块详细设计及对应的第 6 章追溯时，使用 `platform_word.py revise-detail --input 已有软设.docx --output 修订软设.docx`。设计标识采用「父级标识-子项后缀」，例如 `LBLS-GN-LBXX-SN-F0175`；已有功能尾号不变，需规标识、部署单元标识和接口编号不随之调整。

统一架构、部件和接口章节中的联邦学习标识时，先导出图 40-3、40-4、40-6、41、174-1、174-2 的最新 draw.io 图源，再运行 `platform_word.py revise-identifiers --input 已有软设.docx --output 修订软设.docx`。同一业务部件沿用详细设计标识，部署单元使用 `LBLS-GN-LBXX-DEP-*`，接口编号和图示关系编号保持不变。

在已有软设 Word 上更新接口章节时，使用 `platform_word.py revise-interfaces --input 已有软设.docx --output 修订软设.docx`。如果只调整归类和引用、不重新生成契约正文，改用 `classify-interfaces`，并采用新文件名。

更新正文包里的联邦学习模块性能设计后，可用 `platform_word.py revise-performance --input 已有软设.docx --output 修订软设.docx`，只将 4.7 的这四段回填到已有 Word。

5.1 以已有软件单元、功能登记和当前模型为来源，按原设计标识组织正文；八个关键处理单元的现状审校维护在 `sdd-detail-units.json`。生成时不重复接口字段表和数据库表。对已有 Word，可用 `platform_word.py restore-detail --input 已有软设.docx --output 修订软设.docx` 补全空缺的 5.1；若该节已有正文，命令会拒绝覆盖。

生成总稿时，联邦学习功能追溯由 `sdd-trace-design-map.json` 指定操作标识，从现行需规读取功能名称，从 5.1 正文推导设计章节号；逆向追溯使用同一份对应关系。在完整的既有 Word 上同步接口、详细设计及其追溯时使用 `platform_word.py revise-detail-review --input 已有软设.docx --output 修订软设.docx`。

## 将 Word 中的手工修改回存到项目

需要增删段落、调整表格结构或替换图件时，从最新 JSON 生成一份 Word，再在 Word 中编辑并保存。如果已经在 Word 里修改过，先保存这份 Word，直接用它提取。将保存后的版本完整提取到新目录。

```sh
docs/generated/platform-word-venv/bin/python -B \
  docs/document-support/gbt-8567/platform-word/platform_word.py extract \
  --input 'docs/generated/platform-word/已经手工修改的需规.docx' \
  --bundle docs/generated/platform-word/srs-import
```

核对新旧 `text.json` 的差异，确认 Word 已包含要保留的变更后，将新提取的 `text.json` 和 `layout.docx` 一起更新到本地 `srs/`，软设同理。将版式上传到团队共享文档存储后，再提交正文 JSON。脚本会校验 JSON 与版式的配套关系。手工修改和重新生成均以新文件保存，便于比对。
