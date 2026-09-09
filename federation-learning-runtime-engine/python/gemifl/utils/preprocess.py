# -*- coding: utf-8 -*-
import os
import nibabel as nib
from torchvision import datasets, models, transforms
import torch
from torch.utils.data.sampler import SubsetRandomSampler
from torch.utils.data import Dataset as tDataset
from torch.utils.data import TensorDataset, RandomSampler, SequentialSampler, DataLoader, WeightedRandomSampler
from transformers import *
import numpy as np
import pandas as pd
import scipy
import re
import random

class DenseNetImage:
    def __init__(self, traindir, valdir, train_batch_size, valid_batch_size):
        self.datadir = {"train": traindir, "val": valdir}
        self.batch_size = {"train": train_batch_size, "val": valid_batch_size}
        self.data_transforms = {"train": transforms.Compose([
            # Resizes all images into same dimension
            transforms.Resize((150, 150)),
            transforms.ToTensor()  # Coverts into Tensors
        ]),  # Normalizes
            "val": transforms.Compose([
                transforms.Resize((150, 150)),
                transforms.ToTensor()
            ])}
        self.dataset_size = {}

    def get_dataloader(self, dtype):
        # Picks up Image Paths from its respective folders and label them
        imgdata = datasets.ImageFolder(
            self.datadir[dtype], transform=self.data_transforms[dtype])
        nums = len(imgdata)
        data_idx = list(range(nums))
        np.random.shuffle(data_idx)
        data_sampler = SubsetRandomSampler(data_idx)
        batch_size = self.batch_size.get(dtype)
        if batch_size == -1:
            batch_size = nums
        dataloader = torch.utils.data.DataLoader(imgdata,
                                                 sampler=data_sampler,
                                                 batch_size=batch_size)
        self.dataset_size[dtype] = len(data_idx)
        return dataloader


class CovidxCXR(DenseNetImage):
    def __init__(self, traindir, valdir, train_batch_size, valid_batch_size):
        super().__init__(traindir, valdir, train_batch_size, valid_batch_size)
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

    def get_dataloader(self, dtype):
        # Picks up Image Paths from its respective folders and label them
        if len(os.listdir(self.datadir[dtype])) != 0:
            imgdata = datasets.ImageFolder(
                self.datadir[dtype], transform=self.data_transforms[dtype])
            nums = len(imgdata)
            data_idx = list(range(nums))
            np.random.shuffle(data_idx)
            data_sampler = SubsetRandomSampler(data_idx)
            batch_size = self.batch_size.get(dtype)
            if batch_size == -1:
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
            batch_size = self.batch_size.get(dtype)
            if batch_size == -1:
                batch_size = nums
            dataloader = torch.utils.data.DataLoader(imgdata,
                                                     sampler=data_sampler,
                                                     batch_size=batch_size)

            self.dataset_size[dtype] = len(data_idx)

            return dataloader


class ProstateImageDataset(tDataset):
    def __init__(self, img_dir, crop_size):
        self.img_dir = img_dir
        self.img_paths = [os.path.join(img_dir, i) for i in os.listdir(img_dir) if i.endswith('npz')]
        self.crop_size = crop_size

    def __len__(self):
        return len(self.img_paths)

    def _center_crop(self, data):
        start_depth, start_width, start_height = [(data.shape[i] - self.crop_size[i]) // 2 for i in [1, 2, 3]]
        end_depth, end_width, end_height = [(data.shape[i] - self.crop_size[i]) // 2 + self.crop_size[i] for i in
                                            [1, 2, 3]]
        return data[:, start_depth:end_depth, start_height:end_height, start_width:end_width]

    def __getitem__(self, idx):
        img_path = self.img_paths[idx]
        data = np.load(img_path)
        image, label = self._center_crop(data['data']), self._center_crop(data['seg'])
        return image, label


class ProstateImage:
    def __init__(self, traindir, valdir, train_batch_size, valid_batch_size):
        self.datadir = {"train": traindir, "val": valdir}
        self.batch_size = {"train": train_batch_size, "val": valid_batch_size}
        self.dataset_size = {}

    def get_dataloader(self, dtype):
        # Picks up Image Paths from its respective folders and label them
        imgdata = ProstateImageDataset(self.datadir[dtype], (1, 23, 320, 320))
        nums = len(imgdata)
        data_idx = list(range(nums))
        np.random.shuffle(data_idx)
        batch_size = self.batch_size.get(dtype)
        if batch_size == -1:
            batch_size = nums
        dataloader = torch.utils.data.DataLoader(imgdata,
                                                 shuffle=True,
                                                 batch_size=batch_size)

        self.dataset_size[dtype] = len(data_idx)
        return dataloader


class CsvParser:
    def __init__(self, traindir, valdir, train_batch_size, valid_batch_size, header=None, encoding="utf-8", label_col="label"):
        self.datadir = {"train": traindir, "val": valdir}
        self.batch_size = {"train": train_batch_size, "val": valid_batch_size}
        self.dataset_size = {}
        self.header = header
        if self.header == "None":
            self.header = None
        self.encoding = encoding
        self.label_col = label_col

    def get_dataloader(self, dtype):
        raw = pd.read_csv(
            self.datadir[dtype], header=self.header, encoding=self.encoding)
        y = raw[self.label_col].values
        target_mapper = {class_label: index for index,
                         class_label in enumerate(sorted(list(set(y))))}
        y = np.vectorize(target_mapper.get)(y)
        features = [f for f in raw if f != self.label_col]
        x = raw[features].values
        return x, y


class CsvGenerator:
    def __init__(self, traindir, valdir, train_batch_size, valid_batch_size, header=None, encoding="utf-8", label_col="label"):
        self.datadir = {"train": traindir, "val": valdir}
        self.batch_size = {"train": train_batch_size, "val": valid_batch_size}
        self.dataset_size = {}
        self.header = header
        if self.header == "None":
            self.header = None
        self.encoding = encoding
        self.label_col = label_col
    def get_dataloader(self, dtype):
        '''
        for pytorch style model, pending....
        :param dtype:
        :return:
        '''
        raw = pd.read_csv(
            self.datadir[dtype], header=self.header, encoding=self.encoding)
        y = raw[self.label_col].values
        target_mapper = {class_label: index for index,
                         class_label in enumerate(sorted(list(set(y))))}
        y = np.vectorize(target_mapper.get)(y)
        features = [f for f in raw if f != self.label_col]
        x = raw[features].values
        nums = len(x)
        data_idx = list(range(nums))
        data_sampler = SubsetRandomSampler(data_idx)
        batch_size = self.batch_size.get(dtype)

        if scipy.sparse.issparse(x):
            dataloader = DataLoader(
                SparseTorchDataset(x.astype(np.float32), y),
                batch_size=batch_size,
                sampler=data_sampler
            )
        else:
            dataloader = DataLoader(
                TorchDataset(x.astype(np.float32), y),
                batch_size=batch_size,
                sampler=data_sampler
            )
        return dataloader


class SparseTorchDataset(tDataset):
    """
    Format for csr_matrix

    Parameters
    ----------
    X : CSR matrix
        The input matrix
    y : 2D array
        The one-hot encoded target
    """

    def __init__(self, x, y):
        self.x = x
        self.y = y

    def __len__(self):
        return self.x.shape[0]

    def __getitem__(self, index):
        x = torch.from_numpy(self.x[index].toarray()[0]).float()
        y = self.y[index]
        return x, y


class TorchDataset(tDataset):
    """
    Format for numpy array

    Parameters
    ----------
    X : 2D array
        The input matrix
    y : 2D array
        The one-hot encoded target
    """

    def __init__(self, x, y):
        self.x = x
        self.y = y

    def __len__(self):
        return len(self.x)

    def __getitem__(self, index):
        x, y = self.x[index], self.y[index]
        return x, y

def text_preprocessing(text):
    """
    - Remove entity mentions (eg. '@united')
    - Correct errors (eg. '&amp;' to '&')
    @param    text (str): a string to be processed.
    @return   text (Str): the processed string.
    """
    try:
        # Remove '@name'
        text = re.sub(r'(@.*?)[\s]', ' ', text)
        # Replace '&amp;' with '&'
        text = re.sub(r'&amp;', '&', text)
        # Remove trailing whitespace
        text = re.sub(r'\s+', ' ', text).strip()
    except:
        print(text)
        text = re.sub(r'(@.*?)[\s]', ' ', text)
    return text

def preprocessing_for_bert(textdata, tokenizer, pad):
    """Perform required preprocessing steps for pretrained BERT.
    @param    data (np.array): Array of texts to be processed.
    @return   input_ids (torch.Tensor): Tensor of token ids to be fed to a model.
    @return   attention_masks (torch.Tensor): Tensor of indices specifying which
                  tokens should be attended to by the model.
    """
    # Create empty lists to store outputs
    input_ids = []
    attention_masks = []
    # For every sentence...
    for sent in textdata:
        # `encode_plus` will:
        #    (1) Tokenize the sentence
        #    (2) Add the `[CLS]` and `[SEP]` token to the start and end
        #    (3) Truncate/Pad sentence to max length
        #    (4) Map tokens to their IDs
        #    (5) Create attention mask
        #    (6) Return a dictionary of outputs
        encoded_sent = tokenizer.encode_plus(
            text=text_preprocessing(sent),  # Preprocess sentence
            add_special_tokens=True,        # Add `[CLS]` and `[SEP]`
            max_length=pad,                  # Max length to truncate/pad
            padding='max_length',         # Pad sentence to max length
            truncation=True,
            return_tensors='pt',           # Return PyTorch tensor
            return_attention_mask=True      # Return attention mask
        )

        # Add the outputs to the lists
        input_ids.append(encoded_sent.get('input_ids').tolist()[0])
        attention_masks.append(encoded_sent.get('attention_mask').tolist()[0])
        # try:
        #     torch.tensor(input_ids)
        # except:
        #     print(input_ids)
        #     raise
    # Convert lists to tensors
    input_ids = torch.tensor(input_ids)
    attention_masks = torch.tensor(attention_masks)

    return input_ids, attention_masks

class HuggingfacePretrainTokenizer:
    def __init__(self, traindir, valdir, train_batch_size, valid_batch_size, pretrain, max_length,sep="\t", text_col="text",label_col="label"):
        self.datadir = {"train": traindir, "val": valdir}
        self.batch_size = {"train": train_batch_size, "val": valid_batch_size}
        self.dataset_size = {}
        self.max_length = max_length
        self.tokenizer = AutoTokenizer.from_pretrained(pretrain)
        self.text_col = text_col
        self.label_col = label_col
        self.sep = sep

    def get_dataloader(self, dtype):
        batch_size = self.batch_size.get(dtype)
        df = pd.read_csv(self.datadir[dtype], sep=self.sep)
        inputs, masks = preprocessing_for_bert(df[self.text_col], self.tokenizer, self.max_length)
        # Convert other data types to torch.Tensor
        labels = torch.FloatTensor(df[self.label_col].apply(lambda x: eval(x)))
        # Create the DataLoader for our training set
        dataset = TensorDataset(inputs, masks, labels)
        data_sampler = RandomSampler(dataset)
        dataloader = DataLoader(dataset, sampler=data_sampler, batch_size=batch_size)
        return dataloader


class BrainTumourImage(tDataset):
    def __init__(self, traindir, valdir, train_batch_size, valid_batch_size):
        self.datadir = {"train": traindir, "val": valdir}
        self.batch_size = {"train": train_batch_size, "val": valid_batch_size}
        self.dataset_size = {}

    def get_dataloader(self, dtype):
        # Picks up Image Paths from its respective folders and label them
        data = torch.load(self.datadir[dtype])
        nums = len(data)
        data_idx = list(range(nums))
        np.random.shuffle(data_idx)
        batch_size = self.batch_size.get(dtype)
        if batch_size == -1:
            batch_size = nums

        imgdata = data[0]
        maskdata = torch.LongTensor(data[1])
        dataset = torch.utils.data.TensorDataset(imgdata, maskdata)

        dataloader = torch.utils.data.DataLoader(dataset,
                                                 shuffle=True,
                                                 batch_size=batch_size)

        self.dataset_size[dtype] = len(data_idx)

        return dataloader


class HepaticVesselsImage(BrainTumourImage):
    def get_dataloader(self, dtype):
        # Picks up Image Paths from its respective folders and label them
        data = torch.load(self.datadir[dtype])
        nums = len(data)
        data_idx = list(range(nums))
        np.random.shuffle(data_idx)
        batch_size = self.batch_size.get(dtype)
        if batch_size == -1:
            batch_size = nums

        imgdata = data[0]
        maskdata = torch.LongTensor(data[1])
        dataset = torch.utils.data.TensorDataset(imgdata, maskdata)

        dataloader = torch.utils.data.DataLoader(dataset,
                                                 shuffle=True,
                                                 batch_size=batch_size)

        self.dataset_size[dtype] = len(data_idx)

        return dataloader


class CovidxCRXPT(BrainTumourImage):
    def get_dataloader(self, dtype):
        # Picks up Image Paths from its respective folders and label them
        data = torch.load(self.datadir[dtype])
        nums = len(data)
        data_idx = list(range(nums))
        np.random.shuffle(data_idx)
        batch_size = self.batch_size.get(dtype)
        if batch_size == -1:
            batch_size = nums

        imgdata = data[0]
        labeldata = torch.LongTensor(data[1])
        dataset = torch.utils.data.TensorDataset(imgdata, labeldata)

        dataloader = torch.utils.data.DataLoader(dataset,
                                                 shuffle=True,
                                                 batch_size=batch_size)

        self.dataset_size[dtype] = len(data_idx)

        return dataloader


if __name__ == "__main__":
    traindir = "/home/wangx/data/covidxcxr_splited/bimcv/train"
    valdir = "/home/wangx/data/covidxcxr_splited/bimcv/train"
    train_batch_size = 32
    valid_batch_size = 32
    dl = CovidxCXR(traindir, valdir, train_batch_size, valid_batch_size)
    for inputs, labels in dl.get_dataloader("train"):
        inputs = inputs.to("cuda:0")
        labels = labels.to("cuda:0")  # [32] longTensor
        print("labels : ", labels)
