"""补充 Q-11 的验证项和实际接口路径。"""

from copy import deepcopy

from docx.text.paragraph import Paragraph


def replace(p, t):
    if p.runs:
        p.runs[0].text=t
        for run in p.runs[1:]:run.text=''
    else:
        p.add_run(t)
def sub(p, old, new):
    assert old in p.text,old
    replace(p,p.text.replace(old,new,1))


def apply(kind, d):
    if kind == "srs":
        pending=next(t for t in d.tables if t.rows[0].cells[1].text=='待确认事项')
        q11=next(row.cells[2].paragraphs[0] for row in pending.rows[1:] if row.cells[0].text=='Q-11')
        sub(q11,'② 自动上报、未批准与审批撤回的选点和执行拒绝。','② 自动上报、未批准和审批撤回后的选点及执行拒绝，选点不足时记录失败且不生成可执行名单，名单固定后新增就绪站点不进入本轮。')
        sub(q11,'③ 提交、异步锁定和首轮选点的联合断言，以及锁定可见前的并发修改、复用和冲突处理规则。','③ 未知任务提交拒绝，提交、异步锁定和首轮选点的联合断言，锁定后修改及复用拒绝，以及锁定可见前的并发修改、复用和冲突处理规则。')
        sub(q11,'任务操作状态与原因、安全聚合负向验证仍需补验，安全基线见 Q-05。','⑦ 确认基础模型可用、配置与引擎兼容、资源可用及运行环境空闲分别在哪个阶段检查。覆盖计划接收与作业启动，验证条件不满足时的拒绝或失败结果。任务操作状态与原因、安全聚合负向验证仍需补验，安全基线见 Q-05。')
        tests=next(t for t in d.tables if t.rows[0].cells[-1].text=='现有用例及待补验证')
        for rid,entry in {
            'FL-SRS-JOB-SUBMIT':'CASE-JOB 读回提交后的任务状态。未知任务拒绝、配置锁定、锁定后修改及复用拒绝、锁定任务和首轮联合核对见 Q-11。',
            'FL-SRS-ROUND-SELECT':'CASE-BASE 覆盖正向选点。选点不足、新就绪站点不改变当轮名单，以及不兼容、无批准、撤回事例见 Q-11。',
            'FL-SRS-ROUND-EXECUTE':'CASE-SCALE 验证后一轮基础模型。训练用例的贡献引用检查只证明引用已记录。执行前检查、实际聚合输入及失败退出见 Q-11、Q-12。',
        }.items():
            row=next(row for row in tests.rows[1:] if row.cells[0].text==rid)
            replace(row.cells[2].paragraphs[0],entry)
    else:
        paras=d.paragraphs
        interface=next(p for p in paras if p.text.startswith('接口双方和主要数据流见 4.1.2。需规 FL-IF-01'))
        cursor=interface._p
        for text in [
            'FL-IF-01 管理端控制台接口路径。定义配置为 POST /trainingrunconfiguration/definetrainingrunconfiguration，提交任务为 POST /trainingjob/submittrainingjob，任务看板为 GET /trainingjob/trainingjobdashboard，按标识查询在同一路径后追加 /{id}。',
            'FL-IF-01、FL-IF-02 参与端接口路径。数据绑定为 POST /runtimedatasetbinding/configureruntimedatasetbinding，绑定查询为 GET /runtimedatasetbinding/runtimedatasetbindingcatalog，批准数据为 POST /dataset/approvedatasetfortraining，执行查询为 GET /roundexecution/roundexecutioncatalog。两类目录均可在路径后追加 /{id} 查询单条记录。',
            'FL-IF-02 对应的管理端元信息接收路径为 POST /runtimedatasetmetadata/recordruntimedatasetmetadata，接口设计编号为 FL-IF-007。服务间下发执行计划仍按本说明 FL-IF-002（4.5.3）。',
        ]:
            clone=deepcopy(interface._p)
            cursor.addnext(clone)
            replace(Paragraph(clone,interface._parent),text)
            cursor=clone
        execution=next(p for p in paras if p.text.startswith('技术实现　派发按已连接端点发送计划'))
        replace(execution,execution.text+' 基础模型可用、配置兼容、资源和空闲状态的检查阶段及失败处置见需规 Q-11。')
