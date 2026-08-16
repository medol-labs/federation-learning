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


class LogisticRegressionClient:
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

    def sigmoid(self, z):
        return 1 / (1 + np.exp(-z))

    def run(self):
        print(self.process)
        if self.process == 'train':
            self.train()

    def compute_gradient(self):
        m = len(self.y)

        # Forward pass
        z = np.dot(self.X, self.weights) + self.bias
        prediction = self.sigmoid(z)

        # Compute gradients
        dw = (1 / m) * np.dot(self.X.T, (prediction - self.y))
        db = (1 / m) * np.sum(prediction - self.y)

        return dw, db
    

    def train(self):
        for i in range(self.epoch):
            dw, db = self.compute_gradient()
            self.client.send(self.target_role,f'result{i}', [dw, db])
            self.set_weights(self.client.get(self.target_role, f'result{i}'))
        print("Client finished")

    def set_weights(self, db):
        global_weights, global_bias = db
        self.weights = global_weights
        self.bias = global_bias

        

    
