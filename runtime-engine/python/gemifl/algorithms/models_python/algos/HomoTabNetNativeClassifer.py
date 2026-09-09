# -*- coding: utf-8 -*-

from gemifl.algorithms.models_python.algos.tab_network import *
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim
from importlib import import_module
import pickle
from numpy import random
from gemifl.utils.metrics_module import *

class HomoTabNetNativeClassifer:
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
        if self.data_type in ("CsvParser", 'CsvGenerator'):
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
        trainloader = preprocessor.get_dataloader("train")
        valloader = preprocessor.get_dataloader("val")
        self.dataloaders = {"train": trainloader, "val": valloader}
        self.data_sizes = {x: len(self.dataloaders[x].sampler) for x in ['train', 'val']}

    def get_device(self):
        nodes_parameter = self.config.get_nodes_parameter(self.my_node_name)
        userdevice = nodes_parameter.get("device","cpu").lower()

        if torch.cuda.is_available() and userdevice!='cpu':
            try:
                self.device = torch.device(userdevice)
            except:
                self.device = torch.device("cuda:0")
            self.logger.info("Training on GPU...")
        else:
            self.device = torch.device("cpu")
            self.logger.info("Training on CPU...")

    def init_model(self):
        self.get_device()
        net_config = self.config.get_model_parameter("net_config")
        input_dim = net_config["input_dim"]

        grouped_features_list = []
        for i in range(input_dim):
            templist = [0.0 for i in range(input_dim)]
            templist[i] = 1.0
            grouped_features_list.append(templist)
        grouped_features = torch.tensor(grouped_features_list).to(self.device)
        net_config["group_attention_matrix"] = grouped_features
        self.model = TabNet(**net_config).to(self.device)
        # specify loss function (categorical cross-entropy loss)
        self.criterion = F.cross_entropy
        # Specify optimizer which performs Gradient Descent
        self.optimizer = optim.Adam(self.model.parameters(), lr=self.config.get_model_parameter("lr"))
        # Decay LR by a factor of 0.1 every 7 epochs
        self.exp_lr_scheduler = optim.lr_scheduler.StepLR(self.optimizer, step_size=7, gamma=0.1)

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
            weight_input[name] = torch.tensor(np.reshape(parameters[name][0], parameters[name][1]))

        self.model.load_state_dict(weight_input)

    def set_best_weight(self):
        if self.best_model_wts:
            self.set_weights(self.best_model_wts)

    def train_one_epoch(self):
        self.model.train()
        current_loss = 0.0
        phase = "train"
        for inputs, labels in self.dataloaders[phase]:
            inputs = inputs.to(self.device)
            labels = labels.to(self.device)

            # We need to zero the gradients in the Cache.
            self.optimizer.zero_grad()

            # Time to carry out the forward training poss
            # We only need to log the loss stats if we are in training phase
            with torch.set_grad_enabled(phase == 'train'):
                outputs = self.model(inputs)
                _, preds = torch.max(outputs[0], 1)
                loss = self.criterion(outputs[0].to(self.device), labels.type(torch.LongTensor).to(self.device))

                # backward + optimize only if in training phase
                if phase == 'train':
                    loss.backward()
                    self.optimizer.step()
            if phase == 'train':
                self.exp_lr_scheduler.step()

            # We want variables to hold the loss statistics
            current_loss += loss.item() * inputs.size(0)

        self.__train_eval(current_loss)

    def __train_eval(self, current_loss):
        epoch_loss = current_loss / self.data_sizes["train"]
        #epoch_acc = current_corrects.double() / self.data_sizes[phase]
        if epoch_loss < self.best_loss:
            self.best_loss = epoch_loss
            self.best_model_wts = self.get_weights()

    def evaluate(self, task_type):
        self.model.eval()
        phase = "val"
        labels_all = []
        preds_all = []
        probs_all = []
        for inputs, labels in self.dataloaders[phase]:
            inputs = inputs.to(self.device)
            labels = labels.to(self.device)
            outputs = self.model(inputs)
            #loss = self.criterion(outputs, labels)
            if task_type in ["multiclass","binary"]:
                _, preds = torch.max(outputs[0], 1)
                preds = preds.cpu().tolist()
                probs = outputs[0][:,1].cpu().tolist()
                probs_all += probs
            else:
                preds = outputs[0][:,0].cpu().tolist()
                probs = None
            labels_list = labels.data.cpu().tolist()
            labels_all += labels_list
            preds_all += preds

        metricsobj = MLMetrics(labels_all,preds_all,probs_all)
        current_score = metricsobj.cal(task_type)
        if current_score["best_score"] > self.bestauc:
            self.bestauc = current_score["best_score"]
        else:
            current_score["best_score"] = self.bestauc

        return current_score

    def dumpfile(self):
        with open(self.model_save, 'wb') as mf:
            pickle.dump(self.model, mf)
        torch.save(self.model.state_dict(), self.model_save+".weight")