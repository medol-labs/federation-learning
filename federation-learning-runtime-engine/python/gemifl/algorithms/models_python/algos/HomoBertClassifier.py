# -*- coding: utf-8 -*-
import numpy as np
import math
import torch
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim
from collections import OrderedDict
from transformers import *
from importlib import import_module
import pickle
import copy
from gemifl.utils.metrics_module import *


class BertClassifier(nn.Module):
    """Bert Model for Classification Tasks.
    """
    def __init__(self, model_path, n_classes=2, freeze_bert=False):
        """
        @param    bert: a BertModel object
        @param    classifier: a torch.nn.Module classifier
        @param    freeze_bert (bool): Set `False` to fine-tune the BERT model
        """
        super(BertClassifier, self).__init__()

        # Instantiate BERT model
        self.bert = BertModel.from_pretrained(model_path)

        # Instantiate an one-layer feed-forward classifier
        self.classifier=nn.Linear(self.bert.config.hidden_size,n_classes)

        # Freeze the BERT model
        if freeze_bert:
            for param in self.bert.parameters():
                param.requires_grad = False

    def forward(self, input_ids, attention_mask):
        """
        Feed input to BERT and the classifier to compute logits.
        @param    input_ids (torch.Tensor): an input tensor with shape (batch_size,
                      max_length)
        @param    attention_mask (torch.Tensor): a tensor that hold attention mask
                      information with shape (batch_size, max_length)
        @return   logits (torch.Tensor): an output tensor with shape (batch_size,
                      num_labels)
        """
        # Feed input to BERT
        outputs = self.bert(input_ids=input_ids,
                            attention_mask=attention_mask)

        # Feed input to classifier to compute logits
        logits = self.classifier(outputs.pooler_output)

        return logits

class HomoBertClassifier:
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
        self.train_data_source, self.val_data_source = self.config.get_data_setting(self.my_node_name)
        self.dataargs = self.config.get_model_parameter("data_config", {})
        # for model config
        self.train_batch_size = self.config.get_model_parameter("train_batch_size")
        self.valid_batch_size = self.config.get_model_parameter("valid_batch_size")
        self.num_class = self.config.get_model_parameter("num_class")
        self.pretrain = self.config.get_model_parameter("pretrain")
        self.model_save = self.config.get_model_parameter("model_save")
        self.weight_save = self.config.get_model_parameter("weight_save")
        self.epochs = self.config.get_model_parameter("epochs",1)
        # for model evaluation
        self.best_loss = np.inf
        self.best_model_wts = None
        self.bestauc = -1

    def get_data(self):
        model_lib = import_module(f"gemifl.utils.preprocess")

        args = {'traindir': self.train_data_source, 'valdir': self.val_data_source,
                'train_batch_size': self.train_batch_size, 'valid_batch_size':self.valid_batch_size,
                "pretrain": self.pretrain,"max_length": self.dataargs["max_length"],
                "text_col": self.dataargs["text_col"],"label_col": self.dataargs["label_col"]}

        for k in self.dataargs:
            v = self.dataargs[k]
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
        #net_config = self.config.get_model_parameter("net_config")
        net_config = {"n_classes":self.num_class, "freeze_bert":False, "model_path":self.pretrain}

        self.model = BertClassifier(**net_config)
        self.get_device()
        self.model.to(self.device)
        # specify loss function
        self.criterion = nn.BCELoss(weight=None, size_average=True)
        # Specify optimizer which performs Gradient Descent
        self.optimizer = optim.AdamW(self.model.parameters(), lr=self.config.get_model_parameter("lr"), eps=1e-8)
        # Decay LR by a factor of 0.1 every 7 epochs
        # Total number of training steps
        try:
            total_steps = self.data_sizes["train"] * self.epochs
        except:
            total_steps = 1

        # Set up the learning rate scheduler
        self.scheduler = get_linear_schedule_with_warmup(self.optimizer,
                                                    num_warmup_steps=0,  # Default value
                                                    num_training_steps=total_steps)
        #self.exp_lr_scheduler = optim.lr_scheduler.StepLR(self.optimizer, step_size=7, gamma=0.1)

    def get_weights(self):
        weight_dict = {}

        for name, weightdata in self.model.state_dict().items():
            if "classifier" in name:
                data_arr = weightdata.cpu().numpy()
                data_arr_flatten = data_arr.flatten()
                data_arr_np = np.array([float(i) for i in data_arr_flatten])
                weight_dict[name] = [data_arr_np, data_arr.shape]
        return weight_dict

    def set_weights(self, parameters):
        weight_input = copy.deepcopy(self.model.state_dict())

        for name in parameters:
            weight_input[name] = torch.tensor(np.reshape(parameters[name][0], parameters[name][1]))

        self.model.load_state_dict(weight_input)

    def set_best_weight(self):
        if self.best_model_wts:
            self.set_weights(self.best_model_wts)

    def train_one_epoch(self):
        self.model.train()
        current_loss = 0.0
        current_corrects = 0
        phase = "train"
        for b_input_ids, b_attn_mask, labels in self.dataloaders[phase]:
            inputs = b_input_ids.to(self.device)
            attn_mask = b_attn_mask.to(self.device)
            labels = labels.to(self.device)

            # We need to zero the gradients in the Cache.
            self.model.zero_grad()
            self.optimizer.zero_grad()

            # Time to carry out the forward training poss
            # We only need to log the loss stats if we are in training phase
            with torch.set_grad_enabled(phase == 'train'):
                logits = self.model(inputs, attn_mask)
                output_sig = torch.sigmoid(logits)
                # Compute loss and accumulate the loss values
                loss = self.criterion(output_sig, labels)
                # backward + optimize only if in training phase
                if phase == 'train':
                    loss.backward()
                    self.optimizer.step()
            if phase == 'train':
                self.scheduler.step()
            torch.nn.utils.clip_grad_norm_(self.model.parameters(), 1.0)

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

        with torch.no_grad():
            for b_input_ids, b_attn_mask, labels in self.dataloaders[phase]:
                inputs = b_input_ids.to(self.device)
                attn_mask = b_attn_mask.to(self.device)
                labels = labels.to(self.device)
                outputs = self.model(inputs, attn_mask)

                if task_type in ["multiclass", "binary","multiclass_onehot"]:
                    _, preds = torch.max(outputs, 1)
                    preds = preds.cpu().tolist()
                    probs = outputs[:, 1].cpu().tolist()
                    probs_all += probs
                else:
                    preds = outputs[:, 0].cpu().tolist()
                    probs = None

                if task_type in ["multiclass_onehot"]:
                    _, labels = torch.max(labels, 1)
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
        torch.save(self.model.state_dict(), self.weight_save)
