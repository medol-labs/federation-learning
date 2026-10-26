# zot operations

Optional zot OCI registry operations assets for FederationLearningPlatform (dev).

zot is the recommended lightweight registry for ARM64 and offline LAN deployments. The official container image is multi-architecture, so Docker or containerd selects the host architecture automatically unless you explicitly pull another platform.

## Start

```bash
cp .env-example .env
docker compose up -d
```

The registry listens on `ZOT_HTTP_PORT` and stores blobs under the `zot-data` volume.

## Offline image package

On a connected machine, pull and save the image for the target architecture:

```bash
docker pull --platform linux/arm64 ghcr.io/project-zot/zot:v2.1.20
docker save ghcr.io/project-zot/zot:v2.1.20 -o zot-linux-arm64-v2.1.20.tar
```

Load it on the offline host:

```bash
docker load -i zot-linux-arm64-v2.1.20.tar
```

## Application image naming

Use this registry as the generated operations image prefix, for example:

```text
federation-learning-platform-zot.local:5000/federation-learning-platform/<service>:<tag>
```

For plain HTTP LAN deployments, configure Docker/containerd on each node to trust the registry as an insecure registry.
