# FedMedMNIST

import os
import argparse
import numpy as np
import hashlib
import requests
import shutil
from tqdm import tqdm
from pathlib import Path


from torchvision.datasets.utils import download_and_extract_archive, download_url
from torch.utils.data import Dataset
from PIL import Image

from dataset_partition import BasicPartitioner

# setup data directory
def get_MedMNIST_root():

    workspace_dir = os.path.expanduser('~/medfl.workspace')
    raw_workspace_dir = './medfl.workspace'
    if not os.path.exists(workspace_dir):
        os.makedirs(workspace_dir)

    dataset_root_dir = os.path.join(workspace_dir, 'dataset')
    if not os.path.exists(dataset_root_dir):
        os.makedirs(dataset_root_dir)

    medmnist_dir = os.path.join(dataset_root_dir, 'medmnist')
    if not os.path.exists(medmnist_dir):
        os.makedirs(medmnist_dir)
    
    return medmnist_dir

MedMNIST_DEFAULT_ROOT = get_MedMNIST_root




def download_MedMNIST_dataset(datasetname):
    """
    Download the MedMNIST datasets.
    Parameters
        ----------
        datasetname: str
            The dataset need to be downloaded from MedMNIST. The MedMNIST dataset consists of 
            12 pre-processed 2D datasets and 6 pre-processed 3D datasets
        output_folder : str
            The folder where to download the dataset.
    """
    os.makedirs('medmnist', exist_ok=True)
    
    filename = "{}.npz".format(datasetname)
    filepath = Path('medmnist').joinpath(filename)
    if os.path.isfile(filepath):
        print(f"{filepath} already exists.")
        return filepath
    
    # download
    url = MedMNIST_INFO_DICT[datasetname]["url"]

    with requests.get(url, stream=True) as response:
        # Raise error if not 200
        response.raise_for_status()
        file_size = int(response.headers.get("Content-Length",0))
        desc = "(Unknown total file size)" if file_size == 0 else ""
        print(f"Downloading to {MedMNIST_DEFAULT_ROOT}")
        with tqdm.wrapattr(response.raw, "read", total=file_size, desc=desc) as r_raw:
            with open(filepath, "wb") as f:
                shutil.copyfileobj(r_raw,f)
    
    # check md5
    md5=MedMNIST_INFO_DICT[datasetname]["MD5"]
    with open(filepath, "rb") as f:
        file_md5 = hashlib.md5(f.read()).hexdigest()
    if file_md5 != md5:
        os.remove(filepath)
        raise RuntimeError(f"MD5 mismatch for file {filename}. Expected {md5}, but got {file_md5}")
    
    return filepath

"""
    try:
        download_url(url=MedMNIST_INFO_DICT[datasetname]["url"],
                     root=str(MedMNIST_DEFAULT_ROOT), 
                     filename="{}.npz".format(datasetname),
                     md5=MedMNIST_INFO_DICT[datasetname]["MD5"])
    except:
        raise RuntimeError('Something went wrong when downloading! ' +
                            'Go to the MedMNIST homepage to download manually. ' +
                            MedMNIST_HOMEPAGE_URL)
"""

MedMNIST_DATASETS = ['pathmnist', 'chestmnist', 'dermamnist', 'octmnist', 'pneumoniamnist', 'retinamnist', 'breastmnist', 
                     'bloodmnist', 'tissuemnist', 'organamnist', 'organcmnist', 'organsmnist', 'organmnist3d', 'nodulemnist3d',
                     'adrenalmnist3d', 'fracturemnist3d', 'vesselmnist3d', 'synapsemnist3d']


class MedMNIST (Dataset):

    dataset_name = ...

    def __init__(self, split, transform=None, target_transform=None, as_rgb=False, dataset_dir=None):
        
        if dataset_dir is not None and os.path.exists(dataset_dir):
            self.dataset_dir = dataset_dir
        else:
            raise RuntimeError("Failed to setup the default `MedMNIST` directory. " +
                               "Please specify and create the `MedMNIST` directory manually.")
        """
        if not os.path.exists(
                os.path.join(self.dataset_dir, "{}.npz".format(self.dataset_name))):
            raise RuntimeError("Dataset not found." +
                               "Please download dataset first")"""
        
        # load dataset
        npz_file = np.load(self.dataset_dir)

        self.info = MedMNIST_INFO_DICT[self.dataset_name]

        self.split = split
        self.transform = transform
        self.target_transform = target_transform
        self.as_rgb = as_rgb

        if self.split == 'train':
            self.imgs = npz_file['train_images']
            self.labels = npz_file['train_labels']
        elif self.split == 'test':
            self.imgs = npz_file['test_images']
            self.labels = npz_file['test_labels']
        elif self.split == 'val':
            self.imgs = npz_file['val_images']
            self.labels = npz_file['val_labels']
        else:
            raise ValueError
        
    def __len__(self):
        return self.imgs.shape[0]
    

class MedMNIST2D(MedMNIST):

    def __getitem__(self, index):
        '''
        return: (without transform/target_transofrm)
            img: PIL.Image
            target: np.array of `L` (L=1 for single-label)
        '''
        img, target = self.imgs[index], self.labels[index].astype(int)
        img = Image.fromarray(img)

        if self.as_rgb:
            img = img.convert('RGB')

        if self.transform is not None:
            img = self.transform(img)

        if self.target_transform is not None:
            target = self.target_transform(target)

        return img, target
    
    def save(self, folder, postfix="png", write_csv=True):
        from medmnist.utils import save2d
        save2d(imgs=self.imgs,
               labels=self.labels,
               img_folder=os.path.join(folder, self.dataset_name),
               split=self.split,
               postfix=postfix,
               csv_path=os.path.join(folder, "{}.csv".format(self.dataset_name)) if write_csv else None)
    
    def montage(self, length=20, replace=False, save_folder=None):
        from medmnist.utils import montage2d

        n_sel = length * length
        sel = np.random.choice(self.__len__(), size=n_sel, replace=replace)

        montage_img = montage2d(imgs=self.imgs,
                                n_channels=self.info['n_channels'],
                                sel=sel)

        if save_folder is not None:
            if not os.path.exists(save_folder):
                os.makedirs(save_folder)
            montage_img.save(os.path.join(save_folder, 
                                          "{}_{}_montage.jpg".format(self.dataset_name, self.split)))

        return montage_img
    

class MedMNIST3D(MedMNIST):
    def __getitem__(self, index):
        '''
        return: (without transform/target_transofrm)
            img: an array of 1x28x28x28 or 3x28x28x28 (if `as_RGB=True`), in [0,1]
            target: np.array of `L` (L=1 for single-label)
        '''
        img, target = self.imgs[index], self.labels[index].astype(int)

        img = np.stack([img/255.]*(3 if self.as_rgb else 1), axis=0)

        if self.transform is not None:
            img = self.transform(img)

        if self.target_transform is not None:
            target = self.target_transform(target)

        return img, target
    
    def save(self, folder, postfix="gif", write_csv=True):
        from medmnist.utils import save3d

        assert postfix == "gif"

        save3d(imgs=self.imgs,
               labels=self.labels,
               img_folder=os.path.join(folder, self.dataset_name),
               split=self.split,
               postfix=postfix,
               csv_path=os.path.join(folder, "{}.csv".format(self.dataset_name)) if write_csv else None)
        
    def montage(self, length=20, replace=False, save_folder=None):
        assert self.info['n_channels'] == 1

        from medmnist.utils import montage3d, save_frames_as_gif
        n_sel = length * length
        sel = np.random.choice(self.__len__(), size=n_sel, replace=replace)

        montage_frames = montage3d(imgs=self.imgs,
                                   n_channels=self.info['n_channels'],
                                   sel=sel)

        if save_folder is not None:
            if not os.path.exists(save_folder):
                os.makedirs(save_folder)

            save_frames_as_gif(montage_frames,
                               os.path.join(save_folder,
                                            "{}_{}_montage.gif".format(self.dataset_name, self.split)))

        return montage_frames

class PathMNIST(MedMNIST2D):
    dataset_name = "pathmnist"


class OCTMNIST(MedMNIST2D):
    dataset_name = "octmnist"


class PneumoniaMNIST(MedMNIST2D):
    dataset_name = "pneumoniamnist"


class ChestMNIST(MedMNIST2D):
    dataset_name = "chestmnist"


class DermaMNIST(MedMNIST2D):
    dataset_name = "dermamnist"


class RetinaMNIST(MedMNIST2D):
    dataset_name = "retinamnist"


class BreastMNIST(MedMNIST2D):
    dataset_name = "breastmnist"


class BloodMNIST(MedMNIST2D):
    dataset_name = "bloodmnist"


class TissueMNIST(MedMNIST2D):
    dataset_name = "tissuemnist"


class OrganAMNIST(MedMNIST2D):
    dataset_name = "organamnist"


class OrganCMNIST(MedMNIST2D):
    dataset_name = "organcmnist"


class OrganSMNIST(MedMNIST2D):
    dataset_name = "organsmnist"


class OrganMNIST3D(MedMNIST3D):
    dataset_name = "organmnist3d"


class NoduleMNIST3D(MedMNIST3D):
    flag = "nodulemnist3d"


class AdrenalMNIST3D(MedMNIST3D):
    dataset_name = "adrenalmnist3d"


class FractureMNIST3D(MedMNIST3D):
    dataset_name = "fracturemnist3d"


class VesselMNIST3D(MedMNIST3D):
    dataset_name = "vesselmnist3d"


class SynapseMNIST3D(MedMNIST3D):
    dataset_name = "synapsemnist3d"


# backward-compatible
OrganMNISTAxial = OrganAMNIST
OrganMNISTCoronal = OrganCMNIST
OrganMNISTSagittal = OrganSMNIST



class PathMNISTPartitioner(BasicPartitioner):
    num_classes = 9

class ChestMNISTPartitioner(BasicPartitioner):
    num_classes = 14

class DermaMNISTPartitioner(BasicPartitioner):
    num_classes = 7

class OCTMNISTPartitioner(BasicPartitioner):
    num_classes = 4

class PneumoniaMNISTPartitioner(BasicPartitioner):
    num_classes = 2

class RetinaMNISTPartitioner(BasicPartitioner):
    num_classes = 5

class BreastMNISTPartitioner(BasicPartitioner):
    num_classes = 2

class BloodMNISTPartitioner(BasicPartitioner):
    num_classes = 8

class TissueMNISTPartitioner(BasicPartitioner):
    num_classes = 8

class OrganAMNISTPartitioner(BasicPartitioner):
    num_classes = 11

class OrganCMNISTPartitioner(BasicPartitioner):
    num_classes = 11

class OrganSMNISTPartitioner(BasicPartitioner):
    num_classes = 11

class OrganMNIST3DPartitioner(BasicPartitioner):
    num_classes = 11

class NoduleMNIST3DPartitioner(BasicPartitioner):
    num_classes = 2

class AdrenalMNIST3DPartitioner(BasicPartitioner):
    num_classes = 2

class FractureMNIST3DPartitioner(BasicPartitioner):
    num_classes = 3

class VesselMNIST3DPartitioner(BasicPartitioner):
    num_classes = 2

class SynapseMNIST3DPartitioner(BasicPartitioner):
    num_classes = 2



MedMNIST_HOMEPAGE_URL = ("https://github.com/MedMNIST/MedMNIST/")

MedMNIST_INFO_DICT = {
    "pathmnist": {
        "python_class": "PathMNIST",
        "description": "The PathMNIST is based on a prior study for predicting survival \
                    from colorectal cancer histology slides, providing a dataset (NCT-CRC-HE-100K) \
                    of 100,000 non-overlapping image patches from hematoxylin & eosin stained histological \
                    images, and a test dataset (CRC-VAL-HE-7K) of 7,180 image patches from a different clinical \
                    center. The dataset is comprised of 9 types of tissues, resulting in a multi-class classification \
                    task. We resize the source images of 3×224×224 into 3×28×28, and split NCT-CRC-HE-100K into training \
                    and validation set with a ratio of 9:1. The CRC-VAL-HE-7K is treated as the test set.",
        "url": "https://zenodo.org/record/6496656/files/pathmnist.npz?download=1",
        "MD5": "a8b06965200029087d5bd730944a56c1",
        "task": "multi-class",
        "label": {
            "0": "adipose",
            "1": "background",
            "2": "debris",
            "3": "lymphocytes",
            "4": "mucus",
            "5": "smooth muscle",
            "6": "normal colon mucosa",
            "7": "cancer-associated stroma",
            "8": "colorectal adenocarcinoma epithelium"
        },
        "n_channels": 3,
        "n_samples": {
            "train": 89996,
            "val": 10004,
            "test": 7180
        },
        "license": "CC BY 4.0"
    },
    "chestmnist": {
        "python_class": "ChestMNIST",
        "description": "The ChestMNIST is based on the NIH-ChestXray14 dataset, a dataset \
                    comprising 112,120 frontal-view X-Ray images of 30,805 unique patients with \
                    the text-mined 14 disease labels, which could be formulized as a multi-label \
                    binary-class classification task. We use the official data split, and resize \
                    the source images of 1×1024×1024 into 1×28×28.",
        "url": "https://zenodo.org/record/6496656/files/chestmnist.npz?download=1",
        "MD5": "02c8a6516a18b556561a56cbdd36c4a8",
        "task": "multi-label, binary-class",
        "label": {
            "0": "atelectasis",
            "1": "cardiomegaly",
            "2": "effusion",
            "3": "infiltration",
            "4": "mass",
            "5": "nodule",
            "6": "pneumonia",
            "7": "pneumothorax",
            "8": "consolidation",
            "9": "edema",
            "10": "emphysema",
            "11": "fibrosis",
            "12": "pleural",
            "13": "hernia"
        },
        "n_channels": 1,
        "n_samples": {
            "train": 78468,
            "val": 11219,
            "test": 22433
        },
        "license": "CC BY 4.0"
    },
    "dermamnist": {
        "python_class": "DermaMNIST",
        "description": "The DermaMNIST is based on the HAM10000, a large collection of multi-source \
                    dermatoscopic images of common pigmented skin lesions. The dataset consists of 10,015 \
                    dermatoscopic images categorized as 7 different diseases, formulized as a multi-class \
                    classification task. We split the images into training, validation and test set with \
                    a ratio of 7:1:2. The source images of 3×600×450 are resized into 3×28×28.",
        "url": "https://zenodo.org/record/6496656/files/dermamnist.npz?download=1",
        "MD5": "0744692d530f8e62ec473284d019b0c7",
        "task": "multi-class",
        "label": {
            "0": "actinic keratoses and intraepithelial carcinoma",
            "1": "basal cell carcinoma",
            "2": "benign keratosis-like lesions",
            "3": "dermatofibroma",
            "4": "melanoma",
            "5": "melanocytic nevi",
            "6": "vascular lesions"
        },
        "n_channels": 3,
        "n_samples": {
            "train": 7007,
            "val": 1003,
            "test": 2005
        },
        "license": "CC BY 4.0"
    },
    "octmnist": {
        "python_class": "OCTMNIST",
        "description": "The OCTMNIST is based on a prior dataset of 109,309 valid optical coherence \
                    tomography (OCT) images for retinal diseases. The dataset is comprised of 4 diagnosis \
                    categories, leading to a multi-class classification task. We split the source training set \
                    with a ratio of 9:1 into training and validation set, and use its source validation set as the \
                    test set. The source images are gray-scale, and their sizes are (384−1,536)×(277−512). We center-crop \
                    the images and resize them into 1×28×28.",
        "url": "https://zenodo.org/record/6496656/files/octmnist.npz?download=1",
        "MD5": "c68d92d5b585d8d81f7112f81e2d0842",
        "task": "multi-class",
        "label": {
            "0": "choroidal neovascularization",
            "1": "diabetic macular edema",
            "2": "drusen",
            "3": "normal"
        },
        "n_channels": 1,
        "n_samples": {
            "train": 97477,
            "val": 10832,
            "test": 1000
        },
        "license": "CC BY 4.0"
    },
    "pneumoniamnist": {
        "python_class": "PneumoniaMNIST",
        "description": "The PneumoniaMNIST is based on a prior dataset of 5,856 pediatric chest \
                    X-Ray images. The task is binary-class classification of pneumonia against normal. \
                    We split the source training set with a ratio of 9:1 into training and validation set \
                    and use its source validation set as the test set. The source images are gray-scale, and \
                    their sizes are (384−2,916)×(127−2,713). We center-crop the images and resize them into 1×28×28.",
        "url": "https://zenodo.org/record/6496656/files/pneumoniamnist.npz?download=1",
        "MD5": "28209eda62fecd6e6a2d98b1501bb15f",
        "task": "binary-class",
        "label": {
            "0": "normal",
            "1": "pneumonia"
        },
        "n_channels": 1,
        "n_samples": {
            "train": 4708,
            "val": 524,
            "test": 624
        },
        "license": "CC BY 4.0"
    },
    "retinamnist": {
        "python_class": "RetinaMNIST",
        "description": "The RetinaMNIST is based on the DeepDRiD challenge, which provides a \
                    dataset of 1,600 retina fundus images. The task is ordinal regression for 5-level \
                    grading of diabetic retinopathy severity. We split the source training set with a ratio \
                    of 9:1 into training and validation set, and use the source validation set as the test set. \
                    The source images of 3×1,736×1,824 are center-cropped and resized into 3×28×28.",
        "url": "https://zenodo.org/record/6496656/files/retinamnist.npz?download=1",
        "MD5": "bd4c0672f1bba3e3a89f0e4e876791e4",
        "task": "ordinal-regression",
        "label": {
            "0": "0",
            "1": "1",
            "2": "2",
            "3": "3",
            "4": "4"
        },
        "n_channels": 3,
        "n_samples": {
            "train": 1080,
            "val": 120,
            "test": 400
        },
        "license": "CC BY 4.0"
    },
    "breastmnist": {
        "python_class": "BreastMNIST",
        "description": "The BreastMNIST is based on a dataset of 780 breast ultrasound images. \
                    It is categorized into 3 classes: normal, benign, and malignant. As we use \
                    low-resolution images, we simplify the task into binary classification by combining \
                    normal and benign as positive and classifying them against malignant as negative. We \
                    split the source dataset with a ratio of 7:1:2 into training, validation and test set. \
                    The source images of 1×500×500 are resized into 1×28×28.",
        "url": "https://zenodo.org/record/6496656/files/breastmnist.npz?download=1",
        "MD5": "750601b1f35ba3300ea97c75c52ff8f6",
        "task": "binary-class",
        "label": {
            "0": "malignant",
            "1": "normal, benign"
        },
        "n_channels": 1,
        "n_samples": {
            "train": 546,
            "val": 78,
            "test": 156
        },
        "license": "CC BY 4.0"
    },
    "bloodmnist": {
        "python_class": "BloodMNIST",
        "description": "The BloodMNIST is based on a dataset of individual normal cells, captured \
                    from individuals without infection, hematologic or oncologic disease and free of \
                    any pharmacologic treatment at the moment of blood collection. It contains a total of \
                    17,092 images and is organized into 8 classes. We split the source dataset with a ratio \
                    of 7:1:2 into training, validation and test set. The source images with resolution 3×360×363 pixels \
                    are center-cropped into 3×200×200, and then resized into 3×28×28.",
        "url": "https://zenodo.org/record/6496656/files/bloodmnist.npz?download=1",
        "MD5": "7053d0359d879ad8a5505303e11de1dc",
        "task": "multi-class",
        "label": {
            "0": "basophil",
            "1": "eosinophil",
            "2": "erythroblast",
            "3": "immature granulocytes(myelocytes, metamyelocytes and promyelocytes)",
            "4": "lymphocyte",
            "5": "monocyte",
            "6": "neutrophil",
            "7": "platelet"
        },
        "n_channels": 3,
        "n_samples": {
            "train": 11959,
            "val": 1712,
            "test": 3421
        },
        "license": "CC BY 4.0"
    },
     "tissuemnist": {
        "python_class": "TissueMNIST",
        "description": "We use the BBBC051, available from the Broad Bioimage Benchmark Collection. \
                    The dataset contains 236,386 human kidney cortex cells, segmented from 3 reference \
                    tissue specimens and organized into 8 categories. We split the source dataset with a \
                    ratio of 7:1:2 into training, validation and test set. Each gray-scale image is 32×32×7 pixels, \
                    where 7 denotes 7 slices. We take maximum values across the slices and resize them into 28×28 gray-scale images.",
        "url": "https://zenodo.org/record/6496656/files/tissuemnist.npz?download=1",
        "MD5": "ebe78ee8b05294063de985d821c1c34b",
        "task": "multi-class",
        "label": {
            "0": "Collecting Duct, Connecting Tubule",
            "1": "Distal Convoluted Tubule",
            "2": "Glomerular endothelial cells",
            "3": "Interstitial endothelial cells",
            "4": "Leukocytes",
            "5": "Podocytes",
            "6": "Proximal Tubule Segments",
            "7": "Thick Ascending Limb"
        },
        "n_channels": 1,
        "n_samples": {
            "train": 165466,
            "val": 23640,
            "test": 47280
        },
        "license": "CC BY 4.0"
    },
    "organamnist": {
        "python_class": "OrganAMNIST",
        "description": "The OrganAMNIST is based on 3D computed tomography (CT) images from \
                    Liver Tumor Segmentation Benchmark (LiTS). It is renamed from OrganMNIST_Axial \
                    (in MedMNIST v1) for simplicity. We use bounding-box annotations of 11 body organs \
                    from another study to obtain the organ labels. Hounsfield-Unit (HU) of the 3D images \
                    are transformed into gray-scale with an abdominal window. We crop 2D images from the center \
                    slices of the 3D bounding boxes in axial views (planes). The images are resized into 1×28×28 \
                    to perform multi-class classification of 11 body organs. 115 and 16 CT scans from the source \
                    training set are used as training and validation set, respectively. The 70 CT scans from the \
                    source test set are treated as the test set.",
        "url": "https://zenodo.org/record/6496656/files/organamnist.npz?download=1",
        "MD5": "866b832ed4eeba67bfb9edee1d5544e6",
        "task": "multi-class",
        "label": {
            "0": "bladder",
            "1": "femur-left",
            "2": "femur-right",
            "3": "heart",
            "4": "kidney-left",
            "5": "kidney-right",
            "6": "liver",
            "7": "lung-left",
            "8": "lung-right",
            "9": "pancreas",
            "10": "spleen"
        },
        "n_channels": 1,
        "n_samples": {
            "train": 34581,
            "val": 6491,
            "test": 17778
        },
        "license": "CC BY 4.0"
    },
    "organcmnist": {
        "python_class": "OrganCMNIST",
        "description": "The OrganCMNIST is based on 3D computed tomography (CT) images \
                    from Liver Tumor Segmentation Benchmark (LiTS). It is renamed from \
                    OrganMNIST_Coronal (in MedMNIST v1) for simplicity. We use bounding-box \
                    annotations of 11 body organs from another study to obtain the organ labels. \
                    Hounsfield-Unit (HU) of the 3D images are transformed into gray-scale with an abdominal window. \
                    We crop 2D images from the center slices of the 3D bounding boxes in coronal views (planes). \
                    The images are resized into 1×28×28 to perform multi-class classification of 11 body organs. \
                    115 and 16 CT scans from the source training set are used as training and validation set, respectively. \
                    The 70 CT scans from the source test set are treated as the test set.",
        "url": "https://zenodo.org/record/6496656/files/organcmnist.npz?download=1",
        "MD5": "0afa5834fb105f7705a7d93372119a21",
        "task": "multi-class",
        "label": {
            "0": "bladder",
            "1": "femur-left",
            "2": "femur-right",
            "3": "heart",
            "4": "kidney-left",
            "5": "kidney-right",
            "6": "liver",
            "7": "lung-left",
            "8": "lung-right",
            "9": "pancreas",
            "10": "spleen"
        },
        "n_channels": 1,
        "n_samples": {
            "train": 13000,
            "val": 2392,
            "test": 8268
        },
        "license": "CC BY 4.0"
    },
    "organsmnist": {
        "python_class": "OrganSMNIST",
        "description": "The OrganSMNIST is based on 3D computed tomography (CT) images \
                    from Liver Tumor Segmentation Benchmark (LiTS). It is renamed from \
                    OrganMNIST_Sagittal (in MedMNIST v1) for simplicity. We use bounding-box \
                    annotations of 11 body organs from another study to obtain the organ labels. \
                    Hounsfield-Unit (HU) of the 3D images are transformed into gray-scale with an abdominal window. \
                    We crop 2D images from the center slices of the 3D bounding boxes in sagittal views (planes). \
                    The images are resized into 1×28×28 to perform multi-class classification of 11 body organs. \
                    115 and 16 CT scans from the source training set are used as training and validation set, respectively. \
                    The 70 CT scans from the source test set are treated as the test set.",
        "url": "https://zenodo.org/record/6496656/files/organsmnist.npz?download=1",
        "MD5": "e5c39f1af030238290b9557d9503af9d",
        "task": "multi-class",
        "label": {
            "0": "bladder",
            "1": "femur-left",
            "2": "femur-right",
            "3": "heart",
            "4": "kidney-left",
            "5": "kidney-right",
            "6": "liver",
            "7": "lung-left",
            "8": "lung-right",
            "9": "pancreas",
            "10": "spleen"
        },
        "n_channels": 1,
        "n_samples": {
            "train": 13940,
            "val": 2452,
            "test": 8829
        },
        "license": "CC BY 4.0"
    },
    "organmnist3d": {
        "python_class": "OrganMNIST3D",
        "description": "The source of the OrganMNIST3D is the same as that of the Organ{A,C,S}MNIST. \
                    Instead of 2D images, we directly use the 3D bounding boxes and process the images \
                    into 28×28×28 to perform multi-class classification of 11 body organs. The same 115 and 16 CT scans \
                    as the Organ{A,C,S}MNIST from the source training set are used as training and validation set, respectively, \
                    and the same 70 CT scans as the Organ{A,C,S}MNIST from the source test set are treated as the test set.",
        "url": "https://zenodo.org/record/6496656/files/organmnist3d.npz?download=1",
        "MD5": "21f0a239e7f502e6eca33c3fc453c0b6",
        "task": "multi-class",
        "label": {
            "0": "liver",
            "1": "kidney-right",
            "2": "kidney-left",
            "3": "femur-right",
            "4": "femur-left",
            "5": "bladder",
            "6": "heart",
            "7": "lung-right",
            "8": "lung-left",
            "9": "spleen",
            "10": "pancreas"
        },
        "n_channels": 1,
        "n_samples": {
            "train": 972,
            "val": 161,
            "test": 610
        },
        "license": "CC BY 4.0"
    },
    "nodulemnist3d": {
        "python_class": "NoduleMNIST3D",
        "description": "The NoduleMNIST3D is based on the LIDC-IDRI, a large public lung nodule dataset, \
                    containing images from thoracic CT scans. The dataset is designed for both lung nodule \
                    segmentation and 5-level malignancy classification task. To perform binary classification, \
                    we categorize cases with malignancy level 1/2 into negative class and 4/5 into positive class, \
                    ignoring the cases with malignancy level 3. We split the source dataset with a ratio of 7:1:2 into \
                    training, validation and test set, and center-crop the spatially normalized images (with a spacing of \
                    1mm×1mm×1mm) into 28×28×28.",
        "url": "https://zenodo.org/record/6496656/files/nodulemnist3d.npz?download=1",
        "MD5": "8755a7e9e05a4d9ce80a24c3e7a256f3",
        "task": "binary-class",
        "label": {
            "0": "benign",
            "1": "malignant"
        },
        "n_channels": 1,
        "n_samples": {
            "train": 1158,
            "val": 165,
            "test": 310
        },
        "license": "CC BY 4.0"
    },
    "adrenalmnist3d": {
        "python_class": "AdrenalMNIST3D",
        "description": "The AdrenalMNIST3D is a new 3D shape classification dataset, \
                    consisting of shape masks from 1,584 left and right adrenal glands (i.e., 792 patients). \
                    Collected from Zhongshan Hospital Affiliated to Fudan University, each 3D shape of adrenal \
                    gland is annotated by an expert endocrinologist using abdominal computed tomography (CT), \
                    together with a binary classification label of normal adrenal gland or adrenal mass. \
                    Considering patient privacy, we do not provide the source CT scans, but the real 3D shapes of \
                    adrenal glands and their classification labels. We calculate the center of adrenal and \
                    resize the center-cropped 64mm×64mm×64mm volume into 28×28×28. The dataset is randomly split into \
                    training/validation/test set of 1,188/98/298 on a patient level.",
        "url": "https://zenodo.org/record/6496656/files/adrenalmnist3d.npz?download=1",
        "MD5": "bbd3c5a5576322bc4cdfea780653b1ce",
        "task": "binary-class",
        "label": {
            "0": "normal",
            "1": "hyperplasia"
        },
        "n_channels": 1,
        "n_samples": {
            "train": 1188,
            "val": 98,
            "test": 298
        },
        "license": "CC BY 4.0"
    },
    "fracturemnist3d": {
        "python_class": "FractureMNIST3D",
        "description": "The FractureMNIST3D is based on the RibFrac Dataset, containing around 5,000 rib fractures \
                    from 660 computed tomography 153 (CT) scans. The dataset organizes detected rib fractures \
                    into 4 clinical categories (i.e., buckle, nondisplaced, displaced, and segmental rib fractures). \
                    As we use low-resolution images, we disregard segmental rib fractures and classify 3 types of rib \
                    fractures (i.e., buckle, nondisplaced, and displaced). For each annotated fracture area, we calculate \
                    its center and resize the center-cropped 64mm×64mm×64mm image into 28×28×28. The official split of training, \
                    validation and test set is used.",
        "url": "https://zenodo.org/record/6496656/files/fracturemnist3d.npz?download=1",
        "MD5": "6aa7b0143a6b42da40027a9dda61302f",
        "task": "multi-class",
        "label": {
            "0": "buckle rib fracture",
            "1": "nondisplaced rib fracture",
            "2": "displaced rib fracture"
        },
        "n_channels": 1,
        "n_samples": {
            "train": 1027,
            "val": 103,
            "test": 240
        },
        "license": "CC BY 4.0"
    },
    "vesselmnist3d": {
        "python_class": "VesselMNIST3D",
        "description": "The VesselMNIST3D is based on an open-access 3D intracranial aneurysm dataset, IntrA, \
                    containing 103 3D models (meshes) of entire brain vessels collected by reconstructing MRA images. \
                    1,694 healthy vessel segments and 215 aneurysm segments are generated automatically from the complete models. \
                    We fix the non-watertight mesh with PyMeshFix and voxelize the watertight mesh with trimesh into 28×28×28 voxels. \
                    We split the source dataset with a ratio of 7:1:2 into training, validation and test set.",
        "url": "https://zenodo.org/record/6496656/files/vesselmnist3d.npz?download=1",
        "MD5": "2ba5b80617d705141f3f85627108fce8",
        "task": "binary-class",
        "label": {
            "0": "vessel",
            "1": "aneurysm"
        },
        "n_channels": 1,
        "n_samples": {
            "train": 1335,
            "val": 192,
            "test": 382
        },
        "license": "CC BY 4.0"
    },
    "synapsemnist3d": {
        "python_class": "SynapseMNIST3D",
        "description": "The SynapseMNIST3D is a new 3D volume dataset to classify whether a synapse is \
                    excitatory or inhibitory. It uses a 3D image volume of an adult rat acquired by a \
                    multi-beam scanning electron microscope. The original data is of the size 100×100×100um^3 \
                    and the resolution 8×8×30nm^3, where a (30um)^3 sub-volume was used in the MitoEM dataset with \
                    dense 3D mitochondria instance segmentation labels. Three neuroscience experts segment a pyramidal \
                    neuron within the whole volume and proofread all the synapses on this neuron with excitatory/inhibitory labels. \
                    For each labeled synaptic location, we crop a 3D volume of 1024×1024×1024nm^3 and resize it into 28×28×28 voxels. \
                    Finally, the dataset is randomly split with a ratio of 7:1:2 into training, validation and test set.",
        "url": "https://zenodo.org/record/6496656/files/synapsemnist3d.npz?download=1",
        "MD5": "1235b78a3cd6280881dd7850a78eadb6",
        "task": "binary-class",
        "label": {
            "0": "inhibitory synapse",
            "1": "excitatory synapse"
        },
        "n_channels": 1,
        "n_samples": {
            "train": 1230,
            "val": 177,
            "test": 352
        },
        "license": "CC BY 4.0"
    }
}

if __name__ == "__main__":

    download_dataset = argparse.ArgumentParser()

    download_dataset.add_argument(
        "--dataset-name",
        type=str,
        help="Which dataset to use for federated learning.",
        required=True
    )

    args = download_dataset.parse_args()

    download_MedMNIST_dataset(args.dataset_name)

