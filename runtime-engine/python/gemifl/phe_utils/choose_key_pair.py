import os


def _platform_key_pair():
    public_key = os.getenv("RUNTIME_ENGINE_PAILLIER_PUBLIC_KEY") or os.getenv("GEMIFL_PAILLIER_PUBLIC_KEY", "")
    private_key = os.getenv("RUNTIME_ENGINE_PAILLIER_PRIVATE_KEY") or os.getenv("GEMIFL_PAILLIER_PRIVATE_KEY", "")

    key_dir = os.getenv("RUNTIME_ENGINE_PAILLIER_KEY_DIR") or os.getenv("GEMIFL_PAILLIER_KEY_DIR", "")
    if key_dir:
        public_key = public_key or os.path.join(key_dir, "public_key.pkl")
        private_key = private_key or os.path.join(key_dir, "private_key.pkl")

    return public_key, private_key


key_pair = _platform_key_pair()
