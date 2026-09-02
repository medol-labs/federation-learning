from __future__ import annotations

from gemifl.algorithms.models_python.algos.HomoDenseNet import HomoDenseNet
from gemifl.runtime.plugins.legacy_image_classifier import LegacyImageClassifierPlugin


class DenseNetPlugin(LegacyImageClassifierPlugin):
    code = "DENSENET"
    aliases = ("DENSENET", "HOMO_DENSENET", "HomoDenseNet")
    legacy_model_class = HomoDenseNet
