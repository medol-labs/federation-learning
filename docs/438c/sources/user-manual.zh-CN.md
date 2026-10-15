# 联邦学习平台（Federation Learning Platform） 使用手册

<!-- em:section id="document.changeLog" -->
## 变更记录

| 版本 | 日期 | 变更说明 | 作者 |
| --- | --- | --- | --- |
| V0.1.0 | 2026-08-23 | 初始发布 | MEDOL |

## 一、概述/FLP-DOC-OV

本文档说明 联邦学习平台（Federation Learning Platform） 的用户操作方式，包括角色权限、功能入口、输入要求、业务结果、数据视图和异常处理。正式交付前可在 Markdown 中继续补充截图和环境说明。

## 二、角色与权限/FLP-DOC-RAP

| 角色 | 可用能力 | 主要入口 |
| --- | --- | --- |
| 组织管理员（Organization Admin） | 注册组织（Register Organization） | 组织注册屏幕（Organization Registration Screen） |
| 平台管理员（Platform Admin） | 激活组织（Activate Organization）, 停用组织（Deactivate Organization）, 重新激活组织（Reactivate Organization）, 创建联邦（Create Federation）, 激活联邦（Activate Federation）, 暂停联邦（Suspend Federation）, 重新激活联邦（Reactivate Federation）, 注册字典（Register Dictionary）, 更新字典（Update Dictionary）, 归档字典（Archive Dictionary）, 添加字典值（Add Dictionary Value）, 停用字典值（Disable Dictionary Value）, 启用字典值（Enable Dictionary Value）, 注册运行时基础设施包（Register Runtime Infrastructure Package）, 创建运行时安装计划（Create Runtime Installation Plan）, 规划运行时基础设施（Plan Runtime Infrastructure）, 验证运行时基础设施（Verify Runtime Infrastructure）, 部署运行时代理（Deploy Runtime Agent）, 重试运行时代理部署（Retry Runtime Agent Deployment）, 激活运行时标识（Activate Runtime Identity） | 组织管理员屏幕（Organization Admin Screen）, 组织管理员屏幕（Organization Admin Screen）, 组织管理员屏幕（Organization Admin Screen）, 联邦设置屏幕（Federation Setup Screen）, 联邦设置屏幕（Federation Setup Screen）, 联邦设置屏幕（Federation Setup Screen）, 联邦设置屏幕（Federation Setup Screen）, 字典目录屏幕（Dictionary Catalog Screen）, 字典目录屏幕（Dictionary Catalog Screen）, 字典目录屏幕（Dictionary Catalog Screen）, 字典值目录屏幕（Dictionary Value Catalog Screen）, 字典值目录屏幕（Dictionary Value Catalog Screen）, 字典值目录屏幕（Dictionary Value Catalog Screen）, 运行时基础设施包目录界面（Runtime Infrastructure Package Catalog Screen）, 运行时安装计划页面（Runtime Installation Plan Screen）, 运行时安装计划页面（Runtime Installation Plan Screen）, 运行时安装计划页面（Runtime Installation Plan Screen）, 运行时安装计划页面（Runtime Installation Plan Screen）, 运行时安装计划页面（Runtime Installation Plan Screen） |
| 联邦所有者（Federation Owner） | 邀请参与者（Invite Participant）, 撤销参与者邀请（Revoke Participant Invitation） | 参与者邀请屏幕（Participant Invitation Screen）, 参与者邀请屏幕（Participant Invitation Screen） |
| 治理审核员（Governance Reviewer） | 批准参与者（Approve Participant）, 拒绝参与者（Reject Participant）, 暂停参与者（Suspend Participant）, 移除参与者（Remove Participant）, 记录模型评估包（Record Model Evaluation Package）, 批准模型（Approve Model） | 成员资格审核屏幕（Membership Review Screen）, 成员资格审核屏幕（Membership Review Screen）, 成员资格审核屏幕（Membership Review Screen）, 成员资格审核屏幕（Membership Review Screen）, 模型审批屏幕（Model Approval Screen）, 模型审批屏幕（Model Approval Screen） |
| 平台用户（Platform User） | 暂存文件上传（Stage File Upload）, 放弃暂存文件（Discard Staged File） | 文件上传对话框（File Upload Dialog）, 文件上传对话框（File Upload Dialog） |
| 平台服务（Platform Service） | 标记暂存文件已消费（Mark Staged File Consumed） | 系统流程/入口未明确 |
| 运行时安装程序（Runtime Installer） | 运行时安装指南（Runtime Installation Guide）, 注册运行时基础设施（Register Runtime Infrastructure） | 运行时安装指南页面（Runtime Installation Guide Screen） |
| 数据管理员（Data Steward） | 定义特征架构（Define Feature Schema）, 发布特征架构（Publish Feature Schema）, 弃用特征架构（Deprecate Feature Schema）, 停用特征架构（Retire Feature Schema）, 取代特征架构版本（Supersede Feature Schema Version）, 标记当前推荐的特征架构版本（Mark Current Recommended Feature Schema Version） | 特征架构编辑器（Feature Schema Editor）, 特征架构编辑器（Feature Schema Editor）, 特征架构编辑器（Feature Schema Editor）, 特征架构编辑器（Feature Schema Editor）, 特征架构编辑器（Feature Schema Editor）, 特征架构编辑器（Feature Schema Editor） |
| MLOps 工程师（MLOps Engineer） | 注册模型制品（Register Model Artifact）, 定义训练运行配置（Define Training Run Configuration）, 更新训练运行配置（Update Training Run Configuration）, 暂停训练作业（Pause Training Job）, 恢复训练作业（Resume Training Job）, 取消训练作业（Cancel Training Job）, 确认训练告警（Acknowledge Training Alert）, 解决训练告警（Resolve Training Alert） | 模型仓库屏幕（Model Repository Screen）, 训练运行配置屏幕（Training Run Configuration Screen）, 训练运行配置屏幕（Training Run Configuration Screen）, 训练操作屏幕（Training Operations Screen）, 训练操作屏幕（Training Operations Screen）, 训练操作屏幕（Training Operations Screen）, 运行时健康仪表板（Runtime Health Dashboard）, 运行时健康仪表板（Runtime Health Dashboard） |
| 研究主管（Research Lead） | 创建训练作业（Create Training Job）, 提交训练作业（Submit Training Job） | 训练作业创建屏幕（Training Job Creation Screen）, 训练提交屏幕（Training Submission Screen） |
| 训练操作员（Training Operator） | 重试训练轮次参与者选择（Retry Training Round Participant Selection） | 训练作业仪表板（Training Job Dashboard） |
| 运行时代理（Runtime Agent） | 提交模型更新提交（Submit Model Update Submission）, 检测运行时能力（Detect Runtime Capabilities）, 验证代理数据集访问（Validate Agent Dataset Access）, 分析代理数据集（Profile Agent Dataset）, 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration）, 报告运行时代理已启动（Report Runtime Agent Started）, 报告运行时实例自检已通过（Report Runtime Instance Self Check Passed） | 系统流程/入口未明确 |
| 聚合运行时（Aggregation Runtime） | 失败训练轮次（Fail Training Round） | 系统流程/入口未明确 |
| 发布管理员（Release Manager） | 提升模型至生产（Promote Model To Production）, 回滚模型（Rollback Model）, 停用模型（Retire Model） | 模型发布屏幕（Model Release Screen）, 模型发布屏幕（Model Release Screen）, 模型发布屏幕（Model Release Screen） |
| 安全审核员（Security Reviewer） | 撤销运行时标识（Revoke Runtime Identity） | 运行时身份屏幕（Runtime Identity Screen） |
| 安全聚合服务（Secure Aggregation Service） | 准备同态加密上下文（Prepare Homomorphic Encryption Context）, 记录加密模型更新（Record Encrypted Model Update）, 完成同态聚合会话（Complete Homomorphic Aggregation Session）, 失败安全聚合会话（Fail Secure Aggregation Session） | 系统流程/入口未明确 |
| 数据所有者（Data Owner） | 声明数据集（Declare Dataset）, 重试数据集合约验证（Retry Dataset Contract Validation） | 数据集注册屏幕（Dataset Registration Screen）, 数据集注册屏幕（Dataset Registration Screen） |
| 节点操作员（Node Operator） | 配置运行时数据集绑定（Configure Runtime Dataset Binding）, 重新验证代理数据集访问（Revalidate Agent Dataset Access）, 重新分析代理数据集（Reprofile Agent Dataset）, 启动失败后重试轮次执行（Retry Round Execution After Start Failure）, 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure） | 运行时数据集绑定屏幕（Runtime Dataset Binding Screen）, 运行时数据集绑定屏幕（Runtime Dataset Binding Screen）, 运行时数据集绑定屏幕（Runtime Dataset Binding Screen）, Round Execution Screen, Round Execution Screen |
| 合规专员（Compliance Officer） | 拒绝数据集用于训练（Reject Dataset For Training）, 批准数据集用于训练（Approve Dataset For Training）, 撤销数据集训练审批（Revoke Dataset Training Approval） | 数据集审批屏幕（Dataset Approval Screen）, 数据集审批屏幕（Dataset Approval Screen）, 数据集审批屏幕（Dataset Approval Screen） |

## 三、快速开始/FLP-DOC-QS

- 使用具备目标业务上下文权限的账号登录系统。
- 从 63 个已建模页面入口中打开目标功能；如为后台流程，则按系统操作规程触发。
- 提交操作前准备必填标识和业务输入数据。
- 提交后确认业务结果，并查看相关 Read Model 或状态视图。
- 如校验失败，根据规则或异常提示修正输入，必要时重新提交。

## 四、功能操作指南/FLP-DOC-FOG

<!-- em:section id="user-manual.context.组织管理（OrganizationManagement）" source="domain/FederationLearningPlatform/context/OrganizationManagement" -->
### 4.1 组织管理（Organization Management）/FLP-DOC-SECTION

上下文说明: 组织管理负责组织生命周期以及运行时发现的计算节点可用性。

<!-- em:section id="user-manual.slice.注册组织（RegisterOrganization）" source="domain/FederationLearningPlatform/context/OrganizationManagement/slice/RegisterOrganization" -->
#### 4.1.1 注册组织（Register Organization）/FLP-DOC-SECTION-2

| 项目 | 说明 |
| --- | --- |
| 入口 | 组织注册屏幕（Organization Registration Screen） |
| 角色/触发条件 | 组织管理员（Organization Admin） |
| 业务对象 | 组织（Organization） |
| 成功结果 | 组织已注册（Organization Registered）, 状态: 已注册（Registered） |

- 打开组织注册屏幕（Organization Registration Screen）。
- 按有效业务数据提交 注册组织（Register Organization）。
- 确认组织已注册（Organization Registered），并确认状态变为 已注册（Registered）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 注册组织（Register Organization） | organizationId | UUID | 必填标识 | id, generated, technical |
| 注册组织（Register Organization） | organizationName | String | 必填/按业务规则 | - |
| 注册组织（Register Organization） | organizationType | OrganizationType | 必填/按业务规则 | - |
| 注册组织（Register Organization） | contactEmail | String | 必填/按业务规则 | - |

**注意事项**

- 组织名称唯一（Organization Name Unique） / 拒绝重复组织（Reject Duplicate Organization） - 当 注册组织（Register Organization）, 则 拒绝: 组织已存在.

<!-- em:section id="user-manual.slice.激活组织（ActivateOrganization）" source="domain/FederationLearningPlatform/context/OrganizationManagement/slice/ActivateOrganization" -->
#### 4.1.2 激活组织（Activate Organization）/FLP-DOC-SECTION-3

| 项目 | 说明 |
| --- | --- |
| 入口 | 组织管理员屏幕（Organization Admin Screen） |
| 角色/触发条件 | 平台管理员（Platform Admin） |
| 业务对象 | 组织（Organization） |
| 成功结果 | 组织已激活（Organization Activated）, 状态: 已激活（Active） |

- 打开组织管理员屏幕（Organization Admin Screen）。
- 按有效业务数据提交 激活组织（Activate Organization）。
- 确认组织已激活（Organization Activated），并确认状态变为 已激活（Active）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 激活组织（Activate Organization） | organizationId | UUID | 必填标识 | id, technical |
| 激活组织（Activate Organization） | activationNote | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.停用组织（DeactivateOrganization）" source="domain/FederationLearningPlatform/context/OrganizationManagement/slice/DeactivateOrganization" -->
#### 4.1.3 停用组织（Deactivate Organization）/FLP-DOC-SECTION-4

| 项目 | 说明 |
| --- | --- |
| 入口 | 组织管理员屏幕（Organization Admin Screen） |
| 角色/触发条件 | 平台管理员（Platform Admin） |
| 业务对象 | 组织（Organization） |
| 成功结果 | 组织已停用（Organization Deactivated）, 状态: 已停用（Deactivated） |

- 打开组织管理员屏幕（Organization Admin Screen）。
- 按有效业务数据提交 停用组织（Deactivate Organization）。
- 确认组织已停用（Organization Deactivated），并确认状态变为 已停用（Deactivated）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 停用组织（Deactivate Organization） | organizationId | UUID | 必填标识 | id, technical |
| 停用组织（Deactivate Organization） | deactivationReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.重新激活组织（ReactivateOrganization）" source="domain/FederationLearningPlatform/context/OrganizationManagement/slice/ReactivateOrganization" -->
#### 4.1.4 重新激活组织（Reactivate Organization）/FLP-DOC-SECTION-5

| 项目 | 说明 |
| --- | --- |
| 入口 | 组织管理员屏幕（Organization Admin Screen） |
| 角色/触发条件 | 平台管理员（Platform Admin） |
| 业务对象 | 组织（Organization） |
| 成功结果 | 组织已重新激活（Organization Reactivated）, 状态: 已激活（Active） |

- 打开组织管理员屏幕（Organization Admin Screen）。
- 按有效业务数据提交 重新激活组织（Reactivate Organization）。
- 确认组织已重新激活（Organization Reactivated），并确认状态变为 已激活（Active）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 重新激活组织（Reactivate Organization） | organizationId | UUID | 必填标识 | id, technical |
| 重新激活组织（Reactivate Organization） | reactivationReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.组织目录（OrganizationDirectory）" source="domain/FederationLearningPlatform/context/OrganizationManagement/slice/OrganizationDirectory" -->
#### 4.1.5 组织目录（Organization Directory）/FLP-DOC-SECTION-6

| 项目 | 说明 |
| --- | --- |
| 入口 | 组织目录（Organization Directory） |
| 角色/触发条件 | 系统 |
| 业务对象 | 组织（Organization） |
| 成功结果 | 尚未明确 |

- 打开组织目录（Organization Directory）。
- 按有效业务数据提交 组织目录（Organization Directory）。
- 确认预期业务结果。
- 查看 组织目录（Organization Directory） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.context.联邦管理（FederationManagement）" source="domain/FederationLearningPlatform/context/FederationManagement" -->
### 4.2 联邦管理（Federation Management）/FLP-DOC-SECTION-7

上下文说明: 联邦管理负责联邦及联邦成员资格治理。

<!-- em:section id="user-manual.slice.创建联邦（CreateFederation）" source="domain/FederationLearningPlatform/context/FederationManagement/slice/CreateFederation" -->
#### 4.2.1 创建联邦（Create Federation）/FLP-DOC-SECTION-8

| 项目 | 说明 |
| --- | --- |
| 入口 | 联邦设置屏幕（Federation Setup Screen） (form) |
| 角色/触发条件 | 平台管理员（Platform Admin） |
| 业务对象 | 联邦（Federation） |
| 成功结果 | 联邦已创建（Federation Created）, 状态: 草稿（Draft） |

- 打开联邦设置屏幕（Federation Setup Screen）。
- 按有效业务数据提交 创建联邦（Create Federation）。
- 确认联邦已创建（Federation Created），并确认状态变为 草稿（Draft）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 创建联邦（Create Federation） | federationId | UUID | 必填标识 | id, generated, technical |
| 创建联邦（Create Federation） | federationName | String | 必填/按业务规则 | - |
| 创建联邦（Create Federation） | description | String | 必填/按业务规则 | - |
| 创建联邦（Create Federation） | minimumParticipantCount | Int | 必填/按业务规则 | - |

**注意事项**

- 联邦名称唯一（Federation Name Unique） / 拒绝重复联邦（Reject Duplicate Federation） - 当 创建联邦（Create Federation）, 则 拒绝: 联邦已存在.

<!-- em:section id="user-manual.slice.激活联邦（ActivateFederation）" source="domain/FederationLearningPlatform/context/FederationManagement/slice/ActivateFederation" -->
#### 4.2.2 激活联邦（Activate Federation）/FLP-DOC-SECTION-9

| 项目 | 说明 |
| --- | --- |
| 入口 | 联邦设置屏幕（Federation Setup Screen） |
| 角色/触发条件 | 平台管理员（Platform Admin） |
| 业务对象 | 联邦（Federation） |
| 成功结果 | 联邦已激活（Federation Activated）, 状态: 已激活（Active） |

- 打开联邦设置屏幕（Federation Setup Screen）。
- 按有效业务数据提交 激活联邦（Activate Federation）。
- 确认联邦已激活（Federation Activated），并确认状态变为 已激活（Active）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 激活联邦（Activate Federation） | federationId | UUID | 必填标识 | id, technical |
| 激活联邦（Activate Federation） | activationNote | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.暂停联邦（SuspendFederation）" source="domain/FederationLearningPlatform/context/FederationManagement/slice/SuspendFederation" -->
#### 4.2.3 暂停联邦（Suspend Federation）/FLP-DOC-SECTION-10

| 项目 | 说明 |
| --- | --- |
| 入口 | 联邦设置屏幕（Federation Setup Screen） (confirm) |
| 角色/触发条件 | 平台管理员（Platform Admin） |
| 业务对象 | 联邦（Federation） |
| 成功结果 | 联邦已暂停（Federation Suspended）, 状态: 已暂停（Suspended） |

- 打开联邦设置屏幕（Federation Setup Screen）。
- 按有效业务数据提交 暂停联邦（Suspend Federation）。
- 确认联邦已暂停（Federation Suspended），并确认状态变为 已暂停（Suspended）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 暂停联邦（Suspend Federation） | federationId | UUID | 必填标识 | id, technical |
| 暂停联邦（Suspend Federation） | suspensionReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.重新激活联邦（ReactivateFederation）" source="domain/FederationLearningPlatform/context/FederationManagement/slice/ReactivateFederation" -->
#### 4.2.4 重新激活联邦（Reactivate Federation）/FLP-DOC-SECTION-11

| 项目 | 说明 |
| --- | --- |
| 入口 | 联邦设置屏幕（Federation Setup Screen） |
| 角色/触发条件 | 平台管理员（Platform Admin） |
| 业务对象 | 联邦（Federation） |
| 成功结果 | 联邦已重新激活（Federation Reactivated）, 状态: 已激活（Active） |

- 打开联邦设置屏幕（Federation Setup Screen）。
- 按有效业务数据提交 重新激活联邦（Reactivate Federation）。
- 确认联邦已重新激活（Federation Reactivated），并确认状态变为 已激活（Active）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 重新激活联邦（Reactivate Federation） | federationId | UUID | 必填标识 | id, technical |
| 重新激活联邦（Reactivate Federation） | reactivationReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.联邦概览（FederationOverview）" source="domain/FederationLearningPlatform/context/FederationManagement/slice/FederationOverview" -->
#### 4.2.5 联邦概览（Federation Overview）/FLP-DOC-SECTION-12

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 联邦（Federation） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 联邦概览（Federation Overview）。
- 确认预期业务结果。
- 查看 联邦概览（Federation Overview） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.邀请参与者（InviteParticipant）" source="domain/FederationLearningPlatform/context/FederationManagement/slice/InviteParticipant" -->
#### 4.2.6 邀请参与者（Invite Participant）/FLP-DOC-SECTION-13

| 项目 | 说明 |
| --- | --- |
| 入口 | 参与者邀请屏幕（Participant Invitation Screen） (dialog) |
| 角色/触发条件 | 联邦所有者（Federation Owner） |
| 业务对象 | 联邦成员资格（Federation Membership） |
| 成功结果 | 参与者已邀请（Participant Invited）, 状态: 已邀请（Invited） |

- 打开参与者邀请屏幕（Participant Invitation Screen）。
- 按有效业务数据提交 邀请参与者（Invite Participant）。
- 确认参与者已邀请（Participant Invited），并确认状态变为 已邀请（Invited）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 邀请参与者（Invite Participant） | federationId | UUID | 必填标识 | id, technical |
| 邀请参与者（Invite Participant） | organizationId | UUID | 必填/按业务规则 | - |
| 邀请参与者（Invite Participant） | invitationNote | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.联邦成员资格目录（FederationMembershipDirectory）" source="domain/FederationLearningPlatform/context/FederationManagement/slice/FederationMembershipDirectory" -->
#### 4.2.7 联邦成员资格目录（Federation Membership Directory）/FLP-DOC-SECTION-14

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 联邦成员资格（Federation Membership） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 联邦成员资格目录（Federation Membership Directory）。
- 确认预期业务结果。
- 查看 联邦成员资格目录（Federation Membership Directory） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.批准参与者（ApproveParticipant）" source="domain/FederationLearningPlatform/context/FederationManagement/slice/ApproveParticipant" -->
#### 4.2.8 批准参与者（Approve Participant）/FLP-DOC-SECTION-15

| 项目 | 说明 |
| --- | --- |
| 入口 | 成员资格审核屏幕（Membership Review Screen） (dialog) |
| 角色/触发条件 | 治理审核员（Governance Reviewer） |
| 业务对象 | 联邦成员资格（Federation Membership） |
| 成功结果 | 参与者已加入（Participant Joined）, 状态: 已激活（Active） |

- 打开成员资格审核屏幕（Membership Review Screen）。
- 按有效业务数据提交 批准参与者（Approve Participant）。
- 确认参与者已加入（Participant Joined），并确认状态变为 已激活（Active）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 批准参与者（Approve Participant） | federationId | UUID | 必填标识 | id, technical |
| 批准参与者（Approve Participant） | organizationId | UUID | 必填/按业务规则 | - |
| 批准参与者（Approve Participant） | approvalNote | String | 必填/按业务规则 | - |

**注意事项**

- 仅已邀请的激活组织加入联邦（Only Invited Active Organizations Join Federation） / 批准已邀请的参与者（Approve Invited Participant） - 当 批准参与者（Approve Participant）, 则 参与者已加入（Participant Joined）.
- 仅已邀请的激活组织加入联邦（Only Invited Active Organizations Join Federation） / 拒绝未邀请参与者的审批（Reject Uninvited Participant Approval） - 当 批准参与者（Approve Participant）, 则 拒绝: 需要参与者邀请.

<!-- em:section id="user-manual.slice.拒绝参与者（RejectParticipant）" source="domain/FederationLearningPlatform/context/FederationManagement/slice/RejectParticipant" -->
#### 4.2.9 拒绝参与者（Reject Participant）/FLP-DOC-SECTION-16

| 项目 | 说明 |
| --- | --- |
| 入口 | 成员资格审核屏幕（Membership Review Screen） (dialog) |
| 角色/触发条件 | 治理审核员（Governance Reviewer） |
| 业务对象 | 联邦成员资格（Federation Membership） |
| 成功结果 | 参与者已拒绝（Participant Rejected）, 状态: 已拒绝（Rejected） |

- 打开成员资格审核屏幕（Membership Review Screen）。
- 按有效业务数据提交 拒绝参与者（Reject Participant）。
- 确认参与者已拒绝（Participant Rejected），并确认状态变为 已拒绝（Rejected）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 拒绝参与者（Reject Participant） | federationId | UUID | 必填标识 | id, technical |
| 拒绝参与者（Reject Participant） | organizationId | UUID | 必填/按业务规则 | - |
| 拒绝参与者（Reject Participant） | rejectionReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.撤销参与者邀请（RevokeParticipantInvitation）" source="domain/FederationLearningPlatform/context/FederationManagement/slice/RevokeParticipantInvitation" -->
#### 4.2.10 撤销参与者邀请（Revoke Participant Invitation）/FLP-DOC-SECTION-17

| 项目 | 说明 |
| --- | --- |
| 入口 | 参与者邀请屏幕（Participant Invitation Screen） (confirm) |
| 角色/触发条件 | 联邦所有者（Federation Owner） |
| 业务对象 | 联邦成员资格（Federation Membership） |
| 成功结果 | 参与者邀请已撤销（Participant Invitation Revoked）, 状态: 邀请已撤销（Invitation Revoked） |

- 打开参与者邀请屏幕（Participant Invitation Screen）。
- 按有效业务数据提交 撤销参与者邀请（Revoke Participant Invitation）。
- 确认参与者邀请已撤销（Participant Invitation Revoked），并确认状态变为 邀请已撤销（Invitation Revoked）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 撤销参与者邀请（Revoke Participant Invitation） | federationId | UUID | 必填标识 | id, technical |
| 撤销参与者邀请（Revoke Participant Invitation） | organizationId | UUID | 必填/按业务规则 | - |
| 撤销参与者邀请（Revoke Participant Invitation） | revokeReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.暂停参与者（SuspendParticipant）" source="domain/FederationLearningPlatform/context/FederationManagement/slice/SuspendParticipant" -->
#### 4.2.11 暂停参与者（Suspend Participant）/FLP-DOC-SECTION-18

| 项目 | 说明 |
| --- | --- |
| 入口 | 成员资格审核屏幕（Membership Review Screen） (dialog) |
| 角色/触发条件 | 治理审核员（Governance Reviewer） |
| 业务对象 | 联邦成员资格（Federation Membership） |
| 成功结果 | 参与者已暂停（Participant Suspended）, 状态: 已暂停（Suspended） |

- 打开成员资格审核屏幕（Membership Review Screen）。
- 按有效业务数据提交 暂停参与者（Suspend Participant）。
- 确认参与者已暂停（Participant Suspended），并确认状态变为 已暂停（Suspended）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 暂停参与者（Suspend Participant） | federationId | UUID | 必填标识 | id, technical |
| 暂停参与者（Suspend Participant） | organizationId | UUID | 必填/按业务规则 | - |
| 暂停参与者（Suspend Participant） | suspensionReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.移除参与者（RemoveParticipant）" source="domain/FederationLearningPlatform/context/FederationManagement/slice/RemoveParticipant" -->
#### 4.2.12 移除参与者（Remove Participant）/FLP-DOC-SECTION-19

| 项目 | 说明 |
| --- | --- |
| 入口 | 成员资格审核屏幕（Membership Review Screen） (confirm) |
| 角色/触发条件 | 治理审核员（Governance Reviewer） |
| 业务对象 | 联邦成员资格（Federation Membership） |
| 成功结果 | 参与者已移除（Participant Removed）, 状态: 已移除（Removed） |

- 打开成员资格审核屏幕（Membership Review Screen）。
- 按有效业务数据提交 移除参与者（Remove Participant）。
- 确认参与者已移除（Participant Removed），并确认状态变为 已移除（Removed）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 移除参与者（Remove Participant） | federationId | UUID | 必填标识 | id, technical |
| 移除参与者（Remove Participant） | organizationId | UUID | 必填/按业务规则 | - |
| 移除参与者（Remove Participant） | removalReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.context.字典维护（DictionaryMaintenance）" source="domain/FederationLearningPlatform/context/DictionaryMaintenance" -->
### 4.3 字典维护（Dictionary Maintenance）/FLP-DOC-SECTION-20

上下文说明: 字典维护负责管理平台上下文使用的动态选项字典。业务上下文存储 valueCode 并通过 dictionaryCode 查询 DictionaryValueCatalog 以进行显示和选择。

<!-- em:section id="user-manual.slice.注册字典（RegisterDictionary）" source="domain/FederationLearningPlatform/context/DictionaryMaintenance/slice/RegisterDictionary" -->
#### 4.3.1 注册字典（Register Dictionary）/FLP-DOC-SECTION-21

| 项目 | 说明 |
| --- | --- |
| 入口 | 字典目录屏幕（Dictionary Catalog Screen） |
| 角色/触发条件 | 平台管理员（Platform Admin） |
| 业务对象 | 字典（Dictionary） |
| 成功结果 | 字典已注册（Dictionary Registered）, 状态: 已注册（Registered） |

- 打开字典目录屏幕（Dictionary Catalog Screen）。
- 按有效业务数据提交 注册字典（Register Dictionary）。
- 确认字典已注册（Dictionary Registered），并确认状态变为 已注册（Registered）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 注册字典（Register Dictionary） | dictionaryId | UUID | 必填标识 | id, generated, technical |
| 注册字典（Register Dictionary） | dictionaryCode | DictionaryCode | 必填/按业务规则 | - |
| 注册字典（Register Dictionary） | dictionaryName | String | 必填/按业务规则 | - |
| 注册字典（Register Dictionary） | description | String | 必填/按业务规则 | - |

**注意事项**

- 字典代码唯一（Dictionary Code Unique） / 拒绝重复字典（Reject Duplicate Dictionary） - 当 注册字典（Register Dictionary）, 则 拒绝: 字典已存在.

<!-- em:section id="user-manual.slice.更新字典（UpdateDictionary）" source="domain/FederationLearningPlatform/context/DictionaryMaintenance/slice/UpdateDictionary" -->
#### 4.3.2 更新字典（Update Dictionary）/FLP-DOC-SECTION-22

| 项目 | 说明 |
| --- | --- |
| 入口 | 字典目录屏幕（Dictionary Catalog Screen） |
| 角色/触发条件 | 平台管理员（Platform Admin） |
| 业务对象 | 字典（Dictionary） |
| 成功结果 | 字典已更新（Dictionary Updated） |

- 打开字典目录屏幕（Dictionary Catalog Screen）。
- 按有效业务数据提交 更新字典（Update Dictionary）。
- 确认字典已更新（Dictionary Updated）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 更新字典（Update Dictionary） | dictionaryId | UUID | 必填标识 | id, technical |
| 更新字典（Update Dictionary） | dictionaryName | String | 必填/按业务规则 | - |
| 更新字典（Update Dictionary） | description | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.归档字典（ArchiveDictionary）" source="domain/FederationLearningPlatform/context/DictionaryMaintenance/slice/ArchiveDictionary" -->
#### 4.3.3 归档字典（Archive Dictionary）/FLP-DOC-SECTION-23

| 项目 | 说明 |
| --- | --- |
| 入口 | 字典目录屏幕（Dictionary Catalog Screen） (confirm) |
| 角色/触发条件 | 平台管理员（Platform Admin） |
| 业务对象 | 字典（Dictionary） |
| 成功结果 | 字典已归档（Dictionary Archived）, 状态: 已归档（Archived） |

- 打开字典目录屏幕（Dictionary Catalog Screen）。
- 按有效业务数据提交 归档字典（Archive Dictionary）。
- 确认字典已归档（Dictionary Archived），并确认状态变为 已归档（Archived）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 归档字典（Archive Dictionary） | dictionaryId | UUID | 必填标识 | id, technical |
| 归档字典（Archive Dictionary） | archiveReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.字典目录（DictionaryCatalog）" source="domain/FederationLearningPlatform/context/DictionaryMaintenance/slice/DictionaryCatalog" -->
#### 4.3.4 字典目录（Dictionary Catalog）/FLP-DOC-SECTION-24

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 字典（Dictionary） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 字典目录（Dictionary Catalog）。
- 确认预期业务结果。
- 查看 字典目录（Dictionary Catalog） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.添加字典值（AddDictionaryValue）" source="domain/FederationLearningPlatform/context/DictionaryMaintenance/slice/AddDictionaryValue" -->
#### 4.3.5 添加字典值（Add Dictionary Value）/FLP-DOC-SECTION-25

| 项目 | 说明 |
| --- | --- |
| 入口 | 字典值目录屏幕（Dictionary Value Catalog Screen） |
| 角色/触发条件 | 平台管理员（Platform Admin） |
| 业务对象 | 字典值（Dictionary Value） |
| 成功结果 | 字典值已添加（Dictionary Value Added）, 状态: 已激活（Active） |

- 打开字典值目录屏幕（Dictionary Value Catalog Screen）。
- 按有效业务数据提交 添加字典值（Add Dictionary Value）。
- 确认字典值已添加（Dictionary Value Added），并确认状态变为 已激活（Active）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 添加字典值（Add Dictionary Value） | dictionaryValueId | UUID | 必填标识 | id, generated, technical |
| 添加字典值（Add Dictionary Value） | dictionaryId | UUID | 必填/按业务规则 | - |
| 添加字典值（Add Dictionary Value） | dictionaryCode | DictionaryCode | 必填/按业务规则 | - |
| 添加字典值（Add Dictionary Value） | valueCode | DictionaryValueCode | 必填/按业务规则 | - |
| 添加字典值（Add Dictionary Value） | displayName | String | 必填/按业务规则 | - |
| 添加字典值（Add Dictionary Value） | displayOrder | DisplayOrder | 必填/按业务规则 | - |
| 添加字典值（Add Dictionary Value） | description | String | 必填/按业务规则 | - |
| 添加字典值（Add Dictionary Value） | active | Boolean | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.停用字典值（DisableDictionaryValue）" source="domain/FederationLearningPlatform/context/DictionaryMaintenance/slice/DisableDictionaryValue" -->
#### 4.3.6 停用字典值（Disable Dictionary Value）/FLP-DOC-SECTION-26

| 项目 | 说明 |
| --- | --- |
| 入口 | 字典值目录屏幕（Dictionary Value Catalog Screen） (confirm) |
| 角色/触发条件 | 平台管理员（Platform Admin） |
| 业务对象 | 字典值（Dictionary Value） |
| 成功结果 | 字典值已停用（Dictionary Value Disabled）, 状态: 已停用（Disabled） |

- 打开字典值目录屏幕（Dictionary Value Catalog Screen）。
- 按有效业务数据提交 停用字典值（Disable Dictionary Value）。
- 确认字典值已停用（Dictionary Value Disabled），并确认状态变为 已停用（Disabled）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 停用字典值（Disable Dictionary Value） | dictionaryValueId | UUID | 必填标识 | id, technical |
| 停用字典值（Disable Dictionary Value） | disabledReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.启用字典值（EnableDictionaryValue）" source="domain/FederationLearningPlatform/context/DictionaryMaintenance/slice/EnableDictionaryValue" -->
#### 4.3.7 启用字典值（Enable Dictionary Value）/FLP-DOC-SECTION-27

| 项目 | 说明 |
| --- | --- |
| 入口 | 字典值目录屏幕（Dictionary Value Catalog Screen） |
| 角色/触发条件 | 平台管理员（Platform Admin） |
| 业务对象 | 字典值（Dictionary Value） |
| 成功结果 | 字典值已启用（Dictionary Value Enabled）, 状态: 已激活（Active） |

- 打开字典值目录屏幕（Dictionary Value Catalog Screen）。
- 按有效业务数据提交 启用字典值（Enable Dictionary Value）。
- 确认字典值已启用（Dictionary Value Enabled），并确认状态变为 已激活（Active）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 启用字典值（Enable Dictionary Value） | dictionaryValueId | UUID | 必填标识 | id, technical |
| 启用字典值（Enable Dictionary Value） | enableReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.字典值目录（DictionaryValueCatalog）" source="domain/FederationLearningPlatform/context/DictionaryMaintenance/slice/DictionaryValueCatalog" -->
#### 4.3.8 字典值目录（Dictionary Value Catalog）/FLP-DOC-SECTION-28

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 字典值（Dictionary Value） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 字典值目录（Dictionary Value Catalog）。
- 确认预期业务结果。
- 查看 字典值目录（Dictionary Value Catalog） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.context.文件上传（FileUpload）" source="domain/FederationLearningPlatform/context/FileUpload" -->
### 4.4 文件上传（File Upload）/FLP-DOC-SECTION-29

上下文说明: 文件上传拥有由生成的表单和后端适配器使用的短期暂存文件引用。业务命令存储此上下文返回的 stagedFileId，而不是浏览器本地的文件路径或存储位置。

<!-- em:section id="user-manual.slice.暂存文件上传（StageFileUpload）" source="domain/FederationLearningPlatform/context/FileUpload/slice/StageFileUpload" -->
#### 4.4.1 暂存文件上传（Stage File Upload）/FLP-DOC-SECTION-30

| 项目 | 说明 |
| --- | --- |
| 入口 | 文件上传对话框（File Upload Dialog） |
| 角色/触发条件 | 平台用户（Platform User） |
| 业务对象 | 暂存文件（Staged File） |
| 成功结果 | 文件上传已暂存（File Upload Staged）, 状态: 已暂存（Staged） |

- 打开文件上传对话框（File Upload Dialog）。
- 按有效业务数据提交 暂存文件上传（Stage File Upload）。
- 确认文件上传已暂存（File Upload Staged），并确认状态变为 已暂存（Staged）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 暂存文件上传（Stage File Upload） | stagedFileId | UUID | 必填标识 | id, generated, technical |
| 暂存文件上传（Stage File Upload） | uploadedFile | String | 必填/按业务规则 | uploadFile |
| 暂存文件上传（Stage File Upload） | originalFileName | String | 必填/按业务规则 | technical |
| 暂存文件上传（Stage File Upload） | contentType | String | 必填/按业务规则 | technical |
| 暂存文件上传（Stage File Upload） | sizeBytes | Long | 必填/按业务规则 | technical |
| 暂存文件上传（Stage File Upload） | stagedFileLocation | String | 必填/按业务规则 | technical |
| 暂存文件上传（Stage File Upload） | checksum | String | 必填/按业务规则 | technical |
| 暂存文件上传（Stage File Upload） | expiresAt | DateTime | 必填/按业务规则 | technical |
| 暂存文件上传（Stage File Upload） | purpose | String | 必填/按业务规则 | - |

**注意事项**

- 在提交业务命令前暂存已上传文件（Uploaded File Is Staged Before Business Command Submission） / 为模型导入暂存上传（Stage Upload For Model Import） - 当 暂存文件上传（Stage File Upload）, 则 文件上传已暂存（File Upload Staged）.

<!-- em:section id="user-manual.slice.标记暂存文件已消费（MarkStagedFileConsumed）" source="domain/FederationLearningPlatform/context/FileUpload/slice/MarkStagedFileConsumed" -->
#### 4.4.2 标记暂存文件已消费（Mark Staged File Consumed）/FLP-DOC-SECTION-31

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 平台服务（Platform Service） |
| 业务对象 | 暂存文件（Staged File） |
| 成功结果 | 暂存文件已消费（Staged File Consumed）, 状态: 已消费（Consumed） |

- 打开已配置的系统入口。
- 按有效业务数据提交 标记暂存文件已消费（Mark Staged File Consumed）。
- 确认暂存文件已消费（Staged File Consumed），并确认状态变为 已消费（Consumed）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 标记暂存文件已消费（Mark Staged File Consumed） | stagedFileId | UUID | 必填标识 | id, technical |
| 标记暂存文件已消费（Mark Staged File Consumed） | consumedByContext | String | 必填/按业务规则 | - |
| 标记暂存文件已消费（Mark Staged File Consumed） | consumedByCommand | String | 必填/按业务规则 | - |
| 标记暂存文件已消费（Mark Staged File Consumed） | consumedByCommandId | UUID | 必填/按业务规则 | - |

**注意事项**

- 已消费的暂存文件不再可由用户丢弃（Consumed Staged File Is No Longer User Discardable） / 通过模型导入标记文件为已消费（Mark File Consumed By Model Import） - 当 标记暂存文件已消费（Mark Staged File Consumed）, 则 暂存文件已消费（Staged File Consumed）.

<!-- em:section id="user-manual.slice.放弃暂存文件（DiscardStagedFile）" source="domain/FederationLearningPlatform/context/FileUpload/slice/DiscardStagedFile" -->
#### 4.4.3 放弃暂存文件（Discard Staged File）/FLP-DOC-SECTION-32

| 项目 | 说明 |
| --- | --- |
| 入口 | 文件上传对话框（File Upload Dialog） |
| 角色/触发条件 | 平台用户（Platform User） |
| 业务对象 | 暂存文件（Staged File） |
| 成功结果 | 暂存文件已丢弃（Staged File Discarded）, 状态: 已放弃（Discarded） |

- 打开文件上传对话框（File Upload Dialog）。
- 按有效业务数据提交 放弃暂存文件（Discard Staged File）。
- 确认暂存文件已丢弃（Staged File Discarded），并确认状态变为 已放弃（Discarded）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 放弃暂存文件（Discard Staged File） | stagedFileId | UUID | 必填标识 | id, technical |
| 放弃暂存文件（Discard Staged File） | discardReason | String | 必填/按业务规则 | - |

**注意事项**

- 仅可丢弃未消费的暂存文件（Only Unconsumed Staged Files Can Be Discarded） / 丢弃未消费的暂存文件（Discard Unconsumed Staged File） - 当 放弃暂存文件（Discard Staged File）, 则 暂存文件已丢弃（Staged File Discarded）.
- 仅可丢弃未消费的暂存文件（Only Unconsumed Staged Files Can Be Discarded） / 拒绝消费后丢弃（Reject Discard After Consumption） - 当 放弃暂存文件（Discard Staged File）, 则 拒绝: 暂存文件已被消费.

<!-- em:section id="user-manual.slice.使暂存文件过期（ExpireStagedFile）" source="domain/FederationLearningPlatform/context/FileUpload/slice/ExpireStagedFile" -->
#### 4.4.4 使暂存文件过期（Expire Staged File）/FLP-DOC-SECTION-33

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 保留期结束后暂存文件过期（Expire Staged File When Retention Elapsed） |
| 业务对象 | 暂存文件（Staged File） |
| 成功结果 | 暂存文件已过期（Staged File Expired）, 状态: 已过期（Expired） |

- 打开已配置的系统入口。
- 按有效业务数据提交 使暂存文件过期（Expire Staged File）。
- 确认暂存文件已过期（Staged File Expired），并确认状态变为 已过期（Expired）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 使暂存文件过期（Expire Staged File） | stagedFileId | UUID | 必填标识 | id, technical |
| 使暂存文件过期（Expire Staged File） | expiredAt | DateTime | 必填/按业务规则 | - |
| 使暂存文件过期（Expire Staged File） | expirationReason | String | 必填/按业务规则 | - |

**注意事项**

- 已过期的暂存文件无法被消费（Expired Staged File Cannot Be Consumed） / 未消费暂存文件过期（Expire Unconsumed Staged File） - 当 使暂存文件过期（Expire Staged File）, 则 暂存文件已过期（Staged File Expired）.
- 已过期的暂存文件无法被消费（Expired Staged File Cannot Be Consumed） / 过期后拒绝消费（Reject Consumption After Expiration） - 当 标记暂存文件已消费（Mark Staged File Consumed）, 则 拒绝: 暂存文件已过期.

<!-- em:section id="user-manual.slice.暂存文件目录（StagedFileCatalog）" source="domain/FederationLearningPlatform/context/FileUpload/slice/StagedFileCatalog" -->
#### 4.4.5 暂存文件目录（Staged File Catalog）/FLP-DOC-SECTION-34

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 暂存文件（Staged File） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 暂存文件目录（Staged File Catalog）。
- 确认预期业务结果。
- 查看 暂存文件目录（Staged File Catalog） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.context.运行时预配（RuntimeProvisioning）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning" -->
### 4.5 运行时预配（Runtime Provisioning）/FLP-DOC-SECTION-35

上下文说明: 运行时入驻负责运行时安装规划、引导配置、运行时环境注册、代理安装、就绪检查、连接报告及访问撤销。K3s、Kubernetes、Docker Compose 与主机进程运行时均为安装目标，而非独立的领域概念。 运行时引导和代理安装材料是支持配置，而非领域状态：平台后端适配器从部署配置或密钥存储中读取 platform.runtime.bootstrap.publicBaseAddress、platform.runtime.agent.platformApiBaseAddress、platform.runtime.agent.controlApiBaseAddress、platform.runtime.agent.containerRegistry、platform.runtime.agent.trustBundleSecretName 和 platform.runtime.agent.accessGrantSecretPrefix。命令、事件和读模型仅保留 runtimeInstallationPlanId、runtimeInfrastructurePackageId 以及生成的访问授权 ID。

<!-- em:section id="user-manual.slice.注册运行时基础设施包（RegisterRuntimeInfrastructurePackage）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RegisterRuntimeInfrastructurePackage" -->
#### 4.5.1 注册运行时基础设施包（Register Runtime Infrastructure Package）/FLP-DOC-SECTION-36

| 项目 | 说明 |
| --- | --- |
| 入口 | 运行时基础设施包目录界面（Runtime Infrastructure Package Catalog Screen） |
| 角色/触发条件 | 平台管理员（Platform Admin） |
| 业务对象 | 运行时基础设施包（Runtime Infrastructure Package） |
| 成功结果 | 运行时基础设施包已注册（Runtime Infrastructure Package Registered）, 状态: 已注册（Registered） |

- 打开运行时基础设施包目录界面（Runtime Infrastructure Package Catalog Screen）。
- 按有效业务数据提交 注册运行时基础设施包（Register Runtime Infrastructure Package）。
- 确认运行时基础设施包已注册（Runtime Infrastructure Package Registered），并确认状态变为 已注册（Registered）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 注册运行时基础设施包（Register Runtime Infrastructure Package） | runtimeInfrastructurePackageId | UUID | 必填标识 | id, generated, technical |
| 注册运行时基础设施包（Register Runtime Infrastructure Package） | packageName | String | 必填/按业务规则 | - |
| 注册运行时基础设施包（Register Runtime Infrastructure Package） | packageVersion | String | 必填/按业务规则 | - |
| 注册运行时基础设施包（Register Runtime Infrastructure Package） | runtimeEnvironmentType | String | 必填/按业务规则 | - |
| 注册运行时基础设施包（Register Runtime Infrastructure Package） | runtimeDeploymentTargetType | String | 必填/按业务规则 | - |
| 注册运行时基础设施包（Register Runtime Infrastructure Package） | installProfile | String | 必填/按业务规则 | - |
| 注册运行时基础设施包（Register Runtime Infrastructure Package） | architecture | String | 必填/按业务规则 | - |
| 注册运行时基础设施包（Register Runtime Infrastructure Package） | installGuide | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.运行时基础设施包目录（RuntimeInfrastructurePackageCatalog）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RuntimeInfrastructurePackageCatalog" -->
#### 4.5.2 运行时基础设施包目录（Runtime Infrastructure Package Catalog）/FLP-DOC-SECTION-37

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 运行时基础设施包（Runtime Infrastructure Package） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 运行时基础设施包目录（Runtime Infrastructure Package Catalog）。
- 确认预期业务结果。
- 查看 运行时基础设施包目录（Runtime Infrastructure Package Catalog） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.创建运行时安装计划（CreateRuntimeInstallationPlan）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/CreateRuntimeInstallationPlan" -->
#### 4.5.3 创建运行时安装计划（Create Runtime Installation Plan）/FLP-DOC-SECTION-38

| 项目 | 说明 |
| --- | --- |
| 入口 | 运行时安装计划页面（Runtime Installation Plan Screen） |
| 角色/触发条件 | 平台管理员（Platform Admin） |
| 业务对象 | 运行时安装计划（Runtime Installation Plan） |
| 成功结果 | 运行时安装计划已创建（Runtime Installation Plan Created）, 状态: 已规划（Planned） |

- 打开运行时安装计划页面（Runtime Installation Plan Screen）。
- 按有效业务数据提交 创建运行时安装计划（Create Runtime Installation Plan）。
- 确认运行时安装计划已创建（Runtime Installation Plan Created），并确认状态变为 已规划（Planned）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 创建运行时安装计划（Create Runtime Installation Plan） | runtimeInstallationPlanId | UUID | 必填标识 | id, generated, technical |
| 创建运行时安装计划（Create Runtime Installation Plan） | organizationId | UUID | 必填/按业务规则 | - |
| 创建运行时安装计划（Create Runtime Installation Plan） | runtimeInfrastructurePackageId | UUID | 必填/按业务规则 | - |
| 创建运行时安装计划（Create Runtime Installation Plan） | runtimeName | String | 必填/按业务规则 | - |
| 创建运行时安装计划（Create Runtime Installation Plan） | agentInstallMode | String | 必填/按业务规则 | - |
| 创建运行时安装计划（Create Runtime Installation Plan） | expectedNodeCount | Int | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.运行时安装计划目录（RuntimeInstallationPlanCatalog）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RuntimeInstallationPlanCatalog" -->
#### 4.5.4 运行时安装计划目录（Runtime Installation Plan Catalog）/FLP-DOC-SECTION-39

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 运行时安装计划（Runtime Installation Plan） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 运行时安装计划目录（Runtime Installation Plan Catalog）。
- 确认预期业务结果。
- 查看 运行时安装计划目录（Runtime Installation Plan Catalog） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.运行时安装指南（RuntimeInstallationGuide）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RuntimeInstallationGuide" -->
#### 4.5.5 运行时安装指南（Runtime Installation Guide）/FLP-DOC-SECTION-40

| 项目 | 说明 |
| --- | --- |
| 入口 | 运行时安装指南页面（Runtime Installation Guide Screen） |
| 角色/触发条件 | 运行时安装程序（Runtime Installer） |
| 业务对象 | 运行时安装计划（Runtime Installation Plan） |
| 成功结果 | 尚未明确 |

- 打开运行时安装指南页面（Runtime Installation Guide Screen）。
- 按有效业务数据提交 运行时安装指南（Runtime Installation Guide）。
- 确认预期业务结果。
- 查看 运行时安装指南（Runtime Installation Guide） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.规划运行时基础设施（PlanRuntimeInfrastructure）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/PlanRuntimeInfrastructure" -->
#### 4.5.6 规划运行时基础设施（Plan Runtime Infrastructure）/FLP-DOC-SECTION-41

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 平台管理员（Platform Admin） |
| 业务对象 | 运行时基础设施（Runtime Infrastructure） |
| 成功结果 | 运行时基础设施已计划（Runtime Infrastructure Planned）, 状态: 已规划（Planned） |

- 打开已配置的系统入口。
- 按有效业务数据提交 规划运行时基础设施（Plan Runtime Infrastructure）。
- 确认运行时基础设施已计划（Runtime Infrastructure Planned），并确认状态变为 已规划（Planned）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 规划运行时基础设施（Plan Runtime Infrastructure） | runtimeInfrastructureId | UUID | 必填标识 | 来源：RuntimeInstallationPlanCreated.runtimeInfrastructureId; id, technical |
| 规划运行时基础设施（Plan Runtime Infrastructure） | runtimeInstallationPlanId | UUID | 必填/按业务规则 | 来源：RuntimeInstallationPlanCreated.runtimeInstallationPlanId; technical |

<!-- em:section id="user-manual.slice.注册运行时基础设施（RegisterRuntimeInfrastructure）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RegisterRuntimeInfrastructure" -->
#### 4.5.7 注册运行时基础设施（Register Runtime Infrastructure）/FLP-DOC-SECTION-42

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 运行时安装程序（Runtime Installer） |
| 业务对象 | 运行时基础设施（Runtime Infrastructure） |
| 成功结果 | 运行时基础设施已注册（Runtime Infrastructure Registered）, 状态: 已注册（Registered） |

- 打开已配置的系统入口。
- 按有效业务数据提交 注册运行时基础设施（Register Runtime Infrastructure）。
- 确认运行时基础设施已注册（Runtime Infrastructure Registered），并确认状态变为 已注册（Registered）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 注册运行时基础设施（Register Runtime Infrastructure） | runtimeInfrastructureId | UUID | 必填标识 | 来源：RuntimeInfrastructurePlanned.runtimeInfrastructureId; id, technical |
| 注册运行时基础设施（Register Runtime Infrastructure） | runtimeAgentId | UUID | 必填/按业务规则 | generated, technical |

<!-- em:section id="user-manual.slice.验证运行时基础设施（VerifyRuntimeInfrastructure）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/VerifyRuntimeInfrastructure" -->
#### 4.5.8 验证运行时基础设施（Verify Runtime Infrastructure）/FLP-DOC-SECTION-43

| 项目 | 说明 |
| --- | --- |
| 入口 | 运行时安装计划页面（Runtime Installation Plan Screen） |
| 角色/触发条件 | 平台管理员（Platform Admin） |
| 业务对象 | 运行时基础设施（Runtime Infrastructure） |
| 成功结果 | 运行时基础设施已验证（Runtime Infrastructure Verified）, 运行时基础设施验证失败（Runtime Infrastructure Verification Failed）, 状态: 已验证（Verified） |

- 打开运行时安装计划页面（Runtime Installation Plan Screen）。
- 按有效业务数据提交 验证运行时基础设施（Verify Runtime Infrastructure）。
- 确认运行时基础设施已验证（Runtime Infrastructure Verified）, 运行时基础设施验证失败（Runtime Infrastructure Verification Failed），并确认状态变为 已验证（Verified）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 验证运行时基础设施（Verify Runtime Infrastructure） | runtimeInfrastructureId | UUID | 必填标识 | 来源：RuntimeInfrastructureRegistered.runtimeInfrastructureId; id, technical |
| 验证运行时基础设施（Verify Runtime Infrastructure） | runtimeAgentId | UUID | 必填/按业务规则 | 来源：RuntimeInfrastructureRegistered.runtimeAgentId; technical |
| 验证运行时基础设施（Verify Runtime Infrastructure） | agentInstallMode | String | 必填/按业务规则 | 派生：RuntimeInstallationPlanCreated.agentInstallMode |
| 验证运行时基础设施（Verify Runtime Infrastructure） | verificationPassed | Boolean | 必填/按业务规则 | - |
| 验证运行时基础设施（Verify Runtime Infrastructure） | observedNodeCount | Int | 必填/按业务规则 | - |
| 验证运行时基础设施（Verify Runtime Infrastructure） | failureReason | String | 必填/按业务规则 | - |

**注意事项**

- 运行时基础设施验证结果（Runtime Infrastructure Verification Outcome） / 运行时基础设施验证通过（Runtime Infrastructure Verification Passed） - 当 验证运行时基础设施（Verify Runtime Infrastructure）, 则 运行时基础设施已验证（Runtime Infrastructure Verified）.
- 运行时基础设施验证结果（Runtime Infrastructure Verification Outcome） / 运行时基础设施验证失败（Runtime Infrastructure Verification Failed） - 当 验证运行时基础设施（Verify Runtime Infrastructure）, 则 运行时基础设施验证失败（Runtime Infrastructure Verification Failed）.

<!-- em:section id="user-manual.slice.部署运行时代理（DeployRuntimeAgent）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/DeployRuntimeAgent" -->
#### 4.5.9 部署运行时代理（Deploy Runtime Agent）/FLP-DOC-SECTION-44

| 项目 | 说明 |
| --- | --- |
| 入口 | 运行时安装计划页面（Runtime Installation Plan Screen） |
| 角色/触发条件 | 平台管理员（Platform Admin） |
| 业务对象 | 运行时基础设施（Runtime Infrastructure） |
| 成功结果 | 运行时代理安装成功（Runtime Agent Installation Succeeded）, 运行时代理安装失败（Runtime Agent Installation Failed）, 状态: 代理就绪（Agent Ready） |

- 打开运行时安装计划页面（Runtime Installation Plan Screen）。
- 按有效业务数据提交 部署运行时代理（Deploy Runtime Agent）。
- 确认运行时代理安装成功（Runtime Agent Installation Succeeded）, 运行时代理安装失败（Runtime Agent Installation Failed），并确认状态变为 代理就绪（Agent Ready）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 部署运行时代理（Deploy Runtime Agent） | runtimeAgentId | UUID | 必填/按业务规则 | 来源：RuntimeInfrastructureVerified.runtimeAgentId; technical |
| 部署运行时代理（Deploy Runtime Agent） | runtimeInfrastructureId | UUID | 必填标识 | id, technical |

**注意事项**

- 运行时代理部署结果（Runtime Agent Deployment Outcome） / 运行时代理部署成功（Runtime Agent Deployment Succeeded） - 当 部署运行时代理（Deploy Runtime Agent）, 则 运行时代理安装成功（Runtime Agent Installation Succeeded）.
- 运行时代理部署结果（Runtime Agent Deployment Outcome） / 运行时代理部署失败（Runtime Agent Deployment Failed） - 当 部署运行时代理（Deploy Runtime Agent）, 则 运行时代理安装失败（Runtime Agent Installation Failed）.

<!-- em:section id="user-manual.slice.重试运行时代理部署（RetryRuntimeAgentDeployment）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RetryRuntimeAgentDeployment" -->
#### 4.5.10 重试运行时代理部署（Retry Runtime Agent Deployment）/FLP-DOC-SECTION-45

| 项目 | 说明 |
| --- | --- |
| 入口 | 运行时安装计划页面（Runtime Installation Plan Screen） (confirm) |
| 角色/触发条件 | 平台管理员（Platform Admin） |
| 业务对象 | 运行时基础设施（Runtime Infrastructure） |
| 成功结果 | 运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）, 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed）, 状态: 代理就绪（Agent Ready） |

- 打开运行时安装计划页面（Runtime Installation Plan Screen）。
- 按有效业务数据提交 重试运行时代理部署（Retry Runtime Agent Deployment）。
- 确认运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）, 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed），并确认状态变为 代理就绪（Agent Ready）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 重试运行时代理部署（Retry Runtime Agent Deployment） | runtimeAgentId | UUID | 必填/按业务规则 | 派生：RuntimeInfrastructureAccessView.runtimeAgentId; technical |
| 重试运行时代理部署（Retry Runtime Agent Deployment） | runtimeInfrastructureId | UUID | 必填标识 | id, technical |
| 重试运行时代理部署（Retry Runtime Agent Deployment） | currentRuntimeInfrastructureState | RuntimeInfrastructure.State | 必填/按业务规则 | 派生：RuntimeInfrastructureAccessView.state |
| 重试运行时代理部署（Retry Runtime Agent Deployment） | retryReason | String | 必填/按业务规则 | - |

**注意事项**

- 运行时代理部署重试结果（Runtime Agent Deployment Retry Outcome） / 部署失败后运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded After Failed Deployment） - 当 重试运行时代理部署（Retry Runtime Agent Deployment）, 则 运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）.
- 运行时代理部署重试结果（Runtime Agent Deployment Retry Outcome） / 离线后运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded After Offline） - 当 重试运行时代理部署（Retry Runtime Agent Deployment）, 则 运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）.
- 运行时代理部署重试结果（Runtime Agent Deployment Retry Outcome） / 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed） - 当 重试运行时代理部署（Retry Runtime Agent Deployment）, 则 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed）.
- 运行时代理部署重试结果（Runtime Agent Deployment Retry Outcome） / 已连接时拒绝运行时代理部署重试（Reject Runtime Agent Deployment Retry While Connected） - 当 重试运行时代理部署（Retry Runtime Agent Deployment）, 则 拒绝: 不允许运行时代理部署重试.

<!-- em:section id="user-manual.slice.记录运行时连接已建立（RecordRuntimeConnectionEstablished）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RecordRuntimeConnectionEstablished" -->
#### 4.5.11 记录运行时连接已建立（Record Runtime Connection Established）/FLP-DOC-SECTION-46

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 运行时基础设施（Runtime Infrastructure） |
| 成功结果 | 运行时连接已建立（Runtime Connection Established）, 状态: 已连接（Connected） |

- 打开已配置的系统入口。
- 按有效业务数据提交 记录运行时连接已建立（Record Runtime Connection Established）。
- 确认运行时连接已建立（Runtime Connection Established），并确认状态变为 已连接（Connected）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 记录运行时连接已建立（Record Runtime Connection Established） | runtimeInfrastructureId | UUID | 必填标识 | 来源：AgentRuntimeConnectionEstablished.runtimeInfrastructureId; id, technical |
| 记录运行时连接已建立（Record Runtime Connection Established） | runtimeAgentId | UUID | 必填/按业务规则 | 来源：AgentRuntimeConnectionEstablished.runtimeAgentId |
| 记录运行时连接已建立（Record Runtime Connection Established） | agentInstallMode | String | 必填/按业务规则 | 派生：RuntimeInstallationPlanCreated.agentInstallMode |
| 记录运行时连接已建立（Record Runtime Connection Established） | organizationId | UUID | 必填/按业务规则 | 派生：RuntimeInstallationPlanCreated.organizationId |
| 记录运行时连接已建立（Record Runtime Connection Established） | runtimeName | String | 必填/按业务规则 | 派生：RuntimeInstallationPlanCreated.runtimeName |
| 记录运行时连接已建立（Record Runtime Connection Established） | runtimeAgentEndpoint | String | 必填/按业务规则 | 来源：AgentRuntimeConnectionEstablished.runtimeAgentEndpoint |
| 记录运行时连接已建立（Record Runtime Connection Established） | endpointScope | String | 必填/按业务规则 | 来源：AgentRuntimeConnectionEstablished.endpointScope |

<!-- em:section id="user-manual.slice.运行时代理端点目录（RuntimeAgentEndpointCatalog）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RuntimeAgentEndpointCatalog" -->
#### 4.5.12 运行时代理端点目录（Runtime Agent Endpoint Catalog）/FLP-DOC-SECTION-47

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 运行时基础设施（Runtime Infrastructure） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 运行时代理端点目录（Runtime Agent Endpoint Catalog）。
- 确认预期业务结果。
- 查看 运行时代理端点目录（Runtime Agent Endpoint Catalog） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.运行时基础设施访问视图（RuntimeInfrastructureAccessView）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning/slice/RuntimeInfrastructureAccessView" -->
#### 4.5.13 运行时基础设施访问视图（Runtime Infrastructure Access View）/FLP-DOC-SECTION-48

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 运行时基础设施（Runtime Infrastructure） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 运行时基础设施访问视图（Runtime Infrastructure Access View）。
- 确认预期业务结果。
- 查看 运行时基础设施访问视图（Runtime Infrastructure Access View） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.context.数据集治理（DatasetGovernance）" source="domain/FederationLearningPlatform/context/DatasetGovernance" -->
### 4.6 数据集治理（Dataset Governance）/FLP-DOC-SECTION-49

上下文说明: 数据集治理将特征架构、平台记录的运行时数据集元数据、数据集访问验证证据以及训练评估包进行分离。运行时代理在参与者基础设施内拥有数据集声明、数据连接器、本地访问配置文件和凭据。

<!-- em:section id="user-manual.slice.定义特征架构（DefineFeatureSchema）" source="domain/FederationLearningPlatform/context/DatasetGovernance/slice/DefineFeatureSchema" -->
#### 4.6.1 定义特征架构（Define Feature Schema）/FLP-DOC-SECTION-50

| 项目 | 说明 |
| --- | --- |
| 入口 | 特征架构编辑器（Feature Schema Editor） |
| 角色/触发条件 | 数据管理员（Data Steward） |
| 业务对象 | 特征架构（Feature Schema） |
| 成功结果 | 特征架构已定义（Feature Schema Defined）, 状态: 草稿（Draft） |

- 打开特征架构编辑器（Feature Schema Editor）。
- 按有效业务数据提交 定义特征架构（Define Feature Schema）。
- 确认特征架构已定义（Feature Schema Defined），并确认状态变为 草稿（Draft）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 定义特征架构（Define Feature Schema） | featureSchemaId | UUID | 必填标识 | id, generated, technical |
| 定义特征架构（Define Feature Schema） | featureDomain | String | 必填/按业务规则 | - |
| 定义特征架构（Define Feature Schema） | version | String | 必填/按业务规则 | generated, technical |
| 定义特征架构（Define Feature Schema） | dataModality | String | 必填/按业务规则 | - |
| 定义特征架构（Define Feature Schema） | features | FeatureDefinition | 必填/按业务规则 | - |
| 定义特征架构（Define Feature Schema） | labels | LabelDefinition | 必填/按业务规则 | - |

**注意事项**

- 特征架构域版本唯一（Feature Schema Domain Version Unique） / 拒绝重复的特征架构版本（Reject Duplicate Feature Schema Version） - 当 定义特征架构（Define Feature Schema）, 则 拒绝: 特征架构版本已存在.

<!-- em:section id="user-manual.slice.发布特征架构（PublishFeatureSchema）" source="domain/FederationLearningPlatform/context/DatasetGovernance/slice/PublishFeatureSchema" -->
#### 4.6.2 发布特征架构（Publish Feature Schema）/FLP-DOC-SECTION-51

| 项目 | 说明 |
| --- | --- |
| 入口 | 特征架构编辑器（Feature Schema Editor） (confirm) |
| 角色/触发条件 | 数据管理员（Data Steward） |
| 业务对象 | 特征架构（Feature Schema） |
| 成功结果 | 特征架构已发布（Feature Schema Published）, 状态: 已发布（Published） |

- 打开特征架构编辑器（Feature Schema Editor）。
- 按有效业务数据提交 发布特征架构（Publish Feature Schema）。
- 确认特征架构已发布（Feature Schema Published），并确认状态变为 已发布（Published）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 发布特征架构（Publish Feature Schema） | featureSchemaId | UUID | 必填标识 | id, technical |
| 发布特征架构（Publish Feature Schema） | publishNote | String | 必填/按业务规则 | - |

**注意事项**

- 仅可发布草稿架构（Only Draft Schemas Can Be Published） / 发布草稿架构（Publish Draft Schema） - 当 发布特征架构（Publish Feature Schema）, 则 特征架构已发布（Feature Schema Published）.

<!-- em:section id="user-manual.slice.弃用特征架构（DeprecateFeatureSchema）" source="domain/FederationLearningPlatform/context/DatasetGovernance/slice/DeprecateFeatureSchema" -->
#### 4.6.3 弃用特征架构（Deprecate Feature Schema）/FLP-DOC-SECTION-52

| 项目 | 说明 |
| --- | --- |
| 入口 | 特征架构编辑器（Feature Schema Editor） (confirm) |
| 角色/触发条件 | 数据管理员（Data Steward） |
| 业务对象 | 特征架构（Feature Schema） |
| 成功结果 | 特征架构已弃用（Feature Schema Deprecated）, 状态: 已弃用（Deprecated） |

- 打开特征架构编辑器（Feature Schema Editor）。
- 按有效业务数据提交 弃用特征架构（Deprecate Feature Schema）。
- 确认特征架构已弃用（Feature Schema Deprecated），并确认状态变为 已弃用（Deprecated）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 弃用特征架构（Deprecate Feature Schema） | featureSchemaId | UUID | 必填标识 | id, technical |
| 弃用特征架构（Deprecate Feature Schema） | deprecationReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.停用特征架构（RetireFeatureSchema）" source="domain/FederationLearningPlatform/context/DatasetGovernance/slice/RetireFeatureSchema" -->
#### 4.6.4 停用特征架构（Retire Feature Schema）/FLP-DOC-SECTION-53

| 项目 | 说明 |
| --- | --- |
| 入口 | 特征架构编辑器（Feature Schema Editor） (confirm) |
| 角色/触发条件 | 数据管理员（Data Steward） |
| 业务对象 | 特征架构（Feature Schema） |
| 成功结果 | 特征架构已停用（Feature Schema Retired）, 状态: 已停用（Retired） |

- 打开特征架构编辑器（Feature Schema Editor）。
- 按有效业务数据提交 停用特征架构（Retire Feature Schema）。
- 确认特征架构已停用（Feature Schema Retired），并确认状态变为 已停用（Retired）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 停用特征架构（Retire Feature Schema） | featureSchemaId | UUID | 必填标识 | id, technical |
| 停用特征架构（Retire Feature Schema） | retirementReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.取代特征架构版本（SupersedeFeatureSchemaVersion）" source="domain/FederationLearningPlatform/context/DatasetGovernance/slice/SupersedeFeatureSchemaVersion" -->
#### 4.6.5 取代特征架构版本（Supersede Feature Schema Version）/FLP-DOC-SECTION-54

| 项目 | 说明 |
| --- | --- |
| 入口 | 特征架构编辑器（Feature Schema Editor） (confirm) |
| 角色/触发条件 | 数据管理员（Data Steward） |
| 业务对象 | 特征架构（Feature Schema） |
| 成功结果 | 特征架构版本已取代（Feature Schema Version Superseded） |

- 打开特征架构编辑器（Feature Schema Editor）。
- 按有效业务数据提交 取代特征架构版本（Supersede Feature Schema Version）。
- 确认特征架构版本已取代（Feature Schema Version Superseded）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 取代特征架构版本（Supersede Feature Schema Version） | featureSchemaId | UUID | 必填标识 | id, technical |
| 取代特征架构版本（Supersede Feature Schema Version） | supersededByFeatureSchemaId | UUID | 必填/按业务规则 | - |
| 取代特征架构版本（Supersede Feature Schema Version） | supersessionReason | String | 必填/按业务规则 | - |

**注意事项**

- 使用较新版本取代已发布的架构（Supersede Published Schema With Newer Version） / 取代较旧版本（Supersede Older Version） - 当 取代特征架构版本（Supersede Feature Schema Version）, 则 特征架构版本已取代（Feature Schema Version Superseded）.

<!-- em:section id="user-manual.slice.标记当前推荐的特征架构版本（MarkCurrentRecommendedFeatureSchemaVersion）" source="domain/FederationLearningPlatform/context/DatasetGovernance/slice/MarkCurrentRecommendedFeatureSchemaVersion" -->
#### 4.6.6 标记当前推荐的特征架构版本（Mark Current Recommended Feature Schema Version）/FLP-DOC-SECTION-55

| 项目 | 说明 |
| --- | --- |
| 入口 | 特征架构编辑器（Feature Schema Editor） (confirm) |
| 角色/触发条件 | 数据管理员（Data Steward） |
| 业务对象 | 特征架构（Feature Schema） |
| 成功结果 | 已标记当前推荐特征架构版本（Current Recommended Feature Schema Version Marked） |

- 打开特征架构编辑器（Feature Schema Editor）。
- 按有效业务数据提交 标记当前推荐的特征架构版本（Mark Current Recommended Feature Schema Version）。
- 确认已标记当前推荐特征架构版本（Current Recommended Feature Schema Version Marked）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 标记当前推荐的特征架构版本（Mark Current Recommended Feature Schema Version） | featureSchemaId | UUID | 必填标识 | id, technical |
| 标记当前推荐的特征架构版本（Mark Current Recommended Feature Schema Version） | recommendationNote | String | 必填/按业务规则 | - |

**注意事项**

- 每个域仅一个推荐版本（Only One Recommended Version Per Domain） / 标记新的推荐版本（Mark New Recommended Version） - 当 标记当前推荐的特征架构版本（Mark Current Recommended Feature Schema Version）, 则 已标记当前推荐特征架构版本（Current Recommended Feature Schema Version Marked）.

<!-- em:section id="user-manual.slice.当前推荐的特征架构目录（CurrentRecommendedFeatureSchemaCatalog）" source="domain/FederationLearningPlatform/context/DatasetGovernance/slice/CurrentRecommendedFeatureSchemaCatalog" -->
#### 4.6.7 当前推荐的特征架构目录（Current Recommended Feature Schema Catalog）/FLP-DOC-SECTION-56

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 特征架构（Feature Schema） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 当前推荐的特征架构目录（Current Recommended Feature Schema Catalog）。
- 确认预期业务结果。
- 查看 当前推荐的特征架构目录（Current Recommended Feature Schema Catalog） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.特征架构目录（FeatureSchemaCatalog）" source="domain/FederationLearningPlatform/context/DatasetGovernance/slice/FeatureSchemaCatalog" -->
#### 4.6.8 特征架构目录（Feature Schema Catalog）/FLP-DOC-SECTION-57

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 特征架构（Feature Schema） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 特征架构目录（Feature Schema Catalog）。
- 确认预期业务结果。
- 查看 特征架构目录（Feature Schema Catalog） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.记录运行时数据集元数据（RecordRuntimeDatasetMetadata）" source="domain/FederationLearningPlatform/context/DatasetGovernance/slice/RecordRuntimeDatasetMetadata" -->
#### 4.6.9 记录运行时数据集元数据（Record Runtime Dataset Metadata）/FLP-DOC-SECTION-58

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 在代理报告时记录数据集元数据（Record Dataset Metadata When Agent Reported） |
| 业务对象 | 运行时数据集元数据（Runtime Dataset Metadata） |
| 成功结果 | 数据集元数据已报告（Dataset Metadata Reported）, 状态: 已报告元数据（Metadata Reported） |

- 打开已配置的系统入口。
- 按有效业务数据提交 记录运行时数据集元数据（Record Runtime Dataset Metadata）。
- 确认数据集元数据已报告（Dataset Metadata Reported），并确认状态变为 已报告元数据（Metadata Reported）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 记录运行时数据集元数据（Record Runtime Dataset Metadata） | metadataReportId | UUID | 必填标识 | 来源：AgentDatasetMetadataReported.metadataReportId; id, technical |
| 记录运行时数据集元数据（Record Runtime Dataset Metadata） | datasetId | UUID | 必填/按业务规则 | 来源：AgentDatasetMetadataReported.datasetId |
| 记录运行时数据集元数据（Record Runtime Dataset Metadata） | organizationId | UUID | 必填/按业务规则 | 来源：AgentDatasetMetadataReported.organizationId |
| 记录运行时数据集元数据（Record Runtime Dataset Metadata） | runtimeId | UUID | 必填/按业务规则 | 来源：AgentDatasetMetadataReported.runtimeId |
| 记录运行时数据集元数据（Record Runtime Dataset Metadata） | featureSchemaId | UUID | 必填/按业务规则 | 来源：AgentDatasetMetadataReported.featureSchemaId |
| 记录运行时数据集元数据（Record Runtime Dataset Metadata） | datasetName | String | 必填/按业务规则 | 来源：AgentDatasetMetadataReported.datasetName |
| 记录运行时数据集元数据（Record Runtime Dataset Metadata） | sampleCount | Int | 必填/按业务规则 | 来源：AgentDatasetMetadataReported.sampleCount |
| 记录运行时数据集元数据（Record Runtime Dataset Metadata） | featureCount | Int | 必填/按业务规则 | 来源：AgentDatasetMetadataReported.featureCount |
| 记录运行时数据集元数据（Record Runtime Dataset Metadata） | schemaCompatible | Boolean | 必填/按业务规则 | 来源：AgentDatasetMetadataReported.schemaCompatible |
| 记录运行时数据集元数据（Record Runtime Dataset Metadata） | labelCompatible | Boolean | 必填/按业务规则 | 来源：AgentDatasetMetadataReported.labelCompatible |
| 记录运行时数据集元数据（Record Runtime Dataset Metadata） | missingValueRate | Decimal | 必填/按业务规则 | 来源：AgentDatasetMetadataReported.missingValueRate |
| 记录运行时数据集元数据（Record Runtime Dataset Metadata） | duplicateRate | Decimal | 必填/按业务规则 | 来源：AgentDatasetMetadataReported.duplicateRate |
| 记录运行时数据集元数据（Record Runtime Dataset Metadata） | qualityScore | Decimal | 必填/按业务规则 | 来源：AgentDatasetMetadataReported.qualityScore |
| 记录运行时数据集元数据（Record Runtime Dataset Metadata） | nonIidScore | Decimal | 必填/按业务规则 | 来源：AgentDatasetMetadataReported.nonIidScore |
| 记录运行时数据集元数据（Record Runtime Dataset Metadata） | classBalanceScore | Decimal | 必填/按业务规则 | 来源：AgentDatasetMetadataReported.classBalanceScore |

<!-- em:section id="user-manual.slice.记录运行时数据集重新分析元数据（RecordRuntimeDatasetReprofiledMetadata）" source="domain/FederationLearningPlatform/context/DatasetGovernance/slice/RecordRuntimeDatasetReprofiledMetadata" -->
#### 4.6.10 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata）/FLP-DOC-SECTION-59

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 代理重新分析时记录数据集元数据（Record Dataset Metadata When Agent Reprofiled） |
| 业务对象 | 运行时数据集元数据（Runtime Dataset Metadata） |
| 成功结果 | 数据集元数据已重新分析（Dataset Metadata Reprofiled）, 状态: 已报告元数据（Metadata Reported） |

- 打开已配置的系统入口。
- 按有效业务数据提交 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata）。
- 确认数据集元数据已重新分析（Dataset Metadata Reprofiled），并确认状态变为 已报告元数据（Metadata Reported）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata） | metadataReportId | UUID | 必填标识 | 来源：AgentDatasetReprofiled.metadataReportId; id, technical |
| 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata） | datasetId | UUID | 必填/按业务规则 | 来源：AgentDatasetReprofiled.datasetId |
| 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata） | organizationId | UUID | 必填/按业务规则 | 来源：AgentDatasetReprofiled.organizationId |
| 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata） | runtimeId | UUID | 必填/按业务规则 | 来源：AgentDatasetReprofiled.runtimeId |
| 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata） | featureSchemaId | UUID | 必填/按业务规则 | 来源：AgentDatasetReprofiled.featureSchemaId |
| 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata） | datasetName | String | 必填/按业务规则 | 来源：AgentDatasetReprofiled.datasetName |
| 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata） | sampleCount | Int | 必填/按业务规则 | 来源：AgentDatasetReprofiled.sampleCount |
| 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata） | featureCount | Int | 必填/按业务规则 | 来源：AgentDatasetReprofiled.featureCount |
| 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata） | schemaCompatible | Boolean | 必填/按业务规则 | 来源：AgentDatasetReprofiled.schemaCompatible |
| 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata） | labelCompatible | Boolean | 必填/按业务规则 | 来源：AgentDatasetReprofiled.labelCompatible |
| 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata） | missingValueRate | Decimal | 必填/按业务规则 | 来源：AgentDatasetReprofiled.missingValueRate |
| 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata） | duplicateRate | Decimal | 必填/按业务规则 | 来源：AgentDatasetReprofiled.duplicateRate |
| 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata） | qualityScore | Decimal | 必填/按业务规则 | 来源：AgentDatasetReprofiled.qualityScore |
| 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata） | nonIidScore | Decimal | 必填/按业务规则 | 来源：AgentDatasetReprofiled.nonIidScore |
| 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata） | classBalanceScore | Decimal | 必填/按业务规则 | 来源：AgentDatasetReprofiled.classBalanceScore |

<!-- em:section id="user-manual.slice.运行时数据集元数据目录（RuntimeDatasetMetadataCatalog）" source="domain/FederationLearningPlatform/context/DatasetGovernance/slice/RuntimeDatasetMetadataCatalog" -->
#### 4.6.11 运行时数据集元数据目录（Runtime Dataset Metadata Catalog）/FLP-DOC-SECTION-60

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 运行时数据集元数据（Runtime Dataset Metadata） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 运行时数据集元数据目录（Runtime Dataset Metadata Catalog）。
- 确认预期业务结果。
- 查看 运行时数据集元数据目录（Runtime Dataset Metadata Catalog） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.context.模型仓库（ModelRepository）" source="domain/FederationLearningPlatform/context/ModelRepository" -->
### 4.7 模型仓库（Model Repository）/FLP-DOC-SECTION-61

上下文说明: 模型仓库拥有模型标识以及不可变的、可拉取的模型制品位置，供训练编排和运行时代理使用。

<!-- em:section id="user-manual.slice.注册模型制品（RegisterModelArtifact）" source="domain/FederationLearningPlatform/context/ModelRepository/slice/RegisterModelArtifact" -->
#### 4.7.1 注册模型制品（Register Model Artifact）/FLP-DOC-SECTION-62

| 项目 | 说明 |
| --- | --- |
| 入口 | 模型仓库屏幕（Model Repository Screen） |
| 角色/触发条件 | MLOps 工程师（MLOps Engineer） |
| 业务对象 | 模型制品（Model Artifact） |
| 成功结果 | 模型工件已注册（Model Artifact Registered）, 状态: 已注册（Registered） |

- 打开模型仓库屏幕（Model Repository Screen）。
- 按有效业务数据提交 注册模型制品（Register Model Artifact）。
- 确认模型工件已注册（Model Artifact Registered），并确认状态变为 已注册（Registered）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 注册模型制品（Register Model Artifact） | modelId | UUID | 必填标识 | id, generated, technical |
| 注册模型制品（Register Model Artifact） | modelName | String | 必填/按业务规则 | display |
| 注册模型制品（Register Model Artifact） | modelVersion | String | 必填/按业务规则 | - |
| 注册模型制品（Register Model Artifact） | sourceType | String | 必填/按业务规则 | - |
| 注册模型制品（Register Model Artifact） | stagedFileId | UUID | 必填/按业务规则 | file |
| 注册模型制品（Register Model Artifact） | modelFormat | String | 必填/按业务规则 | - |

**注意事项**

- 模型工件必须可拉取且可验证（Model Artifact Must Be Pullable And Verifiable） / 注册可拉取模型工件（Register Pullable Model Artifact） - 当 注册模型制品（Register Model Artifact）, 则 模型工件已注册（Model Artifact Registered）.

<!-- em:section id="user-manual.slice.模型制品目录（ModelArtifactCatalog）" source="domain/FederationLearningPlatform/context/ModelRepository/slice/ModelArtifactCatalog" -->
#### 4.7.2 模型制品目录（Model Artifact Catalog）/FLP-DOC-SECTION-63

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 模型制品（Model Artifact） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 模型制品目录（Model Artifact Catalog）。
- 确认预期业务结果。
- 查看 模型制品目录（Model Artifact Catalog） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.context.训练编排（TrainingOrchestration）" source="domain/FederationLearningPlatform/context/TrainingOrchestration" -->
### 4.8 训练编排（Training Orchestration）/FLP-DOC-SECTION-64

上下文说明: 训练编排负责作业意图、参与者资格、运行时分发以及每轮分布式训练和评估。

<!-- em:section id="user-manual.slice.定义训练运行配置（DefineTrainingRunConfiguration）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/DefineTrainingRunConfiguration" -->
#### 4.8.1 定义训练运行配置（Define Training Run Configuration）/FLP-DOC-SECTION-65

| 项目 | 说明 |
| --- | --- |
| 入口 | 训练运行配置屏幕（Training Run Configuration Screen） |
| 角色/触发条件 | MLOps 工程师（MLOps Engineer） |
| 业务对象 | 训练运行配置（Training Run Configuration） |
| 成功结果 | 训练运行配置已定义（Training Run Configuration Defined）, 状态: 草稿（Draft） |

- 打开训练运行配置屏幕（Training Run Configuration Screen）。
- 按有效业务数据提交 定义训练运行配置（Define Training Run Configuration）。
- 确认训练运行配置已定义（Training Run Configuration Defined），并确认状态变为 草稿（Draft）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 定义训练运行配置（Define Training Run Configuration） | trainingRunConfigurationId | UUID | 必填标识 | id, generated, technical |
| 定义训练运行配置（Define Training Run Configuration） | federationId | UUID | 必填/按业务规则 | - |
| 定义训练运行配置（Define Training Run Configuration） | featureSchemaId | UUID | 必填/按业务规则 | - |
| 定义训练运行配置（Define Training Run Configuration） | initialModelId | UUID | 必填/按业务规则 | 来源：ModelArtifactCatalog.modelId |
| 定义训练运行配置（Define Training Run Configuration） | strategyName | String | 必填/按业务规则 | - |
| 定义训练运行配置（Define Training Run Configuration） | aggregationAlgorithm | String | 必填/按业务规则 | - |
| 定义训练运行配置（Define Training Run Configuration） | maxRounds | Int | 必填/按业务规则 | - |
| 定义训练运行配置（Define Training Run Configuration） | minimumNodesPerRound | Int | 必填/按业务规则 | - |
| 定义训练运行配置（Define Training Run Configuration） | roundTimeoutSeconds | Int | 必填/按业务规则 | - |
| 定义训练运行配置（Define Training Run Configuration） | nodeResponseTimeoutSeconds | Int | 必填/按业务规则 | - |
| 定义训练运行配置（Define Training Run Configuration） | localEpochs | Int | 必填/按业务规则 | - |
| 定义训练运行配置（Define Training Run Configuration） | batchSize | Int | 必填/按业务规则 | - |
| 定义训练运行配置（Define Training Run Configuration） | learningRate | Decimal | 必填/按业务规则 | - |
| 定义训练运行配置（Define Training Run Configuration） | optimizer | String | 必填/按业务规则 | - |
| 定义训练运行配置（Define Training Run Configuration） | lossFunction | String | 必填/按业务规则 | - |
| 定义训练运行配置（Define Training Run Configuration） | gradientClippingNorm | Decimal | 必填/按业务规则 | - |
| 定义训练运行配置（Define Training Run Configuration） | secureAggregationRequired | Boolean | 必填/按业务规则 | - |
| 定义训练运行配置（Define Training Run Configuration） | differentialPrivacyEnabled | Boolean | 必填/按业务规则 | - |
| 定义训练运行配置（Define Training Run Configuration） | dpNoiseMultiplier | Decimal | 必填/按业务规则 | - |
| 定义训练运行配置（Define Training Run Configuration） | dpClipNorm | Decimal | 必填/按业务规则 | - |
| 定义训练运行配置（Define Training Run Configuration） | minimumAccuracy | Decimal | 必填/按业务规则 | - |
| 定义训练运行配置（Define Training Run Configuration） | minimumFairnessScore | Decimal | 必填/按业务规则 | - |
| 定义训练运行配置（Define Training Run Configuration） | failureToleranceRatio | Decimal | 必填/按业务规则 | - |

**注意事项**

- 训练配置必须可运行（Training Configuration Must Be Runnable） / 定义可运行配置（Define Runnable Configuration） - 当 定义训练运行配置（Define Training Run Configuration）, 则 训练运行配置已定义（Training Run Configuration Defined）.
- 训练配置必须可运行（Training Configuration Must Be Runnable） / 拒绝空轮次预算（Reject Empty Round Budget） - 当 定义训练运行配置（Define Training Run Configuration）, 则 拒绝: 轮次预算必须为正数.
- 训练配置必须可运行（Training Configuration Must Be Runnable） / 拒绝无参数的差分隐私（Reject Differential Privacy Without Parameters） - 当 定义训练运行配置（Define Training Run Configuration）, 则 拒绝: 差分隐私参数为必填项.

<!-- em:section id="user-manual.slice.更新训练运行配置（UpdateTrainingRunConfiguration）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/UpdateTrainingRunConfiguration" -->
#### 4.8.2 更新训练运行配置（Update Training Run Configuration）/FLP-DOC-SECTION-66

| 项目 | 说明 |
| --- | --- |
| 入口 | 训练运行配置屏幕（Training Run Configuration Screen） |
| 角色/触发条件 | MLOps 工程师（MLOps Engineer） |
| 业务对象 | 训练运行配置（Training Run Configuration） |
| 成功结果 | 训练运行配置已更新（Training Run Configuration Updated）, 状态: 草稿（Draft） |

- 打开训练运行配置屏幕（Training Run Configuration Screen）。
- 按有效业务数据提交 更新训练运行配置（Update Training Run Configuration）。
- 确认训练运行配置已更新（Training Run Configuration Updated），并确认状态变为 草稿（Draft）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 更新训练运行配置（Update Training Run Configuration） | trainingRunConfigurationId | UUID | 必填标识 | id, technical |
| 更新训练运行配置（Update Training Run Configuration） | federationId | UUID | 必填/按业务规则 | - |
| 更新训练运行配置（Update Training Run Configuration） | featureSchemaId | UUID | 必填/按业务规则 | - |
| 更新训练运行配置（Update Training Run Configuration） | initialModelId | UUID | 必填/按业务规则 | 来源：ModelArtifactCatalog.modelId |
| 更新训练运行配置（Update Training Run Configuration） | strategyName | String | 必填/按业务规则 | - |
| 更新训练运行配置（Update Training Run Configuration） | aggregationAlgorithm | String | 必填/按业务规则 | - |
| 更新训练运行配置（Update Training Run Configuration） | maxRounds | Int | 必填/按业务规则 | - |
| 更新训练运行配置（Update Training Run Configuration） | minimumNodesPerRound | Int | 必填/按业务规则 | - |
| 更新训练运行配置（Update Training Run Configuration） | roundTimeoutSeconds | Int | 必填/按业务规则 | - |
| 更新训练运行配置（Update Training Run Configuration） | nodeResponseTimeoutSeconds | Int | 必填/按业务规则 | - |
| 更新训练运行配置（Update Training Run Configuration） | localEpochs | Int | 必填/按业务规则 | - |
| 更新训练运行配置（Update Training Run Configuration） | batchSize | Int | 必填/按业务规则 | - |
| 更新训练运行配置（Update Training Run Configuration） | learningRate | Decimal | 必填/按业务规则 | - |
| 更新训练运行配置（Update Training Run Configuration） | optimizer | String | 必填/按业务规则 | - |
| 更新训练运行配置（Update Training Run Configuration） | lossFunction | String | 必填/按业务规则 | - |
| 更新训练运行配置（Update Training Run Configuration） | gradientClippingNorm | Decimal | 必填/按业务规则 | - |
| 更新训练运行配置（Update Training Run Configuration） | secureAggregationRequired | Boolean | 必填/按业务规则 | - |
| 更新训练运行配置（Update Training Run Configuration） | differentialPrivacyEnabled | Boolean | 必填/按业务规则 | - |
| 更新训练运行配置（Update Training Run Configuration） | dpNoiseMultiplier | Decimal | 必填/按业务规则 | - |
| 更新训练运行配置（Update Training Run Configuration） | dpClipNorm | Decimal | 必填/按业务规则 | - |
| 更新训练运行配置（Update Training Run Configuration） | minimumAccuracy | Decimal | 必填/按业务规则 | - |
| 更新训练运行配置（Update Training Run Configuration） | minimumFairnessScore | Decimal | 必填/按业务规则 | - |
| 更新训练运行配置（Update Training Run Configuration） | failureToleranceRatio | Decimal | 必填/按业务规则 | - |
| 更新训练运行配置（Update Training Run Configuration） | updateReason | String | 必填/按业务规则 | - |

**注意事项**

- 草稿训练配置可以更新（Draft Training Configuration Can Be Updated） / 更新草稿可运行配置（Update Draft Runnable Configuration） - 当 更新训练运行配置（Update Training Run Configuration）, 则 训练运行配置已更新（Training Run Configuration Updated）.
- 草稿训练配置可以更新（Draft Training Configuration Can Be Updated） / 配置锁定后拒绝更新（Reject Update After Configuration Locked） - 当 更新训练运行配置（Update Training Run Configuration）, 则 拒绝: 训练运行配置已锁定.

<!-- em:section id="user-manual.slice.锁定训练运行配置（LockTrainingRunConfiguration）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/LockTrainingRunConfiguration" -->
#### 4.8.3 锁定训练运行配置（Lock Training Run Configuration）/FLP-DOC-SECTION-67

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 训练提交时锁定配置（Lock Configuration When Training Submitted） |
| 业务对象 | 训练运行配置（Training Run Configuration） |
| 成功结果 | 训练运行配置已锁定（Training Run Configuration Locked）, 状态: 已锁定（Locked） |

- 打开已配置的系统入口。
- 按有效业务数据提交 锁定训练运行配置（Lock Training Run Configuration）。
- 确认训练运行配置已锁定（Training Run Configuration Locked），并确认状态变为 已锁定（Locked）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 锁定训练运行配置（Lock Training Run Configuration） | trainingRunConfigurationId | UUID | 必填标识 | 派生：TrainingJobSubmitted.trainingRunConfigurationId；规则：锁定已提交训练作业事件所携带的配置。; id, technical |
| 锁定训练运行配置（Lock Training Run Configuration） | trainingJobId | UUID | 必填/按业务规则 | 来源：TrainingJobSubmitted.trainingJobId |

<!-- em:section id="user-manual.slice.训练运行配置目录（TrainingRunConfigurationCatalog）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/TrainingRunConfigurationCatalog" -->
#### 4.8.4 训练运行配置目录（Training Run Configuration Catalog）/FLP-DOC-SECTION-68

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 训练运行配置（Training Run Configuration） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 训练运行配置目录（Training Run Configuration Catalog）。
- 确认预期业务结果。
- 查看 训练运行配置目录（Training Run Configuration Catalog） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.创建训练作业（CreateTrainingJob）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/CreateTrainingJob" -->
#### 4.8.5 创建训练作业（Create Training Job）/FLP-DOC-SECTION-69

| 项目 | 说明 |
| --- | --- |
| 入口 | 训练作业创建屏幕（Training Job Creation Screen） |
| 角色/触发条件 | 研究主管（Research Lead） |
| 业务对象 | 训练作业（Training Job） |
| 成功结果 | 训练作业已创建（Training Job Created）, 状态: 草稿（Draft） |

- 打开训练作业创建屏幕（Training Job Creation Screen）。
- 按有效业务数据提交 创建训练作业（Create Training Job）。
- 确认训练作业已创建（Training Job Created），并确认状态变为 草稿（Draft）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 创建训练作业（Create Training Job） | trainingJobId | UUID | 必填标识 | id, generated, technical |
| 创建训练作业（Create Training Job） | federationId | UUID | 必填/按业务规则 | - |
| 创建训练作业（Create Training Job） | trainingRunConfigurationId | UUID | 必填/按业务规则 | - |
| 创建训练作业（Create Training Job） | objective | String | 必填/按业务规则 | - |

**注意事项**

- 训练作业需要可运行配置和运行时访问（Training Job Requires Runnable Configuration And Runtime Access） / 使用可运行配置创建训练作业（Create Training Job With Runnable Configuration） - 当 创建训练作业（Create Training Job）, 则 训练作业已创建（Training Job Created）.
- 训练作业需要可运行配置和运行时访问（Training Job Requires Runnable Configuration And Runtime Access） / 拒绝无可运行配置的训练作业（Reject Training Job Without Runnable Configuration） - 当 创建训练作业（Create Training Job）, 则 拒绝: 需要可运行训练配置.

<!-- em:section id="user-manual.slice.提交训练作业（SubmitTrainingJob）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/SubmitTrainingJob" -->
#### 4.8.6 提交训练作业（Submit Training Job）/FLP-DOC-SECTION-70

| 项目 | 说明 |
| --- | --- |
| 入口 | 训练提交屏幕（Training Submission Screen） |
| 角色/触发条件 | 研究主管（Research Lead） |
| 业务对象 | 训练作业（Training Job） |
| 成功结果 | 训练作业已提交（Training Job Submitted）, 状态: 已提交（Submitted） |

- 打开训练提交屏幕（Training Submission Screen）。
- 按有效业务数据提交 提交训练作业（Submit Training Job）。
- 确认训练作业已提交（Training Job Submitted），并确认状态变为 已提交（Submitted）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 提交训练作业（Submit Training Job） | trainingJobId | UUID | 必填标识 | id, technical |

**注意事项**

- 只有已创建的训练作业才能提交（Only Created Training Jobs Can Be Submitted） / 提交已创建的训练作业（Submit Created Training Job） - 当 提交训练作业（Submit Training Job）, 则 训练作业已提交（Training Job Submitted）.
- 只有已创建的训练作业才能提交（Only Created Training Jobs Can Be Submitted） / 拒绝未知训练作业（Reject Unknown Training Job） - 当 提交训练作业（Submit Training Job）, 则 拒绝: 训练作业为必填项.

<!-- em:section id="user-manual.slice.训练参与者资格（TrainingParticipantEligibility）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/TrainingParticipantEligibility" -->
#### 4.8.7 训练参与者资格（Training Participant Eligibility）/FLP-DOC-SECTION-71

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 训练作业（Training Job） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 训练参与者资格（Training Participant Eligibility）。
- 确认预期业务结果。
- 查看 训练参与者资格（Training Participant Eligibility） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.暂停训练作业（PauseTrainingJob）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/PauseTrainingJob" -->
#### 4.8.8 暂停训练作业（Pause Training Job）/FLP-DOC-SECTION-72

| 项目 | 说明 |
| --- | --- |
| 入口 | 训练操作屏幕（Training Operations Screen） |
| 角色/触发条件 | MLOps 工程师（MLOps Engineer） |
| 业务对象 | 训练作业（Training Job） |
| 成功结果 | 训练作业已暂停（Training Job Paused）, 状态: 已暂停（Paused） |

- 打开训练操作屏幕（Training Operations Screen）。
- 按有效业务数据提交 暂停训练作业（Pause Training Job）。
- 确认训练作业已暂停（Training Job Paused），并确认状态变为 已暂停（Paused）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 暂停训练作业（Pause Training Job） | trainingJobId | UUID | 必填标识 | id, technical |
| 暂停训练作业（Pause Training Job） | pauseReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.恢复训练作业（ResumeTrainingJob）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/ResumeTrainingJob" -->
#### 4.8.9 恢复训练作业（Resume Training Job）/FLP-DOC-SECTION-73

| 项目 | 说明 |
| --- | --- |
| 入口 | 训练操作屏幕（Training Operations Screen） |
| 角色/触发条件 | MLOps 工程师（MLOps Engineer） |
| 业务对象 | 训练作业（Training Job） |
| 成功结果 | 训练作业已恢复（Training Job Resumed）, 状态: 运行中（Running） |

- 打开训练操作屏幕（Training Operations Screen）。
- 按有效业务数据提交 恢复训练作业（Resume Training Job）。
- 确认训练作业已恢复（Training Job Resumed），并确认状态变为 运行中（Running）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 恢复训练作业（Resume Training Job） | trainingJobId | UUID | 必填标识 | id, technical |
| 恢复训练作业（Resume Training Job） | resumeReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.取消训练作业（CancelTrainingJob）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/CancelTrainingJob" -->
#### 4.8.10 取消训练作业（Cancel Training Job）/FLP-DOC-SECTION-74

| 项目 | 说明 |
| --- | --- |
| 入口 | 训练操作屏幕（Training Operations Screen） |
| 角色/触发条件 | MLOps 工程师（MLOps Engineer） |
| 业务对象 | 训练作业（Training Job） |
| 成功结果 | 训练作业已取消（Training Job Canceled）, 状态: 已取消（Canceled） |

- 打开训练操作屏幕（Training Operations Screen）。
- 按有效业务数据提交 取消训练作业（Cancel Training Job）。
- 确认训练作业已取消（Training Job Canceled），并确认状态变为 已取消（Canceled）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 取消训练作业（Cancel Training Job） | trainingJobId | UUID | 必填标识 | id, technical |
| 取消训练作业（Cancel Training Job） | cancelReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.安排下一训练轮次（ScheduleNextTrainingRound）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/ScheduleNextTrainingRound" -->
#### 4.8.11 安排下一训练轮次（Schedule Next Training Round）/FLP-DOC-SECTION-75

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 当训练应继续时选择下一轮参与者（Select Next Round Participants When Training Should Continue） |
| 业务对象 | 训练作业（Training Job） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 安排下一训练轮次（Schedule Next Training Round）。
- 确认预期业务结果。
- 在相关状态或审计视图中确认操作结果。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.完成训练作业（CompleteTrainingJob）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/CompleteTrainingJob" -->
#### 4.8.12 完成训练作业（Complete Training Job）/FLP-DOC-SECTION-76

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 轮次预算耗尽时完成作业（Complete Job When Round Budget Exhausted）, 达到全局指标时完成作业（Complete Job When Global Metric Reached） |
| 业务对象 | 训练作业（Training Job） |
| 成功结果 | 训练作业已完成（Training Job Completed）, 状态: 已完成（Completed） |

- 打开已配置的系统入口。
- 按有效业务数据提交 完成训练作业（Complete Training Job）。
- 确认训练作业已完成（Training Job Completed），并确认状态变为 已完成（Completed）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 完成训练作业（Complete Training Job） | trainingJobId | UUID | 必填标识 | id, technical |
| 完成训练作业（Complete Training Job） | finalRoundId | UUID | 必填/按业务规则 | - |
| 完成训练作业（Complete Training Job） | finalModelId | UUID | 必填/按业务规则 | - |
| 完成训练作业（Complete Training Job） | stopReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.训练作业仪表板（TrainingJobDashboard）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/TrainingJobDashboard" -->
#### 4.8.13 训练作业仪表板（Training Job Dashboard）/FLP-DOC-SECTION-77

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 训练作业（Training Job） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 训练作业仪表板（Training Job Dashboard）。
- 确认预期业务结果。
- 查看 训练作业仪表板（Training Job Dashboard） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.选择训练轮次参与者（SelectTrainingRoundParticipants）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/SelectTrainingRoundParticipants" -->
#### 4.8.14 选择训练轮次参与者（Select Training Round Participants）/FLP-DOC-SECTION-78

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 训练提交时选择初始轮次参与者（Select Initial Round Participants When Training Submitted） |
| 业务对象 | 训练轮次（Training Round） |
| 成功结果 | 训练轮次参与者已选择（Training Round Participants Selected）, 训练轮次参与者选择失败（Training Round Participant Selection Failed）, 状态: 参与者已选择（Participants Selected） |

- 打开已配置的系统入口。
- 按有效业务数据提交 选择训练轮次参与者（Select Training Round Participants）。
- 确认训练轮次参与者已选择（Training Round Participants Selected）, 训练轮次参与者选择失败（Training Round Participant Selection Failed），并确认状态变为 参与者已选择（Participants Selected）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 选择训练轮次参与者（Select Training Round Participants） | trainingJobId | UUID | 必填标识 | 来源：TrainingJobSubmitted.trainingJobId; id, technical |

**注意事项**

- 训练轮次参与者按轮快照（Training Round Participants Are Snapshotted Per Round） / 运行时池达到法定数量时选择参与者（Select Participants When Runtime Pool Reaches Quorum） - 当 选择训练轮次参与者（Select Training Round Participants）, 则 训练轮次参与者已选择（Training Round Participants Selected）.
- 训练轮次参与者按轮快照（Training Round Participants Are Snapshotted Per Round） / 拒绝低于法定数量的参与者快照（Reject Participant Snapshot Below Quorum） - 当 选择训练轮次参与者（Select Training Round Participants）, 则 训练轮次参与者选择失败（Training Round Participant Selection Failed）.

<!-- em:section id="user-manual.slice.重试训练轮次参与者选择（RetryTrainingRoundParticipantSelection）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/RetryTrainingRoundParticipantSelection" -->
#### 4.8.15 重试训练轮次参与者选择（Retry Training Round Participant Selection）/FLP-DOC-SECTION-79

| 项目 | 说明 |
| --- | --- |
| 入口 | 训练作业仪表板（Training Job Dashboard） (confirm) |
| 角色/触发条件 | 训练操作员（Training Operator） |
| 业务对象 | 训练轮次（Training Round） |
| 成功结果 | 训练轮次参与者选择重试已请求（Training Round Participant Selection Retry Requested） |

- 打开训练作业仪表板（Training Job Dashboard）。
- 按有效业务数据提交 重试训练轮次参与者选择（Retry Training Round Participant Selection）。
- 确认训练轮次参与者选择重试已请求（Training Round Participant Selection Retry Requested）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 重试训练轮次参与者选择（Retry Training Round Participant Selection） | trainingJobId | UUID | 必填标识 | id, technical |

**注意事项**

- 在就绪状态变化后重试参与者选择（Retry Participant Selection After Readiness Changes） / 重试参与者选择（Retry Participant Selection） - 当 重试训练轮次参与者选择（Retry Training Round Participant Selection）, 则 训练轮次参与者选择重试已请求（Training Round Participant Selection Retry Requested）.

<!-- em:section id="user-manual.slice.开始训练轮次（StartTrainingRound）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/StartTrainingRound" -->
#### 4.8.16 开始训练轮次（Start Training Round）/FLP-DOC-SECTION-80

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 参与者已选择时启动轮次（Start Round When Participants Selected） |
| 业务对象 | 训练轮次（Training Round） |
| 成功结果 | 训练轮次已启动（Training Round Started）, 训练轮次启动失败（Training Round Start Failed）, 状态: 运行中（Running） |

- 打开已配置的系统入口。
- 按有效业务数据提交 开始训练轮次（Start Training Round）。
- 确认训练轮次已启动（Training Round Started）, 训练轮次启动失败（Training Round Start Failed），并确认状态变为 运行中（Running）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 开始训练轮次（Start Training Round） | trainingJobId | UUID | 必填标识 | 来源：TrainingRoundParticipantsSelected.trainingJobId; id, technical |
| 开始训练轮次（Start Training Round） | trainingRunConfigurationId | UUID | 必填/按业务规则 | 来源：TrainingRoundParticipantsSelected.trainingRunConfigurationId |
| 开始训练轮次（Start Training Round） | featureSchemaId | UUID | 必填/按业务规则 | 来源：TrainingRoundParticipantsSelected.featureSchemaId |
| 开始训练轮次（Start Training Round） | roundId | UUID | 必填/按业务规则 | 来源：TrainingRoundParticipantsSelected.roundId |
| 开始训练轮次（Start Training Round） | roundNumber | Int | 必填/按业务规则 | 来源：TrainingRoundParticipantsSelected.roundNumber |
| 开始训练轮次（Start Training Round） | selectedOrganizationIds | UUID | 必填/按业务规则 | 来源：TrainingRoundParticipantsSelected.selectedOrganizationIds |
| 开始训练轮次（Start Training Round） | selectedRuntimeIds | UUID | 必填/按业务规则 | 来源：TrainingRoundParticipantsSelected.selectedRuntimeIds |
| 开始训练轮次（Start Training Round） | selectedParticipants | TrainingRoundParticipant | 必填/按业务规则 | 来源：TrainingRoundParticipantsSelected.selectedParticipants |
| 开始训练轮次（Start Training Round） | selectedOrganizationCount | Int | 必填/按业务规则 | 来源：TrainingRoundParticipantsSelected.selectedOrganizationCount |
| 开始训练轮次（Start Training Round） | selectedRuntimeCount | Int | 必填/按业务规则 | 来源：TrainingRoundParticipantsSelected.selectedRuntimeCount |
| 开始训练轮次（Start Training Round） | minimumNodesPerRound | Int | 必填/按业务规则 | 来源：TrainingRoundParticipantsSelected.minimumNodesPerRound |

**注意事项**

- 训练轮次需要已选择的运行时法定人数（Training Round Requires Selected Runtime Quorum） / 使用已选择的运行时法定人数启动轮次（Start Round With Selected Runtime Quorum） - 当 开始训练轮次（Start Training Round）, 则 训练轮次已启动（Training Round Started）.
- 训练轮次需要已选择的运行时法定人数（Training Round Requires Selected Runtime Quorum） / 记录低于法定人数的失败轮次启动（Record Failed Round Start Below Quorum） - 当 开始训练轮次（Start Training Round）, 则 训练轮次启动失败（Training Round Start Failed）.

<!-- em:section id="user-manual.slice.生成参与者执行计划（GenerateParticipantExecutionPlan）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/GenerateParticipantExecutionPlan" -->
#### 4.8.17 生成参与者执行计划（Generate Participant Execution Plan）/FLP-DOC-SECTION-81

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 为已选择的运行时生成计划（Generate Plan For Selected Runtime） |
| 业务对象 | 参与者执行计划（Participant Execution Plan） |
| 成功结果 | 参与者执行计划已生成（Participant Execution Plan Generated）, 状态: 计划已生成（Plan Generated） |

- 打开已配置的系统入口。
- 按有效业务数据提交 生成参与者执行计划（Generate Participant Execution Plan）。
- 确认参与者执行计划已生成（Participant Execution Plan Generated），并确认状态变为 计划已生成（Plan Generated）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 生成参与者执行计划（Generate Participant Execution Plan） | executionPlanId | UUID | 必填标识 | id, generated, technical |
| 生成参与者执行计划（Generate Participant Execution Plan） | executionSessionId | UUID | 必填/按业务规则 | generated, technical |
| 生成参与者执行计划（Generate Participant Execution Plan） | trainingJobId | UUID | 必填/按业务规则 | 来源：TrainingRoundStarted.trainingJobId |
| 生成参与者执行计划（Generate Participant Execution Plan） | trainingRunConfigurationId | UUID | 必填/按业务规则 | 来源：TrainingRoundStarted.trainingRunConfigurationId |
| 生成参与者执行计划（Generate Participant Execution Plan） | featureSchemaId | UUID | 必填/按业务规则 | 来源：TrainingRoundStarted.featureSchemaId |
| 生成参与者执行计划（Generate Participant Execution Plan） | roundId | UUID | 必填/按业务规则 | 来源：TrainingRoundStarted.roundId |
| 生成参与者执行计划（Generate Participant Execution Plan） | roundNumber | Int | 必填/按业务规则 | 来源：TrainingRoundStarted.roundNumber |
| 生成参与者执行计划（Generate Participant Execution Plan） | runtimeId | UUID | 必填/按业务规则 | 来源：participant.runtimeId |
| 生成参与者执行计划（Generate Participant Execution Plan） | organizationId | UUID | 必填/按业务规则 | 来源：participant.organizationId |
| 生成参与者执行计划（Generate Participant Execution Plan） | baseModelId | UUID | 必填/按业务规则 | 派生：TrainingRunConfiguration.initialModelId, TrainingRoundCompleted.aggregatedModelId；规则：第一轮使用训练配置中选择的初始模型；后续轮次使用上一已完成轮次的聚合模型。 |
| 生成参与者执行计划（Generate Participant Execution Plan） | baseModelArtifactUri | String | 必填/按业务规则 | 派生：TrainingRunConfiguration.initialModelArtifactUri, TrainingRoundCompleted.aggregatedModelArtifactUri；规则：对此参与者执行计划所使用基础模型的可拉取工件 URI 进行快照。 |
| 生成参与者执行计划（Generate Participant Execution Plan） | baseModelRegistryRef | String | 必填/按业务规则 | 派生：TrainingRunConfiguration.initialModelRegistryRef, TrainingRoundCompleted.aggregatedModelRegistryRef；规则：对拥有基础模型工件的模型注册表引用进行快照。 |
| 生成参与者执行计划（Generate Participant Execution Plan） | baseModelFormat | String | 必填/按业务规则 | 派生：TrainingRunConfiguration.initialModelFormat, TrainingRoundCompleted.modelFormat；规则：对运行时引擎所需的模型格式进行快照。 |
| 生成参与者执行计划（Generate Participant Execution Plan） | baseModelArtifactDigest | String | 必填/按业务规则 | 派生：TrainingRunConfiguration.initialModelArtifactDigest, TrainingRoundCompleted.modelArtifactDigest；规则：对运行时代理用于验证基础模型的工件摘要进行快照。 |
| 生成参与者执行计划（Generate Participant Execution Plan） | baseModelSignatureUri | String | 必填/按业务规则 | 派生：TrainingRunConfiguration.initialModelSignatureUri, TrainingRoundCompleted.aggregatedModelSignatureUri；规则：对此参与者执行计划所使用基础模型的可选签名 URI 进行快照。 |

**注意事项**

- 参与者执行计划携带基础模型工件快照（Participant Execution Plan Carries Base Model Artifact Snapshot） / 使用初始模型快照生成初始轮次计划（Generate Initial Round Plan With Initial Model Snapshot） - 当 生成参与者执行计划（Generate Participant Execution Plan）, 则 参与者执行计划已生成（Participant Execution Plan Generated）.
- 参与者执行计划携带基础模型工件快照（Participant Execution Plan Carries Base Model Artifact Snapshot） / 使用聚合模型快照生成后续轮次计划（Generate Later Round Plan With Aggregated Model Snapshot） - 当 生成参与者执行计划（Generate Participant Execution Plan）, 则 参与者执行计划已生成（Participant Execution Plan Generated）.

<!-- em:section id="user-manual.slice.分发参与者执行计划（DispatchParticipantExecutionPlan）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/DispatchParticipantExecutionPlan" -->
#### 4.8.18 分发参与者执行计划（Dispatch Participant Execution Plan）/FLP-DOC-SECTION-82

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 生成时分发计划（Dispatch Plan When Generated） |
| 业务对象 | 参与者执行计划（Participant Execution Plan） |
| 成功结果 | 参与者执行计划已分发（Participant Execution Plan Dispatched）, 状态: 计划已分发（Plan Dispatched） |

- 打开已配置的系统入口。
- 按有效业务数据提交 分发参与者执行计划（Dispatch Participant Execution Plan）。
- 确认参与者执行计划已分发（Participant Execution Plan Dispatched），并确认状态变为 计划已分发（Plan Dispatched）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 分发参与者执行计划（Dispatch Participant Execution Plan） | executionPlanId | UUID | 必填标识 | 来源：ParticipantExecutionPlanGenerated.executionPlanId; id, technical |
| 分发参与者执行计划（Dispatch Participant Execution Plan） | executionSessionId | UUID | 必填/按业务规则 | 来源：ParticipantExecutionPlanGenerated.executionSessionId |
| 分发参与者执行计划（Dispatch Participant Execution Plan） | trainingJobId | UUID | 必填/按业务规则 | 来源：ParticipantExecutionPlanGenerated.trainingJobId |
| 分发参与者执行计划（Dispatch Participant Execution Plan） | trainingRunConfigurationId | UUID | 必填/按业务规则 | 来源：ParticipantExecutionPlanGenerated.trainingRunConfigurationId |
| 分发参与者执行计划（Dispatch Participant Execution Plan） | featureSchemaId | UUID | 必填/按业务规则 | 来源：ParticipantExecutionPlanGenerated.featureSchemaId |
| 分发参与者执行计划（Dispatch Participant Execution Plan） | roundId | UUID | 必填/按业务规则 | 来源：ParticipantExecutionPlanGenerated.roundId |
| 分发参与者执行计划（Dispatch Participant Execution Plan） | roundNumber | Int | 必填/按业务规则 | 来源：ParticipantExecutionPlanGenerated.roundNumber |
| 分发参与者执行计划（Dispatch Participant Execution Plan） | runtimeId | UUID | 必填/按业务规则 | 来源：ParticipantExecutionPlanGenerated.runtimeId |
| 分发参与者执行计划（Dispatch Participant Execution Plan） | organizationId | UUID | 必填/按业务规则 | 来源：ParticipantExecutionPlanGenerated.organizationId |
| 分发参与者执行计划（Dispatch Participant Execution Plan） | baseModelId | UUID | 必填/按业务规则 | 来源：ParticipantExecutionPlanGenerated.baseModelId |
| 分发参与者执行计划（Dispatch Participant Execution Plan） | baseModelArtifactUri | String | 必填/按业务规则 | 来源：ParticipantExecutionPlanGenerated.baseModelArtifactUri |
| 分发参与者执行计划（Dispatch Participant Execution Plan） | baseModelRegistryRef | String | 必填/按业务规则 | 来源：ParticipantExecutionPlanGenerated.baseModelRegistryRef |
| 分发参与者执行计划（Dispatch Participant Execution Plan） | baseModelFormat | String | 必填/按业务规则 | 来源：ParticipantExecutionPlanGenerated.baseModelFormat |
| 分发参与者执行计划（Dispatch Participant Execution Plan） | baseModelArtifactDigest | String | 必填/按业务规则 | 来源：ParticipantExecutionPlanGenerated.baseModelArtifactDigest |
| 分发参与者执行计划（Dispatch Participant Execution Plan） | baseModelSignatureUri | String | 必填/按业务规则 | 来源：ParticipantExecutionPlanGenerated.baseModelSignatureUri |

<!-- em:section id="user-manual.slice.提交模型更新提交（SubmitModelUpdateSubmission）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/SubmitModelUpdateSubmission" -->
#### 4.8.19 提交模型更新提交（Submit Model Update Submission）/FLP-DOC-SECTION-83

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 运行时代理（Runtime Agent） |
| 业务对象 | 训练轮次（Training Round） |
| 成功结果 | 模型更新提交已接收（Model Update Submission Received）, 状态: 已接收（Received） |

- 打开已配置的系统入口。
- 按有效业务数据提交 提交模型更新提交（Submit Model Update Submission）。
- 确认模型更新提交已接收（Model Update Submission Received），并确认状态变为 已接收（Received）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 提交模型更新提交（Submit Model Update Submission） | modelUpdateSubmissionId | UUID | 必填标识 | id, generated, technical |
| 提交模型更新提交（Submit Model Update Submission） | executionSessionId | UUID | 必填/按业务规则 | 来源：AgentLocalModelUpdateSubmitted.executionSessionId |
| 提交模型更新提交（Submit Model Update Submission） | executionPlanId | UUID | 必填/按业务规则 | 来源：AgentLocalModelUpdateSubmitted.executionPlanId |
| 提交模型更新提交（Submit Model Update Submission） | trainingJobId | UUID | 必填/按业务规则 | 来源：AgentLocalModelUpdateSubmitted.trainingJobId |
| 提交模型更新提交（Submit Model Update Submission） | trainingRunConfigurationId | UUID | 必填/按业务规则 | - |
| 提交模型更新提交（Submit Model Update Submission） | roundId | UUID | 必填/按业务规则 | - |
| 提交模型更新提交（Submit Model Update Submission） | roundExecutionId | UUID | 必填/按业务规则 | 来源：AgentLocalModelUpdateSubmitted.roundExecutionId |
| 提交模型更新提交（Submit Model Update Submission） | runtimeId | UUID | 必填/按业务规则 | 来源：AgentLocalModelUpdateSubmitted.runtimeId |
| 提交模型更新提交（Submit Model Update Submission） | featureSchemaId | UUID | 必填/按业务规则 | - |
| 提交模型更新提交（Submit Model Update Submission） | localModelId | UUID | 必填/按业务规则 | - |
| 提交模型更新提交（Submit Model Update Submission） | updateArtifactId | UUID | 必填/按业务规则 | - |
| 提交模型更新提交（Submit Model Update Submission） | artifactRef | String | 必填/按业务规则 | - |
| 提交模型更新提交（Submit Model Update Submission） | artifactDigest | String | 必填/按业务规则 | - |
| 提交模型更新提交（Submit Model Update Submission） | trainingLoss | Decimal | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.评估模型更新提交（EvaluateModelUpdateSubmission）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/EvaluateModelUpdateSubmission" -->
#### 4.8.20 评估模型更新提交（Evaluate Model Update Submission）/FLP-DOC-SECTION-84

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 收到时评估模型更新（Evaluate Model Update When Received） |
| 业务对象 | 训练轮次（Training Round） |
| 成功结果 | 模型更新提交已接受（Model Update Submission Accepted）, 模型更新提交已拒绝（Model Update Submission Rejected）, 状态: 已评估（Evaluated） |

- 打开已配置的系统入口。
- 按有效业务数据提交 评估模型更新提交（Evaluate Model Update Submission）。
- 确认模型更新提交已接受（Model Update Submission Accepted）, 模型更新提交已拒绝（Model Update Submission Rejected），并确认状态变为 已评估（Evaluated）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 评估模型更新提交（Evaluate Model Update Submission） | modelUpdateSubmissionId | UUID | 必填标识 | 来源：ModelUpdateSubmissionReceived.modelUpdateSubmissionId; id, technical |
| 评估模型更新提交（Evaluate Model Update Submission） | executionSessionId | UUID | 必填/按业务规则 | 来源：ModelUpdateSubmissionReceived.executionSessionId |
| 评估模型更新提交（Evaluate Model Update Submission） | executionPlanId | UUID | 必填/按业务规则 | 来源：ModelUpdateSubmissionReceived.executionPlanId |
| 评估模型更新提交（Evaluate Model Update Submission） | trainingJobId | UUID | 必填/按业务规则 | 来源：ModelUpdateSubmissionReceived.trainingJobId |
| 评估模型更新提交（Evaluate Model Update Submission） | trainingRunConfigurationId | UUID | 必填/按业务规则 | 来源：ModelUpdateSubmissionReceived.trainingRunConfigurationId |
| 评估模型更新提交（Evaluate Model Update Submission） | roundId | UUID | 必填/按业务规则 | 来源：ModelUpdateSubmissionReceived.roundId |
| 评估模型更新提交（Evaluate Model Update Submission） | runtimeId | UUID | 必填/按业务规则 | 来源：ModelUpdateSubmissionReceived.runtimeId |
| 评估模型更新提交（Evaluate Model Update Submission） | anomalyScore | Decimal | 必填/按业务规则 | generated, technical |

**注意事项**

- 已排除受污染的本地更新（Poisoned Local Updates Are Excluded） / 拒绝异常更新（Reject Anomalous Update） - 当 评估模型更新提交（Evaluate Model Update Submission）, 则 模型更新提交已拒绝（Model Update Submission Rejected）.

<!-- em:section id="user-manual.slice.请求安全聚合（RequestSecureAggregation）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/RequestSecureAggregation" -->
#### 4.8.21 请求安全聚合（Request Secure Aggregation）/FLP-DOC-SECTION-85

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 在已评估更新完成时请求聚合（Request Aggregation When Evaluated Updates Complete） |
| 业务对象 | 训练轮次（Training Round） |
| 成功结果 | 已请求安全聚合（Secure Aggregation Requested）, 状态: 聚合中（Aggregating） |

- 打开已配置的系统入口。
- 按有效业务数据提交 请求安全聚合（Request Secure Aggregation）。
- 确认已请求安全聚合（Secure Aggregation Requested），并确认状态变为 聚合中（Aggregating）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 请求安全聚合（Request Secure Aggregation） | trainingJobId | UUID | 必填标识 | id, technical |
| 请求安全聚合（Request Secure Aggregation） | trainingRunConfigurationId | UUID | 必填/按业务规则 | - |
| 请求安全聚合（Request Secure Aggregation） | featureSchemaId | UUID | 必填/按业务规则 | - |
| 请求安全聚合（Request Secure Aggregation） | roundId | UUID | 必填/按业务规则 | - |
| 请求安全聚合（Request Secure Aggregation） | acceptedModelUpdateCount | Int | 必填/按业务规则 | - |
| 请求安全聚合（Request Secure Aggregation） | acceptedRuntimeIds | UUID | 必填/按业务规则 | 派生：ModelUpdateSubmissionAccepted.runtimeId |
| 请求安全聚合（Request Secure Aggregation） | minimumNodesPerRound | Int | 必填/按业务规则 | 派生：TrainingRoundStarted.minimumNodesPerRound |

<!-- em:section id="user-manual.slice.完成安全聚合（CompleteSecureAggregation）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/CompleteSecureAggregation" -->
#### 4.8.22 完成安全聚合（Complete Secure Aggregation）/FLP-DOC-SECTION-86

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 训练轮次（Training Round） |
| 成功结果 | 全局模型已更新（Global Model Updated）, 状态: 评估全局模型中（Evaluating Global Model） |

- 打开已配置的系统入口。
- 按有效业务数据提交 完成安全聚合（Complete Secure Aggregation）。
- 确认全局模型已更新（Global Model Updated），并确认状态变为 评估全局模型中（Evaluating Global Model）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 完成安全聚合（Complete Secure Aggregation） | trainingJobId | UUID | 必填标识 | 来源：SecureAggregationCompleted.trainingJobId; id, technical |
| 完成安全聚合（Complete Secure Aggregation） | trainingRunConfigurationId | UUID | 必填/按业务规则 | 来源：SecureAggregationCompleted.trainingRunConfigurationId |
| 完成安全聚合（Complete Secure Aggregation） | featureSchemaId | UUID | 必填/按业务规则 | 来源：SecureAggregationCompleted.featureSchemaId |
| 完成安全聚合（Complete Secure Aggregation） | roundId | UUID | 必填/按业务规则 | 来源：SecureAggregationCompleted.roundId |
| 完成安全聚合（Complete Secure Aggregation） | secureAggregationSessionId | UUID | 必填/按业务规则 | 来源：SecureAggregationCompleted.secureAggregationSessionId |
| 完成安全聚合（Complete Secure Aggregation） | aggregatedModelId | UUID | 必填/按业务规则 | 来源：SecureAggregationCompleted.aggregatedModelId |
| 完成安全聚合（Complete Secure Aggregation） | aggregatedModelArtifactUri | String | 必填/按业务规则 | 来源：SecureAggregationCompleted.aggregatedModelArtifactUri |
| 完成安全聚合（Complete Secure Aggregation） | aggregatedModelRegistryRef | String | 必填/按业务规则 | 来源：SecureAggregationCompleted.aggregatedModelRegistryRef |
| 完成安全聚合（Complete Secure Aggregation） | modelFormat | String | 必填/按业务规则 | 来源：SecureAggregationCompleted.modelFormat |
| 完成安全聚合（Complete Secure Aggregation） | modelArtifactDigest | String | 必填/按业务规则 | 来源：SecureAggregationCompleted.modelArtifactDigest |
| 完成安全聚合（Complete Secure Aggregation） | aggregatedModelSignatureUri | String | 必填/按业务规则 | 来源：SecureAggregationCompleted.aggregatedModelSignatureUri |

<!-- em:section id="user-manual.slice.提交全局模型评估（SubmitGlobalModelEvaluation）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/SubmitGlobalModelEvaluation" -->
#### 4.8.23 提交全局模型评估（Submit Global Model Evaluation）/FLP-DOC-SECTION-87

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 训练轮次（Training Round） |
| 成功结果 | 全局模型评估已提交（Global Model Evaluation Submitted） |

- 打开已配置的系统入口。
- 按有效业务数据提交 提交全局模型评估（Submit Global Model Evaluation）。
- 确认全局模型评估已提交（Global Model Evaluation Submitted）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 提交全局模型评估（Submit Global Model Evaluation） | trainingJobId | UUID | 必填标识 | id, technical |
| 提交全局模型评估（Submit Global Model Evaluation） | trainingRunConfigurationId | UUID | 必填/按业务规则 | - |
| 提交全局模型评估（Submit Global Model Evaluation） | featureSchemaId | UUID | 必填/按业务规则 | - |
| 提交全局模型评估（Submit Global Model Evaluation） | roundId | UUID | 必填/按业务规则 | - |
| 提交全局模型评估（Submit Global Model Evaluation） | aggregatedModelId | UUID | 必填/按业务规则 | - |
| 提交全局模型评估（Submit Global Model Evaluation） | aggregatedModelArtifactUri | String | 必填/按业务规则 | 来源：GlobalModelUpdated.aggregatedModelArtifactUri |
| 提交全局模型评估（Submit Global Model Evaluation） | aggregatedModelRegistryRef | String | 必填/按业务规则 | 来源：GlobalModelUpdated.aggregatedModelRegistryRef |
| 提交全局模型评估（Submit Global Model Evaluation） | modelFormat | String | 必填/按业务规则 | 来源：GlobalModelUpdated.modelFormat |
| 提交全局模型评估（Submit Global Model Evaluation） | modelArtifactDigest | String | 必填/按业务规则 | 来源：GlobalModelUpdated.modelArtifactDigest |
| 提交全局模型评估（Submit Global Model Evaluation） | aggregatedModelSignatureUri | String | 必填/按业务规则 | 来源：GlobalModelUpdated.aggregatedModelSignatureUri |
| 提交全局模型评估（Submit Global Model Evaluation） | globalAccuracy | Decimal | 必填/按业务规则 | - |
| 提交全局模型评估（Submit Global Model Evaluation） | globalFairnessScore | Decimal | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.完成训练轮次（CompleteTrainingRound）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/CompleteTrainingRound" -->
#### 4.8.24 完成训练轮次（Complete Training Round）/FLP-DOC-SECTION-88

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 全局模型评估后结束轮次（Finish Round After Global Model Evaluated） |
| 业务对象 | 训练轮次（Training Round） |
| 成功结果 | 训练轮次已完成（Training Round Completed）, 状态: 已完成（Completed） |

- 打开已配置的系统入口。
- 按有效业务数据提交 完成训练轮次（Complete Training Round）。
- 确认训练轮次已完成（Training Round Completed），并确认状态变为 已完成（Completed）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 完成训练轮次（Complete Training Round） | trainingJobId | UUID | 必填标识 | id, technical |
| 完成训练轮次（Complete Training Round） | trainingRunConfigurationId | UUID | 必填/按业务规则 | - |
| 完成训练轮次（Complete Training Round） | featureSchemaId | UUID | 必填/按业务规则 | - |
| 完成训练轮次（Complete Training Round） | roundId | UUID | 必填/按业务规则 | - |
| 完成训练轮次（Complete Training Round） | aggregatedModelId | UUID | 必填/按业务规则 | - |
| 完成训练轮次（Complete Training Round） | aggregatedModelArtifactUri | String | 必填/按业务规则 | 来源：GlobalModelEvaluationSubmitted.aggregatedModelArtifactUri |
| 完成训练轮次（Complete Training Round） | aggregatedModelRegistryRef | String | 必填/按业务规则 | 来源：GlobalModelEvaluationSubmitted.aggregatedModelRegistryRef |
| 完成训练轮次（Complete Training Round） | modelFormat | String | 必填/按业务规则 | 来源：GlobalModelEvaluationSubmitted.modelFormat |
| 完成训练轮次（Complete Training Round） | modelArtifactDigest | String | 必填/按业务规则 | 来源：GlobalModelEvaluationSubmitted.modelArtifactDigest |
| 完成训练轮次（Complete Training Round） | aggregatedModelSignatureUri | String | 必填/按业务规则 | 来源：GlobalModelEvaluationSubmitted.aggregatedModelSignatureUri |
| 完成训练轮次（Complete Training Round） | globalAccuracy | Decimal | 必填/按业务规则 | - |

**注意事项**

- 训练轮次完成需要全局评估（Training Round Completion Requires Global Evaluation） / 全局评估后完成轮次（Complete Round After Global Evaluation） - 当 完成训练轮次（Complete Training Round）, 则 训练轮次已完成（Training Round Completed）.
- 训练轮次完成需要全局评估（Training Round Completion Requires Global Evaluation） / 无全局评估则拒绝完成轮次（Reject Round Completion Without Global Evaluation） - 当 完成训练轮次（Complete Training Round）, 则 拒绝: 需要全局模型评估.

<!-- em:section id="user-manual.slice.失败训练轮次（FailTrainingRound）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/FailTrainingRound" -->
#### 4.8.25 失败训练轮次（Fail Training Round）/FLP-DOC-SECTION-89

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 聚合运行时（Aggregation Runtime） |
| 业务对象 | 训练轮次（Training Round） |
| 成功结果 | 训练轮次失败（Training Round Failed）, 状态: 已失败（Failed） |

- 打开已配置的系统入口。
- 按有效业务数据提交 失败训练轮次（Fail Training Round）。
- 确认训练轮次失败（Training Round Failed），并确认状态变为 已失败（Failed）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 失败训练轮次（Fail Training Round） | trainingJobId | UUID | 必填标识 | id, technical |
| 失败训练轮次（Fail Training Round） | trainingRunConfigurationId | UUID | 必填/按业务规则 | - |
| 失败训练轮次（Fail Training Round） | featureSchemaId | UUID | 必填/按业务规则 | - |
| 失败训练轮次（Fail Training Round） | roundId | UUID | 必填/按业务规则 | - |
| 失败训练轮次（Fail Training Round） | selectedRuntimeCount | Int | 必填/按业务规则 | - |
| 失败训练轮次（Fail Training Round） | minimumNodesPerRound | Int | 必填/按业务规则 | - |
| 失败训练轮次（Fail Training Round） | failureReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.训练轮次进度（TrainingRoundProgress）" source="domain/FederationLearningPlatform/context/TrainingOrchestration/slice/TrainingRoundProgress" -->
#### 4.8.26 训练轮次进度（Training Round Progress）/FLP-DOC-SECTION-90

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 训练轮次（Training Round） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 训练轮次进度（Training Round Progress）。
- 确认预期业务结果。
- 查看 训练轮次进度（Training Round Progress） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.context.模型生命周期（ModelLifecycle）" source="domain/FederationLearningPlatform/context/ModelLifecycle" -->
### 4.9 模型生命周期（Model Lifecycle）/FLP-DOC-SECTION-91

上下文说明: 模型生命周期从训练产生最终已评估候选之后开始。

<!-- em:section id="user-manual.slice.注册候选模型（RegisterCandidateModel）" source="domain/FederationLearningPlatform/context/ModelLifecycle/slice/RegisterCandidateModel" -->
#### 4.9.1 注册候选模型（Register Candidate Model）/FLP-DOC-SECTION-92

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 训练作业完成时注册最终模型（Register Final Model When Training Job Completed） |
| 业务对象 | 模型（Model） |
| 成功结果 | 模型候选已注册（Model Candidate Registered）, 状态: 候选（Candidate） |

- 打开已配置的系统入口。
- 按有效业务数据提交 注册候选模型（Register Candidate Model）。
- 确认模型候选已注册（Model Candidate Registered），并确认状态变为 候选（Candidate）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 注册候选模型（Register Candidate Model） | modelId | UUID | 必填标识 | id, technical |
| 注册候选模型（Register Candidate Model） | trainingJobId | UUID | 必填/按业务规则 | - |
| 注册候选模型（Register Candidate Model） | finalRoundId | UUID | 必填/按业务规则 | - |
| 注册候选模型（Register Candidate Model） | modelArtifactId | UUID | 必填/按业务规则 | - |
| 注册候选模型（Register Candidate Model） | modelArtifactDigest | String | 必填/按业务规则 | - |
| 注册候选模型（Register Candidate Model） | evaluationReportId | UUID | 必填/按业务规则 | - |
| 注册候选模型（Register Candidate Model） | finalGlobalAccuracy | Decimal | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.记录模型评估包（RecordModelEvaluationPackage）" source="domain/FederationLearningPlatform/context/ModelLifecycle/slice/RecordModelEvaluationPackage" -->
#### 4.9.2 记录模型评估包（Record Model Evaluation Package）/FLP-DOC-SECTION-93

| 项目 | 说明 |
| --- | --- |
| 入口 | 模型审批屏幕（Model Approval Screen） |
| 角色/触发条件 | 治理审核员（Governance Reviewer） |
| 业务对象 | 模型（Model） |
| 成功结果 | 模型评估包已记录（Model Evaluation Package Recorded）, 状态: 评估已打包（Evaluation Packaged） |

- 打开模型审批屏幕（Model Approval Screen）。
- 按有效业务数据提交 记录模型评估包（Record Model Evaluation Package）。
- 确认模型评估包已记录（Model Evaluation Package Recorded），并确认状态变为 评估已打包（Evaluation Packaged）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 记录模型评估包（Record Model Evaluation Package） | modelId | UUID | 必填标识 | id, technical |
| 记录模型评估包（Record Model Evaluation Package） | trainingJobId | UUID | 必填/按业务规则 | - |
| 记录模型评估包（Record Model Evaluation Package） | evaluationReportId | UUID | 必填/按业务规则 | - |
| 记录模型评估包（Record Model Evaluation Package） | experimentId | UUID | 必填/按业务规则 | - |
| 记录模型评估包（Record Model Evaluation Package） | hyperparameterSnapshotId | UUID | 必填/按业务规则 | - |
| 记录模型评估包（Record Model Evaluation Package） | reproducibilityManifestId | UUID | 必填/按业务规则 | - |
| 记录模型评估包（Record Model Evaluation Package） | modelCardId | UUID | 必填/按业务规则 | - |
| 记录模型评估包（Record Model Evaluation Package） | baselineModelId | UUID | 必填/按业务规则 | - |

**注意事项**

- 模型审批需要评估包（Model Approval Requires Evaluation Package） / 记录完整评估包（Record Complete Evaluation Package） - 当 记录模型评估包（Record Model Evaluation Package）, 则 模型评估包已记录（Model Evaluation Package Recorded）.

<!-- em:section id="user-manual.slice.批准模型（ApproveModel）" source="domain/FederationLearningPlatform/context/ModelLifecycle/slice/ApproveModel" -->
#### 4.9.3 批准模型（Approve Model）/FLP-DOC-SECTION-94

| 项目 | 说明 |
| --- | --- |
| 入口 | 模型审批屏幕（Model Approval Screen） |
| 角色/触发条件 | 治理审核员（Governance Reviewer） |
| 业务对象 | 模型（Model） |
| 成功结果 | 模型已批准（Model Approved）, 状态: 已批准（Approved） |

- 打开模型审批屏幕（Model Approval Screen）。
- 按有效业务数据提交 批准模型（Approve Model）。
- 确认模型已批准（Model Approved），并确认状态变为 已批准（Approved）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 批准模型（Approve Model） | modelId | UUID | 必填标识 | id, technical |
| 批准模型（Approve Model） | approvalNote | String | 必填/按业务规则 | - |

**注意事项**

- 只有评估打包模型才能被批准（Only Evaluation Packaged Models Can Be Approved） / 批准评估打包模型（Approve Evaluation Packaged Model） - 当 批准模型（Approve Model）, 则 模型已批准（Model Approved）.
- 只有评估打包模型才能被批准（Only Evaluation Packaged Models Can Be Approved） / 拒绝没有评估包的候选模型（Reject Candidate Without Evaluation Package） - 当 批准模型（Approve Model）, 则 拒绝: 需要模型评估包.

<!-- em:section id="user-manual.slice.提升模型至生产（PromoteModelToProduction）" source="domain/FederationLearningPlatform/context/ModelLifecycle/slice/PromoteModelToProduction" -->
#### 4.9.4 提升模型至生产（Promote Model To Production）/FLP-DOC-SECTION-95

| 项目 | 说明 |
| --- | --- |
| 入口 | 模型发布屏幕（Model Release Screen） |
| 角色/触发条件 | 发布管理员（Release Manager） |
| 业务对象 | 模型（Model） |
| 成功结果 | 模型已提升到生产环境（Model Promoted To Production）, 状态: 生产（Production） |

- 打开模型发布屏幕（Model Release Screen）。
- 按有效业务数据提交 提升模型至生产（Promote Model To Production）。
- 确认模型已提升到生产环境（Model Promoted To Production），并确认状态变为 生产（Production）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 提升模型至生产（Promote Model To Production） | modelId | UUID | 必填标识 | id, technical |
| 提升模型至生产（Promote Model To Production） | releaseChannel | String | 必填/按业务规则 | - |
| 提升模型至生产（Promote Model To Production） | productionStage | String | 必填/按业务规则 | - |

**注意事项**

- 仅已批准的模型可提升到生产环境（Only Approved Models Can Be Promoted To Production） / 提升已批准的模型（Promote Approved Model） - 当 提升模型至生产（Promote Model To Production）, 则 模型已提升到生产环境（Model Promoted To Production）.
- 仅已批准的模型可提升到生产环境（Only Approved Models Can Be Promoted To Production） / 拒绝未批准模型的提升（Reject Unapproved Model Promotion） - 当 提升模型至生产（Promote Model To Production）, 则 拒绝: 需要模型批准.

<!-- em:section id="user-manual.slice.回滚模型（RollbackModel）" source="domain/FederationLearningPlatform/context/ModelLifecycle/slice/RollbackModel" -->
#### 4.9.5 回滚模型（Rollback Model）/FLP-DOC-SECTION-96

| 项目 | 说明 |
| --- | --- |
| 入口 | 模型发布屏幕（Model Release Screen） |
| 角色/触发条件 | 发布管理员（Release Manager） |
| 业务对象 | 模型（Model） |
| 成功结果 | 模型已回滚（Model Rolled Back）, 状态: 已回滚（Rolled Back） |

- 打开模型发布屏幕（Model Release Screen）。
- 按有效业务数据提交 回滚模型（Rollback Model）。
- 确认模型已回滚（Model Rolled Back），并确认状态变为 已回滚（Rolled Back）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 回滚模型（Rollback Model） | modelId | UUID | 必填标识 | id, technical |
| 回滚模型（Rollback Model） | previousModelId | UUID | 必填/按业务规则 | - |
| 回滚模型（Rollback Model） | rollbackReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.停用模型（RetireModel）" source="domain/FederationLearningPlatform/context/ModelLifecycle/slice/RetireModel" -->
#### 4.9.6 停用模型（Retire Model）/FLP-DOC-SECTION-97

| 项目 | 说明 |
| --- | --- |
| 入口 | 模型发布屏幕（Model Release Screen） |
| 角色/触发条件 | 发布管理员（Release Manager） |
| 业务对象 | 模型（Model） |
| 成功结果 | 模型已退役（Model Retired）, 状态: 已停用（Retired） |

- 打开模型发布屏幕（Model Release Screen）。
- 按有效业务数据提交 停用模型（Retire Model）。
- 确认模型已退役（Model Retired），并确认状态变为 已停用（Retired）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 停用模型（Retire Model） | modelId | UUID | 必填标识 | id, technical |
| 停用模型（Retire Model） | retirementReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.模型目录（ModelCatalog）" source="domain/FederationLearningPlatform/context/ModelLifecycle/slice/ModelCatalog" -->
#### 4.9.7 模型目录（Model Catalog）/FLP-DOC-SECTION-98

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 模型（Model） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 模型目录（Model Catalog）。
- 确认预期业务结果。
- 查看 模型目录（Model Catalog） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.context.运行时监控（RuntimeMonitoring）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring" -->
### 4.10 运行时监控（Runtime Monitoring）/FLP-DOC-SECTION-99

上下文说明: 运行时运维将低频运行时状态变更、节点清单变更、最新遥测视图、训练告警以及仅追加审计记录区分开来。高频心跳和资源样本属于遥测，而非事件溯源领域事件。

<!-- em:section id="user-manual.slice.运行时最新遥测（RuntimeTelemetryLatest）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/RuntimeTelemetryLatest" -->
#### 4.10.1 运行时最新遥测（Runtime Telemetry Latest）/FLP-DOC-SECTION-100

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 节点运行时健康（Node Runtime Health） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 运行时最新遥测（Runtime Telemetry Latest）。
- 确认预期业务结果。
- 查看 运行时最新遥测（Runtime Telemetry Latest） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.检测运行时代理离线（DetectRuntimeAgentOffline）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/DetectRuntimeAgentOffline" -->
#### 4.10.2 检测运行时代理离线（Detect Runtime Agent Offline）/FLP-DOC-SECTION-101

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 从遥测检测离线运行时代理（Detect Offline Runtime Agent From Telemetry） |
| 业务对象 | 节点运行时健康（Node Runtime Health） |
| 成功结果 | 已检测到运行时代理离线（Runtime Agent Offline Detected）, 状态: 离线（Offline） |

- 打开已配置的系统入口。
- 按有效业务数据提交 检测运行时代理离线（Detect Runtime Agent Offline）。
- 确认已检测到运行时代理离线（Runtime Agent Offline Detected），并确认状态变为 离线（Offline）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 检测运行时代理离线（Detect Runtime Agent Offline） | nodeId | UUID | 必填标识 | id, technical |
| 检测运行时代理离线（Detect Runtime Agent Offline） | runtimeAgentId | UUID | 必填/按业务规则 | - |
| 检测运行时代理离线（Detect Runtime Agent Offline） | offlineReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.标记运行时代理已恢复（MarkRuntimeAgentRecovered）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/MarkRuntimeAgentRecovered" -->
#### 4.10.3 标记运行时代理已恢复（Mark Runtime Agent Recovered）/FLP-DOC-SECTION-102

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 从遥测标记运行时代理已恢复（Mark Recovered Runtime Agent From Telemetry） |
| 业务对象 | 节点运行时健康（Node Runtime Health） |
| 成功结果 | 运行时代理已恢复（Runtime Agent Recovered）, 状态: 健康（Healthy） |

- 打开已配置的系统入口。
- 按有效业务数据提交 标记运行时代理已恢复（Mark Runtime Agent Recovered）。
- 确认运行时代理已恢复（Runtime Agent Recovered），并确认状态变为 健康（Healthy）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 标记运行时代理已恢复（Mark Runtime Agent Recovered） | nodeId | UUID | 必填标识 | id, technical |
| 标记运行时代理已恢复（Mark Runtime Agent Recovered） | runtimeAgentId | UUID | 必填/按业务规则 | - |
| 标记运行时代理已恢复（Mark Runtime Agent Recovered） | recoveryReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.检测运行时节点资源压力（DetectRuntimeNodeResourcePressure）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/DetectRuntimeNodeResourcePressure" -->
#### 4.10.4 检测运行时节点资源压力（Detect Runtime Node Resource Pressure）/FLP-DOC-SECTION-103

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 从遥测检测资源压力（Detect Resource Pressure From Telemetry） |
| 业务对象 | 运行时节点资源压力（Runtime Node Resource Pressure） |
| 成功结果 | 已检测到运行时节点资源压力（Runtime Node Resource Pressure Detected）, 状态: 已检测到压力（Pressure Detected） |

- 打开已配置的系统入口。
- 按有效业务数据提交 检测运行时节点资源压力（Detect Runtime Node Resource Pressure）。
- 确认已检测到运行时节点资源压力（Runtime Node Resource Pressure Detected），并确认状态变为 已检测到压力（Pressure Detected）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 检测运行时节点资源压力（Detect Runtime Node Resource Pressure） | nodeId | UUID | 必填标识 | id, technical |
| 检测运行时节点资源压力（Detect Runtime Node Resource Pressure） | runtimeAgentId | UUID | 必填/按业务规则 | - |
| 检测运行时节点资源压力（Detect Runtime Node Resource Pressure） | trainingJobId | UUID | 必填/按业务规则 | - |
| 检测运行时节点资源压力（Detect Runtime Node Resource Pressure） | pressureType | String | 必填/按业务规则 | - |
| 检测运行时节点资源压力（Detect Runtime Node Resource Pressure） | observedValue | Decimal | 必填/按业务规则 | - |
| 检测运行时节点资源压力（Detect Runtime Node Resource Pressure） | thresholdValue | Decimal | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.记录运行时节点清单（RecordRuntimeNodeInventory）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/RecordRuntimeNodeInventory" -->
#### 4.10.5 记录运行时节点清单（Record Runtime Node Inventory）/FLP-DOC-SECTION-104

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 代理报告时记录运行时节点清单（Record Runtime Node Inventory When Agent Reported） |
| 业务对象 | 运行时节点清单（Runtime Node Inventory） |
| 成功结果 | 运行时节点清单已报告（Runtime Node Inventory Reported）, 状态: 已报告（Reported） |

- 打开已配置的系统入口。
- 按有效业务数据提交 记录运行时节点清单（Record Runtime Node Inventory）。
- 确认运行时节点清单已报告（Runtime Node Inventory Reported），并确认状态变为 已报告（Reported）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 记录运行时节点清单（Record Runtime Node Inventory） | nodeId | UUID | 必填标识 | id, technical |
| 记录运行时节点清单（Record Runtime Node Inventory） | runtimeNodeInventoryReportId | UUID | 必填/按业务规则 | - |
| 记录运行时节点清单（Record Runtime Node Inventory） | organizationId | UUID | 必填/按业务规则 | - |
| 记录运行时节点清单（Record Runtime Node Inventory） | runtimeInfrastructureId | UUID | 必填/按业务规则 | - |
| 记录运行时节点清单（Record Runtime Node Inventory） | runtimeAgentId | UUID | 必填/按业务规则 | - |
| 记录运行时节点清单（Record Runtime Node Inventory） | runtimeNodeName | String | 必填/按业务规则 | - |
| 记录运行时节点清单（Record Runtime Node Inventory） | infrastructureNodeId | String | 必填/按业务规则 | - |
| 记录运行时节点清单（Record Runtime Node Inventory） | runtimeNodeRole | String | 必填/按业务规则 | - |
| 记录运行时节点清单（Record Runtime Node Inventory） | nodeReady | Boolean | 必填/按业务规则 | - |
| 记录运行时节点清单（Record Runtime Node Inventory） | runtimeEngineVersion | String | 必填/按业务规则 | - |
| 记录运行时节点清单（Record Runtime Node Inventory） | containerEngineVersion | String | 必填/按业务规则 | - |
| 记录运行时节点清单（Record Runtime Node Inventory） | operatingSystem | String | 必填/按业务规则 | - |
| 记录运行时节点清单（Record Runtime Node Inventory） | architecture | String | 必填/按业务规则 | - |
| 记录运行时节点清单（Record Runtime Node Inventory） | inventoryHash | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.运行时节点清单视图（RuntimeNodeInventoryView）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/RuntimeNodeInventoryView" -->
#### 4.10.6 运行时节点清单视图（Runtime Node Inventory View）/FLP-DOC-SECTION-105

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 运行时节点清单（Runtime Node Inventory） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 运行时节点清单视图（Runtime Node Inventory View）。
- 确认预期业务结果。
- 查看 运行时节点清单视图（Runtime Node Inventory View） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.运行时节点最新资源（RuntimeNodeResourceLatest）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/RuntimeNodeResourceLatest" -->
#### 4.10.7 运行时节点最新资源（Runtime Node Resource Latest）/FLP-DOC-SECTION-106

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 运行时节点资源遥测（Runtime Node Resource Telemetry） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 运行时节点最新资源（Runtime Node Resource Latest）。
- 确认预期业务结果。
- 查看 运行时节点最新资源（Runtime Node Resource Latest） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.检测运行时节点容量变更（DetectRuntimeNodeCapacityChange）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/DetectRuntimeNodeCapacityChange" -->
#### 4.10.8 检测运行时节点容量变更（Detect Runtime Node Capacity Change）/FLP-DOC-SECTION-107

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 从资源遥测检测容量变化（Detect Capacity Change From Resource Telemetry） |
| 业务对象 | 运行时节点容量（Runtime Node Capacity） |
| 成功结果 | 运行时节点容量已更改（Runtime Node Capacity Changed）, 状态: 容量已变更（Capacity Changed） |

- 打开已配置的系统入口。
- 按有效业务数据提交 检测运行时节点容量变更（Detect Runtime Node Capacity Change）。
- 确认运行时节点容量已更改（Runtime Node Capacity Changed），并确认状态变为 容量已变更（Capacity Changed）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 检测运行时节点容量变更（Detect Runtime Node Capacity Change） | nodeId | UUID | 必填标识 | id, technical |
| 检测运行时节点容量变更（Detect Runtime Node Capacity Change） | runtimeAgentId | UUID | 必填/按业务规则 | - |
| 检测运行时节点容量变更（Detect Runtime Node Capacity Change） | previousCapacityHash | String | 必填/按业务规则 | - |
| 检测运行时节点容量变更（Detect Runtime Node Capacity Change） | currentCapacityHash | String | 必填/按业务规则 | - |
| 检测运行时节点容量变更（Detect Runtime Node Capacity Change） | allocatableCpuCores | Int | 必填/按业务规则 | - |
| 检测运行时节点容量变更（Detect Runtime Node Capacity Change） | allocatableMemoryGb | Int | 必填/按业务规则 | - |
| 检测运行时节点容量变更（Detect Runtime Node Capacity Change） | allocatableGpuCount | Int | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.运行时健康仪表板（RuntimeHealthDashboard）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/RuntimeHealthDashboard" -->
#### 4.10.9 运行时健康仪表板（Runtime Health Dashboard）/FLP-DOC-SECTION-108

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 节点运行时健康（Node Runtime Health） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 运行时健康仪表板（Runtime Health Dashboard）。
- 确认预期业务结果。
- 查看 运行时健康仪表板（Runtime Health Dashboard） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.引发训练告警（RaiseTrainingAlert）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/RaiseTrainingAlert" -->
#### 4.10.10 引发训练告警（Raise Training Alert）/FLP-DOC-SECTION-109

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 在节点资源压力时发出警报（Raise Alert On Node Resource Pressure） |
| 业务对象 | 训练告警（Training Alert） |
| 成功结果 | 训练警报已发出（Training Alert Raised）, 状态: 已引发（Raised） |

- 打开已配置的系统入口。
- 按有效业务数据提交 引发训练告警（Raise Training Alert）。
- 确认训练警报已发出（Training Alert Raised），并确认状态变为 已引发（Raised）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 引发训练告警（Raise Training Alert） | alertId | UUID | 必填标识 | id, generated, technical |
| 引发训练告警（Raise Training Alert） | nodeId | UUID | 必填/按业务规则 | 来源：RuntimeNodeResourcePressureDetected.nodeId |
| 引发训练告警（Raise Training Alert） | trainingJobId | UUID | 必填/按业务规则 | 来源：RuntimeNodeResourcePressureDetected.trainingJobId |
| 引发训练告警（Raise Training Alert） | severity | String | 必填/按业务规则 | - |
| 引发训练告警（Raise Training Alert） | message | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.训练告警目录（TrainingAlertCatalog）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/TrainingAlertCatalog" -->
#### 4.10.11 训练告警目录（Training Alert Catalog）/FLP-DOC-SECTION-110

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 训练告警（Training Alert） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 训练告警目录（Training Alert Catalog）。
- 确认预期业务结果。
- 查看 训练告警目录（Training Alert Catalog） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.确认训练告警（AcknowledgeTrainingAlert）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/AcknowledgeTrainingAlert" -->
#### 4.10.12 确认训练告警（Acknowledge Training Alert）/FLP-DOC-SECTION-111

| 项目 | 说明 |
| --- | --- |
| 入口 | 运行时健康仪表板（Runtime Health Dashboard） |
| 角色/触发条件 | MLOps 工程师（MLOps Engineer） |
| 业务对象 | 训练告警（Training Alert） |
| 成功结果 | 训练警报已确认（Training Alert Acknowledged）, 状态: 已确认（Acknowledged） |

- 打开运行时健康仪表板（Runtime Health Dashboard）。
- 按有效业务数据提交 确认训练告警（Acknowledge Training Alert）。
- 确认训练警报已确认（Training Alert Acknowledged），并确认状态变为 已确认（Acknowledged）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 确认训练告警（Acknowledge Training Alert） | alertId | UUID | 必填标识 | id, technical |
| 确认训练告警（Acknowledge Training Alert） | acknowledgementNote | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.解决训练告警（ResolveTrainingAlert）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/ResolveTrainingAlert" -->
#### 4.10.13 解决训练告警（Resolve Training Alert）/FLP-DOC-SECTION-112

| 项目 | 说明 |
| --- | --- |
| 入口 | 运行时健康仪表板（Runtime Health Dashboard） |
| 角色/触发条件 | MLOps 工程师（MLOps Engineer） |
| 业务对象 | 训练告警（Training Alert） |
| 成功结果 | 训练警报已解决（Training Alert Resolved）, 状态: 已解决（Resolved） |

- 打开运行时健康仪表板（Runtime Health Dashboard）。
- 按有效业务数据提交 解决训练告警（Resolve Training Alert）。
- 确认训练警报已解决（Training Alert Resolved），并确认状态变为 已解决（Resolved）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 解决训练告警（Resolve Training Alert） | alertId | UUID | 必填标识 | id, technical |
| 解决训练告警（Resolve Training Alert） | resolutionSummary | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.追加审计跟踪（AppendAuditTrail）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/AppendAuditTrail" -->
#### 4.10.14 追加审计跟踪（Append Audit Trail）/FLP-DOC-SECTION-113

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 审计关键训练事件（Audit Critical Training Events）, 审计联邦已激活（Audit Federation Activated）, 审计联邦已暂停（Audit Federation Suspended）, 审计联邦已重新激活（Audit Federation Reactivated）, 审计参与者已加入（Audit Participant Joined）, 审计参与者已暂停（Audit Participant Suspended）, 审计数据集批准（Audit Dataset Approval）, 审计数据集批准已撤销（Audit Dataset Approval Revoked）, 审计训练作业已提交（Audit Training Job Submitted）, 审计模型已提升至生产（Audit Model Promoted To Production） |
| 业务对象 | 审计记录（Audit Record） |
| 成功结果 | 审计跟踪已追加（Audit Trail Appended）, 状态: 已追加（Appended） |

- 打开已配置的系统入口。
- 按有效业务数据提交 追加审计跟踪（Append Audit Trail）。
- 确认审计跟踪已追加（Audit Trail Appended），并确认状态变为 已追加（Appended）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 追加审计跟踪（Append Audit Trail） | auditRecordId | UUID | 必填标识 | id, generated, technical |
| 追加审计跟踪（Append Audit Trail） | sourceEventName | String | 必填/按业务规则 | - |
| 追加审计跟踪（Append Audit Trail） | sourceEntityId | UUID | 必填/按业务规则 | - |
| 追加审计跟踪（Append Audit Trail） | severity | String | 必填/按业务规则 | - |
| 追加审计跟踪（Append Audit Trail） | payloadHash | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.审计记录日志（AuditRecordLog）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring/slice/AuditRecordLog" -->
#### 4.10.15 审计记录日志（Audit Record Log）/FLP-DOC-SECTION-114

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 审计记录（Audit Record） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 审计记录日志（Audit Record Log）。
- 确认预期业务结果。
- 查看 审计记录日志（Audit Record Log） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.context.运行时治理（RuntimeGovernance）" source="domain/FederationLearningPlatform/context/RuntimeGovernance" -->
### 4.11 运行时治理（Runtime Governance）/FLP-DOC-SECTION-115

上下文说明: 运行时治理负责稳定的 RuntimeIdentity 激活、能力检测和撤销。运行时私钥、本地路径、凭据、节点调度和数据平面连接保留在参与者运行时基础设施内部。 平台管理的运行时代理根据平台部署结果激活。网络可达性不等于业务授权。训练资格使用活跃的联邦成员资格、RuntimeIdentity、已检测到的能力、已连接的运行时状态以及数据集就绪情况。 训练参数加密、安全聚合密钥和数据平面消息加密由协议适配器或 SecureAggregation 处理，不由 RuntimeIdentity 事件处理。

<!-- em:section id="user-manual.slice.激活运行时标识（ActivateRuntimeIdentity）" source="domain/FederationLearningPlatform/context/RuntimeGovernance/slice/ActivateRuntimeIdentity" -->
#### 4.11.1 激活运行时标识（Activate Runtime Identity）/FLP-DOC-SECTION-116

| 项目 | 说明 |
| --- | --- |
| 入口 | 运行时安装计划页面（Runtime Installation Plan Screen） |
| 角色/触发条件 | 平台管理员（Platform Admin） |
| 业务对象 | 运行时标识（Runtime Identity） |
| 成功结果 | 运行时身份已激活（Runtime Identity Activated）, 状态: 已激活（Active） |

- 打开运行时安装计划页面（Runtime Installation Plan Screen）。
- 按有效业务数据提交 激活运行时标识（Activate Runtime Identity）。
- 确认运行时身份已激活（Runtime Identity Activated），并确认状态变为 已激活（Active）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 激活运行时标识（Activate Runtime Identity） | runtimeId | UUID | 必填标识 | id, generated, technical |
| 激活运行时标识（Activate Runtime Identity） | runtimeInfrastructureId | UUID | 必填/按业务规则 | - |
| 激活运行时标识（Activate Runtime Identity） | runtimeAgentId | UUID | 必填/按业务规则 | - |
| 激活运行时标识（Activate Runtime Identity） | organizationId | UUID | 必填/按业务规则 | 派生：RuntimeInstallationPlanCreated.organizationId |
| 激活运行时标识（Activate Runtime Identity） | runtimeName | String | 必填/按业务规则 | 派生：RuntimeInstallationPlanCreated.runtimeName |

<!-- em:section id="user-manual.slice.撤销运行时标识（RevokeRuntimeIdentity）" source="domain/FederationLearningPlatform/context/RuntimeGovernance/slice/RevokeRuntimeIdentity" -->
#### 4.11.2 撤销运行时标识（Revoke Runtime Identity）/FLP-DOC-SECTION-117

| 项目 | 说明 |
| --- | --- |
| 入口 | 运行时身份屏幕（Runtime Identity Screen） (confirm) |
| 角色/触发条件 | 安全审核员（Security Reviewer） |
| 业务对象 | 运行时标识（Runtime Identity） |
| 成功结果 | 运行时身份已撤销（Runtime Identity Revoked）, 状态: 已撤销（Revoked） |

- 打开运行时身份屏幕（Runtime Identity Screen）。
- 按有效业务数据提交 撤销运行时标识（Revoke Runtime Identity）。
- 确认运行时身份已撤销（Runtime Identity Revoked），并确认状态变为 已撤销（Revoked）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 撤销运行时标识（Revoke Runtime Identity） | runtimeId | UUID | 必填标识 | id, technical |
| 撤销运行时标识（Revoke Runtime Identity） | revocationReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.检测运行时能力（DetectRuntimeCapabilities）" source="domain/FederationLearningPlatform/context/RuntimeGovernance/slice/DetectRuntimeCapabilities" -->
#### 4.11.3 检测运行时能力（Detect Runtime Capabilities）/FLP-DOC-SECTION-118

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 运行时代理（Runtime Agent） |
| 业务对象 | 运行时能力（Runtime Capability） |
| 成功结果 | 运行时能力已检测（Runtime Capabilities Detected）, 状态: 已检测到（Detected） |

- 打开已配置的系统入口。
- 按有效业务数据提交 检测运行时能力（Detect Runtime Capabilities）。
- 确认运行时能力已检测（Runtime Capabilities Detected），并确认状态变为 已检测到（Detected）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 检测运行时能力（Detect Runtime Capabilities） | runtimeId | UUID | 必填标识 | 来源：RuntimeIdentityActivated.runtimeId; id, technical |
| 检测运行时能力（Detect Runtime Capabilities） | capabilityTypes | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.运行时标识目录（RuntimeIdentityCatalog）" source="domain/FederationLearningPlatform/context/RuntimeGovernance/slice/RuntimeIdentityCatalog" -->
#### 4.11.4 运行时标识目录（Runtime Identity Catalog）/FLP-DOC-SECTION-119

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 运行时标识（Runtime Identity） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 运行时标识目录（Runtime Identity Catalog）。
- 确认预期业务结果。
- 查看 运行时标识目录（Runtime Identity Catalog） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.运行时能力目录（RuntimeCapabilityCatalog）" source="domain/FederationLearningPlatform/context/RuntimeGovernance/slice/RuntimeCapabilityCatalog" -->
#### 4.11.5 运行时能力目录（Runtime Capability Catalog）/FLP-DOC-SECTION-120

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 运行时能力（Runtime Capability） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 运行时能力目录（Runtime Capability Catalog）。
- 确认预期业务结果。
- 查看 运行时能力目录（Runtime Capability Catalog） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.context.安全聚合（SecureAggregation）" source="domain/FederationLearningPlatform/context/SecureAggregation" -->
### 4.12 安全聚合（Secure Aggregation）/FLP-DOC-SECTION-121

上下文说明: 安全聚合拥有基于同态加密的聚合会话。平台持有私钥，仅向运行时代理发布加密上下文元数据；原始密钥材料、公钥文档和存储位置保留在 SecureAggregationService 或 KMS 适配器配置中。

<!-- em:section id="user-manual.slice.创建安全聚合会话（CreateSecureAggregationSession）" source="domain/FederationLearningPlatform/context/SecureAggregation/slice/CreateSecureAggregationSession" -->
#### 4.12.1 创建安全聚合会话（Create Secure Aggregation Session）/FLP-DOC-SECTION-122

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 在请求聚合时创建会话（Create Session When Aggregation Requested） |
| 业务对象 | 安全聚合会话（Secure Aggregation Session） |
| 成功结果 | 安全聚合会话已创建（Secure Aggregation Session Created）, 状态: 已规划（Planned） |

- 打开已配置的系统入口。
- 按有效业务数据提交 创建安全聚合会话（Create Secure Aggregation Session）。
- 确认安全聚合会话已创建（Secure Aggregation Session Created），并确认状态变为 已规划（Planned）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 创建安全聚合会话（Create Secure Aggregation Session） | secureAggregationSessionId | UUID | 必填标识 | id, generated, technical |
| 创建安全聚合会话（Create Secure Aggregation Session） | trainingJobId | UUID | 必填/按业务规则 | 来源：SecureAggregationRequested.trainingJobId |
| 创建安全聚合会话（Create Secure Aggregation Session） | trainingRunConfigurationId | UUID | 必填/按业务规则 | 来源：SecureAggregationRequested.trainingRunConfigurationId |
| 创建安全聚合会话（Create Secure Aggregation Session） | featureSchemaId | UUID | 必填/按业务规则 | 来源：SecureAggregationRequested.featureSchemaId |
| 创建安全聚合会话（Create Secure Aggregation Session） | roundId | UUID | 必填/按业务规则 | 来源：SecureAggregationRequested.roundId |
| 创建安全聚合会话（Create Secure Aggregation Session） | requiredParticipantCount | Int | 必填/按业务规则 | 来源：SecureAggregationRequested.acceptedModelUpdateCount |
| 创建安全聚合会话（Create Secure Aggregation Session） | acceptedRuntimeIds | UUID | 必填/按业务规则 | 来源：SecureAggregationRequested.acceptedRuntimeIds |

<!-- em:section id="user-manual.slice.选择安全聚合参与者（SelectSecureAggregationParticipants）" source="domain/FederationLearningPlatform/context/SecureAggregation/slice/SelectSecureAggregationParticipants" -->
#### 4.12.2 选择安全聚合参与者（Select Secure Aggregation Participants）/FLP-DOC-SECTION-123

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 会话创建时选择参与者（Select Participants When Session Created） |
| 业务对象 | 安全聚合会话（Secure Aggregation Session） |
| 成功结果 | 安全聚合参与者已选择（Secure Aggregation Participants Selected）, 状态: 参与者已选择（Participants Selected） |

- 打开已配置的系统入口。
- 按有效业务数据提交 选择安全聚合参与者（Select Secure Aggregation Participants）。
- 确认安全聚合参与者已选择（Secure Aggregation Participants Selected），并确认状态变为 参与者已选择（Participants Selected）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 选择安全聚合参与者（Select Secure Aggregation Participants） | secureAggregationSessionId | UUID | 必填标识 | id, technical |
| 选择安全聚合参与者（Select Secure Aggregation Participants） | roundId | UUID | 必填/按业务规则 | 来源：SecureAggregationSessionCreated.roundId |
| 选择安全聚合参与者（Select Secure Aggregation Participants） | acceptedRuntimeIds | UUID | 必填/按业务规则 | 来源：SecureAggregationSessionCreated.acceptedRuntimeIds |
| 选择安全聚合参与者（Select Secure Aggregation Participants） | selectedRuntimeIds | UUID | 必填/按业务规则 | 来源：SecureAggregationSessionCreated.acceptedRuntimeIds |
| 选择安全聚合参与者（Select Secure Aggregation Participants） | selectedParticipantCount | Int | 必填/按业务规则 | 来源：SecureAggregationSessionCreated.requiredParticipantCount |

<!-- em:section id="user-manual.slice.准备同态加密上下文（PrepareHomomorphicEncryptionContext）" source="domain/FederationLearningPlatform/context/SecureAggregation/slice/PrepareHomomorphicEncryptionContext" -->
#### 4.12.3 准备同态加密上下文（Prepare Homomorphic Encryption Context）/FLP-DOC-SECTION-124

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 安全聚合服务（Secure Aggregation Service） |
| 业务对象 | 安全聚合会话（Secure Aggregation Session） |
| 成功结果 | 同态加密上下文已准备（Homomorphic Encryption Context Prepared）, 状态: 已准备加密上下文（Encryption Context Prepared） |

- 打开已配置的系统入口。
- 按有效业务数据提交 准备同态加密上下文（Prepare Homomorphic Encryption Context）。
- 确认同态加密上下文已准备（Homomorphic Encryption Context Prepared），并确认状态变为 已准备加密上下文（Encryption Context Prepared）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 准备同态加密上下文（Prepare Homomorphic Encryption Context） | secureAggregationSessionId | UUID | 必填标识 | id, technical |
| 准备同态加密上下文（Prepare Homomorphic Encryption Context） | encryptionScheme | String | 必填/按业务规则 | - |
| 准备同态加密上下文（Prepare Homomorphic Encryption Context） | publicKeyVersion | String | 必填/按业务规则 | - |
| 准备同态加密上下文（Prepare Homomorphic Encryption Context） | encryptedParameterScale | Int | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.记录加密模型更新（RecordEncryptedModelUpdate）" source="domain/FederationLearningPlatform/context/SecureAggregation/slice/RecordEncryptedModelUpdate" -->
#### 4.12.4 记录加密模型更新（Record Encrypted Model Update）/FLP-DOC-SECTION-125

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 安全聚合服务（Secure Aggregation Service） |
| 业务对象 | 安全聚合会话（Secure Aggregation Session） |
| 成功结果 | 已接收加密模型更新（Encrypted Model Update Received） |

- 打开已配置的系统入口。
- 按有效业务数据提交 记录加密模型更新（Record Encrypted Model Update）。
- 确认已接收加密模型更新（Encrypted Model Update Received）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 记录加密模型更新（Record Encrypted Model Update） | secureAggregationSessionId | UUID | 必填标识 | id, technical |
| 记录加密模型更新（Record Encrypted Model Update） | submissionId | UUID | 必填/按业务规则 | - |
| 记录加密模型更新（Record Encrypted Model Update） | runtimeId | UUID | 必填/按业务规则 | - |
| 记录加密模型更新（Record Encrypted Model Update） | encryptedUpdateDigest | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.完成同态聚合会话（CompleteHomomorphicAggregationSession）" source="domain/FederationLearningPlatform/context/SecureAggregation/slice/CompleteHomomorphicAggregationSession" -->
#### 4.12.5 完成同态聚合会话（Complete Homomorphic Aggregation Session）/FLP-DOC-SECTION-126

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 安全聚合服务（Secure Aggregation Service） |
| 业务对象 | 安全聚合会话（Secure Aggregation Session） |
| 成功结果 | 安全聚合已完成（Secure Aggregation Completed）, 状态: 已完成（Completed） |

- 打开已配置的系统入口。
- 按有效业务数据提交 完成同态聚合会话（Complete Homomorphic Aggregation Session）。
- 确认安全聚合已完成（Secure Aggregation Completed），并确认状态变为 已完成（Completed）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 完成同态聚合会话（Complete Homomorphic Aggregation Session） | secureAggregationSessionId | UUID | 必填标识 | id, technical |
| 完成同态聚合会话（Complete Homomorphic Aggregation Session） | trainingJobId | UUID | 必填/按业务规则 | 来源：SecureAggregationSessionCreated.trainingJobId |
| 完成同态聚合会话（Complete Homomorphic Aggregation Session） | trainingRunConfigurationId | UUID | 必填/按业务规则 | 来源：SecureAggregationSessionCreated.trainingRunConfigurationId |
| 完成同态聚合会话（Complete Homomorphic Aggregation Session） | featureSchemaId | UUID | 必填/按业务规则 | 来源：SecureAggregationSessionCreated.featureSchemaId |
| 完成同态聚合会话（Complete Homomorphic Aggregation Session） | roundId | UUID | 必填/按业务规则 | 来源：SecureAggregationSessionCreated.roundId |
| 完成同态聚合会话（Complete Homomorphic Aggregation Session） | aggregatedModelId | UUID | 必填/按业务规则 | generated, technical |
| 完成同态聚合会话（Complete Homomorphic Aggregation Session） | aggregatedModelArtifactUri | String | 必填/按业务规则 | - |
| 完成同态聚合会话（Complete Homomorphic Aggregation Session） | aggregatedModelRegistryRef | String | 必填/按业务规则 | - |
| 完成同态聚合会话（Complete Homomorphic Aggregation Session） | modelFormat | String | 必填/按业务规则 | - |
| 完成同态聚合会话（Complete Homomorphic Aggregation Session） | modelArtifactDigest | String | 必填/按业务规则 | - |
| 完成同态聚合会话（Complete Homomorphic Aggregation Session） | aggregatedModelSignatureUri | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.失败安全聚合会话（FailSecureAggregationSession）" source="domain/FederationLearningPlatform/context/SecureAggregation/slice/FailSecureAggregationSession" -->
#### 4.12.6 失败安全聚合会话（Fail Secure Aggregation Session）/FLP-DOC-SECTION-127

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 安全聚合服务（Secure Aggregation Service） |
| 业务对象 | 安全聚合会话（Secure Aggregation Session） |
| 成功结果 | 安全聚合失败（Secure Aggregation Failed）, 状态: 已失败（Failed） |

- 打开已配置的系统入口。
- 按有效业务数据提交 失败安全聚合会话（Fail Secure Aggregation Session）。
- 确认安全聚合失败（Secure Aggregation Failed），并确认状态变为 已失败（Failed）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 失败安全聚合会话（Fail Secure Aggregation Session） | secureAggregationSessionId | UUID | 必填标识 | id, technical |
| 失败安全聚合会话（Fail Secure Aggregation Session） | failureReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.安全聚合会话目录（SecureAggregationSessionCatalog）" source="domain/FederationLearningPlatform/context/SecureAggregation/slice/SecureAggregationSessionCatalog" -->
#### 4.12.7 安全聚合会话目录（Secure Aggregation Session Catalog）/FLP-DOC-SECTION-128

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 安全聚合会话（Secure Aggregation Session） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 安全聚合会话目录（Secure Aggregation Session Catalog）。
- 确认预期业务结果。
- 查看 安全聚合会话目录（Secure Aggregation Session Catalog） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.context.运行时代理操作（RuntimeAgentOperations）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations" -->
### 4.13 运行时代理操作（Runtime Agent Operations）/FLP-DOC-SECTION-129

上下文说明: 运行时代理操作是一个独立的可部署组件，在参与者基础设施内运行，并使用本地数据和本地机密执行分派的训练作业。

<!-- em:section id="user-manual.slice.声明数据集（DeclareDataset）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/DeclareDataset" -->
#### 4.13.1 声明数据集（Declare Dataset）/FLP-DOC-SECTION-130

| 项目 | 说明 |
| --- | --- |
| 入口 | 数据集注册屏幕（Dataset Registration Screen） |
| 角色/触发条件 | 数据所有者（Data Owner） |
| 业务对象 | 数据集（Dataset） |
| 成功结果 | 数据集已声明（Dataset Declared）, 状态: 已注册（Registered） |

- 打开数据集注册屏幕（Dataset Registration Screen）。
- 按有效业务数据提交 声明数据集（Declare Dataset）。
- 确认数据集已声明（Dataset Declared），并确认状态变为 已注册（Registered）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 声明数据集（Declare Dataset） | datasetId | UUID | 必填标识 | id, generated, technical |
| 声明数据集（Declare Dataset） | organizationId | UUID | 必填/按业务规则 | - |
| 声明数据集（Declare Dataset） | featureSchemaId | UUID | 必填/按业务规则 | - |
| 声明数据集（Declare Dataset） | datasetName | String | 必填/按业务规则 | - |
| 声明数据集（Declare Dataset） | datasetType | String | 必填/按业务规则 | - |
| 声明数据集（Declare Dataset） | datasetUsage | String | 必填/按业务规则 | - |

**注意事项**

- 数据集声明需要特征架构快照（Feature Schema Snapshot Required For Dataset Declaration） / 使用特征架构快照声明数据集（Declare Dataset With Feature Schema Snapshot） - 当 声明数据集（Declare Dataset）, 则 数据集已声明（Dataset Declared）.
- 数据集声明需要特征架构快照（Feature Schema Snapshot Required For Dataset Declaration） / 特征架构加载失败（Feature Schema Load Failed） - 当 声明数据集（Declare Dataset）, 则 错误: 特征架构加载失败.

<!-- em:section id="user-manual.slice.配置运行时数据集绑定（ConfigureRuntimeDatasetBinding）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ConfigureRuntimeDatasetBinding" -->
#### 4.13.2 配置运行时数据集绑定（Configure Runtime Dataset Binding）/FLP-DOC-SECTION-131

| 项目 | 说明 |
| --- | --- |
| 入口 | 运行时数据集绑定屏幕（Runtime Dataset Binding Screen） |
| 角色/触发条件 | 节点操作员（Node Operator） |
| 业务对象 | 运行时数据集绑定（Runtime Dataset Binding） |
| 成功结果 | 运行时数据集绑定已配置（Runtime Dataset Binding Configured）, 状态: 已配置（Configured） |

- 打开运行时数据集绑定屏幕（Runtime Dataset Binding Screen）。
- 按有效业务数据提交 配置运行时数据集绑定（Configure Runtime Dataset Binding）。
- 确认运行时数据集绑定已配置（Runtime Dataset Binding Configured），并确认状态变为 已配置（Configured）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 配置运行时数据集绑定（Configure Runtime Dataset Binding） | runtimeDatasetBindingId | UUID | 必填标识 | id, generated, technical |
| 配置运行时数据集绑定（Configure Runtime Dataset Binding） | datasetId | UUID | 必填/按业务规则 | 来源：DatasetDeclared.datasetId |
| 配置运行时数据集绑定（Configure Runtime Dataset Binding） | organizationId | UUID | 必填/按业务规则 | 来源：DatasetDeclared.organizationId |
| 配置运行时数据集绑定（Configure Runtime Dataset Binding） | featureSchemaId | UUID | 必填/按业务规则 | 来源：DatasetDeclared.featureSchemaId |
| 配置运行时数据集绑定（Configure Runtime Dataset Binding） | datasetName | String | 必填/按业务规则 | 来源：DatasetDeclared.datasetName |
| 配置运行时数据集绑定（Configure Runtime Dataset Binding） | runtimeId | UUID | 必填/按业务规则 | - |
| 配置运行时数据集绑定（Configure Runtime Dataset Binding） | dataSourceType | String | 必填/按业务规则 | - |
| 配置运行时数据集绑定（Configure Runtime Dataset Binding） | host | String | 必填/按业务规则 | - |
| 配置运行时数据集绑定（Configure Runtime Dataset Binding） | port | Int | 必填/按业务规则 | - |
| 配置运行时数据集绑定（Configure Runtime Dataset Binding） | url | String | 必填/按业务规则 | - |
| 配置运行时数据集绑定（Configure Runtime Dataset Binding） | databaseName | String | 必填/按业务规则 | - |
| 配置运行时数据集绑定（Configure Runtime Dataset Binding） | schemaName | String | 必填/按业务规则 | - |
| 配置运行时数据集绑定（Configure Runtime Dataset Binding） | tableName | String | 必填/按业务规则 | - |
| 配置运行时数据集绑定（Configure Runtime Dataset Binding） | filePath | String | 必填/按业务规则 | - |
| 配置运行时数据集绑定（Configure Runtime Dataset Binding） | objectBucket | String | 必填/按业务规则 | - |
| 配置运行时数据集绑定（Configure Runtime Dataset Binding） | objectPrefix | String | 必填/按业务规则 | - |
| 配置运行时数据集绑定（Configure Runtime Dataset Binding） | dataFormat | String | 必填/按业务规则 | - |
| 配置运行时数据集绑定（Configure Runtime Dataset Binding） | credentialSecretName | String | 必填/按业务规则 | - |

**注意事项**

- 每个运行时数据集的运行时数据集绑定唯一（Runtime Dataset Binding Unique Per Runtime Dataset） / 拒绝重复的运行时数据集绑定（Reject Duplicate Runtime Dataset Binding） - 当 配置运行时数据集绑定（Configure Runtime Dataset Binding）, 则 拒绝: 运行时数据集绑定已存在.

<!-- em:section id="user-manual.slice.运行时数据集绑定目录（RuntimeDatasetBindingCatalog）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/RuntimeDatasetBindingCatalog" -->
#### 4.13.3 运行时数据集绑定目录（Runtime Dataset Binding Catalog）/FLP-DOC-SECTION-132

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 运行时数据集绑定（Runtime Dataset Binding） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 运行时数据集绑定目录（Runtime Dataset Binding Catalog）。
- 确认预期业务结果。
- 查看 运行时数据集绑定目录（Runtime Dataset Binding Catalog） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.验证代理数据集访问（ValidateAgentDatasetAccess）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ValidateAgentDatasetAccess" -->
#### 4.13.4 验证代理数据集访问（Validate Agent Dataset Access）/FLP-DOC-SECTION-133

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 运行时代理（Runtime Agent） |
| 业务对象 | 代理数据集访问验证（Agent Dataset Access Validation） |
| 成功结果 | 代理数据集访问已验证（Agent Dataset Access Validated）, 代理数据集访问验证失败（Agent Dataset Access Validation Failed）, 状态: 已检查（Checked） |

- 打开已配置的系统入口。
- 按有效业务数据提交 验证代理数据集访问（Validate Agent Dataset Access）。
- 确认代理数据集访问已验证（Agent Dataset Access Validated）, 代理数据集访问验证失败（Agent Dataset Access Validation Failed），并确认状态变为 已检查（Checked）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 验证代理数据集访问（Validate Agent Dataset Access） | datasetAccessValidationId | UUID | 必填标识 | id, generated, technical |
| 验证代理数据集访问（Validate Agent Dataset Access） | runtimeDatasetBindingId | UUID | 必填/按业务规则 | 来源：RuntimeDatasetBindingConfigured.runtimeDatasetBindingId |
| 验证代理数据集访问（Validate Agent Dataset Access） | datasetId | UUID | 必填/按业务规则 | 来源：RuntimeDatasetBindingConfigured.datasetId |
| 验证代理数据集访问（Validate Agent Dataset Access） | organizationId | UUID | 必填/按业务规则 | 来源：RuntimeDatasetBindingConfigured.organizationId |
| 验证代理数据集访问（Validate Agent Dataset Access） | featureSchemaId | UUID | 必填/按业务规则 | 来源：RuntimeDatasetBindingConfigured.featureSchemaId |
| 验证代理数据集访问（Validate Agent Dataset Access） | datasetName | String | 必填/按业务规则 | 来源：RuntimeDatasetBindingConfigured.datasetName |
| 验证代理数据集访问（Validate Agent Dataset Access） | runtimeId | UUID | 必填/按业务规则 | 来源：RuntimeDatasetBindingConfigured.runtimeId |
| 验证代理数据集访问（Validate Agent Dataset Access） | dataSourceType | String | 必填/按业务规则 | 来源：RuntimeDatasetBindingConfigured.dataSourceType |
| 验证代理数据集访问（Validate Agent Dataset Access） | host | String | 必填/按业务规则 | 来源：RuntimeDatasetBindingConfigured.host |
| 验证代理数据集访问（Validate Agent Dataset Access） | port | Int | 必填/按业务规则 | 来源：RuntimeDatasetBindingConfigured.port |
| 验证代理数据集访问（Validate Agent Dataset Access） | url | String | 必填/按业务规则 | 来源：RuntimeDatasetBindingConfigured.url |
| 验证代理数据集访问（Validate Agent Dataset Access） | databaseName | String | 必填/按业务规则 | 来源：RuntimeDatasetBindingConfigured.databaseName |
| 验证代理数据集访问（Validate Agent Dataset Access） | schemaName | String | 必填/按业务规则 | 来源：RuntimeDatasetBindingConfigured.schemaName |
| 验证代理数据集访问（Validate Agent Dataset Access） | tableName | String | 必填/按业务规则 | 来源：RuntimeDatasetBindingConfigured.tableName |
| 验证代理数据集访问（Validate Agent Dataset Access） | filePath | String | 必填/按业务规则 | 来源：RuntimeDatasetBindingConfigured.filePath |
| 验证代理数据集访问（Validate Agent Dataset Access） | objectBucket | String | 必填/按业务规则 | 来源：RuntimeDatasetBindingConfigured.objectBucket |
| 验证代理数据集访问（Validate Agent Dataset Access） | objectPrefix | String | 必填/按业务规则 | 来源：RuntimeDatasetBindingConfigured.objectPrefix |
| 验证代理数据集访问（Validate Agent Dataset Access） | dataFormat | String | 必填/按业务规则 | 来源：RuntimeDatasetBindingConfigured.dataFormat |
| 验证代理数据集访问（Validate Agent Dataset Access） | credentialSecretName | String | 必填/按业务规则 | 来源：RuntimeDatasetBindingConfigured.credentialSecretName |

**注意事项**

- 代理数据集访问验证结果（Agent Dataset Access Validation Outcome） / 代理数据集访问已验证（Agent Dataset Access Validated） - 当 验证代理数据集访问（Validate Agent Dataset Access）, 则 代理数据集访问已验证（Agent Dataset Access Validated）.
- 代理数据集访问验证结果（Agent Dataset Access Validation Outcome） / 代理数据集访问验证失败（Agent Dataset Access Validation Failed） - 当 验证代理数据集访问（Validate Agent Dataset Access）, 则 代理数据集访问验证失败（Agent Dataset Access Validation Failed）.

<!-- em:section id="user-manual.slice.重新验证代理数据集访问（RevalidateAgentDatasetAccess）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/RevalidateAgentDatasetAccess" -->
#### 4.13.5 重新验证代理数据集访问（Revalidate Agent Dataset Access）/FLP-DOC-SECTION-134

| 项目 | 说明 |
| --- | --- |
| 入口 | 运行时数据集绑定屏幕（Runtime Dataset Binding Screen） |
| 角色/触发条件 | 节点操作员（Node Operator） |
| 业务对象 | 代理数据集访问验证（Agent Dataset Access Validation） |
| 成功结果 | 代理数据集访问已重新验证（Agent Dataset Access Revalidated）, 代理数据集访问重新验证失败（Agent Dataset Access Revalidation Failed）, 状态: 已检查（Checked） |

- 打开运行时数据集绑定屏幕（Runtime Dataset Binding Screen）。
- 按有效业务数据提交 重新验证代理数据集访问（Revalidate Agent Dataset Access）。
- 确认代理数据集访问已重新验证（Agent Dataset Access Revalidated）, 代理数据集访问重新验证失败（Agent Dataset Access Revalidation Failed），并确认状态变为 已检查（Checked）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 重新验证代理数据集访问（Revalidate Agent Dataset Access） | datasetAccessValidationId | UUID | 必填标识 | id, generated, technical |
| 重新验证代理数据集访问（Revalidate Agent Dataset Access） | runtimeDatasetBindingId | UUID | 必填/按业务规则 | 来源：AgentDatasetAccessValidationFailed.runtimeDatasetBindingId |

**注意事项**

- 代理数据集访问重新验证结果（Agent Dataset Access Revalidation Outcome） / 代理数据集访问已重新验证（Agent Dataset Access Revalidated） - 当 重新验证代理数据集访问（Revalidate Agent Dataset Access）, 则 代理数据集访问已重新验证（Agent Dataset Access Revalidated）.
- 代理数据集访问重新验证结果（Agent Dataset Access Revalidation Outcome） / 代理数据集访问重新验证失败（Agent Dataset Access Revalidation Failed） - 当 重新验证代理数据集访问（Revalidate Agent Dataset Access）, 则 代理数据集访问重新验证失败（Agent Dataset Access Revalidation Failed）.

<!-- em:section id="user-manual.slice.代理数据集访问验证目录（AgentDatasetAccessValidationCatalog）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/AgentDatasetAccessValidationCatalog" -->
#### 4.13.6 代理数据集访问验证目录（Agent Dataset Access Validation Catalog）/FLP-DOC-SECTION-135

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 代理数据集访问验证（Agent Dataset Access Validation） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 代理数据集访问验证目录（Agent Dataset Access Validation Catalog）。
- 确认预期业务结果。
- 查看 代理数据集访问验证目录（Agent Dataset Access Validation Catalog） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.分析代理数据集（ProfileAgentDataset）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ProfileAgentDataset" -->
#### 4.13.7 分析代理数据集（Profile Agent Dataset）/FLP-DOC-SECTION-136

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 运行时代理（Runtime Agent） |
| 业务对象 | 代理数据集配置文件（Agent Dataset Profile） |
| 成功结果 | 代理数据集元数据已报告（Agent Dataset Metadata Reported）, 代理数据集分析失败（Agent Dataset Profiling Failed）, 状态: 已报告（Reported） |

- 打开已配置的系统入口。
- 按有效业务数据提交 分析代理数据集（Profile Agent Dataset）。
- 确认代理数据集元数据已报告（Agent Dataset Metadata Reported）, 代理数据集分析失败（Agent Dataset Profiling Failed），并确认状态变为 已报告（Reported）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 分析代理数据集（Profile Agent Dataset） | metadataReportId | UUID | 必填标识 | id, generated, technical |
| 分析代理数据集（Profile Agent Dataset） | runtimeDatasetBindingId | UUID | 必填/按业务规则 | 来源：AgentDatasetAccessValidated.runtimeDatasetBindingId |
| 分析代理数据集（Profile Agent Dataset） | datasetId | UUID | 必填/按业务规则 | 来源：AgentDatasetAccessValidated.datasetId |
| 分析代理数据集（Profile Agent Dataset） | organizationId | UUID | 必填/按业务规则 | 来源：AgentDatasetAccessValidated.organizationId |
| 分析代理数据集（Profile Agent Dataset） | featureSchemaId | UUID | 必填/按业务规则 | 来源：AgentDatasetAccessValidated.featureSchemaId |
| 分析代理数据集（Profile Agent Dataset） | datasetName | String | 必填/按业务规则 | 来源：AgentDatasetAccessValidated.datasetName |
| 分析代理数据集（Profile Agent Dataset） | runtimeId | UUID | 必填/按业务规则 | 来源：AgentDatasetAccessValidated.runtimeId |

**注意事项**

- 代理数据集分析结果（Agent Dataset Profiling Outcome） / 代理数据集元数据已报告（Agent Dataset Metadata Reported） - 当 分析代理数据集（Profile Agent Dataset）, 则 代理数据集元数据已报告（Agent Dataset Metadata Reported）.
- 代理数据集分析结果（Agent Dataset Profiling Outcome） / 代理数据集分析失败（Agent Dataset Profiling Failed） - 当 分析代理数据集（Profile Agent Dataset）, 则 代理数据集分析失败（Agent Dataset Profiling Failed）.

<!-- em:section id="user-manual.slice.重新分析代理数据集（ReprofileAgentDataset）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ReprofileAgentDataset" -->
#### 4.13.8 重新分析代理数据集（Reprofile Agent Dataset）/FLP-DOC-SECTION-137

| 项目 | 说明 |
| --- | --- |
| 入口 | 运行时数据集绑定屏幕（Runtime Dataset Binding Screen） |
| 角色/触发条件 | 节点操作员（Node Operator） |
| 业务对象 | 代理数据集配置文件（Agent Dataset Profile） |
| 成功结果 | 代理数据集已重新分析（Agent Dataset Reprofiled）, 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed）, 状态: 已报告（Reported） |

- 打开运行时数据集绑定屏幕（Runtime Dataset Binding Screen）。
- 按有效业务数据提交 重新分析代理数据集（Reprofile Agent Dataset）。
- 确认代理数据集已重新分析（Agent Dataset Reprofiled）, 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed），并确认状态变为 已报告（Reported）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 重新分析代理数据集（Reprofile Agent Dataset） | metadataReportId | UUID | 必填标识 | id, generated, technical |
| 重新分析代理数据集（Reprofile Agent Dataset） | runtimeDatasetBindingId | UUID | 必填/按业务规则 | 来源：AgentDatasetProfilingFailed.runtimeDatasetBindingId |

**注意事项**

- 代理数据集重新剖析结果（Agent Dataset Reprofiling Outcome） / 代理数据集已重新分析（Agent Dataset Reprofiled） - 当 重新分析代理数据集（Reprofile Agent Dataset）, 则 代理数据集已重新分析（Agent Dataset Reprofiled）.
- 代理数据集重新剖析结果（Agent Dataset Reprofiling Outcome） / 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed） - 当 重新分析代理数据集（Reprofile Agent Dataset）, 则 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed）.

<!-- em:section id="user-manual.slice.验证数据集合约（ValidateDatasetContract）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ValidateDatasetContract" -->
#### 4.13.9 验证数据集合约（Validate Dataset Contract）/FLP-DOC-SECTION-138

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 自动验证数据集合约（Validate Dataset Contract Automatically） |
| 业务对象 | 数据集（Dataset） |
| 成功结果 | 数据集合约已验证（Dataset Contract Validated）, 数据集合约验证失败（Dataset Contract Validation Failed）, 状态: 合约验证已完成（Contract Validation Completed） |

- 打开已配置的系统入口。
- 按有效业务数据提交 验证数据集合约（Validate Dataset Contract）。
- 确认数据集合约已验证（Dataset Contract Validated）, 数据集合约验证失败（Dataset Contract Validation Failed），并确认状态变为 合约验证已完成（Contract Validation Completed）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 验证数据集合约（Validate Dataset Contract） | datasetId | UUID | 必填标识 | 来源：AgentDatasetMetadataReported.datasetId; id, technical |
| 验证数据集合约（Validate Dataset Contract） | metadataReportId | UUID | 必填/按业务规则 | 来源：AgentDatasetMetadataReported.metadataReportId |
| 验证数据集合约（Validate Dataset Contract） | featureSchemaId | UUID | 必填/按业务规则 | 来源：AgentDatasetMetadataReported.featureSchemaId |

**注意事项**

- 数据集合约验证结果（Dataset Contract Validation Outcome） / 数据集合约验证成功（Dataset Contract Validation Succeeded） - 当 验证数据集合约（Validate Dataset Contract）, 则 数据集合约已验证（Dataset Contract Validated）.
- 数据集合约验证结果（Dataset Contract Validation Outcome） / 数据集合约验证失败（Dataset Contract Validation Failed） - 当 验证数据集合约（Validate Dataset Contract）, 则 数据集合约验证失败（Dataset Contract Validation Failed）.

<!-- em:section id="user-manual.slice.重试数据集合约验证（RetryDatasetContractValidation）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/RetryDatasetContractValidation" -->
#### 4.13.10 重试数据集合约验证（Retry Dataset Contract Validation）/FLP-DOC-SECTION-139

| 项目 | 说明 |
| --- | --- |
| 入口 | 数据集注册屏幕（Dataset Registration Screen） |
| 角色/触发条件 | 数据所有者（Data Owner） |
| 业务对象 | 数据集（Dataset） |
| 成功结果 | 数据集合约已重新验证（Dataset Contract Revalidated）, 数据集合约重新验证失败（Dataset Contract Revalidation Failed）, 状态: 合约验证已完成（Contract Validation Completed） |

- 打开数据集注册屏幕（Dataset Registration Screen）。
- 按有效业务数据提交 重试数据集合约验证（Retry Dataset Contract Validation）。
- 确认数据集合约已重新验证（Dataset Contract Revalidated）, 数据集合约重新验证失败（Dataset Contract Revalidation Failed），并确认状态变为 合约验证已完成（Contract Validation Completed）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 重试数据集合约验证（Retry Dataset Contract Validation） | datasetId | UUID | 必填标识 | id, technical |

**注意事项**

- 数据集合约验证重试结果（Dataset Contract Validation Retry Outcome） / 数据集合约已重新验证（Dataset Contract Revalidated） - 当 重试数据集合约验证（Retry Dataset Contract Validation）, 则 数据集合约已重新验证（Dataset Contract Revalidated）.
- 数据集合约验证重试结果（Dataset Contract Validation Retry Outcome） / 数据集合约重新验证失败（Dataset Contract Revalidation Failed） - 当 重试数据集合约验证（Retry Dataset Contract Validation）, 则 数据集合约重新验证失败（Dataset Contract Revalidation Failed）.

<!-- em:section id="user-manual.slice.拒绝数据集用于训练（RejectDatasetForTraining）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/RejectDatasetForTraining" -->
#### 4.13.11 拒绝数据集用于训练（Reject Dataset For Training）/FLP-DOC-SECTION-140

| 项目 | 说明 |
| --- | --- |
| 入口 | 数据集审批屏幕（Dataset Approval Screen） |
| 角色/触发条件 | 合规专员（Compliance Officer） |
| 业务对象 | 数据集（Dataset） |
| 成功结果 | 数据集已拒绝用于训练（Dataset Rejected For Training）, 状态: 已拒绝（Rejected） |

- 打开数据集审批屏幕（Dataset Approval Screen）。
- 按有效业务数据提交 拒绝数据集用于训练（Reject Dataset For Training）。
- 确认数据集已拒绝用于训练（Dataset Rejected For Training），并确认状态变为 已拒绝（Rejected）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 拒绝数据集用于训练（Reject Dataset For Training） | datasetId | UUID | 必填标识 | id, technical |
| 拒绝数据集用于训练（Reject Dataset For Training） | rejectionReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.批准数据集用于训练（ApproveDatasetForTraining）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ApproveDatasetForTraining" -->
#### 4.13.12 批准数据集用于训练（Approve Dataset For Training）/FLP-DOC-SECTION-141

| 项目 | 说明 |
| --- | --- |
| 入口 | 数据集审批屏幕（Dataset Approval Screen） |
| 角色/触发条件 | 合规专员（Compliance Officer） |
| 业务对象 | 数据集（Dataset） |
| 成功结果 | 数据集已批准用于训练（Dataset Approved For Training）, 状态: 已批准（Approved） |

- 打开数据集审批屏幕（Dataset Approval Screen）。
- 按有效业务数据提交 批准数据集用于训练（Approve Dataset For Training）。
- 确认数据集已批准用于训练（Dataset Approved For Training），并确认状态变为 已批准（Approved）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 批准数据集用于训练（Approve Dataset For Training） | datasetId | UUID | 必填标识 | id, technical |

**注意事项**

- 仅批准已验证的高质量数据集（Only Validated Quality Datasets Are Approved） / 批准已验证的数据集（Approve Validated Dataset） - 当 批准数据集用于训练（Approve Dataset For Training）, 则 数据集已批准用于训练（Dataset Approved For Training）.
- 仅批准已验证的高质量数据集（Only Validated Quality Datasets Are Approved） / 在数据集合约验证前拒绝批准（Reject Approval Before Contract Validation） - 当 批准数据集用于训练（Approve Dataset For Training）, 则 拒绝: 需要数据集合约验证.

<!-- em:section id="user-manual.slice.撤销数据集训练审批（RevokeDatasetTrainingApproval）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/RevokeDatasetTrainingApproval" -->
#### 4.13.13 撤销数据集训练审批（Revoke Dataset Training Approval）/FLP-DOC-SECTION-142

| 项目 | 说明 |
| --- | --- |
| 入口 | 数据集审批屏幕（Dataset Approval Screen） |
| 角色/触发条件 | 合规专员（Compliance Officer） |
| 业务对象 | 数据集（Dataset） |
| 成功结果 | 数据集训练批准已撤销（Dataset Training Approval Revoked）, 状态: 审批已撤销（Approval Revoked） |

- 打开数据集审批屏幕（Dataset Approval Screen）。
- 按有效业务数据提交 撤销数据集训练审批（Revoke Dataset Training Approval）。
- 确认数据集训练批准已撤销（Dataset Training Approval Revoked），并确认状态变为 审批已撤销（Approval Revoked）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 撤销数据集训练审批（Revoke Dataset Training Approval） | datasetId | UUID | 必填标识 | id, technical |
| 撤销数据集训练审批（Revoke Dataset Training Approval） | revokeReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.数据集能力（DatasetCapability）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/DatasetCapability" -->
#### 4.13.14 数据集能力（Dataset Capability）/FLP-DOC-SECTION-143

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 数据集（Dataset） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 数据集能力（Dataset Capability）。
- 确认预期业务结果。
- 查看 数据集能力（Dataset Capability） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.数据集就绪状态（DatasetReadiness）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/DatasetReadiness" -->
#### 4.13.15 数据集就绪状态（Dataset Readiness）/FLP-DOC-SECTION-144

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 数据集（Dataset） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 数据集就绪状态（Dataset Readiness）。
- 确认预期业务结果。
- 查看 数据集就绪状态（Dataset Readiness） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.接收参与者执行计划（ReceiveParticipantExecutionPlan）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ReceiveParticipantExecutionPlan" -->
#### 4.13.16 接收参与者执行计划（Receive Participant Execution Plan）/FLP-DOC-SECTION-145

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 分发时接收执行计划（Receive Execution Plan When Dispatched） |
| 业务对象 | 轮次执行（Round Execution） |
| 成功结果 | 执行计划已接收（Execution Plan Received）, 状态: 计划已接收（Plan Received） |

- 打开已配置的系统入口。
- 按有效业务数据提交 接收参与者执行计划（Receive Participant Execution Plan）。
- 确认执行计划已接收（Execution Plan Received），并确认状态变为 计划已接收（Plan Received）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 接收参与者执行计划（Receive Participant Execution Plan） | executionPlanId | UUID | 必填标识 | 来源：ParticipantExecutionPlanDispatched.executionPlanId; id, technical |
| 接收参与者执行计划（Receive Participant Execution Plan） | executionSessionId | UUID | 必填/按业务规则 | 来源：ParticipantExecutionPlanDispatched.executionSessionId |
| 接收参与者执行计划（Receive Participant Execution Plan） | trainingJobId | UUID | 必填/按业务规则 | 来源：ParticipantExecutionPlanDispatched.trainingJobId |
| 接收参与者执行计划（Receive Participant Execution Plan） | trainingRunConfigurationId | UUID | 必填/按业务规则 | 来源：ParticipantExecutionPlanDispatched.trainingRunConfigurationId |
| 接收参与者执行计划（Receive Participant Execution Plan） | featureSchemaId | UUID | 必填/按业务规则 | 来源：ParticipantExecutionPlanDispatched.featureSchemaId |
| 接收参与者执行计划（Receive Participant Execution Plan） | roundId | UUID | 必填/按业务规则 | 来源：ParticipantExecutionPlanDispatched.roundId |
| 接收参与者执行计划（Receive Participant Execution Plan） | roundNumber | Int | 必填/按业务规则 | 来源：ParticipantExecutionPlanDispatched.roundNumber |
| 接收参与者执行计划（Receive Participant Execution Plan） | runtimeId | UUID | 必填/按业务规则 | 来源：ParticipantExecutionPlanDispatched.runtimeId |
| 接收参与者执行计划（Receive Participant Execution Plan） | organizationId | UUID | 必填/按业务规则 | 来源：ParticipantExecutionPlanDispatched.organizationId |
| 接收参与者执行计划（Receive Participant Execution Plan） | baseModelId | UUID | 必填/按业务规则 | 来源：ParticipantExecutionPlanDispatched.baseModelId |
| 接收参与者执行计划（Receive Participant Execution Plan） | baseModelArtifactUri | String | 必填/按业务规则 | 来源：ParticipantExecutionPlanDispatched.baseModelArtifactUri |
| 接收参与者执行计划（Receive Participant Execution Plan） | baseModelRegistryRef | String | 必填/按业务规则 | 来源：ParticipantExecutionPlanDispatched.baseModelRegistryRef |
| 接收参与者执行计划（Receive Participant Execution Plan） | baseModelFormat | String | 必填/按业务规则 | 来源：ParticipantExecutionPlanDispatched.baseModelFormat |
| 接收参与者执行计划（Receive Participant Execution Plan） | baseModelArtifactDigest | String | 必填/按业务规则 | 来源：ParticipantExecutionPlanDispatched.baseModelArtifactDigest |
| 接收参与者执行计划（Receive Participant Execution Plan） | baseModelSignatureUri | String | 必填/按业务规则 | 来源：ParticipantExecutionPlanDispatched.baseModelSignatureUri |

<!-- em:section id="user-manual.slice.接受执行计划（AcceptExecutionPlan）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/AcceptExecutionPlan" -->
#### 4.13.17 接受执行计划（Accept Execution Plan）/FLP-DOC-SECTION-146

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 收到执行计划时接受执行计划（Accept Execution Plan When Execution Plan Received） |
| 业务对象 | 轮次执行（Round Execution） |
| 成功结果 | 执行计划已接受（Execution Plan Accepted）, 执行计划已拒绝（Execution Plan Rejected）, 状态: 计划已接受（Plan Accepted） |

- 打开已配置的系统入口。
- 按有效业务数据提交 接受执行计划（Accept Execution Plan）。
- 确认执行计划已接受（Execution Plan Accepted）, 执行计划已拒绝（Execution Plan Rejected），并确认状态变为 计划已接受（Plan Accepted）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 接受执行计划（Accept Execution Plan） | executionPlanId | UUID | 必填标识 | 来源：ExecutionPlanReceived.executionPlanId; id, technical |
| 接受执行计划（Accept Execution Plan） | executionSessionId | UUID | 必填/按业务规则 | 来源：ExecutionPlanReceived.executionSessionId |
| 接受执行计划（Accept Execution Plan） | trainingJobId | UUID | 必填/按业务规则 | 来源：ExecutionPlanReceived.trainingJobId |
| 接受执行计划（Accept Execution Plan） | trainingRunConfigurationId | UUID | 必填/按业务规则 | 来源：ExecutionPlanReceived.trainingRunConfigurationId |
| 接受执行计划（Accept Execution Plan） | featureSchemaId | UUID | 必填/按业务规则 | 来源：ExecutionPlanReceived.featureSchemaId |
| 接受执行计划（Accept Execution Plan） | roundId | UUID | 必填/按业务规则 | 来源：ExecutionPlanReceived.roundId |
| 接受执行计划（Accept Execution Plan） | roundNumber | Int | 必填/按业务规则 | 来源：ExecutionPlanReceived.roundNumber |
| 接受执行计划（Accept Execution Plan） | runtimeId | UUID | 必填/按业务规则 | 来源：ExecutionPlanReceived.runtimeId |
| 接受执行计划（Accept Execution Plan） | organizationId | UUID | 必填/按业务规则 | 来源：ExecutionPlanReceived.organizationId |
| 接受执行计划（Accept Execution Plan） | baseModelId | UUID | 必填/按业务规则 | 来源：ExecutionPlanReceived.baseModelId |
| 接受执行计划（Accept Execution Plan） | baseModelArtifactUri | String | 必填/按业务规则 | 来源：ExecutionPlanReceived.baseModelArtifactUri |
| 接受执行计划（Accept Execution Plan） | baseModelRegistryRef | String | 必填/按业务规则 | 来源：ExecutionPlanReceived.baseModelRegistryRef |
| 接受执行计划（Accept Execution Plan） | baseModelFormat | String | 必填/按业务规则 | 来源：ExecutionPlanReceived.baseModelFormat |
| 接受执行计划（Accept Execution Plan） | baseModelArtifactDigest | String | 必填/按业务规则 | 来源：ExecutionPlanReceived.baseModelArtifactDigest |
| 接受执行计划（Accept Execution Plan） | baseModelSignatureUri | String | 必填/按业务规则 | 来源：ExecutionPlanReceived.baseModelSignatureUri |

**注意事项**

- 执行计划本地接受结果（Execution Plan Local Acceptance Outcome） / 执行计划已接受（Execution Plan Accepted） - 当 接受执行计划（Accept Execution Plan）, 则 执行计划已接受（Execution Plan Accepted）.
- 执行计划本地接受结果（Execution Plan Local Acceptance Outcome） / 执行计划已拒绝（Execution Plan Rejected） - 当 接受执行计划（Accept Execution Plan）, 则 执行计划已拒绝（Execution Plan Rejected）.

<!-- em:section id="user-manual.slice.开始轮次执行（StartRoundExecution）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/StartRoundExecution" -->
#### 4.13.18 开始轮次执行（Start Round Execution）/FLP-DOC-SECTION-147

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 计划接受时启动轮次执行（Start Round Execution When Plan Accepted） |
| 业务对象 | 轮次执行（Round Execution） |
| 成功结果 | 轮次执行已启动（Round Execution Started）, 轮次执行启动失败（Round Execution Start Failed）, 状态: 运行中（Running） |

- 打开已配置的系统入口。
- 按有效业务数据提交 开始轮次执行（Start Round Execution）。
- 确认轮次执行已启动（Round Execution Started）, 轮次执行启动失败（Round Execution Start Failed），并确认状态变为 运行中（Running）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 开始轮次执行（Start Round Execution） | roundExecutionId | UUID | 必填标识 | id, generated, technical |
| 开始轮次执行（Start Round Execution） | executionSessionId | UUID | 必填/按业务规则 | 来源：ExecutionPlanAccepted.executionSessionId |
| 开始轮次执行（Start Round Execution） | executionPlanId | UUID | 必填/按业务规则 | 来源：ExecutionPlanAccepted.executionPlanId |
| 开始轮次执行（Start Round Execution） | trainingJobId | UUID | 必填/按业务规则 | 来源：ExecutionPlanAccepted.trainingJobId |
| 开始轮次执行（Start Round Execution） | trainingRunConfigurationId | UUID | 必填/按业务规则 | 来源：ExecutionPlanAccepted.trainingRunConfigurationId |
| 开始轮次执行（Start Round Execution） | roundId | UUID | 必填/按业务规则 | 来源：ExecutionPlanAccepted.roundId |
| 开始轮次执行（Start Round Execution） | roundNumber | Int | 必填/按业务规则 | 来源：ExecutionPlanAccepted.roundNumber |
| 开始轮次执行（Start Round Execution） | runtimeId | UUID | 必填/按业务规则 | 来源：ExecutionPlanAccepted.runtimeId |
| 开始轮次执行（Start Round Execution） | organizationId | UUID | 必填/按业务规则 | 来源：ExecutionPlanAccepted.organizationId |
| 开始轮次执行（Start Round Execution） | featureSchemaId | UUID | 必填/按业务规则 | 来源：ExecutionPlanAccepted.featureSchemaId |
| 开始轮次执行（Start Round Execution） | baseModelId | UUID | 必填/按业务规则 | 来源：ExecutionPlanAccepted.baseModelId |
| 开始轮次执行（Start Round Execution） | baseModelArtifactUri | String | 必填/按业务规则 | 来源：ExecutionPlanAccepted.baseModelArtifactUri |
| 开始轮次执行（Start Round Execution） | baseModelRegistryRef | String | 必填/按业务规则 | 来源：ExecutionPlanAccepted.baseModelRegistryRef |
| 开始轮次执行（Start Round Execution） | baseModelFormat | String | 必填/按业务规则 | 来源：ExecutionPlanAccepted.baseModelFormat |
| 开始轮次执行（Start Round Execution） | baseModelArtifactDigest | String | 必填/按业务规则 | 来源：ExecutionPlanAccepted.baseModelArtifactDigest |
| 开始轮次执行（Start Round Execution） | baseModelSignatureUri | String | 必填/按业务规则 | 来源：ExecutionPlanAccepted.baseModelSignatureUri |

**注意事项**

- 轮次执行运行时引擎启动结果（Round Execution Runtime Engine Start Outcome） / 轮次执行已启动（Round Execution Started） - 当 开始轮次执行（Start Round Execution）, 则 轮次执行已启动（Round Execution Started）.
- 轮次执行运行时引擎启动结果（Round Execution Runtime Engine Start Outcome） / 轮次执行启动失败（Round Execution Start Failed） - 当 开始轮次执行（Start Round Execution）, 则 轮次执行启动失败（Round Execution Start Failed）.

<!-- em:section id="user-manual.slice.观察运行时引擎作业（ObserveRuntimeEngineJob）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ObserveRuntimeEngineJob" -->
#### 4.13.19 观察运行时引擎作业（Observe Runtime Engine Job）/FLP-DOC-SECTION-148

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 轮次执行启动时观察运行时引擎作业（Observe Runtime Engine Job When Round Execution Started） |
| 业务对象 | 轮次执行（Round Execution） |
| 成功结果 | 运行时引擎作业已观察（Runtime Engine Job Observed）, 状态: 运行中（Running） |

- 打开已配置的系统入口。
- 按有效业务数据提交 观察运行时引擎作业（Observe Runtime Engine Job）。
- 确认运行时引擎作业已观察（Runtime Engine Job Observed），并确认状态变为 运行中（Running）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 观察运行时引擎作业（Observe Runtime Engine Job） | roundExecutionId | UUID | 必填标识 | 来源：RoundExecutionStarted.roundExecutionId; id, technical |
| 观察运行时引擎作业（Observe Runtime Engine Job） | executionSessionId | UUID | 必填/按业务规则 | 来源：RoundExecutionStarted.executionSessionId |
| 观察运行时引擎作业（Observe Runtime Engine Job） | executionPlanId | UUID | 必填/按业务规则 | 来源：RoundExecutionStarted.executionPlanId |
| 观察运行时引擎作业（Observe Runtime Engine Job） | trainingJobId | UUID | 必填/按业务规则 | 来源：RoundExecutionStarted.trainingJobId |
| 观察运行时引擎作业（Observe Runtime Engine Job） | trainingRunConfigurationId | UUID | 必填/按业务规则 | 来源：RoundExecutionStarted.trainingRunConfigurationId |
| 观察运行时引擎作业（Observe Runtime Engine Job） | roundId | UUID | 必填/按业务规则 | 来源：RoundExecutionStarted.roundId |
| 观察运行时引擎作业（Observe Runtime Engine Job） | roundNumber | Int | 必填/按业务规则 | 来源：RoundExecutionStarted.roundNumber |
| 观察运行时引擎作业（Observe Runtime Engine Job） | runtimeId | UUID | 必填/按业务规则 | 来源：RoundExecutionStarted.runtimeId |
| 观察运行时引擎作业（Observe Runtime Engine Job） | organizationId | UUID | 必填/按业务规则 | 来源：RoundExecutionStarted.organizationId |
| 观察运行时引擎作业（Observe Runtime Engine Job） | featureSchemaId | UUID | 必填/按业务规则 | 来源：RoundExecutionStarted.featureSchemaId |
| 观察运行时引擎作业（Observe Runtime Engine Job） | runtimeEngineJobId | String | 必填/按业务规则 | 来源：RoundExecutionStarted.runtimeEngineJobId |

**注意事项**

- 运行时引擎作业观察结果（Runtime Engine Job Observation Outcome） / 运行时引擎作业已观察运行中（Runtime Engine Job Observed Running） - 当 观察运行时引擎作业（Observe Runtime Engine Job）, 则 运行时引擎作业已观察（Runtime Engine Job Observed）.
- 运行时引擎作业观察结果（Runtime Engine Job Observation Outcome） / 运行时引擎作业已观察完成（Runtime Engine Job Observed Completed） - 当 观察运行时引擎作业（Observe Runtime Engine Job）, 则 运行时引擎作业已观察（Runtime Engine Job Observed）.
- 运行时引擎作业观察结果（Runtime Engine Job Observation Outcome） / 运行时引擎作业已观察失败（Runtime Engine Job Observed Failed） - 当 观察运行时引擎作业（Observe Runtime Engine Job）, 则 运行时引擎作业已观察（Runtime Engine Job Observed）.

<!-- em:section id="user-manual.slice.完成轮次执行（CompleteRoundExecution）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/CompleteRoundExecution" -->
#### 4.13.20 完成轮次执行（Complete Round Execution）/FLP-DOC-SECTION-149

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 运行时引擎作业完成时完成轮次执行（Complete Round Execution When Runtime Engine Job Completed） |
| 业务对象 | 轮次执行（Round Execution） |
| 成功结果 | 轮次执行已完成（Round Execution Completed）, 状态: 已完成（Completed） |

- 打开已配置的系统入口。
- 按有效业务数据提交 完成轮次执行（Complete Round Execution）。
- 确认轮次执行已完成（Round Execution Completed），并确认状态变为 已完成（Completed）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 完成轮次执行（Complete Round Execution） | roundExecutionId | UUID | 必填标识 | 来源：RuntimeEngineJobObserved.roundExecutionId; id, technical |
| 完成轮次执行（Complete Round Execution） | executionSessionId | UUID | 必填/按业务规则 | 来源：RuntimeEngineJobObserved.executionSessionId |
| 完成轮次执行（Complete Round Execution） | executionPlanId | UUID | 必填/按业务规则 | 来源：RuntimeEngineJobObserved.executionPlanId |
| 完成轮次执行（Complete Round Execution） | trainingJobId | UUID | 必填/按业务规则 | 来源：RuntimeEngineJobObserved.trainingJobId |
| 完成轮次执行（Complete Round Execution） | trainingRunConfigurationId | UUID | 必填/按业务规则 | 来源：RuntimeEngineJobObserved.trainingRunConfigurationId |
| 完成轮次执行（Complete Round Execution） | roundId | UUID | 必填/按业务规则 | 来源：RuntimeEngineJobObserved.roundId |
| 完成轮次执行（Complete Round Execution） | roundNumber | Int | 必填/按业务规则 | 来源：RuntimeEngineJobObserved.roundNumber |
| 完成轮次执行（Complete Round Execution） | runtimeId | UUID | 必填/按业务规则 | 来源：RuntimeEngineJobObserved.runtimeId |
| 完成轮次执行（Complete Round Execution） | organizationId | UUID | 必填/按业务规则 | 来源：RuntimeEngineJobObserved.organizationId |
| 完成轮次执行（Complete Round Execution） | featureSchemaId | UUID | 必填/按业务规则 | 来源：RuntimeEngineJobObserved.featureSchemaId |
| 完成轮次执行（Complete Round Execution） | runtimeEngineJobId | String | 必填/按业务规则 | 来源：RuntimeEngineJobObserved.runtimeEngineJobId |
| 完成轮次执行（Complete Round Execution） | localUpdateArtifactRef | String | 必填/按业务规则 | 来源：RuntimeEngineJobObserved.localUpdateArtifactRef |
| 完成轮次执行（Complete Round Execution） | metricsArtifactRef | String | 必填/按业务规则 | 来源：RuntimeEngineJobObserved.metricsArtifactRef |
| 完成轮次执行（Complete Round Execution） | trainingLoss | Decimal | 必填/按业务规则 | 来源：RuntimeEngineJobObserved.trainingLoss |

<!-- em:section id="user-manual.slice.失败轮次执行（FailRoundExecution）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/FailRoundExecution" -->
#### 4.13.21 失败轮次执行（Fail Round Execution）/FLP-DOC-SECTION-150

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | Fail Round Execution When Runtime Engine Job Failed |
| 业务对象 | 轮次执行（Round Execution） |
| 成功结果 | 轮次执行失败（Round Execution Failed）, 状态: 已失败（Failed） |

- 打开已配置的系统入口。
- 按有效业务数据提交 失败轮次执行（Fail Round Execution）。
- 确认轮次执行失败（Round Execution Failed），并确认状态变为 已失败（Failed）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 失败轮次执行（Fail Round Execution） | roundExecutionId | UUID | 必填标识 | 来源：RuntimeEngineJobObserved.roundExecutionId; id, technical |
| 失败轮次执行（Fail Round Execution） | executionSessionId | UUID | 必填/按业务规则 | 来源：RuntimeEngineJobObserved.executionSessionId |
| 失败轮次执行（Fail Round Execution） | executionPlanId | UUID | 必填/按业务规则 | 来源：RuntimeEngineJobObserved.executionPlanId |
| 失败轮次执行（Fail Round Execution） | trainingJobId | UUID | 必填/按业务规则 | 来源：RuntimeEngineJobObserved.trainingJobId |
| 失败轮次执行（Fail Round Execution） | trainingRunConfigurationId | UUID | 必填/按业务规则 | 来源：RuntimeEngineJobObserved.trainingRunConfigurationId |
| 失败轮次执行（Fail Round Execution） | roundId | UUID | 必填/按业务规则 | 来源：RuntimeEngineJobObserved.roundId |
| 失败轮次执行（Fail Round Execution） | roundNumber | Int | 必填/按业务规则 | 来源：RuntimeEngineJobObserved.roundNumber |
| 失败轮次执行（Fail Round Execution） | runtimeId | UUID | 必填/按业务规则 | 来源：RuntimeEngineJobObserved.runtimeId |
| 失败轮次执行（Fail Round Execution） | organizationId | UUID | 必填/按业务规则 | 来源：RuntimeEngineJobObserved.organizationId |
| 失败轮次执行（Fail Round Execution） | featureSchemaId | UUID | 必填/按业务规则 | 来源：RuntimeEngineJobObserved.featureSchemaId |
| 失败轮次执行（Fail Round Execution） | runtimeEngineJobId | String | 必填/按业务规则 | 来源：RuntimeEngineJobObserved.runtimeEngineJobId |
| 失败轮次执行（Fail Round Execution） | failureReason | String | 必填/按业务规则 | 来源：RuntimeEngineJobObserved.failureReason |

<!-- em:section id="user-manual.slice.启动失败后重试轮次执行（RetryRoundExecutionAfterStartFailure）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/RetryRoundExecutionAfterStartFailure" -->
#### 4.13.22 启动失败后重试轮次执行（Retry Round Execution After Start Failure）/FLP-DOC-SECTION-151

| 项目 | 说明 |
| --- | --- |
| 入口 | Round Execution Screen |
| 角色/触发条件 | 节点操作员（Node Operator） |
| 业务对象 | 轮次执行（Round Execution） |
| 成功结果 | Round Execution Start Retry Started, 轮次执行启动重试失败（Round Execution Start Retry Failed）, 状态: 已重试（Retried） |

- 打开Round Execution Screen。
- 按有效业务数据提交 启动失败后重试轮次执行（Retry Round Execution After Start Failure）。
- 确认Round Execution Start Retry Started, 轮次执行启动重试失败（Round Execution Start Retry Failed），并确认状态变为 已重试（Retried）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 启动失败后重试轮次执行（Retry Round Execution After Start Failure） | roundExecutionId | UUID | 必填标识 | id, technical |
| 启动失败后重试轮次执行（Retry Round Execution After Start Failure） | executionSessionId | UUID | 必填/按业务规则 | 来源：RoundExecutionStartFailed.executionSessionId |
| 启动失败后重试轮次执行（Retry Round Execution After Start Failure） | executionPlanId | UUID | 必填/按业务规则 | 来源：RoundExecutionStartFailed.executionPlanId |
| 启动失败后重试轮次执行（Retry Round Execution After Start Failure） | trainingJobId | UUID | 必填/按业务规则 | 来源：RoundExecutionStartFailed.trainingJobId |
| 启动失败后重试轮次执行（Retry Round Execution After Start Failure） | trainingRunConfigurationId | UUID | 必填/按业务规则 | 来源：RoundExecutionStartFailed.trainingRunConfigurationId |
| 启动失败后重试轮次执行（Retry Round Execution After Start Failure） | roundId | UUID | 必填/按业务规则 | 来源：RoundExecutionStartFailed.roundId |
| 启动失败后重试轮次执行（Retry Round Execution After Start Failure） | roundNumber | Int | 必填/按业务规则 | 来源：RoundExecutionStartFailed.roundNumber |
| 启动失败后重试轮次执行（Retry Round Execution After Start Failure） | runtimeId | UUID | 必填/按业务规则 | 来源：RoundExecutionStartFailed.runtimeId |
| 启动失败后重试轮次执行（Retry Round Execution After Start Failure） | organizationId | UUID | 必填/按业务规则 | 来源：RoundExecutionStartFailed.organizationId |
| 启动失败后重试轮次执行（Retry Round Execution After Start Failure） | featureSchemaId | UUID | 必填/按业务规则 | 来源：RoundExecutionStartFailed.featureSchemaId |
| 启动失败后重试轮次执行（Retry Round Execution After Start Failure） | baseModelId | UUID | 必填/按业务规则 | 来源：RoundExecutionStartFailed.baseModelId |
| 启动失败后重试轮次执行（Retry Round Execution After Start Failure） | runtimeEngineJobId | String | 必填/按业务规则 | - |
| 启动失败后重试轮次执行（Retry Round Execution After Start Failure） | retryReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.运行时失败后重试轮次执行（RetryRoundExecutionAfterRuntimeFailure）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/RetryRoundExecutionAfterRuntimeFailure" -->
#### 4.13.23 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure）/FLP-DOC-SECTION-152

| 项目 | 说明 |
| --- | --- |
| 入口 | Round Execution Screen |
| 角色/触发条件 | 节点操作员（Node Operator） |
| 业务对象 | 轮次执行（Round Execution） |
| 成功结果 | Round Execution Runtime Retry Started, 轮次执行运行时重试失败（Round Execution Runtime Retry Failed）, 状态: 已重试（Retried） |

- 打开Round Execution Screen。
- 按有效业务数据提交 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure）。
- 确认Round Execution Runtime Retry Started, 轮次执行运行时重试失败（Round Execution Runtime Retry Failed），并确认状态变为 已重试（Retried）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure） | roundExecutionId | UUID | 必填标识 | id, technical |
| 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure） | executionSessionId | UUID | 必填/按业务规则 | 来源：RoundExecutionFailed.executionSessionId |
| 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure） | executionPlanId | UUID | 必填/按业务规则 | 来源：RoundExecutionFailed.executionPlanId |
| 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure） | trainingJobId | UUID | 必填/按业务规则 | 来源：RoundExecutionFailed.trainingJobId |
| 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure） | trainingRunConfigurationId | UUID | 必填/按业务规则 | 来源：RoundExecutionFailed.trainingRunConfigurationId |
| 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure） | roundId | UUID | 必填/按业务规则 | 来源：RoundExecutionFailed.roundId |
| 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure） | roundNumber | Int | 必填/按业务规则 | 派生：RoundExecutionStarted.roundNumber |
| 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure） | runtimeId | UUID | 必填/按业务规则 | 来源：RoundExecutionFailed.runtimeId |
| 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure） | organizationId | UUID | 必填/按业务规则 | 派生：RoundExecutionStarted.organizationId |
| 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure） | featureSchemaId | UUID | 必填/按业务规则 | 派生：RoundExecutionStarted.featureSchemaId |
| 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure） | baseModelId | UUID | 必填/按业务规则 | 派生：RoundExecutionStarted.baseModelId |
| 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure） | runtimeEngineJobId | String | 必填/按业务规则 | - |
| 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure） | retryReason | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.提交代理本地模型更新（SubmitAgentLocalModelUpdate）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/SubmitAgentLocalModelUpdate" -->
#### 4.13.24 提交代理本地模型更新（Submit Agent Local Model Update）/FLP-DOC-SECTION-153

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | Submit Local Model Update When Round Execution Completed |
| 业务对象 | 轮次执行（Round Execution） |
| 成功结果 | Agent Local Model Update Submitted, 状态: 更新已提交（Update Submitted） |

- 打开已配置的系统入口。
- 按有效业务数据提交 提交代理本地模型更新（Submit Agent Local Model Update）。
- 确认Agent Local Model Update Submitted，并确认状态变为 更新已提交（Update Submitted）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 提交代理本地模型更新（Submit Agent Local Model Update） | modelUpdateSubmissionId | UUID | 必填标识 | id, generated, technical |
| 提交代理本地模型更新（Submit Agent Local Model Update） | executionSessionId | UUID | 必填/按业务规则 | 来源：RoundExecutionCompleted.executionSessionId |
| 提交代理本地模型更新（Submit Agent Local Model Update） | executionPlanId | UUID | 必填/按业务规则 | 来源：RoundExecutionCompleted.executionPlanId |
| 提交代理本地模型更新（Submit Agent Local Model Update） | roundExecutionId | UUID | 必填/按业务规则 | 来源：RoundExecutionCompleted.roundExecutionId |
| 提交代理本地模型更新（Submit Agent Local Model Update） | trainingJobId | UUID | 必填/按业务规则 | 来源：RoundExecutionCompleted.trainingJobId |
| 提交代理本地模型更新（Submit Agent Local Model Update） | trainingRunConfigurationId | UUID | 必填/按业务规则 | 来源：RoundExecutionCompleted.trainingRunConfigurationId |
| 提交代理本地模型更新（Submit Agent Local Model Update） | roundId | UUID | 必填/按业务规则 | 来源：RoundExecutionCompleted.roundId |
| 提交代理本地模型更新（Submit Agent Local Model Update） | runtimeId | UUID | 必填/按业务规则 | 来源：RoundExecutionCompleted.runtimeId |
| 提交代理本地模型更新（Submit Agent Local Model Update） | featureSchemaId | UUID | 必填/按业务规则 | 来源：RoundExecutionCompleted.featureSchemaId |
| 提交代理本地模型更新（Submit Agent Local Model Update） | localModelId | UUID | 必填/按业务规则 | - |
| 提交代理本地模型更新（Submit Agent Local Model Update） | updateArtifactId | UUID | 必填/按业务规则 | - |
| 提交代理本地模型更新（Submit Agent Local Model Update） | artifactRef | String | 必填/按业务规则 | 来源：RoundExecutionCompleted.localUpdateArtifactRef |
| 提交代理本地模型更新（Submit Agent Local Model Update） | artifactDigest | String | 必填/按业务规则 | - |
| 提交代理本地模型更新（Submit Agent Local Model Update） | trainingLoss | Decimal | 必填/按业务规则 | 来源：RoundExecutionCompleted.trainingLoss |

<!-- em:section id="user-manual.slice.完成后释放运行时引擎作业（ReleaseRuntimeEngineJobAfterCompletion）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ReleaseRuntimeEngineJobAfterCompletion" -->
#### 4.13.25 完成后释放运行时引擎作业（Release Runtime Engine Job After Completion）/FLP-DOC-SECTION-154

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | Release Runtime Engine Job When Round Execution Completed |
| 业务对象 | 轮次执行（Round Execution） |
| 成功结果 | Runtime Engine Job Released, 状态: 运行时引擎已释放（Runtime Engine Released） |

- 打开已配置的系统入口。
- 按有效业务数据提交 完成后释放运行时引擎作业（Release Runtime Engine Job After Completion）。
- 确认Runtime Engine Job Released，并确认状态变为 运行时引擎已释放（Runtime Engine Released）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 完成后释放运行时引擎作业（Release Runtime Engine Job After Completion） | roundExecutionId | UUID | 必填标识 | 来源：RoundExecutionCompleted.roundExecutionId; id, technical |
| 完成后释放运行时引擎作业（Release Runtime Engine Job After Completion） | runtimeEngineJobId | String | 必填/按业务规则 | 来源：RoundExecutionCompleted.runtimeEngineJobId |

<!-- em:section id="user-manual.slice.失败后释放运行时引擎作业（ReleaseRuntimeEngineJobAfterFailure）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ReleaseRuntimeEngineJobAfterFailure" -->
#### 4.13.26 失败后释放运行时引擎作业（Release Runtime Engine Job After Failure）/FLP-DOC-SECTION-155

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | Release Runtime Engine Job When Round Execution Failed |
| 业务对象 | 轮次执行（Round Execution） |
| 成功结果 | Runtime Engine Job Release Failed Or Skipped, 状态: 运行时引擎释放已处理（Runtime Engine Release Handled） |

- 打开已配置的系统入口。
- 按有效业务数据提交 失败后释放运行时引擎作业（Release Runtime Engine Job After Failure）。
- 确认Runtime Engine Job Release Failed Or Skipped，并确认状态变为 运行时引擎释放已处理（Runtime Engine Release Handled）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 失败后释放运行时引擎作业（Release Runtime Engine Job After Failure） | roundExecutionId | UUID | 必填标识 | 来源：RoundExecutionFailed.roundExecutionId; id, technical |
| 失败后释放运行时引擎作业（Release Runtime Engine Job After Failure） | runtimeEngineJobId | String | 必填/按业务规则 | 来源：RoundExecutionFailed.runtimeEngineJobId |

<!-- em:section id="user-manual.slice.启动失败后释放运行时引擎作业（ReleaseRuntimeEngineJobAfterStartFailure）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ReleaseRuntimeEngineJobAfterStartFailure" -->
#### 4.13.27 启动失败后释放运行时引擎作业（Release Runtime Engine Job After Start Failure）/FLP-DOC-SECTION-156

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | Release Runtime Engine Job When Start Failed |
| 业务对象 | 轮次执行（Round Execution） |
| 成功结果 | Runtime Engine Job Release Failed Or Skipped After Start Failure, 状态: 运行时引擎释放已处理（Runtime Engine Release Handled） |

- 打开已配置的系统入口。
- 按有效业务数据提交 启动失败后释放运行时引擎作业（Release Runtime Engine Job After Start Failure）。
- 确认Runtime Engine Job Release Failed Or Skipped After Start Failure，并确认状态变为 运行时引擎释放已处理（Runtime Engine Release Handled）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 启动失败后释放运行时引擎作业（Release Runtime Engine Job After Start Failure） | roundExecutionId | UUID | 必填标识 | 来源：RoundExecutionStartFailed.roundExecutionId; id, technical |
| 启动失败后释放运行时引擎作业（Release Runtime Engine Job After Start Failure） | runtimeEngineJobId | String | 必填/按业务规则 | 来源：RoundExecutionStartFailed.runtimeEngineJobId |

<!-- em:section id="user-manual.slice.重试失败后释放运行时引擎作业（ReleaseRuntimeEngineJobAfterRetryFailure）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ReleaseRuntimeEngineJobAfterRetryFailure" -->
#### 4.13.28 重试失败后释放运行时引擎作业（Release Runtime Engine Job After Retry Failure）/FLP-DOC-SECTION-157

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | Release Runtime Engine Job When Start Retry Failed |
| 业务对象 | 轮次执行（Round Execution） |
| 成功结果 | Runtime Engine Job Release Failed Or Skipped After Retry, 状态: 运行时引擎释放已处理（Runtime Engine Release Handled） |

- 打开已配置的系统入口。
- 按有效业务数据提交 重试失败后释放运行时引擎作业（Release Runtime Engine Job After Retry Failure）。
- 确认Runtime Engine Job Release Failed Or Skipped After Retry，并确认状态变为 运行时引擎释放已处理（Runtime Engine Release Handled）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 重试失败后释放运行时引擎作业（Release Runtime Engine Job After Retry Failure） | roundExecutionId | UUID | 必填标识 | 来源：RoundExecutionStartRetryFailed.roundExecutionId; id, technical |
| 重试失败后释放运行时引擎作业（Release Runtime Engine Job After Retry Failure） | runtimeEngineJobId | String | 必填/按业务规则 | 来源：RoundExecutionStartRetryFailed.runtimeEngineJobId |

<!-- em:section id="user-manual.slice.运行时重试失败后释放运行时引擎作业（ReleaseRuntimeEngineJobAfterRuntimeRetryFailure）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ReleaseRuntimeEngineJobAfterRuntimeRetryFailure" -->
#### 4.13.29 运行时重试失败后释放运行时引擎作业（Release Runtime Engine Job After Runtime Retry Failure）/FLP-DOC-SECTION-158

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | Release Runtime Engine Job When Runtime Retry Failed |
| 业务对象 | 轮次执行（Round Execution） |
| 成功结果 | Runtime Engine Job Release Failed Or Skipped After Runtime Retry, 状态: 运行时引擎释放已处理（Runtime Engine Release Handled） |

- 打开已配置的系统入口。
- 按有效业务数据提交 运行时重试失败后释放运行时引擎作业（Release Runtime Engine Job After Runtime Retry Failure）。
- 确认Runtime Engine Job Release Failed Or Skipped After Runtime Retry，并确认状态变为 运行时引擎释放已处理（Runtime Engine Release Handled）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 运行时重试失败后释放运行时引擎作业（Release Runtime Engine Job After Runtime Retry Failure） | roundExecutionId | UUID | 必填标识 | 来源：RoundExecutionRuntimeRetryFailed.roundExecutionId; id, technical |
| 运行时重试失败后释放运行时引擎作业（Release Runtime Engine Job After Runtime Retry Failure） | runtimeEngineJobId | String | 必填/按业务规则 | 来源：RoundExecutionRuntimeRetryFailed.runtimeEngineJobId |

<!-- em:section id="user-manual.slice.轮次执行目录（RoundExecutionCatalog）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/RoundExecutionCatalog" -->
#### 4.13.30 轮次执行目录（Round Execution Catalog）/FLP-DOC-SECTION-159

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 轮次执行（Round Execution） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 轮次执行目录（Round Execution Catalog）。
- 确认预期业务结果。
- 查看 轮次执行目录（Round Execution Catalog） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.加载运行时代理引导配置（LoadRuntimeAgentBootstrapConfiguration）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/LoadRuntimeAgentBootstrapConfiguration" -->
#### 4.13.31 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration）/FLP-DOC-SECTION-160

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 运行时代理（Runtime Agent） |
| 业务对象 | 运行时代理生命周期（Runtime Agent Lifecycle） |
| 成功结果 | Runtime Agent Bootstrap Configuration Loaded, Runtime Agent Bootstrap Configuration Load Failed, 状态: 引导程序已加载（Bootstrap Loaded） |

- 打开已配置的系统入口。
- 按有效业务数据提交 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration）。
- 确认Runtime Agent Bootstrap Configuration Loaded, Runtime Agent Bootstrap Configuration Load Failed，并确认状态变为 引导程序已加载（Bootstrap Loaded）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration） | bootstrapRequestId | UUID | 必填标识 | id, generated, technical |

**注意事项**

- Runtime Agent Startup Bootstrap Configuration / 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration） - 当 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration）, 则 Runtime Agent Bootstrap Configuration Loaded.
- Runtime Agent Startup Bootstrap Configuration / Reject Missing Runtime Agent Bootstrap Configuration - 当 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration）, 则 Runtime Agent Bootstrap Configuration Load Failed.

<!-- em:section id="user-manual.slice.报告运行时代理已启动（ReportRuntimeAgentStarted）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ReportRuntimeAgentStarted" -->
#### 4.13.32 报告运行时代理已启动（Report Runtime Agent Started）/FLP-DOC-SECTION-161

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 运行时代理（Runtime Agent） |
| 业务对象 | 运行时代理生命周期（Runtime Agent Lifecycle） |
| 成功结果 | Runtime Agent Started, 状态: 已启动（Started） |

- 打开已配置的系统入口。
- 按有效业务数据提交 报告运行时代理已启动（Report Runtime Agent Started）。
- 确认Runtime Agent Started，并确认状态变为 已启动（Started）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 报告运行时代理已启动（Report Runtime Agent Started） | runtimeAgentId | UUID | 必填标识 | 来源：RuntimeAgentBootstrapConfigurationLoaded.runtimeAgentId; id, technical |
| 报告运行时代理已启动（Report Runtime Agent Started） | runtimeInfrastructureId | UUID | 必填/按业务规则 | 来源：RuntimeAgentBootstrapConfigurationLoaded.runtimeInfrastructureId |
| 报告运行时代理已启动（Report Runtime Agent Started） | agentVersion | String | 必填/按业务规则 | 来源：RuntimeAgentBootstrapConfigurationLoaded.agentVersion |

<!-- em:section id="user-manual.slice.报告运行时实例自检已通过（ReportRuntimeInstanceSelfCheckPassed）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ReportRuntimeInstanceSelfCheckPassed" -->
#### 4.13.33 报告运行时实例自检已通过（Report Runtime Instance Self Check Passed）/FLP-DOC-SECTION-162

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 运行时代理（Runtime Agent） |
| 业务对象 | 运行时代理生命周期（Runtime Agent Lifecycle） |
| 成功结果 | Runtime Instance Self Check Passed, 状态: 就绪（Ready） |

- 打开已配置的系统入口。
- 按有效业务数据提交 报告运行时实例自检已通过（Report Runtime Instance Self Check Passed）。
- 确认Runtime Instance Self Check Passed，并确认状态变为 就绪（Ready）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 报告运行时实例自检已通过（Report Runtime Instance Self Check Passed） | runtimeAgentId | UUID | 必填标识 | 来源：RuntimeAgentStarted.runtimeAgentId; id, technical |
| 报告运行时实例自检已通过（Report Runtime Instance Self Check Passed） | runtimeInfrastructureId | UUID | 必填/按业务规则 | 来源：RuntimeAgentStarted.runtimeInfrastructureId |
| 报告运行时实例自检已通过（Report Runtime Instance Self Check Passed） | agentVersion | String | 必填/按业务规则 | 来源：RuntimeAgentStarted.agentVersion |
| 报告运行时实例自检已通过（Report Runtime Instance Self Check Passed） | runtimeAgentSelfCheckPassed | Boolean | 必填/按业务规则 | - |
| 报告运行时实例自检已通过（Report Runtime Instance Self Check Passed） | configurationLoaded | Boolean | 必填/按业务规则 | - |
| 报告运行时实例自检已通过（Report Runtime Instance Self Check Passed） | secretStoreAccessible | Boolean | 必填/按业务规则 | - |
| 报告运行时实例自检已通过（Report Runtime Instance Self Check Passed） | runtimeEngineAdapterReady | Boolean | 必填/按业务规则 | - |
| 报告运行时实例自检已通过（Report Runtime Instance Self Check Passed） | modelRepositoryClientReady | Boolean | 必填/按业务规则 | - |
| 报告运行时实例自检已通过（Report Runtime Instance Self Check Passed） | localDatasetBindingStoreReady | Boolean | 必填/按业务规则 | - |
| 报告运行时实例自检已通过（Report Runtime Instance Self Check Passed） | workingDirectoryWritable | Boolean | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.运行时代理生命周期目录（RuntimeAgentLifecycleCatalog）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/RuntimeAgentLifecycleCatalog" -->
#### 4.13.34 运行时代理生命周期目录（Runtime Agent Lifecycle Catalog）/FLP-DOC-SECTION-163

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 运行时代理生命周期（Runtime Agent Lifecycle） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 运行时代理生命周期目录（Runtime Agent Lifecycle Catalog）。
- 确认预期业务结果。
- 查看 运行时代理生命周期目录（Runtime Agent Lifecycle Catalog） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.报告运行时实例已连接（ReportRuntimeInstanceConnected）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ReportRuntimeInstanceConnected" -->
#### 4.13.35 报告运行时实例已连接（Report Runtime Instance Connected）/FLP-DOC-SECTION-164

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | Report Runtime Instance Connected After Self Check |
| 业务对象 | 代理运行时基础设施连接（Agent Runtime Infrastructure Connection） |
| 成功结果 | Agent Runtime Connection Report Failed, Agent Runtime Connection Established, 状态: 已连接（Connected） |

- 打开已配置的系统入口。
- 按有效业务数据提交 报告运行时实例已连接（Report Runtime Instance Connected）。
- 确认Agent Runtime Connection Report Failed, Agent Runtime Connection Established，并确认状态变为 已连接（Connected）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 报告运行时实例已连接（Report Runtime Instance Connected） | runtimeInfrastructureId | UUID | 必填标识 | 来源：RuntimeInstanceSelfCheckPassed.runtimeInfrastructureId; id, technical |
| 报告运行时实例已连接（Report Runtime Instance Connected） | runtimeAgentId | UUID | 必填/按业务规则 | 来源：RuntimeInstanceSelfCheckPassed.runtimeAgentId |
| 报告运行时实例已连接（Report Runtime Instance Connected） | runtimeAgentEndpoint | String | 必填/按业务规则 | - |
| 报告运行时实例已连接（Report Runtime Instance Connected） | endpointScope | String | 必填/按业务规则 | - |
| 报告运行时实例已连接（Report Runtime Instance Connected） | runtimePlatformConnectionReady | Boolean | 必填/按业务规则 | - |
| 报告运行时实例已连接（Report Runtime Instance Connected） | platformApiReachable | Boolean | 必填/按业务规则 | - |
| 报告运行时实例已连接（Report Runtime Instance Connected） | agentAuthenticationSucceeded | Boolean | 必填/按业务规则 | - |
| 报告运行时实例已连接（Report Runtime Instance Connected） | controlChannelEstablished | Boolean | 必填/按业务规则 | - |
| 报告运行时实例已连接（Report Runtime Instance Connected） | heartbeatAccepted | Boolean | 必填/按业务规则 | - |

**注意事项**

- Runtime Agent Platform Connection Reporting Outcome / 报告运行时实例已连接（Report Runtime Instance Connected） - 当 报告运行时实例已连接（Report Runtime Instance Connected）, 则 Agent Runtime Connection Established.
- Runtime Agent Platform Connection Reporting Outcome / Report Runtime Instance Connection Failed - 当 报告运行时实例已连接（Report Runtime Instance Connected）, 则 Agent Runtime Connection Report Failed.

<!-- em:section id="user-manual.slice.代理运行时基础设施连接目录（AgentRuntimeInfrastructureConnectionCatalog）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/AgentRuntimeInfrastructureConnectionCatalog" -->
#### 4.13.36 代理运行时基础设施连接目录（Agent Runtime Infrastructure Connection Catalog）/FLP-DOC-SECTION-165

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 代理运行时基础设施连接（Agent Runtime Infrastructure Connection） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 代理运行时基础设施连接目录（Agent Runtime Infrastructure Connection Catalog）。
- 确认预期业务结果。
- 查看 代理运行时基础设施连接目录（Agent Runtime Infrastructure Connection Catalog） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.代理运行时遥测最新（AgentRuntimeTelemetryLatest）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/AgentRuntimeTelemetryLatest" -->
#### 4.13.37 代理运行时遥测最新（Agent Runtime Telemetry Latest）/FLP-DOC-SECTION-166

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 代理运行时遥测（Agent Runtime Telemetry） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 代理运行时遥测最新（Agent Runtime Telemetry Latest）。
- 确认预期业务结果。
- 查看 代理运行时遥测最新（Agent Runtime Telemetry Latest） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.报告代理运行时节点清单（ReportAgentRuntimeNodeInventory）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/ReportAgentRuntimeNodeInventory" -->
#### 4.13.38 报告代理运行时节点清单（Report Agent Runtime Node Inventory）/FLP-DOC-SECTION-167

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | Report Node Inventory When Inventory Changed |
| 业务对象 | 代理运行时节点清单（Agent Runtime Node Inventory） |
| 成功结果 | 代理运行时节点清单已报告（Agent Runtime Node Inventory Reported）, 状态: 已报告（Reported） |

- 打开已配置的系统入口。
- 按有效业务数据提交 报告代理运行时节点清单（Report Agent Runtime Node Inventory）。
- 确认代理运行时节点清单已报告（Agent Runtime Node Inventory Reported），并确认状态变为 已报告（Reported）。
- 在相关状态或审计视图中确认操作结果。

| 命令 | 字段 | 类型 | 填写要求 | 示例/规则 |
| --- | --- | --- | --- | --- |
| 报告代理运行时节点清单（Report Agent Runtime Node Inventory） | runtimeNodeInventoryReportId | UUID | 必填标识 | id, generated, technical |
| 报告代理运行时节点清单（Report Agent Runtime Node Inventory） | organizationId | UUID | 必填/按业务规则 | - |
| 报告代理运行时节点清单（Report Agent Runtime Node Inventory） | runtimeInfrastructureId | UUID | 必填/按业务规则 | - |
| 报告代理运行时节点清单（Report Agent Runtime Node Inventory） | runtimeAgentId | UUID | 必填/按业务规则 | - |
| 报告代理运行时节点清单（Report Agent Runtime Node Inventory） | runtimeNodeName | String | 必填/按业务规则 | - |
| 报告代理运行时节点清单（Report Agent Runtime Node Inventory） | infrastructureNodeId | String | 必填/按业务规则 | - |
| 报告代理运行时节点清单（Report Agent Runtime Node Inventory） | runtimeNodeRole | String | 必填/按业务规则 | - |
| 报告代理运行时节点清单（Report Agent Runtime Node Inventory） | nodeReady | Boolean | 必填/按业务规则 | - |
| 报告代理运行时节点清单（Report Agent Runtime Node Inventory） | runtimeEngineVersion | String | 必填/按业务规则 | - |
| 报告代理运行时节点清单（Report Agent Runtime Node Inventory） | containerEngineVersion | String | 必填/按业务规则 | - |
| 报告代理运行时节点清单（Report Agent Runtime Node Inventory） | operatingSystem | String | 必填/按业务规则 | - |
| 报告代理运行时节点清单（Report Agent Runtime Node Inventory） | architecture | String | 必填/按业务规则 | - |
| 报告代理运行时节点清单（Report Agent Runtime Node Inventory） | inventoryHash | String | 必填/按业务规则 | - |

<!-- em:section id="user-manual.slice.代理运行时节点清单目录（AgentRuntimeNodeInventoryCatalog）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/AgentRuntimeNodeInventoryCatalog" -->
#### 4.13.39 代理运行时节点清单目录（Agent Runtime Node Inventory Catalog）/FLP-DOC-SECTION-168

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 代理运行时节点清单（Agent Runtime Node Inventory） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 代理运行时节点清单目录（Agent Runtime Node Inventory Catalog）。
- 确认预期业务结果。
- 查看 代理运行时节点清单目录（Agent Runtime Node Inventory Catalog） 中的最新展示数据。

该能力尚未明确建模输入字段。

<!-- em:section id="user-manual.slice.代理运行时节点资源最新（AgentRuntimeNodeResourceLatest）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations/slice/AgentRuntimeNodeResourceLatest" -->
#### 4.13.40 代理运行时节点资源最新（Agent Runtime Node Resource Latest）/FLP-DOC-SECTION-169

| 项目 | 说明 |
| --- | --- |
| 入口 | 系统流程/入口未明确 |
| 角色/触发条件 | 系统 |
| 业务对象 | 代理运行时节点资源遥测（Agent Runtime Node Resource Telemetry） |
| 成功结果 | 尚未明确 |

- 打开已配置的系统入口。
- 按有效业务数据提交 代理运行时节点资源最新（Agent Runtime Node Resource Latest）。
- 确认预期业务结果。
- 查看 代理运行时节点资源最新（Agent Runtime Node Resource Latest） 中的最新展示数据。

该能力尚未明确建模输入字段。

## 五、数据视图与查询/FLP-DOC-DVQ

| Read Model | 使用位置 | 查询/标识 | 更新来源 |
| --- | --- | --- | --- |
| 组织目录（Organization Directory） | 组织管理（Organization Management） / 组织目录（Organization Directory） | 标识: organizationId; 列表视图 | 组织已注册（Organization Registered）, 组织已激活（Organization Activated）, 组织已停用（Organization Deactivated）, 组织已重新激活（Organization Reactivated）, 数据集已批准用于训练（Dataset Approved For Training） |
| 联邦概览（Federation Overview） | 联邦管理（Federation Management） / 联邦概览（Federation Overview） | 标识: federationId; 列表视图 | 联邦已创建（Federation Created）, 联邦已激活（Federation Activated）, 联邦已暂停（Federation Suspended）, 联邦已重新激活（Federation Reactivated）, 参与者已邀请（Participant Invited）, 参与者已加入（Participant Joined）, 参与者已拒绝（Participant Rejected）, 参与者邀请已撤销（Participant Invitation Revoked）, 参与者已暂停（Participant Suspended）, 参与者已移除（Participant Removed）, 运行时身份已激活（Runtime Identity Activated）, 运行时身份已撤销（Runtime Identity Revoked）, 训练作业已提交（Training Job Submitted） |
| 联邦成员资格目录（Federation Membership Directory） | 联邦管理（Federation Management） / 联邦成员资格目录（Federation Membership Directory） | 标识: federationId, organizationId; 列表视图 | 联邦已创建（Federation Created）, 参与者已邀请（Participant Invited）, 参与者已加入（Participant Joined）, 参与者已拒绝（Participant Rejected）, 参与者邀请已撤销（Participant Invitation Revoked）, 参与者已暂停（Participant Suspended）, 参与者已移除（Participant Removed）, 组织已注册（Organization Registered）, 组织已激活（Organization Activated） |
| 字典目录（Dictionary Catalog） | 字典维护（Dictionary Maintenance） / 字典目录（Dictionary Catalog） | 标识: dictionaryId; 查询: dictionaryCode; 列表视图 | 字典已注册（Dictionary Registered）, 字典已更新（Dictionary Updated）, 字典已归档（Dictionary Archived） |
| 字典值目录（Dictionary Value Catalog） | 字典维护（Dictionary Maintenance） / 字典值目录（Dictionary Value Catalog） | 标识: dictionaryValueId; 查询: dictionaryCode, active; 列表视图 | 字典值已添加（Dictionary Value Added）, 字典值已停用（Dictionary Value Disabled）, 字典值已启用（Dictionary Value Enabled） |
| 暂存文件目录（Staged File Catalog） | 文件上传（File Upload） / 暂存文件目录（Staged File Catalog） | 标识: stagedFileId; 查询: purpose; 列表视图 | 文件上传已暂存（File Upload Staged）, 暂存文件已消费（Staged File Consumed）, 暂存文件已丢弃（Staged File Discarded）, 暂存文件已过期（Staged File Expired） |
| 运行时基础设施包目录（Runtime Infrastructure Package Catalog） | 运行时预配（Runtime Provisioning） / 运行时基础设施包目录（Runtime Infrastructure Package Catalog） | 标识: runtimeInfrastructurePackageId; 列表视图 | 运行时基础设施包已注册（Runtime Infrastructure Package Registered） |
| 运行时安装计划目录（Runtime Installation Plan Catalog） | 运行时预配（Runtime Provisioning） / 运行时安装计划目录（Runtime Installation Plan Catalog） | 标识: runtimeInstallationPlanId; 列表视图 | 组织已注册（Organization Registered）, 运行时基础设施包已注册（Runtime Infrastructure Package Registered）, 运行时安装计划已创建（Runtime Installation Plan Created）, 运行时基础设施已计划（Runtime Infrastructure Planned）, 运行时基础设施已注册（Runtime Infrastructure Registered）, 运行时基础设施已验证（Runtime Infrastructure Verified）, 运行时基础设施验证失败（Runtime Infrastructure Verification Failed）, 运行时代理安装成功（Runtime Agent Installation Succeeded）, 运行时代理安装失败（Runtime Agent Installation Failed）, 运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）, 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed）, 运行时连接已建立（Runtime Connection Established） |
| 运行时安装指南（Runtime Installation Guide） | 运行时预配（Runtime Provisioning） / 运行时安装指南（Runtime Installation Guide） | 标识: runtimeInstallationPlanId; 列表视图 | 组织已注册（Organization Registered）, 运行时基础设施包已注册（Runtime Infrastructure Package Registered）, 运行时安装计划已创建（Runtime Installation Plan Created） |
| 运行时代理端点目录（Runtime Agent Endpoint Catalog） | 运行时预配（Runtime Provisioning） / 运行时代理端点目录（Runtime Agent Endpoint Catalog） | 标识: runtimeAgentId; 列表视图 | 运行时连接已建立（Runtime Connection Established）, 运行时身份已激活（Runtime Identity Activated）, 运行时身份已撤销（Runtime Identity Revoked）, 已检测到运行时代理离线（Runtime Agent Offline Detected）, 运行时代理已恢复（Runtime Agent Recovered） |
| 运行时基础设施访问视图（Runtime Infrastructure Access View） | 运行时预配（Runtime Provisioning） / 运行时基础设施访问视图（Runtime Infrastructure Access View） | 标识: runtimeInfrastructureId; 列表视图 | 组织已注册（Organization Registered）, 运行时基础设施包已注册（Runtime Infrastructure Package Registered）, 运行时安装计划已创建（Runtime Installation Plan Created）, 运行时基础设施已计划（Runtime Infrastructure Planned）, 运行时基础设施已注册（Runtime Infrastructure Registered）, 运行时基础设施已验证（Runtime Infrastructure Verified）, 运行时基础设施验证失败（Runtime Infrastructure Verification Failed）, 运行时代理安装成功（Runtime Agent Installation Succeeded）, 运行时代理安装失败（Runtime Agent Installation Failed）, 运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）, 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed）, 运行时连接已建立（Runtime Connection Established） |
| 当前推荐的特征架构目录（Current Recommended Feature Schema Catalog） | 数据集治理（Dataset Governance） / 当前推荐的特征架构目录（Current Recommended Feature Schema Catalog） | 标识: featureDomain; 列表视图 | 已标记当前推荐特征架构版本（Current Recommended Feature Schema Version Marked） |
| 特征架构目录（Feature Schema Catalog） | 数据集治理（Dataset Governance） / 特征架构目录（Feature Schema Catalog） | 标识: featureSchemaId; 列表视图 | 特征架构已定义（Feature Schema Defined）, 特征架构已发布（Feature Schema Published）, 特征架构已弃用（Feature Schema Deprecated）, 特征架构已停用（Feature Schema Retired）, 特征架构版本已取代（Feature Schema Version Superseded）, 已标记当前推荐特征架构版本（Current Recommended Feature Schema Version Marked） |
| 运行时数据集元数据目录（Runtime Dataset Metadata Catalog） | 数据集治理（Dataset Governance） / 运行时数据集元数据目录（Runtime Dataset Metadata Catalog） | 标识: metadataReportId; 列表视图 | 组织已注册（Organization Registered）, 特征架构已定义（Feature Schema Defined）, 数据集元数据已报告（Dataset Metadata Reported）, 数据集元数据已重新分析（Dataset Metadata Reprofiled）, 代理数据集分析失败（Agent Dataset Profiling Failed）, 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed） |
| 模型制品目录（Model Artifact Catalog） | 模型仓库（Model Repository） / 模型制品目录（Model Artifact Catalog） | 标识: modelId; 列表视图 | 训练作业已创建（Training Job Created）, 模型工件已注册（Model Artifact Registered）, 全局模型已更新（Global Model Updated） |
| 训练运行配置目录（Training Run Configuration Catalog） | 训练编排（Training Orchestration） / 训练运行配置目录（Training Run Configuration Catalog） | 标识: trainingRunConfigurationId; 列表视图 | 联邦已创建（Federation Created）, 特征架构已定义（Feature Schema Defined）, 训练运行配置已定义（Training Run Configuration Defined）, 训练运行配置已更新（Training Run Configuration Updated）, 训练运行配置已锁定（Training Run Configuration Locked） |
| 训练参与者资格（Training Participant Eligibility） | 训练编排（Training Orchestration） / 训练参与者资格（Training Participant Eligibility） | 标识: trainingJobId; 列表视图 | 联邦已创建（Federation Created）, 组织已注册（Organization Registered）, 特征架构已定义（Feature Schema Defined）, 训练作业已创建（Training Job Created）, 训练作业已提交（Training Job Submitted）, 参与者已加入（Participant Joined）, 参与者已暂停（Participant Suspended）, 参与者已移除（Participant Removed）, 运行时身份已激活（Runtime Identity Activated）, 运行时身份已撤销（Runtime Identity Revoked）, 运行时能力已检测（Runtime Capabilities Detected）, 运行时连接已建立（Runtime Connection Established）, 已检测到运行时代理离线（Runtime Agent Offline Detected）, 运行时代理已恢复（Runtime Agent Recovered）, 数据集已声明（Dataset Declared）, 数据集元数据已报告（Dataset Metadata Reported）, 数据集合约已验证（Dataset Contract Validated）, 数据集已批准用于训练（Dataset Approved For Training）, 数据集训练批准已撤销（Dataset Training Approval Revoked）, 代理数据集访问已验证（Agent Dataset Access Validated） |
| 训练作业仪表板（Training Job Dashboard） | 训练编排（Training Orchestration） / 训练作业仪表板（Training Job Dashboard） | 标识: trainingJobId; 列表视图 | 联邦已创建（Federation Created）, 特征架构已定义（Feature Schema Defined）, 训练作业已创建（Training Job Created）, 训练作业已提交（Training Job Submitted）, 训练作业已暂停（Training Job Paused）, 训练作业已恢复（Training Job Resumed）, 训练作业已取消（Training Job Canceled）, 训练轮次已启动（Training Round Started）, 训练轮次已完成（Training Round Completed）, 训练作业已完成（Training Job Completed） |
| 训练轮次进度（Training Round Progress） | 训练编排（Training Orchestration） / 训练轮次进度（Training Round Progress） | 标识: trainingJobId, roundId; 列表视图 | 训练作业已创建（Training Job Created）, 特征架构已定义（Feature Schema Defined）, 训练轮次参与者已选择（Training Round Participants Selected）, 训练轮次参与者选择失败（Training Round Participant Selection Failed）, 训练轮次已启动（Training Round Started）, 训练轮次启动失败（Training Round Start Failed）, 参与者执行计划已生成（Participant Execution Plan Generated）, 参与者执行计划已分发（Participant Execution Plan Dispatched）, 轮次执行已启动（Round Execution Started）, 轮次执行启动失败（Round Execution Start Failed）, Round Execution Start Retry Started, 轮次执行启动重试失败（Round Execution Start Retry Failed）, 轮次执行已完成（Round Execution Completed）, 轮次执行失败（Round Execution Failed）, Round Execution Runtime Retry Started, 轮次执行运行时重试失败（Round Execution Runtime Retry Failed）, 模型更新提交已接收（Model Update Submission Received）, 模型更新提交已接受（Model Update Submission Accepted）, 模型更新提交已拒绝（Model Update Submission Rejected）, 已请求安全聚合（Secure Aggregation Requested）, 全局模型已更新（Global Model Updated）, 全局模型评估已提交（Global Model Evaluation Submitted）, 训练轮次已完成（Training Round Completed）, 训练轮次失败（Training Round Failed） |
| 模型目录（Model Catalog） | 模型生命周期（Model Lifecycle） / 模型目录（Model Catalog） | 标识: modelId; 列表视图 | 训练作业已创建（Training Job Created）, 模型候选已注册（Model Candidate Registered）, 模型评估包已记录（Model Evaluation Package Recorded）, 模型已批准（Model Approved）, 模型已提升到生产环境（Model Promoted To Production）, 模型已回滚（Model Rolled Back）, 模型已退役（Model Retired） |
| 运行时最新遥测（Runtime Telemetry Latest） | 运行时监控（Runtime Monitoring） / 运行时最新遥测（Runtime Telemetry Latest） | 标识: nodeId; 列表视图 | 尚未明确 |
| 运行时节点清单视图（Runtime Node Inventory View） | 运行时监控（Runtime Monitoring） / 运行时节点清单视图（Runtime Node Inventory View） | 标识: nodeId; 列表视图 | 组织已注册（Organization Registered）, 运行时安装计划已创建（Runtime Installation Plan Created）, 运行时基础设施已注册（Runtime Infrastructure Registered）, 运行时节点清单已报告（Runtime Node Inventory Reported） |
| 运行时节点最新资源（Runtime Node Resource Latest） | 运行时监控（Runtime Monitoring） / 运行时节点最新资源（Runtime Node Resource Latest） | 标识: nodeId; 列表视图 | 尚未明确 |
| 运行时健康仪表板（Runtime Health Dashboard） | 运行时监控（Runtime Monitoring） / 运行时健康仪表板（Runtime Health Dashboard） | 标识: nodeId; 列表视图 | 联邦已创建（Federation Created）, 训练作业已创建（Training Job Created）, 已检测到运行时代理离线（Runtime Agent Offline Detected）, 运行时代理已恢复（Runtime Agent Recovered）, 已检测到运行时节点资源压力（Runtime Node Resource Pressure Detected）, 运行时节点容量已更改（Runtime Node Capacity Changed）, 运行时节点清单已报告（Runtime Node Inventory Reported）, 训练警报已发出（Training Alert Raised） |
| 训练告警目录（Training Alert Catalog） | 运行时监控（Runtime Monitoring） / 训练告警目录（Training Alert Catalog） | 标识: alertId; 列表视图 | 运行时节点清单已报告（Runtime Node Inventory Reported）, 训练作业已创建（Training Job Created）, 训练警报已发出（Training Alert Raised）, 训练警报已确认（Training Alert Acknowledged）, 训练警报已解决（Training Alert Resolved） |
| 审计记录日志（Audit Record Log） | 运行时监控（Runtime Monitoring） / 审计记录日志（Audit Record Log） | 标识: auditRecordId; 列表视图 | 审计跟踪已追加（Audit Trail Appended） |
| 运行时标识目录（Runtime Identity Catalog） | 运行时治理（Runtime Governance） / 运行时标识目录（Runtime Identity Catalog） | 标识: runtimeId; 列表视图 | 组织已注册（Organization Registered）, 运行时身份已激活（Runtime Identity Activated）, 运行时身份已撤销（Runtime Identity Revoked） |
| 运行时能力目录（Runtime Capability Catalog） | 运行时治理（Runtime Governance） / 运行时能力目录（Runtime Capability Catalog） | 标识: runtimeId; 列表视图 | 运行时能力已检测（Runtime Capabilities Detected） |
| 安全聚合会话目录（Secure Aggregation Session Catalog） | 安全聚合（Secure Aggregation） / 安全聚合会话目录（Secure Aggregation Session Catalog） | 标识: secureAggregationSessionId; 列表视图 | 安全聚合会话已创建（Secure Aggregation Session Created）, 安全聚合参与者已选择（Secure Aggregation Participants Selected）, 同态加密上下文已准备（Homomorphic Encryption Context Prepared）, 已接收加密模型更新（Encrypted Model Update Received）, 安全聚合已完成（Secure Aggregation Completed）, 安全聚合失败（Secure Aggregation Failed） |
| 运行时数据集绑定目录（Runtime Dataset Binding Catalog） | 运行时代理操作（Runtime Agent Operations） / 运行时数据集绑定目录（Runtime Dataset Binding Catalog） | 标识: runtimeDatasetBindingId; 列表视图 | 数据集已声明（Dataset Declared）, 运行时数据集绑定已配置（Runtime Dataset Binding Configured） |
| 代理数据集访问验证目录（Agent Dataset Access Validation Catalog） | 运行时代理操作（Runtime Agent Operations） / 代理数据集访问验证目录（Agent Dataset Access Validation Catalog） | 标识: datasetAccessValidationId; 列表视图 | 数据集已声明（Dataset Declared）, 代理数据集访问已验证（Agent Dataset Access Validated）, 代理数据集访问验证失败（Agent Dataset Access Validation Failed）, 代理数据集访问已重新验证（Agent Dataset Access Revalidated）, 代理数据集访问重新验证失败（Agent Dataset Access Revalidation Failed） |
| 数据集能力（Dataset Capability） | 运行时代理操作（Runtime Agent Operations） / 数据集能力（Dataset Capability） | 标识: datasetId; 列表视图 | 组织已注册（Organization Registered）, 特征架构已定义（Feature Schema Defined）, 数据集已声明（Dataset Declared）, 代理数据集元数据已报告（Agent Dataset Metadata Reported）, 代理数据集分析失败（Agent Dataset Profiling Failed）, 代理数据集已重新分析（Agent Dataset Reprofiled）, 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed）, 数据集合约已验证（Dataset Contract Validated）, 数据集合约验证失败（Dataset Contract Validation Failed）, 数据集合约已重新验证（Dataset Contract Revalidated）, 数据集合约重新验证失败（Dataset Contract Revalidation Failed）, 数据集已拒绝用于训练（Dataset Rejected For Training）, 数据集已批准用于训练（Dataset Approved For Training）, 数据集训练批准已撤销（Dataset Training Approval Revoked） |
| 数据集就绪状态（Dataset Readiness） | 运行时代理操作（Runtime Agent Operations） / 数据集就绪状态（Dataset Readiness） | 标识: datasetId; 列表视图 | 组织已注册（Organization Registered）, 特征架构已定义（Feature Schema Defined）, 数据集已声明（Dataset Declared）, 代理数据集元数据已报告（Agent Dataset Metadata Reported）, 代理数据集分析失败（Agent Dataset Profiling Failed）, 代理数据集已重新分析（Agent Dataset Reprofiled）, 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed）, 数据集合约已验证（Dataset Contract Validated）, 数据集合约验证失败（Dataset Contract Validation Failed）, 数据集合约已重新验证（Dataset Contract Revalidated）, 数据集合约重新验证失败（Dataset Contract Revalidation Failed）, 数据集已拒绝用于训练（Dataset Rejected For Training）, 数据集已批准用于训练（Dataset Approved For Training）, 数据集训练批准已撤销（Dataset Training Approval Revoked）, 代理数据集访问已验证（Agent Dataset Access Validated）, 代理数据集访问验证失败（Agent Dataset Access Validation Failed）, 代理数据集访问已重新验证（Agent Dataset Access Revalidated）, 代理数据集访问重新验证失败（Agent Dataset Access Revalidation Failed）, 运行时连接已建立（Runtime Connection Established）, 已检测到运行时代理离线（Runtime Agent Offline Detected）, 运行时代理已恢复（Runtime Agent Recovered） |
| 轮次执行目录（Round Execution Catalog） | 运行时代理操作（Runtime Agent Operations） / 轮次执行目录（Round Execution Catalog） | 标识: roundExecutionId; 列表视图 | 参与者执行计划已分发（Participant Execution Plan Dispatched）, 执行计划已接收（Execution Plan Received）, 执行计划已接受（Execution Plan Accepted）, 执行计划已拒绝（Execution Plan Rejected）, 轮次执行已启动（Round Execution Started）, 轮次执行启动失败（Round Execution Start Failed）, 运行时引擎作业已观察（Runtime Engine Job Observed）, Round Execution Start Retry Started, 轮次执行启动重试失败（Round Execution Start Retry Failed）, 轮次执行已完成（Round Execution Completed）, 轮次执行失败（Round Execution Failed）, Round Execution Runtime Retry Started, 轮次执行运行时重试失败（Round Execution Runtime Retry Failed）, Agent Local Model Update Submitted, Runtime Engine Job Released, Runtime Engine Job Release Failed Or Skipped, Runtime Engine Job Release Failed Or Skipped After Start Failure, Runtime Engine Job Release Failed Or Skipped After Retry, Runtime Engine Job Release Failed Or Skipped After Runtime Retry |
| 运行时代理生命周期目录（Runtime Agent Lifecycle Catalog） | 运行时代理操作（Runtime Agent Operations） / 运行时代理生命周期目录（Runtime Agent Lifecycle Catalog） | 标识: runtimeAgentId; 列表视图 | Runtime Agent Bootstrap Configuration Loaded, Runtime Agent Bootstrap Configuration Load Failed, Runtime Agent Started, Runtime Instance Self Check Passed |
| 代理运行时基础设施连接目录（Agent Runtime Infrastructure Connection Catalog） | 运行时代理操作（Runtime Agent Operations） / 代理运行时基础设施连接目录（Agent Runtime Infrastructure Connection Catalog） | 标识: runtimeInfrastructureId; 列表视图 | Agent Runtime Connection Established, Agent Runtime Connection Report Failed |
| 代理运行时遥测最新（Agent Runtime Telemetry Latest） | 运行时代理操作（Runtime Agent Operations） / 代理运行时遥测最新（Agent Runtime Telemetry Latest） | 标识: nodeId; 列表视图 | 尚未明确 |
| 代理运行时节点清单目录（Agent Runtime Node Inventory Catalog） | 运行时代理操作（Runtime Agent Operations） / 代理运行时节点清单目录（Agent Runtime Node Inventory Catalog） | 标识: runtimeNodeInventoryReportId; 列表视图 | 组织已注册（Organization Registered）, 代理运行时节点清单已报告（Agent Runtime Node Inventory Reported） |
| 代理运行时节点资源最新（Agent Runtime Node Resource Latest） | 运行时代理操作（Runtime Agent Operations） / 代理运行时节点资源最新（Agent Runtime Node Resource Latest） | 标识: nodeId; 列表视图 | 尚未明确 |

## 六、规则与异常/FLP-DOC-RAE

- 组织管理（Organization Management） / 注册组织（Register Organization）: 组织名称唯一（Organization Name Unique） / 拒绝重复组织（Reject Duplicate Organization） - 当 注册组织（Register Organization）, 则 拒绝: 组织已存在.
- 联邦管理（Federation Management） / 创建联邦（Create Federation）: 联邦名称唯一（Federation Name Unique） / 拒绝重复联邦（Reject Duplicate Federation） - 当 创建联邦（Create Federation）, 则 拒绝: 联邦已存在.
- 联邦管理（Federation Management） / 批准参与者（Approve Participant）: 仅已邀请的激活组织加入联邦（Only Invited Active Organizations Join Federation） / 批准已邀请的参与者（Approve Invited Participant） - 当 批准参与者（Approve Participant）, 则 参与者已加入（Participant Joined）.
- 联邦管理（Federation Management） / 批准参与者（Approve Participant）: 仅已邀请的激活组织加入联邦（Only Invited Active Organizations Join Federation） / 拒绝未邀请参与者的审批（Reject Uninvited Participant Approval） - 当 批准参与者（Approve Participant）, 则 拒绝: 需要参与者邀请.
- 字典维护（Dictionary Maintenance） / 注册字典（Register Dictionary）: 字典代码唯一（Dictionary Code Unique） / 拒绝重复字典（Reject Duplicate Dictionary） - 当 注册字典（Register Dictionary）, 则 拒绝: 字典已存在.
- 文件上传（File Upload） / 暂存文件上传（Stage File Upload）: 在提交业务命令前暂存已上传文件（Uploaded File Is Staged Before Business Command Submission） / 为模型导入暂存上传（Stage Upload For Model Import） - 当 暂存文件上传（Stage File Upload）, 则 文件上传已暂存（File Upload Staged）.
- 文件上传（File Upload） / 标记暂存文件已消费（Mark Staged File Consumed）: 已消费的暂存文件不再可由用户丢弃（Consumed Staged File Is No Longer User Discardable） / 通过模型导入标记文件为已消费（Mark File Consumed By Model Import） - 当 标记暂存文件已消费（Mark Staged File Consumed）, 则 暂存文件已消费（Staged File Consumed）.
- 文件上传（File Upload） / 放弃暂存文件（Discard Staged File）: 仅可丢弃未消费的暂存文件（Only Unconsumed Staged Files Can Be Discarded） / 丢弃未消费的暂存文件（Discard Unconsumed Staged File） - 当 放弃暂存文件（Discard Staged File）, 则 暂存文件已丢弃（Staged File Discarded）.
- 文件上传（File Upload） / 放弃暂存文件（Discard Staged File）: 仅可丢弃未消费的暂存文件（Only Unconsumed Staged Files Can Be Discarded） / 拒绝消费后丢弃（Reject Discard After Consumption） - 当 放弃暂存文件（Discard Staged File）, 则 拒绝: 暂存文件已被消费.
- 文件上传（File Upload） / 使暂存文件过期（Expire Staged File）: 已过期的暂存文件无法被消费（Expired Staged File Cannot Be Consumed） / 未消费暂存文件过期（Expire Unconsumed Staged File） - 当 使暂存文件过期（Expire Staged File）, 则 暂存文件已过期（Staged File Expired）.
- 文件上传（File Upload） / 使暂存文件过期（Expire Staged File）: 已过期的暂存文件无法被消费（Expired Staged File Cannot Be Consumed） / 过期后拒绝消费（Reject Consumption After Expiration） - 当 标记暂存文件已消费（Mark Staged File Consumed）, 则 拒绝: 暂存文件已过期.
- 运行时预配（Runtime Provisioning） / 验证运行时基础设施（Verify Runtime Infrastructure）: 运行时基础设施验证结果（Runtime Infrastructure Verification Outcome） / 运行时基础设施验证通过（Runtime Infrastructure Verification Passed） - 当 验证运行时基础设施（Verify Runtime Infrastructure）, 则 运行时基础设施已验证（Runtime Infrastructure Verified）.
- 运行时预配（Runtime Provisioning） / 验证运行时基础设施（Verify Runtime Infrastructure）: 运行时基础设施验证结果（Runtime Infrastructure Verification Outcome） / 运行时基础设施验证失败（Runtime Infrastructure Verification Failed） - 当 验证运行时基础设施（Verify Runtime Infrastructure）, 则 运行时基础设施验证失败（Runtime Infrastructure Verification Failed）.
- 运行时预配（Runtime Provisioning） / 部署运行时代理（Deploy Runtime Agent）: 运行时代理部署结果（Runtime Agent Deployment Outcome） / 运行时代理部署成功（Runtime Agent Deployment Succeeded） - 当 部署运行时代理（Deploy Runtime Agent）, 则 运行时代理安装成功（Runtime Agent Installation Succeeded）.
- 运行时预配（Runtime Provisioning） / 部署运行时代理（Deploy Runtime Agent）: 运行时代理部署结果（Runtime Agent Deployment Outcome） / 运行时代理部署失败（Runtime Agent Deployment Failed） - 当 部署运行时代理（Deploy Runtime Agent）, 则 运行时代理安装失败（Runtime Agent Installation Failed）.
- 运行时预配（Runtime Provisioning） / 重试运行时代理部署（Retry Runtime Agent Deployment）: 运行时代理部署重试结果（Runtime Agent Deployment Retry Outcome） / 部署失败后运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded After Failed Deployment） - 当 重试运行时代理部署（Retry Runtime Agent Deployment）, 则 运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）.
- 运行时预配（Runtime Provisioning） / 重试运行时代理部署（Retry Runtime Agent Deployment）: 运行时代理部署重试结果（Runtime Agent Deployment Retry Outcome） / 离线后运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded After Offline） - 当 重试运行时代理部署（Retry Runtime Agent Deployment）, 则 运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）.
- 运行时预配（Runtime Provisioning） / 重试运行时代理部署（Retry Runtime Agent Deployment）: 运行时代理部署重试结果（Runtime Agent Deployment Retry Outcome） / 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed） - 当 重试运行时代理部署（Retry Runtime Agent Deployment）, 则 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed）.
- 运行时预配（Runtime Provisioning） / 重试运行时代理部署（Retry Runtime Agent Deployment）: 运行时代理部署重试结果（Runtime Agent Deployment Retry Outcome） / 已连接时拒绝运行时代理部署重试（Reject Runtime Agent Deployment Retry While Connected） - 当 重试运行时代理部署（Retry Runtime Agent Deployment）, 则 拒绝: 不允许运行时代理部署重试.
- 数据集治理（Dataset Governance） / 定义特征架构（Define Feature Schema）: 特征架构域版本唯一（Feature Schema Domain Version Unique） / 拒绝重复的特征架构版本（Reject Duplicate Feature Schema Version） - 当 定义特征架构（Define Feature Schema）, 则 拒绝: 特征架构版本已存在.
- 数据集治理（Dataset Governance） / 发布特征架构（Publish Feature Schema）: 仅可发布草稿架构（Only Draft Schemas Can Be Published） / 发布草稿架构（Publish Draft Schema） - 当 发布特征架构（Publish Feature Schema）, 则 特征架构已发布（Feature Schema Published）.
- 数据集治理（Dataset Governance） / 取代特征架构版本（Supersede Feature Schema Version）: 使用较新版本取代已发布的架构（Supersede Published Schema With Newer Version） / 取代较旧版本（Supersede Older Version） - 当 取代特征架构版本（Supersede Feature Schema Version）, 则 特征架构版本已取代（Feature Schema Version Superseded）.
- 数据集治理（Dataset Governance） / 标记当前推荐的特征架构版本（Mark Current Recommended Feature Schema Version）: 每个域仅一个推荐版本（Only One Recommended Version Per Domain） / 标记新的推荐版本（Mark New Recommended Version） - 当 标记当前推荐的特征架构版本（Mark Current Recommended Feature Schema Version）, 则 已标记当前推荐特征架构版本（Current Recommended Feature Schema Version Marked）.
- 模型仓库（Model Repository） / 注册模型制品（Register Model Artifact）: 模型工件必须可拉取且可验证（Model Artifact Must Be Pullable And Verifiable） / 注册可拉取模型工件（Register Pullable Model Artifact） - 当 注册模型制品（Register Model Artifact）, 则 模型工件已注册（Model Artifact Registered）.
- 训练编排（Training Orchestration） / 定义训练运行配置（Define Training Run Configuration）: 训练配置必须可运行（Training Configuration Must Be Runnable） / 定义可运行配置（Define Runnable Configuration） - 当 定义训练运行配置（Define Training Run Configuration）, 则 训练运行配置已定义（Training Run Configuration Defined）.
- 训练编排（Training Orchestration） / 定义训练运行配置（Define Training Run Configuration）: 训练配置必须可运行（Training Configuration Must Be Runnable） / 拒绝空轮次预算（Reject Empty Round Budget） - 当 定义训练运行配置（Define Training Run Configuration）, 则 拒绝: 轮次预算必须为正数.
- 训练编排（Training Orchestration） / 定义训练运行配置（Define Training Run Configuration）: 训练配置必须可运行（Training Configuration Must Be Runnable） / 拒绝无参数的差分隐私（Reject Differential Privacy Without Parameters） - 当 定义训练运行配置（Define Training Run Configuration）, 则 拒绝: 差分隐私参数为必填项.
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
- 训练编排（Training Orchestration） / 评估模型更新提交（Evaluate Model Update Submission）: 已排除受污染的本地更新（Poisoned Local Updates Are Excluded） / 拒绝异常更新（Reject Anomalous Update） - 当 评估模型更新提交（Evaluate Model Update Submission）, 则 模型更新提交已拒绝（Model Update Submission Rejected）.
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
- 运行时代理操作（Runtime Agent Operations） / 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration）: Runtime Agent Startup Bootstrap Configuration / 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration） - 当 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration）, 则 Runtime Agent Bootstrap Configuration Loaded.
- 运行时代理操作（Runtime Agent Operations） / 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration）: Runtime Agent Startup Bootstrap Configuration / Reject Missing Runtime Agent Bootstrap Configuration - 当 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration）, 则 Runtime Agent Bootstrap Configuration Load Failed.
- 运行时代理操作（Runtime Agent Operations） / 报告运行时实例已连接（Report Runtime Instance Connected）: Runtime Agent Platform Connection Reporting Outcome / 报告运行时实例已连接（Report Runtime Instance Connected） - 当 报告运行时实例已连接（Report Runtime Instance Connected）, 则 Agent Runtime Connection Established.
- 运行时代理操作（Runtime Agent Operations） / 报告运行时实例已连接（Report Runtime Instance Connected）: Runtime Agent Platform Connection Reporting Outcome / Report Runtime Instance Connection Failed - 当 报告运行时实例已连接（Report Runtime Instance Connected）, 则 Agent Runtime Connection Report Failed.

## 七、常见问题处理/FLP-DOC-FAQH

- 如操作无法提交，先检查必填字段、业务标识和权限范围。
- 如列表或详情缺少数据，确认来源事件已产生且 Read Model 已刷新。
- 如业务规则拒绝操作，根据规则说明和验收场景修正输入。
- 如外部系统数据不可用，检查集成连通性和重试策略状态。
- 无法解决的问题需携带功能名称、提交命令、预期结果、发生时间和可见错误信息升级处理。
