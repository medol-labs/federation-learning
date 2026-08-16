import os
import pickle
import numpy as np
from _gcn_base import *
from gemifl.dataset.generator.generate import *
from sklearn.model_selection import train_test_split
from sklearn.metrics import roc_auc_score, precision_recall_curve, auc


def test_score(model, test_loader, current_score=0):
    y_pred = []
    y_true = []
    masks = []
    for (tmp_adj_1, tmp_feature_1, tmp_label_1, mask) in test_loader:
        # tmp_adj_1 = torch.tensor(tmp_adj_1.todense(), dtype=torch.float32)
        # tmp_feature_1 = torch.tensor(tmp_feature_1, dtype=torch.float32)

        test_logits = model(tmp_adj_1, tmp_feature_1)

        y_pred.append(test_logits.detach().numpy())
        y_true.append(tmp_label_1.detach().numpy())
        masks.append(mask.detach().numpy())

    y_pred = np.array(y_pred)
    y_true = np.array(y_true)
    masks = np.array(masks)
    # print(masks)

    results = []
    for label in range(masks.shape[1]):
        valid_idxs = np.nonzero(masks[:, label])
        truth = y_true[valid_idxs, label].flatten()
        pred = y_pred[valid_idxs, label].flatten()

        if np.all(truth == 0.0) or np.all(truth == 1.0):
            results.append(float("nan"))
        else:
            precision, recall, _ = precision_recall_curve(truth, pred)
            score = auc(recall, precision)

            results.append(score)

    score = np.nanmean(results)

    if score > current_score:
        current_score = score
    return current_score


if __name__ == "__main__":
    adj_pkl = "/Users/xusong/Documents/githubs/GemiData/datasets/clintox/adjacency_matrices.pkl"
    feature_pkl = "/Users/xusong/Documents/githubs/GemiData/datasets/clintox/feature_matrices.pkl"
    label_npy = "/Users/xusong/Documents/githubs/GemiData/datasets/clintox/labels.npy"

    # load feature and label of dataset
    if os.path.exists(adj_pkl):
        with open(adj_pkl, "rb") as f:
            adj_mat = pickle.load(f)

    if os.path.exists(feature_pkl):
        with open(feature_pkl, "rb") as f:
            feature_mat = pickle.load(f)

    if os.path.exists(label_npy):
        labels = np.load(label_npy)

    num_cats = labels[0].shape[0]
    # create gcn model
    feature_dimension = feature_mat[0].shape[1]
    hidden_dim = 32
    embedding_dim = 32
    drop = 0.3
    readout_hidden_dim = 64
    graph_embedding_dim = 64
    learning_rate = 0.03
    epochs = 100

    model = GcnMoleculeNet(feat_dim=feature_dimension,
                           hidden_dim=hidden_dim,
                           node_embedding_dim=embedding_dim,
                           readout_hidden_dim=readout_hidden_dim,
                           graph_embedding_dim=graph_embedding_dim,
                           num_categories=num_cats,
                           dropout=drop)

    optimizer = torch.optim.SGD(model.parameters(), lr=0.03)
    criterion = torch.nn.BCEWithLogitsLoss(reduction="none")

    total_auc = []
    total_recall = []
    total_prec = []

    for j in range(1):
        # split adj_mat, feature_mat and labels into training and testing dataset
        train_adj, test_adj, train_feature, test_feature, train_labels, test_labels = train_test_split(
            adj_mat, feature_mat, labels, train_size=0.85, random_state=j)

        train_dataset = MoleculesDataset(adj_matrices=train_adj,
                                         feature_matrices=train_feature,
                                         labels=train_labels)

        test_dataset = MoleculesDataset(adj_matrices=test_adj,
                                        feature_matrices=test_feature,
                                        labels=test_labels)

        collator = DefaultCollator(normalize_features=False,
                                   normalize_adj=False)

        train_dataloader = data.DataLoader(train_dataset,
                                           batch_size=1,
                                           shuffle=True,
                                           collate_fn=collator,
                                           pin_memory=True)

        test_dataloader = data.DataLoader(test_dataset,
                                          batch_size=1,
                                          shuffle=False,
                                          collate_fn=collator,
                                          pin_memory=True)
        total_loss = []
        current_score = 0

        for iter in range(epochs):
            current_loss = []

            for (tmp_adj, tmp_feature, tmp_label, tmp_mask) in train_dataloader:
                optimizer.zero_grad()

                logits = model(tmp_adj, tmp_feature)

                loss = criterion(logits, tmp_label) * tmp_mask
                loss = loss.sum() / tmp_mask.sum()

                loss.backward()
                optimizer.step()

                current_loss.append(loss.detach().numpy())

            # print(np.mean(current_loss))
            current_score = test_score(model, test_dataloader, current_score)

            print("current_score: ", iter, current_score)
