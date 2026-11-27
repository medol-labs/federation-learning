"""Maintain the federation learning interface material in section 4.5."""

import hashlib
import json
import re
import sys
from io import BytesIO
from pathlib import Path
from xml.etree import ElementTree
from zipfile import ZipFile

from docx import Document
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.oxml import OxmlElement
from docx.oxml.ns import qn
from docx.shared import Cm, Pt
from docx.table import Table
from docx.text.paragraph import Paragraph
from lxml import etree
from sdd_identifiers import DEPLOYMENT_IDS
from sdd_interface_ids import DIAGRAM_REFERENCE, INTERFACE_ID, NUMBERED_ID, OVERVIEW, TABLE_RULE, apply_interface_ids
from sdd_interface_outline import update_interface_outline

ROOT = Path(__file__).resolve().parents[4]
sys.path.insert(0, str(ROOT / "scripts"))
from software_design_interface_contracts import resource_interfaces
from software_design_interface_inventory import discover_routes

PLATFORM, AGENT, SUPPORT = ("联邦学习平台", "参与方运行时代理", "共享支撑服务")
TRAIN, AGG = "训练引擎", "聚合引擎"
UNITS = {provider: DEPLOYMENT_IDS[f"FL-SU-{code}"] for provider, code in (
    (PLATFORM, "P"), (AGENT, "A"), (SUPPORT, "S"), (TRAIN, "E"), (AGG, "AGG"),
)}
GROUPS = {
    "G01": ("平台侧控制台或授权客户端", PLATFORM, "平台操作与查询"),
    "G02": ("运行时代理控制台或授权客户端", AGENT, "本地操作与查询"),
    "G03": ("平台侧控制台或授权客户端", SUPPORT, "字典、文件与身份等支撑操作"),
    "G04": (PLATFORM, AGENT, "执行计划下发"),
    "G05": (AGENT, PLATFORM, "连接、数据集及模型更新上报；读取特征和训练配置"),
    "G06": (PLATFORM, SUPPORT, "读取模型更新工件、上传聚合工件"),
    "G07": (AGENT, SUPPORT, "上传模型更新工件"),
    "G08": (PLATFORM, AGG, "聚合作业、工件读取及全局评估"),
    "G09": (AGENT, TRAIN, "检查引擎、提交、查询与取消本地训练作业"),
    "G10": (AGENT, PLATFORM, "组织、特征架构和运行时身份目录同步"),
    "G11": (AGENT, SUPPORT, "字典值及其翻译目录同步"),
}
FIGURES = ("federation-learning-interface-relations", "federation-learning-sync-interface-relations")
LEGACY_FL_HEADING = "联邦学习模块接口设计"
FL_HEADING = "联邦学习模块内部接口"
EXTERNAL_HEADING = "外部接口设计"
INTERNAL_HEADING = "内部接口设计"
NEXT_SECTION = "物模型设计"
FL_SECTION = "4.5.3.4"
PERMISSION = re.compile(r'@PreAuthorize\("([^"]+)"\)')
AUTHORITY = re.compile(r"hasAuthority\('([^']+)'\)")


def group_operation(route):
    provider, path, caller = (route[key] for key in ("provider", "path", "caller"))
    if path.startswith("/sync/"):
        return [{"联邦学习平台": "G10", "共享支撑服务": "G11"}[provider]]
    if provider == AGG:
        return ["G08"]
    if provider == TRAIN:
        return ["G09"]
    if provider == PLATFORM:
        if caller == AGENT:
            return ["G05"]
        return ["G01", "G05"] if (
            path == "/featureschema/featureschemacatalog/{id}"
            or path == "/trainingrunconfiguration/trainingrunconfigurationcatalog/{id}"
        ) else ["G01"]
    if provider == AGENT:
        return ["G04"] if caller == PLATFORM else ["G02"]
    if path == "/uploadedfile/uploadfile/file":
        return ["G03", "G06", "G07"]
    if path == "/api/files/{fileId}/content":
        return ["G03", "G06"]
    return ["G03"]


def permission(route):
    if route["provider"] in (TRAIN, AGG):
        return "引擎不校验应用令牌；按部署边界限制调用范围。"
    source = (ROOT / route["source"]).read_text(encoding="utf-8")
    handler = re.search(r"\bfun\s+" + re.escape(route["operation"]) + r"\s*\(", source)
    if handler:
        annotation_block = source[:handler.start()].rsplit("\n\n", 1)[-1]
        match = PERMISSION.search(annotation_block)
        if match and (authorities := AUTHORITY.findall(match[1])):
            return "授权项：" + " 或 ".join(authorities) + "。"
    return None


def interface_terms():
    return json.loads(Path(__file__).with_name("sdd-interface-terms.zh-CN.json").read_text(encoding="utf-8"))


def interface_data():
    model = json.loads((ROOT / ".medol/codegen-model.json").read_text(encoding="utf-8"))
    translation = model.get("translations", {}).get("zh-CN")
    if not translation:
        translation = json.loads((ROOT / "docs/438c/i18n/zh-CN.json").read_text(encoding="utf-8"))
        terminology = ROOT / "docs/438c/sources/model-translations.zh-CN.md"
        for term, label in re.findall(
            r"^\| ([^|]+?) \| ([^|]+?) \| 已翻译 \|$", terminology.read_text(encoding="utf-8"), re.M
        ):
            translation.setdefault(term, label)
        translation["Submit Model Update Submission"] = "提交模型更新"
    for term, label in interface_terms().items():
        if not translation.get(term) or translation[term] == term:
            translation[term] = label
    model.setdefault("translations", {})["zh-CN"] = translation
    routes = []
    for record in discover_routes(ROOT, translation):
        path = record["path"]
        if record["provider"] == TRAIN:
            if path == "/healthz" or path.endswith("/cancel") or path in ("/jobs", "/jobs/{jobId}"):
                routes.append(dict(record, caller=AGENT))
            if path in ("/jobs", "/jobs/{jobId}", "/evaluations") or "/artifacts/" in path:
                routes.append(dict(
                    record, provider=AGG, caller=PLATFORM,
                    id="E-" + hashlib.sha256(f"aggregation:{record['method']}:{path}".encode()).hexdigest()[:8].upper(),
                ))
        else:
            routes.append(record)
    interfaces = resource_interfaces(
        routes, model, provider_order=tuple(UNITS),
        interface_id=lambda provider, source: "E-" + hashlib.sha256(
            f"{provider}:{source}".encode()
        ).hexdigest()[:8].upper(),
    )
    if len({item["id"] for item in interfaces}) != len(interfaces):
        raise ValueError("Duplicate interface contract identifiers")
    for item in interfaces:
        item["groups"] = sorted({code for route, _ in item["contracts"] for code in group_operation(route)})
        if item["provider"] in (TRAIN, AGG):
            item["name"] = item["provider"] + "作业接口"
    apply_interface_ids(interfaces)
    covered = {code for item in interfaces for code in item["groups"]}
    if covered != set(GROUPS):
        raise ValueError(f"Unmatched interface diagram groups: {set(GROUPS) ^ covered}")
    return interfaces


def figure_paths():
    figures = []
    groups = []
    for name in FIGURES:
        source = ROOT / "docs/438c/merge/figures" / (name + ".drawio")
        image = ROOT / "docs/generated/diagrams" / (name + ".png")
        groups.extend(code for cell in ElementTree.parse(source).iter("mxCell")
                      for code in re.findall(r"\bG\d{2}\b", cell.get("value", "")))
        if not image.is_file() or image.stat().st_mtime < source.stat().st_mtime:
            raise ValueError(f"接口图未导出或已过期，请先导出 {source.name}")
        figures.append(image)
    if sorted(groups) != sorted(GROUPS):
        raise ValueError("Interface figures and contract group labels do not match")
    return figures


def save_section(source, doc):
    """Keep the original package; copy back only the edited body and new images."""
    buffer = BytesIO()
    doc.save(buffer)
    rels_name = "word/_rels/document.xml.rels"
    body_name = "word/document.xml"
    types_name = "[Content_Types].xml"
    with ZipFile(BytesIO(source)) as original, ZipFile(buffer) as edited:
        original_rels = etree.fromstring(original.read(rels_name))
        old_ids = {rel.get("Id") for rel in original_rels}
        new_media = {}
        added_relationships = False
        for rel in etree.fromstring(edited.read(rels_name)):
            if rel.get("Id") in old_ids:
                continue
            if not rel.get("Type").endswith("/image"):
                raise ValueError(f"Unexpected new relationship: {rel.get('Type')}")
            content = edited.read("word/" + rel.get("Target"))
            target = "media/interface-" + hashlib.sha256(content).hexdigest()[:16] + ".png"
            rel.set("Target", target)
            original_rels.append(rel)
            added_relationships = True
            if "word/" + target not in original.namelist():
                new_media["word/" + target] = content
        replacements = {body_name: edited.read(body_name)}
        if added_relationships:
            replacements[rels_name] = etree.tostring(original_rels, xml_declaration=True, encoding="UTF-8")
            types = etree.fromstring(original.read(types_name))
            if not any(item.get("Extension") == "png" for item in types):
                etree.SubElement(types, f"{{{etree.QName(types).namespace}}}Default",
                                 Extension="png", ContentType="image/png")
                replacements[types_name] = etree.tostring(types, xml_declaration=True, encoding="UTF-8")
        result = BytesIO()
        with ZipFile(result, "w") as output:
            for entry in original.infolist():
                output.writestr(entry, replacements.get(entry.filename, original.read(entry.filename)))
            for name, content in new_media.items():
                output.writestr(name, content)
        return result.getvalue()


def federated_bounds(paragraphs):
    for index, paragraph in enumerate(paragraphs):
        heading = paragraph.text.strip()
        if heading in (LEGACY_FL_HEADING, FL_HEADING):
            boundary = EXTERNAL_HEADING if heading == LEGACY_FL_HEADING else NEXT_SECTION
            end = next(i for i in range(index + 1, len(paragraphs)) if paragraphs[i].text.strip() == boundary)
            return index, end
    raise ValueError("Missing federation learning interface heading")


def place_federated_interfaces(doc, start, end):
    if start.text.strip() == FL_HEADING:
        return
    target = next(p for p in doc.paragraphs if p.text.strip() == NEXT_SECTION)
    node = start._p
    while node is not end._p:
        if node is None:
            raise ValueError("Missing boundary for federation learning interface section")
        following = node.getnext()
        target._p.addprevious(node)
        node = following
    start.style = doc.styles["Heading 4"]
    rename = [run for run in start.runs if run.text]
    rename[0].text = FL_HEADING
    for run in rename[1:]:
        run.text = ""

    for paragraph in doc.paragraphs:
        if paragraph.text.startswith(("图 172-", "表 137-")):
            for run in paragraph.runs:
                run.text = run.text.replace("图 172-", "图 174-", 1).replace("表 137-", "表 147-", 1)

    # The TOC includes heading levels 1 to 3, not this new level 4 subsection.
    for paragraph in doc.paragraphs:
        if paragraph.style.name.lower() != "toc 3":
            continue
        label = paragraph.text.split("\t", 1)[0]
        if label == "4.5.2 " + LEGACY_FL_HEADING:
            paragraph._p.getparent().remove(paragraph._p)
        elif label in ("4.5.3 " + EXTERNAL_HEADING, "4.5.4 " + INTERNAL_HEADING):
            number = next(t for t in paragraph._p.iter(qn("w:t")) if t.text == label.split()[0])
            number.text = "4.5.2" if label.endswith(EXTERNAL_HEADING) else "4.5.3"

    for table in doc.tables:
        for row in table.rows:
            cells = row.cells
            if cells and cells[0].text.strip() == "指标4.7 支持数据传输和信息共享":
                for t in cells[2]._tc.iter(qn("w:t")):
                    if t.text == "4.5.3、4.5.4":
                        t.text = "4.5.2、4.5.3"
                return


def classify_interfaces(doc):
    from sdd_interface_layout import is_operation_table

    paragraphs = doc.paragraphs

    def find(value, *, prefix=False, start=0):
        for index, paragraph in enumerate(paragraphs[start:], start):
            text = paragraph.text.strip()
            if (text.startswith(value) if prefix else text == value):
                return index
        raise ValueError(f"Missing interface section text: {value}")

    def replace(paragraph, value):
        runs = [run for run in paragraph.runs if run.text]
        if not runs:
            raise ValueError(f"Cannot update empty interface text: {value}")
        runs[0].text = value
        for run in runs[1:]:
            run.text = ""

    architecture = find("接口设计")
    overview = find("接口标识与接口图", start=architecture + 1)
    federal, end_federal = federated_bounds(paragraphs)
    external = find(EXTERNAL_HEADING, start=overview + 1)
    internal = find(INTERNAL_HEADING, start=external + 1)

    operation_ids = {}
    current = None
    start_node = paragraphs[federal]._p
    end_node = paragraphs[end_federal]._p
    for node in start_node.itersiblings():
        if node is end_node:
            break
        if node.tag == qn("w:p"):
            text = Paragraph(node, doc._body).text.strip()
        elif is_operation_table(node):
            table = Table(node, doc._body)
            if table.rows[0]._tr.find(f'{qn("w:trPr")}/{qn("w:tblHeader")}') is None:
                continue
            text = re.sub(rf"\s+\[{INTERFACE_ID}\]$", "", table.cell(0, 0).text)
        else:
            continue
        title = re.fullmatch(rf"(.+)/({INTERFACE_ID})", text)
        if title:
            current = (title[1], title[2])
        operation = re.search(r"：((?:GET|POST|PUT|PATCH|DELETE) /\S+)$", text)
        if operation and current:
            operation_ids.setdefault(operation[1], []).append(current)

    def reference(operation, *, title=None):
        ids = [identifier for name, identifier in operation_ids.get(operation, []) if not title or name == title]
        if len(ids) != 1:
            raise ValueError(f"Missing or ambiguous federation learning interface reference: {operation}")
        return ids[0]

    download = reference("POST /modelartifact/downloadmodelartifact")
    read = reference("GET /api/files/{fileId}/content")
    dispatch = reference("POST /roundexecution/receiveparticipantexecutionplan")
    submit = reference("POST /traininground/submitmodelupdatesubmission")
    upload = reference("POST /uploadedfile/uploadfile/file")
    train = reference("POST /jobs", title="训练引擎作业接口")
    aggregate = reference("POST /jobs", title="聚合引擎作业接口")

    intro, identifiers, caption, after = paragraphs[overview + 1:overview + 5]
    table_node = caption._p.getnext()
    if table_node.tag != qn("w:tbl"):
        raise ValueError("Table 137 is not after the interface classification caption")
    table = Table(table_node, caption._parent)
    if len(table.rows) == 4 and table.rows[3].cells[1].text.strip() == "联邦孪生平台接口":
        table_node.remove(table.rows[3]._tr)
    if len(table.rows) != 3 or len(table.columns) != 4:
        raise ValueError("Unexpected layout for Table 137")

    replace(intro,
            "接口以联邦孪生平台整体为界划分。内部接口的双方均属于本系统，"
            "包括跨站点部署的联邦学习平台与机构侧运行时代理之间的接口。"
            "外部接口的一方为系统边界之外的系统、设备或应用，例如院内信息系统。"
            "接口分类取决于双方是否属于本系统，不取决于部署位置或通信方式。")
    numbering = ("表 137 给出内外部接口的分类与标识规则。联邦学习接口在内部接口设计中集中说明，"
                 "现有资源契约使用 E- 编号。" if download.startswith("E-") else OVERVIEW)
    if re.fullmatch(NUMBERED_ID, download):
        numbering = "表 137 给出内外部接口的分类与标识规则。现有联邦学习接口按所属模块分配序号。"
    replace(identifiers, numbering)
    rule = TABLE_RULE
    if download.startswith("E-"):
        rule = "既有 JCYL-JK-NB-{功能缩写}；联邦学习沿用 E- 编号"
    elif re.fullmatch(NUMBERED_ID, download):
        rule = "既有 JCYL-JK-NB-{功能缩写}；联邦学习 LBLS-JK-NB-{模块缩写}-{序号}"
    rows = (
        ("1", "内部接口", rule, "4.5.3"),
        ("2", "外部接口", "既有 JCYL-JK-WB-{功能缩写}；复用已有接口时引用原编号", "4.5.2"),
    )
    for row, values in zip(table.rows[1:], rows):
        for cell, value in zip(row.cells, values):
            replace(cell.paragraphs[0], value)
    replace(after,
            "外部和内部接口的总体关系分别见 4.5.2 和 4.5.3。"
            "接口的双方、交换内容和通信约定见相应的接口设计条目。")

    replace(paragraphs[find("平台外部接口标识见表 138。", prefix=True, start=external + 1)],
            "平台外部接口标识见表 138。向系统边界外的接收方交付模型工件时，"
            f"相关的下载操作与文件内容读取分别见 {FL_SECTION} 的 {download}、{read}；"
            "这不属于联邦学习提供的在线推理接口。")
    replace(paragraphs[find("平台内部接口标识见表 143。", prefix=True, start=internal + 1)],
            f"平台内部接口标识见表 143。联邦学习模块内部接口在 {FL_SECTION} 集中说明，"
            "其中包括"
            f"执行计划下发（{dispatch}）、模型更新上报（{submit}）、"
            f"工件上传与读取（{upload}、{read}）、训练与聚合作业（{train}、{aggregate}）。")
    place_federated_interfaces(doc, paragraphs[federal], paragraphs[end_federal])
    update_interface_outline(doc)


def revise_interface_classification(source):
    doc = Document(BytesIO(source))
    classify_interfaces(doc)
    return save_section(source, doc)


def revise_interface_outline(source):
    doc = Document(BytesIO(source))
    update_interface_outline(doc)
    return save_section(source, doc)


def insert_interfaces(source):
    interfaces = interface_data()
    figures = figure_paths()
    doc = Document(BytesIO(source))
    paragraphs = doc.paragraphs
    start_index, end_index = federated_bounds(paragraphs)
    start, end = paragraphs[start_index], paragraphs[end_index]
    node = start._p.getnext()
    while node is not end._p:
        if node is None:
            raise ValueError("Missing boundary after federation learning interfaces")
        following = node.getnext()
        node.getparent().remove(node)
        node = following

    def spacing(p, line, after):
        fmt = p.paragraph_format
        fmt.first_line_indent = fmt.left_indent = fmt.right_indent = Pt(0)
        fmt.space_before, fmt.space_after = Pt(0), Pt(after)
        fmt.line_spacing = line
        indentation = p._p.get_or_add_pPr().get_or_add_ind()
        for dimension in ("firstLineChars", "leftChars", "rightChars"):
            indentation.set(qn(f"w:{dimension}"), "0")
        grid = OxmlElement("w:snapToGrid")
        grid.set(qn("w:val"), "0")
        p._p.get_or_add_pPr().insert_element_before(grid, "w:spacing", "w:ind")

    def paragraph(value="", *, style=None, bold=False, keep=False):
        p = end.insert_paragraph_before(style=style)
        run = p.add_run(value)
        if not style:
            run.font.size = Pt(10.5)
            spacing(p, 1.25, 3)
        if bold:
            run.bold = True
        if keep:
            p.paragraph_format.keep_with_next = True
        return p

    def table(headers, rows, widths):
        t = doc.add_table(rows=0, cols=len(headers))
        end._p.addprevious(t._tbl)
        t.style = "Table Grid"
        t.autofit = False
        for column, width in zip(t.columns, widths):
            column.width = Cm(width)
        for index, values in enumerate((headers, *rows)):
            row = t.add_row()
            properties = row._tr.get_or_add_trPr()
            properties.append(OxmlElement("w:cantSplit"))
            if index == 0:
                properties.append(OxmlElement("w:tblHeader"))
            for column, (cell, value, width) in enumerate(zip(row.cells, values, widths)):
                cell.width = Cm(width)
                p = cell.paragraphs[0]
                spacing(p, 1.15, 2)
                p.alignment = WD_ALIGN_PARAGRAPH.CENTER if column == 0 else WD_ALIGN_PARAGRAPH.LEFT
                run = p.add_run(value)
                run.font.size, run.bold = Pt(10.5), index == 0
                if column == 0 and index:
                    run.font.name = "Times New Roman"

    paragraph("本节说明联邦学习各运行单元的业务接口。跨站点交换执行计划、状态、"
              "数据集元数据和模型工件，原始训练样本保留在机构侧。")
    paragraph(f"接口图中的 G01 至 G11 标识交互关系。可在清单中按图示关系{DIAGRAM_REFERENCE}，再定位接口说明。"
              "图 174-1 表示请求方向；图 174-2 表示目录数据流向，运行时代理主动拉取并更新本地目录。")
    for index, (image, title) in enumerate(zip(
        figures, ("联邦学习业务交互接口图", "运行时代理目录同步关系图")
    ), start=1):
        p = paragraph(keep=True)
        p.alignment = WD_ALIGN_PARAGRAPH.CENTER
        p.add_run().add_picture(str(image), width=Cm(15))
        p = paragraph(f"图 174-{index} {title}")
        p.alignment = WD_ALIGN_PARAGRAPH.CENTER

    paragraph("接口清单", bold=True, keep=True)
    paragraph("以下清单按表 104 的提供方分列。同一资源契约内的各项操作在相同编号下分别说明。"
              "训练引擎与聚合引擎复用同一服务实现，按调用方和实际使用的操作分别登记。"
              "框架管理端点不列入业务清单。")
    for index, (provider, unit) in enumerate(UNITS.items(), start=1):
        entries = [item for item in interfaces if item["provider"] == provider]
        if not entries:
            continue
        paragraph(f"表 147-{index} {provider}接口清单（{unit}）", bold=True, keep=True)
        table(("接口标识", "接口名称", "调用方及图示关系", "主要操作"), [
            (item["id"], item["name"].removeprefix(provider),
             "\n".join(f"{code} {GROUPS[code][0]}" for code in item["groups"]),
             "；".join(dict.fromkeys(route["purpose"] for route, _ in item["contracts"])))
            for item in entries
        ], (3.4, 3.8, 3.9, 3.9))

    paragraph("接口说明", bold=True, keep=True)
    paragraph("未另行注明时，后端业务接口使用 HTTP；结构化报文使用 JSON。用户与服务凭据分别按部署的"
              "认证方式校验，接口另行校验相应操作权限。校验或权限不满足时不执行请求。"
              "命令响应仅表明命令处理结果；异步执行或读模型更新以业务标识关联后续状态，不能以 HTTP 成功替代。")
    file_read = next(item["id"] for item in interfaces if any(
        route["path"] == "/api/files/{fileId}/content" for route, _ in item["contracts"]
    ))
    for item in interfaces:
        provider = item["provider"]
        paragraph(f'{item["name"]}/{item["id"]}', bold=True, keep=True)
        paragraph(f"提供方：{provider}（{UNITS[provider]}）；图示关系：{'、'.join(item['groups'])}。")
        for route, contract in item["contracts"]:
            path = route["path"]
            purpose = route["purpose"]
            if provider in (TRAIN, AGG) and path == "/jobs":
                purpose = "提交本地训练作业" if provider == TRAIN else "提交聚合作业"
            paragraph(f'{purpose}：{route["method"]} {path}', bold=True, keep=True)
            codes = group_operation(route)
            paragraph(f"调用方：{'、'.join(f'{GROUPS[code][0]}（{code}）' for code in codes)}；"
                      f"格式：{contract['format']}。")
            paragraph("请求：" + contract["request"].replace("\n", " "))
            paragraph("响应：" + contract["response"].replace("\n", " "))
            result = contract["result"]
            if path == "/modelartifact/downloadmodelartifact":
                result += f" 该操作不返回文件字节。实际内容由支撑服务接口 {file_read} 单独授权并返回。"
            paragraph("结果与约束：" + result + (" " + auth if (auth := permission(route)) else ""))
    from sdd_interface_descriptions import format_interface_descriptions
    from sdd_interface_layout import format_interface_layout

    classify_interfaces(doc)
    format_interface_layout(doc)
    format_interface_descriptions(doc)
    return save_section(source, doc)
