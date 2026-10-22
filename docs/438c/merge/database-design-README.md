# 联邦学习数据库设计合并稿

`database-design-federation-learning.md` 是联邦学习部分的人工维护来源。它按 438C 和总稿结构分为数据库级设计决策、概念设计、逻辑设计、物理设计、数据库访问软件单元及需求追溯。`database-design-corrections.json` 只修正总稿中会误套联邦学习的全局数据库表述；原始总稿不修改。

逻辑视图字段来自 `.medol/codegen-model.json`，查询表名、主键、JPA 类型及长文本标记来自当前平台与参与方代理的生成实体。每个视图在需规 `3.3.1-function-register.json` 中必须有对应查询功能，缺失或实体字段不一致时合并停止。`database-design-register.json` 为每个查询视图固定 `FL-DB-Lxxx`、`FL-DB-Pxxx` 编号，并保存模型与实体结构指纹；建模顺序变化不改变编号，字段或映射变化则要求先审核。模型、实体或功能需求变化后，应先审核差异，再生成新的合并副本，不直接编辑 Word。共享支撑服务的业务目录由系统管理部分统一设计；本章节只说明其与联邦学习之间的存储边界。

在 `federation-learning` 根目录执行：

```bash
python3 scripts/merge-438c-project-documents.py --only 03 --check
python3 scripts/merge-438c-project-documents.py --only 03
```

如果检查指出查询视图与编号登记不一致，先审阅增删或改名，再执行 `python3 scripts/database_design_inventory.py --sync`，核对登记文件的 Git 差异后重新生成。已删除视图的编号保留为 `retired`，不分配给其他视图。

输出为 `docs/generated/438c/merge/project-documents/03-federation-learning-merged.docx`。该文件是可审阅的合并副本，不是人工修改入口。合并后应在 Word 中更新目录、图表编号和交叉引用；全系统其他模块的表结构不由本脚本重新设计。物理字段表记录当前 JPA 映射，不等于已验收的建库迁移：交付时应核对 PostgreSQL 实际列、约束和索引，以及 UmaDB 与文件工件的备份恢复演练。
