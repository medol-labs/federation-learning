# Observability

Mode: managed. OTLP protocol: HTTP/protobuf.

See [OpenTelemetry + Grafana reference](reference/README.md) for independently configured Collector, Prometheus, Loki, Tempo and Grafana services (no Mimir). This is a separate opt-in stack, not an automatic replacement of managed LGTM.

Configure operations/config/<environment>.yml and regenerate operations. Configuration examples:

```yaml
environments:
  local:
    observability:
      mode: managed # disabled | external | managed
      samplingProbability: 0.1
      storageSize: 10Gi
```

```yaml
observability:
  mode: external
  endpoint: https://collector.example.com:4318
```

Managed mode is a development/test LGTM reference, not production HA. It is included in generated Compose and Kubernetes/K3s resources; operations does not execute deployments. Simulation may deploy those resources using its existing environment/deploy stages. When selecting individual services, include medol-observability or use external mode.

Compose Grafana binds to loopback port 3000 (override MEDOL_GRAFANA_PORT per site). Kubernetes uses ClusterIP: kubectl -n <namespace> port-forward service/medol-observability 3000:3000. Reference image login is admin/admin; change it immediately. Never expose this test stack or its unauthenticated OTLP receiver to untrusted networks.

Each isolated deployment site in managed mode gets its own stack. For centralized multi-site observations use external mode with a reachable Collector endpoint; container DNS is not cross-host routing. Native JVM processes must use a reachable endpoint, not the Compose service DNS.

Spring applications export metrics, traces and logs when MEDOL_OBSERVABILITY_ENABLED=true. Endpoint paths /v1/metrics, /v1/traces and /v1/logs are appended to the base URL. Disabled mode performs no telemetry export. Do not install a second Java auto-instrumentation agent on top without reviewing duplicate instrumentation.

Switching to disabled requires regeneration and application restart. It does not delete previously deployed collectors or data volumes; retire those explicitly through your deployment workflow.

Use Spring management OTLP headers via private configuration or Secrets for authenticated external collectors. Do not put tokens in endpoint URLs or committed files. Production TLS, collector authentication, retention and alert routing belong to the external platform.

Axon payload/metadata logging is disabled by default; opt-in debug output may contain sensitive business data. Audit retention is separate from telemetry retention. No model execution graph, Axon token-lag monitor, host/GPU metrics or Python/browser instrumentation is implied by this baseline.

Verify a request in Grafana Explore: logs by service name, HTTP spans in Tempo, and JVM/HTTP metrics in Prometheus. Stop the collector and confirm business requests still succeed. Generated configs are not proof that telemetry was received.

References: https://github.com/grafana/docker-otel-lgtm and https://docs.spring.io/spring-boot/3.5/reference/actuator/observability.html
