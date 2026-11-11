"""Arrange controlled design content in the reference SDD template's sections.

Source item IDs remain stable; presentation order is independent of change tracking.
"""

from copy import deepcopy


def template_sections(source):
    result = deepcopy(source)
    blocks = {b['id']: b for b in result['sections']['interfaces']}
    ordered = [blocks['FL-SD-INT-01']]
    heading = blocks['FL-SD-INT-RELATIONS']
    heading['text'] = '接口标识和接口图'
    ordered.append(heading)
    # Common characteristics and the complete route index belong to identification,
    # not to additional chapter categories above individual interfaces.
    for identifier in (
        'INTRO', 'SCOPE-01', 'RELATIONS-01', 'RELATIONS-FIG', 'SYNC-FIG', '03',
        'INVENTORY', '09', '10', '13',
    ):
        ordered.append(blocks['FL-SD-INT-' + identifier])
    for identifier in ('QUERY', 'COMM'):
        block = blocks['FL-SD-INT-' + identifier]
        if block['type'] == 'heading':
            block['type'] = 'paragraph'
            block['text'] = f'**{block["text"]} / {block["id"]}**'
            block.pop('level')
        ordered.extend([block, blocks['FL-SD-INT-' + identifier + '-01']])
    ordered.append({'id': 'FL-SD-INT-DESIGNS', 'type': 'paragraph', 'text': '{{INTERFACE_DESIGNS}}'})
    result['sections']['interfaces'] = ordered
    return result


def detailed_units(blocks, groups, render_operations):
    """Render each software unit once, with operations assigned by trace identity."""
    by_id = {b['id']: b for b in blocks}
    parts = [by_id['FL-SD-DET-01']['text'], by_id['FL-SD-DET-03']['text'],
             by_id['FL-SD-DET-08']['text']]
    assignments = {}
    for item in by_id['FL-SD-DET-23']['rows']:
        assignments[item['cells'][0]] = item['cells'][2].split('、')[0]
    all_rows = [row for group in groups for row in group['rows']]
    assigned = set()
    details = {
        'FL-U-SEL': '11', 'FL-U-PLAN': '13', 'FL-U-DISPATCH': '13',
        'FL-U-EXEC': '15', 'FL-U-UPDATE': '17', 'FL-U-AGG': '19', 'FL-U-EVAL': '21',
    }
    for item in by_id['FL-SD-DET-05']['rows'] + by_id['FL-SD-DET-26']['rows']:
        cells = item['cells']
        unit = cells[0].split()[0]
        parts.extend([f'### {cells[0].split(" ", 1)[1]}/{item["id"]}',
                      '**标识符、用途**', cells[0], '**单元设计决策**', cells[1]])
        parts.append('**约束、限定或非常规特征**')
        if item.get('sourceContext'):
            parts.append(cells[3])
        else:
            parts.append(cells[2])
        state_rows = {'FL-U-C-TRAIN': 0, 'FL-U-EXEC': 1, 'FL-U-AGG': 2}
        if unit in state_rows:
            state = by_id['FL-SD-DET-07']['rows'][state_rows[unit]]['cells']
            parts.extend([f'状态约束：{state[1]}。', f'禁止的处理：{state[2]}。'])
        detail = by_id.get('FL-SD-DET-' + details.get(unit, ''))
        parts.extend(['**数据元素**', cells[1], cells[2]])
        if detail:
            parts.append('**逻辑**')
            parts.extend(f'{row["cells"][0]}：{row["cells"][1]}' for row in detail['rows'][:-1])
            parts.extend(['**异常和错误处理**', detail['rows'][-1]['cells'][1]])
        else:
            parts.extend(['**逻辑**', cells[1], '**异常和错误处理**', cells[2]])
        rows = [row for row in all_rows if row['id'] not in assigned and
                (assignments.get(row['requirementId']) == unit or
                 (item.get('sourceContext') == row.get('sourceContext') and
                  row['requirementId'] not in assignments))]
        assigned.update(row['id'] for row in rows)
        if rows:
            parts.append(render_operations([{'title': cells[0], 'rows': rows}], nested=True))
    if assigned != {row['id'] for row in all_rows}:
        raise ValueError('Every operation must belong to exactly one software unit.')
    return '\n\n'.join(parts)
