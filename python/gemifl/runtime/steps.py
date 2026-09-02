import logging

from gemifl.runtime.plugins import get_aggregation_plugin, get_model_plugin


log = logging.getLogger("gemifl.runtime.steps")


def run_platform_step(config: dict) -> None:
    operation = config.get("operation")
    log.info("Running platform step jobId=%s operation=%s nodeName=%s", config.get("jobId"), operation, config.get("nodeName"))
    if operation == "train":
        train(config)
        return
    if operation == "aggregate":
        aggregate(config)
        return
    raise ValueError("operation must be 'train' or 'aggregate'")


def train(config: dict) -> None:
    plugin = get_model_plugin(_model_plugin_code(config))
    log.info("Resolved model plugin jobId=%s plugin=%s", config.get("jobId"), plugin.code)
    plugin.train(config)


def aggregate(config: dict) -> None:
    plugin = get_aggregation_plugin(_aggregation_plugin_code(config))
    log.info("Resolved aggregation plugin jobId=%s plugin=%s", config.get("jobId"), plugin.code)
    plugin.aggregate(config)


def _model_plugin_code(config: dict) -> str | None:
    model_parameter = config.get("modelParameter", {})
    return (
        model_parameter.get("modelPlugin")
        or model_parameter.get("plugin")
        or config.get("model", {}).get("plugin")
        or model_parameter.get("model")
    )


def _aggregation_plugin_code(config: dict) -> str | None:
    model_parameter = config.get("modelParameter", {})
    aggregation_parameter = config.get("aggregation", {})
    return (
        aggregation_parameter.get("plugin")
        or aggregation_parameter.get("algorithm")
        or model_parameter.get("aggregationPlugin")
        or model_parameter.get("aggregationAlgorithm")
    )
