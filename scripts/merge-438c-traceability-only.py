#!/usr/bin/env python3
"""Replace only Federation Learning trace rows in an existing joint draft."""
import argparse
import re
from copy import deepcopy
from pathlib import Path

from docx import Document
from docx.table import _Row
from docx.oxml.ns import qn
from lxml import etree


def is_federation(row):
    return any(any(marker in cell.text for marker in (
        'LBLS-GN-LBXX', 'FL-SD-', 'FL-DB-', '（联邦学习）', '目录、看板与状态视图查询通用要求',
    )) for cell in row.cells)


def merge(target, source, output):
    if target.resolve() == output.resolve():
        raise ValueError('Output must not overwrite the joint source.')
    document, replacement = Document(target), Document(source)
    physical_refs = {}
    counters = []
    for paragraph in document.paragraphs:
        if not paragraph.style.name.startswith('Heading '):
            continue
        level = int(paragraph.style.name.split()[-1])
        counters = counters[:level]
        counters += [0] * (level - len(counters))
        counters[-1] += 1
        match = re.fullmatch(r'(.+?)(?:/|（|\()(FL-DB-[PEASR]\d+)[）)]?', paragraph.text.strip())
        if match:
            physical_refs[match[2]] = '.'.join(map(str, counters)) + ' ' + match[1].strip() + '/' + match[2]
    def trace_tables(doc):
        return [t for t in doc.tables if len(t.columns) == 4
                and t.rows[0].cells[0].text.strip() == '序号'
                and '追踪' in t.rows[0].cells[1].text
                and any(is_federation(r) for r in t.rows[1:])]
    tables = trace_tables(document)
    incoming_tables = trace_tables(replacement)
    if len(tables) != 2 or len(incoming_tables) != 2:
        raise ValueError('Expected exactly two traceability tables.')
    original_body = [etree.tostring(e) for e in document.element.body if all(e is not t._tbl for t in tables)]
    for table, incoming in zip(tables, incoming_tables):
        if len(table.columns) != len(incoming.columns):
            raise ValueError('Traceability column counts differ.')
        old = [r for r in table.rows[1:] if is_federation(r)]
        new = [r for r in incoming.rows[1:] if is_federation(r)]
        if not old or not new:
            raise ValueError('Unexpected source trace rows.')
        untouched = [r._tr for r in table.rows if not is_federation(r)]
        snapshots = [r.xml for r in untouched]
        for row in new:
            copied = deepcopy(row._tr)
            old[0]._tr.addprevious(copied)
            for cell in _Row(copied, table).cells:
                identifier = re.search(r'FL-DB-[PEASR]\d+', cell.text)
                if identifier:
                    if identifier[0] not in physical_refs:
                        raise ValueError(f'Missing physical design heading: {identifier[0]}')
                    paragraph = cell.paragraphs[0]
                    paragraph.runs[0].text = physical_refs[identifier[0]]
                    for run in paragraph.runs[1:]:
                        run.text = ''
        for row in old:
            table._tbl.remove(row._tr)
        if [r.xml for r in untouched] != snapshots:
            raise AssertionError('Non-Federation rows changed.')
        for index, row in enumerate(table.rows[1:], 1):
            cell = row.cells[0]
            if cell.text != str(index):
                paragraph = cell.paragraphs[0]
                if paragraph.runs:
                    paragraph.runs[0].text = str(index)
                    for run in paragraph.runs[1:]:
                        run.text = ''
                else:
                    paragraph.add_run(str(index))
        print(f'Replaced {len(old)} Federation rows with {len(new)}; preserved {len(untouched)} other/header rows.')
    if original_body != [etree.tostring(e) for e in document.element.body if all(e is not t._tbl for t in tables)]:
        raise AssertionError('Content outside trace tables changed.')
    output.parent.mkdir(parents=True, exist_ok=True)
    document.save(output)
    print(output)


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--target', type=Path, required=True)
    parser.add_argument('--source', type=Path, required=True)
    parser.add_argument('--out', type=Path, required=True)
    parser.add_argument('--database-captions', action='store_true')
    args = parser.parse_args()
    merge(args.target, args.source, args.out)
    if args.database_captions:
        from database_captions import normalize_captions
        document = Document(args.out)
        normalize_captions(document)
        document.save(args.out)
