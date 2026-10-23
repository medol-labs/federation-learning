"""Build reviewable operation-level SDD rows from the SRS and current model."""

import re


HEADING = re.compile(r'^\*\*(.+?) / (LBLS-GN-LBXX-F\d{4})\*\*$', re.MULTILINE)
SECTIONS = ('输入要求：', '业务约束：', '查询与展示：', '成功结果：', '状态变化：', '不满足约束时，', '验收情形：')


def requirement_sections(markdown):
    headings = list(HEADING.finditer(markdown))
    sections = {}
    for index, heading in enumerate(headings):
        identifier = heading.group(2)
        if identifier in sections:
            raise ValueError(f'Duplicate SRS function: {identifier}')
        body = markdown[heading.end():headings[index + 1].start() if index + 1 < len(headings) else len(markdown)]
        if '\n## ' in body:
            body = body.split('\n## ', 1)[0]
        sections[identifier] = (heading.group(1), body.strip())
    return sections


def section_lines(body, label):
    match = re.search(rf'^{re.escape(label)}\s*$', body, re.MULTILINE)
    if not match:
        return []
    following = body[match.end():].strip()
    for marker in SECTIONS:
        next_marker = re.search(rf'^{re.escape(marker)}', following, re.MULTILINE)
        if next_marker:
            following = following[:next_marker.start()]
    return [line.removeprefix('- ').strip().rstrip('。；') for line in following.splitlines()
            if line.strip().startswith('- ')]


def inline_value(body, label):
    match = re.search(rf'^{re.escape(label)}\s*(.+)$', body, re.MULTILINE)
    return match.group(1).strip().rstrip('。；') if match else ''


def translated(title, translations):
    value = translations.get(title, title)
    return value if value and value != title else title


def summarized_items(items, limit, noun):
    if len(items) <= limit:
        return '、'.join(items)
    return '、'.join(items[:limit]) + f'等；其余{noun}见需求规格和接口定义'


def query_summary(details):
    if not details:
        return ''
    description = details[0]
    if '包含' not in description:
        return description
    prefix, fields = description.split('包含', 1)
    names = [name.strip() for name in re.split(r'[、，,]', fields) if name.strip()]
    if len(names) <= 8:
        return description
    return prefix + '包含' + '、'.join(names[:8]) + '等字段；完整字段见需求规格和查询接口'


def operation_rows(model, register, srs_markdown):
    srs = requirement_sections(srs_markdown)
    functions = register['functions']
    if set(srs) != {item['id'] for item in functions}:
        raise ValueError('SRS detail entries differ from the function register.')
    slices = {item['id']: item for item in model['slices']}
    concepts = {item['sourceKey']: item for item in register['concepts']}
    translations = model.get('translations', {}).get('zh-CN', {})
    groups = []
    current_group = None
    for function in functions:
        identifier = function['id']
        title, body = srs[identifier]
        if title != function['title']:
            raise ValueError(f'SRS title differs from the function register: {identifier}')
        slice_ = slices.get(function['sourceId'])
        if not slice_ or slice_['name'] != function['sourceName']:
            raise ValueError(f'Current model slice differs from the function register: {identifier}')
        concept_key = f'{function["sourceContext"]}/{function["sourceConcept"]}'
        concept = concepts.get(concept_key)
        if not concept:
            raise ValueError(f'Function has no registered business object: {identifier}')
        if current_group is None or current_group['conceptId'] != concept['id']:
            current_group = {'conceptId': concept['id'], 'title': concept['title'], 'rows': []}
            groups.append(current_group)

        inputs = section_lines(body, '输入要求：')
        constraints = section_lines(body, '业务约束：')
        query_details = section_lines(body, '查询与展示：')
        events = slice_['events']
        commands = slice_['commands']
        readmodels = slice_['readmodels']
        processors = slice_['processors']
        if readmodels and not commands and not events:
            names = '、'.join(translated(item['title'], translations) for item in readmodels)
            input_design = f'在授权范围内接收分页、排序及适用字段的筛选条件；查询对象：{concept["title"]}。'
            process = f'从本服务的{names}查询视图按条件读取；组合条件同时生效，返回满足条件的总条数。'
            result = '无匹配记录返回空结果；查询失败保留条件供重试；查询不改变业务状态。'
            if query_details:
                result = f'{query_summary(query_details)}；{result}'
        else:
            if not commands and not processors:
                raise ValueError(f'Operation has neither command nor query: {identifier}')
            trigger = []
            for processor in processors:
                for dependency in processor.get('dependencies', []):
                    if dependency.get('direction') == 'INBOUND' and dependency.get('elementType') == 'EVENT':
                        event_title = translated(dependency['title'], translations)
                        if event_title not in trigger:
                            trigger.append(event_title)
            input_design = ('接收' + summarized_items(inputs, 8, '输入字段') + '。') if inputs else '接收关联业务标识及操作参数。'
            if trigger:
                input_design = f'由“{"、".join(trigger)}”事件触发；' + input_design
            if commands and not any(command.get('startsLifecycle') for command in commands):
                input_design += f'按{concept["title"]}标识读取当前状态。'
            process_parts = [
                '核对触发事件与后续处理条件' if not commands else
                '校验创建所需的输入与先决条件' if any(command.get('startsLifecycle') for command in commands)
                else f'校验{concept["title"]}的必填参数与状态前置条件'
            ]
            if constraints:
                process_parts.append(summarized_items(constraints, 2, '业务约束'))
            if slice_.get('port'):
                process_parts.append('调用本操作对应的外部能力适配器，以返回结果决定后续处理')
            if not commands:
                next_action = inline_value(body, '后续处理：')
                if next_action:
                    process_parts.append(next_action)
            process = '；'.join(process_parts).rstrip('。') + '。'
            event_names = '、'.join(translated(event['title'], translations) for event in events)
            success = inline_value(body, '成功结果：')
            state = inline_value(body, '状态变化：')
            result_parts = []
            if event_names:
                result_parts.append(f'追加业务结果事件：{event_names}')
            elif success:
                result_parts.append(f'业务结果：{success}')
            if state and (translated(state, translations) != state or re.search(r'[\u4e00-\u9fff]', state)):
                result_parts.append(f'目标状态：{translated(state, translations)}')
            if events:
                result_parts.append('不满足约束或外部调用失败时，不得追加与实际结果不符的成功事件'
                                    if slice_.get('port') else '不满足约束时不得追加成功事件')
                result_parts.append('查询视图由已保存事件异步更新')
            else:
                result_parts.append('本步骤分发后续操作，不单独追加业务事件')
            if readmodels:
                names = '、'.join(translated(item['title'], translations) for item in readmodels)
                result_parts.append(f'可在授权范围内查询{names}的处理状态与结果')
            result = '；'.join(result_parts) + '。'

        current_group['rows'].append({
            'id': f'FL-SD-OP-{identifier.rsplit("-", 1)[1]}',
            'requirementId': identifier,
            'title': title,
            'cells': [f'{title} / FL-SD-OP-{identifier.rsplit("-", 1)[1]}',
                      input_design, process, result],
        })
    return groups


def render_operation_design(groups):
    def table_row(cells):
        return '| ' + ' | '.join(value.replace('|', '\\|').replace('\n', ' ') for value in cells) + ' |'

    parts = ['### 按业务对象的操作处理设计',
             '以下设计行与软件需求规格说明的功能标识逐项对应。输入字段的完整定义见需求规格和接口设计；'
             '这里说明状态来源、处理顺序、业务结果和查询更新边界。外部能力尚有未完成实现的，仍以本章关键软件单元的当前限制为准。']
    for group in groups:
        parts.append(f'#### {group["title"]} / {group["conceptId"]}')
        parts.append('\n'.join([
            table_row(['操作设计项', '输入与状态来源', '处理与外部依赖', '结果、异常及查询更新']),
            table_row(['---'] * 4),
            *(table_row(row['cells']) for row in group['rows']),
        ]))
    return '\n\n'.join(parts)
