import argparse
import json
import logging
import os
import traceback
from importlib import import_module
from pathlib import Path

from gemifl.runtime.steps import run_platform_step


log = logging.getLogger("gemifl.runtime.executor")


def resolve_runtime_config(config_path: str, node_name: str | None = None) -> dict:
    with open(config_path, "r", encoding="utf-8") as f:
        config = json.load(f)

    resolved_node = (
        node_name
        or config.get("nodeName")
        or os.getenv("RUNTIME_ENGINE_NODE_NAME")
        or os.getenv("GEMIFL_NODE_NAME")
    )
    if not resolved_node:
        raise ValueError("Runtime node name is required via --node-name, myName, or RUNTIME_ENGINE_NODE_NAME")

    config["nodeName"] = resolved_node
    job_id = config.get("jobId")
    if not job_id:
        raise ValueError(f"Runtime job config must include jobId. Received keys: {sorted(config.keys())}")
    config["jobId"] = str(job_id)

    config.setdefault("jobParameter", {})
    config.setdefault("nodeParameter", {})
    config.setdefault("modelParameter", {})
    return config


def resolve_role(config: dict) -> str:
    node_name = config["nodeName"]
    for role, nodes in config["roles"].items():
        if node_name in nodes:
            return role
    node_params = config.get("nodeParameter", {}).get(node_name, {})
    if node_params.get("role"):
        return node_params["role"]
    raise ValueError(f"Node {node_name} was not found in roles or nodeParameter role")


def load_model_class(config: dict, role: str):
    engine = config["modelParameter"].get("engine", "python")
    base_model = config["modelParameter"].get("model")
    if not base_model:
        raise ValueError("modelParameter.model is required")

    role_suffix = role[:1].upper() + role[1:]
    model_path = f"{base_model}{role_suffix}"
    module = import_module(f"gemifl.algorithms.models_{engine}.{model_path}")
    class_name = model_path.split(".")[-1]
    return getattr(module, class_name)


def write_runtime_config(config: dict, runtime_root: str = "./tmp/runtime-engine") -> Path:
    job_id = config["jobId"]
    node_name = config["nodeName"]
    config_dir = Path(runtime_root) / job_id / node_name
    config_dir.mkdir(parents=True, exist_ok=True)
    config_path = config_dir / "config.json"
    with config_path.open("w", encoding="utf-8") as f:
        json.dump(config, f, indent=2)
    log.info("Resolved runtime config written jobId=%s nodeName=%s configPath=%s", job_id, node_name, config_path)
    return config_path


def run_config(config_path: str, node_name: str | None = None) -> None:
    config = resolve_runtime_config(config_path, node_name)
    log.info(
        "Runtime executor started jobId=%s nodeName=%s operation=%s model=%s runtimeRoot=%s",
        config.get("jobId"),
        config.get("nodeName"),
        config.get("operation"),
        config.get("modelParameter", {}).get("model"),
        config.get("runtimeRoot"),
    )
    write_runtime_config(config, config.get("runtimeRoot", "./tmp/runtime-engine"))
    if config.get("operation"):
        run_platform_step(config)
        log.info("Runtime executor completed jobId=%s operation=%s", config.get("jobId"), config.get("operation"))
        return
    role = resolve_role(config)
    model_class = load_model_class(config, role)
    app = model_class(config)
    app.run()
    log.info("Runtime executor completed jobId=%s role=%s", config.get("jobId"), role)


def main() -> None:
    logging.basicConfig(
        level=os.getenv("RUNTIME_ENGINE_LOG_LEVEL", "INFO").upper(),
        format="%(asctime)s %(levelname)s %(name)s - %(message)s",
    )
    parser = argparse.ArgumentParser(description="Run one runtime engine training job on this node.")
    parser.add_argument("--config", required=True, help="Path to the runtime job JSON config.")
    parser.add_argument("--node-name", default=None, help="Current node name. Overrides config nodeName.")
    args = parser.parse_args()

    try:
        run_config(args.config, args.node_name)
    except Exception as error:
        log.error("Runtime executor failed: %s", error)
        traceback.print_exc()
        raise


if __name__ == "__main__":
    main()
