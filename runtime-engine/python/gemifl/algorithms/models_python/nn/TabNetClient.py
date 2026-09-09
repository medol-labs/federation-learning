import os
import sys
import time
import pickle
import torch
import logging
import numpy as np
import pandas as pd
from sklearn.metrics import roc_auc_score
from pytorch_tabnet.tab_model import TabNetClassifier
from pytorch_tabnet.pretraining import TabNetPretrainer
from gemifl.network.grpc_client import Client
from gemifl.utils.metrics_recorder import MetricRecord
from gemifl.utils.basefunctions import load_data
from gemifl.utils.average_weights import average_params

logging.basicConfig(level=logging.INFO, stream=sys.stdout)


class TabNetClient:

    def __init__(self, config) -> None:
        self.config = config
        self.nodes = self.config["nodes"]
        self.nodes_parameter = self.config["node_parameter"]
        self.roles = self.config["roles"]
        self.model_parameter = self.config["model_parameter"]

        self.my_node_name = list(self.nodes_parameter.keys())[0]
        self.sorted_nodes = sorted(list(self.nodes.keys()))

        # choose one node for aggregation
        self.selected_node = self.sorted_nodes[0]
        self.other_nodes = [
            key for key in self.nodes.keys() if key != self.my_node_name]
        logging.info(
            f"The current role is: {self.my_node_name} and other nodes {self.other_nodes} {self.config}"
        )
        self.client = Client(node_config=self.nodes, my_name=self.my_node_name)

        train_ds = self.nodes_parameter[self.my_node_name]["data_source"]["train_ds"]
        test_ds = self.nodes_parameter[self.my_node_name]["data_source"]["test_ds"]
        cat_idxs = self.model_parameter["cat_idxs"]
        cat_dims = self.model_parameter["cat_dims"]
        self.epochs = self.model_parameter["epochs"]
        self.local_iters = self.model_parameter["local_iters"]

        self.train_loader = load_data(train_ds)
        self.test_loader = load_data(test_ds)

        # set params for tabnet model
        self.tabnet_params = {
            "cat_idxs": cat_idxs,
            "cat_dims": cat_dims,
            "cat_emb_dim": 5,
            "seed": 42,
            "optimizer_fn": torch.optim.Adam,
            "optimizer_params": dict(lr=2e-2),
            "scheduler_params": {
                "step_size": 50,  # how to use learning rate scheduler
                "gamma": 0.9
            },
            "scheduler_fn": torch.optim.lr_scheduler.StepLR,
            "mask_type": 'entmax',  # "sparsemax"
        }
        self.clf = TabNetClassifier(**self.tabnet_params)

    def evaluation(self, test_data, test_y):

        preds = self.clf.predict(test_data)
        probs = self.clf.predict_proba(test_data)

        recall = (preds * test_y).sum() / max(test_y.sum(), 1)
        precision = (preds * test_y).sum() / max(preds.sum(), 1)

        auc = roc_auc_score(test_y, probs[:, 1])

        metrics = {"precision": precision, "auc": auc, "recall": recall}

        return metrics

    def run(self):
        start_time = time.time()

        feature, label = self.train_loader["x"], self.train_loader["y"]
        test_feature, test_label = self.test_loader["x"], self.test_loader["y"]
        test_mets = MetricRecord(metric_list=["auc", "precision", "recall"],
                                 taskId="tabnet",
                                 node_name=self.my_node_name)
        test_metrics = {}

        for n_iter in range(self.epochs):
            try:
                if n_iter == 0:
                    warm_start = False
                else:
                    warm_start = True

                self.clf.fit(X_train=feature,
                             y_train=label,
                             eval_metric=['balanced_accuracy'],
                             max_epochs=self.local_iters,
                             patience=5,
                             batch_size=32,
                             eval_set=[(test_feature, test_label)],
                             eval_name=['valid'],
                             virtual_batch_size=32,
                             num_workers=0,
                             weights=1,
                             warm_start=warm_start)

                #tabnet_net_params = clf.network.state_dict().items()
                tabnet_net_params = self.clf.network.state_dict()

                averaging_net_params = average_params(
                    tabnet_net_params, self.my_node_name, self.client, self.selected_node, n_iter, self.sorted_nodes)

                self.clf.network.load_state_dict(averaging_net_params)
                test_metrics = self.evaluation(test_feature, test_label)
                test_metrics["n_iter"] = n_iter

                status = {
                    "total_iters":
                        self.epochs,
                    "current_iters":
                        n_iter,
                    "time_left":
                        str((time.time() - start_time) * (self.epochs - n_iter - 1) /
                            (n_iter + 1)) + "s",
                    "is_converged":
                        False,
                    "progress_rate":
                        float(n_iter + 1) / self.epochs
                }
                test_metrics["status"] = status
            except Exception as e:
                logging.error(f"The error is {e}")
            finally:
                test_mets.save(test_metrics)
