# -*- coding: utf-8 -*-
import numpy as np
from gemifl.phe_utils.paillier_utils import *
from gemifl.phe_utils.choose_key_pair import *
import copy
import torch
class FedAvg(object):
    def __init__(self, logger, public_key, workers):
        self.logger = logger
        self.public_key = public_key
        self.workers = workers

    def fedavg_weights_plaintext(self, party_weights, party_sizes):

        weight_names = list(party_weights[0].keys())

        return_weights = party_weights[0]
        for name in weight_names:
            weight_list = [w[name][0] for w in party_weights]
            update_weights = self.__fedavg_single_weight_plainttext(weight_list, party_sizes)
            return_weights[name][0] = update_weights
        weight_name = weight_names[0]
        
        client_weights_str = ', '.join(np.array2string(x, threshold=np.inf) for x in return_weights[weight_name][0])
        self.logger.info(f"aggrated client_weights: {client_weights_str} client_sizes: {party_sizes}")
        return return_weights

    def fedavg_onlyweights_plaintext(self, party_weights):
        return_weights = party_weights[0]
        num_clients = len(party_weights)
        for i in range(1,num_clients):
            pw = party_weights[i]
            for k in return_weights:
                return_weights[k] = torch.add(return_weights[k], pw[k])
        for k in return_weights:
            return_weights[k] = torch.divide(
                return_weights[k], num_clients)
        return return_weights

    def __fedavg_single_weight_plainttext(self, client_weights, client_sizes):
        
        total_size = np.sum(client_sizes)
        
        new_weights = np.zeros(client_weights[0].shape)
        
        for c in range(len(client_weights)):
            for i in range(len(new_weights)):
               
                new_weights[i] += (client_weights[c][i] * client_sizes[c]
                                   / total_size)
        
        return new_weights

    def fedavg_weights(self, client_weights, client_sizes):
        self.logger.debug("aggregating weight by fedavg. array size is %s, client size is %s" % (
        str(len(client_weights[0])), str(len(client_sizes))))
        total_size = sum(client_sizes)
        arr_len = len(client_weights[0])

        cors1 = [client_sizes[0] / total_size for i in range(arr_len)]

        cipherdata = client_weights[0]

        for c in range(1, len(client_weights)):
            cors2 = [client_sizes[c] / total_size for i in range(arr_len)]

            cipherdata = pailler_array_add(cipherdata, client_weights[c], self.public_key,
                                           cors1=cors1, cors2=cors2,
                                           isconvert=False, workers=self.workers)
        new_weights = [(phe.util.int_to_base64(x.ciphertext()), x.exponent) for x in cipherdata]
        return new_weights

    def fedavg_weights_list(self, party_weights, party_sizes):
        weight_names = list(party_weights[0].keys())

        weight_nest_list = []
        for name in weight_names:
            weight_list = [w[name][0] for w in party_weights] # w is like: {"weight_name":[[(str,-15),...],(size)]}
            weight_nest_list.append((name, weight_list))  # [("layer1", [[(str,-15),...as party1], [(str,-15),...as party2], ...]), ("layer2", [[(str,-15),...as party1], [(str,-15),...as party2], ...])]

        weight_xchunk = divide_chunks(weight_nest_list, self.workers)
        with multiprocess.Pool(processes=self.workers) as pool:
            nest_result = pool.map(avg_weights_args, [(i, party_sizes, self.public_key) for i in weight_xchunk])

        result = flatten_extend(nest_result) # [(weight_name, new_weights),(weight_name, new_weights)...]
        return result