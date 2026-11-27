"""Apply federation learning decisions to sections 3.2 through 3.4."""

import json
import re
from io import BytesIO
from pathlib import Path

from docx import Document
from docx.oxml import OxmlElement
from docx.oxml.ns import qn
from docx.shared import Pt

from sdd_interface_outline import cached_entry
from sdd_interface_terms import replace_labels
from sdd_interfaces import save_section
from sdd_federation_decisions import update_federation_decision

START = "CSCI的数据库/数据文件决策"
END = "CSCI体系结构设计"
BOOKMARK_PREFIX = "SddDecisionToc"
SECURITY_TITLE = "联邦学习安全与保密设计决策"
MAINTAINABILITY_TITLE = "联邦学习扩展性与可维护性决策"
LEGACY_LABELS = {
    MAINTAINABILITY_TITLE: ("（1）训练与聚合扩展。", "（2）故障定位与恢复。"),
}
SECURITY_REFERENCE = "联邦学习的实施范围见本节单列的设计决策，不直接沿用"


def chapter3_config():
    return json.loads(Path(__file__).with_name("sdd-chapter3-decisions.json").read_text(encoding="utf-8"))


def decision_content(block):
    if "subsections" not in block:
        return [(None, value) for value in block["paragraphs"]]
    content = []
    for section in block["subsections"]:
        content.append((block["level"] + 1, section["title"]))
        content.extend((None, value) for value in section["paragraphs"])
    return content


def write_decision_content(target, content):
    for level, value in content:
        if level is not None:
            target.insert_paragraph_before(value, style=f"Heading {level}").paragraph_format.keep_with_next = True
            continue
        paragraph = target.insert_paragraph_before(value)
        paragraph.paragraph_format.line_spacing = 1.5
        paragraph.paragraph_format.space_after = Pt(3)
        grid = OxmlElement("w:snapToGrid")
        grid.set(qn("w:val"), "0")
        paragraph._p.get_or_add_pPr().insert_element_before(grid, "w:spacing")


def prior_decision_nodes(heading, target, content, *, legacy_labels=()):
    nodes = []
    node = heading._p.getnext()
    while node is not target._p:
        if node is None or node.tag != qn("w:p") or any(node.iter(qn("w:drawing"))):
            raise ValueError("Unexpected content in existing federation learning decisions")
        nodes.append(node)
        node = node.getnext()
    if legacy_labels:
        old = ["".join(t.text or "" for t in p.iter(qn("w:t"))) for p in nodes]
        if len(old) == len(legacy_labels) and all(
            value.startswith(label) for value, label in zip(old, legacy_labels, strict=True)
        ):
            return nodes
        if len(nodes) == len(content) and all(
            level is None or old[index] == value for index, (level, value) in enumerate(content)
        ):
            return nodes
    elif len(nodes) == len(content):
        return nodes
    raise ValueError("Unexpected structure in existing federation learning decisions")


def revise_decision_block(source, title):
    block = next(block for block in chapter3_config()["blocks"] if block["title"] == title)
    doc = Document(BytesIO(source))
    anchors = [p for p in doc.paragraphs if p.text.strip() in (title, block["before"])]
    if [p.text.strip() for p in anchors] != [title, block["before"]]:
        raise ValueError(f"Missing or ambiguous federation learning decision boundaries: {title}")
    heading, target = anchors
    content = decision_content(block)
    for node in prior_decision_nodes(heading, target, content, legacy_labels=LEGACY_LABELS.get(title, ())):
        node.getparent().remove(node)
    write_decision_content(target, content)
    return save_section(source, doc)


def remove_security_block(doc):
    paragraphs = doc.paragraphs
    headings = [p for p in paragraphs if p.text.strip() == SECURITY_TITLE and p.style.name == "Heading 3"]
    if len(headings) > 1:
        raise ValueError("Ambiguous federation learning security decision heading")
    if headings:
        heading = headings[0]
        target = next(p for p in paragraphs if p.text.strip() == "其它设计决策" and p.style.name == "Heading 2")
        content = [(level, value)
                   for title in ("安全性设计决策", "保密性设计决策", "数据私密性设计决策", "审计、会话与交付约束")
                   for level, value in ((4, title), (None, ""), (None, ""))]
        legacy = ("（1）身份与授权。", "（2）数据与工件访问。", "（3）传输与执行边界。", "（4）追踪与交付配置。")
        nodes = prior_decision_nodes(heading, target, content, legacy_labels=legacy)
        for node in [heading._p, *nodes]:
            node.getparent().remove(node)
    for p in paragraphs:
        if p.style.name.lower() == "toc 3" and p.text.split("\t", 1)[0].strip() == f"3.3.1 {SECURITY_TITLE}":
            p._p.getparent().remove(p._p)
        elif SECURITY_REFERENCE in p.text:
            replace_labels(p._p, re.compile(re.escape(SECURITY_REFERENCE)),
                           {SECURITY_REFERENCE: "联邦学习不直接沿用"})


def remove_security_decisions(source):
    doc = Document(BytesIO(source))
    remove_security_block(doc)
    return save_section(source, doc)


def revise_maintainability_decisions(source):
    return revise_decision_block(source, MAINTAINABILITY_TITLE)


def revise_chapter3_decisions(source):
    config = chapter3_config()
    doc = Document(BytesIO(source))
    remove_security_block(doc)
    ps = doc.paragraphs
    start = next(i for i, p in enumerate(ps) if p.text.strip() == START)
    end = next(i for i, p in enumerate(ps[start + 1:], start + 1) if p.text.strip() == END)
    paragraphs = ps[start:end + 1]
    blocks = config["blocks"]
    already_revised = any(p.text == blocks[0]["title"] for p in paragraphs)

    for old, new in config["edits"]:
        matches = [p for p in paragraphs if old in p.text]
        if not matches and already_revised and (not new or any(new in p.text for p in paragraphs)):
            continue
        if len(matches) != 1 or matches[0].text.count(old) != 1:
            raise ValueError(f"Missing or ambiguous Chapter 3 decision: {old[:40]}")
        p = matches[0]
        runs = [run for run in p.runs if run.text]
        runs[0].text = p.text.replace(old, new)
        for run in runs[1:]:
            run.text = ""

    for p in ps[:start]:
        if p.style.name.lower() == "toc 3" and any(
            BOOKMARK_PREFIX in (n.text or "") for n in p._p.iter(qn("w:instrText"))
        ):
            p._p.getparent().remove(p._p)
    bookmark_id = max((int(n.get(qn("w:id"))) for n in doc.element.iter(qn("w:bookmarkStart"))), default=0)

    for block in blocks:
        target = next(p for p in paragraphs if p.text.strip() == block["before"])
        existing = next((p for p in paragraphs if p.text.strip() == block["title"]), None)
        content = decision_content(block)
        if existing is not None:
            stale = [existing._p, *prior_decision_nodes(
                existing, target, content, legacy_labels=LEGACY_LABELS.get(block["title"], ()),
            )]
            for node in stale:
                node.getparent().remove(node)
        heading = target.insert_paragraph_before(block["title"], style=f"Heading {block['level']}")
        heading.paragraph_format.keep_with_next = True
        write_decision_content(target, content)
        if number := block.get("number"):
            parent_number = number.rsplit(".", 1)[0]
            parent = next(p for p in ps[:start]
                          if p.style.name.lower() == "toc 2" and p.text.startswith(parent_number + " "))
            bookmark_id += 1
            name = BOOKMARK_PREFIX + number.replace(".", "_")
            mark = OxmlElement("w:bookmarkStart")
            mark.set(qn("w:id"), str(bookmark_id))
            mark.set(qn("w:name"), name)
            heading._p.r_lst[0].addprevious(mark)
            close = OxmlElement("w:bookmarkEnd")
            close.set(qn("w:id"), str(bookmark_id))
            heading._p.append(close)
            parent._p.addnext(cached_entry(doc, parent, heading, 3, number, name))
    update_federation_decision(doc, "presentation")
    return save_section(source, doc)
