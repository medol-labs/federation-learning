import torch.nn.functional as F
import torch
import pickle
import numpy as np
import torch.nn as nn
from torch_geometric.nn import GCNConv
from gemifl.utils.basefunctions import load_data
from sklearn.metrics import precision_recall_curve, auc
from gemifl.utils.metrics_module import MLMetrics


class GCN(nn.Module):
    """
    Graph Convolutional Network based on https://arxiv.org/abs/1609.02907
    """

    def __init__(self,
                 feat_dim,
                 hidden_dim1,
                 hidden_dim2,
                 dropout,
                 is_sparse=False):
        """Dense version of GAT."""
        super(GCN, self).__init__()
        # self.dropout = dropout
        self.W1 = nn.Parameter(torch.FloatTensor(feat_dim, hidden_dim1))
        self.W2 = nn.Parameter(torch.FloatTensor(hidden_dim1, hidden_dim2))
        self.relu = nn.ReLU()
        self.dropout = nn.Dropout(p=dropout)

        nn.init.xavier_uniform_(self.W1.data)
        nn.init.xavier_uniform_(self.W2.data)

        self.is_sparse = is_sparse

    def forward(self, x, adj):
        # Layer 1
        support = torch.mm(x, self.W1)
        embeddings = (torch.sparse.mm(adj, support)
                      if self.is_sparse else torch.mm(adj, support))

        embeddings = self.dropout(embeddings)

        # Layer 2
        support = torch.mm(embeddings, self.W2)
        embeddings = (torch.sparse.mm(adj, support)
                      if self.is_sparse else torch.mm(adj, support))

        return embeddings


class Readout(nn.Module):
    """
    This module learns a single graph level representation for a molecule given GNN generated node embeddings
    """

    def __init__(self, attr_dim, embedding_dim, hidden_dim, output_dim,
                 num_cats):
        super(Readout, self).__init__()
        self.attr_dim = attr_dim
        self.hidden_dim = hidden_dim
        self.output_dim = output_dim
        self.num_cats = num_cats

        self.layer1 = nn.Linear(attr_dim + embedding_dim, hidden_dim)
        self.layer2 = nn.Linear(hidden_dim, output_dim)
        self.output = nn.Linear(output_dim, num_cats)
        self.act = nn.ReLU()

    def forward(self, node_features, node_embeddings):
        combined_rep = torch.cat(
            (node_features, node_embeddings),
            dim=1)  # Concat initial node attributed with embeddings from sage
        hidden_rep = self.act(self.layer1(combined_rep))
        graph_rep = self.act(
            self.layer2(hidden_rep))  # Generate final graph level embedding

        logits = torch.mean(
            self.output(graph_rep),
            dim=0)  # Generated logits for multilabel classification

        return logits


class GcnMoleculeNet(nn.Module):
    """
    Network that consolidates GCN + Readout into a single nn.Module
    """

    def __init__(
        self,
        feat_dim,
        hidden_dim,
        node_embedding_dim,
        dropout,
        readout_hidden_dim,
        graph_embedding_dim,
        num_categories,
        sparse_adj=False,
    ):
        super(GcnMoleculeNet, self).__init__()
        self.gcn = GCN(feat_dim,
                       hidden_dim,
                       node_embedding_dim,
                       dropout,
                       is_sparse=sparse_adj)
        self.readout = Readout(
            feat_dim,
            node_embedding_dim,
            readout_hidden_dim,
            graph_embedding_dim,
            num_categories,
        )

    def forward(self, adj_matrix, feature_matrix):
        node_embeddings = self.gcn(feature_matrix, adj_matrix)
        logits = self.readout(feature_matrix, node_embeddings)
        return logits


class GCNNodeCLF(torch.nn.Module):

    def __init__(self, nfeat, nhid, nclass, nlayer, dropout):
        super(GCNNodeCLF, self).__init__()
        self.num_layers = nlayer
        self.dropout = dropout
        self.nclass = nclass

        self.pre = torch.nn.Sequential(torch.nn.Linear(nfeat, nhid))

        self.graph_convs = torch.nn.ModuleList()

        for l in range(nlayer - 1):
            self.graph_convs.append(GCNConv(nhid, nhid))

        self.post = torch.nn.Sequential(torch.nn.Linear(nhid, nclass))

    def forward(self, data):
        x, edge_index = data.x, data.edge_index
        x = self.pre(x)
        for i in range(len(self.graph_convs)):
            x = self.graph_convs[i](x, edge_index)
            x = F.relu(x)
            x = F.dropout(x, self.dropout, training=self.training)
        x = self.post(x)
        return F.log_softmax(x, dim=1)

    def loss(self, pred, label):
        return F.nll_loss(pred, label)


class GCNLinkPred(torch.nn.Module):

    def __init__(self, in_channels, out_channels):
        super(GCNLinkPred, self).__init__()
        self.conv1 = GCNConv(in_channels, 128)
        self.conv2 = GCNConv(128, out_channels)

    def encode(self, x, edge_index):
        x = self.conv1(x, edge_index)
        x = x.relu()
        return self.conv2(x, edge_index)

    def decode(self, z, pos_edge_index, neg_edge_index):
        edge_index = torch.cat([pos_edge_index, neg_edge_index], dim=-1)
        return (z[edge_index[0]] * z[edge_index[1]]).sum(dim=-1)

    def decode_all(self, z):
        prob_adj = z @ z.t()
        return (prob_adj > 0).nonzero(as_tuple=False).t()


class HomoGcn:
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
        self.train_data_source, self.val_data_source = self.config.get_data_setting(
            self.my_node_name)
        # for model config
        self.train_batch_size = self.config.get_model_parameter(
            "train_batch_size")
        self.valid_batch_size = self.config.get_model_parameter(
            "valid_batch_size")
        self.model_save = self.config.get_model_parameter("model_save")
        self.weight_save = self.config.get_model_parameter("weight_save")
        # for model evaluation
        self.best_loss = np.inf
        self.best_model_wts = None
        self.bestauc = -1

    def get_device(self):
        nodes_parameter = self.config.get_nodes_parameter(self.my_node_name)
        userdevice = nodes_parameter.get("device", "cpu").lower()

        if torch.cuda.is_available() and userdevice != 'cpu':
            try:
                self.device = torch.device(userdevice)
            except:
                self.device = torch.device("cuda:0")
            self.logger.info("Training on GPU...")
        else:
            self.device = torch.device("cpu")
            self.logger.info("Training on CPU...")

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
            weight_input[name] = torch.tensor(np.reshape(
                parameters[name][0], parameters[name][1]))

        self.model.load_state_dict(weight_input)

    def set_best_weight(self):
        if self.best_model_wts:
            self.set_weights(self.best_model_wts)

    def init_model(self):
        net_config = self.config.get_model_parameter("net_config")
        self.learning_rate = self.config.get_model_parameter("learning_rate")
        self.model = GcnMoleculeNet(**net_config)
        self.get_device()
        self.model.to(self.device)

        self.optimizer = torch.optim.Adam(
            self.model.parameters(), lr=self.learning_rate)
        self.criterion = torch.nn.BCEWithLogitsLoss(reduction="none")

    def get_data(self):
        self.train_dataloader = load_data(
            self.config.get_nodes_parameter(self.my_node_name)["data_source"]["train"])

        self.test_dataloader = load_data(
            self.config.nodes_parameter[self.my_node_name]["data_source"]["val"])

    def train_one_epoch(self):
        self.model.train()
        self.get_data()

        for tmp_feature, tmp_label, tmp_adj in zip(
                self.train_dataloader["x"], self.train_dataloader["y"],
                self.train_dataloader["adj"]):
            self.optimizer.zero_grad()

            # convert to tensor
            tmp_adj = torch.tensor(tmp_adj.todense(),
                                   dtype=torch.float32).to(self.device)

            tmp_feature = torch.tensor(tmp_feature,
                                       dtype=torch.float32).to(self.device)
            tmp_label = torch.tensor(tmp_label).to(self.device)
            tmp_label = tmp_label.to(dtype=torch.float32,
                                     non_blocking=True)

            logits = self.model(tmp_adj, tmp_feature)
            loss = self.criterion(logits, tmp_label)
            loss = loss.mean()

            loss.backward()
            self.optimizer.step()

    def evaluate(self, task_type):
        self.model.eval()
        y_pred = []
        y_true = []
        current_score = {}

        with torch.no_grad():
            for tmp_feature, tmp_label, tmp_adj in zip(
                    self.test_dataloader["x"], self.test_dataloader["y"],
                    self.test_dataloader["adj"]):

                tmp_adj = torch.tensor(tmp_adj.todense(),
                                       dtype=torch.float32).to(self.device)

                tmp_feature = torch.tensor(tmp_feature,
                                           dtype=torch.float32).to(self.device)

                test_logits = self.model(tmp_adj, tmp_feature)
                y_pred.append(test_logits.cpu().numpy())
                y_true.append(tmp_label)

        metricsobj = MLMetrics(y_true, 0, y_pred)
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
