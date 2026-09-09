import os
import sys
import logging
from typing import Dict
from abc import abstractmethod, ABCMeta
import re
import os
import sys
import json
import time
import torch
import logging
import pandas as pd
from gemifl.network.grpc_client import Client
from torch.utils.data import DataLoader, TensorDataset
from transformers import BertTokenizer, BertForSequenceClassification, AdamW
from typing import Dict
from abc import abstractmethod, ABCMeta


class FedBase(metaclass=ABCMeta):

    def __init__(self, config) -> None:
        self.config = config
        self.nodes = self.config["nodes"]
        self.roles = self.config["roles"]
        self.nodes_parameter = self.config["node_parameter"]
        self.model_parameter = self.config["model_parameter"]

    @abstractmethod
    def set_inputs(self, params) -> None:
        """
        Params
        param: Any type, like dict、list or others.
            A dict which contains a series of key-value, such as
            `test_ds`, `train_ds`, `epochs` etc. 
        """

    @abstractmethod
    def run(self) -> None:
        """The process of task.
        """

    def test(self) -> Dict:
        """Get the outputs of model test.
        """
        return {}

    def save_metrics(self) -> None:
        """Save the results of task.
        """


class ConfigParser:
    def __init__(self, config) -> None:
        self.config = config

        # extract first-layer parameters
        self.node_config = self.__extract_1layer_params("node_config")
        self.TaskManager = self.config.get("TaskManager")
        self.roles = self.__extract_1layer_params("roles")
        self.model_parameter = self.__extract_1layer_params("model_parameter")
        self.node_parameter = self.__extract_1layer_params("node_parameter")
        self.task_id = self.__extract_1layer_params("task_id")
        self.my_name = self.__extract_1layer_params("my_name")

    def __extract_1layer_params(self, key_str):
        return self.config[key_str]

    def __extract_2layer_params(self, key_str1, key_str2):
        return self.config[key_str1][key_str2]

    def set_clinical_bert(self):
        self.token_path = self.__extract_2layer_params(
            "model_parameter", "load_token_path")
        self.model_path = self.__extract_2layer_params(
            "model_parameter", "load_model_path")
        self.num_labels = self.__extract_2layer_params(
            "model_parameter", "num_labels")

    def set_data_source(self, key_str="data_source"):
        self.data_source = self.node_parameter[self.my_name][key_str]

    def get_roles(self, role_type: str):
        return self.roles[role_type]


class Tokenizer:
    def __init__(self, token_path="") -> None:
        self.tokenizer = self.__BertToken(token_path)

    def __BertToken(self, token_path):
        return BertTokenizer.from_pretrained(token_path)

    def BertEncode(self, text):
        code_dict = self.tokenizer.encode_plus(
            text, add_special_tokens=True, max_length=256, truncation=True, padding="max_length", return_tensors='pt')
        code_id = code_dict['input_ids'][0].tolist()
        code_mask = code_dict['attention_mask'].tolist()

        return code_id, code_mask


class ModelLoad:
    def __init__(self, model_path: str, num_labels: int) -> None:
        self.model = self.bert_from_pretrain(model_path, num_labels)

    def bert_from_pretrain(self, bert_path, num_labels):
        return BertForSequenceClassification.from_pretrained(bert_path, num_labels=num_labels)


class DataHandler:
    def __init__(self, data_source: dict, bert_tokenizer: Tokenizer, batch_size: int) -> None:
        self.data_source = data_source
        self.train_path = self.__getPath("train")
        self.test_path = self.__getPath("val")
        self.tokenizer = bert_tokenizer
        self.train_loader = DataLoader(self.__getBertDataSet(
            self.train_path), batch_size=batch_size)
        self.test_loader = DataLoader(self.__getBertDataSet(
            self.test_path), batch_size=batch_size)

    def __getPath(self, data_type):
        return self.data_source.get(data_type)

    def __getBertDataSet(self, data_path):
        df = pd.read_csv(data_path, header=0, sep="\t")
        questions = df["question"]
        answers = df["answer"]
        question_type = df["question_type"]

        # filter non-empty samples and tokenize features
        feature_ids = []
        attention_masks = []
        labels = []

        for tmp_quest, tmp_answer, tmp_label in zip(questions, answers, question_type):
            try:
                if tmp_label is not None:
                    tmp_text = self.clear_string(
                        tmp_quest) + " " + self.clear_string(tmp_answer)

                    code_id, code_mask = self.tokenizer.BertEncode(tmp_text)

                    feature_ids.append(code_id)
                    attention_masks.append(code_mask)
                    labels.append(tmp_label)
                else:
                    print(tmp_quest, tmp_answer)
            except:
                print(tmp_quest, tmp_answer)

        dataset = TensorDataset(torch.tensor(feature_ids), torch.tensor(
            attention_masks), torch.tensor(labels))

        return dataset

    def clear_string(self, raw_str):
        raw_s_li = raw_str.split(' ')
        tmp_str = [
            item.replace("\n", "")
            for item in raw_s_li
            if item != "" or item != "\n" or item != "\t" or item != "-"
        ]
        tmp_str = " ".join(tmp_str).strip().replace(
            "\t", "").replace("\s+- ", " ")
        tmp_str = re.sub(r"\s+ ", " ", tmp_str)
        tmp_str = re.sub(r"-\s+", "", tmp_str)
        return tmp_str


class InfoRecord:
    def __init__(self, root_path="./tmp/gemifL", taks_id=None, role_name="") -> None:
        self.path = os.path.join(root_path, taks_id, role_name)
        self.n_iter = 0

        if not os.path.exists(self.path):
            os.makedirs(self.path)

    def record_status(self, status_dict):
        save_path = os.path.join(self.path, "status.txt")
        with open(save_path, "w") as f:
            json_str = json.dumps(status_dict, indent=4)
            f.write(json_str)

    def record_metrics(self, metric_dict):
        if "n_iter" in metric_dict:
            n = metric_dict["n_iter"]
        else:
            n = self.n_iter

        for key, val in metric_dict.items():
            key = key.lower()

            save_path = os.path.join(self.path, "metric")
            if not os.path.exists(save_path):
                os.makedirs(save_path)

            save_file = os.path.join(save_path, f"{key}.txt")

            with open(save_file, "w+") as f:
                if self.n_iter == 0:
                    f.writelines("iter/tvalue")

                f.writelines(f"{n}/t{val}")
                self.n_iter += 1


class Federation:
    def __init__(self, my_name, roles, node_config, fed_type="AVG") -> None:
        self.grpc_client = Client(node_config, my_name)
        self.name2role = [key for key,
                          val in roles.items() if my_name in val][0]
        self.clients = roles["Client"]
        self.servers = roles["Server"]

    def average_weights(self, key, val=None, time_sleep=6):
        if self.name2role == "Server":
            averaging_net_params = None
            num_clients = len(self.clients)
            for tmp_client in self.clients:
                # increase current model parameters
                if averaging_net_params is None:
                    averaging_net_params = self.grpc_client.get(
                        target_role=tmp_client, key=key, time_sleep=time_sleep)
                else:
                    tmp_net_params = self.grpc_client.get(
                        target_role=tmp_client, key=key, time_sleep=time_sleep)
                    for k in averaging_net_params.keys():
                        averaging_net_params[k] = torch.add(
                            averaging_net_params[k], tmp_net_params[k])

            # averaging model parameters
            for k in averaging_net_params.keys():
                averaging_net_params[k] = torch.divide(
                    averaging_net_params[k], num_clients)

            # send averaging model parameters to the clients
            for tmp_node in self.clients:
                self.grpc_client.send(
                    target_role=tmp_node, key=key, val=averaging_net_params)

        elif self.name2role == "Client":
            # send model weight to server for aggregation
            for tmp_server in self.servers:
                self.grpc_client.send(target_role=tmp_server, key=key, val=val)

            averaging_net_params = self.grpc_client.get(
                target_role=self.servers[0], key=key)

        else:
            raise ValueError("The role is not implemented!")

        return averaging_net_params
