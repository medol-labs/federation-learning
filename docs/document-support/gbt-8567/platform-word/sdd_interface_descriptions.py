"""Lay out the existing 4.5 interface descriptions without rewriting contracts."""

import re
from io import BytesIO

from docx import Document
from docx.enum.table import WD_TABLE_ALIGNMENT
from docx.oxml import OxmlElement
from docx.oxml.ns import qn
from docx.shared import Cm, Pt
from docx.table import Table
from docx.text.paragraph import Paragraph

from sdd_interface_layout import (
    CONTRACT, DETAIL, END, OPERATION, OPERATION_TABLE, START, font, is_operation_table, mark, set_layout,
)

LABELS = ("调用方", "请求", "响应", "结果与约束")
FIELD_BREAK = re.compile(r"(?<=[；。])(?=\s*[A-Za-z_][\w.\[\]-]*：)")


def add_operation_table(doc, values):
    table = doc.add_table(rows=len(LABELS), cols=2)
    table.alignment = WD_TABLE_ALIGNMENT.CENTER
    table.autofit = False
    props = table._tbl.tblPr
    for name, value in (("type", "dxa"), ("w", str(Cm(16).twips))):
        props.find(qn("w:tblW")).set(qn("w:" + name), value)
    desc = OxmlElement("w:tblDescription")
    desc.set(qn("w:val"), OPERATION_TABLE)
    props.append(desc)
    borders = OxmlElement("w:tblBorders")
    props.insert_element_before(borders, "w:tblLayout")
    for edge in ("top", "left", "bottom", "right", "insideH", "insideV"):
        line = OxmlElement("w:" + edge)
        for name, value in (("val", "single"), ("sz", "4"), ("color", "A0A0A0")):
            line.set(qn("w:" + name), value)
        borders.append(line)
    widths = (Cm(2.5), Cm(13.5))
    for column, width in zip(table.columns, widths, strict=True):
        column.width = width
    for label, value, row in zip(LABELS, values, table.rows, strict=True):
        if len(value) <= 240:
            mark(row._tr.get_or_add_trPr(), "cantSplit")
        for column, (cell, width) in enumerate(zip(row.cells, widths, strict=True)):
            cell.width = width
            if column == 0:
                shading = OxmlElement("w:shd")
                shading.set(qn("w:fill"), "F2F2F2")
                cell._tc.get_or_add_tcPr().append(shading)
            p = cell.paragraphs[0]
            set_layout(p, before=2, after=2, line=1.2)
            p.paragraph_format.keep_with_next = False
            p.paragraph_format.keep_together = False
            if column == 0:
                font(p.add_run(label), bold=True)
                continue
            lines = FIELD_BREAK.split(value) if label in ("请求", "响应") else [value]
            for line_index, line in enumerate(lines):
                if line_index:
                    p.add_run().add_break()
                font(p.add_run(line))
    return table


def add_running_header(table, title, identifier):
    header = table.add_row()
    table.rows[0]._tr.addprevious(header._tr)
    props = header._tr.get_or_add_trPr()
    mark(props, "cantSplit")
    mark(props, "tblHeader")
    cell = header.cells[0].merge(header.cells[1])
    shading = OxmlElement("w:shd")
    shading.set(qn("w:fill"), "E7E7E7")
    cell._tc.get_or_add_tcPr().append(shading)
    if title._p.getprevious().tag == qn("w:tbl"):
        # A paragraph keeps adjacent operation tables independent in Word.
        gap = title.insert_paragraph_before()
        set_layout(gap, after=0, line=Pt(5))
        gap.paragraph_format.keep_with_next = True
    title._p.addnext(table._tbl)
    cell.paragraphs[0]._p.addnext(title._p)
    cell._tc.remove(cell.paragraphs[0]._p)
    set_layout(title, before=3, after=3, line=1.15)
    title.paragraph_format.keep_together = True
    title.paragraph_format.keep_with_next = True
    font(title.add_run("  [" + identifier + "]"), bold=True)


def format_interface_descriptions(doc):
    body = list(doc.element.body)

    def text(node):
        return "".join(t.text or "" for t in node.iter(qn("w:t")))

    first = next(i for i, node in enumerate(body) if text(node).strip() == START)
    end = next(i for i in range(first + 1, len(body)) if text(body[i]).strip() == END)
    begin = next(i for i in range(first + 1, end) if text(body[i]).strip() == DETAIL)
    operations = 0
    identifier = None
    for index in range(begin + 1, end):
        node = body[index]
        if is_operation_table(node):
            if node.find(f'{qn("w:tr")}/{qn("w:trPr")}/{qn("w:tblHeader")}') is not None:
                operations += 1
            continue
        if node.tag == qn("w:p") and (contract := CONTRACT.search(text(node).strip())):
            identifier = contract[0][1:]
            following = body[index + 1]
            group = (node, following) if text(following).startswith("提供方：") else (node,)
            for preceding in group:
                fmt = Paragraph(preceding, doc._body).paragraph_format
                fmt.keep_with_next = True
                fmt.keep_together = True
            continue
        if node.tag != qn("w:p") or not OPERATION.fullmatch(text(node).strip()):
            continue
        if identifier is None:
            raise ValueError(f"Missing interface identifier before {text(node)}")
        operations += 1
        following = body[index + 1]
        if is_operation_table(following):
            table = Table(following, doc._body)
        else:
            details = body[index + 1:index + 1 + len(LABELS)]
            values = []
            for label, detail in zip(LABELS, details, strict=True):
                prefix = label + "："
                if detail.tag != qn("w:p") or not (value := text(detail)).startswith(prefix):
                    raise ValueError(f"Missing {label} after {text(node)}")
                values.append(value.removeprefix(prefix))
            table = add_operation_table(doc, values)
            for detail in details:
                detail.getparent().remove(detail)
        add_running_header(table, Paragraph(node, doc._body), identifier)
    if not operations:
        raise ValueError("No operations found in the federation learning interface descriptions")


def revise_interface_descriptions(source):
    from sdd_interfaces import save_section

    doc = Document(BytesIO(source))
    format_interface_descriptions(doc)
    return save_section(source, doc)
