import torch
import numpy as np
import pandas as pd
from sklearn.preprocessing import LabelEncoder
from pytorch_tabnet.tab_model import TabNetClassifier
from matplotlib import pyplot as plt

if __name__ == "__main__":
    csv_path = "/Users/xusong/Documents/githubs/GemiFL/datasets/cornary_heart_disease/heart_disease.csv"
    df = pd.read_csv(csv_path, engine='python', encoding="utf-8", header=None)
    n = len(df)
    # ratio = 0.02
    ratio = 0.01
    limit_size = 10

    # del column that owns NAs great than ratio
    saved_cols = []
    ratio_of_nulls = df.isnull().sum(axis=0) / n

    for col in df.columns:
        if ratio_of_nulls[col] < ratio:
            saved_cols.append(col)

    cleaned_data = df[saved_cols]
    label = cleaned_data.pop(0)

    total_accs = []
    total_recalls = []

    for j in range(10):

        if "Set" not in cleaned_data.columns:
            np.random.seed(j)
            cleaned_data["Set"] = np.random.choice(
                ["train", "valid", "test"],
                p=[.8, .1, .1],
                size=(cleaned_data.shape[0],))

        # get index for each set
        train_indices = cleaned_data[cleaned_data.Set == "train"].index
        valid_indices = cleaned_data[cleaned_data.Set == "valid"].index
        test_indices = cleaned_data[cleaned_data.Set == "test"].index

        # del the 'Set' column
        cleaned_data.pop("Set")

        n_unique = cleaned_data.nunique()
        print("n_unique: ", n_unique)
        n_types = cleaned_data.dtypes

        categorical_columns = []
        categorical_dims = {}

        for col in cleaned_data.columns:
            if n_types[col] == 'object' or n_unique[col] < limit_size:
                print("current categorical: ", col, cleaned_data[col].nunique())
                l_enc = LabelEncoder()
                # cleaned_data[col] = cleaned_data[col].fillna("VV_likely")
                cleaned_data[col] = cleaned_data[col].fillna(0)
                cleaned_data[col] = l_enc.fit_transform(
                    cleaned_data[col].values)
                categorical_columns.append(col)
                categorical_dims[col] = len(l_enc.classes_)
            else:
                # cleaned_data.fillna(cleaned_data.loc[train_indices, col].mean(),
                #                     inplace=True)

                cleaned_data[col].fillna(0, inplace=True)

        features = list(cleaned_data.columns)

        cat_idxs = [
            i for i, f in enumerate(features) if f in categorical_columns
        ]
        cat_dims = [
            categorical_dims[f]
            for i, f in enumerate(features)
            if f in categorical_columns
        ]

        X_train = cleaned_data[features].values[train_indices]
        y_train = label.values[train_indices]

        X_valid = cleaned_data[features].values[valid_indices]
        y_valid = label.values[valid_indices]

        X_test = cleaned_data[features].values[test_indices]
        y_test = label.values[test_indices]

        tabnet_params = {
            "cat_idxs": cat_idxs,
            "cat_dims": cat_dims,
            "cat_emb_dim": 5,
            # "seed": 42,
            "seed": j,
            "optimizer_fn": torch.optim.Adam,
            "optimizer_params": dict(lr=2e-2),
            "scheduler_params": {
                "step_size": 50,  # how to use learning rate scheduler
                "gamma": 0.9
            },
            "scheduler_fn": torch.optim.lr_scheduler.StepLR,
            "mask_type": 'entmax',  # "sparsemax"
        }
        clf = TabNetClassifier(**tabnet_params)

        clf.fit(
            X_train=X_train,
            y_train=y_train,
            # eval_set=[(X_train, y_train), (X_valid, y_valid)],
            # eval_name=['train', 'valid'],
            # eval_set=[(X_valid, y_valid)],
            eval_set=[(X_train, y_train)],
            eval_name=['valid'],
            eval_metric=['balanced_accuracy'],
            max_epochs=50,
            patience=15,
            batch_size=32,
            virtual_batch_size=64,
            num_workers=0,
            weights=1,
            drop_last=False)

        preds = clf.predict(X_test)
        clf.predict_proba

        recall = (preds * y_test).sum() / (y_test == 1).astype(int).sum()
        acc = (preds == y_test).astype(int).sum() / len(preds)

        if recall and recall > 0:
            total_accs.append(acc)
            total_recalls.append(recall)

        print(total_recalls, total_accs)

    print(
        f"Averaged recall is {np.mean(total_recalls)}, averaged acc is {np.mean(total_accs)}"
    )
