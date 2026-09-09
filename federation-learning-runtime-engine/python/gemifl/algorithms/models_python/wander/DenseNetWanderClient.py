import numpy as np
import pandas as pd
from gemifl.utils.config_parser import *
from gemifl.utils.default_values import *
from gemifl.network.grpc_client import Client
from gemifl.utils.log import *
from gemifl.utils.metrics_recorder import MetricRecord
import random
from gemifl.algorithms.models_python.algos.HomoDenseNet import DenseNet
from importlib import import_module
import json

class DenseNetWanderClient:
    def __init__(self, config):
        self.config = ConfigParser(config)
        self.load_config()

    def load_config(self):
        # for node config
        self.members, self.arbitor, self.network_config, self.my_node_name = self.config.get_member_config()

        # for env
        os.environ['NUMEXPR_MAX_THREADS'] = NUMEXPR_MAX_THREADS

        # for job config
        self.__load_job_config()
        # for model
        self.__load_model_config()

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

    def run(self):
        names = self.config.get_root_value("roles",[]).get('Client',[])
        random.seed(0)
        self.orders = []
        '''
        while len(self.orders)<self.num_epochs:
            if len(self.orders) == 0:
                nextnode = random.choices(names, k=1)
                self.orders.append(nextnode)
            else:
                currentnode = self.orders[-1]
                if nextnode!=currentnode:
                    nextnode = random.choices(names, k=1)
                    self.orders.append(nextnode)'''

        self.orders = random.choices(names, k=self.num_epochs)
        print(self.orders)
        # init client
        self.client = Client(node_config=self.network_config,my_name=self.my_node_name)
        # init model
        self.init_model()

        for epoch in range(1, self.num_epochs + 1):
            i = epoch-1
            self.logger.info('Epoch {}/{}'.format(epoch, self.num_epochs))
            current_node = self.orders[epoch-1]
            if current_node != self.my_node_name:
                pass
            else:
                if i==0:
                    self.model.train_one_epoch()
                    local_model_weights = self.model.get_weights()
                    self.client.send(target_role=self.orders[i+1],
                                     key=f'result{i}',
                                     val=local_model_weights,
                                     time_out=self.try_limit
                                     )
                    self.client.send(self.orders[i+1],f'result{i}', local_model_weights)
                else:
                    new_weights = self.client.get(target_role=self.orders[i-1],
                                                  key=f'result{i-1}',
                                                  time_out=self.try_limit,
                                                  time_sleep=self.try_time_sleep
                                                 )

                    self.model.set_weights(new_weights)
                    self.model.train_one_epoch()
                    if epoch!=self.num_epochs:
                        local_model_weights = self.model.get_weights()
                        self.client.send(self.orders[i+1],f'result{i}', local_model_weights)
                # validate
                self.validate(epoch)
                # write status
                self.write_status(epoch)

    def set_weights(self, db):
        global_weights, global_bias = db
        self.weights = global_weights
        self.bias = global_bias


    def validate(self, epoch):
        if epoch % int(self.evaluate_pre_epoch) == 0:
            current_score = self.model.evaluate(self.task_type)
            try:
                current_score = self.model.evaluate(self.task_type)
            except Exception as e:
                self.logger.error(f"validate error: {e}")
                current_score = {}
            current_score["n_iter"] = epoch
            self.test_mets.save(current_score)


    def write_status(self, epoch):
        temp_log_path = f'./tmp/runtime-engine/{self.task_id}/{self.my_node_name}'
        if not os.path.exists(temp_log_path):
            os.makedirs(temp_log_path)
            self.logger.info(f"mkdir {temp_log_path}")

        with open(f'{temp_log_path}/status.txt', 'w') as f:
            json.dump({"progress_rate": round(epoch/(self.num_epochs+1),2)}, f)




    
