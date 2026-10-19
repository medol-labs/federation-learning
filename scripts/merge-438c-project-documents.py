#!/usr/bin/env python3
"""Produce review copies of the other joint 438C documents with FL sections inserted."""

import argparse
import importlib.util
import re
import subprocess
import tempfile
from pathlib import Path

from docx import Document
from docx.oxml.ns import qn
from docx.text.paragraph import Paragraph


ROOT = Path(__file__).resolve().parents[1]
TARGET_DIR = ROOT.parent / 'tmp/merge/项目文档v0.2'
SOURCE_DIR = ROOT / 'docs/438c/merge'
OUTPUT_DIR = ROOT / 'docs/generated/438c/merge/project-documents'
SOFTWARE_SCRIPT = ROOT / 'scripts/merge-438c-software-design.py'
spec = importlib.util.spec_from_file_location('software_design_merge', SOFTWARE_SCRIPT)
software_merge = importlib.util.module_from_spec(spec)
spec.loader.exec_module(software_merge)
insert_section = software_merge.insert_section
read_model_inventory = software_merge.read_model_inventory

MARKER = re.compile(r'^<!-- INSERT ([a-z-]+) -->$', re.MULTILINE)


def sections(name, expected):
    text = (SOURCE_DIR / name).read_text(encoding='utf-8')
    matches = list(MARKER.finditer(text))
    if len(matches) != len(expected) or set(match.group(1) for match in matches) != set(expected):
        raise ValueError(f'{name}: missing or duplicate insertion markers')
    return {
        match.group(1): text[match.end():matches[index + 1].start() if index + 1 < len(matches) else len(text)].strip()
        for index, match in enumerate(matches)
    }


def heading(document, text, style, parent=None):
    current_parent = None
    found = []
    for paragraph in document.paragraphs:
        style_name = paragraph.style.name if paragraph.style is not None else None
        if style_name == 'Heading 2':
            current_parent = paragraph.text.strip()
        if paragraph.text.strip() == text and style_name == style:
            if parent is None or current_parent == parent:
                found.append(paragraph)
    if len(found) != 1:
        raise ValueError(f'Expected one {style} heading {text!r} under {parent!r}; found {len(found)}')
    return found[0]


def convert_requirements(source, out_dir):
    command = [
        'soffice', f'-env:UserInstallation=file://{out_dir / "lo-profile"}',
        '--headless', '--convert-to', 'docx', '--outdir', str(out_dir), str(source),
    ]
    result = subprocess.run(command, capture_output=True, text=True, check=False)
    converted = out_dir / (source.stem + '.docx')
    if result.returncode or not converted.exists():
        raise RuntimeError(f'Could not convert the joint requirements document: {result.stderr}')
    return converted


def requirement_parts():
    source = (SOURCE_DIR / '3.3.1-federation-learning.md').read_text(encoding='utf-8')
    lines = source.splitlines()
    start = next(index for index, line in enumerate(lines) if line.startswith('| 序号 |'))
    model_lines = [line for line in lines[start:] if line.startswith('|')]
    from markdown_to_docx import collect_table
    rows, end = collect_table(lines, start)
    if len(rows) != 172 or len(rows[0]) != 5:
        raise ValueError(f'Expected header plus 171 requirements, got {len(rows)} rows')
    if len(model_lines) < len(rows):
        raise ValueError('Requirement table is incomplete')
    intro = '\n'.join(lines[2:next(i for i, line in enumerate(lines) if line.startswith('## 表 3'))]).strip()
    details = '\n'.join(lines[end:]).strip()
    details = re.sub(r'^## ', '#### ', details, flags=re.MULTILINE)
    details = re.sub(r'^- (.+)$', r'· \1\n', details, flags=re.MULTILINE)
    if not details.startswith('#### 组织管理'):
        raise ValueError('Requirement detail section has an unexpected first heading')
    return intro, rows, details


def requirement_placeholder(document):
    anchor = heading(document, '联邦学习模块配置项功能需求/LBLS-GN-LBXX', 'Heading 3')
    objects = []
    table = None
    for element in anchor._p.itersiblings():
        if element.tag == qn('w:p'):
            paragraph = Paragraph(element, document._body)
            if paragraph.style is not None and paragraph.style.name == 'Heading 3':
                break
            objects.append(paragraph)
        elif element.tag == qn('w:tbl'):
            if table is not None:
                raise ValueError('More than one requirement placeholder table')
            table = next(item for item in document.tables if item._tbl is element)
    if table is None or len(table.rows) != 2 or len(table.columns) != 5:
        raise ValueError('Requirement placeholder table has changed')
    expected_placeholders = {'所有功能标识', '挂*****/LBLS-GN-LBXX-****'}
    found = {paragraph.text.strip() for paragraph in objects if paragraph.style is not None and paragraph.style.name == 'Heading 4'}
    if found != expected_placeholders:
        raise ValueError(f'Requirement placeholder headings have changed: {found}')
    return anchor, objects, table


def merge_requirements(target, out, check):
    document = Document(target)
    anchor, objects, table = requirement_placeholder(document)
    intro, rows, details = requirement_parts()
    if check:
        return
    for paragraph in objects:
        if paragraph.style is not None and paragraph.style.name == 'Heading 4':
            paragraph._p.getparent().remove(paragraph._p)
    insert_section(document, anchor, intro)
    for row in list(table.rows)[1:]:
        table._tbl.remove(row._tr)
    for fields in rows[1:]:
        row = table.add_row()
        for cell, value in zip(row.cells, fields):
            cell.text = value
    marker = document.add_paragraph()
    table._tbl.addnext(marker._p)
    insert_section(document, marker, details)
    marker._p.getparent().remove(marker._p)
    out.parent.mkdir(parents=True, exist_ok=True)
    document.save(out)


def merge_database(target, out, check):
    content = sections('database-design-federation-learning.md', {'conceptual', 'logical', 'physical'})
    content['logical'] = content['logical'].replace('{{READ_MODEL_INVENTORY}}', read_model_inventory())
    document = Document(target)
    anchors = {
        key: heading(document, '联邦学习模块', 'Heading 3', parent)
        for key, parent in {
            'conceptual': '数据库概念设计',
            'logical': '数据库逻辑设计',
            'physical': '数据库物理设计',
        }.items()
    }
    if check:
        return
    for key, anchor in anchors.items():
        insert_section(document, anchor, content[key])
    out.parent.mkdir(parents=True, exist_ok=True)
    document.save(out)


def merge_user_manual(target, out, check):
    content = sections('user-manual-federation-learning.md', {'procedure', 'scenario'})
    document = Document(target)
    procedure = heading(document, '联邦学习模块', 'Heading 3')
    scenario = heading(document, '多中心联邦建模科研场景', 'Heading 2')
    next_section = heading(document, '医疗数字孪生辅助临床诊疗场景', 'Heading 2')
    if check:
        return
    insert_section(document, procedure, content['procedure'])
    element = scenario._p.getnext()
    while element is not None and element is not next_section._p:
        following = element.getnext()
        element.getparent().remove(element)
        element = following
    insert_section(document, scenario, content['scenario'])
    out.parent.mkdir(parents=True, exist_ok=True)
    document.save(out)


def merge_installation(target, out, check):
    content = sections('installation-federation-learning.md', {'installation'})
    document = Document(target)
    heading(document, '系统部署安装', 'Heading 1')
    if check:
        return
    marker = document.add_paragraph()
    insert_section(document, marker, content['installation'])
    marker._p.getparent().remove(marker._p)
    out.parent.mkdir(parents=True, exist_ok=True)
    document.save(out)


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--target-dir', type=Path, default=TARGET_DIR)
    parser.add_argument('--out-dir', type=Path, default=OUTPUT_DIR)
    parser.add_argument('--check', action='store_true')
    args = parser.parse_args()
    targets = {code: next(args.target_dir.glob(f'{code}*')) for code in ('01', '03', '04', '05')}
    if any(target.resolve() == (args.out_dir / f'{code}-federation-learning-merged.docx').resolve() for code, target in targets.items()):
        raise ValueError('The output must not overwrite a joint source document')
    with tempfile.TemporaryDirectory() as directory:
        requirements_docx = convert_requirements(targets['01'], Path(directory))
        jobs = [
            ('01', merge_requirements, requirements_docx),
            ('03', merge_database, targets['03']),
            ('04', merge_user_manual, targets['04']),
            ('05', merge_installation, targets['05']),
        ]
        for code, merge, target in jobs:
            output = args.out_dir / f'{code}-federation-learning-merged.docx'
            merge(target, output, args.check)
            print(f'{code}: {"checked" if args.check else output}')


if __name__ == '__main__':
    main()
