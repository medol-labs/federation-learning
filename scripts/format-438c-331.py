#!/usr/bin/env python3
"""Rearrange the reviewed 3.3.1 draft for delivery without changing its function IDs."""

import json
import re
import sys
from pathlib import Path


FUNCTION = re.compile(r'^\*\*(.+?) / (LBLS-GN-LBXX-F\d{4})\*\*$')
TABLE_FUNCTION = re.compile(r'^\|\s*\d+\s*\|.*?(LBLS-GN-LBXX-F\d{4})\s*\|', re.MULTILINE)
SECTION_LABELS = {
    '操作入口', '自动触发条件', '前提条件', '主事件流', '其他事件流（异常或扩展）',
    '后续处理', '输入要求', '业务约束', '成功结果', '状态变化', '异常结果',
    '验收情形', '查询与展示', '待核对',
}
ROOT = Path(__file__).resolve().parents[1]


def end_sentence(value):
    value = value.strip().replace('。.', '。')
    return value if value.endswith(('。', '！', '？')) else f'{value}。'


def parse_body(lines):
    sections = {}
    summary = None
    current = None
    for raw in lines:
        line = raw.strip()
        if not line or line.startswith('<!--'):
            continue
        label, separator, value = line.partition('：')
        if separator and label in SECTION_LABELS:
            current = label
            sections.setdefault(label, [])
            if value.strip():
                sections[label].append(value.strip())
        elif line.startswith('不满足约束时，'):
            sections.setdefault('不满足约束', []).append(line)
            current = None
        elif current:
            sections[current].append(line)
        elif summary is None:
            summary = line
        else:
            sections.setdefault('补充说明', []).append(line)
    if summary is None:
        raise ValueError('Function has no requirement summary')
    return summary, sections


def items(values):
    return [value.removeprefix('- ').strip().replace('。.', '。') for value in values if value.strip()]


def add_list(lines, values, fallback=None):
    values = items(values)
    if not values and fallback:
        values = [fallback]
    lines.extend(f'- {end_sentence(value)}' for value in values)
    lines.append('')


def add_table(lines, title, headers, rows, table_counter):
    lines.extend([f'**表 {table_counter[0]}：{title}**', '',
                  '| ' + ' | '.join(headers) + ' |',
                  '| ' + ' | '.join('---' for _ in headers) + ' |'])
    table_counter[0] += 1
    for row in rows:
        if len(row) != len(headers) or any('|' in cell for cell in row):
            raise ValueError('A requirement table row has an invalid width or pipe character')
        lines.append('| ' + ' | '.join(row) + ' |')
    lines.append('')


INPUT_FORMS = {
    '文本': '文本输入',
    '整数': '数值输入',
    '小数': '数值输入',
    '日期时间': '日期时间输入',
    '布尔值': '布尔选择',
}


def split_name(name):
    name = re.sub(r'([a-z\d])([A-Z])', r'\1 \2', name)
    name = re.sub(r'([A-Z])([A-Z][a-z])', r'\1 \2', name)
    return re.sub(r'\b\w', lambda match: match.group().upper(), name)


def source_description(field, translations, event_names=None):
    references = (field.get('source') or {}).get('from', [])
    descriptions = []
    for reference in references:
        if '.' not in reference:
            return None
        owner, property_name = reference.rsplit('.', 1)
        owner_title = translations.get(split_name(owner))
        property_title = translations.get(split_name(property_name))
        if not owner_title or not property_title:
            return None
        if owner in (event_names or set()):
            owner_title += '事件'
        descriptions.append(f'{owner_title}中的{property_title}')
    return '、'.join(descriptions) if descriptions else None


def matching_field(field_name, fields, translations):
    return next((field for field in fields or []
                 if translations.get(split_name(field['name'])) == field_name), None)


def input_rows(values, fallback, source_fields=None, translations=None, event_names=None):
    available_fields = list(source_fields or [])
    translations = translations or {}
    rows = []
    for value in items(values):
        match = re.fullmatch(r'(.+)（([^（）]+)）[。.]?', value)
        if not match:
            rows.append(('输入说明', '未指定', '未指定', '—', end_sentence(value), '未指定'))
            continue
        field_name, description = match.groups()
        parts = [part.strip() for part in description.split('，')]
        required = '是' if '必填' in parts else '否' if '可选' in parts else '未指定'
        field = matching_field(field_name, available_fields, translations)
        if field:
            available_fields.remove(field)
        source = source_description(field, translations, event_names) if field else None
        rules = f'类型：{parts[0]}'
        if len(parts) > 1:
            other_rules = [part for part in parts[1:] if part not in ('必填', '可选')]
            if other_rules:
                rules += '；' + '；'.join(other_rules)
        if source:
            rules += f'；来源：{source}'
        rows.append((field_name, INPUT_FORMS.get(parts[0], '未指定'), '是', '—',
                     end_sentence(rules), required))
    return rows or [('无', '—', '否', '—', fallback, '否')]


def system_input_rows(values, source_fields, translations, event_names=None):
    rows = []
    for value in items(values):
        match = re.fullmatch(r'(.+)（([^（）]+)）[。.]?', value)
        field = matching_field(match.group(1), source_fields, translations) if match else None
        source = source_description(field, translations, event_names) if field else None
        content = value.rstrip('。')
        if source:
            content += f'；来源：{source}'
        rows.append(('系统输入', end_sentence(content)))
    return rows


def business_requirement(name, summary, sections, automated, query):
    if query and sections.get('查询与展示'):
        query_details = items(sections['查询与展示'])
        first = query_details[0].split('，包含', 1)[0].rstrip('。')
        description = end_sentence((f'系统{first}' if first.startswith('应') else first) +
                                   '，展示授权范围内的业务信息')
        details = query_details[1:]
    elif automated and sections.get('自动触发条件'):
        trigger = items(sections['自动触发条件'])[0].rstrip('。')
        if trigger.endswith(name):
            condition = trigger[:-len(name)]
            description = end_sentence(f'系统应在{condition}自动{name}' if condition else f'系统应自动{name}')
        else:
            description = end_sentence(f'系统应根据“{trigger}”这一条件自动{name}')
        details = []
    else:
        description = end_sentence(summary)
        details = []

    details.extend(items(sections.get('业务约束', []))[:2])
    if not query:
        outcomes = items(sections.get('成功结果', []))
        if outcomes:
            details.append(f'完成后应形成“{outcomes[0].rstrip("。")}”的业务结果')
    return ''.join([description, *(end_sentence(detail) for detail in details)])


def format_function(name, function_id, body, table_counter=None, source_fields=None,
                    translations=None, event_names=None):
    if table_counter is None:
        table_counter = [4]
    summary, sections = parse_body(body)
    automated = '自动触发条件' in sections or '由业务过程触发' in summary or '系统应自动' in summary
    query = '查询与展示' in sections and '输入要求' not in sections and not automated
    actor_match = re.search(r'由(.+?)执行', summary)
    actor = actor_match.group(1).strip() if actor_match else '有权访问的用户' if query else '相关业务人员'
    outcomes = items(sections.get('成功结果', []))
    if outcomes:
        outcome = outcomes[0].rstrip('。')
        benefit = f'确认“{outcome}”的处理结果'
    elif query:
        benefit = '查看授权范围内的业务信息'
    else:
        benefit = '查看该业务操作的处理结果'
    if automated:
        trigger = items(sections.get('自动触发条件', []))
        occasion = trigger[0].rstrip('。') if trigger else '满足业务触发条件'
        action = '执行该操作' if name in occasion else name
        story = f'作为相关业务人员，我希望系统在{occasion}时自动{action}，以便{benefit}。'
    elif query:
        story = f'作为{actor}，我希望查询{name}，以便{benefit}。'
    else:
        story = f'作为{actor}，我希望{name}，以便{benefit}。'

    requirement = business_requirement(name, summary, sections, automated, query)
    result = [f'**{name} / {function_id}**', '', f'**用户故事：**{story}', '',
              '**需求说明**', '', requirement, '', '**（1）输入**', '', '**1）用户输入**', '']
    input_values = sections.get('输入要求', [])
    input_headers = ('字段名称', '输入形式', '可修改', '默认值', '内容及规则', '必填')
    if automated:
        add_table(result, '用户输入字段', input_headers,
                  [('无', '—', '否', '—', '本功能由业务过程自动触发，不要求用户填写输入。', '否')], table_counter)
        result.extend(['**2）系统输入与触发**', ''])
        system_inputs = [value for value in input_values if '不要求用户填写' not in value]
        system_rows = [('触发条件', value) for value in items(sections.get('自动触发条件', []))]
        system_rows.extend(system_input_rows(system_inputs, source_fields, translations or {}, event_names))
        add_table(result, '系统输入与触发', ('输入项', '内容及规则'),
                  [(label, end_sentence(value)) for label, value in system_rows] or
                  [('系统输入', '系统从已发生的业务结果中取得执行所需信息。')], table_counter)
    elif query:
        add_table(result, '用户输入字段', input_headers,
                  [('查询条件', '条件选择', '是', '—',
                    '用户在授权范围内选择页面提供的查询条件；不提交业务变更命令。', '否')], table_counter)
    else:
        add_table(result, '用户输入字段', input_headers,
                  input_rows(input_values, '不要求用户填写业务字段，由系统关联已有业务记录。',
                             source_fields, translations, event_names), table_counter)

    result.extend(['**（2）处理**', '', '**1）权限要求**', ''])
    if automated:
        permission = '仅允许已授权的系统流程触发，并校验对应业务对象的权限范围。'
    elif query:
        permission = '仅允许获授权用户查询其有权访问的数据；按标识查询详情也须校验权限。'
    else:
        permission = f'仅允许具有该操作权限的{actor}在其授权业务范围内发起请求。'
    add_list(result, [permission])

    result.extend(['**2）处理逻辑**', ''])
    processing = [summary]
    for label in ('操作入口', '自动触发条件', '前提条件', '业务约束', '主事件流', '验收情形', '待核对', '补充说明'):
        processing.extend(f'{label}：{value}' for value in items(sections.get(label, [])))
    if query:
        processing.append('查询应在权限范围内组合条件，并按稳定排序分页返回结果；查询本身不改变业务状态。')
    add_list(result, processing)

    result.extend(['**3）异常处理**', ''])
    exceptions = []
    for label in ('异常结果', '不满足约束', '其他事件流（异常或扩展）'):
        exceptions.extend(f'{label}：{value}' for value in items(sections.get(label, [])))
    if query:
        exceptions.append('无匹配记录时提示无结果；查询失败时保留条件并提示原因，不将空结果伪装为失败。')
    add_list(result, exceptions, '校验或执行失败时应说明原因，不得呈现成功结果。')

    result.extend(['**（3）输出**', ''])
    output = []
    for label in ('成功结果', '状态变化', '后续处理', '查询与展示'):
        output.extend((label, value) for value in items(sections.get(label, [])))
    if query:
        output.append(('查询反馈', '结果列表应显示当前条件下的总条数，并提供无结果、处理中及重新查询的反馈。'))
    add_table(result, '输出信息', ('输出项', '内容及规则'),
              [(label, end_sentence(value)) for label, value in output] or
              [('输出结果', '该功能未明确可验收的输出，交付前须由业务方确认。')], table_counter)
    return '\n'.join(result)


def format_document(markdown):
    register = json.loads((ROOT / 'docs/438c/merge/3.3.1-function-register.json').read_text(encoding='utf-8'))
    model = json.loads((ROOT / '.medol/codegen-model.json').read_text(encoding='utf-8'))
    slices = {item['id']: item for item in model['slices']}
    function_slices = {item['id']: slices[item['sourceId']] for item in register['functions']}
    translations = model.get('translations', {}).get('zh-CN', {})
    event_names = {event['name'] for slice_model in model['slices']
                   for event in slice_model.get('events', [])}
    lines = markdown.splitlines()
    rendered = []
    body = []
    current = None
    seen = []
    table_counter = [4]

    def flush():
        nonlocal body, current
        if current:
            slice_model = function_slices[current[1]]
            fields = [field for command in slice_model.get('commands', []) for field in command.get('fields', [])
                      if not field.get('generated') and not field.get('technicalAttribute')]
            rendered.extend(format_function(current[0], current[1], body, table_counter,
                                            fields, translations, event_names).splitlines())
            rendered.append('')
            body = []
            current = None

    for line in lines:
        function = FUNCTION.match(line)
        if function:
            flush()
            current = function.groups()
            seen.append(function.group(2))
        elif line.startswith('## '):
            flush()
            rendered.append(line)
        elif current:
            body.append(line)
        else:
            rendered.append(line)
    flush()
    table_ids = TABLE_FUNCTION.findall(markdown)
    if not table_ids or set(seen) != set(table_ids) or len(seen) != len(set(seen)) or len(table_ids) != len(set(table_ids)):
        raise ValueError('Function details must match the function table exactly')
    return '\n'.join(rendered).rstrip() + '\n'


if __name__ == '__main__':
    if len(sys.argv) != 2:
        raise SystemExit('Usage: format-438c-331.py draft.md')
    sys.stdout.write(format_document(Path(sys.argv[1]).read_text(encoding='utf-8')))
