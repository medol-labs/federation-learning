# -*- coding: utf-8 -*-

from gemifl.algorithms.models_python.algos.tab_network import *
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim
from importlib import import_module
from pytorch_tabnet.tab_model import TabNetClassifier
import pickle
from numpy import random
from gemifl.utils.metrics_module import *

class HomoTabNetClassifer:
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
        self.dataargs = {}
        self.data_type = self.config.get_model_parameter("data_type")
        self.train_data_source, self.val_data_source = self.config.get_data_setting(self.my_node_name)
        if self.data_type == "CsvParser":
            self.dataargs = self.config.get_model_parameter("csv_config",{})

        # for model config
        self.train_batch_size = self.config.get_model_parameter("train_batch_size")
        self.valid_batch_size = self.config.get_model_parameter("valid_batch_size")
        self.model_save = self.config.get_model_parameter("model_save")
        self.weight_save = self.config.get_model_parameter("weight_save")
        self.task_type = self.config.get_model_parameter("task_type")
        self.lr = self.config.get_model_parameter("lr")

        # for model evaluation
        self.best_loss = np.inf
        self.best_model_wts = None
        self.bestauc = -1
        self.warm_start = False

    def get_data(self):
        model_lib = import_module(f"gemifl.utils.preprocess")
        args = {'traindir':self.train_data_source, 'valdir':self.val_data_source,
                'train_batch_size':self.train_batch_size, 'valid_batch_size':self.valid_batch_size}
        for k in self.dataargs:
            v = self.dataargs[k]
            if v.lower() == "none":
                v = None
            args[k] = v
        preprocessor = getattr(model_lib, self.data_type)(**args)
        train_x, train_y = preprocessor.get_dataloader("train")
        valid_x, valid_y = preprocessor.get_dataloader("val")
        self.dataloaders = {"train": (train_x, train_y), "val": (valid_x, valid_y)}
        self.data_sizes = {"train":len(train_x), "val":len(valid_x)}

    def get_device(self):
        nodes_parameter = self.config.get_nodes_parameter(self.my_node_name)
        return nodes_parameter.get("device","cpu").lower()

    def init_model(self):
        device = self.get_device()
        net_config = self.config.get_model_parameter("net_config")
        net_config["device_name"] = device
        # Specify optimizer which performs Gradient Descent
        net_config["optimizer_fn"] = optim.Adam
        net_config["optimizer_params"] = {"lr":self.lr}
        # Decay LR
        net_config["scheduler_fn"] = optim.lr_scheduler.StepLR
        net_config["scheduler_params"] = {
            "step_size": 50,  # how to use learning rate scheduler
            "gamma": 0.9
        }
        self.model = TabNetClassifier(**net_config)
        input_dim = net_config.get("input_dim")
        output_dim = net_config.get("output_dim")
        x = random.randn(input_dim,output_dim)
        y = np.array([i for i in range(output_dim)])
        self.model.fit(x,y,max_epochs=1)

    def get_weights(self):
        weight_dict = {}
        for name, data in self.model.network.state_dict().items():
            data_arr = data.cpu().numpy()
            data_arr_flatten = data_arr.flatten()
            data_arr_np = np.array([float(i) for i in data_arr_flatten])
            weight_dict[name] = [data_arr_np, data_arr.shape]
        return weight_dict

    def set_weights(self, parameters):
        weight_input = {}

        for name, data in parameters.items():
            weight_input[name] = torch.tensor(np.reshape(parameters[name][0], parameters[name][1]))

        self.model.network.load_state_dict(weight_input)

    def set_best_weight(self):
        if self.best_model_wts:
            self.set_weights(self.best_model_wts)

    def train_one_epoch(self):
        train_x, train_y = self.dataloaders["train"]
        if self.best_model_wts:
            self.warm_start = True

        self.model.fit(train_x, train_y,
                       eval_metric=['balanced_accuracy'],
                       batch_size=self.train_batch_size,
                       warm_start=self.warm_start,
                       max_epochs=1)

        current_loss = np.mean(self.model.history['loss'])

        self.__train_eval(current_loss)

    def __train_eval(self, current_loss):
        epoch_loss = current_loss / self.data_sizes["train"]
        #epoch_acc = current_corrects.double() / self.data_sizes[phase]
        if epoch_loss < self.best_loss:
            self.best_loss = epoch_loss
            self.best_model_wts = self.get_weights()

    def evaluate(self, task_type):
        valid_x, valid_y = self.dataloaders["val"]
        probs_all = self.model.predict_proba(valid_x)[:,1]
        preds_all = self.model.predict(valid_x)

        metricsobj = MLMetrics(valid_y,preds_all,probs_all)
        current_score = metricsobj.cal(task_type)
        if current_score["best_score"] > self.bestauc:
            self.bestauc = current_score["best_score"]
        else:
            current_score["best_score"] = self.bestauc
        return current_score

    def dumpfile(self):
        self.model.save_model(self.model_save)