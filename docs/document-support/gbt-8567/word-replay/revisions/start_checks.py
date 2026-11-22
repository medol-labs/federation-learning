"""区分计划接收和作业启动检查。"""

from copy import deepcopy

from docx.text.paragraph import Paragraph


def put(p, text):
    rr=p.runs
    if rr:
        rr[0].text=text
        for run in rr[1:]:run.text=''
    else:
        p.add_run(text)
def clone_after(p, text):
    node=deepcopy(p._p)
    p._p.addnext(node)
    c=Paragraph(node,p._parent)
    put(c,text)
    return c
def after_heading(d, heading):
    i=next(i for i,p in enumerate(d.paragraphs) if p.text.startswith(heading))
    return d.paragraphs[i+1:i+3]


def apply(k, d):
    if k == "srs":
        req,criteria=after_heading(d,'3.4.4.2　FL-SRS-ROUND-EXECUTE')
        assert req.text.startswith('需求　') and criteria.text.startswith('合格判据　')
        req.add_run(' 参与端应分别记录计划接收后的接受判断和作业启动结果。计划须获接受后方可启动；启动前应复核对应的数据绑定和引擎作业输入。')
        p=clone_after(criteria,'计划接收后的验收　按计划指定的运行环境、组织、特征架构和数据集核对本地绑定。恰好匹配一条且路径已填写时，可接受计划。没有匹配、匹配多条或路径为空时，按原计划可查到拒绝原因，且不会由该结果触发启动。')
        clone_after(p,'作业启动验收　直接启动未获接受的计划时应拒绝。已接受计划的绑定在启动前变为缺失、多条或空路径，或模型插件标识为空时，应记录启动失败。引擎健康就绪前不得提交作业；引擎健康超时、提交失败或返回失败时，应查到启动失败原因。使用预启动引擎时，计划的镜像引用与配置不符也应记录启动失败。引擎受理后按同一执行标识查询对应作业，再单独确认训练结果。')
        pending=next(t for t in d.tables if t.rows[0].cells[1].text=='待确认事项')
        q=next(row.cells[2].paragraphs[0] for row in pending.rows[1:] if row.cells[0].text=='Q-11')
        old='⑦ 确认基础模型可用、配置与引擎兼容、资源可用及运行环境空闲分别在哪个阶段检查。覆盖计划接收与作业启动，验证条件不满足时的拒绝或失败结果。'
        new='⑦ 确认前置检查的补充范围：数据路径实际可读、基础模型文件可取且可加载、插件与训练配置的实际兼容性、资源充足及运行环境空闲。明确应在接收、启动或执行中何时核验，以及发现不满足条件后的拒绝、失败或重试规则。'
        assert old in q.text
        put(q,q.text.replace(old,new))
        tests=next(t for t in d.tables if t.rows[0].cells[-1].text=='现有用例及待补验证')
        row=next(row for row in tests.rows[1:] if row.cells[0].text=='FL-SRS-ROUND-EXECUTE')
        put(row.cells[2].paragraphs[0],'CASE-SCALE 覆盖后继轮基础模型及正向执行。引用断言仅证明已记录。计划绑定拒绝、未接受直接启动、启动输入无效、引擎失败和受理后关联均按正文单独验收。实际聚合输入、失败退出及未定的前置检查见 Q-11、Q-12。')
        trace=next(t for t in d.tables if t.rows[0].cells[-1].text=='实现与用例定位')
        row=next(row for row in trace.rows[1:] if row.cells[0].text=='FL-SRS-ROUND-EXECUTE')
        p=row.cells[1].paragraphs[-1]
        p.add_run('\nPART / LocalAcceptExecutionPlanAdapter.kt、ExecutionPlanDatasetBinding.kt、StartRoundExecutionCommandHandler.kt、StartRoundExecutionDecision.kt、KubernetesStartRoundExecutionAdapter.kt、LocalDockerComposeStartRoundExecutionAdapter.kt')
    else:
        _,p=after_heading(d,'5.4 计划派发与参与方执行')
        assert p.text.startswith('技术实现　派发按已连接端点')
        put(p,'技术实现　管理端按已连接端点派发计划，当前请求的 executionPlanId 与 roundExecutionId 取同值。参与端收到计划后登记 PlanReceived，随后独立执行接受判定。业务执行标识与引擎 jobId 分别保留。首轮使用配置中的初始模型，后续轮次当前优先取同一任务前序聚合模型，缺失时的回退规则见需规 Q-11。')
        p=clone_after(p,'计划接收后的检查　AcceptExecutionPlan 要求当前为 PlanReceived。按 runtimeId、organizationId、featureSchemaId、datasetId 查本站唯一绑定，同时检查 filePath 非空。失败则记录 ExecutionPlanRejectedEvent 及原因；成功记录 ExecutionPlanAcceptedEvent 并触发启动。路径非空检查仅确认绑定填写了路径。')
        p=clone_after(p,'作业启动检查　StartRoundExecution 要求 PlanAccepted，之后才调用启动适配器。Compose／预启动与 Kubernetes 路径均重新查唯一绑定、检查 filePath 和 baseModelPlugin 非空，并执行个性化参数校验。预启动路径另对照配置镜像；Kubernetes 解析计划镜像并启动容器。之后等待引擎健康，再提交单步作业。启动适配器拒绝、不可用或引擎返回失败时记录 RoundExecutionStartFailedEvent 与原因；成功受理则关联运行引擎 jobId。')
        clone_after(p,'待确认的检查　计划接受适配器内的模型可用、配置兼容、资源和空闲判断当前为固定值。启动适配器的插件标识、镜像及健康检查按上述范围执行。完整的数据可读性、模型可取和可加载、插件与配置兼容、资源及空闲检查的责任阶段与失败处置统一见需规 Q-11。')
        refs=next(t for t in d.tables if t.rows[0].cells[-1].text=='实现文件与证据')
        row=next(row for row in refs.rows[1:] if row.cells[0].text=='FL-DES-006')
        row.cells[1].paragraphs[-1].add_run('\nfederation-learning-runtime-agent / ExecutionPlanDatasetBinding.kt、StartRoundExecutionCommandHandler.kt、KubernetesStartRoundExecutionAdapter.kt、LocalDockerComposeStartRoundExecutionAdapter.kt')
