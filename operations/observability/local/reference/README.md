# OpenTelemetry + Grafana Reference

Independent, single-host development/test stack: Collector -> Prometheus (metrics), Loki (logs), Tempo (traces) -> Grafana. No Mimir, Kafka or application-specific logic. This reference does not replace managed LGTM automatically, and is not a production HA installation.

## Start manually

Run from this directory. Copy .env-example to .env and set a private GRAFANA_ADMIN_PASSWORD. Never commit .env. Then:

~~~sh
docker compose -p medol-telemetry-reference config --quiet
docker compose -p medol-telemetry-reference up -d
docker compose -p medol-telemetry-reference ps
~~~

Open http://127.0.0.1:13000 and sign in with your configured account. Prometheus, Loki, Tempo and the Telemetry Reference dashboard are provisioned automatically. Password environment variables initialize a new Grafana database only; changing them does not rotate an existing account password.

## Application connection

For native JVM applications on this host:

~~~sh
export MEDOL_OBSERVABILITY_ENABLED=true
export OTEL_EXPORTER_OTLP_ENDPOINT=http://127.0.0.1:14318
export MEDOL_OBSERVABILITY_ENVIRONMENT=local
export MEDOL_TRACING_SAMPLE_PROBABILITY=1.0
~~~

Generated applications share service.instance.id across metrics, logs and traces. Its fallback combines HOSTNAME (or local), application name and server port. Set OTEL_SERVICE_INSTANCE_ID to a unique deployment instance identifier (for example, a Pod UID) in multi-host deployments, especially when HOSTNAME is unset. Never reuse one identifier for multiple running replicas.

Restart the application. The generated Spring configuration appends /v1/logs, /v1/metrics and /v1/traces. Use 1.0 sampling only for verification; reduce it for normal operation. Never enable a second Java instrumentation agent blindly.

For generated operations choose observability.mode: external and set endpoint to the address reachable FROM the application. This avoids automatically deploying the managed LGTM stack. Container localhost is not the host: join the reference Compose network and use http://collector:4318, or explicitly provide a protected routable Collector. The default network is medol-telemetry-reference_default. An example operations config for containers sharing that network:

~~~yaml
environments:
  local:
    observability:
      mode: external
      endpoint: http://collector:4318
      samplingProbability: 0.1
~~~

Network attachment is a separate deployment action; setting the URL does not attach a container. Kubernetes and remote machines cannot reach these loopback bindings. Deploy a reachable Collector with TLS/authentication or provide an authenticated tunnel. Do not publish this unauthenticated OTLP receiver, Prometheus, Loki or Tempo to untrusted networks. Operations supplies artifacts; deployment remains manual or performed by simulation, not by generation.

## Verify

1. Send a real HTTP request to an instrumented service, then wait at least 45 seconds for export and scraping.
2. Grafana Explore / Prometheus: inspect target_info and search JVM/HTTP metrics. The reference dashboard checks ingestion infrastructure, not business workflow correctness.
3. Explore / Loki: select service_name; check the request's log timestamp and correlation ID.
4. Explore / Tempo: search resource.service.name and inspect HTTP spans. Axon asynchronous causation IDs are not automatically a distributed trace; full Axon propagation is separate work.
5. Stop collector and confirm business requests still succeed; restart it and confirm new telemetry arrives. In-memory queues are bounded and not lossless across restart.

~~~sh
docker compose -p medol-telemetry-reference logs --tail=100 collector
docker compose -p medol-telemetry-reference run --rm --no-deps collector validate --config=/etc/otelcol/config.yml
docker compose -p medol-telemetry-reference run --rm --no-deps --entrypoint promtool prometheus check config /etc/prometheus/prometheus.yml
~~~

CollectorMetricsUnavailable is evaluated in Prometheus; no Alertmanager or external notification receiver is configured. It detects a failed scrape, not individual application failure. No host/GPU agent or runtime-engine Python instrumentation is included.

## Storage and operations

Named volumes persist data. Prometheus is limited to seven days / 5GB; Loki compacts with seven-day retention. Tempo uses its version's default block retention; configure and capacity-test it before long-running use. Grafana dashboards/settings persist separately. Stop using docker compose -p medol-telemetry-reference down; add -v ONLY when intentionally deleting all monitoring data.

Images are pinned in docker-compose.yml and images.json. Use docker compose pull to fetch for the host architecture, or pull/save the listed images explicitly for your offline target architecture. This standalone reference is not part of the managed LGTM image bundle or simulation service selection. Copy the entire reference directory when deploying; mounting only docker-compose.yml is insufficient.

Before production: TLS/authentication, network policy, secrets, backups, storage capacity, explicit retention, HA, notification routing and sensitive-data filtering must be designed. Logs are not an audit store. Payload logging remains disabled by default; avoid sensitive attributes and high-cardinality metric labels.

References:
- https://opentelemetry.io/docs/collector/configuration/
- https://grafana.com/docs/loki/latest/send-data/otel/
- https://grafana.com/docs/tempo/latest/set-up-for-tracing/setup-tempo/plan/deployment-modes/
- https://prometheus.io/docs/prometheus/latest/configuration/configuration/
