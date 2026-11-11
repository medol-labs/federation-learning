"""Fill the reference SDD logical-interface prototype using controlled content."""

import xml.etree.ElementTree as ET

from software_design_interface_contracts import resource_interfaces, reference_project, render_reference_subsections


def validate_interface_diagrams(source, root):
    entities = set()
    for block in source['sections']['interfaces']:
        if block['type'] != 'figure':
            continue
        tree = ET.parse(root / block['source'])
        for cell in tree.iter('mxCell'):
            if cell.get('vertex') == '1':
                entities.add(cell.get('id'))
    expected = {'platform', 'agent', 'support', 'training', 'aggregation'}
    if not expected <= entities:
        raise ValueError(f'Interface diagram is missing providing entities: {expected - entities}')


def render_interface_designs(source, records, model, field_rows):
    summary = next(b['text'] for b in source['sections']['interfaces'] if b['id'] == 'FL-SD-INT-INTRO')
    return render_reference_subsections(reference_project(resource_interfaces(records, model), summary))
