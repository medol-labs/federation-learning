from __future__ import annotations

import logging
from pathlib import Path

import torch

from gemifl.runtime.plugins.io import default_artifact_path, read_json, write_json


log = logging.getLogger("gemifl.runtime.plugins.fed_avg_pytorch_state_dict")


class FedAvgPytorchStateDictAggregationPlugin:
    code = "FED_AVG_PYTORCH_STATE_DICT"
    aliases = (
        "PYTORCH_STATE_DICT_FED_AVG",
        "FED_AVG_TORCH_STATE_DICT",
        "WEIGHTED_FED_AVG_PYTORCH_STATE_DICT",
    )
    supported_update_formats = ("PYTORCH_STATE_DICT",)

    def aggregate(self, config: dict) -> None:
        updates = config.get("input", {}).get("updates", [])
        if not updates:
            raise ValueError("aggregate operation requires input.updates")

        log.info(
            "Aggregating PyTorch state_dict updates jobId=%s plugin=%s updateCount=%s",
            config.get("jobId"),
            self.code,
            len(updates),
        )

        weighted_state = None
        reference_state = None
        total_size = 0
        for update_path in updates:
            update = read_json(update_path)
            train_size = int(update.get("train_size", update.get("trainSize", 1)))
            artifact_path = self._artifact_path(update.get("weightArtifact") or update.get("modelUpdateArtifact"))
            if not artifact_path:
                raise ValueError(f"PyTorch update descriptor must include weightArtifact: {update_path}")
            state_dict = self._load_state_dict(artifact_path)
            if weighted_state is None:
                weighted_state = self._empty_weighted_state(state_dict)
                reference_state = state_dict
            self._accumulate(weighted_state, state_dict, train_size, artifact_path)
            total_size += train_size

        if total_size == 0:
            raise ValueError("aggregate operation received zero total train_size")

        aggregated_state = self._finalize(weighted_state, reference_state, total_size)
        output = config.get("output", {})
        weight_artifact = output.get("weightArtifact") or default_artifact_path(
            config,
            "global_model_state_dict.pt",
        )
        global_model = output.get("globalModel") or default_artifact_path(config, "global_model.json")

        output_path = Path(weight_artifact)
        output_path.parent.mkdir(parents=True, exist_ok=True)
        torch.save(aggregated_state, output_path)

        write_json(
            global_model,
            {
                "weightArtifact": weight_artifact,
                "format": "PYTORCH_STATE_DICT",
                "train_size": total_size,
                "aggregationPlugin": self.code,
            },
        )
        log.info(
            "PyTorch state_dict aggregation completed jobId=%s plugin=%s globalModel=%s weightArtifact=%s totalTrainSize=%s",
            config.get("jobId"),
            self.code,
            global_model,
            weight_artifact,
            total_size,
        )

    def _artifact_path(self, artifact) -> str | None:
        if isinstance(artifact, dict):
            return self._artifact_path(artifact.get("weightArtifact") or artifact.get("modelArtifact"))
        if not isinstance(artifact, str) or not artifact:
            return None
        if artifact.startswith("file://"):
            return artifact.removeprefix("file://")
        return artifact

    def _load_state_dict(self, artifact_path: str) -> dict:
        payload = torch.load(artifact_path, map_location="cpu")
        if isinstance(payload, dict) and "state_dict" in payload:
            payload = payload["state_dict"]
        if not isinstance(payload, dict):
            raise ValueError(f"PyTorch state_dict artifact must contain a dict: {artifact_path}")
        return payload

    def _empty_weighted_state(self, state_dict: dict) -> dict:
        return {
            key: torch.zeros_like(value, dtype=torch.float64)
            for key, value in state_dict.items()
            if torch.is_tensor(value) and (value.is_floating_point() or value.is_complex())
        }

    def _accumulate(self, weighted_state: dict, state_dict: dict, train_size: int, artifact_path: str) -> None:
        missing_keys = set(weighted_state.keys()) - set(state_dict.keys())
        if missing_keys:
            raise ValueError(f"PyTorch state_dict is missing keys {sorted(missing_keys)}: {artifact_path}")
        for key in weighted_state:
            value = state_dict[key]
            if not torch.is_tensor(value):
                raise ValueError(f"PyTorch state_dict key is not a tensor: {key}")
            weighted_state[key] += value.to(dtype=torch.float64) * train_size

    def _finalize(self, weighted_state: dict, reference_state: dict, total_size: int) -> dict:
        aggregated_state = {}
        for key, value in reference_state.items():
            if key in weighted_state:
                averaged = weighted_state[key] / total_size
                aggregated_state[key] = averaged.to(dtype=value.dtype)
            else:
                aggregated_state[key] = value
        return aggregated_state
