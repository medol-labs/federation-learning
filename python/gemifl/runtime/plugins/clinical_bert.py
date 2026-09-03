from __future__ import annotations

from gemifl.algorithms.models_python.algos.HomoClinicalBert import HomoClinicalBert
from gemifl.runtime.plugins.legacy_model import LegacyModelPlugin


class ClinicalBertPlugin(LegacyModelPlugin):
    code = "PYTORCH_CLINICAL_BERT_CLASSIFIER"
    aliases = ("CLINICAL_BERT", "CLINICALBERT", "HOMO_CLINICAL_BERT", "HomoClinicalBert")
    supported_formats = ("PYTORCH_STATE_DICT", "PICKLE")
    supported_tasks = ("TEXT_CLASSIFICATION", "CLINICAL_TEXT_CLASSIFICATION")
    legacy_model_class = HomoClinicalBert
    default_data_type = "CsvGenerator"

    def _apply_default_net_config(self, model_parameter: dict) -> None:
        model_parameter.setdefault("num_labels", 2)
        model_parameter.setdefault("load_model_path", "emilyalsentzer/Bio_ClinicalBERT")
        model_parameter.setdefault("load_token_path", "emilyalsentzer/Bio_ClinicalBERT")
