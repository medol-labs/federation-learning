#!/usr/bin/env python3
"""Validate, normalize and export editable draw.io documents."""

import argparse
import base64
import os
import shutil
import subprocess
import xml.etree.ElementTree as ET
import zlib
from pathlib import Path
from urllib.parse import unquote
from xml.sax.saxutils import escape, quoteattr


def read_diagram(path):
    tree = ET.parse(path)
    root = tree.getroot()
    pages = root.findall('diagram')
    if root.tag != 'mxfile' or not pages:
        raise ValueError(f'{path}: expected a draw.io mxfile with at least one page')
    for page in pages:
        model = page.find('mxGraphModel')
        if model is None:
            try:
                data = zlib.decompress(base64.b64decode((page.text or '').strip(), validate=True), -15)
                model = ET.fromstring(unquote(data.decode('utf-8')))
            except (ValueError, zlib.error, ET.ParseError) as error:
                raise ValueError(f'{path}: cannot decode page {page.get("name")}') from error
            page.text = None
            page.append(model)
        if model.tag != 'mxGraphModel':
            raise ValueError(f'{path}: expected mxGraphModel in page {page.get("name")}')
        parents = {child: parent for parent in model.iter() for child in parent}
        cells = list(model.iter('mxCell'))
        ids = [cell.get('id') or parents[cell].get('id') for cell in cells]
        if any(not identifier for identifier in ids) or len(ids) != len(set(ids)):
            raise ValueError(f'{path}: cell IDs must be present and unique')
        for cell in cells:
            for attribute in ('source', 'target', 'parent'):
                reference = cell.get(attribute)
                if reference is not None and reference not in ids:
                    raise ValueError(f'{path}: {cell.get("id")} has missing {attribute}: {reference}')
    return tree


def serialize(element, level=0):
    indent = '  ' * level
    attributes = list(element.attrib.items())
    if len(attributes) > 2:
        opening = [f'{indent}<{element.tag}', *[
            f'{indent}  {key}={quoteattr(value)}' for key, value in attributes
        ]]
        opening[-1] += '>' if len(element) or (element.text or '').strip() else ' />'
        lines = opening
    else:
        attributes_text = ''.join(f' {key}={quoteattr(value)}' for key, value in attributes)
        if not len(element) and not (element.text or '').strip():
            return [f'{indent}<{element.tag}{attributes_text} />']
        lines = [f'{indent}<{element.tag}{attributes_text}>']
    if not len(element) and not (element.text or '').strip():
        return lines
    if (element.text or '').strip():
        lines.append(f'{indent}  {escape(element.text.strip())}')
    for child in element:
        lines.extend(serialize(child, level + 1))
    lines.append(f'{indent}</{element.tag}>')
    return lines


def normalize(path):
    tree = read_diagram(path)
    root = tree.getroot()
    for attribute in ('modified', 'agent', 'etag'):
        root.attrib.pop(attribute, None)
    root.set('compressed', 'false')
    path.write_text('<?xml version="1.0" encoding="UTF-8"?>\n' +
                    '\n'.join(serialize(root)) + '\n', encoding='utf-8')


def export(path):
    if path.suffix == '.drawio':
        read_diagram(path)
    elif path.suffix != '.dot':
        raise ValueError(f'Expected .drawio or .dot source: {path}')
    output_dir = Path(__file__).resolve().parents[1] / 'generated' / 'diagrams'
    output_dir.mkdir(parents=True, exist_ok=True)
    if path.suffix == '.dot':
        executable = shutil.which('dot')
        if executable is None:
            raise ValueError('Install Graphviz to export .dot diagram sources')
        for extension in ('svg', 'png'):
            output = output_dir / f'{path.stem}.{extension}'
            command = [executable, f'-T{extension}']
            if extension == 'png':
                command.append('-Gdpi=180')
            command.extend([str(path), '-o', str(output)])
            subprocess.run(command, check=True, timeout=90)
            if not output.is_file() or output.stat().st_size == 0:
                raise ValueError(f'Graphviz did not produce {output}')
            print(output)
        return
    executable = os.environ.get('DRAWIO_EXECUTABLE') or shutil.which('drawio')
    if executable is None:
        mac_app = Path('/Applications/draw.io.app/Contents/MacOS/draw.io')
        if mac_app.is_file():
            executable = str(mac_app)
    if executable is None:
        raise ValueError('Install draw.io Desktop or set DRAWIO_EXECUTABLE to its executable')
    for extension in ('svg', 'png'):
        output = output_dir / f'{path.stem}.{extension}'
        command = [executable, '--export', '--format', extension,
                   '--border', '20', '--output', str(output), str(path)]
        if extension == 'png':
            command.extend(['--scale', '2'])
        subprocess.run(command, check=True, timeout=90)
        if not output.is_file() or output.stat().st_size == 0:
            raise ValueError(f'draw.io did not produce {output}')
        print(output)


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('action', choices=('check', 'format', 'export'))
    parser.add_argument('sources', nargs='+', type=Path)
    args = parser.parse_args()
    for source in args.sources:
        source = source.resolve()
        if source.suffix != '.drawio' and not (args.action == 'export' and source.suffix == '.dot'):
            parser.error(f'Expected .drawio source (export also accepts .dot): {source}')
        if args.action == 'format':
            normalize(source)
            print(f'Formatted: {source}')
        elif args.action == 'export':
            export(source)
        else:
            read_diagram(source)
            print(f'Checked: {source}')


if __name__ == '__main__':
    main()
