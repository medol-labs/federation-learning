import unittest
import os
from unittest.mock import patch

from gemifl.runtime.plugins import model_plugin_capabilities


class RuntimePluginRegistryTest(unittest.TestCase):
    def setUp(self):
        environment = patch.dict(os.environ, {"RUNTIME_ENGINE_PLUGIN_PROFILE": "full"}, clear=True)
        environment.start()
        self.addCleanup(environment.stop)

    def test_registers_model_plugins(self):
        codes = {alias for plugin in model_plugin_capabilities() for alias in [plugin["code"], *plugin["aliases"]]}

        self.assertTrue(
            {
                "SKLEARN_LOGISTIC_REGRESSION",
                "DENSENET",
                "RESNET",
                "TABNET",
                "UNET",
                "UNET_3D",
                "BERT",
                "CLINICAL_BERT",
                "GCN",
            }.issubset(codes)
        )

    def test_exposes_legacy_model_aliases(self):
        plugins = {alias: set(plugin["aliases"]) for plugin in model_plugin_capabilities() for alias in [plugin["code"], *plugin["aliases"]]}

        self.assertIn("HomoDenseNet", plugins["DENSENET"])
        self.assertIn("HomoResNet", plugins["RESNET"])
        self.assertIn("HomoTabNetClassifer", plugins["TABNET"])
        self.assertIn("HomoUNet2d", plugins["UNET"])
        self.assertIn("HomoUNet3d", plugins["UNET_3D"])
        self.assertIn("HomoBertClassifier", plugins["BERT"])
        self.assertIn("HomoClinicalBert", plugins["CLINICAL_BERT"])
        self.assertIn("HomoGcn", plugins["GCN"])


if __name__ == "__main__":
    unittest.main()
