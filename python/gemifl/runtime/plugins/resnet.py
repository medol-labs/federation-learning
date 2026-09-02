from __future__ import annotations

from gemifl.algorithms.models_python.algos.HomoResNet import HomoResNet
from gemifl.runtime.plugins.legacy_image_classifier import LegacyImageClassifierPlugin


class ResNetPlugin(LegacyImageClassifierPlugin):
    code = "RESNET"
    aliases = ("RESNET", "HOMO_RESNET", "HomoResNet")
    legacy_model_class = HomoResNet

    def _apply_default_net_config(self, model_parameter: dict) -> None:
        model_parameter.setdefault("block", "BasicBlock")
        model_parameter.setdefault("net_config", {"blocks_num": [2, 2, 2, 2], "num_classes": 2})
