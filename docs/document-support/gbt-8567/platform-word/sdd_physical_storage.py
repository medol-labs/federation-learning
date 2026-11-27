"""Maintain the federation learning physical storage inventory."""

import re
from copy import deepcopy
from io import BytesIO

from docx import Document
from docx.enum.table import WD_CELL_VERTICAL_ALIGNMENT, WD_TABLE_ALIGNMENT
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.oxml import OxmlElement
from docx.oxml.ns import qn
from docx.shared import Cm, Pt
from docx.table import Table

from sdd_interfaces import save_section


TITLE = "联邦学习各部署单元存储对象清单"
REFERENCE = "联邦学习各服务的完整 PostgreSQL 表结构见《联邦孪生平台数据库设计说明》V0.5 第 4.3.1 节「联邦学习模块」。"
STORAGE = (
    ("部署单元", "PostgreSQL", "UmaDB", "文件存储"),
    ("平台服务", "组织与联邦、特征架构、训练作业与轮次、模型工件登记等查询数据及事件处理位置。",
     "组织与成员资格、训练编排、模型更新验收与聚合、模型登记等平台业务事件。",
     "聚合引擎工作目录保存聚合输出等作业文件；登记供交付的模型以支撑服务中的工件引用关联，不在平台数据库中保存文件本体。"),
    ("运行时代理", "本地数据集绑定、数据校验与轮次执行等查询数据，以及本地身份权限数据和事件处理位置。",
     "运行时接入、本地数据集绑定与校验、执行计划接收、本地训练与模型更新上报等事件。",
     "机构侧受控目录保存原始数据集；本地训练工作目录保存模型更新、个性化模型及其他执行产物。原始样本不上传到平台。"),
    ("支撑服务", "字典、通知、数据交换、身份权限及上传文件目录等查询或管理数据，以及事件处理位置。",
     "字典、通知、导入导出、身份权限和上传文件生命周期等支撑业务事件。",
     "保存上传文件、已上传的模型工件及数据交换文件本体，通过文件标识与元数据对应。"),
)
CAPTION = re.compile(r"^表\s+(\d+(?:-\d+)?)\s+")


def insert_inventory_table(doc, template, reference):
    table = deepcopy(template)
    ref = Table(table, doc._body)
    if len(ref.columns) != 4 or len(ref.rows) < len(STORAGE):
        raise ValueError("存储清单的四列表格样式不存在")
    for row in list(table.tr_lst)[len(STORAGE):]:
        table.remove(row)
    for tag in ("w:bookmarkStart", "w:bookmarkEnd"):
        for node in list(table.iter(qn(tag))):
            node.getparent().remove(node)
    ref.alignment = WD_TABLE_ALIGNMENT.CENTER
    ref.autofit = False
    widths = tuple(Cm(c) for c in (2.45, 4.25, 3.9, 4.9))
    for col, width in zip(ref.columns, widths, strict=True):
        col.width = width
    for index, (row, values) in enumerate(zip(ref.rows, STORAGE, strict=True)):
        props = row._tr.get_or_add_trPr()
        for tag in ("w:trHeight", "w:tblHeader"):
            for child in props.findall(qn(tag)):
                props.remove(child)
        if props.find(qn("w:cantSplit")) is None:
            props.insert(0, OxmlElement("w:cantSplit"))
        if index == 0:
            props.append(OxmlElement("w:tblHeader"))
        for column, (cell, value, width) in enumerate(zip(row.cells, values, widths, strict=True)):
            cell.width = width
            cell.vertical_alignment = WD_CELL_VERTICAL_ALIGNMENT.CENTER
            paragraph = cell.paragraphs[0]
            for extra in cell.paragraphs[1:]:
                cell._tc.remove(extra._p)
            paragraph.clear()
            run = paragraph.add_run(value)
            run.font.name = "Times New Roman"
            run.font.size = Pt(10.5)
            run._element.get_or_add_rPr().rFonts.set(qn("w:eastAsia"), "宋体")
            fmt = paragraph.paragraph_format
            fmt.space_before = fmt.space_after = Pt(3)
            fmt.first_line_indent = fmt.left_indent = fmt.right_indent = Pt(0)
            fmt.line_spacing = Pt(15)
            fmt.keep_with_next = index < len(STORAGE) - 1
            props = paragraph._p.get_or_add_pPr()
            grid = props.find(qn("w:snapToGrid"))
            if grid is None:
                grid = OxmlElement("w:snapToGrid")
                props.insert_element_before(grid, "w:spacing", "w:ind", "w:jc")
            grid.set(qn("w:val"), "0")
            paragraph.alignment = (
                WD_ALIGN_PARAGRAPH.CENTER if index == 0 or column == 0 else WD_ALIGN_PARAGRAPH.LEFT
            )
    reference._p.addprevious(table)


def revise_physical_storage(source):
    doc = Document(BytesIO(source))
    paragraphs = doc.paragraphs
    starts = [i for i, p in enumerate(paragraphs) if p.text.strip() == "数据库物理设计"]
    if len(starts) != 1:
        raise ValueError("无法唯一定位数据库物理设计")
    start = starts[0]
    if paragraphs[start + 1].text.strip() != "联邦学习":
        raise ValueError("数据库物理设计缺少联邦学习小节")
    end = next((i for i in range(start + 2, len(paragraphs)) if paragraphs[i].text.strip() == "医疗数字人"), None)
    if end is None:
        raise ValueError("无法定位联邦学习物理设计小节末尾")
    section = paragraphs[start + 2:end]
    if any(p.text.strip().endswith(TITLE) for p in section):
        return source
    ref = next((p for p in section if p.text.startswith("查询表由服务内的 JPA")), None)
    model = next((p for p in paragraphs[end + 1:] if p.text.strip().endswith("医疗数字人数据库独立数据元素表")
                  and p._p.getnext() is not None and p._p.getnext().tag == qn("w:tbl")), None)
    captions = [(i, m[1]) for i, p in enumerate(paragraphs) if (m := CAPTION.match(p.text.strip()))]
    prior = next((number for i, number in reversed(captions) if i < start and "-" not in number), None)
    if ref is None or model is None or prior is None:
        raise ValueError("无法定位联邦学习存储清单的插入位置或题注样式")
    used = {number for _, number in captions}
    suffix = next(n for n in range(1, len(used) + 2) if f"{prior}-{n}" not in used)
    number = f"{prior}-{suffix}"
    intro = ref.insert_paragraph_before(f"各部署单元负责保存的主要对象见表 {number}。", style=ref.style)
    intro.paragraph_format.keep_with_next = True
    caption = ref.insert_paragraph_before(f"表 {number} {TITLE}", style=model.style)
    caption.alignment = WD_ALIGN_PARAGRAPH.CENTER
    caption.paragraph_format.keep_with_next = True
    insert_inventory_table(doc, model._p.getnext(), ref)
    runs = [run for run in ref.runs if run.text]
    runs[0].text = REFERENCE
    for run in runs[1:]:
        run.text = ""
    return save_section(source, doc)
