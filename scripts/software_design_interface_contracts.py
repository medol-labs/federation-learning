"""Build the reference project's structure.4.3 dynamic subsection input.

An interface is an existing resource contract, not a hand-picked traffic group.
All routes must resolve to a model contract or an explicitly reviewed contract.
"""

import hashlib
import json
import re
from collections import OrderedDict
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
OVERRIDES = ROOT / 'docs/438c/merge/software-design-interface-contracts.json'
LABELS = ('名称/标识符', '数据类型', '大小与格式', '计量单位', '可能值的范围或枚举',
          '优先级、定时、频率、容量、序列', '保密性约束', '来源和接受者')


def schema_text(fields, model, context, seen=()):
    lines = []
    for field in fields:
        name, type_ = field['name'], field['type']
        shape = field.get('wireType') or type_ + ('[]' if field.get('cardinality') == 'Multiple' else '')
        notes = ['可空' if field.get('optional') else '非空']
        if field.get('generated'):
            notes.append('系统生成标识')
        if field.get('dictionary'):
            notes.append('字典取值：' + field['dictionary'])
        candidates = [v for v in model['valueTypes'] if v['name'] == type_]
        local = [v for v in candidates if v.get('context', '').lower() == context]
        value_type = (local or candidates or [None])[0]
        if value_type and value_type.get('values'):
            notes.append('枚举：' + '、'.join(value_type['values']))
        lines.append(f'{name}：{shape}；' + '；'.join(notes) + '。')
        if value_type and value_type.get('fields') and type_ not in seen:
            nested = schema_text(value_type['fields'], model, context, (*seen, type_))
            lines.append(f'{type_}的组成：' + nested)
    return '\n'.join(lines) or '无独立字段。'


def wire_fields(record, element, is_query):
    suffix = 'ReadModel' if is_query else 'Command'
    name = element['name'] + suffix
    path = ROOT / Path(record['source']).parent / (name + '.kt')
    text = path.read_text(encoding='utf-8')
    start = re.search(r'\bdata class\s+' + re.escape(name) + r'\s*\(', text)
    if not start:
        raise ValueError(f'Data contract declaration is missing: {path}')
    body = text[start.end():].split('\n)', 1)[0]
    declarations = re.findall(r'\bval\s+(\w+)\s*:\s*([^\n=]+)', body)
    if not declarations:
        raise ValueError(f'Data contract fields cannot be read: {path}')
    by_name = {f['name']: f for f in element['fields']}
    fields = []
    for field_name, declared_type in declarations:
        declared_type = declared_type.strip().rstrip(',').strip()
        field = dict(by_name.get(field_name, {'name': field_name, 'type': declared_type.rstrip('?')}))
        field['optional'] = declared_type.endswith('?')
        field['wireType'] = declared_type.rstrip('?')
        fields.append(field)
    return fields


def model_contract(record, model):
    context = record.get('context')
    if not context:
        return None
    name = record['operation']
    resource = record['resource']
    is_query = resource.endswith('ReadModelResource')
    if is_query:
        name = resource.removesuffix('ReadModelSyncReadModelResource').removesuffix('ReadModelResource')
    if record['path'].endswith('/file'):
        return None  # Multipart wire fields differ from the internal command.
    collection = 'readmodels' if is_query else 'commands'
    matches = [element for s in model['slices'] if s['context'].lower() == context
               for element in s[collection] if element['name'] == name]
    if len(matches) != 1:
        return None
    element = matches[0]
    fields = schema_text(wire_fields(record, element, is_query), model, context)
    if not is_query:
        return {'request': fields, 'response': 'JSON对象，字段与本操作上述请求命令相同。',
                'result': '同步命令处理完成后返回；后续事件消费和查询视图更新不由本次响应表示。',
                'format': 'application/json', 'name': model['translations']['zh-CN'].get(element['title'], element['title'])}
    if record['path'].startswith('/sync/'):
        delta = record['path'].endswith('/deltas')
        return {'name': model['translations']['zh-CN'].get(element['title'], element['title']) + '同步',
                'request': ('afterSequence：Long，默认0。' if delta else 'cursor：String，可空，快照页游标。')
                           + 'size：Int，默认200，限制为1至1000；其他参数为字段相等筛选。',
                'response': ('items为变更数组，包含sequence、operation、channel、sourceContext、sourceReadModel、'
                             'readModelKey、eventId、eventType、occurredAt、payload；nextSequence为后续读取序号。'
                             if delta else 'items为快照记录数组；nextCursor为下一页游标，可空；highWatermarkSequence为高水位序号。')
                            + '\n每条业务记录的数据元素：\n' + fields,
                'result': '读取快照或已保存的增量记录，不改变业务状态。', 'format': 'application/json'}
    paths = re.findall(r'\{([^}]+)\}', record['path'])
    request = ('路径参数：' + '、'.join(paths) + '，用于唯一定位记录。') if paths else (
        'page：页码，从0开始；size：每页条数；sort：字段与排序方向；'
        '采用字段名与运算符组合的条件参数，字段范围为下述查询数据元素。')
    response = ('JSON记录对象。' if paths else '分页对象，content为记录数组，包含总条数、总页数及当前页信息。')
    if record['operation'] == 'export':
        response = '导出文件；内容字段为下述查询数据元素。'
    return {'name': model['translations']['zh-CN'].get(element['title'], element['title']),
            'request': request, 'response': response + '\n' + fields,
            'result': '查询不改变业务状态；列表无匹配返回空结果，详情不存在按接口返回未找到结果。',
            'format': 'application/json' if record['operation'] != 'export' else '按导出接口返回文件媒体类型'}


def resource_interfaces(records, model, root=ROOT):
    overrides = json.loads(OVERRIDES.read_text(encoding='utf-8'))['contracts']
    grouped = OrderedDict()
    for record in records:
        key = (record['provider'], record['source'])
        grouped.setdefault(key, []).append(record)
    interfaces = []
    for (provider, source), routes in sorted(grouped.items(), key=lambda pair: (
            ('联邦学习平台', '参与方运行时代理', '共享支撑服务', '训练引擎').index(pair[0][0]), pair[0][1])):
        identifier = 'IF-' + hashlib.sha256(f'{provider}:{source}'.encode()).hexdigest()[:10].upper()
        contracts = []
        for record in routes:
            key = record['method'] + ' ' + record['path']
            contract = overrides.get(key) or model_contract(record, model)
            if not contract or any(not contract.get(field) for field in ('request', 'response', 'result', 'format', 'name')):
                raise ValueError(f'Interface contract is incomplete: {provider} {key}; supply an explicit contract.')
            contracts.append((record, contract))
        callers = '、'.join(dict.fromkeys(r['caller'] for r in routes))
        names = list(dict.fromkeys(c['name'] for _, c in contracts))
        name = names[0] if len(names) == 1 else routes[0]['resource'].removesuffix('Resource')
        name = {'IamAuth': '用户认证', 'IamAdminSetup': '管理员初始化',
                'RuntimeEngine': '训练引擎作业', 'DataExportJob': '数据导出作业',
                'DataImportJob': '数据导入作业'}.get(name, name)
        title = f'{provider}{name}接口'
        data = []
        for record, contract in contracts:
            data.extend([f'{record["purpose"]}（操作标识：{record["id"]}）。',
                         '请求数据：\n' + contract['request'], '响应数据：\n' + contract['response'],
                         '处理结果与异常：' + contract['result']])
        # Exact public shape used by the reference dynamic-section filler: name/content.
        characteristics = [
            title + ' / ' + identifier,
            '\n\n'.join(data),
            '；'.join(dict.fromkeys(c['format'] for _, c in contracts)),
            '文件大小以字节计；分页数量以条计。其他数值字段按其业务数据定义使用，不统一换算。',
            '字段类型、必填性、可空性及枚举见上述数据元素；未给出长度或数值上限的字段不另设虚构限值。',
            '按请求触发；涉及异步处理的结果通过关联业务标识查询。超时不等于未执行，不得无条件重复写入。',
            ('训练引擎不校验内部令牌，访问范围限制在调用该引擎的平台或代理。' if provider == '训练引擎'
             else '按用户身份、操作权限或服务凭据控制访问。登录及初始化使用对应的凭据校验。'),
            f'请求来源：{callers}；接收方：{provider}；响应返回原调用方。',
        ]
        content = [f'本条描述{title}。', f'接口实体：{callers}与{provider}。',
                   '接口类型：HTTP请求/响应接口。', '接口特性：']
        content.extend(f'{i}) {label}：{value}' for i, (label, value) in enumerate(zip(LABELS, characteristics), 1))
        methods = '；'.join(f'{r["method"]} {r["path"]}（{r["id"]}）' for r in routes)
        content.extend(['通信方法：' + methods + '。', '协议：HTTP；消息格式见大小与格式。'])
        interfaces.append({'id': identifier, 'name': title, 'content': '\n\n'.join(content),
                           'routes': [r['id'] for r in routes], 'provider': provider, 'source': source})
    return interfaces


def reference_project(interfaces, summary):
    return {'structure': {'4.3': {'title': '接口设计', 'type': 'content_generation', 'content': summary,
            'subsections': {f'4.3.{i}': {'name': item['name'], 'content': item['content']}
                            for i, item in enumerate(interfaces, 2)}}}}


def render_reference_subsections(project):
    section = project['structure']['4.3']
    parts = []
    for index, (number, item) in enumerate(section['subsections'].items(), 2):
        if number != f'4.3.{index}' or set(item) != {'name', 'content'}:
            raise ValueError(f'Invalid reference template subsection: {number}')
        parts.extend(['#### ' + item['name'], item['content']])
    return '\n\n'.join(parts)
