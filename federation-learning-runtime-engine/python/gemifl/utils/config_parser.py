# -*- coding: utf-8 -*-

class ConfigParser:
    def __init__(self,config):
        self.config = config
        self.model_parameter = self.config["model_parameter"]
        self.nodes_parameter = self.config["node_parameter"]
        self.job_parameter = self.config.get("job_parameter", {})
        self.node_config = self.config.get("node_config", {})

    def get_job_parameter(self,key,default=''):
        return self.job_parameter.get(key,default)

    def get_node_config(self,key,default=''):
        return self.node_config.get(key,default)

    def get_model_parameter(self,key,default=''):
        return self.model_parameter.get(key,default)

    def get_nodes_parameter(self,key,default=''):
        return self.nodes_parameter.get(key,default)

    def get_root_value(self,key,default=''):
        return self.config.get(key,default)

    def get_member_config(self):
        my_node_name = self.get_root_value("my_name")
        members = []
        arbitor = None
        network_config = {}

        for k in self.nodes_parameter:
            if k != my_node_name:
                members.append(k)
            if self.nodes_parameter[k]["role"] == "arbitor":
                arbitor = k
            if k in self.node_config:
                network_config[k] = self.node_config[k]
        return members, arbitor, network_config, my_node_name

    def get_data_setting(self, my_node_name):
        train_data_source = None
        val_data_source = None

        my_nodes_parameter = self.get_nodes_parameter(my_node_name)
        if my_nodes_parameter["role"] != "arbitor":
            train_data_source = my_nodes_parameter['data_source']['train']
            val_data_source = my_nodes_parameter['data_source']['val']
        return train_data_source, val_data_source
