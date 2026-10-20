# 软件设计说明合并

本目录的 `software-design-federation-learning.md` 只保留九处插入点占位符。输入/输出设计决策由 `software-design-input-output.json` 生成；数据库概念/逻辑/物理设计、体系结构、执行方案、性能、接口和详细设计由 `software-design-sections.json` 的稳定 ID 条目生成。接口设计直接写入本软件设计说明的接口设计章节，不另建接口设计文档。设计项、段落和表格行均可单独追踪，不直接编辑占位符或生成的 Word。输入/输出项关联的建模命令和事件仅用于内部追踪，不写入交付文档。数据库查询目录清单从当前 `.medol/codegen-model.json` 读取，仅列平台和参与方代理的实际业务视图；共享支撑目录由系统管理章节统一处理。

当前总文档底稿位于 `../../../../tmp/merge/02“感一通一算”一体化的跨域健康管理联邦孪生平台-软件设计说明v1.0.docx`。原始文件不受脚本修改。执行：

```bash
python3 scripts/merge-438c-software-design.py --check
python3 scripts/merge-438c-software-design.py
```

第二条命令将合并副本写到 `docs/generated/438c/merge/software-design-federation-learning-merged.docx`。该路径是忽略的生成物，不作为人工编辑源。合并脚本要求九处标题与预期一致，并拒绝覆盖原始总文档或对已合并副本重复插入。

`software-design-register.json` 是受控合并基线，记录九段渲染正文、各设计项与表格行、关联建模定义、查询视图定义、底稿各标题下的内容及整个底稿文件指纹。`--check` 和正式合并遇到差异时会列出变化的条目 ID 或章节并停止；先核对差异、修改对应 JSON 与 `software-design-CHANGELOG.md`，确认后执行 `python3 scripts/merge-438c-software-design.py --sync` 更新基线，再运行 `--check` 与合并。建模命令、事件或查询视图定义、底稿图表、格式或域变化也会触发重新核对。不要直接编辑生成的 Word 副本。

## 仍需总文档核对

- 原稿的全局数据库段落写了海量数据库 M100、“一份主数据库文件”等；联邦学习当前实现使用独立的 PostgreSQL 查询库和 UmaDB 事件存储。总文档应区分不同子系统，不得把全局描述误套到联邦学习。
- 原稿的通用性能段落含缓存、NoSQL、CDN、两阶段/三阶段提交等方案；这些不能自动视为联邦学习已实现能力。联邦学习的可验收量化指标仍需目标环境压测确认。
- 原稿的图表、表号、自动章节编号和其他子系统内容保留不动。联邦学习章节合并后，需要在最终 Word 中更新目录、图表题注和交叉引用，并与其他团队确认共享能力的唯一归属。
- 开发环境和交付环境的服务地址及网络可达性不同，设计正文不写死具体 IP、端口或镜像仓库地址；部署参数由对应环境的安装文档确定。
