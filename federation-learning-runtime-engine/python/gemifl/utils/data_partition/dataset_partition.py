import numpy as np
import pandas as pd
import warnings

from abc import ABC, abstractmethod

class DataPartitioner(ABC):
    
    """
    Base class for data partition in federated learning.
    Details and tutorials of different data partition and datasets, please check 
    `Federated Dataset and DataPartitioner <https://fedlab.readthedocs.io/en/master/tutorials/dataset_partition.html>`_.
    Examples of :class:`DataPartitioner`: :class:`BasicPartitioner`, :class:`CIFAR10Partitioner`.
    """
    def __init__(self):
        pass

    @abstractmethod
    def _perform_partition(self):
        raise NotImplementedError()

    @abstractmethod
    def __getitem__(self, index):
        raise NotImplementedError()

    @abstractmethod
    def __len__(self):
        raise NotImplementedError()

class BasicPartitioner(DataPartitioner):

    """
    Basic data partitioner, supported partition:
    - label-distribution-skew:quantity-based
    - label-distribution-skew:distributed-based (Dirichlet)
    - quantity-skew (Dirichlet)
    - IID
    Parameters
    ----------
    targets : list or numpy.ndarray
        Sample targets. Unshuffled preferred.
    num_clients : int
        Number of clients for partition.
    partition: str
        Partition schemes. Only supports ``"noniid-#label"``, ``"noniid-labeldir"``, ``"unbalance"`` and ``"iid"`` partition schemes.
    dir_alpha : float
        Parameter alpha for Dirichlet distribution. Only works if ``partition="noniid-labeldir"``.
    major_classes_num : int
        Number of major class for each clients. Only works if ``partition="noniid-#label"``.
    verbose : bool
        Whether output intermediate information. Default as ``True``.
    min_require_size : int
        (Optional) Minimum required sample number for each client. 
        If set to ``None``, then equals to ``num_classes``. Only works if ``partition="noniid-labeldir"``.
    seed : int
        Random seed. Default as ``None``.
    Returns
    -------
    client_dict : dict
        ``{ client_id: indices}``
    """

    num_classes = 2

    def __init__(self, targets, num_clients,
                 partition='iid',
                 dir_alpha=None,
                 major_classes_num=1,
                 verbose=True,
                 min_require_size=None,
                 seed=None):
        self.targets = np.array(targets)  # with shape (num_samples,)
        self.num_samples = self.targets.shape[0]
        self.num_clients = num_clients
        self.client_dict = dict()
        self.partition = partition
        self.dir_alpha = dir_alpha
        self.verbose = verbose
        self.min_require_size = min_require_size
            
        # self.rng = np.random.default_rng(seed)  # rng currently not supports randint
        np.random.seed(seed)

        if partition == "noniid-#label":
            # label-distribution-skew:quantity-based
            assert isinstance(major_classes_num, int), f"'major_classes_num' should be integer, " \
                                                       f"not {type(major_classes_num)}."
            assert major_classes_num > 0, f"'major_classes_num' should be positive."
            assert major_classes_num < self.num_classes, f"'major_classes_num' for each client " \
                                                         f"should be less than number of total " \
                                                         f"classes {self.num_classes}."
            self.major_classes_num = major_classes_num
        elif partition in ["noniid-labeldir", "unbalance"]:
            # label-distribution-skew:distributed-based (Dirichlet) and quantity-skew (Dirichlet)
            assert dir_alpha > 0, f"Parameter 'dir_alpha' for Dirichlet distribution should be " \
                                  f"positive."
        elif partition == "iid":
            # IID
            pass
        else:
            raise ValueError(
                f"tabular data partition only supports 'noniid-#label', 'noniid-labeldir', "
                f"'unbalance', 'iid'. {partition} is not supported.")
        
        self.client_dict = self._perform_partition()
        # get sample number count for each client
        self.client_sample_count = samples_num_count(self.client_dict, self.num_clients)

    def _perform_partition(self):
        if self.partition == "noniid-#label":
            # label-distribution-skew:quantity-based
            client_dict = label_skew_quantity_based_partition(self.targets, self.num_clients,
                                                                self.num_classes,
                                                                self.major_classes_num)

        elif self.partition == "noniid-labeldir":
            # label-distribution-skew:distributed-based (Dirichlet)
            client_dict = hetero_dir_partition(self.targets, self.num_clients, self.num_classes,
                                                 self.dir_alpha,
                                                 min_require_size=self.min_require_size)

        elif self.partition == "unbalance":
            # quantity-skew (Dirichlet)
            client_sample_nums = dirichlet_unbalance_split(self.num_clients, self.num_samples,
                                                             self.dir_alpha)
            client_dict = homo_partition(client_sample_nums, self.num_samples)

        else:
            # IID
            client_sample_nums = balance_split(self.num_clients, self.num_samples)
            client_dict = homo_partition(client_sample_nums, self.num_samples)

        return client_dict
    
    def __getitem__(self, index):
        return self.client_dict[index]

    def __len__(self):
        return len(self.client_dict)






def random_slicing(dataset, num_clients):

    """
    Slice datasets randomly and equally for IID.
    Parameters
    ----------
    dataset : torch.utils.Dataset
        The dataset for slicing.
    num_clients : int
        The number of client.
    Returns
    -------
    clientDatasetIndices : dict ``{ 0: indices of dataset, 1: indices of dataset, ..., k: indices of dataset }``
    """
    
    num_items = int(len(dataset) / num_clients)

    clientDatasetIndices, all_idxs = {}, [i for i in range(len(dataset))]
    for i in range(num_clients):
        clientDatasetIndices[i] = list(
            np.random.choice(all_idxs, num_items, replace=False))
        all_idxs = list(set(all_idxs) - set(clientDatasetIndices[i]))
    
    return clientDatasetIndices

def noniid_slicing(dataset, num_clients, num_shards):
    
    """
    Slice datasets for non-IID.
    Parameters
    ----------
    dataset : torch.utils.Dataset
        The dataset for slicing.
    num_clients : int
        The number of client.
    num_shards : int
        The number of shards. (数据集被划分为的片段数量。在训练过程中，每个客户端通常会收到一个或多个片段)
    Returns
    -------
    clientDatasetIndices : dict ``{ 0: indices of dataset, 1: indices of dataset, ..., k: indices of dataset }``
    """

    total_sample_nums = len(dataset)
    size_of_shards = int (total_sample_nums / num_shards)
    if total_sample_nums % num_shards != 0:
        warnings.warn(
            "Warning: the length of dataset isn't divided exactly by num_shard. Some samples will be dropped."
        )
    
    shard_per_client = int(num_shards / num_clients)
    if num_shards % num_clients != 0:
        warnings.warn(
            "Warning: num_shard isn't divided exactly by num_clients. Some samples will be dropped."
        )
    
    clientDatasetIndices = {i: np.array([], dtype='int64') for i in range(num_clients)}

    labels = np.array(dataset.target)
    idxs = np.arange(total_sample_nums)

    # sort sample indices according to labels
    idxs_labels = np.vstack((idxs, labels))
    idxs_labels = idxs_labels[:, idxs_labels[1, :].argsort()]
    idxs = idxs_labels[0, :]  # corresponding labels after sorting are [0, .., 0, 1, ..., 1, ...]

    # assign
    idx_shard = [i for i in range(num_shards)]
    for i in range(num_clients):
        rand_set = set(np.random.choice(idx_shard, shard_per_client, replace=False))
        idx_shard = list(set(idx_shard) - rand_set)
        for rand in rand_set:
            clientDatasetIndices[i] = np.concatenate(
                (clientDatasetIndices[i],
                 idxs[rand * size_of_shards:(rand + 1) * size_of_shards]),
                axis=0)

    return clientDatasetIndices

def balance_split(num_clients, num_samples):
        
    """
    Assign same sample for each client.
    Parameters
    ----------
    num_clients : int
        The number of client.
    num_samples : int
        Total number of samples. 
    Returns
    -------
    client_sample_nums : numpy.ndarray
        A numpy array consisting ``num_clients`` integer elements, each represents sample number of corresponding clients.
    """

    num_samples_per_client = int(num_samples / num_clients)
    client_sample_nums = (np.ones(num_clients) * num_samples_per_client).astype(int)
    return client_sample_nums

def lognormal_unbalance_split(num_clients, num_samples, unbalance_sgm):
    
    """
    Assign different sample number for each client using Log-Normal distribution.
    Parameters
    ----------
    num_clients : int
        The number of client.
    num_samples : int
        Total number of samples.
    unbalance_sgm : float
        Log-normal variance. When equals to ``0``, the partition is equal to :func:`balance_partition`.
    Returns
    -------
    client_sample_nums : numpy.ndarray
        A numpy array consisting ``num_clients`` integer elements, each represents sample number of corresponding clients.
    """

    num_samples_per_client = int(num_samples / num_clients)
    if unbalance_sgm != 0:
        client_sample_nums = np.random.lognormal(mean=np.log(num_samples_per_client),
                                                 sigma=unbalance_sgm,
                                                 size=num_clients)
        client_sample_nums = (
                client_sample_nums / np.sum(client_sample_nums) * num_samples).astype(int)
        diff = np.sum(client_sample_nums) - num_samples  # diff <= 0

        # Add/Subtract the excess number starting from first client
        if diff != 0:
            for cid in range(num_clients):
                if client_sample_nums[cid] > diff:
                    client_sample_nums[cid] -= diff
                    break
    else:
        client_sample_nums = (np.ones(num_clients) * num_samples_per_client).astype(int)

    return client_sample_nums

def dirichlet_unbalance_split(num_clients, num_samples, alpha):

    """
    Assign different sample number for each client using Dirichlet distribution.
    Parameters
    ----------
    num_clients : int
        The number of client.
    num_samples : int
        Total number of samples.
    alpha : float
        Dirichlet concentration parameter.
    Returns
    -------
    client_sample_nums : numpy.ndarray
        A numpy array consisting ``num_clients`` integer elements, each represents sample number of corresponding clients.
    """

    min_size = 0
    while min_size < 10:
        proportions = np.random.dirichlet(np.repeat(alpha, num_clients))
        proportions = proportions / proportions.sum()
        min_size = np.min(proportions * num_samples)

    client_sample_nums = (proportions * num_samples).astype(int)
    return client_sample_nums

def homo_partition(client_sample_nums, num_samples):
    
    """
    Partition data indices in IID way given sample numbers for each clients.
    Parameters
    ----------
    client_sample_nums : numpy.ndarray
        Sample numbers for each clients.
    num_samples : int
        Total number of samples.
    Returns
    -------
    client_dict : dict
        ``{ client_id: indices}``.
    """
     
    rand_perm = np.random.permutation(num_samples)
    num_cumsum = np.cumsum(client_sample_nums).astype(int)
    client_dict = split_indices(num_cumsum, rand_perm)
    return client_dict

def split_indices(num_cumsum, rand_perm):

    """
    Splice the sample index list given number of each client.
    Parameters
    ----------
    num_cumsum : numpy.ndarray
        Cumulative sum of sample number for each client.
    rand_perm: list
        List of random sample index.
    Returns
    -------
    client_dict : dict
        ``{ client_id: indices}``.
    """

    client_indices_pairs = [(cid, idxs) for cid, idxs in
                            enumerate(np.split(rand_perm, num_cumsum)[:-1])]
    client_dict = dict(client_indices_pairs)
    return client_dict


def hetero_dir_partition(targets, num_clients, num_classes, dir_alpha, min_require_size=None):

    """
     Non-iid partition based on Dirichlet distribution. The method is from "hetero-dir" partition of
    `Bayesian Nonparametric Federated Learning of Neural Networks <https://arxiv.org/abs/1905.12022>`_
    and `Federated Learning with Matched Averaging <https://arxiv.org/abs/2002.06440>`_.
    This method simulates heterogeneous partition for which number of data points and class
    proportions are unbalanced. Samples will be partitioned into :math:`J` clients by sampling
    :math:`p_k \sim \\text{Dir}_{J}({\\alpha})` and allocating a :math:`p_{p,j}` proportion of the
    samples of class :math:`k` to local client :math:`j`.
    Sample number for each client is decided in this function.

    Parameters
    ----------
    targets : list or numpy.ndarray
        Sample targets. Unshuffled preferred.
    num_clients : int
        Number of clients for partition.
    num_classes : int
        Number of classes in samples.
    dir_alpha : float
        Parameter alpha for Dirichlet distribution.
    min_require_size : int
        (Optional) Minimum required sample number for each client. If set to ``None``, then equals to ``num_classes``.
    Returns
    -------
    client_dict : dict 
        ``{ client_id: indices }``
    """

    if min_require_size is None:
        min_require_size = num_classes
    
    if not isinstance(targets, np.ndarray):
        targets = np.array(targets)

    num_samples = targets.shape[0]

    min_size = 0
    while min_size < min_require_size:
        idx_batch = [[] for _ in range(num_clients)]
        # for each class in the dataset
        for k in range(num_classes):
            idx_k = np.where(targets == k)[0]
            np.random.shuffle(idx_k)
            proportions = np.random.dirichlet(
                np.repeat(dir_alpha, num_clients))
            # Balance
            proportions = np.array(
                [p * (len(idx_j) < num_samples / num_clients) for p, idx_j in
                 zip(proportions, idx_batch)])
            proportions = proportions / proportions.sum()
            proportions = (np.cumsum(proportions) * len(idx_k)).astype(int)[:-1]
            idx_batch = [idx_j + idx.tolist() for idx_j, idx in
                         zip(idx_batch, np.split(idx_k, proportions))]
            min_size = min([len(idx_j) for idx_j in idx_batch])
    
    client_dict = dict()
    for cid in range(num_clients):
        np.random.shuffle(idx_batch[cid])
        client_dict[cid] = np.array(idx_batch[cid])

    return client_dict


def client_inner_dirichlet_partition(targets, num_clients, num_classes, dir_alpha,
                                     client_sample_nums, verbose=True):
    
    """
    Non-iid partition based on Dirichlet distribution. The method is from `Federated Learning
    Based on Dynamic Regularization <https://openreview.net/forum?id=B7v4QMR6Z9w>`_. 
    This function can be used by given specific sample number for all clients ``client_sample_nums``.
    It's different from :func:`hetero_dir_partition`.

    Parameters
    ----------
    targets : list or numpy.ndarray
        Sample targets. Unshuffled preferred.
    num_clients : int
        Number of clients for partition.
    num_classes : int
        Number of classes in samples.
    dir_alpha : float
        Parameter alpha for Dirichlet distribution.
    client_sample_nums : numpy.ndarray
        A numpy array consisting ``num_clients`` integer elements, each represents sample number of corresponding clients.
    verbose : bool
        (Optional) Whether to print partition process. Default as ``True``.
    Returns
    -------
    client_dict : dict 
        ``{ client_id: indices }``
    """
    
    if not isinstance(targets, np.ndarray):
        targets = np.array(targets)

    rand_perm = np.random.permutation(targets.shape[0])
    targets = targets[rand_perm]

    class_priors = np.random.dirichlet(alpha=[dir_alpha] * num_classes,
                                       size=num_clients)
    prior_cumsum = np.cumsum(class_priors, axis=1)
    idx_list = [np.where(targets == i)[0] for i in range(num_classes)]
    class_amount = [len(idx_list[i]) for i in range(num_classes)]

    client_indices = [np.zeros(client_sample_nums[cid]).astype(np.int64) for cid in
                      range(num_clients)]

    while np.sum(client_sample_nums) != 0:
        curr_cid = np.random.randint(num_clients)
        # If current node is full resample a client
        if verbose:
            print('Remaining Data: %d' % np.sum(client_sample_nums))
        if client_sample_nums[curr_cid] <= 0:
            continue
        client_sample_nums[curr_cid] -= 1
        curr_prior = prior_cumsum[curr_cid]
        while True:
            curr_class = np.argmax(np.random.uniform() <= curr_prior)
            # Redraw class label if no rest in current class samples
            if class_amount[curr_class] <= 0:
                continue
            class_amount[curr_class] -= 1
            client_indices[curr_cid][client_sample_nums[curr_cid]] = \
                idx_list[curr_class][class_amount[curr_class]]

            break

    client_dict = {cid: client_indices[cid] for cid in range(num_clients)}
    return client_dict

def label_skew_quantity_based_partition(targets, num_clients, num_classes, major_classes_num):
    
    """
    Label-skew: quantity based partition. The method is from `Federated Learning
    on Non-IID Data Silos: An Experimental Study <https://arxiv.org/abs/2102.02079>`_. 

    Parameters
    ----------
    targets : list or numpy.ndarray
        Sample targets. Unshuffled preferred.
    num_clients : int
        Number of clients for partition.
    num_classes : int
        Number of classes in samples.
    major_classes_num : int
        Number of classes for each client, should be less then ``num_classes``.
    Returns
    -------
    client_dict : dict 
        ``{ client_id: indices }``
    """

    if not isinstance(targets, np.ndarray):
        targets = np.array(targets)

    idx_batch = [np.ndarray(0, dtype=np.int64) for _ in range(num_clients)]
    # only for major_classes_num < num_classes.
    # if major_classes_num = num_classes, it equals to IID partition
    times = [0 for _ in range(num_classes)]
    contain = []
    for cid in range(num_clients):
        current = [cid % num_classes]
        times[cid % num_classes] += 1
        j = 1
        while j < major_classes_num:
            ind = np.random.randint(num_classes)
            if ind not in current:
                j += 1
                current.append(ind)
                times[ind] += 1
        contain.append(current)

    for k in range(num_classes):
        idx_k = np.where(targets == k)[0]
        np.random.shuffle(idx_k)
        split = np.array_split(idx_k, times[k])
        ids = 0
        for cid in range(num_clients):
            if k in contain[cid]:
                idx_batch[cid] = np.append(idx_batch[cid], split[ids])
                ids += 1

    client_dict = {cid: idx_batch[cid] for cid in range(num_clients)}
    return client_dict

def shards_partition(targets, num_clients, num_shards):
    
    """
    Non-iid partition used in FedAvg `paper <https://arxiv.org/abs/1602.05629>`_.

    Parameters
    ----------
    targets : list or numpy.ndarray
        Sample targets. Unshuffled preferred.
    num_clients : int
        Number of clients for partition.
    num_shards : int
        Number of shards in partition.
    Returns
    -------
    client_dict : dict 
        ``{ client_id: indices }``
    """
    
    if not isinstance(targets, np.ndarray):
        targets = np.array(targets)
    num_samples = targets.shape[0]

    size_shard = int(num_samples / num_shards)
    if num_samples % num_shards != 0:
        warnings.warn("warning: length of dataset isn't divided exactly by num_shards. "
                      "Some samples will be dropped.")

    shards_per_client = int(num_shards / num_clients)
    if num_shards % num_clients != 0:
        warnings.warn("warning: num_shards isn't divided exactly by num_clients. "
                      "Some shards will be dropped.")

    indices = np.arange(num_samples)
    # sort sample indices according to labels
    indices_targets = np.vstack((indices, targets))
    indices_targets = indices_targets[:, indices_targets[1, :].argsort()]
    # corresponding labels after sorting are [0, .., 0, 1, ..., 1, ...]
    sorted_indices = indices_targets[0, :]

    # permute shards idx, and slice shards_per_client shards for each client
    rand_perm = np.random.permutation(num_shards)
    num_client_shards = np.ones(num_clients) * shards_per_client
    # sample index must be int
    num_cumsum = np.cumsum(num_client_shards).astype(int)
    # shard indices for each client
    client_shards_dict = split_indices(num_cumsum, rand_perm)

    # map shard idx to sample idx for each client
    client_dict = dict()
    for cid in range(num_clients):
        shards_set = client_shards_dict[cid]
        current_indices = [
            sorted_indices[shard_id * size_shard: (shard_id + 1) * size_shard]
            for shard_id in shards_set]
        client_dict[cid] = np.concatenate(current_indices, axis=0)

    return client_dict

def samples_num_count(client_dict, num_clients):
    
    """
    Return sample count for all clients in ``client_dict``.

    Parameters
    ----------
    client_dict : dict
        Data partition result for different clients.
    num_clients : int
        Number of clients for partition.
    Returns
    -------
    client_sample_count : pandas.DataFrame
        sample count for all clients
    """
    
    client_samples_nums = [[cid, client_dict[cid].shape[0]] for cid in
                           range(num_clients)]
    client_sample_count = pd.DataFrame(data=client_samples_nums,
                                       columns=['client', 'num_samples']).set_index('client')
    return client_sample_count
