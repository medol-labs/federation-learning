from __future__ import annotations

import logging

import numpy as np

from gemifl.runtime.plugins.io import (
    default_artifact_path,
    load_xy,
    read_json,
    safe_data_source_for_log,
    write_json,
)


log = logging.getLogger("gemifl.runtime.plugins.sklearn_logistic_regression")


class SklearnLogisticRegressionPlugin:
    code = "SKLEARN_LOGISTIC_REGRESSION"
    aliases = ("linear.LogisticRegression", "SKLEARN_LOGISTIC_REGRESSION")
    supported_formats = ("JSON",)
    supported_tasks = ("BINARY_CLASSIFICATION",)

    def train(self, config: dict) -> None:
        data_source = config.get("input", {}).get("dataset") or config.get("dataset")
        if not data_source:
            node_name = config["nodeName"]
            data_source = config.get("nodeParameter", {}).get(node_name, {}).get("dataSource")
        x, y = load_xy(data_source)
        log.info(
            "Loaded training dataset jobId=%s plugin=%s rows=%s features=%s dataSource=%s",
            config.get("jobId"),
            self.code,
            x.shape[0],
            x.shape[1],
            safe_data_source_for_log(data_source),
        )

        model = self._load_model(config.get("input", {}).get("globalModel"), x.shape[1])
        epochs = int(config.get("modelParameter", {}).get("epoch", 1))
        learning_rate = float(config.get("modelParameter", {}).get("learningRate", 0.1))
        log.info(
            "Training model jobId=%s plugin=%s epochs=%s learningRate=%s initialFeatureCount=%s",
            config.get("jobId"),
            self.code,
            epochs,
            learning_rate,
            len(model["weights"]),
        )

        weights = np.array(model["weights"], dtype=float)
        bias = float(model["bias"])
        for _ in range(epochs):
            prediction = self._sigmoid(np.dot(x, weights) + bias)
            weights -= learning_rate * ((1 / len(y)) * np.dot(x.T, prediction - y))
            bias -= learning_rate * ((1 / len(y)) * np.sum(prediction - y))

        output = config.get("output", {})
        local_update = output.get("localUpdate") or default_artifact_path(config, "local_update.json")
        metrics_path = output.get("metrics") or default_artifact_path(config, "metrics.json")

        local_update_payload = {
            "weights": weights.tolist(),
            "bias": bias,
            "train_size": int(len(y)),
            "modelPlugin": self.code,
        }
        metrics_payload = {
            "samples": int(len(y)),
            "loss": self._mse_loss(x, y, weights, bias),
        }
        write_json(local_update, local_update_payload)
        write_json(metrics_path, metrics_payload)
        log.info(
            "Training step completed jobId=%s plugin=%s localUpdate=%s metrics=%s loss=%s",
            config.get("jobId"),
            self.code,
            local_update,
            metrics_path,
            metrics_payload["loss"],
        )

    def _load_model(self, model_path, feature_count: int) -> dict:
        if not model_path:
            return {"weights": np.zeros(feature_count).tolist(), "bias": 0.0}
        model = read_json(model_path)
        if "weights" not in model or "bias" not in model:
            raise ValueError("globalModel must include weights and bias")
        return model

    def _mse_loss(self, x, y, weights, bias) -> float:
        prediction = self._sigmoid(np.dot(x, weights) + bias)
        return float(np.sum((prediction - y) ** 2 / len(prediction)))

    def _sigmoid(self, z):
        z = np.clip(z, -500, 500)
        return 1 / (1 + np.exp(-z))
