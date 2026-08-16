# coding: UTF-8
import os
import pickle

import os, fnmatch

def find_file(pattern, path):
    result = []
    for root, dirs, files in os.walk(path):
        for name in files:
            if fnmatch.fnmatch(name, pattern):
                result.append(os.path.join(root, name))
    return result

def divide_chunks(l, n):
    for i in range(0, len(l), n): 
        yield l[i:i + n]

def flatten_extend(nest):
    flat_list = []
    for row in nest:
        flat_list.extend(row)
    return flat_list


def load_data(ds):
    if os.path.exists(ds):
        with open(ds, "rb") as f:
            data = pickle.load(f)

    else:
        raise ValueError("Data file does not exit!")
    return data
