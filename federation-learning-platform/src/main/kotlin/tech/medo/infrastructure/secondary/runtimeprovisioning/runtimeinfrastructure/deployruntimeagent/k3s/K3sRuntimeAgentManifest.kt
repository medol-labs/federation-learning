package tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.k3s

import tech.medo.runtimeprovisioning.runtimeinstallationplancatalog.RuntimeInstallationPlanCatalogReadModel
import java.nio.file.Files
import java.nio.file.Paths
import java.util.Locale
import java.util.UUID

data class K3sRuntimeAgentManifest(
    val manifestFile: String,
    val deploymentName: String
)

fun prepareRuntimeAgentManifest(
    properties: K3sRuntimeInfrastructureProperties,
    runtimeAgentId: UUID,
    runtimeInfrastructureId: UUID,
    plan: RuntimeInstallationPlanCatalogReadModel
): K3sRuntimeAgentManifest {
    val configuredFile = properties.agentManifestFile?.takeIf { it.isNotBlank() }
    if (configuredFile != null && Files.exists(Paths.get(configuredFile))) {
        return K3sRuntimeAgentManifest(
            manifestFile = configuredFile,
            deploymentName = properties.agentDeploymentName
        )
    }

    val deploymentName = runtimeAgentDeploymentName(properties, runtimeAgentId)
    val manifest = renderRuntimeAgentManifest(properties, runtimeAgentId, runtimeInfrastructureId, plan, deploymentName)
    val directory = Paths.get(properties.renderedManifestDirectory)
    Files.createDirectories(directory)
    val manifestPath = directory.resolve("$deploymentName.yaml")
    Files.writeString(manifestPath, manifest)
    return K3sRuntimeAgentManifest(
        manifestFile = manifestPath.toString(),
        deploymentName = deploymentName
    )
}

fun runtimeAgentDeploymentName(properties: K3sRuntimeInfrastructureProperties, runtimeAgentId: UUID): String {
    val suffix = runtimeAgentId.toString().replace("-", "").take(12)
    return kubernetesName("${properties.agentDeploymentName}-$suffix")
}

private fun renderRuntimeAgentManifest(
    properties: K3sRuntimeInfrastructureProperties,
    runtimeAgentId: UUID,
    runtimeInfrastructureId: UUID,
    plan: RuntimeInstallationPlanCatalogReadModel,
    deploymentName: String
): String {
    val runtimeName = plan.runtimeName ?: deploymentName
    val organizationId = plan.organizationId?.toString().orEmpty()
    val endpoint = "http://$deploymentName:${properties.agentContainerPort}"
    val replicas = properties.agentReplicas.coerceAtLeast(1)
    return """
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ${quote(deploymentName)}
  labels:
    app: ${quote(deploymentName)}
    app.kubernetes.io/name: ${quote(deploymentName)}
    app.kubernetes.io/component: "runtime-agent"
    app.kubernetes.io/managed-by: "federation-learning-platform"
spec:
  replicas: $replicas
  selector:
    matchLabels:
      app: ${quote(deploymentName)}
  template:
    metadata:
      labels:
        app: ${quote(deploymentName)}
    spec:
      containers:
        - name: "runtime-agent"
          image: ${quote(properties.agentImage)}
          ports:
            - containerPort: ${properties.agentContainerPort}
          env:
            - name: "SERVER_PORT"
              value: ${quote(properties.agentContainerPort.toString())}
            - name: "SPRING_DOCKER_COMPOSE_ENABLED"
              value: "false"
            - name: "AXON_SERVER_ENABLED"
              value: "false"
            - name: "AXON_SERVER_EVENT_STORE_ENABLED"
              value: "false"
            - name: "AXON_UPDATE_CHECK_DISABLED"
              value: "true"
            - name: "MEDOL_AXON_EVENT_STORAGE"
              value: "umadb"
            - name: "UMADB_TARGET"
              value: ${quote(properties.umadbTarget)}
            - name: "UMADB_PLAINTEXT"
              value: "true"
            - name: "UMADB_API_KEY"
              value: ""
            - name: "UMADB_BATCH_SIZE"
              value: "256"
            - name: "UMADB_REQUEST_TIMEOUT"
              value: "PT10S"
            - name: "DB_URL"
              value: ${quote(properties.databaseUrl)}
            - name: "DB_USERNAME"
              valueFrom:
                secretKeyRef:
                  name: ${quote(properties.databaseSecretName)}
                  key: "username"
            - name: "DB_PASSWORD"
              valueFrom:
                secretKeyRef:
                  name: ${quote(properties.databaseSecretName)}
                  key: "password"
            - name: "FEDERATION_LEARNING_PLATFORM_URL"
              value: ${quote(properties.platformUrl)}
            - name: "FEDERATION_LEARNING_SUPPORT_URL"
              value: ${quote(properties.supportUrl)}
            - name: "RUNTIME_AGENT_ID"
              value: ${quote(runtimeAgentId.toString())}
            - name: "RUNTIME_INFRASTRUCTURE_ID"
              value: ${quote(runtimeInfrastructureId.toString())}
            - name: "RUNTIME_AGENT_VERSION"
              value: ${quote(properties.agentVersion)}
            - name: "RUNTIME_AGENT_INSTALL_MODE"
              value: "PLATFORM_MANAGED"
            - name: "RUNTIME_AGENT_ORGANIZATION_ID"
              value: ${quote(organizationId)}
            - name: "RUNTIME_AGENT_RUNTIME_NAME"
              value: ${quote(runtimeName)}
            - name: "RUNTIME_AGENT_ENDPOINT"
              value: ${quote(endpoint)}
            - name: "RUNTIME_AGENT_ENDPOINT_SCOPE"
              value: ${quote(properties.endpointScope)}
          readinessProbe:
            httpGet:
              path: "/actuator/health"
              port: ${properties.agentContainerPort}
            periodSeconds: 10
            timeoutSeconds: 5
            failureThreshold: 12
            initialDelaySeconds: 30
          livenessProbe:
            httpGet:
              path: "/actuator/health"
              port: ${properties.agentContainerPort}
            periodSeconds: 10
            timeoutSeconds: 5
            failureThreshold: 12
            initialDelaySeconds: 30
---
apiVersion: v1
kind: Service
metadata:
  name: ${quote(deploymentName)}
  labels:
    app.kubernetes.io/name: ${quote(deploymentName)}
    app.kubernetes.io/component: "runtime-agent-service"
    app.kubernetes.io/managed-by: "federation-learning-platform"
spec:
  type: ClusterIP
  selector:
    app: ${quote(deploymentName)}
  ports:
    - name: "http"
      port: ${properties.agentContainerPort}
      targetPort: ${properties.agentContainerPort}
""".trimStart()
}

private fun kubernetesName(value: String): String {
    val sanitized = value.lowercase(Locale.ROOT)
        .replace(Regex("[^a-z0-9-]"), "-")
        .trim('-')
        .ifBlank { "runtime-agent" }
    return sanitized.take(63).trimEnd('-')
}

private fun quote(value: String): String =
    "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"") + "\""
