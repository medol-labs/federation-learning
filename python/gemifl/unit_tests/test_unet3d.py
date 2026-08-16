from gemifl.algorithms.models_python.nn.CenterUnet3d import UNet3d, train
import unittest


class TestTabNet(unittest.TestCase):

    def __init__(self, methodName: str = "runTest") -> None:
        super().__init__(methodName)

    def test_unet3d(self):
        import os
        import pickle

        train_path = "/Users/xusong/Documents/githubs/GemiFL/datasets/brain_tumor/train/0.pkl"
        with open(train_path, "rb") as fin:
            train_loader = pickle.load(fin)

        model = UNet3d(in_channels=4, n_classes=3, n_channels=24).to('cpu')
        train(model, train_loader)


if __name__ == "__main__":
    unittest.main()
