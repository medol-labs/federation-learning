import unittest
from types import SimpleNamespace
from unittest.mock import Mock, patch

from gemifl.runtime.device import resolve_torch_device


class RuntimeDeviceTest(unittest.TestCase):
    def setUp(self):
        self.torch = Mock()
        self.torch.device.side_effect = lambda value: SimpleNamespace(
            type=value.split(":")[0],
            index=int(value.split(":")[1]) if ":" in value else None,
        )
        self.torch.cuda.is_available.return_value = True
        self.torch.cuda.device_count.return_value = 1
        self.torch.__version__ = "test"

    @patch.dict("os.environ", {}, clear=True)
    def test_default_preserves_cpu(self):
        self.assertEqual(resolve_torch_device({}, self.torch).type, "cpu")

    @patch.dict("os.environ", {"RUNTIME_ENGINE_DEVICE": "cuda"})
    def test_deployment_default_and_task_override(self):
        self.assertEqual(resolve_torch_device({}, self.torch).type, "cuda")
        self.assertEqual(resolve_torch_device({"device": "cpu"}, self.torch).type, "cpu")

    def test_explicit_cuda_fails_without_gpu(self):
        self.torch.cuda.is_available.return_value = False
        with self.assertRaisesRegex(RuntimeError, "CUDA requested"):
            resolve_torch_device({"device": "cuda"}, self.torch)

    def test_auto_fallback(self):
        self.torch.cuda.is_available.return_value = False
        self.assertEqual(resolve_torch_device({"device": "auto"}, self.torch).type, "cpu")
        self.torch.cuda.is_available.return_value = True
        self.assertEqual(resolve_torch_device({"device": "auto"}, self.torch).type, "cuda")

    def test_invalid_device(self):
        for device in ("invalid", "cuda:-1", "cuda:1"):
            with self.subTest(device=device), self.assertRaises(ValueError):
                resolve_torch_device({"device": device}, self.torch)


if __name__ == "__main__":
    unittest.main()
