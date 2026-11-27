"""Expose the interface chapter's subheadings without expanding the whole TOC."""

import re
from copy import deepcopy

from docx.oxml import OxmlElement
from docx.oxml.ns import qn

START = "接口设计"
END = "物模型设计"
FEDERATED = "联邦学习模块内部接口"
SUBHEADINGS = ("接口图", "接口清单", "接口说明")
BOOKMARK_PREFIX = "SddInterfaceToc"
FIELD_SWITCH = r"\f I"
XML_SPACE = "{http://www.w3.org/XML/1998/namespace}space"


def outline_entries(paragraphs):
    start = next(i for i, p in enumerate(paragraphs) if p.text.strip() == START)
    end = next(i for i, p in enumerate(paragraphs[start + 1:], start + 1) if p.text.strip() == END)
    counters = {3: 0, 4: 0, 5: 0}
    federal = False
    for paragraph in paragraphs[start + 1:end]:
        title = paragraph.text.strip()
        if not (style := re.fullmatch(r"Heading ([345])", paragraph.style.name)):
            continue
        level = int(style[1])
        counters[level] += 1
        for child in range(level + 1, 6):
            counters[child] = 0
        if level < 5:
            federal = title == FEDERATED
        if level == 4 or (level == 5 and federal and title in SUBHEADINGS):
            number = ".".join(["4.5", *(str(counters[n]) for n in range(3, level + 1))])
            yield paragraph, level, number


def add_entry_field(paragraph, level, number, bookmark_id):
    anchor = BOOKMARK_PREFIX + number.replace(".", "_")
    properties = paragraph._p.get_or_add_pPr()
    begin = OxmlElement("w:bookmarkStart")
    begin.set(qn("w:id"), str(bookmark_id))
    begin.set(qn("w:name"), anchor)
    properties.addnext(begin)
    end = OxmlElement("w:bookmarkEnd")
    end.set(qn("w:id"), str(bookmark_id))
    paragraph._p.append(end)
    field = OxmlElement("w:fldSimple")
    field.set(qn("w:instr"), f' TC "{number} {paragraph.text.strip()}" {FIELD_SWITCH} \\l {level} ')
    paragraph._p.append(field)
    return anchor


def cached_entry(doc, parent, paragraph, level, number, anchor):
    node = deepcopy(parent._p)
    style = node.find(f"{qn('w:pPr')}/{qn('w:pStyle')}")
    style.set(qn("w:val"), doc.styles[f"toc {level}"].style_id)
    for instruction in node.iter(qn("w:instrText")):
        if (instruction.text or "").lstrip().startswith("HYPERLINK"):
            instruction.text = f' HYPERLINK \\l "{anchor}" '
        elif (instruction.text or "").lstrip().startswith("PAGEREF"):
            instruction.text = f" PAGEREF {anchor} \\h "
    texts = list(node.iter(qn("w:t")))
    if len(texts) != 3:
        raise ValueError("Unexpected parent interface TOC entry format")
    texts[0].text = number
    texts[1].text = " " + paragraph.text.strip()
    texts[1].set(XML_SPACE, "preserve")
    texts[2].text = None
    for field in list(node.iter(qn("w:fldChar"))):
        if field.get(qn("w:fldCharType")) == "begin":
            field.set(qn("w:dirty"), "true")
    return node


def update_interface_outline(doc):
    paragraphs = doc.paragraphs
    first = next(i for i, p in enumerate(paragraphs) if p.text.strip() == FEDERATED)
    last = next(i for i, p in enumerate(paragraphs[first + 1:], first + 1) if p.text.strip() == END)
    block = paragraphs[first + 1:last]
    if not any(p.text.strip() == SUBHEADINGS[0] for p in block):
        figures = next(p for p in block if p.text.startswith("接口图中的 G01"))
        figures.insert_paragraph_before(SUBHEADINGS[0], style="Heading 5")
        paragraphs = doc.paragraphs
        last += 1
        block = paragraphs[first + 1:last]
    for title in SUBHEADINGS:
        matches = [p for p in block if p.text.strip() == title]
        if len(matches) != 1:
            raise ValueError(f"Missing or ambiguous interface subsection: {title}")
        matches[0].style = doc.styles["Heading 5"]
        matches[0].paragraph_format.keep_with_next = True

    for p in doc.paragraphs:
        if p.style.name.lower() in ("toc 4", "toc 5") and any(
            BOOKMARK_PREFIX in (n.text or "") for n in p._p.iter(qn("w:instrText"))
        ):
            p._p.getparent().remove(p._p)
    old_bookmarks = set()
    for node in list(doc.element.iter(qn("w:bookmarkStart"))):
        if (node.get(qn("w:name")) or "").startswith(BOOKMARK_PREFIX):
            old_bookmarks.add(node.get(qn("w:id")))
            node.getparent().remove(node)
    for node in list(doc.element.iter(qn("w:bookmarkEnd"))):
        if node.get(qn("w:id")) in old_bookmarks:
            node.getparent().remove(node)
    for node in list(doc.element.iter(qn("w:fldSimple"))):
        if (node.get(qn("w:instr")) or "").lstrip().startswith('TC "4.5.'):
            node.getparent().remove(node)

    toc = next(n for n in doc.element.iter(qn("w:instrText")) if (n.text or "").lstrip().startswith("TOC "))
    if FIELD_SWITCH not in toc.text:
        toc.text = f"{toc.text.rstrip()} {FIELD_SWITCH} "
    parents = {
        p.text.split(" ", 1)[0]: p for p in doc.paragraphs
        if p.style.name.lower() == "toc 3" and p.text.startswith(("4.5.2 ", "4.5.3 "))
    }
    cursor = {key: value._p for key, value in parents.items()}
    bookmark_id = max((int(n.get(qn("w:id"))) for n in doc.element.iter(qn("w:bookmarkStart"))), default=0)
    for paragraph, level, number in outline_entries(doc.paragraphs):
        parent_key = ".".join(number.split(".")[:3])
        bookmark_id += 1
        anchor = add_entry_field(paragraph, level, number, bookmark_id)
        entry = cached_entry(doc, parents[parent_key], paragraph, level, number, anchor)
        cursor[parent_key].addnext(entry)
        cursor[parent_key] = entry
