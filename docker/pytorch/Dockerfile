ARG BASE_IMAGE=python:3.11-slim
FROM ${BASE_IMAGE}

ARG PLUGIN_PROFILE=full

ENV PYTHONDONTWRITEBYTECODE=1
ENV PYTHONUNBUFFERED=1
ENV PIP_NO_CACHE_DIR=1
ENV RUNTIME_ENGINE_PLUGIN_PROFILE=${PLUGIN_PROFILE}

RUN \
  mkdir -p /workspace/app && \
  mkdir -p /workspace/tmp/runtime-engine && \
  mkdir -p /workspace/datasets && \
  mkdir -p /workspace/models && \
  mkdir -p /workspace/jobs && \
  mkdir -p /workspace/secrets/paillier

COPY python/requirements*.txt /tmp/
RUN \
  case "${PLUGIN_PROFILE}" in \
    core|sklearn) \
      echo "Skipping PyTorch wheel installation for ${PLUGIN_PROFILE} runtime-engine profile" ;; \
    pytorch-vision|pytorch-tabular|pytorch-nlp|pytorch-graph|full) \
      pip install torch torchvision --index-url https://download.pytorch.org/whl/cpu ;; \
    *) \
      echo "Unsupported runtime-engine plugin profile: ${PLUGIN_PROFILE}" >&2 && exit 1 ;; \
  esac
RUN \
  test -f "/tmp/requirements-${PLUGIN_PROFILE}.txt" || \
    (echo "Missing requirements file for runtime-engine plugin profile: ${PLUGIN_PROFILE}" >&2 && exit 1)
RUN pip install -r "/tmp/requirements-${PLUGIN_PROFILE}.txt" -i https://pypi.mirrors.ustc.edu.cn/simple/

COPY . /workspace/app
WORKDIR /workspace/app/python
RUN pip install -e . -i https://pypi.mirrors.ustc.edu.cn/simple/

ENV RUNTIME_ENGINE_NODE_NAME=runtime-node
ENV RUNTIME_ENGINE_HOST=0.0.0.0
ENV RUNTIME_ENGINE_PORT=8080
ENV RUNTIME_ENGINE_ROOT=/workspace/tmp/runtime-engine
ENV RUNTIME_ENGINE_PAILLIER_KEY_DIR=/workspace/secrets/paillier

ADD ./docker/pytorch/start_services.sh /workspace/start_services.sh
RUN chmod +x /workspace/start_services.sh

CMD ["/workspace/start_services.sh"]

EXPOSE 8080

WORKDIR /workspace
