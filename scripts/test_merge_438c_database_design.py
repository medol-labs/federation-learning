"""Regression checks for the project database-design merge."""

import importlib.util
import json
import sys
import tempfile
import unittest
from pathlib import Path

from docx import Document
from docx.oxml.ns import qn


ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'scripts'))
from database_design_inventory import load_catalog, render_logical, render_physical, traceability_rows

spec = importlib.util.spec_from_file_location('project_document_merge', ROOT / 'scripts/merge-438c-project-documents.py')
merge = importlib.util.module_from_spec(spec)
spec.loader.exec_module(merge)


class DatabaseDesignMergeTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.catalog = load_catalog()
        cls.register = json.loads((ROOT / 'docs/438c/merge/3.3.1-function-register.json').read_text(encoding='utf-8'))
        cls.target = next(merge.TARGET_DIR.glob('03*'))

    def test_catalog_matches_model_entities_and_requirements(self):
        self.assertEqual(42, len(self.catalog))
        self.assertEqual(42, len({item['table'] for item in self.catalog}))
        self.assertTrue(all(item['properties'] and item['fields'] for item in self.catalog))
        self.assertEqual(42, len(traceability_rows(self.catalog, self.register)))
        logical_inventory, logical_details = render_logical(self.catalog, self.register)
        physical_inventory, physical_details = render_physical(self.catalog)
        self.assertIn('LBLS-GN-LBXX-', logical_inventory)
        self.assertIn('FL-DB-L042', logical_details)
        self.assertIn('FL-DB-P042', physical_details)
        self.assertIn('organization_directory', physical_inventory)
        reversed_inventory, _ = render_logical(list(reversed(self.catalog)), self.register)
        self.assertIn('FL-DB-L001', reversed_inventory)
        self.assertIn('FL-DB-L042', reversed_inventory)

    def test_source_contains_controlled_sections(self):
        sections = merge.sections('database-design-federation-learning.md', {
            'decisions', 'conceptual', 'logical', 'physical', 'software-units',
            'traceability', 'traceability-forward', 'traceability-reverse',
        })
        self.assertEqual(8, len(sections))
        merge.merge_database(self.target, Path('/unused'), check=True)

    def test_merged_copy_preserves_original_and_adds_database_design(self):
        with tempfile.TemporaryDirectory() as directory:
            output = Path(directory) / 'database-design.docx'
            merge.merge_database(self.target, output, check=False)
            document = Document(output)
        text = '\n'.join(paragraph.text for paragraph in document.paragraphs)
        self.assertIn('联邦学习模块数据库级设计决策', text)
        self.assertIn('FL-DB-L042', text)
        self.assertIn('FL-DB-P042', text)
        self.assertNotIn('仅由一份主数据库文件', text)
        self.assertNotIn('{{READ_MODEL_', text)
        self.assertGreaterEqual(len(document.inline_shapes), 2)
        trace_tables = [table for table in document.tables if table.rows
                        and table.rows[0].cells[0].text == '序号'
                        and len(table.rows) == 43]
        self.assertEqual(2, len(trace_tables))
        self.assertEqual('42', trace_tables[-1].rows[-1].cells[0].text)
        inventory = next(table for table in document.tables if table.rows
                         and table.rows[0].cells[0].text == '设计标识')
        self.assertFalse(inventory.autofit)
        self.assertEqual('RS-表-表头', inventory.rows[0].cells[0].paragraphs[0].style.name)
        self.assertEqual('RS-表-内容', inventory.rows[1].cells[0].paragraphs[0].style.name)
        self.assertIsNotNone(inventory.rows[0]._tr.get_or_add_trPr().find(qn('w:tblHeader')))


if __name__ == '__main__':
    unittest.main()
