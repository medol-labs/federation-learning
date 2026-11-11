"""Focused checks for the controlled software-design merge baseline."""

import importlib.util
import json
import re
import tempfile
import unittest
from copy import deepcopy
from pathlib import Path

from docx import Document
from docx.oxml.ns import qn


SCRIPT = Path(__file__).with_name('merge-438c-software-design.py')
spec = importlib.util.spec_from_file_location('merge_438c_software_design', SCRIPT)
merge = importlib.util.module_from_spec(spec)
spec.loader.exec_module(merge)
from software_design_interface_contracts import resource_interfaces, reference_project, render_reference_subsections


class SoftwareDesignTrackingTest(unittest.TestCase):
    def test_interfaces_cover_contracts_and_use_reference_subsections(self):
        source = merge.read_structured_sections(merge.DEFAULT_SECTIONS)
        model = json.loads(merge.MODEL.read_text())
        records = merge.discover_routes(merge.ROOT, model['translations']['zh-CN'])
        items = resource_interfaces(records, model)
        covered = [route for item in items for route in item['routes']]
        self.assertEqual(sorted(covered), sorted(r['id'] for r in records))
        project = reference_project(items, '接口标识和接口图正文')
        subsection = project['structure']['4.3']['subsections']['4.3.2']
        self.assertEqual(set(subsection), {'name', 'content'})
        self.assertEqual(len(items), len({(r['provider'], r['source']) for r in records}))
        rendered = merge.render_interface_designs(source, records, model, merge.interface_field_rows)
        headings = re.findall(r'^#### (.+)', rendered, re.MULTILINE)
        self.assertEqual(headings, [item['name'] for item in items])
        for label in ('接口实体', '接口类型', '接口特性', '通信方法', '协议'):
            self.assertEqual(len(re.findall(rf'^{label}：', rendered, re.MULTILINE)), len(items))
        for number in range(1, 9):
            self.assertEqual(len(re.findall(rf'^{number}\)', rendered, re.MULTILINE)), len(items))
        self.assertIn('organizationName', rendered)
        self.assertIn('请求数据：', rendered)
        self.assertIn('响应数据：', rendered)
        self.assertNotIn('FL-IF-07', rendered)
        merge.validate_interface_diagrams(source, merge.ROOT)
        changed = deepcopy(source)
        changed['sections']['interfaces'] = [b for b in changed['sections']['interfaces']
                                            if b['id'] != 'FL-SD-INT-RELATIONS-FIG']
        with self.assertRaisesRegex(ValueError, 'providing entities'):
            merge.validate_interface_diagrams(changed, merge.ROOT)
        altered = deepcopy(project)
        altered['structure']['4.3']['subsections']['4.3.2']['extra'] = 'invalid'
        with self.assertRaisesRegex(ValueError, 'Invalid reference template'):
            render_reference_subsections(altered)

    def test_interface_conventions_are_design_rules_not_delivery_instructions(self):
        source = merge.read_structured_sections(merge.DEFAULT_SECTIONS)
        blocks = {block['id']: block for block in source['sections']['interfaces']}
        text = '\n'.join(blocks[key]['text'] for key in ('FL-SD-INT-09', 'FL-SD-INT-10', 'FL-SD-INT-13'))
        self.assertNotIn('FL-SD-INT-11', blocks)
        self.assertNotIn('FL-SD-INT-12', blocks)
        for phrase in ('交付正文', 'RestClient', '交付前确认', '当前代码', '一同归档', '接口变更须'):
            self.assertNotIn(phrase, text)
        self.assertIn('训练引擎接口不执行上述令牌校验', text)
        self.assertIn('不表示后续异步处理', text)
        self.assertIn('不得无条件重复提交', text)

    def test_reference_template_structure_and_unique_operation_ownership(self):
        source = merge.read_structured_sections(merge.DEFAULT_SECTIONS)
        rendered = merge.render_structured_sections(source)
        interfaces = rendered['interfaces']
        self.assertEqual(re.findall(r'^#### (.+)', interfaces, re.MULTILINE)[0], '接口标识和接口图')
        self.assertEqual(len(re.findall(r'^#### ', interfaces, re.MULTILINE)), 1)
        model = json.loads(merge.MODEL.read_text())
        records = merge.discover_routes(merge.ROOT, model['translations']['zh-CN'])
        interfaces = interfaces.replace('{{INTERFACE_DESIGNS}}',
            merge.render_interface_designs(source, records, model, merge.interface_field_rows))
        self.assertEqual(len(re.findall(r'^#### ', interfaces, re.MULTILINE)), 1 + len(resource_interfaces(records, model)))
        self.assertFalse(any('FL-IF-' in title for title in re.findall(r'^#### (.+)', interfaces, re.MULTILINE)))
        self.assertIn('GET /jobs/{jobId}/artifacts/{artifactName}', interfaces)
        self.assertNotRegex(interfaces, r'^#{4,5} (接口总体设计|关键接口详细设计|接口设计通用约定)')
        self.assertNotIn('##### 执行计划下发', interfaces)
        for label in ('接口实体', '接口类型', '通信方法', '协议', '接口特性'):
            self.assertIn(label + '：', interfaces)
        detailed = rendered['detailed']
        operations = re.findall(r'^#### .+ / (FL-SD-OP-F\d+)$', detailed, re.MULTILINE)
        self.assertEqual(len(operations), 192)
        self.assertEqual(len(set(operations)), 192)
        self.assertEqual(len(re.findall(r'^### ', detailed, re.MULTILINE)), 23)
        for title in ('关键软件单元及处理决策', '其他业务上下文的软件单元设计',
                      '按软件单元的操作详细设计', '功能需求与软件单元追踪'):
            self.assertNotIn(title, detailed)
        for label in ('标识符、用途', '单元设计决策', '约束、限定或非常规特征',
                      '数据元素', '逻辑', '异常和错误处理'):
            self.assertIn(f'**{label}**', detailed)

    def test_interface_introduction_describes_design_not_document_generation(self):
        source = merge.read_structured_sections(merge.DEFAULT_SECTIONS)
        intro = next(item['text'] for item in source['sections']['interfaces']
                     if item['id'] == 'FL-SD-INT-INTRO')
        self.assertIn('本节规定', intro)
        self.assertNotIn('路由生成', intro)
        self.assertNotIn('OpenAPI', intro)
        self.assertFalse(any('归档各服务的 OpenAPI' in block.get('text', '')
                             for block in source['sections']['interfaces']))
        self.assertIn('管理接口与业务接口分开管理', intro)

    def test_design_heading_matches_requirements_typography(self):
        document = Document()
        paragraph = document.add_paragraph('5.1.1 参与者选择 / FL-SD-DET-05-R01', style='Heading 3')
        merge.format_design_heading(paragraph)
        self.assertEqual(paragraph.text, '5.1.1 参与者选择/FL-SD-DET-05-R01')
        self.assertTrue(paragraph.runs[0].bold)
        self.assertEqual(paragraph.runs[0].font.name, 'Times New Roman')
        for run in paragraph.runs[1:]:
            self.assertTrue(run.bold)
            self.assertFalse(run.italic)
            self.assertEqual(run.font.name, 'Times New Roman' if run.text.isascii() and run.text.strip() else '黑体')

    def test_preview_chapter_numbers_are_independent(self):
        document = Document()
        for title, level in [('联邦学习模块接口设计', 3), ('接口总体设计', 4),
                             ('接口详细设计', 4), ('下发计划', 5),
                             ('联邦学习模块CSCI详细设计', 2), ('单元设计', 3),
                             ('组织', 4), ('注册组织', 5)]:
            document.add_paragraph(title, style=f'Heading {level}')
        merge.number_preview_headings(document)
        self.assertEqual([p.text.split()[0] for p in document.paragraphs],
                         ['4.4.1', '4.4.1.1', '4.4.1.2', '4.4.1.2.1',
                          '5.1', '5.1.1', '5.1.1.1', '5.1.1.1.1'])
        self.assertTrue(all(p._p.pPr.numPr.numId.val == 0 for p in document.paragraphs))

    def test_detailed_sections_keep_all_fields_and_group_nonadjacent_units(self):
        groups = merge.load_operation_design()
        self.assertEqual(len(groups), len({group['conceptId'] for group in groups}))
        rendered = merge.render_operation_design(groups)
        self.assertEqual(len(re.findall(r'^#### .+ / FL-SD-OP-F\d+', rendered, re.MULTILINE)), 192)
        organization = groups[0]
        activated = next(row for row in organization['rows'] if row['requirementId'].endswith('F0002'))
        self.assertEqual(activated['transitions'][0]['from'], 'Registered')
        self.assertIn('organizationId', rendered)
        self.assertIn('分支条件与预期处理', rendered)
        self.assertNotIn('其余输入字段见', rendered)

    def test_interface_contract_expands_fields_and_rejects_stale_descriptions(self):
        source = merge.read_structured_sections(merge.DEFAULT_SECTIONS)
        model = json.loads(merge.MODEL.read_text())
        block = next(item for item in source['sections']['interfaces'] if item.get('commandRef'))
        rows = merge.interface_field_rows(block, model)
        self.assertGreater(len(rows), len(block['rows']))
        self.assertIn('baseModelPlugin', [row[0] for row in rows])
        changed = deepcopy(block)
        changed['rows'][0]['cells'][0] = 'removedField'
        with self.assertRaisesRegex(ValueError, 'removed fields'):
            merge.interface_field_rows(changed, model)

    def test_other_sections_render_from_stable_items(self):
        source = merge.read_structured_sections(merge.DEFAULT_SECTIONS)
        rendered = merge.render_structured_sections(source)
        self.assertEqual(set(rendered), set(merge.ANCHORS) - {'decisions'})
        self.assertIn('{{READ_MODEL_INVENTORY}}', rendered['db-logical'])
        self.assertIn('| 部件 | 主要职责 | 持久化或外部依赖 |', rendered['architecture'])
        self.assertIn('### 联邦学习模块性能设计', rendered['performance'])
        self.assertEqual(rendered['interfaces'].count('{{INTERFACE_INVENTORY}}'), 1)
        self.assertIn('当前各后端模块配置了 Hibernate ddl-auto=update', rendered['db-physical'])
        self.assertIn('当前平台聚合适配器会在事件处理链中轮询等待引擎结果', rendered['execution'])
        self.assertIn('当前引擎服务端不校验', rendered['architecture'])
        self.assertIn('各软件单元之间的通信与数据访问边界', rendered['architecture'])
        self.assertNotIn('### 安全边界与权限决策', rendered['architecture'])
        self.assertIn('FL-U-AGG 模型聚合', rendered['detailed'])
        self.assertIn('FL-U-EXEC 本地执行', rendered['detailed'])
        self.assertIn('### 组织管理/FL-SD-DET-26-R01', rendered['detailed'])
        self.assertIn('### 参与者选择/FL-SD-DET-05-R01', rendered['detailed'])
        self.assertIn('注册组织 / FL-SD-OP-F0001', rendered['detailed'])
        self.assertIn('字典值翻译目录 / FL-SD-OP-F0171', rendered['detailed'])
        self.assertIn('服务账户 API 令牌目录 / FL-SD-OP-F0192', rendered['detailed'])
        self.assertIn('### 身份访问管理/FL-SD-DET-26-R15', rendered['detailed'])
        self.assertNotIn('不把开发模式自动等同于生产级保密保证', rendered['detailed'])
        self.assertIn('当前本地全局模型评估适配器返回固定精度与公平性数值', rendered['detailed'])
        self.assertIn('LBLS-GN-LBXX-F0131', rendered['detailed'])
        self.assertIn('{{FIGURE:FL-SD-DBC-04}}', rendered['db-conceptual'])
        self.assertIn('{{FIGURE:FL-SD-DBC-05}}', rendered['db-conceptual'])
        self.assertIn('组织与联邦均可关联多条成员资格', rendered['db-conceptual'])

    def test_detailed_design_requirement_references_are_current(self):
        source = merge.read_structured_sections(merge.DEFAULT_SECTIONS)
        signatures = merge.requirement_signatures(source)
        self.assertEqual(len(signatures), 28)
        self.assertIn('LBLS-GN-LBXX-F0066', signatures)
        changed = deepcopy(source)
        table = next(block for block in changed['sections']['detailed'] if block['id'] == 'FL-SD-DET-23')
        table['rows'][0]['cells'][1] = '错误的功能名称'
        with self.assertRaisesRegex(ValueError, 'requirement trace is invalid'):
            merge.requirement_signatures(changed)

    def test_operation_design_covers_commands_queries_and_automation(self):
        groups = merge.load_operation_design()
        rows = {row['requirementId']: row for group in groups for row in group['rows']}
        self.assertEqual(len(rows), 192)
        self.assertIn('组织名称必须唯一', rows['LBLS-GN-LBXX-F0001']['cells'][2])
        self.assertIn('查询视图按条件读取', rows['LBLS-GN-LBXX-F0005']['cells'][2])
        self.assertIn('选择训练轮次参与者', rows['LBLS-GN-LBXX-F0064']['cells'][2])
        self.assertIn('不单独追加业务事件', rows['LBLS-GN-LBXX-F0064']['cells'][3])
        self.assertIn('外部能力适配器', rows['LBLS-GN-LBXX-F0028']['cells'][2])
        self.assertTrue(all(row['id'].startswith('FL-SD-OP-F') for row in rows.values()))

    def test_chapter_six_traces_every_current_function_in_both_directions(self):
        source = merge.read_structured_sections(merge.DEFAULT_SECTIONS)
        traces = merge.traceability_rows(source)
        self.assertEqual(len(traces), 195)
        self.assertEqual(len({row['id'] for row in traces}), len(traces))
        selected = next(row for row in traces if row['id'] == 'LBLS-GN-LBXX-F0066')
        self.assertIn('FL-SD-OP-F0066', selected['design'])
        self.assertIn('FL-SD-OP-F0001', traces[0]['design'])
        self.assertIn('FL-SD-OP-F0192', traces[191]['design'])
        self.assertTrue(all('FL-U-' not in row['design'] for row in traces))
        self.assertEqual(
            {row['id']: row['design'] for row in traces[192:]},
            {
                '3.3.1（查询通用要求）': '第4章 目录与看板查询设计',
                'LBLS-ZY-TX': '第4章 平台与参与方通信设计',
                'LBLS-ZL-XL': '第4章 联邦学习模块性能设计',
            },
        )
        self.assertNotIn('FL-SU-', traces[0]['design'])

        document = Document(merge.DEFAULT_TARGET)
        merge.update_traceability_chapter(document, traces)
        forward, reverse = merge.traceability_tables(document)
        self.assertEqual(len(forward.rows), len(traces) + 26)
        self.assertEqual(len(reverse.rows), len(merge.reverse_traceability_rows(traces)) + 26)
        for table in (forward, reverse):
            self.assertIsNotNone(table.rows[0]._tr.trPr.find(qn('w:tblHeader')))
            self.assertTrue(all(row._tr.trPr.find(qn('w:cantSplit')) is not None for row in table.rows))
        for index, row in enumerate(traces, start=1):
            self.assertEqual(forward.rows[index].cells[1].text, row['requirement'])
            self.assertEqual(forward.rows[index].cells[2].text, row['design'])
            self.assertEqual(forward.rows[index].cells[3].text, '')
        reverse_rows = merge.reverse_traceability_rows(traces)
        reverse_ids = [identifier.rsplit('/', 1)[-1] for row in reverse_rows for identifier in row['requirement'].splitlines()]
        self.assertIn('注册组织/FL-SD-OP-F0001', traces[0]['design'])
        unit_refs = {ref for row in traces for ref in row['unitRefs'] if 'FL-U-' in ref}
        self.assertEqual(len(unit_refs), 23)
        self.assertEqual(sum('FL-SD-OP-' in row['design'] for row in reverse_rows), 192)
        self.assertEqual(set(reverse_ids), {row['id'] for row in traces})
        self.assertEqual(len([row for row in traces if any('FL-SD-DET-05' in ref for ref in row['unitRefs'])]), 28)
        by_id = {row['id']: '；'.join(row['unitRefs']) for row in traces}
        self.assertIn('FL-SD-DET-05-R01', by_id['LBLS-GN-LBXX-F0067'])
        for identifier in ('F0133', 'F0134', 'F0135', 'F0137', 'F0138', 'F0139', 'F0140', 'F0141'):
            self.assertIn('FL-SD-DET-05-R04', by_id[f'LBLS-GN-LBXX-{identifier}'])
        for index, row in enumerate(reverse_rows, start=1):
            self.assertEqual(reverse.rows[index].cells[1].text, row['design'])
            self.assertEqual(reverse.rows[index].cells[2].text, row['requirement'])
            self.assertEqual(reverse.rows[index].cells[3].text, '')
        self.assertIn('LBLS-GN-SZR-HOME', forward.rows[len(traces) + 1].cells[1].text)
        self.assertIn('LBLS-GN-SZR-HOME', reverse.rows[len(reverse_rows) + 1].cells[2].text)
        self.assertEqual(forward.rows[len(traces) + 1].cells[0].text, str(len(traces) + 1))
        self.assertEqual(reverse.rows[len(reverse_rows) + 1].cells[0].text, str(len(reverse_rows) + 1))
        self.assertIn('具体操作设计双向对照', '\n'.join(p.text for p in document.paragraphs))

    def test_unallocated_software_unit_is_rejected(self):
        source = merge.read_structured_sections(merge.DEFAULT_SECTIONS)
        table = next(b for b in source['sections']['detailed'] if b['id'] == 'FL-SD-DET-05')
        table['rows'].append({'id': 'FL-SD-DET-UNASSIGNED', 'cells': ['FL-U-UNASSIGNED 未分配单元', '输入', '输出']})
        with self.assertRaisesRegex(ValueError, 'without requirement allocation'):
            merge.traceability_rows(source)

    def test_traceability_mapping_changes_are_tracked(self):
        source = merge.read_structured_sections(merge.DEFAULT_SECTIONS)
        original = merge.traceability_signatures(merge.traceability_rows(source))
        changed = deepcopy(source)
        table = next(block for block in changed['sections']['detailed'] if block['id'] == 'FL-SD-DET-23')
        table['rows'][0]['cells'][2] = 'FL-U-EVAL'
        updated = merge.traceability_signatures(merge.traceability_rows(changed))
        self.assertEqual(merge.changed_entries(original, updated),
                         ['LBLS-GN-LBXX-F0066', 'LBLS-GN-LBXX-F0067', 'LBLS-GN-LBXX-F0078', 'LBLS-GN-LBXX-F0079'])

    def test_trace_chapters_match_numbered_document_headings(self):
        source = merge.read_structured_sections(merge.DEFAULT_SECTIONS)
        rows = merge.traceability_rows(source)
        first = rows[0]
        self.assertTrue(first['requirement'].startswith('3.3.1.2.1 注册组织/'))
        self.assertEqual(first['design'], '5.1.9.1 注册组织/FL-SD-OP-F0001')
        selected = next(row for row in rows if row['id'] == 'LBLS-GN-LBXX-F0066')
        self.assertIn('5.1.1.1 ', selected['design'])

    def test_shared_requirement_trace_rejects_missing_design_heading(self):
        source = merge.read_structured_sections(merge.DEFAULT_SECTIONS)
        source['sharedRequirementTraces'][0]['designHeadings'] = ['FL-SD-UNKNOWN']
        with self.assertRaisesRegex(ValueError, 'Unknown design heading'):
            merge.traceability_rows(source)

    def test_context_unit_coverage_rejects_new_unmapped_context(self):
        source = merge.read_structured_sections(merge.DEFAULT_SECTIONS)
        context_table = next(block for block in source['sections']['detailed'] if block['id'] == 'FL-SD-DET-26')
        self.assertEqual(len(context_table['rows']), 15)
        changed = deepcopy(source)
        table = next(block for block in changed['sections']['detailed'] if block['id'] == 'FL-SD-DET-26')
        table['rows'] = table['rows'][1:]
        with self.assertRaisesRegex(ValueError, 'Context design units do not cover current requirements'):
            merge.traceability_rows(changed)

    def test_detailed_design_sources_cover_key_units(self):
        source = merge.read_structured_sections(merge.DEFAULT_SECTIONS)
        signatures = merge.implementation_source_signatures(source)
        self.assertEqual(len(signatures), 8)
        self.assertIn('FL-SD-DET-05-R02', signatures)
        self.assertIn('FL-SD-DET-05-R07', signatures)

    def test_conceptual_figures_are_embedded_and_tracked(self):
        source = merge.read_structured_sections(merge.DEFAULT_SECTIONS)
        figures = merge.figure_assets(source)
        document = Document()
        anchor = document.add_paragraph('数据库概念设计', style='Heading 4')
        merge.insert_section(document, anchor, '{{FIGURE:FL-SD-DBC-04}}', figures)
        self.assertEqual(len(document.inline_shapes), 1)
        self.assertEqual(document.paragraphs[2].text, '图：联邦学习概念数据关系（治理与数据准备）')
        self.assertNotIn('FIGURE:', '\n'.join(paragraph.text for paragraph in document.paragraphs))
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            (root / 'image.png').write_bytes(b'before')
            (root / 'figure.dot').write_text('source', encoding='utf-8')
            fixture = {'sections': {'db-conceptual': [{
                'id': 'FL-SD-DBC-TEST', 'type': 'figure', 'image': 'image.png',
                'source': 'figure.dot', 'caption': '测试', 'widthCm': 12,
            }]}}
            before = merge.structured_section_items(fixture, root)
            (root / 'image.png').write_bytes(b'after')
            after = merge.structured_section_items(fixture, root)
            self.assertEqual(merge.changed_entries(before, after), ['db-conceptual:FL-SD-DBC-TEST'])

    def test_interface_inventory_covers_actual_resource_routes(self):
        translations = json.loads(merge.MODEL.read_text(encoding='utf-8'))['translations']['zh-CN']
        records = merge.discover_routes(merge.ROOT, translations)
        routes = {(item['provider'], item['method'], item['path']) for item in records}
        self.assertEqual(len(routes), len(records))
        self.assertGreaterEqual(len(records), 320)
        self.assertIn(('共享支撑服务', 'POST', '/uploadedfile/uploadfile/file'), routes)
        self.assertIn(('训练引擎', 'GET', '/jobs/{jobId}/artifacts/{artifactName}'), routes)
        rendered = merge.render_inventory(records)
        self.assertEqual(rendered.count('\n| IF-'), len(resource_interfaces(records, json.loads(merge.MODEL.read_text()))))
        self.assertIn('文件内容读取接口', rendered)
        source = merge.read_structured_sections(merge.DEFAULT_SECTIONS)
        detail_rows = next(block['rows'] for block in source['sections']['interfaces'] if block['id'] == 'FL-SD-INT-05')
        inventory_ids = {record['id'] for record in records}
        for row in detail_rows:
            references = re.findall(r'\b[APSE]-[0-9A-F]{8}\b', row['cells'][0])
            self.assertTrue(references, row['id'])
            self.assertTrue(set(references) <= inventory_ids, row['id'])

    def test_interface_design_has_identified_field_tables_and_relation_figure(self):
        source = merge.read_structured_sections(merge.DEFAULT_SECTIONS)
        blocks = {block['id']: block for block in source['sections']['interfaces']}
        self.assertEqual(blocks['FL-SD-INT-RELATIONS-FIG']['type'], 'figure')
        self.assertEqual(blocks['FL-SD-INT-INVENTORY']['type'], 'interfaceInventory')
        self.assertNotIn('FL-SD-INT-07', blocks)
        for index in range(1, 11):
            key = f'FL-SD-INT-DETAIL-{index:02d}'
            self.assertNotIn('FL-IF-', blocks[key]['text'])
            self.assertTrue(blocks[key]['interfaceIdentifiers'])
            self.assertTrue(all(value.startswith('FL-IF-') for value in blocks[key]['interfaceIdentifiers']))
            self.assertEqual(blocks[key]['level'], 5)
            self.assertTrue(blocks[key + '-METHOD']['text'])
            self.assertTrue(blocks[key + '-FIELDS']['rows'])
            self.assertTrue(blocks[key + '-RESULT']['text'])

    def test_table_row_change_reports_stable_id(self):
        source = merge.read_structured_sections(merge.DEFAULT_SECTIONS)
        changed = deepcopy(source)
        table = next(block for block in changed['sections']['architecture'] if block['id'] == 'FL-SD-ARC-02')
        table['rows'][0]['cells'][1] = '调整后的职责'
        original_items = merge.structured_section_items(source)
        changed_items = merge.structured_section_items(changed)
        self.assertEqual(
            merge.changed_entries(original_items, changed_items),
            ['architecture:FL-SD-ARC-02-R01'],
        )

    def test_interface_source_change_reports_affected_row(self):
        source = {'sections': {'interfaces': [{
            'type': 'table', 'rows': [{'id': 'FL-SD-INT-05-R01', 'sourceRefs': ['client.kt']}],
        }]}}
        with tempfile.TemporaryDirectory() as directory:
            path = Path(directory) / 'client.kt'
            path.write_text('before', encoding='utf-8')
            before = merge.interface_source_signatures(source, Path(directory))
            path.write_text('after', encoding='utf-8')
            after = merge.interface_source_signatures(source, Path(directory))
            self.assertEqual(merge.changed_entries(before, after), ['FL-SD-INT-05-R01'])
            messages = merge.register_changes(
                {'interfaceSourceRefs': before},
                {'interfaceSourceRefs': after, 'sourceSections': {}, 'targetSections': {},
                 'targetSha256': None, 'target': None, 'source': None},
            )
            self.assertTrue(any('接口实现来源变更 1 处：FL-SD-INT-05-R01' in message for message in messages))
            path.unlink()
            with self.assertRaisesRegex(ValueError, 'Interface source is missing'):
                merge.interface_source_signatures(source, Path(directory))

    def test_input_output_decisions_render_from_tracked_items(self):
        decisions = merge.read_decisions(merge.DEFAULT_DECISIONS)
        markdown = merge.render_decisions(decisions)
        self.assertEqual(markdown.splitlines().count('| --- | --- | --- | --- |'), 1)
        self.assertEqual(markdown.splitlines().count('| --- | --- | --- | --- | --- |'), 1)
        self.assertEqual(markdown.count('\n| '), len(decisions['items']) + len(decisions['behaviorItems']) + 4)
        self.assertIn('原始样本留在参与方', markdown)
        self.assertNotIn('FL-IO-01', markdown)
        self.assertIn('平台到训练引擎的模型聚合', markdown)
        self.assertIn('联邦学习业务行为与查询展示决策', markdown)

    def test_reviewed_corrections_preserve_original_and_fix_merged_copy(self):
        document = Document(merge.DEFAULT_TARGET)
        source = merge.read_corrections(merge.DEFAULT_CORRECTIONS)
        targets = merge.correction_targets(document, source)
        self.assertEqual(len(targets), len(source['paragraphs']) + len(source['tableCells']) + len(source['referenceRows']))
        self.assertEqual(document.tables[0].rows[4].cells[0].text, '软件需求规格说明')
        merge.apply_corrections(source, targets)
        self.assertEqual(document.tables[0].rows[4].cells[0].text, '软件设计说明')
        self.assertEqual(document.tables[2].rows[-1].cells[2].text, '跨域健康管理联邦孪生平台软件需求规格说明')
        self.assertTrue(any('不以 Nacos、Redis 或共用 PostgreSQL' in p.text for p in document.paragraphs))
        self.assertTrue(any('管理员引导流程' in p.text for p in document.paragraphs))
        self.assertEqual(len(merge.correction_signatures(source)), len(targets))
        changed = deepcopy(source)
        changed['paragraphs'][0]['expected'] = '已变化的总稿表述'
        with self.assertRaisesRegex(ValueError, 'Expected one paragraph for correction'):
            merge.correction_targets(Document(merge.DEFAULT_TARGET), changed)

    def test_behavior_and_context_tables_fit_joint_style(self):
        document = Document(merge.DEFAULT_TARGET)
        cases = (
            (('业务过程', '触发与输入', '状态与校验决策', '结果及异常', '查询展示'),
             [1250, 1600, 2400, 2250, 1500]),
            (('上下文软件单元', '输入与主要判断', '状态输出与异常', '读侧或部署边界'),
             [1700, 2550, 2550, 2200]),
        )
        for headers, expected_widths in cases:
            table = document.add_table(rows=2, cols=len(headers))
            for cell, value in zip(table.rows[0].cells, headers):
                cell.text = value
            merge.format_inserted_tables(document, [table])
            self.assertEqual([column.width.twips for column in table.columns], expected_widths)
            self.assertIsNotNone(table.rows[0]._tr.trPr.find(qn('w:tblHeader')))

    def test_model_reference_change_identifies_affected_decision(self):
        decisions = {'items': [{'id': 'FL-IO-01', 'commandRefs': ['Example'], 'eventRefs': []}]}
        before = {'slices': [{'commands': [{'name': 'Example', 'fields': [{'name': 'id'}]}]}]}
        after = {'slices': [{'commands': [{'name': 'Example', 'fields': [{'name': 'id'}, {'name': 'value'}]}]}]}
        original = merge.decision_model_signatures(decisions, before)
        changed = merge.decision_model_signatures(decisions, after)
        self.assertEqual(merge.changed_entries(original, changed), ['FL-IO-01'])
        messages = merge.register_changes(
            {'decisionItems': {'FL-IO-01': 'same'}, 'decisionModelRefs': original,
             'sourceSections': {}, 'targetSections': {}, 'targetSha256': 'same',
             'source': 'source.md', 'target': 'target.docx'},
            {'decisionItems': {'FL-IO-01': 'same'}, 'decisionModelRefs': changed,
             'sourceSections': {}, 'targetSections': {}, 'targetSha256': 'same',
             'source': 'source.md', 'target': 'target.docx'},
        )
        self.assertTrue(any('关联建模定义变更 1 处：FL-IO-01' in message for message in messages))
        with self.assertRaisesRegex(ValueError, 'Unknown commands reference in FL-IO-01'):
            merge.decision_model_signatures(decisions, {'slices': []})

    def test_inserted_table_uses_joint_document_style(self):
        document = Document(merge.DEFAULT_TARGET)
        original_count = len(document.tables)
        table = document.add_table(rows=2, cols=3)
        for cell, text in zip(table.rows[0].cells, ('输入或条件', '处理决策', '输出')):
            cell.text = text
        table.rows[1].cells[0].text = '测试输入'
        merge.format_inserted_tables(document, [table])
        self.assertEqual(len(document.tables), original_count + 1)
        self.assertEqual(table.style.name, 'Normal Table')
        self.assertEqual(table.rows[0].cells[0].paragraphs[0].style.name, 'RS-表-表头')
        self.assertEqual(table.rows[1].cells[0].paragraphs[0].style.name, 'RS-表-内容')
        self.assertIsNone(table.rows[0].cells[0]._tc.tcPr.find(qn('w:shd')))
        self.assertAlmostEqual(sum(column.width.cm for column in table.columns), 15.88, delta=0.1)

    def test_interface_inventory_table_prioritizes_paths_and_repeats_header(self):
        document = Document(merge.DEFAULT_TARGET)
        table = document.add_table(rows=2, cols=4)
        for cell, text in zip(table.rows[0].cells, ('接口标识', '调用方', '方法与相对路径', '用途')):
            cell.text = text
        merge.format_inserted_tables(document, [table])
        widths = [column.width.twips for column in table.columns]
        self.assertEqual(widths, [1500, 1500, 4500, 1500])
        self.assertIsNotNone(table.rows[0]._tr.trPr.find(qn('w:tblHeader')))
        self.assertTrue(all(row._tr.trPr.find(qn('w:cantSplit')) is not None for row in table.rows))

    def test_detailed_design_table_gives_explanation_room(self):
        document = Document(merge.DEFAULT_TARGET)
        table = document.add_table(rows=2, cols=2)
        table.rows[0].cells[0].text = '设计要素'
        table.rows[0].cells[1].text = '设计说明'
        merge.format_inserted_tables(document, [table])
        self.assertEqual([column.width.twips for column in table.columns], [2000, 7000])
        self.assertIsNotNone(table.rows[0]._tr.trPr.find(qn('w:tblHeader')))
        self.assertTrue(all(row._tr.trPr.find(qn('w:cantSplit')) is not None for row in table.rows))

    def test_performance_sections_do_not_mingle(self):
        document = Document()
        performance = document.add_paragraph('性能设计', style='Heading 2')
        document.add_paragraph('其他模块原有性能内容')
        interfaces = document.add_paragraph('接口设计', style='Heading 2')
        merge.insert_section(document, performance, merge.OTHER_MODULES_PERFORMANCE)
        merge.insert_before_heading(document, interfaces, '### 联邦学习模块性能设计\n\n联邦学习性能内容')
        self.assertEqual(
            [paragraph.text for paragraph in document.paragraphs],
            ['性能设计', '医疗数字人及风险动态评估模块性能设计',
             '其他模块原有性能内容', '联邦学习模块性能设计',
             '联邦学习性能内容', '接口设计'],
        )

    def test_target_sections_locate_changed_heading(self):
        original = Document()
        original.add_paragraph('模块 A', style='Heading 2')
        original.add_paragraph('原内容')
        original.add_paragraph('模块 B', style='Heading 2')
        original.add_paragraph('保持不变')
        changed = Document()
        changed.add_paragraph('模块 A', style='Heading 2')
        changed.add_paragraph('调整后的内容')
        changed.add_paragraph('模块 B', style='Heading 2')
        changed.add_paragraph('保持不变')
        self.assertEqual(
            merge.changed_entries(merge.document_sections(original), merge.document_sections(changed)),
            ['模块 A [1]'],
        )

    def test_source_change_and_format_only_change_are_reported(self):
        baseline = {
            'source': 'source.md', 'target': 'target.docx', 'targetSha256': 'before',
            'sourceSections': {'decisions': 'before'}, 'targetSections': {'section': 'same'},
        }
        current = {
            **baseline, 'targetSha256': 'after',
            'sourceSections': {'decisions': 'after'},
        }
        messages = merge.register_changes(baseline, current)
        self.assertTrue(any('联邦学习正文变更 1 处：decisions' in message for message in messages))
        self.assertTrue(any('章节文字未变' in message for message in messages))


if __name__ == '__main__':
    unittest.main()
