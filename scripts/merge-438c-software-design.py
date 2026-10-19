#!/usr/bin/env python3
"""Insert reviewed Federation Learning design text into a copy of the joint SDD."""

import argparse
import json
import re
import sys
from copy import deepcopy
from pathlib import Path

from docx import Document
from docx.oxml.ns import qn


ROOT = Path(__file__).resolve().parents[1]
DEFAULT_TARGET = (
    ROOT.parent
    / 'tmp/merge/项目文档v0.2/02“感一通一算”一体化的跨域健康管理联邦孪生平台-软件设计说明v1.0.docx'
)
DEFAULT_SOURCE = ROOT / 'docs/438c/merge/software-design-federation-learning.md'
DEFAULT_OUTPUT = ROOT / 'docs/generated/438c/merge/software-design-federation-learning-merged.docx'
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
    for slice_ in model['slices']:
        context = slice_['context']
        if context not in contexts:
            continue
        context_title = next(item['title'] for item in model['contexts'] if item['name'] == context)
        for read_model in slice_.get('readmodels', []):
            if read_model['name'] in seen:
                raise ValueError(f"Duplicate read model: {read_model['name']}")
            seen.add(read_model['name'])
            ids = [field['name'] for field in read_model.get('fields', []) if field.get('idAttribute')]
            values = [
                contexts[context],
                f"{translations.get(context_title, context_title)}：{translations.get(read_model['title'], read_model['title'])}",
                '、'.join(ids) if ids else '按持久化实体定义',
            ]
            rows.append('| ' + ' | '.join(value.replace('|', '\\|') for value in values) + ' |')
    if len(seen) < 20:
        raise ValueError('Read model inventory unexpectedly small; check current model export.')
    return '\n'.join(rows)


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


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--target', type=Path, default=DEFAULT_TARGET)
    parser.add_argument('--source', type=Path, default=DEFAULT_SOURCE)
    parser.add_argument('--out', type=Path, default=DEFAULT_OUTPUT)
    parser.add_argument('--check', action='store_true')
    args = parser.parse_args()
    if args.out.resolve() == args.target.resolve():
        raise ValueError('The output must be a copy; refusing to overwrite the joint source document.')

    sections = parse_sections(args.source.read_text(encoding='utf-8'))
    sections['db-logical'] = sections['db-logical'].replace('{{READ_MODEL_INVENTORY}}', read_model_inventory())
    document = Document(args.target)
    if any(paragraph.text.startswith('联邦学习子系统按平台、参与方运行时代理') for paragraph in document.paragraphs):
        raise ValueError('The target already contains this merge content; use the original joint document.')
    anchors = find_anchors(document)
    if args.check:
        print(f'Checked {len(anchors)} design insertion points in {args.target}')
        return
    for key in ANCHORS:
        insert_section(document, anchors[key], sections[key])
    args.out.parent.mkdir(parents=True, exist_ok=True)
    document.save(args.out)
    print(f'Merged design copy: {args.out}')


if __name__ == '__main__':
    main()
