"""Normalize database captions and renumber their prose references."""
import re
from docx.text.paragraph import Paragraph
from docx.oxml import OxmlElement
from docx.oxml.ns import qn
from docx.shared import Pt
from docx.enum.text import WD_ALIGN_PARAGRAPH

CAPTION = re.compile(r'^([图表])\s*[.．]?\s*(\d+)\s*(.*)$')


def normalize_captions(document):
    captions = []
    seen = set()
    heading = ''
    elements = list(document.element.body)
    def add(paragraph, kind, title):
        if paragraph._p not in seen:
            seen.add(paragraph._p)
            captions.append((paragraph, kind, title))
    for i, element in enumerate(elements):
        if element.tag == qn('w:p'):
            p = Paragraph(element, document._body)
            if p.style.name.startswith('Heading '):
                heading = p.text.split('/')[0]
            match = CAPTION.match(p.text)
            if match and (p.style.name == 'Caption' or '题注' in p.style.name):
                add(p, match[1], match[3])
            if element.xpath('.//w:drawing'):
                following = elements[i + 1]
                if following.tag != qn('w:p'):
                    raise ValueError('Image has no following caption paragraph')
                caption = Paragraph(following, document._body)
                match = CAPTION.match(caption.text)
                title = match[3] if match else re.sub(r'^图[：:]\s*', '', caption.text)
                if not title or caption.style.name.startswith('Heading '):
                    raise ValueError('Image caption is missing')
                add(caption, '图', title)
        elif element.tag == qn('w:tbl'):
            # Cover/signature layout tables are not numbered content tables.
            if not heading:
                continue
            previous = element.getprevious()
            while previous is not None and previous.tag == qn('w:p') and not Paragraph(previous, document._body).text.strip():
                previous = previous.getprevious()
            caption = Paragraph(previous, document._body) if previous is not None and previous.tag == qn('w:p') else None
            if caption is not None and CAPTION.match(caption.text) and CAPTION.match(caption.text)[1] == '表':
                add(caption, '表', CAPTION.match(caption.text)[3])
            else:
                node = OxmlElement('w:p')
                element.addprevious(node)
                caption = Paragraph(node, document._body)
                add(caption, '表', heading + ('字段说明' if 'FL-DB-' in heading else ''))
    order = {e: i for i, e in enumerate(document.element.body)}
    captions.sort(key=lambda item: order[item[0]._p])
    counts = {'图': 0, '表': 0}
    mapping = {}
    for paragraph, kind, title in captions:
        counts[kind] += 1
        old = CAPTION.match(paragraph.text)
        if old:
            key = (kind, old[2])
            if key in mapping:
                raise ValueError(f'Duplicate original caption: {key}')
            mapping[key] = str(counts[kind])
        paragraph.clear()
        paragraph.style = document.styles['Caption']
        run = paragraph.add_run(f'{kind} {counts[kind]} {title}')
        run.font.name = 'Times New Roman'
        run._r.get_or_add_rPr().rFonts.set(qn('w:eastAsia'), '黑体')
        run.font.size = Pt(12)
        run.bold = False
        run.italic = False
        fmt = paragraph.paragraph_format
        fmt.alignment = WD_ALIGN_PARAGRAPH.CENTER
        fmt.first_line_indent = Pt(0)
        fmt.left_indent = Pt(0)
        fmt.line_spacing = 1.5
        fmt.space_before = Pt(0)
        fmt.space_after = Pt(0)
        fmt.keep_with_next = kind == '表'
    # Update split-run references without replacing their paragraph formatting.
    for p in document.element.body.iter(qn('w:p')):
        if p in seen:
            continue
        nodes = list(p.iter(qn('w:t')))
        text = ''.join(n.text or '' for n in nodes)
        matches = list(re.finditer(r'([图表])\s*[.．]?\s*(\d+)(?!\d)', text))
        for match in reversed(matches):
            value = mapping.get((match[1], match[2]))
            if value is None or value == match[2]:
                continue
            start, end = match.span(2)
            offset = 0
            for node in nodes:
                current = node.text or ''
                a, b = max(start - offset, 0), min(end - offset, len(current))
                if a < b:
                    node.text = current[:a] + (value if offset <= start else '') + current[b:]
                offset += len(current)
    print(f'Captions: {counts}; updated references using {len(mapping)} original caption numbers.')
    return counts
