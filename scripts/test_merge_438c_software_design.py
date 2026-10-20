"""Focused checks for the controlled software-design merge baseline."""

import importlib.util
import unittest
from copy import deepcopy
from pathlib import Path

from docx import Document
from docx.oxml.ns import qn


SCRIPT = Path(__file__).with_name('merge-438c-software-design.py')
spec = importlib.util.spec_from_file_location('merge_438c_software_design', SCRIPT)
merge = importlib.util.module_from_spec(spec)
spec.loader.exec_module(merge)


class SoftwareDesignTrackingTest(unittest.TestCase):
    def test_other_sections_render_from_stable_items(self):
        source = merge.read_structured_sections(merge.DEFAULT_SECTIONS)
        rendered = merge.render_structured_sections(source)
        self.assertEqual(set(rendered), set(merge.ANCHORS) - {'decisions'})
        self.assertIn('{{READ_MODEL_INVENTORY}}', rendered['db-logical'])
        self.assertIn('| 部件 | 主要职责 | 持久化或外部依赖 |', rendered['architecture'])
        self.assertIn('### 联邦学习模块性能设计', rendered['performance'])

    def test_table_row_change_reports_stable_id(self):
        source = merge.read_structured_sections(merge.DEFAULT_SECTIONS)
        changed = deepcopy(source)
        changed['sections']['architecture'][1]['rows'][0]['cells'][1] = '调整后的职责'
        original_items = merge.structured_section_items(source)
        changed_items = merge.structured_section_items(changed)
        self.assertEqual(
            merge.changed_entries(original_items, changed_items),
            ['architecture:FL-SD-ARC-02-R01'],
        )

    def test_input_output_decisions_render_from_tracked_items(self):
        decisions = merge.read_decisions(merge.DEFAULT_DECISIONS)
        markdown = merge.render_decisions(decisions)
        self.assertEqual(markdown.count('| --- | --- | --- | --- |'), 1)
        self.assertEqual(markdown.count('\n| '), len(decisions['items']) + 2)
        self.assertIn('原始样本留在参与方', markdown)
        self.assertNotIn('FL-IO-01', markdown)

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
