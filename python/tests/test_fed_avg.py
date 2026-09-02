import json
import tempfile
import unittest
from pathlib import Path

from gemifl.runtime.plugins.fed_avg import FedAvgAggregationPlugin


class FedAvgAggregationPluginTest(unittest.TestCase):
    def test_aggregates_updates_by_training_sample_count(self):
        with tempfile.TemporaryDirectory() as temporary_directory:
            root = Path(temporary_directory)
            first_update = root / "first.json"
            second_update = root / "second.json"
            global_model = root / "global.json"
            first_update.write_text(
                json.dumps({"weights": [1.0, 3.0], "bias": 1.0, "train_size": 1}),
                encoding="utf-8",
            )
            second_update.write_text(
                json.dumps({"weights": [3.0, 5.0], "bias": 3.0, "train_size": 3}),
                encoding="utf-8",
            )

            FedAvgAggregationPlugin().aggregate(
                {
                    "jobId": "aggregate-test",
                    "nodeName": "platform-aggregator",
                    "input": {"updates": [str(first_update), str(second_update)]},
                    "output": {"globalModel": str(global_model)},
                }
            )

            result = json.loads(global_model.read_text(encoding="utf-8"))
            self.assertEqual([2.5, 4.5], result["weights"])
            self.assertEqual(2.5, result["bias"])
            self.assertEqual(4, result["train_size"])
            self.assertEqual("FED_AVG", result["aggregationPlugin"])


if __name__ == "__main__":
    unittest.main()
