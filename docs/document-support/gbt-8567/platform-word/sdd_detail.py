"""Restore the controlled federation learning software-unit design in section 5.1."""

import json
import re
import sys
from io import BytesIO
from pathlib import Path

from docx import Document
from docx.oxml import OxmlElement
from docx.oxml.ns import qn
from docx.shared import Pt

from sdd_interfaces import save_section
from sdd_identifiers import DESIGN_ID, MODULE_CODES, NESTED_UNITS, STEP_CODES, normalize_identifiers

ROOT = Path(__file__).resolve().parents[4]
sys.path.insert(0, str(ROOT / "scripts"))
from software_design_operation_inventory import operation_rows
from software_design_template_structure import detailed_units

SOURCE = ROOT / "docs/438c/merge"
START = "联邦学习模块详细设计"
DESIGN_TITLE = f"{START}/{DESIGN_ID}"
END = "医疗数字人模块详细设计"
OPENING = (
    "本节按业务模块组织详细设计，模块内分别说明处理流程、数据、控制移交和异常处置。"
    "同一作业、轮次、计划、代理执行和引擎作业分别用各自标识关联。",
    "跨单元接口的报文、权限及返回约定见4.5，持久化结构见数据库设计说明。"
    "本节的查询结果由事件更新后可见，接口受理不代表后续处理已经完成。",
)
CONSOLE_DESIGN = (
    "**联邦学习平台侧控制台/FL-SU-UI-P：**面向平台运营人员，接收用户操作、筛选条件和分页参数；"
    "按所选资源连接平台服务或支撑服务，提交训练编排及管理操作并呈现返回结果。"
    "会话及当前筛选条件是界面状态，作业状态以后台查询结果为准；提交成功不表示训练已完成。",
    "**运行时代理控制台/FL-SU-UI-A：**面向机构人员，连接本机构的运行时代理，"
    "提交本地数据集维护操作并查询本机构运行时和轮次执行记录，不经控制台传输训练原始数据。"
    "两侧控制台都按权限呈现操作入口，由后端校验请求；查询失败与查无记录分开处理。部署归属见表104。",
)
OPERATION_OWNERS = {
    "FL-SD-OP-F0081": "FL-U-C-TRAIN",
    "FL-SD-OP-F0105": "FL-U-C-SEC",
    "FL-SD-OP-F0136": "FL-U-C-AGENT",
}
REVIEWED_OPERATIONS = {
    "FL-SD-OP-F0069": (
        "新轮次启动后，按该轮已选参与者的运行时和数据集生成计划，读取作业、轮次及训练配置。",
        "首轮使用配置的初始模型；后续轮次使用同一作业中最近一轮已登记的聚合模型。"
        "找不到先前已登记的聚合模型时回退到初始配置；已有聚合模型标识但无法解析工件时停止生成。",
        "保存目标运行时、基础模型及引擎配置，随后交给计划分发。缺少所选数据集、模型工件必需信息或引擎配置时不得生成成功结果。",
    ),
    "FL-SD-OP-F0078": (
        "全局模型更新后，读取当前轮次的聚合工件、预期工件摘要、模型插件及特征架构。",
        "先从支撑服务读取工件，再调用4.5.3.4的聚合引擎接口（LBLS-JK-NB-AGGENG-AGGJOB，POST /evaluations）。"
        "平台核对返回的模型摘要和分类计数，计算准确率；验证数据版本须非空、摘要须符合格式，不在平台侧比较其预期值。",
        "记录有效的评估结果后才允许完成轮次。读取工件失败、引擎拒绝或结果校验失败时不得记录评估成功；不提供公平性得分。",
    ),
    "FL-SD-OP-F0075": (
        "普通聚合条件满足后，读取本轮已接受的更新工件引用、轮次关联和聚合算法配置。",
        "有可用输入才提交聚合作业。远程模式以同一引擎作业标识查询终态；成功后读取对应输出工件，"
        "再由支撑服务保存并返回工件引用。只有产出可用工件才能把控制交给后续评估。",
        "无输入工件、引擎失败或等待超时、输出不可读或保存失败时，不记录可用的全局模型。"
        "适配器抛出的异常可能中断本次消息处理，不能据此推定已记录业务失败事件或已自动重试。",
    ),
    "FL-SD-OP-F0102": (
        "安全聚合会话创建后，使用同一训练轮次已选的运行时标识及参与数量。",
        "前置会话须处于已规划状态；本处理校验运行时列表非空、参与数量为正，"
        "通过后记录安全聚合参与者已选择，交由会话后续步骤使用，不重新进行训练轮次选点。",
        "输入为空或数量无效时拒绝本次处理，不推进会话。选中参与者也不表示其加密更新已提交或聚合已完成。",
    ),
    "FL-SD-OP-F0112": (
        "机构用户查询本代理维护的运行时登记记录，可提供授权范围内的筛选与分页条件。",
        "从本地副本读取运行时标识、所属组织、关联代理和激活或撤销状态。"
        "副本由「查询视图投影」下的目录同步刷新；本操作不实时向平台拉取。",
        "返回本地记录或空结果；查询失败与空结果区分，不能将暂未同步的记录判为已撤销。",
    ),
    "FL-SD-OP-F0134": (
        "轮次执行处于启动失败状态才允许按启动失败路径处理，使用原轮次、执行计划、轮次执行和已有引擎作业标识。",
        "校验当前状态后，将原执行上下文交给重试服务。只有服务报告已开始，才记录启动重试已开始；"
        "重试命令自身不创建新的执行计划。",
        "服务拒绝或不可用时记录启动重试失败，并触发原引擎作业的释放处理。"
        "当前未配置可用的重试适配器时，不能将重试请求视为引擎作业已重新启动。",
    ),
    "FL-SD-OP-F0135": (
        "已运行作业的轮次执行进入运行失败状态后才走此路径，沿用原轮次、计划、执行会话及失败作业标识。",
        "将失败上下文交给运行失败重试服务；仅在服务报告已开始时记录运行重试已开始。"
        "是否重新启动引擎作业由重试服务决定，不由命令处理器或查询操作直接创建。",
        "拒绝或不可用时记录运行重试失败，并触发失败作业释放。"
        "当前无可用适配器时走不可用分支；释放结果独立记录，不代表训练成功。",
    ),
}


def operation_text(groups, nested=False, *, level=4, parent_id):
    parts = []
    for group in groups:
        for row in group["rows"]:
            function_id = row["requirementId"].rsplit("-", 1)[1]
            parts.append(f'{"#" * level} {row["title"]}/{parent_id}-{function_id}')
            if reviewed := REVIEWED_OPERATIONS.get(row["id"]):
                for label, value in zip(("触发与输入", "处理逻辑", "结果与异常"), reviewed, strict=True):
                    parts.append(f"**{label}：**{value}")
                continue
            query = bool(row["slice"]["readmodels"] and not row["slice"]["commands"]
                         and not row["slice"]["events"])
            input_, process, result = row["cells"][1:]
            if query:
                process = "从本服务查询视图按授权范围执行筛选、排序和分页；列表和总数使用相同条件。"
                result = "返回查询记录或空结果；查询失败不改变业务状态，可按原条件重新查询。"
            parts.extend([f"**触发与输入：**{input_}", f"**处理逻辑：**{process}"])
            changes = []
            for transition in row["transitions"]:
                label = row["translations"].get
                before = transition.get("from")
                after = transition.get("to")
                if isinstance(before, str) and isinstance(after, str):
                    event = transition.get("event", {}).get("title", "")
                    changes.append(f"{label(before, before)}经{label(event, event)}转为{label(after, after)}")
            if changes:
                parts.append("**状态变化：**" + "；".join(dict.fromkeys(changes)) + "。")
            parts.append(f"**结果与异常：**{result}")
            if row["acceptance"] and not query:
                parts.append("**处理分支：**" + "；".join(row["acceptance"]) + "。")
    return "\n\n".join(parts)


def arrange_detail_units(units, unit_aliases):
    by_code = {entry[0]: entry for entry in units}
    moved = {}
    child_codes = {code for codes in NESTED_UNITS.values() for code in codes}
    for _, _, _, rows in units:
        for row in rows:
            if owner := OPERATION_OWNERS.get(row["id"]):
                moved.setdefault(owner, []).append(row)
    if {row["id"] for rows in moved.values() for row in rows} != OPERATION_OWNERS.keys():
        raise ValueError("Missing operation in the federation design ownership map")

    def render(entry, level, parent_id):
        code, title, details, rows = entry
        title, legacy_id = title.rsplit("/", 1)
        suffix = (MODULE_CODES if level == 3 else STEP_CODES)[code]
        identifier = f"{parent_id}-{suffix}"
        unit_aliases[legacy_id] = identifier
        rows = [row for row in rows if row["id"] not in OPERATION_OWNERS] + moved.get(code, [])
        if code == "FL-U-PROJ":
            title = "公共设计：查询视图投影与目录同步"
            details = [*CONSOLE_DESIGN, *details]
        parts = [f'{"#" * level} {title}/{identifier}', *details]
        for child in NESTED_UNITS.get(code, ()):
            parts.extend(render(by_code[child], level + 1, identifier))
        if rows:
            parts.append(operation_text([{"rows": rows}], level=level + 1, parent_id=identifier))
        return parts

    parts = []
    for code, _, _, _ in units:
        if code not in child_codes and code != "FL-U-PROJ":
            parts.extend(render(by_code[code], 3, DESIGN_ID))
    parts.extend(render(by_code["FL-U-PROJ"], 3, DESIGN_ID))
    return parts


def detail_translations(model):
    glossary = (ROOT / "docs/438c/sources/model-translations.zh-CN.md").read_text(encoding="utf-8")
    translations = json.loads(Path(__file__).with_name("sdd-detail-terms.zh-CN.json").read_text(encoding="utf-8"))
    translations.update(re.findall(r"^\| ([^|]+?) \| ([^|]+?) \| 已翻译 \|$", glossary, re.M))
    translations.update({key: value for key, value in model.get("translations", {}).get("zh-CN", {}).items()
                         if value and value != key})
    # Transitions carry state identifiers, while the glossary uses display titles.
    for transition in model.get("transitions", []):
        for key in ("from", "to"):
            state = transition.get(key)
            if isinstance(state, str) and state not in translations:
                title = re.sub(r"(?<=[a-z0-9])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])", " ", state)
                if title in translations:
                    translations[state] = translations[title]
    return translations


def detail_markdown(unit_aliases=None):
    if unit_aliases is None:
        unit_aliases = {}
    model = json.loads((ROOT / ".medol/codegen-model.json").read_text(encoding="utf-8"))
    model.setdefault("translations", {})["zh-CN"] = detail_translations(model)
    register = json.loads((SOURCE / "3.3.1-function-register.json").read_text(encoding="utf-8"))
    groups = operation_rows(model, register, (SOURCE / "3.3.1-federation-learning.md").read_text(encoding="utf-8"),
                            include_field_shapes=False)
    blocks = json.loads((SOURCE / "software-design-sections.json").read_text(encoding="utf-8"))["sections"]["detailed"]
    reviewed = json.loads(Path(__file__).with_name("sdd-detail-units.json").read_text(encoding="utf-8"))
    additions = json.loads(Path(__file__).with_name("sdd-detail-extensions.json").read_text(encoding="utf-8"))

    def render_unit(item):
        code = item["cells"][0].split(" ", 1)[0]
        if code in reviewed:
            labels = ("触发与输入", "处理逻辑与移交", "约束与异常")
            details = [f"**{label}：**{value}" for label, value in zip(labels, reviewed[code], strict=True)]
        else:
            details = [f"**输入与处理：**{item['cells'][1]}",
                       f"**结果与异常：**{item['cells'][2]}",
                       f"**职责边界：**{item['cells'][3]}"]
        extensions = [f"**{label}：**{value}" for label, value in additions.get(code, [])]
        return [f"**软件单元标识：**{code}。", *details, *extensions]

    return normalize_identifiers(detailed_units(
        blocks, groups, operation_text, render_unit=render_unit, opening=OPENING,
        arrange_units=lambda units: arrange_detail_units(units, unit_aliases),
    ))


def revise_detail(source, *, refresh=False):
    doc = Document(BytesIO(source))
    anchors = [p for p in doc.paragraphs if p.text.strip() in (START, DESIGN_TITLE, END)]
    if len(anchors) != 2 or anchors[0].text.strip() not in (START, DESIGN_TITLE) or anchors[1].text.strip() != END:
        raise ValueError("Missing or ambiguous section 5.1 boundaries")
    head, tail = anchors
    unit_aliases = {}
    design = detail_markdown(unit_aliases).splitlines()
    expected_units = set(unit_aliases.values())
    if len(expected_units) != len(unit_aliases):
        raise ValueError("Duplicate federation learning software unit identifiers")
    references = {}
    if head.text.strip() == START:
        head.add_run("/" + DESIGN_ID)
    if refresh:
        old_headings = set()
        for node in head._p.itersiblings():
            if node is tail._p:
                break
            value = "".join(t.text or "" for t in node.iter(qn("w:t")))
            suffix = value.rsplit("/", 1)[-1]
            identifier = unit_aliases.get(suffix, suffix)
            if identifier in expected_units:
                old_headings.add(identifier)
                for anchor in node.iter(qn("w:bookmarkStart")):
                    if (name := anchor.get(qn("w:name"), "")).startswith("_Toc"):
                        references[identifier] = name
                        break
        if old_headings != expected_units:
            raise ValueError("Existing section 5.1 does not match the generated unit headings")
    block = head._p.getnext()
    while block is not tail._p:
        if block is None or block.tag != qn("w:p") or any(block.iter(qn("w:drawing"))):
            raise ValueError("Section 5.1 is not empty; refusing to replace existing detailed design")
        following = block.getnext()
        if refresh:
            block.getparent().remove(block)
        elif any((node.text or "").strip() for node in block.iter(qn("w:t"))):
            raise ValueError("Section 5.1 is not empty; refusing to replace existing detailed design")
        block = following

    toc_entries = {}
    existing = {node.get(qn("w:name")) for node in doc.element.iter(qn("w:bookmarkStart"))}
    for paragraph in doc.paragraphs:
        if paragraph.style.name.lower() != "toc 3":
            continue
        match = re.match(r"5\.1\.\d+ .+/([A-Z0-9-]+)\t", paragraph.text)
        if match:
            identifier = unit_aliases.get(match[1], match[1])
            if identifier not in expected_units:
                continue
            toc_entries[identifier] = paragraph._p
            fields = " ".join(t.text or "" for t in paragraph._p.iter(qn("w:instrText")))
            reference = re.search(r"\bPAGEREF (\S+)", fields)
            if reference and reference[1] not in existing:
                references.setdefault(identifier, reference[1])
    bookmark_id = max((int(n.get(qn("w:id"))) for n in doc.element.iter(qn("w:bookmarkStart"))), default=0)
    unit_ids = set()
    top_units = []
    for line in design:
        if not line:
            continue
        if match := re.fullmatch(r"(#{3,5}) (.+)", line):
            depth = len(match[1])
            p = tail.insert_paragraph_before(match[2], style=f"Heading {depth}")
            p.paragraph_format.keep_with_next = True
            p.paragraph_format.space_before = Pt(8)
            p.paragraph_format.space_after = Pt(4)
            if depth == 3:
                top_units.append(match[2])
            identifier = match[2].rsplit("/", 1)[-1]
            if identifier in expected_units:
                unit_ids.add(identifier)
                if name := references.get(identifier):
                    bookmark_id += 1
                    begin = OxmlElement("w:bookmarkStart")
                    begin.set(qn("w:id"), str(bookmark_id))
                    begin.set(qn("w:name"), name)
                    p._p.r_lst[0].addprevious(begin)
                    end = OxmlElement("w:bookmarkEnd")
                    end.set(qn("w:id"), str(bookmark_id))
                    p._p.append(end)
            continue
        p = tail.insert_paragraph_before()
        fmt = p.paragraph_format
        fmt.line_spacing = 1.35
        fmt.space_before, fmt.space_after = Pt(0), Pt(3)
        grid = OxmlElement("w:snapToGrid")
        grid.set(qn("w:val"), "0")
        p._p.get_or_add_pPr().insert_element_before(grid, "w:spacing")
        if line.startswith("**"):
            label, value = line[2:].split("**", 1)
            p.add_run(label).bold = True
            p.add_run(value)
        else:
            p.add_run(line)
    if set(references) - unit_ids:
        raise ValueError("Software unit headings no longer match the existing table of contents")
    if toc_entries:
        cursor = next(p._p for p in doc.paragraphs if p.style.name.lower() == "toc 2" and p.text.startswith("5.1 "))
        _, root_title, _ = cursor.iter(qn("w:t"))
        root_title.text = " " + DESIGN_TITLE
        wanted = {title.rsplit("/", 1)[-1] for title in top_units}
        for identifier, node in toc_entries.items():
            if identifier not in wanted:
                node.getparent().remove(node)
        for number, title in enumerate(top_units, 1):
            node = toc_entries[title.rsplit("/", 1)[-1]]
            seq, heading, page = node.iter(qn("w:t"))
            seq.text, heading.text = f"5.1.{number}", " " + title
            page.text = None
            for field in node.iter(qn("w:fldChar")):
                if field.get(qn("w:fldCharType")) == "begin":
                    field.set(qn("w:dirty"), "true")
            cursor.addnext(node)
            cursor = node
    return save_section(source, doc)
