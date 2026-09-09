# coding: UTF-8
import phe
import numpy as np
import pickle
import argparse
import random
import os
########################################
'''

'''
########################################

def getOptions():
    parser = argparse.ArgumentParser(description="Densenet for BreaKhis")
    parser.add_argument('--num', default=1, type=int, help='number of keys to generate')
    parser.add_argument('--nlength', default=2048, type=int, help='number of keys to generate')
    parser.add_argument('--odir', default="", type=str, help='output dir')
    parser.add_argument('--overwrite_key', default=False, action='store_true', help='whether overwrite key')

    args = parser.parse_args()#解析参数

    return args


def main():
    options = getOptions()

    key_pairs = []
    for base_n in range(options.num):
        public_key, private_key = phe.paillier.generate_paillier_keypair(n_length=options.nlength)
        pubfile = os.path.join(options.odir,"public_key_%s_%s.pkl" % (str(options.nlength),str(base_n+1)))
        prvatfile = os.path.join(options.odir,"private_key_%s_%s.pkl" % (str(options.nlength),str(base_n+1)))

        if options.overwrite_key:
            with open(pubfile,'wb') as fin:
                pickle.dump(public_key, fin)
            with open(prvatfile,'wb') as fin:
                pickle.dump(private_key, fin)
        key_pairs.append((pubfile, prvatfile))

    with open(os.path.join(options.odir,"key_pairs.py"),'w') as f:
        f.write("key_pairs=%s" % str(key_pairs))

    choose_pair = random.choice(key_pairs)
    with open(os.path.join(options.odir,"choose_key_pair.py"),'w') as f:
        f.write("key_pair=%s" % str(choose_pair))



if __name__ == "__main__":
    main()