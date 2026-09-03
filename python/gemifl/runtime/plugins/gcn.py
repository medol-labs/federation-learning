from __future__ import annotations

from gemifl.algorithms.models_python.algos.HomoGcn import HomoGcn
from gemifl.runtime.plugins.legacy_model import LegacyModelPlugin


class GCNPlugin(LegacyModelPlugin):
    code = "PYTORCH_GCN_CLASSIFIER"
    aliases = ("GCN", "HOMO_GCN", "HomoGcn")
    supported_formats = ("PYTORCH_STATE_DICT", "PICKLE")
    supported_tasks = ("GRAPH_CLASSIFICATION", "MOLECULAR_GRAPH_CLASSIFICATION")
    legacy_model_class = HomoGcn

    def _apply_default_net_config(self, model_parameter: dict) -> None:
        model_parameter.setdefault(
            "net_config",
            {
                "feat_dim": 1,
                "hidden_dim": 16,
                "node_embedding_dim": 16,
                "dropout": 0.5,
                "readout_hidden_dim": 16,
                "graph_embedding_dim": 16,
                "num_categories": 2,
            },
        )
