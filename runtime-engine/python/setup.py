from setuptools import setup, find_packages

setup(
    name="runtime-engine",
    version="0.0.1",
    packages=find_packages(),
    install_requires=["setuptools", "grpcio", "protobuf", "cloudpickle"],
    description="Runtime engine for platform-orchestrated federated training",
    entry_points={
        "console_scripts": [
            "runtime-engine-run-job = gemifl.runtime.executor:main",
            "runtime-engine-node = gemifl.runtime.node:main",
            "runtime-engine-capabilities = gemifl.runtime.capabilities:main",
            "gemifl-run-job = gemifl.runtime.executor:main",
            "gemifl-runtime-node = gemifl.runtime.node:main",
            "gemifl-runtime-capabilities = gemifl.runtime.capabilities:main"
        ]
    },
)
