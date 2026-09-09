from __future__ import annotations

from gemifl.algorithms.models_python.algos.HomoBertClassifier import HomoBertClassifier
from gemifl.runtime.plugins.legacy_model import LegacyModelPlugin


class BertPlugin(LegacyModelPlugin):
    code = "PYTORCH_BERT_CLASSIFIER"
    aliases = ("BERT", "HOMO_BERT", "HomoBertClassifier")
    supported_formats = ("PYTORCH_STATE_DICT", "PICKLE")
    supported_tasks = ("TEXT_CLASSIFICATION", "NLP_CLASSIFICATION")
    legacy_model_class = HomoBertClassifier
    default_data_type = "HuggingfacePretrainTokenizer"

    def _apply_default_net_config(self, model_parameter: dict) -> None:
        model_parameter.setdefault("num_class", 2)
        model_parameter.setdefault("pretrain", model_parameter.get("load_model_path", "bert-base-uncased"))
        model_parameter.setdefault("data_config", {"max_length": 128, "text_col": "text", "label_col": "label"})
