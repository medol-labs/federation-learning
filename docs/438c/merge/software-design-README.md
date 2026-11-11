# 软件设计说明合并

标题统一采用中文宋体、西文与序号Times New Roman，全部加粗、不斜体，保留字号、层级及编号。该规则覆盖接口/详细设计、追溯预览及正式合并，自动编号字体同步处理。

## 双向追溯预览

表内功能需求与具体操作设计均带章节号，不重复列上级软件单元。编号读取当前受控需规正文和详细设计渲染顺序，分别复用3.3.1与5.1的编号规则，不由功能标识尾号推算；章节重排会触发追溯变更。

```bash
python3 scripts/merge-438c-software-design.py --traceability-preview --markdown-out docs/generated/438c/merge/software-design-traceability-preview.md
```

输出仅包含联邦学习第6章两张表的Word，沿用总稿表头及样式，不修改总稿或接受基线。正向按具体需求列操作设计，逆向按操作设计汇总需求名称与编号。通用查询、通信、性能要求单独对应其设计项。两张表共用同一份映射；内部软件单元分配关系与覆盖校验仍保留，但不作为表内展示项。

## 接口与单元子节导出

章节编排参照 GJB_438C 项目的 `skills/word-fillter-438c-sdd/templates/project.json`。接口设计先列“接口标识和接口图”，随后使用其 `structure["4.3"].subsections` 动态结构，每项只有 `name` 与 `content`。正文按示例的接口实体、接口类型、接口特性八项、通信方法、协议填写。参考模板没有规定七个分组；本项目按实际资源契约组织子节，同一资源的多个操作在该子节内完整说明，不筛选所谓关键接口。

`software_design_interface_inventory.py` 发现资源与路由，`software_design_interface_contracts.py` 结合模型和实际请求/响应声明填写数据元素。认证、文件及引擎等特殊契约维护在 `software-design-interface-contracts.json`，不以通用命令字段代替其真实报文。未匹配到契约时停止生成；插件开放对象和未规定的限制不得虚构。接口正文指纹纳入合并基线，测试校验所有路由恰好进入一个资源契约。接口图展示通信实体和方向，不用固定七组代替接口明细。查询视图同步图的客户端是调用角色，不是新增部署模块。

预览时同时输出 `software-design-interface-project.json`，供核对上述参考结构。这是接口章节输入片段，不是可独立运行参考项目完整填写程序的项目配置；Word仍通过本项目合并脚本导出，以保持总稿样式与编号。

更新接口图后先导出：

```bash
python3 docs/diagrams/drawio.py export docs/438c/merge/figures/federation-learning-interface-relations.drawio docs/438c/merge/figures/federation-learning-sync-interface-relations.drawio
```

详细设计以软件单元为同级子节，按标识符和用途、单元设计决策、约束、数据元素、逻辑、异常处理组织。关键处理单元及业务单元不再重复输出汇总表；每项操作只归属一个单元，操作为四级标题，保留 `FL-SD-OP-Fxxxx`。数据元素包含输入输出字段，状态来自显式转换定义；约束及验收分支不截断。追溯关系仍在第6章维护。已有实现差距说明继续保留。

`scripts/software_design_template_structure.py` 负责章节编排；原结构化条目仍作为稳定内容源和追溯映射。输出正文指纹纳入既有合并检查，结构变化也会触发审核。参考项目只用于模板结构，不复制编译器示例的业务内容、数值限制或追溯数据。

审阅变更后使用既有 `--sync`、`--check` 流程，再导出Word和可比较的正文：

```bash
python3 scripts/merge-438c-software-design.py --markdown-out docs/generated/438c/merge/software-design-interface-detail.md
```

生成目录仅用于审阅和交付，人工修订应修改结构化来源，不直接编辑导出文件。

若基线还有未审核变化，可先只导出这两章的候选稿，不接受任何基线变化，也不修改总稿：

```bash
python3 scripts/merge-438c-software-design.py --chapter-preview --markdown-out docs/generated/438c/merge/software-design-interface-detail.md
```

候选Word为 `docs/generated/438c/merge/software-design-interface-detail-preview.docx`，继承总稿标题和表格样式。

候选Word的接口设计从 `4.4.1`、详细设计从 `5.1` 开始编号，子标题按层级递增，并关闭继承的自动编号，避免重复显示。正式总稿合并仍使用总稿自己的自动编号。

本目录的 `software-design-federation-learning.md` 只保留九处插入点占位符。输入/输出设计决策由 `software-design-input-output.json` 生成；数据库概念/逻辑/物理设计、体系结构、执行方案、性能、接口和详细设计由 `software-design-sections.json` 的稳定 ID 条目生成。接口设计直接写入本软件设计说明的接口设计章节，不另建接口设计文档。设计项、段落和表格行均可单独追踪，不直接编辑占位符或生成的 Word。输入/输出项关联的建模命令和事件仅用于内部追踪，不写入交付文档。数据库查询目录清单从当前 `.medol/codegen-model.json` 读取，覆盖平台、参与方代理和共享支撑服务的实际业务视图。

`software-design-corrections.json` 对总稿中与联邦学习实际实现冲突的全局表述做逐句勘误，覆盖封面名称、数据库选型及分布、技术栈、部署假设和安全权限描述，并在引用表列出本版需规。每项都先与原稿逐字匹配，匹配不到即停止；仅修改合并副本，不覆盖 `tmp/merge` 原稿，也不改写其他模块的具体业务设计。需规版本号、日期及修订人仍须由总稿负责人确认。

输入/输出源同时维护联邦学习行为与查询展示决策，以现有命令和事件定义校验引用。详细设计包含关键处理单元、业务上下文单元及其具体操作，列出输入与状态来源、业务约束、外部能力、结果和异常；详细字段仍以需求规格与接口设计为准。第6章逐条对照功能需求与第5章的具体操作设计，不重复列上级单元。

合并时将原稿“性能设计”中的既有段落归入“医疗数字人及风险动态评估模块性能设计”，然后在该节末尾插入独立的“联邦学习模块性能设计”。原稿既有段落不改写，也不作为联邦学习已实现能力或验收指标。

联邦学习章节把静态依赖、训练阶段、失败恢复、关键状态和软件单元职责作为受控表格维护。数据库物理设计明确区分当前 JPA 自动更新与交付所需的版本化 DDL；性能设计先定义测量口径，目标值应由交付环境压测确认。安全章节区分已有 JWT/内部令牌机制与训练引擎服务端尚未完成的应用层鉴权。

CSCI 详细设计按训练主链的软件单元展开启动条件、输入来源、处理顺序、结果移交和异常限制。需求与单元追踪表引用 `3.3.1-function-register.json` 中的稳定功能需求标识，合并检查验证名称和定义指纹；关键单元还引用当前实现文件并记录指纹。源文件引用只供复核，不写入交付 Word。已发现的计划字段占位生成、本地计划校验占位、固定全局评估指标等现状在正文中明确列为限制，不表示交付前已完成。

第6章沿用总稿已有的“正向追溯”“逆向追溯”两张表。合并时替换原有联邦学习占位行，覆盖当前功能需求；设计侧追溯到第5章具体操作，通用要求独立映射至第4章对应设计。逆向表按同一操作映射汇总需求名称和标识，长列表分行显示。原稿其他模块行保留，序号顺延。需求、分配关系或章节号变化时，合并基线报告差异并要求人工复核。

数据库概念设计的关系图源位于 `figures/conceptual-data-*.dot`，PNG 导出至已忽略的 `docs/generated/diagrams/`，随结构化条目插入 Word；只提交图源，图和图源的文件指纹仍纳入合并基线。修改图源后执行 `python3 docs/diagrams/drawio.py export docs/438c/merge/figures/*.dot`，再审阅图片并同步基线。路径迁移也会触发基线差异，须按现有审阅及 `--sync` 流程确认，不自动接受。图表达概念关联和平台/参与方归属，不表达物理表或跨库外键。

当前总文档底稿位于 `../../../../tmp/merge/02“感一通一算”一体化的跨域健康管理联邦孪生平台-软件设计说明v1.0.docx`。原始文件不受脚本修改。执行：

```bash
python3 docs/diagrams/drawio.py export docs/438c/merge/figures/federation-learning-components.drawio
python3 docs/diagrams/drawio.py export docs/438c/merge/figures/federation-learning-interface-relations.drawio
python3 scripts/merge-438c-software-design.py --check
python3 scripts/merge-438c-software-design.py
```

第二条命令将合并副本写到 `docs/generated/438c/merge/software-design-federation-learning-merged.docx`。该路径是忽略的生成物，不作为人工编辑源。合并脚本要求九处标题与预期一致，并拒绝覆盖原始总文档或对已合并副本重复插入。

`software-design-register.json` 是受控合并基线，记录九段渲染正文、总稿逐句勘误、第 6 章逐项双向追溯、各设计项与表格行、关联建模定义、查询视图定义、接口与详细设计条目引用的实现文件、功能需求定义、底稿各标题下的内容及整个底稿文件指纹。`--check` 和正式合并遇到差异时会列出变化的条目 ID 或章节并停止；先核对差异、修改对应 JSON 与 `software-design-CHANGELOG.md`，确认后执行 `python3 scripts/merge-438c-software-design.py --sync` 更新基线，再运行 `--check` 与合并。实现变化仅触发人工复核，不自动改写交付正文；引用文件路径也不会进入 Word。建模命令、事件或查询视图定义、底稿图表、格式或域变化也会触发重新核对。不要直接编辑生成的 Word 副本。

接口设计在同章保留接口图与逻辑接口索引。`software_design_interface_inventory.py` 从平台、参与方代理、支撑服务和训练引擎提取 HTTP 操作，将其完整归入各逻辑接口的通信方法表；共享操作可被多个逻辑接口引用，但操作标识不变。字段、鉴权及异步结果说明位于所属接口的特性中。清单记录实现文件指纹，路由或分组变化须审核并同步基线；保留的字段表不表示所有嵌套数据类型和插件契约已完整定义。

## 仍需总文档核对

- 原稿的全局数据库段落写了海量数据库 M100、“一份主数据库文件”等；联邦学习当前实现使用独立的 PostgreSQL 查询库和 UmaDB 事件存储。总文档应区分不同子系统，不得把全局描述误套到联邦学习。
- 原稿的通用性能段落含缓存、NoSQL、CDN、两阶段/三阶段提交等方案；这些不能自动视为联邦学习已实现能力。联邦学习的可验收量化指标仍需目标环境压测确认。
- 原稿的图表、表号、自动章节编号和其他子系统内容保留不动。联邦学习章节合并后，需要在最终 Word 中更新目录、图表题注和交叉引用，并与其他团队确认共享能力的唯一归属。
- 开发环境和交付环境的服务地址及网络可达性不同，设计正文不写死具体 IP、端口或镜像仓库地址；部署参数由对应环境的安装文档确定。
- 总稿负责人需确认需规的正式修订版本、发布日期和软设封面/修订表的一致性；合并脚本不虚构编制人或签发记录。
- 目标环境的性能阈值、训练引擎鉴权/TLS、服务间超时与幂等策略、各后端同版 OpenAPI 归档均需联调或测试证据，当前设计只记录核对项。
- 上下文级软件单元已覆盖当前功能登记，但每个操作的逐方法细设计、异常测试和 438C 验收覆盖仍需按实际交付范围继续完善。
