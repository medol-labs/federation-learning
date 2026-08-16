import argparse
import json
import os
import traceback
from importlib import import_module
from pathlib import Path

from gemifl.runtime.steps import run_platform_step


def resolve_runtime_config(config_path: str, node_name: str | None = None) -> dict:
    with open(config_path, "r", encoding="utf-8") as f:
        config = json.load(f)

    resolved_node = (
        node_name
        or config.get("my_name")
        or os.getenv("RUNTIME_ENGINE_NODE_NAME")
        or os.getenv("GEMIFL_NODE_NAME")
    )
    if not resolved_node:
        raise ValueError("Runtime node name is required via --node-name, my_name, or RUNTIME_ENGINE_NODE_NAME")

    config["my_name"] = resolved_node
    config["task_id"] = str(config.get("task_id") or config.get("job_id") or config.get("jobId"))
    if not config["task_id"]:
        raise ValueError("Runtime job config must include task_id, job_id, or jobId")

    config.setdefault("job_parameter", {})
    config.setdefault("node_parameter", {})
    config.setdefault("model_parameter", {})
    return config


def resolve_role(config: dict) -> str:
    node_name = config["my_name"]
    for role, nodes in config["roles"].items():
        if node_name in nodes:
            return role
    node_params = config.get("node_parameter", {}).get(node_name, {})
    if node_params.get("role"):
        return node_params["role"]
    raise ValueError(f"Node {node_name} was not found in roles or node_parameter role")


def load_model_class(config: dict, role: str):
    engine = config["model_parameter"].get("engine", "python")
    base_model = config["model_parameter"].get("model")
    if not base_model:
        raise ValueError("model_parameter.model is required")

    role_suffix = role[:1].upper() + role[1:]
    model_path = f"{base_model}{role_suffix}"
    module = import_module(f"gemifl.algorithms.models_{engine}.{model_path}")
    class_name = model_path.split(".")[-1]
    return getattr(module, class_name)


def write_runtime_config(config: dict, runtime_root: str = "./tmp/runtime-engine") -> Path:
    task_id = config["task_id"]
    node_name = config["my_name"]
    config_dir = Path(runtime_root) / task_id / node_name
    config_dir.mkdir(parents=True, exist_ok=True)
    config_path = config_dir / "config.json"
    with config_path.open("w", encoding="utf-8") as f:
        json.dump(config, f, indent=2)
    return config_path


def run_config(config_path: str, node_name: str | None = None) -> None:
    config = resolve_runtime_config(config_path, node_name)
    write_runtime_config(config, config.get("runtime_root", "./tmp/runtime-engine"))
    if config.get("operation"):
        run_platform_step(config)
        return
    role = resolve_role(config)
    model_class = load_model_class(config, role)
    app = model_class(config)
    app.run()


def main() -> None:
    parser = argparse.ArgumentParser(description="Run one runtime engine training job on this node.")
    parser.add_argument("--config", required=True, help="Path to the runtime job JSON config.")
    parser.add_argument("--node-name", default=None, help="Current node name. Overrides config my_name.")
    args = parser.parse_args()

    try:
        run_config(args.config, args.node_name)
    except Exception as error:
        print(f"Error in runtime executor: {error}")
        traceback.print_exc()
        raise


if __name__ == "__main__":
    main()
