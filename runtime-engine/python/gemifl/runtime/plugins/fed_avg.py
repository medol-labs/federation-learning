from __future__ import annotations

import logging

import numpy as np

from gemifl.runtime.plugins.io import default_artifact_path, read_json, write_json


log = logging.getLogger("gemifl.runtime.plugins.fed_avg")


class FedAvgJsonAggregationPlugin:
    code = "FED_AVG_JSON"
    aliases = ("FED_AVG", "FEDERATED_AVERAGING", "WEIGHTED_FED_AVG", "FED_AVG_JSON")
    supported_update_formats = ("JSON",)

    def aggregate(self, config: dict) -> None:
        updates = config.get("input", {}).get("updates", [])
        if not updates:
            raise ValueError("aggregate operation requires input.updates")
        log.info(
            "Aggregating model updates jobId=%s plugin=%s updateCount=%s",
            config.get("jobId"),
            self.code,
            len(updates),
        )

        weighted_weights = None
        weighted_bias = 0.0
        total_size = 0
        for update_path in updates:
            update = read_json(update_path)
            train_size = int(update.get("train_size", 1))
            weights = np.array(update["weights"], dtype=float)
            if weighted_weights is None:
                weighted_weights = np.zeros_like(weights)
            weighted_weights += weights * train_size
            weighted_bias += float(update["bias"]) * train_size
            total_size += train_size

        if total_size == 0:
            raise ValueError("aggregate operation received zero total train_size")

        global_model = config.get("output", {}).get("globalModel") or default_artifact_path(
            config,
            "global_model.json",
        )
        global_model_payload = {
            "weights": (weighted_weights / total_size).tolist(),
            "bias": weighted_bias / total_size,
            "train_size": total_size,
            "aggregationPlugin": self.code,
        }
        write_json(global_model, global_model_payload)
        log.info(
            "Aggregation step completed jobId=%s plugin=%s globalModel=%s totalTrainSize=%s",
            config.get("jobId"),
            self.code,
            global_model,
            total_size,
        )
