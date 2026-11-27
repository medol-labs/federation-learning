"""Localize business labels in existing interface lists and operation headers."""

import re
from io import BytesIO

from docx import Document
from docx.oxml.ns import qn

from sdd_interface_layout import CONTRACT, is_operation_table
from sdd_interfaces import FL_HEADING, NEXT_SECTION, interface_terms, save_section


def text(node):
    return "".join(run.text or "" for run in node.iter(qn("w:t")))


def replace_labels(node, pattern, labels):
    runs = []
    end = 0
    for run in node.iter(qn("w:t")):
        if run.text:
            runs.append((end, end + len(run.text), run))
            end = runs[-1][1]
    for match in reversed(list(pattern.finditer(text(node)))):
        first = True
        for start, stop, run in runs:
            if stop <= match.start() or start >= match.end():
                continue
            value = run.text or ""
            label = labels[match[0]] if first else ""
            run.text = value[:max(match.start() - start, 0)] + label + value[match.end() - start:]
            first = False


def localize_interface_labels(doc):
    labels = interface_terms()
    terms = "|".join(re.escape(term) for term in sorted(labels, key=len, reverse=True))
    pattern = re.compile(r"(?<![A-Za-z0-9_])(?:" + terms + r")(?![A-Za-z0-9_])")
    nodes = list(doc.element.body)
    start = next(i for i, node in enumerate(nodes) if text(node).strip() == FL_HEADING)
    end = next(i for i in range(start + 1, len(nodes)) if text(nodes[i]).strip() == NEXT_SECTION)
    lists = operations = 0
    for node in nodes[start + 1:end]:
        if node.tag == qn("w:p") and CONTRACT.search(text(node).strip()):
            replace_labels(node, pattern, labels)
        elif is_operation_table(node):
            header = node.find(qn("w:tr"))
            if header.find(f'{qn("w:trPr")}/{qn("w:tblHeader")}') is None:
                raise ValueError("Interface operation must have a running header")
            replace_labels(header, pattern, labels)
            operations += 1
        elif node.tag == qn("w:tbl"):
            rows = node.findall(qn("w:tr"))
            headers = [text(cell) for cell in rows[0].findall(qn("w:tc"))]
            if headers != ["接口标识", "接口名称", "调用方及图示关系", "主要操作"]:
                continue
            for row in rows[1:]:
                cells = row.findall(qn("w:tc"))
                for cell in (cells[1], cells[3]):
                    for paragraph in cell.iter(qn("w:p")):
                        replace_labels(paragraph, pattern, labels)
            lists += 1
    if not lists or not operations:
        raise ValueError("Missing federation learning interface lists or operations")


def revise_interface_terms(source):
    doc = Document(BytesIO(source))
    localize_interface_labels(doc)
    return save_section(source, doc)
