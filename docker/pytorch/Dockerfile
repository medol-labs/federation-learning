ARG BASE_IMAGE=python:3.11-slim
FROM ${BASE_IMAGE}

ENV PYTHONDONTWRITEBYTECODE=1
ENV PYTHONUNBUFFERED=1
ENV PIP_NO_CACHE_DIR=1

RUN \
  mkdir -p /workspace/app && \
  mkdir -p /workspace/tmp/runtime-engine && \
  mkdir -p /workspace/datasets && \
  mkdir -p /workspace/models && \
  mkdir -p /workspace/jobs && \
  mkdir -p /workspace/secrets/paillier

COPY python/requirements.txt /tmp/runtime-engine-requirements.txt
RUN pip install torch --index-url https://download.pytorch.org/whl/cpu
RUN pip install -r /tmp/runtime-engine-requirements.txt -i https://pypi.mirrors.ustc.edu.cn/simple/

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
