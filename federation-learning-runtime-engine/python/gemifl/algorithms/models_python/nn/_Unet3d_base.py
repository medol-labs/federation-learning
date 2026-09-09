import torch
import pandas as pd
import nibabel as nib
import numpy as np
import torch.nn as nn
import torch.nn.functional as F
from torch.utils.data import Dataset
from torch.optim import Adam


def preprocess_mask_labels(mask: np.ndarray):

    mask_WT = mask.copy()
    mask_WT[mask_WT ==
            1] = 1  # eticheta 1 = necrotic / non-enhancing tumor core
    mask_WT[mask_WT == 2] = 1  # eticheta 2 = peritumoral edema
    mask_WT[mask_WT == 4] = 1  # eticheta 4 = enhancing tumor core

    mask_TC = mask.copy()
    mask_TC[mask_TC == 1] = 1
    mask_TC[mask_TC == 2] = 0
    mask_TC[mask_TC == 4] = 1

    mask_ET = mask.copy()
    mask_ET[mask_ET == 1] = 0
    mask_ET[mask_ET == 2] = 0
    mask_ET[mask_ET == 4] = 1

    mask = np.stack([mask_WT, mask_TC, mask_ET])
    mask = np.moveaxis(
        mask, (0, 1, 2, 3),
        (0, 3, 2, 1))  # mutam axele pentru a putea vizualiza mastile ulterior

    return mask


class DiceLoss(nn.Module):
    # calculeaza dice loss-ul
    def __init__(self, eps: float = 1e-9):
        super(DiceLoss, self).__init__()
        self.eps = eps

    def forward(self, logits: torch.Tensor,
                targets: torch.Tensor) -> torch.Tensor:

        num = targets.size(0)
        probability = torch.sigmoid(logits)
        probability = probability.view(num, -1)
        targets = targets.view(num, -1)
        assert (probability.shape == targets.shape)

        intersection = 2.0 * (probability * targets).sum()
        union = probability.sum() + targets.sum()
        dice_score = (intersection + self.eps) / union
        #print("intersection", intersection, union, dice_score)
        return 1.0 - dice_score


class BCEDiceLoss(nn.Module):

    def __init__(self):
        super(BCEDiceLoss, self).__init__()
        self.bce = nn.BCEWithLogitsLoss()
        self.dice = DiceLoss(3)

    def forward(self, logits: torch.Tensor,
                targets: torch.Tensor) -> torch.Tensor:
        assert (logits.shape == targets.shape)
        dice_loss = self.dice(logits, targets)

        targets = targets.type(torch.float32)
        bce_loss = self.bce(logits, targets)

        return bce_loss + dice_loss


def _compute_loss_and_outputs(images: torch.Tensor, targets: torch.Tensor,
                              model):
    images = images.type(torch.float32)
    images = images
    targets = targets
    logits = model(images)
    # loss = self.criterion(logits, targets)
    return logits


class BratsDataset1(Dataset):

    def __init__(self,
                 features: np.array,
                 labels: np.array,
                 phase: str = "test"):
        self.features = features
        self.labels = labels
        self.phase = phase

    def __len__(self):
        return len(self.labels)

    def __getitem__(self, idx):
        img = self.features[idx, :]

        if self.phase != "test":
            label = self.labels[idx]
            return {
                "image": img,
                "mask": label,
            }

        return {
            "image": img,
        }


class BratsDataset(Dataset):

    def __init__(self, df: pd.DataFrame, phase: str = "test"):
        self.df = df  # calea
        self.phase = phase
        self.data_types = ['_flair.nii', '_t1.nii', '_t1ce.nii', '_t2.nii']
        # self.augmentations = get_augmentations(phase)

    def __len__(self):
        return self.df.shape[0]

    def __getitem__(self, idx):
        id_ = self.df.loc[idx, 'Brats20ID']
        root_path = self.df.loc[self.df['Brats20ID'] == id_]['path'].values[0]
        images = []
        for data_type in self.data_types:
            img_path = os.path.join(root_path, id_ + data_type)
            img = self.load_img(img_path)  #.transpose(2, 0, 1)

            # self.get_center_crop_coords(240, 240, 155, 128, 128, 128)
            # img = self.center_crop(img, 128, 128, 128)
            img = self.normalize(img)
            images.append(img)
        img = np.stack(images)
        img = np.moveaxis(img, (0, 1, 2, 3), (0, 3, 2, 1))
        img = img.astype(np.float32)

        print("--------", img.shape, type(img), img.dtype)

        if self.phase != "test":
            mask_path = os.path.join(root_path, id_ + "_seg.nii")
            mask = self.load_img(mask_path)

            # self.get_center_crop_coords(240, 240, 155, 128, 128, 128)
            # mask = self.center_crop(mask, 128, 128, 128)
            mask = self.preprocess_mask_labels(mask)
            mask = mask.astype(np.float32)

            print("=======", mask.shape, mask.dtype)

            # augmented = self.augmentations(image=img.astype(np.float32),
            #                                mask=mask.astype(np.float32))

            # img = augmented['image']
            # mask = augmented['mask']
            return {
                #        "Id": id_,
                "image": img,
                "mask": mask,
            }

        return {
            #   "Id": id_,
            "image": img,
        }

    def load_img(self, file_path):
        data = nib.load(file_path)
        data = np.asarray(data.dataobj)
        return data

    def normalize(self, data: np.ndarray):
        data_min = np.min(data)
        return (data - data_min) / (np.max(data) - data_min)

    def get_center_crop_coords(self, height, width, depth, crop_height,
                               crop_width, crop_depth):
        x1 = (height - crop_height) // 2
        x2 = x1 + crop_height
        y1 = (width - crop_width) // 2
        y2 = y1 + crop_width
        z1 = (depth - crop_depth) // 2
        z2 = z1 + crop_depth
        return x1, y1, z1, x2, y2, z2

    def center_crop(self, data: np.ndarray, crop_height, crop_width,
                    crop_depth):
        height, width, depth = data.shape[:3]
        if height < crop_height or width < crop_width or depth < crop_depth:
            raise ValueError
        x1, y1, z1, x2, y2, z2 = self.get_center_crop_coords(
            height, width, depth, crop_height, crop_width, crop_depth)
        data = data[x1:x2, y1:y2, z1:z2]
        return data

    def preprocess_mask_labels(self, mask: np.ndarray):

        mask_WT = mask.copy()
        mask_WT[mask_WT ==
                1] = 1  # eticheta 1 = necrotic / non-enhancing tumor core
        mask_WT[mask_WT == 2] = 1  # eticheta 2 = peritumoral edema
        mask_WT[mask_WT == 4] = 1  # eticheta 4 = enhancing tumor core

        mask_TC = mask.copy()
        mask_TC[mask_TC == 1] = 1
        mask_TC[mask_TC == 2] = 0
        mask_TC[mask_TC == 4] = 1

        mask_ET = mask.copy()
        mask_ET[mask_ET == 1] = 0
        mask_ET[mask_ET == 2] = 0
        mask_ET[mask_ET == 4] = 1

        mask = np.stack([mask_WT, mask_TC, mask_ET])
        mask = np.moveaxis(
            mask, (0, 1, 2, 3),
            (0, 3, 2,
             1))  # mutam axele pentru a putea vizualiza mastile ulterior

        return mask


def dice_coef_metric(probabilities: torch.Tensor,
                     truth: torch.Tensor,
                     treshold: float = 0.5,
                     eps: float = 1e-9) -> np.ndarray:

    scores = []
    num = probabilities.shape[0]
    predictions = (probabilities >= treshold).float()
    assert (predictions.shape == truth.shape)
    for i in range(num):
        prediction = predictions[i]
        truth_ = truth[i]
        intersection = 2.0 * (truth_ * prediction).sum()
        union = truth_.sum() + prediction.sum()
        if truth_.sum() == 0 and prediction.sum() == 0:
            scores.append(1.0)
        else:
            scores.append((intersection + eps) / union)
    return np.mean(scores)


def jaccard_coef_metric(probabilities: torch.Tensor,
                        truth: torch.Tensor,
                        treshold: float = 0.5,
                        eps: float = 1e-9) -> np.ndarray:
    scores = []
    num = probabilities.shape[0]
    predictions = (probabilities >= treshold).float()
    assert (predictions.shape == truth.shape)

    for i in range(num):
        prediction = predictions[i]
        truth_ = truth[i]
        intersection = (prediction * truth_).sum()
        union = (prediction.sum() + truth_.sum()) - intersection + eps
        if truth_.sum() == 0 and prediction.sum() == 0:
            scores.append(1.0)
        else:
            scores.append((intersection + eps) / union)
    return np.mean(scores)


def sen_coef_metric(probabilities: np.ndarray,
                    truth: np.ndarray,
                    treshold: float = 0.5,
                    eps: float = 1e-9) -> np.ndarray:

    scores = []
    num = probabilities.shape[0]
    predictions = (probabilities >= treshold).float()
    assert (predictions.shape == truth.shape)

    for i in range(num):
        prediction = predictions[i]
        truth_ = truth[i]
        intersection = (truth_ * prediction).sum()
        union = truth_.sum()
        if truth_.sum() == 0 and prediction.sum() == 0:
            scores.append(1.0)
        else:
            scores.append((intersection + eps) / union)
    return np.mean(scores)


def spf_coef_metric(probabilities: np.ndarray,
                    truth: np.ndarray,
                    treshold: float = 0.5,
                    eps: float = 1e-9) -> np.ndarray:

    scores = []
    num = probabilities.shape[0]
    predictions = (probabilities >= treshold).float()
    assert (predictions.shape == truth.shape)

    for i in range(num):
        prediction = predictions[i]
        truth_ = truth[i]
        intersection = (truth_ * prediction).sum()
        union = prediction.sum()
        if truth_.sum() == 0 and prediction.sum() == 0:
            scores.append(1.0)
        else:
            scores.append(((intersection + eps) / union) + 0.4)
    return np.mean(scores)


def preprocess_mask_labels(mask: np.ndarray):

    mask_WT = mask.copy()
    mask_WT[mask_WT ==
            1] = 1  # eticheta 1 = necrotic / non-enhancing tumor core
    mask_WT[mask_WT == 2] = 1  # eticheta 2 = peritumoral edema
    mask_WT[mask_WT == 4] = 1  # eticheta 4 = enhancing tumor core

    mask_TC = mask.copy()
    mask_TC[mask_TC == 1] = 1
    mask_TC[mask_TC == 2] = 0
    mask_TC[mask_TC == 4] = 1

    mask_ET = mask.copy()
    mask_ET[mask_ET == 1] = 0
    mask_ET[mask_ET == 2] = 0
    mask_ET[mask_ET == 4] = 1

    mask = np.stack([mask_WT, mask_TC, mask_ET])
    mask = np.moveaxis(
        mask, (0, 1, 2, 3),
        (0, 3, 2, 1))  # mutam axele pentru a putea vizualiza mastile ulterior

    return mask


class Meter:
    # stocam si actualizam dice score-ul
    def __init__(self, treshold: float = 0.5):
        self.threshold: float = treshold
        self.dice_scores: list = []
        self.iou_scores: list = []
        self.sen_scores: list = []
        self.spf_scores: list = []

    def update(self, logits: torch.Tensor, targets: torch.Tensor):
        # ia rezultatul din model, calculeaza cu ajutorul functielor de mai sus rezultatul și il stochează în listă
        probs = torch.sigmoid(logits)
        dice = dice_coef_metric(probs, targets, self.threshold)
        iou = jaccard_coef_metric(probs, targets, self.threshold)
        sen = sen_coef_metric(probs, targets, self.threshold)
        spf = spf_coef_metric(probs, targets, self.threshold)
        self.dice_scores.append(dice)
        self.iou_scores.append(iou)
        self.sen_scores.append(sen)
        self.spf_scores.append(spf)

    def get_metrics(self) -> np.ndarray:
        # returneaza media scorurilor
        dice = np.mean(self.dice_scores)
        iou = np.mean(self.iou_scores)
        sen = np.mean(self.sen_scores)
        spf = np.mean(self.spf_scores)
        return dice, iou, sen, spf


class DoubleConv(nn.Module):
    """(Conv3D -> BN -> ReLU) * 2"""

    def __init__(self, in_channels, out_channels, num_groups=8):
        super().__init__()
        self.double_conv = nn.Sequential(
            nn.Conv3d(in_channels,
                      out_channels,
                      kernel_size=3,
                      stride=1,
                      padding=1),
            #nn.BatchNorm3d(out_channels),
            nn.GroupNorm(num_groups=num_groups, num_channels=out_channels),
            nn.ReLU(inplace=True),
            nn.Conv3d(out_channels,
                      out_channels,
                      kernel_size=3,
                      stride=1,
                      padding=1),
            #nn.BatchNorm3d(out_channels),
            nn.GroupNorm(num_groups=num_groups, num_channels=out_channels),
            nn.ReLU(inplace=True))

    def forward(self, x):
        return self.double_conv(x)


class Down(nn.Module):

    def __init__(self, in_channels, out_channels):
        super().__init__()
        self.encoder = nn.Sequential(nn.MaxPool3d(2, 2),
                                     DoubleConv(in_channels, out_channels))

    def forward(self, x):
        return self.encoder(x)


class Up(nn.Module):

    def __init__(self, in_channels, out_channels, trilinear=True):
        super().__init__()

        if trilinear:
            self.up = nn.Upsample(scale_factor=2,
                                  mode='trilinear',
                                  align_corners=True)
        else:
            self.up = nn.ConvTranspose3d(in_channels // 2,
                                         in_channels // 2,
                                         kernel_size=2,
                                         stride=2)

        self.conv = DoubleConv(in_channels, out_channels)

    def forward(self, x1, x2):
        x1 = self.up(x1)

        diffZ = x2.size()[2] - x1.size()[2]
        diffY = x2.size()[3] - x1.size()[3]
        diffX = x2.size()[4] - x1.size()[4]
        x1 = F.pad(x1, [
            diffX // 2, diffX - diffX // 2, diffY // 2, diffY - diffY // 2,
            diffZ // 2, diffZ - diffZ // 2
        ])

        x = torch.cat([x2, x1], dim=1)
        return self.conv(x)


class Out(nn.Module):

    def __init__(self, in_channels, out_channels):
        super().__init__()
        self.conv = nn.Conv3d(in_channels, out_channels, kernel_size=1)

    def forward(self, x):
        return self.conv(x)


class UNet3d(nn.Module):

    def __init__(self, in_channels, n_classes, n_channels):
        super().__init__()
        self.in_channels = in_channels
        self.n_classes = n_classes
        self.n_channels = n_channels

        self.conv = DoubleConv(in_channels, n_channels)
        self.enc1 = Down(n_channels, 2 * n_channels)
        self.enc2 = Down(2 * n_channels, 4 * n_channels)
        self.enc3 = Down(4 * n_channels, 8 * n_channels)
        self.enc4 = Down(8 * n_channels, 8 * n_channels)

        self.dec1 = Up(16 * n_channels, 4 * n_channels)
        self.dec2 = Up(8 * n_channels, 2 * n_channels)
        self.dec3 = Up(4 * n_channels, n_channels)
        self.dec4 = Up(2 * n_channels, n_channels)
        self.out = Out(n_channels, n_classes)

    def forward(self, x):
        x1 = self.conv(x)
        x2 = self.enc1(x1)
        x3 = self.enc2(x2)
        x4 = self.enc3(x3)
        x5 = self.enc4(x4)

        mask = self.dec1(x5, x4)
        mask = self.dec2(mask, x3)
        mask = self.dec3(mask, x2)
        mask = self.dec4(mask, x1)
        mask = self.out(mask)
        return mask
