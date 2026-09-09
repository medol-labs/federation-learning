from __future__ import annotations

from gemifl.algorithms.models_python.algos.HomoDenseNet import HomoDenseNet
from gemifl.runtime.plugins.legacy_model import LegacyModelPlugin


class DenseNetPlugin(LegacyModelPlugin):
    code = "PYTORCH_DENSENET_CLASSIFIER"
    aliases = ("DENSENET", "HOMO_DENSENET", "HomoDenseNet")
    legacy_model_class = HomoDenseNet
