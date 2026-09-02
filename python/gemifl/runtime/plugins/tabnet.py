from __future__ import annotations

from gemifl.algorithms.models_python.algos.HomoTabNetClassifer import HomoTabNetClassifer
from gemifl.runtime.plugins.legacy_image_classifier import LegacyImageClassifierPlugin


class TabNetPlugin(LegacyImageClassifierPlugin):
    code = "TABNET"
    aliases = ("TABNET", "HOMO_TABNET", "HomoTabNetClassifer")
    supported_formats = ("PYTORCH_STATE_DICT", "PICKLE")
    supported_tasks = ("TABULAR_CLASSIFICATION", "BINARY_CLASSIFICATION", "MULTICLASS_CLASSIFICATION")
    legacy_model_class = HomoTabNetClassifer
    default_data_type = "CsvParser"

    def _apply_default_net_config(self, model_parameter: dict) -> None:
        model_parameter.setdefault("net_config", {"input_dim": 1, "output_dim": 2})
