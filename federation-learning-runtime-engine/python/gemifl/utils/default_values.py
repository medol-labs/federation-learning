# -*- coding: utf-8 -*-
import os

metric_list = {
    "binary": ["auc", "precision", "recall", "best_score"],
    "multiclass": ["accuracy", "micro_precision", "macro_precision", "weighted_precision", "micro_recall",
                   "macro_recall", "weighted_recall", "micro_F1", "macro_F1", "weighted_F1", "best_score"]
}

NUMEXPR_MAX_THREADS = '32'

contri_url = os.getenv("GEMIFL_CONTRIBUTION_DEGREES_URL", "http://localhost:8080/api/contribution-degrees")
