# Event Modeling Workspace 软件设计文档

<!-- em:section id="document.changeLog" -->
## 变更记录

| 版本 | 日期 | 变更说明 | 作者 |
| --- | --- | --- | --- |
| V0.1.0 | 2026-09-19 | 初始发布 | MEDOL |

## 一、概要设计/EMW-SD-OD

本文档描述由已评审领域模型推导出的软件设计。文档将系统级架构决策与上下文、业务能力、数据、接口和集成的详细设计分开说明，便于研发、测试和交付协同。

### 1.1 限界上下文与模块划分/EMW-SD-BCAMD

| 限界上下文 | 业务域 | 业务对象 | 主要职责 |
| --- | --- | --- | --- |
| 组织管理（Organization Management） | 联邦学习平台（Federation Learning Platform） | 组织（Organization）, 用户组织成员资格（User Organization Membership） | 组织管理负责组织生命周期以及运行时发现的计算节点可用性。 |
| 联邦管理（Federation Management） | 联邦学习平台（Federation Learning Platform） | 联邦（Federation）, 联邦成员资格（Federation Membership） | 联邦管理负责联邦及联邦成员资格治理。 |
| 字典维护（Dictionary Maintenance） | 联邦学习平台（Federation Learning Platform） | 字典（Dictionary）, 字典值（Dictionary Value） | 字典维护负责管理平台上下文使用的动态选项字典。业务上下文存储 valueCode 并通过 dictionaryCode 查询 DictionaryValueCatalog 以进行显示和选择。 |
| 文件上传（File Upload） | 联邦学习平台（Federation Learning Platform） | 已上传文件（Uploaded File） | FileUpload 是共享的持久化文件存储边界。它通过可替换的存储适配器存储已上传内容，记录每个已上传文件，公开经过身份验证的内容 URI，并通过 expiresAt 应用可选的保留期。业务域引用已上传文件记录，而不是再创建一个物理存储层。 |
| 运行时预配（Runtime Provisioning） | 联邦学习平台（Federation Learning Platform） | 运行时安装计划（Runtime Installation Plan）, 运行时基础设施包（Runtime Infrastructure Package）, 运行时基础设施（Runtime Infrastructure） | 运行时入驻负责运行时安装规划、引导配置、运行时环境注册、代理安装、就绪检查、连接报告及访问撤销。K3s、Kubernetes、Docker Compose 与主机进程运行时均为安装目标，而非独立的领域概念。; 运行时引导和代理安装材料是支持配置，而非领域状态：平台后端适配器从部署配置或密钥存储中读取 platform.runtime.bootstrap.publicBaseAddress、platform.runtime.agent.platformApiBaseAddress、platform.runtime.agent.controlApiBaseAddress、platform.runtime.agent.containerRegistry、platform.runtime.agent.trustBundleSecretName 和 platform.runtime.agent.accessGrantSecretPrefix。命令、事件和读模型仅保留 runtimeInstallationPlanId、runtimeInfrastructurePackageId 以及生成的访问授权 ID。 |
| 数据集治理（Dataset Governance） | 联邦学习平台（Federation Learning Platform） | 特征架构（Feature Schema）, 运行时数据集元数据（Runtime Dataset Metadata） | 数据集治理将特征架构、平台记录的运行时数据集元数据、数据集访问验证证据以及训练评估包进行分离。运行时代理在参与者基础设施内拥有数据集声明、数据连接器、本地访问配置文件和凭据。 |
| 模型仓库（Model Repository） | 联邦学习平台（Federation Learning Platform） | 模型制品（Model Artifact） | 模型仓库拥有模型标识、版本控制、血缘关系以及训练编排和运行时代理所使用的可拉取工件引用。FileUpload 拥有底层字节和存储适配器；ModelArtifact 拥有其特定于模型的生命周期和元数据。 |
| 训练编排（Training Orchestration） | 联邦学习平台（Federation Learning Platform） | 运行时引擎配置文件（Runtime Engine Profile）, 训练运行配置（Training Run Configuration）, 训练作业（Training Job）, 训练轮次（Training Round）, 参与者执行计划（Participant Execution Plan） | 训练编排负责作业意图、参与者资格、运行时分发以及每轮分布式训练和评估。 |
| 模型生命周期（Model Lifecycle） | 联邦学习平台（Federation Learning Platform） | 模型（Model） | 模型生命周期从训练产生最终已评估候选之后开始。 |
| 运行时监控（Runtime Monitoring） | 联邦学习平台（Federation Learning Platform） | 节点运行时健康（Node Runtime Health）, 运行时节点资源压力（Runtime Node Resource Pressure）, 运行时节点容量（Runtime Node Capacity）, 运行时节点清单（Runtime Node Inventory）, 运行时节点资源遥测（Runtime Node Resource Telemetry）, 训练告警（Training Alert）, 审计记录（Audit Record） | 运行时运维将低频运行时状态变更、节点清单变更、最新遥测视图、训练告警以及仅追加审计记录区分开来。高频心跳和资源样本属于遥测，而非事件溯源领域事件。 |
| 运行时治理（Runtime Governance） | 联邦学习平台（Federation Learning Platform） | 运行时标识（Runtime Identity）, 运行时能力（Runtime Capability） | 运行时治理负责稳定的 RuntimeIdentity 激活、能力检测和撤销。运行时私钥、本地路径、凭据、节点调度和数据平面连接保留在参与者运行时基础设施内部。; 平台管理的运行时代理根据平台部署结果激活。网络可达性不等于业务授权。训练资格使用活跃的联邦成员资格、RuntimeIdentity、已检测到的能力、已连接的运行时状态以及数据集就绪情况。; 训练参数加密、安全聚合密钥和数据平面消息加密由协议适配器或 SecureAggregation 处理，不由 RuntimeIdentity 事件处理。 |
| 安全聚合（Secure Aggregation） | 联邦学习平台（Federation Learning Platform） | 安全聚合会话（Secure Aggregation Session） | 安全聚合拥有基于同态加密的聚合会话。平台持有私钥，仅向运行时代理发布加密上下文元数据；原始密钥材料、公钥文档和存储位置保留在 SecureAggregationService 或 KMS 适配器配置中。 |
| 运行时代理操作（Runtime Agent Operations） | 联邦学习平台（Federation Learning Platform） | 运行时数据集绑定（Runtime Dataset Binding）, 数据集（Dataset）, 代理数据集访问验证（Agent Dataset Access Validation）, 代理数据集配置文件（Agent Dataset Profile）, 轮次执行（Round Execution）, 运行时代理生命周期（Runtime Agent Lifecycle）, 代理运行时基础设施连接（Agent Runtime Infrastructure Connection）, 代理运行时遥测（Agent Runtime Telemetry）, 代理运行时节点清单（Agent Runtime Node Inventory）, 代理运行时节点资源遥测（Agent Runtime Node Resource Telemetry） | 运行时代理操作是一个独立的可部署组件，在参与者基础设施内运行，并使用本地数据和本地机密执行分派的训练作业。 |
| 身份访问管理（Identity Access Management） | 身份访问管理（Identity Access Management） | 用户账户（User Account）, 角色（Role）, 权限（Permission）, 用户角色分配（User Role Assignment）, 角色权限授予（Role Permission Grant）, 服务账户 API 令牌（Service Account Api Token） | 尚未明确 |

### 1.2 部署视图/EMW-SD-DV

| 部署单元 | 业务域 | 包含上下文 |
| --- | --- | --- |
| Federation Learning Support | Federation Learning Platform | Dictionary Maintenance, File Upload, Identity Access Management |
| Federation Learning Platform | Federation Learning Platform | Organization Management, Federation Management, Dataset Governance, Model Repository, Training Orchestration, Model Lifecycle, Runtime Monitoring, Runtime Governance, Secure Aggregation, Runtime Provisioning |
| Federation Learning Runtime Agent | Federation Learning Platform | Runtime Agent Operations, Identity Access Management |
| Federation Learning Support | Federation Learning Platform | Dictionary Maintenance, File Upload, Identity Access Management |
| Federation Learning Platform | Federation Learning Platform | Organization Management, Federation Management, Dataset Governance, Model Repository, Training Orchestration, Model Lifecycle, Runtime Monitoring, Runtime Governance, Secure Aggregation, Runtime Provisioning |
| Federation Learning Runtime Agent | Federation Learning Platform | Runtime Agent Operations, Identity Access Management |

### 1.3 逻辑架构/EMW-SD-LA

- 表现层：72 个已建模页面入口承载用户和运维操作。
- 应用层：184 个业务能力负责编排输入、业务结果、规则校验与自动化触发。
- 领域层：45 个业务对象维护生命周期状态与业务不变量。
- 读侧模型：51 个 Read Model 支撑列表、详情、仪表板、资格判断、目录和运维视图。
- 集成层：15 个集成点连接外部系统、运行时或下游服务。

## 二、详细设计/EMW-SD-DD

<!-- em:section id="software.context.组织管理（OrganizationManagement）" source="domain/FederationLearningPlatform/context/OrganizationManagement" -->
### 2.1 组织管理（Organization Management）/EMW-SD-SECTION

上下文职责: 组织管理负责组织生命周期以及运行时发现的计算节点可用性。

#### 2.1.1 业务对象设计/EMW-SD-BOD

| 对象 | 类型 | 生命周期 | 业务能力 |
| --- | --- | --- | --- |
| 组织（Organization） | 概念 | 已注册（Registered） -> 已激活（Active） -> 已停用（Deactivated） | 注册组织（Register Organization）, 激活组织（Activate Organization）, 停用组织（Deactivate Organization）, 重新激活组织（Reactivate Organization）, 组织目录（Organization Directory） |
| 用户组织成员资格（User Organization Membership） | 概念 | 已激活（Active） | 将用户账户绑定到组织（Bind User Account To Organization）, 用户组织成员资格目录（User Organization Membership Directory） |

#### 2.1.2 应用服务与能力设计/EMW-SD-ASCD

| 业务能力 | 角色/触发条件 | 输入 | 业务结果 | 读侧结果 | 规则 |
| --- | --- | --- | --- | --- | --- |
| 注册组织（Register Organization） | 组织管理员（Organization Admin） | 注册组织（Register Organization） | 组织已注册（Organization Registered）, 状态: 已注册（Registered） | - | 拒绝重复组织（Reject Duplicate Organization） |
| 激活组织（Activate Organization） | 平台管理员（Platform Admin） | 激活组织（Activate Organization） | 组织已激活（Organization Activated）, 状态: 已激活（Active） | - | 尚未明确 |
| 停用组织（Deactivate Organization） | 平台管理员（Platform Admin） | 停用组织（Deactivate Organization） | 组织已停用（Organization Deactivated）, 状态: 已停用（Deactivated） | - | 尚未明确 |
| 重新激活组织（Reactivate Organization） | 平台管理员（Platform Admin） | 重新激活组织（Reactivate Organization） | 组织已重新激活（Organization Reactivated）, 状态: 已激活（Active） | - | 尚未明确 |
| 组织目录（Organization Directory） | 系统 | - | 尚未明确 | 组织目录（Organization Directory） | 尚未明确 |
| 将用户账户绑定到组织（Bind User Account To Organization） | 平台管理员（Platform Admin） | 将用户账户绑定到组织（Bind User Account To Organization） | 已绑定组织的用户账户（User Account Bound To Organization）, 状态: 已激活（Active） | - | 拒绝重复的用户组织绑定（Reject Duplicate User Organization Binding） |
| 用户组织成员资格目录（User Organization Membership Directory） | 系统 | - | 尚未明确 | 用户组织成员资格目录（User Organization Membership Directory） | 尚未明确 |

<!-- em:section id="software.slice.注册组织（RegisterOrganization）" source="domain/FederationLearningPlatform/context/OrganizationManagement/slice/RegisterOrganization" -->
##### 2.1.2.1 注册组织（Register Organization）/EMW-SD-SECTION-2

- 归属: 组织（Organization）.
- 角色/触发条件: 组织管理员（Organization Admin）.
- 输入: 注册组织（Register Organization）.
- 业务结果: 组织已注册（Organization Registered）, 状态: 已注册（Registered）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 组织名称唯一（Organization Name Unique） / 拒绝重复组织（Reject Duplicate Organization） - 当 注册组织（Register Organization）, 则 拒绝: 组织已存在.

<!-- em:section id="software.slice.激活组织（ActivateOrganization）" source="domain/FederationLearningPlatform/context/OrganizationManagement/slice/ActivateOrganization" -->
##### 2.1.2.2 激活组织（Activate Organization）/EMW-SD-SECTION-3

- 归属: 组织（Organization）.
- 角色/触发条件: 平台管理员（Platform Admin）.
- 输入: 激活组织（Activate Organization）.
- 业务结果: 组织已激活（Organization Activated）, 状态: 已激活（Active）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.停用组织（DeactivateOrganization）" source="domain/FederationLearningPlatform/context/OrganizationManagement/slice/DeactivateOrganization" -->
##### 2.1.2.3 停用组织（Deactivate Organization）/EMW-SD-SECTION-4

- 归属: 组织（Organization）.
- 角色/触发条件: 平台管理员（Platform Admin）.
- 输入: 停用组织（Deactivate Organization）.
- 业务结果: 组织已停用（Organization Deactivated）, 状态: 已停用（Deactivated）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.重新激活组织（ReactivateOrganization）" source="domain/FederationLearningPlatform/context/OrganizationManagement/slice/ReactivateOrganization" -->
##### 2.1.2.4 重新激活组织（Reactivate Organization）/EMW-SD-SECTION-5

- 归属: 组织（Organization）.
- 角色/触发条件: 平台管理员（Platform Admin）.
- 输入: 重新激活组织（Reactivate Organization）.
- 业务结果: 组织已重新激活（Organization Reactivated）, 状态: 已激活（Active）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.组织目录（OrganizationDirectory）" source="domain/FederationLearningPlatform/context/OrganizationManagement/slice/OrganizationDirectory" -->
##### 2.1.2.5 组织目录（Organization Directory）/EMW-SD-SECTION-6

- 归属: 组织（Organization）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 组织目录（Organization Directory）.

<!-- em:section id="software.slice.将用户账户绑定到组织（BindUserAccountToOrganization）" source="domain/FederationLearningPlatform/context/OrganizationManagement/slice/BindUserAccountToOrganization" -->
##### 2.1.2.6 将用户账户绑定到组织（Bind User Account To Organization）/EMW-SD-SECTION-7

- 归属: 用户组织成员资格（User Organization Membership）.
- 角色/触发条件: 平台管理员（Platform Admin）.
- 输入: 将用户账户绑定到组织（Bind User Account To Organization）.
- 业务结果: 已绑定组织的用户账户（User Account Bound To Organization）, 状态: 已激活（Active）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 用户账户组织绑定唯一（User Account Organization Binding Unique） / 拒绝重复的用户组织绑定（Reject Duplicate User Organization Binding） - 当 将用户账户绑定到组织（Bind User Account To Organization）, 则 拒绝: 用户账户已绑定到此组织。.

<!-- em:section id="software.slice.用户组织成员资格目录（UserOrganizationMembershipDirectory）" source="domain/FederationLearningPlatform/context/OrganizationManagement/slice/UserOrganizationMembershipDirectory" -->
##### 2.1.2.7 用户组织成员资格目录（User Organization Membership Directory）/EMW-SD-SECTION-8

- 归属: 用户组织成员资格（User Organization Membership）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 用户组织成员资格目录（User Organization Membership Directory）.

#### 2.1.3 上下文数据设计/EMW-SD-CDD

| Read Model | 形态 | 来源事件 | 关键字段 | 查询字段 |
| --- | --- | --- | --- | --- |
| 组织目录（Organization Directory） | 集合/列表 | 组织已注册（Organization Registered）, 组织已激活（Organization Activated）, 组织已停用（Organization Deactivated）, 组织已重新激活（Organization Reactivated）, 数据集已批准用于训练（Dataset Approved For Training） | organizationId | 尚未明确 |
| 用户组织成员资格目录（User Organization Membership Directory） | 集合/列表 | 已绑定组织的用户账户（User Account Bound To Organization）, 用户账户已注册（User Account Registered）, 组织已注册（Organization Registered） | userOrganizationMembershipId | 尚未明确 |

#### 2.1.4 值类型设计/EMW-SD-VTD

| 值类型 | 类型 | 基础类型 | 约束/字段 |
| --- | --- | --- | --- |
| Organization Type | enum | String | HOSPITAL, RESEARCH_INSTITUTE, PUBLIC_HEALTH_AGENCY, LABORATORY, REHABILITATION_CENTER |

<!-- em:section id="software.context.联邦管理（FederationManagement）" source="domain/FederationLearningPlatform/context/FederationManagement" -->
### 2.2 联邦管理（Federation Management）/EMW-SD-SECTION-9

上下文职责: 联邦管理负责联邦及联邦成员资格治理。

#### 2.2.1 业务对象设计/EMW-SD-BOD-2

| 对象 | 类型 | 生命周期 | 业务能力 |
| --- | --- | --- | --- |
| 联邦（Federation） | 概念 | 草稿（Draft） -> 已激活（Active） -> 已暂停（Suspended） | 创建联邦（Create Federation）, 激活联邦（Activate Federation）, 暂停联邦（Suspend Federation）, 重新激活联邦（Reactivate Federation）, 联邦概览（Federation Overview） |
| 联邦成员资格（Federation Membership） | 概念 | 已邀请（Invited） -> 已激活（Active） -> 已拒绝（Rejected） -> 邀请已撤销（Invitation Revoked） -> 已暂停（Suspended） -> 已移除（Removed） | 邀请参与者（Invite Participant）, 批准参与者（Approve Participant）, 拒绝参与者（Reject Participant）, 撤销参与者邀请（Revoke Participant Invitation）, 暂停参与者（Suspend Participant）, 移除参与者（Remove Participant）, 联邦成员资格目录（Federation Membership Directory） |

#### 2.2.2 应用服务与能力设计/EMW-SD-ASCD-2

| 业务能力 | 角色/触发条件 | 输入 | 业务结果 | 读侧结果 | 规则 |
| --- | --- | --- | --- | --- | --- |
| 创建联邦（Create Federation） | 平台管理员（Platform Admin） | 创建联邦（Create Federation） | 联邦已创建（Federation Created）, 状态: 草稿（Draft） | - | 拒绝重复联邦（Reject Duplicate Federation） |
| 激活联邦（Activate Federation） | 平台管理员（Platform Admin） | 激活联邦（Activate Federation） | 联邦已激活（Federation Activated）, 状态: 已激活（Active） | - | 尚未明确 |
| 暂停联邦（Suspend Federation） | 平台管理员（Platform Admin） | 暂停联邦（Suspend Federation） | 联邦已暂停（Federation Suspended）, 状态: 已暂停（Suspended） | - | 尚未明确 |
| 重新激活联邦（Reactivate Federation） | 平台管理员（Platform Admin） | 重新激活联邦（Reactivate Federation） | 联邦已重新激活（Federation Reactivated）, 状态: 已激活（Active） | - | 尚未明确 |
| 联邦概览（Federation Overview） | 系统 | - | 尚未明确 | 联邦概览（Federation Overview） | 尚未明确 |
| 邀请参与者（Invite Participant） | 联邦所有者（Federation Owner） | 邀请参与者（Invite Participant） | 参与者已邀请（Participant Invited）, 状态: 已邀请（Invited） | - | 尚未明确 |
| 联邦成员资格目录（Federation Membership Directory） | 系统 | - | 尚未明确 | 联邦成员资格目录（Federation Membership Directory） | 尚未明确 |
| 批准参与者（Approve Participant） | 治理审核员（Governance Reviewer） | 批准参与者（Approve Participant） | 参与者已加入（Participant Joined）, 状态: 已激活（Active） | - | 批准已邀请的参与者（Approve Invited Participant）, 拒绝未邀请参与者的审批（Reject Uninvited Participant Approval） |
| 拒绝参与者（Reject Participant） | 治理审核员（Governance Reviewer） | 拒绝参与者（Reject Participant） | 参与者已拒绝（Participant Rejected）, 状态: 已拒绝（Rejected） | - | 尚未明确 |
| 撤销参与者邀请（Revoke Participant Invitation） | 联邦所有者（Federation Owner） | 撤销参与者邀请（Revoke Participant Invitation） | 参与者邀请已撤销（Participant Invitation Revoked）, 状态: 邀请已撤销（Invitation Revoked） | - | 尚未明确 |
| 暂停参与者（Suspend Participant） | 治理审核员（Governance Reviewer） | 暂停参与者（Suspend Participant） | 参与者已暂停（Participant Suspended）, 状态: 已暂停（Suspended） | - | 尚未明确 |
| 移除参与者（Remove Participant） | 治理审核员（Governance Reviewer） | 移除参与者（Remove Participant） | 参与者已移除（Participant Removed）, 状态: 已移除（Removed） | - | 尚未明确 |

<!-- em:section id="software.slice.创建联邦（CreateFederation）" source="domain/FederationLearningPlatform/context/FederationManagement/slice/CreateFederation" -->
##### 2.2.2.1 创建联邦（Create Federation）/EMW-SD-SECTION-10

- 归属: 联邦（Federation）.
- 角色/触发条件: 平台管理员（Platform Admin）.
- 输入: 创建联邦（Create Federation）.
- 业务结果: 联邦已创建（Federation Created）, 状态: 草稿（Draft）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 联邦名称唯一（Federation Name Unique） / 拒绝重复联邦（Reject Duplicate Federation） - 当 创建联邦（Create Federation）, 则 拒绝: 联邦已存在.

<!-- em:section id="software.slice.激活联邦（ActivateFederation）" source="domain/FederationLearningPlatform/context/FederationManagement/slice/ActivateFederation" -->
##### 2.2.2.2 激活联邦（Activate Federation）/EMW-SD-SECTION-11

- 归属: 联邦（Federation）.
- 角色/触发条件: 平台管理员（Platform Admin）.
- 输入: 激活联邦（Activate Federation）.
- 业务结果: 联邦已激活（Federation Activated）, 状态: 已激活（Active）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.暂停联邦（SuspendFederation）" source="domain/FederationLearningPlatform/context/FederationManagement/slice/SuspendFederation" -->
##### 2.2.2.3 暂停联邦（Suspend Federation）/EMW-SD-SECTION-12

- 归属: 联邦（Federation）.
- 角色/触发条件: 平台管理员（Platform Admin）.
- 输入: 暂停联邦（Suspend Federation）.
- 业务结果: 联邦已暂停（Federation Suspended）, 状态: 已暂停（Suspended）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.重新激活联邦（ReactivateFederation）" source="domain/FederationLearningPlatform/context/FederationManagement/slice/ReactivateFederation" -->
##### 2.2.2.4 重新激活联邦（Reactivate Federation）/EMW-SD-SECTION-13

- 归属: 联邦（Federation）.
- 角色/触发条件: 平台管理员（Platform Admin）.
- 输入: 重新激活联邦（Reactivate Federation）.
- 业务结果: 联邦已重新激活（Federation Reactivated）, 状态: 已激活（Active）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.联邦概览（FederationOverview）" source="domain/FederationLearningPlatform/context/FederationManagement/slice/FederationOverview" -->
##### 2.2.2.5 联邦概览（Federation Overview）/EMW-SD-SECTION-14

- 归属: 联邦（Federation）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 联邦概览（Federation Overview）.

<!-- em:section id="software.slice.邀请参与者（InviteParticipant）" source="domain/FederationLearningPlatform/context/FederationManagement/slice/InviteParticipant" -->
##### 2.2.2.6 邀请参与者（Invite Participant）/EMW-SD-SECTION-15

- 归属: 联邦成员资格（Federation Membership）.
- 角色/触发条件: 联邦所有者（Federation Owner）.
- 输入: 邀请参与者（Invite Participant）.
- 业务结果: 参与者已邀请（Participant Invited）, 状态: 已邀请（Invited）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.联邦成员资格目录（FederationMembershipDirectory）" source="domain/FederationLearningPlatform/context/FederationManagement/slice/FederationMembershipDirectory" -->
##### 2.2.2.7 联邦成员资格目录（Federation Membership Directory）/EMW-SD-SECTION-16

- 归属: 联邦成员资格（Federation Membership）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 联邦成员资格目录（Federation Membership Directory）.

<!-- em:section id="software.slice.批准参与者（ApproveParticipant）" source="domain/FederationLearningPlatform/context/FederationManagement/slice/ApproveParticipant" -->
##### 2.2.2.8 批准参与者（Approve Participant）/EMW-SD-SECTION-17

- 归属: 联邦成员资格（Federation Membership）.
- 角色/触发条件: 治理审核员（Governance Reviewer）.
- 输入: 批准参与者（Approve Participant）.
- 业务结果: 参与者已加入（Participant Joined）, 状态: 已激活（Active）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 仅已邀请的激活组织加入联邦（Only Invited Active Organizations Join Federation） / 批准已邀请的参与者（Approve Invited Participant） - 当 批准参与者（Approve Participant）, 则 参与者已加入（Participant Joined）.
- 仅已邀请的激活组织加入联邦（Only Invited Active Organizations Join Federation） / 拒绝未邀请参与者的审批（Reject Uninvited Participant Approval） - 当 批准参与者（Approve Participant）, 则 拒绝: 需要参与者邀请.

<!-- em:section id="software.slice.拒绝参与者（RejectParticipant）" source="domain/FederationLearningPlatform/context/FederationManagement/slice/RejectParticipant" -->
##### 2.2.2.9 拒绝参与者（Reject Participant）/EMW-SD-SECTION-18

- 归属: 联邦成员资格（Federation Membership）.
- 角色/触发条件: 治理审核员（Governance Reviewer）.
- 输入: 拒绝参与者（Reject Participant）.
- 业务结果: 参与者已拒绝（Participant Rejected）, 状态: 已拒绝（Rejected）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.撤销参与者邀请（RevokeParticipantInvitation）" source="domain/FederationLearningPlatform/context/FederationManagement/slice/RevokeParticipantInvitation" -->
##### 2.2.2.10 撤销参与者邀请（Revoke Participant Invitation）/EMW-SD-SECTION-19

- 归属: 联邦成员资格（Federation Membership）.
- 角色/触发条件: 联邦所有者（Federation Owner）.
- 输入: 撤销参与者邀请（Revoke Participant Invitation）.
- 业务结果: 参与者邀请已撤销（Participant Invitation Revoked）, 状态: 邀请已撤销（Invitation Revoked）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.暂停参与者（SuspendParticipant）" source="domain/FederationLearningPlatform/context/FederationManagement/slice/SuspendParticipant" -->
##### 2.2.2.11 暂停参与者（Suspend Participant）/EMW-SD-SECTION-20

- 归属: 联邦成员资格（Federation Membership）.
- 角色/触发条件: 治理审核员（Governance Reviewer）.
- 输入: 暂停参与者（Suspend Participant）.
- 业务结果: 参与者已暂停（Participant Suspended）, 状态: 已暂停（Suspended）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.移除参与者（RemoveParticipant）" source="domain/FederationLearningPlatform/context/FederationManagement/slice/RemoveParticipant" -->
##### 2.2.2.12 移除参与者（Remove Participant）/EMW-SD-SECTION-21

- 归属: 联邦成员资格（Federation Membership）.
- 角色/触发条件: 治理审核员（Governance Reviewer）.
- 输入: 移除参与者（Remove Participant）.
- 业务结果: 参与者已移除（Participant Removed）, 状态: 已移除（Removed）.
- 读侧结果: 尚未明确.

#### 2.2.3 上下文数据设计/EMW-SD-CDD-2

| Read Model | 形态 | 来源事件 | 关键字段 | 查询字段 |
| --- | --- | --- | --- | --- |
| 联邦概览（Federation Overview） | 集合/列表 | 联邦已创建（Federation Created）, 联邦已激活（Federation Activated）, 联邦已暂停（Federation Suspended）, 联邦已重新激活（Federation Reactivated）, 参与者已邀请（Participant Invited）, 参与者已加入（Participant Joined）, 参与者已拒绝（Participant Rejected）, 参与者邀请已撤销（Participant Invitation Revoked）, 参与者已暂停（Participant Suspended）, 参与者已移除（Participant Removed）, 运行时身份已激活（Runtime Identity Activated）, 运行时身份已撤销（Runtime Identity Revoked）, 训练作业已提交（Training Job Submitted） | federationId | 尚未明确 |
| 联邦成员资格目录（Federation Membership Directory） | 集合/列表 | 联邦已创建（Federation Created）, 参与者已邀请（Participant Invited）, 参与者已加入（Participant Joined）, 参与者已拒绝（Participant Rejected）, 参与者邀请已撤销（Participant Invitation Revoked）, 参与者已暂停（Participant Suspended）, 参与者已移除（Participant Removed）, 组织已注册（Organization Registered）, 组织已激活（Organization Activated） | federationId, organizationId | 尚未明确 |

<!-- em:section id="software.context.字典维护（DictionaryMaintenance）" source="domain/FederationLearningPlatform/context/DictionaryMaintenance" -->
### 2.3 字典维护（Dictionary Maintenance）/EMW-SD-SECTION-22

上下文职责: 字典维护负责管理平台上下文使用的动态选项字典。业务上下文存储 valueCode 并通过 dictionaryCode 查询 DictionaryValueCatalog 以进行显示和选择。

#### 2.3.1 业务对象设计/EMW-SD-BOD-3

| 对象 | 类型 | 生命周期 | 业务能力 |
| --- | --- | --- | --- |
| 字典（Dictionary） | 概念 | 已注册（Registered） -> 已归档（Archived） | 注册字典（Register Dictionary）, 更新字典（Update Dictionary）, 归档字典（Archive Dictionary）, 字典目录（Dictionary Catalog） |
| 字典值（Dictionary Value） | 概念 | 已激活（Active） -> 已停用（Disabled） | 添加字典值（Add Dictionary Value）, 停用字典值（Disable Dictionary Value）, 启用字典值（Enable Dictionary Value）, 字典值目录（Dictionary Value Catalog） |

#### 2.3.2 应用服务与能力设计/EMW-SD-ASCD-3

| 业务能力 | 角色/触发条件 | 输入 | 业务结果 | 读侧结果 | 规则 |
| --- | --- | --- | --- | --- | --- |
| 注册字典（Register Dictionary） | 平台管理员（Platform Admin） | 注册字典（Register Dictionary） | 字典已注册（Dictionary Registered）, 状态: 已注册（Registered） | - | 拒绝重复字典（Reject Duplicate Dictionary） |
| 更新字典（Update Dictionary） | 平台管理员（Platform Admin） | 更新字典（Update Dictionary） | 字典已更新（Dictionary Updated） | - | 尚未明确 |
| 归档字典（Archive Dictionary） | 平台管理员（Platform Admin） | 归档字典（Archive Dictionary） | 字典已归档（Dictionary Archived）, 状态: 已归档（Archived） | - | 尚未明确 |
| 字典目录（Dictionary Catalog） | 系统 | - | 尚未明确 | 字典目录（Dictionary Catalog） | 尚未明确 |
| 添加字典值（Add Dictionary Value） | 平台管理员（Platform Admin） | 添加字典值（Add Dictionary Value） | 字典值已添加（Dictionary Value Added）, 状态: 已激活（Active） | - | 尚未明确 |
| 停用字典值（Disable Dictionary Value） | 平台管理员（Platform Admin） | 停用字典值（Disable Dictionary Value） | 字典值已停用（Dictionary Value Disabled）, 状态: 已停用（Disabled） | - | 尚未明确 |
| 启用字典值（Enable Dictionary Value） | 平台管理员（Platform Admin） | 启用字典值（Enable Dictionary Value） | 字典值已启用（Dictionary Value Enabled）, 状态: 已激活（Active） | - | 尚未明确 |
| 字典值目录（Dictionary Value Catalog） | 系统 | - | 尚未明确 | 字典值目录（Dictionary Value Catalog） | 尚未明确 |

<!-- em:section id="software.slice.注册字典（RegisterDictionary）" source="domain/FederationLearningPlatform/context/DictionaryMaintenance/slice/RegisterDictionary" -->
##### 2.3.2.1 注册字典（Register Dictionary）/EMW-SD-SECTION-23

- 归属: 字典（Dictionary）.
- 角色/触发条件: 平台管理员（Platform Admin）.
- 输入: 注册字典（Register Dictionary）.
- 业务结果: 字典已注册（Dictionary Registered）, 状态: 已注册（Registered）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 字典代码唯一（Dictionary Code Unique） / 拒绝重复字典（Reject Duplicate Dictionary） - 当 注册字典（Register Dictionary）, 则 拒绝: 字典已存在.

<!-- em:section id="software.slice.更新字典（UpdateDictionary）" source="domain/FederationLearningPlatform/context/DictionaryMaintenance/slice/UpdateDictionary" -->
##### 2.3.2.2 更新字典（Update Dictionary）/EMW-SD-SECTION-24

- 归属: 字典（Dictionary）.
- 角色/触发条件: 平台管理员（Platform Admin）.
- 输入: 更新字典（Update Dictionary）.
- 业务结果: 字典已更新（Dictionary Updated）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.归档字典（ArchiveDictionary）" source="domain/FederationLearningPlatform/context/DictionaryMaintenance/slice/ArchiveDictionary" -->
##### 2.3.2.3 归档字典（Archive Dictionary）/EMW-SD-SECTION-25

- 归属: 字典（Dictionary）.
- 角色/触发条件: 平台管理员（Platform Admin）.
- 输入: 归档字典（Archive Dictionary）.
- 业务结果: 字典已归档（Dictionary Archived）, 状态: 已归档（Archived）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.字典目录（DictionaryCatalog）" source="domain/FederationLearningPlatform/context/DictionaryMaintenance/slice/DictionaryCatalog" -->
##### 2.3.2.4 字典目录（Dictionary Catalog）/EMW-SD-SECTION-26

- 归属: 字典（Dictionary）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 字典目录（Dictionary Catalog）.

<!-- em:section id="software.slice.添加字典值（AddDictionaryValue）" source="domain/FederationLearningPlatform/context/DictionaryMaintenance/slice/AddDictionaryValue" -->
##### 2.3.2.5 添加字典值（Add Dictionary Value）/EMW-SD-SECTION-27

- 归属: 字典值（Dictionary Value）.
- 角色/触发条件: 平台管理员（Platform Admin）.
- 输入: 添加字典值（Add Dictionary Value）.
- 业务结果: 字典值已添加（Dictionary Value Added）, 状态: 已激活（Active）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.停用字典值（DisableDictionaryValue）" source="domain/FederationLearningPlatform/context/DictionaryMaintenance/slice/DisableDictionaryValue" -->
##### 2.3.2.6 停用字典值（Disable Dictionary Value）/EMW-SD-SECTION-28

- 归属: 字典值（Dictionary Value）.
- 角色/触发条件: 平台管理员（Platform Admin）.
- 输入: 停用字典值（Disable Dictionary Value）.
- 业务结果: 字典值已停用（Dictionary Value Disabled）, 状态: 已停用（Disabled）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.启用字典值（EnableDictionaryValue）" source="domain/FederationLearningPlatform/context/DictionaryMaintenance/slice/EnableDictionaryValue" -->
##### 2.3.2.7 启用字典值（Enable Dictionary Value）/EMW-SD-SECTION-29

- 归属: 字典值（Dictionary Value）.
- 角色/触发条件: 平台管理员（Platform Admin）.
- 输入: 启用字典值（Enable Dictionary Value）.
- 业务结果: 字典值已启用（Dictionary Value Enabled）, 状态: 已激活（Active）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.字典值目录（DictionaryValueCatalog）" source="domain/FederationLearningPlatform/context/DictionaryMaintenance/slice/DictionaryValueCatalog" -->
##### 2.3.2.8 字典值目录（Dictionary Value Catalog）/EMW-SD-SECTION-30

- 归属: 字典值（Dictionary Value）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 字典值目录（Dictionary Value Catalog）.

#### 2.3.3 上下文数据设计/EMW-SD-CDD-3

| Read Model | 形态 | 来源事件 | 关键字段 | 查询字段 |
| --- | --- | --- | --- | --- |
| 字典目录（Dictionary Catalog） | 集合/列表 | 字典已注册（Dictionary Registered）, 字典已更新（Dictionary Updated）, 字典已归档（Dictionary Archived） | dictionaryId | dictionaryCode |
| 字典值目录（Dictionary Value Catalog） | 集合/列表 | 字典值已添加（Dictionary Value Added）, 字典值已停用（Dictionary Value Disabled）, 字典值已启用（Dictionary Value Enabled） | dictionaryValueId | dictionaryCode, active |

#### 2.3.4 值类型设计/EMW-SD-VTD-2

| 值类型 | 类型 | 基础类型 | 约束/字段 |
| --- | --- | --- | --- |
| Dictionary Code | scalar | String | - |
| Dictionary Value Code | scalar | String | - |
| Display Order | scalar | Int | - |

<!-- em:section id="software.context.文件上传（FileUpload）" source="domain/FederationLearningPlatform/context/FileUpload" -->
### 2.4 文件上传（File Upload）/EMW-SD-SECTION-31

上下文职责: FileUpload 是共享的持久化文件存储边界。它通过可替换的存储适配器存储已上传内容，记录每个已上传文件，公开经过身份验证的内容 URI，并通过 expiresAt 应用可选的保留期。业务域引用已上传文件记录，而不是再创建一个物理存储层。

#### 2.4.1 业务对象设计/EMW-SD-BOD-4

| 对象 | 类型 | 生命周期 | 业务能力 |
| --- | --- | --- | --- |
| 已上传文件（Uploaded File） | 概念 | 可用（Available） -> 已引用（Referenced） -> 已放弃（Discarded） -> 已过期（Expired） | 上传文件（Upload File）, 将文件标记为已引用（Mark File Referenced）, 下载文件（Download File）, 丢弃文件（Discard File）, 使文件过期（Expire File）, 已上传文件目录（Uploaded File Catalog） |

#### 2.4.2 应用服务与能力设计/EMW-SD-ASCD-4

| 业务能力 | 角色/触发条件 | 输入 | 业务结果 | 读侧结果 | 规则 |
| --- | --- | --- | --- | --- | --- |
| 上传文件（Upload File） | 平台用户（Platform User） | 上传文件（Upload File） | 文件已上传（File Uploaded）, 状态: 可用（Available） | - | 为模型导入上传文件（Upload File For Model Import） |
| 将文件标记为已引用（Mark File Referenced） | 平台服务（Platform Service） | 将文件标记为已引用（Mark File Referenced） | 文件已引用（File Referenced）, 状态: 已引用（Referenced） | - | 通过模型导入标记文件已引用（Mark File Referenced By Model Import） |
| 下载文件（Download File） | 平台用户（Platform User） | 下载文件（Download File） | 文件下载已授权（File Download Authorized） | - | 授权可用文件下载（Authorize Available File Download）, 拒绝过期文件下载（Reject Expired File Download） |
| 丢弃文件（Discard File） | 平台用户（Platform User） | 丢弃文件（Discard File） | 文件已丢弃（File Discarded）, 状态: 已放弃（Discarded） | - | 丢弃未引用文件（Discard Unreferenced File）, 拒绝消费后丢弃（Reject Discard After Consumption） |
| 使文件过期（Expire File） | 保留期过后使文件过期（Expire File When Retention Elapsed） | 使文件过期（Expire File） | 文件已过期（File Expired）, 状态: 已过期（Expired） | - | 使已上传文件过期（Expire Uploaded File）, 过期后拒绝消费（Reject Consumption After Expiration） |
| 已上传文件目录（Uploaded File Catalog） | 系统 | - | 尚未明确 | 已上传文件目录（Uploaded File Catalog） | 尚未明确 |

<!-- em:section id="software.slice.上传文件（UploadFile）" source="domain/FederationLearningPlatform/context/FileUpload/slice/UploadFile" -->
##### 2.4.2.1 上传文件（Upload File）/EMW-SD-SECTION-32

- 归属: 已上传文件（Uploaded File）.
- 角色/触发条件: 平台用户（Platform User）.
- 输入: 上传文件（Upload File）.
- 业务结果: 文件已上传（File Uploaded）, 状态: 可用（Available）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 已上传文件已持久化并已记录（Uploaded File Is Persisted And Recorded） / 为模型导入上传文件（Upload File For Model Import） - 当 上传文件（Upload File）, 则 文件已上传（File Uploaded）.

<!-- em:section id="software.slice.将文件标记为已引用（MarkFileReferenced）" source="domain/FederationLearningPlatform/context/FileUpload/slice/MarkFileReferenced" -->
##### 2.4.2.2 将文件标记为已引用（Mark File Referenced）/EMW-SD-SECTION-33

- 归属: 已上传文件（Uploaded File）.
- 角色/触发条件: 平台服务（Platform Service）.
- 输入: 将文件标记为已引用（Mark File Referenced）.
- 业务结果: 文件已引用（File Referenced）, 状态: 已引用（Referenced）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 被引用的文件受保护以防用户丢弃（Referenced File Is Protected From User Discard） / 通过模型导入标记文件已引用（Mark File Referenced By Model Import） - 当 将文件标记为已引用（Mark File Referenced）, 则 文件已引用（File Referenced）.

<!-- em:section id="software.slice.下载文件（DownloadFile）" source="domain/FederationLearningPlatform/context/FileUpload/slice/DownloadFile" -->
##### 2.4.2.3 下载文件（Download File）/EMW-SD-SECTION-34

- 归属: 已上传文件（Uploaded File）.
- 角色/触发条件: 平台用户（Platform User）.
- 输入: 下载文件（Download File）.
- 业务结果: 文件下载已授权（File Download Authorized）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 仅可用的已存储文件可被下载（Only Available Stored Files Can Be Downloaded） / 授权可用文件下载（Authorize Available File Download） - 当 下载文件（Download File）, 则 文件下载已授权（File Download Authorized）.
- 仅可用的已存储文件可被下载（Only Available Stored Files Can Be Downloaded） / 拒绝过期文件下载（Reject Expired File Download） - 当 下载文件（Download File）, 则 错误: 文件不可下载。.

<!-- em:section id="software.slice.丢弃文件（DiscardFile）" source="domain/FederationLearningPlatform/context/FileUpload/slice/DiscardFile" -->
##### 2.4.2.4 丢弃文件（Discard File）/EMW-SD-SECTION-35

- 归属: 已上传文件（Uploaded File）.
- 角色/触发条件: 平台用户（Platform User）.
- 输入: 丢弃文件（Discard File）.
- 业务结果: 文件已丢弃（File Discarded）, 状态: 已放弃（Discarded）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 仅可丢弃未引用的文件（Only Unreferenced Files Can Be Discarded） / 丢弃未引用文件（Discard Unreferenced File） - 当 丢弃文件（Discard File）, 则 文件已丢弃（File Discarded）.
- 仅可丢弃未引用的文件（Only Unreferenced Files Can Be Discarded） / 拒绝消费后丢弃（Reject Discard After Consumption） - 当 丢弃文件（Discard File）, 则 拒绝: 文件已被引用.

<!-- em:section id="software.slice.使文件过期（ExpireFile）" source="domain/FederationLearningPlatform/context/FileUpload/slice/ExpireFile" -->
##### 2.4.2.5 使文件过期（Expire File）/EMW-SD-SECTION-36

- 归属: 已上传文件（Uploaded File）.
- 角色/触发条件: 保留期过后使文件过期（Expire File When Retention Elapsed）.
- 输入: 使文件过期（Expire File）.
- 业务结果: 文件已过期（File Expired）, 状态: 已过期（Expired）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 过期文件不可被引用（Expired File Cannot Be Referenced） / 使已上传文件过期（Expire Uploaded File） - 当 使文件过期（Expire File）, 则 文件已过期（File Expired）.
- 过期文件不可被引用（Expired File Cannot Be Referenced） / 过期后拒绝消费（Reject Consumption After Expiration） - 当 将文件标记为已引用（Mark File Referenced）, 则 拒绝: 文件已过期.

<!-- em:section id="software.slice.已上传文件目录（UploadedFileCatalog）" source="domain/FederationLearningPlatform/context/FileUpload/slice/UploadedFileCatalog" -->
##### 2.4.2.6 已上传文件目录（Uploaded File Catalog）/EMW-SD-SECTION-37

- 归属: 已上传文件（Uploaded File）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 已上传文件目录（Uploaded File Catalog）.

#### 2.4.3 上下文数据设计/EMW-SD-CDD-4

| Read Model | 形态 | 来源事件 | 关键字段 | 查询字段 |
| --- | --- | --- | --- | --- |
| 已上传文件目录（Uploaded File Catalog） | 集合/列表 | 文件已上传（File Uploaded）, 文件已引用（File Referenced）, 文件已丢弃（File Discarded）, 文件已过期（File Expired） | fileId | purpose |

<!-- em:section id="software.context.运行时预配（RuntimeProvisioning）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning" -->
### 2.5 运行时预配（Runtime Provisioning）/EMW-SD-SECTION-38

上下文职责: 运行时入驻负责运行时安装规划、引导配置、运行时环境注册、代理安装、就绪检查、连接报告及访问撤销。K3s、Kubernetes、Docker Compose 与主机进程运行时均为安装目标，而非独立的领域概念。 运行时引导和代理安装材料是支持配置，而非领域状态：平台后端适配器从部署配置或密钥存储中读取 platform.runtime.bootstrap.publicBaseAddress、platform.runtime.agent.platformApiBaseAddress、platform.runtime.agent.controlApiBaseAddress、platform.runtime.agent.containerRegistry、platform.runtime.agent.trustBundleSecretName 和 platform.runtime.agent.accessGrantSecretPrefix。命令、事件和读模型仅保留 runtimeInstallationPlanId、runtimeInfrastructurePackageId 以及生成的访问授权 ID。

#### 2.5.1 业务对象设计/EMW-SD-BOD-5

| 对象 | 类型 | 生命周期 | 业务能力 |
| --- | --- | --- | --- |
| 运行时安装计划（Runtime Installation Plan） | 概念 | 已规划（Planned） | 创建运行时安装计划（Create Runtime Installation Plan）, 运行时安装计划目录（Runtime Installation Plan Catalog）, 运行时安装指南（Runtime Installation Guide） |
| 运行时基础设施包（Runtime Infrastructure Package） | 概念 | 已注册（Registered） | 注册运行时基础设施包（Register Runtime Infrastructure Package）, 运行时基础设施包目录（Runtime Infrastructure Package Catalog） |
| 运行时基础设施（Runtime Infrastructure） | 概念 | 已规划（Planned） -> 已注册（Registered） -> 已就绪（Prepared） -> 已验证（Verified） -> 验证失败（Verification Failed） -> 代理就绪（Agent Ready） -> 运行时代理失败（Runtime Agent Failed） -> 离线（Offline） -> 已连接（Connected） | 规划运行时基础设施（Plan Runtime Infrastructure）, 注册运行时基础设施（Register Runtime Infrastructure）, 确认运行时基础设施已就绪（Confirm Runtime Infrastructure Prepared）, 验证运行时基础设施（Verify Runtime Infrastructure）, 部署运行时代理（Deploy Runtime Agent）, 重试运行时基础设施验证（Retry Runtime Infrastructure Verification）, 重试运行时代理部署（Retry Runtime Agent Deployment）, 记录运行时连接已建立（Record Runtime Connection Established）, 运行时代理端点目录（Runtime Agent Endpoint Catalog）, 运行时基础设施访问视图（Runtime Infrastructure Access View） |

#### 2.5.2 应用服务与能力设计/EMW-SD-ASCD-5

| 业务能力 | 角色/触发条件 | 输入 | 业务结果 | 读侧结果 | 规则 |
| --- | --- | --- | --- | --- | --- |
| 注册运行时基础设施包（Register Runtime Infrastructure Package） | 平台管理员（Platform Admin） | 注册运行时基础设施包（Register Runtime Infrastructure Package） | 运行时基础设施包已注册（Runtime Infrastructure Package Registered）, 状态: 已注册（Registered） | - | 尚未明确 |
| 运行时基础设施包目录（Runtime Infrastructure Package Catalog） | 系统 | - | 尚未明确 | 运行时基础设施包目录（Runtime Infrastructure Package Catalog） | 尚未明确 |
| 创建运行时安装计划（Create Runtime Installation Plan） | 平台管理员（Platform Admin） | 创建运行时安装计划（Create Runtime Installation Plan） | 运行时安装计划已创建（Runtime Installation Plan Created）, 状态: 已规划（Planned） | - | 尚未明确 |
| 运行时安装计划目录（Runtime Installation Plan Catalog） | 系统 | - | 尚未明确 | 运行时安装计划目录（Runtime Installation Plan Catalog） | 尚未明确 |
| 运行时安装指南（Runtime Installation Guide） | 运行时安装程序（Runtime Installer） | - | 尚未明确 | 运行时安装指南（Runtime Installation Guide） | 尚未明确 |
| 规划运行时基础设施（Plan Runtime Infrastructure） | 平台管理员（Platform Admin） | 规划运行时基础设施（Plan Runtime Infrastructure） | 运行时基础设施已计划（Runtime Infrastructure Planned）, 状态: 已规划（Planned） | - | 尚未明确 |
| 注册运行时基础设施（Register Runtime Infrastructure） | 运行时安装程序（Runtime Installer） | 注册运行时基础设施（Register Runtime Infrastructure） | 运行时基础设施已注册（Runtime Infrastructure Registered）, 状态: 已注册（Registered） | - | 尚未明确 |
| 确认运行时基础设施已就绪（Confirm Runtime Infrastructure Prepared） | 运行时安装程序（Runtime Installer） | 确认运行时基础设施已就绪（Confirm Runtime Infrastructure Prepared） | 运行时基础设施已准备（Runtime Infrastructure Prepared）, 状态: 已就绪（Prepared） | - | 确认运行时基础设施已就绪（Confirm Runtime Infrastructure Prepared） |
| 验证运行时基础设施（Verify Runtime Infrastructure） | 平台管理员（Platform Admin） | 验证运行时基础设施（Verify Runtime Infrastructure） | 运行时基础设施已验证（Runtime Infrastructure Verified）, 运行时基础设施验证失败（Runtime Infrastructure Verification Failed）, 状态: 已验证（Verified） | - | 运行时基础设施验证通过（Runtime Infrastructure Verification Passed）, 运行时基础设施验证失败（Runtime Infrastructure Verification Failed） |
| 部署运行时代理（Deploy Runtime Agent） | 平台管理员（Platform Admin） | 部署运行时代理（Deploy Runtime Agent） | 运行时代理安装成功（Runtime Agent Installation Succeeded）, 运行时代理安装失败（Runtime Agent Installation Failed）, 状态: 代理就绪（Agent Ready） | - | 运行时代理部署成功（Runtime Agent Deployment Succeeded）, 运行时代理部署失败（Runtime Agent Deployment Failed） |
| 重试运行时基础设施验证（Retry Runtime Infrastructure Verification） | 平台管理员（Platform Admin） | 重试运行时基础设施验证（Retry Runtime Infrastructure Verification） | 运行时基础设施验证重试成功（Runtime Infrastructure Verification Retry Succeeded）, 运行时基础设施验证重试失败（Runtime Infrastructure Verification Retry Failed）, 状态: 已验证（Verified） | - | 运行时基础设施验证重试成功（Runtime Infrastructure Verification Retry Succeeded）, 运行时基础设施验证重试失败（Runtime Infrastructure Verification Retry Failed）, 在已验证状态下拒绝运行时基础设施验证重试（Reject Runtime Infrastructure Verification Retry While Verified） |
| 重试运行时代理部署（Retry Runtime Agent Deployment） | 平台管理员（Platform Admin） | 重试运行时代理部署（Retry Runtime Agent Deployment） | 运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）, 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed）, 状态: 代理就绪（Agent Ready） | - | 部署失败后运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded After Failed Deployment）, 离线后运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded After Offline）, 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed）, 已连接时拒绝运行时代理部署重试（Reject Runtime Agent Deployment Retry While Connected） |
| 记录运行时连接已建立（Record Runtime Connection Established） | 系统 | 记录运行时连接已建立（Record Runtime Connection Established） | 运行时连接已建立（Runtime Connection Established）, 状态: 已连接（Connected） | - | 尚未明确 |
| 运行时代理端点目录（Runtime Agent Endpoint Catalog） | 系统 | - | 尚未明确 | 运行时代理端点目录（Runtime Agent Endpoint Catalog） | 尚未明确 |
| 运行时基础设施访问视图（Runtime Infrastructure Access View） | 系统 | - | 尚未明确 | 运行时基础设施访问视图（Runtime Infrastructure Access View） | 尚未明确 |

<!-- em:section id="software.slice.注册运行时基础设施包（RegisterRuntimeInfrastructurePackage）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RegisterRuntimeInfrastructurePackage" -->
##### 2.5.2.1 注册运行时基础设施包（Register Runtime Infrastructure Package）/EMW-SD-SECTION-39

- 归属: 运行时基础设施包（Runtime Infrastructure Package）.
- 角色/触发条件: 平台管理员（Platform Admin）.
- 输入: 注册运行时基础设施包（Register Runtime Infrastructure Package）.
- 业务结果: 运行时基础设施包已注册（Runtime Infrastructure Package Registered）, 状态: 已注册（Registered）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.运行时基础设施包目录（RuntimeInfrastructurePackageCatalog）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RuntimeInfrastructurePackageCatalog" -->
##### 2.5.2.2 运行时基础设施包目录（Runtime Infrastructure Package Catalog）/EMW-SD-SECTION-40

- 归属: 运行时基础设施包（Runtime Infrastructure Package）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 运行时基础设施包目录（Runtime Infrastructure Package Catalog）.

<!-- em:section id="software.slice.创建运行时安装计划（CreateRuntimeInstallationPlan）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/CreateRuntimeInstallationPlan" -->
##### 2.5.2.3 创建运行时安装计划（Create Runtime Installation Plan）/EMW-SD-SECTION-41

- 归属: 运行时安装计划（Runtime Installation Plan）.
- 角色/触发条件: 平台管理员（Platform Admin）.
- 输入: 创建运行时安装计划（Create Runtime Installation Plan）.
- 业务结果: 运行时安装计划已创建（Runtime Installation Plan Created）, 状态: 已规划（Planned）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.运行时安装计划目录（RuntimeInstallationPlanCatalog）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RuntimeInstallationPlanCatalog" -->
##### 2.5.2.4 运行时安装计划目录（Runtime Installation Plan Catalog）/EMW-SD-SECTION-42

- 归属: 运行时安装计划（Runtime Installation Plan）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 运行时安装计划目录（Runtime Installation Plan Catalog）.

<!-- em:section id="software.slice.运行时安装指南（RuntimeInstallationGuide）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RuntimeInstallationGuide" -->
##### 2.5.2.5 运行时安装指南（Runtime Installation Guide）/EMW-SD-SECTION-43

- 归属: 运行时安装计划（Runtime Installation Plan）.
- 角色/触发条件: 运行时安装程序（Runtime Installer）.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 运行时安装指南（Runtime Installation Guide）.

<!-- em:section id="software.slice.规划运行时基础设施（PlanRuntimeInfrastructure）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/PlanRuntimeInfrastructure" -->
##### 2.5.2.6 规划运行时基础设施（Plan Runtime Infrastructure）/EMW-SD-SECTION-44

- 归属: 运行时基础设施（Runtime Infrastructure）.
- 角色/触发条件: 平台管理员（Platform Admin）.
- 输入: 规划运行时基础设施（Plan Runtime Infrastructure）.
- 业务结果: 运行时基础设施已计划（Runtime Infrastructure Planned）, 状态: 已规划（Planned）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.注册运行时基础设施（RegisterRuntimeInfrastructure）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RegisterRuntimeInfrastructure" -->
##### 2.5.2.7 注册运行时基础设施（Register Runtime Infrastructure）/EMW-SD-SECTION-45

- 归属: 运行时基础设施（Runtime Infrastructure）.
- 角色/触发条件: 运行时安装程序（Runtime Installer）.
- 输入: 注册运行时基础设施（Register Runtime Infrastructure）.
- 业务结果: 运行时基础设施已注册（Runtime Infrastructure Registered）, 状态: 已注册（Registered）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.确认运行时基础设施已就绪（ConfirmRuntimeInfrastructurePrepared）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/ConfirmRuntimeInfrastructurePrepared" -->
##### 2.5.2.8 确认运行时基础设施已就绪（Confirm Runtime Infrastructure Prepared）/EMW-SD-SECTION-46

- 归属: 运行时基础设施（Runtime Infrastructure）.
- 角色/触发条件: 运行时安装程序（Runtime Installer）.
- 输入: 确认运行时基础设施已就绪（Confirm Runtime Infrastructure Prepared）.
- 业务结果: 运行时基础设施已准备（Runtime Infrastructure Prepared）, 状态: 已就绪（Prepared）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 验证前运行时基础设施已准备（Runtime Infrastructure Prepared Before Verification） / 确认运行时基础设施已就绪（Confirm Runtime Infrastructure Prepared） - 当 确认运行时基础设施已就绪（Confirm Runtime Infrastructure Prepared）, 则 运行时基础设施已准备（Runtime Infrastructure Prepared）.

<!-- em:section id="software.slice.验证运行时基础设施（VerifyRuntimeInfrastructure）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/VerifyRuntimeInfrastructure" -->
##### 2.5.2.9 验证运行时基础设施（Verify Runtime Infrastructure）/EMW-SD-SECTION-47

- 归属: 运行时基础设施（Runtime Infrastructure）.
- 角色/触发条件: 平台管理员（Platform Admin）.
- 输入: 验证运行时基础设施（Verify Runtime Infrastructure）.
- 业务结果: 运行时基础设施已验证（Runtime Infrastructure Verified）, 运行时基础设施验证失败（Runtime Infrastructure Verification Failed）, 状态: 已验证（Verified）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 运行时基础设施验证结果（Runtime Infrastructure Verification Outcome） / 运行时基础设施验证通过（Runtime Infrastructure Verification Passed） - 当 验证运行时基础设施（Verify Runtime Infrastructure）, 则 运行时基础设施已验证（Runtime Infrastructure Verified）.
- 运行时基础设施验证结果（Runtime Infrastructure Verification Outcome） / 运行时基础设施验证失败（Runtime Infrastructure Verification Failed） - 当 验证运行时基础设施（Verify Runtime Infrastructure）, 则 运行时基础设施验证失败（Runtime Infrastructure Verification Failed）.

<!-- em:section id="software.slice.部署运行时代理（DeployRuntimeAgent）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/DeployRuntimeAgent" -->
##### 2.5.2.10 部署运行时代理（Deploy Runtime Agent）/EMW-SD-SECTION-48

- 归属: 运行时基础设施（Runtime Infrastructure）.
- 角色/触发条件: 平台管理员（Platform Admin）.
- 输入: 部署运行时代理（Deploy Runtime Agent）.
- 业务结果: 运行时代理安装成功（Runtime Agent Installation Succeeded）, 运行时代理安装失败（Runtime Agent Installation Failed）, 状态: 代理就绪（Agent Ready）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 运行时代理部署结果（Runtime Agent Deployment Outcome） / 运行时代理部署成功（Runtime Agent Deployment Succeeded） - 当 部署运行时代理（Deploy Runtime Agent）, 则 运行时代理安装成功（Runtime Agent Installation Succeeded）.
- 运行时代理部署结果（Runtime Agent Deployment Outcome） / 运行时代理部署失败（Runtime Agent Deployment Failed） - 当 部署运行时代理（Deploy Runtime Agent）, 则 运行时代理安装失败（Runtime Agent Installation Failed）.

<!-- em:section id="software.slice.重试运行时基础设施验证（RetryRuntimeInfrastructureVerification）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RetryRuntimeInfrastructureVerification" -->
##### 2.5.2.11 重试运行时基础设施验证（Retry Runtime Infrastructure Verification）/EMW-SD-SECTION-49

- 归属: 运行时基础设施（Runtime Infrastructure）.
- 角色/触发条件: 平台管理员（Platform Admin）.
- 输入: 重试运行时基础设施验证（Retry Runtime Infrastructure Verification）.
- 业务结果: 运行时基础设施验证重试成功（Runtime Infrastructure Verification Retry Succeeded）, 运行时基础设施验证重试失败（Runtime Infrastructure Verification Retry Failed）, 状态: 已验证（Verified）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 运行时基础设施验证重试结果（Runtime Infrastructure Verification Retry Outcome） / 运行时基础设施验证重试成功（Runtime Infrastructure Verification Retry Succeeded） - 当 重试运行时基础设施验证（Retry Runtime Infrastructure Verification）, 则 运行时基础设施验证重试成功（Runtime Infrastructure Verification Retry Succeeded）.
- 运行时基础设施验证重试结果（Runtime Infrastructure Verification Retry Outcome） / 运行时基础设施验证重试失败（Runtime Infrastructure Verification Retry Failed） - 当 重试运行时基础设施验证（Retry Runtime Infrastructure Verification）, 则 运行时基础设施验证重试失败（Runtime Infrastructure Verification Retry Failed）.
- 运行时基础设施验证重试结果（Runtime Infrastructure Verification Retry Outcome） / 在已验证状态下拒绝运行时基础设施验证重试（Reject Runtime Infrastructure Verification Retry While Verified） - 当 重试运行时基础设施验证（Retry Runtime Infrastructure Verification）, 则 拒绝: 不允许运行时基础设施验证重试.

<!-- em:section id="software.slice.重试运行时代理部署（RetryRuntimeAgentDeployment）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RetryRuntimeAgentDeployment" -->
##### 2.5.2.12 重试运行时代理部署（Retry Runtime Agent Deployment）/EMW-SD-SECTION-50

- 归属: 运行时基础设施（Runtime Infrastructure）.
- 角色/触发条件: 平台管理员（Platform Admin）.
- 输入: 重试运行时代理部署（Retry Runtime Agent Deployment）.
- 业务结果: 运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）, 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed）, 状态: 代理就绪（Agent Ready）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 运行时代理部署重试结果（Runtime Agent Deployment Retry Outcome） / 部署失败后运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded After Failed Deployment） - 当 重试运行时代理部署（Retry Runtime Agent Deployment）, 则 运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）.
- 运行时代理部署重试结果（Runtime Agent Deployment Retry Outcome） / 离线后运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded After Offline） - 当 重试运行时代理部署（Retry Runtime Agent Deployment）, 则 运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）.
- 运行时代理部署重试结果（Runtime Agent Deployment Retry Outcome） / 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed） - 当 重试运行时代理部署（Retry Runtime Agent Deployment）, 则 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed）.
- 运行时代理部署重试结果（Runtime Agent Deployment Retry Outcome） / 已连接时拒绝运行时代理部署重试（Reject Runtime Agent Deployment Retry While Connected） - 当 重试运行时代理部署（Retry Runtime Agent Deployment）, 则 拒绝: 不允许运行时代理部署重试.

<!-- em:section id="software.slice.记录运行时连接已建立（RecordRuntimeConnectionEstablished）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RecordRuntimeConnectionEstablished" -->
##### 2.5.2.13 记录运行时连接已建立（Record Runtime Connection Established）/EMW-SD-SECTION-51

- 归属: 运行时基础设施（Runtime Infrastructure）.
- 角色/触发条件: 系统.
- 输入: 记录运行时连接已建立（Record Runtime Connection Established）.
- 业务结果: 运行时连接已建立（Runtime Connection Established）, 状态: 已连接（Connected）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.运行时代理端点目录（RuntimeAgentEndpointCatalog）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RuntimeAgentEndpointCatalog" -->
##### 2.5.2.14 运行时代理端点目录（Runtime Agent Endpoint Catalog）/EMW-SD-SECTION-52

- 归属: 运行时基础设施（Runtime Infrastructure）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 运行时代理端点目录（Runtime Agent Endpoint Catalog）.

<!-- em:section id="software.slice.运行时基础设施访问视图（RuntimeInfrastructureAccessView）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RuntimeInfrastructureAccessView" -->
##### 2.5.2.15 运行时基础设施访问视图（Runtime Infrastructure Access View）/EMW-SD-SECTION-53

- 归属: 运行时基础设施（Runtime Infrastructure）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 运行时基础设施访问视图（Runtime Infrastructure Access View）.

#### 2.5.3 上下文数据设计/EMW-SD-CDD-5

| Read Model | 形态 | 来源事件 | 关键字段 | 查询字段 |
| --- | --- | --- | --- | --- |
| 运行时基础设施包目录（Runtime Infrastructure Package Catalog） | 集合/列表 | 运行时基础设施包已注册（Runtime Infrastructure Package Registered） | runtimeInfrastructurePackageId | 尚未明确 |
| 运行时安装计划目录（Runtime Installation Plan Catalog） | 集合/列表 | 组织已注册（Organization Registered）, 运行时基础设施包已注册（Runtime Infrastructure Package Registered）, 运行时安装计划已创建（Runtime Installation Plan Created）, 运行时基础设施已计划（Runtime Infrastructure Planned）, 运行时基础设施已注册（Runtime Infrastructure Registered）, 运行时基础设施已准备（Runtime Infrastructure Prepared）, 运行时基础设施已验证（Runtime Infrastructure Verified）, 运行时基础设施验证失败（Runtime Infrastructure Verification Failed）, 运行时基础设施验证重试成功（Runtime Infrastructure Verification Retry Succeeded）, 运行时基础设施验证重试失败（Runtime Infrastructure Verification Retry Failed）, 运行时代理安装成功（Runtime Agent Installation Succeeded）, 运行时代理安装失败（Runtime Agent Installation Failed）, 运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）, 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed）, 运行时连接已建立（Runtime Connection Established） | runtimeInstallationPlanId | 尚未明确 |
| 运行时安装指南（Runtime Installation Guide） | 集合/列表 | 组织已注册（Organization Registered）, 运行时基础设施包已注册（Runtime Infrastructure Package Registered）, 运行时安装计划已创建（Runtime Installation Plan Created）, 运行时基础设施已计划（Runtime Infrastructure Planned）, 运行时基础设施已注册（Runtime Infrastructure Registered）, 运行时基础设施已准备（Runtime Infrastructure Prepared）, 运行时基础设施已验证（Runtime Infrastructure Verified）, 运行时基础设施验证失败（Runtime Infrastructure Verification Failed）, 运行时基础设施验证重试成功（Runtime Infrastructure Verification Retry Succeeded）, 运行时基础设施验证重试失败（Runtime Infrastructure Verification Retry Failed） | runtimeInstallationPlanId | 尚未明确 |
| 运行时代理端点目录（Runtime Agent Endpoint Catalog） | 集合/列表 | 运行时连接已建立（Runtime Connection Established）, 运行时身份已激活（Runtime Identity Activated）, 运行时身份已撤销（Runtime Identity Revoked）, 已检测到运行时代理离线（Runtime Agent Offline Detected）, 运行时代理已恢复（Runtime Agent Recovered） | runtimeAgentId | 尚未明确 |
| 运行时基础设施访问视图（Runtime Infrastructure Access View） | 集合/列表 | 组织已注册（Organization Registered）, 运行时基础设施包已注册（Runtime Infrastructure Package Registered）, 运行时安装计划已创建（Runtime Installation Plan Created）, 运行时基础设施已计划（Runtime Infrastructure Planned）, 运行时基础设施已注册（Runtime Infrastructure Registered）, 运行时基础设施已准备（Runtime Infrastructure Prepared）, 运行时基础设施已验证（Runtime Infrastructure Verified）, 运行时基础设施验证失败（Runtime Infrastructure Verification Failed）, 运行时基础设施验证重试成功（Runtime Infrastructure Verification Retry Succeeded）, 运行时基础设施验证重试失败（Runtime Infrastructure Verification Retry Failed）, 运行时代理安装成功（Runtime Agent Installation Succeeded）, 运行时代理安装失败（Runtime Agent Installation Failed）, 运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）, 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed）, 运行时连接已建立（Runtime Connection Established） | runtimeInfrastructureId | 尚未明确 |

<!-- em:section id="software.context.数据集治理（DatasetGovernance）" source="domain/FederationLearningPlatform/context/DatasetGovernance" -->
### 2.6 数据集治理（Dataset Governance）/EMW-SD-SECTION-54

上下文职责: 数据集治理将特征架构、平台记录的运行时数据集元数据、数据集访问验证证据以及训练评估包进行分离。运行时代理在参与者基础设施内拥有数据集声明、数据连接器、本地访问配置文件和凭据。

#### 2.6.1 业务对象设计/EMW-SD-BOD-6

| 对象 | 类型 | 生命周期 | 业务能力 |
| --- | --- | --- | --- |
| 特征架构（Feature Schema） | 概念 | 草稿（Draft） -> 已发布（Published） -> 已弃用（Deprecated） -> 已停用（Retired） | 定义特征架构（Define Feature Schema）, 发布特征架构（Publish Feature Schema）, 弃用特征架构（Deprecate Feature Schema）, 停用特征架构（Retire Feature Schema）, 特征架构目录（Feature Schema Catalog）, 取代特征架构版本（Supersede Feature Schema Version）, 标记当前推荐的特征架构版本（Mark Current Recommended Feature Schema Version）, 当前推荐的特征架构目录（Current Recommended Feature Schema Catalog） |
| 运行时数据集元数据（Runtime Dataset Metadata） | 概念 | 已报告元数据（Metadata Reported） | 记录运行时数据集元数据（Record Runtime Dataset Metadata）, 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata）, 运行时数据集元数据目录（Runtime Dataset Metadata Catalog） |

#### 2.6.2 应用服务与能力设计/EMW-SD-ASCD-6

| 业务能力 | 角色/触发条件 | 输入 | 业务结果 | 读侧结果 | 规则 |
| --- | --- | --- | --- | --- | --- |
| 定义特征架构（Define Feature Schema） | 数据管理员（Data Steward） | 定义特征架构（Define Feature Schema） | 特征架构已定义（Feature Schema Defined）, 状态: 草稿（Draft） | - | 拒绝重复的特征架构版本（Reject Duplicate Feature Schema Version） |
| 发布特征架构（Publish Feature Schema） | 数据管理员（Data Steward） | 发布特征架构（Publish Feature Schema） | 特征架构已发布（Feature Schema Published）, 状态: 已发布（Published） | - | 发布草稿架构（Publish Draft Schema） |
| 弃用特征架构（Deprecate Feature Schema） | 数据管理员（Data Steward） | 弃用特征架构（Deprecate Feature Schema） | 特征架构已弃用（Feature Schema Deprecated）, 状态: 已弃用（Deprecated） | - | 尚未明确 |
| 停用特征架构（Retire Feature Schema） | 数据管理员（Data Steward） | 停用特征架构（Retire Feature Schema） | 特征架构已停用（Feature Schema Retired）, 状态: 已停用（Retired） | - | 尚未明确 |
| 取代特征架构版本（Supersede Feature Schema Version） | 数据管理员（Data Steward） | 取代特征架构版本（Supersede Feature Schema Version） | 特征架构版本已取代（Feature Schema Version Superseded） | - | 取代较旧版本（Supersede Older Version） |
| 标记当前推荐的特征架构版本（Mark Current Recommended Feature Schema Version） | 数据管理员（Data Steward） | 标记当前推荐的特征架构版本（Mark Current Recommended Feature Schema Version） | 已标记当前推荐特征架构版本（Current Recommended Feature Schema Version Marked） | - | 标记新的推荐版本（Mark New Recommended Version） |
| 当前推荐的特征架构目录（Current Recommended Feature Schema Catalog） | 系统 | - | 尚未明确 | 当前推荐的特征架构目录（Current Recommended Feature Schema Catalog） | 尚未明确 |
| 特征架构目录（Feature Schema Catalog） | 系统 | - | 尚未明确 | 特征架构目录（Feature Schema Catalog） | 尚未明确 |
| 记录运行时数据集元数据（Record Runtime Dataset Metadata） | 在代理报告时记录数据集元数据（Record Dataset Metadata When Agent Reported） | 记录运行时数据集元数据（Record Runtime Dataset Metadata） | 数据集元数据已报告（Dataset Metadata Reported）, 状态: 已报告元数据（Metadata Reported） | - | 尚未明确 |
| 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata） | 代理重新分析时记录数据集元数据（Record Dataset Metadata When Agent Reprofiled） | 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata） | 数据集元数据已重新分析（Dataset Metadata Reprofiled）, 状态: 已报告元数据（Metadata Reported） | - | 尚未明确 |
| 运行时数据集元数据目录（Runtime Dataset Metadata Catalog） | 系统 | - | 尚未明确 | 运行时数据集元数据目录（Runtime Dataset Metadata Catalog） | 尚未明确 |

<!-- em:section id="software.slice.定义特征架构（DefineFeatureSchema）" source="domain/FederationLearningPlatform/context/DatasetGovernance/slice/DefineFeatureSchema" -->
##### 2.6.2.1 定义特征架构（Define Feature Schema）/EMW-SD-SECTION-55

- 归属: 特征架构（Feature Schema）.
- 角色/触发条件: 数据管理员（Data Steward）.
- 输入: 定义特征架构（Define Feature Schema）.
- 业务结果: 特征架构已定义（Feature Schema Defined）, 状态: 草稿（Draft）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 特征架构域版本唯一（Feature Schema Domain Version Unique） / 拒绝重复的特征架构版本（Reject Duplicate Feature Schema Version） - 当 定义特征架构（Define Feature Schema）, 则 拒绝: 特征架构版本已存在.

<!-- em:section id="software.slice.发布特征架构（PublishFeatureSchema）" source="domain/FederationLearningPlatform/context/DatasetGovernance/slice/PublishFeatureSchema" -->
##### 2.6.2.2 发布特征架构（Publish Feature Schema）/EMW-SD-SECTION-56

- 归属: 特征架构（Feature Schema）.
- 角色/触发条件: 数据管理员（Data Steward）.
- 输入: 发布特征架构（Publish Feature Schema）.
- 业务结果: 特征架构已发布（Feature Schema Published）, 状态: 已发布（Published）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 仅可发布草稿架构（Only Draft Schemas Can Be Published） / 发布草稿架构（Publish Draft Schema） - 当 发布特征架构（Publish Feature Schema）, 则 特征架构已发布（Feature Schema Published）.

<!-- em:section id="software.slice.弃用特征架构（DeprecateFeatureSchema）" source="domain/FederationLearningPlatform/context/DatasetGovernance/slice/DeprecateFeatureSchema" -->
##### 2.6.2.3 弃用特征架构（Deprecate Feature Schema）/EMW-SD-SECTION-57

- 归属: 特征架构（Feature Schema）.
- 角色/触发条件: 数据管理员（Data Steward）.
- 输入: 弃用特征架构（Deprecate Feature Schema）.
- 业务结果: 特征架构已弃用（Feature Schema Deprecated）, 状态: 已弃用（Deprecated）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.停用特征架构（RetireFeatureSchema）" source="domain/FederationLearningPlatform/context/DatasetGovernance/slice/RetireFeatureSchema" -->
##### 2.6.2.4 停用特征架构（Retire Feature Schema）/EMW-SD-SECTION-58

- 归属: 特征架构（Feature Schema）.
- 角色/触发条件: 数据管理员（Data Steward）.
- 输入: 停用特征架构（Retire Feature Schema）.
- 业务结果: 特征架构已停用（Feature Schema Retired）, 状态: 已停用（Retired）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.取代特征架构版本（SupersedeFeatureSchemaVersion）" source="domain/FederationLearningPlatform/context/DatasetGovernance/slice/SupersedeFeatureSchemaVersion" -->
##### 2.6.2.5 取代特征架构版本（Supersede Feature Schema Version）/EMW-SD-SECTION-59

- 归属: 特征架构（Feature Schema）.
- 角色/触发条件: 数据管理员（Data Steward）.
- 输入: 取代特征架构版本（Supersede Feature Schema Version）.
- 业务结果: 特征架构版本已取代（Feature Schema Version Superseded）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 使用较新版本取代已发布的架构（Supersede Published Schema With Newer Version） / 取代较旧版本（Supersede Older Version） - 当 取代特征架构版本（Supersede Feature Schema Version）, 则 特征架构版本已取代（Feature Schema Version Superseded）.

<!-- em:section id="software.slice.标记当前推荐的特征架构版本（MarkCurrentRecommendedFeatureSchemaVersion）" source="domain/FederationLearningPlatform/context/DatasetGovernance/slice/MarkCurrentRecommendedFeatureSchemaVersion" -->
##### 2.6.2.6 标记当前推荐的特征架构版本（Mark Current Recommended Feature Schema Version）/EMW-SD-SECTION-60

- 归属: 特征架构（Feature Schema）.
- 角色/触发条件: 数据管理员（Data Steward）.
- 输入: 标记当前推荐的特征架构版本（Mark Current Recommended Feature Schema Version）.
- 业务结果: 已标记当前推荐特征架构版本（Current Recommended Feature Schema Version Marked）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 每个域仅一个推荐版本（Only One Recommended Version Per Domain） / 标记新的推荐版本（Mark New Recommended Version） - 当 标记当前推荐的特征架构版本（Mark Current Recommended Feature Schema Version）, 则 已标记当前推荐特征架构版本（Current Recommended Feature Schema Version Marked）.

<!-- em:section id="software.slice.当前推荐的特征架构目录（CurrentRecommendedFeatureSchemaCatalog）" source="domain/FederationLearningPlatform/context/DatasetGovernance/slice/CurrentRecommendedFeatureSchemaCatalog" -->
##### 2.6.2.7 当前推荐的特征架构目录（Current Recommended Feature Schema Catalog）/EMW-SD-SECTION-61

- 归属: 特征架构（Feature Schema）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 当前推荐的特征架构目录（Current Recommended Feature Schema Catalog）.

<!-- em:section id="software.slice.特征架构目录（FeatureSchemaCatalog）" source="domain/FederationLearningPlatform/context/DatasetGovernance/slice/FeatureSchemaCatalog" -->
##### 2.6.2.8 特征架构目录（Feature Schema Catalog）/EMW-SD-SECTION-62

- 归属: 特征架构（Feature Schema）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 特征架构目录（Feature Schema Catalog）.

<!-- em:section id="software.slice.记录运行时数据集元数据（RecordRuntimeDatasetMetadata）" source="domain/FederationLearningPlatform/context/DatasetGovernance/slice/RecordRuntimeDatasetMetadata" -->
##### 2.6.2.9 记录运行时数据集元数据（Record Runtime Dataset Metadata）/EMW-SD-SECTION-63

- 归属: 运行时数据集元数据（Runtime Dataset Metadata）.
- 角色/触发条件: 在代理报告时记录数据集元数据（Record Dataset Metadata When Agent Reported）.
- 输入: 记录运行时数据集元数据（Record Runtime Dataset Metadata）.
- 业务结果: 数据集元数据已报告（Dataset Metadata Reported）, 状态: 已报告元数据（Metadata Reported）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.记录运行时数据集重新分析元数据（RecordRuntimeDatasetReprofiledMetadata）" source="domain/FederationLearningPlatform/context/DatasetGovernance/slice/RecordRuntimeDatasetReprofiledMetadata" -->
##### 2.6.2.10 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata）/EMW-SD-SECTION-64

- 归属: 运行时数据集元数据（Runtime Dataset Metadata）.
- 角色/触发条件: 代理重新分析时记录数据集元数据（Record Dataset Metadata When Agent Reprofiled）.
- 输入: 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata）.
- 业务结果: 数据集元数据已重新分析（Dataset Metadata Reprofiled）, 状态: 已报告元数据（Metadata Reported）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.运行时数据集元数据目录（RuntimeDatasetMetadataCatalog）" source="domain/FederationLearningPlatform/context/DatasetGovernance/slice/RuntimeDatasetMetadataCatalog" -->
##### 2.6.2.11 运行时数据集元数据目录（Runtime Dataset Metadata Catalog）/EMW-SD-SECTION-65

- 归属: 运行时数据集元数据（Runtime Dataset Metadata）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 运行时数据集元数据目录（Runtime Dataset Metadata Catalog）.

#### 2.6.3 上下文数据设计/EMW-SD-CDD-6

| Read Model | 形态 | 来源事件 | 关键字段 | 查询字段 |
| --- | --- | --- | --- | --- |
| 当前推荐的特征架构目录（Current Recommended Feature Schema Catalog） | 集合/列表 | 已标记当前推荐特征架构版本（Current Recommended Feature Schema Version Marked） | featureDomain | 尚未明确 |
| 特征架构目录（Feature Schema Catalog） | 集合/列表 | 特征架构已定义（Feature Schema Defined）, 特征架构已发布（Feature Schema Published）, 特征架构已弃用（Feature Schema Deprecated）, 特征架构已停用（Feature Schema Retired）, 特征架构版本已取代（Feature Schema Version Superseded）, 已标记当前推荐特征架构版本（Current Recommended Feature Schema Version Marked） | featureSchemaId | 尚未明确 |
| 运行时数据集元数据目录（Runtime Dataset Metadata Catalog） | 集合/列表 | 组织已注册（Organization Registered）, 特征架构已定义（Feature Schema Defined）, 数据集元数据已报告（Dataset Metadata Reported）, 数据集元数据已重新分析（Dataset Metadata Reprofiled）, 代理数据集分析失败（Agent Dataset Profiling Failed）, 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed） | runtimeDatasetBindingId | 尚未明确 |

#### 2.6.4 值类型设计/EMW-SD-VTD-3

| 值类型 | 类型 | 基础类型 | 约束/字段 |
| --- | --- | --- | --- |
| Feature Definition | object | FeatureDefinition | featureName: String, dataType: String, required: Boolean, nullable: Boolean, description: String, validationRules: String, defaultValue: String, isIdentifier: Boolean, isSensitive: Boolean, encodingStrategy: String, featureTags: String |
| Label Definition | object | LabelDefinition | labelName: String, dataType: String, cardinality: Int, classLabels: String, isMultilabel: Boolean, description: String, validationRules: String, defaultValue: String |

<!-- em:section id="software.context.模型仓库（ModelRepository）" source="domain/FederationLearningPlatform/context/ModelRepository" -->
### 2.7 模型仓库（Model Repository）/EMW-SD-SECTION-66

上下文职责: 模型仓库拥有模型标识、版本控制、血缘关系以及训练编排和运行时代理所使用的可拉取工件引用。FileUpload 拥有底层字节和存储适配器；ModelArtifact 拥有其特定于模型的生命周期和元数据。

#### 2.7.1 业务对象设计/EMW-SD-BOD-7

| 对象 | 类型 | 生命周期 | 业务能力 |
| --- | --- | --- | --- |
| 模型制品（Model Artifact） | 概念 | 已注册（Registered） | 注册模型制品（Register Model Artifact）, 注册联邦模型工件（Register Federated Model Artifact）, 下载模型工件（Download Model Artifact）, 模型制品目录（Model Artifact Catalog） |

#### 2.7.2 应用服务与能力设计/EMW-SD-ASCD-7

| 业务能力 | 角色/触发条件 | 输入 | 业务结果 | 读侧结果 | 规则 |
| --- | --- | --- | --- | --- | --- |
| 注册模型制品（Register Model Artifact） | MLOps 工程师（MLOps Engineer） | 注册模型制品（Register Model Artifact） | 模型工件已注册（Model Artifact Registered）, 状态: 已注册（Registered） | - | 注册可拉取模型工件（Register Pullable Model Artifact） |
| 模型制品目录（Model Artifact Catalog） | 系统 | - | 尚未明确 | 模型制品目录（Model Artifact Catalog） | 尚未明确 |
| 下载模型工件（Download Model Artifact） | MLOps 工程师（MLOps Engineer） | 下载模型工件（Download Model Artifact） | 模型工件下载已授权（Model Artifact Download Authorized） | - | 授权已注册模型工件下载（Authorize Registered Model Artifact Download）, 拒绝缺失的模型工件内容（Reject Missing Model Artifact Content） |
| 注册联邦模型工件（Register Federated Model Artifact） | 全局模型更新时注册联邦模型（Register Federated Model When Global Model Updated） | 注册联邦模型工件（Register Federated Model Artifact） | 联邦模型工件已注册（Federated Model Artifact Registered）, 状态: 已注册（Registered） | - | 注册聚合轮次模型（Register Aggregated Round Model） |

<!-- em:section id="software.slice.注册模型制品（RegisterModelArtifact）" source="domain/FederationLearningPlatform/context/ModelRepository/slice/RegisterModelArtifact" -->
##### 2.7.2.1 注册模型制品（Register Model Artifact）/EMW-SD-SECTION-67

- 归属: 模型制品（Model Artifact）.
- 角色/触发条件: MLOps 工程师（MLOps Engineer）.
- 输入: 注册模型制品（Register Model Artifact）.
- 业务结果: 模型工件已注册（Model Artifact Registered）, 状态: 已注册（Registered）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 模型工件必须可拉取且可验证（Model Artifact Must Be Pullable And Verifiable） / 注册可拉取模型工件（Register Pullable Model Artifact） - 当 注册模型制品（Register Model Artifact）, 则 模型工件已注册（Model Artifact Registered）.

<!-- em:section id="software.slice.模型制品目录（ModelArtifactCatalog）" source="domain/FederationLearningPlatform/context/ModelRepository/slice/ModelArtifactCatalog" -->
##### 2.7.2.2 模型制品目录（Model Artifact Catalog）/EMW-SD-SECTION-68

- 归属: 模型制品（Model Artifact）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 模型制品目录（Model Artifact Catalog）.

<!-- em:section id="software.slice.下载模型工件（DownloadModelArtifact）" source="domain/FederationLearningPlatform/context/ModelRepository/slice/DownloadModelArtifact" -->
##### 2.7.2.3 下载模型工件（Download Model Artifact）/EMW-SD-SECTION-69

- 归属: 模型制品（Model Artifact）.
- 角色/触发条件: MLOps 工程师（MLOps Engineer）.
- 输入: 下载模型工件（Download Model Artifact）.
- 业务结果: 模型工件下载已授权（Model Artifact Download Authorized）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 已注册模型工件解析为存储文件下载（Registered Model Artifact Resolves To Stored File Download） / 授权已注册模型工件下载（Authorize Registered Model Artifact Download） - 当 下载模型工件（Download Model Artifact）, 则 模型工件下载已授权（Model Artifact Download Authorized）.
- 已注册模型工件解析为存储文件下载（Registered Model Artifact Resolves To Stored File Download） / 拒绝缺失的模型工件内容（Reject Missing Model Artifact Content） - 当 下载模型工件（Download Model Artifact）, 则 错误: 模型工件内容不可下载。.

<!-- em:section id="software.slice.注册联邦模型工件（RegisterFederatedModelArtifact）" source="domain/FederationLearningPlatform/context/ModelRepository/slice/RegisterFederatedModelArtifact" -->
##### 2.7.2.4 注册联邦模型工件（Register Federated Model Artifact）/EMW-SD-SECTION-70

- 归属: 模型制品（Model Artifact）.
- 角色/触发条件: 全局模型更新时注册联邦模型（Register Federated Model When Global Model Updated）.
- 输入: 注册联邦模型工件（Register Federated Model Artifact）.
- 业务结果: 联邦模型工件已注册（Federated Model Artifact Registered）, 状态: 已注册（Registered）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 联邦轮次模型已从受管存储注册（Federated Round Model Is Registered From Managed Storage） / 注册聚合轮次模型（Register Aggregated Round Model） - 当 注册联邦模型工件（Register Federated Model Artifact）, 则 联邦模型工件已注册（Federated Model Artifact Registered）.

#### 2.7.3 上下文数据设计/EMW-SD-CDD-7

| Read Model | 形态 | 来源事件 | 关键字段 | 查询字段 |
| --- | --- | --- | --- | --- |
| 模型制品目录（Model Artifact Catalog） | 集合/列表 | 训练作业已创建（Training Job Created）, 模型工件已注册（Model Artifact Registered）, 联邦模型工件已注册（Federated Model Artifact Registered）, 全局模型已更新（Global Model Updated） | modelId | 尚未明确 |

<!-- em:section id="software.context.训练编排（TrainingOrchestration）" source="domain/FederationLearningPlatform/context/TrainingOrchestration" -->
### 2.8 训练编排（Training Orchestration）/EMW-SD-SECTION-71

上下文职责: 训练编排负责作业意图、参与者资格、运行时分发以及每轮分布式训练和评估。

#### 2.8.1 业务对象设计/EMW-SD-BOD-8

| 对象 | 类型 | 生命周期 | 业务能力 |
| --- | --- | --- | --- |
| 运行时引擎配置文件（Runtime Engine Profile） | 概念 | 已注册（Registered） | 注册运行时引擎配置文件（Register Runtime Engine Profile）, 运行时引擎配置文件目录（Runtime Engine Profile Catalog） |
| 训练运行配置（Training Run Configuration） | 概念 | 草稿（Draft） -> 已锁定（Locked） | 定义训练运行配置（Define Training Run Configuration）, 更新训练运行配置（Update Training Run Configuration）, 锁定训练运行配置（Lock Training Run Configuration）, 训练运行配置目录（Training Run Configuration Catalog） |
| 训练作业（Training Job） | 概念 | 草稿（Draft） -> 已提交（Submitted） -> 运行中（Running） -> 已暂停（Paused） -> 已取消（Canceled） -> 已完成（Completed） | 创建训练作业（Create Training Job）, 提交训练作业（Submit Training Job）, 暂停训练作业（Pause Training Job）, 恢复训练作业（Resume Training Job）, 取消训练作业（Cancel Training Job）, 安排下一训练轮次（Schedule Next Training Round）, 完成训练作业（Complete Training Job）, 训练作业仪表板（Training Job Dashboard）, 训练参与者资格（Training Participant Eligibility） |
| 训练轮次（Training Round） | 概念 | 参与者已选择（Participants Selected） -> 运行中（Running） -> 收集更新中（Collecting Updates） -> 聚合中（Aggregating） -> 评估全局模型中（Evaluating Global Model） -> 已完成（Completed） -> 已失败（Failed） | 选择训练轮次参与者（Select Training Round Participants）, 重试训练轮次参与者选择（Retry Training Round Participant Selection）, 开始训练轮次（Start Training Round）, 提交模型更新提交（Submit Model Update Submission）, 评估模型更新提交（Evaluate Model Update Submission）, 聚合明文模型更新（Aggregate Plain Model Updates）, 请求安全聚合（Request Secure Aggregation）, 完成模型聚合（Complete Model Aggregation）, 提交全局模型评估（Submit Global Model Evaluation）, 完成训练轮次（Complete Training Round）, 失败训练轮次（Fail Training Round）, 训练轮次进度（Training Round Progress） |
| 参与者执行计划（Participant Execution Plan） | 概念 | 计划已生成（Plan Generated） -> 计划已分发（Plan Dispatched） | 生成参与者执行计划（Generate Participant Execution Plan）, 分发参与者执行计划（Dispatch Participant Execution Plan） |

#### 2.8.2 应用服务与能力设计/EMW-SD-ASCD-8

| 业务能力 | 角色/触发条件 | 输入 | 业务结果 | 读侧结果 | 规则 |
| --- | --- | --- | --- | --- | --- |
| 注册运行时引擎配置文件（Register Runtime Engine Profile） | 平台管理员（Platform Admin） | 注册运行时引擎配置文件（Register Runtime Engine Profile） | 运行时引擎配置文件已注册（Runtime Engine Profile Registered）, 状态: 已注册（Registered） | - | 注册 Py Torch Vision 运行时引擎配置文件（Register Py Torch Vision Runtime Engine Profile） |
| 运行时引擎配置文件目录（Runtime Engine Profile Catalog） | 系统 | - | 尚未明确 | 运行时引擎配置文件目录（Runtime Engine Profile Catalog） | 尚未明确 |
| 定义训练运行配置（Define Training Run Configuration） | MLOps 工程师（MLOps Engineer） | 定义训练运行配置（Define Training Run Configuration） | 训练运行配置已定义（Training Run Configuration Defined）, 状态: 草稿（Draft） | - | 定义可运行配置（Define Runnable Configuration）, 拒绝空轮次预算（Reject Empty Round Budget） |
| 更新训练运行配置（Update Training Run Configuration） | MLOps 工程师（MLOps Engineer） | 更新训练运行配置（Update Training Run Configuration） | 训练运行配置已更新（Training Run Configuration Updated）, 状态: 草稿（Draft） | - | 更新草稿可运行配置（Update Draft Runnable Configuration）, 配置锁定后拒绝更新（Reject Update After Configuration Locked） |
| 锁定训练运行配置（Lock Training Run Configuration） | 训练提交时锁定配置（Lock Configuration When Training Submitted） | 锁定训练运行配置（Lock Training Run Configuration） | 训练运行配置已锁定（Training Run Configuration Locked）, 状态: 已锁定（Locked） | - | 尚未明确 |
| 训练运行配置目录（Training Run Configuration Catalog） | 系统 | - | 尚未明确 | 训练运行配置目录（Training Run Configuration Catalog） | 尚未明确 |
| 创建训练作业（Create Training Job） | 研究主管（Research Lead） | 创建训练作业（Create Training Job） | 训练作业已创建（Training Job Created）, 状态: 草稿（Draft） | - | 使用可运行配置创建训练作业（Create Training Job With Runnable Configuration）, 拒绝无可运行配置的训练作业（Reject Training Job Without Runnable Configuration） |
| 提交训练作业（Submit Training Job） | 研究主管（Research Lead） | 提交训练作业（Submit Training Job） | 训练作业已提交（Training Job Submitted）, 状态: 已提交（Submitted） | - | 提交已创建的训练作业（Submit Created Training Job）, 拒绝未知训练作业（Reject Unknown Training Job） |
| 训练作业仪表板（Training Job Dashboard） | 系统 | - | 尚未明确 | 训练作业仪表板（Training Job Dashboard） | 尚未明确 |
| 训练参与者资格（Training Participant Eligibility） | 系统 | - | 尚未明确 | 训练参与者资格（Training Participant Eligibility） | 尚未明确 |
| 暂停训练作业（Pause Training Job） | MLOps 工程师（MLOps Engineer） | 暂停训练作业（Pause Training Job） | 训练作业已暂停（Training Job Paused）, 状态: 已暂停（Paused） | - | 尚未明确 |
| 恢复训练作业（Resume Training Job） | MLOps 工程师（MLOps Engineer） | 恢复训练作业（Resume Training Job） | 训练作业已恢复（Training Job Resumed）, 状态: 运行中（Running） | - | 尚未明确 |
| 取消训练作业（Cancel Training Job） | MLOps 工程师（MLOps Engineer） | 取消训练作业（Cancel Training Job） | 训练作业已取消（Training Job Canceled）, 状态: 已取消（Canceled） | - | 尚未明确 |
| 安排下一训练轮次（Schedule Next Training Round） | 当训练应继续时选择下一轮参与者（Select Next Round Participants When Training Should Continue） | - | 尚未明确 | - | 尚未明确 |
| 完成训练作业（Complete Training Job） | 满足训练停止条件时完成作业（Complete Job When Training Stop Criteria Met） | 完成训练作业（Complete Training Job） | 训练作业已完成（Training Job Completed）, 状态: 已完成（Completed） | - | 尚未明确 |
| 选择训练轮次参与者（Select Training Round Participants） | 训练提交时选择初始轮次参与者（Select Initial Round Participants When Training Submitted） | 选择训练轮次参与者（Select Training Round Participants） | 训练轮次参与者已选择（Training Round Participants Selected）, 训练轮次参与者选择失败（Training Round Participant Selection Failed）, 状态: 参与者已选择（Participants Selected） | - | 运行时池达到法定数量时选择参与者（Select Participants When Runtime Pool Reaches Quorum）, 拒绝低于法定数量的参与者快照（Reject Participant Snapshot Below Quorum） |
| 重试训练轮次参与者选择（Retry Training Round Participant Selection） | 训练操作员（Training Operator） | 重试训练轮次参与者选择（Retry Training Round Participant Selection） | 训练轮次参与者选择重试已请求（Training Round Participant Selection Retry Requested） | - | 重试参与者选择（Retry Participant Selection） |
| 开始训练轮次（Start Training Round） | 选择无安全聚合参与者时启动轮次（Start Round When Participants Selected Without Secure Aggregation）, 加密上下文准备就绪时启动轮次（Start Round When Encryption Context Prepared） | 开始训练轮次（Start Training Round） | 训练轮次已启动（Training Round Started）, 训练轮次启动失败（Training Round Start Failed）, 状态: 运行中（Running） | - | 使用已选择的运行时法定人数启动轮次（Start Round With Selected Runtime Quorum）, 记录低于法定人数的失败轮次启动（Record Failed Round Start Below Quorum） |
| 生成参与者执行计划（Generate Participant Execution Plan） | 为已选择的运行时生成计划（Generate Plan For Selected Runtime） | 生成参与者执行计划（Generate Participant Execution Plan） | 参与者执行计划已生成（Participant Execution Plan Generated）, 状态: 计划已生成（Plan Generated） | - | 使用初始模型快照生成初始轮次计划（Generate Initial Round Plan With Initial Model Snapshot）, 使用聚合模型快照生成后续轮次计划（Generate Later Round Plan With Aggregated Model Snapshot） |
| 分发参与者执行计划（Dispatch Participant Execution Plan） | 生成时分发计划（Dispatch Plan When Generated） | 分发参与者执行计划（Dispatch Participant Execution Plan） | 参与者执行计划已分发（Participant Execution Plan Dispatched）, 状态: 计划已分发（Plan Dispatched） | - | 尚未明确 |
| 提交模型更新提交（Submit Model Update Submission） | 运行时代理（Runtime Agent） | 提交模型更新提交（Submit Model Update Submission） | 模型更新提交已接收（Model Update Submission Received）, 状态: 已接收（Received） | - | 尚未明确 |
| 评估模型更新提交（Evaluate Model Update Submission） | 收到时评估模型更新（Evaluate Model Update When Received） | 评估模型更新提交（Evaluate Model Update Submission） | 模型更新提交已接受（Model Update Submission Accepted）, 模型更新提交已拒绝（Model Update Submission Rejected）, 状态: 已评估（Evaluated） | - | 接受正常更新（Accept Normal Update）, 拒绝异常更新（Reject Anomalous Update） |
| 聚合明文模型更新（Aggregate Plain Model Updates） | 达到法定人数时聚合明文更新（Aggregate Plain Updates When Quorum Reached） | 聚合明文模型更新（Aggregate Plain Model Updates） | 明文模型聚合已完成（Plain Model Aggregation Completed）, 状态: 已完成（Completed） | - | 聚合已接受的明文更新（Aggregate Accepted Plain Updates） |
| 请求安全聚合（Request Secure Aggregation） | 当选择参与者时请求聚合（Request Aggregation When Participants Selected） | 请求安全聚合（Request Secure Aggregation） | 已请求安全聚合（Secure Aggregation Requested） | - | 尚未明确 |
| 完成模型聚合（Complete Model Aggregation） | 会话完成时完成安全聚合（Complete Secure Aggregation When Session Completed）, 完成后完成明文聚合（Complete Plain Aggregation When Completed） | 完成模型聚合（Complete Model Aggregation） | 全局模型已更新（Global Model Updated）, 状态: 评估全局模型中（Evaluating Global Model） | - | 尚未明确 |
| 提交全局模型评估（Submit Global Model Evaluation） | 当全局模型更新时提交全局模型评估（Submit Global Model Evaluation When Global Model Updated） | 提交全局模型评估（Submit Global Model Evaluation） | 全局模型评估已提交（Global Model Evaluation Submitted） | - | 尚未明确 |
| 完成训练轮次（Complete Training Round） | 全局模型评估后结束轮次（Finish Round After Global Model Evaluated） | 完成训练轮次（Complete Training Round） | 训练轮次已完成（Training Round Completed）, 状态: 已完成（Completed） | - | 全局评估后完成轮次（Complete Round After Global Evaluation）, 无全局评估则拒绝完成轮次（Reject Round Completion Without Global Evaluation） |
| 失败训练轮次（Fail Training Round） | 聚合运行时（Aggregation Runtime） | 失败训练轮次（Fail Training Round） | 训练轮次失败（Training Round Failed）, 状态: 已失败（Failed） | - | 尚未明确 |
| 训练轮次进度（Training Round Progress） | 系统 | - | 尚未明确 | 训练轮次进度（Training Round Progress） | 尚未明确 |

<!-- em:section id="software.slice.注册运行时引擎配置文件（RegisterRuntimeEngineProfile）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/RegisterRuntimeEngineProfile" -->
##### 2.8.2.1 注册运行时引擎配置文件（Register Runtime Engine Profile）/EMW-SD-SECTION-72

- 归属: 运行时引擎配置文件（Runtime Engine Profile）.
- 角色/触发条件: 平台管理员（Platform Admin）.
- 输入: 注册运行时引擎配置文件（Register Runtime Engine Profile）.
- 业务结果: 运行时引擎配置文件已注册（Runtime Engine Profile Registered）, 状态: 已注册（Registered）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 运行时引擎配置文件选择可拉取的引擎镜像（Runtime Engine Profile Selects Pullable Engine Image） / 注册 Py Torch Vision 运行时引擎配置文件（Register Py Torch Vision Runtime Engine Profile） - 当 注册运行时引擎配置文件（Register Runtime Engine Profile）, 则 运行时引擎配置文件已注册（Runtime Engine Profile Registered）.

<!-- em:section id="software.slice.运行时引擎配置文件目录（RuntimeEngineProfileCatalog）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/RuntimeEngineProfileCatalog" -->
##### 2.8.2.2 运行时引擎配置文件目录（Runtime Engine Profile Catalog）/EMW-SD-SECTION-73

- 归属: 运行时引擎配置文件（Runtime Engine Profile）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 运行时引擎配置文件目录（Runtime Engine Profile Catalog）.

<!-- em:section id="software.slice.定义训练运行配置（DefineTrainingRunConfiguration）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/DefineTrainingRunConfiguration" -->
##### 2.8.2.3 定义训练运行配置（Define Training Run Configuration）/EMW-SD-SECTION-74

- 归属: 训练运行配置（Training Run Configuration）.
- 角色/触发条件: MLOps 工程师（MLOps Engineer）.
- 输入: 定义训练运行配置（Define Training Run Configuration）.
- 业务结果: 训练运行配置已定义（Training Run Configuration Defined）, 状态: 草稿（Draft）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 训练配置必须可运行（Training Configuration Must Be Runnable） / 定义可运行配置（Define Runnable Configuration） - 当 定义训练运行配置（Define Training Run Configuration）, 则 训练运行配置已定义（Training Run Configuration Defined）.
- 训练配置必须可运行（Training Configuration Must Be Runnable） / 拒绝空轮次预算（Reject Empty Round Budget） - 当 定义训练运行配置（Define Training Run Configuration）, 则 拒绝: 轮次预算必须为正数.

<!-- em:section id="software.slice.更新训练运行配置（UpdateTrainingRunConfiguration）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/UpdateTrainingRunConfiguration" -->
##### 2.8.2.4 更新训练运行配置（Update Training Run Configuration）/EMW-SD-SECTION-75

- 归属: 训练运行配置（Training Run Configuration）.
- 角色/触发条件: MLOps 工程师（MLOps Engineer）.
- 输入: 更新训练运行配置（Update Training Run Configuration）.
- 业务结果: 训练运行配置已更新（Training Run Configuration Updated）, 状态: 草稿（Draft）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 草稿训练配置可以更新（Draft Training Configuration Can Be Updated） / 更新草稿可运行配置（Update Draft Runnable Configuration） - 当 更新训练运行配置（Update Training Run Configuration）, 则 训练运行配置已更新（Training Run Configuration Updated）.
- 草稿训练配置可以更新（Draft Training Configuration Can Be Updated） / 配置锁定后拒绝更新（Reject Update After Configuration Locked） - 当 更新训练运行配置（Update Training Run Configuration）, 则 拒绝: 训练运行配置已锁定.

<!-- em:section id="software.slice.锁定训练运行配置（LockTrainingRunConfiguration）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/LockTrainingRunConfiguration" -->
##### 2.8.2.5 锁定训练运行配置（Lock Training Run Configuration）/EMW-SD-SECTION-76

- 归属: 训练运行配置（Training Run Configuration）.
- 角色/触发条件: 训练提交时锁定配置（Lock Configuration When Training Submitted）.
- 输入: 锁定训练运行配置（Lock Training Run Configuration）.
- 业务结果: 训练运行配置已锁定（Training Run Configuration Locked）, 状态: 已锁定（Locked）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.训练运行配置目录（TrainingRunConfigurationCatalog）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/TrainingRunConfigurationCatalog" -->
##### 2.8.2.6 训练运行配置目录（Training Run Configuration Catalog）/EMW-SD-SECTION-77

- 归属: 训练运行配置（Training Run Configuration）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 训练运行配置目录（Training Run Configuration Catalog）.

<!-- em:section id="software.slice.创建训练作业（CreateTrainingJob）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/CreateTrainingJob" -->
##### 2.8.2.7 创建训练作业（Create Training Job）/EMW-SD-SECTION-78

- 归属: 训练作业（Training Job）.
- 角色/触发条件: 研究主管（Research Lead）.
- 输入: 创建训练作业（Create Training Job）.
- 业务结果: 训练作业已创建（Training Job Created）, 状态: 草稿（Draft）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 训练作业需要可运行配置和运行时访问（Training Job Requires Runnable Configuration And Runtime Access） / 使用可运行配置创建训练作业（Create Training Job With Runnable Configuration） - 当 创建训练作业（Create Training Job）, 则 训练作业已创建（Training Job Created）.
- 训练作业需要可运行配置和运行时访问（Training Job Requires Runnable Configuration And Runtime Access） / 拒绝无可运行配置的训练作业（Reject Training Job Without Runnable Configuration） - 当 创建训练作业（Create Training Job）, 则 拒绝: 需要可运行训练配置.

<!-- em:section id="software.slice.提交训练作业（SubmitTrainingJob）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/SubmitTrainingJob" -->
##### 2.8.2.8 提交训练作业（Submit Training Job）/EMW-SD-SECTION-79

- 归属: 训练作业（Training Job）.
- 角色/触发条件: 研究主管（Research Lead）.
- 输入: 提交训练作业（Submit Training Job）.
- 业务结果: 训练作业已提交（Training Job Submitted）, 状态: 已提交（Submitted）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 只有已创建的训练作业才能提交（Only Created Training Jobs Can Be Submitted） / 提交已创建的训练作业（Submit Created Training Job） - 当 提交训练作业（Submit Training Job）, 则 训练作业已提交（Training Job Submitted）.
- 只有已创建的训练作业才能提交（Only Created Training Jobs Can Be Submitted） / 拒绝未知训练作业（Reject Unknown Training Job） - 当 提交训练作业（Submit Training Job）, 则 拒绝: 训练作业为必填项.

<!-- em:section id="software.slice.训练作业仪表板（TrainingJobDashboard）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/TrainingJobDashboard" -->
##### 2.8.2.9 训练作业仪表板（Training Job Dashboard）/EMW-SD-SECTION-80

- 归属: 训练作业（Training Job）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 训练作业仪表板（Training Job Dashboard）.

<!-- em:section id="software.slice.训练参与者资格（TrainingParticipantEligibility）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/TrainingParticipantEligibility" -->
##### 2.8.2.10 训练参与者资格（Training Participant Eligibility）/EMW-SD-SECTION-81

- 归属: 训练作业（Training Job）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 训练参与者资格（Training Participant Eligibility）.

<!-- em:section id="software.slice.暂停训练作业（PauseTrainingJob）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/PauseTrainingJob" -->
##### 2.8.2.11 暂停训练作业（Pause Training Job）/EMW-SD-SECTION-82

- 归属: 训练作业（Training Job）.
- 角色/触发条件: MLOps 工程师（MLOps Engineer）.
- 输入: 暂停训练作业（Pause Training Job）.
- 业务结果: 训练作业已暂停（Training Job Paused）, 状态: 已暂停（Paused）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.恢复训练作业（ResumeTrainingJob）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/ResumeTrainingJob" -->
##### 2.8.2.12 恢复训练作业（Resume Training Job）/EMW-SD-SECTION-83

- 归属: 训练作业（Training Job）.
- 角色/触发条件: MLOps 工程师（MLOps Engineer）.
- 输入: 恢复训练作业（Resume Training Job）.
- 业务结果: 训练作业已恢复（Training Job Resumed）, 状态: 运行中（Running）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.取消训练作业（CancelTrainingJob）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/CancelTrainingJob" -->
##### 2.8.2.13 取消训练作业（Cancel Training Job）/EMW-SD-SECTION-84

- 归属: 训练作业（Training Job）.
- 角色/触发条件: MLOps 工程师（MLOps Engineer）.
- 输入: 取消训练作业（Cancel Training Job）.
- 业务结果: 训练作业已取消（Training Job Canceled）, 状态: 已取消（Canceled）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.安排下一训练轮次（ScheduleNextTrainingRound）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/ScheduleNextTrainingRound" -->
##### 2.8.2.14 安排下一训练轮次（Schedule Next Training Round）/EMW-SD-SECTION-85

- 归属: 训练作业（Training Job）.
- 角色/触发条件: 当训练应继续时选择下一轮参与者（Select Next Round Participants When Training Should Continue）.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.完成训练作业（CompleteTrainingJob）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/CompleteTrainingJob" -->
##### 2.8.2.15 完成训练作业（Complete Training Job）/EMW-SD-SECTION-86

- 归属: 训练作业（Training Job）.
- 角色/触发条件: 满足训练停止条件时完成作业（Complete Job When Training Stop Criteria Met）.
- 输入: 完成训练作业（Complete Training Job）.
- 业务结果: 训练作业已完成（Training Job Completed）, 状态: 已完成（Completed）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.选择训练轮次参与者（SelectTrainingRoundParticipants）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/SelectTrainingRoundParticipants" -->
##### 2.8.2.16 选择训练轮次参与者（Select Training Round Participants）/EMW-SD-SECTION-87

- 归属: 训练轮次（Training Round）.
- 角色/触发条件: 训练提交时选择初始轮次参与者（Select Initial Round Participants When Training Submitted）.
- 输入: 选择训练轮次参与者（Select Training Round Participants）.
- 业务结果: 训练轮次参与者已选择（Training Round Participants Selected）, 训练轮次参与者选择失败（Training Round Participant Selection Failed）, 状态: 参与者已选择（Participants Selected）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 训练轮次参与者按轮快照（Training Round Participants Are Snapshotted Per Round） / 运行时池达到法定数量时选择参与者（Select Participants When Runtime Pool Reaches Quorum） - 当 选择训练轮次参与者（Select Training Round Participants）, 则 训练轮次参与者已选择（Training Round Participants Selected）.
- 训练轮次参与者按轮快照（Training Round Participants Are Snapshotted Per Round） / 拒绝低于法定数量的参与者快照（Reject Participant Snapshot Below Quorum） - 当 选择训练轮次参与者（Select Training Round Participants）, 则 训练轮次参与者选择失败（Training Round Participant Selection Failed）.

<!-- em:section id="software.slice.重试训练轮次参与者选择（RetryTrainingRoundParticipantSelection）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/RetryTrainingRoundParticipantSelection" -->
##### 2.8.2.17 重试训练轮次参与者选择（Retry Training Round Participant Selection）/EMW-SD-SECTION-88

- 归属: 训练轮次（Training Round）.
- 角色/触发条件: 训练操作员（Training Operator）.
- 输入: 重试训练轮次参与者选择（Retry Training Round Participant Selection）.
- 业务结果: 训练轮次参与者选择重试已请求（Training Round Participant Selection Retry Requested）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 在就绪状态变化后重试参与者选择（Retry Participant Selection After Readiness Changes） / 重试参与者选择（Retry Participant Selection） - 当 重试训练轮次参与者选择（Retry Training Round Participant Selection）, 则 训练轮次参与者选择重试已请求（Training Round Participant Selection Retry Requested）.

<!-- em:section id="software.slice.开始训练轮次（StartTrainingRound）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/StartTrainingRound" -->
##### 2.8.2.18 开始训练轮次（Start Training Round）/EMW-SD-SECTION-89

- 归属: 训练轮次（Training Round）.
- 角色/触发条件: 选择无安全聚合参与者时启动轮次（Start Round When Participants Selected Without Secure Aggregation）, 加密上下文准备就绪时启动轮次（Start Round When Encryption Context Prepared）.
- 输入: 开始训练轮次（Start Training Round）.
- 业务结果: 训练轮次已启动（Training Round Started）, 训练轮次启动失败（Training Round Start Failed）, 状态: 运行中（Running）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 训练轮次需要已选择的运行时法定人数（Training Round Requires Selected Runtime Quorum） / 使用已选择的运行时法定人数启动轮次（Start Round With Selected Runtime Quorum） - 当 开始训练轮次（Start Training Round）, 则 训练轮次已启动（Training Round Started）.
- 训练轮次需要已选择的运行时法定人数（Training Round Requires Selected Runtime Quorum） / 记录低于法定人数的失败轮次启动（Record Failed Round Start Below Quorum） - 当 开始训练轮次（Start Training Round）, 则 训练轮次启动失败（Training Round Start Failed）.

<!-- em:section id="software.slice.生成参与者执行计划（GenerateParticipantExecutionPlan）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/GenerateParticipantExecutionPlan" -->
##### 2.8.2.19 生成参与者执行计划（Generate Participant Execution Plan）/EMW-SD-SECTION-90

- 归属: 参与者执行计划（Participant Execution Plan）.
- 角色/触发条件: 为已选择的运行时生成计划（Generate Plan For Selected Runtime）.
- 输入: 生成参与者执行计划（Generate Participant Execution Plan）.
- 业务结果: 参与者执行计划已生成（Participant Execution Plan Generated）, 状态: 计划已生成（Plan Generated）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 参与者执行计划携带基础模型工件快照（Participant Execution Plan Carries Base Model Artifact Snapshot） / 使用初始模型快照生成初始轮次计划（Generate Initial Round Plan With Initial Model Snapshot） - 当 生成参与者执行计划（Generate Participant Execution Plan）, 则 参与者执行计划已生成（Participant Execution Plan Generated）.
- 参与者执行计划携带基础模型工件快照（Participant Execution Plan Carries Base Model Artifact Snapshot） / 使用聚合模型快照生成后续轮次计划（Generate Later Round Plan With Aggregated Model Snapshot） - 当 生成参与者执行计划（Generate Participant Execution Plan）, 则 参与者执行计划已生成（Participant Execution Plan Generated）.

<!-- em:section id="software.slice.分发参与者执行计划（DispatchParticipantExecutionPlan）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/DispatchParticipantExecutionPlan" -->
##### 2.8.2.20 分发参与者执行计划（Dispatch Participant Execution Plan）/EMW-SD-SECTION-91

- 归属: 参与者执行计划（Participant Execution Plan）.
- 角色/触发条件: 生成时分发计划（Dispatch Plan When Generated）.
- 输入: 分发参与者执行计划（Dispatch Participant Execution Plan）.
- 业务结果: 参与者执行计划已分发（Participant Execution Plan Dispatched）, 状态: 计划已分发（Plan Dispatched）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.提交模型更新提交（SubmitModelUpdateSubmission）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/SubmitModelUpdateSubmission" -->
##### 2.8.2.21 提交模型更新提交（Submit Model Update Submission）/EMW-SD-SECTION-92

- 归属: 训练轮次（Training Round）.
- 角色/触发条件: 运行时代理（Runtime Agent）.
- 输入: 提交模型更新提交（Submit Model Update Submission）.
- 业务结果: 模型更新提交已接收（Model Update Submission Received）, 状态: 已接收（Received）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.评估模型更新提交（EvaluateModelUpdateSubmission）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/EvaluateModelUpdateSubmission" -->
##### 2.8.2.22 评估模型更新提交（Evaluate Model Update Submission）/EMW-SD-SECTION-93

- 归属: 训练轮次（Training Round）.
- 角色/触发条件: 收到时评估模型更新（Evaluate Model Update When Received）.
- 输入: 评估模型更新提交（Evaluate Model Update Submission）.
- 业务结果: 模型更新提交已接受（Model Update Submission Accepted）, 模型更新提交已拒绝（Model Update Submission Rejected）, 状态: 已评估（Evaluated）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 已排除受污染的本地更新（Poisoned Local Updates Are Excluded） / 接受正常更新（Accept Normal Update） - 当 评估模型更新提交（Evaluate Model Update Submission）, 则 模型更新提交已接受（Model Update Submission Accepted）.
- 已排除受污染的本地更新（Poisoned Local Updates Are Excluded） / 拒绝异常更新（Reject Anomalous Update） - 当 评估模型更新提交（Evaluate Model Update Submission）, 则 拒绝: 异常模型更新已拒绝.

<!-- em:section id="software.slice.聚合明文模型更新（AggregatePlainModelUpdates）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/AggregatePlainModelUpdates" -->
##### 2.8.2.23 聚合明文模型更新（Aggregate Plain Model Updates）/EMW-SD-SECTION-94

- 归属: 训练轮次（Training Round）.
- 角色/触发条件: 达到法定人数时聚合明文更新（Aggregate Plain Updates When Quorum Reached）.
- 输入: 聚合明文模型更新（Aggregate Plain Model Updates）.
- 业务结果: 明文模型聚合已完成（Plain Model Aggregation Completed）, 状态: 已完成（Completed）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 达到法定人数后聚合明文模型更新（Plain Model Updates Are Aggregated After Quorum） / 聚合已接受的明文更新（Aggregate Accepted Plain Updates） - 当 聚合明文模型更新（Aggregate Plain Model Updates）, 则 明文模型聚合已完成（Plain Model Aggregation Completed）.

<!-- em:section id="software.slice.请求安全聚合（RequestSecureAggregation）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/RequestSecureAggregation" -->
##### 2.8.2.24 请求安全聚合（Request Secure Aggregation）/EMW-SD-SECTION-95

- 归属: 训练轮次（Training Round）.
- 角色/触发条件: 当选择参与者时请求聚合（Request Aggregation When Participants Selected）.
- 输入: 请求安全聚合（Request Secure Aggregation）.
- 业务结果: 已请求安全聚合（Secure Aggregation Requested）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.完成模型聚合（CompleteModelAggregation）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/CompleteModelAggregation" -->
##### 2.8.2.25 完成模型聚合（Complete Model Aggregation）/EMW-SD-SECTION-96

- 归属: 训练轮次（Training Round）.
- 角色/触发条件: 会话完成时完成安全聚合（Complete Secure Aggregation When Session Completed）, 完成后完成明文聚合（Complete Plain Aggregation When Completed）.
- 输入: 完成模型聚合（Complete Model Aggregation）.
- 业务结果: 全局模型已更新（Global Model Updated）, 状态: 评估全局模型中（Evaluating Global Model）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.提交全局模型评估（SubmitGlobalModelEvaluation）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/SubmitGlobalModelEvaluation" -->
##### 2.8.2.26 提交全局模型评估（Submit Global Model Evaluation）/EMW-SD-SECTION-97

- 归属: 训练轮次（Training Round）.
- 角色/触发条件: 当全局模型更新时提交全局模型评估（Submit Global Model Evaluation When Global Model Updated）.
- 输入: 提交全局模型评估（Submit Global Model Evaluation）.
- 业务结果: 全局模型评估已提交（Global Model Evaluation Submitted）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.完成训练轮次（CompleteTrainingRound）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/CompleteTrainingRound" -->
##### 2.8.2.27 完成训练轮次（Complete Training Round）/EMW-SD-SECTION-98

- 归属: 训练轮次（Training Round）.
- 角色/触发条件: 全局模型评估后结束轮次（Finish Round After Global Model Evaluated）.
- 输入: 完成训练轮次（Complete Training Round）.
- 业务结果: 训练轮次已完成（Training Round Completed）, 状态: 已完成（Completed）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 训练轮次完成需要全局评估（Training Round Completion Requires Global Evaluation） / 全局评估后完成轮次（Complete Round After Global Evaluation） - 当 完成训练轮次（Complete Training Round）, 则 训练轮次已完成（Training Round Completed）.
- 训练轮次完成需要全局评估（Training Round Completion Requires Global Evaluation） / 无全局评估则拒绝完成轮次（Reject Round Completion Without Global Evaluation） - 当 完成训练轮次（Complete Training Round）, 则 拒绝: 需要全局模型评估.

<!-- em:section id="software.slice.失败训练轮次（FailTrainingRound）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/FailTrainingRound" -->
##### 2.8.2.28 失败训练轮次（Fail Training Round）/EMW-SD-SECTION-99

- 归属: 训练轮次（Training Round）.
- 角色/触发条件: 聚合运行时（Aggregation Runtime）.
- 输入: 失败训练轮次（Fail Training Round）.
- 业务结果: 训练轮次失败（Training Round Failed）, 状态: 已失败（Failed）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.训练轮次进度（TrainingRoundProgress）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/TrainingRoundProgress" -->
##### 2.8.2.29 训练轮次进度（Training Round Progress）/EMW-SD-SECTION-100

- 归属: 训练轮次（Training Round）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 训练轮次进度（Training Round Progress）.

#### 2.8.3 上下文数据设计/EMW-SD-CDD-8

| Read Model | 形态 | 来源事件 | 关键字段 | 查询字段 |
| --- | --- | --- | --- | --- |
| 运行时引擎配置文件目录（Runtime Engine Profile Catalog） | 集合/列表 | 运行时引擎配置文件已注册（Runtime Engine Profile Registered） | runtimeEngineProfileId | 尚未明确 |
| 训练运行配置目录（Training Run Configuration Catalog） | 集合/列表 | 联邦已创建（Federation Created）, 特征架构已定义（Feature Schema Defined）, 训练运行配置已定义（Training Run Configuration Defined）, 训练运行配置已更新（Training Run Configuration Updated）, 训练运行配置已锁定（Training Run Configuration Locked） | trainingRunConfigurationId | 尚未明确 |
| 训练作业仪表板（Training Job Dashboard） | 集合/列表 | 联邦已创建（Federation Created）, 特征架构已定义（Feature Schema Defined）, 训练作业已创建（Training Job Created）, 训练作业已提交（Training Job Submitted）, 训练作业已暂停（Training Job Paused）, 训练作业已恢复（Training Job Resumed）, 训练作业已取消（Training Job Canceled）, 训练轮次已启动（Training Round Started）, 训练轮次已完成（Training Round Completed）, 训练作业已完成（Training Job Completed） | trainingJobId | 尚未明确 |
| 训练参与者资格（Training Participant Eligibility） | 集合/列表 | 联邦已创建（Federation Created）, 组织已注册（Organization Registered）, 特征架构已定义（Feature Schema Defined）, 训练作业已创建（Training Job Created）, 训练作业已提交（Training Job Submitted）, 参与者已加入（Participant Joined）, 参与者已暂停（Participant Suspended）, 参与者已移除（Participant Removed）, 运行时身份已激活（Runtime Identity Activated）, 运行时身份已撤销（Runtime Identity Revoked）, 运行时能力已检测（Runtime Capabilities Detected）, 运行时连接已建立（Runtime Connection Established）, 已检测到运行时代理离线（Runtime Agent Offline Detected）, 运行时代理已恢复（Runtime Agent Recovered）, 数据集已声明（Dataset Declared）, 数据集元数据已报告（Dataset Metadata Reported）, 数据集合约已验证（Dataset Contract Validated）, 数据集已批准用于训练（Dataset Approved For Training）, 数据集训练批准已撤销（Dataset Training Approval Revoked）, 代理数据集访问已验证（Agent Dataset Access Validated） | trainingJobId | 尚未明确 |
| 训练轮次进度（Training Round Progress） | 集合/列表 | 训练作业已创建（Training Job Created）, 特征架构已定义（Feature Schema Defined）, 训练轮次参与者已选择（Training Round Participants Selected）, 训练轮次参与者选择失败（Training Round Participant Selection Failed）, 训练轮次已启动（Training Round Started）, 训练轮次启动失败（Training Round Start Failed）, 参与者执行计划已生成（Participant Execution Plan Generated）, 参与者执行计划已分发（Participant Execution Plan Dispatched）, 轮次执行已启动（Round Execution Started）, 轮次执行启动失败（Round Execution Start Failed）, 轮次执行启动重试已开始（Round Execution Start Retry Started）, 轮次执行启动重试失败（Round Execution Start Retry Failed）, 轮次执行已完成（Round Execution Completed）, 轮次执行失败（Round Execution Failed）, 轮次执行运行时重试已启动（Round Execution Runtime Retry Started）, 轮次执行运行时重试失败（Round Execution Runtime Retry Failed）, 模型更新提交已接收（Model Update Submission Received）, 模型更新提交已接受（Model Update Submission Accepted）, 模型更新提交已拒绝（Model Update Submission Rejected）, 已请求安全聚合（Secure Aggregation Requested）, 全局模型已更新（Global Model Updated）, 全局模型评估已提交（Global Model Evaluation Submitted）, 训练轮次已完成（Training Round Completed）, 训练轮次失败（Training Round Failed） | trainingJobId, roundId | 尚未明确 |

#### 2.8.4 值类型设计/EMW-SD-VTD-4

| 值类型 | 类型 | 基础类型 | 约束/字段 |
| --- | --- | --- | --- |
| Training Round Participant | object | TrainingRoundParticipant | organizationId: UUID, runtimeId: UUID, datasetId: UUID |

<!-- em:section id="software.context.模型生命周期（ModelLifecycle）" source="domain/FederationLearningPlatform/context/ModelLifecycle" -->
### 2.9 模型生命周期（Model Lifecycle）/EMW-SD-SECTION-101

上下文职责: 模型生命周期从训练产生最终已评估候选之后开始。

#### 2.9.1 业务对象设计/EMW-SD-BOD-9

| 对象 | 类型 | 生命周期 | 业务能力 |
| --- | --- | --- | --- |
| 模型（Model） | 概念 | 候选（Candidate） -> 评估已打包（Evaluation Packaged） -> 已批准（Approved） -> 生产（Production） -> 已回滚（Rolled Back） -> 已停用（Retired） | 注册候选模型（Register Candidate Model）, 记录模型评估包（Record Model Evaluation Package）, 批准模型（Approve Model）, 提升模型至生产（Promote Model To Production）, 回滚模型（Rollback Model）, 停用模型（Retire Model）, 模型目录（Model Catalog） |

#### 2.9.2 应用服务与能力设计/EMW-SD-ASCD-9

| 业务能力 | 角色/触发条件 | 输入 | 业务结果 | 读侧结果 | 规则 |
| --- | --- | --- | --- | --- | --- |
| 注册候选模型（Register Candidate Model） | 训练作业完成时注册最终模型（Register Final Model When Training Job Completed） | 注册候选模型（Register Candidate Model） | 模型候选已注册（Model Candidate Registered）, 状态: 候选（Candidate） | - | 尚未明确 |
| 记录模型评估包（Record Model Evaluation Package） | 治理审核员（Governance Reviewer） | 记录模型评估包（Record Model Evaluation Package） | 模型评估包已记录（Model Evaluation Package Recorded）, 状态: 评估已打包（Evaluation Packaged） | - | 记录完整评估包（Record Complete Evaluation Package） |
| 批准模型（Approve Model） | 治理审核员（Governance Reviewer） | 批准模型（Approve Model） | 模型已批准（Model Approved）, 状态: 已批准（Approved） | - | 批准评估打包模型（Approve Evaluation Packaged Model）, 拒绝没有评估包的候选模型（Reject Candidate Without Evaluation Package） |
| 提升模型至生产（Promote Model To Production） | 发布管理员（Release Manager） | 提升模型至生产（Promote Model To Production） | 模型已提升到生产环境（Model Promoted To Production）, 状态: 生产（Production） | - | 提升已批准的模型（Promote Approved Model）, 拒绝未批准模型的提升（Reject Unapproved Model Promotion） |
| 回滚模型（Rollback Model） | 发布管理员（Release Manager） | 回滚模型（Rollback Model） | 模型已回滚（Model Rolled Back）, 状态: 已回滚（Rolled Back） | - | 尚未明确 |
| 停用模型（Retire Model） | 发布管理员（Release Manager） | 停用模型（Retire Model） | 模型已退役（Model Retired）, 状态: 已停用（Retired） | - | 尚未明确 |
| 模型目录（Model Catalog） | 系统 | - | 尚未明确 | 模型目录（Model Catalog） | 尚未明确 |

<!-- em:section id="software.slice.注册候选模型（RegisterCandidateModel）" source="domain/FederationLearningPlatform/context/ModelLifecycle/slice/RegisterCandidateModel" -->
##### 2.9.2.1 注册候选模型（Register Candidate Model）/EMW-SD-SECTION-102

- 归属: 模型（Model）.
- 角色/触发条件: 训练作业完成时注册最终模型（Register Final Model When Training Job Completed）.
- 输入: 注册候选模型（Register Candidate Model）.
- 业务结果: 模型候选已注册（Model Candidate Registered）, 状态: 候选（Candidate）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.记录模型评估包（RecordModelEvaluationPackage）" source="domain/FederationLearningPlatform/context/ModelLifecycle/slice/RecordModelEvaluationPackage" -->
##### 2.9.2.2 记录模型评估包（Record Model Evaluation Package）/EMW-SD-SECTION-103

- 归属: 模型（Model）.
- 角色/触发条件: 治理审核员（Governance Reviewer）.
- 输入: 记录模型评估包（Record Model Evaluation Package）.
- 业务结果: 模型评估包已记录（Model Evaluation Package Recorded）, 状态: 评估已打包（Evaluation Packaged）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 模型审批需要评估包（Model Approval Requires Evaluation Package） / 记录完整评估包（Record Complete Evaluation Package） - 当 记录模型评估包（Record Model Evaluation Package）, 则 模型评估包已记录（Model Evaluation Package Recorded）.

<!-- em:section id="software.slice.批准模型（ApproveModel）" source="domain/FederationLearningPlatform/context/ModelLifecycle/slice/ApproveModel" -->
##### 2.9.2.3 批准模型（Approve Model）/EMW-SD-SECTION-104

- 归属: 模型（Model）.
- 角色/触发条件: 治理审核员（Governance Reviewer）.
- 输入: 批准模型（Approve Model）.
- 业务结果: 模型已批准（Model Approved）, 状态: 已批准（Approved）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 只有评估打包模型才能被批准（Only Evaluation Packaged Models Can Be Approved） / 批准评估打包模型（Approve Evaluation Packaged Model） - 当 批准模型（Approve Model）, 则 模型已批准（Model Approved）.
- 只有评估打包模型才能被批准（Only Evaluation Packaged Models Can Be Approved） / 拒绝没有评估包的候选模型（Reject Candidate Without Evaluation Package） - 当 批准模型（Approve Model）, 则 拒绝: 需要模型评估包.

<!-- em:section id="software.slice.提升模型至生产（PromoteModelToProduction）" source="domain/FederationLearningPlatform/context/ModelLifecycle/slice/PromoteModelToProduction" -->
##### 2.9.2.4 提升模型至生产（Promote Model To Production）/EMW-SD-SECTION-105

- 归属: 模型（Model）.
- 角色/触发条件: 发布管理员（Release Manager）.
- 输入: 提升模型至生产（Promote Model To Production）.
- 业务结果: 模型已提升到生产环境（Model Promoted To Production）, 状态: 生产（Production）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 仅已批准的模型可提升到生产环境（Only Approved Models Can Be Promoted To Production） / 提升已批准的模型（Promote Approved Model） - 当 提升模型至生产（Promote Model To Production）, 则 模型已提升到生产环境（Model Promoted To Production）.
- 仅已批准的模型可提升到生产环境（Only Approved Models Can Be Promoted To Production） / 拒绝未批准模型的提升（Reject Unapproved Model Promotion） - 当 提升模型至生产（Promote Model To Production）, 则 拒绝: 需要模型批准.

<!-- em:section id="software.slice.回滚模型（RollbackModel）" source="domain/FederationLearningPlatform/context/ModelLifecycle/slice/RollbackModel" -->
##### 2.9.2.5 回滚模型（Rollback Model）/EMW-SD-SECTION-106

- 归属: 模型（Model）.
- 角色/触发条件: 发布管理员（Release Manager）.
- 输入: 回滚模型（Rollback Model）.
- 业务结果: 模型已回滚（Model Rolled Back）, 状态: 已回滚（Rolled Back）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.停用模型（RetireModel）" source="domain/FederationLearningPlatform/context/ModelLifecycle/slice/RetireModel" -->
##### 2.9.2.6 停用模型（Retire Model）/EMW-SD-SECTION-107

- 归属: 模型（Model）.
- 角色/触发条件: 发布管理员（Release Manager）.
- 输入: 停用模型（Retire Model）.
- 业务结果: 模型已退役（Model Retired）, 状态: 已停用（Retired）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.模型目录（ModelCatalog）" source="domain/FederationLearningPlatform/context/ModelLifecycle/slice/ModelCatalog" -->
##### 2.9.2.7 模型目录（Model Catalog）/EMW-SD-SECTION-108

- 归属: 模型（Model）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 模型目录（Model Catalog）.

#### 2.9.3 上下文数据设计/EMW-SD-CDD-9

| Read Model | 形态 | 来源事件 | 关键字段 | 查询字段 |
| --- | --- | --- | --- | --- |
| 模型目录（Model Catalog） | 集合/列表 | 训练作业已创建（Training Job Created）, 模型候选已注册（Model Candidate Registered）, 模型评估包已记录（Model Evaluation Package Recorded）, 模型已批准（Model Approved）, 模型已提升到生产环境（Model Promoted To Production）, 模型已回滚（Model Rolled Back）, 模型已退役（Model Retired） | modelId | 尚未明确 |

<!-- em:section id="software.context.运行时监控（RuntimeMonitoring）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring" -->
### 2.10 运行时监控（Runtime Monitoring）/EMW-SD-SECTION-109

上下文职责: 运行时运维将低频运行时状态变更、节点清单变更、最新遥测视图、训练告警以及仅追加审计记录区分开来。高频心跳和资源样本属于遥测，而非事件溯源领域事件。

#### 2.10.1 业务对象设计/EMW-SD-BOD-10

| 对象 | 类型 | 生命周期 | 业务能力 |
| --- | --- | --- | --- |
| 节点运行时健康（Node Runtime Health） | 概念 | 健康（Healthy） -> 离线（Offline） | 运行时最新遥测（Runtime Telemetry Latest）, 检测运行时代理离线（Detect Runtime Agent Offline）, 标记运行时代理已恢复（Mark Runtime Agent Recovered）, 运行时健康仪表板（Runtime Health Dashboard） |
| 运行时节点资源压力（Runtime Node Resource Pressure） | 概念 | 已检测到压力（Pressure Detected） | 检测运行时节点资源压力（Detect Runtime Node Resource Pressure） |
| 运行时节点容量（Runtime Node Capacity） | 概念 | 容量已变更（Capacity Changed） | 检测运行时节点容量变更（Detect Runtime Node Capacity Change） |
| 运行时节点清单（Runtime Node Inventory） | 概念 | 已报告（Reported） | 记录运行时节点清单（Record Runtime Node Inventory）, 运行时节点清单视图（Runtime Node Inventory View） |
| 运行时节点资源遥测（Runtime Node Resource Telemetry） | 概念 | 尚未明确 | 运行时节点最新资源（Runtime Node Resource Latest） |
| 训练告警（Training Alert） | 概念 | 已引发（Raised） -> 已确认（Acknowledged） -> 已解决（Resolved） | 引发训练告警（Raise Training Alert）, 确认训练告警（Acknowledge Training Alert）, 解决训练告警（Resolve Training Alert）, 训练告警目录（Training Alert Catalog） |
| 审计记录（Audit Record） | 概念 | 已追加（Appended） | 追加审计跟踪（Append Audit Trail）, 审计记录日志（Audit Record Log） |

#### 2.10.2 应用服务与能力设计/EMW-SD-ASCD-10

| 业务能力 | 角色/触发条件 | 输入 | 业务结果 | 读侧结果 | 规则 |
| --- | --- | --- | --- | --- | --- |
| 运行时最新遥测（Runtime Telemetry Latest） | 系统 | - | 尚未明确 | 运行时最新遥测（Runtime Telemetry Latest） | 尚未明确 |
| 检测运行时代理离线（Detect Runtime Agent Offline） | 从遥测检测离线运行时代理（Detect Offline Runtime Agent From Telemetry） | 检测运行时代理离线（Detect Runtime Agent Offline） | 已检测到运行时代理离线（Runtime Agent Offline Detected）, 状态: 离线（Offline） | - | 尚未明确 |
| 标记运行时代理已恢复（Mark Runtime Agent Recovered） | 从遥测标记运行时代理已恢复（Mark Recovered Runtime Agent From Telemetry） | 标记运行时代理已恢复（Mark Runtime Agent Recovered） | 运行时代理已恢复（Runtime Agent Recovered）, 状态: 健康（Healthy） | - | 尚未明确 |
| 检测运行时节点资源压力（Detect Runtime Node Resource Pressure） | 从遥测检测资源压力（Detect Resource Pressure From Telemetry） | 检测运行时节点资源压力（Detect Runtime Node Resource Pressure） | 已检测到运行时节点资源压力（Runtime Node Resource Pressure Detected）, 状态: 已检测到压力（Pressure Detected） | - | 尚未明确 |
| 记录运行时节点清单（Record Runtime Node Inventory） | 代理报告时记录运行时节点清单（Record Runtime Node Inventory When Agent Reported） | 记录运行时节点清单（Record Runtime Node Inventory） | 运行时节点清单已报告（Runtime Node Inventory Reported）, 状态: 已报告（Reported） | - | 尚未明确 |
| 运行时节点清单视图（Runtime Node Inventory View） | 系统 | - | 尚未明确 | 运行时节点清单视图（Runtime Node Inventory View） | 尚未明确 |
| 运行时节点最新资源（Runtime Node Resource Latest） | 系统 | - | 尚未明确 | 运行时节点最新资源（Runtime Node Resource Latest） | 尚未明确 |
| 检测运行时节点容量变更（Detect Runtime Node Capacity Change） | 从资源遥测检测容量变化（Detect Capacity Change From Resource Telemetry） | 检测运行时节点容量变更（Detect Runtime Node Capacity Change） | 运行时节点容量已更改（Runtime Node Capacity Changed）, 状态: 容量已变更（Capacity Changed） | - | 尚未明确 |
| 运行时健康仪表板（Runtime Health Dashboard） | 系统 | - | 尚未明确 | 运行时健康仪表板（Runtime Health Dashboard） | 尚未明确 |
| 引发训练告警（Raise Training Alert） | 在节点资源压力时发出警报（Raise Alert On Node Resource Pressure） | 引发训练告警（Raise Training Alert） | 训练警报已发出（Training Alert Raised）, 状态: 已引发（Raised） | - | 尚未明确 |
| 训练告警目录（Training Alert Catalog） | 系统 | - | 尚未明确 | 训练告警目录（Training Alert Catalog） | 尚未明确 |
| 确认训练告警（Acknowledge Training Alert） | MLOps 工程师（MLOps Engineer） | 确认训练告警（Acknowledge Training Alert） | 训练警报已确认（Training Alert Acknowledged）, 状态: 已确认（Acknowledged） | - | 尚未明确 |
| 解决训练告警（Resolve Training Alert） | MLOps 工程师（MLOps Engineer） | 解决训练告警（Resolve Training Alert） | 训练警报已解决（Training Alert Resolved）, 状态: 已解决（Resolved） | - | 尚未明确 |
| 追加审计跟踪（Append Audit Trail） | 审计关键训练事件（Audit Critical Training Events）, 审计联邦已激活（Audit Federation Activated）, 审计联邦已暂停（Audit Federation Suspended）, 审计联邦已重新激活（Audit Federation Reactivated）, 审计参与者已加入（Audit Participant Joined）, 审计参与者已暂停（Audit Participant Suspended）, 审计数据集批准（Audit Dataset Approval）, 审计数据集批准已撤销（Audit Dataset Approval Revoked）, 审计训练作业已提交（Audit Training Job Submitted）, 审计模型已提升至生产（Audit Model Promoted To Production） | 追加审计跟踪（Append Audit Trail） | 审计跟踪已追加（Audit Trail Appended）, 状态: 已追加（Appended） | - | 尚未明确 |
| 审计记录日志（Audit Record Log） | 系统 | - | 尚未明确 | 审计记录日志（Audit Record Log） | 尚未明确 |

<!-- em:section id="software.slice.运行时最新遥测（RuntimeTelemetryLatest）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/RuntimeTelemetryLatest" -->
##### 2.10.2.1 运行时最新遥测（Runtime Telemetry Latest）/EMW-SD-SECTION-110

- 归属: 节点运行时健康（Node Runtime Health）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 运行时最新遥测（Runtime Telemetry Latest）.

<!-- em:section id="software.slice.检测运行时代理离线（DetectRuntimeAgentOffline）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/DetectRuntimeAgentOffline" -->
##### 2.10.2.2 检测运行时代理离线（Detect Runtime Agent Offline）/EMW-SD-SECTION-111

- 归属: 节点运行时健康（Node Runtime Health）.
- 角色/触发条件: 从遥测检测离线运行时代理（Detect Offline Runtime Agent From Telemetry）.
- 输入: 检测运行时代理离线（Detect Runtime Agent Offline）.
- 业务结果: 已检测到运行时代理离线（Runtime Agent Offline Detected）, 状态: 离线（Offline）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.标记运行时代理已恢复（MarkRuntimeAgentRecovered）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/MarkRuntimeAgentRecovered" -->
##### 2.10.2.3 标记运行时代理已恢复（Mark Runtime Agent Recovered）/EMW-SD-SECTION-112

- 归属: 节点运行时健康（Node Runtime Health）.
- 角色/触发条件: 从遥测标记运行时代理已恢复（Mark Recovered Runtime Agent From Telemetry）.
- 输入: 标记运行时代理已恢复（Mark Runtime Agent Recovered）.
- 业务结果: 运行时代理已恢复（Runtime Agent Recovered）, 状态: 健康（Healthy）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.检测运行时节点资源压力（DetectRuntimeNodeResourcePressure）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/DetectRuntimeNodeResourcePressure" -->
##### 2.10.2.4 检测运行时节点资源压力（Detect Runtime Node Resource Pressure）/EMW-SD-SECTION-113

- 归属: 运行时节点资源压力（Runtime Node Resource Pressure）.
- 角色/触发条件: 从遥测检测资源压力（Detect Resource Pressure From Telemetry）.
- 输入: 检测运行时节点资源压力（Detect Runtime Node Resource Pressure）.
- 业务结果: 已检测到运行时节点资源压力（Runtime Node Resource Pressure Detected）, 状态: 已检测到压力（Pressure Detected）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.记录运行时节点清单（RecordRuntimeNodeInventory）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/RecordRuntimeNodeInventory" -->
##### 2.10.2.5 记录运行时节点清单（Record Runtime Node Inventory）/EMW-SD-SECTION-114

- 归属: 运行时节点清单（Runtime Node Inventory）.
- 角色/触发条件: 代理报告时记录运行时节点清单（Record Runtime Node Inventory When Agent Reported）.
- 输入: 记录运行时节点清单（Record Runtime Node Inventory）.
- 业务结果: 运行时节点清单已报告（Runtime Node Inventory Reported）, 状态: 已报告（Reported）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.运行时节点清单视图（RuntimeNodeInventoryView）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/RuntimeNodeInventoryView" -->
##### 2.10.2.6 运行时节点清单视图（Runtime Node Inventory View）/EMW-SD-SECTION-115

- 归属: 运行时节点清单（Runtime Node Inventory）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 运行时节点清单视图（Runtime Node Inventory View）.

<!-- em:section id="software.slice.运行时节点最新资源（RuntimeNodeResourceLatest）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/RuntimeNodeResourceLatest" -->
##### 2.10.2.7 运行时节点最新资源（Runtime Node Resource Latest）/EMW-SD-SECTION-116

- 归属: 运行时节点资源遥测（Runtime Node Resource Telemetry）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 运行时节点最新资源（Runtime Node Resource Latest）.

<!-- em:section id="software.slice.检测运行时节点容量变更（DetectRuntimeNodeCapacityChange）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/DetectRuntimeNodeCapacityChange" -->
##### 2.10.2.8 检测运行时节点容量变更（Detect Runtime Node Capacity Change）/EMW-SD-SECTION-117

- 归属: 运行时节点容量（Runtime Node Capacity）.
- 角色/触发条件: 从资源遥测检测容量变化（Detect Capacity Change From Resource Telemetry）.
- 输入: 检测运行时节点容量变更（Detect Runtime Node Capacity Change）.
- 业务结果: 运行时节点容量已更改（Runtime Node Capacity Changed）, 状态: 容量已变更（Capacity Changed）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.运行时健康仪表板（RuntimeHealthDashboard）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/RuntimeHealthDashboard" -->
##### 2.10.2.9 运行时健康仪表板（Runtime Health Dashboard）/EMW-SD-SECTION-118

- 归属: 节点运行时健康（Node Runtime Health）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 运行时健康仪表板（Runtime Health Dashboard）.

<!-- em:section id="software.slice.引发训练告警（RaiseTrainingAlert）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/RaiseTrainingAlert" -->
##### 2.10.2.10 引发训练告警（Raise Training Alert）/EMW-SD-SECTION-119

- 归属: 训练告警（Training Alert）.
- 角色/触发条件: 在节点资源压力时发出警报（Raise Alert On Node Resource Pressure）.
- 输入: 引发训练告警（Raise Training Alert）.
- 业务结果: 训练警报已发出（Training Alert Raised）, 状态: 已引发（Raised）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.训练告警目录（TrainingAlertCatalog）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/TrainingAlertCatalog" -->
##### 2.10.2.11 训练告警目录（Training Alert Catalog）/EMW-SD-SECTION-120

- 归属: 训练告警（Training Alert）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 训练告警目录（Training Alert Catalog）.

<!-- em:section id="software.slice.确认训练告警（AcknowledgeTrainingAlert）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/AcknowledgeTrainingAlert" -->
##### 2.10.2.12 确认训练告警（Acknowledge Training Alert）/EMW-SD-SECTION-121

- 归属: 训练告警（Training Alert）.
- 角色/触发条件: MLOps 工程师（MLOps Engineer）.
- 输入: 确认训练告警（Acknowledge Training Alert）.
- 业务结果: 训练警报已确认（Training Alert Acknowledged）, 状态: 已确认（Acknowledged）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.解决训练告警（ResolveTrainingAlert）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/ResolveTrainingAlert" -->
##### 2.10.2.13 解决训练告警（Resolve Training Alert）/EMW-SD-SECTION-122

- 归属: 训练告警（Training Alert）.
- 角色/触发条件: MLOps 工程师（MLOps Engineer）.
- 输入: 解决训练告警（Resolve Training Alert）.
- 业务结果: 训练警报已解决（Training Alert Resolved）, 状态: 已解决（Resolved）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.追加审计跟踪（AppendAuditTrail）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/AppendAuditTrail" -->
##### 2.10.2.14 追加审计跟踪（Append Audit Trail）/EMW-SD-SECTION-123

- 归属: 审计记录（Audit Record）.
- 角色/触发条件: 审计关键训练事件（Audit Critical Training Events）, 审计联邦已激活（Audit Federation Activated）, 审计联邦已暂停（Audit Federation Suspended）, 审计联邦已重新激活（Audit Federation Reactivated）, 审计参与者已加入（Audit Participant Joined）, 审计参与者已暂停（Audit Participant Suspended）, 审计数据集批准（Audit Dataset Approval）, 审计数据集批准已撤销（Audit Dataset Approval Revoked）, 审计训练作业已提交（Audit Training Job Submitted）, 审计模型已提升至生产（Audit Model Promoted To Production）.
- 输入: 追加审计跟踪（Append Audit Trail）.
- 业务结果: 审计跟踪已追加（Audit Trail Appended）, 状态: 已追加（Appended）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.审计记录日志（AuditRecordLog）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/AuditRecordLog" -->
##### 2.10.2.15 审计记录日志（Audit Record Log）/EMW-SD-SECTION-124

- 归属: 审计记录（Audit Record）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 审计记录日志（Audit Record Log）.

#### 2.10.3 上下文数据设计/EMW-SD-CDD-10

| Read Model | 形态 | 来源事件 | 关键字段 | 查询字段 |
| --- | --- | --- | --- | --- |
| 运行时最新遥测（Runtime Telemetry Latest） | 集合/列表 | 尚未明确 | nodeId | 尚未明确 |
| 运行时节点清单视图（Runtime Node Inventory View） | 集合/列表 | 组织已注册（Organization Registered）, 运行时安装计划已创建（Runtime Installation Plan Created）, 运行时基础设施已注册（Runtime Infrastructure Registered）, 运行时节点清单已报告（Runtime Node Inventory Reported） | nodeId | 尚未明确 |
| 运行时节点最新资源（Runtime Node Resource Latest） | 集合/列表 | 尚未明确 | nodeId | 尚未明确 |
| 运行时健康仪表板（Runtime Health Dashboard） | 集合/列表 | 联邦已创建（Federation Created）, 训练作业已创建（Training Job Created）, 已检测到运行时代理离线（Runtime Agent Offline Detected）, 运行时代理已恢复（Runtime Agent Recovered）, 已检测到运行时节点资源压力（Runtime Node Resource Pressure Detected）, 运行时节点容量已更改（Runtime Node Capacity Changed）, 运行时节点清单已报告（Runtime Node Inventory Reported）, 训练警报已发出（Training Alert Raised） | nodeId | 尚未明确 |
| 训练告警目录（Training Alert Catalog） | 集合/列表 | 运行时节点清单已报告（Runtime Node Inventory Reported）, 训练作业已创建（Training Job Created）, 训练警报已发出（Training Alert Raised）, 训练警报已确认（Training Alert Acknowledged）, 训练警报已解决（Training Alert Resolved） | alertId | 尚未明确 |
| 审计记录日志（Audit Record Log） | 集合/列表 | 审计跟踪已追加（Audit Trail Appended） | auditRecordId | 尚未明确 |

<!-- em:section id="software.context.运行时治理（RuntimeGovernance）" source="domain/FederationLearningPlatform/context/RuntimeGovernance" -->
### 2.11 运行时治理（Runtime Governance）/EMW-SD-SECTION-125

上下文职责: 运行时治理负责稳定的 RuntimeIdentity 激活、能力检测和撤销。运行时私钥、本地路径、凭据、节点调度和数据平面连接保留在参与者运行时基础设施内部。 平台管理的运行时代理根据平台部署结果激活。网络可达性不等于业务授权。训练资格使用活跃的联邦成员资格、RuntimeIdentity、已检测到的能力、已连接的运行时状态以及数据集就绪情况。 训练参数加密、安全聚合密钥和数据平面消息加密由协议适配器或 SecureAggregation 处理，不由 RuntimeIdentity 事件处理。

#### 2.11.1 业务对象设计/EMW-SD-BOD-11

| 对象 | 类型 | 生命周期 | 业务能力 |
| --- | --- | --- | --- |
| 运行时标识（Runtime Identity） | 概念 | 已激活（Active） -> 已撤销（Revoked） | 激活运行时标识（Activate Runtime Identity）, 撤销运行时标识（Revoke Runtime Identity）, 运行时标识目录（Runtime Identity Catalog） |
| 运行时能力（Runtime Capability） | 概念 | 已检测到（Detected） | 检测运行时能力（Detect Runtime Capabilities）, 运行时能力目录（Runtime Capability Catalog） |

#### 2.11.2 应用服务与能力设计/EMW-SD-ASCD-11

| 业务能力 | 角色/触发条件 | 输入 | 业务结果 | 读侧结果 | 规则 |
| --- | --- | --- | --- | --- | --- |
| 激活运行时标识（Activate Runtime Identity） | 平台管理员（Platform Admin） | 激活运行时标识（Activate Runtime Identity） | 运行时身份已激活（Runtime Identity Activated）, 状态: 已激活（Active） | - | 尚未明确 |
| 撤销运行时标识（Revoke Runtime Identity） | 安全审核员（Security Reviewer） | 撤销运行时标识（Revoke Runtime Identity） | 运行时身份已撤销（Runtime Identity Revoked）, 状态: 已撤销（Revoked） | - | 尚未明确 |
| 检测运行时能力（Detect Runtime Capabilities） | 运行时代理（Runtime Agent） | 检测运行时能力（Detect Runtime Capabilities） | 运行时能力已检测（Runtime Capabilities Detected）, 状态: 已检测到（Detected） | - | 尚未明确 |
| 运行时标识目录（Runtime Identity Catalog） | 系统 | - | 尚未明确 | 运行时标识目录（Runtime Identity Catalog） | 尚未明确 |
| 运行时能力目录（Runtime Capability Catalog） | 系统 | - | 尚未明确 | 运行时能力目录（Runtime Capability Catalog） | 尚未明确 |

<!-- em:section id="software.slice.激活运行时标识（ActivateRuntimeIdentity）" source="domain/FederationLearningPlatform/context/RuntimeGovernance/slice/ActivateRuntimeIdentity" -->
##### 2.11.2.1 激活运行时标识（Activate Runtime Identity）/EMW-SD-SECTION-126

- 归属: 运行时标识（Runtime Identity）.
- 角色/触发条件: 平台管理员（Platform Admin）.
- 输入: 激活运行时标识（Activate Runtime Identity）.
- 业务结果: 运行时身份已激活（Runtime Identity Activated）, 状态: 已激活（Active）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.撤销运行时标识（RevokeRuntimeIdentity）" source="domain/FederationLearningPlatform/context/RuntimeGovernance/slice/RevokeRuntimeIdentity" -->
##### 2.11.2.2 撤销运行时标识（Revoke Runtime Identity）/EMW-SD-SECTION-127

- 归属: 运行时标识（Runtime Identity）.
- 角色/触发条件: 安全审核员（Security Reviewer）.
- 输入: 撤销运行时标识（Revoke Runtime Identity）.
- 业务结果: 运行时身份已撤销（Runtime Identity Revoked）, 状态: 已撤销（Revoked）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.检测运行时能力（DetectRuntimeCapabilities）" source="domain/FederationLearningPlatform/context/RuntimeGovernance/slice/DetectRuntimeCapabilities" -->
##### 2.11.2.3 检测运行时能力（Detect Runtime Capabilities）/EMW-SD-SECTION-128

- 归属: 运行时能力（Runtime Capability）.
- 角色/触发条件: 运行时代理（Runtime Agent）.
- 输入: 检测运行时能力（Detect Runtime Capabilities）.
- 业务结果: 运行时能力已检测（Runtime Capabilities Detected）, 状态: 已检测到（Detected）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.运行时标识目录（RuntimeIdentityCatalog）" source="domain/FederationLearningPlatform/context/RuntimeGovernance/slice/RuntimeIdentityCatalog" -->
##### 2.11.2.4 运行时标识目录（Runtime Identity Catalog）/EMW-SD-SECTION-129

- 归属: 运行时标识（Runtime Identity）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 运行时标识目录（Runtime Identity Catalog）.

<!-- em:section id="software.slice.运行时能力目录（RuntimeCapabilityCatalog）" source="domain/FederationLearningPlatform/context/RuntimeGovernance/slice/RuntimeCapabilityCatalog" -->
##### 2.11.2.5 运行时能力目录（Runtime Capability Catalog）/EMW-SD-SECTION-130

- 归属: 运行时能力（Runtime Capability）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 运行时能力目录（Runtime Capability Catalog）.

#### 2.11.3 上下文数据设计/EMW-SD-CDD-11

| Read Model | 形态 | 来源事件 | 关键字段 | 查询字段 |
| --- | --- | --- | --- | --- |
| 运行时标识目录（Runtime Identity Catalog） | 集合/列表 | 组织已注册（Organization Registered）, 运行时身份已激活（Runtime Identity Activated）, 运行时身份已撤销（Runtime Identity Revoked） | runtimeId | 尚未明确 |
| 运行时能力目录（Runtime Capability Catalog） | 集合/列表 | 运行时能力已检测（Runtime Capabilities Detected） | runtimeId | 尚未明确 |

<!-- em:section id="software.context.安全聚合（SecureAggregation）" source="domain/FederationLearningPlatform/context/SecureAggregation" -->
### 2.12 安全聚合（Secure Aggregation）/EMW-SD-SECTION-131

上下文职责: 安全聚合拥有基于同态加密的聚合会话。平台持有私钥，仅向运行时代理发布加密上下文元数据；原始密钥材料、公钥文档和存储位置保留在 SecureAggregationService 或 KMS 适配器配置中。

#### 2.12.1 业务对象设计/EMW-SD-BOD-12

| 对象 | 类型 | 生命周期 | 业务能力 |
| --- | --- | --- | --- |
| 安全聚合会话（Secure Aggregation Session） | 概念 | 已规划（Planned） -> 参与者已选择（Participants Selected） -> 已准备加密上下文（Encryption Context Prepared） -> 已完成（Completed） -> 已失败（Failed） | 创建安全聚合会话（Create Secure Aggregation Session）, 选择安全聚合参与者（Select Secure Aggregation Participants）, 准备同态加密上下文（Prepare Homomorphic Encryption Context）, 记录加密模型更新（Record Encrypted Model Update）, 完成同态聚合会话（Complete Homomorphic Aggregation Session）, 失败安全聚合会话（Fail Secure Aggregation Session）, 安全聚合会话目录（Secure Aggregation Session Catalog） |

#### 2.12.2 应用服务与能力设计/EMW-SD-ASCD-12

| 业务能力 | 角色/触发条件 | 输入 | 业务结果 | 读侧结果 | 规则 |
| --- | --- | --- | --- | --- | --- |
| 创建安全聚合会话（Create Secure Aggregation Session） | 在请求聚合时创建会话（Create Session When Aggregation Requested） | 创建安全聚合会话（Create Secure Aggregation Session） | 安全聚合会话已创建（Secure Aggregation Session Created）, 状态: 已规划（Planned） | - | 尚未明确 |
| 选择安全聚合参与者（Select Secure Aggregation Participants） | 会话创建时选择参与者（Select Participants When Session Created） | 选择安全聚合参与者（Select Secure Aggregation Participants） | 安全聚合参与者已选择（Secure Aggregation Participants Selected）, 状态: 参与者已选择（Participants Selected） | - | 尚未明确 |
| 准备同态加密上下文（Prepare Homomorphic Encryption Context） | 安全聚合服务（Secure Aggregation Service） | 准备同态加密上下文（Prepare Homomorphic Encryption Context） | 同态加密上下文已准备（Homomorphic Encryption Context Prepared）, 状态: 已准备加密上下文（Encryption Context Prepared） | - | 尚未明确 |
| 记录加密模型更新（Record Encrypted Model Update） | 安全聚合服务（Secure Aggregation Service） | 记录加密模型更新（Record Encrypted Model Update） | 已接收加密模型更新（Encrypted Model Update Received） | - | 尚未明确 |
| 完成同态聚合会话（Complete Homomorphic Aggregation Session） | 安全聚合服务（Secure Aggregation Service） | 完成同态聚合会话（Complete Homomorphic Aggregation Session） | 安全聚合已完成（Secure Aggregation Completed）, 状态: 已完成（Completed） | - | 尚未明确 |
| 失败安全聚合会话（Fail Secure Aggregation Session） | 安全聚合服务（Secure Aggregation Service） | 失败安全聚合会话（Fail Secure Aggregation Session） | 安全聚合失败（Secure Aggregation Failed）, 状态: 已失败（Failed） | - | 尚未明确 |
| 安全聚合会话目录（Secure Aggregation Session Catalog） | 系统 | - | 尚未明确 | 安全聚合会话目录（Secure Aggregation Session Catalog） | 尚未明确 |

<!-- em:section id="software.slice.创建安全聚合会话（CreateSecureAggregationSession）" source="domain/FederationLearningPlatform/context/SecureAggregation/slice/CreateSecureAggregationSession" -->
##### 2.12.2.1 创建安全聚合会话（Create Secure Aggregation Session）/EMW-SD-SECTION-132

- 归属: 安全聚合会话（Secure Aggregation Session）.
- 角色/触发条件: 在请求聚合时创建会话（Create Session When Aggregation Requested）.
- 输入: 创建安全聚合会话（Create Secure Aggregation Session）.
- 业务结果: 安全聚合会话已创建（Secure Aggregation Session Created）, 状态: 已规划（Planned）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.选择安全聚合参与者（SelectSecureAggregationParticipants）" source="domain/FederationLearningPlatform/context/SecureAggregation/slice/SelectSecureAggregationParticipants" -->
##### 2.12.2.2 选择安全聚合参与者（Select Secure Aggregation Participants）/EMW-SD-SECTION-133

- 归属: 安全聚合会话（Secure Aggregation Session）.
- 角色/触发条件: 会话创建时选择参与者（Select Participants When Session Created）.
- 输入: 选择安全聚合参与者（Select Secure Aggregation Participants）.
- 业务结果: 安全聚合参与者已选择（Secure Aggregation Participants Selected）, 状态: 参与者已选择（Participants Selected）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.准备同态加密上下文（PrepareHomomorphicEncryptionContext）" source="domain/FederationLearningPlatform/context/SecureAggregation/slice/PrepareHomomorphicEncryptionContext" -->
##### 2.12.2.3 准备同态加密上下文（Prepare Homomorphic Encryption Context）/EMW-SD-SECTION-134

- 归属: 安全聚合会话（Secure Aggregation Session）.
- 角色/触发条件: 安全聚合服务（Secure Aggregation Service）.
- 输入: 准备同态加密上下文（Prepare Homomorphic Encryption Context）.
- 业务结果: 同态加密上下文已准备（Homomorphic Encryption Context Prepared）, 状态: 已准备加密上下文（Encryption Context Prepared）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.记录加密模型更新（RecordEncryptedModelUpdate）" source="domain/FederationLearningPlatform/context/SecureAggregation/slice/RecordEncryptedModelUpdate" -->
##### 2.12.2.4 记录加密模型更新（Record Encrypted Model Update）/EMW-SD-SECTION-135

- 归属: 安全聚合会话（Secure Aggregation Session）.
- 角色/触发条件: 安全聚合服务（Secure Aggregation Service）.
- 输入: 记录加密模型更新（Record Encrypted Model Update）.
- 业务结果: 已接收加密模型更新（Encrypted Model Update Received）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.完成同态聚合会话（CompleteHomomorphicAggregationSession）" source="domain/FederationLearningPlatform/context/SecureAggregation/slice/CompleteHomomorphicAggregationSession" -->
##### 2.12.2.5 完成同态聚合会话（Complete Homomorphic Aggregation Session）/EMW-SD-SECTION-136

- 归属: 安全聚合会话（Secure Aggregation Session）.
- 角色/触发条件: 安全聚合服务（Secure Aggregation Service）.
- 输入: 完成同态聚合会话（Complete Homomorphic Aggregation Session）.
- 业务结果: 安全聚合已完成（Secure Aggregation Completed）, 状态: 已完成（Completed）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.失败安全聚合会话（FailSecureAggregationSession）" source="domain/FederationLearningPlatform/context/SecureAggregation/slice/FailSecureAggregationSession" -->
##### 2.12.2.6 失败安全聚合会话（Fail Secure Aggregation Session）/EMW-SD-SECTION-137

- 归属: 安全聚合会话（Secure Aggregation Session）.
- 角色/触发条件: 安全聚合服务（Secure Aggregation Service）.
- 输入: 失败安全聚合会话（Fail Secure Aggregation Session）.
- 业务结果: 安全聚合失败（Secure Aggregation Failed）, 状态: 已失败（Failed）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.安全聚合会话目录（SecureAggregationSessionCatalog）" source="domain/FederationLearningPlatform/context/SecureAggregation/slice/SecureAggregationSessionCatalog" -->
##### 2.12.2.7 安全聚合会话目录（Secure Aggregation Session Catalog）/EMW-SD-SECTION-138

- 归属: 安全聚合会话（Secure Aggregation Session）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 安全聚合会话目录（Secure Aggregation Session Catalog）.

#### 2.12.3 上下文数据设计/EMW-SD-CDD-12

| Read Model | 形态 | 来源事件 | 关键字段 | 查询字段 |
| --- | --- | --- | --- | --- |
| 安全聚合会话目录（Secure Aggregation Session Catalog） | 集合/列表 | 安全聚合会话已创建（Secure Aggregation Session Created）, 安全聚合参与者已选择（Secure Aggregation Participants Selected）, 同态加密上下文已准备（Homomorphic Encryption Context Prepared）, 已接收加密模型更新（Encrypted Model Update Received）, 安全聚合已完成（Secure Aggregation Completed）, 安全聚合失败（Secure Aggregation Failed） | secureAggregationSessionId | 尚未明确 |

<!-- em:section id="software.context.运行时代理操作（RuntimeAgentOperations）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations" -->
### 2.13 运行时代理操作（Runtime Agent Operations）/EMW-SD-SECTION-139

上下文职责: 运行时代理操作是一个独立的可部署组件，在参与者基础设施内运行，并使用本地数据和本地机密执行分派的训练作业。

#### 2.13.1 业务对象设计/EMW-SD-BOD-13

| 对象 | 类型 | 生命周期 | 业务能力 |
| --- | --- | --- | --- |
| 运行时数据集绑定（Runtime Dataset Binding） | 概念 | 已配置（Configured） | 配置运行时数据集绑定（Configure Runtime Dataset Binding）, 运行时数据集绑定目录（Runtime Dataset Binding Catalog） |
| 数据集（Dataset） | 概念 | 已注册（Registered） -> 合约验证已完成（Contract Validation Completed） -> 已批准（Approved） -> 已拒绝（Rejected） -> 审批已撤销（Approval Revoked） | 声明数据集（Declare Dataset）, 验证数据集合约（Validate Dataset Contract）, 重试数据集合约验证（Retry Dataset Contract Validation）, 拒绝数据集用于训练（Reject Dataset For Training）, 批准数据集用于训练（Approve Dataset For Training）, 撤销数据集训练审批（Revoke Dataset Training Approval）, 数据集能力（Dataset Capability）, 数据集就绪状态（Dataset Readiness） |
| 代理数据集访问验证（Agent Dataset Access Validation） | 概念 | 已检查（Checked） | 验证代理数据集访问（Validate Agent Dataset Access）, 重新验证代理数据集访问（Revalidate Agent Dataset Access）, 代理数据集访问验证目录（Agent Dataset Access Validation Catalog） |
| 代理数据集配置文件（Agent Dataset Profile） | 概念 | 已报告（Reported） | 分析代理数据集（Profile Agent Dataset）, 重新分析代理数据集（Reprofile Agent Dataset） |
| 轮次执行（Round Execution） | 概念 | 计划已接收（Plan Received） -> 计划已接受（Plan Accepted） -> 计划已拒绝（Plan Rejected） -> 运行中（Running） -> 启动失败（Start Failed） -> 已重试（Retried） -> 已完成（Completed） -> 已失败（Failed） -> 更新已提交（Update Submitted） -> 运行时引擎已释放（Runtime Engine Released） -> 运行时引擎释放已处理（Runtime Engine Release Handled） | 接收参与者执行计划（Receive Participant Execution Plan）, 接受执行计划（Accept Execution Plan）, 开始轮次执行（Start Round Execution）, 观察运行时引擎作业（Observe Runtime Engine Job）, 完成轮次执行（Complete Round Execution）, 失败轮次执行（Fail Round Execution）, 启动失败后重试轮次执行（Retry Round Execution After Start Failure）, 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure）, 提交代理本地模型更新（Submit Agent Local Model Update）, 完成后释放运行时引擎作业（Release Runtime Engine Job After Completion）, 失败后释放运行时引擎作业（Release Runtime Engine Job After Failure）, 启动失败后释放运行时引擎作业（Release Runtime Engine Job After Start Failure）, 重试失败后释放运行时引擎作业（Release Runtime Engine Job After Retry Failure）, 运行时重试失败后释放运行时引擎作业（Release Runtime Engine Job After Runtime Retry Failure）, 轮次执行目录（Round Execution Catalog） |
| 运行时代理生命周期（Runtime Agent Lifecycle） | 概念 | 引导程序已加载（Bootstrap Loaded） -> 已启动（Started） -> 就绪（Ready） | 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration）, 报告运行时代理已启动（Report Runtime Agent Started）, 报告运行时实例自检已通过（Report Runtime Instance Self Check Passed）, 运行时代理生命周期目录（Runtime Agent Lifecycle Catalog） |
| 代理运行时基础设施连接（Agent Runtime Infrastructure Connection） | 概念 | 已连接（Connected） -> 连接报告失败（Connection Report Failed） | 报告运行时实例已连接（Report Runtime Instance Connected）, 代理运行时基础设施连接目录（Agent Runtime Infrastructure Connection Catalog） |
| 代理运行时遥测（Agent Runtime Telemetry） | 概念 | 尚未明确 | 代理运行时遥测最新（Agent Runtime Telemetry Latest） |
| 代理运行时节点清单（Agent Runtime Node Inventory） | 概念 | 已报告（Reported） | 报告代理运行时节点清单（Report Agent Runtime Node Inventory）, 代理运行时节点清单目录（Agent Runtime Node Inventory Catalog） |
| 代理运行时节点资源遥测（Agent Runtime Node Resource Telemetry） | 概念 | 尚未明确 | 代理运行时节点资源最新（Agent Runtime Node Resource Latest） |

#### 2.13.2 应用服务与能力设计/EMW-SD-ASCD-13

| 业务能力 | 角色/触发条件 | 输入 | 业务结果 | 读侧结果 | 规则 |
| --- | --- | --- | --- | --- | --- |
| 代理组织目录（Agent Organization Directory） | 系统 | - | 尚未明确 | 代理组织目录（Agent Organization Directory） | 尚未明确 |
| 代理功能架构目录（Agent Feature Schema Catalog） | 系统 | - | 尚未明确 | 代理功能架构目录（Agent Feature Schema Catalog） | 尚未明确 |
| 代理字典值目录（Agent Dictionary Value Catalog） | 系统 | - | 尚未明确 | 代理字典值目录（Agent Dictionary Value Catalog） | 尚未明确 |
| 代理运行时身份目录（Agent Runtime Identity Catalog） | 系统 | - | 尚未明确 | 代理运行时身份目录（Agent Runtime Identity Catalog） | 尚未明确 |
| 声明数据集（Declare Dataset） | 数据所有者（Data Owner） | 声明数据集（Declare Dataset） | 数据集已声明（Dataset Declared）, 状态: 已注册（Registered） | - | 使用特征架构快照声明数据集（Declare Dataset With Feature Schema Snapshot）, 特征架构加载失败（Feature Schema Load Failed） |
| 配置运行时数据集绑定（Configure Runtime Dataset Binding） | 节点操作员（Node Operator） | 配置运行时数据集绑定（Configure Runtime Dataset Binding） | 运行时数据集绑定已配置（Runtime Dataset Binding Configured）, 状态: 已配置（Configured） | - | 拒绝重复的运行时数据集绑定（Reject Duplicate Runtime Dataset Binding） |
| 运行时数据集绑定目录（Runtime Dataset Binding Catalog） | 系统 | - | 尚未明确 | 运行时数据集绑定目录（Runtime Dataset Binding Catalog） | 尚未明确 |
| 验证代理数据集访问（Validate Agent Dataset Access） | 运行时代理（Runtime Agent） | 验证代理数据集访问（Validate Agent Dataset Access） | 代理数据集访问已验证（Agent Dataset Access Validated）, 代理数据集访问验证失败（Agent Dataset Access Validation Failed）, 状态: 已检查（Checked） | - | 代理数据集访问已验证（Agent Dataset Access Validated）, 代理数据集访问验证失败（Agent Dataset Access Validation Failed） |
| 重新验证代理数据集访问（Revalidate Agent Dataset Access） | 节点操作员（Node Operator） | 重新验证代理数据集访问（Revalidate Agent Dataset Access） | 代理数据集访问已重新验证（Agent Dataset Access Revalidated）, 代理数据集访问重新验证失败（Agent Dataset Access Revalidation Failed）, 状态: 已检查（Checked） | - | 代理数据集访问已重新验证（Agent Dataset Access Revalidated）, 代理数据集访问重新验证失败（Agent Dataset Access Revalidation Failed） |
| 代理数据集访问验证目录（Agent Dataset Access Validation Catalog） | 系统 | - | 尚未明确 | 代理数据集访问验证目录（Agent Dataset Access Validation Catalog） | 尚未明确 |
| 分析代理数据集（Profile Agent Dataset） | 运行时代理（Runtime Agent） | 分析代理数据集（Profile Agent Dataset） | 代理数据集元数据已报告（Agent Dataset Metadata Reported）, 代理数据集分析失败（Agent Dataset Profiling Failed）, 状态: 已报告（Reported） | - | 代理数据集元数据已报告（Agent Dataset Metadata Reported）, 代理数据集分析失败（Agent Dataset Profiling Failed） |
| 重新分析代理数据集（Reprofile Agent Dataset） | 节点操作员（Node Operator） | 重新分析代理数据集（Reprofile Agent Dataset） | 代理数据集已重新分析（Agent Dataset Reprofiled）, 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed）, 状态: 已报告（Reported） | - | 代理数据集已重新分析（Agent Dataset Reprofiled）, 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed） |
| 验证数据集合约（Validate Dataset Contract） | 自动验证数据集合约（Validate Dataset Contract Automatically） | 验证数据集合约（Validate Dataset Contract） | 数据集合约已验证（Dataset Contract Validated）, 数据集合约验证失败（Dataset Contract Validation Failed）, 状态: 合约验证已完成（Contract Validation Completed） | - | 数据集合约验证成功（Dataset Contract Validation Succeeded）, 数据集合约验证失败（Dataset Contract Validation Failed） |
| 重试数据集合约验证（Retry Dataset Contract Validation） | 数据所有者（Data Owner） | 重试数据集合约验证（Retry Dataset Contract Validation） | 数据集合约已重新验证（Dataset Contract Revalidated）, 数据集合约重新验证失败（Dataset Contract Revalidation Failed）, 状态: 合约验证已完成（Contract Validation Completed） | - | 数据集合约已重新验证（Dataset Contract Revalidated）, 数据集合约重新验证失败（Dataset Contract Revalidation Failed） |
| 拒绝数据集用于训练（Reject Dataset For Training） | 合规专员（Compliance Officer） | 拒绝数据集用于训练（Reject Dataset For Training） | 数据集已拒绝用于训练（Dataset Rejected For Training）, 状态: 已拒绝（Rejected） | - | 尚未明确 |
| 批准数据集用于训练（Approve Dataset For Training） | 合规专员（Compliance Officer） | 批准数据集用于训练（Approve Dataset For Training） | 数据集已批准用于训练（Dataset Approved For Training）, 状态: 已批准（Approved） | - | 批准已验证的数据集（Approve Validated Dataset）, 在数据集合约验证前拒绝批准（Reject Approval Before Contract Validation） |
| 撤销数据集训练审批（Revoke Dataset Training Approval） | 合规专员（Compliance Officer） | 撤销数据集训练审批（Revoke Dataset Training Approval） | 数据集训练批准已撤销（Dataset Training Approval Revoked）, 状态: 审批已撤销（Approval Revoked） | - | 尚未明确 |
| 数据集能力（Dataset Capability） | 系统 | - | 尚未明确 | 数据集能力（Dataset Capability） | 尚未明确 |
| 数据集就绪状态（Dataset Readiness） | 系统 | - | 尚未明确 | 数据集就绪状态（Dataset Readiness） | 尚未明确 |
| 接收参与者执行计划（Receive Participant Execution Plan） | 分发时接收执行计划（Receive Execution Plan When Dispatched） | 接收参与者执行计划（Receive Participant Execution Plan） | 执行计划已接收（Execution Plan Received）, 状态: 计划已接收（Plan Received） | - | 尚未明确 |
| 接受执行计划（Accept Execution Plan） | 收到执行计划时接受执行计划（Accept Execution Plan When Execution Plan Received） | 接受执行计划（Accept Execution Plan） | 执行计划已接受（Execution Plan Accepted）, 执行计划已拒绝（Execution Plan Rejected）, 状态: 计划已接受（Plan Accepted） | - | 执行计划已接受（Execution Plan Accepted）, 执行计划已拒绝（Execution Plan Rejected） |
| 开始轮次执行（Start Round Execution） | 计划接受时启动轮次执行（Start Round Execution When Plan Accepted） | 开始轮次执行（Start Round Execution） | 轮次执行已启动（Round Execution Started）, 轮次执行启动失败（Round Execution Start Failed）, 状态: 运行中（Running） | - | 轮次执行已启动（Round Execution Started）, 轮次执行启动失败（Round Execution Start Failed） |
| 观察运行时引擎作业（Observe Runtime Engine Job） | 轮次执行启动时观察运行时引擎作业（Observe Runtime Engine Job When Round Execution Started） | 观察运行时引擎作业（Observe Runtime Engine Job） | 运行时引擎作业已观察（Runtime Engine Job Observed） | - | 运行时引擎作业已观察运行中（Runtime Engine Job Observed Running）, 运行时引擎作业已观察完成（Runtime Engine Job Observed Completed）, 运行时引擎作业已观察失败（Runtime Engine Job Observed Failed） |
| 完成轮次执行（Complete Round Execution） | 运行时引擎作业完成时完成轮次执行（Complete Round Execution When Runtime Engine Job Completed） | 完成轮次执行（Complete Round Execution） | 轮次执行已完成（Round Execution Completed）, 状态: 已完成（Completed） | - | 尚未明确 |
| 失败轮次执行（Fail Round Execution） | 运行时引擎作业失败时使轮次执行失败（Fail Round Execution When Runtime Engine Job Failed） | 失败轮次执行（Fail Round Execution） | 轮次执行失败（Round Execution Failed）, 状态: 已失败（Failed） | - | 尚未明确 |
| 启动失败后重试轮次执行（Retry Round Execution After Start Failure） | 节点操作员（Node Operator） | 启动失败后重试轮次执行（Retry Round Execution After Start Failure） | 轮次执行启动重试已开始（Round Execution Start Retry Started）, 轮次执行启动重试失败（Round Execution Start Retry Failed）, 状态: 已重试（Retried） | - | 尚未明确 |
| 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure） | 节点操作员（Node Operator） | 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure） | 轮次执行运行时重试已启动（Round Execution Runtime Retry Started）, 轮次执行运行时重试失败（Round Execution Runtime Retry Failed）, 状态: 已重试（Retried） | - | 尚未明确 |
| 提交代理本地模型更新（Submit Agent Local Model Update） | 在轮次执行完成时提交本地模型更新（Submit Local Model Update When Round Execution Completed） | 提交代理本地模型更新（Submit Agent Local Model Update） | 代理本地模型更新已提交（Agent Local Model Update Submitted）, 状态: 更新已提交（Update Submitted） | - | 尚未明确 |
| 完成后释放运行时引擎作业（Release Runtime Engine Job After Completion） | 本地模型更新已提交时发布运行时引擎作业（Release Runtime Engine Job When Local Model Update Submitted） | 完成后释放运行时引擎作业（Release Runtime Engine Job After Completion） | 运行时引擎作业已释放（Runtime Engine Job Released）, 状态: 运行时引擎已释放（Runtime Engine Released） | - | 尚未明确 |
| 失败后释放运行时引擎作业（Release Runtime Engine Job After Failure） | 当轮次执行失败时释放运行时引擎作业（Release Runtime Engine Job When Round Execution Failed） | 失败后释放运行时引擎作业（Release Runtime Engine Job After Failure） | 运行时引擎作业释放失败或已跳过（Runtime Engine Job Release Failed Or Skipped）, 状态: 运行时引擎释放已处理（Runtime Engine Release Handled） | - | 尚未明确 |
| 启动失败后释放运行时引擎作业（Release Runtime Engine Job After Start Failure） | 启动失败时释放运行时引擎作业（Release Runtime Engine Job When Start Failed） | 启动失败后释放运行时引擎作业（Release Runtime Engine Job After Start Failure） | 启动失败后运行时引擎作业释放失败或已跳过（Runtime Engine Job Release Failed Or Skipped After Start Failure）, 状态: 运行时引擎释放已处理（Runtime Engine Release Handled） | - | 尚未明确 |
| 重试失败后释放运行时引擎作业（Release Runtime Engine Job After Retry Failure） | 启动重试失败时释放运行时引擎作业（Release Runtime Engine Job When Start Retry Failed） | 重试失败后释放运行时引擎作业（Release Runtime Engine Job After Retry Failure） | 重试后运行时引擎作业释放失败或已跳过（Runtime Engine Job Release Failed Or Skipped After Retry）, 状态: 运行时引擎释放已处理（Runtime Engine Release Handled） | - | 尚未明确 |
| 运行时重试失败后释放运行时引擎作业（Release Runtime Engine Job After Runtime Retry Failure） | 运行时重试失败时释放运行时引擎作业（Release Runtime Engine Job When Runtime Retry Failed） | 运行时重试失败后释放运行时引擎作业（Release Runtime Engine Job After Runtime Retry Failure） | 运行时重试后运行时引擎作业释放失败或跳过（Runtime Engine Job Release Failed Or Skipped After Runtime Retry）, 状态: 运行时引擎释放已处理（Runtime Engine Release Handled） | - | 尚未明确 |
| 轮次执行目录（Round Execution Catalog） | 系统 | - | 尚未明确 | 轮次执行目录（Round Execution Catalog） | 尚未明确 |
| 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration） | 运行时代理（Runtime Agent） | 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration） | 运行时代理引导配置已加载（Runtime Agent Bootstrap Configuration Loaded）, 运行时代理引导配置加载失败（Runtime Agent Bootstrap Configuration Load Failed）, 状态: 引导程序已加载（Bootstrap Loaded） | - | 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration）, 拒绝缺失的运行时代理引导配置（Reject Missing Runtime Agent Bootstrap Configuration） |
| 报告运行时代理已启动（Report Runtime Agent Started） | 运行时代理（Runtime Agent） | 报告运行时代理已启动（Report Runtime Agent Started） | 运行时代理已启动（Runtime Agent Started）, 状态: 已启动（Started） | - | 尚未明确 |
| 报告运行时实例自检已通过（Report Runtime Instance Self Check Passed） | 运行时代理（Runtime Agent） | 报告运行时实例自检已通过（Report Runtime Instance Self Check Passed） | 运行时实例自检通过（Runtime Instance Self Check Passed）, 状态: 就绪（Ready） | - | 尚未明确 |
| 运行时代理生命周期目录（Runtime Agent Lifecycle Catalog） | 系统 | - | 尚未明确 | 运行时代理生命周期目录（Runtime Agent Lifecycle Catalog） | 尚未明确 |
| 报告运行时实例已连接（Report Runtime Instance Connected） | 在自检后报告运行时实例已连接（Report Runtime Instance Connected After Self Check） | 报告运行时实例已连接（Report Runtime Instance Connected） | 代理运行时连接报告失败（Agent Runtime Connection Report Failed）, 代理运行时连接已建立（Agent Runtime Connection Established）, 状态: 已连接（Connected） | - | 报告运行时实例已连接（Report Runtime Instance Connected）, 报告运行时实例连接失败（Report Runtime Instance Connection Failed） |
| 代理运行时基础设施连接目录（Agent Runtime Infrastructure Connection Catalog） | 系统 | - | 尚未明确 | 代理运行时基础设施连接目录（Agent Runtime Infrastructure Connection Catalog） | 尚未明确 |
| 代理运行时遥测最新（Agent Runtime Telemetry Latest） | 系统 | - | 尚未明确 | 代理运行时遥测最新（Agent Runtime Telemetry Latest） | 尚未明确 |
| 报告代理运行时节点清单（Report Agent Runtime Node Inventory） | 清单更改时报告节点清单（Report Node Inventory When Inventory Changed） | 报告代理运行时节点清单（Report Agent Runtime Node Inventory） | 代理运行时节点清单已报告（Agent Runtime Node Inventory Reported）, 状态: 已报告（Reported） | - | 尚未明确 |
| 代理运行时节点清单目录（Agent Runtime Node Inventory Catalog） | 系统 | - | 尚未明确 | 代理运行时节点清单目录（Agent Runtime Node Inventory Catalog） | 尚未明确 |
| 代理运行时节点资源最新（Agent Runtime Node Resource Latest） | 系统 | - | 尚未明确 | 代理运行时节点资源最新（Agent Runtime Node Resource Latest） | 尚未明确 |

<!-- em:section id="software.slice.代理组织目录（AgentOrganizationDirectory）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/AgentOrganizationDirectory" -->
##### 2.13.2.1 代理组织目录（Agent Organization Directory）/EMW-SD-SECTION-140

- 归属: Context.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 代理组织目录（Agent Organization Directory）.

<!-- em:section id="software.slice.代理功能架构目录（AgentFeatureSchemaCatalog）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/AgentFeatureSchemaCatalog" -->
##### 2.13.2.2 代理功能架构目录（Agent Feature Schema Catalog）/EMW-SD-SECTION-141

- 归属: Context.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 代理功能架构目录（Agent Feature Schema Catalog）.

<!-- em:section id="software.slice.代理字典值目录（AgentDictionaryValueCatalog）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/AgentDictionaryValueCatalog" -->
##### 2.13.2.3 代理字典值目录（Agent Dictionary Value Catalog）/EMW-SD-SECTION-142

- 归属: Context.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 代理字典值目录（Agent Dictionary Value Catalog）.

<!-- em:section id="software.slice.代理运行时身份目录（AgentRuntimeIdentityCatalog）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/AgentRuntimeIdentityCatalog" -->
##### 2.13.2.4 代理运行时身份目录（Agent Runtime Identity Catalog）/EMW-SD-SECTION-143

- 归属: Context.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 代理运行时身份目录（Agent Runtime Identity Catalog）.

<!-- em:section id="software.slice.声明数据集（DeclareDataset）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/DeclareDataset" -->
##### 2.13.2.5 声明数据集（Declare Dataset）/EMW-SD-SECTION-144

- 归属: 数据集（Dataset）.
- 角色/触发条件: 数据所有者（Data Owner）.
- 输入: 声明数据集（Declare Dataset）.
- 业务结果: 数据集已声明（Dataset Declared）, 状态: 已注册（Registered）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 数据集声明需要特征架构快照（Feature Schema Snapshot Required For Dataset Declaration） / 使用特征架构快照声明数据集（Declare Dataset With Feature Schema Snapshot） - 当 声明数据集（Declare Dataset）, 则 数据集已声明（Dataset Declared）.
- 数据集声明需要特征架构快照（Feature Schema Snapshot Required For Dataset Declaration） / 特征架构加载失败（Feature Schema Load Failed） - 当 声明数据集（Declare Dataset）, 则 错误: 特征架构加载失败.

<!-- em:section id="software.slice.配置运行时数据集绑定（ConfigureRuntimeDatasetBinding）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ConfigureRuntimeDatasetBinding" -->
##### 2.13.2.6 配置运行时数据集绑定（Configure Runtime Dataset Binding）/EMW-SD-SECTION-145

- 归属: 运行时数据集绑定（Runtime Dataset Binding）.
- 角色/触发条件: 节点操作员（Node Operator）.
- 输入: 配置运行时数据集绑定（Configure Runtime Dataset Binding）.
- 业务结果: 运行时数据集绑定已配置（Runtime Dataset Binding Configured）, 状态: 已配置（Configured）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 每个运行时数据集的运行时数据集绑定唯一（Runtime Dataset Binding Unique Per Runtime Dataset） / 拒绝重复的运行时数据集绑定（Reject Duplicate Runtime Dataset Binding） - 当 配置运行时数据集绑定（Configure Runtime Dataset Binding）, 则 拒绝: 运行时数据集绑定已存在.

<!-- em:section id="software.slice.运行时数据集绑定目录（RuntimeDatasetBindingCatalog）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/RuntimeDatasetBindingCatalog" -->
##### 2.13.2.7 运行时数据集绑定目录（Runtime Dataset Binding Catalog）/EMW-SD-SECTION-146

- 归属: 运行时数据集绑定（Runtime Dataset Binding）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 运行时数据集绑定目录（Runtime Dataset Binding Catalog）.

<!-- em:section id="software.slice.验证代理数据集访问（ValidateAgentDatasetAccess）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ValidateAgentDatasetAccess" -->
##### 2.13.2.8 验证代理数据集访问（Validate Agent Dataset Access）/EMW-SD-SECTION-147

- 归属: 代理数据集访问验证（Agent Dataset Access Validation）.
- 角色/触发条件: 运行时代理（Runtime Agent）.
- 输入: 验证代理数据集访问（Validate Agent Dataset Access）.
- 业务结果: 代理数据集访问已验证（Agent Dataset Access Validated）, 代理数据集访问验证失败（Agent Dataset Access Validation Failed）, 状态: 已检查（Checked）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 代理数据集访问验证结果（Agent Dataset Access Validation Outcome） / 代理数据集访问已验证（Agent Dataset Access Validated） - 当 验证代理数据集访问（Validate Agent Dataset Access）, 则 代理数据集访问已验证（Agent Dataset Access Validated）.
- 代理数据集访问验证结果（Agent Dataset Access Validation Outcome） / 代理数据集访问验证失败（Agent Dataset Access Validation Failed） - 当 验证代理数据集访问（Validate Agent Dataset Access）, 则 代理数据集访问验证失败（Agent Dataset Access Validation Failed）.

<!-- em:section id="software.slice.重新验证代理数据集访问（RevalidateAgentDatasetAccess）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/RevalidateAgentDatasetAccess" -->
##### 2.13.2.9 重新验证代理数据集访问（Revalidate Agent Dataset Access）/EMW-SD-SECTION-148

- 归属: 代理数据集访问验证（Agent Dataset Access Validation）.
- 角色/触发条件: 节点操作员（Node Operator）.
- 输入: 重新验证代理数据集访问（Revalidate Agent Dataset Access）.
- 业务结果: 代理数据集访问已重新验证（Agent Dataset Access Revalidated）, 代理数据集访问重新验证失败（Agent Dataset Access Revalidation Failed）, 状态: 已检查（Checked）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 代理数据集访问重新验证结果（Agent Dataset Access Revalidation Outcome） / 代理数据集访问已重新验证（Agent Dataset Access Revalidated） - 当 重新验证代理数据集访问（Revalidate Agent Dataset Access）, 则 代理数据集访问已重新验证（Agent Dataset Access Revalidated）.
- 代理数据集访问重新验证结果（Agent Dataset Access Revalidation Outcome） / 代理数据集访问重新验证失败（Agent Dataset Access Revalidation Failed） - 当 重新验证代理数据集访问（Revalidate Agent Dataset Access）, 则 代理数据集访问重新验证失败（Agent Dataset Access Revalidation Failed）.

<!-- em:section id="software.slice.代理数据集访问验证目录（AgentDatasetAccessValidationCatalog）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/AgentDatasetAccessValidationCatalog" -->
##### 2.13.2.10 代理数据集访问验证目录（Agent Dataset Access Validation Catalog）/EMW-SD-SECTION-149

- 归属: 代理数据集访问验证（Agent Dataset Access Validation）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 代理数据集访问验证目录（Agent Dataset Access Validation Catalog）.

<!-- em:section id="software.slice.分析代理数据集（ProfileAgentDataset）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ProfileAgentDataset" -->
##### 2.13.2.11 分析代理数据集（Profile Agent Dataset）/EMW-SD-SECTION-150

- 归属: 代理数据集配置文件（Agent Dataset Profile）.
- 角色/触发条件: 运行时代理（Runtime Agent）.
- 输入: 分析代理数据集（Profile Agent Dataset）.
- 业务结果: 代理数据集元数据已报告（Agent Dataset Metadata Reported）, 代理数据集分析失败（Agent Dataset Profiling Failed）, 状态: 已报告（Reported）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 代理数据集分析结果（Agent Dataset Profiling Outcome） / 代理数据集元数据已报告（Agent Dataset Metadata Reported） - 当 分析代理数据集（Profile Agent Dataset）, 则 代理数据集元数据已报告（Agent Dataset Metadata Reported）.
- 代理数据集分析结果（Agent Dataset Profiling Outcome） / 代理数据集分析失败（Agent Dataset Profiling Failed） - 当 分析代理数据集（Profile Agent Dataset）, 则 代理数据集分析失败（Agent Dataset Profiling Failed）.

<!-- em:section id="software.slice.重新分析代理数据集（ReprofileAgentDataset）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ReprofileAgentDataset" -->
##### 2.13.2.12 重新分析代理数据集（Reprofile Agent Dataset）/EMW-SD-SECTION-151

- 归属: 代理数据集配置文件（Agent Dataset Profile）.
- 角色/触发条件: 节点操作员（Node Operator）.
- 输入: 重新分析代理数据集（Reprofile Agent Dataset）.
- 业务结果: 代理数据集已重新分析（Agent Dataset Reprofiled）, 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed）, 状态: 已报告（Reported）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 代理数据集重新剖析结果（Agent Dataset Reprofiling Outcome） / 代理数据集已重新分析（Agent Dataset Reprofiled） - 当 重新分析代理数据集（Reprofile Agent Dataset）, 则 代理数据集已重新分析（Agent Dataset Reprofiled）.
- 代理数据集重新剖析结果（Agent Dataset Reprofiling Outcome） / 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed） - 当 重新分析代理数据集（Reprofile Agent Dataset）, 则 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed）.

<!-- em:section id="software.slice.验证数据集合约（ValidateDatasetContract）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ValidateDatasetContract" -->
##### 2.13.2.13 验证数据集合约（Validate Dataset Contract）/EMW-SD-SECTION-152

- 归属: 数据集（Dataset）.
- 角色/触发条件: 自动验证数据集合约（Validate Dataset Contract Automatically）.
- 输入: 验证数据集合约（Validate Dataset Contract）.
- 业务结果: 数据集合约已验证（Dataset Contract Validated）, 数据集合约验证失败（Dataset Contract Validation Failed）, 状态: 合约验证已完成（Contract Validation Completed）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 数据集合约验证结果（Dataset Contract Validation Outcome） / 数据集合约验证成功（Dataset Contract Validation Succeeded） - 当 验证数据集合约（Validate Dataset Contract）, 则 数据集合约已验证（Dataset Contract Validated）.
- 数据集合约验证结果（Dataset Contract Validation Outcome） / 数据集合约验证失败（Dataset Contract Validation Failed） - 当 验证数据集合约（Validate Dataset Contract）, 则 数据集合约验证失败（Dataset Contract Validation Failed）.

<!-- em:section id="software.slice.重试数据集合约验证（RetryDatasetContractValidation）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/RetryDatasetContractValidation" -->
##### 2.13.2.14 重试数据集合约验证（Retry Dataset Contract Validation）/EMW-SD-SECTION-153

- 归属: 数据集（Dataset）.
- 角色/触发条件: 数据所有者（Data Owner）.
- 输入: 重试数据集合约验证（Retry Dataset Contract Validation）.
- 业务结果: 数据集合约已重新验证（Dataset Contract Revalidated）, 数据集合约重新验证失败（Dataset Contract Revalidation Failed）, 状态: 合约验证已完成（Contract Validation Completed）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 数据集合约验证重试结果（Dataset Contract Validation Retry Outcome） / 数据集合约已重新验证（Dataset Contract Revalidated） - 当 重试数据集合约验证（Retry Dataset Contract Validation）, 则 数据集合约已重新验证（Dataset Contract Revalidated）.
- 数据集合约验证重试结果（Dataset Contract Validation Retry Outcome） / 数据集合约重新验证失败（Dataset Contract Revalidation Failed） - 当 重试数据集合约验证（Retry Dataset Contract Validation）, 则 数据集合约重新验证失败（Dataset Contract Revalidation Failed）.

<!-- em:section id="software.slice.拒绝数据集用于训练（RejectDatasetForTraining）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/RejectDatasetForTraining" -->
##### 2.13.2.15 拒绝数据集用于训练（Reject Dataset For Training）/EMW-SD-SECTION-154

- 归属: 数据集（Dataset）.
- 角色/触发条件: 合规专员（Compliance Officer）.
- 输入: 拒绝数据集用于训练（Reject Dataset For Training）.
- 业务结果: 数据集已拒绝用于训练（Dataset Rejected For Training）, 状态: 已拒绝（Rejected）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.批准数据集用于训练（ApproveDatasetForTraining）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ApproveDatasetForTraining" -->
##### 2.13.2.16 批准数据集用于训练（Approve Dataset For Training）/EMW-SD-SECTION-155

- 归属: 数据集（Dataset）.
- 角色/触发条件: 合规专员（Compliance Officer）.
- 输入: 批准数据集用于训练（Approve Dataset For Training）.
- 业务结果: 数据集已批准用于训练（Dataset Approved For Training）, 状态: 已批准（Approved）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 仅批准已验证的高质量数据集（Only Validated Quality Datasets Are Approved） / 批准已验证的数据集（Approve Validated Dataset） - 当 批准数据集用于训练（Approve Dataset For Training）, 则 数据集已批准用于训练（Dataset Approved For Training）.
- 仅批准已验证的高质量数据集（Only Validated Quality Datasets Are Approved） / 在数据集合约验证前拒绝批准（Reject Approval Before Contract Validation） - 当 批准数据集用于训练（Approve Dataset For Training）, 则 拒绝: 需要数据集合约验证.

<!-- em:section id="software.slice.撤销数据集训练审批（RevokeDatasetTrainingApproval）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/RevokeDatasetTrainingApproval" -->
##### 2.13.2.17 撤销数据集训练审批（Revoke Dataset Training Approval）/EMW-SD-SECTION-156

- 归属: 数据集（Dataset）.
- 角色/触发条件: 合规专员（Compliance Officer）.
- 输入: 撤销数据集训练审批（Revoke Dataset Training Approval）.
- 业务结果: 数据集训练批准已撤销（Dataset Training Approval Revoked）, 状态: 审批已撤销（Approval Revoked）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.数据集能力（DatasetCapability）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/DatasetCapability" -->
##### 2.13.2.18 数据集能力（Dataset Capability）/EMW-SD-SECTION-157

- 归属: 数据集（Dataset）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 数据集能力（Dataset Capability）.

<!-- em:section id="software.slice.数据集就绪状态（DatasetReadiness）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/DatasetReadiness" -->
##### 2.13.2.19 数据集就绪状态（Dataset Readiness）/EMW-SD-SECTION-158

- 归属: 数据集（Dataset）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 数据集就绪状态（Dataset Readiness）.

<!-- em:section id="software.slice.接收参与者执行计划（ReceiveParticipantExecutionPlan）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ReceiveParticipantExecutionPlan" -->
##### 2.13.2.20 接收参与者执行计划（Receive Participant Execution Plan）/EMW-SD-SECTION-159

- 归属: 轮次执行（Round Execution）.
- 角色/触发条件: 分发时接收执行计划（Receive Execution Plan When Dispatched）.
- 输入: 接收参与者执行计划（Receive Participant Execution Plan）.
- 业务结果: 执行计划已接收（Execution Plan Received）, 状态: 计划已接收（Plan Received）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.接受执行计划（AcceptExecutionPlan）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/AcceptExecutionPlan" -->
##### 2.13.2.21 接受执行计划（Accept Execution Plan）/EMW-SD-SECTION-160

- 归属: 轮次执行（Round Execution）.
- 角色/触发条件: 收到执行计划时接受执行计划（Accept Execution Plan When Execution Plan Received）.
- 输入: 接受执行计划（Accept Execution Plan）.
- 业务结果: 执行计划已接受（Execution Plan Accepted）, 执行计划已拒绝（Execution Plan Rejected）, 状态: 计划已接受（Plan Accepted）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 执行计划本地接受结果（Execution Plan Local Acceptance Outcome） / 执行计划已接受（Execution Plan Accepted） - 当 接受执行计划（Accept Execution Plan）, 则 执行计划已接受（Execution Plan Accepted）.
- 执行计划本地接受结果（Execution Plan Local Acceptance Outcome） / 执行计划已拒绝（Execution Plan Rejected） - 当 接受执行计划（Accept Execution Plan）, 则 执行计划已拒绝（Execution Plan Rejected）.

<!-- em:section id="software.slice.开始轮次执行（StartRoundExecution）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/StartRoundExecution" -->
##### 2.13.2.22 开始轮次执行（Start Round Execution）/EMW-SD-SECTION-161

- 归属: 轮次执行（Round Execution）.
- 角色/触发条件: 计划接受时启动轮次执行（Start Round Execution When Plan Accepted）.
- 输入: 开始轮次执行（Start Round Execution）.
- 业务结果: 轮次执行已启动（Round Execution Started）, 轮次执行启动失败（Round Execution Start Failed）, 状态: 运行中（Running）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 轮次执行运行时引擎启动结果（Round Execution Runtime Engine Start Outcome） / 轮次执行已启动（Round Execution Started） - 当 开始轮次执行（Start Round Execution）, 则 轮次执行已启动（Round Execution Started）.
- 轮次执行运行时引擎启动结果（Round Execution Runtime Engine Start Outcome） / 轮次执行启动失败（Round Execution Start Failed） - 当 开始轮次执行（Start Round Execution）, 则 轮次执行启动失败（Round Execution Start Failed）.

<!-- em:section id="software.slice.观察运行时引擎作业（ObserveRuntimeEngineJob）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ObserveRuntimeEngineJob" -->
##### 2.13.2.23 观察运行时引擎作业（Observe Runtime Engine Job）/EMW-SD-SECTION-162

- 归属: 轮次执行（Round Execution）.
- 角色/触发条件: 轮次执行启动时观察运行时引擎作业（Observe Runtime Engine Job When Round Execution Started）.
- 输入: 观察运行时引擎作业（Observe Runtime Engine Job）.
- 业务结果: 运行时引擎作业已观察（Runtime Engine Job Observed）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 运行时引擎作业观察结果（Runtime Engine Job Observation Outcome） / 运行时引擎作业已观察运行中（Runtime Engine Job Observed Running） - 当 观察运行时引擎作业（Observe Runtime Engine Job）, 则 运行时引擎作业已观察（Runtime Engine Job Observed）.
- 运行时引擎作业观察结果（Runtime Engine Job Observation Outcome） / 运行时引擎作业已观察完成（Runtime Engine Job Observed Completed） - 当 观察运行时引擎作业（Observe Runtime Engine Job）, 则 运行时引擎作业已观察（Runtime Engine Job Observed）.
- 运行时引擎作业观察结果（Runtime Engine Job Observation Outcome） / 运行时引擎作业已观察失败（Runtime Engine Job Observed Failed） - 当 观察运行时引擎作业（Observe Runtime Engine Job）, 则 运行时引擎作业已观察（Runtime Engine Job Observed）.

<!-- em:section id="software.slice.完成轮次执行（CompleteRoundExecution）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/CompleteRoundExecution" -->
##### 2.13.2.24 完成轮次执行（Complete Round Execution）/EMW-SD-SECTION-163

- 归属: 轮次执行（Round Execution）.
- 角色/触发条件: 运行时引擎作业完成时完成轮次执行（Complete Round Execution When Runtime Engine Job Completed）.
- 输入: 完成轮次执行（Complete Round Execution）.
- 业务结果: 轮次执行已完成（Round Execution Completed）, 状态: 已完成（Completed）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.失败轮次执行（FailRoundExecution）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/FailRoundExecution" -->
##### 2.13.2.25 失败轮次执行（Fail Round Execution）/EMW-SD-SECTION-164

- 归属: 轮次执行（Round Execution）.
- 角色/触发条件: 运行时引擎作业失败时使轮次执行失败（Fail Round Execution When Runtime Engine Job Failed）.
- 输入: 失败轮次执行（Fail Round Execution）.
- 业务结果: 轮次执行失败（Round Execution Failed）, 状态: 已失败（Failed）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.启动失败后重试轮次执行（RetryRoundExecutionAfterStartFailure）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/RetryRoundExecutionAfterStartFailure" -->
##### 2.13.2.26 启动失败后重试轮次执行（Retry Round Execution After Start Failure）/EMW-SD-SECTION-165

- 归属: 轮次执行（Round Execution）.
- 角色/触发条件: 节点操作员（Node Operator）.
- 输入: 启动失败后重试轮次执行（Retry Round Execution After Start Failure）.
- 业务结果: 轮次执行启动重试已开始（Round Execution Start Retry Started）, 轮次执行启动重试失败（Round Execution Start Retry Failed）, 状态: 已重试（Retried）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.运行时失败后重试轮次执行（RetryRoundExecutionAfterRuntimeFailure）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/RetryRoundExecutionAfterRuntimeFailure" -->
##### 2.13.2.27 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure）/EMW-SD-SECTION-166

- 归属: 轮次执行（Round Execution）.
- 角色/触发条件: 节点操作员（Node Operator）.
- 输入: 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure）.
- 业务结果: 轮次执行运行时重试已启动（Round Execution Runtime Retry Started）, 轮次执行运行时重试失败（Round Execution Runtime Retry Failed）, 状态: 已重试（Retried）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.提交代理本地模型更新（SubmitAgentLocalModelUpdate）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/SubmitAgentLocalModelUpdate" -->
##### 2.13.2.28 提交代理本地模型更新（Submit Agent Local Model Update）/EMW-SD-SECTION-167

- 归属: 轮次执行（Round Execution）.
- 角色/触发条件: 在轮次执行完成时提交本地模型更新（Submit Local Model Update When Round Execution Completed）.
- 输入: 提交代理本地模型更新（Submit Agent Local Model Update）.
- 业务结果: 代理本地模型更新已提交（Agent Local Model Update Submitted）, 状态: 更新已提交（Update Submitted）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.完成后释放运行时引擎作业（ReleaseRuntimeEngineJobAfterCompletion）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ReleaseRuntimeEngineJobAfterCompletion" -->
##### 2.13.2.29 完成后释放运行时引擎作业（Release Runtime Engine Job After Completion）/EMW-SD-SECTION-168

- 归属: 轮次执行（Round Execution）.
- 角色/触发条件: 本地模型更新已提交时发布运行时引擎作业（Release Runtime Engine Job When Local Model Update Submitted）.
- 输入: 完成后释放运行时引擎作业（Release Runtime Engine Job After Completion）.
- 业务结果: 运行时引擎作业已释放（Runtime Engine Job Released）, 状态: 运行时引擎已释放（Runtime Engine Released）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.失败后释放运行时引擎作业（ReleaseRuntimeEngineJobAfterFailure）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ReleaseRuntimeEngineJobAfterFailure" -->
##### 2.13.2.30 失败后释放运行时引擎作业（Release Runtime Engine Job After Failure）/EMW-SD-SECTION-169

- 归属: 轮次执行（Round Execution）.
- 角色/触发条件: 当轮次执行失败时释放运行时引擎作业（Release Runtime Engine Job When Round Execution Failed）.
- 输入: 失败后释放运行时引擎作业（Release Runtime Engine Job After Failure）.
- 业务结果: 运行时引擎作业释放失败或已跳过（Runtime Engine Job Release Failed Or Skipped）, 状态: 运行时引擎释放已处理（Runtime Engine Release Handled）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.启动失败后释放运行时引擎作业（ReleaseRuntimeEngineJobAfterStartFailure）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ReleaseRuntimeEngineJobAfterStartFailure" -->
##### 2.13.2.31 启动失败后释放运行时引擎作业（Release Runtime Engine Job After Start Failure）/EMW-SD-SECTION-170

- 归属: 轮次执行（Round Execution）.
- 角色/触发条件: 启动失败时释放运行时引擎作业（Release Runtime Engine Job When Start Failed）.
- 输入: 启动失败后释放运行时引擎作业（Release Runtime Engine Job After Start Failure）.
- 业务结果: 启动失败后运行时引擎作业释放失败或已跳过（Runtime Engine Job Release Failed Or Skipped After Start Failure）, 状态: 运行时引擎释放已处理（Runtime Engine Release Handled）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.重试失败后释放运行时引擎作业（ReleaseRuntimeEngineJobAfterRetryFailure）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ReleaseRuntimeEngineJobAfterRetryFailure" -->
##### 2.13.2.32 重试失败后释放运行时引擎作业（Release Runtime Engine Job After Retry Failure）/EMW-SD-SECTION-171

- 归属: 轮次执行（Round Execution）.
- 角色/触发条件: 启动重试失败时释放运行时引擎作业（Release Runtime Engine Job When Start Retry Failed）.
- 输入: 重试失败后释放运行时引擎作业（Release Runtime Engine Job After Retry Failure）.
- 业务结果: 重试后运行时引擎作业释放失败或已跳过（Runtime Engine Job Release Failed Or Skipped After Retry）, 状态: 运行时引擎释放已处理（Runtime Engine Release Handled）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.运行时重试失败后释放运行时引擎作业（ReleaseRuntimeEngineJobAfterRuntimeRetryFailure）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ReleaseRuntimeEngineJobAfterRuntimeRetryFailure" -->
##### 2.13.2.33 运行时重试失败后释放运行时引擎作业（Release Runtime Engine Job After Runtime Retry Failure）/EMW-SD-SECTION-172

- 归属: 轮次执行（Round Execution）.
- 角色/触发条件: 运行时重试失败时释放运行时引擎作业（Release Runtime Engine Job When Runtime Retry Failed）.
- 输入: 运行时重试失败后释放运行时引擎作业（Release Runtime Engine Job After Runtime Retry Failure）.
- 业务结果: 运行时重试后运行时引擎作业释放失败或跳过（Runtime Engine Job Release Failed Or Skipped After Runtime Retry）, 状态: 运行时引擎释放已处理（Runtime Engine Release Handled）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.轮次执行目录（RoundExecutionCatalog）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/RoundExecutionCatalog" -->
##### 2.13.2.34 轮次执行目录（Round Execution Catalog）/EMW-SD-SECTION-173

- 归属: 轮次执行（Round Execution）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 轮次执行目录（Round Execution Catalog）.

<!-- em:section id="software.slice.加载运行时代理引导配置（LoadRuntimeAgentBootstrapConfiguration）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/LoadRuntimeAgentBootstrapConfiguration" -->
##### 2.13.2.35 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration）/EMW-SD-SECTION-174

- 归属: 运行时代理生命周期（Runtime Agent Lifecycle）.
- 角色/触发条件: 运行时代理（Runtime Agent）.
- 输入: 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration）.
- 业务结果: 运行时代理引导配置已加载（Runtime Agent Bootstrap Configuration Loaded）, 运行时代理引导配置加载失败（Runtime Agent Bootstrap Configuration Load Failed）, 状态: 引导程序已加载（Bootstrap Loaded）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 运行时代理启动引导配置（Runtime Agent Startup Bootstrap Configuration） / 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration） - 当 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration）, 则 运行时代理引导配置已加载（Runtime Agent Bootstrap Configuration Loaded）.
- 运行时代理启动引导配置（Runtime Agent Startup Bootstrap Configuration） / 拒绝缺失的运行时代理引导配置（Reject Missing Runtime Agent Bootstrap Configuration） - 当 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration）, 则 运行时代理引导配置加载失败（Runtime Agent Bootstrap Configuration Load Failed）.

<!-- em:section id="software.slice.报告运行时代理已启动（ReportRuntimeAgentStarted）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ReportRuntimeAgentStarted" -->
##### 2.13.2.36 报告运行时代理已启动（Report Runtime Agent Started）/EMW-SD-SECTION-175

- 归属: 运行时代理生命周期（Runtime Agent Lifecycle）.
- 角色/触发条件: 运行时代理（Runtime Agent）.
- 输入: 报告运行时代理已启动（Report Runtime Agent Started）.
- 业务结果: 运行时代理已启动（Runtime Agent Started）, 状态: 已启动（Started）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.报告运行时实例自检已通过（ReportRuntimeInstanceSelfCheckPassed）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ReportRuntimeInstanceSelfCheckPassed" -->
##### 2.13.2.37 报告运行时实例自检已通过（Report Runtime Instance Self Check Passed）/EMW-SD-SECTION-176

- 归属: 运行时代理生命周期（Runtime Agent Lifecycle）.
- 角色/触发条件: 运行时代理（Runtime Agent）.
- 输入: 报告运行时实例自检已通过（Report Runtime Instance Self Check Passed）.
- 业务结果: 运行时实例自检通过（Runtime Instance Self Check Passed）, 状态: 就绪（Ready）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.运行时代理生命周期目录（RuntimeAgentLifecycleCatalog）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/RuntimeAgentLifecycleCatalog" -->
##### 2.13.2.38 运行时代理生命周期目录（Runtime Agent Lifecycle Catalog）/EMW-SD-SECTION-177

- 归属: 运行时代理生命周期（Runtime Agent Lifecycle）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 运行时代理生命周期目录（Runtime Agent Lifecycle Catalog）.

<!-- em:section id="software.slice.报告运行时实例已连接（ReportRuntimeInstanceConnected）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ReportRuntimeInstanceConnected" -->
##### 2.13.2.39 报告运行时实例已连接（Report Runtime Instance Connected）/EMW-SD-SECTION-178

- 归属: 代理运行时基础设施连接（Agent Runtime Infrastructure Connection）.
- 角色/触发条件: 在自检后报告运行时实例已连接（Report Runtime Instance Connected After Self Check）.
- 输入: 报告运行时实例已连接（Report Runtime Instance Connected）.
- 业务结果: 代理运行时连接报告失败（Agent Runtime Connection Report Failed）, 代理运行时连接已建立（Agent Runtime Connection Established）, 状态: 已连接（Connected）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 运行时代理平台连接报告结果（Runtime Agent Platform Connection Reporting Outcome） / 报告运行时实例已连接（Report Runtime Instance Connected） - 当 报告运行时实例已连接（Report Runtime Instance Connected）, 则 代理运行时连接已建立（Agent Runtime Connection Established）.
- 运行时代理平台连接报告结果（Runtime Agent Platform Connection Reporting Outcome） / 报告运行时实例连接失败（Report Runtime Instance Connection Failed） - 当 报告运行时实例已连接（Report Runtime Instance Connected）, 则 代理运行时连接报告失败（Agent Runtime Connection Report Failed）.

<!-- em:section id="software.slice.代理运行时基础设施连接目录（AgentRuntimeInfrastructureConnectionCatalog）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/AgentRuntimeInfrastructureConnectionCatalog" -->
##### 2.13.2.40 代理运行时基础设施连接目录（Agent Runtime Infrastructure Connection Catalog）/EMW-SD-SECTION-179

- 归属: 代理运行时基础设施连接（Agent Runtime Infrastructure Connection）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 代理运行时基础设施连接目录（Agent Runtime Infrastructure Connection Catalog）.

<!-- em:section id="software.slice.代理运行时遥测最新（AgentRuntimeTelemetryLatest）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/AgentRuntimeTelemetryLatest" -->
##### 2.13.2.41 代理运行时遥测最新（Agent Runtime Telemetry Latest）/EMW-SD-SECTION-180

- 归属: 代理运行时遥测（Agent Runtime Telemetry）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 代理运行时遥测最新（Agent Runtime Telemetry Latest）.

<!-- em:section id="software.slice.报告代理运行时节点清单（ReportAgentRuntimeNodeInventory）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ReportAgentRuntimeNodeInventory" -->
##### 2.13.2.42 报告代理运行时节点清单（Report Agent Runtime Node Inventory）/EMW-SD-SECTION-181

- 归属: 代理运行时节点清单（Agent Runtime Node Inventory）.
- 角色/触发条件: 清单更改时报告节点清单（Report Node Inventory When Inventory Changed）.
- 输入: 报告代理运行时节点清单（Report Agent Runtime Node Inventory）.
- 业务结果: 代理运行时节点清单已报告（Agent Runtime Node Inventory Reported）, 状态: 已报告（Reported）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.代理运行时节点清单目录（AgentRuntimeNodeInventoryCatalog）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/AgentRuntimeNodeInventoryCatalog" -->
##### 2.13.2.43 代理运行时节点清单目录（Agent Runtime Node Inventory Catalog）/EMW-SD-SECTION-182

- 归属: 代理运行时节点清单（Agent Runtime Node Inventory）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 代理运行时节点清单目录（Agent Runtime Node Inventory Catalog）.

<!-- em:section id="software.slice.代理运行时节点资源最新（AgentRuntimeNodeResourceLatest）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/AgentRuntimeNodeResourceLatest" -->
##### 2.13.2.44 代理运行时节点资源最新（Agent Runtime Node Resource Latest）/EMW-SD-SECTION-183

- 归属: 代理运行时节点资源遥测（Agent Runtime Node Resource Telemetry）.
- 角色/触发条件: 系统.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 代理运行时节点资源最新（Agent Runtime Node Resource Latest）.

#### 2.13.3 上下文数据设计/EMW-SD-CDD-13

| Read Model | 形态 | 来源事件 | 关键字段 | 查询字段 |
| --- | --- | --- | --- | --- |
| 代理组织目录（Agent Organization Directory） | 集合/列表 | 尚未明确 | organizationId | 尚未明确 |
| 代理功能架构目录（Agent Feature Schema Catalog） | 集合/列表 | 尚未明确 | featureSchemaId | 尚未明确 |
| 代理字典值目录（Agent Dictionary Value Catalog） | 集合/列表 | 尚未明确 | dictionaryValueId | dictionaryCode, active |
| 代理运行时身份目录（Agent Runtime Identity Catalog） | 集合/列表 | 尚未明确 | runtimeId | 尚未明确 |
| 运行时数据集绑定目录（Runtime Dataset Binding Catalog） | 集合/列表 | 数据集已声明（Dataset Declared）, 运行时数据集绑定已配置（Runtime Dataset Binding Configured） | runtimeDatasetBindingId | 尚未明确 |
| 代理数据集访问验证目录（Agent Dataset Access Validation Catalog） | 集合/列表 | 数据集已声明（Dataset Declared）, 代理数据集访问已验证（Agent Dataset Access Validated）, 代理数据集访问验证失败（Agent Dataset Access Validation Failed）, 代理数据集访问已重新验证（Agent Dataset Access Revalidated）, 代理数据集访问重新验证失败（Agent Dataset Access Revalidation Failed） | datasetAccessValidationId | 尚未明确 |
| 数据集能力（Dataset Capability） | 集合/列表 | 组织已注册（Organization Registered）, 特征架构已定义（Feature Schema Defined）, 数据集已声明（Dataset Declared）, 代理数据集元数据已报告（Agent Dataset Metadata Reported）, 代理数据集分析失败（Agent Dataset Profiling Failed）, 代理数据集已重新分析（Agent Dataset Reprofiled）, 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed）, 数据集合约已验证（Dataset Contract Validated）, 数据集合约验证失败（Dataset Contract Validation Failed）, 数据集合约已重新验证（Dataset Contract Revalidated）, 数据集合约重新验证失败（Dataset Contract Revalidation Failed）, 数据集已拒绝用于训练（Dataset Rejected For Training）, 数据集已批准用于训练（Dataset Approved For Training）, 数据集训练批准已撤销（Dataset Training Approval Revoked） | datasetId | 尚未明确 |
| 数据集就绪状态（Dataset Readiness） | 集合/列表 | 组织已注册（Organization Registered）, 特征架构已定义（Feature Schema Defined）, 数据集已声明（Dataset Declared）, 代理数据集元数据已报告（Agent Dataset Metadata Reported）, 代理数据集分析失败（Agent Dataset Profiling Failed）, 代理数据集已重新分析（Agent Dataset Reprofiled）, 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed）, 数据集合约已验证（Dataset Contract Validated）, 数据集合约验证失败（Dataset Contract Validation Failed）, 数据集合约已重新验证（Dataset Contract Revalidated）, 数据集合约重新验证失败（Dataset Contract Revalidation Failed）, 数据集已拒绝用于训练（Dataset Rejected For Training）, 数据集已批准用于训练（Dataset Approved For Training）, 数据集训练批准已撤销（Dataset Training Approval Revoked）, 代理数据集访问已验证（Agent Dataset Access Validated）, 代理数据集访问验证失败（Agent Dataset Access Validation Failed）, 代理数据集访问已重新验证（Agent Dataset Access Revalidated）, 代理数据集访问重新验证失败（Agent Dataset Access Revalidation Failed）, 运行时连接已建立（Runtime Connection Established）, 已检测到运行时代理离线（Runtime Agent Offline Detected）, 运行时代理已恢复（Runtime Agent Recovered） | datasetId | 尚未明确 |
| 轮次执行目录（Round Execution Catalog） | 集合/列表 | 参与者执行计划已分发（Participant Execution Plan Dispatched）, 执行计划已接收（Execution Plan Received）, 执行计划已接受（Execution Plan Accepted）, 执行计划已拒绝（Execution Plan Rejected）, 轮次执行已启动（Round Execution Started）, 轮次执行启动失败（Round Execution Start Failed）, 运行时引擎作业已观察（Runtime Engine Job Observed）, 轮次执行启动重试已开始（Round Execution Start Retry Started）, 轮次执行启动重试失败（Round Execution Start Retry Failed）, 轮次执行已完成（Round Execution Completed）, 轮次执行失败（Round Execution Failed）, 轮次执行运行时重试已启动（Round Execution Runtime Retry Started）, 轮次执行运行时重试失败（Round Execution Runtime Retry Failed）, 代理本地模型更新已提交（Agent Local Model Update Submitted）, 运行时引擎作业已释放（Runtime Engine Job Released）, 运行时引擎作业释放失败或已跳过（Runtime Engine Job Release Failed Or Skipped）, 启动失败后运行时引擎作业释放失败或已跳过（Runtime Engine Job Release Failed Or Skipped After Start Failure）, 重试后运行时引擎作业释放失败或已跳过（Runtime Engine Job Release Failed Or Skipped After Retry）, 运行时重试后运行时引擎作业释放失败或跳过（Runtime Engine Job Release Failed Or Skipped After Runtime Retry） | roundExecutionId | 尚未明确 |
| 运行时代理生命周期目录（Runtime Agent Lifecycle Catalog） | 集合/列表 | 运行时代理引导配置已加载（Runtime Agent Bootstrap Configuration Loaded）, 运行时代理引导配置加载失败（Runtime Agent Bootstrap Configuration Load Failed）, 运行时代理已启动（Runtime Agent Started）, 运行时实例自检通过（Runtime Instance Self Check Passed） | runtimeAgentId | 尚未明确 |
| 代理运行时基础设施连接目录（Agent Runtime Infrastructure Connection Catalog） | 集合/列表 | 代理运行时连接已建立（Agent Runtime Connection Established）, 代理运行时连接报告失败（Agent Runtime Connection Report Failed） | runtimeInfrastructureId | 尚未明确 |
| 代理运行时遥测最新（Agent Runtime Telemetry Latest） | 集合/列表 | 尚未明确 | nodeId | 尚未明确 |
| 代理运行时节点清单目录（Agent Runtime Node Inventory Catalog） | 集合/列表 | 组织已注册（Organization Registered）, 代理运行时节点清单已报告（Agent Runtime Node Inventory Reported） | runtimeNodeInventoryReportId | 尚未明确 |
| 代理运行时节点资源最新（Agent Runtime Node Resource Latest） | 集合/列表 | 尚未明确 | nodeId | 尚未明确 |

#### 2.13.4 值类型设计/EMW-SD-VTD-5

| 值类型 | 类型 | 基础类型 | 约束/字段 |
| --- | --- | --- | --- |
| Feature Definition | object | FeatureDefinition | featureName: String, dataType: String, required: Boolean, nullable: Boolean, description: String, validationRules: String, defaultValue: String, isIdentifier: Boolean, isSensitive: Boolean, encodingStrategy: String, featureTags: String |
| Label Definition | object | LabelDefinition | labelName: String, dataType: String, cardinality: Int, classLabels: String, isMultilabel: Boolean, description: String, validationRules: String, defaultValue: String |

<!-- em:section id="software.context.身份访问管理（IdentityAccessManagement）" source="domain/IdentityAccessManagement/context/IdentityAccessManagement" -->
### 2.14 身份访问管理（Identity Access Management）/EMW-SD-SECTION-184

上下文职责: 尚未明确

#### 2.14.1 业务对象设计/EMW-SD-BOD-14

| 对象 | 类型 | 生命周期 | 业务能力 |
| --- | --- | --- | --- |
| 用户账户（User Account） | 概念 | 已激活（Active） -> 已停用（Deactivated） | 注册用户账户（Register User Account）, 生成用户账户登录密码（Generate User Account Login Password）, 停用用户账户（Deactivate User Account）, 为用户分配角色（Assign Role To User）, 用户账户目录（User Account Catalogs） |
| 角色（Role） | 概念 | 已注册（Registered） | 注册角色（Register Role）, 为角色授予权限（Grant Permission To Role）, 角色目录（Role Catalogs） |
| 权限（Permission） | 概念 | 已注册（Registered） | 注册权限（Register Permission）, 权限目录（Permission Catalogs） |
| 用户角色分配（User Role Assignment） | 概念 | 尚未明确 | 用户角色分配目录（User Role Assignment Catalog） |
| 角色权限授予（Role Permission Grant） | 概念 | 尚未明确 | 角色权限授予目录（Role Permission Grant Catalog） |
| 服务账户 API 令牌（Service Account Api Token） | 概念 | 已颁发（Issued） | 颁发服务账户 API 令牌（Issue Service Account Api Token）, 服务账户 API 令牌目录（Service Account Api Token Catalogs） |

#### 2.14.2 应用服务与能力设计/EMW-SD-ASCD-14

| 业务能力 | 角色/触发条件 | 输入 | 业务结果 | 读侧结果 | 规则 |
| --- | --- | --- | --- | --- | --- |
| 注册用户账户（Register User Account） | 身份管理员（Identity Administrator） | 注册用户账户（Register User Account） | 用户账户已注册（User Account Registered）, 状态: 已激活（Active） | - | 拒绝重复用户名（Reject Duplicate Username） |
| 停用用户账户（Deactivate User Account） | 身份管理员（Identity Administrator） | 停用用户账户（Deactivate User Account） | 用户账户已停用（User Account Deactivated）, 状态: 已停用（Deactivated） | - | 尚未明确 |
| 生成用户账户登录密码（Generate User Account Login Password） | 身份管理员（Identity Administrator） | 生成用户账户登录密码（Generate User Account Login Password） | 用户账户登录密码已生成（User Account Login Password Generated） | - | 尚未明确 |
| 注册角色（Register Role） | 身份管理员（Identity Administrator） | 注册角色（Register Role） | 角色已注册（Role Registered）, 状态: 已注册（Registered） | - | 尚未明确 |
| 注册权限（Register Permission） | 身份管理员（Identity Administrator） | 注册权限（Register Permission） | 权限已注册（Permission Registered）, 状态: 已注册（Registered） | - | 尚未明确 |
| 为角色授予权限（Grant Permission To Role） | 身份管理员（Identity Administrator） | 为角色授予权限（Grant Permission To Role） | 权限已授予角色（Permission Granted To Role）, 已从角色撤销权限（Permission Revoked From Role）, 状态: 已注册（Registered） | - | 尚未明确 |
| 为用户分配角色（Assign Role To User） | 身份管理员（Identity Administrator） | 为用户分配角色（Assign Role To User） | 角色已分配给用户（Role Assigned To User）, 角色已从用户取消分配（Role Unassigned From User）, 状态: 已激活（Active） | - | 尚未明确 |
| 颁发服务账户 API 令牌（Issue Service Account Api Token） | 超级管理员（Super Administrator） | 颁发服务账户 API 令牌（Issue Service Account Api Token） | 服务账户 API 令牌已颁发（Service Account Api Token Issued）, 状态: 已颁发（Issued） | - | 超级管理员为服务账户颁发令牌（Super Administrator Issues Token For Service Account）, 已颁发事件存储摘要而非明文令牌（Issued Event Stores Digest Instead Of Plaintext Token）, 已颁发令牌捕获当前权限（Issued Token Captures Current Permissions） |
| 用户账户目录（User Account Catalogs） | 身份管理员（Identity Administrator） | - | 尚未明确 | 用户账户目录（User Account Catalog） | 尚未明确 |
| 角色目录（Role Catalogs） | 身份管理员（Identity Administrator） | - | 尚未明确 | 角色目录（Role Catalog） | 尚未明确 |
| 权限目录（Permission Catalogs） | 身份管理员（Identity Administrator） | - | 尚未明确 | 权限目录（Permission Catalog） | 尚未明确 |
| 用户角色分配目录（User Role Assignment Catalog） | 身份管理员（Identity Administrator） | - | 尚未明确 | 用户角色分配目录（User Role Assignment Catalog） | 尚未明确 |
| 角色权限授予目录（Role Permission Grant Catalog） | 身份管理员（Identity Administrator） | - | 尚未明确 | 角色权限授予目录（Role Permission Grant Catalog） | 尚未明确 |
| 服务账户 API 令牌目录（Service Account Api Token Catalogs） | 超级管理员（Super Administrator） | - | 尚未明确 | 服务账户 API 令牌目录（Service Account Api Token Catalog） | 尚未明确 |

<!-- em:section id="software.slice.注册用户账户（RegisterUserAccount）" source="domain/IdentityAccessManagement/context/IdentityAccessManagement/slice/RegisterUserAccount" -->
##### 2.14.2.1 注册用户账户（Register User Account）/EMW-SD-SECTION-185

- 归属: 用户账户（User Account）.
- 角色/触发条件: 身份管理员（Identity Administrator）.
- 输入: 注册用户账户（Register User Account）.
- 业务结果: 用户账户已注册（User Account Registered）, 状态: 已激活（Active）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 用户账户用户名唯一（User Account Username Unique） / 拒绝重复用户名（Reject Duplicate Username） - 当 注册用户账户（Register User Account）, 则 拒绝: 用户名已存在。.

<!-- em:section id="software.slice.停用用户账户（DeactivateUserAccount）" source="domain/IdentityAccessManagement/context/IdentityAccessManagement/slice/DeactivateUserAccount" -->
##### 2.14.2.2 停用用户账户（Deactivate User Account）/EMW-SD-SECTION-186

- 归属: 用户账户（User Account）.
- 角色/触发条件: 身份管理员（Identity Administrator）.
- 输入: 停用用户账户（Deactivate User Account）.
- 业务结果: 用户账户已停用（User Account Deactivated）, 状态: 已停用（Deactivated）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.生成用户账户登录密码（GenerateUserAccountLoginPassword）" source="domain/IdentityAccessManagement/context/IdentityAccessManagement/slice/GenerateUserAccountLoginPassword" -->
##### 2.14.2.3 生成用户账户登录密码（Generate User Account Login Password）/EMW-SD-SECTION-187

- 归属: 用户账户（User Account）.
- 角色/触发条件: 身份管理员（Identity Administrator）.
- 输入: 生成用户账户登录密码（Generate User Account Login Password）.
- 业务结果: 用户账户登录密码已生成（User Account Login Password Generated）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.注册角色（RegisterRole）" source="domain/IdentityAccessManagement/context/IdentityAccessManagement/slice/RegisterRole" -->
##### 2.14.2.4 注册角色（Register Role）/EMW-SD-SECTION-188

- 归属: 角色（Role）.
- 角色/触发条件: 身份管理员（Identity Administrator）.
- 输入: 注册角色（Register Role）.
- 业务结果: 角色已注册（Role Registered）, 状态: 已注册（Registered）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.注册权限（RegisterPermission）" source="domain/IdentityAccessManagement/context/IdentityAccessManagement/slice/RegisterPermission" -->
##### 2.14.2.5 注册权限（Register Permission）/EMW-SD-SECTION-189

- 归属: 权限（Permission）.
- 角色/触发条件: 身份管理员（Identity Administrator）.
- 输入: 注册权限（Register Permission）.
- 业务结果: 权限已注册（Permission Registered）, 状态: 已注册（Registered）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.为角色授予权限（GrantPermissionToRole）" source="domain/IdentityAccessManagement/context/IdentityAccessManagement/slice/GrantPermissionToRole" -->
##### 2.14.2.6 为角色授予权限（Grant Permission To Role）/EMW-SD-SECTION-190

- 归属: 角色（Role）.
- 角色/触发条件: 身份管理员（Identity Administrator）.
- 输入: 为角色授予权限（Grant Permission To Role）.
- 业务结果: 权限已授予角色（Permission Granted To Role）, 已从角色撤销权限（Permission Revoked From Role）, 状态: 已注册（Registered）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.为用户分配角色（AssignRoleToUser）" source="domain/IdentityAccessManagement/context/IdentityAccessManagement/slice/AssignRoleToUser" -->
##### 2.14.2.7 为用户分配角色（Assign Role To User）/EMW-SD-SECTION-191

- 归属: 用户账户（User Account）.
- 角色/触发条件: 身份管理员（Identity Administrator）.
- 输入: 为用户分配角色（Assign Role To User）.
- 业务结果: 角色已分配给用户（Role Assigned To User）, 角色已从用户取消分配（Role Unassigned From User）, 状态: 已激活（Active）.
- 读侧结果: 尚未明确.

<!-- em:section id="software.slice.颁发服务账户 API 令牌（IssueServiceAccountApiToken）" source="domain/IdentityAccessManagement/context/IdentityAccessManagement/slice/IssueServiceAccountApiToken" -->
##### 2.14.2.8 颁发服务账户 API 令牌（Issue Service Account Api Token）/EMW-SD-A

- 归属: 服务账户 API 令牌（Service Account Api Token）.
- 角色/触发条件: 超级管理员（Super Administrator）.
- 输入: 颁发服务账户 API 令牌（Issue Service Account Api Token）.
- 业务结果: 服务账户 API 令牌已颁发（Service Account Api Token Issued）, 状态: 已颁发（Issued）.
- 读侧结果: 尚未明确.

**规则与异常设计**

- 仅超级管理员可颁发服务账户 API 令牌（Only Super Administrator Can Issue Service Account Api Token） / 超级管理员为服务账户颁发令牌（Super Administrator Issues Token For Service Account） - 当 颁发服务账户 API 令牌（Issue Service Account Api Token）, 则 服务账户 API 令牌已颁发（Service Account Api Token Issued）.
- 服务账户 API 令牌明文仅返回一次（Service Account Api Token Plaintext Returned Once） / 已颁发事件存储摘要而非明文令牌（Issued Event Stores Digest Instead Of Plaintext Token） - 当 颁发服务账户 API 令牌（Issue Service Account Api Token）, 则 服务账户 API 令牌已颁发（Service Account Api Token Issued）.
- 服务账户 API 令牌捕获权限快照（Service Account Api Token Captures Permission Snapshot） / 已颁发令牌捕获当前权限（Issued Token Captures Current Permissions） - 当 颁发服务账户 API 令牌（Issue Service Account Api Token）, 则 服务账户 API 令牌已颁发（Service Account Api Token Issued）.

<!-- em:section id="software.slice.用户账户目录（UserAccountCatalogs）" source="domain/IdentityAccessManagement/context/IdentityAccessManagement/slice/UserAccountCatalogs" -->
##### 2.14.2.9 用户账户目录（User Account Catalogs）/EMW-SD-SECTION-192

- 归属: 用户账户（User Account）.
- 角色/触发条件: 身份管理员（Identity Administrator）.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 用户账户目录（User Account Catalog）.

<!-- em:section id="software.slice.角色目录（RoleCatalogs）" source="domain/IdentityAccessManagement/context/IdentityAccessManagement/slice/RoleCatalogs" -->
##### 2.14.2.10 角色目录（Role Catalogs）/EMW-SD-SECTION-193

- 归属: 角色（Role）.
- 角色/触发条件: 身份管理员（Identity Administrator）.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 角色目录（Role Catalog）.

<!-- em:section id="software.slice.权限目录（PermissionCatalogs）" source="domain/IdentityAccessManagement/context/IdentityAccessManagement/slice/PermissionCatalogs" -->
##### 2.14.2.11 权限目录（Permission Catalogs）/EMW-SD-SECTION-194

- 归属: 权限（Permission）.
- 角色/触发条件: 身份管理员（Identity Administrator）.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 权限目录（Permission Catalog）.

<!-- em:section id="software.slice.用户角色分配目录（UserRoleAssignmentCatalog）" source="domain/IdentityAccessManagement/context/IdentityAccessManagement/slice/UserRoleAssignmentCatalog" -->
##### 2.14.2.12 用户角色分配目录（User Role Assignment Catalog）/EMW-SD-SECTION-195

- 归属: 用户角色分配（User Role Assignment）.
- 角色/触发条件: 身份管理员（Identity Administrator）.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 用户角色分配目录（User Role Assignment Catalog）.

<!-- em:section id="software.slice.角色权限授予目录（RolePermissionGrantCatalog）" source="domain/IdentityAccessManagement/context/IdentityAccessManagement/slice/RolePermissionGrantCatalog" -->
##### 2.14.2.13 角色权限授予目录（Role Permission Grant Catalog）/EMW-SD-SECTION-196

- 归属: 角色权限授予（Role Permission Grant）.
- 角色/触发条件: 身份管理员（Identity Administrator）.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 角色权限授予目录（Role Permission Grant Catalog）.

<!-- em:section id="software.slice.服务账户 API 令牌目录（ServiceAccountApiTokenCatalogs）" source="domain/IdentityAccessManagement/context/IdentityAccessManagement/slice/ServiceAccountApiTokenCatalogs" -->
##### 2.14.2.14 服务账户 API 令牌目录（Service Account Api Token Catalogs）/EMW-SD-A-2

- 归属: 服务账户 API 令牌（Service Account Api Token）.
- 角色/触发条件: 超级管理员（Super Administrator）.
- 输入: 尚未明确.
- 业务结果: 尚未明确.
- 读侧结果: 服务账户 API 令牌目录（Service Account Api Token Catalog）.

#### 2.14.3 上下文数据设计/EMW-SD-CDD-14

| Read Model | 形态 | 来源事件 | 关键字段 | 查询字段 |
| --- | --- | --- | --- | --- |
| 用户账户目录（User Account Catalog） | 集合/列表 | 用户账户已注册（User Account Registered）, 用户账户登录密码已生成（User Account Login Password Generated）, 用户账户已停用（User Account Deactivated） | userAccountId | 尚未明确 |
| 角色目录（Role Catalog） | 集合/列表 | 角色已注册（Role Registered） | roleId | 尚未明确 |
| 权限目录（Permission Catalog） | 集合/列表 | 权限已注册（Permission Registered） | permissionId | 尚未明确 |
| 用户角色分配目录（User Role Assignment Catalog） | 集合/列表 | 角色已分配给用户（Role Assigned To User） | userAccountId, roleCode | 尚未明确 |
| 角色权限授予目录（Role Permission Grant Catalog） | 集合/列表 | 权限已授予角色（Permission Granted To Role） | roleCode, permissionCode | 尚未明确 |
| 服务账户 API 令牌目录（Service Account Api Token Catalog） | 集合/列表 | 服务账户 API 令牌已颁发（Service Account Api Token Issued） | apiTokenId | 尚未明确 |

## 三、接口与集成设计/EMW-SD-IID

### 3.1 页面设计/EMW-SD-UID

| 页面 | 交互 | 业务能力 | 输入 | 读侧结果 |
| --- | --- | --- | --- | --- |
| 组织注册屏幕（Organization Registration Screen） | unspecified | 注册组织（Register Organization） | 注册组织（Register Organization） | - |
| 组织管理员屏幕（Organization Admin Screen） | unspecified | 激活组织（Activate Organization） | 激活组织（Activate Organization） | - |
| 组织管理员屏幕（Organization Admin Screen） | unspecified | 停用组织（Deactivate Organization） | 停用组织（Deactivate Organization） | - |
| 组织管理员屏幕（Organization Admin Screen） | unspecified | 重新激活组织（Reactivate Organization） | 重新激活组织（Reactivate Organization） | - |
| 组织目录（Organization Directory） | unspecified | 组织目录（Organization Directory） | - | 组织目录（Organization Directory） |
| 组织管理员屏幕（Organization Admin Screen） | unspecified | 将用户账户绑定到组织（Bind User Account To Organization） | 将用户账户绑定到组织（Bind User Account To Organization） | - |
| 组织管理员屏幕（Organization Admin Screen） | unspecified | 用户组织成员资格目录（User Organization Membership Directory） | - | 用户组织成员资格目录（User Organization Membership Directory） |
| 联邦设置屏幕（Federation Setup Screen） | form | 创建联邦（Create Federation） | 创建联邦（Create Federation） | - |
| 联邦设置屏幕（Federation Setup Screen） | unspecified | 激活联邦（Activate Federation） | 激活联邦（Activate Federation） | - |
| 联邦设置屏幕（Federation Setup Screen） | confirm | 暂停联邦（Suspend Federation） | 暂停联邦（Suspend Federation） | - |
| 联邦设置屏幕（Federation Setup Screen） | unspecified | 重新激活联邦（Reactivate Federation） | 重新激活联邦（Reactivate Federation） | - |
| 参与者邀请屏幕（Participant Invitation Screen） | dialog | 邀请参与者（Invite Participant） | 邀请参与者（Invite Participant） | - |
| 成员资格审核屏幕（Membership Review Screen） | dialog | 批准参与者（Approve Participant） | 批准参与者（Approve Participant） | - |
| 成员资格审核屏幕（Membership Review Screen） | dialog | 拒绝参与者（Reject Participant） | 拒绝参与者（Reject Participant） | - |
| 参与者邀请屏幕（Participant Invitation Screen） | confirm | 撤销参与者邀请（Revoke Participant Invitation） | 撤销参与者邀请（Revoke Participant Invitation） | - |
| 成员资格审核屏幕（Membership Review Screen） | dialog | 暂停参与者（Suspend Participant） | 暂停参与者（Suspend Participant） | - |
| 成员资格审核屏幕（Membership Review Screen） | confirm | 移除参与者（Remove Participant） | 移除参与者（Remove Participant） | - |
| 字典目录屏幕（Dictionary Catalog Screen） | unspecified | 注册字典（Register Dictionary） | 注册字典（Register Dictionary） | - |
| 字典目录屏幕（Dictionary Catalog Screen） | unspecified | 更新字典（Update Dictionary） | 更新字典（Update Dictionary） | - |
| 字典目录屏幕（Dictionary Catalog Screen） | confirm | 归档字典（Archive Dictionary） | 归档字典（Archive Dictionary） | - |
| 字典值目录屏幕（Dictionary Value Catalog Screen） | unspecified | 添加字典值（Add Dictionary Value） | 添加字典值（Add Dictionary Value） | - |
| 字典值目录屏幕（Dictionary Value Catalog Screen） | confirm | 停用字典值（Disable Dictionary Value） | 停用字典值（Disable Dictionary Value） | - |
| 字典值目录屏幕（Dictionary Value Catalog Screen） | unspecified | 启用字典值（Enable Dictionary Value） | 启用字典值（Enable Dictionary Value） | - |
| 文件上传对话框（File Upload Dialog） | unspecified | 上传文件（Upload File） | 上传文件（Upload File） | - |
| 已上传文件目录屏幕（Uploaded File Catalog Screen） | unspecified | 下载文件（Download File） | 下载文件（Download File） | - |
| 文件上传对话框（File Upload Dialog） | unspecified | 丢弃文件（Discard File） | 丢弃文件（Discard File） | - |
| 运行时基础设施包目录界面（Runtime Infrastructure Package Catalog Screen） | unspecified | 注册运行时基础设施包（Register Runtime Infrastructure Package） | 注册运行时基础设施包（Register Runtime Infrastructure Package） | - |
| 运行时安装计划页面（Runtime Installation Plan Screen） | unspecified | 创建运行时安装计划（Create Runtime Installation Plan） | 创建运行时安装计划（Create Runtime Installation Plan） | - |
| 运行时安装计划页面（Runtime Installation Plan Screen） | unspecified | 运行时安装计划目录（Runtime Installation Plan Catalog） | - | 运行时安装计划目录（Runtime Installation Plan Catalog） |
| 运行时安装指南页面（Runtime Installation Guide Screen） | unspecified | 运行时安装指南（Runtime Installation Guide） | - | 运行时安装指南（Runtime Installation Guide） |
| 运行时安装指南页面（Runtime Installation Guide Screen） | unspecified | 确认运行时基础设施已就绪（Confirm Runtime Infrastructure Prepared） | 确认运行时基础设施已就绪（Confirm Runtime Infrastructure Prepared） | - |
| 运行时安装计划页面（Runtime Installation Plan Screen） | unspecified | 验证运行时基础设施（Verify Runtime Infrastructure） | 验证运行时基础设施（Verify Runtime Infrastructure） | - |
| 运行时安装计划页面（Runtime Installation Plan Screen） | unspecified | 部署运行时代理（Deploy Runtime Agent） | 部署运行时代理（Deploy Runtime Agent） | - |
| 运行时安装计划页面（Runtime Installation Plan Screen） | confirm | 重试运行时基础设施验证（Retry Runtime Infrastructure Verification） | 重试运行时基础设施验证（Retry Runtime Infrastructure Verification） | - |
| 运行时安装计划页面（Runtime Installation Plan Screen） | confirm | 重试运行时代理部署（Retry Runtime Agent Deployment） | 重试运行时代理部署（Retry Runtime Agent Deployment） | - |
| 特征架构目录界面（Feature Schema Catalog Screen） | unspecified | 定义特征架构（Define Feature Schema） | 定义特征架构（Define Feature Schema） | - |
| 特征架构目录界面（Feature Schema Catalog Screen） | confirm | 发布特征架构（Publish Feature Schema） | 发布特征架构（Publish Feature Schema） | - |
| 特征架构目录界面（Feature Schema Catalog Screen） | confirm | 弃用特征架构（Deprecate Feature Schema） | 弃用特征架构（Deprecate Feature Schema） | - |
| 特征架构目录界面（Feature Schema Catalog Screen） | confirm | 停用特征架构（Retire Feature Schema） | 停用特征架构（Retire Feature Schema） | - |
| 特征架构目录界面（Feature Schema Catalog Screen） | confirm | 取代特征架构版本（Supersede Feature Schema Version） | 取代特征架构版本（Supersede Feature Schema Version） | - |
| 特征架构目录界面（Feature Schema Catalog Screen） | confirm | 标记当前推荐的特征架构版本（Mark Current Recommended Feature Schema Version） | 标记当前推荐的特征架构版本（Mark Current Recommended Feature Schema Version） | - |
| 模型仓库屏幕（Model Repository Screen） | unspecified | 注册模型制品（Register Model Artifact） | 注册模型制品（Register Model Artifact） | - |
| 模型仓库屏幕（Model Repository Screen） | unspecified | 下载模型工件（Download Model Artifact） | 下载模型工件（Download Model Artifact） | - |
| 运行时引擎配置文件界面（Runtime Engine Profile Screen） | unspecified | 注册运行时引擎配置文件（Register Runtime Engine Profile） | 注册运行时引擎配置文件（Register Runtime Engine Profile） | - |
| 训练运行配置屏幕（Training Run Configuration Screen） | unspecified | 定义训练运行配置（Define Training Run Configuration） | 定义训练运行配置（Define Training Run Configuration） | - |
| 训练运行配置屏幕（Training Run Configuration Screen） | unspecified | 更新训练运行配置（Update Training Run Configuration） | 更新训练运行配置（Update Training Run Configuration） | - |
| 训练作业创建屏幕（Training Job Creation Screen） | unspecified | 创建训练作业（Create Training Job） | 创建训练作业（Create Training Job） | - |
| 训练提交屏幕（Training Submission Screen） | unspecified | 提交训练作业（Submit Training Job） | 提交训练作业（Submit Training Job） | - |
| 训练操作屏幕（Training Operations Screen） | unspecified | 暂停训练作业（Pause Training Job） | 暂停训练作业（Pause Training Job） | - |
| 训练操作屏幕（Training Operations Screen） | unspecified | 恢复训练作业（Resume Training Job） | 恢复训练作业（Resume Training Job） | - |
| 训练操作屏幕（Training Operations Screen） | unspecified | 取消训练作业（Cancel Training Job） | 取消训练作业（Cancel Training Job） | - |
| 训练作业仪表板（Training Job Dashboard） | confirm | 重试训练轮次参与者选择（Retry Training Round Participant Selection） | 重试训练轮次参与者选择（Retry Training Round Participant Selection） | - |
| 模型审批屏幕（Model Approval Screen） | unspecified | 记录模型评估包（Record Model Evaluation Package） | 记录模型评估包（Record Model Evaluation Package） | - |
| 模型审批屏幕（Model Approval Screen） | unspecified | 批准模型（Approve Model） | 批准模型（Approve Model） | - |
| 模型发布屏幕（Model Release Screen） | unspecified | 提升模型至生产（Promote Model To Production） | 提升模型至生产（Promote Model To Production） | - |
| 模型发布屏幕（Model Release Screen） | unspecified | 回滚模型（Rollback Model） | 回滚模型（Rollback Model） | - |
| 模型发布屏幕（Model Release Screen） | unspecified | 停用模型（Retire Model） | 停用模型（Retire Model） | - |
| 运行时健康仪表板（Runtime Health Dashboard） | unspecified | 确认训练告警（Acknowledge Training Alert） | 确认训练告警（Acknowledge Training Alert） | - |
| 运行时健康仪表板（Runtime Health Dashboard） | unspecified | 解决训练告警（Resolve Training Alert） | 解决训练告警（Resolve Training Alert） | - |
| 运行时安装计划页面（Runtime Installation Plan Screen） | unspecified | 激活运行时标识（Activate Runtime Identity） | 激活运行时标识（Activate Runtime Identity） | - |
| 运行时身份屏幕（Runtime Identity Screen） | confirm | 撤销运行时标识（Revoke Runtime Identity） | 撤销运行时标识（Revoke Runtime Identity） | - |
| 数据集注册屏幕（Dataset Registration Screen） | unspecified | 声明数据集（Declare Dataset） | 声明数据集（Declare Dataset） | - |
| 运行时数据集绑定屏幕（Runtime Dataset Binding Screen） | unspecified | 配置运行时数据集绑定（Configure Runtime Dataset Binding） | 配置运行时数据集绑定（Configure Runtime Dataset Binding） | - |
| 运行时数据集绑定屏幕（Runtime Dataset Binding Screen） | unspecified | 重新验证代理数据集访问（Revalidate Agent Dataset Access） | 重新验证代理数据集访问（Revalidate Agent Dataset Access） | - |
| 运行时数据集绑定屏幕（Runtime Dataset Binding Screen） | unspecified | 重新分析代理数据集（Reprofile Agent Dataset） | 重新分析代理数据集（Reprofile Agent Dataset） | - |
| 数据集注册屏幕（Dataset Registration Screen） | unspecified | 重试数据集合约验证（Retry Dataset Contract Validation） | 重试数据集合约验证（Retry Dataset Contract Validation） | - |
| 数据集审批屏幕（Dataset Approval Screen） | unspecified | 拒绝数据集用于训练（Reject Dataset For Training） | 拒绝数据集用于训练（Reject Dataset For Training） | - |
| 数据集审批屏幕（Dataset Approval Screen） | unspecified | 批准数据集用于训练（Approve Dataset For Training） | 批准数据集用于训练（Approve Dataset For Training） | - |
| 数据集审批屏幕（Dataset Approval Screen） | unspecified | 撤销数据集训练审批（Revoke Dataset Training Approval） | 撤销数据集训练审批（Revoke Dataset Training Approval） | - |
| 轮次执行屏幕（Round Execution Screen） | unspecified | 启动失败后重试轮次执行（Retry Round Execution After Start Failure） | 启动失败后重试轮次执行（Retry Round Execution After Start Failure） | - |
| 轮次执行屏幕（Round Execution Screen） | unspecified | 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure） | 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure） | - |
| 加载运行时代理启动配置确认（Load Runtime Agent Bootstrap Configuration Confirm） | confirm | 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration） | 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration） | - |

### 3.2 集成设计/EMW-SD-ID

| 集成 | 限界上下文 | 来源 | 目标 |
| --- | --- | --- | --- |
| 安全聚合提供程序（Secure Aggregation Provider） | 训练编排（Training Orchestration） | Training Round | Secure Aggregation Service |
| 参与者轮次运行时（Participant Round Runtime） | 训练编排（Training Orchestration） | Training Round | Runtime Agent |
| 聚合节点运行时（Aggregation Node Runtime） | 训练编排（Training Orchestration） | Training Round | Aggregation Runtime |
| 轮次运行时引擎（Round Runtime Engine） | 运行时代理操作（Runtime Agent Operations） | Round Execution | Runtime Engine |
| 轮次运行时引擎观察（Round Runtime Engine Observation） | 运行时代理操作（Runtime Agent Operations） | Round Execution | Runtime Engine |
| 已重试轮次运行时引擎（Retried Round Runtime Engine） | 运行时代理操作（Runtime Agent Operations） | Round Execution | Runtime Engine |
| 已重试轮次运行时引擎观察（Retried Round Runtime Engine Observation） | 运行时代理操作（Runtime Agent Operations） | Round Execution | Runtime Engine |
| 运行时失败已重试轮次运行时引擎（Runtime Failure Retried Round Runtime Engine） | 运行时代理操作（Runtime Agent Operations） | Round Execution | Runtime Engine |
| 运行时失败已重试轮次运行时引擎观察（Runtime Failure Retried Round Runtime Engine Observation） | 运行时代理操作（Runtime Agent Operations） | Round Execution | Runtime Engine |
| 平台数据集元数据报告（Platform Dataset Metadata Reporting） | 运行时代理操作（Runtime Agent Operations） | Agent Dataset Profile | Dataset Governance |
| 平台数据集重新分析元数据报告（Platform Dataset Reprofiled Metadata Reporting） | 运行时代理操作（Runtime Agent Operations） | Agent Dataset Profile | Dataset Governance |
| 平台轮次更新报告（Platform Round Update Reporting） | 运行时代理操作（Runtime Agent Operations） | Round Execution | Training Orchestration |
| 平台运行时基础设施连接报告（Platform Runtime Infrastructure Connection Reporting） | 运行时代理操作（Runtime Agent Operations） | Agent Runtime Infrastructure Connection | Runtime Provisioning |
| 平台运行时节点清单报告（Platform Runtime Node Inventory Reporting） | 运行时代理操作（Runtime Agent Operations） | Agent Runtime Node Inventory | Runtime Monitoring |
| Runtime Agent | 运行时预配（Runtime Provisioning） | runtime-agent | command:Request Dataset Access, event:Dataset Access Validated |

## 四、异常与规则设计/EMW-SD-ERD

- 组织管理（Organization Management） / 注册组织（Register Organization）: 组织名称唯一（Organization Name Unique） / 拒绝重复组织（Reject Duplicate Organization） - 当 注册组织（Register Organization）, 则 拒绝: 组织已存在.
- 组织管理（Organization Management） / 将用户账户绑定到组织（Bind User Account To Organization）: 用户账户组织绑定唯一（User Account Organization Binding Unique） / 拒绝重复的用户组织绑定（Reject Duplicate User Organization Binding） - 当 将用户账户绑定到组织（Bind User Account To Organization）, 则 拒绝: 用户账户已绑定到此组织。.
- 联邦管理（Federation Management） / 创建联邦（Create Federation）: 联邦名称唯一（Federation Name Unique） / 拒绝重复联邦（Reject Duplicate Federation） - 当 创建联邦（Create Federation）, 则 拒绝: 联邦已存在.
- 联邦管理（Federation Management） / 批准参与者（Approve Participant）: 仅已邀请的激活组织加入联邦（Only Invited Active Organizations Join Federation） / 批准已邀请的参与者（Approve Invited Participant） - 当 批准参与者（Approve Participant）, 则 参与者已加入（Participant Joined）.
- 联邦管理（Federation Management） / 批准参与者（Approve Participant）: 仅已邀请的激活组织加入联邦（Only Invited Active Organizations Join Federation） / 拒绝未邀请参与者的审批（Reject Uninvited Participant Approval） - 当 批准参与者（Approve Participant）, 则 拒绝: 需要参与者邀请.
- 字典维护（Dictionary Maintenance） / 注册字典（Register Dictionary）: 字典代码唯一（Dictionary Code Unique） / 拒绝重复字典（Reject Duplicate Dictionary） - 当 注册字典（Register Dictionary）, 则 拒绝: 字典已存在.
- 文件上传（File Upload） / 上传文件（Upload File）: 已上传文件已持久化并已记录（Uploaded File Is Persisted And Recorded） / 为模型导入上传文件（Upload File For Model Import） - 当 上传文件（Upload File）, 则 文件已上传（File Uploaded）.
- 文件上传（File Upload） / 将文件标记为已引用（Mark File Referenced）: 被引用的文件受保护以防用户丢弃（Referenced File Is Protected From User Discard） / 通过模型导入标记文件已引用（Mark File Referenced By Model Import） - 当 将文件标记为已引用（Mark File Referenced）, 则 文件已引用（File Referenced）.
- 文件上传（File Upload） / 下载文件（Download File）: 仅可用的已存储文件可被下载（Only Available Stored Files Can Be Downloaded） / 授权可用文件下载（Authorize Available File Download） - 当 下载文件（Download File）, 则 文件下载已授权（File Download Authorized）.
- 文件上传（File Upload） / 下载文件（Download File）: 仅可用的已存储文件可被下载（Only Available Stored Files Can Be Downloaded） / 拒绝过期文件下载（Reject Expired File Download） - 当 下载文件（Download File）, 则 错误: 文件不可下载。.
- 文件上传（File Upload） / 丢弃文件（Discard File）: 仅可丢弃未引用的文件（Only Unreferenced Files Can Be Discarded） / 丢弃未引用文件（Discard Unreferenced File） - 当 丢弃文件（Discard File）, 则 文件已丢弃（File Discarded）.
- 文件上传（File Upload） / 丢弃文件（Discard File）: 仅可丢弃未引用的文件（Only Unreferenced Files Can Be Discarded） / 拒绝消费后丢弃（Reject Discard After Consumption） - 当 丢弃文件（Discard File）, 则 拒绝: 文件已被引用.
- 文件上传（File Upload） / 使文件过期（Expire File）: 过期文件不可被引用（Expired File Cannot Be Referenced） / 使已上传文件过期（Expire Uploaded File） - 当 使文件过期（Expire File）, 则 文件已过期（File Expired）.
- 文件上传（File Upload） / 使文件过期（Expire File）: 过期文件不可被引用（Expired File Cannot Be Referenced） / 过期后拒绝消费（Reject Consumption After Expiration） - 当 将文件标记为已引用（Mark File Referenced）, 则 拒绝: 文件已过期.
- 运行时预配（Runtime Provisioning） / 确认运行时基础设施已就绪（Confirm Runtime Infrastructure Prepared）: 验证前运行时基础设施已准备（Runtime Infrastructure Prepared Before Verification） / 确认运行时基础设施已就绪（Confirm Runtime Infrastructure Prepared） - 当 确认运行时基础设施已就绪（Confirm Runtime Infrastructure Prepared）, 则 运行时基础设施已准备（Runtime Infrastructure Prepared）.
- 运行时预配（Runtime Provisioning） / 验证运行时基础设施（Verify Runtime Infrastructure）: 运行时基础设施验证结果（Runtime Infrastructure Verification Outcome） / 运行时基础设施验证通过（Runtime Infrastructure Verification Passed） - 当 验证运行时基础设施（Verify Runtime Infrastructure）, 则 运行时基础设施已验证（Runtime Infrastructure Verified）.
- 运行时预配（Runtime Provisioning） / 验证运行时基础设施（Verify Runtime Infrastructure）: 运行时基础设施验证结果（Runtime Infrastructure Verification Outcome） / 运行时基础设施验证失败（Runtime Infrastructure Verification Failed） - 当 验证运行时基础设施（Verify Runtime Infrastructure）, 则 运行时基础设施验证失败（Runtime Infrastructure Verification Failed）.
- 运行时预配（Runtime Provisioning） / 部署运行时代理（Deploy Runtime Agent）: 运行时代理部署结果（Runtime Agent Deployment Outcome） / 运行时代理部署成功（Runtime Agent Deployment Succeeded） - 当 部署运行时代理（Deploy Runtime Agent）, 则 运行时代理安装成功（Runtime Agent Installation Succeeded）.
- 运行时预配（Runtime Provisioning） / 部署运行时代理（Deploy Runtime Agent）: 运行时代理部署结果（Runtime Agent Deployment Outcome） / 运行时代理部署失败（Runtime Agent Deployment Failed） - 当 部署运行时代理（Deploy Runtime Agent）, 则 运行时代理安装失败（Runtime Agent Installation Failed）.
- 运行时预配（Runtime Provisioning） / 重试运行时基础设施验证（Retry Runtime Infrastructure Verification）: 运行时基础设施验证重试结果（Runtime Infrastructure Verification Retry Outcome） / 运行时基础设施验证重试成功（Runtime Infrastructure Verification Retry Succeeded） - 当 重试运行时基础设施验证（Retry Runtime Infrastructure Verification）, 则 运行时基础设施验证重试成功（Runtime Infrastructure Verification Retry Succeeded）.
- 运行时预配（Runtime Provisioning） / 重试运行时基础设施验证（Retry Runtime Infrastructure Verification）: 运行时基础设施验证重试结果（Runtime Infrastructure Verification Retry Outcome） / 运行时基础设施验证重试失败（Runtime Infrastructure Verification Retry Failed） - 当 重试运行时基础设施验证（Retry Runtime Infrastructure Verification）, 则 运行时基础设施验证重试失败（Runtime Infrastructure Verification Retry Failed）.
- 运行时预配（Runtime Provisioning） / 重试运行时基础设施验证（Retry Runtime Infrastructure Verification）: 运行时基础设施验证重试结果（Runtime Infrastructure Verification Retry Outcome） / 在已验证状态下拒绝运行时基础设施验证重试（Reject Runtime Infrastructure Verification Retry While Verified） - 当 重试运行时基础设施验证（Retry Runtime Infrastructure Verification）, 则 拒绝: 不允许运行时基础设施验证重试.
- 运行时预配（Runtime Provisioning） / 重试运行时代理部署（Retry Runtime Agent Deployment）: 运行时代理部署重试结果（Runtime Agent Deployment Retry Outcome） / 部署失败后运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded After Failed Deployment） - 当 重试运行时代理部署（Retry Runtime Agent Deployment）, 则 运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）.
- 运行时预配（Runtime Provisioning） / 重试运行时代理部署（Retry Runtime Agent Deployment）: 运行时代理部署重试结果（Runtime Agent Deployment Retry Outcome） / 离线后运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded After Offline） - 当 重试运行时代理部署（Retry Runtime Agent Deployment）, 则 运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）.
- 运行时预配（Runtime Provisioning） / 重试运行时代理部署（Retry Runtime Agent Deployment）: 运行时代理部署重试结果（Runtime Agent Deployment Retry Outcome） / 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed） - 当 重试运行时代理部署（Retry Runtime Agent Deployment）, 则 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed）.
- 运行时预配（Runtime Provisioning） / 重试运行时代理部署（Retry Runtime Agent Deployment）: 运行时代理部署重试结果（Runtime Agent Deployment Retry Outcome） / 已连接时拒绝运行时代理部署重试（Reject Runtime Agent Deployment Retry While Connected） - 当 重试运行时代理部署（Retry Runtime Agent Deployment）, 则 拒绝: 不允许运行时代理部署重试.
- 数据集治理（Dataset Governance） / 定义特征架构（Define Feature Schema）: 特征架构域版本唯一（Feature Schema Domain Version Unique） / 拒绝重复的特征架构版本（Reject Duplicate Feature Schema Version） - 当 定义特征架构（Define Feature Schema）, 则 拒绝: 特征架构版本已存在.
- 数据集治理（Dataset Governance） / 发布特征架构（Publish Feature Schema）: 仅可发布草稿架构（Only Draft Schemas Can Be Published） / 发布草稿架构（Publish Draft Schema） - 当 发布特征架构（Publish Feature Schema）, 则 特征架构已发布（Feature Schema Published）.
- 数据集治理（Dataset Governance） / 取代特征架构版本（Supersede Feature Schema Version）: 使用较新版本取代已发布的架构（Supersede Published Schema With Newer Version） / 取代较旧版本（Supersede Older Version） - 当 取代特征架构版本（Supersede Feature Schema Version）, 则 特征架构版本已取代（Feature Schema Version Superseded）.
- 数据集治理（Dataset Governance） / 标记当前推荐的特征架构版本（Mark Current Recommended Feature Schema Version）: 每个域仅一个推荐版本（Only One Recommended Version Per Domain） / 标记新的推荐版本（Mark New Recommended Version） - 当 标记当前推荐的特征架构版本（Mark Current Recommended Feature Schema Version）, 则 已标记当前推荐特征架构版本（Current Recommended Feature Schema Version Marked）.
- 模型仓库（Model Repository） / 注册模型制品（Register Model Artifact）: 模型工件必须可拉取且可验证（Model Artifact Must Be Pullable And Verifiable） / 注册可拉取模型工件（Register Pullable Model Artifact） - 当 注册模型制品（Register Model Artifact）, 则 模型工件已注册（Model Artifact Registered）.
- 模型仓库（Model Repository） / 下载模型工件（Download Model Artifact）: 已注册模型工件解析为存储文件下载（Registered Model Artifact Resolves To Stored File Download） / 授权已注册模型工件下载（Authorize Registered Model Artifact Download） - 当 下载模型工件（Download Model Artifact）, 则 模型工件下载已授权（Model Artifact Download Authorized）.
- 模型仓库（Model Repository） / 下载模型工件（Download Model Artifact）: 已注册模型工件解析为存储文件下载（Registered Model Artifact Resolves To Stored File Download） / 拒绝缺失的模型工件内容（Reject Missing Model Artifact Content） - 当 下载模型工件（Download Model Artifact）, 则 错误: 模型工件内容不可下载。.
- 模型仓库（Model Repository） / 注册联邦模型工件（Register Federated Model Artifact）: 联邦轮次模型已从受管存储注册（Federated Round Model Is Registered From Managed Storage） / 注册聚合轮次模型（Register Aggregated Round Model） - 当 注册联邦模型工件（Register Federated Model Artifact）, 则 联邦模型工件已注册（Federated Model Artifact Registered）.
- 训练编排（Training Orchestration） / 注册运行时引擎配置文件（Register Runtime Engine Profile）: 运行时引擎配置文件选择可拉取的引擎镜像（Runtime Engine Profile Selects Pullable Engine Image） / 注册 Py Torch Vision 运行时引擎配置文件（Register Py Torch Vision Runtime Engine Profile） - 当 注册运行时引擎配置文件（Register Runtime Engine Profile）, 则 运行时引擎配置文件已注册（Runtime Engine Profile Registered）.
- 训练编排（Training Orchestration） / 定义训练运行配置（Define Training Run Configuration）: 训练配置必须可运行（Training Configuration Must Be Runnable） / 定义可运行配置（Define Runnable Configuration） - 当 定义训练运行配置（Define Training Run Configuration）, 则 训练运行配置已定义（Training Run Configuration Defined）.
- 训练编排（Training Orchestration） / 定义训练运行配置（Define Training Run Configuration）: 训练配置必须可运行（Training Configuration Must Be Runnable） / 拒绝空轮次预算（Reject Empty Round Budget） - 当 定义训练运行配置（Define Training Run Configuration）, 则 拒绝: 轮次预算必须为正数.
- 训练编排（Training Orchestration） / 更新训练运行配置（Update Training Run Configuration）: 草稿训练配置可以更新（Draft Training Configuration Can Be Updated） / 更新草稿可运行配置（Update Draft Runnable Configuration） - 当 更新训练运行配置（Update Training Run Configuration）, 则 训练运行配置已更新（Training Run Configuration Updated）.
- 训练编排（Training Orchestration） / 更新训练运行配置（Update Training Run Configuration）: 草稿训练配置可以更新（Draft Training Configuration Can Be Updated） / 配置锁定后拒绝更新（Reject Update After Configuration Locked） - 当 更新训练运行配置（Update Training Run Configuration）, 则 拒绝: 训练运行配置已锁定.
- 训练编排（Training Orchestration） / 创建训练作业（Create Training Job）: 训练作业需要可运行配置和运行时访问（Training Job Requires Runnable Configuration And Runtime Access） / 使用可运行配置创建训练作业（Create Training Job With Runnable Configuration） - 当 创建训练作业（Create Training Job）, 则 训练作业已创建（Training Job Created）.
- 训练编排（Training Orchestration） / 创建训练作业（Create Training Job）: 训练作业需要可运行配置和运行时访问（Training Job Requires Runnable Configuration And Runtime Access） / 拒绝无可运行配置的训练作业（Reject Training Job Without Runnable Configuration） - 当 创建训练作业（Create Training Job）, 则 拒绝: 需要可运行训练配置.
- 训练编排（Training Orchestration） / 提交训练作业（Submit Training Job）: 只有已创建的训练作业才能提交（Only Created Training Jobs Can Be Submitted） / 提交已创建的训练作业（Submit Created Training Job） - 当 提交训练作业（Submit Training Job）, 则 训练作业已提交（Training Job Submitted）.
- 训练编排（Training Orchestration） / 提交训练作业（Submit Training Job）: 只有已创建的训练作业才能提交（Only Created Training Jobs Can Be Submitted） / 拒绝未知训练作业（Reject Unknown Training Job） - 当 提交训练作业（Submit Training Job）, 则 拒绝: 训练作业为必填项.
- 训练编排（Training Orchestration） / 选择训练轮次参与者（Select Training Round Participants）: 训练轮次参与者按轮快照（Training Round Participants Are Snapshotted Per Round） / 运行时池达到法定数量时选择参与者（Select Participants When Runtime Pool Reaches Quorum） - 当 选择训练轮次参与者（Select Training Round Participants）, 则 训练轮次参与者已选择（Training Round Participants Selected）.
- 训练编排（Training Orchestration） / 选择训练轮次参与者（Select Training Round Participants）: 训练轮次参与者按轮快照（Training Round Participants Are Snapshotted Per Round） / 拒绝低于法定数量的参与者快照（Reject Participant Snapshot Below Quorum） - 当 选择训练轮次参与者（Select Training Round Participants）, 则 训练轮次参与者选择失败（Training Round Participant Selection Failed）.
- 训练编排（Training Orchestration） / 重试训练轮次参与者选择（Retry Training Round Participant Selection）: 在就绪状态变化后重试参与者选择（Retry Participant Selection After Readiness Changes） / 重试参与者选择（Retry Participant Selection） - 当 重试训练轮次参与者选择（Retry Training Round Participant Selection）, 则 训练轮次参与者选择重试已请求（Training Round Participant Selection Retry Requested）.
- 训练编排（Training Orchestration） / 开始训练轮次（Start Training Round）: 训练轮次需要已选择的运行时法定人数（Training Round Requires Selected Runtime Quorum） / 使用已选择的运行时法定人数启动轮次（Start Round With Selected Runtime Quorum） - 当 开始训练轮次（Start Training Round）, 则 训练轮次已启动（Training Round Started）.
- 训练编排（Training Orchestration） / 开始训练轮次（Start Training Round）: 训练轮次需要已选择的运行时法定人数（Training Round Requires Selected Runtime Quorum） / 记录低于法定人数的失败轮次启动（Record Failed Round Start Below Quorum） - 当 开始训练轮次（Start Training Round）, 则 训练轮次启动失败（Training Round Start Failed）.
- 训练编排（Training Orchestration） / 生成参与者执行计划（Generate Participant Execution Plan）: 参与者执行计划携带基础模型工件快照（Participant Execution Plan Carries Base Model Artifact Snapshot） / 使用初始模型快照生成初始轮次计划（Generate Initial Round Plan With Initial Model Snapshot） - 当 生成参与者执行计划（Generate Participant Execution Plan）, 则 参与者执行计划已生成（Participant Execution Plan Generated）.
- 训练编排（Training Orchestration） / 生成参与者执行计划（Generate Participant Execution Plan）: 参与者执行计划携带基础模型工件快照（Participant Execution Plan Carries Base Model Artifact Snapshot） / 使用聚合模型快照生成后续轮次计划（Generate Later Round Plan With Aggregated Model Snapshot） - 当 生成参与者执行计划（Generate Participant Execution Plan）, 则 参与者执行计划已生成（Participant Execution Plan Generated）.
- 训练编排（Training Orchestration） / 评估模型更新提交（Evaluate Model Update Submission）: 已排除受污染的本地更新（Poisoned Local Updates Are Excluded） / 接受正常更新（Accept Normal Update） - 当 评估模型更新提交（Evaluate Model Update Submission）, 则 模型更新提交已接受（Model Update Submission Accepted）.
- 训练编排（Training Orchestration） / 评估模型更新提交（Evaluate Model Update Submission）: 已排除受污染的本地更新（Poisoned Local Updates Are Excluded） / 拒绝异常更新（Reject Anomalous Update） - 当 评估模型更新提交（Evaluate Model Update Submission）, 则 拒绝: 异常模型更新已拒绝.
- 训练编排（Training Orchestration） / 聚合明文模型更新（Aggregate Plain Model Updates）: 达到法定人数后聚合明文模型更新（Plain Model Updates Are Aggregated After Quorum） / 聚合已接受的明文更新（Aggregate Accepted Plain Updates） - 当 聚合明文模型更新（Aggregate Plain Model Updates）, 则 明文模型聚合已完成（Plain Model Aggregation Completed）.
- 训练编排（Training Orchestration） / 完成训练轮次（Complete Training Round）: 训练轮次完成需要全局评估（Training Round Completion Requires Global Evaluation） / 全局评估后完成轮次（Complete Round After Global Evaluation） - 当 完成训练轮次（Complete Training Round）, 则 训练轮次已完成（Training Round Completed）.
- 训练编排（Training Orchestration） / 完成训练轮次（Complete Training Round）: 训练轮次完成需要全局评估（Training Round Completion Requires Global Evaluation） / 无全局评估则拒绝完成轮次（Reject Round Completion Without Global Evaluation） - 当 完成训练轮次（Complete Training Round）, 则 拒绝: 需要全局模型评估.
- 模型生命周期（Model Lifecycle） / 记录模型评估包（Record Model Evaluation Package）: 模型审批需要评估包（Model Approval Requires Evaluation Package） / 记录完整评估包（Record Complete Evaluation Package） - 当 记录模型评估包（Record Model Evaluation Package）, 则 模型评估包已记录（Model Evaluation Package Recorded）.
- 模型生命周期（Model Lifecycle） / 批准模型（Approve Model）: 只有评估打包模型才能被批准（Only Evaluation Packaged Models Can Be Approved） / 批准评估打包模型（Approve Evaluation Packaged Model） - 当 批准模型（Approve Model）, 则 模型已批准（Model Approved）.
- 模型生命周期（Model Lifecycle） / 批准模型（Approve Model）: 只有评估打包模型才能被批准（Only Evaluation Packaged Models Can Be Approved） / 拒绝没有评估包的候选模型（Reject Candidate Without Evaluation Package） - 当 批准模型（Approve Model）, 则 拒绝: 需要模型评估包.
- 模型生命周期（Model Lifecycle） / 提升模型至生产（Promote Model To Production）: 仅已批准的模型可提升到生产环境（Only Approved Models Can Be Promoted To Production） / 提升已批准的模型（Promote Approved Model） - 当 提升模型至生产（Promote Model To Production）, 则 模型已提升到生产环境（Model Promoted To Production）.
- 模型生命周期（Model Lifecycle） / 提升模型至生产（Promote Model To Production）: 仅已批准的模型可提升到生产环境（Only Approved Models Can Be Promoted To Production） / 拒绝未批准模型的提升（Reject Unapproved Model Promotion） - 当 提升模型至生产（Promote Model To Production）, 则 拒绝: 需要模型批准.
- 运行时代理操作（Runtime Agent Operations） / 声明数据集（Declare Dataset）: 数据集声明需要特征架构快照（Feature Schema Snapshot Required For Dataset Declaration） / 使用特征架构快照声明数据集（Declare Dataset With Feature Schema Snapshot） - 当 声明数据集（Declare Dataset）, 则 数据集已声明（Dataset Declared）.
- 运行时代理操作（Runtime Agent Operations） / 声明数据集（Declare Dataset）: 数据集声明需要特征架构快照（Feature Schema Snapshot Required For Dataset Declaration） / 特征架构加载失败（Feature Schema Load Failed） - 当 声明数据集（Declare Dataset）, 则 错误: 特征架构加载失败.
- 运行时代理操作（Runtime Agent Operations） / 配置运行时数据集绑定（Configure Runtime Dataset Binding）: 每个运行时数据集的运行时数据集绑定唯一（Runtime Dataset Binding Unique Per Runtime Dataset） / 拒绝重复的运行时数据集绑定（Reject Duplicate Runtime Dataset Binding） - 当 配置运行时数据集绑定（Configure Runtime Dataset Binding）, 则 拒绝: 运行时数据集绑定已存在.
- 运行时代理操作（Runtime Agent Operations） / 验证代理数据集访问（Validate Agent Dataset Access）: 代理数据集访问验证结果（Agent Dataset Access Validation Outcome） / 代理数据集访问已验证（Agent Dataset Access Validated） - 当 验证代理数据集访问（Validate Agent Dataset Access）, 则 代理数据集访问已验证（Agent Dataset Access Validated）.
- 运行时代理操作（Runtime Agent Operations） / 验证代理数据集访问（Validate Agent Dataset Access）: 代理数据集访问验证结果（Agent Dataset Access Validation Outcome） / 代理数据集访问验证失败（Agent Dataset Access Validation Failed） - 当 验证代理数据集访问（Validate Agent Dataset Access）, 则 代理数据集访问验证失败（Agent Dataset Access Validation Failed）.
- 运行时代理操作（Runtime Agent Operations） / 重新验证代理数据集访问（Revalidate Agent Dataset Access）: 代理数据集访问重新验证结果（Agent Dataset Access Revalidation Outcome） / 代理数据集访问已重新验证（Agent Dataset Access Revalidated） - 当 重新验证代理数据集访问（Revalidate Agent Dataset Access）, 则 代理数据集访问已重新验证（Agent Dataset Access Revalidated）.
- 运行时代理操作（Runtime Agent Operations） / 重新验证代理数据集访问（Revalidate Agent Dataset Access）: 代理数据集访问重新验证结果（Agent Dataset Access Revalidation Outcome） / 代理数据集访问重新验证失败（Agent Dataset Access Revalidation Failed） - 当 重新验证代理数据集访问（Revalidate Agent Dataset Access）, 则 代理数据集访问重新验证失败（Agent Dataset Access Revalidation Failed）.
- 运行时代理操作（Runtime Agent Operations） / 分析代理数据集（Profile Agent Dataset）: 代理数据集分析结果（Agent Dataset Profiling Outcome） / 代理数据集元数据已报告（Agent Dataset Metadata Reported） - 当 分析代理数据集（Profile Agent Dataset）, 则 代理数据集元数据已报告（Agent Dataset Metadata Reported）.
- 运行时代理操作（Runtime Agent Operations） / 分析代理数据集（Profile Agent Dataset）: 代理数据集分析结果（Agent Dataset Profiling Outcome） / 代理数据集分析失败（Agent Dataset Profiling Failed） - 当 分析代理数据集（Profile Agent Dataset）, 则 代理数据集分析失败（Agent Dataset Profiling Failed）.
- 运行时代理操作（Runtime Agent Operations） / 重新分析代理数据集（Reprofile Agent Dataset）: 代理数据集重新剖析结果（Agent Dataset Reprofiling Outcome） / 代理数据集已重新分析（Agent Dataset Reprofiled） - 当 重新分析代理数据集（Reprofile Agent Dataset）, 则 代理数据集已重新分析（Agent Dataset Reprofiled）.
- 运行时代理操作（Runtime Agent Operations） / 重新分析代理数据集（Reprofile Agent Dataset）: 代理数据集重新剖析结果（Agent Dataset Reprofiling Outcome） / 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed） - 当 重新分析代理数据集（Reprofile Agent Dataset）, 则 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed）.
- 运行时代理操作（Runtime Agent Operations） / 验证数据集合约（Validate Dataset Contract）: 数据集合约验证结果（Dataset Contract Validation Outcome） / 数据集合约验证成功（Dataset Contract Validation Succeeded） - 当 验证数据集合约（Validate Dataset Contract）, 则 数据集合约已验证（Dataset Contract Validated）.
- 运行时代理操作（Runtime Agent Operations） / 验证数据集合约（Validate Dataset Contract）: 数据集合约验证结果（Dataset Contract Validation Outcome） / 数据集合约验证失败（Dataset Contract Validation Failed） - 当 验证数据集合约（Validate Dataset Contract）, 则 数据集合约验证失败（Dataset Contract Validation Failed）.
- 运行时代理操作（Runtime Agent Operations） / 重试数据集合约验证（Retry Dataset Contract Validation）: 数据集合约验证重试结果（Dataset Contract Validation Retry Outcome） / 数据集合约已重新验证（Dataset Contract Revalidated） - 当 重试数据集合约验证（Retry Dataset Contract Validation）, 则 数据集合约已重新验证（Dataset Contract Revalidated）.
- 运行时代理操作（Runtime Agent Operations） / 重试数据集合约验证（Retry Dataset Contract Validation）: 数据集合约验证重试结果（Dataset Contract Validation Retry Outcome） / 数据集合约重新验证失败（Dataset Contract Revalidation Failed） - 当 重试数据集合约验证（Retry Dataset Contract Validation）, 则 数据集合约重新验证失败（Dataset Contract Revalidation Failed）.
- 运行时代理操作（Runtime Agent Operations） / 批准数据集用于训练（Approve Dataset For Training）: 仅批准已验证的高质量数据集（Only Validated Quality Datasets Are Approved） / 批准已验证的数据集（Approve Validated Dataset） - 当 批准数据集用于训练（Approve Dataset For Training）, 则 数据集已批准用于训练（Dataset Approved For Training）.
- 运行时代理操作（Runtime Agent Operations） / 批准数据集用于训练（Approve Dataset For Training）: 仅批准已验证的高质量数据集（Only Validated Quality Datasets Are Approved） / 在数据集合约验证前拒绝批准（Reject Approval Before Contract Validation） - 当 批准数据集用于训练（Approve Dataset For Training）, 则 拒绝: 需要数据集合约验证.
- 运行时代理操作（Runtime Agent Operations） / 接受执行计划（Accept Execution Plan）: 执行计划本地接受结果（Execution Plan Local Acceptance Outcome） / 执行计划已接受（Execution Plan Accepted） - 当 接受执行计划（Accept Execution Plan）, 则 执行计划已接受（Execution Plan Accepted）.
- 运行时代理操作（Runtime Agent Operations） / 接受执行计划（Accept Execution Plan）: 执行计划本地接受结果（Execution Plan Local Acceptance Outcome） / 执行计划已拒绝（Execution Plan Rejected） - 当 接受执行计划（Accept Execution Plan）, 则 执行计划已拒绝（Execution Plan Rejected）.
- 运行时代理操作（Runtime Agent Operations） / 开始轮次执行（Start Round Execution）: 轮次执行运行时引擎启动结果（Round Execution Runtime Engine Start Outcome） / 轮次执行已启动（Round Execution Started） - 当 开始轮次执行（Start Round Execution）, 则 轮次执行已启动（Round Execution Started）.
- 运行时代理操作（Runtime Agent Operations） / 开始轮次执行（Start Round Execution）: 轮次执行运行时引擎启动结果（Round Execution Runtime Engine Start Outcome） / 轮次执行启动失败（Round Execution Start Failed） - 当 开始轮次执行（Start Round Execution）, 则 轮次执行启动失败（Round Execution Start Failed）.
- 运行时代理操作（Runtime Agent Operations） / 观察运行时引擎作业（Observe Runtime Engine Job）: 运行时引擎作业观察结果（Runtime Engine Job Observation Outcome） / 运行时引擎作业已观察运行中（Runtime Engine Job Observed Running） - 当 观察运行时引擎作业（Observe Runtime Engine Job）, 则 运行时引擎作业已观察（Runtime Engine Job Observed）.
- 运行时代理操作（Runtime Agent Operations） / 观察运行时引擎作业（Observe Runtime Engine Job）: 运行时引擎作业观察结果（Runtime Engine Job Observation Outcome） / 运行时引擎作业已观察完成（Runtime Engine Job Observed Completed） - 当 观察运行时引擎作业（Observe Runtime Engine Job）, 则 运行时引擎作业已观察（Runtime Engine Job Observed）.
- 运行时代理操作（Runtime Agent Operations） / 观察运行时引擎作业（Observe Runtime Engine Job）: 运行时引擎作业观察结果（Runtime Engine Job Observation Outcome） / 运行时引擎作业已观察失败（Runtime Engine Job Observed Failed） - 当 观察运行时引擎作业（Observe Runtime Engine Job）, 则 运行时引擎作业已观察（Runtime Engine Job Observed）.
- 运行时代理操作（Runtime Agent Operations） / 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration）: 运行时代理启动引导配置（Runtime Agent Startup Bootstrap Configuration） / 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration） - 当 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration）, 则 运行时代理引导配置已加载（Runtime Agent Bootstrap Configuration Loaded）.
- 运行时代理操作（Runtime Agent Operations） / 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration）: 运行时代理启动引导配置（Runtime Agent Startup Bootstrap Configuration） / 拒绝缺失的运行时代理引导配置（Reject Missing Runtime Agent Bootstrap Configuration） - 当 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration）, 则 运行时代理引导配置加载失败（Runtime Agent Bootstrap Configuration Load Failed）.
- 运行时代理操作（Runtime Agent Operations） / 报告运行时实例已连接（Report Runtime Instance Connected）: 运行时代理平台连接报告结果（Runtime Agent Platform Connection Reporting Outcome） / 报告运行时实例已连接（Report Runtime Instance Connected） - 当 报告运行时实例已连接（Report Runtime Instance Connected）, 则 代理运行时连接已建立（Agent Runtime Connection Established）.
- 运行时代理操作（Runtime Agent Operations） / 报告运行时实例已连接（Report Runtime Instance Connected）: 运行时代理平台连接报告结果（Runtime Agent Platform Connection Reporting Outcome） / 报告运行时实例连接失败（Report Runtime Instance Connection Failed） - 当 报告运行时实例已连接（Report Runtime Instance Connected）, 则 代理运行时连接报告失败（Agent Runtime Connection Report Failed）.
- 身份访问管理（Identity Access Management） / 注册用户账户（Register User Account）: 用户账户用户名唯一（User Account Username Unique） / 拒绝重复用户名（Reject Duplicate Username） - 当 注册用户账户（Register User Account）, 则 拒绝: 用户名已存在。.
- 身份访问管理（Identity Access Management） / 颁发服务账户 API 令牌（Issue Service Account Api Token）: 仅超级管理员可颁发服务账户 API 令牌（Only Super Administrator Can Issue Service Account Api Token） / 超级管理员为服务账户颁发令牌（Super Administrator Issues Token For Service Account） - 当 颁发服务账户 API 令牌（Issue Service Account Api Token）, 则 服务账户 API 令牌已颁发（Service Account Api Token Issued）.
- 身份访问管理（Identity Access Management） / 颁发服务账户 API 令牌（Issue Service Account Api Token）: 服务账户 API 令牌明文仅返回一次（Service Account Api Token Plaintext Returned Once） / 已颁发事件存储摘要而非明文令牌（Issued Event Stores Digest Instead Of Plaintext Token） - 当 颁发服务账户 API 令牌（Issue Service Account Api Token）, 则 服务账户 API 令牌已颁发（Service Account Api Token Issued）.
- 身份访问管理（Identity Access Management） / 颁发服务账户 API 令牌（Issue Service Account Api Token）: 服务账户 API 令牌捕获权限快照（Service Account Api Token Captures Permission Snapshot） / 已颁发令牌捕获当前权限（Issued Token Captures Current Permissions） - 当 颁发服务账户 API 令牌（Issue Service Account Api Token）, 则 服务账户 API 令牌已颁发（Service Account Api Token Issued）.

## 五、质量属性、运维与可观测性设计/EMW-SD-QAOO

### 5.1 组织管理（Organization Management）/EMW-SD-SECTION-197

**设计决策**

尚未明确设计决策。

**风险**

- 如果未强制执行组织生命周期状态，非激活组织可以被邀请加入联邦。
- 如果未强制执行运行时发现的节点可用性，禁用的计算节点仍可被选择用于训练。

**指标**

- organization Activation Lead Time
- runtime Identity Coverage

### 5.2 联邦管理（Federation Management）/EMW-SD-SECTION-198

**设计决策**

尚未明确设计决策。

**风险**

- 非激活组织加入联邦可能会损害模型治理和数据共享协议。
- 如果未强制执行资格条件，联邦训练可能在可用的组织拥有的计算节点过少的情况下继续进行。

**指标**

- participant Approval Lead Time
- federation Activation Lead Time

### 5.3 字典维护（Dictionary Maintenance）/EMW-SD-SECTION-199

**设计决策**

尚未明确设计决策。

**风险**

- 如果字典值被移除而不是停用，运行时选项更改可能会使待处理的安装计划失效。
- 重复的字典值代码可能会在可部署项之间导致不一致的命令负载。

**指标**

- dictionary Value Activation Latency

### 5.4 文件上传（File Upload）/EMW-SD-SECTION-200

**设计决策**

尚未明确设计决策。

**风险**

- 如果未强制实施保留期、所有权、授权和内容验证，则存储的文件可能泄漏敏感的模型或数据集内容。
- 如果保留期导致仍被域记录引用的文件过期，则业务引用可能失效。

**指标**

- file Upload Latency
- file Upload Failure Rate

### 5.5 运行时预配（Runtime Provisioning）/EMW-SD-SECTION-201

**设计决策**

尚未明确设计决策。

**风险**

- 组织可能会收到错误的运行时安装指南，并无法连接运行时基础设施。
- 泄漏的引导令牌可能允许未经授权的基础设施加入平台。

**指标**

- runtime Bootstrap Success Rate
- runtime Infrastructure Connection Latency

### 5.6 数据集治理（Dataset Governance）/EMW-SD-SECTION-202

**设计决策**

尚未明确设计决策。

**风险**

- 即使存在使训练假设失效的架构漂移、隐私敏感性或非 IID 偏差，数据集也可能被批准。
- 已验证的数据集访问在治理撤销后仍可保持可选，除非被明确阻止。

**指标**

- dataset Contract Validation Failure Rate
- runtime Dataset Access Validation Latency

### 5.7 模型仓库（Model Repository）/EMW-SD-SECTION-203

**设计决策**

尚未明确设计决策。

**风险**

- 如果未将制品 URI、摘要、注册表、格式及签名与训练运行一同快照，运行时代理可能会拉取到过时或被篡改的模型。
- 如果未记录生成聚合轮次模型的训练任务及其所属轮次，聚合轮次模型可能会丢失其谱系。

**指标**

- model Artifact Registration Latency
- model Artifact Pull Failure Rate

### 5.8 训练编排（Training Orchestration）/EMW-SD-SECTION-204

**设计决策**

尚未明确设计决策。

**风险**

- 当已启动的参与者运行时数量低于法定人数，或在执行计划分发前安全聚合上下文准备工作失败时，训练可能会停滞。
- 如果未将异常检查建模为关键决策，受污染的本地更新可能会降低全局模型的性能。

**指标**

- node Dropout Rate
- round Completion Latency
- global Accuracy Gap

### 5.9 模型生命周期（Model Lifecycle）/EMW-SD-SECTION-205

**设计决策**

尚未明确设计决策。

**风险**

- 模型可以在没有可复现包、模型卡片或已批准的评估报告的情况下被提升。
- 如果未捕获之前的生产版本，回滚决策可能会丢失血缘关系。

**指标**

- model Approval Lead Time
- production Rollback Rate

### 5.10 运行时监控（Runtime Monitoring）/EMW-SD-SECTION-206

**设计决策**

尚未明确设计决策。

**风险**

- 没有确认或解决跟踪的操作告警可能会在训练期间隐藏节点降级。
- 没有负载哈希和源事件分类的审计记录会削弱合规证据。

**指标**

- runtime Alert Mean Time To Acknowledge
- audit Trail Completeness

### 5.11 运行时治理（Runtime Governance）/EMW-SD-SECTION-207

**设计决策**

尚未明确设计决策。

**风险**

- 已配置的运行时可能被误认为是活跃的训练参与者。
- 控制平面被攻破不得暴露运行时私钥或本地数据集凭据。

**指标**

- runtime Activation Lead Time

### 5.12 安全聚合（Secure Aggregation）/EMW-SD-SECTION-208

**设计决策**

尚未明确设计决策。

**风险**

- 同态加密更新仅在聚合由足够参与者完成时才保护单个参数；单参与者或非常小的群组在解密后仍可能暴露本地更新。
- 聚合可以在加密更新不足或加密上下文与当前活动轮次不匹配的情况下完成。

**指标**

- secure Aggregation Completion Latency

### 5.13 运行时代理操作（Runtime Agent Operations）/EMW-SD-SECTION-209

**设计决策**

尚未明确设计决策。

**风险**

- 如果机密引用在参与者边界之外被实例化，代理作业可能会泄漏本地凭据。
- 运行时引擎作业在平台取消后可能仍保持运行，除非明确跟踪本地作业生命周期。

**指标**

- agent Runtime Job Failure Rate
- agent Runtime Job Startup Latency

### 5.14 身份访问管理（Identity Access Management）/EMW-SD-SECTION-210

**设计决策**

尚未明确设计决策。

**风险**

尚未明确风险。

**指标**

尚未明确指标。

## 六、实现缺口/EMW-SD-IG

- 组织管理（Organization Management） 有 3 个业务操作尚未定义明确验收规则：激活组织（Activate Organization）, 停用组织（Deactivate Organization）, 重新激活组织（Reactivate Organization）。
- 联邦管理（Federation Management） 有 8 个业务操作尚未定义明确验收规则：激活联邦（Activate Federation）, 暂停联邦（Suspend Federation）, 重新激活联邦（Reactivate Federation）, 邀请参与者（Invite Participant），另有 4 个。
- 字典维护（Dictionary Maintenance） 有 5 个业务操作尚未定义明确验收规则：更新字典（Update Dictionary）, 归档字典（Archive Dictionary）, 添加字典值（Add Dictionary Value）, 停用字典值（Disable Dictionary Value），另有 1 个。
- 运行时预配（Runtime Provisioning） 有 5 个业务操作尚未定义明确验收规则：注册运行时基础设施包（Register Runtime Infrastructure Package）, 创建运行时安装计划（Create Runtime Installation Plan）, 规划运行时基础设施（Plan Runtime Infrastructure）, 注册运行时基础设施（Register Runtime Infrastructure），另有 1 个。
- 数据集治理（Dataset Governance） 有 4 个业务操作尚未定义明确验收规则：弃用特征架构（Deprecate Feature Schema）, 停用特征架构（Retire Feature Schema）, 记录运行时数据集元数据（Record Runtime Dataset Metadata）, 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata）。
- 训练编排（Training Orchestration） 有 11 个业务操作尚未定义明确验收规则：锁定训练运行配置（Lock Training Run Configuration）, 暂停训练作业（Pause Training Job）, 恢复训练作业（Resume Training Job）, 取消训练作业（Cancel Training Job），另有 7 个。
- 模型生命周期（Model Lifecycle） 有 3 个业务操作尚未定义明确验收规则：注册候选模型（Register Candidate Model）, 回滚模型（Rollback Model）, 停用模型（Retire Model）。
- 运行时监控（Runtime Monitoring） 有 9 个业务操作尚未定义明确验收规则：检测运行时代理离线（Detect Runtime Agent Offline）, 标记运行时代理已恢复（Mark Runtime Agent Recovered）, 检测运行时节点资源压力（Detect Runtime Node Resource Pressure）, 记录运行时节点清单（Record Runtime Node Inventory），另有 5 个。
- 运行时治理（Runtime Governance） 有 3 个业务操作尚未定义明确验收规则：激活运行时标识（Activate Runtime Identity）, 撤销运行时标识（Revoke Runtime Identity）, 检测运行时能力（Detect Runtime Capabilities）。
- 安全聚合（Secure Aggregation） 有 6 个业务操作尚未定义明确验收规则：创建安全聚合会话（Create Secure Aggregation Session）, 选择安全聚合参与者（Select Secure Aggregation Participants）, 准备同态加密上下文（Prepare Homomorphic Encryption Context）, 记录加密模型更新（Record Encrypted Model Update），另有 2 个。
- 运行时代理操作（Runtime Agent Operations） 有 16 个业务操作尚未定义明确验收规则：拒绝数据集用于训练（Reject Dataset For Training）, 撤销数据集训练审批（Revoke Dataset Training Approval）, 接收参与者执行计划（Receive Participant Execution Plan）, 完成轮次执行（Complete Round Execution），另有 12 个。
- 身份访问管理（Identity Access Management） 有 6 个业务操作尚未定义明确验收规则：停用用户账户（Deactivate User Account）, 生成用户账户登录密码（Generate User Account Login Password）, 注册角色（Register Role）, 注册权限（Register Permission），另有 2 个。
- 运行时最新遥测（Runtime Telemetry Latest） 尚未明确数据更新来源。
- 运行时节点最新资源（Runtime Node Resource Latest） 尚未明确数据更新来源。
- 代理组织目录（Agent Organization Directory） 尚未明确数据更新来源。
- 代理功能架构目录（Agent Feature Schema Catalog） 尚未明确数据更新来源。
- 代理字典值目录（Agent Dictionary Value Catalog） 尚未明确数据更新来源。
- 代理运行时身份目录（Agent Runtime Identity Catalog） 尚未明确数据更新来源。
- 代理运行时遥测最新（Agent Runtime Telemetry Latest） 尚未明确数据更新来源。
- 代理运行时节点资源最新（Agent Runtime Node Resource Latest） 尚未明确数据更新来源。
