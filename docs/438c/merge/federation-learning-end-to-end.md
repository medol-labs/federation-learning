# 联邦学习端到端业务流程

![联邦学习端到端业务流程](../../generated/diagrams/federation-learning-end-to-end.png)

[draw.io 图源](figures/federation-learning-end-to-end.drawio) · [SVG 矢量图](../../generated/diagrams/federation-learning-end-to-end.svg)

本图以当前建模中的 `FederationLearningEndToEnd` 用户旅程为依据，将原有 Mermaid 流程整理为与业务架构、内部集成关系一致的原生 draw.io 图稿。保留全部 16 个阶段和 18 条连接关系，中文标题按业务阅读习惯调整，节点 ID 保留对应阶段名称。它是项目文档图稿，不修改 MEDOL 建模或文档生成器。

## 阅读顺序

1. 准备协作条件：组织注册后加入联邦，并准备、确认机构运行环境；定义特征与标签后，在运行时就绪的条件下准备机构本地数据集。初始模型独立注册。
2. 汇合训练前置条件：加入联邦、机构数据集准备及初始模型共同支撑训练配置。图中三条输入分别进入训练配置节点，不表示三项条件有强制的串行顺序。
3. 执行训练轮次：配置训练后选择本轮参与方，由机构本地执行训练，提交模型更新及训练指标。
4. 按条件聚合：需要安全聚合时进入安全聚合分支，否则进入普通聚合分支。两个分支均进入全局模型评估，不将安全聚合画成普通聚合前的必经步骤。
5. 决定后续动作：评估后判断继续训练或完成任务。需要更多轮次时返回参与方选择；训练完成时发布模型，进行版本管理并提供下载。
6. 独立开展监控：运行时就绪后开展健康监控，不将监控完成作为启动训练的条件。

本图是业务总流程，不展开失败重试、权限校验、具体命令、事件与接口。模型发布不表示自动部署到孪生业务应用侧，原始训练数据不出机构本地域。

## 阶段对应

| 建模阶段 | 图中业务标题 |
| --- | --- |
| OnboardOrganization | 注册组织 |
| JoinFederation | 加入联邦 |
| ProvisionRuntime | 准备运行环境 |
| ConfirmRuntimeReady | 确认运行时就绪 |
| DefineFeatureSchema | 定义特征与标签 |
| PrepareParticipantDataset | 准备机构本地数据集 |
| RegisterInitialModel | 注册初始模型 |
| ConfigureTraining | 配置并提交训练任务 |
| SelectParticipants | 选择本轮参与方 |
| TrainOnParticipants | 机构本地训练 |
| SecurelyAggregateUpdates | 安全聚合模型更新 |
| AggregateUpdates | 聚合模型更新 |
| EvaluateGlobalModel | 评估全局模型 |
| ContinueOrCompleteTraining | 判断继续或完成训练 |
| PublishTrainedModel | 发布训练模型 |
| MonitorRuntimeHealth | 持续监控运行时健康 |

## 编辑与导出

以 `.drawio` 文件作为唯一布局编辑来源，后续建模调整时核对阶段与连接关系，并同步更新图源和本文。导出图片位于已忽略的 `docs/generated/diagrams/`，不提交 PNG、SVG。

```bash
python3 docs/diagrams/drawio.py format docs/438c/merge/figures/federation-learning-end-to-end.drawio
python3 docs/diagrams/drawio.py check docs/438c/merge/figures/federation-learning-end-to-end.drawio
python3 docs/diagrams/drawio.py export docs/438c/merge/figures/federation-learning-end-to-end.drawio
```
