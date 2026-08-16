import os
import time
import pickle
import logging
import torch
from gemifl.utils.basefunctions import load_data
from gemifl.utils.average_weights import average_params
from gemifl.network.grpc_client import Client
from gemifl.utils.metrics_recorder import MetricRecord
from gemifl.algorithms.models_python.nn._Unet3d_base import UNet3d, Adam, BCEDiceLoss, Meter


class Unet3dClient:

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
        other_nodes = [key for key in self.nodes.keys() if key !=
                       self.my_node_name]
        logging.info(
            f"The current role is: {self.my_node_name} and other nodes {other_nodes}"
        )

        self.epochs = self.model_parameter["epochs"]
        in_channels = self.model_parameter["in_channels"]
        n_classes = self.model_parameter["n_classes"]
        n_channels = self.model_parameter["n_channels"]
        self.learning_rate = self.model_parameter["learning_rate"]

        # load train and test dataset
        self.train_ds = load_data(
            self.nodes_parameter[self.my_node_name]["data_source"]["train_ds"])
        self.test_ds = load_data(
            self.nodes_parameter[self.my_node_name]["data_source"]["train_ds"])

        # initialize gnn model
        self.model = UNet3d(in_channels=in_channels,
                            n_classes=n_classes,
                            n_channels=n_channels)

        #model = model.to(device)
        self.meter = Meter()

        # set msg transfer channel
        self.client = Client(node_config=self.nodes, my_name=self.my_node_name)

    def train(self, optimizer, device, criterion):
        self.model.train()
        current_loss = 0.0
        sample_num = len(self.train_ds)

        # start training
        for _, data_batch in enumerate(self.train_ds):
            image, target = data_batch[0], data_batch[1]
            optimizer.zero_grad()

            target = target.to(device=device)
            image = image.type(torch.float32).to(device)

            logit = self.model(image)
            loss = criterion(logit, target)

            loss.backward()
            optimizer.step()

            current_loss += loss.item()
            self.meter.update(logit.detach().cpu(), target.detach().cpu())

        return current_loss, sample_num

    def test_score(self, current_metrics):
        test_loss = 0.0
        criterion = BCEDiceLoss()
        meter = Meter()
        self.model.eval()

        with torch.no_grad():
            for _, data_batch in enumerate(self.test_ds):
                image, target = data_batch[0], data_batch[1]
                logit = self.model(image)
                loss = criterion(logit, target)
                test_loss += loss
                meter.update(logit.detach().cpu(), target.detach().cpu())

        test_loss /= len(self.test_ds)
        test_dice, test_iou, test_sen, test_spf = meter.get_metrics()

        if test_loss < current_metrics["loss"]:
            current_metrics["loss"] = test_loss
            current_metrics["dice"] = test_dice
            current_metrics["iou"] = test_iou
            current_metrics["sen"] = test_sen
            current_metrics["spf"] = test_spf

        return {
            "loss": test_loss,
            "dice": test_dice,
            "iou": test_iou,
            "sen": test_sen,
            "spf": test_spf
        }

    def run(self):
        start_time = time.time()
        os.environ['NUMEXPR_MAX_THREADS'] = '40'
        criterion = BCEDiceLoss()
        optimizer = Adam(self.model.parameters(), lr=self.learning_rate)
        device = 'cuda' if torch.cuda.is_available() else 'cpu'

        device = 'cpu'

        losses = []
        dice_scores = []
        jaccard_scores = []
        sen_scores = []
        spf_scores = []

        current_metrics = {
            "loss": float("inf"),
            "dice": 0,
            "iou": 0,
            "sen": 0,
            "spf": 0
        }
        test_mets = MetricRecord(metric_list=["loss", "dice", "iou"],
                                 taskId="unet3d",
                                 node_name=self.my_node_name)

        for n_iter in range(self.epochs):
            # TODO: check dice and iou results
            try:
                current_loss, sample_num = self.train(
                    optimizer, device, criterion)

                epoch_loss = current_loss / sample_num
                epoch_dice, epoch_iou, epoch_sen, epoch_spf = self.meter.get_metrics(
                )

                losses.append(epoch_loss)
                dice_scores.append(epoch_dice)
                jaccard_scores.append(epoch_iou)
                sen_scores.append(epoch_sen)
                spf_scores.append(epoch_spf)

                local_model_weights = self.model.state_dict()

                # send local model weights to selected node
                averaging_net_params = average_params(
                    local_model_weights, self.my_node_name, self.client, self.selected_node, n_iter, self.sorted_nodes)

                self.model.load_state_dict(averaging_net_params)

                # evaluation for test data
                current_metrics = self.test_score(current_metrics)
                current_metrics["n_iter"] = n_iter
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
                current_metrics["status"] = status
            except Exception as e:
                logging.error(f"The error is {e}")
            finally:
                test_mets.save(current_metrics)
