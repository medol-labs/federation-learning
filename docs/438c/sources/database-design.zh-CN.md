# 联邦学习平台（Federation Learning Platform） Read Model 数据库设计

<!-- em:section id="document.changeLog" -->
## 变更记录

| 版本 | 日期 | 变更说明 | 作者 |
| --- | --- | --- | --- |
| V0.1.0 | 2026-08-23 | 初始发布 | MEDOL |

## 一、设计范围/FLPRM-DB-DS

本文档描述由 Event Modeling Read Model 推导出的读侧数据模型。除非领域模型明确说明，否则存储引擎、物理表名、数据保留策略和一致性 SLA 均属于后续实现决策。

## 二、Read Model 清单/FLPRM-DB-RMI

| Read Model | 限界上下文 | 聚合 | 形态 | 来源事件 |
| --- | --- | --- | --- | --- |
| 组织目录（Organization Directory） | 组织管理（Organization Management） | 组织（Organization） | 集合/列表 | 组织已注册（Organization Registered）, 组织已激活（Organization Activated）, 组织已停用（Organization Deactivated）, 组织已重新激活（Organization Reactivated）, 数据集已批准用于训练（Dataset Approved For Training） |
| 联邦概览（Federation Overview） | 联邦管理（Federation Management） | 联邦（Federation） | 集合/列表 | 联邦已创建（Federation Created）, 联邦已激活（Federation Activated）, 联邦已暂停（Federation Suspended）, 联邦已重新激活（Federation Reactivated）, 参与者已邀请（Participant Invited）, 参与者已加入（Participant Joined）, 参与者已拒绝（Participant Rejected）, 参与者邀请已撤销（Participant Invitation Revoked）, 参与者已暂停（Participant Suspended）, 参与者已移除（Participant Removed）, 运行时身份已激活（Runtime Identity Activated）, 运行时身份已撤销（Runtime Identity Revoked）, 训练作业已提交（Training Job Submitted） |
| 联邦成员资格目录（Federation Membership Directory） | 联邦管理（Federation Management） | 联邦成员资格（Federation Membership） | 集合/列表 | 联邦已创建（Federation Created）, 参与者已邀请（Participant Invited）, 参与者已加入（Participant Joined）, 参与者已拒绝（Participant Rejected）, 参与者邀请已撤销（Participant Invitation Revoked）, 参与者已暂停（Participant Suspended）, 参与者已移除（Participant Removed）, 组织已注册（Organization Registered）, 组织已激活（Organization Activated） |
| 字典目录（Dictionary Catalog） | 字典维护（Dictionary Maintenance） | 字典（Dictionary） | 集合/列表 | 字典已注册（Dictionary Registered）, 字典已更新（Dictionary Updated）, 字典已归档（Dictionary Archived） |
| 字典值目录（Dictionary Value Catalog） | 字典维护（Dictionary Maintenance） | 字典值（Dictionary Value） | 集合/列表 | 字典值已添加（Dictionary Value Added）, 字典值已停用（Dictionary Value Disabled）, 字典值已启用（Dictionary Value Enabled） |
| 暂存文件目录（Staged File Catalog） | 文件上传（File Upload） | 暂存文件（Staged File） | 集合/列表 | 文件上传已暂存（File Upload Staged）, 暂存文件已消费（Staged File Consumed）, 暂存文件已丢弃（Staged File Discarded）, 暂存文件已过期（Staged File Expired） |
| 运行时基础设施包目录（Runtime Infrastructure Package Catalog） | 运行时预配（Runtime Provisioning） | 运行时基础设施包（Runtime Infrastructure Package） | 集合/列表 | 运行时基础设施包已注册（Runtime Infrastructure Package Registered） |
| 运行时安装计划目录（Runtime Installation Plan Catalog） | 运行时预配（Runtime Provisioning） | 运行时安装计划（Runtime Installation Plan） | 集合/列表 | 组织已注册（Organization Registered）, 运行时基础设施包已注册（Runtime Infrastructure Package Registered）, 运行时安装计划已创建（Runtime Installation Plan Created）, 运行时基础设施已计划（Runtime Infrastructure Planned）, 运行时基础设施已注册（Runtime Infrastructure Registered）, 运行时基础设施已验证（Runtime Infrastructure Verified）, 运行时基础设施验证失败（Runtime Infrastructure Verification Failed）, 运行时代理安装成功（Runtime Agent Installation Succeeded）, 运行时代理安装失败（Runtime Agent Installation Failed）, 运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）, 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed）, 运行时连接已建立（Runtime Connection Established） |
| 运行时安装指南（Runtime Installation Guide） | 运行时预配（Runtime Provisioning） | 运行时安装计划（Runtime Installation Plan） | 集合/列表 | 组织已注册（Organization Registered）, 运行时基础设施包已注册（Runtime Infrastructure Package Registered）, 运行时安装计划已创建（Runtime Installation Plan Created） |
| 运行时代理端点目录（Runtime Agent Endpoint Catalog） | 运行时预配（Runtime Provisioning） | 运行时基础设施（Runtime Infrastructure） | 集合/列表 | 运行时连接已建立（Runtime Connection Established）, 运行时身份已激活（Runtime Identity Activated）, 运行时身份已撤销（Runtime Identity Revoked）, 已检测到运行时代理离线（Runtime Agent Offline Detected）, 运行时代理已恢复（Runtime Agent Recovered） |
| 运行时基础设施访问视图（Runtime Infrastructure Access View） | 运行时预配（Runtime Provisioning） | 运行时基础设施（Runtime Infrastructure） | 集合/列表 | 组织已注册（Organization Registered）, 运行时基础设施包已注册（Runtime Infrastructure Package Registered）, 运行时安装计划已创建（Runtime Installation Plan Created）, 运行时基础设施已计划（Runtime Infrastructure Planned）, 运行时基础设施已注册（Runtime Infrastructure Registered）, 运行时基础设施已验证（Runtime Infrastructure Verified）, 运行时基础设施验证失败（Runtime Infrastructure Verification Failed）, 运行时代理安装成功（Runtime Agent Installation Succeeded）, 运行时代理安装失败（Runtime Agent Installation Failed）, 运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）, 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed）, 运行时连接已建立（Runtime Connection Established） |
| 当前推荐的特征架构目录（Current Recommended Feature Schema Catalog） | 数据集治理（Dataset Governance） | 特征架构（Feature Schema） | 集合/列表 | 已标记当前推荐特征架构版本（Current Recommended Feature Schema Version Marked） |
| 特征架构目录（Feature Schema Catalog） | 数据集治理（Dataset Governance） | 特征架构（Feature Schema） | 集合/列表 | 特征架构已定义（Feature Schema Defined）, 特征架构已发布（Feature Schema Published）, 特征架构已弃用（Feature Schema Deprecated）, 特征架构已停用（Feature Schema Retired）, 特征架构版本已取代（Feature Schema Version Superseded）, 已标记当前推荐特征架构版本（Current Recommended Feature Schema Version Marked） |
| 运行时数据集元数据目录（Runtime Dataset Metadata Catalog） | 数据集治理（Dataset Governance） | 运行时数据集元数据（Runtime Dataset Metadata） | 集合/列表 | 组织已注册（Organization Registered）, 特征架构已定义（Feature Schema Defined）, 数据集元数据已报告（Dataset Metadata Reported）, 数据集元数据已重新分析（Dataset Metadata Reprofiled）, 代理数据集分析失败（Agent Dataset Profiling Failed）, 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed） |
| 模型制品目录（Model Artifact Catalog） | 模型仓库（Model Repository） | 模型制品（Model Artifact） | 集合/列表 | 训练作业已创建（Training Job Created）, 模型工件已注册（Model Artifact Registered）, 全局模型已更新（Global Model Updated） |
| 训练运行配置目录（Training Run Configuration Catalog） | 训练编排（Training Orchestration） | 训练运行配置（Training Run Configuration） | 集合/列表 | 联邦已创建（Federation Created）, 特征架构已定义（Feature Schema Defined）, 训练运行配置已定义（Training Run Configuration Defined）, 训练运行配置已更新（Training Run Configuration Updated）, 训练运行配置已锁定（Training Run Configuration Locked） |
| 训练参与者资格（Training Participant Eligibility） | 训练编排（Training Orchestration） | 训练作业（Training Job） | 集合/列表 | 联邦已创建（Federation Created）, 组织已注册（Organization Registered）, 特征架构已定义（Feature Schema Defined）, 训练作业已创建（Training Job Created）, 训练作业已提交（Training Job Submitted）, 参与者已加入（Participant Joined）, 参与者已暂停（Participant Suspended）, 参与者已移除（Participant Removed）, 运行时身份已激活（Runtime Identity Activated）, 运行时身份已撤销（Runtime Identity Revoked）, 运行时能力已检测（Runtime Capabilities Detected）, 运行时连接已建立（Runtime Connection Established）, 已检测到运行时代理离线（Runtime Agent Offline Detected）, 运行时代理已恢复（Runtime Agent Recovered）, 数据集已声明（Dataset Declared）, 数据集元数据已报告（Dataset Metadata Reported）, 数据集合约已验证（Dataset Contract Validated）, 数据集已批准用于训练（Dataset Approved For Training）, 数据集训练批准已撤销（Dataset Training Approval Revoked）, 代理数据集访问已验证（Agent Dataset Access Validated） |
| 训练作业仪表板（Training Job Dashboard） | 训练编排（Training Orchestration） | 训练作业（Training Job） | 集合/列表 | 联邦已创建（Federation Created）, 特征架构已定义（Feature Schema Defined）, 训练作业已创建（Training Job Created）, 训练作业已提交（Training Job Submitted）, 训练作业已暂停（Training Job Paused）, 训练作业已恢复（Training Job Resumed）, 训练作业已取消（Training Job Canceled）, 训练轮次已启动（Training Round Started）, 训练轮次已完成（Training Round Completed）, 训练作业已完成（Training Job Completed） |
| 训练轮次进度（Training Round Progress） | 训练编排（Training Orchestration） | 训练轮次（Training Round） | 集合/列表 | 训练作业已创建（Training Job Created）, 特征架构已定义（Feature Schema Defined）, 训练轮次参与者已选择（Training Round Participants Selected）, 训练轮次参与者选择失败（Training Round Participant Selection Failed）, 训练轮次已启动（Training Round Started）, 训练轮次启动失败（Training Round Start Failed）, 参与者执行计划已生成（Participant Execution Plan Generated）, 参与者执行计划已分发（Participant Execution Plan Dispatched）, 轮次执行已启动（Round Execution Started）, 轮次执行启动失败（Round Execution Start Failed）, Round Execution Start Retry Started, 轮次执行启动重试失败（Round Execution Start Retry Failed）, 轮次执行已完成（Round Execution Completed）, 轮次执行失败（Round Execution Failed）, Round Execution Runtime Retry Started, 轮次执行运行时重试失败（Round Execution Runtime Retry Failed）, 模型更新提交已接收（Model Update Submission Received）, 模型更新提交已接受（Model Update Submission Accepted）, 模型更新提交已拒绝（Model Update Submission Rejected）, 已请求安全聚合（Secure Aggregation Requested）, 全局模型已更新（Global Model Updated）, 全局模型评估已提交（Global Model Evaluation Submitted）, 训练轮次已完成（Training Round Completed）, 训练轮次失败（Training Round Failed） |
| 模型目录（Model Catalog） | 模型生命周期（Model Lifecycle） | 模型（Model） | 集合/列表 | 训练作业已创建（Training Job Created）, 模型候选已注册（Model Candidate Registered）, 模型评估包已记录（Model Evaluation Package Recorded）, 模型已批准（Model Approved）, 模型已提升到生产环境（Model Promoted To Production）, 模型已回滚（Model Rolled Back）, 模型已退役（Model Retired） |
| 运行时最新遥测（Runtime Telemetry Latest） | 运行时监控（Runtime Monitoring） | 节点运行时健康（Node Runtime Health） | 集合/列表 | 尚未明确 |
| 运行时节点清单视图（Runtime Node Inventory View） | 运行时监控（Runtime Monitoring） | 运行时节点清单（Runtime Node Inventory） | 集合/列表 | 组织已注册（Organization Registered）, 运行时安装计划已创建（Runtime Installation Plan Created）, 运行时基础设施已注册（Runtime Infrastructure Registered）, 运行时节点清单已报告（Runtime Node Inventory Reported） |
| 运行时节点最新资源（Runtime Node Resource Latest） | 运行时监控（Runtime Monitoring） | 运行时节点资源遥测（Runtime Node Resource Telemetry） | 集合/列表 | 尚未明确 |
| 运行时健康仪表板（Runtime Health Dashboard） | 运行时监控（Runtime Monitoring） | 节点运行时健康（Node Runtime Health） | 集合/列表 | 联邦已创建（Federation Created）, 训练作业已创建（Training Job Created）, 已检测到运行时代理离线（Runtime Agent Offline Detected）, 运行时代理已恢复（Runtime Agent Recovered）, 已检测到运行时节点资源压力（Runtime Node Resource Pressure Detected）, 运行时节点容量已更改（Runtime Node Capacity Changed）, 运行时节点清单已报告（Runtime Node Inventory Reported）, 训练警报已发出（Training Alert Raised） |
| 训练告警目录（Training Alert Catalog） | 运行时监控（Runtime Monitoring） | 训练告警（Training Alert） | 集合/列表 | 运行时节点清单已报告（Runtime Node Inventory Reported）, 训练作业已创建（Training Job Created）, 训练警报已发出（Training Alert Raised）, 训练警报已确认（Training Alert Acknowledged）, 训练警报已解决（Training Alert Resolved） |
| 审计记录日志（Audit Record Log） | 运行时监控（Runtime Monitoring） | 审计记录（Audit Record） | 集合/列表 | 审计跟踪已追加（Audit Trail Appended） |
| 运行时标识目录（Runtime Identity Catalog） | 运行时治理（Runtime Governance） | 运行时标识（Runtime Identity） | 集合/列表 | 组织已注册（Organization Registered）, 运行时身份已激活（Runtime Identity Activated）, 运行时身份已撤销（Runtime Identity Revoked） |
| 运行时能力目录（Runtime Capability Catalog） | 运行时治理（Runtime Governance） | 运行时能力（Runtime Capability） | 集合/列表 | 运行时能力已检测（Runtime Capabilities Detected） |
| 安全聚合会话目录（Secure Aggregation Session Catalog） | 安全聚合（Secure Aggregation） | 安全聚合会话（Secure Aggregation Session） | 集合/列表 | 安全聚合会话已创建（Secure Aggregation Session Created）, 安全聚合参与者已选择（Secure Aggregation Participants Selected）, 同态加密上下文已准备（Homomorphic Encryption Context Prepared）, 已接收加密模型更新（Encrypted Model Update Received）, 安全聚合已完成（Secure Aggregation Completed）, 安全聚合失败（Secure Aggregation Failed） |
| 运行时数据集绑定目录（Runtime Dataset Binding Catalog） | 运行时代理操作（Runtime Agent Operations） | 运行时数据集绑定（Runtime Dataset Binding） | 集合/列表 | 数据集已声明（Dataset Declared）, 运行时数据集绑定已配置（Runtime Dataset Binding Configured） |
| 代理数据集访问验证目录（Agent Dataset Access Validation Catalog） | 运行时代理操作（Runtime Agent Operations） | 代理数据集访问验证（Agent Dataset Access Validation） | 集合/列表 | 数据集已声明（Dataset Declared）, 代理数据集访问已验证（Agent Dataset Access Validated）, 代理数据集访问验证失败（Agent Dataset Access Validation Failed）, 代理数据集访问已重新验证（Agent Dataset Access Revalidated）, 代理数据集访问重新验证失败（Agent Dataset Access Revalidation Failed） |
| 数据集能力（Dataset Capability） | 运行时代理操作（Runtime Agent Operations） | 数据集（Dataset） | 集合/列表 | 组织已注册（Organization Registered）, 特征架构已定义（Feature Schema Defined）, 数据集已声明（Dataset Declared）, 代理数据集元数据已报告（Agent Dataset Metadata Reported）, 代理数据集分析失败（Agent Dataset Profiling Failed）, 代理数据集已重新分析（Agent Dataset Reprofiled）, 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed）, 数据集合约已验证（Dataset Contract Validated）, 数据集合约验证失败（Dataset Contract Validation Failed）, 数据集合约已重新验证（Dataset Contract Revalidated）, 数据集合约重新验证失败（Dataset Contract Revalidation Failed）, 数据集已拒绝用于训练（Dataset Rejected For Training）, 数据集已批准用于训练（Dataset Approved For Training）, 数据集训练批准已撤销（Dataset Training Approval Revoked） |
| 数据集就绪状态（Dataset Readiness） | 运行时代理操作（Runtime Agent Operations） | 数据集（Dataset） | 集合/列表 | 组织已注册（Organization Registered）, 特征架构已定义（Feature Schema Defined）, 数据集已声明（Dataset Declared）, 代理数据集元数据已报告（Agent Dataset Metadata Reported）, 代理数据集分析失败（Agent Dataset Profiling Failed）, 代理数据集已重新分析（Agent Dataset Reprofiled）, 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed）, 数据集合约已验证（Dataset Contract Validated）, 数据集合约验证失败（Dataset Contract Validation Failed）, 数据集合约已重新验证（Dataset Contract Revalidated）, 数据集合约重新验证失败（Dataset Contract Revalidation Failed）, 数据集已拒绝用于训练（Dataset Rejected For Training）, 数据集已批准用于训练（Dataset Approved For Training）, 数据集训练批准已撤销（Dataset Training Approval Revoked）, 代理数据集访问已验证（Agent Dataset Access Validated）, 代理数据集访问验证失败（Agent Dataset Access Validation Failed）, 代理数据集访问已重新验证（Agent Dataset Access Revalidated）, 代理数据集访问重新验证失败（Agent Dataset Access Revalidation Failed）, 运行时连接已建立（Runtime Connection Established）, 已检测到运行时代理离线（Runtime Agent Offline Detected）, 运行时代理已恢复（Runtime Agent Recovered） |
| 轮次执行目录（Round Execution Catalog） | 运行时代理操作（Runtime Agent Operations） | 轮次执行（Round Execution） | 集合/列表 | 参与者执行计划已分发（Participant Execution Plan Dispatched）, 执行计划已接收（Execution Plan Received）, 执行计划已接受（Execution Plan Accepted）, 执行计划已拒绝（Execution Plan Rejected）, 轮次执行已启动（Round Execution Started）, 轮次执行启动失败（Round Execution Start Failed）, 运行时引擎作业已观察（Runtime Engine Job Observed）, Round Execution Start Retry Started, 轮次执行启动重试失败（Round Execution Start Retry Failed）, 轮次执行已完成（Round Execution Completed）, 轮次执行失败（Round Execution Failed）, Round Execution Runtime Retry Started, 轮次执行运行时重试失败（Round Execution Runtime Retry Failed）, Agent Local Model Update Submitted, Runtime Engine Job Released, Runtime Engine Job Release Failed Or Skipped, Runtime Engine Job Release Failed Or Skipped After Start Failure, Runtime Engine Job Release Failed Or Skipped After Retry, Runtime Engine Job Release Failed Or Skipped After Runtime Retry |
| 运行时代理生命周期目录（Runtime Agent Lifecycle Catalog） | 运行时代理操作（Runtime Agent Operations） | 运行时代理生命周期（Runtime Agent Lifecycle） | 集合/列表 | Runtime Agent Bootstrap Configuration Loaded, Runtime Agent Bootstrap Configuration Load Failed, Runtime Agent Started, Runtime Instance Self Check Passed |
| 代理运行时基础设施连接目录（Agent Runtime Infrastructure Connection Catalog） | 运行时代理操作（Runtime Agent Operations） | 代理运行时基础设施连接（Agent Runtime Infrastructure Connection） | 集合/列表 | Agent Runtime Connection Established, Agent Runtime Connection Report Failed |
| 代理运行时遥测最新（Agent Runtime Telemetry Latest） | 运行时代理操作（Runtime Agent Operations） | 代理运行时遥测（Agent Runtime Telemetry） | 集合/列表 | 尚未明确 |
| 代理运行时节点清单目录（Agent Runtime Node Inventory Catalog） | 运行时代理操作（Runtime Agent Operations） | 代理运行时节点清单（Agent Runtime Node Inventory） | 集合/列表 | 组织已注册（Organization Registered）, 代理运行时节点清单已报告（Agent Runtime Node Inventory Reported） |
| 代理运行时节点资源最新（Agent Runtime Node Resource Latest） | 运行时代理操作（Runtime Agent Operations） | 代理运行时节点资源遥测（Agent Runtime Node Resource Telemetry） | 集合/列表 | 尚未明确 |

<!-- em:section id="database.readmodel.组织目录（OrganizationDirectory）" sources="domain/FederationLearningPlatform/context/OrganizationManagement/slice/OrganizationDirectory/readmodel/OrganizationDirectory domain/FederationLearningPlatform/context/OrganizationManagement/slice/OrganizationDirectory" -->
## 三、组织目录（Organization Directory）/FLPRM-DB-SECTION

归属： 组织管理（Organization Management） / 组织（Organization） / 组织目录（Organization Directory）

逻辑形态： 集合/列表 Read Model

订阅事件： 组织已注册（Organization Registered）, 组织已激活（Organization Activated）, 组织已停用（Organization Deactivated）, 组织已重新激活（Organization Reactivated）, 数据集已批准用于训练（Dataset Approved For Training）

### 3.1 逻辑结构/FLPRM-DB-LS

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| organizationId | UUID | 单值 | 标识 | - | - |
| organizationName | String | 单值 | display | - | - |
| organizationType | OrganizationType | 单值 | - | - | - |
| state | Organization.State | 单值 | - | - | - |
| approvedDatasetCount | Int | 单值 | - | - | - |

### 3.2 主键与访问路径/FLPRM-DB-SECTION-2

- 逻辑标识：organizationId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 3.3 更新语义/FLPRM-DB-USM

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。

<!-- em:section id="database.readmodel.联邦概览（FederationOverview）" sources="domain/FederationLearningPlatform/context/FederationManagement/slice/FederationOverview/readmodel/FederationOverview domain/FederationLearningPlatform/context/FederationManagement/slice/FederationOverview" -->
## 四、联邦概览（Federation Overview）/FLPRM-DB-SECTION-3

归属： 联邦管理（Federation Management） / 联邦（Federation） / 联邦概览（Federation Overview）

逻辑形态： 集合/列表 Read Model

订阅事件： 联邦已创建（Federation Created）, 联邦已激活（Federation Activated）, 联邦已暂停（Federation Suspended）, 联邦已重新激活（Federation Reactivated）, 参与者已邀请（Participant Invited）, 参与者已加入（Participant Joined）, 参与者已拒绝（Participant Rejected）, 参与者邀请已撤销（Participant Invitation Revoked）, 参与者已暂停（Participant Suspended）, 参与者已移除（Participant Removed）, 运行时身份已激活（Runtime Identity Activated）, 运行时身份已撤销（Runtime Identity Revoked）, 训练作业已提交（Training Job Submitted）

### 4.1 逻辑结构/FLPRM-DB-LS-2

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| federationId | UUID | 单值 | 标识 | - | - |
| federationName | String | 单值 | display | - | - |
| state | Federation.State | 单值 | - | - | - |
| minimumParticipantCount | Int | 单值 | - | - | - |
| activeMemberCount | Int | 单值 | - | - | - |
| pendingInvitationCount | Int | 单值 | - | - | - |
| activeRuntimeCount | Int | 单值 | - | - | - |
| activeTrainingJobCount | Int | 单值 | - | - | - |

### 4.2 主键与访问路径/FLPRM-DB-SECTION-4

- 逻辑标识：federationId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 4.3 更新语义/FLPRM-DB-USM-2

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。

<!-- em:section id="database.readmodel.联邦成员资格目录（FederationMembershipDirectory）" sources="domain/FederationLearningPlatform/context/FederationManagement/slice/FederationMembershipDirectory/readmodel/FederationMembershipDirectory domain/FederationLearningPlatform/context/FederationManagement/slice/FederationMembershipDirectory" -->
## 五、联邦成员资格目录（Federation Membership Directory）/FLPRM-DB-SECTION-5

归属： 联邦管理（Federation Management） / 联邦成员资格（Federation Membership） / 联邦成员资格目录（Federation Membership Directory）

逻辑形态： 集合/列表 Read Model

订阅事件： 联邦已创建（Federation Created）, 参与者已邀请（Participant Invited）, 参与者已加入（Participant Joined）, 参与者已拒绝（Participant Rejected）, 参与者邀请已撤销（Participant Invitation Revoked）, 参与者已暂停（Participant Suspended）, 参与者已移除（Participant Removed）, 组织已注册（Organization Registered）, 组织已激活（Organization Activated）

### 5.1 逻辑结构/FLPRM-DB-LS-3

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| federationId | UUID | 单值 | 标识 | - | - |
| organizationId | UUID | 单值 | 标识 | - | - |
| federationName | String | 可选 | - | - | 派生：FederationOverview.federationName |
| organizationName | String | 可选 | - | - | 派生：OrganizationDirectory.organizationName |
| membershipStatus | String | 单值 | - | - | - |
| invitationNote | String | 可选 | - | - | - |
| approvalNote | String | 可选 | - | - | - |

### 5.2 主键与访问路径/FLPRM-DB-SECTION-6

- 逻辑标识：federationId, organizationId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 5.3 更新语义/FLPRM-DB-USM-3

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。
- federationName: 派生：FederationOverview.federationName.
- organizationName: 派生：OrganizationDirectory.organizationName.

<!-- em:section id="database.readmodel.字典目录（DictionaryCatalog）" sources="domain/FederationLearningPlatform/context/DictionaryMaintenance/slice/DictionaryCatalog/readmodel/DictionaryCatalog domain/FederationLearningPlatform/context/DictionaryMaintenance/slice/DictionaryCatalog" -->
## 六、字典目录（Dictionary Catalog）/FLPRM-DB-SECTION-7

归属： 字典维护（Dictionary Maintenance） / 字典（Dictionary） / 字典目录（Dictionary Catalog）

逻辑形态： 集合/列表 Read Model

订阅事件： 字典已注册（Dictionary Registered）, 字典已更新（Dictionary Updated）, 字典已归档（Dictionary Archived）

### 6.1 逻辑结构/FLPRM-DB-LS-4

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| dictionaryId | UUID | 单值 | 标识 | - | - |
| dictionaryCode | DictionaryCode | 单值 | 查询 | - | - |
| dictionaryName | String | 单值 | - | - | - |
| description | String | 可选 | - | - | - |
| state | Dictionary.State | 单值 | - | - | - |
| registeredAt | DateTime | 单值 | - | - | - |
| updatedAt | DateTime | 可选 | - | - | - |
| archivedAt | DateTime | 可选 | - | - | - |
| archiveReason | String | 可选 | - | - | - |

### 6.2 主键与访问路径/FLPRM-DB-SECTION-8

- 逻辑标识：dictionaryId。
- 候选查询索引：dictionaryCode。
- 集合访问需要提供稳定排序与分页能力。

### 6.3 更新语义/FLPRM-DB-USM-4

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。

<!-- em:section id="database.readmodel.字典值目录（DictionaryValueCatalog）" sources="domain/FederationLearningPlatform/context/DictionaryMaintenance/slice/DictionaryValueCatalog/readmodel/DictionaryValueCatalog domain/FederationLearningPlatform/context/DictionaryMaintenance/slice/DictionaryValueCatalog" -->
## 七、字典值目录（Dictionary Value Catalog）/FLPRM-DB-SECTION-9

归属： 字典维护（Dictionary Maintenance） / 字典值（Dictionary Value） / 字典值目录（Dictionary Value Catalog）

逻辑形态： 集合/列表 Read Model

订阅事件： 字典值已添加（Dictionary Value Added）, 字典值已停用（Dictionary Value Disabled）, 字典值已启用（Dictionary Value Enabled）

### 7.1 逻辑结构/FLPRM-DB-LS-5

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| dictionaryValueId | UUID | 单值 | 标识 | - | - |
| dictionaryId | UUID | 单值 | - | - | - |
| dictionaryCode | DictionaryCode | 单值 | 查询 | - | - |
| valueCode | DictionaryValueCode | 单值 | - | - | - |
| displayName | String | 单值 | display | - | - |
| displayOrder | DisplayOrder | 可选 | - | - | - |
| description | String | 可选 | - | - | - |
| active | Boolean | 单值 | 查询 | - | - |
| state | DictionaryValue.State | 单值 | - | - | - |
| addedAt | DateTime | 单值 | - | - | - |
| updatedAt | DateTime | 可选 | - | - | - |
| disabledAt | DateTime | 可选 | - | - | - |
| disabledReason | String | 可选 | - | - | - |
| enabledAt | DateTime | 可选 | - | - | - |

### 7.2 主键与访问路径/FLPRM-DB-SECTION-10

- 逻辑标识：dictionaryValueId。
- 候选查询索引：dictionaryCode, active。
- 集合访问需要提供稳定排序与分页能力。

### 7.3 更新语义/FLPRM-DB-USM-5

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。

<!-- em:section id="database.readmodel.暂存文件目录（StagedFileCatalog）" sources="domain/FederationLearningPlatform/context/FileUpload/slice/StagedFileCatalog/readmodel/StagedFileCatalog domain/FederationLearningPlatform/context/FileUpload/slice/StagedFileCatalog" -->
## 八、暂存文件目录（Staged File Catalog）/FLPRM-DB-SECTION-11

归属： 文件上传（File Upload） / 暂存文件（Staged File） / 暂存文件目录（Staged File Catalog）

逻辑形态： 集合/列表 Read Model

订阅事件： 文件上传已暂存（File Upload Staged）, 暂存文件已消费（Staged File Consumed）, 暂存文件已丢弃（Staged File Discarded）, 暂存文件已过期（Staged File Expired）

### 8.1 逻辑结构/FLPRM-DB-LS-6

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| stagedFileId | UUID | 单值 | 标识 | - | - |
| originalFileName | String | 单值 | display | - | - |
| contentType | String | 可选 | - | - | - |
| sizeBytes | Long | 可选 | - | - | - |
| purpose | String | 单值 | 查询 | - | - |
| stagedFileLocation | String | 单值 | - | - | - |
| checksum | String | 可选 | - | - | - |
| state | StagedFile.State | 单值 | - | - | - |
| stagedAt | DateTime | 可选 | - | - | - |
| consumedAt | DateTime | 可选 | - | - | - |
| consumedByContext | String | 可选 | - | - | - |
| consumedByCommand | String | 可选 | - | - | - |
| consumedByCommandId | UUID | 可选 | - | - | - |
| discardedAt | DateTime | 可选 | - | - | - |
| discardReason | String | 可选 | - | - | - |
| expiresAt | DateTime | 可选 | - | - | - |
| expiredAt | DateTime | 可选 | - | - | - |
| expirationReason | String | 可选 | - | - | - |

### 8.2 主键与访问路径/FLPRM-DB-SECTION-12

- 逻辑标识：stagedFileId。
- 候选查询索引：purpose。
- 集合访问需要提供稳定排序与分页能力。

### 8.3 更新语义/FLPRM-DB-USM-6

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。

<!-- em:section id="database.readmodel.运行时基础设施包目录（RuntimeInfrastructurePackageCatalog）" sources="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RuntimeInfrastructurePackageCatalog/readmodel/RuntimeInfrastructurePackageCatalog domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RuntimeInfrastructurePackageCatalog" -->
## 九、运行时基础设施包目录（Runtime Infrastructure Package Catalog）/FLPRM-DB-SECTION-13

归属： 运行时预配（Runtime Provisioning） / 运行时基础设施包（Runtime Infrastructure Package） / 运行时基础设施包目录（Runtime Infrastructure Package Catalog）

逻辑形态： 集合/列表 Read Model

订阅事件： 运行时基础设施包已注册（Runtime Infrastructure Package Registered）

### 9.1 逻辑结构/FLPRM-DB-LS-7

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| runtimeInfrastructurePackageId | UUID | 单值 | 标识 | - | - |
| packageName | String | 单值 | - | - | - |
| packageVersion | String | 单值 | - | - | - |
| runtimeEnvironmentType | String | 单值 | - | - | - |
| runtimeDeploymentTargetType | String | 单值 | - | - | - |
| installProfile | String | 单值 | - | - | - |
| architecture | String | 单值 | - | - | - |
| installGuide | String | 单值 | - | - | - |
| state | RuntimeInfrastructurePackage.State | 单值 | - | - | - |

### 9.2 主键与访问路径/FLPRM-DB-SECTION-14

- 逻辑标识：runtimeInfrastructurePackageId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 9.3 更新语义/FLPRM-DB-USM-7

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。

<!-- em:section id="database.readmodel.运行时安装计划目录（RuntimeInstallationPlanCatalog）" sources="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RuntimeInstallationPlanCatalog/readmodel/RuntimeInstallationPlanCatalog domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RuntimeInstallationPlanCatalog" -->
## 十、运行时安装计划目录（Runtime Installation Plan Catalog）/FLPRM-DB-SECTION-15

归属： 运行时预配（Runtime Provisioning） / 运行时安装计划（Runtime Installation Plan） / 运行时安装计划目录（Runtime Installation Plan Catalog）

逻辑形态： 集合/列表 Read Model

订阅事件： 组织已注册（Organization Registered）, 运行时基础设施包已注册（Runtime Infrastructure Package Registered）, 运行时安装计划已创建（Runtime Installation Plan Created）, 运行时基础设施已计划（Runtime Infrastructure Planned）, 运行时基础设施已注册（Runtime Infrastructure Registered）, 运行时基础设施已验证（Runtime Infrastructure Verified）, 运行时基础设施验证失败（Runtime Infrastructure Verification Failed）, 运行时代理安装成功（Runtime Agent Installation Succeeded）, 运行时代理安装失败（Runtime Agent Installation Failed）, 运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）, 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed）, 运行时连接已建立（Runtime Connection Established）

### 10.1 逻辑结构/FLPRM-DB-LS-8

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| runtimeInstallationPlanId | UUID | 单值 | 标识 | - | - |
| organizationId | UUID | 单值 | - | - | - |
| organizationName | String | 可选 | - | - | 派生：OrganizationRegistered.organizationName；规则：通过 organizationId 查找并将组织显示名称反规范化到运行时安装计划目录中。 |
| runtimeInfrastructurePackageId | UUID | 单值 | - | - | - |
| runtimeInfrastructurePackageName | String | 可选 | display | - | 派生：RuntimeInfrastructurePackageCatalog.packageName |
| runtimeInfrastructurePackageVersion | String | 可选 | - | - | 派生：RuntimeInfrastructurePackageCatalog.packageVersion |
| runtimeName | String | 单值 | - | - | - |
| agentInstallMode | String | 单值 | - | - | - |
| expectedNodeCount | Int | 单值 | - | - | - |
| planStatus | String | 单值 | - | - | - |
| runtimeInfrastructureId | UUID | 可选 | - | - | - |
| observedNodeCount | Int | 可选 | - | - | - |
| runtimeAgentId | UUID | 可选 | - | - | - |
| runtimeAgentVersion | String | 可选 | - | - | - |
| plannedAt | DateTime | 单值 | - | - | - |
| verifiedAt | DateTime | 可选 | - | - | - |
| verificationFailedAt | DateTime | 可选 | - | - | - |
| verificationFailureReason | String | 可选 | - | - | - |
| agentReadyAt | DateTime | 可选 | - | - | - |
| agentDeploymentFailedAt | DateTime | 可选 | - | - | - |
| agentDeploymentFailureReason | String | 可选 | - | - | - |
| agentDeploymentRetryFailedAt | DateTime | 可选 | - | - | - |
| agentDeploymentRetryFailureReason | String | 可选 | - | - | - |
| lastConnectedAt | DateTime | 可选 | - | - | - |

### 10.2 主键与访问路径/FLPRM-DB-SECTION-16

- 逻辑标识：runtimeInstallationPlanId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 10.3 更新语义/FLPRM-DB-USM-8

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。
- organizationName: 派生：OrganizationRegistered.organizationName；规则：通过 organizationId 查找并将组织显示名称反规范化到运行时安装计划目录中。.
- runtimeInfrastructurePackageName: 派生：RuntimeInfrastructurePackageCatalog.packageName.
- runtimeInfrastructurePackageVersion: 派生：RuntimeInfrastructurePackageCatalog.packageVersion.

<!-- em:section id="database.readmodel.运行时安装指南（RuntimeInstallationGuide）" sources="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RuntimeInstallationGuide/readmodel/RuntimeInstallationGuide domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RuntimeInstallationGuide" -->
## 十一、运行时安装指南（Runtime Installation Guide）/FLPRM-DB-SECTION-17

归属： 运行时预配（Runtime Provisioning） / 运行时安装计划（Runtime Installation Plan） / 运行时安装指南（Runtime Installation Guide）

逻辑形态： 集合/列表 Read Model

订阅事件： 组织已注册（Organization Registered）, 运行时基础设施包已注册（Runtime Infrastructure Package Registered）, 运行时安装计划已创建（Runtime Installation Plan Created）

### 11.1 逻辑结构/FLPRM-DB-LS-9

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| runtimeInstallationPlanId | UUID | 单值 | 标识 | - | - |
| organizationId | UUID | 单值 | - | - | - |
| runtimeInfrastructureId | UUID | 单值 | - | - | - |
| runtimeInfrastructurePackageId | UUID | 可选 | - | - | 派生：RuntimeInstallationPlanCreated.runtimeInfrastructurePackageId；规则：按 runtimeInstallationPlanId 查询并显示所选基础设施安装包。 |
| runtimeInfrastructurePackageName | String | 可选 | - | - | 派生：RuntimeInfrastructurePackageCatalog.packageName |
| runtimeInfrastructurePackageVersion | String | 可选 | - | - | 派生：RuntimeInfrastructurePackageCatalog.packageVersion |
| infrastructureInstallGuide | String | 可选 | - | - | 派生：RuntimeInfrastructurePackageCatalog.installGuide |
| organizationName | String | 可选 | - | - | 派生：OrganizationRegistered.organizationName；规则：按 organizationId 查询并将组织显示名称反规范化为运行时安装指南。 |
| runtimeName | String | 可选 | - | - | 派生：RuntimeInstallationPlanCreated.runtimeName；规则：按 runtimeInstallationPlanId 查询并将计划运行时显示名称反规范化为运行时安装指南。 |
| bootstrapCommand | String | 单值 | - | - | 派生：RuntimeInstallationPlanCreated.runtimeInstallationPlanId；规则：使用运行时安装计划和平台管理的引导服务渲染可复制的 install/start 命令。 |
| runtimeDeploymentTargetType | String | 可选 | - | - | 派生：RuntimeInfrastructurePackageCatalog.runtimeDeploymentTargetType |
| runtimeEnvironmentType | String | 可选 | - | - | 派生：RuntimeInfrastructurePackageCatalog.runtimeEnvironmentType |
| agentInstallMode | String | 可选 | - | - | 派生：RuntimeInstallationPlanCreated.agentInstallMode；规则：按 runtimeInstallationPlanId 查询并显示平台应如何请求运行时代理安装。 |
| installProfile | String | 可选 | - | - | 派生：RuntimeInfrastructurePackageCatalog.installProfile |
| architecture | String | 可选 | - | - | 派生：RuntimeInfrastructurePackageCatalog.architecture |
| expectedNodeCount | Int | 可选 | - | - | 派生：RuntimeInstallationPlanCreated.expectedNodeCount；规则：按 runtimeInstallationPlanId 查询并显示计划节点数量。 |

### 11.2 主键与访问路径/FLPRM-DB-SECTION-18

- 逻辑标识：runtimeInstallationPlanId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 11.3 更新语义/FLPRM-DB-USM-9

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。
- runtimeInfrastructurePackageId: 派生：RuntimeInstallationPlanCreated.runtimeInfrastructurePackageId；规则：按 runtimeInstallationPlanId 查询并显示所选基础设施安装包。.
- runtimeInfrastructurePackageName: 派生：RuntimeInfrastructurePackageCatalog.packageName.
- runtimeInfrastructurePackageVersion: 派生：RuntimeInfrastructurePackageCatalog.packageVersion.
- infrastructureInstallGuide: 派生：RuntimeInfrastructurePackageCatalog.installGuide.
- organizationName: 派生：OrganizationRegistered.organizationName；规则：按 organizationId 查询并将组织显示名称反规范化为运行时安装指南。.
- runtimeName: 派生：RuntimeInstallationPlanCreated.runtimeName；规则：按 runtimeInstallationPlanId 查询并将计划运行时显示名称反规范化为运行时安装指南。.
- bootstrapCommand: 派生：RuntimeInstallationPlanCreated.runtimeInstallationPlanId；规则：使用运行时安装计划和平台管理的引导服务渲染可复制的 install/start 命令。.
- runtimeDeploymentTargetType: 派生：RuntimeInfrastructurePackageCatalog.runtimeDeploymentTargetType.
- runtimeEnvironmentType: 派生：RuntimeInfrastructurePackageCatalog.runtimeEnvironmentType.
- agentInstallMode: 派生：RuntimeInstallationPlanCreated.agentInstallMode；规则：按 runtimeInstallationPlanId 查询并显示平台应如何请求运行时代理安装。.
- installProfile: 派生：RuntimeInfrastructurePackageCatalog.installProfile.
- architecture: 派生：RuntimeInfrastructurePackageCatalog.architecture.
- expectedNodeCount: 派生：RuntimeInstallationPlanCreated.expectedNodeCount；规则：按 runtimeInstallationPlanId 查询并显示计划节点数量。.

<!-- em:section id="database.readmodel.运行时代理端点目录（RuntimeAgentEndpointCatalog）" sources="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RuntimeAgentEndpointCatalog/readmodel/RuntimeAgentEndpointCatalog domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RuntimeAgentEndpointCatalog" -->
## 十二、运行时代理端点目录（Runtime Agent Endpoint Catalog）/FLPRM-DB-SECTION-19

归属： 运行时预配（Runtime Provisioning） / 运行时基础设施（Runtime Infrastructure） / 运行时代理端点目录（Runtime Agent Endpoint Catalog）

逻辑形态： 集合/列表 Read Model

订阅事件： 运行时连接已建立（Runtime Connection Established）, 运行时身份已激活（Runtime Identity Activated）, 运行时身份已撤销（Runtime Identity Revoked）, 已检测到运行时代理离线（Runtime Agent Offline Detected）, 运行时代理已恢复（Runtime Agent Recovered）

### 12.1 逻辑结构/FLPRM-DB-LS-10

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| runtimeAgentId | UUID | 单值 | 标识 | - | - |
| runtimeId | UUID | 可选 | - | - | - |
| runtimeInfrastructureId | UUID | 单值 | - | - | - |
| organizationId | UUID | 单值 | - | - | - |
| runtimeName | String | 单值 | - | - | - |
| runtimeAgentEndpoint | String | 单值 | - | - | - |
| endpointScope | String | 单值 | - | - | - |
| connectionStatus | String | 单值 | - | - | - |
| connectedAt | DateTime | 可选 | - | - | - |
| activatedAt | DateTime | 可选 | - | - | - |

### 12.2 主键与访问路径/FLPRM-DB-SECTION-20

- 逻辑标识：runtimeAgentId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 12.3 更新语义/FLPRM-DB-USM-10

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。

<!-- em:section id="database.readmodel.运行时基础设施访问视图（RuntimeInfrastructureAccessView）" sources="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RuntimeInfrastructureAccessView/readmodel/RuntimeInfrastructureAccessView domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RuntimeInfrastructureAccessView" -->
## 十三、运行时基础设施访问视图（Runtime Infrastructure Access View）/FLPRM-DB-SECTION-21

归属： 运行时预配（Runtime Provisioning） / 运行时基础设施（Runtime Infrastructure） / 运行时基础设施访问视图（Runtime Infrastructure Access View）

逻辑形态： 集合/列表 Read Model

订阅事件： 组织已注册（Organization Registered）, 运行时基础设施包已注册（Runtime Infrastructure Package Registered）, 运行时安装计划已创建（Runtime Installation Plan Created）, 运行时基础设施已计划（Runtime Infrastructure Planned）, 运行时基础设施已注册（Runtime Infrastructure Registered）, 运行时基础设施已验证（Runtime Infrastructure Verified）, 运行时基础设施验证失败（Runtime Infrastructure Verification Failed）, 运行时代理安装成功（Runtime Agent Installation Succeeded）, 运行时代理安装失败（Runtime Agent Installation Failed）, 运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）, 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed）, 运行时连接已建立（Runtime Connection Established）

### 13.1 逻辑结构/FLPRM-DB-LS-11

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| runtimeInfrastructureId | UUID | 单值 | 标识 | - | - |
| organizationId | UUID | 单值 | - | - | - |
| runtimeInstallationPlanId | UUID | 单值 | - | - | - |
| runtimeInfrastructurePackageId | UUID | 单值 | - | - | - |
| runtimeInfrastructurePackageName | String | 可选 | - | - | 派生：RuntimeInfrastructurePackageCatalog.packageName |
| runtimeInfrastructurePackageVersion | String | 可选 | - | - | 派生：RuntimeInfrastructurePackageCatalog.packageVersion |
| organizationName | String | 可选 | - | - | 派生：OrganizationRegistered.organizationName；规则：按 organizationId 查询并将组织显示名称反规范化到运行时基础设施访问视图中。 |
| runtimeName | String | 可选 | - | - | 派生：RuntimeInstallationPlanCreated.runtimeName；规则：按 runtimeInfrastructureId 查询并将运行时显示名称反规范化到访问视图中。 |
| runtimeDeploymentTargetType | String | 可选 | - | - | 派生：RuntimeInfrastructurePackageCatalog.runtimeDeploymentTargetType |
| runtimeEnvironmentType | String | 可选 | - | - | 派生：RuntimeInfrastructurePackageCatalog.runtimeEnvironmentType |
| agentInstallMode | String | 单值 | - | - | - |
| expectedNodeCount | Int | 单值 | - | - | - |
| runtimeAgentId | UUID | 可选 | - | - | - |
| runtimeAgentVersion | String | 可选 | - | - | - |
| infrastructureVerifiedAt | DateTime | 可选 | - | - | - |
| infrastructureVerificationFailedAt | DateTime | 可选 | - | - | - |
| infrastructureVerificationFailureReason | String | 可选 | - | - | - |
| agentReadyAt | DateTime | 可选 | - | - | - |
| agentDeploymentFailedAt | DateTime | 可选 | - | - | - |
| agentDeploymentFailureReason | String | 可选 | - | - | - |
| agentDeploymentRetryFailedAt | DateTime | 可选 | - | - | - |
| agentDeploymentRetryFailureReason | String | 可选 | - | - | - |
| connectedAt | DateTime | 可选 | - | - | - |
| state | RuntimeInfrastructure.State | 单值 | - | - | - |

### 13.2 主键与访问路径/FLPRM-DB-SECTION-22

- 逻辑标识：runtimeInfrastructureId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 13.3 更新语义/FLPRM-DB-USM-11

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。
- runtimeInfrastructurePackageName: 派生：RuntimeInfrastructurePackageCatalog.packageName.
- runtimeInfrastructurePackageVersion: 派生：RuntimeInfrastructurePackageCatalog.packageVersion.
- organizationName: 派生：OrganizationRegistered.organizationName；规则：按 organizationId 查询并将组织显示名称反规范化到运行时基础设施访问视图中。.
- runtimeName: 派生：RuntimeInstallationPlanCreated.runtimeName；规则：按 runtimeInfrastructureId 查询并将运行时显示名称反规范化到访问视图中。.
- runtimeDeploymentTargetType: 派生：RuntimeInfrastructurePackageCatalog.runtimeDeploymentTargetType.
- runtimeEnvironmentType: 派生：RuntimeInfrastructurePackageCatalog.runtimeEnvironmentType.

<!-- em:section id="database.readmodel.当前推荐的特征架构目录（CurrentRecommendedFeatureSchemaCatalog）" sources="domain/FederationLearningPlatform/context/DatasetGovernance/slice/CurrentRecommendedFeatureSchemaCatalog/readmodel/CurrentRecommendedFeatureSchemaCatalog domain/FederationLearningPlatform/context/DatasetGovernance/slice/CurrentRecommendedFeatureSchemaCatalog" -->
## 十四、当前推荐的特征架构目录（Current Recommended Feature Schema Catalog）/FLPRM-DB-SECTION-23

归属： 数据集治理（Dataset Governance） / 特征架构（Feature Schema） / 当前推荐的特征架构目录（Current Recommended Feature Schema Catalog）

逻辑形态： 集合/列表 Read Model

订阅事件： 已标记当前推荐特征架构版本（Current Recommended Feature Schema Version Marked）

### 14.1 逻辑结构/FLPRM-DB-LS-12

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| featureDomain | String | 单值 | 标识 | - | - |
| recommendedFeatureSchemaId | UUID | 单值 | - | - | - |
| recommendedVersion | String | 单值 | - | - | - |
| recommendedAt | DateTime | 单值 | - | - | - |
| recommendationNote | String | 可选 | - | - | - |

### 14.2 主键与访问路径/FLPRM-DB-SECTION-24

- 逻辑标识：featureDomain。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 14.3 更新语义/FLPRM-DB-USM-12

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。

<!-- em:section id="database.readmodel.特征架构目录（FeatureSchemaCatalog）" sources="domain/FederationLearningPlatform/context/DatasetGovernance/slice/FeatureSchemaCatalog/readmodel/FeatureSchemaCatalog domain/FederationLearningPlatform/context/DatasetGovernance/slice/FeatureSchemaCatalog" -->
## 十五、特征架构目录（Feature Schema Catalog）/FLPRM-DB-SECTION-25

归属： 数据集治理（Dataset Governance） / 特征架构（Feature Schema） / 特征架构目录（Feature Schema Catalog）

逻辑形态： 集合/列表 Read Model

订阅事件： 特征架构已定义（Feature Schema Defined）, 特征架构已发布（Feature Schema Published）, 特征架构已弃用（Feature Schema Deprecated）, 特征架构已停用（Feature Schema Retired）, 特征架构版本已取代（Feature Schema Version Superseded）, 已标记当前推荐特征架构版本（Current Recommended Feature Schema Version Marked）

### 15.1 逻辑结构/FLPRM-DB-LS-13

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| featureSchemaId | UUID | 单值 | 标识 | - | - |
| featureDomain | String | 单值 | display | - | - |
| version | String | 单值 | - | - | - |
| dataModality | String | 单值 | - | - | - |
| features | FeatureDefinition | 列表 | - | - | - |
| labels | LabelDefinition | OptionalList | - | - | - |
| featureCount | Int | 单值 | - | - | - |
| schemaStatus | String | 单值 | - | - | - |
| supersededByFeatureSchemaId | UUID | 可选 | - | - | - |
| recommendedForDomain | Boolean | 单值 | - | - | - |

### 15.2 主键与访问路径/FLPRM-DB-SECTION-26

- 逻辑标识：featureSchemaId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 15.3 更新语义/FLPRM-DB-USM-13

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。

<!-- em:section id="database.readmodel.运行时数据集元数据目录（RuntimeDatasetMetadataCatalog）" sources="domain/FederationLearningPlatform/context/DatasetGovernance/slice/RuntimeDatasetMetadataCatalog/readmodel/RuntimeDatasetMetadataCatalog domain/FederationLearningPlatform/context/DatasetGovernance/slice/RuntimeDatasetMetadataCatalog" -->
## 十六、运行时数据集元数据目录（Runtime Dataset Metadata Catalog）/FLPRM-DB-SECTION-27

归属： 数据集治理（Dataset Governance） / 运行时数据集元数据（Runtime Dataset Metadata） / 运行时数据集元数据目录（Runtime Dataset Metadata Catalog）

逻辑形态： 集合/列表 Read Model

订阅事件： 组织已注册（Organization Registered）, 特征架构已定义（Feature Schema Defined）, 数据集元数据已报告（Dataset Metadata Reported）, 数据集元数据已重新分析（Dataset Metadata Reprofiled）, 代理数据集分析失败（Agent Dataset Profiling Failed）, 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed）

### 16.1 逻辑结构/FLPRM-DB-LS-14

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| metadataReportId | UUID | 单值 | 标识 | - | - |
| datasetId | UUID | 单值 | - | - | - |
| organizationId | UUID | 单值 | - | - | - |
| runtimeId | UUID | 单值 | - | - | - |
| featureSchemaId | UUID | 单值 | - | - | - |
| datasetName | String | 可选 | display | - | - |
| organizationName | String | 可选 | - | - | 派生：OrganizationRegistered.organizationName；规则：按 organizationId 查询并将组织显示名称反规范化为平台运行时数据集元数据目录。 |
| featureDomain | String | 可选 | - | - | 派生：FeatureSchemaDefined.featureDomain；规则：按 featureSchemaId 查询并将特征架构域反规范化为平台运行时数据集元数据目录。 |
| featureSchemaVersion | String | 可选 | - | - | 派生：FeatureSchemaDefined.version；规则：按 featureSchemaId 查询并将特征架构版本反规范化为平台运行时数据集元数据目录。 |
| sampleCount | Int | 单值 | - | - | - |
| featureCount | Int | 单值 | - | - | - |
| schemaCompatible | Boolean | 可选 | - | - | - |
| labelCompatible | Boolean | 可选 | - | - | - |
| missingValueRate | Decimal | 可选 | - | - | - |
| duplicateRate | Decimal | 可选 | - | - | - |
| qualityScore | Decimal | 可选 | - | - | - |
| nonIidScore | Decimal | 可选 | - | - | - |
| classBalanceScore | Decimal | 可选 | - | - | - |
| profilingStatus | String | 单值 | - | - | - |
| failureReason | String | 可选 | - | - | - |
| profiledAt | DateTime | 可选 | - | - | - |

### 16.2 主键与访问路径/FLPRM-DB-SECTION-28

- 逻辑标识：metadataReportId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 16.3 更新语义/FLPRM-DB-USM-14

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。
- organizationName: 派生：OrganizationRegistered.organizationName；规则：按 organizationId 查询并将组织显示名称反规范化为平台运行时数据集元数据目录。.
- featureDomain: 派生：FeatureSchemaDefined.featureDomain；规则：按 featureSchemaId 查询并将特征架构域反规范化为平台运行时数据集元数据目录。.
- featureSchemaVersion: 派生：FeatureSchemaDefined.version；规则：按 featureSchemaId 查询并将特征架构版本反规范化为平台运行时数据集元数据目录。.

<!-- em:section id="database.readmodel.模型制品目录（ModelArtifactCatalog）" sources="domain/FederationLearningPlatform/context/ModelRepository/slice/ModelArtifactCatalog/readmodel/ModelArtifactCatalog domain/FederationLearningPlatform/context/ModelRepository/slice/ModelArtifactCatalog" -->
## 十七、模型制品目录（Model Artifact Catalog）/FLPRM-DB-SECTION-29

归属： 模型仓库（Model Repository） / 模型制品（Model Artifact） / 模型制品目录（Model Artifact Catalog）

逻辑形态： 集合/列表 Read Model

订阅事件： 训练作业已创建（Training Job Created）, 模型工件已注册（Model Artifact Registered）, 全局模型已更新（Global Model Updated）

### 17.1 逻辑结构/FLPRM-DB-LS-15

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| modelId | UUID | 单值 | 标识 | - | - |
| modelName | String | 单值 | display | - | - |
| modelVersion | String | 单值 | - | - | - |
| sourceType | String | 可选 | - | - | - |
| modelArtifactUri | String | 单值 | - | - | - |
| modelRegistryRef | String | 单值 | - | - | - |
| modelFormat | String | 单值 | - | - | - |
| modelArtifactDigest | String | 单值 | - | - | - |
| modelSignatureUri | String | 可选 | - | - | - |
| modelSizeBytes | Int | 可选 | - | - | - |
| trainingJobId | UUID | 可选 | - | - | - |
| roundId | UUID | 可选 | - | - | - |
| trainingJobObjective | String | 可选 | - | - | 派生：TrainingJobCreated.objective；规则：通过 trainingJobId 查找并将训练作业目标反规范化至模型工件目录。 |
| state | ModelArtifact.State | 单值 | - | - | - |
| registeredAt | DateTime | 可选 | - | - | - |

### 17.2 主键与访问路径/FLPRM-DB-SECTION-30

- 逻辑标识：modelId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 17.3 更新语义/FLPRM-DB-USM-15

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。
- trainingJobObjective: 派生：TrainingJobCreated.objective；规则：通过 trainingJobId 查找并将训练作业目标反规范化至模型工件目录。.

<!-- em:section id="database.readmodel.训练运行配置目录（TrainingRunConfigurationCatalog）" sources="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/TrainingRunConfigurationCatalog/readmodel/TrainingRunConfigurationCatalog domain/FederationLearningPlatform/context/TrainingOrchestration/slice/TrainingRunConfigurationCatalog" -->
## 十八、训练运行配置目录（Training Run Configuration Catalog）/FLPRM-DB-SECTION-31

归属： 训练编排（Training Orchestration） / 训练运行配置（Training Run Configuration） / 训练运行配置目录（Training Run Configuration Catalog）

逻辑形态： 集合/列表 Read Model

订阅事件： 联邦已创建（Federation Created）, 特征架构已定义（Feature Schema Defined）, 训练运行配置已定义（Training Run Configuration Defined）, 训练运行配置已更新（Training Run Configuration Updated）, 训练运行配置已锁定（Training Run Configuration Locked）

### 18.1 逻辑结构/FLPRM-DB-LS-16

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| trainingRunConfigurationId | UUID | 单值 | 标识 | - | - |
| federationId | UUID | 单值 | - | - | - |
| featureSchemaId | UUID | 单值 | - | - | - |
| initialModelId | UUID | 单值 | - | - | - |
| initialModelName | String | 单值 | display | - | - |
| initialModelVersion | String | 单值 | - | - | - |
| federationName | String | 可选 | display | - | 派生：FederationCreated.federationName；规则：按 federationId 查找并将联邦显示名称非规范化到训练运行配置目录中。 |
| featureDomain | String | 可选 | - | - | 派生：FeatureSchemaDefined.featureDomain；规则：按 featureSchemaId 查找并将特征架构域非规范化到训练运行配置目录中。 |
| featureSchemaVersion | String | 可选 | - | - | 派生：FeatureSchemaDefined.version；规则：按 featureSchemaId 查找并将特征架构版本非规范化到训练运行配置目录中。 |
| initialModelArtifactUri | String | 单值 | - | - | - |
| initialModelRegistryRef | String | 单值 | - | - | - |
| initialModelFormat | String | 单值 | - | - | - |
| initialModelArtifactDigest | String | 单值 | - | - | - |
| initialModelSignatureUri | String | 可选 | - | - | - |
| strategyName | String | 单值 | - | - | - |
| aggregationAlgorithm | String | 单值 | - | - | - |
| maxRounds | Int | 单值 | - | - | - |
| minimumNodesPerRound | Int | 单值 | - | - | - |
| roundTimeoutSeconds | Int | 单值 | - | - | - |
| nodeResponseTimeoutSeconds | Int | 单值 | - | - | - |
| localEpochs | Int | 单值 | - | - | - |
| batchSize | Int | 单值 | - | - | - |
| learningRate | Decimal | 单值 | - | - | - |
| optimizer | String | 单值 | - | - | - |
| lossFunction | String | 单值 | - | - | - |
| gradientClippingNorm | Decimal | 可选 | - | - | - |
| secureAggregationRequired | Boolean | 单值 | - | - | - |
| differentialPrivacyEnabled | Boolean | 单值 | - | - | - |
| dpNoiseMultiplier | Decimal | 可选 | - | - | - |
| dpClipNorm | Decimal | 可选 | - | - | - |
| minimumAccuracy | Decimal | 单值 | - | - | - |
| minimumFairnessScore | Decimal | 可选 | - | - | - |
| failureToleranceRatio | Decimal | 单值 | - | - | - |
| updateReason | String | 可选 | - | - | - |
| lockedByTrainingJobId | UUID | 可选 | - | - | 派生：TrainingRunConfigurationLocked.trainingJobId；规则：记录在提交后锁定此配置的训练作业。 |
| state | TrainingRunConfiguration.State | 单值 | - | - | - |

### 18.2 主键与访问路径/FLPRM-DB-SECTION-32

- 逻辑标识：trainingRunConfigurationId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 18.3 更新语义/FLPRM-DB-USM-16

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。
- federationName: 派生：FederationCreated.federationName；规则：按 federationId 查找并将联邦显示名称非规范化到训练运行配置目录中。.
- featureDomain: 派生：FeatureSchemaDefined.featureDomain；规则：按 featureSchemaId 查找并将特征架构域非规范化到训练运行配置目录中。.
- featureSchemaVersion: 派生：FeatureSchemaDefined.version；规则：按 featureSchemaId 查找并将特征架构版本非规范化到训练运行配置目录中。.
- lockedByTrainingJobId: 派生：TrainingRunConfigurationLocked.trainingJobId；规则：记录在提交后锁定此配置的训练作业。.

<!-- em:section id="database.readmodel.训练参与者资格（TrainingParticipantEligibility）" sources="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/TrainingParticipantEligibility/readmodel/TrainingParticipantEligibility domain/FederationLearningPlatform/context/TrainingOrchestration/slice/TrainingParticipantEligibility" -->
## 十九、训练参与者资格（Training Participant Eligibility）/FLPRM-DB-SECTION-33

归属： 训练编排（Training Orchestration） / 训练作业（Training Job） / 训练参与者资格（Training Participant Eligibility）

逻辑形态： 集合/列表 Read Model

订阅事件： 联邦已创建（Federation Created）, 组织已注册（Organization Registered）, 特征架构已定义（Feature Schema Defined）, 训练作业已创建（Training Job Created）, 训练作业已提交（Training Job Submitted）, 参与者已加入（Participant Joined）, 参与者已暂停（Participant Suspended）, 参与者已移除（Participant Removed）, 运行时身份已激活（Runtime Identity Activated）, 运行时身份已撤销（Runtime Identity Revoked）, 运行时能力已检测（Runtime Capabilities Detected）, 运行时连接已建立（Runtime Connection Established）, 已检测到运行时代理离线（Runtime Agent Offline Detected）, 运行时代理已恢复（Runtime Agent Recovered）, 数据集已声明（Dataset Declared）, 数据集元数据已报告（Dataset Metadata Reported）, 数据集合约已验证（Dataset Contract Validated）, 数据集已批准用于训练（Dataset Approved For Training）, 数据集训练批准已撤销（Dataset Training Approval Revoked）, 代理数据集访问已验证（Agent Dataset Access Validated）

### 19.1 逻辑结构/FLPRM-DB-LS-17

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| trainingJobId | UUID | 单值 | 标识 | - | - |
| federationId | UUID | 单值 | - | - | - |
| organizationId | UUID | 单值 | - | - | - |
| runtimeId | UUID | 可选 | - | - | - |
| featureSchemaId | UUID | 单值 | - | - | - |
| federationName | String | 可选 | - | - | 派生：FederationCreated.federationName；规则：按 federationId 查找并将联邦显示名称反规范化为参与者资格。 |
| organizationName | String | 可选 | - | - | 派生：OrganizationRegistered.organizationName；规则：按 organizationId 查找并将组织显示名称反规范化为参与者资格。 |
| featureDomain | String | 可选 | - | - | 派生：FeatureSchemaDefined.featureDomain；规则：按 featureSchemaId 查找并将特征架构域反规范化为参与者资格。 |
| featureSchemaVersion | String | 可选 | - | - | 派生：FeatureSchemaDefined.version；规则：按 featureSchemaId 查找并将特征架构版本反规范化为参与者资格。 |
| participantStatus | String | 单值 | - | - | - |
| readinessStatus | String | 单值 | - | - | - |
| readinessStage | String | 单值 | - | - | - |
| eligibilityScore | Int | 单值 | - | - | - |
| runtimeIdentityActive | Boolean | 单值 | - | - | - |
| runtimeCapabilitySatisfied | Boolean | 单值 | - | - | - |
| runtimeConnectionEstablished | Boolean | 单值 | - | - | - |
| runtimeHealthy | Boolean | 单值 | - | - | - |
| datasetId | UUID | 可选 | - | - | - |
| datasetName | String | 可选 | - | - | - |
| datasetReady | Boolean | 单值 | - | - | - |
| datasetReadinessStatus | String | 单值 | - | - | - |
| matchedDatasetMetadataReady | Boolean | 单值 | - | - | - |
| datasetAccessValidated | Boolean | 单值 | - | - | - |
| datasetApprovedForTraining | Boolean | 单值 | - | - | - |
| schemaCompatible | Boolean | 可选 | - | - | - |
| labelCompatible | Boolean | 可选 | - | - | - |
| qualityScore | Decimal | 可选 | - | - | - |
| securityReady | Boolean | 单值 | - | - | - |
| eligible | Boolean | 单值 | - | - | - |
| eligibleRuntimeCount | Int | 单值 | - | - | - |
| minimumNodesPerRound | Int | 单值 | - | - | - |
| selectionReady | Boolean | 单值 | - | - | - |
| eligibilityReason | String | 可选 | - | - | - |
| ineligibleReasons | String | 列表 | - | - | - |
| warningReasons | String | 列表 | - | - | - |
| nextRequiredAction | String | 可选 | - | - | - |

### 19.2 主键与访问路径/FLPRM-DB-SECTION-34

- 逻辑标识：trainingJobId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 19.3 更新语义/FLPRM-DB-USM-17

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。
- federationName: 派生：FederationCreated.federationName；规则：按 federationId 查找并将联邦显示名称反规范化为参与者资格。.
- organizationName: 派生：OrganizationRegistered.organizationName；规则：按 organizationId 查找并将组织显示名称反规范化为参与者资格。.
- featureDomain: 派生：FeatureSchemaDefined.featureDomain；规则：按 featureSchemaId 查找并将特征架构域反规范化为参与者资格。.
- featureSchemaVersion: 派生：FeatureSchemaDefined.version；规则：按 featureSchemaId 查找并将特征架构版本反规范化为参与者资格。.

<!-- em:section id="database.readmodel.训练作业仪表板（TrainingJobDashboard）" sources="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/TrainingJobDashboard/readmodel/TrainingJobDashboard domain/FederationLearningPlatform/context/TrainingOrchestration/slice/TrainingJobDashboard" -->
## 二十、训练作业仪表板（Training Job Dashboard）/FLPRM-DB-SECTION-35

归属： 训练编排（Training Orchestration） / 训练作业（Training Job） / 训练作业仪表板（Training Job Dashboard）

逻辑形态： 集合/列表 Read Model

订阅事件： 联邦已创建（Federation Created）, 特征架构已定义（Feature Schema Defined）, 训练作业已创建（Training Job Created）, 训练作业已提交（Training Job Submitted）, 训练作业已暂停（Training Job Paused）, 训练作业已恢复（Training Job Resumed）, 训练作业已取消（Training Job Canceled）, 训练轮次已启动（Training Round Started）, 训练轮次已完成（Training Round Completed）, 训练作业已完成（Training Job Completed）

### 20.1 逻辑结构/FLPRM-DB-LS-18

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| trainingJobId | UUID | 单值 | 标识 | - | - |
| federationId | UUID | 单值 | - | - | - |
| trainingRunConfigurationId | UUID | 单值 | - | - | - |
| featureSchemaId | UUID | 单值 | - | - | - |
| federationName | String | 可选 | - | - | 派生：FederationCreated.federationName；规则：按 federationId 查找并将联邦显示名称非规范化到训练作业仪表板。 |
| featureDomain | String | 可选 | - | - | 派生：FeatureSchemaDefined.featureDomain；规则：按 featureSchemaId 查找并将特征架构域非规范化到训练作业仪表板。 |
| featureSchemaVersion | String | 可选 | - | - | 派生：FeatureSchemaDefined.version；规则：按 featureSchemaId 查找并将特征架构版本非规范化到训练作业仪表板。 |
| objective | String | 单值 | - | - | - |
| strategyName | String | 可选 | - | - | - |
| aggregationAlgorithm | String | 可选 | - | - | - |
| secureAggregationRequired | Boolean | 可选 | - | - | - |
| state | TrainingJob.State | 单值 | - | - | - |
| workflowStage | String | 单值 | - | - | - |
| workflowStep | Int | 单值 | - | - | - |
| nextAction | String | 可选 | - | - | - |
| availableActions | String | 列表 | - | - | - |
| blockedReason | String | 可选 | - | - | - |
| canSubmit | Boolean | 单值 | - | - | - |
| canStartRound | Boolean | 单值 | - | - | - |
| canPause | Boolean | 单值 | - | - | - |
| canResume | Boolean | 单值 | - | - | - |
| canCancel | Boolean | 单值 | - | - | - |
| canComplete | Boolean | 单值 | - | - | - |
| currentRoundNumber | Int | 单值 | - | - | - |
| startedRuntimeCount | Int | 单值 | - | - | - |
| minimumNodesPerRound | Int | 单值 | - | - | - |
| maxRounds | Int | 单值 | - | - | - |
| roundProgressPercent | Int | 单值 | - | - | - |
| globalAccuracy | Decimal | 可选 | - | - | - |
| finalModelId | UUID | 可选 | - | - | - |
| stopReason | String | 可选 | - | - | - |

### 20.2 主键与访问路径/FLPRM-DB-SECTION-36

- 逻辑标识：trainingJobId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 20.3 更新语义/FLPRM-DB-USM-18

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。
- federationName: 派生：FederationCreated.federationName；规则：按 federationId 查找并将联邦显示名称非规范化到训练作业仪表板。.
- featureDomain: 派生：FeatureSchemaDefined.featureDomain；规则：按 featureSchemaId 查找并将特征架构域非规范化到训练作业仪表板。.
- featureSchemaVersion: 派生：FeatureSchemaDefined.version；规则：按 featureSchemaId 查找并将特征架构版本非规范化到训练作业仪表板。.

<!-- em:section id="database.readmodel.训练轮次进度（TrainingRoundProgress）" sources="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/TrainingRoundProgress/readmodel/TrainingRoundProgress domain/FederationLearningPlatform/context/TrainingOrchestration/slice/TrainingRoundProgress" -->
## 二十一、训练轮次进度（Training Round Progress）/FLPRM-DB-SECTION-37

归属： 训练编排（Training Orchestration） / 训练轮次（Training Round） / 训练轮次进度（Training Round Progress）

逻辑形态： 集合/列表 Read Model

订阅事件： 训练作业已创建（Training Job Created）, 特征架构已定义（Feature Schema Defined）, 训练轮次参与者已选择（Training Round Participants Selected）, 训练轮次参与者选择失败（Training Round Participant Selection Failed）, 训练轮次已启动（Training Round Started）, 训练轮次启动失败（Training Round Start Failed）, 参与者执行计划已生成（Participant Execution Plan Generated）, 参与者执行计划已分发（Participant Execution Plan Dispatched）, 轮次执行已启动（Round Execution Started）, 轮次执行启动失败（Round Execution Start Failed）, Round Execution Start Retry Started, 轮次执行启动重试失败（Round Execution Start Retry Failed）, 轮次执行已完成（Round Execution Completed）, 轮次执行失败（Round Execution Failed）, Round Execution Runtime Retry Started, 轮次执行运行时重试失败（Round Execution Runtime Retry Failed）, 模型更新提交已接收（Model Update Submission Received）, 模型更新提交已接受（Model Update Submission Accepted）, 模型更新提交已拒绝（Model Update Submission Rejected）, 已请求安全聚合（Secure Aggregation Requested）, 全局模型已更新（Global Model Updated）, 全局模型评估已提交（Global Model Evaluation Submitted）, 训练轮次已完成（Training Round Completed）, 训练轮次失败（Training Round Failed）

### 21.1 逻辑结构/FLPRM-DB-LS-19

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| trainingJobId | UUID | 单值 | 标识 | - | - |
| trainingRunConfigurationId | UUID | 单值 | - | - | - |
| featureSchemaId | UUID | 单值 | - | - | - |
| roundId | UUID | 单值 | 标识 | - | - |
| trainingJobObjective | String | 可选 | - | - | 派生：TrainingJobCreated.objective；规则：通过 trainingJobId 查询并将训练作业目标非规范化到轮次进度中。 |
| featureDomain | String | 可选 | - | - | 派生：FeatureSchemaDefined.featureDomain；规则：通过 featureSchemaId 查询并将特征架构域非规范化到轮次进度中。 |
| featureSchemaVersion | String | 可选 | - | - | 派生：FeatureSchemaDefined.version；规则：通过 featureSchemaId 查询并将特征架构版本非规范化到轮次进度中。 |
| roundNumber | Int | 单值 | - | - | - |
| state | TrainingRound.State | 单值 | - | - | - |
| selectedOrganizationIds | UUID | 列表 | - | - | - |
| selectedParticipants | TrainingRoundParticipant | 列表 | - | - | - |
| selectedOrganizationCount | Int | 单值 | - | - | - |
| selectedRuntimeCount | Int | 单值 | - | - | - |
| targetRuntimeCount | Int | 单值 | - | - | - |
| executionPlanDispatchedCount | Int | 单值 | - | - | - |
| roundExecutionStartedCount | Int | 单值 | - | - | - |
| submittedModelUpdateCount | Int | 单值 | - | - | - |
| rejectedUpdateCount | Int | 单值 | - | - | - |
| acceptedModelUpdateCount | Int | 单值 | - | - | - |
| acceptedUpdateCount | Int | 单值 | - | - | - |
| pendingUpdateCount | Int | 单值 | - | - | - |
| failedRoundExecutionCount | Int | 单值 | - | - | - |
| completedRoundExecutionCount | Int | 单值 | - | - | - |
| retriedRoundExecutionCount | Int | 单值 | - | - | - |
| failedRoundExecutionRetryCount | Int | 单值 | - | - | - |
| quorumMet | Boolean | 单值 | - | - | - |
| quorumStatus | String | 单值 | - | - | - |
| minimumNodesPerRound | Int | 可选 | - | - | - |
| aggregationReady | Boolean | 单值 | - | - | - |
| secureAggregationRequired | Boolean | 单值 | - | - | - |
| secureAggregationStatus | String | 可选 | - | - | - |
| evaluationComplete | Boolean | 单值 | - | - | - |
| progressPercent | Int | 单值 | - | - | - |
| currentPhase | String | 单值 | - | - | - |
| nextAction | String | 可选 | - | - | - |
| blockedReason | String | 可选 | - | - | - |
| delayedReason | String | 可选 | - | - | - |
| roundStartedAt | DateTime | 可选 | - | - | - |
| contributionDeadlineAt | DateTime | 可选 | - | - | - |
| aggregationStartedAt | DateTime | 可选 | - | - | - |
| evaluationSubmittedAt | DateTime | 可选 | - | - | - |
| completedAt | DateTime | 可选 | - | - | - |
| failedAt | DateTime | 可选 | - | - | - |
| baseModelId | UUID | 可选 | - | - | - |
| artifactRefs | String | 列表 | - | - | - |
| rejectedUpdateReasons | String | 列表 | - | - | - |
| aggregatedModelId | UUID | 可选 | - | - | - |
| globalAccuracy | Decimal | 可选 | - | - | - |
| globalFairnessScore | Decimal | 可选 | - | - | - |
| failureReason | String | 可选 | - | - | - |

### 21.2 主键与访问路径/FLPRM-DB-SECTION-38

- 逻辑标识：trainingJobId, roundId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 21.3 更新语义/FLPRM-DB-USM-19

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。
- trainingJobObjective: 派生：TrainingJobCreated.objective；规则：通过 trainingJobId 查询并将训练作业目标非规范化到轮次进度中。.
- featureDomain: 派生：FeatureSchemaDefined.featureDomain；规则：通过 featureSchemaId 查询并将特征架构域非规范化到轮次进度中。.
- featureSchemaVersion: 派生：FeatureSchemaDefined.version；规则：通过 featureSchemaId 查询并将特征架构版本非规范化到轮次进度中。.

<!-- em:section id="database.readmodel.模型目录（ModelCatalog）" sources="domain/FederationLearningPlatform/context/ModelLifecycle/slice/ModelCatalog/readmodel/ModelCatalog domain/FederationLearningPlatform/context/ModelLifecycle/slice/ModelCatalog" -->
## 二十二、模型目录（Model Catalog）/FLPRM-DB-SECTION-39

归属： 模型生命周期（Model Lifecycle） / 模型（Model） / 模型目录（Model Catalog）

逻辑形态： 集合/列表 Read Model

订阅事件： 训练作业已创建（Training Job Created）, 模型候选已注册（Model Candidate Registered）, 模型评估包已记录（Model Evaluation Package Recorded）, 模型已批准（Model Approved）, 模型已提升到生产环境（Model Promoted To Production）, 模型已回滚（Model Rolled Back）, 模型已退役（Model Retired）

### 22.1 逻辑结构/FLPRM-DB-LS-20

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| modelId | UUID | 单值 | 标识 | - | - |
| trainingJobId | UUID | 单值 | - | - | - |
| finalRoundId | UUID | 单值 | - | - | - |
| modelArtifactId | UUID | 单值 | - | - | - |
| trainingJobObjective | String | 可选 | - | - | 派生：TrainingJobCreated.objective；规则：通过 trainingJobId 进行查找，并将训练作业目标反规范化到模型目录。 |
| modelArtifactDigest | String | 单值 | - | - | - |
| evaluationReportId | UUID | 单值 | - | - | - |
| finalGlobalAccuracy | Decimal | 单值 | - | - | - |
| state | Model.State | 单值 | - | - | - |
| releaseChannel | String | 可选 | - | - | - |
| productionStage | String | 可选 | - | - | - |
| previousModelId | UUID | 可选 | - | - | - |
| experimentId | UUID | 可选 | - | - | - |
| hyperparameterSnapshotId | UUID | 可选 | - | - | - |
| reproducibilityManifestId | UUID | 可选 | - | - | - |
| modelCardId | UUID | 可选 | - | - | - |
| baselineModelId | UUID | 可选 | - | - | - |
| hasEvaluationPackage | Boolean | 单值 | - | - | - |
| approvalStatus | String | 单值 | - | - | - |
| releaseStatus | String | 单值 | - | - | - |
| isProduction | Boolean | 单值 | - | - | - |
| canRecordEvaluationPackage | Boolean | 单值 | - | - | - |
| canApprove | Boolean | 单值 | - | - | - |
| canPromoteToProduction | Boolean | 单值 | - | - | - |
| canRollback | Boolean | 单值 | - | - | - |
| canRetire | Boolean | 单值 | - | - | - |
| blockedReason | String | 可选 | - | - | - |

### 22.2 主键与访问路径/FLPRM-DB-SECTION-40

- 逻辑标识：modelId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 22.3 更新语义/FLPRM-DB-USM-20

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。
- trainingJobObjective: 派生：TrainingJobCreated.objective；规则：通过 trainingJobId 进行查找，并将训练作业目标反规范化到模型目录。.

<!-- em:section id="database.readmodel.运行时最新遥测（RuntimeTelemetryLatest）" sources="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/RuntimeTelemetryLatest/readmodel/RuntimeTelemetryLatest domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/RuntimeTelemetryLatest" -->
## 二十三、运行时最新遥测（Runtime Telemetry Latest）/FLPRM-DB-SECTION-41

归属： 运行时监控（Runtime Monitoring） / 节点运行时健康（Node Runtime Health） / 运行时最新遥测（Runtime Telemetry Latest）

逻辑形态： 集合/列表 Read Model

订阅事件： 尚未明确订阅事件

### 23.1 逻辑结构/FLPRM-DB-LS-21

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| nodeId | UUID | 单值 | 标识 | - | - |
| runtimeAgentId | UUID | 单值 | - | - | - |
| federationId | UUID | 可选 | - | - | - |
| trainingJobId | UUID | 可选 | - | - | - |
| roundExecutionId | UUID | 可选 | - | - | - |
| cpuLoad | Decimal | 可选 | - | - | - |
| gpuLoad | Decimal | 可选 | - | - | - |
| memoryLoad | Decimal | 可选 | - | - | - |
| lastHeartbeatAt | DateTime | 可选 | - | - | - |
| heartbeatMissingBeyondThreshold | Boolean | 单值 | - | - | - |
| heartbeatObservedAfterOffline | Boolean | 单值 | - | - | - |
| resourcePressureDetected | Boolean | 单值 | - | - | - |
| telemetryRetentionPolicy | String | 单值 | - | - | - |

### 23.2 主键与访问路径/FLPRM-DB-SECTION-42

- 逻辑标识：nodeId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 23.3 更新语义/FLPRM-DB-USM-21

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。

<!-- em:section id="database.readmodel.运行时节点清单视图（RuntimeNodeInventoryView）" sources="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/RuntimeNodeInventoryView/readmodel/RuntimeNodeInventoryView domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/RuntimeNodeInventoryView" -->
## 二十四、运行时节点清单视图（Runtime Node Inventory View）/FLPRM-DB-SECTION-43

归属： 运行时监控（Runtime Monitoring） / 运行时节点清单（Runtime Node Inventory） / 运行时节点清单视图（Runtime Node Inventory View）

逻辑形态： 集合/列表 Read Model

订阅事件： 组织已注册（Organization Registered）, 运行时安装计划已创建（Runtime Installation Plan Created）, 运行时基础设施已注册（Runtime Infrastructure Registered）, 运行时节点清单已报告（Runtime Node Inventory Reported）

### 24.1 逻辑结构/FLPRM-DB-LS-22

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| nodeId | UUID | 单值 | 标识 | - | - |
| runtimeNodeInventoryReportId | UUID | 单值 | - | - | - |
| organizationId | UUID | 单值 | - | - | - |
| runtimeInfrastructureId | UUID | 单值 | - | - | - |
| runtimeAgentId | UUID | 单值 | - | - | - |
| organizationName | String | 可选 | - | - | 派生：OrganizationRegistered.organizationName；规则：按 organizationId 查询并将组织显示名称反规范化到运行时节点清单视图。 |
| runtimeName | String | 可选 | - | - | 派生：RuntimeInstallationPlanCreated.runtimeName；规则：按 runtimeInfrastructureId 查询并将运行时显示名称反规范化到运行时节点清单视图。 |
| runtimeNodeName | String | 单值 | - | - | - |
| infrastructureNodeId | String | 可选 | - | - | - |
| runtimeNodeRole | String | 单值 | - | - | - |
| nodeReady | Boolean | 单值 | - | - | - |
| runtimeEngineVersion | String | 可选 | - | - | - |
| containerEngineVersion | String | 可选 | - | - | - |
| operatingSystem | String | 可选 | - | - | - |
| architecture | String | 单值 | - | - | - |
| inventoryHash | String | 单值 | - | - | - |
| discoveredAt | DateTime | 单值 | - | - | - |
| recordedAt | DateTime | 单值 | - | - | - |

### 24.2 主键与访问路径/FLPRM-DB-SECTION-44

- 逻辑标识：nodeId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 24.3 更新语义/FLPRM-DB-USM-22

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。
- organizationName: 派生：OrganizationRegistered.organizationName；规则：按 organizationId 查询并将组织显示名称反规范化到运行时节点清单视图。.
- runtimeName: 派生：RuntimeInstallationPlanCreated.runtimeName；规则：按 runtimeInfrastructureId 查询并将运行时显示名称反规范化到运行时节点清单视图。.

<!-- em:section id="database.readmodel.运行时节点最新资源（RuntimeNodeResourceLatest）" sources="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/RuntimeNodeResourceLatest/readmodel/RuntimeNodeResourceLatest domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/RuntimeNodeResourceLatest" -->
## 二十五、运行时节点最新资源（Runtime Node Resource Latest）/FLPRM-DB-SECTION-45

归属： 运行时监控（Runtime Monitoring） / 运行时节点资源遥测（Runtime Node Resource Telemetry） / 运行时节点最新资源（Runtime Node Resource Latest）

逻辑形态： 集合/列表 Read Model

订阅事件： 尚未明确订阅事件

### 25.1 逻辑结构/FLPRM-DB-LS-23

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| nodeId | UUID | 单值 | 标识 | - | - |
| runtimeAgentId | UUID | 单值 | - | - | - |
| runtimeInfrastructureId | UUID | 可选 | - | - | - |
| runtimeNodeName | String | 可选 | - | - | - |
| nodeReady | Boolean | 单值 | - | - | - |
| allocatableCpuCores | Int | 单值 | - | - | - |
| allocatableMemoryGb | Int | 单值 | - | - | - |
| allocatableGpuCount | Int | 单值 | - | - | - |
| allocatedCpuCores | Int | 单值 | - | - | - |
| allocatedMemoryGb | Int | 单值 | - | - | - |
| allocatedGpuCount | Int | 单值 | - | - | - |
| availableCpuCores | Int | 单值 | - | - | - |
| availableMemoryGb | Int | 单值 | - | - | - |
| availableGpuCount | Int | 单值 | - | - | - |
| runningWorkloadCount | Int | 单值 | - | - | - |
| workloadCapacity | Int | 单值 | - | - | - |
| observedAt | DateTime | 单值 | - | - | - |
| allocatableCapacityChanged | Boolean | 单值 | - | - | - |
| telemetryRetentionPolicy | String | 单值 | - | - | - |

### 25.2 主键与访问路径/FLPRM-DB-SECTION-46

- 逻辑标识：nodeId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 25.3 更新语义/FLPRM-DB-USM-23

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。

<!-- em:section id="database.readmodel.运行时健康仪表板（RuntimeHealthDashboard）" sources="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/RuntimeHealthDashboard/readmodel/RuntimeHealthDashboard domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/RuntimeHealthDashboard" -->
## 二十六、运行时健康仪表板（Runtime Health Dashboard）/FLPRM-DB-SECTION-47

归属： 运行时监控（Runtime Monitoring） / 节点运行时健康（Node Runtime Health） / 运行时健康仪表板（Runtime Health Dashboard）

逻辑形态： 集合/列表 Read Model

订阅事件： 联邦已创建（Federation Created）, 训练作业已创建（Training Job Created）, 已检测到运行时代理离线（Runtime Agent Offline Detected）, 运行时代理已恢复（Runtime Agent Recovered）, 已检测到运行时节点资源压力（Runtime Node Resource Pressure Detected）, 运行时节点容量已更改（Runtime Node Capacity Changed）, 运行时节点清单已报告（Runtime Node Inventory Reported）, 训练警报已发出（Training Alert Raised）

### 26.1 逻辑结构/FLPRM-DB-LS-24

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| nodeId | UUID | 单值 | 标识 | - | - |
| runtimeAgentId | UUID | 单值 | - | - | - |
| federationId | UUID | 单值 | - | - | - |
| trainingJobId | UUID | 可选 | - | - | - |
| roundExecutionId | UUID | 可选 | - | - | - |
| federationName | String | 可选 | - | - | 派生：FederationCreated.federationName；规则：按 federationId 查找并将联合体显示名称反规范化到运行时健康仪表板。 |
| trainingJobObjective | String | 可选 | - | - | 派生：TrainingJobCreated.objective；规则：按 trainingJobId 查找并将训练作业目标反规范化到运行时健康仪表板。 |
| cpuLoad | Decimal | 单值 | - | - | - |
| gpuLoad | Decimal | 单值 | - | - | - |
| memoryLoad | Decimal | 单值 | - | - | - |
| nodeReady | Boolean | 可选 | - | - | - |
| availableCpuCores | Int | 可选 | - | - | - |
| availableMemoryGb | Int | 可选 | - | - | - |
| availableGpuCount | Int | 可选 | - | - | - |
| runningWorkloadCount | Int | 可选 | - | - | - |
| workloadCapacity | Int | 可选 | - | - | - |
| healthStatus | String | 单值 | - | - | - |
| lastHeartbeatAt | DateTime | 单值 | - | - | - |
| lastResourceSnapshotAt | DateTime | 可选 | - | - | - |

### 26.2 主键与访问路径/FLPRM-DB-SECTION-48

- 逻辑标识：nodeId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 26.3 更新语义/FLPRM-DB-USM-24

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。
- federationName: 派生：FederationCreated.federationName；规则：按 federationId 查找并将联合体显示名称反规范化到运行时健康仪表板。.
- trainingJobObjective: 派生：TrainingJobCreated.objective；规则：按 trainingJobId 查找并将训练作业目标反规范化到运行时健康仪表板。.

<!-- em:section id="database.readmodel.训练告警目录（TrainingAlertCatalog）" sources="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/TrainingAlertCatalog/readmodel/TrainingAlertCatalog domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/TrainingAlertCatalog" -->
## 二十七、训练告警目录（Training Alert Catalog）/FLPRM-DB-SECTION-49

归属： 运行时监控（Runtime Monitoring） / 训练告警（Training Alert） / 训练告警目录（Training Alert Catalog）

逻辑形态： 集合/列表 Read Model

订阅事件： 运行时节点清单已报告（Runtime Node Inventory Reported）, 训练作业已创建（Training Job Created）, 训练警报已发出（Training Alert Raised）, 训练警报已确认（Training Alert Acknowledged）, 训练警报已解决（Training Alert Resolved）

### 27.1 逻辑结构/FLPRM-DB-LS-25

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| alertId | UUID | 单值 | 标识 | - | - |
| nodeId | UUID | 单值 | - | - | - |
| trainingJobId | UUID | 可选 | - | - | - |
| runtimeNodeName | String | 可选 | - | - | 派生：RuntimeNodeInventoryReported.runtimeNodeName；规则：按 nodeId 查找并将运行时节点显示名称反规范化到训练警报目录中。 |
| trainingJobObjective | String | 可选 | - | - | 派生：TrainingJobCreated.objective；规则：按 trainingJobId 查找并将训练作业目标反规范化到训练警报目录中。 |
| severity | String | 单值 | - | - | - |
| message | String | 单值 | - | - | - |
| state | TrainingAlert.State | 单值 | - | - | - |
| acknowledgedAt | DateTime | 可选 | - | - | - |
| resolvedAt | DateTime | 可选 | - | - | - |
| resolutionSummary | String | 可选 | - | - | - |
| canAcknowledge | Boolean | 单值 | - | - | - |
| canResolve | Boolean | 单值 | - | - | - |

### 27.2 主键与访问路径/FLPRM-DB-SECTION-50

- 逻辑标识：alertId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 27.3 更新语义/FLPRM-DB-USM-25

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。
- runtimeNodeName: 派生：RuntimeNodeInventoryReported.runtimeNodeName；规则：按 nodeId 查找并将运行时节点显示名称反规范化到训练警报目录中。.
- trainingJobObjective: 派生：TrainingJobCreated.objective；规则：按 trainingJobId 查找并将训练作业目标反规范化到训练警报目录中。.

<!-- em:section id="database.readmodel.审计记录日志（AuditRecordLog）" sources="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/AuditRecordLog/readmodel/AuditRecordLog domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/AuditRecordLog" -->
## 二十八、审计记录日志（Audit Record Log）/FLPRM-DB-SECTION-51

归属： 运行时监控（Runtime Monitoring） / 审计记录（Audit Record） / 审计记录日志（Audit Record Log）

逻辑形态： 集合/列表 Read Model

订阅事件： 审计跟踪已追加（Audit Trail Appended）

### 28.1 逻辑结构/FLPRM-DB-LS-26

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| auditRecordId | UUID | 单值 | 标识 | - | - |
| sourceEventName | String | 单值 | - | - | - |
| sourceEntityId | UUID | 可选 | - | - | - |
| severity | String | 单值 | - | - | - |
| payloadHash | String | 单值 | - | - | - |

### 28.2 主键与访问路径/FLPRM-DB-SECTION-52

- 逻辑标识：auditRecordId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 28.3 更新语义/FLPRM-DB-USM-26

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。

<!-- em:section id="database.readmodel.运行时标识目录（RuntimeIdentityCatalog）" sources="domain/FederationLearningPlatform/context/RuntimeGovernance/slice/RuntimeIdentityCatalog/readmodel/RuntimeIdentityCatalog domain/FederationLearningPlatform/context/RuntimeGovernance/slice/RuntimeIdentityCatalog" -->
## 二十九、运行时标识目录（Runtime Identity Catalog）/FLPRM-DB-SECTION-53

归属： 运行时治理（Runtime Governance） / 运行时标识（Runtime Identity） / 运行时标识目录（Runtime Identity Catalog）

逻辑形态： 集合/列表 Read Model

订阅事件： 组织已注册（Organization Registered）, 运行时身份已激活（Runtime Identity Activated）, 运行时身份已撤销（Runtime Identity Revoked）

### 29.1 逻辑结构/FLPRM-DB-LS-27

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| runtimeId | UUID | 单值 | 标识 | - | - |
| runtimeInfrastructureId | UUID | 单值 | - | - | - |
| runtimeAgentId | UUID | 单值 | - | - | - |
| organizationId | UUID | 单值 | - | - | - |
| organizationName | String | 可选 | - | - | 派生：OrganizationRegistered.organizationName |
| runtimeName | String | 单值 | display | - | - |
| identityStatus | String | 单值 | - | - | - |
| activatedAt | DateTime | 可选 | - | - | - |
| revokedAt | DateTime | 可选 | - | - | - |

### 29.2 主键与访问路径/FLPRM-DB-SECTION-54

- 逻辑标识：runtimeId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 29.3 更新语义/FLPRM-DB-USM-27

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。
- organizationName: 派生：OrganizationRegistered.organizationName.

<!-- em:section id="database.readmodel.运行时能力目录（RuntimeCapabilityCatalog）" sources="domain/FederationLearningPlatform/context/RuntimeGovernance/slice/RuntimeCapabilityCatalog/readmodel/RuntimeCapabilityCatalog domain/FederationLearningPlatform/context/RuntimeGovernance/slice/RuntimeCapabilityCatalog" -->
## 三十、运行时能力目录（Runtime Capability Catalog）/FLPRM-DB-SECTION-55

归属： 运行时治理（Runtime Governance） / 运行时能力（Runtime Capability） / 运行时能力目录（Runtime Capability Catalog）

逻辑形态： 集合/列表 Read Model

订阅事件： 运行时能力已检测（Runtime Capabilities Detected）

### 30.1 逻辑结构/FLPRM-DB-LS-28

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| runtimeId | UUID | 单值 | 标识 | - | - |
| capabilityTypes | String | 列表 | - | - | - |
| capabilityStatus | String | 单值 | - | - | - |
| detectedAt | DateTime | 单值 | - | - | - |

### 30.2 主键与访问路径/FLPRM-DB-SECTION-56

- 逻辑标识：runtimeId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 30.3 更新语义/FLPRM-DB-USM-28

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。

<!-- em:section id="database.readmodel.安全聚合会话目录（SecureAggregationSessionCatalog）" sources="domain/FederationLearningPlatform/context/SecureAggregation/slice/SecureAggregationSessionCatalog/readmodel/SecureAggregationSessionCatalog domain/FederationLearningPlatform/context/SecureAggregation/slice/SecureAggregationSessionCatalog" -->
## 三十一、安全聚合会话目录（Secure Aggregation Session Catalog）/FLPRM-DB-SECTION-57

归属： 安全聚合（Secure Aggregation） / 安全聚合会话（Secure Aggregation Session） / 安全聚合会话目录（Secure Aggregation Session Catalog）

逻辑形态： 集合/列表 Read Model

订阅事件： 安全聚合会话已创建（Secure Aggregation Session Created）, 安全聚合参与者已选择（Secure Aggregation Participants Selected）, 同态加密上下文已准备（Homomorphic Encryption Context Prepared）, 已接收加密模型更新（Encrypted Model Update Received）, 安全聚合已完成（Secure Aggregation Completed）, 安全聚合失败（Secure Aggregation Failed）

### 31.1 逻辑结构/FLPRM-DB-LS-29

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| secureAggregationSessionId | UUID | 单值 | 标识 | - | - |
| trainingJobId | UUID | 单值 | - | - | - |
| trainingRunConfigurationId | UUID | 单值 | - | - | - |
| featureSchemaId | UUID | 单值 | - | - | - |
| roundId | UUID | 单值 | - | - | - |
| requiredParticipantCount | Int | 单值 | - | - | - |
| acceptedRuntimeIds | UUID | 列表 | - | - | - |
| selectedRuntimeIds | UUID | 列表 | - | - | - |
| selectedParticipantCount | Int | 可选 | - | - | - |
| encryptionContextPrepared | Boolean | 单值 | - | - | - |
| receivedEncryptedUpdateCount | Int | 单值 | - | - | - |
| encryptionScheme | String | 可选 | - | - | - |
| publicKeyVersion | String | 可选 | - | - | - |
| encryptedParameterScale | Int | 可选 | - | - | - |
| aggregatedModelId | UUID | 可选 | - | - | - |
| modelFormat | String | 可选 | - | - | - |
| modelArtifactDigest | String | 可选 | - | - | - |
| state | SecureAggregationSession.State | 单值 | - | - | - |
| failureReason | String | 可选 | - | - | - |
| createdAt | DateTime | 单值 | - | - | - |
| selectedAt | DateTime | 可选 | - | - | - |
| encryptionContextPreparedAt | DateTime | 可选 | - | - | - |
| decryptedAt | DateTime | 可选 | - | - | - |
| completedAt | DateTime | 可选 | - | - | - |
| failedAt | DateTime | 可选 | - | - | - |

### 31.2 主键与访问路径/FLPRM-DB-SECTION-58

- 逻辑标识：secureAggregationSessionId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 31.3 更新语义/FLPRM-DB-USM-29

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。

<!-- em:section id="database.readmodel.运行时数据集绑定目录（RuntimeDatasetBindingCatalog）" sources="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/RuntimeDatasetBindingCatalog/readmodel/RuntimeDatasetBindingCatalog domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/RuntimeDatasetBindingCatalog" -->
## 三十二、运行时数据集绑定目录（Runtime Dataset Binding Catalog）/FLPRM-DB-SECTION-59

归属： 运行时代理操作（Runtime Agent Operations） / 运行时数据集绑定（Runtime Dataset Binding） / 运行时数据集绑定目录（Runtime Dataset Binding Catalog）

逻辑形态： 集合/列表 Read Model

订阅事件： 数据集已声明（Dataset Declared）, 运行时数据集绑定已配置（Runtime Dataset Binding Configured）

### 32.1 逻辑结构/FLPRM-DB-LS-30

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| runtimeDatasetBindingId | UUID | 单值 | 标识 | - | - |
| datasetId | UUID | 单值 | - | - | - |
| organizationId | UUID | 单值 | - | - | - |
| runtimeId | UUID | 单值 | - | - | - |
| datasetName | String | 可选 | display | - | - |
| dataSourceType | String | 单值 | - | - | - |
| host | String | 可选 | - | - | - |
| port | Int | 可选 | - | - | - |
| url | String | 可选 | - | - | - |
| databaseName | String | 可选 | - | - | - |
| schemaName | String | 可选 | - | - | - |
| tableName | String | 可选 | - | - | - |
| filePath | String | 可选 | - | - | - |
| objectBucket | String | 可选 | - | - | - |
| objectPrefix | String | 可选 | - | - | - |
| dataFormat | String | 单值 | - | - | - |
| credentialSecretName | String | 可选 | - | - | - |
| configuredAt | DateTime | 单值 | - | - | - |

### 32.2 主键与访问路径/FLPRM-DB-SECTION-60

- 逻辑标识：runtimeDatasetBindingId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 32.3 更新语义/FLPRM-DB-USM-30

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。

<!-- em:section id="database.readmodel.代理数据集访问验证目录（AgentDatasetAccessValidationCatalog）" sources="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/AgentDatasetAccessValidationCatalog/readmodel/AgentDatasetAccessValidationCatalog domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/AgentDatasetAccessValidationCatalog" -->
## 三十三、代理数据集访问验证目录（Agent Dataset Access Validation Catalog）/FLPRM-DB-SECTION-61

归属： 运行时代理操作（Runtime Agent Operations） / 代理数据集访问验证（Agent Dataset Access Validation） / 代理数据集访问验证目录（Agent Dataset Access Validation Catalog）

逻辑形态： 集合/列表 Read Model

订阅事件： 数据集已声明（Dataset Declared）, 代理数据集访问已验证（Agent Dataset Access Validated）, 代理数据集访问验证失败（Agent Dataset Access Validation Failed）, 代理数据集访问已重新验证（Agent Dataset Access Revalidated）, 代理数据集访问重新验证失败（Agent Dataset Access Revalidation Failed）

### 33.1 逻辑结构/FLPRM-DB-LS-31

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| datasetAccessValidationId | UUID | 单值 | 标识 | - | - |
| runtimeDatasetBindingId | UUID | 单值 | - | - | - |
| datasetId | UUID | 单值 | - | - | - |
| organizationId | UUID | 单值 | - | - | - |
| featureSchemaId | UUID | 单值 | - | - | - |
| runtimeId | UUID | 单值 | - | - | - |
| datasetName | String | 可选 | - | - | - |
| readable | Boolean | 单值 | - | - | - |
| schemaReadable | Boolean | 单值 | - | - | - |
| sampleBatchReadable | Boolean | 单值 | - | - | - |
| validationStatus | String | 单值 | - | - | - |
| failureReason | String | 可选 | - | - | - |
| validatedAt | DateTime | 单值 | - | - | - |

### 33.2 主键与访问路径/FLPRM-DB-SECTION-62

- 逻辑标识：datasetAccessValidationId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 33.3 更新语义/FLPRM-DB-USM-31

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。

<!-- em:section id="database.readmodel.数据集能力（DatasetCapability）" sources="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/DatasetCapability/readmodel/DatasetCapability domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/DatasetCapability" -->
## 三十四、数据集能力（Dataset Capability）/FLPRM-DB-SECTION-63

归属： 运行时代理操作（Runtime Agent Operations） / 数据集（Dataset） / 数据集能力（Dataset Capability）

逻辑形态： 集合/列表 Read Model

订阅事件： 组织已注册（Organization Registered）, 特征架构已定义（Feature Schema Defined）, 数据集已声明（Dataset Declared）, 代理数据集元数据已报告（Agent Dataset Metadata Reported）, 代理数据集分析失败（Agent Dataset Profiling Failed）, 代理数据集已重新分析（Agent Dataset Reprofiled）, 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed）, 数据集合约已验证（Dataset Contract Validated）, 数据集合约验证失败（Dataset Contract Validation Failed）, 数据集合约已重新验证（Dataset Contract Revalidated）, 数据集合约重新验证失败（Dataset Contract Revalidation Failed）, 数据集已拒绝用于训练（Dataset Rejected For Training）, 数据集已批准用于训练（Dataset Approved For Training）, 数据集训练批准已撤销（Dataset Training Approval Revoked）

### 34.1 逻辑结构/FLPRM-DB-LS-32

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| datasetId | UUID | 单值 | 标识 | - | - |
| organizationId | UUID | 单值 | - | - | - |
| runtimeId | UUID | 可选 | - | - | - |
| featureSchemaId | UUID | 单值 | - | - | - |
| features | FeatureDefinition | 列表 | - | - | - |
| labels | LabelDefinition | OptionalList | - | - | - |
| organizationName | String | 可选 | - | - | 派生：OrganizationRegistered.organizationName；规则：通过 organizationId 查找并将组织显示名称反规范化到数据集能力目录中。 |
| featureDomain | String | 可选 | - | - | 派生：FeatureSchemaDefined.featureDomain；规则：通过 featureSchemaId 查找并将特征架构域反规范化到数据集能力目录中。 |
| featureSchemaVersion | String | 可选 | - | - | 派生：FeatureSchemaDefined.version；规则：通过 featureSchemaId 查找并将特征架构版本反规范化到数据集能力目录中。 |
| datasetName | String | 单值 | display | - | - |
| datasetUsage | String | 单值 | - | - | - |
| sampleCount | Int | 可选 | - | - | - |
| featureCount | Int | 可选 | - | - | - |
| schemaCompatible | Boolean | 可选 | - | - | - |
| labelCompatible | Boolean | 可选 | - | - | - |
| qualityScore | Decimal | 可选 | - | - | - |
| nonIidScore | Decimal | 可选 | - | - | - |
| metadataReportId | UUID | 可选 | - | - | - |
| metadataStatus | String | 单值 | - | - | - |
| contractStatus | String | 单值 | - | - | - |
| approvalStatus | String | 单值 | - | - | - |
| approved | Boolean | 单值 | - | - | - |
| lastProfiledAt | DateTime | 可选 | - | - | - |

### 34.2 主键与访问路径/FLPRM-DB-SECTION-64

- 逻辑标识：datasetId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 34.3 更新语义/FLPRM-DB-USM-32

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。
- organizationName: 派生：OrganizationRegistered.organizationName；规则：通过 organizationId 查找并将组织显示名称反规范化到数据集能力目录中。.
- featureDomain: 派生：FeatureSchemaDefined.featureDomain；规则：通过 featureSchemaId 查找并将特征架构域反规范化到数据集能力目录中。.
- featureSchemaVersion: 派生：FeatureSchemaDefined.version；规则：通过 featureSchemaId 查找并将特征架构版本反规范化到数据集能力目录中。.

<!-- em:section id="database.readmodel.数据集就绪状态（DatasetReadiness）" sources="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/DatasetReadiness/readmodel/DatasetReadiness domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/DatasetReadiness" -->
## 三十五、数据集就绪状态（Dataset Readiness）/FLPRM-DB-SECTION-65

归属： 运行时代理操作（Runtime Agent Operations） / 数据集（Dataset） / 数据集就绪状态（Dataset Readiness）

逻辑形态： 集合/列表 Read Model

订阅事件： 组织已注册（Organization Registered）, 特征架构已定义（Feature Schema Defined）, 数据集已声明（Dataset Declared）, 代理数据集元数据已报告（Agent Dataset Metadata Reported）, 代理数据集分析失败（Agent Dataset Profiling Failed）, 代理数据集已重新分析（Agent Dataset Reprofiled）, 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed）, 数据集合约已验证（Dataset Contract Validated）, 数据集合约验证失败（Dataset Contract Validation Failed）, 数据集合约已重新验证（Dataset Contract Revalidated）, 数据集合约重新验证失败（Dataset Contract Revalidation Failed）, 数据集已拒绝用于训练（Dataset Rejected For Training）, 数据集已批准用于训练（Dataset Approved For Training）, 数据集训练批准已撤销（Dataset Training Approval Revoked）, 代理数据集访问已验证（Agent Dataset Access Validated）, 代理数据集访问验证失败（Agent Dataset Access Validation Failed）, 代理数据集访问已重新验证（Agent Dataset Access Revalidated）, 代理数据集访问重新验证失败（Agent Dataset Access Revalidation Failed）, 运行时连接已建立（Runtime Connection Established）, 已检测到运行时代理离线（Runtime Agent Offline Detected）, 运行时代理已恢复（Runtime Agent Recovered）

### 35.1 逻辑结构/FLPRM-DB-LS-33

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| datasetId | UUID | 单值 | 标识 | - | - |
| organizationId | UUID | 单值 | - | - | - |
| runtimeId | UUID | 可选 | - | - | - |
| featureSchemaId | UUID | 单值 | - | - | - |
| datasetName | String | 单值 | display | - | - |
| organizationName | String | 可选 | - | - | 派生：OrganizationRegistered.organizationName；规则：通过 organizationId 查找并将组织显示名称反规范化到数据集就绪视图中。 |
| featureDomain | String | 可选 | - | - | 派生：FeatureSchemaDefined.featureDomain；规则：通过 featureSchemaId 查找并将特征架构域反规范化到数据集就绪视图中。 |
| featureSchemaVersion | String | 可选 | - | - | 派生：FeatureSchemaDefined.version；规则：通过 featureSchemaId 查找并将特征架构版本反规范化到数据集就绪视图中。 |
| datasetUsage | String | 单值 | - | - | - |
| metadataStatus | String | 单值 | - | - | - |
| contractStatus | String | 单值 | - | - | - |
| approvalStatus | String | 单值 | - | - | - |
| accessStatus | String | 单值 | - | - | - |
| runtimeStatus | String | 单值 | - | - | - |
| overallReadiness | String | 单值 | - | - | - |
| readyForTraining | Boolean | 单值 | - | - | - |
| canBeSelectedForTraining | Boolean | 单值 | - | - | - |
| readinessScore | Int | 单值 | - | - | - |
| missingRequirements | String | 列表 | - | - | - |
| blockingReasons | String | 列表 | - | - | - |
| warnings | String | 列表 | - | - | - |
| sampleCount | Int | 可选 | - | - | - |
| featureCount | Int | 可选 | - | - | - |
| schemaCompatible | Boolean | 可选 | - | - | - |
| labelCompatible | Boolean | 可选 | - | - | - |
| qualityScore | Decimal | 可选 | - | - | - |
| nonIidScore | Decimal | 可选 | - | - | - |
| classBalanceScore | Decimal | 可选 | - | - | - |
| metadataReportId | UUID | 可选 | - | - | - |
| datasetAccessValidationId | UUID | 可选 | - | - | - |
| readable | Boolean | 可选 | - | - | - |
| schemaReadable | Boolean | 可选 | - | - | - |
| sampleBatchReadable | Boolean | 可选 | - | - | - |
| lastProfiledAt | DateTime | 可选 | - | - | - |
| lastAccessValidatedAt | DateTime | 可选 | - | - | - |
| lastRuntimeHeartbeatAt | DateTime | 可选 | - | - | - |
| lastUpdatedAt | DateTime | 可选 | - | - | - |

### 35.2 主键与访问路径/FLPRM-DB-SECTION-66

- 逻辑标识：datasetId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 35.3 更新语义/FLPRM-DB-USM-33

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。
- organizationName: 派生：OrganizationRegistered.organizationName；规则：通过 organizationId 查找并将组织显示名称反规范化到数据集就绪视图中。.
- featureDomain: 派生：FeatureSchemaDefined.featureDomain；规则：通过 featureSchemaId 查找并将特征架构域反规范化到数据集就绪视图中。.
- featureSchemaVersion: 派生：FeatureSchemaDefined.version；规则：通过 featureSchemaId 查找并将特征架构版本反规范化到数据集就绪视图中。.

<!-- em:section id="database.readmodel.轮次执行目录（RoundExecutionCatalog）" sources="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/RoundExecutionCatalog/readmodel/RoundExecutionCatalog domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/RoundExecutionCatalog" -->
## 三十六、轮次执行目录（Round Execution Catalog）/FLPRM-DB-SECTION-67

归属： 运行时代理操作（Runtime Agent Operations） / 轮次执行（Round Execution） / 轮次执行目录（Round Execution Catalog）

逻辑形态： 集合/列表 Read Model

订阅事件： 参与者执行计划已分发（Participant Execution Plan Dispatched）, 执行计划已接收（Execution Plan Received）, 执行计划已接受（Execution Plan Accepted）, 执行计划已拒绝（Execution Plan Rejected）, 轮次执行已启动（Round Execution Started）, 轮次执行启动失败（Round Execution Start Failed）, 运行时引擎作业已观察（Runtime Engine Job Observed）, Round Execution Start Retry Started, 轮次执行启动重试失败（Round Execution Start Retry Failed）, 轮次执行已完成（Round Execution Completed）, 轮次执行失败（Round Execution Failed）, Round Execution Runtime Retry Started, 轮次执行运行时重试失败（Round Execution Runtime Retry Failed）, Agent Local Model Update Submitted, Runtime Engine Job Released, Runtime Engine Job Release Failed Or Skipped, Runtime Engine Job Release Failed Or Skipped After Start Failure, Runtime Engine Job Release Failed Or Skipped After Retry, Runtime Engine Job Release Failed Or Skipped After Runtime Retry

### 36.1 逻辑结构/FLPRM-DB-LS-34

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| roundExecutionId | UUID | 单值 | 标识 | - | - |
| executionSessionId | UUID | 可选 | - | - | - |
| executionPlanId | UUID | 可选 | - | - | - |
| trainingJobId | UUID | 单值 | - | - | - |
| trainingRunConfigurationId | UUID | 单值 | - | - | - |
| roundId | UUID | 单值 | - | - | - |
| roundNumber | Int | 可选 | - | - | - |
| organizationId | UUID | 可选 | - | - | - |
| runtimeId | UUID | 单值 | - | - | - |
| state | RoundExecution.State | 单值 | - | - | - |
| featureSchemaId | UUID | 可选 | - | - | - |
| baseModelId | UUID | 可选 | - | - | - |
| runtimeEngineJobId | String | 可选 | - | - | - |
| runtimeEngineObservedStatus | String | 可选 | - | - | - |
| runtimeEngineObservationAt | DateTime | 可选 | - | - | - |
| localUpdateArtifactRef | String | 可选 | - | - | - |
| metricsArtifactRef | String | 可选 | - | - | - |
| localExecutionRequirementsSatisfied | Boolean | 可选 | - | - | - |
| runtimeIdentityMatched | Boolean | 可选 | - | - | - |
| runtimeDatasetBindingAvailable | Boolean | 可选 | - | - | - |
| datasetAccessValidated | Boolean | 可选 | - | - | - |
| baseModelAvailable | Boolean | 可选 | - | - | - |
| trainingConfigurationSupported | Boolean | 可选 | - | - | - |
| runtimeResourceAvailable | Boolean | 可选 | - | - | - |
| runtimeAgentIdle | Boolean | 可选 | - | - | - |
| updateArtifactId | UUID | 可选 | - | - | - |
| artifactRef | String | 可选 | - | - | - |
| artifactDigest | String | 可选 | - | - | - |
| trainingLoss | Decimal | 可选 | - | - | - |
| receivedAt | DateTime | 可选 | - | - | - |
| acceptedAt | DateTime | 可选 | - | - | - |
| rejectedAt | DateTime | 可选 | - | - | - |
| startedAt | DateTime | 可选 | - | - | - |
| completedAt | DateTime | 可选 | - | - | - |
| failedAt | DateTime | 可选 | - | - | - |
| submittedAt | DateTime | 可选 | - | - | - |
| failureReason | String | 可选 | - | - | - |
| retryReason | String | 可选 | - | - | - |
| runtimeEngineReleased | Boolean | 可选 | - | - | - |
| runtimeEngineReleaseFailureReason | String | 可选 | - | - | - |
| rejectionReasons | String | 列表 | - | - | - |

### 36.2 主键与访问路径/FLPRM-DB-SECTION-68

- 逻辑标识：roundExecutionId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 36.3 更新语义/FLPRM-DB-USM-34

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。

<!-- em:section id="database.readmodel.运行时代理生命周期目录（RuntimeAgentLifecycleCatalog）" sources="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/RuntimeAgentLifecycleCatalog/readmodel/RuntimeAgentLifecycleCatalog domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/RuntimeAgentLifecycleCatalog" -->
## 三十七、运行时代理生命周期目录（Runtime Agent Lifecycle Catalog）/FLPRM-DB-SECTION-69

归属： 运行时代理操作（Runtime Agent Operations） / 运行时代理生命周期（Runtime Agent Lifecycle） / 运行时代理生命周期目录（Runtime Agent Lifecycle Catalog）

逻辑形态： 集合/列表 Read Model

订阅事件： Runtime Agent Bootstrap Configuration Loaded, Runtime Agent Bootstrap Configuration Load Failed, Runtime Agent Started, Runtime Instance Self Check Passed

### 37.1 逻辑结构/FLPRM-DB-LS-35

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| runtimeAgentId | UUID | 单值 | 标识 | - | - |
| runtimeInfrastructureId | UUID | 单值 | - | - | - |
| agentVersion | String | 单值 | - | - | - |
| lifecycleStatus | String | 单值 | - | - | - |
| bootstrapConfigurationLoaded | Boolean | 可选 | - | - | - |
| bootstrapFailureReason | String | 可选 | - | - | - |
| runtimeAgentSelfCheckPassed | Boolean | 可选 | - | - | - |
| configurationLoaded | Boolean | 可选 | - | - | - |
| secretStoreAccessible | Boolean | 可选 | - | - | - |
| runtimeEngineAdapterReady | Boolean | 可选 | - | - | - |
| modelRepositoryClientReady | Boolean | 可选 | - | - | - |
| localDatasetBindingStoreReady | Boolean | 可选 | - | - | - |
| workingDirectoryWritable | Boolean | 可选 | - | - | - |
| bootstrappedAt | DateTime | 可选 | - | - | - |
| bootstrapFailedAt | DateTime | 可选 | - | - | - |
| startedAt | DateTime | 可选 | - | - | - |
| readyAt | DateTime | 可选 | - | - | - |

### 37.2 主键与访问路径/FLPRM-DB-SECTION-70

- 逻辑标识：runtimeAgentId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 37.3 更新语义/FLPRM-DB-USM-35

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。

<!-- em:section id="database.readmodel.代理运行时基础设施连接目录（AgentRuntimeInfrastructureConnectionCatalog）" sources="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/AgentRuntimeInfrastructureConnectionCatalog/readmodel/AgentRuntimeInfrastructureConnectionCatalog domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/AgentRuntimeInfrastructureConnectionCatalog" -->
## 三十八、代理运行时基础设施连接目录（Agent Runtime Infrastructure Connection Catalog）/FLPRM-DB-SECTION-71

归属： 运行时代理操作（Runtime Agent Operations） / 代理运行时基础设施连接（Agent Runtime Infrastructure Connection） / 代理运行时基础设施连接目录（Agent Runtime Infrastructure Connection Catalog）

逻辑形态： 集合/列表 Read Model

订阅事件： Agent Runtime Connection Established, Agent Runtime Connection Report Failed

### 38.1 逻辑结构/FLPRM-DB-LS-36

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| runtimeInfrastructureId | UUID | 单值 | 标识 | - | - |
| runtimeAgentId | UUID | 单值 | - | - | - |
| runtimePlatformConnectionReady | Boolean | 单值 | - | - | - |
| platformApiReachable | Boolean | 单值 | - | - | - |
| agentAuthenticationSucceeded | Boolean | 单值 | - | - | - |
| controlChannelEstablished | Boolean | 单值 | - | - | - |
| heartbeatAccepted | Boolean | 单值 | - | - | - |
| connectedAt | DateTime | 单值 | - | - | - |
| connectionReportFailedAt | DateTime | 可选 | - | - | - |
| connectionReportFailureReason | String | 可选 | - | - | - |
| connectionReportRetryable | Boolean | 可选 | - | - | - |

### 38.2 主键与访问路径/FLPRM-DB-SECTION-72

- 逻辑标识：runtimeInfrastructureId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 38.3 更新语义/FLPRM-DB-USM-36

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。

<!-- em:section id="database.readmodel.代理运行时遥测最新（AgentRuntimeTelemetryLatest）" sources="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/AgentRuntimeTelemetryLatest/readmodel/AgentRuntimeTelemetryLatest domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/AgentRuntimeTelemetryLatest" -->
## 三十九、代理运行时遥测最新（Agent Runtime Telemetry Latest）/FLPRM-DB-SECTION-73

归属： 运行时代理操作（Runtime Agent Operations） / 代理运行时遥测（Agent Runtime Telemetry） / 代理运行时遥测最新（Agent Runtime Telemetry Latest）

逻辑形态： 集合/列表 Read Model

订阅事件： 尚未明确订阅事件

### 39.1 逻辑结构/FLPRM-DB-LS-37

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| nodeId | UUID | 单值 | 标识 | - | - |
| runtimeAgentId | UUID | 单值 | - | - | - |
| federationId | UUID | 可选 | - | - | - |
| trainingJobId | UUID | 可选 | - | - | - |
| roundExecutionId | UUID | 可选 | - | - | - |
| cpuLoad | Decimal | 可选 | - | - | - |
| gpuLoad | Decimal | 可选 | - | - | - |
| memoryLoad | Decimal | 可选 | - | - | - |
| lastHeartbeatAt | DateTime | 可选 | - | - | - |
| telemetryRetentionPolicy | String | 单值 | - | - | - |

### 39.2 主键与访问路径/FLPRM-DB-SECTION-74

- 逻辑标识：nodeId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 39.3 更新语义/FLPRM-DB-USM-37

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。

<!-- em:section id="database.readmodel.代理运行时节点清单目录（AgentRuntimeNodeInventoryCatalog）" sources="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/AgentRuntimeNodeInventoryCatalog/readmodel/AgentRuntimeNodeInventoryCatalog domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/AgentRuntimeNodeInventoryCatalog" -->
## 四十、代理运行时节点清单目录（Agent Runtime Node Inventory Catalog）/FLPRM-DB-SECTION-75

归属： 运行时代理操作（Runtime Agent Operations） / 代理运行时节点清单（Agent Runtime Node Inventory） / 代理运行时节点清单目录（Agent Runtime Node Inventory Catalog）

逻辑形态： 集合/列表 Read Model

订阅事件： 组织已注册（Organization Registered）, 代理运行时节点清单已报告（Agent Runtime Node Inventory Reported）

### 40.1 逻辑结构/FLPRM-DB-LS-38

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| runtimeNodeInventoryReportId | UUID | 单值 | 标识 | - | - |
| organizationId | UUID | 单值 | - | - | - |
| runtimeInfrastructureId | UUID | 单值 | - | - | - |
| runtimeAgentId | UUID | 单值 | - | - | - |
| organizationName | String | 可选 | - | - | 派生：OrganizationRegistered.organizationName；规则：Lookup by organizationId and denormalize the organization display name into the agent runtime node inventory catalog. |
| runtimeNodeName | String | 单值 | - | - | - |
| infrastructureNodeId | String | 可选 | - | - | - |
| runtimeNodeRole | String | 单值 | - | - | - |
| nodeReady | Boolean | 单值 | - | - | - |
| runtimeEngineVersion | String | 可选 | - | - | - |
| containerEngineVersion | String | 可选 | - | - | - |
| operatingSystem | String | 可选 | - | - | - |
| architecture | String | 单值 | - | - | - |
| inventoryHash | String | 单值 | - | - | - |
| discoveredAt | DateTime | 单值 | - | - | - |

### 40.2 主键与访问路径/FLPRM-DB-SECTION-76

- 逻辑标识：runtimeNodeInventoryReportId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 40.3 更新语义/FLPRM-DB-USM-38

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。
- organizationName: 派生：OrganizationRegistered.organizationName；规则：Lookup by organizationId and denormalize the organization display name into the agent runtime node inventory catalog..

<!-- em:section id="database.readmodel.代理运行时节点资源最新（AgentRuntimeNodeResourceLatest）" sources="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/AgentRuntimeNodeResourceLatest/readmodel/AgentRuntimeNodeResourceLatest domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/AgentRuntimeNodeResourceLatest" -->
## 四十一、代理运行时节点资源最新（Agent Runtime Node Resource Latest）/FLPRM-DB-SECTION-77

归属： 运行时代理操作（Runtime Agent Operations） / 代理运行时节点资源遥测（Agent Runtime Node Resource Telemetry） / 代理运行时节点资源最新（Agent Runtime Node Resource Latest）

逻辑形态： 集合/列表 Read Model

订阅事件： 尚未明确订阅事件

### 41.1 逻辑结构/FLPRM-DB-LS-39

| 字段 | 类型 | 数量 | 属性 | 示例 | 来源/计算规则 |
| --- | --- | --- | --- | --- | --- |
| nodeId | UUID | 单值 | 标识 | - | - |
| runtimeAgentId | UUID | 单值 | - | - | - |
| runtimeInfrastructureId | UUID | 可选 | - | - | - |
| runtimeNodeName | String | 可选 | - | - | - |
| nodeReady | Boolean | 单值 | - | - | - |
| allocatableCpuCores | Int | 单值 | - | - | - |
| allocatableMemoryGb | Int | 单值 | - | - | - |
| allocatableGpuCount | Int | 单值 | - | - | - |
| allocatedCpuCores | Int | 单值 | - | - | - |
| allocatedMemoryGb | Int | 单值 | - | - | - |
| allocatedGpuCount | Int | 单值 | - | - | - |
| availableCpuCores | Int | 单值 | - | - | - |
| availableMemoryGb | Int | 单值 | - | - | - |
| availableGpuCount | Int | 单值 | - | - | - |
| runningWorkloadCount | Int | 单值 | - | - | - |
| workloadCapacity | Int | 单值 | - | - | - |
| observedAt | DateTime | 单值 | - | - | - |
| telemetryRetentionPolicy | String | 单值 | - | - | - |

### 41.2 主键与访问路径/FLPRM-DB-SECTION-78

- 逻辑标识：nodeId。
- 尚未明确标记查询字段，请根据 API 与页面访问模式推导索引。
- 集合访问需要提供稳定排序与分页能力。

### 41.3 更新语义/FLPRM-DB-USM-39

- 订阅事件处理需要具备幂等性。
- 需要支持重放与恢复时，应记录事件位置或版本。
- 需要确认删除、保留、回填与重建行为。

## 四十二、数据库通用决策/FLPRM-DB-DGD

- 根据 Read Model 访问模式选择存储技术，不应把读模型直接等同于聚合持久化。
- 写模型事务边界与最终一致的读模型更新需要分离设计。
- 需要定义 Read Model 重建、Schema 迁移、可观测性与失败恢复流程。
- 涉及个人身份信息或敏感字段时，需要定义脱敏与保留控制。
