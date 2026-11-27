"""Insert the scoped behavior and presentation decisions in chapter 3."""

import json
from io import BytesIO
from pathlib import Path

from docx import Document
from docx.oxml import OxmlElement
from docx.oxml.ns import qn
from docx.shared import Pt

from sdd_interfaces import save_section


def update_federation_decision(doc, key):
    blocks = json.loads(Path(__file__).with_name("sdd-federation-decisions.json").read_text(encoding="utf-8"))
    block = blocks[key]
    paragraphs = doc.paragraphs
    start = [i for i, p in enumerate(paragraphs) if p.text.strip() == block["after"]]
    end = [i for i, p in enumerate(paragraphs) if p.text.strip() == block["before"]]
    if len(start) != 1 or len(end) != 1 or start[0] >= end[0]:
        raise ValueError(f"Missing or ambiguous federation decision boundaries: {key}")
    target = paragraphs[end[0]]
    lines = [block["title"], *(f"（{num}）{label}。{value}"
                              for num, (label, value) in enumerate(block["items"], 1))]
    existing = [i for i in range(start[0] + 1, end[0]) if paragraphs[i].text.strip() == block["title"]]
    if existing:
        if len(existing) != 1:
            raise ValueError(f"Duplicate federation decision: {key}")
        old = paragraphs[existing[0]:end[0]]
        if [p.text for p in old] == lines:
            return
        if len(old) != len(lines) or any(p._p.getnext() is not q._p for p, q in zip(old, [*old[1:], target])):
            raise ValueError(f"Unexpected content in federation decision: {key}")
        for p in old:
            p._p.getparent().remove(p._p)
    heading = target.insert_paragraph_before(lines[0], style="Heading 4")
    heading.paragraph_format.keep_with_next = True
    for value in lines[1:]:
        p = target.insert_paragraph_before(value)
        p.paragraph_format.line_spacing = 1.5
        p.paragraph_format.space_after = Pt(3)
        grid = OxmlElement("w:snapToGrid")
        grid.set(qn("w:val"), "0")
        p._p.get_or_add_pPr().insert_element_before(grid, "w:spacing")


def revise_federation_decisions(source):
    doc = Document(BytesIO(source))
    for key in ("behavior", "presentation"):
        update_federation_decision(doc, key)
    return save_section(source, doc)
