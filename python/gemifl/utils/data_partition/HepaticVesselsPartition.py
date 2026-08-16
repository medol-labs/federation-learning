import os
import glob
import argparse
import nibabel as nib
import numpy as np
import torch
import cv2
from torch.utils.data import DataLoader
from torch.utils.data import Dataset as tDataset
from pathlib import Path
from albumentations import (HorizontalFlip, ShiftScaleRotate, Normalize, Resize, Compose, GaussNoise)
from albumentations.pytorch import ToTensorV2
from sklearn.model_selection import train_test_split
from dataset_partition import BasicPartitioner


def create_dir(dir):
    if not os.path.exists(dir):
        os.makedirs(dir)


def data_preprocess(imageTrPath, savePath):
    imgSavePath = os.path.join(savePath, "imagesTr")
    labelSavePath = os.path.join(savePath, "labelsTr")
    create_dir(imgSavePath)
    create_dir(labelSavePath)
    imgPathlist = Path(imageTrPath).glob('hepaticvessel_*.gz')
    num = 1
    for filename in imgPathlist:
        img = nib.load(filename)
        nii_data = np.rot90(img.get_fdata(), 3, axes=(1, 0))
        # nii_data[nii_data<-1024]=-1024
        print(filename, '-', nii_data.shape)

        maskFileName = str(filename).replace("images", "labels")
        img = nib.load(maskFileName)
        nii_mask = np.rot90(img.get_fdata(), 3, axes=(1, 0))
        for j in range(nii_mask.shape[2]):
            img0 = np.array(np.round(nii_data[:, :, j], 0), dtype='int16')
            mask0 = np.array(nii_mask[:, :, j], dtype='int16')

            np.savez_compressed(imgSavePath + "/" + str(num).zfill(3) + '_' + str(j).zfill(3) + '.npz', img=img0)
            np.savez_compressed(labelSavePath + "/" + str(num).zfill(3) + '_' + str(j).zfill(3) + '.npz', img=mask0)
        num += 1

def data_selection(imgPathTr, dstPath):
    imgDstPath = os.path.join(dstPath, "imagesTr")
    labelDstPath = os.path.join(dstPath, "labelsTr")
    create_dir(imgDstPath)
    create_dir(labelDstPath)
    imgPathlist = Path(imgPathTr).glob('*.npz')
    for filename in imgPathlist:
        s1 = os.path.split(filename)[-1]
        s2 = s1.split('_')[0]
        s3 = s1.split('_')[1].split('.')[0]
        if int(s2) <= 100:
            maskFileName = str(filename).replace("images", "labels")
            mask0 = np.load(maskFileName)['img']
            img0 = np.load(filename)['img']
            # assuming vessels as background
            mask0[mask0 != 2] = 0
            mask0[mask0 == 2] = 1
            np.savez_compressed(imgDstPath + '/' + s2 + '_' + s3 + '.npz', img=img0)
            np.savez_compressed(labelDstPath + '/' + s2 + '_' + s3 + '.npz', img=mask0)


def window(img, mean=81, std=44):
    upper, lower = mean+3*std, mean-3*std
    X = np.clip(img.copy(), lower, upper)
    X = X - np.min(X)
    X = X / (np.max(X)/255.0)
    X = cv2.merge([X,X,X])
    X = X.astype('uint8')
    return X

def dataset_split(datadir):
    imgSavePath = os.path.join(datadir, "imagesTr")
    labelSavePath = os.path.join(datadir, "labelsTr")
    train_subject, test_subject = train_test_split(np.arange(1, 101, 1), test_size=0.2, random_state=0)

    train_slices = []

    for i in train_subject:
        train_slices += glob.glob(imgSavePath + "/" + str(i).zfill(3) + '*.npz')


    test_slices = []
    for i in test_subject:
        test_slices += glob.glob(labelSavePath + "/" + str(i).zfill(3) + '*.npz')
    test_slices = sorted(test_slices)

    return train_slices, test_slices



def get_transforms(phase, size, mean, std):
    list_transforms = []
    if phase == "train":
        list_transforms.extend(
            [
                HorizontalFlip(),
                ShiftScaleRotate(
                    shift_limit=0,  # no resizing
                    scale_limit=0.1,
                    rotate_limit=10, # rotate
                    p=0.5,
                    border_mode=cv2.BORDER_CONSTANT
                ),
#                 GaussNoise(),
            ]
        )
    list_transforms.extend(
        [
            Resize(size, size),
            Normalize(mean=mean, std=std, p=1),
            ToTensorV2()
        ]
    )
    list_trfms = Compose(list_transforms)
    return list_trfms



class HepaticVesselsImageDataset(tDataset):
    def __init__(self, files_list, size, mean, std, phase):
        self.size = size
        self.mean = mean
        self.std = std
        self.phase = phase
        self.transforms = get_transforms(phase, size, mean, std)
        self.fnames = files_list

    def __getitem__(self, idx):
        image_path = self.fnames[idx]
        image = window(np.load(image_path)['img'])
        image = image.astype(float)
        mask = np.load(image_path.replace('imagesTr', 'labelsTr'))['img']
        mask = mask.astype(float)

        augmented = self.transforms(image=image, mask=mask)
        image = augmented['image']
        mask = augmented['mask']
        return image, mask

    def __len__(self):
        return len(self.fnames)


class HepaticVesselsImage:
    def __init__(self, datadir):
        self.datadir = datadir
        self.size = 256
        self.mean = (0.485, 0.456, 0.406)
        self.std = (0.229, 0.224, 0.225)
        self.train_slices, self.test_slices = dataset_split(self.datadir)


    def get_dataloader(self):
        # Picks up Image Paths from its respective folders and label them
        trainset = HepaticVesselsImageDataset(self.train_slices, self.size, self.mean, self.std,"train")

        trainloader = torch.utils.data.DataLoader(trainset,
                                                 shuffle=True,
                                                 batch_size=len(trainset))

        testset = HepaticVesselsImageDataset(self.test_slices, self.size, self.mean, self.std, "test")
        testloader = torch.utils.data.DataLoader(testset,
                                                  shuffle=True,
                                                  batch_size=len(testset))


        return trainloader, testloader

class HepaticVesselsPartitioner(BasicPartitioner):
    num_classes = 3


def prepare_dataset(datadir, datatype, partition, num_clients, pardir):
    print('Preparing dataset {}'.format("Brain Tumor Segmentation"))
    trainloader, testloader = HepaticVesselsImage(datadir).get_dataloader()
    images = []
    masks = []
    if datatype == "test":
        dataloader = testloader
    else:
        dataloader = trainloader
    for data in dataloader:
        imgs, labels = data
        masks.append(labels.numpy())
        for input_tensor in imgs:
            images.append(input_tensor)

    targets = np.concatenate(masks)
    # print("标签维度： ", targets.shape)

    images = torch.stack(images)
    # print("图像维度： ", images.shape)

    # split the dataset for federated learning

    fed_dataset = HepaticVesselsPartitioner(targets=targets, num_clients=num_clients, partition=partition)
    client_dict = fed_dataset.client_dict

    fed_file_path = os.path.join(pardir, "HepaticVessels")

    for client_id, indices in client_dict.items():
        client_dir = os.path.join(fed_file_path, str(partition), datatype)
        os.makedirs(client_dir, exist_ok=True)

        client_targets = targets[indices]
        client_images = images[indices]

        client_data = (client_images, client_targets)

        client_file = os.path.join(client_dir, f"{client_id}.pt")

        torch.save(client_data, client_file)

if __name__=="__main__":
    imgTrPath = "/home/wangx/data/Task08_HepaticVessel/imagesTr/"
    savePath = "/home/wangx/data/MSD/Task08_HepaticVessel/2D-data/"
    # data_preprocess(imgTrPath, savePath)
    imgPathTr = os.path.join(savePath, "imagesTr")
    datadir= "/home/wangx/data/MSD/Task08_HepaticVessel/partOfData/"
    pardir = "/home/wangx/data/MSD/Partation/"
    # data_selection(imgPathTr, datadir)
    parser_prepare_dataset = argparse.ArgumentParser()

    parser_prepare_dataset.add_argument(
        "--partition-schemes",
        type=str,
        help="Partition schemes. Only supports noniid-#label, noniid-labeldir, unbalance and iid",
        required=True
    )

    parser_prepare_dataset.add_argument(
        "--num-clients",
        type=int

    )
    args = parser_prepare_dataset.parse_args()
    datatype = "test"
    prepare_dataset(datadir, datatype, args.partition_schemes, args.num_clients, pardir)
