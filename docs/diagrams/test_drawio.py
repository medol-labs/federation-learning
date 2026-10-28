import importlib.util
from pathlib import Path
import tempfile
import unittest
from unittest.mock import patch


spec = importlib.util.spec_from_file_location('drawio', Path(__file__).with_name('drawio.py'))
drawio = importlib.util.module_from_spec(spec)
spec.loader.exec_module(drawio)


class DiagramExportTests(unittest.TestCase):
    def setUp(self):
        self.directory = tempfile.TemporaryDirectory()
        self.addCleanup(self.directory.cleanup)
        self.root = Path(self.directory.name)
        self.docs = self.root / 'docs'
        self.docs.mkdir()
        self.script_patch = patch.object(drawio, '__file__', str(self.docs / 'diagrams/drawio.py'))
        self.script_patch.start()
        self.addCleanup(self.script_patch.stop)
        self.commands = []

    def render(self, command, **kwargs):
        self.commands.append(command)
        option = '-o' if command[0] == '/fake/dot' else '--output'
        Path(command[command.index(option) + 1]).write_bytes(b'generated fixture')

    def test_drawio_exports_to_generated_without_changing_source(self):
        source = self.docs / 'layout.drawio'
        content = '<mxfile><diagram><mxGraphModel><root><mxCell id="0" /></root></mxGraphModel></diagram></mxfile>'
        source.write_text(content)
        with patch.dict('os.environ', {'DRAWIO_EXECUTABLE': '/fake/drawio'}), patch.object(drawio.subprocess, 'run', side_effect=self.render):
            drawio.export(source)
        self.assertEqual(source.read_text(), content)
        for extension in ('png', 'svg'):
            self.assertTrue((self.docs / f'generated/diagrams/layout.{extension}').is_file())
            self.assertFalse(source.with_suffix(f'.{extension}').exists())
        self.assertIn('--scale', self.commands[1])
        self.assertEqual(self.commands[1][-1], '2')

    def test_graphviz_uses_same_output_directory_and_png_resolution(self):
        source = self.docs / 'concept.dot'
        source.write_text('digraph { a -> b }')
        with patch.object(drawio.shutil, 'which', return_value='/fake/dot'), patch.object(drawio.subprocess, 'run', side_effect=self.render):
            drawio.export(source)
        self.assertTrue((self.docs / 'generated/diagrams/concept.png').is_file())
        self.assertTrue((self.docs / 'generated/diagrams/concept.svg').is_file())
        self.assertIn('-Gdpi=180', self.commands[1])
        self.assertFalse(source.with_suffix('.png').exists())

    def test_missing_graphviz_has_actionable_error(self):
        with patch.object(drawio.shutil, 'which', return_value=None):
            with self.assertRaisesRegex(ValueError, 'Install Graphviz'):
                drawio.export(self.docs / 'concept.dot')

    def test_empty_export_is_rejected(self):
        source = self.docs / 'concept.dot'
        source.write_text('digraph {}')
        with patch.object(drawio.shutil, 'which', return_value='/fake/dot'), patch.object(drawio.subprocess, 'run'):
            with self.assertRaisesRegex(ValueError, 'did not produce'):
                drawio.export(source)

    def test_non_source_input_is_rejected(self):
        with self.assertRaisesRegex(ValueError, 'Expected .drawio or .dot'):
            drawio.export(self.docs / 'image.png')


if __name__ == '__main__':
    unittest.main()
