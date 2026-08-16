import numpy as np
import pandas as pd
from gemifl.network.grpc_client import Client
import random
class LogisticRegressionWanderClient:
    def __init__(self, config):
        self.learning_rate = config["model_parameter"]['learning_rate']
        self.epoch = int(config["model_parameter"]['epoch'])
        self.process = config["model_parameter"]['process']
        self.name = config["my_name"]
        self.X = pd.read_csv(config["node_parameter"][self.name]["data_source"]["X"]).values
        self.y = pd.read_csv(config["node_parameter"][self.name]["data_source"]["y"]).values.flatten()
        self.val_X = pd.read_csv(config["node_parameter"][self.name]["data_source"]["val_X"]).values
        self.val_y = pd.read_csv(config["node_parameter"][self.name]["data_source"]["val_y"]).values.flatten()
        self.weights = np.zeros(self.X.shape[1])
        self.bias = 0.0
        self.client = Client(config['node_config'], self.name)
        #Get the order of wandering and remove the roles that repeat twice in one role
        names = config["roles"]['Client']
        random.seed(0)
        tmp_list = random.choices(names, k = self.epoch*20)
        self.orders = [tmp_list[0]]
        current_count = 1
        while len(self.orders)<(self.epoch+1):
            current_node = tmp_list[current_count]
            if current_node!=self.orders[-1]:
                self.orders.append(current_node)
            else:
                current_count+=1

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
        cul_dw = np.zeros(self.X.shape[1])
        cul_db = 0.0
        for i in range(self.epoch):
            dw, db = self.compute_gradient()
            cul_dw += dw
            cul_db += db
            current_node = self.orders[i]
            if current_node!=self.name:
                self.weights -= self.learning_rate * dw
                self.bias -= self.learning_rate * db
            else:
                if i==0:
                    self.weights -= self.learning_rate * cul_dw
                    self.bias -= self.learning_rate * cul_db
                    self.evaluate(i)
                    self.client.send(self.orders[i+1],f'result{i}', [self.weights, self.bias])
                else:
                    self.set_weights(self.client.get(self.orders[i-1], f'result{i-1}'))
                    self.weights -= self.learning_rate * cul_dw
                    self.bias -= self.learning_rate * cul_db
                    self.evaluate(i)
                    if i!=self.epoch-1:
                        self.client.send(self.orders[i+1],f'result{i}', [self.weights, self.bias])
                cul_dw = np.zeros(self.X.shape[1])
                cul_db = 0.0

    def set_weights(self, db):
        global_weights, global_bias = db
        self.weights = global_weights
        self.bias = global_bias

    def evaluate(self, i):
        z = np.dot(self.val_X, self.weights) + self.bias
        prediction = self.sigmoid(z)
        loss = np.sum((prediction - self.val_y)**2/len(prediction))


        if i==0:
            f = open('./tmp/result_LRW.csv', "w")
            f.write(f"0,1"+'\n')
            f.close()

        
        f = open('./tmp/result_LRW.csv', "a")
        f.write(f"{i}, {loss}"+'\n')
        f.close()



    

