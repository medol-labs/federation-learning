"""Apply the three-tier layout to the maintained conceptual relationship diagram."""
from pathlib import Path
import xml.etree.ElementTree as ET

path = Path(__file__).resolve().parents[1] / '438c/merge/figures/federated-twin-data-relations.drawio'
tree = ET.parse(path)
cells = {c.get('id'): c for c in tree.findall('.//mxCell')}
positions = {
    'title': (30, 15, 1570, 60),
    'fl': (30, 100, 1570, 330),
    'med': (30, 630, 650, 440),
    'risk': (950, 630, 650, 440),
    'sys': (30, 1260, 1570, 220),
    'org': (70, 170, 250, 65), 'fed': (480, 170, 250, 65),
    'data': (70, 315, 250, 65), 'job': (890, 170, 250, 65),
    'schema': (480, 315, 250, 65), 'model': (1310, 170, 250, 80),
    'patient': (60, 700, 250, 65), 'visit': (400, 700, 250, 65),
    'bind': (60, 825, 250, 65), 'video': (400, 825, 250, 65),
    'result': (60, 955, 590, 80),
    'assessment': (980, 730, 590, 80), 'use': (980, 955, 590, 65),
    'inst': (70, 1360, 250, 65), 'user': (480, 1360, 250, 65),
    'roles': (890, 1360, 250, 65), 'log': (1300, 1360, 250, 65),
    'legend': (30, 1520, 1570, 115),
}
for id_, geometry in positions.items():
    cells[id_].find('mxGeometry').attrib.update(dict(zip(('x', 'y', 'width', 'height'), map(str, geometry))))
tree.find('.//mxGraphModel').set('pageHeight', '1670')
root = tree.find('.//root')
existing = cells.get('twin-system')
if existing is not None:
    root.remove(existing)
group = ET.Element('mxCell', {
    'id': 'twin-system', 'value': '孪生应用系统',
    'style': 'swimlane;startSize=45;rounded=0;whiteSpace=wrap;html=0;fontFamily=PingFang SC;fontSize=22;fontStyle=1;fillColor=#F5F7FA;swimlaneFillColor=#FFFFFF;strokeColor=#536B82;strokeWidth=2;',
    'vertex': '1', 'parent': '1',
})
ET.SubElement(group, 'mxGeometry', {'x': '10', 'y': '555', 'width': '1610', 'height': '945', 'as': 'geometry'})
# Draw the boundary behind the three existing module frames.
root.insert(list(root).index(cells['med']), group)


def route(id_, ports, points=()):
    cell = cells[id_]
    style = dict(item.split('=', 1) for item in cell.get('style').split(';') if '=' in item)
    style.update(dict(zip(('exitX', 'exitY', 'entryX', 'entryY'), map(str, ports))))
    cell.set('style', ';'.join(f'{k}={v}' for k, v in style.items()) + ';')
    geometry = cell.find('mxGeometry')
    for child in list(geometry):
        geometry.remove(child)
    if points:
        array = ET.SubElement(geometry, 'Array', {'as': 'points'})
        for x, y in points:
            ET.SubElement(array, 'mxPoint', {'x': str(x), 'y': str(y)})


route('e2', (1, .5, .5, 1), [(370, 347), (370, 280), (1015, 280)])
route('e3', (1, .5, 1, 1), [(1180, 347), (1180, 270), (1140, 270)])
route('e4', (1, .5, 0, .5))
route('e10', (1, .5, 0, .5))
route('e11', (.5, 1, .5, 1), [(605, 1455), (1425, 1455)])
# Separate lanes for model delivery and management to avoid shared trunks.
route('e13', (.3, 1, 1, .25), [(1385, 480), (760, 480), (760, 975)])
route('e14', (.75, 1, .65, 0))
route('e15', (1, .75, 0, .75), [(835, 1015), (835, 790)])
route('e16', (.85, 0, .85, 1), [(692, 1175), (1481, 1175)])
route('e17', (.15, 0, .5, 1), [(517, 1145), (355, 1145)])
positions = {
    'title': (30, 15, 2220, 60), 'fl': (30, 100, 650, 1100),
    'med': (800, 170, 650, 440), 'risk': (1600, 170, 650, 440),
    'sys': (800, 850, 1450, 250),
    'org': (60, 250, 250, 65), 'fed': (400, 250, 250, 65),
    'data': (60, 480, 250, 65), 'job': (400, 480, 250, 65),
    'schema': (60, 710, 250, 65), 'model': (400, 950, 250, 80),
    'patient': (830, 240, 250, 65), 'visit': (1170, 240, 250, 65),
    'bind': (830, 365, 250, 65), 'video': (1170, 365, 250, 65),
    'result': (830, 495, 590, 80),
    'assessment': (1630, 260, 590, 80), 'use': (1630, 495, 590, 65),
    'inst': (830, 930, 250, 65), 'user': (1190, 930, 250, 65),
    'roles': (1550, 930, 250, 65), 'log': (1910, 930, 250, 65),
    'legend': (30, 1240, 2220, 115),
}
for id_, geometry in positions.items():
    cells[id_].find('mxGeometry').attrib.update(dict(zip(('x', 'y', 'width', 'height'), map(str, geometry))))
group.find('mxGeometry').attrib.update({'x': '780', 'y': '100', 'width': '1490', 'height': '1100'})
tree.find('.//mxGraphModel').attrib.update({'pageWidth': '2300', 'pageHeight': '1400'})
route('e2', (1, .5, 0, .5))
route('e3', (1, .5, 0, 1), [(355, 742), (355, 580), (400, 580)])
route('e4', (.5, 1, .5, 0))
route('e11', (.5, 1, .5, 1), [(1315, 1050), (2035, 1050)])
route('e13', (1, .25, 0, .5), [(720, 970), (720, 535)])
route('e14', (1, .8, 0, .3), [(750, 1014), (750, 680), (1510, 680), (1510, 284)])
route('e15', (1, .8, 0, .8), [(1545, 559), (1545, 324)])
route('e16', (.85, 0, 1, .7), [(1402, 760), (2240, 760), (2240, 316)])
route('e17', (.15, 0, .5, 1), [(1227, 720), (1125, 720)])
ET.indent(tree, space='  ')
tree.write(path, encoding='utf-8', xml_declaration=True)
