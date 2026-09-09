# coding: UTF-8
import phe
import numpy as np
import multiprocess
from gemifl.utils.basefunctions import *

def encrypt_number(public_key, v):
    en_v = public_key.encrypt(float(v))
    return (phe.util.int_to_base64(en_v.ciphertext()), en_v.exponent) 

def encrypt_list(args):
    public_key, chunk = args
    return [encrypt_number(public_key, num) for num in chunk]

def encrypt_array(public_key,x, workers=-1):
    '''
    x = [1,2,3,4,5,6]
    '''
    if workers==-1:
        workers = multiprocess.cpu_count()

    x_chunks = divide_chunks(x, workers) # [[1,2,3],[4,5,6]]
    
    with multiprocess.Pool(processes=workers) as pool:
        nest_result = pool.map(encrypt_list, [(public_key,i) for i in x_chunks])
    return flatten_extend(nest_result)

def decrypt_number(public_key,private_key, v):
    ennumber = phe.paillier.EncryptedNumber(public_key, ciphertext=phe.util.base64_to_int(v[0]), exponent=int(v[1]))
    return private_key.decrypt(ennumber) 

def decrypt_list(args):
    public_key,private_key, chunk = args
    return [decrypt_number(public_key,private_key, num) for num in chunk]

def decrypt_array(public_key, private_key, x, workers=-1):
    if workers==-1:
        workers = multiprocess.cpu_count()
    
    x_chunks = divide_chunks(x, workers)
    with multiprocess.Pool(processes=workers) as pool:
        nest_result = pool.map(decrypt_list, [(public_key,private_key,i) for i in x_chunks])
    return flatten_extend(nest_result)

def pailler_array_add(arr1, arr2, public_key, cors1=[], cors2=[], isconvert=True, workers=-1):
    if workers==-1:
        workers = multiprocess.cpu_count()

    if len(cors1) == 0:
        cors1 = [1 for i in range(len(arr1))]
    if len(cors2) == 0:
        cors2 = [1 for i in range(len(arr2))]


    input_list = []

    for i in range(len(arr1)):
        templist = [arr1[i], arr2[i], cors1[i], cors2[i]]
        input_list.append(templist)
        #addnum = pailler_item_add(arr1[i],arr2[i],cors1[i], cors2[i], public_key,isconvert)
        #result.append(addnum)

    x_chunks = list(divide_chunks(input_list, workers))
    with multiprocess.Pool(processes=workers) as pool:
        nest_result = pool.map(pailler_list_add, [(i,public_key,isconvert) for i in x_chunks])
    return flatten_extend(nest_result)

def pailler_list_add(args):
    inputlist, public_key, isconvert = args


    result = []
    for i in range(len(inputlist)):
        addnum = pailler_item_add(inputlist[i][0], inputlist[i][1], inputlist[i][2], inputlist[i][3], public_key, isconvert)
        result.append(addnum)
    return result

def pailler_item_add(num1, num2, c1, c2, public_key, isconvert=True):
    if not isconvert:
        num1 = phe.paillier.EncryptedNumber(public_key, ciphertext=phe.util.base64_to_int(num1[0]),exponent=int(num1[1]))
        num2 = phe.paillier.EncryptedNumber(public_key, ciphertext=phe.util.base64_to_int(num2[0]),exponent=int(num2[1]))

    return num1*c1+num2*c2

def avg_weights_args(args):
    client_weights_tuple, client_sizes, public_key = args # client_weights_tuple=[("layer1", [[(str,-15),...as party1], [(str,-15),...as party2], ...]), ("layer2", [[(str,-15),...as party1], [(str,-15),...as party2], ...])]

    result = []
    #print(len(client_weights_tuple)) # 7
    #print(len(client_weights_tuple[0])) #2
    total_size = sum(client_sizes)
    for client_item in client_weights_tuple:
        client_weights = client_item[1]
        weight_name = client_item[0]
        cipherdata = [
            phe.paillier.EncryptedNumber(public_key, ciphertext=phe.util.base64_to_int(v[0]),
                                         exponent=int(v[1])) * client_sizes[0] / total_size
            for v in client_weights[0]
        ]

        for c in range(1, len(client_weights)):
            encrypt_values = [
                phe.paillier.EncryptedNumber(public_key, ciphertext=phe.util.base64_to_int(v[0]),
                                             exponent=int(v[1])) * client_sizes[c] / total_size
                for v in client_weights[c]
            ]

            for el in range(len(encrypt_values)):
                cipherdata[el] += encrypt_values[el]
        new_weights = [(phe.util.int_to_base64(x.ciphertext()), x.exponent) for x in cipherdata]
        result.append((weight_name, new_weights))

    return result




