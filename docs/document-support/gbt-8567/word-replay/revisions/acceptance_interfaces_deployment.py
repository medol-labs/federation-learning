"""明确需规判据、软设接口对应和数据库部署说明。"""

import json
from pathlib import Path

def put(p, s):
    label,sp,body=s.partition('　')
    rr=p.runs
    if sp and len(rr)>1 and rr[0].bold and rr[0].text.strip()==label:
        rr[0].text=label+sp
        rr[1].text=body
        for r in rr[2:]:r.text=''
    elif rr:
        rr[0].text=s
        for r in rr[1:]:r.text=''
    else:
        p.add_run(s)
def cell(c, s):
    put(c.paragraphs[0],s)
    for p in c.paragraphs[1:]:p._p.getparent().remove(p._p)
def find(t, i):
    return next(r for r in t.rows[1:] if r.cells[0].text==i)
def patch(b, values):
    for i,s in values.items():
        put(b[i],s)

def srs(b):
    patch(b,{
        58: '合格判据　验证失败的数据不得获准；兼容但未批准或批准已撤回的数据不得进入新一轮选点或获得新的执行放行。兼容且已批准时，可按站点和数据集关联两端记录。启用自动上报时，管理端能查到对应元信息，原始数据及本地路径留在站点。',
        66: '合格判据　创建后可查询草稿任务。提交后可查到该任务及首轮选点，并能回查配置已锁定且关联本次提交任务。未知任务不得提交，锁定后的配置不得修改或供另一任务创建。锁定生效前的并发情形按 Q-11 确认。',
        74: '合格判据　按同一任务、轮次和站点查到对应计划、执行结果及更新验收结论。首轮使用所选初始模型，下一轮使用同一任务前一轮的聚合模型。已收到但未接受或已拒绝的更新不得参与聚合。对暂时可恢复的故障可按原轮次重试；当有效贡献已无法达到最低参与数时，应查到轮次失败且无成功聚合产物。',
        79: '合格判据　当轮缺少任何已选参与方的有效更新时不得聚合。重复更新和未选站点的更新不得代替缺少的更新。收齐后产生该轮聚合结果，实际纳入的贡献者与当轮名单一致。安全路径应能回查会话与加密更新的轮次和参与方归属，密码学验收见 Q-05。',
        85: '合格判据　可按任务查最终模型，按末轮查受评模型与登记制品。三处模型标识及任务、轮次归属一致。获授权用户能下载非空的实际聚合制品，并核对其版本、格式、引用及 SHA-256；下载内容的摘要须与末轮评估和模型目录所记录的一致。',
        140: '第 3 章规定可观察的业务结果及合格判据。下表按实际断言说明已批准用例的覆盖范围。必要时，按软设和数据库设计核对接受事件、实际聚合输入、末轮评估事件及下载内容。实际执行结果以测试记录为准；未覆盖的判据统一由第 6 章跟踪。',
    })
    checks={
'FL-SRS-PREP-SCHEMA-MODEL':'CASE-SCHEMA 覆盖架构发布及模型登记。CASE-CONFIG 断言配置记录存在，未断言选择的特征架构和初始模型被正确保存。待补见 Q-11。',
'FL-SRS-PREP-DATA':'CASE-BIND、CASE-DATA 覆盖绑定、批准及管理端元信息，CASE-DATA 可直接登记。自动上报、未批准及撤回后的拒绝待补，见 Q-11、Q-12。',
'FL-SRS-JOB-CONFIG':'CASE-CONFIG 覆盖正向创建和配置记录读回，零轮次创建拒绝及不可选引用的负向验证见 Q-11。',
'FL-SRS-JOB-SUBMIT':'CASE-JOB 读回提交后的任务状态，配置锁定、锁定任务和首轮联合核对见 Q-11。',
'FL-SRS-ROUND-EXECUTE':'CASE-SCALE 验证后一轮基础模型。训练用例的贡献引用检查基于轮次投影，只证明引用已记录。实际纳入聚合的更新及失败退出，见 Q-11、Q-12。',
'FL-SRS-MODEL-AGGREGATE':'CASE-BASE、CASE-SCALE 核对正向产物。实际输入、名单完整性和负向工况见 Q-11；安全验收见 Q-05。',
'FL-SRS-MODEL-DELIVER':'CASE-BASE、CASE-SCALE 核对逐轮制品下载与摘要。最终模型与末轮评估、制品的一致性见 Q-11。',
    }
    for i,s in checks.items():
        cell(find(b[141],i).cells[2],s)
    q=find(b[152],'Q-11').cells[2]
    cell(q,q.text.replace('① 配置与任务的联邦资格及归属、零轮次拒绝。','① 配置与任务的联邦资格及归属、零轮次拒绝、不可选引用，以及配置读回的特征架构和初始模型引用一致性。'))
    q=find(b[152],'Q-07').cells[2]
    cell(q,q.text+' 按实际部署核对处理令牌存储、处理器分段位置及恢复后是否追平。')
    q=find(b[152],'Q-08').cells[2]
    cell(q,q.text+' 以现场清单及运行配置核对事件存储端点、关系库、文件根目录和卷。')

def sdd(b):
    patch(b,{
        117: '接口双方和主要数据流见 4.1.2。需规 FL-IF-01 为控制台操作接口：管理端定义配置对应本说明 4.5.2（FL-IF-001），任务提交与查询见 5.3；参与端绑定、审批及执行查询见 4.1.2、5.2 和 5.4。需规 FL-IF-02 为参与组织数据接入接口：本地接入与审批见 5.2，向管理端上报元信息对应 4.5.8（FL-IF-007）。本说明 4.5.3 的 FL-IF-002 用于服务间下发执行计划，对应需规 3.6 内部交互。各端点按部署配置提供。',
        119: b[119].text+' 该操作是需规 FL-IF-01 的管理端训练配置入口。',
        149: b[149].text+' 本接口承接需规 FL-IF-02 中的跨端元信息回报；参与组织的本地数据接入及审批见 5.2。',
        192: '批准工况为 2 参与方 1 轮及 20 参与方 2 轮。用例断言范围以需规第 4 章为准。普通训练用例以轮次投影检查已记录的制品引用。核验实际聚合输入时，以接受事件的 acceptedModelUpdateArtifactRefs 和聚合命令为据；最终模型核验见 5.5。仍需补齐的验证统一见需规 Q-11。',
        193: '技术指标按实际工况采集，分别记录训练数据和跨站载荷大小、编排延迟、节点计算及制品传输耗时、轮次有效贡献数、投影滞后及运行资源。验收目标及测试条件统一见需规第 6 章 Q-03 至 Q-08。',
    })

def dbdd(b):
    cell(b[48].rows[2].cells[2],'事件处理器更新实体，查询 API 读取 PostgreSQL 投影。Flyway V1 建立 token_entry。现场处理器采用的令牌存储和实际分段进度须核验，见需规 Q-07。')
    patch(b,{
        49: '仓库的中心分站 Compose 配置中，管理端与共享支撑服务连接同一中心 UmaDB，使用不同的 PostgreSQL 库。参与分站 Compose 配置指向本站的 UmaDB 和 PostgreSQL。训练演示 Compose 可在同一环境运行多套实例。实际连接与隔离范围按现场部署清单核验，见需规 Q-08。',
        56: '恢复时按同一业务检查点核对事件存储、各服务的关系库、文件卷与必要的站点输入。投影可按事件重建，文件内容需由文件备份保全。处理令牌、重放范围与外部副作用的恢复验收见需规 Q-07。恢复结果按 5.2 核验任务、末轮评估、模型目录和下载摘要。',
        57: '共享支撑服务的文件根目录可通过 FILE_UPLOAD_LOCAL_ROOT_DIRECTORY 指定，未设置时使用临时目录下的上传目录。训练演示 Compose 将上传目录设为 /workspace/uploads，并挂载 uploads 卷；仓库的中心分站 Compose 未为该服务配置此上传卷。生产采用的文件根目录及持久卷按需规 Q-08 核验，文件留存权限和回收处置分别见 Q-06、Q-07。',
        88: '各服务 Flyway V1 迁移定义 token_entry，主键为 processor_name 和 segment，表结构可从迁移脚本核对。项目应用配置启用 Flyway，并以 Hibernate update 更新关系结构；管理端后续迁移包含长文本列和基础设施列调整。现场实际令牌存储及分段进度按需规 Q-07 核验，索引和容量按 Q-03、Q-08 的现场基线核对。',
    })

def apply(kind, document):
    {"srs": srs, "sdd": sdd, "dbdd": dbdd}[kind](list(document.iter_inner_content()))
    if kind == "dbdd":
        pages = json.loads(Path(__file__).with_name("dbdd-toc-pages.json").read_text(encoding="utf-8"))
        for paragraph in document.paragraphs:
            if paragraph.style.name.startswith("TOC "):
                heading = "".join(
                    node.text or "" for node in paragraph._p.xpath("./w:hyperlink//w:t")
                )
                paragraph.runs[-1].text = f"\t{pages[heading]}"
