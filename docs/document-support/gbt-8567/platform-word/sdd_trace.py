"""Trace the current business requirements to the actual section 5.1 headings."""

import json
import re
from collections import defaultdict
from copy import deepcopy
from io import BytesIO
from pathlib import Path

from docx import Document
from docx.oxml import OxmlElement
from docx.oxml.ns import qn
from docx.shared import Pt
from docx.table import _Row

from sdd_detail import DESIGN_ID, DESIGN_TITLE, START, END
from sdd_interfaces import save_section

BUNDLE = Path(__file__).resolve().parent
REQUIREMENT = "3.4.1 联邦学习模块能力需求/LBLS-GN-LBXX"
DESIGN = "5.1 联邦学习模块详细设计"
REVERSE = "逆向追溯"


def requirement_mapping():
    content = json.loads((BUNDLE / "srs/text.json").read_text(encoding="utf-8"))
    paragraphs = content["parts"]["word/document.xml"]
    start = paragraphs.index("联邦学习模块能力需求/LBLS-GN-LBXX")
    end = paragraphs.index("目录、看板与状态视图查询通用要求", start)
    mapping = json.loads((BUNDLE / "sdd-trace-design-map.json").read_text(encoding="utf-8"))
    groups = {}
    active = None
    for index in range(start + 1, end):
        text, following = paragraphs[index:index + 2]
        if not isinstance(text, str):
            continue
        if match := re.fullmatch(r"(.+)/(LBLS-GN-LBXX-([A-Z]+))", text):
            active = match[3]
            groups[active] = (match[1], match[2], [])
        elif active and re.fullmatch(r"（\d+）[^：。；]+", text) and isinstance(following, str) and following.startswith("说明"):
            groups[active][2].append(text)
    if set(groups) != set(mapping):
        raise ValueError("Current SRS groups do not match the federation trace map")
    for code, (title, identifier, functions) in groups.items():
        for name, operations in zip(functions or [title], mapping[code], strict=True):
            requirement = f"3.4.1 {title}/{identifier} {name}" if functions else f"3.4.1 {title}/{identifier}"
            yield requirement, [f"F{number:04d}" for number in operations]


def design_headings(doc):
    paragraphs = doc.paragraphs
    start = next(i for i, p in enumerate(paragraphs) if p.text.strip() in (START, DESIGN_TITLE))
    end = next(i for i, p in enumerate(paragraphs[start + 1:], start + 1) if p.text.strip() == END)
    headings = {}
    counters = [0, 0, 0]
    for p in paragraphs[start + 1:end]:
        if not (style := re.fullmatch(r"Heading ([345])", p.style.name)):
            continue
        depth = int(style[1]) - 3
        counters[depth] += 1
        counters[depth + 1:] = [0] * (2 - depth)
        title, sep, identifier = p.text.rpartition("/")
        if not sep or not identifier.startswith((DESIGN_ID + "-", "FL-SD-OP-")):
            continue
        number_id = identifier.rsplit("-", 1)[-1]
        if not re.fullmatch(r"F\d{4}", number_id):
            continue
        if number_id in headings:
            raise ValueError(f"Duplicate operation design {identifier}")
        number = ".".join(str(n) for n in counters[:depth + 1])
        headings[number_id] = f"5.1.{number} {title}/{identifier}"
    return headings


def replace_trace_rows(doc, *, reverse, mappings):
    left, right = (DESIGN, REQUIREMENT) if reverse else (REQUIREMENT, DESIGN)
    matches = []
    for table in doc.tables:
        if not table.rows or len(table.columns) < 3:
            continue
        row = table.rows[1] if len(table.rows) > 1 else table.rows[0]
        cells = row.cells
        if len(cells) >= 3 and (cells[1].text.strip(), cells[2].text.strip()) == (left, right):
            matches.append((table, row))
        elif len(cells) >= 3 and cells[0].text.strip() == "1.1" and any(
            value in cells[1].text for value in ("5.1.", "3.4.1 ")
        ) and cells[1].text.startswith("5.1." if reverse else "3.4.1 "):
            matches.append((table, row))
    if len(matches) != 1:
        raise ValueError(f"Missing or ambiguous {'reverse' if reverse else 'forward'} trace table")
    table, row = matches[0]
    source = row._tr
    for index, (first, second) in enumerate(mappings, 1):
        node = deepcopy(source)
        source.addprevious(node)
        new = _Row(node, table)
        for cell, value in zip(new.cells[:3], (f"1.{index}", first, second), strict=True):
            p = cell.paragraphs[0]
            runs = [run for run in p.runs if run.text]
            runs[0].text = value
            for run in runs[1:]:
                run.text = ""
        numbering = new.cells[0]
        properties = numbering._tc.get_or_add_tcPr()
        if properties.find(qn("w:noWrap")) is None:
            properties.insert_element_before(OxmlElement("w:noWrap"), "w:tcMar", "w:textDirection",
                                             "w:tcFitText", "w:vAlign")
        margins = properties.find(qn("w:tcMar"))
        if margins is None:
            margins = OxmlElement("w:tcMar")
            properties.insert_element_before(margins, "w:textDirection", "w:tcFitText", "w:vAlign")
        for edge in ("left", "right"):
            margin = margins.find(qn(f"w:{edge}"))
            if margin is None:
                margin = OxmlElement(f"w:{edge}")
                margins.append(margin)
            margin.set(qn("w:w"), "0")
            margin.set(qn("w:type"), "dxa")
        p = numbering.paragraphs[0]
        indentation = p._p.get_or_add_pPr().get_or_add_ind()
        for name in ("left", "leftChars", "right", "rightChars", "firstLine", "firstLineChars"):
            indentation.set(qn(f"w:{name}"), "0")
        run = next(run for run in p.runs if run.text)
        run.font.name = "Times New Roman"
        run.font.size = Pt(8.5)
    node = source
    while node is not None:
        following = node.getnext()
        row_number = _Row(node, table).cells[0].text.strip()
        if row_number != "1" and not re.fullmatch(r"1\.\d+", row_number):
            break
        node.getparent().remove(node)
        node = following


def revise_trace(source):
    doc = Document(BytesIO(source))
    headings = design_headings(doc)
    forward = []
    inverse = defaultdict(list)
    for requirement, operations in requirement_mapping():
        designs = [headings[operation] for operation in operations]
        forward.append((requirement, "；".join(designs)))
        for design in designs:
            inverse[design].append(requirement)
    replace_trace_rows(doc, reverse=False, mappings=forward)
    replace_trace_rows(doc, reverse=True, mappings=[
        (design, "；".join(inverse[design])) for design in headings.values() if design in inverse
    ])
    return save_section(source, doc)
