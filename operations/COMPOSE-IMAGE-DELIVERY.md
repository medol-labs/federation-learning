# Compose 镜像构建与离线交付

以 prod、ARM64 为例；test / staging 替换环境名，x86 用 linux/amd64。构建机有源码、Node.js、Docker；部署机只需 Docker 和 Compose v2。

## 1. 构建机：准备生成产物

在生成器环境生成 Compose（已生成可跳过）：

```bash
gen --generator operations --generator-type docker-compose --environment prod --force
```

以下命令都在 Federation Learning 项目根目录执行。

## 2. 构建并导出镜像

```bash
ENV=prod
ARCH=linux/arm64
IMAGE_DIR="$PWD/operations/images/$ENV/.work/image-bundle/images"
node operations/images/images.mjs package --environment "$ENV" --platform "$ARCH" --skip-archive
(
  cd federation-learning-runtime-engine
  node scripts/build-images.mjs --environment "$ENV" --platform "$ARCH" --plugin-profile pytorch-vision
  node scripts/export-images.mjs --environment "$ENV" --platform "$ARCH" --plugin-profile pytorch-vision \
    --output "$IMAGE_DIR/runtime-engine-pytorch-vision.tar"
)
```

第一条 package 构建应用、拉取依赖并导出 tar；skip-archive 只跳过外层压缩。引擎单独处理。已有本次发布完整归档时跳过这一步。生成输入哈希不匹配时，先重新生成对应代码再构建，不用旧 tar 冒充新产物。

## 3. 打交付包

```bash
DELIVERY="/tmp/fl-compose-$ENV"
node scripts/package-compose.mjs --environment "$ENV" \
  --output "$DELIVERY"
```

输出目录可重复使用：内容相同的文件跳过，有变化的文件更新，已有 `.env` 等额外文件保留，不自动删除历史文件。跨版本交付建议使用新目录，避免残留旧镜像归档。默认生成 platform/、participant/，可加 --site 只打一个。每个目录包含 Compose、runtime.yml、网关配置、数据库初始化文件、.env-example、镜像 tar、install.sh 和 SHA256SUMS。真实凭据、数据集和历史数据库不在包内。

Runtime Engine 归档可选：默认复制该环境 `.work/image-bundle/images/runtime-engine-pytorch-vision.tar`，不存在则提示并跳过。其他路径可用 `--engine-archive <tar>` 指定，显式指定的文件必须存在。未携带引擎归档时，部署机须在执行 `install.sh` 前自行 load 或 pull 对应引擎镜像；安装脚本不会自动拉取。

可直接复制目录，也可压缩传输：

```bash
tar -czf "$DELIVERY.tar.gz" -C "$DELIVERY" .
```

## 4A. 直接离线安装

把对应站点目录交给部署机，按 [Compose 部署](COMPOSE-PRODUCTION.md) 填 .env，执行 bash install.sh。它会校验并导入包内镜像，不必单独 docker load。

## 4B. 离线传给发布机，再推送 Zot / Registry

构建机不必能访问生产 Registry。将交付包交给能访问 Registry 的发布机，在各站点目录执行：

```bash
sha256sum -c SHA256SUMS
for archive in images/*.tar; do docker load -i "$archive" || exit 1; done
docker image ls
```

HTTP Registry：在发布机 /etc/docker/daemon.json 中合并以下配置，不要覆盖已有键：

```json
{"insecure-registries":["registry.example.com:5000"]}
```

```bash
sudo dockerd --validate --config-file=/etc/docker/daemon.json
sudo systemctl restart docker
```

打交付包时自动在每个站点目录生成 `publish.tsv`，从所有已打包归档读取源镜像（包括可选引擎），无需手工列举。第二列使用 `<REGISTRY>` 占位符，发布时指定地址：

```text
fl/federation-learning-support:0.0.1-SNAPSHOT	<REGISTRY>/fl/federation-learning-support:0.0.1-SNAPSHOT
postgres:16	<REGISTRY>/library/postgres:16
```

上面仅展示格式，实际文件自动列全归档内镜像并去重。进入站点交付目录，一键校验、导入并推送全部镜像：

```bash
bash publish.sh all registry.example.com:5000

# 也可以分步执行
bash publish.sh load
bash publish.sh push registry.example.com:5000
```

也支持 `REGISTRY=registry.example.com:5000 bash publish.sh all`。地址不带协议或命名空间；脚本保留清单中的仓库路径。任一步失败立即停止，推送前先检查所有源镜像是否存在。此命令不启动服务。

启用认证时先 docker login。不同架构不要用同名单架构标签相互覆盖。

部署机的 .env 填写上述完整目标引用，按 [Compose 部署](COMPOSE-PRODUCTION.md) 的 Registry 分支 pull、up，不执行离线 install.sh。HTTP Registry 配置也需应用到部署机的 Docker；与 K3s registries.yaml 无关。
