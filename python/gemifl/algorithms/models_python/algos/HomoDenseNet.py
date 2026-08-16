# -*- coding: utf-8 -*-
import numpy as np
import math
import torch
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim
from collections import OrderedDict
from importlib import import_module
import pickle
from gemifl.utils.metrics_module import *

def _bn_function_factory(norm, relu, conv):
    def bn_function(*inputs):
        concated_features = torch.cat(inputs, 1)
        bottleneck_output = conv(relu(norm(concated_features)))
        return bottleneck_output

    return bn_function

class _DenseLayer(nn.Module):
    def __init__(self, num_input_features, growth_rate, bn_size, drop_rate, efficient=False):
        super(_DenseLayer, self).__init__()
        self.add_module('norm1', nn.BatchNorm2d(num_input_features)),
        self.add_module('relu1', nn.ReLU(inplace=True)),
        self.add_module('conv1', nn.Conv2d(num_input_features, bn_size * growth_rate,
                        kernel_size=1, stride=1, bias=False)),
        self.add_module('norm2', nn.BatchNorm2d(bn_size * growth_rate)),
        self.add_module('relu2', nn.ReLU(inplace=True)),
        self.add_module('conv2', nn.Conv2d(bn_size * growth_rate, growth_rate,
                        kernel_size=3, stride=1, padding=1, bias=False)),
        self.drop_rate = drop_rate
        self.efficient = efficient

    def forward(self, *prev_features):
        bn_function = _bn_function_factory(self.norm1, self.relu1, self.conv1)
        if self.efficient and any(prev_feature.requires_grad for prev_feature in prev_features):
            bottleneck_output = torch.utils.checkpoint.checkpoint(bn_function, *prev_features,use_reentrant=False)
        else:
            bottleneck_output = bn_function(*prev_features)
        new_features = self.conv2(self.relu2(self.norm2(bottleneck_output)))
        if self.drop_rate > 0:
            new_features = F.dropout(new_features, p=self.drop_rate, training=self.training)
        return new_features


class _Transition(nn.Sequential):
    def __init__(self, num_input_features, num_output_features):
        super(_Transition, self).__init__()
        self.add_module('norm', nn.BatchNorm2d(num_input_features))
        self.add_module('relu', nn.ReLU(inplace=True))
        self.add_module('conv', nn.Conv2d(num_input_features, num_output_features,
                                          kernel_size=1, stride=1, bias=False))
        self.add_module('pool', nn.AvgPool2d(kernel_size=2, stride=2))


class _DenseBlock(nn.Module):
    def __init__(self, num_layers, num_input_features, bn_size, growth_rate, drop_rate, efficient=False):
        super(_DenseBlock, self).__init__()
        for i in range(num_layers):
            layer = _DenseLayer(
                num_input_features + i * growth_rate,
                growth_rate=growth_rate,
                bn_size=bn_size,
                drop_rate=drop_rate,
                efficient=efficient,
            )
            self.add_module('denselayer%d' % (i + 1), layer)

    def forward(self, init_features):
        features = [init_features]
        for name, layer in self.named_children():
            new_features = layer(*features)
            features.append(new_features)
        return torch.cat(features, 1)


class DenseNet(nn.Module):
    r"""Densenet-BC model class, based on
    `"Densely Connected Convolutional Networks" <https://arxiv.org/pdf/1608.06993.pdf>`
    Args:
        growth_rate (int) - how many filters to add each layer (`k` in paper)
        block_config (list of 3 or 4 ints) - how many layers in each pooling block
        num_init_features (int) - the number of filters to learn in the first convolution layer
        bn_size (int) - multiplicative factor for number of bottle neck layers
            (i.e. bn_size * k features in the bottleneck layer)
        drop_rate (float) - dropout rate after each dense layer
        num_classes (int) - number of classification classes
        small_inputs (bool) - set to True if images are 32x32. Otherwise assumes images are larger.
        efficient (bool) - set to True to use checkpointing. Much more memory efficient, but slower.
    """
    def __init__(self, growth_rate=12, block_config=(16, 16, 16), compression=0.5,
                 num_init_features=24, bn_size=4, drop_rate=0,
                 num_classes=10, small_inputs=True, efficient=False):

        super(DenseNet, self).__init__()
        assert 0 < compression <= 1, 'compression of densenet should be between 0 and 1'

        # First convolution
        if small_inputs:
            self.features = nn.Sequential(OrderedDict([
                ('conv0', nn.Conv2d(3, num_init_features, kernel_size=3, stride=1, padding=1, bias=False)),
            ]))
        else:
            self.features = nn.Sequential(OrderedDict([
                ('conv0', nn.Conv2d(3, num_init_features, kernel_size=7, stride=2, padding=3, bias=False)),
            ]))
            self.features.add_module('norm0', nn.BatchNorm2d(num_init_features))
            self.features.add_module('relu0', nn.ReLU(inplace=True))
            self.features.add_module('pool0', nn.MaxPool2d(kernel_size=3, stride=2, padding=1,
                                                           ceil_mode=False))

        # Each denseblock
        num_features = num_init_features
        for i, num_layers in enumerate(block_config):
            block = _DenseBlock(
                num_layers=num_layers,
                num_input_features=num_features,
                bn_size=bn_size,
                growth_rate=growth_rate,
                drop_rate=drop_rate,
                efficient=efficient,
            )
            self.features.add_module('denseblock%d' % (i + 1), block)
            num_features = num_features + num_layers * growth_rate
            if i != len(block_config) - 1:
                trans = _Transition(num_input_features=num_features,
                                    num_output_features=int(num_features * compression))
                self.features.add_module('transition%d' % (i + 1), trans)
                num_features = int(num_features * compression)

        # Final batch norm
        self.features.add_module('norm_final', nn.BatchNorm2d(num_features))

        # Linear layer
        self.classifier = nn.Linear(num_features, num_classes)

        # Initialization
        for name, param in self.named_parameters():
            if 'conv' in name and 'weight' in name:
                n = param.size(0) * param.size(2) * param.size(3)
                param.data.normal_().mul_(math.sqrt(2. / n))
            elif 'norm' in name and 'weight' in name:
                param.data.fill_(1)
            elif 'norm' in name and 'bias' in name:
                param.data.fill_(0)
            elif 'classifier' in name and 'bias' in name:
                param.data.fill_(0)

    def forward(self, x):
        features = self.features(x)
        out = F.relu(features, inplace=True)
        out = F.adaptive_avg_pool2d(out, (1, 1))
        out = torch.flatten(out, 1)
        out = self.classifier(out)
        return out

class HomoDenseNet:
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
        # for model config
        self.train_batch_size = self.config.get_model_parameter("train_batch_size")
        self.valid_batch_size = self.config.get_model_parameter("valid_batch_size")
        self.model_save = self.config.get_model_parameter("model_save")
        self.weight_save = self.config.get_model_parameter("weight_save")
        # for model evaluation
        self.best_loss = np.inf
        self.best_model_wts = None
        self.bestauc = -1

    def get_data(self):
        model_lib = import_module(f"gemifl.utils.preprocess")
        args = {'traindir':self.train_data_source, 'valdir':self.val_data_source,
                'train_batch_size':self.train_batch_size, 'valid_batch_size':self.valid_batch_size}
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
        net_config = self.config.get_model_parameter("net_config")
        self.model = DenseNet(**net_config)
        self.get_device()
        self.model.to(self.device)
        # specify loss function (categorical cross-entropy loss)
        self.criterion = nn.CrossEntropyLoss()
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
        current_corrects = 0
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
                _, preds = torch.max(outputs, 1)
                loss = self.criterion(outputs, labels)

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

            if task_type in ["multiclass","binary"]:
                _, preds = torch.max(outputs, 1)
                preds = preds.cpu().tolist()
                probs = outputs[:,1].cpu().tolist()
                probs_all += probs
            else:
                preds = outputs[:,0].cpu().tolist()
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
        torch.save(self.model.state_dict(), self.weight_save)

