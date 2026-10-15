# 联邦学习平台（Federation Learning Platform） 测试大纲

<!-- em:section id="document.changeLog" -->
## 变更记录

| 版本 | 日期 | 变更说明 | 作者 |
| --- | --- | --- | --- |
| V0.1.0 | 2026-08-23 | 初始发布 | MEDOL |

## 一、测试目标与范围/FLP-TO-TGAS

- 覆盖 13 个业务上下文、156 个功能能力、39 个 Read Model 和 14 个已建模集成点。
- 在编写详细测试用例之前，定义系统要测什么、怎么划分测试单元、重点场景在哪里，以及覆盖到什么程度算测试设计充分。
- 以模型中的 Slice、Specification、事件、Projection、Automation 和集成为可追溯来源，形成后续测试用例和自动化测试的设计依据。

## 二、测试对象/FLP-TO-TO

| 上下文 | 测试单元 | 业务对象 | 角色/触发条件 | 可观察结果 |
| --- | --- | --- | --- | --- |
| 组织管理（Organization Management） | 注册组织（Register Organization） | 组织（Organization） | 组织管理员（Organization Admin） | 组织已注册（Organization Registered）, 状态: 已注册（Registered） |
| 组织管理（Organization Management） | 激活组织（Activate Organization） | 组织（Organization） | 平台管理员（Platform Admin） | 组织已激活（Organization Activated）, 状态: 已激活（Active） |
| 组织管理（Organization Management） | 停用组织（Deactivate Organization） | 组织（Organization） | 平台管理员（Platform Admin） | 组织已停用（Organization Deactivated）, 状态: 已停用（Deactivated） |
| 组织管理（Organization Management） | 重新激活组织（Reactivate Organization） | 组织（Organization） | 平台管理员（Platform Admin） | 组织已重新激活（Organization Reactivated）, 状态: 已激活（Active） |
| 组织管理（Organization Management） | 组织目录（Organization Directory） | 组织（Organization） | 系统 | 尚未明确 |
| 联邦管理（Federation Management） | 创建联邦（Create Federation） | 联邦（Federation） | 平台管理员（Platform Admin） | 联邦已创建（Federation Created）, 状态: 草稿（Draft） |
| 联邦管理（Federation Management） | 激活联邦（Activate Federation） | 联邦（Federation） | 平台管理员（Platform Admin） | 联邦已激活（Federation Activated）, 状态: 已激活（Active） |
| 联邦管理（Federation Management） | 暂停联邦（Suspend Federation） | 联邦（Federation） | 平台管理员（Platform Admin） | 联邦已暂停（Federation Suspended）, 状态: 已暂停（Suspended） |
| 联邦管理（Federation Management） | 重新激活联邦（Reactivate Federation） | 联邦（Federation） | 平台管理员（Platform Admin） | 联邦已重新激活（Federation Reactivated）, 状态: 已激活（Active） |
| 联邦管理（Federation Management） | 联邦概览（Federation Overview） | 联邦（Federation） | 系统 | 尚未明确 |
| 联邦管理（Federation Management） | 邀请参与者（Invite Participant） | 联邦成员资格（Federation Membership） | 联邦所有者（Federation Owner） | 参与者已邀请（Participant Invited）, 状态: 已邀请（Invited） |
| 联邦管理（Federation Management） | 联邦成员资格目录（Federation Membership Directory） | 联邦成员资格（Federation Membership） | 系统 | 尚未明确 |
| 联邦管理（Federation Management） | 批准参与者（Approve Participant） | 联邦成员资格（Federation Membership） | 治理审核员（Governance Reviewer） | 参与者已加入（Participant Joined）, 状态: 已激活（Active） |
| 联邦管理（Federation Management） | 拒绝参与者（Reject Participant） | 联邦成员资格（Federation Membership） | 治理审核员（Governance Reviewer） | 参与者已拒绝（Participant Rejected）, 状态: 已拒绝（Rejected） |
| 联邦管理（Federation Management） | 撤销参与者邀请（Revoke Participant Invitation） | 联邦成员资格（Federation Membership） | 联邦所有者（Federation Owner） | 参与者邀请已撤销（Participant Invitation Revoked）, 状态: 邀请已撤销（Invitation Revoked） |
| 联邦管理（Federation Management） | 暂停参与者（Suspend Participant） | 联邦成员资格（Federation Membership） | 治理审核员（Governance Reviewer） | 参与者已暂停（Participant Suspended）, 状态: 已暂停（Suspended） |
| 联邦管理（Federation Management） | 移除参与者（Remove Participant） | 联邦成员资格（Federation Membership） | 治理审核员（Governance Reviewer） | 参与者已移除（Participant Removed）, 状态: 已移除（Removed） |
| 字典维护（Dictionary Maintenance） | 注册字典（Register Dictionary） | 字典（Dictionary） | 平台管理员（Platform Admin） | 字典已注册（Dictionary Registered）, 状态: 已注册（Registered） |
| 字典维护（Dictionary Maintenance） | 更新字典（Update Dictionary） | 字典（Dictionary） | 平台管理员（Platform Admin） | 字典已更新（Dictionary Updated） |
| 字典维护（Dictionary Maintenance） | 归档字典（Archive Dictionary） | 字典（Dictionary） | 平台管理员（Platform Admin） | 字典已归档（Dictionary Archived）, 状态: 已归档（Archived） |
| 字典维护（Dictionary Maintenance） | 字典目录（Dictionary Catalog） | 字典（Dictionary） | 系统 | 尚未明确 |
| 字典维护（Dictionary Maintenance） | 添加字典值（Add Dictionary Value） | 字典值（Dictionary Value） | 平台管理员（Platform Admin） | 字典值已添加（Dictionary Value Added）, 状态: 已激活（Active） |
| 字典维护（Dictionary Maintenance） | 停用字典值（Disable Dictionary Value） | 字典值（Dictionary Value） | 平台管理员（Platform Admin） | 字典值已停用（Dictionary Value Disabled）, 状态: 已停用（Disabled） |
| 字典维护（Dictionary Maintenance） | 启用字典值（Enable Dictionary Value） | 字典值（Dictionary Value） | 平台管理员（Platform Admin） | 字典值已启用（Dictionary Value Enabled）, 状态: 已激活（Active） |
| 字典维护（Dictionary Maintenance） | 字典值目录（Dictionary Value Catalog） | 字典值（Dictionary Value） | 系统 | 尚未明确 |
| 文件上传（File Upload） | 暂存文件上传（Stage File Upload） | 暂存文件（Staged File） | 平台用户（Platform User） | 文件上传已暂存（File Upload Staged）, 状态: 已暂存（Staged） |
| 文件上传（File Upload） | 标记暂存文件已消费（Mark Staged File Consumed） | 暂存文件（Staged File） | 平台服务（Platform Service） | 暂存文件已消费（Staged File Consumed）, 状态: 已消费（Consumed） |
| 文件上传（File Upload） | 放弃暂存文件（Discard Staged File） | 暂存文件（Staged File） | 平台用户（Platform User） | 暂存文件已丢弃（Staged File Discarded）, 状态: 已放弃（Discarded） |
| 文件上传（File Upload） | 使暂存文件过期（Expire Staged File） | 暂存文件（Staged File） | 保留期结束后暂存文件过期（Expire Staged File When Retention Elapsed） | 暂存文件已过期（Staged File Expired）, 状态: 已过期（Expired） |
| 文件上传（File Upload） | 暂存文件目录（Staged File Catalog） | 暂存文件（Staged File） | 系统 | 尚未明确 |
| 运行时预配（Runtime Provisioning） | 注册运行时基础设施包（Register Runtime Infrastructure Package） | 运行时基础设施包（Runtime Infrastructure Package） | 平台管理员（Platform Admin） | 运行时基础设施包已注册（Runtime Infrastructure Package Registered）, 状态: 已注册（Registered） |
| 运行时预配（Runtime Provisioning） | 运行时基础设施包目录（Runtime Infrastructure Package Catalog） | 运行时基础设施包（Runtime Infrastructure Package） | 系统 | 尚未明确 |
| 运行时预配（Runtime Provisioning） | 创建运行时安装计划（Create Runtime Installation Plan） | 运行时安装计划（Runtime Installation Plan） | 平台管理员（Platform Admin） | 运行时安装计划已创建（Runtime Installation Plan Created）, 状态: 已规划（Planned） |
| 运行时预配（Runtime Provisioning） | 运行时安装计划目录（Runtime Installation Plan Catalog） | 运行时安装计划（Runtime Installation Plan） | 系统 | 尚未明确 |
| 运行时预配（Runtime Provisioning） | 运行时安装指南（Runtime Installation Guide） | 运行时安装计划（Runtime Installation Plan） | 运行时安装程序（Runtime Installer） | 尚未明确 |
| 运行时预配（Runtime Provisioning） | 规划运行时基础设施（Plan Runtime Infrastructure） | 运行时基础设施（Runtime Infrastructure） | 平台管理员（Platform Admin） | 运行时基础设施已计划（Runtime Infrastructure Planned）, 状态: 已规划（Planned） |
| 运行时预配（Runtime Provisioning） | 注册运行时基础设施（Register Runtime Infrastructure） | 运行时基础设施（Runtime Infrastructure） | 运行时安装程序（Runtime Installer） | 运行时基础设施已注册（Runtime Infrastructure Registered）, 状态: 已注册（Registered） |
| 运行时预配（Runtime Provisioning） | 验证运行时基础设施（Verify Runtime Infrastructure） | 运行时基础设施（Runtime Infrastructure） | 平台管理员（Platform Admin） | 运行时基础设施已验证（Runtime Infrastructure Verified）, 运行时基础设施验证失败（Runtime Infrastructure Verification Failed）, 状态: 已验证（Verified） |
| 运行时预配（Runtime Provisioning） | 部署运行时代理（Deploy Runtime Agent） | 运行时基础设施（Runtime Infrastructure） | 平台管理员（Platform Admin） | 运行时代理安装成功（Runtime Agent Installation Succeeded）, 运行时代理安装失败（Runtime Agent Installation Failed）, 状态: 代理就绪（Agent Ready） |
| 运行时预配（Runtime Provisioning） | 重试运行时代理部署（Retry Runtime Agent Deployment） | 运行时基础设施（Runtime Infrastructure） | 平台管理员（Platform Admin） | 运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）, 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed）, 状态: 代理就绪（Agent Ready） |
| 运行时预配（Runtime Provisioning） | 记录运行时连接已建立（Record Runtime Connection Established） | 运行时基础设施（Runtime Infrastructure） | 系统 | 运行时连接已建立（Runtime Connection Established）, 状态: 已连接（Connected） |
| 运行时预配（Runtime Provisioning） | 运行时代理端点目录（Runtime Agent Endpoint Catalog） | 运行时基础设施（Runtime Infrastructure） | 系统 | 尚未明确 |
| 运行时预配（Runtime Provisioning） | 运行时基础设施访问视图（Runtime Infrastructure Access View） | 运行时基础设施（Runtime Infrastructure） | 系统 | 尚未明确 |
| 数据集治理（Dataset Governance） | 定义特征架构（Define Feature Schema） | 特征架构（Feature Schema） | 数据管理员（Data Steward） | 特征架构已定义（Feature Schema Defined）, 状态: 草稿（Draft） |
| 数据集治理（Dataset Governance） | 发布特征架构（Publish Feature Schema） | 特征架构（Feature Schema） | 数据管理员（Data Steward） | 特征架构已发布（Feature Schema Published）, 状态: 已发布（Published） |
| 数据集治理（Dataset Governance） | 弃用特征架构（Deprecate Feature Schema） | 特征架构（Feature Schema） | 数据管理员（Data Steward） | 特征架构已弃用（Feature Schema Deprecated）, 状态: 已弃用（Deprecated） |
| 数据集治理（Dataset Governance） | 停用特征架构（Retire Feature Schema） | 特征架构（Feature Schema） | 数据管理员（Data Steward） | 特征架构已停用（Feature Schema Retired）, 状态: 已停用（Retired） |
| 数据集治理（Dataset Governance） | 取代特征架构版本（Supersede Feature Schema Version） | 特征架构（Feature Schema） | 数据管理员（Data Steward） | 特征架构版本已取代（Feature Schema Version Superseded） |
| 数据集治理（Dataset Governance） | 标记当前推荐的特征架构版本（Mark Current Recommended Feature Schema Version） | 特征架构（Feature Schema） | 数据管理员（Data Steward） | 已标记当前推荐特征架构版本（Current Recommended Feature Schema Version Marked） |
| 数据集治理（Dataset Governance） | 当前推荐的特征架构目录（Current Recommended Feature Schema Catalog） | 特征架构（Feature Schema） | 系统 | 尚未明确 |
| 数据集治理（Dataset Governance） | 特征架构目录（Feature Schema Catalog） | 特征架构（Feature Schema） | 系统 | 尚未明确 |
| 数据集治理（Dataset Governance） | 记录运行时数据集元数据（Record Runtime Dataset Metadata） | 运行时数据集元数据（Runtime Dataset Metadata） | 在代理报告时记录数据集元数据（Record Dataset Metadata When Agent Reported） | 数据集元数据已报告（Dataset Metadata Reported）, 状态: 已报告元数据（Metadata Reported） |
| 数据集治理（Dataset Governance） | 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata） | 运行时数据集元数据（Runtime Dataset Metadata） | 代理重新分析时记录数据集元数据（Record Dataset Metadata When Agent Reprofiled） | 数据集元数据已重新分析（Dataset Metadata Reprofiled）, 状态: 已报告元数据（Metadata Reported） |
| 数据集治理（Dataset Governance） | 运行时数据集元数据目录（Runtime Dataset Metadata Catalog） | 运行时数据集元数据（Runtime Dataset Metadata） | 系统 | 尚未明确 |
| 模型仓库（Model Repository） | 注册模型制品（Register Model Artifact） | 模型制品（Model Artifact） | MLOps 工程师（MLOps Engineer） | 模型工件已注册（Model Artifact Registered）, 状态: 已注册（Registered） |
| 模型仓库（Model Repository） | 模型制品目录（Model Artifact Catalog） | 模型制品（Model Artifact） | 系统 | 尚未明确 |
| 训练编排（Training Orchestration） | 定义训练运行配置（Define Training Run Configuration） | 训练运行配置（Training Run Configuration） | MLOps 工程师（MLOps Engineer） | 训练运行配置已定义（Training Run Configuration Defined）, 状态: 草稿（Draft） |
| 训练编排（Training Orchestration） | 更新训练运行配置（Update Training Run Configuration） | 训练运行配置（Training Run Configuration） | MLOps 工程师（MLOps Engineer） | 训练运行配置已更新（Training Run Configuration Updated）, 状态: 草稿（Draft） |
| 训练编排（Training Orchestration） | 锁定训练运行配置（Lock Training Run Configuration） | 训练运行配置（Training Run Configuration） | 训练提交时锁定配置（Lock Configuration When Training Submitted） | 训练运行配置已锁定（Training Run Configuration Locked）, 状态: 已锁定（Locked） |
| 训练编排（Training Orchestration） | 训练运行配置目录（Training Run Configuration Catalog） | 训练运行配置（Training Run Configuration） | 系统 | 尚未明确 |
| 训练编排（Training Orchestration） | 创建训练作业（Create Training Job） | 训练作业（Training Job） | 研究主管（Research Lead） | 训练作业已创建（Training Job Created）, 状态: 草稿（Draft） |
| 训练编排（Training Orchestration） | 提交训练作业（Submit Training Job） | 训练作业（Training Job） | 研究主管（Research Lead） | 训练作业已提交（Training Job Submitted）, 状态: 已提交（Submitted） |
| 训练编排（Training Orchestration） | 训练参与者资格（Training Participant Eligibility） | 训练作业（Training Job） | 系统 | 尚未明确 |
| 训练编排（Training Orchestration） | 暂停训练作业（Pause Training Job） | 训练作业（Training Job） | MLOps 工程师（MLOps Engineer） | 训练作业已暂停（Training Job Paused）, 状态: 已暂停（Paused） |
| 训练编排（Training Orchestration） | 恢复训练作业（Resume Training Job） | 训练作业（Training Job） | MLOps 工程师（MLOps Engineer） | 训练作业已恢复（Training Job Resumed）, 状态: 运行中（Running） |
| 训练编排（Training Orchestration） | 取消训练作业（Cancel Training Job） | 训练作业（Training Job） | MLOps 工程师（MLOps Engineer） | 训练作业已取消（Training Job Canceled）, 状态: 已取消（Canceled） |
| 训练编排（Training Orchestration） | 安排下一训练轮次（Schedule Next Training Round） | 训练作业（Training Job） | 当训练应继续时选择下一轮参与者（Select Next Round Participants When Training Should Continue） | 尚未明确 |
| 训练编排（Training Orchestration） | 完成训练作业（Complete Training Job） | 训练作业（Training Job） | 轮次预算耗尽时完成作业（Complete Job When Round Budget Exhausted）, 达到全局指标时完成作业（Complete Job When Global Metric Reached） | 训练作业已完成（Training Job Completed）, 状态: 已完成（Completed） |
| 训练编排（Training Orchestration） | 训练作业仪表板（Training Job Dashboard） | 训练作业（Training Job） | 系统 | 尚未明确 |
| 训练编排（Training Orchestration） | 选择训练轮次参与者（Select Training Round Participants） | 训练轮次（Training Round） | 训练提交时选择初始轮次参与者（Select Initial Round Participants When Training Submitted） | 训练轮次参与者已选择（Training Round Participants Selected）, 训练轮次参与者选择失败（Training Round Participant Selection Failed）, 状态: 参与者已选择（Participants Selected） |
| 训练编排（Training Orchestration） | 重试训练轮次参与者选择（Retry Training Round Participant Selection） | 训练轮次（Training Round） | 训练操作员（Training Operator） | 训练轮次参与者选择重试已请求（Training Round Participant Selection Retry Requested） |
| 训练编排（Training Orchestration） | 开始训练轮次（Start Training Round） | 训练轮次（Training Round） | 参与者已选择时启动轮次（Start Round When Participants Selected） | 训练轮次已启动（Training Round Started）, 训练轮次启动失败（Training Round Start Failed）, 状态: 运行中（Running） |
| 训练编排（Training Orchestration） | 生成参与者执行计划（Generate Participant Execution Plan） | 参与者执行计划（Participant Execution Plan） | 为已选择的运行时生成计划（Generate Plan For Selected Runtime） | 参与者执行计划已生成（Participant Execution Plan Generated）, 状态: 计划已生成（Plan Generated） |
| 训练编排（Training Orchestration） | 分发参与者执行计划（Dispatch Participant Execution Plan） | 参与者执行计划（Participant Execution Plan） | 生成时分发计划（Dispatch Plan When Generated） | 参与者执行计划已分发（Participant Execution Plan Dispatched）, 状态: 计划已分发（Plan Dispatched） |
| 训练编排（Training Orchestration） | 提交模型更新提交（Submit Model Update Submission） | 训练轮次（Training Round） | 运行时代理（Runtime Agent） | 模型更新提交已接收（Model Update Submission Received）, 状态: 已接收（Received） |
| 训练编排（Training Orchestration） | 评估模型更新提交（Evaluate Model Update Submission） | 训练轮次（Training Round） | 收到时评估模型更新（Evaluate Model Update When Received） | 模型更新提交已接受（Model Update Submission Accepted）, 模型更新提交已拒绝（Model Update Submission Rejected）, 状态: 已评估（Evaluated） |
| 训练编排（Training Orchestration） | 请求安全聚合（Request Secure Aggregation） | 训练轮次（Training Round） | 在已评估更新完成时请求聚合（Request Aggregation When Evaluated Updates Complete） | 已请求安全聚合（Secure Aggregation Requested）, 状态: 聚合中（Aggregating） |
| 训练编排（Training Orchestration） | 完成安全聚合（Complete Secure Aggregation） | 训练轮次（Training Round） | 系统 | 全局模型已更新（Global Model Updated）, 状态: 评估全局模型中（Evaluating Global Model） |
| 训练编排（Training Orchestration） | 提交全局模型评估（Submit Global Model Evaluation） | 训练轮次（Training Round） | 系统 | 全局模型评估已提交（Global Model Evaluation Submitted） |
| 训练编排（Training Orchestration） | 完成训练轮次（Complete Training Round） | 训练轮次（Training Round） | 全局模型评估后结束轮次（Finish Round After Global Model Evaluated） | 训练轮次已完成（Training Round Completed）, 状态: 已完成（Completed） |
| 训练编排（Training Orchestration） | 失败训练轮次（Fail Training Round） | 训练轮次（Training Round） | 聚合运行时（Aggregation Runtime） | 训练轮次失败（Training Round Failed）, 状态: 已失败（Failed） |
| 训练编排（Training Orchestration） | 训练轮次进度（Training Round Progress） | 训练轮次（Training Round） | 系统 | 尚未明确 |
| 模型生命周期（Model Lifecycle） | 注册候选模型（Register Candidate Model） | 模型（Model） | 训练作业完成时注册最终模型（Register Final Model When Training Job Completed） | 模型候选已注册（Model Candidate Registered）, 状态: 候选（Candidate） |
| 模型生命周期（Model Lifecycle） | 记录模型评估包（Record Model Evaluation Package） | 模型（Model） | 治理审核员（Governance Reviewer） | 模型评估包已记录（Model Evaluation Package Recorded）, 状态: 评估已打包（Evaluation Packaged） |
| 模型生命周期（Model Lifecycle） | 批准模型（Approve Model） | 模型（Model） | 治理审核员（Governance Reviewer） | 模型已批准（Model Approved）, 状态: 已批准（Approved） |
| 模型生命周期（Model Lifecycle） | 提升模型至生产（Promote Model To Production） | 模型（Model） | 发布管理员（Release Manager） | 模型已提升到生产环境（Model Promoted To Production）, 状态: 生产（Production） |
| 模型生命周期（Model Lifecycle） | 回滚模型（Rollback Model） | 模型（Model） | 发布管理员（Release Manager） | 模型已回滚（Model Rolled Back）, 状态: 已回滚（Rolled Back） |
| 模型生命周期（Model Lifecycle） | 停用模型（Retire Model） | 模型（Model） | 发布管理员（Release Manager） | 模型已退役（Model Retired）, 状态: 已停用（Retired） |
| 模型生命周期（Model Lifecycle） | 模型目录（Model Catalog） | 模型（Model） | 系统 | 尚未明确 |
| 运行时监控（Runtime Monitoring） | 运行时最新遥测（Runtime Telemetry Latest） | 节点运行时健康（Node Runtime Health） | 系统 | 尚未明确 |
| 运行时监控（Runtime Monitoring） | 检测运行时代理离线（Detect Runtime Agent Offline） | 节点运行时健康（Node Runtime Health） | 从遥测检测离线运行时代理（Detect Offline Runtime Agent From Telemetry） | 已检测到运行时代理离线（Runtime Agent Offline Detected）, 状态: 离线（Offline） |
| 运行时监控（Runtime Monitoring） | 标记运行时代理已恢复（Mark Runtime Agent Recovered） | 节点运行时健康（Node Runtime Health） | 从遥测标记运行时代理已恢复（Mark Recovered Runtime Agent From Telemetry） | 运行时代理已恢复（Runtime Agent Recovered）, 状态: 健康（Healthy） |
| 运行时监控（Runtime Monitoring） | 检测运行时节点资源压力（Detect Runtime Node Resource Pressure） | 运行时节点资源压力（Runtime Node Resource Pressure） | 从遥测检测资源压力（Detect Resource Pressure From Telemetry） | 已检测到运行时节点资源压力（Runtime Node Resource Pressure Detected）, 状态: 已检测到压力（Pressure Detected） |
| 运行时监控（Runtime Monitoring） | 记录运行时节点清单（Record Runtime Node Inventory） | 运行时节点清单（Runtime Node Inventory） | 代理报告时记录运行时节点清单（Record Runtime Node Inventory When Agent Reported） | 运行时节点清单已报告（Runtime Node Inventory Reported）, 状态: 已报告（Reported） |
| 运行时监控（Runtime Monitoring） | 运行时节点清单视图（Runtime Node Inventory View） | 运行时节点清单（Runtime Node Inventory） | 系统 | 尚未明确 |
| 运行时监控（Runtime Monitoring） | 运行时节点最新资源（Runtime Node Resource Latest） | 运行时节点资源遥测（Runtime Node Resource Telemetry） | 系统 | 尚未明确 |
| 运行时监控（Runtime Monitoring） | 检测运行时节点容量变更（Detect Runtime Node Capacity Change） | 运行时节点容量（Runtime Node Capacity） | 从资源遥测检测容量变化（Detect Capacity Change From Resource Telemetry） | 运行时节点容量已更改（Runtime Node Capacity Changed）, 状态: 容量已变更（Capacity Changed） |
| 运行时监控（Runtime Monitoring） | 运行时健康仪表板（Runtime Health Dashboard） | 节点运行时健康（Node Runtime Health） | 系统 | 尚未明确 |
| 运行时监控（Runtime Monitoring） | 引发训练告警（Raise Training Alert） | 训练告警（Training Alert） | 在节点资源压力时发出警报（Raise Alert On Node Resource Pressure） | 训练警报已发出（Training Alert Raised）, 状态: 已引发（Raised） |
| 运行时监控（Runtime Monitoring） | 训练告警目录（Training Alert Catalog） | 训练告警（Training Alert） | 系统 | 尚未明确 |
| 运行时监控（Runtime Monitoring） | 确认训练告警（Acknowledge Training Alert） | 训练告警（Training Alert） | MLOps 工程师（MLOps Engineer） | 训练警报已确认（Training Alert Acknowledged）, 状态: 已确认（Acknowledged） |
| 运行时监控（Runtime Monitoring） | 解决训练告警（Resolve Training Alert） | 训练告警（Training Alert） | MLOps 工程师（MLOps Engineer） | 训练警报已解决（Training Alert Resolved）, 状态: 已解决（Resolved） |
| 运行时监控（Runtime Monitoring） | 追加审计跟踪（Append Audit Trail） | 审计记录（Audit Record） | 审计关键训练事件（Audit Critical Training Events）, 审计联邦已激活（Audit Federation Activated）, 审计联邦已暂停（Audit Federation Suspended）, 审计联邦已重新激活（Audit Federation Reactivated）, 审计参与者已加入（Audit Participant Joined）, 审计参与者已暂停（Audit Participant Suspended）, 审计数据集批准（Audit Dataset Approval）, 审计数据集批准已撤销（Audit Dataset Approval Revoked）, 审计训练作业已提交（Audit Training Job Submitted）, 审计模型已提升至生产（Audit Model Promoted To Production） | 审计跟踪已追加（Audit Trail Appended）, 状态: 已追加（Appended） |
| 运行时监控（Runtime Monitoring） | 审计记录日志（Audit Record Log） | 审计记录（Audit Record） | 系统 | 尚未明确 |
| 运行时治理（Runtime Governance） | 激活运行时标识（Activate Runtime Identity） | 运行时标识（Runtime Identity） | 平台管理员（Platform Admin） | 运行时身份已激活（Runtime Identity Activated）, 状态: 已激活（Active） |
| 运行时治理（Runtime Governance） | 撤销运行时标识（Revoke Runtime Identity） | 运行时标识（Runtime Identity） | 安全审核员（Security Reviewer） | 运行时身份已撤销（Runtime Identity Revoked）, 状态: 已撤销（Revoked） |
| 运行时治理（Runtime Governance） | 检测运行时能力（Detect Runtime Capabilities） | 运行时能力（Runtime Capability） | 运行时代理（Runtime Agent） | 运行时能力已检测（Runtime Capabilities Detected）, 状态: 已检测到（Detected） |
| 运行时治理（Runtime Governance） | 运行时标识目录（Runtime Identity Catalog） | 运行时标识（Runtime Identity） | 系统 | 尚未明确 |
| 运行时治理（Runtime Governance） | 运行时能力目录（Runtime Capability Catalog） | 运行时能力（Runtime Capability） | 系统 | 尚未明确 |
| 安全聚合（Secure Aggregation） | 创建安全聚合会话（Create Secure Aggregation Session） | 安全聚合会话（Secure Aggregation Session） | 在请求聚合时创建会话（Create Session When Aggregation Requested） | 安全聚合会话已创建（Secure Aggregation Session Created）, 状态: 已规划（Planned） |
| 安全聚合（Secure Aggregation） | 选择安全聚合参与者（Select Secure Aggregation Participants） | 安全聚合会话（Secure Aggregation Session） | 会话创建时选择参与者（Select Participants When Session Created） | 安全聚合参与者已选择（Secure Aggregation Participants Selected）, 状态: 参与者已选择（Participants Selected） |
| 安全聚合（Secure Aggregation） | 准备同态加密上下文（Prepare Homomorphic Encryption Context） | 安全聚合会话（Secure Aggregation Session） | 安全聚合服务（Secure Aggregation Service） | 同态加密上下文已准备（Homomorphic Encryption Context Prepared）, 状态: 已准备加密上下文（Encryption Context Prepared） |
| 安全聚合（Secure Aggregation） | 记录加密模型更新（Record Encrypted Model Update） | 安全聚合会话（Secure Aggregation Session） | 安全聚合服务（Secure Aggregation Service） | 已接收加密模型更新（Encrypted Model Update Received） |
| 安全聚合（Secure Aggregation） | 完成同态聚合会话（Complete Homomorphic Aggregation Session） | 安全聚合会话（Secure Aggregation Session） | 安全聚合服务（Secure Aggregation Service） | 安全聚合已完成（Secure Aggregation Completed）, 状态: 已完成（Completed） |
| 安全聚合（Secure Aggregation） | 失败安全聚合会话（Fail Secure Aggregation Session） | 安全聚合会话（Secure Aggregation Session） | 安全聚合服务（Secure Aggregation Service） | 安全聚合失败（Secure Aggregation Failed）, 状态: 已失败（Failed） |
| 安全聚合（Secure Aggregation） | 安全聚合会话目录（Secure Aggregation Session Catalog） | 安全聚合会话（Secure Aggregation Session） | 系统 | 尚未明确 |
| 运行时代理操作（Runtime Agent Operations） | 声明数据集（Declare Dataset） | 数据集（Dataset） | 数据所有者（Data Owner） | 数据集已声明（Dataset Declared）, 状态: 已注册（Registered） |
| 运行时代理操作（Runtime Agent Operations） | 配置运行时数据集绑定（Configure Runtime Dataset Binding） | 运行时数据集绑定（Runtime Dataset Binding） | 节点操作员（Node Operator） | 运行时数据集绑定已配置（Runtime Dataset Binding Configured）, 状态: 已配置（Configured） |
| 运行时代理操作（Runtime Agent Operations） | 运行时数据集绑定目录（Runtime Dataset Binding Catalog） | 运行时数据集绑定（Runtime Dataset Binding） | 系统 | 尚未明确 |
| 运行时代理操作（Runtime Agent Operations） | 验证代理数据集访问（Validate Agent Dataset Access） | 代理数据集访问验证（Agent Dataset Access Validation） | 运行时代理（Runtime Agent） | 代理数据集访问已验证（Agent Dataset Access Validated）, 代理数据集访问验证失败（Agent Dataset Access Validation Failed）, 状态: 已检查（Checked） |
| 运行时代理操作（Runtime Agent Operations） | 重新验证代理数据集访问（Revalidate Agent Dataset Access） | 代理数据集访问验证（Agent Dataset Access Validation） | 节点操作员（Node Operator） | 代理数据集访问已重新验证（Agent Dataset Access Revalidated）, 代理数据集访问重新验证失败（Agent Dataset Access Revalidation Failed）, 状态: 已检查（Checked） |
| 运行时代理操作（Runtime Agent Operations） | 代理数据集访问验证目录（Agent Dataset Access Validation Catalog） | 代理数据集访问验证（Agent Dataset Access Validation） | 系统 | 尚未明确 |
| 运行时代理操作（Runtime Agent Operations） | 分析代理数据集（Profile Agent Dataset） | 代理数据集配置文件（Agent Dataset Profile） | 运行时代理（Runtime Agent） | 代理数据集元数据已报告（Agent Dataset Metadata Reported）, 代理数据集分析失败（Agent Dataset Profiling Failed）, 状态: 已报告（Reported） |
| 运行时代理操作（Runtime Agent Operations） | 重新分析代理数据集（Reprofile Agent Dataset） | 代理数据集配置文件（Agent Dataset Profile） | 节点操作员（Node Operator） | 代理数据集已重新分析（Agent Dataset Reprofiled）, 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed）, 状态: 已报告（Reported） |
| 运行时代理操作（Runtime Agent Operations） | 验证数据集合约（Validate Dataset Contract） | 数据集（Dataset） | 自动验证数据集合约（Validate Dataset Contract Automatically） | 数据集合约已验证（Dataset Contract Validated）, 数据集合约验证失败（Dataset Contract Validation Failed）, 状态: 合约验证已完成（Contract Validation Completed） |
| 运行时代理操作（Runtime Agent Operations） | 重试数据集合约验证（Retry Dataset Contract Validation） | 数据集（Dataset） | 数据所有者（Data Owner） | 数据集合约已重新验证（Dataset Contract Revalidated）, 数据集合约重新验证失败（Dataset Contract Revalidation Failed）, 状态: 合约验证已完成（Contract Validation Completed） |
| 运行时代理操作（Runtime Agent Operations） | 拒绝数据集用于训练（Reject Dataset For Training） | 数据集（Dataset） | 合规专员（Compliance Officer） | 数据集已拒绝用于训练（Dataset Rejected For Training）, 状态: 已拒绝（Rejected） |
| 运行时代理操作（Runtime Agent Operations） | 批准数据集用于训练（Approve Dataset For Training） | 数据集（Dataset） | 合规专员（Compliance Officer） | 数据集已批准用于训练（Dataset Approved For Training）, 状态: 已批准（Approved） |
| 运行时代理操作（Runtime Agent Operations） | 撤销数据集训练审批（Revoke Dataset Training Approval） | 数据集（Dataset） | 合规专员（Compliance Officer） | 数据集训练批准已撤销（Dataset Training Approval Revoked）, 状态: 审批已撤销（Approval Revoked） |
| 运行时代理操作（Runtime Agent Operations） | 数据集能力（Dataset Capability） | 数据集（Dataset） | 系统 | 尚未明确 |
| 运行时代理操作（Runtime Agent Operations） | 数据集就绪状态（Dataset Readiness） | 数据集（Dataset） | 系统 | 尚未明确 |
| 运行时代理操作（Runtime Agent Operations） | 接收参与者执行计划（Receive Participant Execution Plan） | 轮次执行（Round Execution） | 分发时接收执行计划（Receive Execution Plan When Dispatched） | 执行计划已接收（Execution Plan Received）, 状态: 计划已接收（Plan Received） |
| 运行时代理操作（Runtime Agent Operations） | 接受执行计划（Accept Execution Plan） | 轮次执行（Round Execution） | 收到执行计划时接受执行计划（Accept Execution Plan When Execution Plan Received） | 执行计划已接受（Execution Plan Accepted）, 执行计划已拒绝（Execution Plan Rejected）, 状态: 计划已接受（Plan Accepted） |
| 运行时代理操作（Runtime Agent Operations） | 开始轮次执行（Start Round Execution） | 轮次执行（Round Execution） | 计划接受时启动轮次执行（Start Round Execution When Plan Accepted） | 轮次执行已启动（Round Execution Started）, 轮次执行启动失败（Round Execution Start Failed）, 状态: 运行中（Running） |
| 运行时代理操作（Runtime Agent Operations） | 观察运行时引擎作业（Observe Runtime Engine Job） | 轮次执行（Round Execution） | 轮次执行启动时观察运行时引擎作业（Observe Runtime Engine Job When Round Execution Started） | 运行时引擎作业已观察（Runtime Engine Job Observed）, 状态: 运行中（Running） |
| 运行时代理操作（Runtime Agent Operations） | 完成轮次执行（Complete Round Execution） | 轮次执行（Round Execution） | 运行时引擎作业完成时完成轮次执行（Complete Round Execution When Runtime Engine Job Completed） | 轮次执行已完成（Round Execution Completed）, 状态: 已完成（Completed） |
| 运行时代理操作（Runtime Agent Operations） | 失败轮次执行（Fail Round Execution） | 轮次执行（Round Execution） | Fail Round Execution When Runtime Engine Job Failed | 轮次执行失败（Round Execution Failed）, 状态: 已失败（Failed） |
| 运行时代理操作（Runtime Agent Operations） | 启动失败后重试轮次执行（Retry Round Execution After Start Failure） | 轮次执行（Round Execution） | 节点操作员（Node Operator） | Round Execution Start Retry Started, 轮次执行启动重试失败（Round Execution Start Retry Failed）, 状态: 已重试（Retried） |
| 运行时代理操作（Runtime Agent Operations） | 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure） | 轮次执行（Round Execution） | 节点操作员（Node Operator） | Round Execution Runtime Retry Started, 轮次执行运行时重试失败（Round Execution Runtime Retry Failed）, 状态: 已重试（Retried） |
| 运行时代理操作（Runtime Agent Operations） | 提交代理本地模型更新（Submit Agent Local Model Update） | 轮次执行（Round Execution） | Submit Local Model Update When Round Execution Completed | Agent Local Model Update Submitted, 状态: 更新已提交（Update Submitted） |
| 运行时代理操作（Runtime Agent Operations） | 完成后释放运行时引擎作业（Release Runtime Engine Job After Completion） | 轮次执行（Round Execution） | Release Runtime Engine Job When Round Execution Completed | Runtime Engine Job Released, 状态: 运行时引擎已释放（Runtime Engine Released） |
| 运行时代理操作（Runtime Agent Operations） | 失败后释放运行时引擎作业（Release Runtime Engine Job After Failure） | 轮次执行（Round Execution） | Release Runtime Engine Job When Round Execution Failed | Runtime Engine Job Release Failed Or Skipped, 状态: 运行时引擎释放已处理（Runtime Engine Release Handled） |
| 运行时代理操作（Runtime Agent Operations） | 启动失败后释放运行时引擎作业（Release Runtime Engine Job After Start Failure） | 轮次执行（Round Execution） | Release Runtime Engine Job When Start Failed | Runtime Engine Job Release Failed Or Skipped After Start Failure, 状态: 运行时引擎释放已处理（Runtime Engine Release Handled） |
| 运行时代理操作（Runtime Agent Operations） | 重试失败后释放运行时引擎作业（Release Runtime Engine Job After Retry Failure） | 轮次执行（Round Execution） | Release Runtime Engine Job When Start Retry Failed | Runtime Engine Job Release Failed Or Skipped After Retry, 状态: 运行时引擎释放已处理（Runtime Engine Release Handled） |
| 运行时代理操作（Runtime Agent Operations） | 运行时重试失败后释放运行时引擎作业（Release Runtime Engine Job After Runtime Retry Failure） | 轮次执行（Round Execution） | Release Runtime Engine Job When Runtime Retry Failed | Runtime Engine Job Release Failed Or Skipped After Runtime Retry, 状态: 运行时引擎释放已处理（Runtime Engine Release Handled） |
| 运行时代理操作（Runtime Agent Operations） | 轮次执行目录（Round Execution Catalog） | 轮次执行（Round Execution） | 系统 | 尚未明确 |
| 运行时代理操作（Runtime Agent Operations） | 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration） | 运行时代理生命周期（Runtime Agent Lifecycle） | 运行时代理（Runtime Agent） | Runtime Agent Bootstrap Configuration Loaded, Runtime Agent Bootstrap Configuration Load Failed, 状态: 引导程序已加载（Bootstrap Loaded） |
| 运行时代理操作（Runtime Agent Operations） | 报告运行时代理已启动（Report Runtime Agent Started） | 运行时代理生命周期（Runtime Agent Lifecycle） | 运行时代理（Runtime Agent） | Runtime Agent Started, 状态: 已启动（Started） |
| 运行时代理操作（Runtime Agent Operations） | 报告运行时实例自检已通过（Report Runtime Instance Self Check Passed） | 运行时代理生命周期（Runtime Agent Lifecycle） | 运行时代理（Runtime Agent） | Runtime Instance Self Check Passed, 状态: 就绪（Ready） |
| 运行时代理操作（Runtime Agent Operations） | 运行时代理生命周期目录（Runtime Agent Lifecycle Catalog） | 运行时代理生命周期（Runtime Agent Lifecycle） | 系统 | 尚未明确 |
| 运行时代理操作（Runtime Agent Operations） | 报告运行时实例已连接（Report Runtime Instance Connected） | 代理运行时基础设施连接（Agent Runtime Infrastructure Connection） | Report Runtime Instance Connected After Self Check | Agent Runtime Connection Report Failed, Agent Runtime Connection Established, 状态: 已连接（Connected） |
| 运行时代理操作（Runtime Agent Operations） | 代理运行时基础设施连接目录（Agent Runtime Infrastructure Connection Catalog） | 代理运行时基础设施连接（Agent Runtime Infrastructure Connection） | 系统 | 尚未明确 |
| 运行时代理操作（Runtime Agent Operations） | 代理运行时遥测最新（Agent Runtime Telemetry Latest） | 代理运行时遥测（Agent Runtime Telemetry） | 系统 | 尚未明确 |
| 运行时代理操作（Runtime Agent Operations） | 报告代理运行时节点清单（Report Agent Runtime Node Inventory） | 代理运行时节点清单（Agent Runtime Node Inventory） | Report Node Inventory When Inventory Changed | 代理运行时节点清单已报告（Agent Runtime Node Inventory Reported）, 状态: 已报告（Reported） |
| 运行时代理操作（Runtime Agent Operations） | 代理运行时节点清单目录（Agent Runtime Node Inventory Catalog） | 代理运行时节点清单（Agent Runtime Node Inventory） | 系统 | 尚未明确 |
| 运行时代理操作（Runtime Agent Operations） | 代理运行时节点资源最新（Agent Runtime Node Resource Latest） | 代理运行时节点资源遥测（Agent Runtime Node Resource Telemetry） | 系统 | 尚未明确 |

## 三、测试范围边界/FLP-TO-TSB

### 3.1 纳入测试范围/FLP-TO-ITS

- 模型中已明确的 Slice、Command、Event、Specification、状态流转、Read Model 更新、Automation 触发和外部集成。
- 基于模型推导的正常路径、业务拒绝路径、权限边界、重复操作、幂等、重试、并发和数据一致性验证。

### 3.2 不纳入测试范围/FLP-TO-OOS

- 未建模业务流程、纯实现细节、生产运维操作，以及超出已建模集成契约的外部系统内部行为。
- 具体输入值、逐步操作步骤、接口报文样例等属于详细测试用例，不在测试大纲中展开。

## 四、测试策略/FLP-TO-TST

- 面向用户功能和系统触发流程采用场景化功能测试。
- 没有页面入口的操作采用 API 或服务层测试。
- 外部系统、自动化触发和跨上下文协作采用集成测试。
- Read Model 通过最终一致性、投影新鲜度、主键、过滤、排序、重放和幂等更新断言进行验证。
- 生命周期流转、权限边界、校验规则和重试行为纳入回归测试。

## 五、测试环境与测试数据/FLP-TO-TETD

- 测试执行前准备稳定的标识、参考数据、字典值和代表性用户角色。
- 为每个业务操作准备有效、无效、重复、边界和缺失字段请求数据。
- 为列表、详情、仪表板和目录视图准备可确定验证的种子数据或回放数据。
- 为外部系统和运行时适配器准备集成沙箱或替身服务。

## 六、测试类型/FLP-TO-TT

- Specification Test：将 Given / When / Then 规则映射为可执行领域场景。
- Domain Test：验证聚合或概念的状态流转、不变量、拒绝路径和产生事件。
- Adapter Contract Test：验证端口、外部系统契约、失败映射、重试和超时行为。
- Integration Test：验证跨上下文协作、事件处理器、自动化、Projection 和外部依赖。
- Workflow / E2E Test：验证用户或运维可感知的端到端业务流程。
- Regression Test：固化生命周期流转、业务规则、权限、Read Model 重放和历史缺陷回归覆盖。

## 七、功能测试范围/FLP-TO-FTS

<!-- em:section id="test.context.组织管理（OrganizationManagement）" source="domain/FederationLearningPlatform/context/OrganizationManagement" -->
### 7.1 组织管理（Organization Management）/FLP-TO-SECTION

| 测试域 | 角色/触发条件 | 输入 | 预期结果 | 优先级依据 |
| --- | --- | --- | --- | --- |
| 注册组织（Register Organization） | 组织管理员（Organization Admin） | 注册组织（Register Organization） | 组织已注册（Organization Registered）, 状态: 已注册（Registered） | 生命周期起点 |
| 激活组织（Activate Organization） | 平台管理员（Platform Admin） | 激活组织（Activate Organization） | 组织已激活（Organization Activated）, 状态: 已激活（Active） | 需评审确认 |
| 停用组织（Deactivate Organization） | 平台管理员（Platform Admin） | 停用组织（Deactivate Organization） | 组织已停用（Organization Deactivated）, 状态: 已停用（Deactivated） | 需评审确认 |
| 重新激活组织（Reactivate Organization） | 平台管理员（Platform Admin） | 重新激活组织（Reactivate Organization） | 组织已重新激活（Organization Reactivated）, 状态: 已激活（Active） | 需评审确认 |
| 组织目录（Organization Directory） | 系统 | - | 尚未明确 | 需评审确认 |

<!-- em:section id="test.context.联邦管理（FederationManagement）" source="domain/FederationLearningPlatform/context/FederationManagement" -->
### 7.2 联邦管理（Federation Management）/FLP-TO-SECTION-2

| 测试域 | 角色/触发条件 | 输入 | 预期结果 | 优先级依据 |
| --- | --- | --- | --- | --- |
| 创建联邦（Create Federation） | 平台管理员（Platform Admin） | 创建联邦（Create Federation） | 联邦已创建（Federation Created）, 状态: 草稿（Draft） | 生命周期起点 |
| 激活联邦（Activate Federation） | 平台管理员（Platform Admin） | 激活联邦（Activate Federation） | 联邦已激活（Federation Activated）, 状态: 已激活（Active） | 需评审确认 |
| 暂停联邦（Suspend Federation） | 平台管理员（Platform Admin） | 暂停联邦（Suspend Federation） | 联邦已暂停（Federation Suspended）, 状态: 已暂停（Suspended） | 需评审确认 |
| 重新激活联邦（Reactivate Federation） | 平台管理员（Platform Admin） | 重新激活联邦（Reactivate Federation） | 联邦已重新激活（Federation Reactivated）, 状态: 已激活（Active） | 需评审确认 |
| 联邦概览（Federation Overview） | 系统 | - | 尚未明确 | 需评审确认 |
| 邀请参与者（Invite Participant） | 联邦所有者（Federation Owner） | 邀请参与者（Invite Participant） | 参与者已邀请（Participant Invited）, 状态: 已邀请（Invited） | 生命周期起点 |
| 联邦成员资格目录（Federation Membership Directory） | 系统 | - | 尚未明确 | 需评审确认 |
| 批准参与者（Approve Participant） | 治理审核员（Governance Reviewer） | 批准参与者（Approve Participant） | 参与者已加入（Participant Joined）, 状态: 已激活（Active） | 规则已覆盖 |
| 拒绝参与者（Reject Participant） | 治理审核员（Governance Reviewer） | 拒绝参与者（Reject Participant） | 参与者已拒绝（Participant Rejected）, 状态: 已拒绝（Rejected） | 需评审确认 |
| 撤销参与者邀请（Revoke Participant Invitation） | 联邦所有者（Federation Owner） | 撤销参与者邀请（Revoke Participant Invitation） | 参与者邀请已撤销（Participant Invitation Revoked）, 状态: 邀请已撤销（Invitation Revoked） | 需评审确认 |
| 暂停参与者（Suspend Participant） | 治理审核员（Governance Reviewer） | 暂停参与者（Suspend Participant） | 参与者已暂停（Participant Suspended）, 状态: 已暂停（Suspended） | 需评审确认 |
| 移除参与者（Remove Participant） | 治理审核员（Governance Reviewer） | 移除参与者（Remove Participant） | 参与者已移除（Participant Removed）, 状态: 已移除（Removed） | 需评审确认 |

<!-- em:section id="test.context.字典维护（DictionaryMaintenance）" source="domain/FederationLearningPlatform/context/DictionaryMaintenance" -->
### 7.3 字典维护（Dictionary Maintenance）/FLP-TO-SECTION-3

| 测试域 | 角色/触发条件 | 输入 | 预期结果 | 优先级依据 |
| --- | --- | --- | --- | --- |
| 注册字典（Register Dictionary） | 平台管理员（Platform Admin） | 注册字典（Register Dictionary） | 字典已注册（Dictionary Registered）, 状态: 已注册（Registered） | 生命周期起点 |
| 更新字典（Update Dictionary） | 平台管理员（Platform Admin） | 更新字典（Update Dictionary） | 字典已更新（Dictionary Updated） | 需评审确认 |
| 归档字典（Archive Dictionary） | 平台管理员（Platform Admin） | 归档字典（Archive Dictionary） | 字典已归档（Dictionary Archived）, 状态: 已归档（Archived） | 需评审确认 |
| 字典目录（Dictionary Catalog） | 系统 | - | 尚未明确 | 需评审确认 |
| 添加字典值（Add Dictionary Value） | 平台管理员（Platform Admin） | 添加字典值（Add Dictionary Value） | 字典值已添加（Dictionary Value Added）, 状态: 已激活（Active） | 生命周期起点 |
| 停用字典值（Disable Dictionary Value） | 平台管理员（Platform Admin） | 停用字典值（Disable Dictionary Value） | 字典值已停用（Dictionary Value Disabled）, 状态: 已停用（Disabled） | 需评审确认 |
| 启用字典值（Enable Dictionary Value） | 平台管理员（Platform Admin） | 启用字典值（Enable Dictionary Value） | 字典值已启用（Dictionary Value Enabled）, 状态: 已激活（Active） | 需评审确认 |
| 字典值目录（Dictionary Value Catalog） | 系统 | - | 尚未明确 | 需评审确认 |

<!-- em:section id="test.context.文件上传（FileUpload）" source="domain/FederationLearningPlatform/context/FileUpload" -->
### 7.4 文件上传（File Upload）/FLP-TO-SECTION-4

| 测试域 | 角色/触发条件 | 输入 | 预期结果 | 优先级依据 |
| --- | --- | --- | --- | --- |
| 暂存文件上传（Stage File Upload） | 平台用户（Platform User） | 暂存文件上传（Stage File Upload） | 文件上传已暂存（File Upload Staged）, 状态: 已暂存（Staged） | 生命周期起点 |
| 标记暂存文件已消费（Mark Staged File Consumed） | 平台服务（Platform Service） | 标记暂存文件已消费（Mark Staged File Consumed） | 暂存文件已消费（Staged File Consumed）, 状态: 已消费（Consumed） | 规则已覆盖 |
| 放弃暂存文件（Discard Staged File） | 平台用户（Platform User） | 放弃暂存文件（Discard Staged File） | 暂存文件已丢弃（Staged File Discarded）, 状态: 已放弃（Discarded） | 规则已覆盖 |
| 使暂存文件过期（Expire Staged File） | 保留期结束后暂存文件过期（Expire Staged File When Retention Elapsed） | 使暂存文件过期（Expire Staged File） | 暂存文件已过期（Staged File Expired）, 状态: 已过期（Expired） | 规则已覆盖 |
| 暂存文件目录（Staged File Catalog） | 系统 | - | 尚未明确 | 需评审确认 |

<!-- em:section id="test.context.运行时预配（RuntimeProvisioning）" source="domain/FederationLearningPlatform/context/RuntimeProvisioning" -->
### 7.5 运行时预配（Runtime Provisioning）/FLP-TO-SECTION-5

| 测试域 | 角色/触发条件 | 输入 | 预期结果 | 优先级依据 |
| --- | --- | --- | --- | --- |
| 注册运行时基础设施包（Register Runtime Infrastructure Package） | 平台管理员（Platform Admin） | 注册运行时基础设施包（Register Runtime Infrastructure Package） | 运行时基础设施包已注册（Runtime Infrastructure Package Registered）, 状态: 已注册（Registered） | 生命周期起点 |
| 运行时基础设施包目录（Runtime Infrastructure Package Catalog） | 系统 | - | 尚未明确 | 需评审确认 |
| 创建运行时安装计划（Create Runtime Installation Plan） | 平台管理员（Platform Admin） | 创建运行时安装计划（Create Runtime Installation Plan） | 运行时安装计划已创建（Runtime Installation Plan Created）, 状态: 已规划（Planned） | 生命周期起点 |
| 运行时安装计划目录（Runtime Installation Plan Catalog） | 系统 | - | 尚未明确 | 需评审确认 |
| 运行时安装指南（Runtime Installation Guide） | 运行时安装程序（Runtime Installer） | - | 尚未明确 | 需评审确认 |
| 规划运行时基础设施（Plan Runtime Infrastructure） | 平台管理员（Platform Admin） | 规划运行时基础设施（Plan Runtime Infrastructure） | 运行时基础设施已计划（Runtime Infrastructure Planned）, 状态: 已规划（Planned） | 生命周期起点 |
| 注册运行时基础设施（Register Runtime Infrastructure） | 运行时安装程序（Runtime Installer） | 注册运行时基础设施（Register Runtime Infrastructure） | 运行时基础设施已注册（Runtime Infrastructure Registered）, 状态: 已注册（Registered） | 需评审确认 |
| 验证运行时基础设施（Verify Runtime Infrastructure） | 平台管理员（Platform Admin） | 验证运行时基础设施（Verify Runtime Infrastructure） | 运行时基础设施已验证（Runtime Infrastructure Verified）, 运行时基础设施验证失败（Runtime Infrastructure Verification Failed）, 状态: 已验证（Verified） | 规则已覆盖 |
| 部署运行时代理（Deploy Runtime Agent） | 平台管理员（Platform Admin） | 部署运行时代理（Deploy Runtime Agent） | 运行时代理安装成功（Runtime Agent Installation Succeeded）, 运行时代理安装失败（Runtime Agent Installation Failed）, 状态: 代理就绪（Agent Ready） | 规则已覆盖 |
| 重试运行时代理部署（Retry Runtime Agent Deployment） | 平台管理员（Platform Admin） | 重试运行时代理部署（Retry Runtime Agent Deployment） | 运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）, 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed）, 状态: 代理就绪（Agent Ready） | 规则已覆盖 |
| 记录运行时连接已建立（Record Runtime Connection Established） | 系统 | 记录运行时连接已建立（Record Runtime Connection Established） | 运行时连接已建立（Runtime Connection Established）, 状态: 已连接（Connected） | 需评审确认 |
| 运行时代理端点目录（Runtime Agent Endpoint Catalog） | 系统 | - | 尚未明确 | 需评审确认 |
| 运行时基础设施访问视图（Runtime Infrastructure Access View） | 系统 | - | 尚未明确 | 需评审确认 |

<!-- em:section id="test.context.数据集治理（DatasetGovernance）" source="domain/FederationLearningPlatform/context/DatasetGovernance" -->
### 7.6 数据集治理（Dataset Governance）/FLP-TO-SECTION-6

| 测试域 | 角色/触发条件 | 输入 | 预期结果 | 优先级依据 |
| --- | --- | --- | --- | --- |
| 定义特征架构（Define Feature Schema） | 数据管理员（Data Steward） | 定义特征架构（Define Feature Schema） | 特征架构已定义（Feature Schema Defined）, 状态: 草稿（Draft） | 生命周期起点 |
| 发布特征架构（Publish Feature Schema） | 数据管理员（Data Steward） | 发布特征架构（Publish Feature Schema） | 特征架构已发布（Feature Schema Published）, 状态: 已发布（Published） | 规则已覆盖 |
| 弃用特征架构（Deprecate Feature Schema） | 数据管理员（Data Steward） | 弃用特征架构（Deprecate Feature Schema） | 特征架构已弃用（Feature Schema Deprecated）, 状态: 已弃用（Deprecated） | 需评审确认 |
| 停用特征架构（Retire Feature Schema） | 数据管理员（Data Steward） | 停用特征架构（Retire Feature Schema） | 特征架构已停用（Feature Schema Retired）, 状态: 已停用（Retired） | 需评审确认 |
| 取代特征架构版本（Supersede Feature Schema Version） | 数据管理员（Data Steward） | 取代特征架构版本（Supersede Feature Schema Version） | 特征架构版本已取代（Feature Schema Version Superseded） | 规则已覆盖 |
| 标记当前推荐的特征架构版本（Mark Current Recommended Feature Schema Version） | 数据管理员（Data Steward） | 标记当前推荐的特征架构版本（Mark Current Recommended Feature Schema Version） | 已标记当前推荐特征架构版本（Current Recommended Feature Schema Version Marked） | 规则已覆盖 |
| 当前推荐的特征架构目录（Current Recommended Feature Schema Catalog） | 系统 | - | 尚未明确 | 需评审确认 |
| 特征架构目录（Feature Schema Catalog） | 系统 | - | 尚未明确 | 需评审确认 |
| 记录运行时数据集元数据（Record Runtime Dataset Metadata） | 在代理报告时记录数据集元数据（Record Dataset Metadata When Agent Reported） | 记录运行时数据集元数据（Record Runtime Dataset Metadata） | 数据集元数据已报告（Dataset Metadata Reported）, 状态: 已报告元数据（Metadata Reported） | 生命周期起点 |
| 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata） | 代理重新分析时记录数据集元数据（Record Dataset Metadata When Agent Reprofiled） | 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata） | 数据集元数据已重新分析（Dataset Metadata Reprofiled）, 状态: 已报告元数据（Metadata Reported） | 需评审确认 |
| 运行时数据集元数据目录（Runtime Dataset Metadata Catalog） | 系统 | - | 尚未明确 | 需评审确认 |

<!-- em:section id="test.context.模型仓库（ModelRepository）" source="domain/FederationLearningPlatform/context/ModelRepository" -->
### 7.7 模型仓库（Model Repository）/FLP-TO-SECTION-7

| 测试域 | 角色/触发条件 | 输入 | 预期结果 | 优先级依据 |
| --- | --- | --- | --- | --- |
| 注册模型制品（Register Model Artifact） | MLOps 工程师（MLOps Engineer） | 注册模型制品（Register Model Artifact） | 模型工件已注册（Model Artifact Registered）, 状态: 已注册（Registered） | 生命周期起点 |
| 模型制品目录（Model Artifact Catalog） | 系统 | - | 尚未明确 | 需评审确认 |

<!-- em:section id="test.context.训练编排（TrainingOrchestration）" source="domain/FederationLearningPlatform/context/TrainingOrchestration" -->
### 7.8 训练编排（Training Orchestration）/FLP-TO-SECTION-8

| 测试域 | 角色/触发条件 | 输入 | 预期结果 | 优先级依据 |
| --- | --- | --- | --- | --- |
| 定义训练运行配置（Define Training Run Configuration） | MLOps 工程师（MLOps Engineer） | 定义训练运行配置（Define Training Run Configuration） | 训练运行配置已定义（Training Run Configuration Defined）, 状态: 草稿（Draft） | 生命周期起点 |
| 更新训练运行配置（Update Training Run Configuration） | MLOps 工程师（MLOps Engineer） | 更新训练运行配置（Update Training Run Configuration） | 训练运行配置已更新（Training Run Configuration Updated）, 状态: 草稿（Draft） | 规则已覆盖 |
| 锁定训练运行配置（Lock Training Run Configuration） | 训练提交时锁定配置（Lock Configuration When Training Submitted） | 锁定训练运行配置（Lock Training Run Configuration） | 训练运行配置已锁定（Training Run Configuration Locked）, 状态: 已锁定（Locked） | 需评审确认 |
| 训练运行配置目录（Training Run Configuration Catalog） | 系统 | - | 尚未明确 | 需评审确认 |
| 创建训练作业（Create Training Job） | 研究主管（Research Lead） | 创建训练作业（Create Training Job） | 训练作业已创建（Training Job Created）, 状态: 草稿（Draft） | 生命周期起点 |
| 提交训练作业（Submit Training Job） | 研究主管（Research Lead） | 提交训练作业（Submit Training Job） | 训练作业已提交（Training Job Submitted）, 状态: 已提交（Submitted） | 规则已覆盖 |
| 训练参与者资格（Training Participant Eligibility） | 系统 | - | 尚未明确 | 需评审确认 |
| 暂停训练作业（Pause Training Job） | MLOps 工程师（MLOps Engineer） | 暂停训练作业（Pause Training Job） | 训练作业已暂停（Training Job Paused）, 状态: 已暂停（Paused） | 需评审确认 |
| 恢复训练作业（Resume Training Job） | MLOps 工程师（MLOps Engineer） | 恢复训练作业（Resume Training Job） | 训练作业已恢复（Training Job Resumed）, 状态: 运行中（Running） | 需评审确认 |
| 取消训练作业（Cancel Training Job） | MLOps 工程师（MLOps Engineer） | 取消训练作业（Cancel Training Job） | 训练作业已取消（Training Job Canceled）, 状态: 已取消（Canceled） | 需评审确认 |
| 安排下一训练轮次（Schedule Next Training Round） | 当训练应继续时选择下一轮参与者（Select Next Round Participants When Training Should Continue） | - | 尚未明确 | 需评审确认 |
| 完成训练作业（Complete Training Job） | 轮次预算耗尽时完成作业（Complete Job When Round Budget Exhausted）, 达到全局指标时完成作业（Complete Job When Global Metric Reached） | 完成训练作业（Complete Training Job） | 训练作业已完成（Training Job Completed）, 状态: 已完成（Completed） | 需评审确认 |
| 训练作业仪表板（Training Job Dashboard） | 系统 | - | 尚未明确 | 需评审确认 |
| 选择训练轮次参与者（Select Training Round Participants） | 训练提交时选择初始轮次参与者（Select Initial Round Participants When Training Submitted） | 选择训练轮次参与者（Select Training Round Participants） | 训练轮次参与者已选择（Training Round Participants Selected）, 训练轮次参与者选择失败（Training Round Participant Selection Failed）, 状态: 参与者已选择（Participants Selected） | 生命周期起点 |
| 重试训练轮次参与者选择（Retry Training Round Participant Selection） | 训练操作员（Training Operator） | 重试训练轮次参与者选择（Retry Training Round Participant Selection） | 训练轮次参与者选择重试已请求（Training Round Participant Selection Retry Requested） | 规则已覆盖 |
| 开始训练轮次（Start Training Round） | 参与者已选择时启动轮次（Start Round When Participants Selected） | 开始训练轮次（Start Training Round） | 训练轮次已启动（Training Round Started）, 训练轮次启动失败（Training Round Start Failed）, 状态: 运行中（Running） | 规则已覆盖 |
| 生成参与者执行计划（Generate Participant Execution Plan） | 为已选择的运行时生成计划（Generate Plan For Selected Runtime） | 生成参与者执行计划（Generate Participant Execution Plan） | 参与者执行计划已生成（Participant Execution Plan Generated）, 状态: 计划已生成（Plan Generated） | 生命周期起点 |
| 分发参与者执行计划（Dispatch Participant Execution Plan） | 生成时分发计划（Dispatch Plan When Generated） | 分发参与者执行计划（Dispatch Participant Execution Plan） | 参与者执行计划已分发（Participant Execution Plan Dispatched）, 状态: 计划已分发（Plan Dispatched） | 需评审确认 |
| 提交模型更新提交（Submit Model Update Submission） | 运行时代理（Runtime Agent） | 提交模型更新提交（Submit Model Update Submission） | 模型更新提交已接收（Model Update Submission Received）, 状态: 已接收（Received） | 需评审确认 |
| 评估模型更新提交（Evaluate Model Update Submission） | 收到时评估模型更新（Evaluate Model Update When Received） | 评估模型更新提交（Evaluate Model Update Submission） | 模型更新提交已接受（Model Update Submission Accepted）, 模型更新提交已拒绝（Model Update Submission Rejected）, 状态: 已评估（Evaluated） | 规则已覆盖 |
| 请求安全聚合（Request Secure Aggregation） | 在已评估更新完成时请求聚合（Request Aggregation When Evaluated Updates Complete） | 请求安全聚合（Request Secure Aggregation） | 已请求安全聚合（Secure Aggregation Requested）, 状态: 聚合中（Aggregating） | 需评审确认 |
| 完成安全聚合（Complete Secure Aggregation） | 系统 | 完成安全聚合（Complete Secure Aggregation） | 全局模型已更新（Global Model Updated）, 状态: 评估全局模型中（Evaluating Global Model） | 需评审确认 |
| 提交全局模型评估（Submit Global Model Evaluation） | 系统 | 提交全局模型评估（Submit Global Model Evaluation） | 全局模型评估已提交（Global Model Evaluation Submitted） | 需评审确认 |
| 完成训练轮次（Complete Training Round） | 全局模型评估后结束轮次（Finish Round After Global Model Evaluated） | 完成训练轮次（Complete Training Round） | 训练轮次已完成（Training Round Completed）, 状态: 已完成（Completed） | 规则已覆盖 |
| 失败训练轮次（Fail Training Round） | 聚合运行时（Aggregation Runtime） | 失败训练轮次（Fail Training Round） | 训练轮次失败（Training Round Failed）, 状态: 已失败（Failed） | 需评审确认 |
| 训练轮次进度（Training Round Progress） | 系统 | - | 尚未明确 | 需评审确认 |

<!-- em:section id="test.context.模型生命周期（ModelLifecycle）" source="domain/FederationLearningPlatform/context/ModelLifecycle" -->
### 7.9 模型生命周期（Model Lifecycle）/FLP-TO-SECTION-9

| 测试域 | 角色/触发条件 | 输入 | 预期结果 | 优先级依据 |
| --- | --- | --- | --- | --- |
| 注册候选模型（Register Candidate Model） | 训练作业完成时注册最终模型（Register Final Model When Training Job Completed） | 注册候选模型（Register Candidate Model） | 模型候选已注册（Model Candidate Registered）, 状态: 候选（Candidate） | 生命周期起点 |
| 记录模型评估包（Record Model Evaluation Package） | 治理审核员（Governance Reviewer） | 记录模型评估包（Record Model Evaluation Package） | 模型评估包已记录（Model Evaluation Package Recorded）, 状态: 评估已打包（Evaluation Packaged） | 规则已覆盖 |
| 批准模型（Approve Model） | 治理审核员（Governance Reviewer） | 批准模型（Approve Model） | 模型已批准（Model Approved）, 状态: 已批准（Approved） | 规则已覆盖 |
| 提升模型至生产（Promote Model To Production） | 发布管理员（Release Manager） | 提升模型至生产（Promote Model To Production） | 模型已提升到生产环境（Model Promoted To Production）, 状态: 生产（Production） | 规则已覆盖 |
| 回滚模型（Rollback Model） | 发布管理员（Release Manager） | 回滚模型（Rollback Model） | 模型已回滚（Model Rolled Back）, 状态: 已回滚（Rolled Back） | 需评审确认 |
| 停用模型（Retire Model） | 发布管理员（Release Manager） | 停用模型（Retire Model） | 模型已退役（Model Retired）, 状态: 已停用（Retired） | 需评审确认 |
| 模型目录（Model Catalog） | 系统 | - | 尚未明确 | 需评审确认 |

<!-- em:section id="test.context.运行时监控（RuntimeMonitoring）" source="domain/FederationLearningPlatform/context/RuntimeMonitoring" -->
### 7.10 运行时监控（Runtime Monitoring）/FLP-TO-SECTION-10

| 测试域 | 角色/触发条件 | 输入 | 预期结果 | 优先级依据 |
| --- | --- | --- | --- | --- |
| 运行时最新遥测（Runtime Telemetry Latest） | 系统 | - | 尚未明确 | 需评审确认 |
| 检测运行时代理离线（Detect Runtime Agent Offline） | 从遥测检测离线运行时代理（Detect Offline Runtime Agent From Telemetry） | 检测运行时代理离线（Detect Runtime Agent Offline） | 已检测到运行时代理离线（Runtime Agent Offline Detected）, 状态: 离线（Offline） | 生命周期起点 |
| 标记运行时代理已恢复（Mark Runtime Agent Recovered） | 从遥测标记运行时代理已恢复（Mark Recovered Runtime Agent From Telemetry） | 标记运行时代理已恢复（Mark Runtime Agent Recovered） | 运行时代理已恢复（Runtime Agent Recovered）, 状态: 健康（Healthy） | 需评审确认 |
| 检测运行时节点资源压力（Detect Runtime Node Resource Pressure） | 从遥测检测资源压力（Detect Resource Pressure From Telemetry） | 检测运行时节点资源压力（Detect Runtime Node Resource Pressure） | 已检测到运行时节点资源压力（Runtime Node Resource Pressure Detected）, 状态: 已检测到压力（Pressure Detected） | 生命周期起点 |
| 记录运行时节点清单（Record Runtime Node Inventory） | 代理报告时记录运行时节点清单（Record Runtime Node Inventory When Agent Reported） | 记录运行时节点清单（Record Runtime Node Inventory） | 运行时节点清单已报告（Runtime Node Inventory Reported）, 状态: 已报告（Reported） | 生命周期起点 |
| 运行时节点清单视图（Runtime Node Inventory View） | 系统 | - | 尚未明确 | 需评审确认 |
| 运行时节点最新资源（Runtime Node Resource Latest） | 系统 | - | 尚未明确 | 需评审确认 |
| 检测运行时节点容量变更（Detect Runtime Node Capacity Change） | 从资源遥测检测容量变化（Detect Capacity Change From Resource Telemetry） | 检测运行时节点容量变更（Detect Runtime Node Capacity Change） | 运行时节点容量已更改（Runtime Node Capacity Changed）, 状态: 容量已变更（Capacity Changed） | 生命周期起点 |
| 运行时健康仪表板（Runtime Health Dashboard） | 系统 | - | 尚未明确 | 需评审确认 |
| 引发训练告警（Raise Training Alert） | 在节点资源压力时发出警报（Raise Alert On Node Resource Pressure） | 引发训练告警（Raise Training Alert） | 训练警报已发出（Training Alert Raised）, 状态: 已引发（Raised） | 生命周期起点 |
| 训练告警目录（Training Alert Catalog） | 系统 | - | 尚未明确 | 需评审确认 |
| 确认训练告警（Acknowledge Training Alert） | MLOps 工程师（MLOps Engineer） | 确认训练告警（Acknowledge Training Alert） | 训练警报已确认（Training Alert Acknowledged）, 状态: 已确认（Acknowledged） | 需评审确认 |
| 解决训练告警（Resolve Training Alert） | MLOps 工程师（MLOps Engineer） | 解决训练告警（Resolve Training Alert） | 训练警报已解决（Training Alert Resolved）, 状态: 已解决（Resolved） | 需评审确认 |
| 追加审计跟踪（Append Audit Trail） | 审计关键训练事件（Audit Critical Training Events）, 审计联邦已激活（Audit Federation Activated）, 审计联邦已暂停（Audit Federation Suspended）, 审计联邦已重新激活（Audit Federation Reactivated）, 审计参与者已加入（Audit Participant Joined）, 审计参与者已暂停（Audit Participant Suspended）, 审计数据集批准（Audit Dataset Approval）, 审计数据集批准已撤销（Audit Dataset Approval Revoked）, 审计训练作业已提交（Audit Training Job Submitted）, 审计模型已提升至生产（Audit Model Promoted To Production） | 追加审计跟踪（Append Audit Trail） | 审计跟踪已追加（Audit Trail Appended）, 状态: 已追加（Appended） | 生命周期起点 |
| 审计记录日志（Audit Record Log） | 系统 | - | 尚未明确 | 需评审确认 |

<!-- em:section id="test.context.运行时治理（RuntimeGovernance）" source="domain/FederationLearningPlatform/context/RuntimeGovernance" -->
### 7.11 运行时治理（Runtime Governance）/FLP-TO-SECTION-11

| 测试域 | 角色/触发条件 | 输入 | 预期结果 | 优先级依据 |
| --- | --- | --- | --- | --- |
| 激活运行时标识（Activate Runtime Identity） | 平台管理员（Platform Admin） | 激活运行时标识（Activate Runtime Identity） | 运行时身份已激活（Runtime Identity Activated）, 状态: 已激活（Active） | 生命周期起点 |
| 撤销运行时标识（Revoke Runtime Identity） | 安全审核员（Security Reviewer） | 撤销运行时标识（Revoke Runtime Identity） | 运行时身份已撤销（Runtime Identity Revoked）, 状态: 已撤销（Revoked） | 需评审确认 |
| 检测运行时能力（Detect Runtime Capabilities） | 运行时代理（Runtime Agent） | 检测运行时能力（Detect Runtime Capabilities） | 运行时能力已检测（Runtime Capabilities Detected）, 状态: 已检测到（Detected） | 生命周期起点 |
| 运行时标识目录（Runtime Identity Catalog） | 系统 | - | 尚未明确 | 需评审确认 |
| 运行时能力目录（Runtime Capability Catalog） | 系统 | - | 尚未明确 | 需评审确认 |

<!-- em:section id="test.context.安全聚合（SecureAggregation）" source="domain/FederationLearningPlatform/context/SecureAggregation" -->
### 7.12 安全聚合（Secure Aggregation）/FLP-TO-SECTION-12

| 测试域 | 角色/触发条件 | 输入 | 预期结果 | 优先级依据 |
| --- | --- | --- | --- | --- |
| 创建安全聚合会话（Create Secure Aggregation Session） | 在请求聚合时创建会话（Create Session When Aggregation Requested） | 创建安全聚合会话（Create Secure Aggregation Session） | 安全聚合会话已创建（Secure Aggregation Session Created）, 状态: 已规划（Planned） | 生命周期起点 |
| 选择安全聚合参与者（Select Secure Aggregation Participants） | 会话创建时选择参与者（Select Participants When Session Created） | 选择安全聚合参与者（Select Secure Aggregation Participants） | 安全聚合参与者已选择（Secure Aggregation Participants Selected）, 状态: 参与者已选择（Participants Selected） | 需评审确认 |
| 准备同态加密上下文（Prepare Homomorphic Encryption Context） | 安全聚合服务（Secure Aggregation Service） | 准备同态加密上下文（Prepare Homomorphic Encryption Context） | 同态加密上下文已准备（Homomorphic Encryption Context Prepared）, 状态: 已准备加密上下文（Encryption Context Prepared） | 需评审确认 |
| 记录加密模型更新（Record Encrypted Model Update） | 安全聚合服务（Secure Aggregation Service） | 记录加密模型更新（Record Encrypted Model Update） | 已接收加密模型更新（Encrypted Model Update Received） | 需评审确认 |
| 完成同态聚合会话（Complete Homomorphic Aggregation Session） | 安全聚合服务（Secure Aggregation Service） | 完成同态聚合会话（Complete Homomorphic Aggregation Session） | 安全聚合已完成（Secure Aggregation Completed）, 状态: 已完成（Completed） | 需评审确认 |
| 失败安全聚合会话（Fail Secure Aggregation Session） | 安全聚合服务（Secure Aggregation Service） | 失败安全聚合会话（Fail Secure Aggregation Session） | 安全聚合失败（Secure Aggregation Failed）, 状态: 已失败（Failed） | 需评审确认 |
| 安全聚合会话目录（Secure Aggregation Session Catalog） | 系统 | - | 尚未明确 | 需评审确认 |

<!-- em:section id="test.context.运行时代理操作（RuntimeAgentOperations）" source="domain/FederationLearningPlatform/context/RuntimeAgentOperations" -->
### 7.13 运行时代理操作（Runtime Agent Operations）/FLP-TO-SECTION-13

| 测试域 | 角色/触发条件 | 输入 | 预期结果 | 优先级依据 |
| --- | --- | --- | --- | --- |
| 声明数据集（Declare Dataset） | 数据所有者（Data Owner） | 声明数据集（Declare Dataset） | 数据集已声明（Dataset Declared）, 状态: 已注册（Registered） | 生命周期起点 |
| 配置运行时数据集绑定（Configure Runtime Dataset Binding） | 节点操作员（Node Operator） | 配置运行时数据集绑定（Configure Runtime Dataset Binding） | 运行时数据集绑定已配置（Runtime Dataset Binding Configured）, 状态: 已配置（Configured） | 生命周期起点 |
| 运行时数据集绑定目录（Runtime Dataset Binding Catalog） | 系统 | - | 尚未明确 | 需评审确认 |
| 验证代理数据集访问（Validate Agent Dataset Access） | 运行时代理（Runtime Agent） | 验证代理数据集访问（Validate Agent Dataset Access） | 代理数据集访问已验证（Agent Dataset Access Validated）, 代理数据集访问验证失败（Agent Dataset Access Validation Failed）, 状态: 已检查（Checked） | 生命周期起点 |
| 重新验证代理数据集访问（Revalidate Agent Dataset Access） | 节点操作员（Node Operator） | 重新验证代理数据集访问（Revalidate Agent Dataset Access） | 代理数据集访问已重新验证（Agent Dataset Access Revalidated）, 代理数据集访问重新验证失败（Agent Dataset Access Revalidation Failed）, 状态: 已检查（Checked） | 规则已覆盖 |
| 代理数据集访问验证目录（Agent Dataset Access Validation Catalog） | 系统 | - | 尚未明确 | 需评审确认 |
| 分析代理数据集（Profile Agent Dataset） | 运行时代理（Runtime Agent） | 分析代理数据集（Profile Agent Dataset） | 代理数据集元数据已报告（Agent Dataset Metadata Reported）, 代理数据集分析失败（Agent Dataset Profiling Failed）, 状态: 已报告（Reported） | 生命周期起点 |
| 重新分析代理数据集（Reprofile Agent Dataset） | 节点操作员（Node Operator） | 重新分析代理数据集（Reprofile Agent Dataset） | 代理数据集已重新分析（Agent Dataset Reprofiled）, 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed）, 状态: 已报告（Reported） | 规则已覆盖 |
| 验证数据集合约（Validate Dataset Contract） | 自动验证数据集合约（Validate Dataset Contract Automatically） | 验证数据集合约（Validate Dataset Contract） | 数据集合约已验证（Dataset Contract Validated）, 数据集合约验证失败（Dataset Contract Validation Failed）, 状态: 合约验证已完成（Contract Validation Completed） | 规则已覆盖 |
| 重试数据集合约验证（Retry Dataset Contract Validation） | 数据所有者（Data Owner） | 重试数据集合约验证（Retry Dataset Contract Validation） | 数据集合约已重新验证（Dataset Contract Revalidated）, 数据集合约重新验证失败（Dataset Contract Revalidation Failed）, 状态: 合约验证已完成（Contract Validation Completed） | 规则已覆盖 |
| 拒绝数据集用于训练（Reject Dataset For Training） | 合规专员（Compliance Officer） | 拒绝数据集用于训练（Reject Dataset For Training） | 数据集已拒绝用于训练（Dataset Rejected For Training）, 状态: 已拒绝（Rejected） | 需评审确认 |
| 批准数据集用于训练（Approve Dataset For Training） | 合规专员（Compliance Officer） | 批准数据集用于训练（Approve Dataset For Training） | 数据集已批准用于训练（Dataset Approved For Training）, 状态: 已批准（Approved） | 规则已覆盖 |
| 撤销数据集训练审批（Revoke Dataset Training Approval） | 合规专员（Compliance Officer） | 撤销数据集训练审批（Revoke Dataset Training Approval） | 数据集训练批准已撤销（Dataset Training Approval Revoked）, 状态: 审批已撤销（Approval Revoked） | 需评审确认 |
| 数据集能力（Dataset Capability） | 系统 | - | 尚未明确 | 需评审确认 |
| 数据集就绪状态（Dataset Readiness） | 系统 | - | 尚未明确 | 需评审确认 |
| 接收参与者执行计划（Receive Participant Execution Plan） | 分发时接收执行计划（Receive Execution Plan When Dispatched） | 接收参与者执行计划（Receive Participant Execution Plan） | 执行计划已接收（Execution Plan Received）, 状态: 计划已接收（Plan Received） | 生命周期起点 |
| 接受执行计划（Accept Execution Plan） | 收到执行计划时接受执行计划（Accept Execution Plan When Execution Plan Received） | 接受执行计划（Accept Execution Plan） | 执行计划已接受（Execution Plan Accepted）, 执行计划已拒绝（Execution Plan Rejected）, 状态: 计划已接受（Plan Accepted） | 规则已覆盖 |
| 开始轮次执行（Start Round Execution） | 计划接受时启动轮次执行（Start Round Execution When Plan Accepted） | 开始轮次执行（Start Round Execution） | 轮次执行已启动（Round Execution Started）, 轮次执行启动失败（Round Execution Start Failed）, 状态: 运行中（Running） | 规则已覆盖 |
| 观察运行时引擎作业（Observe Runtime Engine Job） | 轮次执行启动时观察运行时引擎作业（Observe Runtime Engine Job When Round Execution Started） | 观察运行时引擎作业（Observe Runtime Engine Job） | 运行时引擎作业已观察（Runtime Engine Job Observed）, 状态: 运行中（Running） | 规则已覆盖 |
| 完成轮次执行（Complete Round Execution） | 运行时引擎作业完成时完成轮次执行（Complete Round Execution When Runtime Engine Job Completed） | 完成轮次执行（Complete Round Execution） | 轮次执行已完成（Round Execution Completed）, 状态: 已完成（Completed） | 需评审确认 |
| 失败轮次执行（Fail Round Execution） | Fail Round Execution When Runtime Engine Job Failed | 失败轮次执行（Fail Round Execution） | 轮次执行失败（Round Execution Failed）, 状态: 已失败（Failed） | 需评审确认 |
| 启动失败后重试轮次执行（Retry Round Execution After Start Failure） | 节点操作员（Node Operator） | 启动失败后重试轮次执行（Retry Round Execution After Start Failure） | Round Execution Start Retry Started, 轮次执行启动重试失败（Round Execution Start Retry Failed）, 状态: 已重试（Retried） | 需评审确认 |
| 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure） | 节点操作员（Node Operator） | 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure） | Round Execution Runtime Retry Started, 轮次执行运行时重试失败（Round Execution Runtime Retry Failed）, 状态: 已重试（Retried） | 需评审确认 |
| 提交代理本地模型更新（Submit Agent Local Model Update） | Submit Local Model Update When Round Execution Completed | 提交代理本地模型更新（Submit Agent Local Model Update） | Agent Local Model Update Submitted, 状态: 更新已提交（Update Submitted） | 需评审确认 |
| 完成后释放运行时引擎作业（Release Runtime Engine Job After Completion） | Release Runtime Engine Job When Round Execution Completed | 完成后释放运行时引擎作业（Release Runtime Engine Job After Completion） | Runtime Engine Job Released, 状态: 运行时引擎已释放（Runtime Engine Released） | 需评审确认 |
| 失败后释放运行时引擎作业（Release Runtime Engine Job After Failure） | Release Runtime Engine Job When Round Execution Failed | 失败后释放运行时引擎作业（Release Runtime Engine Job After Failure） | Runtime Engine Job Release Failed Or Skipped, 状态: 运行时引擎释放已处理（Runtime Engine Release Handled） | 需评审确认 |
| 启动失败后释放运行时引擎作业（Release Runtime Engine Job After Start Failure） | Release Runtime Engine Job When Start Failed | 启动失败后释放运行时引擎作业（Release Runtime Engine Job After Start Failure） | Runtime Engine Job Release Failed Or Skipped After Start Failure, 状态: 运行时引擎释放已处理（Runtime Engine Release Handled） | 需评审确认 |
| 重试失败后释放运行时引擎作业（Release Runtime Engine Job After Retry Failure） | Release Runtime Engine Job When Start Retry Failed | 重试失败后释放运行时引擎作业（Release Runtime Engine Job After Retry Failure） | Runtime Engine Job Release Failed Or Skipped After Retry, 状态: 运行时引擎释放已处理（Runtime Engine Release Handled） | 需评审确认 |
| 运行时重试失败后释放运行时引擎作业（Release Runtime Engine Job After Runtime Retry Failure） | Release Runtime Engine Job When Runtime Retry Failed | 运行时重试失败后释放运行时引擎作业（Release Runtime Engine Job After Runtime Retry Failure） | Runtime Engine Job Release Failed Or Skipped After Runtime Retry, 状态: 运行时引擎释放已处理（Runtime Engine Release Handled） | 需评审确认 |
| 轮次执行目录（Round Execution Catalog） | 系统 | - | 尚未明确 | 需评审确认 |
| 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration） | 运行时代理（Runtime Agent） | 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration） | Runtime Agent Bootstrap Configuration Loaded, Runtime Agent Bootstrap Configuration Load Failed, 状态: 引导程序已加载（Bootstrap Loaded） | 生命周期起点 |
| 报告运行时代理已启动（Report Runtime Agent Started） | 运行时代理（Runtime Agent） | 报告运行时代理已启动（Report Runtime Agent Started） | Runtime Agent Started, 状态: 已启动（Started） | 需评审确认 |
| 报告运行时实例自检已通过（Report Runtime Instance Self Check Passed） | 运行时代理（Runtime Agent） | 报告运行时实例自检已通过（Report Runtime Instance Self Check Passed） | Runtime Instance Self Check Passed, 状态: 就绪（Ready） | 需评审确认 |
| 运行时代理生命周期目录（Runtime Agent Lifecycle Catalog） | 系统 | - | 尚未明确 | 需评审确认 |
| 报告运行时实例已连接（Report Runtime Instance Connected） | Report Runtime Instance Connected After Self Check | 报告运行时实例已连接（Report Runtime Instance Connected） | Agent Runtime Connection Report Failed, Agent Runtime Connection Established, 状态: 已连接（Connected） | 生命周期起点 |
| 代理运行时基础设施连接目录（Agent Runtime Infrastructure Connection Catalog） | 系统 | - | 尚未明确 | 需评审确认 |
| 代理运行时遥测最新（Agent Runtime Telemetry Latest） | 系统 | - | 尚未明确 | 需评审确认 |
| 报告代理运行时节点清单（Report Agent Runtime Node Inventory） | Report Node Inventory When Inventory Changed | 报告代理运行时节点清单（Report Agent Runtime Node Inventory） | 代理运行时节点清单已报告（Agent Runtime Node Inventory Reported）, 状态: 已报告（Reported） | 生命周期起点 |
| 代理运行时节点清单目录（Agent Runtime Node Inventory Catalog） | 系统 | - | 尚未明确 | 需评审确认 |
| 代理运行时节点资源最新（Agent Runtime Node Resource Latest） | 系统 | - | 尚未明确 | 需评审确认 |

## 八、测试场景覆盖/FLP-TO-TSC

| 测试单元 | 正常路径 | 业务规则/异常 | 状态与事件 | 读侧/自动化 | 覆盖重点 |
| --- | --- | --- | --- | --- | --- |
| 注册组织（Register Organization） | 成功完成 注册组织（Register Organization）。 | 组织名称唯一（Organization Name Unique） / 拒绝重复组织（Reject Duplicate Organization） - 当 注册组织（Register Organization）, 则 拒绝: 组织已存在. | 成功时产生: 组织已注册（Organization Registered）; 状态: 已注册（Registered） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 激活组织（Activate Organization） | 成功完成 激活组织（Activate Organization）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 组织已激活（Organization Activated）; 状态: 已激活（Active） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 停用组织（Deactivate Organization） | 成功完成 停用组织（Deactivate Organization）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 组织已停用（Organization Deactivated）; 状态: 已停用（Deactivated） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 重新激活组织（Reactivate Organization） | 成功完成 重新激活组织（Reactivate Organization）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 组织已重新激活（Organization Reactivated）; 状态: 已激活（Active） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 组织目录（Organization Directory） | 成功完成 组织目录（Organization Directory）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 组织目录（Organization Directory） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 创建联邦（Create Federation） | 成功完成 创建联邦（Create Federation）。 | 联邦名称唯一（Federation Name Unique） / 拒绝重复联邦（Reject Duplicate Federation） - 当 创建联邦（Create Federation）, 则 拒绝: 联邦已存在. | 成功时产生: 联邦已创建（Federation Created）; 状态: 草稿（Draft） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 激活联邦（Activate Federation） | 成功完成 激活联邦（Activate Federation）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 联邦已激活（Federation Activated）; 状态: 已激活（Active） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 暂停联邦（Suspend Federation） | 成功完成 暂停联邦（Suspend Federation）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 联邦已暂停（Federation Suspended）; 状态: 已暂停（Suspended） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 重新激活联邦（Reactivate Federation） | 成功完成 重新激活联邦（Reactivate Federation）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 联邦已重新激活（Federation Reactivated）; 状态: 已激活（Active） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 联邦概览（Federation Overview） | 成功完成 联邦概览（Federation Overview）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 联邦概览（Federation Overview） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 邀请参与者（Invite Participant） | 成功完成 邀请参与者（Invite Participant）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 参与者已邀请（Participant Invited）; 状态: 已邀请（Invited） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 联邦成员资格目录（Federation Membership Directory） | 成功完成 联邦成员资格目录（Federation Membership Directory）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 联邦成员资格目录（Federation Membership Directory） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 批准参与者（Approve Participant） | 成功完成 批准参与者（Approve Participant）。 | 仅已邀请的激活组织加入联邦（Only Invited Active Organizations Join Federation） / 批准已邀请的参与者（Approve Invited Participant） - 当 批准参与者（Approve Participant）, 则 参与者已加入（Participant Joined）.; 仅已邀请的激活组织加入联邦（Only Invited Active Organizations Join Federation） / 拒绝未邀请参与者的审批（Reject Uninvited Participant Approval） - 当 批准参与者（Approve Participant）, 则 拒绝: 需要参与者邀请. | 成功时产生: 参与者已加入（Participant Joined）; 状态: 已激活（Active） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 拒绝参与者（Reject Participant） | 成功完成 拒绝参与者（Reject Participant）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 参与者已拒绝（Participant Rejected）; 状态: 已拒绝（Rejected） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 撤销参与者邀请（Revoke Participant Invitation） | 成功完成 撤销参与者邀请（Revoke Participant Invitation）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 参与者邀请已撤销（Participant Invitation Revoked）; 状态: 邀请已撤销（Invitation Revoked） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 暂停参与者（Suspend Participant） | 成功完成 暂停参与者（Suspend Participant）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 参与者已暂停（Participant Suspended）; 状态: 已暂停（Suspended） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 移除参与者（Remove Participant） | 成功完成 移除参与者（Remove Participant）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 参与者已移除（Participant Removed）; 状态: 已移除（Removed） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 注册字典（Register Dictionary） | 成功完成 注册字典（Register Dictionary）。 | 字典代码唯一（Dictionary Code Unique） / 拒绝重复字典（Reject Duplicate Dictionary） - 当 注册字典（Register Dictionary）, 则 拒绝: 字典已存在. | 成功时产生: 字典已注册（Dictionary Registered）; 状态: 已注册（Registered） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 更新字典（Update Dictionary） | 成功完成 更新字典（Update Dictionary）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 字典已更新（Dictionary Updated） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 归档字典（Archive Dictionary） | 成功完成 归档字典（Archive Dictionary）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 字典已归档（Dictionary Archived）; 状态: 已归档（Archived） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 字典目录（Dictionary Catalog） | 成功完成 字典目录（Dictionary Catalog）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 字典目录（Dictionary Catalog） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 添加字典值（Add Dictionary Value） | 成功完成 添加字典值（Add Dictionary Value）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 字典值已添加（Dictionary Value Added）; 状态: 已激活（Active） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 停用字典值（Disable Dictionary Value） | 成功完成 停用字典值（Disable Dictionary Value）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 字典值已停用（Dictionary Value Disabled）; 状态: 已停用（Disabled） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 启用字典值（Enable Dictionary Value） | 成功完成 启用字典值（Enable Dictionary Value）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 字典值已启用（Dictionary Value Enabled）; 状态: 已激活（Active） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 字典值目录（Dictionary Value Catalog） | 成功完成 字典值目录（Dictionary Value Catalog）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 字典值目录（Dictionary Value Catalog） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 暂存文件上传（Stage File Upload） | 成功完成 暂存文件上传（Stage File Upload）。 | 在提交业务命令前暂存已上传文件（Uploaded File Is Staged Before Business Command Submission） / 为模型导入暂存上传（Stage Upload For Model Import） - 当 暂存文件上传（Stage File Upload）, 则 文件上传已暂存（File Upload Staged）. | 成功时产生: 文件上传已暂存（File Upload Staged）; 状态: 已暂存（Staged） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 标记暂存文件已消费（Mark Staged File Consumed） | 成功完成 标记暂存文件已消费（Mark Staged File Consumed）。 | 已消费的暂存文件不再可由用户丢弃（Consumed Staged File Is No Longer User Discardable） / 通过模型导入标记文件为已消费（Mark File Consumed By Model Import） - 当 标记暂存文件已消费（Mark Staged File Consumed）, 则 暂存文件已消费（Staged File Consumed）. | 成功时产生: 暂存文件已消费（Staged File Consumed）; 状态: 已消费（Consumed） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 放弃暂存文件（Discard Staged File） | 成功完成 放弃暂存文件（Discard Staged File）。 | 仅可丢弃未消费的暂存文件（Only Unconsumed Staged Files Can Be Discarded） / 丢弃未消费的暂存文件（Discard Unconsumed Staged File） - 当 放弃暂存文件（Discard Staged File）, 则 暂存文件已丢弃（Staged File Discarded）.; 仅可丢弃未消费的暂存文件（Only Unconsumed Staged Files Can Be Discarded） / 拒绝消费后丢弃（Reject Discard After Consumption） - 当 放弃暂存文件（Discard Staged File）, 则 拒绝: 暂存文件已被消费. | 成功时产生: 暂存文件已丢弃（Staged File Discarded）; 状态: 已放弃（Discarded） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 使暂存文件过期（Expire Staged File） | 成功完成 使暂存文件过期（Expire Staged File）。 | 已过期的暂存文件无法被消费（Expired Staged File Cannot Be Consumed） / 未消费暂存文件过期（Expire Unconsumed Staged File） - 当 使暂存文件过期（Expire Staged File）, 则 暂存文件已过期（Staged File Expired）.; 已过期的暂存文件无法被消费（Expired Staged File Cannot Be Consumed） / 过期后拒绝消费（Reject Consumption After Expiration） - 当 标记暂存文件已消费（Mark Staged File Consumed）, 则 拒绝: 暂存文件已过期. | 成功时产生: 暂存文件已过期（Staged File Expired）; 状态: 已过期（Expired） | 保留期结束后暂存文件过期（Expire Staged File When Retention Elapsed） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 暂存文件目录（Staged File Catalog） | 成功完成 暂存文件目录（Staged File Catalog）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 暂存文件目录（Staged File Catalog） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 注册运行时基础设施包（Register Runtime Infrastructure Package） | 成功完成 注册运行时基础设施包（Register Runtime Infrastructure Package）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 运行时基础设施包已注册（Runtime Infrastructure Package Registered）; 状态: 已注册（Registered） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 运行时基础设施包目录（Runtime Infrastructure Package Catalog） | 成功完成 运行时基础设施包目录（Runtime Infrastructure Package Catalog）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 运行时基础设施包目录（Runtime Infrastructure Package Catalog） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 创建运行时安装计划（Create Runtime Installation Plan） | 成功完成 创建运行时安装计划（Create Runtime Installation Plan）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 运行时安装计划已创建（Runtime Installation Plan Created）; 状态: 已规划（Planned） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 运行时安装计划目录（Runtime Installation Plan Catalog） | 成功完成 运行时安装计划目录（Runtime Installation Plan Catalog）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 运行时安装计划目录（Runtime Installation Plan Catalog） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 运行时安装指南（Runtime Installation Guide） | 成功完成 运行时安装指南（Runtime Installation Guide）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 运行时安装指南（Runtime Installation Guide） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 规划运行时基础设施（Plan Runtime Infrastructure） | 成功完成 规划运行时基础设施（Plan Runtime Infrastructure）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 运行时基础设施已计划（Runtime Infrastructure Planned）; 状态: 已规划（Planned） | 安装计划创建时规划运行时基础设施（Plan Runtime Infrastructure When Installation Plan Created） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 注册运行时基础设施（Register Runtime Infrastructure） | 成功完成 注册运行时基础设施（Register Runtime Infrastructure）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 运行时基础设施已注册（Runtime Infrastructure Registered）; 状态: 已注册（Registered） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 验证运行时基础设施（Verify Runtime Infrastructure） | 成功完成 验证运行时基础设施（Verify Runtime Infrastructure）。 | 运行时基础设施验证结果（Runtime Infrastructure Verification Outcome） / 运行时基础设施验证通过（Runtime Infrastructure Verification Passed） - 当 验证运行时基础设施（Verify Runtime Infrastructure）, 则 运行时基础设施已验证（Runtime Infrastructure Verified）.; 运行时基础设施验证结果（Runtime Infrastructure Verification Outcome） / 运行时基础设施验证失败（Runtime Infrastructure Verification Failed） - 当 验证运行时基础设施（Verify Runtime Infrastructure）, 则 运行时基础设施验证失败（Runtime Infrastructure Verification Failed）. | 成功时产生: 运行时基础设施已验证（Runtime Infrastructure Verified）, 运行时基础设施验证失败（Runtime Infrastructure Verification Failed）; 状态: 已验证（Verified） | 注册时验证基础设施（Verify Infrastructure When Registered） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 部署运行时代理（Deploy Runtime Agent） | 成功完成 部署运行时代理（Deploy Runtime Agent）。 | 运行时代理部署结果（Runtime Agent Deployment Outcome） / 运行时代理部署成功（Runtime Agent Deployment Succeeded） - 当 部署运行时代理（Deploy Runtime Agent）, 则 运行时代理安装成功（Runtime Agent Installation Succeeded）.; 运行时代理部署结果（Runtime Agent Deployment Outcome） / 运行时代理部署失败（Runtime Agent Deployment Failed） - 当 部署运行时代理（Deploy Runtime Agent）, 则 运行时代理安装失败（Runtime Agent Installation Failed）. | 成功时产生: 运行时代理安装成功（Runtime Agent Installation Succeeded）, 运行时代理安装失败（Runtime Agent Installation Failed）; 状态: 代理就绪（Agent Ready） | 在基础设施验证后部署运行时代理（Deploy Runtime Agent When Infrastructure Verified） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 重试运行时代理部署（Retry Runtime Agent Deployment） | 成功完成 重试运行时代理部署（Retry Runtime Agent Deployment）。 | 运行时代理部署重试结果（Runtime Agent Deployment Retry Outcome） / 部署失败后运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded After Failed Deployment） - 当 重试运行时代理部署（Retry Runtime Agent Deployment）, 则 运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）.; 运行时代理部署重试结果（Runtime Agent Deployment Retry Outcome） / 离线后运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded After Offline） - 当 重试运行时代理部署（Retry Runtime Agent Deployment）, 则 运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）.; 运行时代理部署重试结果（Runtime Agent Deployment Retry Outcome） / 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed） - 当 重试运行时代理部署（Retry Runtime Agent Deployment）, 则 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed）.; 运行时代理部署重试结果（Runtime Agent Deployment Retry Outcome） / 已连接时拒绝运行时代理部署重试（Reject Runtime Agent Deployment Retry While Connected） - 当 重试运行时代理部署（Retry Runtime Agent Deployment）, 则 拒绝: 不允许运行时代理部署重试. | 成功时产生: 运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）, 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed）; 状态: 代理就绪（Agent Ready） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 记录运行时连接已建立（Record Runtime Connection Established） | 成功完成 记录运行时连接已建立（Record Runtime Connection Established）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 运行时连接已建立（Runtime Connection Established）; 状态: 已连接（Connected） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 运行时代理端点目录（Runtime Agent Endpoint Catalog） | 成功完成 运行时代理端点目录（Runtime Agent Endpoint Catalog）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 运行时代理端点目录（Runtime Agent Endpoint Catalog） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 运行时基础设施访问视图（Runtime Infrastructure Access View） | 成功完成 运行时基础设施访问视图（Runtime Infrastructure Access View）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 运行时基础设施访问视图（Runtime Infrastructure Access View） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 定义特征架构（Define Feature Schema） | 成功完成 定义特征架构（Define Feature Schema）。 | 特征架构域版本唯一（Feature Schema Domain Version Unique） / 拒绝重复的特征架构版本（Reject Duplicate Feature Schema Version） - 当 定义特征架构（Define Feature Schema）, 则 拒绝: 特征架构版本已存在. | 成功时产生: 特征架构已定义（Feature Schema Defined）; 状态: 草稿（Draft） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 发布特征架构（Publish Feature Schema） | 成功完成 发布特征架构（Publish Feature Schema）。 | 仅可发布草稿架构（Only Draft Schemas Can Be Published） / 发布草稿架构（Publish Draft Schema） - 当 发布特征架构（Publish Feature Schema）, 则 特征架构已发布（Feature Schema Published）. | 成功时产生: 特征架构已发布（Feature Schema Published）; 状态: 已发布（Published） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 弃用特征架构（Deprecate Feature Schema） | 成功完成 弃用特征架构（Deprecate Feature Schema）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 特征架构已弃用（Feature Schema Deprecated）; 状态: 已弃用（Deprecated） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 停用特征架构（Retire Feature Schema） | 成功完成 停用特征架构（Retire Feature Schema）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 特征架构已停用（Feature Schema Retired）; 状态: 已停用（Retired） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 取代特征架构版本（Supersede Feature Schema Version） | 成功完成 取代特征架构版本（Supersede Feature Schema Version）。 | 使用较新版本取代已发布的架构（Supersede Published Schema With Newer Version） / 取代较旧版本（Supersede Older Version） - 当 取代特征架构版本（Supersede Feature Schema Version）, 则 特征架构版本已取代（Feature Schema Version Superseded）. | 成功时产生: 特征架构版本已取代（Feature Schema Version Superseded） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 标记当前推荐的特征架构版本（Mark Current Recommended Feature Schema Version） | 成功完成 标记当前推荐的特征架构版本（Mark Current Recommended Feature Schema Version）。 | 每个域仅一个推荐版本（Only One Recommended Version Per Domain） / 标记新的推荐版本（Mark New Recommended Version） - 当 标记当前推荐的特征架构版本（Mark Current Recommended Feature Schema Version）, 则 已标记当前推荐特征架构版本（Current Recommended Feature Schema Version Marked）. | 成功时产生: 已标记当前推荐特征架构版本（Current Recommended Feature Schema Version Marked） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 当前推荐的特征架构目录（Current Recommended Feature Schema Catalog） | 成功完成 当前推荐的特征架构目录（Current Recommended Feature Schema Catalog）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 当前推荐的特征架构目录（Current Recommended Feature Schema Catalog） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 特征架构目录（Feature Schema Catalog） | 成功完成 特征架构目录（Feature Schema Catalog）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 特征架构目录（Feature Schema Catalog） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 记录运行时数据集元数据（Record Runtime Dataset Metadata） | 成功完成 记录运行时数据集元数据（Record Runtime Dataset Metadata）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 数据集元数据已报告（Dataset Metadata Reported）; 状态: 已报告元数据（Metadata Reported） | 在代理报告时记录数据集元数据（Record Dataset Metadata When Agent Reported） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata） | 成功完成 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 数据集元数据已重新分析（Dataset Metadata Reprofiled）; 状态: 已报告元数据（Metadata Reported） | 代理重新分析时记录数据集元数据（Record Dataset Metadata When Agent Reprofiled） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 运行时数据集元数据目录（Runtime Dataset Metadata Catalog） | 成功完成 运行时数据集元数据目录（Runtime Dataset Metadata Catalog）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 运行时数据集元数据目录（Runtime Dataset Metadata Catalog） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 注册模型制品（Register Model Artifact） | 成功完成 注册模型制品（Register Model Artifact）。 | 模型工件必须可拉取且可验证（Model Artifact Must Be Pullable And Verifiable） / 注册可拉取模型工件（Register Pullable Model Artifact） - 当 注册模型制品（Register Model Artifact）, 则 模型工件已注册（Model Artifact Registered）. | 成功时产生: 模型工件已注册（Model Artifact Registered）; 状态: 已注册（Registered） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 模型制品目录（Model Artifact Catalog） | 成功完成 模型制品目录（Model Artifact Catalog）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 模型制品目录（Model Artifact Catalog） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 定义训练运行配置（Define Training Run Configuration） | 成功完成 定义训练运行配置（Define Training Run Configuration）。 | 训练配置必须可运行（Training Configuration Must Be Runnable） / 定义可运行配置（Define Runnable Configuration） - 当 定义训练运行配置（Define Training Run Configuration）, 则 训练运行配置已定义（Training Run Configuration Defined）.; 训练配置必须可运行（Training Configuration Must Be Runnable） / 拒绝空轮次预算（Reject Empty Round Budget） - 当 定义训练运行配置（Define Training Run Configuration）, 则 拒绝: 轮次预算必须为正数.; 训练配置必须可运行（Training Configuration Must Be Runnable） / 拒绝无参数的差分隐私（Reject Differential Privacy Without Parameters） - 当 定义训练运行配置（Define Training Run Configuration）, 则 拒绝: 差分隐私参数为必填项. | 成功时产生: 训练运行配置已定义（Training Run Configuration Defined）; 状态: 草稿（Draft） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 更新训练运行配置（Update Training Run Configuration） | 成功完成 更新训练运行配置（Update Training Run Configuration）。 | 草稿训练配置可以更新（Draft Training Configuration Can Be Updated） / 更新草稿可运行配置（Update Draft Runnable Configuration） - 当 更新训练运行配置（Update Training Run Configuration）, 则 训练运行配置已更新（Training Run Configuration Updated）.; 草稿训练配置可以更新（Draft Training Configuration Can Be Updated） / 配置锁定后拒绝更新（Reject Update After Configuration Locked） - 当 更新训练运行配置（Update Training Run Configuration）, 则 拒绝: 训练运行配置已锁定. | 成功时产生: 训练运行配置已更新（Training Run Configuration Updated）; 状态: 草稿（Draft） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 锁定训练运行配置（Lock Training Run Configuration） | 成功完成 锁定训练运行配置（Lock Training Run Configuration）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 训练运行配置已锁定（Training Run Configuration Locked）; 状态: 已锁定（Locked） | 训练提交时锁定配置（Lock Configuration When Training Submitted） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 训练运行配置目录（Training Run Configuration Catalog） | 成功完成 训练运行配置目录（Training Run Configuration Catalog）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 训练运行配置目录（Training Run Configuration Catalog） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 创建训练作业（Create Training Job） | 成功完成 创建训练作业（Create Training Job）。 | 训练作业需要可运行配置和运行时访问（Training Job Requires Runnable Configuration And Runtime Access） / 使用可运行配置创建训练作业（Create Training Job With Runnable Configuration） - 当 创建训练作业（Create Training Job）, 则 训练作业已创建（Training Job Created）.; 训练作业需要可运行配置和运行时访问（Training Job Requires Runnable Configuration And Runtime Access） / 拒绝无可运行配置的训练作业（Reject Training Job Without Runnable Configuration） - 当 创建训练作业（Create Training Job）, 则 拒绝: 需要可运行训练配置. | 成功时产生: 训练作业已创建（Training Job Created）; 状态: 草稿（Draft） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 提交训练作业（Submit Training Job） | 成功完成 提交训练作业（Submit Training Job）。 | 只有已创建的训练作业才能提交（Only Created Training Jobs Can Be Submitted） / 提交已创建的训练作业（Submit Created Training Job） - 当 提交训练作业（Submit Training Job）, 则 训练作业已提交（Training Job Submitted）.; 只有已创建的训练作业才能提交（Only Created Training Jobs Can Be Submitted） / 拒绝未知训练作业（Reject Unknown Training Job） - 当 提交训练作业（Submit Training Job）, 则 拒绝: 训练作业为必填项. | 成功时产生: 训练作业已提交（Training Job Submitted）; 状态: 已提交（Submitted） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 训练参与者资格（Training Participant Eligibility） | 成功完成 训练参与者资格（Training Participant Eligibility）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 训练参与者资格（Training Participant Eligibility） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 暂停训练作业（Pause Training Job） | 成功完成 暂停训练作业（Pause Training Job）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 训练作业已暂停（Training Job Paused）; 状态: 已暂停（Paused） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 恢复训练作业（Resume Training Job） | 成功完成 恢复训练作业（Resume Training Job）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 训练作业已恢复（Training Job Resumed）; 状态: 运行中（Running） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 取消训练作业（Cancel Training Job） | 成功完成 取消训练作业（Cancel Training Job）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 训练作业已取消（Training Job Canceled）; 状态: 已取消（Canceled） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 安排下一训练轮次（Schedule Next Training Round） | 成功完成 安排下一训练轮次（Schedule Next Training Round）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 当训练应继续时选择下一轮参与者（Select Next Round Participants When Training Should Continue） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 完成训练作业（Complete Training Job） | 成功完成 完成训练作业（Complete Training Job）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 训练作业已完成（Training Job Completed）; 状态: 已完成（Completed） | 轮次预算耗尽时完成作业（Complete Job When Round Budget Exhausted）, 达到全局指标时完成作业（Complete Job When Global Metric Reached） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 训练作业仪表板（Training Job Dashboard） | 成功完成 训练作业仪表板（Training Job Dashboard）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 训练作业仪表板（Training Job Dashboard） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 选择训练轮次参与者（Select Training Round Participants） | 成功完成 选择训练轮次参与者（Select Training Round Participants）。 | 训练轮次参与者按轮快照（Training Round Participants Are Snapshotted Per Round） / 运行时池达到法定数量时选择参与者（Select Participants When Runtime Pool Reaches Quorum） - 当 选择训练轮次参与者（Select Training Round Participants）, 则 训练轮次参与者已选择（Training Round Participants Selected）.; 训练轮次参与者按轮快照（Training Round Participants Are Snapshotted Per Round） / 拒绝低于法定数量的参与者快照（Reject Participant Snapshot Below Quorum） - 当 选择训练轮次参与者（Select Training Round Participants）, 则 训练轮次参与者选择失败（Training Round Participant Selection Failed）. | 成功时产生: 训练轮次参与者已选择（Training Round Participants Selected）, 训练轮次参与者选择失败（Training Round Participant Selection Failed）; 状态: 参与者已选择（Participants Selected） | 训练提交时选择初始轮次参与者（Select Initial Round Participants When Training Submitted） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 重试训练轮次参与者选择（Retry Training Round Participant Selection） | 成功完成 重试训练轮次参与者选择（Retry Training Round Participant Selection）。 | 在就绪状态变化后重试参与者选择（Retry Participant Selection After Readiness Changes） / 重试参与者选择（Retry Participant Selection） - 当 重试训练轮次参与者选择（Retry Training Round Participant Selection）, 则 训练轮次参与者选择重试已请求（Training Round Participant Selection Retry Requested）. | 成功时产生: 训练轮次参与者选择重试已请求（Training Round Participant Selection Retry Requested） | 请求重试时选择训练轮次参与者（Select Training Round Participants When Retry Requested） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 开始训练轮次（Start Training Round） | 成功完成 开始训练轮次（Start Training Round）。 | 训练轮次需要已选择的运行时法定人数（Training Round Requires Selected Runtime Quorum） / 使用已选择的运行时法定人数启动轮次（Start Round With Selected Runtime Quorum） - 当 开始训练轮次（Start Training Round）, 则 训练轮次已启动（Training Round Started）.; 训练轮次需要已选择的运行时法定人数（Training Round Requires Selected Runtime Quorum） / 记录低于法定人数的失败轮次启动（Record Failed Round Start Below Quorum） - 当 开始训练轮次（Start Training Round）, 则 训练轮次启动失败（Training Round Start Failed）. | 成功时产生: 训练轮次已启动（Training Round Started）, 训练轮次启动失败（Training Round Start Failed）; 状态: 运行中（Running） | 参与者已选择时启动轮次（Start Round When Participants Selected） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 生成参与者执行计划（Generate Participant Execution Plan） | 成功完成 生成参与者执行计划（Generate Participant Execution Plan）。 | 参与者执行计划携带基础模型工件快照（Participant Execution Plan Carries Base Model Artifact Snapshot） / 使用初始模型快照生成初始轮次计划（Generate Initial Round Plan With Initial Model Snapshot） - 当 生成参与者执行计划（Generate Participant Execution Plan）, 则 参与者执行计划已生成（Participant Execution Plan Generated）.; 参与者执行计划携带基础模型工件快照（Participant Execution Plan Carries Base Model Artifact Snapshot） / 使用聚合模型快照生成后续轮次计划（Generate Later Round Plan With Aggregated Model Snapshot） - 当 生成参与者执行计划（Generate Participant Execution Plan）, 则 参与者执行计划已生成（Participant Execution Plan Generated）. | 成功时产生: 参与者执行计划已生成（Participant Execution Plan Generated）; 状态: 计划已生成（Plan Generated） | 为已选择的运行时生成计划（Generate Plan For Selected Runtime） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 分发参与者执行计划（Dispatch Participant Execution Plan） | 成功完成 分发参与者执行计划（Dispatch Participant Execution Plan）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 参与者执行计划已分发（Participant Execution Plan Dispatched）; 状态: 计划已分发（Plan Dispatched） | 生成时分发计划（Dispatch Plan When Generated） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 提交模型更新提交（Submit Model Update Submission） | 成功完成 提交模型更新提交（Submit Model Update Submission）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 模型更新提交已接收（Model Update Submission Received）; 状态: 已接收（Received） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 评估模型更新提交（Evaluate Model Update Submission） | 成功完成 评估模型更新提交（Evaluate Model Update Submission）。 | 已排除受污染的本地更新（Poisoned Local Updates Are Excluded） / 拒绝异常更新（Reject Anomalous Update） - 当 评估模型更新提交（Evaluate Model Update Submission）, 则 模型更新提交已拒绝（Model Update Submission Rejected）. | 成功时产生: 模型更新提交已接受（Model Update Submission Accepted）, 模型更新提交已拒绝（Model Update Submission Rejected）; 状态: 已评估（Evaluated） | 收到时评估模型更新（Evaluate Model Update When Received） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 请求安全聚合（Request Secure Aggregation） | 成功完成 请求安全聚合（Request Secure Aggregation）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 已请求安全聚合（Secure Aggregation Requested）; 状态: 聚合中（Aggregating） | 在已评估更新完成时请求聚合（Request Aggregation When Evaluated Updates Complete） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 完成安全聚合（Complete Secure Aggregation） | 成功完成 完成安全聚合（Complete Secure Aggregation）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 全局模型已更新（Global Model Updated）; 状态: 评估全局模型中（Evaluating Global Model） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 提交全局模型评估（Submit Global Model Evaluation） | 成功完成 提交全局模型评估（Submit Global Model Evaluation）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 全局模型评估已提交（Global Model Evaluation Submitted） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 完成训练轮次（Complete Training Round） | 成功完成 完成训练轮次（Complete Training Round）。 | 训练轮次完成需要全局评估（Training Round Completion Requires Global Evaluation） / 全局评估后完成轮次（Complete Round After Global Evaluation） - 当 完成训练轮次（Complete Training Round）, 则 训练轮次已完成（Training Round Completed）.; 训练轮次完成需要全局评估（Training Round Completion Requires Global Evaluation） / 无全局评估则拒绝完成轮次（Reject Round Completion Without Global Evaluation） - 当 完成训练轮次（Complete Training Round）, 则 拒绝: 需要全局模型评估. | 成功时产生: 训练轮次已完成（Training Round Completed）; 状态: 已完成（Completed） | 全局模型评估后结束轮次（Finish Round After Global Model Evaluated） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 失败训练轮次（Fail Training Round） | 成功完成 失败训练轮次（Fail Training Round）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 训练轮次失败（Training Round Failed）; 状态: 已失败（Failed） | 因被拒绝的更新破坏法定人数而失败的轮次（Fail Round When Rejected Updates Break Quorum）, 因运行时执行失败破坏法定人数而失败的轮次（Fail Round When Runtime Execution Failure Breaks Quorum）, 因运行时执行启动失败破坏法定人数而失败的轮次（Fail Round When Runtime Execution Start Failure Breaks Quorum）, 因运行时执行启动重试失败破坏法定人数而失败的轮次（Fail Round When Runtime Execution Start Retry Failure Breaks Quorum）, 因运行时执行重试失败破坏法定人数而失败的轮次（Fail Round When Runtime Execution Retry Failure Breaks Quorum）, 因错过贡献截止时间而失败的轮次（Fail Round When Contribution Deadline Missed） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 训练轮次进度（Training Round Progress） | 成功完成 训练轮次进度（Training Round Progress）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 训练轮次进度（Training Round Progress） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 注册候选模型（Register Candidate Model） | 成功完成 注册候选模型（Register Candidate Model）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 模型候选已注册（Model Candidate Registered）; 状态: 候选（Candidate） | 训练作业完成时注册最终模型（Register Final Model When Training Job Completed） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 记录模型评估包（Record Model Evaluation Package） | 成功完成 记录模型评估包（Record Model Evaluation Package）。 | 模型审批需要评估包（Model Approval Requires Evaluation Package） / 记录完整评估包（Record Complete Evaluation Package） - 当 记录模型评估包（Record Model Evaluation Package）, 则 模型评估包已记录（Model Evaluation Package Recorded）. | 成功时产生: 模型评估包已记录（Model Evaluation Package Recorded）; 状态: 评估已打包（Evaluation Packaged） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 批准模型（Approve Model） | 成功完成 批准模型（Approve Model）。 | 只有评估打包模型才能被批准（Only Evaluation Packaged Models Can Be Approved） / 批准评估打包模型（Approve Evaluation Packaged Model） - 当 批准模型（Approve Model）, 则 模型已批准（Model Approved）.; 只有评估打包模型才能被批准（Only Evaluation Packaged Models Can Be Approved） / 拒绝没有评估包的候选模型（Reject Candidate Without Evaluation Package） - 当 批准模型（Approve Model）, 则 拒绝: 需要模型评估包. | 成功时产生: 模型已批准（Model Approved）; 状态: 已批准（Approved） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 提升模型至生产（Promote Model To Production） | 成功完成 提升模型至生产（Promote Model To Production）。 | 仅已批准的模型可提升到生产环境（Only Approved Models Can Be Promoted To Production） / 提升已批准的模型（Promote Approved Model） - 当 提升模型至生产（Promote Model To Production）, 则 模型已提升到生产环境（Model Promoted To Production）.; 仅已批准的模型可提升到生产环境（Only Approved Models Can Be Promoted To Production） / 拒绝未批准模型的提升（Reject Unapproved Model Promotion） - 当 提升模型至生产（Promote Model To Production）, 则 拒绝: 需要模型批准. | 成功时产生: 模型已提升到生产环境（Model Promoted To Production）; 状态: 生产（Production） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 回滚模型（Rollback Model） | 成功完成 回滚模型（Rollback Model）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 模型已回滚（Model Rolled Back）; 状态: 已回滚（Rolled Back） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 停用模型（Retire Model） | 成功完成 停用模型（Retire Model）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 模型已退役（Model Retired）; 状态: 已停用（Retired） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 模型目录（Model Catalog） | 成功完成 模型目录（Model Catalog）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 模型目录（Model Catalog） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 运行时最新遥测（Runtime Telemetry Latest） | 成功完成 运行时最新遥测（Runtime Telemetry Latest）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 运行时最新遥测（Runtime Telemetry Latest） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 检测运行时代理离线（Detect Runtime Agent Offline） | 成功完成 检测运行时代理离线（Detect Runtime Agent Offline）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 已检测到运行时代理离线（Runtime Agent Offline Detected）; 状态: 离线（Offline） | 从遥测检测离线运行时代理（Detect Offline Runtime Agent From Telemetry） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 标记运行时代理已恢复（Mark Runtime Agent Recovered） | 成功完成 标记运行时代理已恢复（Mark Runtime Agent Recovered）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 运行时代理已恢复（Runtime Agent Recovered）; 状态: 健康（Healthy） | 从遥测标记运行时代理已恢复（Mark Recovered Runtime Agent From Telemetry） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 检测运行时节点资源压力（Detect Runtime Node Resource Pressure） | 成功完成 检测运行时节点资源压力（Detect Runtime Node Resource Pressure）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 已检测到运行时节点资源压力（Runtime Node Resource Pressure Detected）; 状态: 已检测到压力（Pressure Detected） | 从遥测检测资源压力（Detect Resource Pressure From Telemetry） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 记录运行时节点清单（Record Runtime Node Inventory） | 成功完成 记录运行时节点清单（Record Runtime Node Inventory）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 运行时节点清单已报告（Runtime Node Inventory Reported）; 状态: 已报告（Reported） | 代理报告时记录运行时节点清单（Record Runtime Node Inventory When Agent Reported） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 运行时节点清单视图（Runtime Node Inventory View） | 成功完成 运行时节点清单视图（Runtime Node Inventory View）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 运行时节点清单视图（Runtime Node Inventory View） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 运行时节点最新资源（Runtime Node Resource Latest） | 成功完成 运行时节点最新资源（Runtime Node Resource Latest）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 运行时节点最新资源（Runtime Node Resource Latest） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 检测运行时节点容量变更（Detect Runtime Node Capacity Change） | 成功完成 检测运行时节点容量变更（Detect Runtime Node Capacity Change）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 运行时节点容量已更改（Runtime Node Capacity Changed）; 状态: 容量已变更（Capacity Changed） | 从资源遥测检测容量变化（Detect Capacity Change From Resource Telemetry） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 运行时健康仪表板（Runtime Health Dashboard） | 成功完成 运行时健康仪表板（Runtime Health Dashboard）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 运行时健康仪表板（Runtime Health Dashboard） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 引发训练告警（Raise Training Alert） | 成功完成 引发训练告警（Raise Training Alert）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 训练警报已发出（Training Alert Raised）; 状态: 已引发（Raised） | 在节点资源压力时发出警报（Raise Alert On Node Resource Pressure） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 训练告警目录（Training Alert Catalog） | 成功完成 训练告警目录（Training Alert Catalog）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 训练告警目录（Training Alert Catalog） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 确认训练告警（Acknowledge Training Alert） | 成功完成 确认训练告警（Acknowledge Training Alert）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 训练警报已确认（Training Alert Acknowledged）; 状态: 已确认（Acknowledged） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 解决训练告警（Resolve Training Alert） | 成功完成 解决训练告警（Resolve Training Alert）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 训练警报已解决（Training Alert Resolved）; 状态: 已解决（Resolved） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 追加审计跟踪（Append Audit Trail） | 成功完成 追加审计跟踪（Append Audit Trail）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 审计跟踪已追加（Audit Trail Appended）; 状态: 已追加（Appended） | 审计关键训练事件（Audit Critical Training Events）, 审计联邦已激活（Audit Federation Activated）, 审计联邦已暂停（Audit Federation Suspended）, 审计联邦已重新激活（Audit Federation Reactivated）, 审计参与者已加入（Audit Participant Joined）, 审计参与者已暂停（Audit Participant Suspended）, 审计数据集批准（Audit Dataset Approval）, 审计数据集批准已撤销（Audit Dataset Approval Revoked）, 审计训练作业已提交（Audit Training Job Submitted）, 审计模型已提升至生产（Audit Model Promoted To Production） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 审计记录日志（Audit Record Log） | 成功完成 审计记录日志（Audit Record Log）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 审计记录日志（Audit Record Log） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 激活运行时标识（Activate Runtime Identity） | 成功完成 激活运行时标识（Activate Runtime Identity）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 运行时身份已激活（Runtime Identity Activated）; 状态: 已激活（Active） | 在代理已安装时激活运行时身份（Activate Runtime Identity When Agent Installed）, 在手动运行时已连接时激活运行时身份（Activate Runtime Identity When Manual Runtime Connected） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 撤销运行时标识（Revoke Runtime Identity） | 成功完成 撤销运行时标识（Revoke Runtime Identity）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 运行时身份已撤销（Runtime Identity Revoked）; 状态: 已撤销（Revoked） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 检测运行时能力（Detect Runtime Capabilities） | 成功完成 检测运行时能力（Detect Runtime Capabilities）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 运行时能力已检测（Runtime Capabilities Detected）; 状态: 已检测到（Detected） | 运行时激活时检测运行时能力（Detect Runtime Capabilities When Runtime Activated） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 运行时标识目录（Runtime Identity Catalog） | 成功完成 运行时标识目录（Runtime Identity Catalog）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 运行时标识目录（Runtime Identity Catalog） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 运行时能力目录（Runtime Capability Catalog） | 成功完成 运行时能力目录（Runtime Capability Catalog）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 运行时能力目录（Runtime Capability Catalog） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 创建安全聚合会话（Create Secure Aggregation Session） | 成功完成 创建安全聚合会话（Create Secure Aggregation Session）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 安全聚合会话已创建（Secure Aggregation Session Created）; 状态: 已规划（Planned） | 在请求聚合时创建会话（Create Session When Aggregation Requested） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 选择安全聚合参与者（Select Secure Aggregation Participants） | 成功完成 选择安全聚合参与者（Select Secure Aggregation Participants）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 安全聚合参与者已选择（Secure Aggregation Participants Selected）; 状态: 参与者已选择（Participants Selected） | 会话创建时选择参与者（Select Participants When Session Created） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 准备同态加密上下文（Prepare Homomorphic Encryption Context） | 成功完成 准备同态加密上下文（Prepare Homomorphic Encryption Context）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 同态加密上下文已准备（Homomorphic Encryption Context Prepared）; 状态: 已准备加密上下文（Encryption Context Prepared） | 选择参与者时准备加密上下文（Prepare Encryption Context When Participants Selected） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 记录加密模型更新（Record Encrypted Model Update） | 成功完成 记录加密模型更新（Record Encrypted Model Update）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 已接收加密模型更新（Encrypted Model Update Received） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 完成同态聚合会话（Complete Homomorphic Aggregation Session） | 成功完成 完成同态聚合会话（Complete Homomorphic Aggregation Session）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 安全聚合已完成（Secure Aggregation Completed）; 状态: 已完成（Completed） | 加密更新就绪时完成聚合（Complete Aggregation When Encrypted Updates Ready） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 失败安全聚合会话（Fail Secure Aggregation Session） | 成功完成 失败安全聚合会话（Fail Secure Aggregation Session）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 安全聚合失败（Secure Aggregation Failed）; 状态: 已失败（Failed） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 安全聚合会话目录（Secure Aggregation Session Catalog） | 成功完成 安全聚合会话目录（Secure Aggregation Session Catalog）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 安全聚合会话目录（Secure Aggregation Session Catalog） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 声明数据集（Declare Dataset） | 成功完成 声明数据集（Declare Dataset）。 | 数据集声明需要特征架构快照（Feature Schema Snapshot Required For Dataset Declaration） / 使用特征架构快照声明数据集（Declare Dataset With Feature Schema Snapshot） - 当 声明数据集（Declare Dataset）, 则 数据集已声明（Dataset Declared）.; 数据集声明需要特征架构快照（Feature Schema Snapshot Required For Dataset Declaration） / 特征架构加载失败（Feature Schema Load Failed） - 当 声明数据集（Declare Dataset）, 则 错误: 特征架构加载失败. | 成功时产生: 数据集已声明（Dataset Declared）; 状态: 已注册（Registered） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 配置运行时数据集绑定（Configure Runtime Dataset Binding） | 成功完成 配置运行时数据集绑定（Configure Runtime Dataset Binding）。 | 每个运行时数据集的运行时数据集绑定唯一（Runtime Dataset Binding Unique Per Runtime Dataset） / 拒绝重复的运行时数据集绑定（Reject Duplicate Runtime Dataset Binding） - 当 配置运行时数据集绑定（Configure Runtime Dataset Binding）, 则 拒绝: 运行时数据集绑定已存在. | 成功时产生: 运行时数据集绑定已配置（Runtime Dataset Binding Configured）; 状态: 已配置（Configured） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 运行时数据集绑定目录（Runtime Dataset Binding Catalog） | 成功完成 运行时数据集绑定目录（Runtime Dataset Binding Catalog）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 运行时数据集绑定目录（Runtime Dataset Binding Catalog） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 验证代理数据集访问（Validate Agent Dataset Access） | 成功完成 验证代理数据集访问（Validate Agent Dataset Access）。 | 代理数据集访问验证结果（Agent Dataset Access Validation Outcome） / 代理数据集访问已验证（Agent Dataset Access Validated） - 当 验证代理数据集访问（Validate Agent Dataset Access）, 则 代理数据集访问已验证（Agent Dataset Access Validated）.; 代理数据集访问验证结果（Agent Dataset Access Validation Outcome） / 代理数据集访问验证失败（Agent Dataset Access Validation Failed） - 当 验证代理数据集访问（Validate Agent Dataset Access）, 则 代理数据集访问验证失败（Agent Dataset Access Validation Failed）. | 成功时产生: 代理数据集访问已验证（Agent Dataset Access Validated）, 代理数据集访问验证失败（Agent Dataset Access Validation Failed）; 状态: 已检查（Checked） | 绑定配置时验证代理数据集访问（Validate Agent Dataset Access When Binding Configured） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 重新验证代理数据集访问（Revalidate Agent Dataset Access） | 成功完成 重新验证代理数据集访问（Revalidate Agent Dataset Access）。 | 代理数据集访问重新验证结果（Agent Dataset Access Revalidation Outcome） / 代理数据集访问已重新验证（Agent Dataset Access Revalidated） - 当 重新验证代理数据集访问（Revalidate Agent Dataset Access）, 则 代理数据集访问已重新验证（Agent Dataset Access Revalidated）.; 代理数据集访问重新验证结果（Agent Dataset Access Revalidation Outcome） / 代理数据集访问重新验证失败（Agent Dataset Access Revalidation Failed） - 当 重新验证代理数据集访问（Revalidate Agent Dataset Access）, 则 代理数据集访问重新验证失败（Agent Dataset Access Revalidation Failed）. | 成功时产生: 代理数据集访问已重新验证（Agent Dataset Access Revalidated）, 代理数据集访问重新验证失败（Agent Dataset Access Revalidation Failed）; 状态: 已检查（Checked） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 代理数据集访问验证目录（Agent Dataset Access Validation Catalog） | 成功完成 代理数据集访问验证目录（Agent Dataset Access Validation Catalog）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 代理数据集访问验证目录（Agent Dataset Access Validation Catalog） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 分析代理数据集（Profile Agent Dataset） | 成功完成 分析代理数据集（Profile Agent Dataset）。 | 代理数据集分析结果（Agent Dataset Profiling Outcome） / 代理数据集元数据已报告（Agent Dataset Metadata Reported） - 当 分析代理数据集（Profile Agent Dataset）, 则 代理数据集元数据已报告（Agent Dataset Metadata Reported）.; 代理数据集分析结果（Agent Dataset Profiling Outcome） / 代理数据集分析失败（Agent Dataset Profiling Failed） - 当 分析代理数据集（Profile Agent Dataset）, 则 代理数据集分析失败（Agent Dataset Profiling Failed）. | 成功时产生: 代理数据集元数据已报告（Agent Dataset Metadata Reported）, 代理数据集分析失败（Agent Dataset Profiling Failed）; 状态: 已报告（Reported） | 访问验证通过时分析数据集（Profile Dataset When Access Validated）, 访问重新验证时分析数据集（Profile Dataset When Access Revalidated） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 重新分析代理数据集（Reprofile Agent Dataset） | 成功完成 重新分析代理数据集（Reprofile Agent Dataset）。 | 代理数据集重新剖析结果（Agent Dataset Reprofiling Outcome） / 代理数据集已重新分析（Agent Dataset Reprofiled） - 当 重新分析代理数据集（Reprofile Agent Dataset）, 则 代理数据集已重新分析（Agent Dataset Reprofiled）.; 代理数据集重新剖析结果（Agent Dataset Reprofiling Outcome） / 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed） - 当 重新分析代理数据集（Reprofile Agent Dataset）, 则 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed）. | 成功时产生: 代理数据集已重新分析（Agent Dataset Reprofiled）, 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed）; 状态: 已报告（Reported） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 验证数据集合约（Validate Dataset Contract） | 成功完成 验证数据集合约（Validate Dataset Contract）。 | 数据集合约验证结果（Dataset Contract Validation Outcome） / 数据集合约验证成功（Dataset Contract Validation Succeeded） - 当 验证数据集合约（Validate Dataset Contract）, 则 数据集合约已验证（Dataset Contract Validated）.; 数据集合约验证结果（Dataset Contract Validation Outcome） / 数据集合约验证失败（Dataset Contract Validation Failed） - 当 验证数据集合约（Validate Dataset Contract）, 则 数据集合约验证失败（Dataset Contract Validation Failed）. | 成功时产生: 数据集合约已验证（Dataset Contract Validated）, 数据集合约验证失败（Dataset Contract Validation Failed）; 状态: 合约验证已完成（Contract Validation Completed） | 自动验证数据集合约（Validate Dataset Contract Automatically） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 重试数据集合约验证（Retry Dataset Contract Validation） | 成功完成 重试数据集合约验证（Retry Dataset Contract Validation）。 | 数据集合约验证重试结果（Dataset Contract Validation Retry Outcome） / 数据集合约已重新验证（Dataset Contract Revalidated） - 当 重试数据集合约验证（Retry Dataset Contract Validation）, 则 数据集合约已重新验证（Dataset Contract Revalidated）.; 数据集合约验证重试结果（Dataset Contract Validation Retry Outcome） / 数据集合约重新验证失败（Dataset Contract Revalidation Failed） - 当 重试数据集合约验证（Retry Dataset Contract Validation）, 则 数据集合约重新验证失败（Dataset Contract Revalidation Failed）. | 成功时产生: 数据集合约已重新验证（Dataset Contract Revalidated）, 数据集合约重新验证失败（Dataset Contract Revalidation Failed）; 状态: 合约验证已完成（Contract Validation Completed） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 拒绝数据集用于训练（Reject Dataset For Training） | 成功完成 拒绝数据集用于训练（Reject Dataset For Training）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 数据集已拒绝用于训练（Dataset Rejected For Training）; 状态: 已拒绝（Rejected） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 批准数据集用于训练（Approve Dataset For Training） | 成功完成 批准数据集用于训练（Approve Dataset For Training）。 | 仅批准已验证的高质量数据集（Only Validated Quality Datasets Are Approved） / 批准已验证的数据集（Approve Validated Dataset） - 当 批准数据集用于训练（Approve Dataset For Training）, 则 数据集已批准用于训练（Dataset Approved For Training）.; 仅批准已验证的高质量数据集（Only Validated Quality Datasets Are Approved） / 在数据集合约验证前拒绝批准（Reject Approval Before Contract Validation） - 当 批准数据集用于训练（Approve Dataset For Training）, 则 拒绝: 需要数据集合约验证. | 成功时产生: 数据集已批准用于训练（Dataset Approved For Training）; 状态: 已批准（Approved） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 撤销数据集训练审批（Revoke Dataset Training Approval） | 成功完成 撤销数据集训练审批（Revoke Dataset Training Approval）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 数据集训练批准已撤销（Dataset Training Approval Revoked）; 状态: 审批已撤销（Approval Revoked） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 数据集能力（Dataset Capability） | 成功完成 数据集能力（Dataset Capability）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 数据集能力（Dataset Capability） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 数据集就绪状态（Dataset Readiness） | 成功完成 数据集就绪状态（Dataset Readiness）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 数据集就绪状态（Dataset Readiness） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 接收参与者执行计划（Receive Participant Execution Plan） | 成功完成 接收参与者执行计划（Receive Participant Execution Plan）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 执行计划已接收（Execution Plan Received）; 状态: 计划已接收（Plan Received） | 分发时接收执行计划（Receive Execution Plan When Dispatched） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 接受执行计划（Accept Execution Plan） | 成功完成 接受执行计划（Accept Execution Plan）。 | 执行计划本地接受结果（Execution Plan Local Acceptance Outcome） / 执行计划已接受（Execution Plan Accepted） - 当 接受执行计划（Accept Execution Plan）, 则 执行计划已接受（Execution Plan Accepted）.; 执行计划本地接受结果（Execution Plan Local Acceptance Outcome） / 执行计划已拒绝（Execution Plan Rejected） - 当 接受执行计划（Accept Execution Plan）, 则 执行计划已拒绝（Execution Plan Rejected）. | 成功时产生: 执行计划已接受（Execution Plan Accepted）, 执行计划已拒绝（Execution Plan Rejected）; 状态: 计划已接受（Plan Accepted） | 收到执行计划时接受执行计划（Accept Execution Plan When Execution Plan Received） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 开始轮次执行（Start Round Execution） | 成功完成 开始轮次执行（Start Round Execution）。 | 轮次执行运行时引擎启动结果（Round Execution Runtime Engine Start Outcome） / 轮次执行已启动（Round Execution Started） - 当 开始轮次执行（Start Round Execution）, 则 轮次执行已启动（Round Execution Started）.; 轮次执行运行时引擎启动结果（Round Execution Runtime Engine Start Outcome） / 轮次执行启动失败（Round Execution Start Failed） - 当 开始轮次执行（Start Round Execution）, 则 轮次执行启动失败（Round Execution Start Failed）. | 成功时产生: 轮次执行已启动（Round Execution Started）, 轮次执行启动失败（Round Execution Start Failed）; 状态: 运行中（Running） | 计划接受时启动轮次执行（Start Round Execution When Plan Accepted） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 观察运行时引擎作业（Observe Runtime Engine Job） | 成功完成 观察运行时引擎作业（Observe Runtime Engine Job）。 | 运行时引擎作业观察结果（Runtime Engine Job Observation Outcome） / 运行时引擎作业已观察运行中（Runtime Engine Job Observed Running） - 当 观察运行时引擎作业（Observe Runtime Engine Job）, 则 运行时引擎作业已观察（Runtime Engine Job Observed）.; 运行时引擎作业观察结果（Runtime Engine Job Observation Outcome） / 运行时引擎作业已观察完成（Runtime Engine Job Observed Completed） - 当 观察运行时引擎作业（Observe Runtime Engine Job）, 则 运行时引擎作业已观察（Runtime Engine Job Observed）.; 运行时引擎作业观察结果（Runtime Engine Job Observation Outcome） / 运行时引擎作业已观察失败（Runtime Engine Job Observed Failed） - 当 观察运行时引擎作业（Observe Runtime Engine Job）, 则 运行时引擎作业已观察（Runtime Engine Job Observed）. | 成功时产生: 运行时引擎作业已观察（Runtime Engine Job Observed）; 状态: 运行中（Running） | 轮次执行启动时观察运行时引擎作业（Observe Runtime Engine Job When Round Execution Started） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 完成轮次执行（Complete Round Execution） | 成功完成 完成轮次执行（Complete Round Execution）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 轮次执行已完成（Round Execution Completed）; 状态: 已完成（Completed） | 运行时引擎作业完成时完成轮次执行（Complete Round Execution When Runtime Engine Job Completed） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 失败轮次执行（Fail Round Execution） | 成功完成 失败轮次执行（Fail Round Execution）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 轮次执行失败（Round Execution Failed）; 状态: 已失败（Failed） | Fail Round Execution When Runtime Engine Job Failed | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 启动失败后重试轮次执行（Retry Round Execution After Start Failure） | 成功完成 启动失败后重试轮次执行（Retry Round Execution After Start Failure）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: Round Execution Start Retry Started, 轮次执行启动重试失败（Round Execution Start Retry Failed）; 状态: 已重试（Retried） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure） | 成功完成 运行时失败后重试轮次执行（Retry Round Execution After Runtime Failure）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: Round Execution Runtime Retry Started, 轮次执行运行时重试失败（Round Execution Runtime Retry Failed）; 状态: 已重试（Retried） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 提交代理本地模型更新（Submit Agent Local Model Update） | 成功完成 提交代理本地模型更新（Submit Agent Local Model Update）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: Agent Local Model Update Submitted; 状态: 更新已提交（Update Submitted） | Submit Local Model Update When Round Execution Completed | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 完成后释放运行时引擎作业（Release Runtime Engine Job After Completion） | 成功完成 完成后释放运行时引擎作业（Release Runtime Engine Job After Completion）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: Runtime Engine Job Released; 状态: 运行时引擎已释放（Runtime Engine Released） | Release Runtime Engine Job When Round Execution Completed | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 失败后释放运行时引擎作业（Release Runtime Engine Job After Failure） | 成功完成 失败后释放运行时引擎作业（Release Runtime Engine Job After Failure）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: Runtime Engine Job Release Failed Or Skipped; 状态: 运行时引擎释放已处理（Runtime Engine Release Handled） | Release Runtime Engine Job When Round Execution Failed | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 启动失败后释放运行时引擎作业（Release Runtime Engine Job After Start Failure） | 成功完成 启动失败后释放运行时引擎作业（Release Runtime Engine Job After Start Failure）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: Runtime Engine Job Release Failed Or Skipped After Start Failure; 状态: 运行时引擎释放已处理（Runtime Engine Release Handled） | Release Runtime Engine Job When Start Failed | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 重试失败后释放运行时引擎作业（Release Runtime Engine Job After Retry Failure） | 成功完成 重试失败后释放运行时引擎作业（Release Runtime Engine Job After Retry Failure）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: Runtime Engine Job Release Failed Or Skipped After Retry; 状态: 运行时引擎释放已处理（Runtime Engine Release Handled） | Release Runtime Engine Job When Start Retry Failed | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 运行时重试失败后释放运行时引擎作业（Release Runtime Engine Job After Runtime Retry Failure） | 成功完成 运行时重试失败后释放运行时引擎作业（Release Runtime Engine Job After Runtime Retry Failure）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: Runtime Engine Job Release Failed Or Skipped After Runtime Retry; 状态: 运行时引擎释放已处理（Runtime Engine Release Handled） | Release Runtime Engine Job When Runtime Retry Failed | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 轮次执行目录（Round Execution Catalog） | 成功完成 轮次执行目录（Round Execution Catalog）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 轮次执行目录（Round Execution Catalog） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration） | 成功完成 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration）。 | Runtime Agent Startup Bootstrap Configuration / 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration） - 当 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration）, 则 Runtime Agent Bootstrap Configuration Loaded.; Runtime Agent Startup Bootstrap Configuration / Reject Missing Runtime Agent Bootstrap Configuration - 当 加载运行时代理引导配置（Load Runtime Agent Bootstrap Configuration）, 则 Runtime Agent Bootstrap Configuration Load Failed. | 成功时产生: Runtime Agent Bootstrap Configuration Loaded, Runtime Agent Bootstrap Configuration Load Failed; 状态: 引导程序已加载（Bootstrap Loaded） | 读侧或自动化结果需确认 | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 报告运行时代理已启动（Report Runtime Agent Started） | 成功完成 报告运行时代理已启动（Report Runtime Agent Started）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: Runtime Agent Started; 状态: 已启动（Started） | Report Runtime Agent Started When Bootstrap Configuration Loaded | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 报告运行时实例自检已通过（Report Runtime Instance Self Check Passed） | 成功完成 报告运行时实例自检已通过（Report Runtime Instance Self Check Passed）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: Runtime Instance Self Check Passed; 状态: 就绪（Ready） | Report Ready After Configuration Fetch And Self Check | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 运行时代理生命周期目录（Runtime Agent Lifecycle Catalog） | 成功完成 运行时代理生命周期目录（Runtime Agent Lifecycle Catalog）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 运行时代理生命周期目录（Runtime Agent Lifecycle Catalog） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 报告运行时实例已连接（Report Runtime Instance Connected） | 成功完成 报告运行时实例已连接（Report Runtime Instance Connected）。 | Runtime Agent Platform Connection Reporting Outcome / 报告运行时实例已连接（Report Runtime Instance Connected） - 当 报告运行时实例已连接（Report Runtime Instance Connected）, 则 Agent Runtime Connection Established.; Runtime Agent Platform Connection Reporting Outcome / Report Runtime Instance Connection Failed - 当 报告运行时实例已连接（Report Runtime Instance Connected）, 则 Agent Runtime Connection Report Failed. | 成功时产生: Agent Runtime Connection Report Failed, Agent Runtime Connection Established; 状态: 已连接（Connected） | Report Runtime Instance Connected After Self Check | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 代理运行时基础设施连接目录（Agent Runtime Infrastructure Connection Catalog） | 成功完成 代理运行时基础设施连接目录（Agent Runtime Infrastructure Connection Catalog）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 代理运行时基础设施连接目录（Agent Runtime Infrastructure Connection Catalog） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 代理运行时遥测最新（Agent Runtime Telemetry Latest） | 成功完成 代理运行时遥测最新（Agent Runtime Telemetry Latest）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 代理运行时遥测最新（Agent Runtime Telemetry Latest） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 报告代理运行时节点清单（Report Agent Runtime Node Inventory） | 成功完成 报告代理运行时节点清单（Report Agent Runtime Node Inventory）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 成功时产生: 代理运行时节点清单已报告（Agent Runtime Node Inventory Reported）; 状态: 已报告（Reported） | Report Node Inventory When Inventory Changed | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 代理运行时节点清单目录（Agent Runtime Node Inventory Catalog） | 成功完成 代理运行时节点清单目录（Agent Runtime Node Inventory Catalog）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 代理运行时节点清单目录（Agent Runtime Node Inventory Catalog） | 正常路径；拒绝路径；幂等；权限；边界；并发 |
| 代理运行时节点资源最新（Agent Runtime Node Resource Latest） | 成功完成 代理运行时节点资源最新（Agent Runtime Node Resource Latest）。 | 从校验、状态、权限、边界、重复提交和缺失字段推导反向场景。 | 事件结果需确认 | 代理运行时节点资源最新（Agent Runtime Node Resource Latest） | 正常路径；拒绝路径；幂等；权限；边界；并发 |

## 九、业务规则与异常测试/FLP-TO-BRAET

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

## 十、接口与集成测试/FLP-TO-IIT

- 训练编排（Training Orchestration） / 安全聚合提供程序（Secure Aggregation Provider）: Training Round -> Secure Aggregation Service.
- 训练编排（Training Orchestration） / 参与者轮次运行时（Participant Round Runtime）: Training Round -> Runtime Agent.
- 训练编排（Training Orchestration） / 聚合节点运行时（Aggregation Node Runtime）: Training Round -> Aggregation Runtime.
- 运行时代理操作（Runtime Agent Operations） / 轮次运行时引擎（Round Runtime Engine）: Round Execution -> Runtime Engine.
- 运行时代理操作（Runtime Agent Operations） / 轮次运行时引擎观察（Round Runtime Engine Observation）: Round Execution -> Runtime Engine.
- 运行时代理操作（Runtime Agent Operations） / 已重试轮次运行时引擎（Retried Round Runtime Engine）: Round Execution -> Runtime Engine.
- 运行时代理操作（Runtime Agent Operations） / 已重试轮次运行时引擎观察（Retried Round Runtime Engine Observation）: Round Execution -> Runtime Engine.
- 运行时代理操作（Runtime Agent Operations） / 运行时失败已重试轮次运行时引擎（Runtime Failure Retried Round Runtime Engine）: Round Execution -> Runtime Engine.
- 运行时代理操作（Runtime Agent Operations） / 运行时失败已重试轮次运行时引擎观察（Runtime Failure Retried Round Runtime Engine Observation）: Round Execution -> Runtime Engine.
- 运行时代理操作（Runtime Agent Operations） / 平台数据集元数据报告（Platform Dataset Metadata Reporting）: Agent Dataset Profile -> Dataset Governance.
- 运行时代理操作（Runtime Agent Operations） / 平台数据集重新分析元数据报告（Platform Dataset Reprofiled Metadata Reporting）: Agent Dataset Profile -> Dataset Governance.
- 运行时代理操作（Runtime Agent Operations） / 平台轮次更新报告（Platform Round Update Reporting）: Round Execution -> Training Orchestration.
- 运行时代理操作（Runtime Agent Operations） / 平台运行时基础设施连接报告（Platform Runtime Infrastructure Connection Reporting）: Agent Runtime Infrastructure Connection -> Runtime Provisioning.
- 运行时代理操作（Runtime Agent Operations） / 平台运行时节点清单报告（Platform Runtime Node Inventory Reporting）: Agent Runtime Node Inventory -> Runtime Monitoring.
- 运行时预配（Runtime Provisioning） / Runtime Agent: command:RequestDatasetAccess, event:DatasetAccessValidated.
- 文件上传（File Upload） / 保留期结束后暂存文件过期（Expire Staged File When Retention Elapsed）: 验证触发条件、载荷、重试和失败处理： 使暂存文件过期（Expire Staged File）.
- 运行时预配（Runtime Provisioning） / 安装计划创建时规划运行时基础设施（Plan Runtime Infrastructure When Installation Plan Created）: 验证触发条件、载荷、重试和失败处理： 规划运行时基础设施（Plan Runtime Infrastructure）.
- 运行时预配（Runtime Provisioning） / 注册时验证基础设施（Verify Infrastructure When Registered）: 验证触发条件、载荷、重试和失败处理： 验证运行时基础设施（Verify Runtime Infrastructure）.
- 运行时预配（Runtime Provisioning） / 在基础设施验证后部署运行时代理（Deploy Runtime Agent When Infrastructure Verified）: 验证触发条件、载荷、重试和失败处理： 部署运行时代理（Deploy Runtime Agent）.
- 数据集治理（Dataset Governance） / 在代理报告时记录数据集元数据（Record Dataset Metadata When Agent Reported）: 验证触发条件、载荷、重试和失败处理： 记录运行时数据集元数据（Record Runtime Dataset Metadata）.
- 数据集治理（Dataset Governance） / 代理重新分析时记录数据集元数据（Record Dataset Metadata When Agent Reprofiled）: 验证触发条件、载荷、重试和失败处理： 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata）.
- 训练编排（Training Orchestration） / 训练提交时锁定配置（Lock Configuration When Training Submitted）: 验证触发条件、载荷、重试和失败处理： 锁定训练运行配置（Lock Training Run Configuration）.
- 训练编排（Training Orchestration） / 当训练应继续时选择下一轮参与者（Select Next Round Participants When Training Should Continue）: 验证触发条件、载荷、重试和失败处理： 安排下一训练轮次（Schedule Next Training Round）.
- 训练编排（Training Orchestration） / 轮次预算耗尽时完成作业（Complete Job When Round Budget Exhausted）: 验证触发条件、载荷、重试和失败处理： 完成训练作业（Complete Training Job）.
- 训练编排（Training Orchestration） / 达到全局指标时完成作业（Complete Job When Global Metric Reached）: 验证触发条件、载荷、重试和失败处理： 完成训练作业（Complete Training Job）.
- 训练编排（Training Orchestration） / 训练提交时选择初始轮次参与者（Select Initial Round Participants When Training Submitted）: 验证触发条件、载荷、重试和失败处理： 选择训练轮次参与者（Select Training Round Participants）.
- 训练编排（Training Orchestration） / 请求重试时选择训练轮次参与者（Select Training Round Participants When Retry Requested）: 验证触发条件、载荷、重试和失败处理： 重试训练轮次参与者选择（Retry Training Round Participant Selection）.
- 训练编排（Training Orchestration） / 参与者已选择时启动轮次（Start Round When Participants Selected）: 验证触发条件、载荷、重试和失败处理： 开始训练轮次（Start Training Round）.
- 训练编排（Training Orchestration） / 为已选择的运行时生成计划（Generate Plan For Selected Runtime）: 验证触发条件、载荷、重试和失败处理： 生成参与者执行计划（Generate Participant Execution Plan）.
- 训练编排（Training Orchestration） / 生成时分发计划（Dispatch Plan When Generated）: 验证触发条件、载荷、重试和失败处理： 分发参与者执行计划（Dispatch Participant Execution Plan）.
- 训练编排（Training Orchestration） / 收到时评估模型更新（Evaluate Model Update When Received）: 验证触发条件、载荷、重试和失败处理： 评估模型更新提交（Evaluate Model Update Submission）.
- 训练编排（Training Orchestration） / 在已评估更新完成时请求聚合（Request Aggregation When Evaluated Updates Complete）: 验证触发条件、载荷、重试和失败处理： 请求安全聚合（Request Secure Aggregation）.
- 训练编排（Training Orchestration） / 全局模型评估后结束轮次（Finish Round After Global Model Evaluated）: 验证触发条件、载荷、重试和失败处理： 完成训练轮次（Complete Training Round）.
- 训练编排（Training Orchestration） / 因被拒绝的更新破坏法定人数而失败的轮次（Fail Round When Rejected Updates Break Quorum）: 验证触发条件、载荷、重试和失败处理： 失败训练轮次（Fail Training Round）.
- 训练编排（Training Orchestration） / 因运行时执行失败破坏法定人数而失败的轮次（Fail Round When Runtime Execution Failure Breaks Quorum）: 验证触发条件、载荷、重试和失败处理： 失败训练轮次（Fail Training Round）.
- 训练编排（Training Orchestration） / 因运行时执行启动失败破坏法定人数而失败的轮次（Fail Round When Runtime Execution Start Failure Breaks Quorum）: 验证触发条件、载荷、重试和失败处理： 失败训练轮次（Fail Training Round）.
- 训练编排（Training Orchestration） / 因运行时执行启动重试失败破坏法定人数而失败的轮次（Fail Round When Runtime Execution Start Retry Failure Breaks Quorum）: 验证触发条件、载荷、重试和失败处理： 失败训练轮次（Fail Training Round）.
- 训练编排（Training Orchestration） / 因运行时执行重试失败破坏法定人数而失败的轮次（Fail Round When Runtime Execution Retry Failure Breaks Quorum）: 验证触发条件、载荷、重试和失败处理： 失败训练轮次（Fail Training Round）.
- 训练编排（Training Orchestration） / 因错过贡献截止时间而失败的轮次（Fail Round When Contribution Deadline Missed）: 验证触发条件、载荷、重试和失败处理： 失败训练轮次（Fail Training Round）.
- 模型生命周期（Model Lifecycle） / 训练作业完成时注册最终模型（Register Final Model When Training Job Completed）: 验证触发条件、载荷、重试和失败处理： 注册候选模型（Register Candidate Model）.
- 运行时监控（Runtime Monitoring） / 从遥测检测离线运行时代理（Detect Offline Runtime Agent From Telemetry）: 验证触发条件、载荷、重试和失败处理： 检测运行时代理离线（Detect Runtime Agent Offline）.
- 运行时监控（Runtime Monitoring） / 从遥测标记运行时代理已恢复（Mark Recovered Runtime Agent From Telemetry）: 验证触发条件、载荷、重试和失败处理： 标记运行时代理已恢复（Mark Runtime Agent Recovered）.
- 运行时监控（Runtime Monitoring） / 从遥测检测资源压力（Detect Resource Pressure From Telemetry）: 验证触发条件、载荷、重试和失败处理： 检测运行时节点资源压力（Detect Runtime Node Resource Pressure）.
- 运行时监控（Runtime Monitoring） / 代理报告时记录运行时节点清单（Record Runtime Node Inventory When Agent Reported）: 验证触发条件、载荷、重试和失败处理： 记录运行时节点清单（Record Runtime Node Inventory）.
- 运行时监控（Runtime Monitoring） / 从资源遥测检测容量变化（Detect Capacity Change From Resource Telemetry）: 验证触发条件、载荷、重试和失败处理： 检测运行时节点容量变更（Detect Runtime Node Capacity Change）.
- 运行时监控（Runtime Monitoring） / 在节点资源压力时发出警报（Raise Alert On Node Resource Pressure）: 验证触发条件、载荷、重试和失败处理： 引发训练告警（Raise Training Alert）.
- 运行时监控（Runtime Monitoring） / 审计关键训练事件（Audit Critical Training Events）: 验证触发条件、载荷、重试和失败处理： 追加审计跟踪（Append Audit Trail）.
- 运行时监控（Runtime Monitoring） / 审计联邦已激活（Audit Federation Activated）: 验证触发条件、载荷、重试和失败处理： 追加审计跟踪（Append Audit Trail）.
- 运行时监控（Runtime Monitoring） / 审计联邦已暂停（Audit Federation Suspended）: 验证触发条件、载荷、重试和失败处理： 追加审计跟踪（Append Audit Trail）.
- 运行时监控（Runtime Monitoring） / 审计联邦已重新激活（Audit Federation Reactivated）: 验证触发条件、载荷、重试和失败处理： 追加审计跟踪（Append Audit Trail）.
- 运行时监控（Runtime Monitoring） / 审计参与者已加入（Audit Participant Joined）: 验证触发条件、载荷、重试和失败处理： 追加审计跟踪（Append Audit Trail）.
- 运行时监控（Runtime Monitoring） / 审计参与者已暂停（Audit Participant Suspended）: 验证触发条件、载荷、重试和失败处理： 追加审计跟踪（Append Audit Trail）.
- 运行时监控（Runtime Monitoring） / 审计数据集批准（Audit Dataset Approval）: 验证触发条件、载荷、重试和失败处理： 追加审计跟踪（Append Audit Trail）.
- 运行时监控（Runtime Monitoring） / 审计数据集批准已撤销（Audit Dataset Approval Revoked）: 验证触发条件、载荷、重试和失败处理： 追加审计跟踪（Append Audit Trail）.
- 运行时监控（Runtime Monitoring） / 审计训练作业已提交（Audit Training Job Submitted）: 验证触发条件、载荷、重试和失败处理： 追加审计跟踪（Append Audit Trail）.
- 运行时监控（Runtime Monitoring） / 审计模型已提升至生产（Audit Model Promoted To Production）: 验证触发条件、载荷、重试和失败处理： 追加审计跟踪（Append Audit Trail）.
- 运行时治理（Runtime Governance） / 在代理已安装时激活运行时身份（Activate Runtime Identity When Agent Installed）: 验证触发条件、载荷、重试和失败处理： 激活运行时标识（Activate Runtime Identity）.
- 运行时治理（Runtime Governance） / 在手动运行时已连接时激活运行时身份（Activate Runtime Identity When Manual Runtime Connected）: 验证触发条件、载荷、重试和失败处理： 激活运行时标识（Activate Runtime Identity）.
- 运行时治理（Runtime Governance） / 运行时激活时检测运行时能力（Detect Runtime Capabilities When Runtime Activated）: 验证触发条件、载荷、重试和失败处理： 检测运行时能力（Detect Runtime Capabilities）.
- 安全聚合（Secure Aggregation） / 在请求聚合时创建会话（Create Session When Aggregation Requested）: 验证触发条件、载荷、重试和失败处理： 创建安全聚合会话（Create Secure Aggregation Session）.
- 安全聚合（Secure Aggregation） / 会话创建时选择参与者（Select Participants When Session Created）: 验证触发条件、载荷、重试和失败处理： 选择安全聚合参与者（Select Secure Aggregation Participants）.
- 安全聚合（Secure Aggregation） / 选择参与者时准备加密上下文（Prepare Encryption Context When Participants Selected）: 验证触发条件、载荷、重试和失败处理： 准备同态加密上下文（Prepare Homomorphic Encryption Context）.
- 安全聚合（Secure Aggregation） / 加密更新就绪时完成聚合（Complete Aggregation When Encrypted Updates Ready）: 验证触发条件、载荷、重试和失败处理： 完成同态聚合会话（Complete Homomorphic Aggregation Session）.
- 运行时代理操作（Runtime Agent Operations） / 绑定配置时验证代理数据集访问（Validate Agent Dataset Access When Binding Configured）: 验证触发条件、载荷、重试和失败处理： 验证代理数据集访问（Validate Agent Dataset Access）.
- 运行时代理操作（Runtime Agent Operations） / 访问验证通过时分析数据集（Profile Dataset When Access Validated）: 验证触发条件、载荷、重试和失败处理： 分析代理数据集（Profile Agent Dataset）.
- 运行时代理操作（Runtime Agent Operations） / 访问重新验证时分析数据集（Profile Dataset When Access Revalidated）: 验证触发条件、载荷、重试和失败处理： 分析代理数据集（Profile Agent Dataset）.
- 运行时代理操作（Runtime Agent Operations） / 自动验证数据集合约（Validate Dataset Contract Automatically）: 验证触发条件、载荷、重试和失败处理： 验证数据集合约（Validate Dataset Contract）.
- 运行时代理操作（Runtime Agent Operations） / 分发时接收执行计划（Receive Execution Plan When Dispatched）: 验证触发条件、载荷、重试和失败处理： 接收参与者执行计划（Receive Participant Execution Plan）.
- 运行时代理操作（Runtime Agent Operations） / 收到执行计划时接受执行计划（Accept Execution Plan When Execution Plan Received）: 验证触发条件、载荷、重试和失败处理： 接受执行计划（Accept Execution Plan）.
- 运行时代理操作（Runtime Agent Operations） / 计划接受时启动轮次执行（Start Round Execution When Plan Accepted）: 验证触发条件、载荷、重试和失败处理： 开始轮次执行（Start Round Execution）.
- 运行时代理操作（Runtime Agent Operations） / 轮次执行启动时观察运行时引擎作业（Observe Runtime Engine Job When Round Execution Started）: 验证触发条件、载荷、重试和失败处理： 观察运行时引擎作业（Observe Runtime Engine Job）.
- 运行时代理操作（Runtime Agent Operations） / 运行时引擎作业完成时完成轮次执行（Complete Round Execution When Runtime Engine Job Completed）: 验证触发条件、载荷、重试和失败处理： 完成轮次执行（Complete Round Execution）.
- 运行时代理操作（Runtime Agent Operations） / Fail Round Execution When Runtime Engine Job Failed: 验证触发条件、载荷、重试和失败处理： 失败轮次执行（Fail Round Execution）.
- 运行时代理操作（Runtime Agent Operations） / Submit Local Model Update When Round Execution Completed: 验证触发条件、载荷、重试和失败处理： 提交代理本地模型更新（Submit Agent Local Model Update）.
- 运行时代理操作（Runtime Agent Operations） / Release Runtime Engine Job When Round Execution Completed: 验证触发条件、载荷、重试和失败处理： 完成后释放运行时引擎作业（Release Runtime Engine Job After Completion）.
- 运行时代理操作（Runtime Agent Operations） / Release Runtime Engine Job When Round Execution Failed: 验证触发条件、载荷、重试和失败处理： 失败后释放运行时引擎作业（Release Runtime Engine Job After Failure）.
- 运行时代理操作（Runtime Agent Operations） / Release Runtime Engine Job When Start Failed: 验证触发条件、载荷、重试和失败处理： 启动失败后释放运行时引擎作业（Release Runtime Engine Job After Start Failure）.
- 运行时代理操作（Runtime Agent Operations） / Release Runtime Engine Job When Start Retry Failed: 验证触发条件、载荷、重试和失败处理： 重试失败后释放运行时引擎作业（Release Runtime Engine Job After Retry Failure）.
- 运行时代理操作（Runtime Agent Operations） / Release Runtime Engine Job When Runtime Retry Failed: 验证触发条件、载荷、重试和失败处理： 运行时重试失败后释放运行时引擎作业（Release Runtime Engine Job After Runtime Retry Failure）.
- 运行时代理操作（Runtime Agent Operations） / Report Runtime Agent Started When Bootstrap Configuration Loaded: 验证触发条件、载荷、重试和失败处理： 报告运行时代理已启动（Report Runtime Agent Started）.
- 运行时代理操作（Runtime Agent Operations） / Report Ready After Configuration Fetch And Self Check: 验证触发条件、载荷、重试和失败处理： 报告运行时实例自检已通过（Report Runtime Instance Self Check Passed）.
- 运行时代理操作（Runtime Agent Operations） / Report Runtime Instance Connected After Self Check: 验证触发条件、载荷、重试和失败处理： 报告运行时实例已连接（Report Runtime Instance Connected）.
- 运行时代理操作（Runtime Agent Operations） / Report Node Inventory When Inventory Changed: 验证触发条件、载荷、重试和失败处理： 报告代理运行时节点清单（Report Agent Runtime Node Inventory）.

## 十一、数据与 Read Model 测试/FLP-TO-DRMT

| Read Model | 形态 | 来源事件 | 关键断言 |
| --- | --- | --- | --- |
| 组织目录（Organization Directory） | 集合/列表 | 组织已注册（Organization Registered）, 组织已激活（Organization Activated）, 组织已停用（Organization Deactivated）, 组织已重新激活（Organization Reactivated）, 数据集已批准用于训练（Dataset Approved For Training） | 标识: organizationId; 按访问模式推导查询断言; 分页与稳定排序 |
| 联邦概览（Federation Overview） | 集合/列表 | 联邦已创建（Federation Created）, 联邦已激活（Federation Activated）, 联邦已暂停（Federation Suspended）, 联邦已重新激活（Federation Reactivated）, 参与者已邀请（Participant Invited）, 参与者已加入（Participant Joined）, 参与者已拒绝（Participant Rejected）, 参与者邀请已撤销（Participant Invitation Revoked）, 参与者已暂停（Participant Suspended）, 参与者已移除（Participant Removed）, 运行时身份已激活（Runtime Identity Activated）, 运行时身份已撤销（Runtime Identity Revoked）, 训练作业已提交（Training Job Submitted） | 标识: federationId; 按访问模式推导查询断言; 分页与稳定排序 |
| 联邦成员资格目录（Federation Membership Directory） | 集合/列表 | 联邦已创建（Federation Created）, 参与者已邀请（Participant Invited）, 参与者已加入（Participant Joined）, 参与者已拒绝（Participant Rejected）, 参与者邀请已撤销（Participant Invitation Revoked）, 参与者已暂停（Participant Suspended）, 参与者已移除（Participant Removed）, 组织已注册（Organization Registered）, 组织已激活（Organization Activated） | 标识: federationId, organizationId; 按访问模式推导查询断言; 分页与稳定排序 |
| 字典目录（Dictionary Catalog） | 集合/列表 | 字典已注册（Dictionary Registered）, 字典已更新（Dictionary Updated）, 字典已归档（Dictionary Archived） | 标识: dictionaryId; 查询: dictionaryCode; 分页与稳定排序 |
| 字典值目录（Dictionary Value Catalog） | 集合/列表 | 字典值已添加（Dictionary Value Added）, 字典值已停用（Dictionary Value Disabled）, 字典值已启用（Dictionary Value Enabled） | 标识: dictionaryValueId; 查询: dictionaryCode, active; 分页与稳定排序 |
| 暂存文件目录（Staged File Catalog） | 集合/列表 | 文件上传已暂存（File Upload Staged）, 暂存文件已消费（Staged File Consumed）, 暂存文件已丢弃（Staged File Discarded）, 暂存文件已过期（Staged File Expired） | 标识: stagedFileId; 查询: purpose; 分页与稳定排序 |
| 运行时基础设施包目录（Runtime Infrastructure Package Catalog） | 集合/列表 | 运行时基础设施包已注册（Runtime Infrastructure Package Registered） | 标识: runtimeInfrastructurePackageId; 按访问模式推导查询断言; 分页与稳定排序 |
| 运行时安装计划目录（Runtime Installation Plan Catalog） | 集合/列表 | 组织已注册（Organization Registered）, 运行时基础设施包已注册（Runtime Infrastructure Package Registered）, 运行时安装计划已创建（Runtime Installation Plan Created）, 运行时基础设施已计划（Runtime Infrastructure Planned）, 运行时基础设施已注册（Runtime Infrastructure Registered）, 运行时基础设施已验证（Runtime Infrastructure Verified）, 运行时基础设施验证失败（Runtime Infrastructure Verification Failed）, 运行时代理安装成功（Runtime Agent Installation Succeeded）, 运行时代理安装失败（Runtime Agent Installation Failed）, 运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）, 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed）, 运行时连接已建立（Runtime Connection Established） | 标识: runtimeInstallationPlanId; 按访问模式推导查询断言; 分页与稳定排序 |
| 运行时安装指南（Runtime Installation Guide） | 集合/列表 | 组织已注册（Organization Registered）, 运行时基础设施包已注册（Runtime Infrastructure Package Registered）, 运行时安装计划已创建（Runtime Installation Plan Created） | 标识: runtimeInstallationPlanId; 按访问模式推导查询断言; 分页与稳定排序 |
| 运行时代理端点目录（Runtime Agent Endpoint Catalog） | 集合/列表 | 运行时连接已建立（Runtime Connection Established）, 运行时身份已激活（Runtime Identity Activated）, 运行时身份已撤销（Runtime Identity Revoked）, 已检测到运行时代理离线（Runtime Agent Offline Detected）, 运行时代理已恢复（Runtime Agent Recovered） | 标识: runtimeAgentId; 按访问模式推导查询断言; 分页与稳定排序 |
| 运行时基础设施访问视图（Runtime Infrastructure Access View） | 集合/列表 | 组织已注册（Organization Registered）, 运行时基础设施包已注册（Runtime Infrastructure Package Registered）, 运行时安装计划已创建（Runtime Installation Plan Created）, 运行时基础设施已计划（Runtime Infrastructure Planned）, 运行时基础设施已注册（Runtime Infrastructure Registered）, 运行时基础设施已验证（Runtime Infrastructure Verified）, 运行时基础设施验证失败（Runtime Infrastructure Verification Failed）, 运行时代理安装成功（Runtime Agent Installation Succeeded）, 运行时代理安装失败（Runtime Agent Installation Failed）, 运行时代理部署重试成功（Runtime Agent Deployment Retry Succeeded）, 运行时代理部署重试失败（Runtime Agent Deployment Retry Failed）, 运行时连接已建立（Runtime Connection Established） | 标识: runtimeInfrastructureId; 按访问模式推导查询断言; 分页与稳定排序 |
| 当前推荐的特征架构目录（Current Recommended Feature Schema Catalog） | 集合/列表 | 已标记当前推荐特征架构版本（Current Recommended Feature Schema Version Marked） | 标识: featureDomain; 按访问模式推导查询断言; 分页与稳定排序 |
| 特征架构目录（Feature Schema Catalog） | 集合/列表 | 特征架构已定义（Feature Schema Defined）, 特征架构已发布（Feature Schema Published）, 特征架构已弃用（Feature Schema Deprecated）, 特征架构已停用（Feature Schema Retired）, 特征架构版本已取代（Feature Schema Version Superseded）, 已标记当前推荐特征架构版本（Current Recommended Feature Schema Version Marked） | 标识: featureSchemaId; 按访问模式推导查询断言; 分页与稳定排序 |
| 运行时数据集元数据目录（Runtime Dataset Metadata Catalog） | 集合/列表 | 组织已注册（Organization Registered）, 特征架构已定义（Feature Schema Defined）, 数据集元数据已报告（Dataset Metadata Reported）, 数据集元数据已重新分析（Dataset Metadata Reprofiled）, 代理数据集分析失败（Agent Dataset Profiling Failed）, 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed） | 标识: metadataReportId; 按访问模式推导查询断言; 分页与稳定排序 |
| 模型制品目录（Model Artifact Catalog） | 集合/列表 | 训练作业已创建（Training Job Created）, 模型工件已注册（Model Artifact Registered）, 全局模型已更新（Global Model Updated） | 标识: modelId; 按访问模式推导查询断言; 分页与稳定排序 |
| 训练运行配置目录（Training Run Configuration Catalog） | 集合/列表 | 联邦已创建（Federation Created）, 特征架构已定义（Feature Schema Defined）, 训练运行配置已定义（Training Run Configuration Defined）, 训练运行配置已更新（Training Run Configuration Updated）, 训练运行配置已锁定（Training Run Configuration Locked） | 标识: trainingRunConfigurationId; 按访问模式推导查询断言; 分页与稳定排序 |
| 训练参与者资格（Training Participant Eligibility） | 集合/列表 | 联邦已创建（Federation Created）, 组织已注册（Organization Registered）, 特征架构已定义（Feature Schema Defined）, 训练作业已创建（Training Job Created）, 训练作业已提交（Training Job Submitted）, 参与者已加入（Participant Joined）, 参与者已暂停（Participant Suspended）, 参与者已移除（Participant Removed）, 运行时身份已激活（Runtime Identity Activated）, 运行时身份已撤销（Runtime Identity Revoked）, 运行时能力已检测（Runtime Capabilities Detected）, 运行时连接已建立（Runtime Connection Established）, 已检测到运行时代理离线（Runtime Agent Offline Detected）, 运行时代理已恢复（Runtime Agent Recovered）, 数据集已声明（Dataset Declared）, 数据集元数据已报告（Dataset Metadata Reported）, 数据集合约已验证（Dataset Contract Validated）, 数据集已批准用于训练（Dataset Approved For Training）, 数据集训练批准已撤销（Dataset Training Approval Revoked）, 代理数据集访问已验证（Agent Dataset Access Validated） | 标识: trainingJobId; 按访问模式推导查询断言; 分页与稳定排序 |
| 训练作业仪表板（Training Job Dashboard） | 集合/列表 | 联邦已创建（Federation Created）, 特征架构已定义（Feature Schema Defined）, 训练作业已创建（Training Job Created）, 训练作业已提交（Training Job Submitted）, 训练作业已暂停（Training Job Paused）, 训练作业已恢复（Training Job Resumed）, 训练作业已取消（Training Job Canceled）, 训练轮次已启动（Training Round Started）, 训练轮次已完成（Training Round Completed）, 训练作业已完成（Training Job Completed） | 标识: trainingJobId; 按访问模式推导查询断言; 分页与稳定排序 |
| 训练轮次进度（Training Round Progress） | 集合/列表 | 训练作业已创建（Training Job Created）, 特征架构已定义（Feature Schema Defined）, 训练轮次参与者已选择（Training Round Participants Selected）, 训练轮次参与者选择失败（Training Round Participant Selection Failed）, 训练轮次已启动（Training Round Started）, 训练轮次启动失败（Training Round Start Failed）, 参与者执行计划已生成（Participant Execution Plan Generated）, 参与者执行计划已分发（Participant Execution Plan Dispatched）, 轮次执行已启动（Round Execution Started）, 轮次执行启动失败（Round Execution Start Failed）, Round Execution Start Retry Started, 轮次执行启动重试失败（Round Execution Start Retry Failed）, 轮次执行已完成（Round Execution Completed）, 轮次执行失败（Round Execution Failed）, Round Execution Runtime Retry Started, 轮次执行运行时重试失败（Round Execution Runtime Retry Failed）, 模型更新提交已接收（Model Update Submission Received）, 模型更新提交已接受（Model Update Submission Accepted）, 模型更新提交已拒绝（Model Update Submission Rejected）, 已请求安全聚合（Secure Aggregation Requested）, 全局模型已更新（Global Model Updated）, 全局模型评估已提交（Global Model Evaluation Submitted）, 训练轮次已完成（Training Round Completed）, 训练轮次失败（Training Round Failed） | 标识: trainingJobId, roundId; 按访问模式推导查询断言; 分页与稳定排序 |
| 模型目录（Model Catalog） | 集合/列表 | 训练作业已创建（Training Job Created）, 模型候选已注册（Model Candidate Registered）, 模型评估包已记录（Model Evaluation Package Recorded）, 模型已批准（Model Approved）, 模型已提升到生产环境（Model Promoted To Production）, 模型已回滚（Model Rolled Back）, 模型已退役（Model Retired） | 标识: modelId; 按访问模式推导查询断言; 分页与稳定排序 |
| 运行时最新遥测（Runtime Telemetry Latest） | 集合/列表 | 尚未明确 | 标识: nodeId; 按访问模式推导查询断言; 分页与稳定排序 |
| 运行时节点清单视图（Runtime Node Inventory View） | 集合/列表 | 组织已注册（Organization Registered）, 运行时安装计划已创建（Runtime Installation Plan Created）, 运行时基础设施已注册（Runtime Infrastructure Registered）, 运行时节点清单已报告（Runtime Node Inventory Reported） | 标识: nodeId; 按访问模式推导查询断言; 分页与稳定排序 |
| 运行时节点最新资源（Runtime Node Resource Latest） | 集合/列表 | 尚未明确 | 标识: nodeId; 按访问模式推导查询断言; 分页与稳定排序 |
| 运行时健康仪表板（Runtime Health Dashboard） | 集合/列表 | 联邦已创建（Federation Created）, 训练作业已创建（Training Job Created）, 已检测到运行时代理离线（Runtime Agent Offline Detected）, 运行时代理已恢复（Runtime Agent Recovered）, 已检测到运行时节点资源压力（Runtime Node Resource Pressure Detected）, 运行时节点容量已更改（Runtime Node Capacity Changed）, 运行时节点清单已报告（Runtime Node Inventory Reported）, 训练警报已发出（Training Alert Raised） | 标识: nodeId; 按访问模式推导查询断言; 分页与稳定排序 |
| 训练告警目录（Training Alert Catalog） | 集合/列表 | 运行时节点清单已报告（Runtime Node Inventory Reported）, 训练作业已创建（Training Job Created）, 训练警报已发出（Training Alert Raised）, 训练警报已确认（Training Alert Acknowledged）, 训练警报已解决（Training Alert Resolved） | 标识: alertId; 按访问模式推导查询断言; 分页与稳定排序 |
| 审计记录日志（Audit Record Log） | 集合/列表 | 审计跟踪已追加（Audit Trail Appended） | 标识: auditRecordId; 按访问模式推导查询断言; 分页与稳定排序 |
| 运行时标识目录（Runtime Identity Catalog） | 集合/列表 | 组织已注册（Organization Registered）, 运行时身份已激活（Runtime Identity Activated）, 运行时身份已撤销（Runtime Identity Revoked） | 标识: runtimeId; 按访问模式推导查询断言; 分页与稳定排序 |
| 运行时能力目录（Runtime Capability Catalog） | 集合/列表 | 运行时能力已检测（Runtime Capabilities Detected） | 标识: runtimeId; 按访问模式推导查询断言; 分页与稳定排序 |
| 安全聚合会话目录（Secure Aggregation Session Catalog） | 集合/列表 | 安全聚合会话已创建（Secure Aggregation Session Created）, 安全聚合参与者已选择（Secure Aggregation Participants Selected）, 同态加密上下文已准备（Homomorphic Encryption Context Prepared）, 已接收加密模型更新（Encrypted Model Update Received）, 安全聚合已完成（Secure Aggregation Completed）, 安全聚合失败（Secure Aggregation Failed） | 标识: secureAggregationSessionId; 按访问模式推导查询断言; 分页与稳定排序 |
| 运行时数据集绑定目录（Runtime Dataset Binding Catalog） | 集合/列表 | 数据集已声明（Dataset Declared）, 运行时数据集绑定已配置（Runtime Dataset Binding Configured） | 标识: runtimeDatasetBindingId; 按访问模式推导查询断言; 分页与稳定排序 |
| 代理数据集访问验证目录（Agent Dataset Access Validation Catalog） | 集合/列表 | 数据集已声明（Dataset Declared）, 代理数据集访问已验证（Agent Dataset Access Validated）, 代理数据集访问验证失败（Agent Dataset Access Validation Failed）, 代理数据集访问已重新验证（Agent Dataset Access Revalidated）, 代理数据集访问重新验证失败（Agent Dataset Access Revalidation Failed） | 标识: datasetAccessValidationId; 按访问模式推导查询断言; 分页与稳定排序 |
| 数据集能力（Dataset Capability） | 集合/列表 | 组织已注册（Organization Registered）, 特征架构已定义（Feature Schema Defined）, 数据集已声明（Dataset Declared）, 代理数据集元数据已报告（Agent Dataset Metadata Reported）, 代理数据集分析失败（Agent Dataset Profiling Failed）, 代理数据集已重新分析（Agent Dataset Reprofiled）, 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed）, 数据集合约已验证（Dataset Contract Validated）, 数据集合约验证失败（Dataset Contract Validation Failed）, 数据集合约已重新验证（Dataset Contract Revalidated）, 数据集合约重新验证失败（Dataset Contract Revalidation Failed）, 数据集已拒绝用于训练（Dataset Rejected For Training）, 数据集已批准用于训练（Dataset Approved For Training）, 数据集训练批准已撤销（Dataset Training Approval Revoked） | 标识: datasetId; 按访问模式推导查询断言; 分页与稳定排序 |
| 数据集就绪状态（Dataset Readiness） | 集合/列表 | 组织已注册（Organization Registered）, 特征架构已定义（Feature Schema Defined）, 数据集已声明（Dataset Declared）, 代理数据集元数据已报告（Agent Dataset Metadata Reported）, 代理数据集分析失败（Agent Dataset Profiling Failed）, 代理数据集已重新分析（Agent Dataset Reprofiled）, 代理数据集重新剖析失败（Agent Dataset Reprofiling Failed）, 数据集合约已验证（Dataset Contract Validated）, 数据集合约验证失败（Dataset Contract Validation Failed）, 数据集合约已重新验证（Dataset Contract Revalidated）, 数据集合约重新验证失败（Dataset Contract Revalidation Failed）, 数据集已拒绝用于训练（Dataset Rejected For Training）, 数据集已批准用于训练（Dataset Approved For Training）, 数据集训练批准已撤销（Dataset Training Approval Revoked）, 代理数据集访问已验证（Agent Dataset Access Validated）, 代理数据集访问验证失败（Agent Dataset Access Validation Failed）, 代理数据集访问已重新验证（Agent Dataset Access Revalidated）, 代理数据集访问重新验证失败（Agent Dataset Access Revalidation Failed）, 运行时连接已建立（Runtime Connection Established）, 已检测到运行时代理离线（Runtime Agent Offline Detected）, 运行时代理已恢复（Runtime Agent Recovered） | 标识: datasetId; 按访问模式推导查询断言; 分页与稳定排序 |
| 轮次执行目录（Round Execution Catalog） | 集合/列表 | 参与者执行计划已分发（Participant Execution Plan Dispatched）, 执行计划已接收（Execution Plan Received）, 执行计划已接受（Execution Plan Accepted）, 执行计划已拒绝（Execution Plan Rejected）, 轮次执行已启动（Round Execution Started）, 轮次执行启动失败（Round Execution Start Failed）, 运行时引擎作业已观察（Runtime Engine Job Observed）, Round Execution Start Retry Started, 轮次执行启动重试失败（Round Execution Start Retry Failed）, 轮次执行已完成（Round Execution Completed）, 轮次执行失败（Round Execution Failed）, Round Execution Runtime Retry Started, 轮次执行运行时重试失败（Round Execution Runtime Retry Failed）, Agent Local Model Update Submitted, Runtime Engine Job Released, Runtime Engine Job Release Failed Or Skipped, Runtime Engine Job Release Failed Or Skipped After Start Failure, Runtime Engine Job Release Failed Or Skipped After Retry, Runtime Engine Job Release Failed Or Skipped After Runtime Retry | 标识: roundExecutionId; 按访问模式推导查询断言; 分页与稳定排序 |
| 运行时代理生命周期目录（Runtime Agent Lifecycle Catalog） | 集合/列表 | Runtime Agent Bootstrap Configuration Loaded, Runtime Agent Bootstrap Configuration Load Failed, Runtime Agent Started, Runtime Instance Self Check Passed | 标识: runtimeAgentId; 按访问模式推导查询断言; 分页与稳定排序 |
| 代理运行时基础设施连接目录（Agent Runtime Infrastructure Connection Catalog） | 集合/列表 | Agent Runtime Connection Established, Agent Runtime Connection Report Failed | 标识: runtimeInfrastructureId; 按访问模式推导查询断言; 分页与稳定排序 |
| 代理运行时遥测最新（Agent Runtime Telemetry Latest） | 集合/列表 | 尚未明确 | 标识: nodeId; 按访问模式推导查询断言; 分页与稳定排序 |
| 代理运行时节点清单目录（Agent Runtime Node Inventory Catalog） | 集合/列表 | 组织已注册（Organization Registered）, 代理运行时节点清单已报告（Agent Runtime Node Inventory Reported） | 标识: runtimeNodeInventoryReportId; 按访问模式推导查询断言; 分页与稳定排序 |
| 代理运行时节点资源最新（Agent Runtime Node Resource Latest） | 集合/列表 | 尚未明确 | 标识: nodeId; 按访问模式推导查询断言; 分页与稳定排序 |

## 十二、非功能测试范围/FLP-TO-NFTS

- 每个面向用户操作需要覆盖权限与租户隔离测试。
- 会改变状态的操作需要覆盖幂等、重复提交、重试和并发测试。
- 涉及敏感或监管数据时需要覆盖审计、追踪、脱敏和保留策略检查。
- 列表、详情、仪表板和高数据量读模型需要做性能冒烟测试。
- 自动化、集成和 Read Model 重放路径需要覆盖失败恢复测试。

## 十三、回归与自动化计划/FLP-TO-RAP

- 为 组织管理（Organization Management） 的业务能力、状态变化和读侧视图建立冒烟与回归覆盖。
- 为 联邦管理（Federation Management） 的业务能力、状态变化和读侧视图建立冒烟与回归覆盖。
- 为 字典维护（Dictionary Maintenance） 的业务能力、状态变化和读侧视图建立冒烟与回归覆盖。
- 为 文件上传（File Upload） 的业务能力、状态变化和读侧视图建立冒烟与回归覆盖。
- 为 运行时预配（Runtime Provisioning） 的业务能力、状态变化和读侧视图建立冒烟与回归覆盖。
- 为 数据集治理（Dataset Governance） 的业务能力、状态变化和读侧视图建立冒烟与回归覆盖。
- 为 模型仓库（Model Repository） 的业务能力、状态变化和读侧视图建立冒烟与回归覆盖。
- 为 训练编排（Training Orchestration） 的业务能力、状态变化和读侧视图建立冒烟与回归覆盖。
- 为 模型生命周期（Model Lifecycle） 的业务能力、状态变化和读侧视图建立冒烟与回归覆盖。
- 为 运行时监控（Runtime Monitoring） 的业务能力、状态变化和读侧视图建立冒烟与回归覆盖。
- 为 运行时治理（Runtime Governance） 的业务能力、状态变化和读侧视图建立冒烟与回归覆盖。
- 为 安全聚合（Secure Aggregation） 的业务能力、状态变化和读侧视图建立冒烟与回归覆盖。
- 为 运行时代理操作（Runtime Agent Operations） 的业务能力、状态变化和读侧视图建立冒烟与回归覆盖。
- 缺口驱动测试: 组织管理（Organization Management） 有 3 个业务操作尚未定义明确验收规则：激活组织（Activate Organization）, 停用组织（Deactivate Organization）, 重新激活组织（Reactivate Organization）。
- 缺口驱动测试: 联邦管理（Federation Management） 有 8 个业务操作尚未定义明确验收规则：激活联邦（Activate Federation）, 暂停联邦（Suspend Federation）, 重新激活联邦（Reactivate Federation）, 邀请参与者（Invite Participant），另有 4 个。
- 缺口驱动测试: 字典维护（Dictionary Maintenance） 有 5 个业务操作尚未定义明确验收规则：更新字典（Update Dictionary）, 归档字典（Archive Dictionary）, 添加字典值（Add Dictionary Value）, 停用字典值（Disable Dictionary Value），另有 1 个。
- 缺口驱动测试: 运行时预配（Runtime Provisioning） 有 5 个业务操作尚未定义明确验收规则：注册运行时基础设施包（Register Runtime Infrastructure Package）, 创建运行时安装计划（Create Runtime Installation Plan）, 规划运行时基础设施（Plan Runtime Infrastructure）, 注册运行时基础设施（Register Runtime Infrastructure），另有 1 个。
- 缺口驱动测试: 数据集治理（Dataset Governance） 有 4 个业务操作尚未定义明确验收规则：弃用特征架构（Deprecate Feature Schema）, 停用特征架构（Retire Feature Schema）, 记录运行时数据集元数据（Record Runtime Dataset Metadata）, 记录运行时数据集重新分析元数据（Record Runtime Dataset Reprofiled Metadata）。
- 缺口驱动测试: 训练编排（Training Orchestration） 有 11 个业务操作尚未定义明确验收规则：锁定训练运行配置（Lock Training Run Configuration）, 暂停训练作业（Pause Training Job）, 恢复训练作业（Resume Training Job）, 取消训练作业（Cancel Training Job），另有 7 个。
- 缺口驱动测试: 模型生命周期（Model Lifecycle） 有 3 个业务操作尚未定义明确验收规则：注册候选模型（Register Candidate Model）, 回滚模型（Rollback Model）, 停用模型（Retire Model）。
- 缺口驱动测试: 运行时监控（Runtime Monitoring） 有 9 个业务操作尚未定义明确验收规则：检测运行时代理离线（Detect Runtime Agent Offline）, 标记运行时代理已恢复（Mark Runtime Agent Recovered）, 检测运行时节点资源压力（Detect Runtime Node Resource Pressure）, 记录运行时节点清单（Record Runtime Node Inventory），另有 5 个。
- 缺口驱动测试: 运行时治理（Runtime Governance） 有 3 个业务操作尚未定义明确验收规则：激活运行时标识（Activate Runtime Identity）, 撤销运行时标识（Revoke Runtime Identity）, 检测运行时能力（Detect Runtime Capabilities）。
- 缺口驱动测试: 安全聚合（Secure Aggregation） 有 6 个业务操作尚未定义明确验收规则：创建安全聚合会话（Create Secure Aggregation Session）, 选择安全聚合参与者（Select Secure Aggregation Participants）, 准备同态加密上下文（Prepare Homomorphic Encryption Context）, 记录加密模型更新（Record Encrypted Model Update），另有 2 个。
- 缺口驱动测试: 运行时代理操作（Runtime Agent Operations） 有 16 个业务操作尚未定义明确验收规则：拒绝数据集用于训练（Reject Dataset For Training）, 撤销数据集训练审批（Revoke Dataset Training Approval）, 接收参与者执行计划（Receive Participant Execution Plan）, 完成轮次执行（Complete Round Execution），另有 12 个。
- 缺口驱动测试: 运行时最新遥测（Runtime Telemetry Latest） 尚未明确数据更新来源。
- 缺口驱动测试: 运行时节点最新资源（Runtime Node Resource Latest） 尚未明确数据更新来源。
- 缺口驱动测试: 代理运行时遥测最新（Agent Runtime Telemetry Latest） 尚未明确数据更新来源。
- 缺口驱动测试: 代理运行时节点资源最新（Agent Runtime Node Resource Latest） 尚未明确数据更新来源。

## 十四、准出标准/FLP-TO-EC

- 目标环境中所有 P0/P1 功能流程测试通过。
- 所有明确业务规则均具备正向和反向测试覆盖。
- 关键 Read Model 已验证更新来源、标识、查询行为和重建假设。
- 已知缺口和待确认事项已解决，或由产品、研发、测试共同接受。
- 回归自动化覆盖生命周期流转、权限、重试和主要集成路径。
