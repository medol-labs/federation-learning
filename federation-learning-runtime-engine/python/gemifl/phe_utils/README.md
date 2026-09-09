# 基于Paillier的半同态加密

## 生成公私密钥

#### Shell

    python paillier_generate_keys.py --num=4 --nlength=2048 --odir=PYTHON_LIB_DIR/gemifl/phe_utils  --overwrite_key

#### python

    import phe
    public_key, private_key = phe.paillier.generate_paillier_keypair(n_length=2048)

##  对单个数值加解密

    # encryption
    v = 256
    encrypted_number = public_key.encrypt(float(v))   # paillier object
    encrypted_data_pair = (phe.util.int_to_base64(encrypted_number.ciphertext()), encrypted_number.exponent)
    # decryption
    encrypted_number_for_decryption = phe.paillier.EncryptedNumber(public_key, ciphertext=phe.util.base64_to_int(encrypted_data_pair[0]), exponent=int(encrypted_data_pair[1]))
    number = private_key.decrypt(encrypted_number_for_decryption)

## 对numpy数组加解密

    from gemifl.phe_utils.paillier_utils import *
    import numpy as np
    arr = np.array([1,2,3])
    encrypt_arr = encrypt_array(public_key,arr)
    decrypt_arr = decrypt_array(public_key,private_key,encrypt_arr)

## Note

具体训练过程中的使用，见[DenseNet Training Test](https://github.com/GemiFL/GemiFL/blob/paillier/python/gemifl/algorithms/models_python/DenseNet1/HorizontalDenseNetClient.py)





