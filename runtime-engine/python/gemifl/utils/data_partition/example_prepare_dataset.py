import os
import argparse
import FedMedMNIST
import numpy as np
import torch
from torch.utils.data import DataLoader
import torchvision.transforms as transforms
from FedMedMNIST import MedMNIST_DEFAULT_ROOT, MedMNIST_INFO_DICT, MedMNIST_DATASETS
from FedMedMNIST import MedMNIST, MedMNIST2D, MedMNIST3D
from FedMedMNIST import BreastMNISTPartitioner


def prepare_dataset(dataset_name, partition, num_clients):

    print ('Preparing dataset {}'.format(dataset_name))
    
    if dataset_name in MedMNIST_DATASETS: 

        # download the dataset
        from FedMedMNIST import download_MedMNIST_dataset
        file_path = download_MedMNIST_dataset(datasetname=dataset_name)

        dataset = getattr(FedMedMNIST, MedMNIST_INFO_DICT[dataset_name]['python_class'])

        # prepocess the dataset
        data_transform = transforms.Compose([
            transforms.ToTensor(),
            transforms.Normalize(mean=[.5], std=[.5])])
        
        # load the dataset
        train_dataset = dataset(split='train', transform=data_transform, dataset_dir=file_path)
        test_dataset = dataset(split='test', transform=data_transform, dataset_dir=file_path)
        
        train_loader = DataLoader(train_dataset, batch_size=len(train_dataset), shuffle=False)
        test_loader = DataLoader(test_dataset, batch_size=len(test_dataset), shuffle=False)
        
        images = []
        targets = []
        
        for data in train_loader:
            inputs, batch_targets = data
            targets.append(batch_targets.numpy())
            for input_tensor in inputs:
                images.append(input_tensor)
                
        targets = np.concatenate(targets)
        
        images = torch.stack(images)

        # split the dataset for federated learning
        fed_class_name = type(train_dataset).__name__ + 'Partitioner'
        fed_dataset = globals()[fed_class_name](targets=targets, num_clients=num_clients, partition=partition)
        client_dict = fed_dataset.client_dict
        
        fed_file_path = os.path.join(os.path.dirname(file_path), dataset_name)
        os.makedirs(fed_file_path, exist_ok=True)

        for client_id, indices in client_dict.items():
            client_dir = os.path.join(fed_file_path, "client_{}".format(client_id))
            os.makedirs(client_dir, exist_ok=True)
            
            client_targets = [targets[i] for i in indices]
            client_images = images[indices]
            
            client_data = (client_images, client_targets)
       
            client_file = os.path.join(client_dir, f"{client_id}.pt")
            
            torch.save(client_data, client_file)


if __name__ == "__main__":

    parser_prepare_dataset = argparse.ArgumentParser()

    parser_prepare_dataset.add_argument(
        "--dataset-name",
        type=str,
        help="Which dataset to use for federated learning.",
        required=True
    )

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
    prepare_dataset(args.dataset_name, args.partition_schemes, args.num_clients)









    
