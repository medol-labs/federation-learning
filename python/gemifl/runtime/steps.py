import json
import logging
from pathlib import Path

import numpy as np
import pandas as pd


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
    model_name = config.get("modelParameter", {}).get("model", "linear.LogisticRegression")
    if model_name != "linear.LogisticRegression":
        raise ValueError(f"Unsupported platform-step model: {model_name}")

    data_source = config.get("input", {}).get("dataset") or config.get("dataset")
    if not data_source:
        node_name = config["nodeName"]
        data_source = config.get("nodeParameter", {}).get(node_name, {}).get("dataSource")
    x, y = _load_xy(data_source)
    log.info(
        "Loaded training dataset jobId=%s model=%s rows=%s features=%s dataSource=%s",
        config.get("jobId"),
        model_name,
        x.shape[0],
        x.shape[1],
        _safe_data_source_for_log(data_source),
    )

    model = _load_model(config.get("input", {}).get("globalModel"), x.shape[1])
    epochs = int(config.get("modelParameter", {}).get("epoch", 1))
    learning_rate = float(config.get("modelParameter", {}).get("learningRate", 0.1))
    log.info(
        "Training logistic regression jobId=%s epochs=%s learningRate=%s initialFeatureCount=%s",
        config.get("jobId"),
        epochs,
        learning_rate,
        len(model["weights"]),
    )

    weights = np.array(model["weights"], dtype=float)
    bias = float(model["bias"])
    for _ in range(epochs):
        prediction = _sigmoid(np.dot(x, weights) + bias)
        weights -= learning_rate * ((1 / len(y)) * np.dot(x.T, prediction - y))
        bias -= learning_rate * ((1 / len(y)) * np.sum(prediction - y))

    output = config.get("output", {})
    local_update = output.get("localUpdate") or _default_artifact_path(config, "local_update.json")
    metrics_path = output.get("metrics") or _default_artifact_path(config, "metrics.json")

    local_update_payload = {
        "weights": weights.tolist(),
        "bias": bias,
        "train_size": int(len(y)),
    }
    metrics_payload = {
        "samples": int(len(y)),
        "loss": _mse_loss(x, y, weights, bias),
    }
    _write_json(local_update, local_update_payload)
    _write_json(metrics_path, metrics_payload)
    log.info(
        "Training step completed jobId=%s localUpdate=%s metrics=%s loss=%s",
        config.get("jobId"),
        local_update,
        metrics_path,
        metrics_payload["loss"],
    )


def aggregate(config: dict) -> None:
    updates = config.get("input", {}).get("updates", [])
    if not updates:
        raise ValueError("aggregate operation requires input.updates")
    log.info("Aggregating model updates jobId=%s updateCount=%s", config.get("jobId"), len(updates))

    weighted_weights = None
    weighted_bias = 0.0
    total_size = 0
    for update_path in updates:
        update = _read_json(update_path)
        train_size = int(update.get("train_size", 1))
        weights = np.array(update["weights"], dtype=float)
        if weighted_weights is None:
            weighted_weights = np.zeros_like(weights)
        weighted_weights += weights * train_size
        weighted_bias += float(update["bias"]) * train_size
        total_size += train_size

    if total_size == 0:
        raise ValueError("aggregate operation received zero total train_size")

    global_model = config.get("output", {}).get("globalModel") or _default_artifact_path(config, "global_model.json")
    global_model_payload = {
        "weights": (weighted_weights / total_size).tolist(),
        "bias": weighted_bias / total_size,
        "train_size": total_size,
    }
    _write_json(global_model, global_model_payload)
    log.info(
        "Aggregation step completed jobId=%s globalModel=%s totalTrainSize=%s",
        config.get("jobId"),
        global_model,
        total_size,
    )


def _load_xy(data_source):
    if isinstance(data_source, str):
        data_source = {"path": data_source}
    if not isinstance(data_source, dict):
        raise ValueError("dataset input must be a path or data_source object")

    if "path" in data_source:
        df = pd.read_csv(data_source["path"])
        label_column = data_source.get("labelColumn", "y")
        id_column = data_source.get("id")
        y = df[label_column].values.flatten()
        drop_columns = [label_column]
        if id_column and id_column in df.columns:
            drop_columns.append(id_column)
        return df.drop(columns=drop_columns).values, y

    return (
        pd.read_csv(data_source["X"]).values,
        pd.read_csv(data_source["y"]).values.flatten(),
    )


def _load_model(model_path, feature_count: int) -> dict:
    if not model_path:
        return {"weights": np.zeros(feature_count).tolist(), "bias": 0.0}
    model = _read_json(model_path)
    if "weights" not in model or "bias" not in model:
        raise ValueError("globalModel must include weights and bias")
    return model


def _sigmoid(z):
    z = np.clip(z, -500, 500)
    return 1 / (1 + np.exp(-z))


def _mse_loss(x, y, weights, bias) -> float:
    prediction = _sigmoid(np.dot(x, weights) + bias)
    return float(np.sum((prediction - y) ** 2 / len(prediction)))


def _default_artifact_path(config: dict, name: str) -> str:
    root = Path(config.get("runtimeRoot", "./tmp/runtime-engine"))
    job_id = config["jobId"]
    node_name = config["nodeName"]
    return str(root / job_id / node_name / name)


def _read_json(path):
    with open(path, "r", encoding="utf-8") as f:
        return json.load(f)


def _write_json(path, payload):
    output_path = Path(path)
    output_path.parent.mkdir(parents=True, exist_ok=True)
    with output_path.open("w", encoding="utf-8") as f:
        json.dump(payload, f, indent=2)


def _safe_data_source_for_log(data_source):
    if isinstance(data_source, dict):
        return {key: value for key, value in data_source.items() if key in {"path", "X", "y", "labelColumn", "id"}}
    return data_source
