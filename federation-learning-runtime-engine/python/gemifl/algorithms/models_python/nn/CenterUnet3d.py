import os
import sys
import logging
import pandas as pd
import torch
from torch.utils.data import DataLoader
from gemifl.algorithms.models_python.nn._Unet3d_base import UNet3d, BratsDataset, Meter, BCEDiceLoss, Adam

device = 'cuda' if torch.cuda.is_available() else 'cpu'

logging.basicConfig(level=logging.INFO, stream=sys.stdout)


def load_brain_tumor(
    data_path="/Users/xusong/braintumor/archive1/BraTS2020_TrainingData/MICCAI_BraTS2020_TrainingData"
):
    data_dir = data_path
    survival_path = os.path.join(data_dir, "survival_info.csv")
    name_mapping_path = os.path.join(data_dir, "name_mapping.csv")

    survival_df = pd.read_csv(survival_path)
    name_mapping_df = pd.read_csv(name_mapping_path)

    # rename column name to match 'survival_df'
    name_mapping_df.rename({'BraTS_2020_subject_ID': 'Brats20ID'},
                           axis=1,
                           inplace=True)

    train_df = survival_df.merge(name_mapping_df, on="Brats20ID", how="right")

    paths = []
    for index, row in train_df.iterrows():

        id_ = row['Brats20ID']
        phase = id_.split("_")[-2]

        if phase == 'Training':
            path = os.path.join(data_dir, id_)
        else:
            path = ""
        paths.append(path)

    train_df['path'] = paths

    train_data = train_df.loc[
        train_df['Brats20ID'] != 'BraTS20_Training_355'].reset_index(drop=True)

    current_data = BratsDataset(train_data, phase="trian")

    current_data_loader = DataLoader(
        current_data,
        batch_size=1,
        num_workers=0,
        pin_memory=True,
        shuffle=False,
    )

    return current_data_loader


def train(model,
          train_loader,
          train_epochs=5,
          test_x=None,
          test_y=None,
          device="cpu"):
    device = device

    model = model.to(device)
    meter = Meter()

    losses = []
    dice_scores = []
    jaccard_scores = []
    sen_scores = []
    spf_scores = []

    criterion = BCEDiceLoss()
    optimizer = Adam(model.parameters(), lr=0.05)

    best_loss = float("inf")

    for _ in range(train_epochs):
        model.train()
        current_loss = 0.0
        sample_num = len(train_loader)

        # training the model
        for n_iter, data_batch in enumerate(train_loader):
            # image, target = data_batch['image'], data_batch['mask']
            image, target = data_batch[0], data_batch[1]
            optimizer.zero_grad()

            target = target.to(device=device)
            image = image.type(torch.float32).to(device)

            logit = model(image)
            loss = criterion(logit, target)

            loss.backward()
            optimizer.step()

            current_loss += loss.item()
            meter.update(logit.detach().cpu(), target.detach().cpu())

        epoch_loss = current_loss / sample_num
        logging.info(f"current epoch loss: {epoch_loss}")
        epoch_dice, epoch_iou, epoch_sen, epoch_spf = meter.get_metrics()

        losses.append(epoch_loss)
        dice_scores.append(epoch_dice)
        jaccard_scores.append(epoch_iou)
        sen_scores.append(epoch_sen)
        spf_scores.append(epoch_spf)

        # testing the model
        if test_x is not None:
            model.eval()
            with torch.no_grad():
                test_x = torch.tensor(test_x).to(device)
                test_logits = model(test_x)

                test_loss = criterion(test_logits, test_y)

                if test_loss < best_loss:
                    best_loss = test_loss

            print("current best loss is: ", best_loss)


def test(model, test_x, test_y):
    model.eval()
    meter = Meter()

    with torch.no_grad():
        for (target, image) in zip(test_x, test_y):
            image = image.type(torch.float32)
            logit = model(image)
            meter.update(logit, target)

    epoch_dice, epoch_iou, epoch_sen, epoch_spf = meter.get_metrics()
    return epoch_dice