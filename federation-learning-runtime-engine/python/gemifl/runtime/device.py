"""Resolve devices without silently ignoring an explicit GPU request."""

import logging
import os


def resolve_torch_device(parameters, torch):
    requested = str(parameters.get("device") or os.environ.get("RUNTIME_ENGINE_DEVICE") or "cpu").strip().lower()
    if requested == "auto":
        requested = "cuda" if torch.cuda.is_available() else "cpu"
    if requested not in ("cpu", "cuda") and not (
        requested.startswith("cuda:") and requested[5:].isdigit()
    ):
        raise ValueError(f"Unsupported training device: {requested}")
    device = torch.device(requested)
    if device.type == "cuda":
        if not torch.cuda.is_available():
            raise RuntimeError("CUDA requested but unavailable. Check the CUDA image, NVIDIA driver and Compose GPU reservation.")
        index = device.index if device.index is not None else 0
        if index >= torch.cuda.device_count():
            raise ValueError(f"CUDA device index is unavailable: {index}")
        logging.getLogger(__name__).info(
            "Training device=%s gpu=%s torch=%s cuda=%s", device,
            torch.cuda.get_device_name(index), torch.__version__, torch.version.cuda,
        )
    return device
