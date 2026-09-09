# -*- coding: utf-8 -*-
import torch

import numpy as np
import pickle
import copy
import os
import sys
import time
from importlib import import_module
from gemifl.utils.log import *
from gemifl.utils.config_parser import *
from gemifl.utils.default_values import *
from gemifl.phe_utils.choose_key_pair import *
from gemifl.algorithms.models_python.aggregator.Aggregator import *
import shutil
from gemifl.network.grpc_client import Client

class HorizontalServer:

    def __init__(self, config):
        self.config = ConfigParser(config)
        self.load_config()
        self.aggregator = FedAvg(self.logger, self.public_key, self.paillier_workers)

    def load_config(self):
        # for node config
        self.members, self.arbitor, self.network_config, self.my_node_name = self.config.get_member_config()
        # for env
        os.environ['NUMEXPR_MAX_THREADS'] = NUMEXPR_MAX_THREADS
        # for encrypt
        self.__load_encrypt_config()
        # for job config
        self.__load_job_config()
        # for model
        self.__load_model_config()
    def __load_encrypt_config(self):
        self.encrypt_method = self.config.get_job_parameter("encrypt_method", "plain")
        if self.encrypt_method == "pailler":
            public_key_path = self.config.get_job_parameter("paillier_public_key", key_pair[0])
            if not public_key_path:
                raise ValueError("Paillier encryption requires a platform-managed public key path.")
            with open(public_key_path, 'rb') as f:
                self.public_key = pickle.load(f)
        else:
            self.public_key = None

    def __load_job_config(self):
        self.task_id = str(self.config.get_root_value("task_id","1"))
        self.try_limit = self.config.get_job_parameter("try_limit")
        self.try_time_sleep = self.config.get_job_parameter("try_time_sleep")
        self.paillier_workers = self.config.get_job_parameter("paillier_workers")
        self.agg_method = self.config.get_job_parameter("agg_method","wight_and_size_avg")

        logpath = self.config.get_job_parameter("logpath",r"./tmp/runtime-engine")
        self.logger = Logger(task_id=self.task_id,
                             logger_level=self.config.get_job_parameter("logger_level","DEBUG"),
                             name=self.my_node_name,logpath=logpath)

    def __load_model_config(self):
        self.algo = self.config.get_model_parameter("algo")
        self.num_epochs = self.config.get_model_parameter("epochs", 1)

    def send_party_weight(self, weight, key):
        for party_node in self.members:
            self.client.send(target_role=party_node,
                             key=key,
                             val=weight,time_out=self.try_limit)
    def get_party_weights(self, epoch):
        party_weights = []
        party_sizes = []

        for party_node in self.members:
            party_weight_dict = self.client.get(target_role=party_node,
                                                key=party_node + "_train_params_" + str(epoch),
                                                time_out=self.try_limit,
                                                time_sleep=self.try_time_sleep
                                                )
            party_weights.append(party_weight_dict["weight"])  # [{name1:[np, shape],name2:[np, shape]..},{}]
            party_sizes.append(party_weight_dict["train_size"])
        return party_weights, party_sizes

    def agg_weight(self,party_weights,party_sizes):
        if self.encrypt_method != 'pailler':
            if self.agg_method == "weight_avg":
                return_weights = self.aggregator.fedavg_onlyweights_plaintext(party_weights)
            else:
                return_weights = self.aggregator.fedavg_weights_plaintext(party_weights, party_sizes)
        else:
            return_weights = copy.deepcopy(party_weights[0])
            result = self.aggregator.fedavg_weights_list(party_weights, party_sizes)  # [(weight_name, new_weights),(weight_name, new_weights)...]
            for r in result:
                name = r[0]
                return_weights[name][0] = r[1]
        return return_weights

    def encrypt_weight(self,weight):
        for name in weight:
            weight[name][0] = encrypt_array(self.public_key, weight[name][0],
                                            self.paillier_workers)
        return weight

    def run(self):
        self.client = Client(node_config=self.network_config,my_name=self.my_node_name)
        model_lib = import_module(f"gemifl.algorithms.models_python.algos.{self.algo}")
        self.model = getattr(model_lib, self.algo)(self.config, self.logger)

        # init weight
        init_weight = self.model.get_weights()
        if self.encrypt_method == "pailler":
            init_weight = self.encrypt_weight(init_weight)

        # send init weight to host and guest
        send_key = self.my_node_name + "_init_params"

        self.send_party_weight(init_weight, send_key)

        for epoch in range(1, self.num_epochs + 1):
            # receive and formate request party weights [{"weight":numpy.array, "size":10}]
            party_weights, party_sizes = self.get_party_weights(epoch)
            # agg weight
            return_weights = self.agg_weight(party_weights, party_sizes)
            # send back to parties
            send_key = self.my_node_name + "_agg_params_"+str(epoch)
            self.send_party_weight(return_weights, send_key)
