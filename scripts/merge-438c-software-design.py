#!/usr/bin/env python3
"""Insert reviewed Federation Learning design text into a copy of the joint SDD."""

import argparse
import hashlib
import json
import re
import sys
from collections import Counter
from copy import deepcopy
from pathlib import Path

from docx import Document
from docx.oxml.ns import qn
from docx.shared import Twips
from docx.text.paragraph import Paragraph


ROOT = Path(__file__).resolve().parents[1]
DEFAULT_TARGET = (
    ROOT.parent
    / 'tmp/merge/02“感一通一算”一体化的跨域健康管理联邦孪生平台-软件设计说明v1.0.docx'
)
DEFAULT_SOURCE = ROOT / 'docs/438c/merge/software-design-federation-learning.md'
DEFAULT_DECISIONS = ROOT / 'docs/438c/merge/software-design-input-output.json'
DEFAULT_SECTIONS = ROOT / 'docs/438c/merge/software-design-sections.json'
DEFAULT_OUTPUT = ROOT / 'docs/generated/438c/merge/software-design-federation-learning-merged.docx'
REGISTER = ROOT / 'docs/438c/merge/software-design-register.json'
MODEL = ROOT / '.medol/codegen-model.json'
sys.path.insert(0, str(ROOT / 'docs/document-pack/federation-learning'))
from markdown_to_docx import write_markdown  # noqa: E402


ANCHORS = {
    'decisions': ('联邦学习模块输入/输出设计决策/LBLS-GN-LBXX', 'Heading 3'),
    'db-conceptual': ('联邦学习', 'Heading 5'),
    'db-logical': ('联邦学习', 'Heading 5'),
    'db-physical': ('联邦学习', 'Heading 5'),
    'architecture': ('联邦学习模块CSCI部件', 'Heading 3'),
    'execution': ('联邦学习模块执行方案', 'Heading 3'),
    'performance': ('性能设计', 'Heading 2'),
    'interfaces': ('接口设计', 'Heading 2'),
    'detailed': ('联邦学习模块CSCI详细设计', 'Heading 2'),
}
DATABASE_PARENTS = {
    'db-conceptual': '数据库概念设计',
    'db-logical': '数据库逻辑设计',
    'db-physical': '数据库物理设计',
}
MARKER = re.compile(r'^<!-- INSERT ([a-z-]+) -->$', re.MULTILINE)
DECISIONS_PLACEHOLDER = '{{INPUT_OUTPUT_DECISIONS}}'
SECTION_PLACEHOLDER = '{{SOFTWARE_DESIGN_SECTION}}'
OTHER_MODULES_PERFORMANCE = '### 医疗数字人及风险动态评估模块性能设计'


def parse_sections(text):
    markers = list(MARKER.finditer(text))
    if set(match.group(1) for match in markers) != set(ANCHORS):
        raise ValueError('Design merge source must contain each expected INSERT marker exactly once.')
    if len(markers) != len(ANCHORS):
        raise ValueError('Duplicate design merge marker.')
    return {
        match.group(1): text[match.end():markers[index + 1].start() if index + 1 < len(markers) else len(text)].strip()
        for index, match in enumerate(markers)
    }


def read_decisions(path):
    decisions = json.loads(path.read_text(encoding='utf-8'))
    if decisions.get('schemaVersion') != 1:
        raise ValueError('Unsupported input/output design source version.')
    if not decisions.get('introduction') or not decisions.get('conclusion'):
        raise ValueError('Input/output design source requires introduction and conclusion.')
    items = decisions.get('items', [])
    ids = [item.get('id') for item in items]
    if not ids or any(not id_ for id_ in ids) or len(set(ids)) != len(ids):
        raise ValueError('Input/output design items require unique stable IDs.')
    for item in items:
        if any(not item.get(key) for key in ('direction', 'input', 'output', 'decision')):
            raise ValueError(f'Input/output design item is incomplete: {item["id"]}')
        if not item.get('commandRefs') and not item.get('eventRefs'):
            raise ValueError(f'Input/output design item has no model references: {item["id"]}')
    return decisions


def render_decisions(decisions):
    def cell(value):
        return value.replace('|', '\\|').replace('\n', ' ')

    rows = [
        decisions['introduction'],
        '',
        '| 交换方向 | 主要输入 | 主要输出 | 输入/输出设计决策 |',
        '| --- | --- | --- | --- |',
    ]
    for item in decisions['items']:
        rows.append('| ' + ' | '.join(cell(item[key]) for key in ('direction', 'input', 'output', 'decision')) + ' |')
    rows.extend(('', decisions['conclusion']))
    return '\n'.join(rows)


def read_structured_sections(path):
    source = json.loads(path.read_text(encoding='utf-8'))
    if source.get('schemaVersion') != 1 or set(source.get('sections', {})) != set(ANCHORS) - {'decisions'}:
        raise ValueError('Software design source must define the other eight insertion sections.')
    seen = set()
    for key, blocks in source['sections'].items():
        if not blocks:
            raise ValueError(f'Software design section is empty: {key}')
        for block in blocks:
            id_ = block.get('id')
            if not id_ or id_ in seen:
                raise ValueError(f'Duplicate or missing software design item ID: {id_}')
            seen.add(id_)
            kind = block.get('type')
            if kind == 'table':
                width = len(block.get('headers', []))
                if width < 2 or not block.get('rows'):
                    raise ValueError(f'Software design table is incomplete: {id_}')
                for row in block['rows']:
                    row_id = row.get('id')
                    if not row_id or row_id in seen or len(row.get('cells', [])) != width:
                        raise ValueError(f'Software design table row is invalid: {row_id}')
                    seen.add(row_id)
            elif kind == 'paragraph' and not block.get('text'):
                raise ValueError(f'Software design paragraph is empty: {id_}')
            elif kind == 'heading' and (block.get('level') not in (3, 4, 5) or not block.get('text')):
                raise ValueError(f'Software design heading is invalid: {id_}')
            elif kind == 'readModelInventory' and key != 'db-logical':
                raise ValueError(f'Read model inventory belongs in db-logical: {id_}')
            elif kind not in ('table', 'paragraph', 'heading', 'readModelInventory'):
                raise ValueError(f'Unsupported software design item type: {kind}')
    return source


def render_structured_sections(source):
    def row(cells):
        return '| ' + ' | '.join(value.replace('|', '\\|').replace('\n', ' ') for value in cells) + ' |'

    rendered = {}
    for key, blocks in source['sections'].items():
        fragments = []
        for block in blocks:
            kind = block['type']
            if kind == 'paragraph':
                fragments.append(block['text'])
            elif kind == 'heading':
                fragments.append('#' * block['level'] + ' ' + block['text'])
            elif kind == 'readModelInventory':
                fragments.append('{{READ_MODEL_INVENTORY}}')
            else:
                headers = block['headers']
                lines = [row(headers), row(['---'] * len(headers))]
                lines.extend(row(item['cells']) for item in block['rows'])
                fragments.append('\n'.join(lines))
        rendered[key] = '\n\n'.join(fragments)
    return rendered


def structured_section_items(source):
    items = {}
    for key, blocks in source['sections'].items():
        for block in blocks:
            # Table rows get independent identities so one edited row is reported precisely.
            summary = {field: value for field, value in block.items() if field != 'rows'}
            items[f'{key}:{block["id"]}'] = digest(json.dumps(summary, ensure_ascii=False, sort_keys=True))
            for row in block.get('rows', []):
                items[f'{key}:{row["id"]}'] = digest(json.dumps(row, ensure_ascii=False, sort_keys=True))
    return items


def decision_model_signatures(decisions, model):
    indexed = {
        kind: {entry['name']: entry for slice_ in model['slices'] for entry in slice_.get(kind, [])}
        for kind in ('commands', 'events')
    }
    signatures = {}
    for item in decisions['items']:
        references = []
        for kind, key in (('commands', 'commandRefs'), ('events', 'eventRefs')):
            for name in item.get(key, []):
                entry = indexed[kind].get(name)
                if entry is None:
                    raise ValueError(f'Unknown {kind} reference in {item["id"]}: {name}')
                references.append((kind, name, entry))
        signatures[item['id']] = digest(json.dumps(references, ensure_ascii=False, sort_keys=True))
    return signatures


def read_model_inventory():
    model = json.loads(MODEL.read_text(encoding='utf-8'))
    translations = model.get('translations', {}).get('zh-CN', {})
    deployment_labels = {
        'FederationLearningPlatform': '平台',
        'FederationLearningRuntimeAgent': '参与方代理',
    }
    contexts = {
        context['name']: deployment_labels[deployment['name']]
        for deployment in model['deployments']
        if deployment['name'] in deployment_labels
        for context in deployment['contexts']
        if context['name'] != 'IdentityAccessManagement'
    }
    rows = ['| 部署单元 | 数据主题与查询视图 | 标识字段 |', '| --- | --- | --- |']
    seen = set()
    signatures = {}
    for slice_ in model['slices']:
        context = slice_['context']
        if context not in contexts:
            continue
        context_title = next(item['title'] for item in model['contexts'] if item['name'] == context)
        for read_model in slice_.get('readmodels', []):
            if read_model['name'] in seen:
                raise ValueError(f"Duplicate read model: {read_model['name']}")
            seen.add(read_model['name'])
            signatures[read_model['name']] = digest(json.dumps(read_model, ensure_ascii=False, sort_keys=True))
            ids = [field['name'] for field in read_model.get('fields', []) if field.get('idAttribute')]
            values = [
                contexts[context],
                f"{translations.get(context_title, context_title)}：{translations.get(read_model['title'], read_model['title'])}",
                '、'.join(ids) if ids else '按持久化实体定义',
            ]
            rows.append('| ' + ' | '.join(value.replace('|', '\\|') for value in values) + ' |')
    if len(seen) < 20:
        raise ValueError('Read model inventory unexpectedly small; check current model export.')
    return '\n'.join(rows), signatures


def find_anchors(document):
    matches = {}
    previous_heading4 = None
    database_index = 0
    for paragraph in document.paragraphs:
        if paragraph.style.name == 'Heading 4':
            previous_heading4 = paragraph.text.strip()
        text = paragraph.text.strip()
        if text == '联邦学习' and paragraph.style.name == 'Heading 5':
            database_index += 1
            key = ('db-conceptual', 'db-logical', 'db-physical')[database_index - 1]
            if previous_heading4 != DATABASE_PARENTS[key]:
                raise ValueError(f'Unexpected database parent for {key}: {previous_heading4}')
            matches[key] = paragraph
            continue
        for key, (expected_text, expected_style) in ANCHORS.items():
            if key.startswith('db-'):
                continue
            if text == expected_text and paragraph.style.name == expected_style:
                if key in matches:
                    raise ValueError(f'Duplicate target anchor: {key}')
                matches[key] = paragraph
    if set(matches) != set(ANCHORS):
        raise ValueError(f'Target outline differs; missing anchors: {set(ANCHORS) - set(matches)}')
    return matches


def digest(value):
    return hashlib.sha256(value.encode('utf-8')).hexdigest()


def document_sections(document):
    """Fingerprint text and table contents under each heading in the joint draft."""
    outline = {}
    occurrences = Counter()
    key = '文档前置内容'
    sections = {key: []}
    for element in document.element.body:
        if element.tag == qn('w:p'):
            paragraph = Paragraph(element, document._body)
            style = paragraph.style.name if paragraph.style is not None else ''
            if style.startswith('Heading ') and style.removeprefix('Heading ').isdigit():
                level = int(style.removeprefix('Heading '))
                outline = {depth: title for depth, title in outline.items() if depth < level}
                outline[level] = paragraph.text.strip()
                path = ' / '.join(title for _, title in sorted(outline.items()))
                occurrences[path] += 1
                key = f'{path} [{occurrences[path]}]'
                sections[key] = []
            else:
                sections[key].append(paragraph.text.strip())
        elif element.tag == qn('w:tbl'):
            sections[key].append(' | '.join(node.text or '' for node in element.iter(qn('w:t'))))
    return {key: digest('\n'.join(lines)) for key, lines in sections.items()}


def source_label(path):
    try:
        return str(path.resolve().relative_to(ROOT.parent.resolve()))
    except ValueError:
        return str(path.resolve())


def merge_register(source, target, document, sections, anchors, decisions, decisions_path,
                   model_signatures, structured_source, structured_path, read_model_signatures):
    return {
        'schemaVersion': 3,
        'source': source_label(source),
        'decisionSource': source_label(decisions_path),
        'structuredSource': source_label(structured_path),
        'target': source_label(target),
        'targetSha256': hashlib.sha256(target.read_bytes()).hexdigest(),
        'sourceSections': {key: digest(value) for key, value in sections.items()},
        'decisionItems': {
            item['id']: digest(json.dumps(item, ensure_ascii=False, sort_keys=True))
            for item in decisions['items']
        },
        'decisionModelRefs': model_signatures,
        'structuredItems': structured_section_items(structured_source),
        'readModelItems': read_model_signatures,
        'targetSections': document_sections(document),
        'anchors': {key: {'text': paragraph.text.strip(), 'style': paragraph.style.name}
                    for key, paragraph in anchors.items()},
    }


def changed_entries(previous, current):
    return [key for key in current if previous.get(key) != current[key]] + [
        key for key in previous if key not in current
    ]


def register_changes(previous, current):
    changes = []
    for field, label in (
        ('decisionItems', '输入/输出设计项'),
        ('decisionModelRefs', '关联建模定义'),
        ('structuredItems', '其他设计章节条目'),
        ('readModelItems', '查询视图建模定义'),
    ):
        changed = changed_entries(previous.get(field, {}), current.get(field, {}))
        if changed:
            changes.append(f'{label}变更 {len(changed)} 处：{"；".join(changed[:12])}{"；…" if len(changed) > 12 else ""}')
    for field, label in (('sourceSections', '联邦学习正文'), ('targetSections', '总软设章节')):
        changed = changed_entries(previous.get(field, {}), current[field])
        if changed:
            sample = '；'.join(changed[:12])
            changes.append(f'{label}变更 {len(changed)} 处：{sample}{"；…" if len(changed) > 12 else ""}')
    if previous.get('target') != current['target']:
        changes.append(f'总软设底稿路径变更：{previous.get("target")} -> {current["target"]}')
    if previous.get('source') != current['source']:
        changes.append(f'联邦学习源稿路径变更：{previous.get("source")} -> {current["source"]}')
    if previous.get('decisionSource') != current.get('decisionSource'):
        changes.append(f'输入/输出设计源路径变更：{previous.get("decisionSource")} -> {current["decisionSource"]}')
    if previous.get('structuredSource') != current.get('structuredSource'):
        changes.append(f'其他设计章节源路径变更：{previous.get("structuredSource")} -> {current["structuredSource"]}')
    if previous.get('targetSha256') != current['targetSha256'] and not any(
        item.startswith('总软设章节变更') for item in changes
    ):
        changes.append('总软设文件已变更，但章节文字未变；请检查格式、图表或域。')
    return changes


def insert_section(document, anchor, markdown):
    fragment = Document()
    write_markdown(fragment, markdown, '')
    style_ids = {
        style.style_id: document.styles[style.name].style_id
        for style in fragment.styles
        if style.name in document.styles
    }
    last = anchor._p
    for element in fragment.element.body:
        if element.tag not in {qn('w:p'), qn('w:tbl')}:
            continue
        copied = deepcopy(element)
        for style_ref in copied.xpath('./w:pPr/w:pStyle | ./w:tblPr/w:tblStyle'):
            original_id = style_ref.get(qn('w:val'))
            if original_id not in style_ids:
                if style_ref.tag == qn('w:tblStyle'):
                    style_ref.getparent().remove(style_ref)
                    continue
                raise ValueError(f'Target document has no matching paragraph style for {original_id}')
            style_ref.set(qn('w:val'), style_ids[original_id])
        for fonts in copied.iter(qn('w:rFonts')):
            font = '黑体' if copied.tag == qn('w:p') and copied.xpath('./w:pPr/w:pStyle') else '宋体'
            for attribute in ('ascii', 'hAnsi', 'eastAsia', 'cs'):
                fonts.set(qn(f'w:{attribute}'), font)
        last.addnext(copied)
        last = copied


def insert_before_heading(document, heading, markdown):
    marker = document.add_paragraph()
    heading._p.addprevious(marker._p)
    try:
        insert_section(document, marker, markdown)
    finally:
        marker._p.getparent().remove(marker._p)


def format_inserted_tables(document, tables):
    """Use the joint design document's table style without changing existing tables."""
    reference = next(table for table in document.tables if table.rows[0].cells[0].text.strip() == '序号')
    template_properties = reference._tbl.tblPr
    for table in tables:
        table._tbl.replace(table._tbl.tblPr, deepcopy(template_properties))
        table.autofit = False
        original_widths = [column.width.twips for column in table.columns]
        total_width = sum(original_widths)
        widths = [round(9000 * width / total_width) for width in original_widths[:-1]]
        widths.append(9000 - sum(widths))
        for column, width in zip(table.columns, widths):
            column.width = Twips(width)
        for row_index, row in enumerate(table.rows):
            for cell, width in zip(row.cells, widths):
                cell.width = Twips(width)
                tc_pr = cell._tc.get_or_add_tcPr()
                for name in ('shd', 'tcBorders', 'tcMar'):
                    element = tc_pr.find(qn(f'w:{name}'))
                    if element is not None:
                        tc_pr.remove(element)
                for paragraph in cell.paragraphs:
                    paragraph.style = document.styles['RS-表-表头' if row_index == 0 else 'RS-表-内容']
                    paragraph.paragraph_format.space_after = None
                    paragraph.paragraph_format.line_spacing = None
                    paragraph.paragraph_format.alignment = None
                    for run in paragraph.runs:
                        run.font.size = None
                        run.bold = None


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--target', type=Path, default=DEFAULT_TARGET)
    parser.add_argument('--source', type=Path, default=DEFAULT_SOURCE)
    parser.add_argument('--decisions', type=Path, default=DEFAULT_DECISIONS)
    parser.add_argument('--sections', type=Path, default=DEFAULT_SECTIONS)
    parser.add_argument('--out', type=Path, default=DEFAULT_OUTPUT)
    parser.add_argument('--check', action='store_true')
    parser.add_argument('--sync', action='store_true', help='Accept the reviewed source and joint draft as a new merge baseline')
    args = parser.parse_args()
    if args.check and args.sync:
        parser.error('--check and --sync cannot be used together')
    if args.out.resolve() == args.target.resolve():
        raise ValueError('The output must be a copy; refusing to overwrite the joint source document.')

    sections = parse_sections(args.source.read_text(encoding='utf-8'))
    if sections['decisions'].count(DECISIONS_PLACEHOLDER) != 1:
        raise ValueError('Design merge source must contain the input/output decisions placeholder exactly once.')
    decisions = read_decisions(args.decisions)
    model_signatures = decision_model_signatures(decisions, json.loads(MODEL.read_text(encoding='utf-8')))
    sections['decisions'] = sections['decisions'].replace(DECISIONS_PLACEHOLDER, render_decisions(decisions))
    structured_source = read_structured_sections(args.sections)
    rendered_sections = render_structured_sections(structured_source)
    for key, rendered in rendered_sections.items():
        if sections[key].strip() != SECTION_PLACEHOLDER:
            raise ValueError(f'Software design section must use the structured placeholder: {key}')
        sections[key] = rendered
    read_models, read_model_signatures = read_model_inventory()
    sections['db-logical'] = sections['db-logical'].replace('{{READ_MODEL_INVENTORY}}', read_models)
    document = Document(args.target)
    if any(paragraph.text.startswith('联邦学习子系统按平台、参与方运行时代理') for paragraph in document.paragraphs):
        raise ValueError('The target already contains this merge content; use the original joint document.')
    anchors = find_anchors(document)
    current = merge_register(
        args.source, args.target, document, sections, anchors, decisions, args.decisions,
        model_signatures, structured_source, args.sections, read_model_signatures,
    )
    previous = json.loads(REGISTER.read_text(encoding='utf-8')) if REGISTER.exists() else {}
    changes = register_changes(previous, current)
    for change in changes:
        print(change)
    if args.sync:
        REGISTER.write_text(json.dumps(current, ensure_ascii=False, indent=2) + '\n', encoding='utf-8')
        print(f'Accepted merge baseline: {REGISTER}')
        return
    if changes:
        raise ValueError('Merge baseline has changed; review the locations above, then run --sync to accept them.')
    if args.check:
        print(f'Checked {len(anchors)} design insertion points in {args.target}')
        return
    existing_tables = {table._tbl for table in document.tables}
    for key in ANCHORS:
        if key == 'performance':
            insert_section(document, anchors[key], OTHER_MODULES_PERFORMANCE)
            insert_before_heading(document, anchors['interfaces'], sections[key])
        else:
            insert_section(document, anchors[key], sections[key])
    added_tables = [table for table in document.tables if table._tbl not in existing_tables]
    format_inserted_tables(document, added_tables)
    args.out.parent.mkdir(parents=True, exist_ok=True)
    document.save(args.out)
    print(f'Merged design copy: {args.out}')


if __name__ == '__main__':
    main()
