"""Format section 4.5 in place, without rebuilding its interface contracts."""

import re
from io import BytesIO

from docx import Document
from docx.enum.table import WD_TABLE_ALIGNMENT, WD_CELL_VERTICAL_ALIGNMENT, WD_ROW_HEIGHT_RULE
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.oxml import OxmlElement
from docx.oxml.ns import qn
from docx.shared import Cm, Pt
from docx.table import Table
from docx.text.paragraph import Paragraph
from sdd_interface_ids import INTERFACE_ID

START = "接口设计"
END = "物模型设计"
DETAIL = "接口说明"
PROVIDERS = ("联邦学习平台", "参与方运行时代理", "共享支撑服务", "训练引擎", "聚合引擎")
PROVIDER_TITLES = {provider: provider + "提供的接口" for provider in PROVIDERS}
CONTRACT = re.compile(rf"/{INTERFACE_ID}$")
OPERATION = re.compile(r"^(.+?：)((?:GET|POST|PUT|PATCH|DELETE) /\S+)$")
OPERATION_TABLE = "Federation learning interface operation"


def is_operation_table(node):
    desc = node.find(f'{qn("w:tblPr")}/{qn("w:tblDescription")}')
    return desc is not None and desc.get(qn("w:val")) == OPERATION_TABLE


def font(run, *, size=10.5, bold=False, east="宋体", latin="Times New Roman"):
    run.font.name = latin
    run.font.size = Pt(size)
    run.bold = bold
    run._r.get_or_add_rPr().rFonts.set(qn("w:eastAsia"), east)


def set_layout(p, *, before=0, after=3, line=1.35, left=0, first=0, align=WD_ALIGN_PARAGRAPH.LEFT):
    fmt = p.paragraph_format
    fmt.space_before, fmt.space_after, fmt.line_spacing = Pt(before), Pt(after), line
    fmt.left_indent, fmt.right_indent, fmt.first_line_indent = Cm(left), Pt(0), Cm(first)
    fmt.widow_control = True
    p.alignment = align
    props = p._p.get_or_add_pPr()
    ind = props.get_or_add_ind()
    for key in ("firstLineChars", "hangingChars", "leftChars", "rightChars"):
        ind.attrib.pop(qn(f"w:{key}"), None)
    if not first:
        ind.set(qn("w:firstLineChars"), "0")
    ind.set(qn("w:leftChars"), str(round(left * 215)))
    ind.set(qn("w:rightChars"), "0")
    grid = props.find(qn("w:snapToGrid"))
    if grid is None:
        grid = OxmlElement("w:snapToGrid")
        props.insert_element_before(grid, "w:spacing", "w:ind")
    grid.set(qn("w:val"), "0")


def mark(parent, name):
    tag = qn("w:" + name)
    if parent.find(tag) is None:
        parent.append(OxmlElement("w:" + name))


def format_table(table):
    headers = [cell.text.strip() for cell in table.rows[0].cells]
    if headers[0] == "接口标识":
        widths = (4.7, 4.0, 3.2, 4.1)
    elif headers == ["序号", "接口类别", "标识规则", "说明章节"]:
        widths = (1.0, 3.0, 8.0, 4.0)
    elif len(headers) == 8 and headers[1] == "接口名称/标识":
        widths = (1.0, 3.2, 2.9, 1.95, 1.95, 1.75, 1.45, 1.8)
    else:
        rest = [column.width.cm for column in list(table.columns)[1:]]
        widths = (1.0, *(15 * width / sum(rest) for width in rest))
    table.alignment = WD_TABLE_ALIGNMENT.CENTER
    table.autofit = False
    props = table._tbl.tblPr
    for name in ("tblpPr", "tblInd"):
        if (node := props.find(qn("w:" + name))) is not None:
            props.remove(node)
    prop = props.find(qn("w:tblW"))
    if prop is not None:
        prop.set(qn("w:type"), "dxa")
        prop.set(qn("w:w"), str(Cm(16).twips))
    borders = props.find(qn("w:tblBorders"))
    if borders is None:
        borders = OxmlElement("w:tblBorders")
        props.insert_element_before(borders, "w:shd", "w:tblLayout", "w:tblCellMar", "w:tblLook")
    for edge in ("top", "left", "bottom", "right", "insideH", "insideV"):
        line = borders.find(qn("w:" + edge))
        if line is None:
            line = OxmlElement("w:" + edge)
            borders.append(line)
        line.set(qn("w:val"), "single")
        line.set(qn("w:sz"), "4")
        line.set(qn("w:color"), "000000")
    for column, width in zip(table.columns, widths, strict=True):
        column.width = Cm(width)
    for index, row in enumerate(table.rows):
        row.height_rule = WD_ROW_HEIGHT_RULE.AUTO
        row.height = None
        row_props = row._tr.get_or_add_trPr()
        mark(row_props, "cantSplit")
        if index == 0:
            mark(row_props, "tblHeader")
        seen = set()
        for column, cell in enumerate(row.cells):
            if cell._tc in seen:
                continue
            seen.add(cell._tc)
            cell.width = Cm(sum(widths[column:column + cell._tc.grid_span]))
            cell.vertical_alignment = WD_CELL_VERTICAL_ALIGNMENT.CENTER if index == 0 else WD_CELL_VERTICAL_ALIGNMENT.TOP
            cell_props = cell._tc.get_or_add_tcPr()
            margins = cell_props.find(qn("w:tcMar"))
            if margins is None:
                margins = OxmlElement("w:tcMar")
                cell_props.insert_element_before(margins, "w:textDirection", "w:tcFitText", "w:vAlign")
            for direction, amount in (("top", 45), ("left", 65), ("bottom", 45), ("right", 65)):
                item = margins.find(qn("w:" + direction))
                if item is None:
                    item = OxmlElement("w:" + direction)
                    margins.append(item)
                item.set(qn("w:w"), str(amount))
                item.set(qn("w:type"), "dxa")
            for p in cell.paragraphs:
                center = index == 0 or column == 0
                set_layout(p, after=0, line=1.2, align=WD_ALIGN_PARAGRAPH.CENTER if center else WD_ALIGN_PARAGRAPH.LEFT)
                p.paragraph_format.keep_together = False
                p.paragraph_format.keep_with_next = index == 0
                for run in p.runs:
                    font(run, size=10 if index == 0 else 9.5, bold=not index)


def format_interface_layout(doc):
    body = list(doc.element.body)
    text = lambda n: "".join(node.text or "" for node in n.iter(qn("w:t"))).strip()
    first = next(i for i, node in enumerate(body) if text(node) == START)
    last = next(i for i, node in enumerate(body[first + 1:], first + 1) if text(node) == END)
    active = False
    providers_seen = set()
    for index in range(first + 1, last):
        node = body[index]
        if node.tag == qn("w:tbl"):
            if not is_operation_table(node):
                format_table(Table(node, doc._body))
            continue
        if node.tag != qn("w:p"):
            continue
        p = Paragraph(node, doc._body)
        title = p.text.strip()
        if next(node.iter(qn("w:drawing"), qn("w:pict")), None) is not None:
            set_layout(p, before=4, after=2, align=WD_ALIGN_PARAGRAPH.CENTER)
            p.paragraph_format.keep_with_next = True
            continue
        if not title:
            continue
        if title == DETAIL:
            active = True
            p.paragraph_format.page_break_before = True
        if p.style.name.startswith("Heading "):
            set_layout(p, before=9, after=4, line=1.25)
            p.paragraph_format.keep_with_next = True
            continue
        is_table_caption = bool(re.match(r"^表\s*\d", title)) and body[index + 1].tag == qn("w:tbl")
        is_figure_caption = bool(re.match(r"^图\s*\d", title))
        if is_table_caption or is_figure_caption:
            set_layout(p, before=5 if is_table_caption else 0, after=4 if is_table_caption else 7,
                       line=1.2, align=WD_ALIGN_PARAGRAPH.CENTER)
            p.paragraph_format.keep_with_next = is_table_caption
            for run in p.runs:
                font(run, bold=True, east="黑体")
            continue
        if active and CONTRACT.search(title):
            run_panel = Paragraph(body[index + 1], doc._body)
            provider = run_panel.text.removeprefix("提供方：").split("（", 1)[0]
            if provider in PROVIDER_TITLES and provider not in providers_seen:
                providers_seen.add(provider)
                if text(body[index - 1]) != PROVIDER_TITLES[provider]:
                    heading = p.insert_paragraph_before(PROVIDER_TITLES[provider], style="Heading 6")
                    set_layout(heading, before=11, after=5, line=1.25)
                    heading.paragraph_format.keep_with_next = True
            set_layout(p, before=9, after=3, line=1.2)
            p.paragraph_format.keep_with_next = True
            for run in p.runs:
                font(run, size=11, bold=True, east="黑体")
        elif active and (operation := OPERATION.fullmatch(title)):
            set_layout(p, before=4, after=2, line=1.25)
            p.paragraph_format.keep_with_next = True
            p.clear()
            font(p.add_run(operation[1]), bold=True)
            font(p.add_run(operation[2]), size=9.5, latin="Consolas")
        elif active and title.startswith(("提供方：", "调用方：", "请求：", "响应：", "结果与约束：")):
            label, content = title.split("：", 1)
            set_layout(p, after=4 if label == "结果与约束" else 2, line=1.28, left=0.45)
            p.paragraph_format.keep_with_next = label in ("提供方", "调用方")
            p.clear()
            font(p.add_run(label + "："), bold=True)
            font(p.add_run(content))
        else:
            set_layout(p)
            for run in p.runs:
                font(run, bold=bool(run.bold))
    if providers_seen != set(PROVIDERS):
        raise ValueError("Missing provider groups in the federation learning interface chapter")


def revise_interface_layout(source):
    from sdd_interfaces import save_section

    document = Document(BytesIO(source))
    format_interface_layout(document)
    return save_section(source, document)
