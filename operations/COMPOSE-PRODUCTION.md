# Docker Compose 部署

适用 test、staging、prod。目标机需要 Linux、Docker 和 Compose v2，不需要 Node.js。
先按 [镜像构建与离线交付](COMPOSE-IMAGE-DELIVERY.md) 准备安装包。

## 1. 分发

- platform/ 交给平台主机：Platform、Support、平台前端、数据库、网关及聚合引擎。
- participant/ 交给参与方主机：Runtime Agent、参与方前端、数据库、网关及训练引擎。

后续命令在各自站点目录执行。目录内必须包含 docker-compose.yml、runtime.yml、.env-example 和 images/。
两端使用独立数据卷；从 K3s 切换不会自动迁移数据。

## 2. 配置

```bash
cp -n .env-example .env
chmod 600 .env
vi .env
```

两端都要填写：

| 变量 | 内容 |
| --- | --- |
| POSTGRES_PASSWORD | 自行设置的数据库密码 |
| MEDOL_SECURITY_INTERNAL_TOKEN | 两端一致的服务间 Token |
| MEDOL_SECURITY_JWT_SECRET | 随机签名密钥，HS256 至少 32 字节 |
| MEDOL_SECURITY_ADMIN_BOOTSTRAP_ENABLED | 首次创建管理员时 true，完成后 false |
| MEDOL_SECURITY_ADMIN_BOOTSTRAP_SETUP_TOKEN | 自行设置的管理员初始化口令 |
| MEDOL_SECURITY_ALLOWED_ORIGINS | 浏览器访问本站点的实际 Origin，含协议、端口 |
| GATEWAY_PORT | 默认 9080；同机不同站点用不同端口 |
| 各项 *_IMAGE | 与导入镜像的名称、标签一致 |

平台额外填写：

```ini
AGGREGATION_ENGINE_IMAGE=fl/federation-learning-runtime-engine:pytorch-vision-0.0.1-SNAPSHOT
AGGREGATION_ENGINE_PLUGIN_PROFILE=pytorch-vision
FEDERATION_LEARNING_RUNTIME_AGENT_URL=http://participant.example:9080/api/federation-learning-runtime-agent
```

参与方额外填写：

```ini
PARTICIPANT_ENGINE_IMAGE=fl/federation-learning-runtime-engine:pytorch-vision-0.0.1-SNAPSHOT
PARTICIPANT_ENGINE_PLUGIN_PROFILE=pytorch-vision
PARTICIPANT_DATASETS_DIR=/data/fl/datasets
FEDERATION_LEARNING_PLATFORM_URL=http://platform.example:9080/api/federation-learning-platform
FEDERATION_LEARNING_SUPPORT_URL=http://platform.example:9080/api/federation-learning-support
MEDOL_SYNC_SOURCE_BASE_URL=http://platform.example:9080/api/federation-learning-platform
MEDOL_SYNC_SOURCE_BASE_URLS_DICTIONARYMAINTENANCE=http://platform.example:9080/api/federation-learning-support
```

替换域名和镜像版本。参与方宿主机的数据目录必须已存在、包含实际数据且容器可读；系统内登记路径使用 /workspace/datasets/...。跨主机不能用 localhost 或 Docker 服务名。

## 3. 启动

先平台、后参与方。直接使用包内镜像：

```bash
bash install.sh
```

脚本校验、导入镜像并启动，不拉取镜像。若没有 .env，首次运行会创建后退出，填好再重跑。

**使用 Registry 时**，先把 .env 中所有镜像改成完整 Registry 引用，再用以下命令替代 install.sh：

```bash
docker compose --env-file .env -f docker-compose.yml -f runtime.yml config --quiet
docker compose --env-file .env -f docker-compose.yml -f runtime.yml pull
docker compose --env-file .env -f docker-compose.yml -f runtime.yml up -d --wait --wait-timeout 300
```

## 4. 验证

```bash
docker compose --env-file .env -f docker-compose.yml -f runtime.yml ps
docker compose --env-file .env -f docker-compose.yml -f runtime.yml logs --tail=100
```

浏览器访问 http://本站点主机:9080，创建管理员并登录，然后关闭管理员初始化开关，再执行 up -d。
人工接入 Runtime Agent，不在平台选择 K3s 托管部署。训练引擎使用 prestarted 模式，Agent 镜像须支持该模式，执行计划镜像须与 PARTICIPANT_ENGINE_IMAGE 一致。
先验证一轮训练到聚合；安全聚合密钥另行配置。

## 5. 更新

改 .env 后执行 up -d，普通 restart 不会重新读取容器环境变量：

```bash
docker compose --env-file .env -f docker-compose.yml -f runtime.yml up -d --wait --wait-timeout 300
```

更新镜像前先导入或拉取新镜像，并备份 PostgreSQL、UmaDB。保持 Compose 项目名不变，禁止用 down -v 升级。正式对外开放前配置 HTTPS、防火墙；本方案不提供高可用。
