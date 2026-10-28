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

- 平台总体业务架构图及配套表述见 [联邦孪生平台业务架构](federated-twin-business-architecture.md)，以原生 draw.io 图稿维护布局，并使用[统一图库与导出工具](../../diagrams/README.md)生成 SVG、PNG。用户使用反馈为人工沟通，不表示系统提供独立评审功能或自动重训能力。
- 训练、模型交付、应用推理及业务反馈的内部交互见[系统集成关系图](federated-twin-internal-integration.md)，同样以原生 draw.io 维护；所有 PNG、SVG 导出至已忽略的 `docs/generated/diagrams/`，仅提交图源。
- 院内既有系统、检测与可穿戴设备的接入关系见[院内系统与设备集成关系](federated-twin-hospital-device-integration.md)，区分授权业务信息使用与原始训练数据不出域，并提供可供总稿使用的正文。
- 联邦学习业务总流程见[端到端业务流程](federation-learning-end-to-end.md)，按当前用户旅程保留 16 个阶段及 18 条连接，以 draw.io 维护；明确前置条件、可选安全聚合、多轮训练回路和独立监控分支。
- 云端与医院侧的实际部署分工见[总体部署架构](federated-twin-deployment-overview.md)，区分云端大模型、通用医疗模型与联邦平台，以及医院孪生应用、本院个性化模型和联邦代理，明确孪生与联邦医院集合可部分重叠。

- 01 原稿为 `.doc`，脚本先转为 `.docx` 再插入内容；Word 中应复核页码、目录、跨页表格与字体。
- 03 原稿数据库级设计决策中仍有“仅由一份主数据库文件”等与联邦学习架构不符的表述；该章暂保留总稿原文，待统稿审定。
- 03 合并脚本仅按 `database-design-corrections.json` 修正范围章节的全局表述，并补充联邦学习的数据元素、表映射、访问单元及双向追溯。详细更新入口与核对要求见 `database-design-README.md`。
- 04 原有其他模块的流程和场景未调整；应复核跨子系统角色名称、导航与截图是否与交付版本一致。
- 05 原有 JDK 1.8、MySQL、Nacos 安装步骤属于联合项目的其他部分，不能作为联邦学习子系统的部署步骤。需按实际交付环境补齐镜像版本、容量、网络策略、备份与恢复参数。
- 05 原有截图展示旧环境连接信息和疑似明文口令；对外发布前必须逐页检查、脱敏或替换。生成脚本仅补充联邦学习章节，不会自动改动其他子系统的截图。
- 五份稿件是待审阅合并候选稿，不代表联合项目的其他章节已经完成技术一致性检查。源 Markdown 应作为联邦学习部分后续修改的入口，再重新生成 Word 副本。
