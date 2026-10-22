# 438C 项目文档联邦学习部分合并稿

此目录保存联邦学习子系统的可追溯补充内容。脚本以 `tmp/merge/项目文档v0.2/` 下的联合项目文档为底稿，生成独立的待审阅 Word 副本，不修改原文件。

## 生成

在 `federation-learning` 根目录执行：

```bash
python3 scripts/merge-438c-project-documents.py --check
python3 scripts/merge-438c-project-documents.py
```

输出位于 `docs/generated/438c/merge/project-documents/`：

| 文件前缀 | 文档 | 联邦学习补充来源 |
| --- | --- | --- |
| 01 | 软件需求规格说明 | `3.3.1-federation-learning.md` |
| 03 | 数据库设计说明 | `database-design-federation-learning.md`、模型查询视图与 JPA 实体 |
| 04 | 用户操作手册 | `user-manual-federation-learning.md` |
| 05 | 安装部署手册 | `installation-federation-learning.md` |

02 软件设计说明另由 `scripts/merge-438c-software-design.py` 生成，来源是 `software-design-federation-learning.md`。

## 统稿注意

- 01 原稿为 `.doc`，脚本先转为 `.docx` 再插入内容；Word 中应复核页码、目录、跨页表格与字体。
- 03 原稿按单一 PostgreSQL 数据库叙述，且出现“仅由一份主数据库文件”等表述；合并副本必须按联邦学习的 UmaDB 事件存储、各服务 PostgreSQL 查询库和文件工件边界修正。
- 03 合并脚本已在副本中按 `database-design-corrections.json` 精确修正上述全局冲突表述，并补充联邦学习的数据库级决策、数据元素、表映射、访问单元及双向追溯。详细更新入口与核对要求见 `database-design-README.md`。
- 04 原有其他模块的流程和场景未调整；应复核跨子系统角色名称、导航与截图是否与交付版本一致。
- 05 原有 JDK 1.8、MySQL、Nacos 安装步骤属于联合项目的其他部分，不能作为联邦学习子系统的部署步骤。需按实际交付环境补齐镜像版本、容量、网络策略、备份与恢复参数。
- 05 原有截图展示旧环境连接信息和疑似明文口令；对外发布前必须逐页检查、脱敏或替换。生成脚本仅补充联邦学习章节，不会自动改动其他子系统的截图。
- 五份稿件是待审阅合并候选稿，不代表联合项目的其他章节已经完成技术一致性检查。源 Markdown 应作为联邦学习部分后续修改的入口，再重新生成 Word 副本。
