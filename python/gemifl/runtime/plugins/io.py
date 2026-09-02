from __future__ import annotations

import json
import os
from pathlib import Path
from urllib.parse import urlsplit
from urllib.request import Request, urlopen

import pandas as pd


def load_xy(data_source):
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


def default_artifact_path(config: dict, name: str) -> str:
    root = Path(config.get("runtimeRoot", "./tmp/runtime-engine"))
    job_id = config["jobId"]
    node_name = config["nodeName"]
    return str(root / job_id / node_name / name)


def read_json(path):
    if str(path).startswith(("http://", "https://")):
        headers = {}
        internal_token = os.getenv("MEDOL_SECURITY_INTERNAL_TOKEN")
        if internal_token:
            headers["X-MEDOL-INTERNAL-TOKEN"] = internal_token
        request = Request(resolve_file_access_url(str(path)), headers=headers)
        with urlopen(request, timeout=30) as response:
            return json.load(response)
    with open(path, "r", encoding="utf-8") as f:
        return json.load(f)


def resolve_file_access_url(file_url: str) -> str:
    access_base_url = os.getenv("RUNTIME_ENGINE_FILE_ACCESS_BASE_URL", "").strip().rstrip("/")
    if not access_base_url:
        return file_url
    parsed = urlsplit(file_url)
    suffix = parsed.path
    if parsed.query:
        suffix += f"?{parsed.query}"
    return f"{access_base_url}{suffix}"


def write_json(path, payload):
    output_path = Path(path)
    output_path.parent.mkdir(parents=True, exist_ok=True)
    with output_path.open("w", encoding="utf-8") as f:
        json.dump(payload, f, indent=2)


def safe_data_source_for_log(data_source):
    if isinstance(data_source, dict):
        return {
            key: value
            for key, value in data_source.items()
            if key in {"path", "X", "y", "labelColumn", "id"}
        }
    return data_source
