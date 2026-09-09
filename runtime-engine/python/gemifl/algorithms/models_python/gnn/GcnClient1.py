import os
import sys
import torch
import time
import pickle
import logging
import numpy as np
from gemifl.utils.metrics_recorder import MetricRecord
from gemifl.utils.seq2seq import seq2seq_avg
from sklearn.metrics import precision_recall_curve, auc
from gemifl.algorithms.models_python.model_base import ModelBase
from gemifl.algorithms.models_python.models._gcn_base import GcnMoleculeNet
from gemifl.network.grpc_client import Client


def _test_score(model, test_loader, current_score=0):
    y_pred = []
    y_true = []
    masks = []

    model.eval()
    with torch.no_grad():
        for tmp_adj_1, tmp_feature_1, tmp_label_1 in zip(
                test_loader["adj"], test_loader["x"], test_loader["y"]):
            tmp_adj_1 = torch.tensor(tmp_adj_1.todense(), dtype=torch.float32)
            tmp_feature_1 = torch.tensor(tmp_feature_1, dtype=torch.float32)

            test_logits = model(tmp_adj_1, tmp_feature_1)

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


class GcnClient(ModelBase):

    def __init__(self, config) -> None:
        super().__init__(config)

    def set_inputs(self) -> None:
        self.my_node_name = list(self.nodes_parameter.keys())[0]
        self.sorted_nodes = sorted(list(self.nodes.keys()))

        # choose one node for aggregation
        self.selected_node = self.sorted_nodes[0]
        self.other_nodes = [
            key for key in self.nodes.keys() if key != self.my_node_name
        ]
        logging.info(
            f"The current role is: {self.my_node_name} and other nodes {self.other_nodes}"
        )

        # set msg transfer channel
        self.client = Client(node_config=self.nodes, my_name=self.my_node_name)

        # get parameters for model structure
        self.epochs = self.model_parameter["epochs"]
        self.feature_dim = self.model_parameter["feature_dim"]
        self.num_cats = self.model_parameter["num_cats"]
        self.local_iters = self.model_parameter["local_iters"]

        # set model params
        self.hidden_dim = self.model_parameter["hidden_dim"]
        self.embedding_dim = self.model_parameter["embedding_dim"]
        self.drop = self.model_parameter["drop"]
        self.readout_hidden_dim = self.model_parameter["readout_hidden_dim"]
        self.graph_embedding_dim = self.model_parameter["graph_embedding_dim"]
        self.learning_rate = self.model_parameter["learning_rate"]

        # load data for model training
        train_ds = self.nodes_parameter[
            self.my_node_name]["data_source"]["train_ds"]
        if os.path.exists(train_ds):
            with open(train_ds, "rb") as f:
                self.train_dataloader = pickle.load(f)
        else:
            raise ValueError("Train file does not exist!")

        # load data for model evaluation
        test_ds = self.nodes_parameter[
            self.my_node_name]["data_source"]["train_ds"]
        if os.path.exists(test_ds):
            with open(test_ds, "rb") as f:
                self.test_dataloader = pickle.load(f)

        else:
            raise ValueError("Test file does not exit!")

    def run(self) -> None:
        # initialize model
        start_time = time.time()
        self.set_inputs()
        model = GcnMoleculeNet(feat_dim=self.feature_dim,
                               hidden_dim=self.hidden_dim,
                               node_embedding_dim=self.embedding_dim,
                               readout_hidden_dim=self.readout_hidden_dim,
                               graph_embedding_dim=self.graph_embedding_dim,
                               num_categories=self.num_cats,
                               dropout=self.drop)

        #optimizer = torch.optim.SGD(model.parameters(), lr=learning_rate)
        optimizer = torch.optim.Adam(model.parameters(), lr=self.learning_rate)
        criterion = torch.nn.BCEWithLogitsLoss(reduction="none")

        test_mets = MetricRecord(
            metric_list=["auc", "precision", "recall", "best_score"],
            taskId="gnn",
            node_name=self.my_node_name)
        current_score = {}
        current_score["best_score"] = 0

        for n_iter in range(self.epochs):
            try:
                # locally training
                model.train()
                for _ in range(self.local_iters):
                    for tmp_feature, tmp_label, tmp_adj in zip(
                            self.train_dataloader["x"],
                            self.train_dataloader["y"],
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

                        logits = model(tmp_adj, tmp_feature)
                        loss = criterion(logits, tmp_label)
                        loss = loss.mean()

                        loss.backward()
                        optimizer.step()

                # averaging model weights with seq2seq method
                averaging_net_params = seq2seq_avg(
                    model=model,
                    client=self.client,
                    my_node_name=self.my_node_name,
                    current_epoch=n_iter,
                    selected_node=self.selected_node,
                    sorted_nodes=self.sorted_nodes)

                model.load_state_dict(averaging_net_params)

                # evaluation for test data
                current_score = _test_score(model, self.test_dataloader,
                                            current_score)
                logging.info(
                    f"current {n_iter} and the best test score is {current_score} "
                )
                current_score["n_iter"] = n_iter

                # set current status
                status = {
                    "total_iters":
                        self.epochs,
                    "current_iters":
                        n_iter,
                    "time_left":
                        str((time.time() - start_time) *
                            (self.epochs - n_iter - 1) / (n_iter + 1)) + "s",
                    "is_converged":
                        False,
                    "progress_rate":
                        float(n_iter + 1) / self.epochs
                }
                current_score["status"] = status

            except Exception as e:
                logging.error(f"The error is {e}")

            finally:
                # save metric results
                test_mets.save(current_score)