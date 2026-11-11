#!/usr/bin/env python3
"""Refresh only the three FL database subsections in a module-organized draft."""
import argparse
from copy import deepcopy
from io import BytesIO
from pathlib import Path
from docx import Document
from docx.oxml.ns import qn
from docx.text.paragraph import Paragraph
from docx.shared import Pt
from docx.enum.text import WD_ALIGN_PARAGRAPH
from lxml import etree


def section(document, title, level, parent=None):
    elements = list(document.element.body)
    current_parent = None
    matches = []
    for i, element in enumerate(elements):
        if element.tag != qn('w:p'):
            continue
        p = Paragraph(element, document._body)
        if p.style.name == f'Heading {level - 1}':
            current_parent = p.text.strip()
        if p.style.name == f'Heading {level}' and p.text.strip() == title and (parent is None or parent == current_parent):
            matches.append(i)
    if len(matches) != 1:
        raise ValueError(f'Ambiguous section: {title}, {parent}')
    start = matches[0]
    end = start + 1
    while end < len(elements):
        if elements[end].tag == qn('w:p'):
            p = Paragraph(elements[end], document._body)
            if p.style.name.startswith('Heading ') and int(p.style.name.split()[-1]) <= level:
                break
        end += 1
    return elements[start], elements[start + 1:end]


def merge(target, source, output):
    if target.resolve() == output.resolve():
        raise ValueError('Do not overwrite the source draft.')
    doc, src = Document(target), Document(source)
    replacements = []
    for title in ('数据库概念设计', '数据库逻辑设计', '数据库物理设计'):
        anchor, old = section(doc, title, 3, '联邦学习模块配置项/LBLS-GN-LBXX')
        _, incoming = section(src, '联邦学习模块', 3, title)
        replacements.append((anchor, old, incoming))
    removed = {e for _, old, _ in replacements for e in old}
    preserved = [(e, etree.tostring(e)) for e in doc.element.body if e not in removed]
    relmap = {}
    for anchor, old, incoming in replacements:
        for e in old:
            doc.element.body.remove(e)
        for e in incoming:
            copied = deepcopy(e)
            for node in copied.iter():
                for attr, value in list(node.attrib.items()):
                    if attr not in (qn('r:embed'), qn('r:link'), qn('r:id')):
                        continue
                    if value not in relmap:
                        rel = src.part.rels[value]
                        if rel.is_external:
                            relmap[value] = doc.part.relate_to(rel.target_ref, rel.reltype, is_external=True)
                        elif rel.reltype.endswith('/image'):
                            relmap[value] = doc.part.get_or_add_image(BytesIO(rel.target_part.blob))[0]
                        else:
                            raise ValueError(f'Unsupported relationship: {rel.reltype}')
                    node.set(attr, relmap[value])
            anchor.addnext(copied)
            anchor = copied
    assert all(etree.tostring(e) == before for e, before in preserved), 'Content outside subsections changed'
    add_overview(doc)
    output.parent.mkdir(parents=True, exist_ok=True)
    doc.save(output)
    print(f'Updated three FL database subsections; preserved all other body elements: {output}')


def add_overview(document):
    image = Path(__file__).resolve().parents[1] / 'docs/generated/diagrams/federated-twin-conceptual-er.png'
    if not image.exists():
        raise ValueError(f'Export the maintained conceptual ER drawio diagram first: {image}')
    heading, _ = section(document, '数据库详细设计', 1)
    caption_text = '联邦孪生平台总体概念 E-R 图'
    if any(p.text == caption_text for p in document.paragraphs):
        raise ValueError('Overview diagram already present; use the original joint draft.')
    intro = document.add_paragraph('下图展示平台主要业务实体及其概念关联。各模块分别管理本域数据，图中关联不表示跨数据库物理外键，也不表示不同模块共用实体数据；具体数据元素和存储结构见各模块分节。')
    picture = document.add_paragraph()
    picture.alignment = WD_ALIGN_PARAGRAPH.CENTER
    page = document.sections[0]
    picture.add_run().add_picture(str(image), width=page.page_width - page.left_margin - page.right_margin)
    caption = document.add_paragraph(caption_text)
    caption.alignment = WD_ALIGN_PARAGRAPH.CENTER
    for paragraph, font in ((intro, '宋体'), (caption, '黑体')):
        fmt = paragraph.paragraph_format
        fmt.line_spacing = 1.5
        fmt.space_before = Pt(0)
        fmt.space_after = Pt(0)
        fmt.first_line_indent = Pt(24 if paragraph is intro else 0)
        for run in paragraph.runs:
            run.font.name = font
            run._r.get_or_add_rPr().rFonts.set(qn('w:eastAsia'), font)
            run.font.size = Pt(12)
    intro.paragraph_format.keep_with_next = True
    picture.paragraph_format.keep_with_next = True
    for paragraph in (intro, picture, caption):
        heading.addnext(paragraph._p)
        heading = paragraph._p


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    for name in ('target', 'source', 'out'):
        parser.add_argument('--' + name, type=Path, required=True)
    args = parser.parse_args()
    merge(args.target, args.source, args.out)
