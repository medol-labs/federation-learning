from __future__ import annotations

from typing import Protocol


class ModelPlugin(Protocol):
    code: str
    aliases: tuple[str, ...]
    supported_formats: tuple[str, ...]
    supported_tasks: tuple[str, ...]

    def train(self, config: dict) -> None:
        ...


class AggregationPlugin(Protocol):
    code: str
    aliases: tuple[str, ...]
    supported_update_formats: tuple[str, ...]

    def aggregate(self, config: dict) -> None:
        ...
