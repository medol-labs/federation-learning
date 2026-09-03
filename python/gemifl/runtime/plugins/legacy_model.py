from __future__ import annotations

import logging
from pathlib import Path
from typing import Type

from gemifl.runtime.plugins.io import default_artifact_path, read_json, write_json
from gemifl.utils.config_parser import ConfigParser


log = logging.getLogger("gemifl.runtime.plugins.legacy_model")


class LegacyModelPlugin:
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
        self._load_initial_model(model, config)
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
        initial_model_path = self._initial_model_path(config)
        if initial_model_path:
            model_parameter.setdefault("load_model_path", initial_model_path)
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

    def _load_initial_model(self, model, config: dict) -> None:
        initial_model = config.get("input", {}).get("globalModel")
        if not initial_model:
            return

        parameters = self._initial_model_parameters(initial_model)
        if parameters and hasattr(model, "set_weights"):
            model.set_weights(parameters)
            log.info(
                "Loaded initial model parameters jobId=%s plugin=%s",
                config.get("jobId"),
                self.code,
            )
            return

        artifact_path = self._initial_model_path(config)
        if artifact_path and self._load_state_dict_artifact(model, artifact_path):
            log.info(
                "Loaded initial model state dict jobId=%s plugin=%s artifact=%s",
                config.get("jobId"),
                self.code,
                artifact_path,
            )
            return

        log.info(
            "Initial model was provided but no compatible legacy weight payload was found jobId=%s plugin=%s",
            config.get("jobId"),
            self.code,
        )

    def _initial_model_parameters(self, initial_model):
        if isinstance(initial_model, dict):
            parameters = initial_model.get("parameters") or initial_model.get("weights")
            if isinstance(parameters, dict):
                return parameters
            artifact = initial_model.get("weightArtifact") or initial_model.get("modelArtifact")
            if artifact:
                return self._initial_model_parameters(artifact)
            return None

        artifact_path = self._normalize_artifact_path(initial_model)
        if not artifact_path:
            return None

        path = Path(artifact_path)
        if not path.exists() or path.suffix.lower() != ".json":
            return None

        payload = read_json(path)
        parameters = payload.get("parameters") or payload.get("weights")
        if isinstance(parameters, dict):
            return parameters
        artifact = payload.get("weightArtifact") or payload.get("modelArtifact")
        if artifact:
            return self._initial_model_parameters(artifact)
        return None

    def _initial_model_path(self, config: dict) -> str | None:
        initial_model = config.get("input", {}).get("globalModel")
        if isinstance(initial_model, dict):
            artifact = initial_model.get("weightArtifact") or initial_model.get("modelArtifact")
            return self._normalize_artifact_path(artifact)
        return self._normalize_artifact_path(initial_model)

    def _normalize_artifact_path(self, artifact) -> str | None:
        if not isinstance(artifact, str) or not artifact:
            return None
        if artifact.startswith("file://"):
            return artifact.removeprefix("file://")
        return artifact

    def _load_state_dict_artifact(self, model, artifact_path: str) -> bool:
        path = Path(artifact_path)
        if not path.exists() or path.suffix.lower() not in {".pt", ".pth"}:
            return False

        import torch

        payload = torch.load(path, map_location="cpu")
        state_dict = payload.get("state_dict") if isinstance(payload, dict) and "state_dict" in payload else payload
        target = self._state_dict_target(model)
        if not target:
            return False
        target.load_state_dict(state_dict)
        return True

    def _state_dict_target(self, model):
        target = getattr(model, "model", None)
        if target is None:
            return None
        if hasattr(target, "load_state_dict"):
            return target
        network = getattr(target, "network", None)
        if network is not None and hasattr(network, "load_state_dict"):
            return network
        return None
