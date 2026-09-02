from __future__ import annotations

from dataclasses import dataclass
from importlib import import_module


@dataclass(frozen=True)
class PluginRegistration:
    code: str
    aliases: tuple[str, ...]
    module: str
    class_name: str
    supported_formats: tuple[str, ...] = ()
    supported_tasks: tuple[str, ...] = ()
    supported_update_formats: tuple[str, ...] = ()


MODEL_PLUGIN_REGISTRY = (
    PluginRegistration(
        code="SKLEARN_LOGISTIC_REGRESSION",
        aliases=("linear.LogisticRegression", "SKLEARN_LOGISTIC_REGRESSION"),
        module="gemifl.runtime.plugins.sklearn_logistic_regression",
        class_name="SklearnLogisticRegressionPlugin",
        supported_formats=("JSON",),
        supported_tasks=("BINARY_CLASSIFICATION",),
    ),
    PluginRegistration(
        code="DENSENET",
        aliases=("DENSENET", "HOMO_DENSENET", "HomoDenseNet"),
        module="gemifl.runtime.plugins.densenet",
        class_name="DenseNetPlugin",
        supported_formats=("PYTORCH_STATE_DICT", "PICKLE"),
        supported_tasks=("IMAGE_CLASSIFICATION", "MEDICAL_IMAGE_CLASSIFICATION"),
    ),
    PluginRegistration(
        code="RESNET",
        aliases=("RESNET", "HOMO_RESNET", "HomoResNet"),
        module="gemifl.runtime.plugins.resnet",
        class_name="ResNetPlugin",
        supported_formats=("PYTORCH_STATE_DICT", "PICKLE"),
        supported_tasks=("IMAGE_CLASSIFICATION", "MEDICAL_IMAGE_CLASSIFICATION"),
    ),
    PluginRegistration(
        code="TABNET",
        aliases=("TABNET", "HOMO_TABNET", "HomoTabNetClassifer"),
        module="gemifl.runtime.plugins.tabnet",
        class_name="TabNetPlugin",
        supported_formats=("PYTORCH_STATE_DICT", "PICKLE"),
        supported_tasks=("TABULAR_CLASSIFICATION", "BINARY_CLASSIFICATION", "MULTICLASS_CLASSIFICATION"),
    ),
    PluginRegistration(
        code="UNET",
        aliases=("UNET", "UNET_2D", "HOMO_UNET_2D", "HomoUNet2d"),
        module="gemifl.runtime.plugins.unet",
        class_name="UNet2DPlugin",
        supported_formats=("PYTORCH_STATE_DICT", "PICKLE"),
        supported_tasks=("IMAGE_SEGMENTATION", "MEDICAL_IMAGE_SEGMENTATION"),
    ),
    PluginRegistration(
        code="UNET_3D",
        aliases=("UNET_3D", "HOMO_UNET_3D", "HomoUNet3d"),
        module="gemifl.runtime.plugins.unet",
        class_name="UNet3DPlugin",
        supported_formats=("PYTORCH_STATE_DICT", "PICKLE"),
        supported_tasks=("IMAGE_SEGMENTATION", "MEDICAL_IMAGE_SEGMENTATION"),
    ),
    PluginRegistration(
        code="BERT",
        aliases=("BERT", "HOMO_BERT", "HomoBertClassifier"),
        module="gemifl.runtime.plugins.bert",
        class_name="BertPlugin",
        supported_formats=("PYTORCH_STATE_DICT", "PICKLE"),
        supported_tasks=("TEXT_CLASSIFICATION", "NLP_CLASSIFICATION"),
    ),
    PluginRegistration(
        code="CLINICAL_BERT",
        aliases=("CLINICAL_BERT", "CLINICALBERT", "HOMO_CLINICAL_BERT", "HomoClinicalBert"),
        module="gemifl.runtime.plugins.clinical_bert",
        class_name="ClinicalBertPlugin",
        supported_formats=("PYTORCH_STATE_DICT", "PICKLE"),
        supported_tasks=("TEXT_CLASSIFICATION", "CLINICAL_TEXT_CLASSIFICATION"),
    ),
    PluginRegistration(
        code="GCN",
        aliases=("GCN", "HOMO_GCN", "HomoGcn"),
        module="gemifl.runtime.plugins.gcn",
        class_name="GCNPlugin",
        supported_formats=("PYTORCH_STATE_DICT", "PICKLE"),
        supported_tasks=("GRAPH_CLASSIFICATION", "MOLECULAR_GRAPH_CLASSIFICATION"),
    ),
)

AGGREGATION_PLUGIN_REGISTRY = (
    PluginRegistration(
        code="FED_AVG",
        aliases=("FED_AVG", "FEDERATED_AVERAGING", "WEIGHTED_FED_AVG"),
        module="gemifl.runtime.plugins.fed_avg",
        class_name="FedAvgAggregationPlugin",
        supported_update_formats=("JSON",),
    ),
)


def get_model_plugin(code: str | None):
    registration = _find_registration(code or "SKLEARN_LOGISTIC_REGRESSION", MODEL_PLUGIN_REGISTRY)
    if registration:
        return _instantiate(registration)
    supported = ", ".join(plugin.code for plugin in MODEL_PLUGIN_REGISTRY)
    normalized_code = code or "SKLEARN_LOGISTIC_REGRESSION"
    raise ValueError(f"Unsupported model plugin: {normalized_code}. Supported plugins: {supported}")


def get_aggregation_plugin(code: str | None):
    registration = _find_registration(code or "FED_AVG", AGGREGATION_PLUGIN_REGISTRY)
    if registration:
        return _instantiate(registration)
    supported = ", ".join(plugin.code for plugin in AGGREGATION_PLUGIN_REGISTRY)
    normalized_code = code or "FED_AVG"
    raise ValueError(f"Unsupported aggregation plugin: {normalized_code}. Supported plugins: {supported}")


def model_plugin_capabilities() -> list[dict]:
    return [
        {
            "code": registration.code,
            "aliases": list(registration.aliases),
            "supportedFormats": list(registration.supported_formats),
            "supportedTasks": list(registration.supported_tasks),
        }
        for registration in MODEL_PLUGIN_REGISTRY
    ]


def aggregation_plugin_capabilities() -> list[dict]:
    return [
        {
            "code": registration.code,
            "aliases": list(registration.aliases),
            "supportedUpdateFormats": list(registration.supported_update_formats),
        }
        for registration in AGGREGATION_PLUGIN_REGISTRY
    ]


def _find_registration(code: str, registry: tuple[PluginRegistration, ...]) -> PluginRegistration | None:
    for registration in registry:
        if code == registration.code or code in registration.aliases:
            return registration
    return None


def _instantiate(registration: PluginRegistration):
    module = import_module(registration.module)
    plugin_class = getattr(module, registration.class_name)
    return plugin_class()
