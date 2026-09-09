from __future__ import annotations

import argparse
import json

from gemifl.runtime.plugins import aggregation_plugin_capabilities, model_plugin_capabilities, plugin_profile


def runtime_capabilities() -> dict:
    return {
        "pluginProfile": plugin_profile(),
        "modelPlugins": model_plugin_capabilities(),
        "aggregationPlugins": aggregation_plugin_capabilities(),
    }


def main() -> None:
    parser = argparse.ArgumentParser(description="Print Runtime Engine plugin capabilities.")
    parser.add_argument("--pretty", action="store_true", help="Pretty-print JSON output.")
    args = parser.parse_args()
    print(json.dumps(runtime_capabilities(), indent=2 if args.pretty else None, sort_keys=True))


if __name__ == "__main__":
    main()
