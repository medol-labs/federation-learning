import os
import sys
import time
import torch
import pickle
import numpy as np
import logging
from gemifl.utils.basefunctions import load_data
from gemifl.network.grpc_client import Client
from gemifl.utils.average_weights import average_params
from sklearn.metrics import precision_recall_curve, auc
from gemifl.utils.metrics_recorder import MetricRecord
from gemifl.algorithms.models_python.gnn._gcn_base import GcnMoleculeNet

logging.basicConfig(level=logging.INFO, stream=sys.stdout)


class GcnClient:

    def __init__(self, config) -> None:
        self.config = config
        self.nodes = self.config["nodes"]
        self.nodes_parameter = self.config["node_parameter"]
        self.roles = self.config["roles"]
        self.model_parameter = self.config["model_parameter"]

        # get parameters for model structure
        feature_dim = self.model_parameter["feature_dim"]
        num_cats = self.model_parameter["num_cats"]

        # set model params
        hidden_dim = self.model_parameter["hidden_dim"]
        embedding_dim = self.model_parameter["embedding_dim"]
        drop = self.model_parameter["drop"]
        readout_hidden_dim = self.model_parameter["readout_hidden_dim"]
        graph_embedding_dim = self.model_parameter["graph_embedding_dim"]
        self.model = GcnMoleculeNet(feat_dim=feature_dim,
                                    hidden_dim=hidden_dim,
                                    node_embedding_dim=embedding_dim,
                                    readout_hidden_dim=readout_hidden_dim,
                                    graph_embedding_dim=graph_embedding_dim,
                                    num_categories=num_cats,
                                    dropout=drop)

        self.my_node_name = self.config["my_name"]
        self.sorted_nodes = sorted(list(self.nodes.keys()))

        # choose one node for aggregation
        self.selected_node = self.sorted_nodes[0]
        other_nodes = [key for key in self.nodes.keys() if key !=
                       self.my_node_name]
        logging.info(
            f"The current role is: {self.my_node_name} and other nodes {other_nodes}"
        )
        self.epochs = self.model_parameter["epochs"]
        self.local_iters = self.model_parameter["local_iters"]

        # set msg transfer channel
        self.client = Client(node_config=self.nodes, my_name=self.my_node_name)
        self.learning_rate = self.model_parameter["learning_rate"]

        # load data for model training
        self.train_dataloader = load_data(
            self.nodes_parameter[self.my_node_name]["data_source"]["train_ds"])

        # load data for model evaluation
        self.test_dataloader = load_data(
            self.nodes_parameter[self.my_node_name]["data_source"]["train_ds"])

    def train(self, optimizer, criterion):
        self.model.train()
        for _ in range(self.local_iters):
            for tmp_feature, tmp_label, tmp_adj in zip(
                    self.train_dataloader["x"], self.train_dataloader["y"],
                    self.train_dataloader["adj"]):
                optimizer.zero_grad()

                # convert to tensor
                tmp_adj = torch.tensor(tmp_adj.todense(),
                                       dtype=torch.float32)
                tmp_feature = torch.tensor(tmp_feature,
                                           dtype=torch.float32)

                tmp_label = torch.tensor(tmp_label)
                tmp_label = tmp_label.to(dtype=torch.float32,
                                         non_blocking=True)

                logits = self.model(tmp_adj, tmp_feature)
                loss = criterion(logits, tmp_label)
                loss = loss.mean()

                loss.backward()
                optimizer.step()

    def test_score(self, current_score=0):
        y_pred = []
        y_true = []
        masks = []

        self.model.eval()
        with torch.no_grad():
            for tmp_adj_1, tmp_feature_1, tmp_label_1 in zip(
                    self.test_dataloader["adj"], self.test_dataloader["x"], self.test_dataloader["y"]):
                tmp_adj_1 = torch.tensor(
                    tmp_adj_1.todense(), dtype=torch.float32)
                tmp_feature_1 = torch.tensor(
                    tmp_feature_1, dtype=torch.float32)

                test_logits = self.model(tmp_adj_1, tmp_feature_1)

                y_pred.append(test_logits.detach().numpy())
                y_true.append(tmp_label_1)

        y_pred = np.array(y_pred)
        y_true = np.array(y_true)

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

        if score > current_score["best_score"]:
            current_score["best_score"] = score

        # return current_score
        return {
            "recall": np.nanmean(recalls),
            "precision": np.nanmean(precs),
            "auc": np.nanmean(results),
            "best_score": current_score["best_score"]
        }

    def run(self):
        start_time = time.time()
        #optimizer = torch.optim.SGD(model.parameters(), lr=learning_rate)
        optimizer = torch.optim.Adam(
            self.model.parameters(), lr=self.learning_rate)
        criterion = torch.nn.BCEWithLogitsLoss(reduction="none")

        # test_mets = MetricRecord(
        #     metric_list=["auc", "precision", "recall", "best_score"],
        #     taskId="gnn",
        #     node_name=self.my_node_name)
        current_score = {}
        current_score["best_score"] = 0

        for n_iter in range(self.epochs):
            try:
                # locally training
                self.train(optimizer, criterion)

                local_model_weights = self.model.state_dict()

                averaging_net_params = average_params(local_model_weights, my_node_name=self.my_node_name, grpc_client=self.client,
                                                      selected_node=self.selected_node, n_iter=n_iter, sorted_nodes=self.sorted_nodes)

                self.model.load_state_dict(averaging_net_params)

                # evaluation for test data
                current_score = self.test_score(current_score)
                logging.info(
                    f"current {n_iter} and the best test score is {current_score} "
                )
                current_score["n_iter"] = n_iter
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
                current_score["status"] = status

            except Exception as e:
                logging.error(f"The error is {e}")

            finally:
                # test_mets.save(current_score)
                print("current_score: ", current_score)
