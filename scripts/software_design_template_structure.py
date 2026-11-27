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


def detailed_units(blocks, groups, render_operations, *, render_unit=None, opening=None, arrange_units=None):
    """Render each software unit once, with operations assigned by trace identity."""
    by_id = {b['id']: b for b in blocks}
    parts = list(opening) if opening is not None else [
        by_id['FL-SD-DET-01']['text'], by_id['FL-SD-DET-03']['text'], by_id['FL-SD-DET-08']['text']
    ]
    assignments = {}
    for item in by_id['FL-SD-DET-23']['rows']:
        assignments[item['cells'][0]] = item['cells'][2].split('、')[0]
    all_rows = [row for group in groups for row in group['rows']]
    assigned = set()
    units = []
    details = {
        'FL-U-SEL': '11', 'FL-U-PLAN': '13', 'FL-U-DISPATCH': '13',
        'FL-U-EXEC': '15', 'FL-U-UPDATE': '17', 'FL-U-AGG': '19', 'FL-U-EVAL': '21',
    }
    for item in by_id['FL-SD-DET-05']['rows'] + by_id['FL-SD-DET-26']['rows']:
        cells = item['cells']
        unit = cells[0].split()[0]
        title = f'{cells[0].split(" ", 1)[1]}/{item["id"]}'
        if render_unit is not None:
            detail_lines = render_unit(item)
        else:
            detail_lines = ['**标识符、用途**', cells[0], '**单元设计决策**', cells[1],
                            '**约束、限定或非常规特征**',
                            cells[3] if item.get('sourceContext') else cells[2]]
            state_rows = {'FL-U-C-TRAIN': 0, 'FL-U-EXEC': 1, 'FL-U-AGG': 2}
            if unit in state_rows:
                state = by_id['FL-SD-DET-07']['rows'][state_rows[unit]]['cells']
                detail_lines.extend([f'状态约束：{state[1]}。', f'禁止的处理：{state[2]}。'])
            detail = by_id.get('FL-SD-DET-' + details.get(unit, ''))
            detail_lines.extend(['**数据元素**', cells[1], cells[2]])
            if detail:
                detail_lines.append('**逻辑**')
                detail_lines.extend(f'{row["cells"][0]}：{row["cells"][1]}' for row in detail['rows'][:-1])
                detail_lines.extend(['**异常和错误处理**', detail['rows'][-1]['cells'][1]])
            else:
                detail_lines.extend(['**逻辑**', cells[1], '**异常和错误处理**', cells[2]])
        rows = [row for row in all_rows if row['id'] not in assigned and
                (assignments.get(row['requirementId']) == unit or
                 (item.get('sourceContext') == row.get('sourceContext') and
                  row['requirementId'] not in assignments))]
        assigned.update(row['id'] for row in rows)
        units.append((unit, title, detail_lines, rows))
    if assigned != {row['id'] for row in all_rows}:
        raise ValueError('Every operation must belong to exactly one software unit.')
    if arrange_units is not None:
        parts.extend(arrange_units(units))
    else:
        labels = {item['id']: item['cells'][0] for item in by_id['FL-SD-DET-05']['rows'] + by_id['FL-SD-DET-26']['rows']}
        for _, title, detail_lines, rows in units:
            parts.extend([f'### {title}', *detail_lines])
            if rows:
                parts.append(render_operations([{'title': labels[title.rsplit('/', 1)[1]], 'rows': rows}], nested=True))
    return '\n\n'.join(parts)
