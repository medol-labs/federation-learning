import os
import pickle
import numpy as np
import torch
from transformers import AdamW
from gemifl.utils.base_modules import Tokenizer, DataHandler, ModelLoad
from gemifl.utils.metrics_module import *


class HomoClinicalBert:
    def __init__(self, config, logger):
        self.config = config
        self.logger = logger
        self.load_default_variable()
        if self.train_data_source:
            self.get_data()
        self.init_model()

    def load_default_variable(self):
        self.my_node_name = self.config.get_root_value("my_name")
        # for data
        self.data_type = self.config.get_model_parameter("data_type")
        self.train_data_source, self.val_data_source = self.config.get_data_setting(
            self.my_node_name)
        # for model config
        self.train_batch_size = self.config.get_model_parameter(
            "train_batch_size")
        self.valid_batch_size = self.config.get_model_parameter(
            "valid_batch_size")
        self.model_save = self.config.get_model_parameter("model_save")
        self.weight_save = self.config.get_model_parameter("weight_save")
        # for model evaluation
        self.best_loss = np.inf
        self.best_model_wts = None
        self.bestauc = -1

    def get_device(self):
        nodes_parameter = self.config.get_nodes_parameter(self.my_node_name)
        userdevice = nodes_parameter.get("device", "cpu").lower()

        if torch.cuda.is_available() and userdevice != 'cpu':
            try:
                self.device = torch.device(userdevice)
            except:
                self.device = torch.device("cuda:0")
            self.logger.info("Training on GPU...")
        else:
            self.device = torch.device("cpu")
            self.logger.info("Training on CPU...")

    def get_weights(self):
        weight_dict = {}

        for name, data in self.model.state_dict().items():
            data_arr = data.cpu().numpy()
            data_arr_flatten = data_arr.flatten()
            data_arr_np = np.array([float(i) for i in data_arr_flatten])
            weight_dict[name] = [data_arr_np, data_arr.shape]
        return weight_dict

    def set_weights(self, parameters):
        weight_input = {}

        for name, data in parameters.items():
            weight_input[name] = torch.tensor(np.reshape(
                parameters[name][0], parameters[name][1]))

        self.model.load_state_dict(weight_input)

    def set_best_weight(self):
        if self.best_model_wts:
            self.set_weights(self.best_model_wts)

    def init_model(self):
        self.model = ModelLoad(self.config.get_model_parameter("load_model_path"),
                               self.config.get_model_parameter("num_labels")).model

        self.get_device()

    def get_data(self):
        self.tokenizer = Tokenizer(
            self.config.get_model_parameter("load_token_path"))
        self.data_loader = DataHandler(
            self.config.get_nodes_parameter(self.my_node_name)["data_source"], self.tokenizer, batch_size=16)

    def train_one_epoch(self):
        self.model.to(self.device)
        optimizer = AdamW(self.model.parameters(), lr=1e-5, eps=1e-8)
        self.get_data()

        self.model.train()
        for step, tmp_batch in enumerate(self.data_loader.train_loader):
            tmp_ids = tmp_batch[0].to(self.device)
            tmp_mask = tmp_batch[1].to(self.device)
            tmp_label = tmp_batch[2].to(self.device)

            # Clear any previously calculated gradients
            self.model.zero_grad()

            outputs = self.model(tmp_ids,
                                 token_type_ids=None,
                                 attention_mask=tmp_mask,
                                 labels=tmp_label)

            loss = outputs[0]
            loss.backward()

            optimizer.step()

    def evaluate_bak(self, task_type):
        self.model.eval()
        labels_all = []
        preds_all = []
        probs_all = []
        with torch.no_grad():
            for step, tmp_batch in enumerate(self.data_loader.test_loader):
                tmp_ids = tmp_batch[0].to(self.device)
                tmp_mask = tmp_batch[1].to(self.device)
                tmp_label = tmp_batch[2].to(self.device)

                outputs = self.model(tmp_ids, tmp_mask).to(self.device)

                _, preds = torch.max(outputs, 1)

                if task_type in ["multiclass","binary_class"]:
                    _, preds = torch.max(outputs, 1)
                    preds = preds.cpu().tolist()
                    probs = outputs[:,1].cpu().tolist()
                    probs_all += probs
                else:
                    preds = outputs[:,0].cpu().tolist()
                    probs = None
                labels_list = tmp_label.data.cpu().tolist()
                labels_all += labels_list
                preds_all += preds

        metricsobj = MLMetrics(labels_all,preds_all,probs_all)
        current_score = metricsobj.cal(task_type)
        if current_score["best_score"] > self.bestauc:
            self.bestauc = current_score["best_score"]
        else:
            current_score["best_score"] = self.bestauc
        return current_score

    def evaluate(self, task_type):
        self.model.eval()

        labels_all = []
        preds_all = []
        probs_all = []
        self.bestauc = 0
        # current_score["best_score"] = 0
        with torch.no_grad():
            for step, tmp_batch in enumerate(self.data_loader.test_loader):
                tmp_ids = tmp_batch[0].to(self.device)
                tmp_mask = tmp_batch[1].to(self.device)
                tmp_label = tmp_batch[2].to(self.device)

                outputs = self.model(tmp_ids, tmp_mask)

                if task_type in ["multiclass", "binary"]:
                    _, preds = torch.max(outputs[0], 1)
                    preds = preds.cpu().tolist()
                    probs = outputs[0][:, 1].cpu().tolist()
                    probs_all += probs
                else:
                    preds = outputs[0][:, 0].cpu().tolist()
                    probs = None
                labels_list = tmp_label.data.cpu().tolist()
                labels_all += labels_list
                preds_all += preds

            metricsobj = MLMetrics(labels_all, preds_all, probs_all)
            current_score = metricsobj.cal(task_type)
            if current_score["best_score"] > self.bestauc:
                self.bestauc = current_score["best_score"]
            else:
                current_score["best_score"] = self.bestauc
        return current_score

    def dumpfile(self):
        with open(self.model_save, 'wb') as mf:
            pickle.dump(self.model, mf)
        torch.save(self.model.state_dict(), self.weight_save)
