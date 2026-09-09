import torch.nn.functional as F
import torch
import torch.nn as nn
from torch_geometric.nn import GCNConv


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