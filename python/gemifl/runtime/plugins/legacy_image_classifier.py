from __future__ import annotations

import logging
from pathlib import Path
from typing import Type

from gemifl.runtime.plugins.io import default_artifact_path, write_json
from gemifl.utils.config_parser import ConfigParser


log = logging.getLogger("gemifl.runtime.plugins.legacy_image_classifier")


class LegacyImageClassifierPlugin:
    code = ""
    aliases: tuple[str, ...] = ()
    supported_formats = ("PYTORCH_STATE_DICT", "PICKLE")
    supported_tasks = ("IMAGE_CLASSIFICATION", "MEDICAL_IMAGE_CLASSIFICATION")
    legacy_model_class: Type | None = None
    default_data_type = "DenseNetImage"
    default_train_batch_size = 8
    default_valid_batch_size = 8
    default_task_type = "multiclass"
    default_learning_rate = 0.001

    def train(self, config: dict) -> None:
        if self.legacy_model_class is None:
            raise ValueError(f"{self.__class__.__name__} must define legacy_model_class")

        legacy_config = self._to_legacy_config(config)
        model = self.legacy_model_class(ConfigParser(legacy_config), log)
        epochs = int(legacy_config["model_parameter"].get("epochs", 1))
        task_type = legacy_config["model_parameter"].get("task_type", "multiclass")

        for _ in range(epochs):
            model.train_one_epoch()

        metrics = model.evaluate(task_type) if getattr(model, "val_data_source", None) else {}
        model.set_best_weight()
        model.dumpfile()

        output = config.get("output", {})
        local_update = output.get("localUpdate") or default_artifact_path(config, "local_update.json")
        metrics_path = output.get("metrics") or default_artifact_path(config, "metrics.json")

        local_update_payload = {
            "modelPlugin": self.code,
            "weightArtifact": legacy_config["model_parameter"]["weight_save"],
            "modelArtifact": legacy_config["model_parameter"]["model_save"],
            "format": "PYTORCH_STATE_DICT",
        }
        write_json(local_update, local_update_payload)
        write_json(metrics_path, metrics)
        log.info(
            "Legacy image classifier completed jobId=%s plugin=%s localUpdate=%s metrics=%s",
            config.get("jobId"),
            self.code,
            local_update,
            metrics_path,
        )

    def _to_legacy_config(self, config: dict) -> dict:
        node_name = config.get("nodeName") or config.get("my_name")
        if not node_name:
            raise ValueError("nodeName is required for legacy image classifier plugins")

        model_parameter = dict(config.get("modelParameter") or config.get("model_parameter") or {})
        node_parameter = dict(config.get("nodeParameter") or config.get("node_parameter") or {})
        node_config = dict(config.get("nodeConfig") or config.get("node_config") or {})
        role = config.get("role") or node_parameter.get(node_name, {}).get("role") or "client"

        data_source = (
            config.get("input", {}).get("dataset")
            or config.get("dataset")
            or node_parameter.get(node_name, {}).get("dataSource")
            or node_parameter.get(node_name, {}).get("data_source")
        )
        if data_source:
            node_parameter.setdefault(node_name, {})
            node_parameter[node_name].setdefault("role", role)
            node_parameter[node_name]["data_source"] = self._normalize_data_source(data_source)

        model_parameter.setdefault("model_save", self._artifact_path(config, "model.pkl"))
        model_parameter.setdefault("weight_save", self._artifact_path(config, "model_state_dict.pt"))
        model_parameter.setdefault("data_type", self.default_data_type)
        model_parameter.setdefault("train_batch_size", self.default_train_batch_size)
        model_parameter.setdefault("valid_batch_size", self.default_valid_batch_size)
        model_parameter.setdefault("epochs", model_parameter.get("epoch", 1))
        model_parameter.setdefault("task_type", self.default_task_type)
        model_parameter.setdefault("lr", model_parameter.get("learningRate", self.default_learning_rate))
        model_parameter.setdefault("learning_rate", model_parameter.get("learningRate", self.default_learning_rate))
        self._apply_default_net_config(model_parameter)

        return {
            "job_id": config.get("jobId"),
            "task_id": config.get("jobId"),
            "my_name": node_name,
            "node_config": node_config,
            "model_parameter": model_parameter,
            "node_parameter": node_parameter,
        }

    def _apply_default_net_config(self, model_parameter: dict) -> None:
        model_parameter.setdefault("net_config", {"num_classes": 2, "small_inputs": False})

    def _normalize_data_source(self, data_source) -> dict:
        if isinstance(data_source, str):
            return {"train": data_source, "val": data_source}
        if not isinstance(data_source, dict):
            raise ValueError("dataset input must be a path or data_source object")

        train = data_source.get("train") or data_source.get("trainPath") or data_source.get("path")
        val = data_source.get("val") or data_source.get("validation") or data_source.get("validationPath") or train
        if not train:
            raise ValueError("legacy image classifier dataset requires train/path")
        return {"train": train, "val": val}

    def _artifact_path(self, config: dict, name: str) -> str:
        path = Path(default_artifact_path(config, name))
        path.parent.mkdir(parents=True, exist_ok=True)
        return str(path)
