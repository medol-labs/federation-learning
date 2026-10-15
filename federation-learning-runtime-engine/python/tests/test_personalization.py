import json
import tempfile
import unittest
from pathlib import Path

import torch
from PIL import Image
from torch import nn

from gemifl.runtime.plugins.torchvision_densenet import TorchvisionDenseNet121ClassifierPlugin


class TinyClassifier(TorchvisionDenseNet121ClassifierPlugin):
    def _create_model(self, parameters, class_count):
        return nn.Sequential(nn.AdaptiveAvgPool2d(1), nn.Flatten(), nn.Linear(3, class_count))


class PersonalizationTest(unittest.TestCase):
    def test_local_adaptation_keeps_global_update_and_saves_independent_model(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            for label, color in [("a", "red"), ("b", "blue")]:
                folder = root / "data" / label
                folder.mkdir(parents=True)
                for index in range(2):
                    Image.new("RGB", (8, 8), color=color).save(folder / f"{index}.png")
            torch.manual_seed(7)
            config = {
                "jobId": "test-job", "nodeName": "participant-1", "runtimeRoot": str(root),
                "input": {"dataset": {"path": str(root / "data")}},
                "modelParameter": {"imageSize": 8, "trainBatchSize": 4, "epochs": 1,
                                   "learningRate": 0.05, "personalizationEpochs": 2,
                                   "personalizationMode": "LOCAL_ADAPTATION", "minimumPersonalizationLift": 0},
            }
            TinyClassifier().train(config)
            output = root / "test-job" / "participant-1"
            descriptor = json.loads((output / "local_update.json").read_text())
            self.assertEqual(str(output / "model_state_dict.pt"), descriptor["weightArtifact"])
            global_state = torch.load(output / "model_state_dict.pt", weights_only=True)
            personal_state = torch.load(output / "personalized_model_state_dict.pt", weights_only=True)
            self.assertTrue(any(not torch.equal(global_state[key], personal_state[key]) for key in global_state))
            metrics = json.loads((output / "personalized_metrics.json").read_text())
            self.assertAlmostEqual(metrics["personalizedAccuracy"] - metrics["baselineAccuracy"], metrics["personalizationLift"])
            self.assertGreaterEqual(metrics["personalizationLift"], 0)
            self.assertEqual(2, metrics["adaptationEpochs"])

    def test_rejects_missing_validation_data(self):
        with self.assertRaisesRegex(ValueError, "validation dataset"):
            TinyClassifier()._personalize({}, nn.Linear(3, 2), [], None, "cpu", 0.01)


if __name__ == "__main__":
    unittest.main()
