"""Build the federation-learning database catalog from the model and JPA entities."""

import json
import re
import argparse
import hashlib
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
MODEL = ROOT / '.medol/codegen-model.json'
BACKEND = ROOT / 'federation-learning-platform'
REGISTER = ROOT / 'docs/438c/merge/database-design-register.json'
DEPLOYMENTS = {
    'FederationLearningPlatform': ('平台', 'federation-learning-platform'),
    'FederationLearningRuntimeAgent': ('参与方代理', 'federation-learning-runtime-agent'),
}
TABLE = re.compile(r'@Table\(name\s*=\s*"([^"]+)"\)')
PROPERTY = re.compile(r'^\s*var\s+(\w+):\s+([^=]+?)\s*=\s*null\s*$', re.MULTILINE)


def load_catalog(validate_register=True):
    model = json.loads(MODEL.read_text(encoding='utf-8'))
    translations = model.get('translations', {}).get('zh-CN', {})
    context_deployment = {
        context['name']: deployment['name']
        for deployment in model['deployments']
        for context in deployment['contexts']
    }
    entity_paths = {
        path.name: path
        for module in DEPLOYMENTS.values()
        for path in (BACKEND / module[1] / 'src/main/kotlin').rglob('*ReadModelEntity.kt')
    }
    catalog = []
    for slice_ in model['slices']:
        deployment = context_deployment[slice_['context']]
        if deployment not in DEPLOYMENTS:
            continue
        for read_model in slice_.get('readmodels', []):
            name = read_model['name']
            path = entity_paths.get(f'{name}ReadModelEntity.kt')
            if path is None:
                raise ValueError(f'Missing JPA entity for {name}')
            if DEPLOYMENTS[deployment][1] not in path.parts:
                raise ValueError(f'Wrong deployment for {name}: {path}')
            source = path.read_text(encoding='utf-8')
            table = TABLE.search(source)
            if table is None:
                raise ValueError(f'Missing @Table on {path}')
            lines = source.splitlines()
            properties = []
            for index, line in enumerate(lines):
                match = PROPERTY.match(line)
                if match is None:
                    continue
                annotations = []
                cursor = index - 1
                while cursor >= 0 and lines[cursor].strip().startswith('@'):
                    annotations.append(lines[cursor].strip())
                    cursor -= 1
                properties.append({
                    'name': match.group(1),
                    'type': match.group(2).strip(),
                    'id': '@Id' in annotations,
                    'text': '@Column(columnDefinition = "text")' in annotations,
                })
            model_ids = {field['name'] for field in read_model['fields'] if field.get('idAttribute')}
            entity_ids = {field['name'] for field in properties if field['id']}
            if model_ids != entity_ids:
                raise ValueError(f'JPA key differs from model for {name}: {model_ids} != {entity_ids}')
            entity_names = {field['name'] for field in properties}
            missing = {field['name'] for field in read_model['fields']} - entity_names
            if missing:
                raise ValueError(f'JPA entity omits model fields for {name}: {sorted(missing)}')
            catalog.append({
                'sourceId': read_model['id'],
                'name': name,
                'title': translations.get(read_model['title'], read_model['title']),
                'context': slice_['context'],
                'deployment': DEPLOYMENTS[deployment][0],
                'table': table.group(1),
                'fields': read_model['fields'],
                'properties': properties,
                'events': [translations.get(item['title'], item['title'])
                           for item in read_model.get('dependencies', [])
                           if item.get('elementType') == 'EVENT'],
                'sliceId': slice_['id'],
                'source': str(path.relative_to(ROOT)),
                'sourceHash': hashlib.sha256(json.dumps({
                    'readModel': read_model, 'table': table.group(1), 'properties': properties,
                }, ensure_ascii=False, sort_keys=True).encode('utf-8')).hexdigest(),
            })
    if len(catalog) < 20 or len({item['name'] for item in catalog}) != len(catalog):
        raise ValueError('Incomplete or duplicate read-model catalog')
    if validate_register:
        if not REGISTER.exists():
            raise ValueError('Database design register is missing; run database_design_inventory.py --sync')
        register = json.loads(REGISTER.read_text(encoding='utf-8'))
        if register.get('schemaVersion') != 1:
            raise ValueError('Unsupported database design register version')
        entries = {item['sourceId']: item for item in register['items'] if item['status'] == 'active'}
        current_ids = {item['sourceId'] for item in catalog}
        if set(entries) != current_ids:
            raise ValueError('Database read models differ from the design register; review and run --sync')
        for item in catalog:
            entry = entries[item['sourceId']]
            if (entry['name'] != item['name'] or entry['deployment'] != item['deployment']
                    or entry.get('sourceHash') != item['sourceHash']):
                raise ValueError(f'Database design register changed for {item["sourceId"]}; review and run --sync')
            item['number'] = entry['number']
    else:
        for index, item in enumerate(catalog, 1):
            item['number'] = index
    return catalog


def sync_register():
    catalog = load_catalog(validate_register=False)
    previous = json.loads(REGISTER.read_text(encoding='utf-8')) if REGISTER.exists() else {'items': []}
    entries = {item['sourceId']: item for item in previous['items']}
    current_ids = {item['sourceId'] for item in catalog}
    next_number = max((item['number'] for item in entries.values()), default=0) + 1
    for item in catalog:
        entry = entries.get(item['sourceId'])
        if entry is None:
            entry = {'sourceId': item['sourceId'], 'number': next_number}
            entries[item['sourceId']] = entry
            next_number += 1
        entry.update(name=item['name'], deployment=item['deployment'],
                     sourceHash=item['sourceHash'], status='active')
    for source_id, entry in entries.items():
        if source_id not in current_ids:
            entry['status'] = 'retired'
    REGISTER.write_text(json.dumps({'schemaVersion': 1, 'items': sorted(
        entries.values(), key=lambda item: item['number'],
    )}, ensure_ascii=False, indent=2) + '\n', encoding='utf-8')


def markdown_table(headers, rows):
    def escape(value):
        return str(value).replace('|', '\\|').replace('\n', ' ')

    return '\n'.join([
        '| ' + ' | '.join(map(escape, headers)) + ' |',
        '| ' + ' | '.join('---' for _ in headers) + ' |',
        *['| ' + ' | '.join(map(escape, row)) + ' |' for row in rows],
    ])


def render_logical(catalog, requirement_register):
    functions = {item['sourceId']: item for item in requirement_register['functions']}
    rows = []
    details = []
    for item in catalog:
        requirement = functions.get(item['sliceId'])
        if requirement is None:
            raise ValueError(f'Missing requirement for {item["name"]}')
        design_id = f'FL-DB-L{item["number"]:03d}'
        key = '、'.join(field['name'] for field in item['fields'] if field.get('idAttribute'))
        rows.append([design_id, item['deployment'], item['title'], key, requirement['id']])
        details.append(f'##### {item["title"]}（{design_id}）')
        details.append(f'来源业务事件：{"、".join(item["events"]) if item["events"] else "无直接事件依赖"}。')
        details.append(markdown_table(
            ['数据元素', '逻辑类型', '数量', '必需', '标识'],
            [[field['name'], field['type'],
              {'Single': '单值', 'Multiple': '多值'}.get(
                  field.get('cardinality', 'Single'), field.get('cardinality', 'Single')),
              '否' if field.get('optional') else '是',
              '是' if field.get('idAttribute') else '否'] for field in item['fields']],
        ))
    inventory = markdown_table(
        ['设计标识', '部署单元', '查询视图', '逻辑标识', '对应需求'], rows,
    )
    return inventory, '\n\n'.join(details)


def render_physical(catalog):
    rows = []
    details = []
    for item in catalog:
        design_id = f'FL-DB-P{item["number"]:03d}'
        key = '、'.join(prop['name'] for prop in item['properties'] if prop['id'])
        rows.append([design_id, item['deployment'], item['table'], key, item['name']])
        details.append(f'##### {item["title"]}（{design_id}）')
        details.append(f'查询表：`{item["table"]}`。对应逻辑视图：FL-DB-L{item["number"]:03d}。')
        details.append(markdown_table(
            ['JPA 属性', '映射类型', '主键', '长文本', '说明'],
            [[prop['name'], prop['type'], '是' if prop['id'] else '否',
              '是' if prop['text'] else '否',
              '投影元数据' if prop['name'] in {
                  'projectionUpdatedAt', 'userId', 'sessionId', 'correlationId',
                  'causationId', 'traceId', 'tenantId',
              } else '业务视图字段'] for prop in item['properties']],
        ))
    return markdown_table(
        ['设计标识', '部署单元', 'JPA 查询表', '主键属性', '对应实体'], rows,
    ), '\n\n'.join(details)


def traceability_rows(catalog, requirement_register):
    functions = {item['sourceId']: item for item in requirement_register['functions']}
    rows = []
    for item in catalog:
        requirement = functions[item['sliceId']]
        rows.append([requirement['id'], requirement['title'], f'FL-DB-L{item["number"]:03d}',
                     f'FL-DB-P{item["number"]:03d}', item['table']])
    return rows


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--sync', action='store_true', help='Accept reviewed database view changes')
    args = parser.parse_args()
    if args.sync:
        sync_register()
        print(f'Accepted database design register: {REGISTER}')
    else:
        print(f'Checked {len(load_catalog())} database read models')
