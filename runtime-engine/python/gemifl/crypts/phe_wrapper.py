import os
import time
from functools import wraps
from phe import paillier
import numpy as np
from multiprocess import Pool
from collections import Iterable
from functools import partial

cpu_num = os.cpu_count()
MAX_POOLS = cpu_num - 2


def timeit(func):

    @wraps(func)
    def timeit_wrapper(*args, **kwargs):
        start_time = time.perf_counter()
        result = func(*args, **kwargs)
        end_time = time.perf_counter()
        total_time = end_time - start_time
        print(
            f'Function {func.__name__} {kwargs} Took {total_time:.4f} seconds')
        return result

    return timeit_wrapper


def encrypt_list(flat_list, public_key):
    return [public_key.encrypt(item) for item in flat_list]


@timeit
def encode_numbers(plain_arr, public_key, pools=5, limit_size=100):
    map_pool = Pool(MAX_POOLS)
    if isinstance(plain_arr, Iterable):
        if isinstance(plain_arr, list):
            plain_arr = np.array(plain_arr)

        arr_shape = plain_arr.shape
        flatten_list = plain_arr.flatten().tolist()

        cuts = [
            flatten_list[x:(x + limit_size)]
            for x in range(0, len(flatten_list), limit_size)
        ]
        results = map_pool.map(partial(encrypt_list, public_key=public_key),
                               cuts)

        return np.array(results).reshape(arr_shape)

    else:
        return public_key.encrypt(plain_arr)


def encrypt_add(flat_arr1, flat_arr2):
    res = [item1 + item2 for item1, item2 in zip(flat_arr1, flat_arr2)]
    return res


@timeit
def phe_add(encrypt_arr1, encrypt_arr2):
    shape = encrypt_arr1.shape
    pools = MAX_POOLS
    map_pool = Pool(pools)

    flat_arr1 = encrypt_arr1.flatten()
    flat_arr2 = encrypt_arr2.flatten()

    assert len(flat_arr1) == len(flat_arr2)

    bucket_size = int(np.ceil(len(flat_arr1) / pools))

    cuts1 = [
        flat_arr1[x:(x + bucket_size)]
        for x in range(0, len(flat_arr1), bucket_size)
    ]

    cuts2 = [
        flat_arr2[x:(x + bucket_size)]
        for x in range(0, len(flat_arr2), bucket_size)
    ]

    # cuts = [(flat_arr1[x:(x + bucket_size)], flat_arr2[x:(x + bucket_size)])
    #         for x in range(0, len(flat_arr1), bucket_size)]

    results = map_pool.starmap(encrypt_add, zip(cuts1, cuts2))

    return np.array(results).reshape(shape)


def phe_multiply(encrypt_arr, weights):
    res = []
    shape = encrypt_arr.shape
    flat_arr = encrypt_arr.flatten()
    if type(weights) == int or float:
        for item in encrypt_arr:
            res.append(item * weights)
    else:
        flat_weights = weights.flatten()
        assert len(flat_arr) == len(flat_weights)

        for tmp_weight, tmp_item in zip(flat_weights, flat_arr):
            res.append(tmp_item * tmp_weight)

    return np.array(res).reshape(shape)


def decrypt_list(encode_list, private_key):
    return [private_key.decrypt(item) for item in encode_list]


@timeit
def phe_decrypt(encode_arr, private_key):
    if isinstance(encode_arr, Iterable):
        map_pool = Pool(MAX_POOLS)

        if isinstance(encode_arr, list):
            encode_arr = np.array(encode_arr)

        shape = encode_arr.shape
        flat_arr = encode_arr.flatten().tolist()
        bucket_size = int(np.ceil(len(flat_arr) / MAX_POOLS))

        cuts = [
            flat_arr[x:(x + bucket_size)]
            for x in range(0, len(flat_arr), bucket_size)
        ]

        results = map_pool.map(partial(decrypt_list, private_key=private_key),
                               cuts)
        return np.array(results).reshape(shape)
    else:
        return private_key.decrypt(encode_arr)