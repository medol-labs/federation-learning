from gemifl.crypts.phe_wrapper import *
import unittest


class TestPhe(unittest.TestCase):

    def __init__(self,
                 methodName: str = "runTest",
                 public_key=None,
                 private_key=None):
        super().__init__(methodName)
        public_key, private_key = paillier.generate_paillier_keypair(
            n_length=4096)
        self.public_key = public_key
        self.private_key = private_key

    def test_add(self):
        arr1 = np.ones(100) * np.random.randint(100)
        arr2 = np.ones(100) * np.random.randint(100)

        enc1 = encode_numbers(arr1, self.public_key)
        enc2 = encode_numbers(arr2, self.public_key)

        add_enc = phe_add(enc1, enc2)

        decrypt_add = phe_decrypt(add_enc, self.private_key)
        np.allclose(decrypt_add, arr1 + arr2, rtol=0.0001)


if __name__ == "__main__":
    unittest.main()