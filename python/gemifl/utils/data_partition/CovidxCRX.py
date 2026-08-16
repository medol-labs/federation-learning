import os
import argparse
import torch
import numpy as np
import hashlib
import requests
import shutil
from tqdm import tqdm
from pathlib import Path
from torch.utils.data.sampler import SubsetRandomSampler
from torchvision import datasets, models, transforms
from torchvision.datasets.utils import download_and_extract_archive, download_url
from torch.utils.data import Dataset
from PIL import Image

from dataset_partition import BasicPartitioner


class CovidxCXR:
    def __init__(self, traindir, valdir):
        self.datadir = {"train": traindir, "val": valdir}
        self.data_transforms = {"train": transforms.Compose([
            # Resizes all images into same dimension
            transforms.RandomRotation(degrees=(0, 25)),
            transforms.RandomHorizontalFlip(),
            transforms.Resize((224, 224)),
            transforms.ToTensor()  # Coverts into Tensors
        ]),  # Normalizes
            "val": transforms.Compose([
                transforms.Resize((224, 224)),
                transforms.ToTensor()
            ])}
        self.dataset_size = {}

    def get_dataloader(self, dtype):
        # Picks up Image Paths from its respective folders and label them
        if len(os.listdir(self.datadir[dtype])) != 0:
            imgdata = datasets.ImageFolder(
                self.datadir[dtype], transform=self.data_transforms[dtype])
            nums = len(imgdata)
            data_idx = list(range(nums))
            np.random.shuffle(data_idx)
            data_sampler = SubsetRandomSampler(data_idx)
            batch_size = nums
            dataloader = torch.utils.data.DataLoader(imgdata,
                                                     sampler=data_sampler,
                                                     batch_size=batch_size,
                                                     num_workers=4,
                                                     pin_memory=True)

            return dataloader

        else:
            imgdata = torch.tensor([])
            nums = len(imgdata)
            data_idx = list(range(nums))
            np.random.shuffle(data_idx)
            data_sampler = SubsetRandomSampler(data_idx)
            # print("data_sampler: ", data_sampler)
            batch_size = nums

            dataloader = torch.utils.data.DataLoader(imgdata,
                                                     sampler=data_sampler,
                                                     batch_size=batch_size)

            self.dataset_size[dtype] = len(data_idx)

            return dataloader


class CovidxCRXPartition(BasicPartitioner):
    num_classes = 2


def prepare_dataset(traindir, valdir, dtype, partition, num_clients, pardir):
    print('Preparing dataset {}'.format("CovidxCRX"))
    dataloader = CovidxCXR(traindir, valdir).get_dataloader(dtype)
    images = []
    targets = []

    for data in dataloader:
        imgs, batch_targets = data
        targets.append(batch_targets.numpy())
        for input_tensor in imgs:
            images.append(input_tensor)

    targets = np.concatenate(targets)
    print("标签维度： ", targets.shape)

    images = torch.stack(images)
    print("图像维度： ", images.shape)

    # split the dataset for federated learning

    fed_dataset = CovidxCRXPartition(targets=targets, num_clients=num_clients, partition=partition)
    client_dict = fed_dataset.client_dict

    fed_file_path = os.path.join(pardir, "CovidxCRX")

    for client_id, indices in client_dict.items():
        client_dir = os.path.join(fed_file_path, str(partition), dtype)
        os.makedirs(client_dir, exist_ok=True)

        client_targets = targets[indices]
        client_images = images[indices]

        client_data = (client_images, client_targets)

        client_file = os.path.join(client_dir, f"{client_id}.pt")

        torch.save(client_data, client_file)



if __name__ == "__main__":

    traindir = r"/home/wangx/data/COVID19Detection/train/"
    valdir = r"/home/wangx/data/COVID19Detection/test/"
    pardir = "/home/wangx/data/CovidPartition/Partition"
    dtype = "train"
    partition = "iid" #noniid-#label, noniid-labeldir, unbalance and iid
    num_clients= 8
    prepare_dataset(traindir, valdir, dtype, partition, num_clients, pardir)


