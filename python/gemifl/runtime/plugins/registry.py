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
