import os
import argparse
import nibabel as nib
import numpy as np
import torch
from torch.utils.data import DataLoader
from torch.utils.data import Dataset as tDataset
import torchvision.transforms as transforms
from dataset_partition import BasicPartitioner
import random


def read_nifti_seg_file(filepath):
    # Read file
    img = nib.load(filepath)
    # Get raw data H W D C
    img = img.get_fdata().astype(np.float32)
    # H W D C -> C H W D
    img = img.transpose(3, 0, 1, 2)
    mask = img.sum(0) > 0
    for k in range(img.shape[0]):
        x = img[k, ...]
        y = x[mask]

        # 对背景外的区域进行归一化
        x[mask] -= y.mean()
        x[mask] /= y.std()
        img[k, ...] = x

    return img


def get_nifti_seg_mask(file_path):
    mask = nib.load(file_path)
    mask = mask.get_fdata().astype(np.int64)

    return mask


class RandomRotFlip(object):
    """
    Crop randomly flip the dataset in a sample
    Args:
    output_size (int): Desired output size
    """

    def __call__(self, sample):
        image, mask = sample['image'], sample['mask']
        k = np.random.randint(0, 4)
        image = np.stack([np.rot90(x,k) for x in image],axis=0)
        mask = np.rot90(mask, k)
        axis = np.random.randint(1, 4)
        image = np.flip(image, axis=axis).copy()
        mask = np.flip(mask, axis=axis-1).copy()

        return {'image': image, 'mask': mask}


class RandomCrop(object):
    """
    Crop randomly the image in a sample
    Args:
    output_size (int): Desired output size
    """
    def __init__(self, output_size):
        self.output_size = output_size

    def __call__(self, sample):

        image, mask = sample['image'], sample['mask']

        (c, h, w, d) = image.shape
        h1 = np.random.randint(0, h - self.output_size[0])
        w1 = np.random.randint(0, w - self.output_size[1])
        d1 = np.random.randint(0, d - self.output_size[2])

        mask = mask[h1:h1 + self.output_size[0], w1:w1 + self.output_size[1], d1:d1 + self.output_size[2]]
        image = image[:,h1:h1 + self.output_size[0], w1:w1 + self.output_size[1], d1:d1 + self.output_size[2]]

        return {'image': image, 'mask': mask}


def augment_gaussian_noise(data_sample, noise_variance=(0, 0.1)):
    if noise_variance[0] == noise_variance[1]:
        variance = noise_variance[0]
    else:
        variance = random.uniform(noise_variance[0], noise_variance[1])
    data_sample = data_sample + np.random.normal(0.0, variance, size=data_sample.shape)
    return data_sample


class GaussianNoise(object):
    def __init__(self, noise_variance=(0, 0.1), p=0.5):
        self.prob = p
        self.noise_variance = noise_variance

    def __call__(self, sample):
        image = sample['image']
        mask = sample['mask']
        if np.random.uniform() < self.prob:
            image = augment_gaussian_noise(image, self.noise_variance)
        return {'image': image, 'mask': mask}


class ToTensor(object):
    """Convert ndarrays in sample to Tensors."""
    def __call__(self, sample):
        image = sample['image']
        mask = sample['mask']

        image = torch.from_numpy(image).float()
        mask = torch.from_numpy(mask).long()
        return {'image': image, 'mask': mask}


class BrainTumourImageDataset(tDataset):
    def __init__(self, srcpath, transform=None):
        self.srcpath = srcpath
        img_path = os.path.join(self.srcpath, "imagesTr")
        mask_path = os.path.join(self.srcpath, "labelsTr")
        self.img_list = [read_nifti_seg_file(os.path.join(img_path, img_name)) for img_name in os.listdir(img_path)]
        self.mask_list = [get_nifti_seg_mask(os.path.join(mask_path, mask_name)) for mask_name in os.listdir(mask_path)]
        self.transform = transform

    def __getitem__(self, index):
        # 加载图像
        image = self.img_list[index]
        mask = self.mask_list[index]
        sample = {'image': image, 'mask': mask}
        if self.transform:
            sample = self.transform(sample)
        return sample['image'], sample['mask']

    def __len__(self):
        return len(self.img_list)

    def collate(self, batch):
        return [torch.cat(v) for v in zip(*batch)]


class BrainTumourImage:
    def __init__(self, datadir):
        self.datadir = datadir
        self.train_size = {}

    def get_dataloader(self):
        # Picks up Image Paths from its respective folders and label them
        dataset = BrainTumourImageDataset(self.datadir, transform=transforms.Compose([
        RandomRotFlip(),
        RandomCrop((160, 160, 128)),
        GaussianNoise(p=0.1),
        ToTensor()
        ]))

        data_nums = len(dataset)
        train_size = int(data_nums * 0.8)
        test_size = data_nums - train_size
        train_idx = list(range(train_size))
        test_idx = list(range(test_size))
        np.random.shuffle(train_idx)
        np.random.shuffle(test_idx)
        trainset, testset = torch.utils.data.random_split(dataset, [train_size, test_size])

        trainloader = torch.utils.data.DataLoader(trainset,
                                                 shuffle=True,
                                                 batch_size=len(trainset))

        testloader = torch.utils.data.DataLoader(testset,
                                                  shuffle=True,
                                                  batch_size=len(testset))

        self.train_size = len(train_idx)
        self.test_size = len(test_idx)

        return trainloader, testloader


class BraTSPartitioner(BasicPartitioner):
    num_classes = 4


def prepare_dataset(datadir, datatype, partition, num_clients, pardir):
    print('Preparing dataset {}'.format("Brain Tumor Segmentation"))
    trainloader, testloader = BrainTumourImage(datadir).get_dataloader()
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

    fed_dataset = BraTSPartitioner(targets=targets, num_clients=num_clients, partition=partition)
    client_dict = fed_dataset.client_dict

    fed_file_path = os.path.join(pardir, "BraTS")

    for client_id, indices in client_dict.items():
        client_dir = os.path.join(fed_file_path, str(partition), datatype)
        os.makedirs(client_dir, exist_ok=True)

        client_targets = targets[indices]
        client_images = images[indices]

        client_data = (client_images, client_targets)

        client_file = os.path.join(client_dir, f"{client_id}.pt")

        torch.save(client_data, client_file)


if __name__ == "__main__":
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
    datadir = r"/home/wangx/data/Task01_BrainTumour"
    pardir = "/home/wangx/data/BraTS/BraTSPartition"
    datatype = "train"
    prepare_dataset(datadir,datatype,args.partition_schemes, args.num_clients, pardir)












