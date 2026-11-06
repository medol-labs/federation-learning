# 软件分层体系结构

![软件分层体系结构](../../generated/diagrams/federated-twin-layered-software-architecture.png)

图：联邦孪生平台软件分层体系结构图

软件按职责划分为应用层、应用集成框架层、基础层和数据层。分层用于分离业务功能、应用协作、运行机制和数据管理，降低业务规则与界面、外部接口及存储实现之间的耦合。图中横向区分联邦学习软件与孪生应用软件，同层组件不表示共享部署、权限或数据库。各层是职责分类，不代表每个框均为独立服务，也不规定所有请求逐层穿过全部组件。

应用层提供面向用户和业务流程的功能。联邦学习包括组织与联邦管理、数据集元数据治理、训练编排、安全聚合、运行时与本地执行管理、模型工件管理和状态查询；孪生应用包括医疗数字人与风险动态评估及管理，分别承担患者状态展示、疾病推演、风险画像、评估及预警。

应用集成框架层承担界面适配、业务处理协调、外部能力接入和数据访问。联邦学习通过 Refine、React 构建控制台，通过 Spring 接口接收命令和查询，使用 Axon 组织命令处理、业务事件与查询投影，形成事件溯源与读写职责分离的处理机制。IAM、文件、字典和通知等是联邦学习自身支撑能力。孪生应用分别维护界面与业务接口、院内数据接入、模型推理调用、用户权限及配置，不与联邦学习共用这些服务实例。

基础层提供运行与算法执行环境。联邦学习后端使用 Java 21、Kotlin 和 Spring Boot，训练与聚合引擎使用 Python 及相应算法插件；浏览器、openEuler 操作系统和容器运行环境承载相应部署。孪生侧按其软件实现配置浏览器、可视化、模型推理及应用运行环境，不从联邦学习技术栈推定孪生侧采用相同框架。框架层表示软件组织与协作机制，基础层表示其运行依赖。

数据层分别管理业务事件、查询视图、文件工件和业务数据。联邦学习各部署单元独立使用 UmaDB 保存事件、PostgreSQL 保存查询视图及处理位置，文件和模型工件独立管理，原始训练数据保留在医院。孪生侧管理患者、诊疗、监测、风险结果以及模型和三维资源。模型通过可下载工件交付，下载后由应用推理侧部署，不通过共享数据库形成跨系统集成。

## 维护与引用

参照本地《11-软件设计说明书(438C原始模板)》“软件体系结构”的图 2 分层表达方式编制，不照搬其示例业务或 ODBC 等技术选型。孪生侧未确认的框架和数据库产品以职责类别表示，具体产品由其详细设计确定。

此图用于软件设计说明的“软件体系结构”总体说明。原有 `federated-twin-software-architecture.drawio` 保持不变，继续作为软件部件协作视图；本图补充分层及功能包视角，不覆盖原图。维护说明不进入交付稿。

[可编辑 draw.io 图源](figures/federated-twin-layered-software-architecture.drawio)

```bash
python3 docs/diagrams/drawio.py format docs/438c/merge/figures/federated-twin-layered-software-architecture.drawio
python3 docs/diagrams/drawio.py check docs/438c/merge/figures/federated-twin-layered-software-architecture.drawio
python3 docs/diagrams/drawio.py export docs/438c/merge/figures/federated-twin-layered-software-architecture.drawio
```
