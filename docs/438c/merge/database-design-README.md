# 联邦学习数据库设计合并稿

`database-design-federation-learning.md` 是联邦学习部分的人工维护来源。它按总稿结构补充概念设计、逻辑设计、物理设计及数据库访问软件单元；正向、逆向需求追溯由脚本根据稳定编号与需求登记填入总稿原有表格，不另起章节或表格。数据库级设计决策章节暂时保留总稿原文，不插入联邦学习专属决策，也不改写该章段落。`database-design-corrections.json` 目前只修正范围章节的全局数据库表述；原始总稿不修改。

数据库合并底稿固定为 `../tmp/merge/03“感一通一算”一体化的跨域健康管理联邦孪生平台-数据库设计说明v1.0.docx`，不再使用 `tmp/merge/项目文档v0.2/` 下的旧版。`database-design-merge-base.json` 记录已审阅底稿的 SHA-256；底稿更新时，先比较内容和章节锚点，审核后再更新指纹，不直接绕过校验。

逻辑视图字段来自 `.medol/codegen-model.json`，查询表名、主键、JPA 类型及长文本标记来自当前平台、参与方代理与共享支撑服务的生成实体。每个视图在需规 `3.3.1-function-register.json` 中必须有对应查询功能，缺失或实体字段不一致时合并停止。`database-design-register.json` 为每个查询视图固定 `FL-DB-Lxxx`、`FL-DB-Pxxx` 编号，并保存模型与实体结构指纹；建模顺序变化不改变编号，字段或映射变化则要求先审核。模型、实体或功能需求变化后，应先审核差异，再生成新的合并副本，不直接编辑 Word。本章节同时列出共享支撑服务的通知、数据交换、字典、文件上传和身份访问管理目录；这些是联邦学习部署的对象，不覆盖新版底稿中系统管理模块已定义的独立表。

在 `federation-learning` 根目录执行：

```bash
python3 scripts/merge-438c-project-documents.py --only 03 --check
python3 scripts/merge-438c-project-documents.py --only 03
```

如果检查指出查询视图与编号登记不一致，先审阅增删或改名，再执行 `python3 scripts/database_design_inventory.py --sync`，核对登记文件的 Git 差异后重新生成。已删除视图的编号保留为 `retired`，不分配给其他视图。

输出为 `docs/generated/438c/merge/project-documents/03-federation-learning-merged.docx`。该文件是可审阅的合并副本，不是人工修改入口。合并后应在 Word 中更新目录、图表编号和交叉引用；全系统其他模块的表结构不由本脚本重新设计。物理字段表记录当前 JPA 映射，不等于已验收的建库迁移：交付时应核对 PostgreSQL 实际列、约束和索引，以及 UmaDB 与文件工件的备份恢复演练。

总稿数据库级设计决策仍有“仅由一份主数据库文件”等与联邦学习部署不符的表述，现阶段不自动修改；统稿时须单独审定。
