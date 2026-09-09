from __future__ import annotations

import os
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
    profiles: tuple[str, ...] = ("full",)


PROFILE_ALIASES = {
    "all": "full",
    "base": "core",
    "pytorch": "full",
    "torchvision": "pytorch-vision",
    "vision": "pytorch-vision",
    "tabular": "pytorch-tabular",
    "nlp": "pytorch-nlp",
    "graph": "pytorch-graph",
}


MODEL_PLUGIN_REGISTRY = (
    PluginRegistration(
        code="SKLEARN_LOGISTIC_REGRESSION",
        aliases=("linear.LogisticRegression", "SKLEARN_LOGISTIC_REGRESSION"),
        module="gemifl.runtime.plugins.sklearn_logistic_regression",
        class_name="SklearnLogisticRegressionPlugin",
        supported_formats=("JSON",),
        supported_tasks=("BINARY_CLASSIFICATION",),
        profiles=("sklearn", "full"),
    ),
    PluginRegistration(
        code="PYTORCH_DENSENET_CLASSIFIER",
        aliases=("DENSENET", "HOMO_DENSENET", "HomoDenseNet"),
        module="gemifl.runtime.plugins.densenet",
        class_name="DenseNetPlugin",
        supported_formats=("PYTORCH_STATE_DICT", "PICKLE"),
        supported_tasks=("IMAGE_CLASSIFICATION", "MEDICAL_IMAGE_CLASSIFICATION"),
        profiles=("pytorch-vision", "full"),
    ),
    PluginRegistration(
        code="PYTORCH_TORCHVISION_DENSENET121_CLASSIFIER",
        aliases=("TORCHVISION_DENSENET121", "DENSENET121", "PYTORCH_DENSENET121_CLASSIFIER"),
        module="gemifl.runtime.plugins.torchvision_densenet",
        class_name="TorchvisionDenseNet121ClassifierPlugin",
        supported_formats=("PYTORCH_STATE_DICT",),
        supported_tasks=("IMAGE_CLASSIFICATION", "MEDICAL_IMAGE_CLASSIFICATION"),
        profiles=("pytorch-vision", "full"),
    ),
    PluginRegistration(
        code="PYTORCH_RESNET_CLASSIFIER",
        aliases=("RESNET", "HOMO_RESNET", "HomoResNet"),
        module="gemifl.runtime.plugins.resnet",
        class_name="ResNetPlugin",
        supported_formats=("PYTORCH_STATE_DICT", "PICKLE"),
        supported_tasks=("IMAGE_CLASSIFICATION", "MEDICAL_IMAGE_CLASSIFICATION"),
        profiles=("pytorch-vision", "full"),
    ),
    PluginRegistration(
        code="PYTORCH_TABNET_CLASSIFIER",
        aliases=("TABNET", "HOMO_TABNET", "HomoTabNetClassifer"),
        module="gemifl.runtime.plugins.tabnet",
        class_name="TabNetPlugin",
        supported_formats=("PYTORCH_STATE_DICT", "PICKLE"),
        supported_tasks=("TABULAR_CLASSIFICATION", "BINARY_CLASSIFICATION", "MULTICLASS_CLASSIFICATION"),
        profiles=("pytorch-tabular", "full"),
    ),
    PluginRegistration(
        code="PYTORCH_UNET_2D_SEGMENTER",
        aliases=("UNET", "UNET_2D", "HOMO_UNET_2D", "HomoUNet2d"),
        module="gemifl.runtime.plugins.unet",
        class_name="UNet2DPlugin",
        supported_formats=("PYTORCH_STATE_DICT", "PICKLE"),
        supported_tasks=("IMAGE_SEGMENTATION", "MEDICAL_IMAGE_SEGMENTATION"),
        profiles=("pytorch-vision", "full"),
    ),
    PluginRegistration(
        code="PYTORCH_UNET_3D_SEGMENTER",
        aliases=("UNET_3D", "HOMO_UNET_3D", "HomoUNet3d"),
        module="gemifl.runtime.plugins.unet",
        class_name="UNet3DPlugin",
        supported_formats=("PYTORCH_STATE_DICT", "PICKLE"),
        supported_tasks=("IMAGE_SEGMENTATION", "MEDICAL_IMAGE_SEGMENTATION"),
        profiles=("pytorch-vision", "full"),
    ),
    PluginRegistration(
        code="PYTORCH_BERT_CLASSIFIER",
        aliases=("BERT", "HOMO_BERT", "HomoBertClassifier"),
        module="gemifl.runtime.plugins.bert",
        class_name="BertPlugin",
        supported_formats=("PYTORCH_STATE_DICT", "PICKLE"),
        supported_tasks=("TEXT_CLASSIFICATION", "NLP_CLASSIFICATION"),
        profiles=("pytorch-nlp", "full"),
    ),
    PluginRegistration(
        code="PYTORCH_CLINICAL_BERT_CLASSIFIER",
        aliases=("CLINICAL_BERT", "CLINICALBERT", "HOMO_CLINICAL_BERT", "HomoClinicalBert"),
        module="gemifl.runtime.plugins.clinical_bert",
        class_name="ClinicalBertPlugin",
        supported_formats=("PYTORCH_STATE_DICT", "PICKLE"),
        supported_tasks=("TEXT_CLASSIFICATION", "CLINICAL_TEXT_CLASSIFICATION"),
        profiles=("pytorch-nlp", "full"),
    ),
    PluginRegistration(
        code="PYTORCH_GCN_CLASSIFIER",
        aliases=("GCN", "HOMO_GCN", "HomoGcn"),
        module="gemifl.runtime.plugins.gcn",
        class_name="GCNPlugin",
        supported_formats=("PYTORCH_STATE_DICT", "PICKLE"),
        supported_tasks=("GRAPH_CLASSIFICATION", "MOLECULAR_GRAPH_CLASSIFICATION"),
        profiles=("pytorch-graph", "full"),
    ),
)

AGGREGATION_PLUGIN_REGISTRY = (
    PluginRegistration(
        code="FED_AVG_JSON",
        aliases=("FED_AVG", "FEDERATED_AVERAGING", "WEIGHTED_FED_AVG"),
        module="gemifl.runtime.plugins.fed_avg",
        class_name="FedAvgJsonAggregationPlugin",
        supported_update_formats=("JSON",),
        profiles=("sklearn", "full"),
    ),
    PluginRegistration(
        code="FED_AVG_PYTORCH_STATE_DICT",
        aliases=(
            "PYTORCH_STATE_DICT_FED_AVG",
            "FED_AVG_TORCH_STATE_DICT",
            "WEIGHTED_FED_AVG_PYTORCH_STATE_DICT",
        ),
        module="gemifl.runtime.plugins.fed_avg_pytorch_state_dict",
        class_name="FedAvgPytorchStateDictAggregationPlugin",
        supported_update_formats=("PYTORCH_STATE_DICT",),
        profiles=("pytorch-vision", "pytorch-tabular", "pytorch-nlp", "pytorch-graph", "full"),
    ),
)


def get_model_plugin(code: str | None):
    enabled_registry = _enabled_model_registrations()
    registration = _find_registration(code or "SKLEARN_LOGISTIC_REGRESSION", enabled_registry)
    if registration:
        return _instantiate(registration)
    supported = ", ".join(plugin.code for plugin in enabled_registry)
    normalized_code = code or "SKLEARN_LOGISTIC_REGRESSION"
    raise ValueError(
        f"Unsupported or disabled model plugin: {normalized_code}. "
        f"Enabled plugins for profile {plugin_profile()}: {supported}"
    )


def get_aggregation_plugin(code: str | None):
    enabled_registry = _enabled_aggregation_registrations()
    requested_code = code or _default_aggregation_plugin_code()
    registration = _find_registration(requested_code, enabled_registry)
    if registration is None and _is_generic_fed_avg_code(requested_code):
        registration = _find_registration(_default_aggregation_plugin_code(), enabled_registry)
    if registration:
        return _instantiate(registration)
    supported = ", ".join(plugin.code for plugin in enabled_registry)
    raise ValueError(
        f"Unsupported or disabled aggregation plugin: {requested_code}. "
        f"Enabled plugins for profile {plugin_profile()}: {supported}"
    )


def model_plugin_capabilities() -> list[dict]:
    return [
        {
            "code": registration.code,
            "aliases": list(registration.aliases),
            "supportedFormats": list(registration.supported_formats),
            "supportedTasks": list(registration.supported_tasks),
            "profiles": list(registration.profiles),
        }
        for registration in _enabled_model_registrations()
    ]


def aggregation_plugin_capabilities() -> list[dict]:
    return [
        {
            "code": registration.code,
            "aliases": list(registration.aliases),
            "supportedUpdateFormats": list(registration.supported_update_formats),
            "profiles": list(registration.profiles),
        }
        for registration in _enabled_aggregation_registrations()
    ]


def _find_registration(code: str, registry: tuple[PluginRegistration, ...]) -> PluginRegistration | None:
    for registration in registry:
        if code == registration.code or code in registration.aliases:
            return registration
    return None


def plugin_profile() -> str:
    profile = os.getenv("RUNTIME_ENGINE_PLUGIN_PROFILE", "full").strip().lower()
    return PROFILE_ALIASES.get(profile, profile) if profile else "full"


def _enabled_model_registrations() -> tuple[PluginRegistration, ...]:
    explicit_plugins = _explicit_enabled_model_plugins()
    if explicit_plugins is not None:
        return tuple(
            registration
            for registration in MODEL_PLUGIN_REGISTRY
            if registration.code in explicit_plugins or any(alias in explicit_plugins for alias in registration.aliases)
        )

    profile = plugin_profile()
    if profile == "full":
        return MODEL_PLUGIN_REGISTRY
    if profile == "core":
        return ()
    return tuple(registration for registration in MODEL_PLUGIN_REGISTRY if profile in registration.profiles)


def _enabled_aggregation_registrations() -> tuple[PluginRegistration, ...]:
    profile = plugin_profile()
    if profile == "full":
        return AGGREGATION_PLUGIN_REGISTRY
    if profile == "core":
        return ()
    return tuple(registration for registration in AGGREGATION_PLUGIN_REGISTRY if profile in registration.profiles)


def _default_aggregation_plugin_code() -> str:
    profile = plugin_profile()
    if profile.startswith("pytorch-"):
        return "FED_AVG_PYTORCH_STATE_DICT"
    return "FED_AVG_JSON"


def _is_generic_fed_avg_code(code: str) -> bool:
    return code in {"FED_AVG", "FEDERATED_AVERAGING", "WEIGHTED_FED_AVG"}


def _explicit_enabled_model_plugins() -> set[str] | None:
    raw_value = os.getenv("RUNTIME_ENGINE_ENABLED_MODEL_PLUGINS")
    if raw_value is None or not raw_value.strip():
        return None
    return {part.strip() for part in raw_value.split(",") if part.strip()}


def _instantiate(registration: PluginRegistration):
    module = import_module(registration.module)
    plugin_class = getattr(module, registration.class_name)
    return plugin_class()
