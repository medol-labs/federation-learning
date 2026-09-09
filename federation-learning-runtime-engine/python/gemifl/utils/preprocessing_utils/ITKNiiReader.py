# -*- coding: utf-8 -*-
from monai.data import ITKReader, PILReader
from monai.data import ImageDataset, DataLoader
from monai.transforms import EnsureChannelFirst, Compose, RandRotate90, Resize, ScaleIntensity
import os
from glob import glob
import numpy as np
from collections import Counter
import torch
class ImgReader:
    def __init__(self, basepath, labels=[]):
        self.imageslist = []
        self.y = []
        if len(labels) == 0:
            self.labels = os.listdir(basepath)
        self.label_len = len(self.labels)

        for idx, lb in enumerate(self.labels):
            imglist = glob(os.path.join(basepath, lb, "*.nii"))
            self.imageslist += imglist
            self.y = self.y + [idx for i in range(len(imglist))]
        self.y = np.array(self.y)

    def data_stat(self):
        stat = {"data_count": len(self.y)}
        cnt = Counter(self.y)
        for k in cnt:
            stat[self.labels[k]] = cnt[k]

        return stat

class ITKNiiReader:
    def __init__(self, traindir, valdir, train_batch_size, valid_batch_size):
        self.datadir = {"train": traindir, "val": valdir}
        self.batch_size = {"train": train_batch_size, "val": valid_batch_size}
        self.data_transforms = Compose([ScaleIntensity(), EnsureChannelFirst(), Resize((96, 96, 96))])


        self.dataset_size = {}

    def get_dataloader(self, dtype):
        imgdata = ImgReader(self.datadir[dtype])
        self.dataset_size[dtype] = len(imgdata.imageslist)
        data_ds = ImageDataset(image_files=imgdata.imageslist, labels=imgdata.y, transform=self.data_transforms,reader=ITKReader)
        dataloader = DataLoader(data_ds, batch_size=2, shuffle=True, num_workers=2,pin_memory=torch.cuda.is_available())

        return dataloader
