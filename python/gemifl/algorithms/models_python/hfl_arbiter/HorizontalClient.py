# -*- coding: utf-8 -*-
from gemifl.utils.log import *
from gemifl.utils.config_parser import *
from gemifl.utils.default_values import *
from gemifl.utils.metrics_recorder import MetricRecord
from gemifl.phe_utils.choose_key_pair import *
from gemifl.phe_utils.paillier_utils import *
import os
import sys
import pickle
from importlib import import_module
import shutil
import json
from gemifl.network.grpc_client import Client
import requests

class HorizontalClient:

    def __init__(self, config):
        self.config = ConfigParser(config)
        self.load_config()

    def load_config(self):
        # for node config
        self.members, self.arbitor, self.network_config, self.my_node_name = self.config.get_member_config()

        # for env
        os.environ['NUMEXPR_MAX_THREADS'] = NUMEXPR_MAX_THREADS
        # for encrypt/decrypt
        self.__load_encrypt_config()

        # for job config
        self.__load_job_config()
        # for model
        self.__load_model_config()

    def __load_encrypt_config(self):
        self.encrypt_method = self.config.get_job_parameter("encrypt_method","plain")
        if self.encrypt_method == "pailler":
            public_key_path = self.config.get_job_parameter("paillier_public_key", key_pair[0])
            private_key_path = self.config.get_job_parameter("paillier_private_key", key_pair[1])
            if not public_key_path or not private_key_path:
                raise ValueError("Paillier encryption requires platform-managed public/private key paths.")
            with open(public_key_path, 'rb') as f:
                self.public_key = pickle.load(f)
            with open(private_key_path, 'rb') as f:
                self.private_key = pickle.load(f)

    def __load_job_config(self):
        self.task_id = str(self.config.get_root_value("task_id","1"))
        self.try_limit = self.config.get_job_parameter("try_limit")
        self.try_time_sleep = self.config.get_job_parameter("try_time_sleep")
        self.paillier_workers = self.config.get_job_parameter("paillier_workers")

        logpath = self.config.get_job_parameter("logpath",r"./tmp/runtime-engine")
        self.logger = Logger(task_id=self.task_id,
                             logger_level=self.config.get_job_parameter("logger_level","DEBUG"),
                             name=self.my_node_name,logpath=logpath)
        metric_dir = self.config.get_model_parameter("metric_dir","./tmp/runtime-engine")
        self.task_type = self.config.get_model_parameter("task_type","")
        self.test_mets = MetricRecord(
            root_dir=metric_dir,
            taskId=self.task_id,
            node_name=self.my_node_name)

    def __load_model_config(self):
        self.evaluate_pre_epoch = self.config.get_model_parameter("evaluate_pre_epoch",1)
        self.algo = self.config.get_model_parameter("algo")
        self.num_epochs = self.config.get_model_parameter("epochs", 1)


    def init_model(self):
        model_lib = import_module(f"gemifl.algorithms.models_python.algos.{self.algo}")
        self.model = getattr(model_lib, self.algo)(self.config, self.logger)

        init_weight = self.client.get(target_role=self.arbitor, key=self.arbitor + "_init_params",
                                      time_out=self.try_limit,
                                      time_sleep=self.try_time_sleep)

        if self.encrypt_method == "pailler":
            init_weight = self.decrypt_weight(init_weight)

        self.model.set_weights(init_weight)

    def send_arbitor_weight(self, local_model_weights, epoch):
        try:
            train_size = self.model.data_sizes["train"]
        except:
            train_size = 1
        if self.encrypt_method == "pailler":
            local_model_weights = self.encrypt_weight(local_model_weights)

        self.client.send(target_role=self.arbitor,
                         key=self.my_node_name + "_train_params_" + str(epoch),
                         val={"weight": local_model_weights, "train_size": train_size},
                         time_out=self.try_limit
                         )

    def get_arbitor_weight(self, epoch):
        agg_weight = self.client.get(target_role=self.arbitor,
                                     key=self.arbitor + "_agg_params_" + str(epoch),
                                     time_out=self.try_limit,
                                     time_sleep=self.try_time_sleep
                                     )
        if self.encrypt_method == "pailler":
            agg_weight = self.decrypt_weight(agg_weight)
        return agg_weight

    def decrypt_weight(self, weight):
        for name in weight:
            weight[name][0] = decrypt_array(self.public_key, self.private_key,
                                                 weight[name][0],
                                                 self.paillier_workers)
        return weight

    def encrypt_weight(self,weight):
        for name in weight:
            weight[name][0] = encrypt_array(self.public_key, weight[name][0],
                                            self.paillier_workers)
        return weight

    def validate(self, epoch):
        
        if epoch % int(self.evaluate_pre_epoch) == 0:
            try:
                current_score = self.model.evaluate(self.task_type)
            except Exception as e:
                self.logger.error(f"validate error: {e}")
                current_score = {}
            current_score["n_iter"] = epoch
            self.test_mets.save(current_score)

    def datascoreCal(self):
        datasize = self.model.data_sizes["train"]
        if datasize<10000:
            return 30
        elif datasize>=10000 and datasize<20000:
            return 60
        else:
            return 1000

    def set_contribute(self):
        try:
            datasize_score = self.datascoreCal()
            contri = datasize_score
            contri = {
                        "center": self.my_node_name,
                        "degree": contri
                     }
            url = contri_url
            payload = json.dumps(contri)
            headers = {
                'Content-Type': 'application/json'
            }
            response = requests.request("POST", url, headers=headers, data=payload)
            self.logger.info("set contribute from %s to center" % (self.my_node_name))
        except Exception as e:
            self.logger.error(f"{e.__class__.__name__}: {e}")

    def run(self):
        # init client
        self.client = Client(node_config=self.network_config,my_name=self.my_node_name)
        # init model
        self.init_model()

        for epoch in range(1, self.num_epochs + 1):
            self.logger.info('Epoch {}/{}'.format(epoch, self.num_epochs))
            self.model.train_one_epoch()
            local_model_weights = self.model.get_weights()

            # send local model weights to arbitor
            self.send_arbitor_weight(local_model_weights, epoch)

            # wait and get agg weight
            agg_weight = self.get_arbitor_weight(epoch)

            self.model.set_weights(agg_weight)
            # validate
            self.validate(epoch)
            # write status
            self.write_status(epoch)

            self.model.dumpfile()

        self.logger.info("train finished")
        self.model.set_best_weight()
        # self.model.dumpfile()
        self.set_contribute()

    def write_status(self, epoch):
        temp_log_path = f'./tmp/runtime-engine/{self.task_id}/{self.my_node_name}'
        if not os.path.exists(temp_log_path):
            os.makedirs(temp_log_path)
            self.logger.info(f"mkdir {temp_log_path}")

        with open(f'{temp_log_path}/status.txt', 'w') as f:
            json.dump({"progress_rate": round(epoch/(self.num_epochs+1),2)}, f)
