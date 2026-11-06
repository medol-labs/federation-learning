# 总体概念 E-R 图

![总体概念 E-R 图](../../generated/diagrams/federated-twin-conceptual-er.png)

本图用于数据库设计第 4 章“数据库详细设计”开头，作为各模块概念设计的总览。它表达主要业务实体和关系，不替代各模块完整实体、属性及物理表设计，也不替代第 3.6 节数据库分布图。

联邦学习中，组织通过成员资格参与联邦，一个组织可以拥有多个运行时。运行时配置数据集绑定，绑定引用本地数据集，数据集遵循特征架构。训练运行配置采用特征架构，训练作业采用运行配置，作业包含多轮训练，每轮为参与方形成执行计划。图中计划、轮次、作业之间为业务关联，不要求跨服务数据库建立外键。

医疗数字人与风险评估使用患者主索引关联就诊、病灶绑定、处置绑定和风险评估记录。患者病灶绑定同时关联病灶及解剖部位。孪生自身系统管理中的机构、科室和用户另行管理，不与联邦学习组织或 IAM 用户自动合并。

1:N 和 N:1 表示关系的最大基数，不规定是否必须已有子记录，最小基数及可空条件见模块详细设计。框中标识为概念标识，不表示已存在数据库外键。图中未建立患者与训练作业、孪生机构与联邦组织之间未经确认的统一映射，也未将模型工件交付画成跨库实体外键。

## 维护依据

- 联邦部分：当前 `.medol/source.medol` 及已有概念关系图。
- 孪生部分：`tmp/merge/项目文档v0.3(1)/03健康管理联邦孪生平台-数据库设计说明V0.4.docx` 的实体关系表；就诊标识按患者标识与就诊序次组合表达。
- 本图选取主要关系，不穷举 IAM、文件、字典等支撑实体，也不将事件和查询投影重复画成两份业务实体。
- 配套说明与图源受 Git 维护，导出图片不提交；本次不直接修改 Word。

[draw.io 图源](figures/federated-twin-conceptual-er.drawio)

```bash
python3 docs/diagrams/drawio.py format docs/438c/merge/figures/federated-twin-conceptual-er.drawio
python3 docs/diagrams/drawio.py check docs/438c/merge/figures/federated-twin-conceptual-er.drawio
python3 docs/diagrams/drawio.py export docs/438c/merge/figures/federated-twin-conceptual-er.drawio
```
