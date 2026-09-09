from __future__ import annotations

from gemifl.algorithms.models_python.algos.HomoUNet2d import HomoUNet2d
from gemifl.algorithms.models_python.algos.HomoUNet3d import HomoUNet3d
from gemifl.runtime.plugins.legacy_model import LegacyModelPlugin


class UNet2DPlugin(LegacyModelPlugin):
    code = "PYTORCH_UNET_2D_SEGMENTER"
    aliases = ("UNET", "UNET_2D", "HOMO_UNET_2D", "HomoUNet2d")
    supported_formats = ("PYTORCH_STATE_DICT", "PICKLE")
    supported_tasks = ("IMAGE_SEGMENTATION", "MEDICAL_IMAGE_SEGMENTATION")
    legacy_model_class = HomoUNet2d
    default_data_type = "DenseNetImage"

    def _apply_default_net_config(self, model_parameter: dict) -> None:
        model_parameter.setdefault("net_config", {"n_classes": 2, "pretrained": False})


class UNet3DPlugin(LegacyModelPlugin):
    code = "PYTORCH_UNET_3D_SEGMENTER"
    aliases = ("UNET_3D", "HOMO_UNET_3D", "HomoUNet3d")
    supported_formats = ("PYTORCH_STATE_DICT", "PICKLE")
    supported_tasks = ("IMAGE_SEGMENTATION", "MEDICAL_IMAGE_SEGMENTATION")
    legacy_model_class = HomoUNet3d
    default_data_type = "BrainTumourImage"

    def _apply_default_net_config(self, model_parameter: dict) -> None:
        model_parameter.setdefault("net_config", {"in_channels": 4, "n_classes": 1, "n_channels": 24})
