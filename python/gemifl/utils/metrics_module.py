# metrics_module.py

import os
import json
import time
import numpy as np
from sklearn.metrics import *
import pickle


class MLMetrics:
    def __init__(self, labels, preds, probs):
        self.labels = labels
        self.preds = preds
        self.probs = probs
        self.calfun = {"multiclass": self.__multiclass_cal,"multiclass_onehot":self.__multiclass_cal,
                       "binary": self.__binary_cal, "multiclass1": self.__multiclass_cal1,
                       "segmentation": self.__segmentation_cal
                       }

    def __binary_cal(self):
        acc = accuracy_score(self.labels, self.preds)
        auc = roc_auc_score(self.labels, self.probs)
        precision = precision_score(self.labels, self.preds)
        recall = recall_score(self.labels, self.preds)
        current_score = {"accuracy": acc, "best_score": auc,
                         "auc": auc, "precision": precision, "recall": recall}
        return current_score

    def __multiclass_cal1(self):
        current_score = {}

        current_score["best_score"] = 0
        y_pred = np.array(self.probs)
        y_true = np.array(self.labels)

        results = []
        precs = []
        recalls = []
        for i in range(y_pred.shape[1]):
            truth = y_true[:, i]
            pred = y_pred[:, i]

            if np.all(truth == 0.0) or np.all(truth == 1.0):
                results.append(float("nan"))
            else:
                precision, recall, thres = precision_recall_curve(truth, pred)
                score = auc(recall, precision)

                results.append(score)
                precs.append(np.mean(precision))
                recalls.append(np.mean(recall))

        score = np.nanmean(results)
        current_score["best_score"] = score

        return {
            "recall": np.nanmean(recalls),
            "precision": np.nanmean(precs),
            "auc": np.nanmean(results),
            "best_score": current_score["best_score"]}

    def __multiclass_cal(self):
        # accuracy
        acc = accuracy_score(self.labels, self.preds)
        # precision
        micro_precision = precision_score(self.labels, self.preds, average='micro')
        macro_precision = precision_score(self.labels, self.preds, average='macro')
        weighted_precision = precision_score(self.labels, self.preds, average="weighted")
        # recall
        micro_recall = recall_score(self.labels, self.preds, average='micro')
        macro_recall = recall_score(self.labels, self.preds, average='macro')
        weighted_recall = recall_score(self.labels, self.preds, average='weighted')
        # F1_Score
        micro_F1 = f1_score(self.labels, self.preds, average="micro")
        macro_F1 = f1_score(self.labels, self.preds, average="macro")
        weighted_F1 = f1_score(self.labels, self.preds, average="weighted")

        current_score = {"accuracy": acc,
                         "micro_precision": micro_precision,
                         "macro_precision": macro_precision,
                         "weighted_precision": weighted_precision,
                         "micro_recall": micro_recall,
                         "macro_recall": macro_recall,
                         "weighted_recall": weighted_recall,
                         "micro_F1": micro_F1,
                         "macro_F1": macro_F1,
                         "weighted_F1": weighted_F1,
                         "best_score": acc
                         }
        return current_score

    def _dice_score(self):
        scores = []
        threshold, eps = 0.5, 1e-9
        batch_size = self.preds.shape[0]
        predictions = (self.preds >= threshold).float()
        assert (predictions.shape == self.labels.shape)
        for i in range(batch_size):
            prediction = predictions[i]
            truth_ = self.labels[i]
            intersection = 2.0 * (truth_ * prediction).sum()
            union = truth_.sum() + prediction.sum()
            if truth_.sum() == 0 and prediction.sum() == 0:
                scores.append(1.0)
            else:
                scores.append((intersection + eps) / union)
        return sum(scores) / len(scores)

    def __segmentation_cal(self):
        dice = self._dice_score()
        current_score = {"dice": dice, "best_score": dice}
        return current_score

    def cal(self, task_type):
        return self.calfun[task_type]()
