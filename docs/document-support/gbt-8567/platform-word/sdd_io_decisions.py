"""Update only the federation learning input/output decision section (3.1.1)."""

import json
from io import BytesIO
from pathlib import Path

from docx import Document
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.oxml import OxmlElement
from docx.oxml.ns import qn
from docx.shared import Cm, Pt
from docx.table import Table

from sdd_federation_decisions import update_federation_decision
from sdd_interfaces import save_section

START = "联邦学习模块输入/输出设计决策/LBLS-GN-LBXX"
END = "医疗数字人模块输入/输出设计决策/LBLS-GN-SZR"


def compact(paragraph):
    fmt = paragraph.paragraph_format
    fmt.first_line_indent = fmt.left_indent = fmt.right_indent = Pt(0)
    fmt.space_before, fmt.space_after, fmt.line_spacing = Pt(0), Pt(2), 1.2
    props = paragraph._p.get_or_add_pPr()
    indent = props.get_or_add_ind()
    for attribute in ("firstLineChars", "leftChars", "rightChars"):
        indent.set(qn(f"w:{attribute}"), "0")
    grid = OxmlElement("w:snapToGrid")
    grid.set(qn("w:val"), "0")
    props.insert_element_before(grid, "w:spacing", "w:ind")


def revise_io_decisions(source):
    content = json.loads(Path(__file__).with_name("sdd-io-decisions.json").read_text(encoding="utf-8"))
    doc = Document(BytesIO(source))
    anchors = [p for p in doc.paragraphs if p.text.strip() in (START, END)]
    if [p.text.strip() for p in anchors] != [START, END]:
        raise ValueError("Missing or ambiguous boundaries for 3.1.1")
    first, end = anchors
    nodes = []
    node = first._p.getnext()
    while node is not end._p:
        if node is None:
            raise ValueError("Unterminated section 3.1.1")
        nodes.append(node)
        node = node.getnext()
    for node in nodes:
        if node.tag == qn("w:p") and not list(node.iter(qn("w:drawing"))):
            continue
        if node.tag == qn("w:tbl") and tuple(c.text for c in Table(node, first._parent).rows[0].cells
                                             ) == tuple(content["headers"]):
            continue
        raise ValueError("Unexpected content in 3.1.1, not replacing it")
    for node in nodes:
        node.getparent().remove(node)

    end.insert_paragraph_before(content["overview"])
    end.insert_paragraph_before(content["reference"]).paragraph_format.keep_with_next = True
    caption = end.insert_paragraph_before()
    compact(caption)
    caption.alignment = WD_ALIGN_PARAGRAPH.CENTER
    caption.paragraph_format.keep_with_next = True
    caption.add_run(content["caption"]).font.size = Pt(12)

    widths = (2.75, 3.65, 3.6, 5.0)
    table = doc.add_table(rows=0, cols=len(widths))
    end._p.addprevious(table._tbl)
    table.style = "Table Grid"
    table.autofit = False
    for column, width in zip(table.columns, widths, strict=True):
        column.width = Cm(width)
    for index, values in enumerate([content["headers"], *content["rows"]]):
        row = table.add_row()
        properties = row._tr.get_or_add_trPr()
        properties.append(OxmlElement("w:cantSplit"))
        if index == 0:
            properties.append(OxmlElement("w:tblHeader"))
        for cell, text, width in zip(row.cells, values, widths, strict=True):
            cell.width = Cm(width)
            p = cell.paragraphs[0]
            compact(p)
            p.alignment = WD_ALIGN_PARAGRAPH.CENTER if index == 0 else WD_ALIGN_PARAGRAPH.LEFT
            run = p.add_run(text)
            run.font.size, run.bold = Pt(10.5), index == 0
    update_federation_decision(doc, "behavior")
    return save_section(source, doc)
