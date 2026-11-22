"""统一业务规则、验收判据及追溯。"""

import re
from copy import deepcopy

versions={'srs':'0.12','sdd':'0.4','dbdd':'0.3'}


def ptext(p, text):
    if p.runs:
        p.runs[0].text=text
        for run in p.runs[1:]: run.text=''
    else:
        p.add_run(text)

def ctext(cell, text):
    ptext(cell.paragraphs[0],text)
    for p in cell.paragraphs[1:]:p._p.getparent().remove(p._p)

def update(b, changes):
    for i,v in changes.items():
        p=b[i]
        label,sep,rest=v.partition('　')
        # Retain bold requirement/criteria labels in the source.
        if sep and p.runs and p.runs[0].bold and p.runs[0].text.startswith(label) and len(p.runs)>1:
            p.runs[0].text=label+sep
            p.runs[1].text=rest
            for run in p.runs[2:]:run.text=''
        else:
            ptext(p,v)

def version(b, k):
    v=versions[k]
    ctext(b[0].rows[0].cells[0],'版本：V'+v)
    t = b[2]
    t._tbl.append(deepcopy(t.rows[-1]._tr))
    for c,val in zip(t.rows[-1].cells,('V'+v,'统一业务规则、验收判据及追溯','待确认','2026-11-21','评审稿')):
        ctext(c,val)

def by_id(table, rid):
    return next(r for r in table.rows[1:] if r.cells[0].text==rid)

def append(table, rid, col, extra):
    c=by_id(table,rid).cells[col]
    ctext(c,c.text+'\n'+extra)

def srs(d, b):
    version(b,'srs')
    ctext(b[5].rows[3].cells[1],'FL-SRS，0.12（评审稿）')
    ctext(b[150].rows[0].cells[1],'V0.12 需求或章节')
    update(b,{
        49: '需求　新建训练配置和训练任务时，所用联邦均须为 Active。任务引用的配置必须属于同一联邦。',
        50: '合格判据　对同一组其余有效输入，分别提交创建配置和创建任务，使用 Active 联邦可创建，使用 Draft 或 Suspended 联邦均被拒绝且原状态仍可查询。两个联邦均为 Active 时，使用另一联邦的草稿配置创建任务也应被拒绝。',
        55: '合格判据　从目录核对已发布架构的标识与版本、已登记初始模型的标识与制品引用。按配置标识读回配置，核对其特征架构和初始模型与所选目录项一致。',
        57: '需求　参与端应校验本地绑定数据的特征与标签契约，并独立记录审批。管理端可接收不含原始数据或本地路径的选点元信息。仅有兼容证据的数据不能获得训练资格；未批准、验证失败或批准已撤回的数据不得用于新的选点或执行放行。已派发执行的处置见 Q-12。',
        58: '合格判据　分别核验本地绑定、契约验证、审批和管理端元信息。验证失败时拒绝批准；兼容但未批准及批准已撤回时，新的选点与执行放行均拒绝。兼容且已批准时按标识关联两端记录。启用自动上报后单独核对其载荷和管理端记录，确认原始数据及本地路径留在站点。',
        65: '需求　软件应区分草稿创建和正式提交。提交应启动首轮选点并锁定引用的配置，两个结果可分别查验。锁定后可回查对应任务，配置不可按草稿修改或用于新建任务。锁定可见前的并发处置规则见 Q-11。',
        66: '合格判据　按同一任务核对草稿与提交或后续状态、首轮选点记录，以及同一配置的 Locked 状态和锁定任务标识。未知任务不可提交，锁定后修改或再次引用该配置均应拒绝。对锁定可见前的修改及并发复用另取证，按 Q-11 确认的规则验收。',
        70: '需求　依据已加入的成员资格、活跃运行身份、特征及标签兼容证据和站点数据批准状态选择参与方。达到最低参与数后固定当轮名单及数据引用。',
        71: '合格判据　符合条件且足量时，可查询固定的当轮名单，每个运行环境对应一份数据。排除缺少兼容证据、未批准及批准已撤回的数据。不足时记录选点失败，后续新出现的就绪站点不得改变本轮名单。',
        73: '需求　管理端应向当轮所选站点交付执行计划并记录更新验收。首轮使用配置指定的初始模型，后续轮次接续同一任务上一轮的聚合模型。收到的更新与已接受更新分开核对。故障按轮次定位，符合条件方可重试；确认剩余可取得的有效贡献低于最低参与数时，应记录轮次失败并停止推进成功聚合。前轮模型缺失及失败后的任务处置见 Q-11、Q-12。',
        74: '合格判据　按任务、轮次、运行环境、计划及执行标识关联回报；核对首轮及下一轮的基础模型标识。核对接受事件与聚合输入，已收到但未接受或已拒绝的更新不得进入输入。模拟暂时可恢复故障及已无法达到最低参与数的故障，分别验证按原轮次重试及轮次失败，失败轮次不得产生成功聚合结果。',
        78: '需求　明文聚合须收齐固定快照内每个参与方的一份已接受更新。同一参与方的重复更新不能补计人数，未选站点不能替代当轮成员。安全聚合采用与本轮快照关联的会话并跟踪加密更新。',
        79: '合格判据　分别用少一方、重复提交、未选站点补足数量及全部到齐的输入验证。前三种不产生新的成功聚合触发，全部到齐产生可关联的结果。以接受事件核对聚合输入，快照和有效贡献名单应一致。安全路径核对会话与加密更新归属，密码学验收见 Q-05。',
        82: '合格判据　仅有聚合模型时无法完成轮次，评估失败或与本轮模型关联不符时也不得完成。有效评估能回查本轮模型；预算未用完进入下一轮，达到预算后任务完成并记录末轮的最终模型标识。',
        84: '需求　向获授权用户提供训练任务及轮次对应的聚合模型制品。任务完成后的最终模型应与末轮通过有效评估的聚合模型一致。',
        85: '合格判据　按同一任务查最终模型标识、末轮评估和制品登记，三者的模型标识及任务与轮次归属须一致。取实际聚合制品核对版本、格式和引用；下载字节非空，复算 SHA-256 与评估及目录记录一致。初始模型登记摘要按其登记规则核对。',
        124: '区分选点、作业启动和执行失败，按任务、轮次、运行环境及作业标识定位。轮次失败和重试按 FL-SRS-004-002 验收。当前最低参与数失败闭环尚待补齐；失败后的任务终态、处置和恢复范围按 Q-07、Q-11、Q-12 确认。',
})
    cases={
 '001-002':'分别验证配置、任务两个入口的三种联邦状态。两侧均有效但归属不一致的交叉输入仍缺实现与用例，见 Q-11。',
 '002-001':'CASE-SCHEMA 覆盖架构发布及模型登记。CASE-CONFIG 读回配置标识。配置的架构与模型引用一致性需补断言。',
 '002-002':'CASE-BIND、CASE-DATA 覆盖绑定、批准及管理端元信息。后者可直接登记元信息。自动上报、审批撤回及无批准放行需独立验证，见 Q-11。',
 '003-002':'CASE-JOB 读回提交后的任务状态。需补 Locked、锁定任务标识、首轮选点的联合断言，以及未知任务、锁定后拒绝、锁定可见前并发窗口，见 Q-11。',
 '004-001':'CASE-BASE 覆盖正向选点。须补不兼容、无批准、撤回、选点不足与固定名单的独立核验，见 Q-11。',
 '004-002':'CASE-SCALE 核对后一轮基础模型。现有引用断言读取轮次投影，需补首轮模型、接受事件与实际聚合输入、失败退出及缺前轮模型场景，见 Q-11、Q-12。',
 '005-001':'CASE-BASE、CASE-SCALE 验证正向产物。需补收齐前、重复提交、未选站点补足、接受事件引用与实际输入比对。安全分支见 Q-05、Q-11。',
 '005-002':'CASE-SCALE 覆盖正常两轮完成。评估失败、越序完成及任务最终模型指向末轮评估需补独立断言，见 Q-04、Q-11。',
 '005-003':'CASE-BASE、CASE-SCALE 覆盖逐轮下载及摘要。需补任务最终模型、末轮评估与制品登记的一致性断言，见 Q-11。',
    }
    for rid,text in cases.items():
        ctext(by_id(b[141],'FL-SRS-'+rid).cells[2],text)
    evidence={
 '002-001':'MGR / RegisterModelArtifactDecisionComponent.kt、DefineTrainingRunConfigurationCommandHandler.kt、TrainingRunConfigurationCatalogReadModelProjector.kt',
 '002-002':'MGR / ReadModelTrainingRoundParticipantSelectionAdapter.kt\nPART / LocalAcceptExecutionPlanAdapter.kt',
 '003-002':'MGR / LockTrainingRunConfigurationDecision.kt、UpdateTrainingRunConfigurationDecision.kt、CreateTrainingJobCommandHandler.kt',
 '004-002':'MGR / GenerateParticipantExecutionPlanDecisionComponent.kt、RuntimeAgentDispatchParticipantExecutionPlanAdapter.kt、TrainingRoundProgressReadModelProjector.kt、FailRoundWhenRejectedUpdatesBreakQuorumProcessor.kt\nCASE-SCALE / previous-model',
 '005-001':'MGR / TrainingRoundProgressReadModelProjector.kt（收到的引用）及 ModelUpdateSubmissionAcceptedEvent.kt（接受的引用）',
 '005-002':'MGR / CompleteJobWhenTrainingStopCriteriaMetProcessor.kt、GlobalModelEvaluationSubmittedEvent.kt',
 '005-003':'MGR / TrainingJobCompletedEvent.kt、TrainingJobDashboardReadModelProjector.kt、GlobalModelEvaluationSubmittedEvent.kt',
    }
    for rid,t in evidence.items():
        append(b[145],'FL-SRS-'+rid,1,t)
    qs={
 'Q-06':'确认角色权限矩阵、跨站端点和网络白名单、传输保护、患者或个人数据政策和审计范围。明确元信息与制品保留期、过期后下载权限及物理回收要求。',
 'Q-07':'确认故障模型、可用率、恢复时间、允许的数据损失、备份恢复验收、取消传播和在途制品处置。恢复时联合核验业务事件、投影进度、模型目录、文件与摘要，并确认物理回收和孤立文件处理责任。',
 'Q-08':'确认运行现场、软硬件依赖矩阵、容器和插件版本、网络拓扑、交付介质、包装标识及校验办法。明确中心文件服务与站点需要的持久卷、挂载、容量、备份责任和重建后的核验方式。',
 'Q-11':'补齐下列验证及对应实现缺口。① 配置与任务的联邦资格及归属、零轮次拒绝。② 自动上报、未批准与审批撤回的选点和执行拒绝。③ 提交、异步锁定和首轮选点的联合断言，以及锁定可见前的并发修改、复用和冲突处理规则。④ 首轮模型、后继轮模型接续及前轮模型缺失时是否允许回退。⑤ 已收到与已接受更新、未选站点与重复提交、实际聚合输入及最低参与数失败闭环。⑥ 评估失败、越序完成及最终模型与末轮评估和制品的一致性。任务操作状态与原因、安全聚合负向验证仍需补验，安全基线见 Q-05。',
 'Q-12':'确认联邦暂停、版本退役、站点失联或审批撤回后，已提交任务与已固定轮次的继续、取消和重新派发规则。确认轮次失败后的任务终态、同轮重试条件及已产生制品的处置；恢复目标见 Q-07。新选点与执行放行的审批要求见 FL-SRS-002-002、FL-SRS-004-001。',
    }
    for rid,t in qs.items():
        ctext(by_id(b[152],rid).cells[2],t)

def sdd(d, b):
    version(b,'sdd')
    ctext(b[5].rows[2].cells[1],'FL-SDD，V0.4 评审稿')
    ctext(b[5].rows[3].cells[1],b[5].rows[3].cells[1].text.replace('V0.11','V0.12'))
    update(b,{
        9: b[9].text.replace('V0.11','V0.12'),
        13: b[13].text.replace('V0.11','V0.12'),
        168: '技术实现　成员邀请与批准分别更新成员目录。定义配置与创建任务的入口分别校验联邦 Active。按需规 FL-SRS-001-002 对界面和直接提交分别验证，任务与配置的联邦归属校验缺口见 Q-11。',
        170: '业务设计　剖析和上报用于共享数据兼容证据，本地契约校验与审批决定训练资格。未批准或批准撤回的数据应阻止新的选点与执行放行，关联 FL-SRS-002-002、FL-SRS-004-001。已派发执行的处置按需规 Q-12。',
        171: '技术实现　启用自动上报时，剖析成功事件发送架构和兼容证据。审批另走本地操作。管理端选点目前检查架构与标签兼容性，参与端计划接收检查唯一绑定和非空路径；两处均未检查本地审批。自动上报、兼容未批准及批准撤回的验证统一见需规 Q-11。配置读回还须核对所选的架构和初始模型引用，见 FL-SRS-002-001。',
        174: '技术实现　TrainingJobSubmittedEvent 由独立处理器异步触发首轮选点和配置锁定，无固定先后次序。分别等待和核对同一任务的首轮记录、Locked 及锁定任务标识。锁定决策未检查前置状态，锁定可见前的并发修改或复用规则与验收，以及联邦归属和零轮次前置拒绝，统一见需规 Q-11。',
        176: '业务设计　按当轮固定名单为每个参与站点交付数据和基础模型引用。首轮使用配置的初始模型，后续轮次使用同一任务上一轮的聚合模型，见需规 FL-SRS-004-002。',
        177: '技术实现　派发按已连接端点发送计划，当前请求的 executionPlanId 与 roundExecutionId 取同值。参与端接收时实际检查唯一数据绑定及非空路径；模型可用、配置兼容、资源和空闲状态在该适配器中仍为固定值。启动时按作业载荷准备数据与模型，选择本地或容器运行方式并调用引擎。业务执行键与引擎 jobId 分别保留。后继轮当前优先取同一任务此前最近的聚合模型，取不到时可能回退，规则与验证见需规 Q-11。',
        180: '技术实现　接收以 roundExecutionId 和文件字段比较收据，验收按 runtimeId 去重。查询投影 artifactRefs 包含已收到和已接受的引用；明文聚合读取接受事件的 acceptedModelUpdateArtifactRefs。普通路径目前按接受数量达到已选数量判定就绪，名单逐项匹配和异常拒绝待补。验收按需规 FL-SRS-004-002、FL-SRS-005-001 分别核对名单、接受事件、实际聚合输入与触发次数，缺口见 Q-11。',
        184: '技术实现　参与端记录失败阶段和作业标识，按条件提供重试与释放路径。管理端保留轮次状态，更新拒绝后跌破最低参与数的自动失败分支尚未启用。按需规 FL-SRS-004-002 验收可重试与轮次失败两个出口；待补闭环见 Q-11，任务终态和制品处置见 Q-07、Q-12。',
        190: '交付记录应能回查软件修订、构建结果、镜像版本和部署配置。按组件分别核验实际插件、节点资源和站点资格。文件登记状态、授权下载与物理保留分别核对，生产承载和备份回收边界统一见需规 Q-06 至 Q-08。',
        192: '批准工况包括 2 参与方 1 轮与 20 参与方 2 轮。既有用例核对任务完成、轮次数、执行及逐轮制品摘要；多轮还核对后继轮模型。贡献引用现有断言读取轮次投影，接受事件与聚合输入的比对、任务最终模型一致性断言均按需规 Q-11 补验。',
    })
    c=by_id(b[196],'FL-SRS-004-001').cells[1]
    ctext(c,c.text+'、FL-DES-012')
    c=by_id(b[197],'FL-DES-012').cells[1]
    ctext(c,c.text+'、FL-SRS-004-001')
    c=by_id(b[196],'FL-SRS-004-002').cells[1]
    ctext(c,c.text+'、FL-DES-018')
    c=by_id(b[197],'FL-DES-018').cells[1]
    ctext(c,'FL-SRS-004-002、'+c.text)
    append(b[201],'FL-DES-004',1,'federation-learning-platform / RegisterModelArtifactDecisionComponent.kt')
    append(b[201],'FL-DES-006',1,'federation-learning-runtime-agent / LocalAcceptExecutionPlanAdapter.kt')
    append(b[201],'FL-DES-008',1,'federation-learning-platform / TrainingRoundProgressReadModelProjector.kt\nfederation-learning-platform / AggregatePlainUpdatesWhenQuorumReachedProcessor.kt')

def dbdd(d, b):
    update(b,{
        5: '文档版本\tV0.3 评审稿',
        37: b[37].text.replace('FL-DBDD V0.2','FL-DBDD V0.3').replace('FL-SRS V0.21','FL-SRS V0.12').replace('FL-SDD V0.3','FL-SDD V0.4'),
        57: b[57].text+' 过期下载权限见需规 Q-06，物理回收与恢复处置见 Q-07，生产持久卷见 Q-08。',
        67: '按数据绑定和运行环境标识关联两端记录，分别核验自动上报、审批和选点。管理端元信息未携带本站审批状态，计划接收目前只检查唯一绑定及非空路径。兼容未批准、审批撤回后的新选点及放行按需规 FL-SRS-002-002、FL-SRS-004-001 验收，缺口与已有执行处置分别见 Q-11、Q-12。',
        72: '分别按同一任务回查锁定与首轮选点。核对配置 Locked、lockedByTrainingJobId 和首轮记录。锁定前的并发修改、复用、联邦归属和零轮次前置拒绝按需规 Q-11 核对，技术触发见软设 5.3。',
        75: b[75].text+' 正向用例读取的轮次投影引用可证明已记录，验收有效贡献需另核对接受事件和聚合命令中的引用，并核对参与方名单。失败退出、未选站点和重复提交的检查按需规 Q-11。',
        80: '任务达到轮次预算时，TrainingJobCompletedEvent 记录 finalRoundId、finalModelId、最终制品摘要及评估关联标识；任务看板可查 finalModelId。首轮执行的 baseModelId 来自训练配置，后一轮应关联上一轮聚合模型。当前取不到前轮基线可能回退到初始模型，验收规则见需规 FL-SRS-004-002、Q-11 和软设 5.4。',
        95: '最终模型按需规 FL-SRS-005-002、FL-SRS-005-003 与软设 5.5、5.9 核验。用同一 trainingJobId 读取已完成任务和达到预算的末轮，核对成功评估事件、模型目录及下载文件。最终模型、受评模型及登记模型标识必须一致；对照各自任务和轮次归属，下载复算 SHA-256 并核对评估和目录摘要。现有批准用例覆盖逐轮制品，最终模型一致性断言统一见需规 Q-11。',
        98: b[98].text.replace('V0.21','V0.12').replace('V0.3','V0.4'),
    })
    for tag,v in [('FL-SRS','V0.12'),('FL-SDD','V0.4')]:
        c=by_id(b[44],tag).cells[1]
        ctext(c,re.sub(r'V0\.\d+',v,c.text))
    ctext(b[100].rows[0].cells[3],'软设 V0.4')
    for row in b[100].rows[1:]:
        rid=row.cells[1].text
        if rid=='FL-SRS-004-002':
            ctext(row.cells[2],'4.4 计划、模型接续、收据、接受贡献和失败；5.2 核验')
            ctext(row.cells[3],'5.4 至 5.6')
        elif rid=='FL-SRS-005-002':
            ctext(row.cells[2],'4.5 评估与最终轮次；5.2 一致性核验')

def apply(kind, document):
    headings = [p.text for p in document.paragraphs if p.style.name.startswith("Heading")]
    {"srs": srs, "sdd": sdd, "dbdd": dbdd}[kind](document, list(document.iter_inner_content()))
    if headings != [p.text for p in document.paragraphs if p.style.name.startswith("Heading")]:
        raise ValueError(f"{kind} 的原有章节标题发生变化")
    version_number = versions[kind]
    properties = document.core_properties
    properties.revision = int(version_number.split(".")[1])
    properties.subject = re.sub(r"V0\.\d+", f"V{version_number}", properties.subject)
