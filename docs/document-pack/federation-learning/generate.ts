import { execFileSync } from 'node:child_process';
import { mkdirSync, readFileSync, writeFileSync } from 'node:fs';
import { dirname, join, relative, resolve } from 'node:path';
import { fileURLToPath } from 'node:url';

type DocumentationKind =
  | 'prd'
  | 'software-design'
  | 'database-design'
  | 'user-manual'
  | 'installation-manual'
  | 'test-outline'
  | 'test-record';

type ModelTranslations = Record<string, string>;

type OutputMode = 'standalone' | 'merged' | 'all';

interface PackOptions {
  sourceModelPath: string;
  translationPath?: string;
  outputDirectory: string;
  mode: OutputMode;
  enhanceWithModel: boolean;
  generatedAt: string;
}

interface ProjectDocumentSpec {
  number: string;
  kind: DocumentationKind;
  name: string;
  existingTitle: string;
  filenameTitle: string;
  standalone: (body: string) => string[];
  merged: (body: string) => string[];
}

const scriptPath = fileURLToPath(import.meta.url);
const packDirectory = dirname(scriptPath);
const federationLearningRoot = resolve(packDirectory, '../../..');
const workspaceRoot = resolve(federationLearningRoot, '..');
const medolRoot = resolve(workspaceRoot, 'medol');
const markdownToDocxScript = join(packDirectory, 'markdown_to_docx.py');

const projectTitle = '“感-通-算”一体化的跨域健康管理联邦孪生平台';
const filesystemProjectTitle = '“感一通一算”一体化的跨域健康管理联邦孪生平台';
const subsystemName = '联邦学习子系统';

const supplementalTranslations: ModelTranslations = {
  'Federation Learning Platform': '联邦学习平台',
  'File upload owns short-lived staged file references used by generated forms and backend adapters. Business commands store the stagedFileId returned by this context, not a browser-local file path or storage location.': '文件上传负责管理生成表单和后端适配器使用的短期暂存文件引用。业务命令只保存该上下文返回的暂存文件标识，不保存浏览器本地文件路径或存储位置。',
  'Runtime onboarding owns runtime installation planning, bootstrap configuration, runtime environment registration, agent installation, readiness, connection reporting, and access revocation. K3s, Kubernetes, Docker Compose, and host process runtimes are installation targets, not separate domain concepts.': '运行时接入负责运行时安装规划、启动配置、运行环境登记、代理安装、就绪检查、连接上报和访问撤销。K3s、Kubernetes、Docker Compose 及主机进程运行时属于安装目标，不作为独立领域概念。',
  'Runtime bootstrap and agent installation material is support configuration, not domain state: the platform backend adapter reads platform.runtime.bootstrap.publicBaseAddress, platform.runtime.agent.platformApiBaseAddress, platform.runtime.agent.controlApiBaseAddress, platform.runtime.agent.containerRegistry, platform.runtime.agent.trustBundleSecretName, and platform.runtime.agent.accessGrantSecretPrefix from deployment config or a secret store. Commands, events, and read models keep only runtimeInstallationPlanId, runtimeInfrastructurePackageId, and generated access grant IDs.': '运行时启动与代理安装材料属于支撑配置，不属于领域状态。平台后端适配器从部署配置或密钥存储读取平台访问地址、代理控制地址、容器镜像仓库、信任包密钥名称和访问授权密钥前缀。命令、事件和读模型中仅保留运行时安装计划标识、运行时基础设施包标识和生成的访问授权标识。',
  'Dataset governance separates feature schemas, platform-recorded runtime dataset metadata, dataset access validation evidence, and train evaluation bundles. Runtime agents own dataset declarations, data connectors, local access profiles, and credentials inside participant infrastructure.': '数据集治理区分特征模式、平台记录的运行时数据集元数据、数据集访问校验证据和训练评估包。运行时代理在参与方基础设施内负责数据集声明、数据连接器、本地访问配置和凭证。',
  'Model repository owns model identity and immutable, pullable model artifact locations used by training orchestration and runtime agents.': '模型仓库负责模型标识，以及训练编排和运行时代理使用的不可变、可拉取模型构件位置。',
  'Training orchestration owns job intent, participant eligibility, runtime dispatch, and each round of distributed training and evaluation.': '训练编排负责训练作业意图、参与方资格、运行时分发，以及每轮分布式训练和评估。',
  'Model lifecycle starts after training has produced a final evaluated candidate.': '模型生命周期从训练产出完成评估的候选模型后开始。',
  'Runtime operations separates low-frequency runtime state changes, node inventory changes, latest telemetry views, training alerts, and append only audit records. High-frequency heartbeat and resource samples are telemetry, not event-sourced domain events.': '运行时运维区分低频运行时状态变化、节点清单变化、最新遥测视图、训练告警和追加式审计记录。高频心跳和资源采样属于遥测数据，不作为事件溯源领域事件。',
  'Runtime governance owns stable RuntimeIdentity activation, capability detection, and revocation. Runtime private keys, local paths, credentials, node scheduling, and data-plane connectivity stay inside participant runtime infrastructure.': '运行时治理负责稳定运行时身份的激活、能力探测和撤销。运行时私钥、本地路径、凭证、节点调度和数据平面连接保留在参与方运行时基础设施内。',
  'Platform-managed runtime agents are activated from platform deployment outcomes. Network reachability is not business authorization. Training eligibility uses active federation membership, RuntimeIdentity, detected capabilities, connected runtime status, and dataset readiness.': '平台托管运行时代理由平台部署结果激活。网络可达不等同于业务授权。训练资格依据有效联邦成员关系、运行时身份、已探测能力、运行时连接状态和数据集就绪状态判断。',
  'Training parameter encryption, secure aggregation keys, and data-plane message encryption are handled by protocol adapters or SecureAggregation, not by RuntimeIdentity events.': '训练参数加密、安全聚合密钥和数据平面消息加密由协议适配器或安全聚合能力处理，不由运行时身份事件处理。',
  'Secure aggregation owns homomorphic-encryption-based aggregation sessions. The platform owns the private key and publishes only encryption context metadata to runtime agents; raw key material, public key documents, and storage locations stay in SecureAggregationService or KMS adapter configuration.': '安全聚合负责基于同态加密的聚合会话。平台持有私钥，仅向运行时代理发布加密上下文元数据；原始密钥材料、公钥文档和存储位置保留在安全聚合服务或 KMS 适配器配置中。',
  'Runtime agent operations is a separate deployable that runs inside participant infrastructure and executes dispatched training jobs with local data and local secrets.': '运行时代理操作是部署在参与方基础设施内的独立单元，使用本地数据和本地密钥执行下发的训练任务。',
  'API': '接口',
  'MLOps': '机器学习运维',
  'MLOps Engineer': '机器学习运维工程师',
  'Read Model': '读模型',
  'String': '文本',
  'UUID': '唯一标识',
  'Int': '整数',
  'Decimal': '小数',
  'Boolean': '布尔值',
  'DateTime': '日期时间',
  'Single': '单条',
  'Optional': '可选',
  'technical': '技术',
  'generated': '生成',
  'emits': '产生',
  'automation': '自动化',
  'event': '事件',
  'onKind': '触发类型',
  'unspecified': '未指定',
  'id': '标识',
  'File Upload': '文件上传',
  'Runtime Provisioning': '运行时预配',
  'Training Orchestration': '训练编排',
  'Model Lifecycle': '模型生命周期',
  'Runtime Monitoring': '运行时监控',
  'Runtime Governance': '运行时治理',
  'Secure Aggregation': '安全聚合',
  'Runtime Agent Operations': '运行时代理操作',
  'Runtime Infrastructure Package Catalog': '运行时基础设施包目录',
  'Runtime Agent Endpoint Catalog': '运行时代理端点目录',
  'Runtime Dataset Metadata Catalog': '运行时数据集元数据目录',
  'Training Run Configuration Catalog': '训练运行配置目录',
  'Training Job Dashboard': '训练作业看板',
  'Training Round Progress': '训练轮次进度',
  'Runtime Telemetry Latest': '最新运行时遥测',
  'Runtime Node Resource Latest': '最新运行时节点资源',
  'Agent Runtime Telemetry Latest': '最新代理运行时遥测',
  'Agent Runtime Node Resource Latest': '最新代理运行时节点资源',
  'Runtime Identity': '运行时身份',
  'Runtime Capability': '运行时能力',
  'Runtime Health': '运行时健康',
  'Training Job': '训练作业',
  'Training Round': '训练轮次',
  'Training Run Configuration': '训练运行配置',
  'Training Alert': '训练告警',
  'Feature Schema': '特征模式',
  'Runtime Engine Job': '运行时引擎作业',
  'Secure Aggregation Session': '安全聚合会话'
};

const projectDocumentTranslations: ModelTranslations = {
  'Event Modeling': '事件建模',
  'Word': 'Word',
  'Markdown': 'Markdown',
  'SLA': '服务等级协议',
  'User': '用户',
  'User Account': '用户账号',
  'User Account Bound To Organization': '用户账号已绑定到组织',
  'User Account Registered': '用户账号已注册',
  'User Organization Membership': '用户组织成员关系',
  'User Organization Membership Directory': '用户组织成员关系目录',
  'Uploaded File': '已上传文件',
  'Uploaded File Catalog': '已上传文件目录',
  'Upload File': '上传文件',
  'FileUpload is the shared persistent file-storage boundary. It stores uploaded content through a replaceable storage adapter, records each uploaded file, exposes an authenticated content URI, and applies optional retention through expiresAt. Business domains reference the uploaded file record instead of creating another physical storage tier.': '文件上传是共享的持久化文件存储边界。它通过可替换的存储适配器保存上传内容，记录每个已上传文件，暴露经过认证的内容访问地址，并通过过期时间支持可选留存策略。业务领域引用上传文件记录，不再重复建设物理存储层。',
  'File Uploaded': '文件已上传',
  'File Referenced': '文件已引用',
  'File Discarded': '文件已丢弃',
  'File Expired': '文件已过期',
  'Mark File Referenced': '标记文件已引用',
  'Download File': '下载文件',
  'Discard File': '丢弃文件',
  'Expire File': '使文件过期',
  'Platform Service': '平台服务',
  'Platform User': '平台用户',
  'Retention Elapsed': '留存期限到期',
  'Referenced': '已引用',
  'Discarded': '已丢弃',
  'Expired': '已过期',
  'File Download Authorized': '文件下载已授权',
  'Authorize Available File Download': '授权可用文件下载',
  'Reject Expired File Download': '拒绝过期文件下载',
  'Discard Unreferenced File': '丢弃未引用文件',
  'Reject Discard After Consumption': '拒绝消费后丢弃',
  'Expire Uploaded File': '使已上传文件过期',
  'Reject Consumption After Expiration': '拒绝过期后消费',
  'Uploaded File Is Persisted And Recorded': '已上传文件已持久化并记录',
  'Upload File For Model Import': '为模型导入上传文件',
  'Referenced File Is Protected From User Discard': '已引用文件禁止用户丢弃',
  'Mark File Referenced By Model Import': '模型导入时标记文件已引用',
  'Only Available Stored Files Can Be Downloaded': '仅可下载可用的已存储文件',
  'Only Unreferenced Files Can Be Discarded': '仅可丢弃未引用文件',
  'Expired File Cannot Be Referenced': '过期文件不可被引用',
  'File is not available for download': '文件不可用于下载',
  'File Is Referenced': '文件已被引用',
  'Register Runtime Infrastructure Package': '注册运行时基础设施包',
  'Runtime Infrastructure Package': '运行时基础设施包',
  'Runtime Infrastructure Package Registered': '运行时基础设施包已注册',
  'Runtime Infrastructure Planned': '运行时基础设施已计划',
  'Runtime Infrastructure Prepared': '运行时基础设施已准备',
  'Runtime Infrastructure Verified': '运行时基础设施已验证',
  'Runtime Infrastructure Verification Failed': '运行时基础设施验证失败',
  'Runtime Infrastructure Verification Retry Succeeded': '运行时基础设施验证重试成功',
  'Runtime Infrastructure Verification Retry Failed': '运行时基础设施验证重试失败',
  'Runtime Agent Installation Succeeded': '运行时代理安装成功',
  'Runtime Agent Installation Failed': '运行时代理安装失败',
  'Runtime Agent Deployment Retry Succeeded': '运行时代理部署重试成功',
  'Runtime Agent Deployment Retry Failed': '运行时代理部署重试失败',
  'Runtime Connection Established': '运行时连接已建立',
  'Current Recommended Feature Schema Catalog': '当前推荐特征模式目录',
  'Current Recommended Feature Schema Version Marked': '当前推荐特征模式版本已标记',
  'Feature Schema Defined': '特征模式已定义',
  'Feature Schema Published': '特征模式已发布',
  'Feature Schema Deprecated': '特征模式已弃用',
  'Feature Schema Retired': '特征模式已停用',
  'Feature Schema Version Superseded': '特征模式版本已取代',
  'Dataset Metadata Reported': '数据集元数据已上报',
  'Runtime Dataset Metadata': '运行时数据集元数据',
  'Dataset Metadata Reprofiled': '数据集元数据已重新画像',
  'Agent Dataset Profiling Failed': '代理数据集画像失败',
  'Agent Dataset Reprofiling Failed': '代理数据集重新画像失败',
  'Federated Model Artifact Registered': '联邦模型构件已注册',
  'Global Model Updated': '全局模型已更新',
  'Runtime Engine Profile': '运行时引擎配置',
  'Runtime Engine Profile Catalog': '运行时引擎配置目录',
  'Runtime Engine Profile Registered': '运行时引擎配置已注册',
  'Training Run Configuration Defined': '训练运行配置已定义',
  'Training Run Configuration Updated': '训练运行配置已更新',
  'Training Run Configuration Locked': '训练运行配置已锁定',
  'Training Job Created': '训练作业已创建',
  'Training Job Submitted': '训练作业已提交',
  'Training Job Paused': '训练作业已暂停',
  'Training Job Resumed': '训练作业已恢复',
  'Training Job Canceled': '训练作业已取消',
  'Training Job Completed': '训练作业已完成',
  'Training Participant Eligibility': '训练参与方资格',
  'Participant Execution Plan': '参与方执行计划',
  'Plan Generated': '计划已生成',
  'Plan Dispatched': '计划已下发',
  'Plan Received': '计划已接收',
  'Plan Accepted': '计划已接受',
  'Plan Rejected': '计划已拒绝',
  'Collecting Updates': '正在收集更新',
  'Evaluating Global Model': '正在评估全局模型',
  'Training Round Completed': '训练轮次已完成',
  'Training Round Failed': '训练轮次失败',
  'Training Round Participants Selected': '训练轮次参与方已选择',
  'Training Round Participant Selection Failed': '训练轮次参与方选择失败',
  'Training Round Started': '训练轮次已开始',
  'Training Round Start Failed': '训练轮次启动失败',
  'Participant Execution Plan Generated': '参与方执行计划已生成',
  'Participant Execution Plan Dispatched': '参与方执行计划已下发',
  'Round Execution Started': '轮次执行已开始',
  'Round Execution Start Failed': '轮次执行启动失败',
  'Round Execution Start Retry Started': '轮次执行启动重试已开始',
  'Round Execution Start Retry Failed': '轮次执行启动重试失败',
  'Round Execution Completed': '轮次执行已完成',
  'Round Execution Failed': '轮次执行失败',
  'Round Execution Runtime Retry Started': '轮次执行运行时重试已开始',
  'Round Execution Runtime Retry Failed': '轮次执行运行时重试失败',
  'Model Update Submission Received': '模型更新提交已接收',
  'Model Update Submission Accepted': '模型更新提交已接受',
  'Model Update Submission Rejected': '模型更新提交已拒绝',
  'Global Model Evaluation Submitted': '全局模型评估已提交',
  'Model Catalog': '模型目录',
  'Model': '模型',
  'Model repository owns model identity, versioning, lineage, and pullable artifact references used by training orchestration and runtime agents. FileUpload owns the underlying bytes and storage adapter; ModelArtifact owns their model-specific lifecycle and metadata.': '模型仓库负责模型身份、版本、血缘关系以及训练编排和运行时代理使用的可拉取构件引用。文件上传负责底层字节内容和存储适配器；模型构件负责模型相关生命周期和元数据。',
  'model repository owns model identity, versioning, lineage, and pullable artifact references used by training orchestration and runtime agents. FileUpload owns the underlying bytes and storage adapter; ModelArtifact owns their model-specific lifecycle and metadata.': '模型仓库负责模型身份、版本、血缘关系以及训练编排和运行时代理使用的可拉取构件引用。文件上传负责底层字节内容和存储适配器；模型构件负责模型相关生命周期和元数据。',
  'Model Candidate Registered': '候选模型已注册',
  'Model Evaluation Package Recorded': '模型评估包已记录',
  'Model Approved': '模型已批准',
  'Model Promoted To Production': '模型已提升为生产版本',
  'Model Rolled Back': '模型已回滚',
  'Model Retired': '模型已退役',
  'Runtime Node Inventory': '运行时节点清单',
  'Runtime Node Inventory View': '运行时节点清单视图',
  'Runtime Node Inventory Reported': '运行时节点清单已上报',
  'Runtime Node Resource Telemetry': '运行时节点资源遥测',
  'Runtime Node Resource Pressure': '运行时节点资源压力',
  'Runtime Node Resource Pressure Detected': '运行时节点资源压力已检测',
  'Runtime Node Capacity': '运行时节点容量',
  'Runtime Node Capacity Changed': '运行时节点容量已变化',
  'Runtime Health Dashboard': '运行时健康仪表盘',
  'Node Runtime Health': '节点运行时健康',
  'Node': '节点',
  'Training Alert Catalog': '训练告警目录',
  'Training Alert Raised': '训练告警已产生',
  'Training Alert Acknowledged': '训练告警已确认',
  'Training Alert Resolved': '训练告警已解决',
  'Audit Record': '审计记录',
  'Audit Record Log': '审计记录日志',
  'Audit Trail Appended': '审计轨迹已追加',
  'Runtime Identity Activated': '运行时身份已激活',
  'Runtime Identity Revoked': '运行时身份已撤销',
  'Runtime Agent Offline Detected': '运行时代理离线已探测',
  'Runtime Agent Recovered': '运行时代理已恢复',
  'Runtime Capabilities Detected': '运行时能力已探测',
  'Runtime Capability Catalog': '运行时能力目录',
  'Secure Aggregation Session Catalog': '安全聚合会话目录',
  'Secure Aggregation Session Created': '安全聚合会话已创建',
  'Secure Aggregation Participants Selected': '安全聚合参与方已选择',
  'Secure Aggregation Requested': '安全聚合已请求',
  'Homomorphic Encryption Context Prepared': '同态加密上下文已准备',
  'Encrypted Model Update Received': '加密模型更新已接收',
  'Secure Aggregation Completed': '安全聚合已完成',
  'Secure Aggregation Failed': '安全聚合失败',
  'Agent Organization Directory': '代理组织目录',
  'Agent Feature Schema Catalog': '代理特征模式目录',
  'Agent Dictionary Value Catalog': '代理字典值目录',
  'Agent Runtime Identity Catalog': '代理运行时身份目录',
  'Runtime Dataset Binding': '运行时数据集绑定',
  'Runtime Dataset Binding Catalog': '运行时数据集绑定目录',
  'Runtime Dataset Binding Configured': '运行时数据集绑定已配置',
  'Agent Dataset Access Validation': '代理数据集访问校验',
  'Agent Dataset Access Validation Catalog': '代理数据集访问校验目录',
  'Agent Dataset Access Validated': '代理数据集访问已验证',
  'Agent Dataset Access Validation Failed': '代理数据集访问校验失败',
  'Agent Dataset Access Revalidated': '代理数据集访问已重新验证',
  'Agent Dataset Access Revalidation Failed': '代理数据集访问重新验证失败',
  'Dataset Declared': '数据集已声明',
  'Dataset Contract Validation Failed': '数据集合约校验失败',
  'Dataset Contract Revalidated': '数据集合约已重新验证',
  'Dataset Contract Revalidation Failed': '数据集合约重新验证失败',
  'Agent Dataset Metadata Reported': '代理数据集元数据已上报',
  'Agent Dataset Reprofiled': '代理数据集已重新画像',
  'Round Execution': '轮次执行',
  'Round Execution Catalog': '轮次执行目录',
  'Execution Plan Received': '执行计划已接收',
  'Execution Plan Accepted': '执行计划已接受',
  'Execution Plan Rejected': '执行计划已拒绝',
  'Runtime Engine Job Observed': '运行时引擎作业已观测',
  'Agent Local Model Update Submitted': '代理本地模型更新已提交',
  'Runtime Engine Job Released': '运行时引擎作业已释放',
  'Runtime Engine Job Release Failed Or Skipped': '运行时引擎作业释放失败或跳过',
  'Runtime Engine Job Release Failed Or Skipped After Start Failure': '启动失败后运行时引擎作业释放失败或跳过',
  'Runtime Engine Job Release Failed Or Skipped After Retry': '重试后运行时引擎作业释放失败或跳过',
  'Runtime Engine Job Release Failed Or Skipped After Runtime Retry': '运行时重试后运行时引擎作业释放失败或跳过',
  'Runtime Agent Lifecycle': '运行时代理生命周期',
  'Runtime Agent Lifecycle Catalog': '运行时代理生命周期目录',
  'Runtime Agent Bootstrap Configuration Loaded': '运行时代理启动配置已加载',
  'Runtime Agent Bootstrap Configuration Load Failed': '运行时代理启动配置加载失败',
  'Runtime Agent Started': '运行时代理已启动',
  'Runtime Instance Self Check Passed': '运行时实例自检通过',
  'Agent Runtime Infrastructure Connection': '代理运行时基础设施连接',
  'Agent Runtime Infrastructure Connection Catalog': '代理运行时基础设施连接目录',
  'Agent Runtime Connection Established': '代理运行时连接已建立',
  'Agent Runtime Connection Report Failed': '代理运行时连接上报失败',
  'Agent Runtime Telemetry': '代理运行时遥测',
  'Agent Runtime Node Inventory': '代理运行时节点清单',
  'Agent Runtime Node Inventory Catalog': '代理运行时节点清单目录',
  'Agent Runtime Node Inventory Reported': '代理运行时节点清单已上报',
  'Agent Runtime Node Resource Telemetry': '代理运行时节点资源遥测',
  'Identity Access Management': '身份访问管理',
  'Role Permission Grant': '角色权限授权',
  'User Role Assignment': '用户角色分配',
  'Service Account Api Token': '服务账号接口令牌',
  'User Account Login Password Generated': '用户账号登录密码已生成',
  'User Account Deactivated': '用户账号已停用',
  'Role Registered': '角色已注册',
  'Permission Registered': '权限已注册',
  'Role Assigned To User': '角色已分配给用户',
  'Permission Granted To Role': '权限已授予角色',
  'Service Account Api Token Issued': '服务账号接口令牌已颁发',
  'State': '状态',
  'Federation Learning Support': '联邦学习支撑服务',
  'Federation Learning Runtime Agent': '联邦学习运行时代理',
  'Learning Support': '学习支撑服务',
  'Learning Runtime Agent': '学习运行时代理',
  'Bind User Account To Organization': '绑定用户账号到组织',
  'Reject Duplicate User Organization Binding': '拒绝重复的用户组织绑定',
  'User Organization Binding Unique': '用户组织绑定唯一',
  'User account is already bound to this organization': '用户账号已绑定到该组织',
  'Invitation Revoked': '邀请已撤销',
  'Metadata Reported': '元数据已上报',
  'Record Runtime Dataset Reprofiled Metadata': '记录运行时数据集重新画像元数据',
  'Record Dataset Metadata When Agent Reprofiled': '代理重新画像时记录数据集元数据',
  'Download Model Artifact': '下载模型构件',
  'Register Federated Model Artifact': '注册联邦模型构件',
  'Register Federated Model When Global Model Updated': '全局模型更新时注册联邦模型构件',
  'Model Artifact Download Authorized': '模型构件下载已授权',
  'Authorize Registered Model Artifact Download': '授权已注册模型构件下载',
  'Reject Missing Model Artifact Content': '拒绝缺少模型构件内容',
  'Registered Model Artifact Resolves To Stored File Download': '已注册模型构件解析到已存储文件下载',
  'Model artifact content is not available for download': '模型构件内容不可用于下载',
  'model artifact content is not available for download': '模型构件内容不可用于下载',
  'Register Aggregated Round Model': '注册聚合轮次模型',
  'Federated Round Model Is Registered From Managed Storage': '联邦轮次模型从托管存储注册',
  'Register Runtime Engine Profile': '注册运行时引擎配置',
  'Register Py Torch Vision Runtime Engine Profile': '注册 PyTorch Vision 运行时引擎配置',
  'Define Training Run Configuration': '定义训练运行配置',
  'Update Training Run Configuration': '更新训练运行配置',
  'Lock Training Run Configuration': '锁定训练运行配置',
  'Create Training Job': '创建训练作业',
  'Pause Training Job': '暂停训练作业',
  'Resume Training Job': '恢复训练作业',
  'Schedule Next Training Round': '调度下一训练轮次',
  'Complete Job When Training Stop Criteria Met': '达到训练停止条件时完成训练任务',
  'Define Runnable Configuration': '定义可运行配置',
  'Update Draft Runnable Configuration': '更新草稿态可运行配置',
  'Reject Empty Round Budget': '拒绝空轮次预算',
  'Reject Update After Configuration Locked': '拒绝配置锁定后更新',
  'Create Training Job With Runnable Configuration': '基于可运行配置创建训练作业',
  'Reject Training Job Without Runnable Configuration': '拒绝缺少可运行配置的训练作业',
  'Reject Unknown Training Job': '拒绝未知训练作业',
  'Select Training Round Participants': '选择训练轮次参与方',
  'Retry Training Round Participant Selection': '重试训练轮次参与方选择',
  'Select Initial Round Participants When Training Submitted': '训练提交后选择初始轮次参与方',
  'Training Round Participant Selection Retry Requested': '训练轮次参与方选择重试已请求',
  'Retry Participant Selection': '重试参与方选择',
  'Select Participants When Runtime Pool Reaches Quorum': '运行时池达到法定数量时选择参与方',
  'Reject Participant Snapshot Below Quorum': '拒绝低于法定数量的参与方快照',
  'Reject Selection Without Joined Federation Members': '拒绝没有已加入联邦成员的选择',
  'Reject Selection Without Compatible Dataset Evidence': '拒绝缺少兼容数据集证据的选择',
  'Verify Runtime Infrastructure': '验证运行时基础设施',
  'Deploy Runtime Agent': '部署运行时代理',
  'Retry Runtime Infrastructure Verification': '重试运行时基础设施验证',
  'Retry Runtime Agent Deployment': '重试运行时代理部署',
  'Runtime Infrastructure Verification Passed': '运行时基础设施验证通过',
  'Runtime Agent Deployment Succeeded': '运行时代理部署成功',
  'Runtime Agent Deployment Failed': '运行时代理部署失败',
  'Runtime Infrastructure Before Verification': '验证前的运行时基础设施',
  'Runtime Infrastructure Verification Outcome': '运行时基础设施验证结果',
  'Runtime Infrastructure Verification Retry Outcome': '运行时基础设施验证重试结果',
  'Runtime Agent Deployment Outcome': '运行时代理部署结果',
  'Runtime Agent Deployment Retry Outcome': '运行时代理部署重试结果',
  'Reject Runtime Infrastructure Verification Retry While Verified': '拒绝已验证状态下重试运行时基础设施验证',
  'Reject Runtime Agent Deployment Retry While Connected': '拒绝已连接状态下重试运行时代理部署',
  'Runtime Infrastructure Verification Retry Not Allowed': '运行时基础设施验证重试不允许',
  'Runtime Agent Deployment Retry Not Allowed': '运行时代理部署重试不允许',
  'Confirm Runtime Infrastructure Prepared': '确认运行时基础设施已准备',
  'Detect Runtime Capabilities': '探测运行时能力',
  'Create Secure Aggregation Session': '创建安全聚合会话',
  'Create Session When Aggregation Requested': '聚合请求后创建会话',
  'Select Secure Aggregation Participants': '选择安全聚合参与方',
  'Select Participants When Session Created': '会话创建后选择参与方',
  'Prepare Homomorphic Encryption Context': '准备同态加密上下文',
  'Record Encrypted Model Update': '记录加密模型更新',
  'Complete Homomorphic Aggregation Session': '完成同态聚合会话',
  'Encryption Context Prepared': '加密上下文已准备',
  'Dataset Contract Validation Completed': '数据集合约校验已完成',
  'Contract Validation Completed': '合约校验已完成',
  'Dataset Contract Validation Succeeded': '数据集合约校验成功',
  'Dataset Contract Validation Outcome': '数据集合约校验结果',
  'Dataset Contract Validation Retry Outcome': '数据集合约校验重试结果',
  'Retry Dataset Contract Validation': '重试数据集合约校验',
  'Receive Participant Execution Plan': '接收参与方执行计划',
  'Receive Execution Plan When Dispatched': '执行计划下发后接收',
  'Accept Execution Plan': '接受执行计划',
  'Accept Execution Plan When Execution Plan Received': '执行计划接收后接受',
  'Start Round Execution': '启动轮次执行',
  'Start Round Execution When Plan Accepted': '计划接受后启动轮次执行',
  'Retry Round Execution After Start Failure': '启动失败后重试轮次执行',
  'Retry Round Execution After Runtime Failure': '运行时失败后重试轮次执行',
  'Submit Agent Local Model Update': '提交代理本地模型更新',
  'Submit Local Model Update When Round Execution Completed': '轮次执行完成后提交本地模型更新',
  'Release Runtime Engine Job After Completion': '完成后释放运行时引擎作业',
  'Release Runtime Engine Job After Failure': '失败后释放运行时引擎作业',
  'Release Runtime Engine Job After Start Failure': '启动失败后释放运行时引擎作业',
  'Release Runtime Engine Job After Retry Failure': '重试失败后释放运行时引擎作业',
  'Release Runtime Engine Job After Runtime Retry Failure': '运行时重试失败后释放运行时引擎作业',
  'Release Runtime Engine Job When Local Model Update Submitted': '本地模型更新提交后释放运行时引擎作业',
  'Release Runtime Engine Job When Round Execution Failed': '轮次执行失败后释放运行时引擎作业',
  'Release Runtime Engine Job When Start Failure': '启动失败时释放运行时引擎作业',
  'Release Runtime Engine Job When Start Retry Failure': '启动重试失败时释放运行时引擎作业',
  'Release Runtime Engine Job When Runtime Retry Failure': '运行时重试失败时释放运行时引擎作业',
  'Load Runtime Agent Bootstrap Configuration': '加载运行时代理启动配置',
  'Reject Missing Runtime Agent Bootstrap Configuration': '拒绝缺少运行时代理启动配置',
  'Report Runtime Agent Started': '上报运行时代理已启动',
  'Report Runtime Instance Self Check Passed': '上报运行时实例自检通过',
  'Report Runtime Instance Connected': '上报运行时实例已连接',
  'Report Runtime Instance Connected After Self Check': '自检后上报运行时实例已连接',
  'Report Runtime Instance Connection Failed': '上报运行时实例连接失败',
  'Report Agent Runtime Node Inventory': '上报代理运行时节点清单',
  'Report Node Inventory When Inventory Changed': '节点清单变化时上报清单',
  'Snapshot Required For Dataset Declaration': '数据集声明需要快照',
  'Declare Dataset With Feature Schema Snapshot': '携带特征模式快照声明数据集',
  'Feature Schema Load Failed': '特征模式加载失败',
  'Runtime Dataset Binding Unique Per Runtime Dataset': '运行时数据集绑定在每个运行时数据集内唯一',
  'Reject Duplicate Runtime Dataset Binding': '拒绝重复运行时数据集绑定',
  'Runtime Dataset Binding Already Exists': '运行时数据集绑定已存在',
  'Agent Dataset Access Revalidation Outcome': '代理数据集访问重新验证结果',
  'Agent Dataset Profiling Outcome': '代理数据集画像结果',
  'Agent Dataset Reprofiling Outcome': '代理数据集重新画像结果',
  'Revalidate Agent Dataset Access': '重新验证代理数据集访问',
  'Reprofile Agent Dataset': '重新画像代理数据集',
  'Audit Critical Training Events': '审计关键训练事件',
  'Append Audit Trail': '追加审计轨迹',
  'Raise Alert On Node Resource Pressure': '节点资源压力时产生告警',
  'Lock Configuration When Training Submitted': '训练提交时锁定配置',
  'Select Next Round Participants When Training Should Continue': '训练应继续时选择下一轮参与方',
  'Start Round When Participants Selected Without Secure Aggregation': '无安全聚合且参与方已选择时启动轮次',
  'Start Round When Encryption Context Prepared': '加密上下文准备后启动轮次',
  'Start Round With Selected Runtime Quorum': '使用满足法定数量的已选运行时启动轮次',
  'Record Failed Round Start Below Quorum': '记录低于法定数量导致的轮次启动失败',
  'Generate Participant Execution Plan': '生成参与方执行计划',
  'Generate Plan For Selected Runtime': '为已选运行时生成计划',
  'Generate Initial Round Plan With Initial Model Snapshot': '使用初始模型快照生成首轮计划',
  'Generate Later Round Plan With Aggregated Model Snapshot': '使用聚合模型快照生成后续轮次计划',
  'Dispatch Participant Execution Plan': '下发参与方执行计划',
  'Dispatch Plan When Generated': '计划生成后下发',
  'Submit Model Update Submission': '提交模型更新',
  'Evaluate Model Update Submission': '评估模型更新',
  'Evaluate Model Update When Received': '接收后评估模型更新',
  'Accept Normal Update': '接受正常更新',
  'Reject Anomalous Update': '拒绝异常更新',
  'Anomalous Model Update Rejected': '异常模型更新已拒绝',
  'Poisoned Local Updates Are Excluded': '排除有毒本地更新',
  'Aggregate Plain Model Updates': '聚合明文模型更新',
  'Aggregate Plain Updates When Quorum Reached': '达到法定数量后聚合明文更新',
  'Plain Model Aggregation Completed': '明文模型聚合已完成',
  'Aggregate Accepted Plain Updates': '聚合已接受的明文更新',
  'Plain Model Updates Are Aggregated After Quorum': '达到法定数量后聚合明文模型更新',
  'Request Secure Aggregation': '请求安全聚合',
  'Request Aggregation When Participants Selected': '参与方选择后请求聚合',
  'Complete Model Aggregation': '完成模型聚合',
  'Complete Plain Aggregation When Completed': '明文聚合完成后完成模型聚合',
  'Submit Global Model Evaluation': '提交全局模型评估',
  'Submit Global Model Evaluation When Global Model Updated': '全局模型更新后提交全局模型评估',
  'Finish Round After Global Model Evaluated': '全局模型评估后完成轮次',
  'Training Round Completion Requires Global Evaluation': '训练轮次完成需要全局评估',
  'Reject Round Completion Without Global Evaluation': '拒绝缺少全局评估的轮次完成',
  'Global Model Evaluation Required': '需要全局模型评估',
  'Training Configuration Must Be Runnable': '训练配置必须可运行',
  'Positive Round Budget Required': '轮次预算必须为正',
  'Draft Training Configuration Can Be Updated': '草稿态训练配置可更新',
  'Training Job Requires Runnable Configuration And Runtime Access': '训练作业需要可运行配置和运行时访问',
  'Runnable Training Configuration Required': '需要可运行训练配置',
  'Only Created Training Jobs Can Be Submitted': '仅已创建训练作业可提交',
  'Training Job Required': '需要训练作业',
  'Training Round Participants Are Snapshotted Per Round': '训练轮次参与方按轮次固化快照',
  'Retry Participant Selection After Readiness Changes': '就绪状态变化后重试参与方选择',
  'Participant Execution Plan Carries Base Model Artifact Snapshot': '参与方执行计划携带基础模型构件快照',
  'Register Candidate Model': '注册候选模型',
  'Register Final Model When Training Job Completed': '训练作业完成后注册最终模型',
  'Candidate': '候选',
  'Record Model Evaluation Package': '记录模型评估包',
  'Evaluation Packaged': '评估包已记录',
  'Record Complete Evaluation Package': '记录完整评估包',
  'Approve Model': '批准模型',
  'Approve Evaluation Packaged Model': '批准已记录评估包的模型',
  'Reject Candidate Without Evaluation Package': '拒绝缺少评估包的候选模型',
  'Model Approval Requires Evaluation Package': '模型批准需要评估包',
  'Only Evaluation Packaged Models Can Be Approved': '仅已记录评估包的模型可批准',
  'Model Evaluation Package Required': '需要模型评估包',
  'Promote Model To Production': '提升模型为生产版本',
  'Promote Approved Model': '提升已批准模型',
  'Reject Unapproved Model Promotion': '拒绝未批准模型提升',
  'Only Approved Models Can Be Promoted To Production': '仅已批准模型可提升为生产版本',
  'Model Approval Required': '需要模型批准',
  'Rollback Model': '回滚模型',
  'Retire Model': '退役模型',
  'Production': '生产版本',
  'Rolled Back': '已回滚',
  'Capacity Changed': '容量已变化',
  'Detect Runtime Node Resource Pressure': '检测运行时节点资源压力',
  'Detect Resource Pressure From Telemetry': '从遥测检测资源压力',
  'Pressure Detected': '压力已检测',
  'Detect Runtime Node Capacity Change': '检测运行时节点容量变化',
  'Detect Capacity Change From Resource Telemetry': '从资源遥测检测容量变化',
  'Observe Runtime Engine Job': '观测运行时引擎作业',
  'Complete Round Execution': '完成轮次执行',
  'Fail Round Execution': '标记轮次执行失败',
  'Execution Plan Local Acceptance Outcome': '执行计划本地接受结果',
  'Round Execution Runtime Engine Start Outcome': '轮次执行运行时引擎启动结果',
  'Runtime Engine Job Observation Outcome': '运行时引擎作业观测结果',
  'Runtime Agent Instance Self Check': '运行时代理实例自检',
  'Runtime Agent Platform Connection Reporting Outcome': '运行时代理平台连接上报结果',
  'Runtime Engine Released': '运行时引擎已释放',
  'Runtime Engine Release Handled': '运行时引擎释放已处理',
  'Bootstrap Loaded': '启动配置已加载',
  'Ready': '就绪',
  'Started': '已启动',
  'Register User Account': '注册用户账号',
  'Deactivate User Account': '停用用户账号',
  'Generate User Account Login Password': '生成用户账号登录密码',
  'Register Role': '注册角色',
  'Register Permission': '注册权限',
  'Grant Permission To Role': '授予角色权限',
  'Permission Revoked From Role': '角色权限已撤销',
  'Assign Role To User': '分配角色给用户',
  'Role Unassigned From User': '用户角色已取消分配',
  'Issue Service Account Api Token': '签发服务账号接口令牌',
  'Identity Administrator': '身份管理员',
  'Super Administrator': '超级管理员',
  'Reject Duplicate Username': '拒绝重复用户名',
  'Username Unique': '用户名唯一',
  'Username already exists': '用户名已存在',
  'Super Administrator Issues Token For Service Account': '超级管理员为服务账号签发令牌',
  'Issued Event Stores Digest Instead Of Plaintext Token': '已颁发事件存储摘要而非明文令牌',
  'Issued Token Captures Current Permissions': '已颁发令牌捕获当前权限',
  'Only Super Administrator Can Issue Service Account Api Token': '仅超级管理员可签发服务账号接口令牌',
  'Service Account Api Token Plaintext Returned Once': '服务账号接口令牌明文仅返回一次',
  'Service Account Api Token Captures Permission Snapshot': '服务账号接口令牌捕获权限快照',
  'Creation Screen': '创建页面',
  'Submission Screen': '提交页面',
  'Operations Screen': '操作页面',
  'Approval Screen': '审批页面',
  'Release Screen': '发布页面',
  'When': '当',
  'Before': '前',
  'After': '后',
  'Without': '缺少',
  'With': '带有',
  'From': '来自',
  'To': '到',
  'Only': '仅',
  'Can Be': '可',
  'Must Be': '必须',
  'Required': '必需',
  'Requires': '需要',
  'Select': '选择',
  'Start': '启动',
  'Generate': '生成',
  'Dispatch': '下发',
  'Submit': '提交',
  'Evaluate': '评估',
  'Aggregate': '聚合',
  'Complete': '完成',
  'Finish': '结束',
  'Reject': '拒绝',
  'Retry': '重试',
  'Report': '上报',
  'Load': '加载',
  'Observe': '观测',
  'Release': '释放',
  'Update': '更新',
  'Snapshot': '快照',
  'Outcome': '结果',
  'Configuration': '配置',
  'Runnable': '可运行',
  'Quorum': '法定数量',
  'Operator': '操作员',
  'Plain': '明文',
  'Updates': '更新',
  'Evaluation': '评估',
  'Approval': '批准',
  'Promotion': '提升',
  'Password': '密码',
  'Username': '用户名',
  'Issues': '签发',
  'Token': '令牌',
  'Digest': '摘要',
  'Plaintext': '明文',
  'Captures': '捕获',
  'Current': '当前',
  'Next Round Participants': '下一轮参与方',
  'Training Should Continue': '训练应继续',
  'Initial Round Participants': '初始轮次参与方',
  'Runtime Pool Reaches': '运行时池达到',
  'Selected Runtime': '已选运行时',
  'Pullable Engine Image': '可拉取引擎镜像',
  'Selects Pullable Engine Image': '选择可拉取引擎镜像',
  'Participants': '参与方',
  'Session Completed': '会话已完成',
  'Session': '会话',
  'Evaluated': '已评估',
  'Running': '运行中',
  'Fail Training Round': '标记训练轮次失败',
  'Fail Round': '标记轮次失败',
  'Fail': '标记失败',
  'Training Operator': '训练操作员',
  'Catalogs': '目录',
  'Training Submitted': '训练已提交',
  'Training': '训练',
  'Round': '轮次',
  'Runtime': '运行时',
  'Engine': '引擎',
  'Next': '下一',
  'Initial': '初始',
  'Dashboard': '仪表盘',
  'Catalog': '目录',
  'Package': '包',
  'Profile': '配置',
  'Metadata': '元数据',
  'Context': '上下文',
  'Agent': '代理',
  'Access': '访问',
  'Binding': '绑定',
  'Role': '角色',
  'Permission': '权限',
  'Offline': '离线',
  'Recovered': '已恢复',
  'Declared': '已声明',
  'Participants Selected': '参与方已选择',
  'Received': '已接收',
  'Accepted': '已接受',
  'Released': '已释放',
  'Lifecycle': '生命周期',
  'Bootstrap Configuration': '启动配置',
  'Self Check Passed': '自检通过',
  'Connection': '连接',
  'Telemetry': '遥测',
  'Registered': '已注册',
  'Prepared': '已准备',
  'Verified': '已验证',
  'Activated': '已激活',
  'Revoked': '已撤销',
  'Submitted': '已提交',
  'Created': '已创建',
  'Defined': '已定义',
  'Updated': '已更新',
  'Locked': '已锁定',
  'Paused': '已暂停',
  'Resumed': '已恢复',
  'Canceled': '已取消',
  'Completed': '已完成',
  'Failed': '失败',
  'Detected': '已探测',
  'Validated': '已验证',
  'Approved': '已批准',
  'Rejected': '已拒绝',
  'Requested': '已请求',
  'Checked': '已检查',
  'Draft': '草稿',
  'Text': '文本',
  'display': '展示'
};

const generatedBodies = new Map<DocumentationKind, string>();
let modelTranslations: ModelTranslations = {};
let localizationDictionary: Array<[string, string]> = [];

function parseOptions(args: string[]): PackOptions {
  const argValue = (name: string): string | undefined => {
    const prefix = `--${name}=`;
    return args.find((arg) => arg.startsWith(prefix))?.slice(prefix.length);
  };
  const modeArg = argValue('mode');
  const mode: OutputMode = modeArg === 'standalone' || modeArg === 'merged' || modeArg === 'all'
    ? modeArg
    : 'all';
  return {
    sourceModelPath: resolve(federationLearningRoot, argValue('model') ?? '.medol/source.medol'),
    translationPath: resolve(workspaceRoot, argValue('translations') ?? 'medol/examples/fl/model-translations.zh-CN.json'),
    outputDirectory: resolve(federationLearningRoot, argValue('out') ?? 'docs/generated'),
    mode,
    enhanceWithModel: args.includes('--enhance-with-model'),
    generatedAt: argValue('generated-at') ?? new Date().toISOString()
  };
}

function prdSections(body: string, mode: 'standalone' | 'merged'): string[] {
  return [
    `# ${projectTitle} 需求规格说明`,
    '',
    ...(mode === 'standalone'
      ? commonStandaloneScope('需求内容')
      : [
          ...mergedHeader('需求规格说明'),
          '## 1 范围',
          '',
          '### 1.1 标识',
          '',
          `本文档为平台级需求规格说明融合稿，当前补充${subsystemName}内容。`,
          '',
          '### 1.2 系统概述',
          '',
          systemOverviewText(),
          '',
          '### 1.3 文档概述',
          '',
          `平台公共背景、标准引用、签署页、术语总表等内容由平台级文档统一维护；本文保留“${subsystemName}”锚点，便于与其他子系统内容合并。`,
          '',
          '## 2 引用文档',
          '',
          '沿用平台级引用文档。'
        ]),
    '## 3 需求',
    '',
    '### 3.1 要求的状态和方式',
    '',
    `#### ${subsystemName}`,
    '',
    '联邦学习相关业务对象通过建模中定义的状态流转表达，包括组织、联邦、参与方、数据集、训练作业、训练轮次、模型、运行时代理和安全聚合会话等对象的生命周期状态。',
    '',
    '### 3.2 软件配置项总体需求',
    '',
    `#### ${subsystemName}`,
    '',
    demote(stripPrdReviewOnlySections(body), 2),
    '',
    '### 3.3 软件配置项功能需求',
    '',
    `#### ${subsystemName}`,
    '',
    '功能需求以 3.2 中的能力、命令、读模型、自动化处理器和验收矩阵为准，覆盖组织管理、联邦管理、数据集治理、模型仓库、训练编排、运行时治理、安全聚合和运行时代理操作。',
    '',
    '### 3.4 软件配置项性能需求',
    '',
    `#### ${subsystemName}`,
    '',
    '- 平台侧接口应满足组织、联邦、数据集、训练作业、模型构件等常用查询和命令处理的交互时延要求。',
    '- 训练编排应支持多参与方、多轮次任务状态更新和运行时回调，避免单个参与方异常阻塞整体状态追踪。',
    '- 运行时代理遥测、节点资源和训练进度属于持续上报数据，应与低频业务事件分开处理。',
    '',
    '### 3.5 软件配置项外部接口需求',
    '',
    `#### ${subsystemName}`,
    '',
    '外部接口包括平台前端、参与方前端、平台后端接口、运行时代理控制接口、运行时引擎接口、文件/对象存储接口、模型构件下载接口和安全聚合协议适配接口。',
    '',
    '### 3.6 软件配置项内部接口需求',
    '',
    `#### ${subsystemName}`,
    '',
    '内部接口包括命令处理器、事件处理器、读模型投影、自动化处理器、权限适配器、组织适配器、运行时调度适配器和文档/构件下载适配器之间的调用关系。',
    '',
    '### 3.7 软件配置项内部数据需求',
    '',
    `#### ${subsystemName}`,
    '',
    '内部数据包括命令载荷、领域事件、读模型、运行时遥测、审计记录、训练轮次状态、安全聚合会话状态、模型构件元数据和运行时数据集元数据。',
    '',
    '### 3.8 适应性需求',
    '',
    `#### ${subsystemName}`,
    '',
    '系统应适应平台侧集中部署、参与方运行时独立部署、参与方前端独立部署以及不同运行时基础设施形态。运行时代理应允许在参与方网络内独立鉴权和访问本地数据。',
    '',
    '### 3.9 安全性需求',
    '',
    `#### ${subsystemName}`,
    '',
    '安全性需求包括用户身份认证、角色权限、组织边界、运行时身份、服务账号令牌、审计追踪、运行时代理隔离、安全聚合和模型构件访问控制。',
    '',
    '### 3.10 保密性需求',
    '',
    `#### ${subsystemName}`,
    '',
    '参与方原始数据、运行时本地凭证、本地数据连接信息、运行时私钥和数据平面连接配置不得进入平台侧业务状态。平台仅保存训练协同所需的元数据、授权状态和审计记录。',
    '',
    '### 3.11 标准需求',
    '',
    `#### ${subsystemName}`,
    '',
    '应遵循平台级软件工程、信息安全、数据安全、审计追踪、接口管理和部署运维标准；联邦学习协议、安全聚合和模型管理相关标准可在项目级标准清单中统一维护。',
    '',
    '### 3.12 软件配置项环境需求',
    '',
    `#### ${subsystemName}`,
    '',
    '平台侧需要前端运行环境、后端服务运行环境、数据库/事件存储、文件或对象存储和容器化部署环境；参与方侧需要运行时代理、运行时引擎、数据集访问环境和本地鉴权环境。',
    '',
    '### 3.13 计算机资源需求',
    '',
    `#### ${subsystemName}`,
    '',
    '资源需求包括平台后端计算资源、训练调度资源、运行时代理所在节点资源、模型构件存储资源、训练数据本地访问资源、日志审计存储和遥测数据存储。',
    '',
    '### 3.14 软件质量因素',
    '',
    `#### ${subsystemName}`,
    '',
    '质量因素包括功能完整性、可靠性、可观测性、可维护性、可扩展性、安全性、隐私保护、部署适应性和故障恢复能力。',
    '',
    '### 3.15 设计和实现的约束',
    '',
    `#### ${subsystemName}`,
    '',
    '生成代码与手写扩展应保持边界清晰；参与方隐私数据不得由平台侧直接读取；运行时代理的用户鉴权和数据访问应由参与方独立控制。',
    '',
    '### 3.17 培训需求',
    '',
    `#### ${subsystemName}`,
    '',
    '培训对象包括平台管理员、联邦负责人、数据负责人、训练操作员、运行时节点操作员、安全审核员和参与方管理员。培训内容应覆盖联邦配置、数据集治理、训练发起、运行时接入、模型构件下载和故障处理。'
  ];
}

function softwareDesignSections(body: string, mode: 'standalone' | 'merged'): string[] {
  return [
    `# ${projectTitle} 软件设计说明`,
    '',
    ...(mode === 'standalone' ? commonStandaloneScope('软件设计内容') : [
      ...mergedHeader('软件设计说明'),
      '## 1 范围',
      '',
      `本文档为平台级软件设计说明融合稿，当前补充${subsystemName}的软件设计内容。`,
      '',
      '## 2 引用文档',
      '',
      '沿用平台级引用文档。'
    ]),
    '## 3 CSCI级设计决策',
    '',
    '### 3.1 输入/输出设计决策',
    '',
    `#### ${subsystemName}`,
    '',
    '联邦学习子系统的输入包括用户命令、运行时代理回调、训练配置、数据集元数据、模型构件元数据和遥测数据；输出包括领域事件、读模型、运行时任务、模型下载响应、审计记录和告警。',
    '',
    '##### 输入/输出数据流图',
    '',
    '数据流由前端或运行时代理进入平台服务，经命令处理、事件记录、读模型投影和自动化处理后，形成查询视图、运行时调度请求或审计记录。',
    '',
    '##### 输入/输出说明',
    '',
    '输入/输出字段以 MEDOL 模型中的命令、事件和读模型定义为准。',
    '',
    '##### 行为设计决策',
    '',
    '关键业务行为以事件溯源方式记录；高频遥测数据作为运维视图处理，不作为领域事件的唯一来源。',
    '',
    '## 4 CSCI体系结构设计',
    '',
    '### 4.1 逻辑结构及部署关系',
    '',
    `${subsystemName}由平台后端、平台 Console、参与方 Console、运行时代理、运行时引擎、数据库/事件存储和文件/对象存储组成。参与方侧运行时代理和前端可独立部署。`,
    '',
    '### 4.2 CSCI部件',
    '',
    `#### ${subsystemName}`,
    '',
    '主要部件包括领域命令处理、事件存储、读模型投影、运行时调度适配器、运行时代理 API、前端资源页面、权限适配器、审计记录和运维视图。',
    '',
    '### 4.3 执行方案',
    '',
    `#### ${subsystemName}`,
    '',
    '执行方案包括用户侧业务操作、平台侧命令处理、事件发布、读模型更新、训练轮次自动化推进、运行时代理回调和模型构件发布下载。',
    '',
    '### 4.4 性能设计',
    '',
    `#### ${subsystemName}`,
    '',
    '性能设计侧重读模型查询、训练状态更新、运行时遥测写入、模型构件下载和运行时回调处理的吞吐与隔离。',
    '',
    '## 5 CSCI详细设计',
    '',
    `### ${subsystemName}`,
    '',
    demote(replaceSoftwareQualitySection(body), 2),
    '',
    '## 6 需求可追踪性',
    '',
    '### 6.1 正向追溯',
    '',
    `${subsystemName}需求可追溯到 MEDOL 模型中的上下文、概念、命令、事件、读模型和自动化处理器。`,
    '',
    '### 6.2 逆向追溯',
    '',
    '命令、事件、读模型和页面入口可反向追溯到需求规格说明中的功能需求、数据需求、安全需求和部署约束。'
  ];
}

function databaseSections(body: string, mode: 'standalone' | 'merged'): string[] {
  return [
    `# ${projectTitle} 数据库设计说明`,
    '',
    ...(mode === 'standalone' ? commonStandaloneScope('数据模型和数据库设计内容') : [
      ...mergedHeader('数据库设计说明'),
      '## 1 范围',
      '',
      `本文档为平台级数据库设计说明融合稿，当前补充${subsystemName}的数据模型和数据库设计内容。`,
      '',
      '## 2 引用文档',
      '',
      '沿用平台级引用文档。'
    ]),
    '## 3 数据库级设计决策',
    '',
    `### ${subsystemName}`,
    '',
    '联邦学习子系统的数据设计以事件、业务状态、查询读模型和运行时反馈数据为核心。物理数据库产品、部署拓扑、备份恢复和安全策略建议在平台级数据库设计中统一描述。',
    '',
    '## 4 数据库详细设计',
    '',
    `### ${subsystemName}`,
    '',
    demote(body, 2),
    '',
    '## 5 用于数据库访问或操纵的软件单元的详细设计',
    '',
    `### ${subsystemName}`,
    '',
    '数据库访问单元与各业务上下文中的命令处理、事件处理、读模型更新和自动化处理器对应。',
    '',
    '## 6 需求可追踪性',
    '',
    '### 6.1 正向追溯',
    '',
    '需求到数据对象的追踪关系可通过需求规格说明中的能力、数据库设计中的读模型和事件来源共同建立。',
    '',
    '### 6.2 逆向追溯',
    '',
    '读模型、事件表、审计记录和运行时遥测视图可反向追溯到联邦学习子系统的业务需求和运维需求。',
    '',
    '## 7 注释',
    '',
    '本生成稿中的物理库表命名、索引和归档策略应在最终数据库实施设计中结合部署数据库产品确认。',
    '',
    '## 附录',
    '',
    '附录可补充数据库 ER 图、逻辑模型图、物理模型图和字段字典导出。'
  ];
}

function userManualSections(body: string, mode: 'standalone' | 'merged'): string[] {
  return [
    `# ${projectTitle} 用户操作手册`,
    '',
    ...(mode === 'standalone' ? commonStandaloneScope('用户角色、入口、操作流程、数据视图和异常处理建议') : [
      ...mergedHeader('用户操作手册'),
      '## 1 范围',
      '',
      `本文档为平台级用户操作手册融合稿，当前补充${subsystemName}的用户角色、入口、操作流程、数据视图和异常处理建议。`,
      '',
      '## 2 引用文档',
      '',
      '沿用平台级引用文档。'
    ]),
    '## 3 软件应用',
    '',
    '### 3.1 用户角色与应用',
    '',
    '联邦学习子系统面向平台管理员、联邦负责人、治理审核员、数据负责人、节点操作员、训练操作员、机器学习运维工程师、安全审核员、运行时代理和参与方管理员等角色。',
    '',
    '### 3.2 软件清单',
    '',
    '软件清单包括 Federation Learning Console、Federation Learning Participant Console、平台后端服务、运行时代理、运行时引擎和部署运维脚本。',
    '',
    '### 3.3 软件环境',
    '',
    '软件环境包括浏览器、后端服务运行环境、数据库、文件/对象存储、容器运行环境、K3s/Kubernetes 或主机进程运行环境。',
    '',
    '### 3.4 软件组织和操作概述',
    '',
    '用户先完成基础配置，再进行联邦创建、参与方接入、数据集治理、训练配置、训练提交、运行时监控和模型发布。',
    '',
    '### 3.5 意外事故及运行的备用状态和方式',
    '',
    '常见异常包括运行时离线、数据集访问失败、训练轮次失败、安全聚合失败、模型构件下载失败和权限不足。系统应提供重试、暂停、取消、告警和审计记录。',
    '',
    '### 3.6 保密性',
    '',
    '参与方原始数据、本地凭证和运行时私钥保留在参与方环境内，平台用户仅能访问授权范围内的元数据和训练状态。',
    '',
    '### 3.7 帮助和问题报告',
    '',
    '问题报告应包含用户、组织、联邦、训练作业、运行时身份、时间、错误信息和相关审计记录。',
    '',
    '## 4 软件入门',
    '',
    '### 4.1 软件的首次用户',
    '',
    '首次使用时建议先完成账号登录、组织绑定、权限分配、运行时基础设施准备和参与方运行时接入。',
    '',
    '### 4.2 启动过程',
    '',
    '启动顺序包括平台服务、前端应用、运行时代理、运行时引擎和参与方前端。启动后应检查健康状态和运行时连接状态。',
    '',
    '### 4.3 停止和挂起工作',
    '',
    '停止训练或挂起操作应通过训练作业暂停、取消、运行时任务释放和运行时下线流程完成。',
    '',
    '### 4.4 卸载',
    '',
    '卸载应先撤销运行时访问授权、停止运行时代理和运行时引擎、清理部署资源，再按平台级运维流程处理本地数据和日志。',
    '',
    '## 5 使用指南',
    '',
    `### 5.1 ${subsystemName}`,
    '',
    '#### 5.1.1 能力',
    '',
    demote(body, 3),
    '',
    '#### 5.1.2 约定',
    '',
    '页面操作约定、颜色约定、命名约定和权限提示沿用平台级用户操作手册；联邦学习子系统页面中的组织、联邦、运行时、数据集、训练作业、模型等对象名称应与建模术语保持一致。',
    '',
    '#### 5.1.3 处理规程',
    '',
    '典型处理规程包括组织准备、联邦创建、参与方接入、数据集声明、特征模式发布、训练配置、训练提交、运行时监控、安全聚合、模型评估、模型发布和模型构件下载。'
  ];
}

function installationSections(body: string, mode: 'standalone' | 'merged'): string[] {
  return [
    `# ${projectTitle} 安装部署手册`,
    '',
    ...(mode === 'merged' ? mergedHeader('安装部署手册') : []),
    '## 1 范围',
    '',
    '### 1.1 标识',
    '',
    `本文档补充${subsystemName}的安装部署内容，用于后续与平台级安装部署手册合并。`,
    '',
    '## 2 系统要求',
    '',
    '### 2.1 硬件要求',
    '',
    '平台侧需要可运行后端服务、前端静态资源、数据库、文件/对象存储和部署工具的服务器资源；参与方侧需要可运行运行时代理、运行时引擎和本地数据访问组件的计算资源。',
    '',
    '### 2.2 软件（操作系统）要求',
    '',
    '部署环境应支持 Java 后端、Node/Vite 前端构建、数据库或事件存储、Docker/K3s/Kubernetes、Nginx 或等效静态资源服务，以及运行时代理所需的 Python/训练运行环境。',
    '',
    '## 3 常用软件安装',
    '',
    '### 3.1 Jdk1.8安装',
    '',
    '联邦学习后端运行时的 Java 版本以项目实际构建配置为准；如平台级手册统一 JDK 安装，本节引用平台级安装步骤。',
    '',
    '### 3.2 Redis安装',
    '',
    '如部署方案启用缓存、会话或异步队列组件，Redis 安装与配置沿用平台级手册。',
    '',
    '### 3.3 Mysql安装',
    '',
    '数据库安装、账号、库表初始化和备份策略沿用平台级手册；联邦学习子系统的数据对象由数据库设计说明和迁移脚本约束。',
    '',
    '### 3.4 nginx安装及配置',
    '',
    'Nginx 或等效 Web 服务用于发布 Federation Learning Console 和 Federation Learning Participant Console 静态资源，也可作为平台侧接口代理入口。',
    '',
    '### 3.5 elasticsearch安装',
    '',
    '如平台级日志检索、审计检索或运行时遥测检索依赖 Elasticsearch，本节引用平台级安装步骤。',
    '',
    '### 3.6 Nacos安装',
    '',
    '如部署方案使用 Nacos 或等效配置中心，本节引用平台级安装步骤；运行时代理相关密钥和参与方本地配置不得直接暴露给平台侧普通用户。',
    '',
    '## 4 系统部署安装',
    '',
    `### ${subsystemName}`,
    '',
    demote(body, 2)
  ];
}

function testRecordSections(mode: 'standalone' | 'merged'): string[] {
  return [
    `# ${projectTitle} 测试记录`,
    '',
    ...(mode === 'merged' ? mergedHeader('测试记录') : []),
    '## 1 任务描述',
    '',
    `本文档用于记录${subsystemName}测试执行情况。生成稿仅提供记录结构和待填写项，实际测试结果应由测试执行过程产生。`,
    '',
    '## 2 目标',
    '',
    '验证联邦学习子系统是否满足需求规格说明、软件设计说明、数据库设计说明、用户操作手册和安装部署手册中的功能、接口、性能、安全和运维要求。',
    '',
    '## 3 环境类型',
    '',
    '| 环境项 | 记录内容 |',
    '| --- | --- |',
    '| 测试环境 | 待填写 |',
    '| 平台服务地址 | 待填写 |',
    '| 参与方运行时地址 | 待填写 |',
    '| 数据库/存储 | 待填写 |',
    '',
    '## 4 云类型',
    '',
    '| 云类型 | 记录内容 |',
    '| --- | --- |',
    '| 部署形态 | 待填写 |',
    '| Kubernetes/K3s | 待填写 |',
    '| 参与方本地环境 | 待填写 |',
    '',
    '## 5 功能测试用例设计',
    '',
    '功能测试用例应覆盖组织管理、联邦管理、运行时接入、数据集治理、模型仓库、训练编排、模型生命周期、运行时监控、运行时治理、安全聚合、运行时代理操作和身份权限管理。',
    '',
    '## 6 功能测试用例',
    '',
    '| 用例编号 | 测试项 | 前置条件 | 操作步骤 | 预期结果 | 实际结果 | 结论 |',
    '| --- | --- | --- | --- | --- | --- | --- |',
    '| FL-TR-001 | 训练作业提交 | 待填写 | 待填写 | 待填写 | 待执行 | 待填写 |',
    '| FL-RA-001 | 运行时代理接入 | 待填写 | 待填写 | 待填写 | 待执行 | 待填写 |',
    '| FL-DG-001 | 数据集契约校验 | 待填写 | 待填写 | 待填写 | 待执行 | 待填写 |',
    '',
    '## 7 测试结果',
    '',
    '### 7.1 测试结果汇总',
    '',
    '| 测试类别 | 用例总数 | 通过 | 失败 | 阻塞 | 未执行 |',
    '| --- | --- | --- | --- | --- | --- |',
    '| 功能测试 | 待填写 | 待填写 | 待填写 | 待填写 | 待填写 |',
    '| 接口测试 | 待填写 | 待填写 | 待填写 | 待填写 | 待填写 |',
    '| 安全测试 | 待填写 | 待填写 | 待填写 | 待填写 | 待填写 |',
    '',
    '### 7.2 缺陷统计与分析（适用于多轮测试）',
    '',
    '| 缺陷等级 | 数量 | 状态 | 备注 |',
    '| --- | --- | --- | --- |',
    '| 严重 | 待填写 | 待填写 | 待填写 |',
    '| 一般 | 待填写 | 待填写 | 待填写 |',
    '',
    '### 7.3 缺陷分析图',
    '',
    '缺陷等级分布图和缺陷趋势图由实际测试管理工具或统计结果生成后补充。',
    '',
    '### 7.4 测试结论',
    '',
    '测试结论应在测试执行完成后填写，不由生成器自动判定。'
  ];
}

function testOutlineSections(body: string, mode: 'standalone' | 'merged'): string[] {
  return [
    `# ${projectTitle} 测试大纲`,
    '',
    ...(mode === 'merged' ? mergedHeader('测试大纲') : []),
    '## 1 范围',
    '',
    `本文档${mode === 'merged' ? '为平台级测试大纲融合稿，当前补充' : '补充描述'}${subsystemName}的测试范围、测试项、验收关注点和测试组织建议。`,
    '',
    '### 1.1 标识',
    '',
    `测试对象为${subsystemName}。`,
    '',
    '### 1.2 系统概述',
    '',
    systemOverviewText(),
    '',
    '### 1.3 文档概述',
    '',
    '本文档说明测试依据、测试环境、测试标识、测试进度、终止条件、测试说明和需求可追踪性。',
    '',
    '### 1.4 与其它计划的关系',
    '',
    '测试大纲应与需求规格说明、软件设计说明、数据库设计说明、安装部署手册和测试记录配套使用。',
    '',
    '## 2 引用文档',
    '',
    '沿用平台级引用文档。',
    '',
    '## 3 测试依据',
    '',
    '测试依据包括 MEDOL 模型、需求规格说明、软件设计说明、数据库设计说明、接口约定、部署配置和用户操作手册。',
    '',
    '## 4 软件测试环境',
    '',
    '### 4.1 内部测试环境',
    '',
    '内部测试环境应包含平台后端、平台 Console、参与方 Console、数据库、存储、运行时代理和运行时引擎。',
    '',
    '### 4.2 试点医院现场测试环境',
    '',
    '现场测试环境应确认参与方网络边界、本地用户鉴权、本地数据集访问、运行时代理部署和平台可达性。',
    '',
    '## 5 测试标识',
    '',
    '### 5.1 一般信息',
    '',
    '测试级、测试类别、测试条件、测试进展、数据记录与整理方式沿用平台级测试管理要求。',
    '',
    '### 5.2 计划执行的测试',
    '',
    '计划执行功能测试、外部接口测试、内部接口测试、性能测试、安全测试、部署测试和隐私边界测试。',
    '',
    '## 6 测试进度',
    '',
    '测试进度由项目测试计划统一安排，联邦学习子系统应覆盖模型生成、部署、业务操作、运行时代理和参与方前端。',
    '',
    '## 7 测试终止条件',
    '',
    '测试终止条件包括计划用例执行完成、严重缺陷关闭或风险接受、关键隐私边界验证通过、部署与回归测试完成。',
    '',
    '## 8 测试说明',
    '',
    `### ${subsystemName}`,
    '',
    demote(body, 2),
    '',
    '## 9 需求的可追踪性',
    '',
    '### 9.1 正向追溯',
    '',
    '测试项应正向追溯到需求、命令、事件、读模型、页面入口和运行时接口。',
    '',
    '### 9.2 逆向追溯',
    '',
    '缺陷、测试结果和验收记录应反向追溯到对应需求和设计章节。',
    '',
    '## 10 注释',
    '',
    '本生成稿不包含实际测试结果。',
    '',
    '## 附录',
    '',
    '附录可补充测试数据、截图、日志、缺陷清单和测试报告导出。'
  ];
}

function systemOverviewText(): string {
  return `平台包含孪生数字人子系统与${subsystemName}。${subsystemName}围绕组织、联邦、数据集、模型、训练编排、运行时代理、安全聚合与运行时治理能力，支撑跨机构数据不出域条件下的协同建模。`;
}

const documentSpecs: ProjectDocumentSpec[] = [
  {
    number: '3.1',
    kind: 'prd',
    name: '需求规格说明',
    existingTitle: '01“感一通一算”一体化的跨域健康管理联邦孪生平台-需求规格说明v1.0.docx',
    filenameTitle: '需求规格说明',
    standalone: (body) => prdSections(body, 'standalone'),
    merged: (body) => prdSections(body, 'merged')
  },
  {
    number: '3.2',
    kind: 'software-design',
    name: '软件设计说明',
    existingTitle: '02“感一通一算”一体化的跨域健康管理联邦孪生平台-软件设计说明v1.0.docx',
    filenameTitle: '软件设计说明',
    standalone: (body) => softwareDesignSections(body, 'standalone'),
    merged: (body) => softwareDesignSections(body, 'merged')
  },
  {
    number: '3.3',
    kind: 'database-design',
    name: '数据库设计说明',
    existingTitle: '03“感一通一算”一体化的跨域健康管理联邦孪生平台-数据库设计说明v1.0.docx',
    filenameTitle: '数据库设计说明',
    standalone: (body) => databaseSections(body, 'standalone'),
    merged: (body) => databaseSections(body, 'merged')
  },
  {
    number: '3.4',
    kind: 'user-manual',
    name: '用户操作手册',
    existingTitle: '04“感一通一算”一体化的跨域健康管理联邦孪生平台-用户操作手册v1.0.docx',
    filenameTitle: '用户操作手册',
    standalone: (body) => userManualSections(body, 'standalone'),
    merged: (body) => userManualSections(body, 'merged')
  },
  {
    number: '3.5',
    kind: 'installation-manual',
    name: '安装部署手册',
    existingTitle: '05“感一通一算”一体化的跨域健康管理联邦孪生平台-安装部署手册v1.0.docx',
    filenameTitle: '安装部署手册',
    standalone: (body) => installationSections(body, 'standalone'),
    merged: (body) => installationSections(body, 'merged')
  },
  {
    number: '3.6',
    kind: 'test-record',
    name: '测试记录',
    existingTitle: '06“感一通一算”一体化的跨域健康管理联邦孪生平台-测试记录V1.0.docx',
    filenameTitle: '测试记录',
    standalone: () => testRecordSections('standalone'),
    merged: () => testRecordSections('merged')
  },
  {
    number: '3.7',
    kind: 'test-outline',
    name: '测试大纲',
    existingTitle: '07“感一通一算”一体化的跨域健康管理联邦孪生平台-测试大纲V1.0.docx',
    filenameTitle: '测试大纲',
    standalone: (body) => testOutlineSections(body, 'standalone'),
    merged: (body) => testOutlineSections(body, 'merged')
  }
];

async function main(): Promise<void> {
  const options = parseOptions(process.argv.slice(2));
  modelTranslations = {
    ...readModelTranslations(options.translationPath),
    ...supplementalTranslations,
    ...projectDocumentTranslations
  };
  localizationDictionary = buildLocalizationDictionary(modelTranslations);

  const outputGroups: string[] = [];
  mkdirSync(options.outputDirectory, { recursive: true });

  if (options.mode === 'standalone' || options.mode === 'all') {
    outputGroups.push('standalone');
    mkdirSync(join(options.outputDirectory, 'standalone'), { recursive: true });
    mkdirSync(join(options.outputDirectory, 'word', 'standalone'), { recursive: true });
  }
  if (options.mode === 'merged' || options.mode === 'all') {
    outputGroups.push('merged');
    mkdirSync(join(options.outputDirectory, 'merged'), { recursive: true });
    mkdirSync(join(options.outputDirectory, 'word', 'merged'), { recursive: true });
  }

  writeSupportFiles(options);

  for (const spec of documentSpecs) {
    const body = generatedBody(spec.kind, options);
    const standaloneMarkdown = await withModelEnhancementIfNeeded(
      spec,
      'standalone',
      spec.standalone(body).join('\n'),
      options
    );
    const mergedMarkdown = await withModelEnhancementIfNeeded(
      spec,
      'merged',
      spec.merged(body).join('\n'),
      options
    );

    if (outputGroups.includes('standalone')) {
      const filename = `${spec.number}-${projectTitle}-${spec.name}-${subsystemName}`;
      const markdownPath = join(options.outputDirectory, 'standalone', `${filename}.md`);
      writeMarkdown(markdownPath, standaloneMarkdown);
      writeDocx(
        markdownPath,
        join(options.outputDirectory, 'word', 'standalone', `${filename}.docx`),
        spec,
        `${projectTitle} ${spec.name}`
      );
    }
    if (outputGroups.includes('merged')) {
      const filename = `${spec.number}-${filesystemProjectTitle}-${spec.name}v1.0-融合稿`;
      const markdownPath = join(options.outputDirectory, 'merged', `${filename}.md`);
      writeMarkdown(markdownPath, mergedMarkdown);
      writeDocx(
        markdownPath,
        join(options.outputDirectory, 'word', 'merged', `${filename}.docx`),
        spec,
        `${projectTitle} ${spec.name}`
      );
    }
  }

  console.log(`Generated federation learning project documents in ${options.outputDirectory}`);
}

function generatedBody(kind: DocumentationKind, options: PackOptions): string {
  const existing = generatedBodies.get(kind);
  if (existing) return existing;
  if (kind === 'test-record') {
    const body = '';
    generatedBodies.set(kind, body);
    return body;
  }
  const markdown = execFileSync(
    'npm',
    [
      'run',
      '--silent',
      'docs:generate',
      '--',
      options.sourceModelPath,
      `--kind=${kind}`,
      '--language=zh-CN'
    ],
    {
      cwd: medolRoot,
      encoding: 'utf8',
      env: process.env,
      maxBuffer: 64 * 1024 * 1024
    }
  );
  let body = stripGeneratedDocumentChrome(markdown);
  if (kind === 'prd') body = stripPrdReviewOnlySections(body);
  if (kind === 'software-design') body = replaceSoftwareQualitySection(body);
  body = localizeBareEnglish(body);
  generatedBodies.set(kind, body);
  return body;
}

function readModelTranslations(path?: string): ModelTranslations {
  if (!path) return {};
  try {
    const content = JSON.parse(readFileSync(path, 'utf8')) as {
      translations?: Record<string, ModelTranslations>;
    };
    return content.translations?.['zh-CN'] ?? {};
  } catch {
    return {};
  }
}

function commonStandaloneScope(contentName: string): string[] {
  return [
    '## 1 范围',
    '',
    '### 1.1 标识',
    '',
    `本文档补充描述${subsystemName}的${contentName}，用于后续与其他子系统内容合并形成平台级文档。`,
    '',
    '### 1.2 系统概述',
    '',
    `平台包含孪生数字人子系统与${subsystemName}。${subsystemName}围绕组织、联邦、数据集、模型、训练编排、运行时代理、安全聚合与运行时治理能力，支撑跨机构数据不出域条件下的协同建模。`,
    '',
    '### 1.3 文档概述',
    '',
    '本生成稿仅包含联邦学习相关内容。平台公共背景、标准引用、签署页、术语总表等内容建议在最终合并稿中统一维护。',
    '',
    '## 2 引用文档',
    '',
    '本节建议沿用平台级引用文档，不按子系统重复拆分。',
    ''
  ];
}

function mergedHeader(name: string): string[] {
  return [
    '<!--',
    `融合参考：${name} 对应项目文档目录中的平台级 Word 文档。`,
    '该文件保留平台级标题体系，并在对应章节下插入“联邦学习子系统”内容。',
    '生成稿不覆盖原始 Word 文档；如需正式交付，可将本 Markdown 内容人工或自动合入 Word 模板。',
    '-->',
    ''
  ];
}

function writeSupportFiles(options: PackOptions): void {
  const lines = [
    `# ${subsystemName}项目文档生成说明`,
    '',
    '## 输入',
    '',
    `- MEDOL 模型：\`${relative(federationLearningRoot, options.sourceModelPath)}\``,
    `- 术语表：\`${options.translationPath ? relative(federationLearningRoot, options.translationPath) : '未配置'}\``,
    '- 参考文档：工作区根目录 `项目文档/*.docx`',
    '',
    '## 输出',
    '',
    '- `standalone/`：联邦学习子系统独立文档。',
    '- `merged/`：按项目 Word 文档标题体系整理的融合稿。',
    '- `word/standalone/`：联邦学习子系统独立文档 Word 版。',
    '- `word/merged/`：项目文档融合稿 Word 版。',
    '',
    '## 项目文档标题结构',
    '',
    '- 3.1 需求规格说明：范围、引用文档、3.1-3.17 需求章节。',
    '- 3.2 软件设计说明：范围、引用文档、CSCI级设计决策、CSCI体系结构设计、CSCI详细设计、需求可追踪性。',
    '- 3.3 数据库设计说明：范围、引用文档、数据库级设计决策、数据库详细设计、数据库访问单元、需求可追踪性、注释、附录。',
    '- 3.4 用户操作手册：范围、引用文档、软件应用、软件入门、使用指南。',
    '- 3.5 安装部署手册：范围、系统要求、常用软件安装、系统部署安装。',
    '- 3.6 测试记录：任务描述、目标、环境类型、云类型、功能测试用例设计、功能测试用例、测试结果。',
    '- 3.7 测试大纲：范围、引用文档、测试依据、软件测试环境、测试标识、测试进度、测试终止条件、测试说明、需求可追踪性、注释、附录。',
    '',
    '测试记录仅生成待填写结构，不自动编造测试执行结果。',
    '',
    '## 可选模型增强',
    '',
    '默认生成完全确定，不调用模型。需要润色项目表达时可显式追加 `--enhance-with-model`，并配置：',
    '',
    '```bash',
    'AGENT_PROVIDER=openai OPENAI_API_KEY=... OPENAI_MODEL=gpt-5.2 node scripts/generate-project-docs.mjs --enhance-with-model',
    '```',
    '',
    '模型增强只应做表达润色、结构衔接和项目文档语气统一，不应新增模型中不存在的业务事实。'
  ];
  writeMarkdown(join(options.outputDirectory, 'README.md'), lines.join('\n'));
}

function writeMarkdown(path: string, markdown: string): void {
  mkdirSync(dirname(path), { recursive: true });
  writeFileSync(path, `${localizeBareEnglish(markdown).trim()}\n`, 'utf8');
}

function writeDocx(markdownPath: string, docxPath: string, spec: ProjectDocumentSpec, title: string): void {
  mkdirSync(dirname(docxPath), { recursive: true });
  const reference = resolve(workspaceRoot, '项目文档', spec.existingTitle);
  execFileSync(
    process.env.PYTHON ?? 'python3',
    [
      markdownToDocxScript,
      markdownPath,
      '--out',
      docxPath,
      '--title',
      title,
      ...(reference ? ['--reference', reference] : [])
    ],
    {
      cwd: federationLearningRoot,
      env: process.env,
      stdio: 'inherit'
    }
  );
}

function stripGeneratedDocumentChrome(markdown: string): string {
  return markdown
    .replace(/^# .+\n+/u, '')
    .replace(/<!-- em:section id="document\.toc" -->\n## 目录\n\n[\s\S]*?(?=<!-- em:section id="document\.changeLog" -->|## [一二三四五六七八九十])/u, '')
    .replace(/<!-- em:section id="document\.changeLog" -->\n## 变更记录\n\n\|[\s\S]*?(?=<!-- em:section id="(?!document\.)|## [一二三四五六七八九十])/u, '')
    .replace(/^## 变更记录\n\n\|[\s\S]*?(?=<!-- em:section id="(?!document\.)|## [一二三四五六七八九十])/mu, '')
    .replace(/^\s*<!--\s*em:section\b[^>]*-->\s*$/gmu, '')
    .trim();
}

function stripPrdReviewOnlySections(markdown: string): string {
  return markdown
    .replace(/\n+\*\*已建模产品指标\*\*\n\n(?:- .+\n)+/u, '\n')
    .replace(/\n+## 十三、待确认事项[^\n]*\n[\s\S]*$/u, '')
    .trim();
}

function replaceSoftwareQualitySection(markdown: string): string {
  const replacement = [
    '## 五、质量属性、运维与可观测性设计/FLP-SD-SECTION-80',
    '',
    '### 5.1 质量属性设计',
    '',
    '- 可用性：联邦学习子系统应保证平台管理端、训练编排服务、运行时代理连接和关键读模型在正常部署条件下稳定可用。',
    '- 一致性：命令处理、事件记录、读模型更新和自动化处理应保持可追踪关系，关键业务状态不得出现无法解释的跳变。',
    '- 安全性：组织、联邦、数据集、模型、运行时身份和安全聚合相关操作应执行角色权限、租户边界和敏感数据保护控制。',
    '- 可扩展性：训练任务、训练轮次、参与方运行时、数据集和模型构件应支持按业务规模扩展。',
    '- 可维护性：业务能力按上下文拆分，命令、事件、读模型和自动化处理器保持清晰边界，便于后续扩展和问题定位。',
    '',
    '### 5.2 运维设计',
    '',
    '- 服务状态：运维侧应能查看前端、后端服务、数据库、对象存储、消息或调度组件、运行时代理的启动状态和健康状态。',
    '- 任务处理：训练编排、轮次执行、安全聚合、模型评估和运行时回调应具备失败记录、重试处理和人工排查入口。',
    '- 审计追踪：联邦激活、参与方加入、数据集审批、训练提交、模型发布、运行时身份变更等关键操作应形成审计记录。',
    '- 配置管理：运行时安装计划、代理启动配置、访问授权、镜像仓库和信任包等配置应支持变更留痕和回滚。',
    '- 故障恢复：训练任务失败、运行时离线、节点资源压力、代理连接失败和安全聚合失败应提供明确的恢复路径。',
    '',
    '### 5.3 可观测性设计',
    '',
    '| 观测对象 | 观测内容 | 处理建议 |',
    '| --- | --- | --- |',
    '| 平台服务 | 服务启动、接口错误、任务积压、数据库连接状态 | 接入日志、指标和告警，异常时定位到服务实例和请求链路。 |',
    '| 训练任务 | 作业状态、轮次状态、参与方选择、模型更新、评估结果 | 在训练作业看板中展示关键状态，并保留异常原因。 |',
    '| 运行时代理 | 代理启动、自检、连接状态、数据集访问、运行时执行结果 | 通过运行时健康视图和代理生命周期视图进行跟踪。 |',
    '| 节点资源 | 节点清单、处理器、内存、存储、资源压力 | 资源压力触发告警，并关联受影响训练任务。 |',
    '| 安全聚合 | 会话创建、参与方选择、加密上下文、聚合完成或失败 | 聚合失败时记录缺失参与方、加密上下文和失败阶段。 |',
    '',
    '### 5.4 关键运维指标',
    '',
    '- 运行时连接成功率',
    '- 训练轮次完成时延',
    '- 训练作业失败率',
    '- 运行时代理任务启动时延',
    '- 数据集契约校验失败率',
    '- 模型构件拉取失败率',
    '- 安全聚合完成时延',
    '- 告警平均确认时间',
    '- 审计记录完整率',
    ''
  ].join('\n');

  return markdown.replace(
    /\n+## 五、质量属性、运维与可观测性设计[^\n]*\n[\s\S]*?(?=\n+## 六、实现缺口)/u,
    `\n\n${replacement}\n`
  );
}

function demote(markdown: string, levels: number): string {
  return markdown.replace(/^(#{1,6}) /gmu, (_match, hashes: string) =>
    `${'#'.repeat(Math.min(6, hashes.length + levels))} `
  );
}

function buildLocalizationDictionary(source: ModelTranslations): Array<[string, string]> {
  return Object.entries(source)
    .filter(([english, chinese]) =>
      /[A-Za-z]/u.test(english)
      && Boolean(chinese.trim())
      && chinese.trim() !== english
      && !/ is required$|^Enter |^Select /u.test(english)
    )
    .sort((left, right) => right[0].length - left[0].length);
}

function localizeBareEnglish(markdown: string): string {
  const parts = markdown.split(/(（[^）]*）)/u);
  const protectedReplacements: string[] = [];
  const localizedMarkdown = parts.map((part) => {
    if (part.startsWith('（') && part.endsWith('）')) return part;
    let localized = part;
    for (const [english, chinese] of localizationDictionary) {
      const replacement = chinese;
      const pattern = new RegExp(`(^|[^（A-Za-z0-9_])${escapeRegExp(english)}($|[^）A-Za-z0-9_])`, 'gu');
      localized = localized.replace(pattern, (_match, prefix: string, suffix: string) =>
        `${prefix}${protectReplacement(replacement, protectedReplacements)}${suffix}`
      );
    }
    return localized;
  }).join('').replace(/@@MEDOL_PROTECTED_REPLACEMENT_(\d+)@@/gu, (_match, index: string) =>
    protectedReplacements[Number(index)] ?? ''
  );
  return removeTranslatedEnglishGlosses(localizedMarkdown);
}

function removeTranslatedEnglishGlosses(markdown: string): string {
  return markdown
    .replace(/([\u4e00-\u9fff][\u4e00-\u9fffA-Za-z0-9·、/ -]{0,40}[\u4e00-\u9fff])（[A-Za-z][A-Za-z0-9 ._/-]{1,80}）/gu, '$1')
    .replace(/（[A-Za-z][A-Za-z0-9 ._/-]{1,80}）/gu, '');
}

function protectReplacement(value: string, protectedReplacements: string[]): string {
  const index = protectedReplacements.push(value) - 1;
  return `@@MEDOL_PROTECTED_REPLACEMENT_${index}@@`;
}

function escapeRegExp(value: string): string {
  return value.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
}

async function withModelEnhancementIfNeeded(
  spec: ProjectDocumentSpec,
  mode: 'standalone' | 'merged',
  markdown: string,
  options: PackOptions
): Promise<string> {
  if (!options.enhanceWithModel) return markdown;
  const enhanced = await runModelEnhancement({
    title: `${spec.name}-${mode}`,
    markdown,
    sourceModel: readFileSync(options.sourceModelPath, 'utf8')
  });
  return enhanced ?? markdown;
}

async function runModelEnhancement(input: {
  title: string;
  markdown: string;
  sourceModel: string;
}): Promise<string | undefined> {
  const provider = process.env.AGENT_PROVIDER;
  if (provider !== 'openai' && provider !== 'minimax') {
    console.warn(`[docs] AGENT_PROVIDER is not openai/minimax; skip model enhancement for ${input.title}.`);
    return undefined;
  }
  const apiKey = provider === 'openai' ? process.env.OPENAI_API_KEY : process.env.MINIMAX_API_KEY;
  if (!apiKey) {
    console.warn(`[docs] Missing API key; skip model enhancement for ${input.title}.`);
    return undefined;
  }
  const baseURL = provider === 'openai'
    ? process.env.OPENAI_BASE_URL ?? 'https://api.openai.com/v1'
    : process.env.MINIMAX_BASE_URL ?? 'https://api.minimaxi.com/v1';
  const modelName = provider === 'openai'
    ? process.env.OPENAI_MODEL ?? 'gpt-5.2'
    : process.env.MINIMAX_MODEL ?? 'MiniMax-M3';

  const prompt = [
    `文档：${input.title}`,
    '',
    '请基于下面 Markdown 做一次项目文档风格润色：',
    '- 保留原有 Markdown 标题层级、表格、列表和业务事实；',
    '- 不新增模型中没有的功能、角色、接口、指标或部署方式；',
    '- 允许调整中文表达、章节衔接和项目交付文档语气；',
    '- 只返回完整 Markdown 正文，不要解释。',
    '',
    '--- Markdown ---',
    input.markdown,
    '',
    '--- MEDOL Source Excerpt ---',
    truncate(input.sourceModel, 60000)
  ].join('\n');

  try {
    const response = await fetch(`${trimTrailingSlash(baseURL)}/responses`, {
      method: 'POST',
      headers: {
        Authorization: `Bearer ${apiKey}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        model: modelName,
        instructions: [
          '你是严谨的中文项目交付文档技术写作专家。',
          '你只能润色和整理用户提供的 Markdown，不能杜撰事实。',
          '返回内容必须是 Markdown 正文。'
        ].join('\n'),
        input: prompt,
        temperature: 0,
        reasoning: { effort: 'low' }
      })
    });
    const responseText = await response.text();
    if (!response.ok) {
      console.warn(`[docs] Model enhancement failed for ${input.title}: HTTP ${response.status}. Use deterministic document.`);
      return undefined;
    }
    const responseJson = parseJson(responseText);
    const output = (responseJson ? extractOutputText(responseJson) : undefined) ?? responseText;
    const markdown = unwrapMarkdownFence(output).trim();
    return markdown || undefined;
  } catch (error) {
    console.warn(`[docs] Model enhancement failed for ${input.title}: ${formatError(error)}. Use deterministic document.`);
    return undefined;
  }
}

function trimTrailingSlash(value: string): string {
  return value.replace(/\/+$/u, '');
}

function truncate(value: string, maxLength: number): string {
  return value.length > maxLength
    ? `${value.slice(0, maxLength)}\n\n[truncated ${value.length - maxLength} chars]`
    : value;
}

function parseJson(value: string): unknown | undefined {
  try {
    return JSON.parse(value);
  } catch {
    return undefined;
  }
}

function extractOutputText(value: unknown): string | undefined {
  if (!value || typeof value !== 'object') return undefined;
  const record = value as Record<string, unknown>;
  if (typeof record.output_text === 'string') return record.output_text;
  if (typeof record.text === 'string') return record.text;
  if (Array.isArray(record.output)) {
    const parts = record.output.flatMap((item) => {
      if (!item || typeof item !== 'object') return [];
      const content = (item as Record<string, unknown>).content;
      if (!Array.isArray(content)) return [];
      return content.flatMap((entry) => {
        if (!entry || typeof entry !== 'object') return [];
        const text = (entry as Record<string, unknown>).text;
        return typeof text === 'string' ? [text] : [];
      });
    });
    if (parts.length) return parts.join('\n');
  }
  return undefined;
}

function unwrapMarkdownFence(value: string): string {
  return value.trim()
    .replace(/^```(?:markdown|md)?\s*/iu, '')
    .replace(/\s*```$/u, '');
}

function formatError(error: unknown): string {
  return error instanceof Error ? error.message : String(error);
}

main().catch((error) => {
  console.error(formatError(error));
  process.exitCode = 1;
});
