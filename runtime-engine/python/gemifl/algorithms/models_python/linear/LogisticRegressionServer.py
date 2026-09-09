import numpy as np
import pandas as pd
from gemifl.network.grpc_client import Client


def load_xy(data_source):
    if "path" in data_source:
        df = pd.read_csv(data_source["path"])
        label_column = data_source.get("label_column", "y")
        id_column = data_source.get("id")
        y = df[label_column].values.flatten()
        drop_columns = [label_column]
        if id_column and id_column in df.columns:
            drop_columns.append(id_column)
        x = df.drop(columns=drop_columns).values
        return x, y
    return (
        pd.read_csv(data_source["X"]).values,
        pd.read_csv(data_source["y"]).values.flatten()
    )


class LogisticRegressionServer:
    def __init__(self, config):
        self.learning_rate = config["model_parameter"]['learning_rate']
        self.epoch = int(config["model_parameter"]['epoch'])
        self.process = config["model_parameter"]['process']
        self.name = config["my_name"]
        self.X, self.y = load_xy(config["node_parameter"][self.name]["data_source"])
        self.target_role = config["roles"]["Server"][0]
        self.weights = np.zeros(self.X.shape[1])
        self.bias = 0.0
        self.client = Client(config['node_config'], self.name)
        self.results = []
        self.n_client = 0

    def sigmoid(self, z):
        return 1 / (1 + np.exp(-z))
    
    def aggregate_gradients(self, i):
        # Initialize accumulators for gradients
        total_dw = np.zeros(self.X.shape[1])
        total_db = 0

        # Sum gradients from all clients
        dw_dbs = self.client.get_all(f'result{i}')
        for dw, db in dw_dbs:
            total_dw += dw
            total_db += db
        self.n_client = len(dw_dbs)
        # Average the gradients
        avg_dw = total_dw / self.n_client
        avg_db = total_db / self.n_client

        return avg_dw, avg_db

    def run(self):
        if self.process == 'train':
            self.train()
    
    def train(self):
        for i in range(self.epoch):
            avg_dw, avg_db = self.aggregate_gradients(i)
            self.weights -= self.learning_rate * avg_dw
            self.bias -= self.learning_rate * avg_db
            self.client.send_all(f'result{i}', [self.weights,self.bias ])
            self.evaluate(i)
        
        result_df = pd.DataFrame(self.results)
        result_df.to_csv("./tmp/result_LR.csv", index = None)
        print("Server finished")
        exit()
    
    def evaluate(self, i):
        z = np.dot(self.X, self.weights) + self.bias
        prediction = self.sigmoid(z)
        loss = np.sum((prediction - self.y)**2/len(prediction))
        self.results.append([i*self.n_client,loss])

