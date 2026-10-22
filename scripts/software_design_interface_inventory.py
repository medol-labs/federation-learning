"""Discover the HTTP routes exposed by the current deployable services."""

import hashlib
import re
from pathlib import Path


MODULES = (
    ('federation-learning-platform', '联邦学习平台'),
    ('federation-learning-runtime-agent', '参与方运行时代理'),
    ('federation-learning-support', '共享支撑服务'),
)
PROVIDER_CODES = {
    '联邦学习平台': 'P',
    '参与方运行时代理': 'A',
    '共享支撑服务': 'S',
    '训练引擎': 'E',
}
CLASS_ROUTE = re.compile(r'@RequestMapping\("([^"]*)"\)')
METHOD_ROUTE = re.compile(r'@(Get|Post|Put|Delete|Patch)Mapping(?:\(\s*"([^"]*)"[^\n]*\))?')
METHOD_NAME = re.compile(r'\bfun\s+(\w+)\s*\(')


def join_path(base, suffix):
    return '/' + '/'.join(part for part in (base.strip('/'), suffix.strip('/')) if part)


def words(name):
    return re.sub(r'(?<=[a-z0-9])(?=[A-Z])', ' ', name).strip()


def route_purpose(path, method_name, resource_name, translation):
    name = method_name.removesuffix('ReadModel')
    resource = resource_name.removesuffix('ReadModelResource').removesuffix('Resource')
    resource_label = translation.get(words(resource), {
        'Audit Trail': '审计记录',
        'Data Export Job': '数据导出任务',
    }.get(words(resource), words(resource)))
    if path.startswith('/sync/'):
        return '读取查询视图增量' if path.endswith('/deltas') else '读取查询视图快照'
    if name in ('getAll', 'findAll', 'list'):
        return f'查询{resource_label}列表'
    if name in ('getById', 'findById', 'getOne', 'findOne'):
        return f'查询{resource_label}详情'
    if name == 'download':
        return '下载文件'
    if name == 'export':
        return f'导出{resource_label}'
    translated = translation.get(words(name))
    if translated:
        return translated
    return {
        'login': '用户登录',
        'exchangePortalJwtForSystemSession': '交换门户登录凭据',
        'setupAdmin': '初始化管理员',
        'me': '获取当前用户信息',
        'getContent': '读取文件内容',
        'setupLocalAdmin': '初始化本地管理员',
        'setupSupabaseAdmin': '初始化 Supabase 管理员',
        'UploadFileFile': '上传文件',
        'datasetAccessValidated': '接收数据集访问校验结果',
    }.get(name, words(name) or path)


def caller_for(module, path):
    if path.startswith('/sync/'):
        return '内部同步服务'
    if path.startswith('/api/auth') or path == '/api/me':
        return '控制台'
    if module == 'federation-learning-runtime-agent' and path == '/roundexecution/receiveparticipantexecutionplan':
        return '联邦学习平台'
    if module == 'federation-learning-platform' and path in {
        '/runtimeinfrastructure/recordruntimeconnectionestablished',
        '/runtimedatasetmetadata/recordruntimedatasetmetadata',
        '/traininground/submitmodelupdatesubmission',
    }:
        return '参与方运行时代理'
    if module == 'federation-learning-platform' and path.startswith('/featureschema/featureschemacatalog'):
        return '控制台或参与方代理'
    if module == 'federation-learning-support' and path.startswith(('/uploadedfile/', '/api/files/')):
        return '控制台、平台或参与方代理'
    return '控制台或授权客户端'


def discover_routes(root, translation):
    backend = root / 'federation-learning-platform'
    shared = backend / 'shared-kernel/src/main/kotlin'
    records = []
    for module, provider in MODULES:
        files = sorted((backend / module / 'src/main/kotlin').rglob('*Resource.kt'))
        files += sorted(shared.rglob('*Resource.kt'))
        for file in files:
            source = file.read_text(encoding='utf-8')
            base = CLASS_ROUTE.search(source)
            if not base:
                raise ValueError(f'HTTP resource has no base mapping: {file}')
            mappings = list(METHOD_ROUTE.finditer(source))
            if not mappings:
                raise ValueError(f'HTTP resource has no method mapping: {file}')
            for mapping in mappings:
                method = mapping.group(1).upper()
                path = join_path(base.group(1), mapping.group(2) or '')
                declaration = METHOD_NAME.search(source, mapping.end())
                if not declaration:
                    raise ValueError(f'HTTP mapping has no method: {file}: {path}')
                key = f'{module}:{method}:{path}'
                records.append({
                    'id': f'{PROVIDER_CODES[provider]}-' + hashlib.sha256(key.encode()).hexdigest()[:8].upper(),
                    'provider': provider,
                    'caller': caller_for(module, path),
                    'method': method,
                    'path': path,
                    'purpose': route_purpose(path, declaration.group(1), file.stem, translation),
                    'source': str(file.relative_to(root)),
                    'sourceSha256': hashlib.sha256(source.encode()).hexdigest(),
                })
    engine_source = root / 'federation-learning-runtime-engine/python/gemifl/runtime/node.py'
    source = engine_source.read_text(encoding='utf-8')
    for method, path, caller, purpose, marker in (
        ('GET', '/healthz', '平台或参与方代理', '检查训练引擎健康状态', 'parsed.path == "/healthz"'),
        ('POST', '/jobs', '平台或参与方代理', '提交训练或聚合作业', 'parsed.path == "/jobs"'),
        ('GET', '/jobs/{jobId}', '平台或参与方代理', '查询作业状态', 'parsed.path.startswith("/jobs/")'),
        ('POST', '/jobs/{jobId}/cancel', '参与方代理', '取消作业', 'parsed.path.endswith("/cancel")'),
        ('GET', '/jobs/{jobId}/artifacts/{artifactName}', '联邦学习平台', '读取作业工件', '"/artifacts/" in parsed.path'),
    ):
        if marker not in source:
            raise ValueError(f'Training engine route is missing: {method} {path}')
        key = f'engine:{method}:{path}'
        records.append({
            'id': 'E-' + hashlib.sha256(key.encode()).hexdigest()[:8].upper(),
            'provider': '训练引擎',
            'caller': caller,
            'method': method,
            'path': path,
            'purpose': purpose,
            'source': str(engine_source.relative_to(root)),
            'sourceSha256': hashlib.sha256(source.encode()).hexdigest(),
        })
    keys = [(item['provider'], item['method'], item['path']) for item in records]
    if len(keys) != len(set(keys)):
        raise ValueError('Duplicate HTTP route in interface inventory.')
    ids = [item['id'] for item in records]
    if len(ids) != len(set(ids)):
        raise ValueError('Interface inventory identifier collision.')
    return sorted(records, key=lambda item: (item['provider'], item['path'], item['method']))


def render_inventory(records):
    parts = []
    for provider in ('联邦学习平台', '参与方运行时代理', '共享支撑服务', '训练引擎'):
        group = [record for record in records if record['provider'] == provider]
        parts.extend((
            f'##### {provider}对外提供的 HTTP 接口（{len(group)} 项）',
            '',
            '| 接口标识 | 调用方 | 方法与相对路径 | 用途 |',
            '| --- | --- | --- | --- |',
        ))
        for record in group:
            cells = (record['id'], record['caller'], f'{record["method"]} {record["path"]}', record['purpose'])
            parts.append('| ' + ' | '.join(cell.replace('|', '\\|') for cell in cells) + ' |')
        parts.append('')
    return '\n'.join(parts).strip()
