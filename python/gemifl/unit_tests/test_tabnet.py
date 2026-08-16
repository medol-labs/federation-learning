from gemifl.algorithms.models_python.nn.TabNetClient import TabNetClient
import unittest


class TestTabNet(unittest.TestCase):

    def __init__(self, methodName: str = "runTest") -> None:
        super().__init__(methodName)
        config = {
            "nodes": {
                "Alice": "127.0.0.1:1439",
                "Bob": "127.0.0.1:1440",
                "Charlie": "127.0.0.1:1441"
            },
            "roles": {
                "TabNetClient": ["Alice", "Bob", "Charlie"]
            },
            "model_parameter": {
                "module":
                    "tabNet.tabnet_client",
                "process":
                    "train",
                "engine":
                    "python",
                "epochs":
                    3,
                "cat_idxs": [
                    0, 1, 2, 3, 4, 5, 6, 7, 19, 21, 22, 23, 24, 25, 26, 27, 28,
                    29, 30, 31, 32
                ],
                "cat_dims": [
                    2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2,
                    2
                ]
            },
            "node_parameter": {
                "Alice": {
                    "data_source": {
                        "train_ds":
                            "/Users/xusong/Documents/githubs/GemiFL/datasets/cornary_heart_disease/train/0.pkl",
                        "test_ds":
                            "/Users/xusong/Documents/githubs/GemiFL/datasets/cornary_heart_disease/test/0.pkl"
                    }
                },
                "Bob": {
                    "data_source": {
                        "train_ds":
                            "/Users/xusong/Documents/githubs/GemiFL/datasets/cornary_heart_disease/train/1.pkl",
                        "test_ds":
                            "/Users/xusong/Documents/githubs/GemiFL/datasets/cornary_heart_disease/test/1.pkl"
                    }
                },
                "Charlie": {
                    "data_source": {
                        "train_ds":
                            "/Users/xusong/Documents/githubs/GemiFL/datasets/cornary_heart_disease/train/2.pkl",
                        "test_ds":
                            "/Users/xusong/Documents/githubs/GemiFL/datasets/cornary_heart_disease/test/2.pkl"
                    }
                }
            }
        }
        self.model = TabNetClient(config)

    def test_model(self):
        self.model.run()


if __name__ == "__main__":
    unittest.main()