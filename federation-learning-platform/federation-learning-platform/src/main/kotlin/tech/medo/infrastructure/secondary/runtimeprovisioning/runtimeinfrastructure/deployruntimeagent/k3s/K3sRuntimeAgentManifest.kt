package tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.k3s

import tech.medo.runtimeprovisioning.runtimeinstallationplancatalog.RuntimeInstallationPlanCatalogReadModel
import java.nio.file.Files
import java.nio.file.Paths
import java.util.Locale
import java.util.UUID

data class K3sRuntimeAgentManifest(
    val manifestFile: String,
    val deploymentName: String,
    val dependencyDeploymentNames: List<String> = emptyList()
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
    val dependencyNames = runtimeAgentDependencyNames(deploymentName)
    val manifest = renderRuntimeAgentManifest(properties, runtimeAgentId, runtimeInfrastructureId, plan, deploymentName, dependencyNames)
    val directory = Paths.get(properties.renderedManifestDirectory)
    Files.createDirectories(directory)
    val manifestPath = directory.resolve("$deploymentName.yaml")
    Files.writeString(manifestPath, manifest)
    return K3sRuntimeAgentManifest(
        manifestFile = manifestPath.toString(),
        deploymentName = deploymentName,
        dependencyDeploymentNames = listOf(dependencyNames.postgres, dependencyNames.umadb)
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
    deploymentName: String,
    dependencyNames: RuntimeAgentDependencyNames
): String {
    val runtimeName = plan.runtimeName ?: deploymentName
    val organizationId = plan.organizationId?.toString().orEmpty()
    val endpoint = "http://$deploymentName:${properties.agentContainerPort}"
    val replicas = properties.agentReplicas.coerceAtLeast(1)
    val databaseUrl = "jdbc:postgresql://${dependencyNames.postgres}:5432/${properties.databaseName}"
    val umadbTarget = "${dependencyNames.umadb}:50051"
    return """
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: ${quote(dependencyNames.postgresData)}
  labels:
    app: ${quote(dependencyNames.postgres)}
    app.kubernetes.io/name: ${quote(dependencyNames.postgres)}
    app.kubernetes.io/component: "runtime-agent-postgres"
    app.kubernetes.io/managed-by: "federation-learning-platform"
    medol.dev/runtime-agent-id: ${quote(runtimeAgentId.toString())}
    medol.dev/runtime-infrastructure-id: ${quote(runtimeInfrastructureId.toString())}
spec:
  accessModes:
    - "ReadWriteOnce"
  resources:
    requests:
      storage: ${quote(properties.databaseStorageSize)}
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ${quote(dependencyNames.postgres)}
  labels:
    app: ${quote(dependencyNames.postgres)}
    app.kubernetes.io/name: ${quote(dependencyNames.postgres)}
    app.kubernetes.io/component: "runtime-agent-postgres"
    app.kubernetes.io/managed-by: "federation-learning-platform"
    medol.dev/runtime-agent-id: ${quote(runtimeAgentId.toString())}
    medol.dev/runtime-infrastructure-id: ${quote(runtimeInfrastructureId.toString())}
spec:
  replicas: 1
  selector:
    matchLabels:
      app: ${quote(dependencyNames.postgres)}
  template:
    metadata:
      labels:
        app: ${quote(dependencyNames.postgres)}
    spec:
      nodeSelector:
        medol.dev/node-role: "runtime"
        medol.dev/runtime-infrastructure-id: ${quote(runtimeInfrastructureId.toString())}
      tolerations:
        - key: "medol.dev/runtime-only"
          operator: "Equal"
          value: "true"
          effect: "NoSchedule"
      containers:
        - name: "postgres"
          image: ${quote(properties.databaseImage)}
          ports:
            - containerPort: 5432
          env:
            - name: "POSTGRES_USER"
              valueFrom:
                secretKeyRef:
                  name: ${quote(properties.databaseSecretName)}
                  key: "username"
            - name: "POSTGRES_PASSWORD"
              valueFrom:
                secretKeyRef:
                  name: ${quote(properties.databaseSecretName)}
                  key: "password"
            - name: "POSTGRES_DB"
              value: ${quote(properties.databaseName)}
          volumeMounts:
            - name: "postgres-data"
              mountPath: "/var/lib/postgresql/data"
      volumes:
        - name: "postgres-data"
          persistentVolumeClaim:
            claimName: ${quote(dependencyNames.postgresData)}
---
apiVersion: v1
kind: Service
metadata:
  name: ${quote(dependencyNames.postgres)}
  labels:
    app.kubernetes.io/name: ${quote(dependencyNames.postgres)}
    app.kubernetes.io/component: "runtime-agent-postgres-service"
    app.kubernetes.io/managed-by: "federation-learning-platform"
    medol.dev/runtime-agent-id: ${quote(runtimeAgentId.toString())}
    medol.dev/runtime-infrastructure-id: ${quote(runtimeInfrastructureId.toString())}
spec:
  type: ClusterIP
  selector:
    app: ${quote(dependencyNames.postgres)}
  ports:
    - name: "postgres"
      port: 5432
      targetPort: 5432
---
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: ${quote(dependencyNames.umadbData)}
  labels:
    app: ${quote(dependencyNames.umadb)}
    app.kubernetes.io/name: ${quote(dependencyNames.umadb)}
    app.kubernetes.io/component: "runtime-agent-umadb"
    app.kubernetes.io/managed-by: "federation-learning-platform"
    medol.dev/runtime-agent-id: ${quote(runtimeAgentId.toString())}
    medol.dev/runtime-infrastructure-id: ${quote(runtimeInfrastructureId.toString())}
spec:
  accessModes:
    - "ReadWriteOnce"
  resources:
    requests:
      storage: ${quote(properties.umadbStorageSize)}
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ${quote(dependencyNames.umadb)}
  labels:
    app: ${quote(dependencyNames.umadb)}
    app.kubernetes.io/name: ${quote(dependencyNames.umadb)}
    app.kubernetes.io/component: "runtime-agent-umadb"
    app.kubernetes.io/managed-by: "federation-learning-platform"
    medol.dev/runtime-agent-id: ${quote(runtimeAgentId.toString())}
    medol.dev/runtime-infrastructure-id: ${quote(runtimeInfrastructureId.toString())}
spec:
  replicas: 1
  selector:
    matchLabels:
      app: ${quote(dependencyNames.umadb)}
  template:
    metadata:
      labels:
        app: ${quote(dependencyNames.umadb)}
    spec:
      nodeSelector:
        medol.dev/node-role: "runtime"
        medol.dev/runtime-infrastructure-id: ${quote(runtimeInfrastructureId.toString())}
      tolerations:
        - key: "medol.dev/runtime-only"
          operator: "Equal"
          value: "true"
          effect: "NoSchedule"
      containers:
        - name: "umadb"
          image: ${quote(properties.umadbImage)}
          ports:
            - containerPort: 50051
          volumeMounts:
            - name: "umadb-data"
              mountPath: "/data"
      volumes:
        - name: "umadb-data"
          persistentVolumeClaim:
            claimName: ${quote(dependencyNames.umadbData)}
---
apiVersion: v1
kind: Service
metadata:
  name: ${quote(dependencyNames.umadb)}
  labels:
    app.kubernetes.io/name: ${quote(dependencyNames.umadb)}
    app.kubernetes.io/component: "runtime-agent-umadb-service"
    app.kubernetes.io/managed-by: "federation-learning-platform"
    medol.dev/runtime-agent-id: ${quote(runtimeAgentId.toString())}
    medol.dev/runtime-infrastructure-id: ${quote(runtimeInfrastructureId.toString())}
spec:
  type: ClusterIP
  selector:
    app: ${quote(dependencyNames.umadb)}
  ports:
    - name: "grpc"
      port: 50051
      targetPort: 50051
---
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
      serviceAccountName: ${quote(properties.runtimeEngineServiceAccountName)}
      nodeSelector:
        medol.dev/node-role: "runtime"
        medol.dev/runtime-infrastructure-id: ${quote(runtimeInfrastructureId.toString())}
      tolerations:
        - key: "medol.dev/runtime-only"
          operator: "Equal"
          value: "true"
          effect: "NoSchedule"
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
              value: ${quote(umadbTarget)}
            - name: "UMADB_PLAINTEXT"
              value: "true"
            - name: "UMADB_API_KEY"
              value: ""
            - name: "UMADB_BATCH_SIZE"
              value: "256"
            - name: "UMADB_REQUEST_TIMEOUT"
              value: "PT10S"
            - name: "DB_URL"
              value: ${quote(databaseUrl)}
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
            - name: "RUNTIME_AGENT_LOCAL_RUNTIME_ENGINE_MODE"
              value: "kubernetes"
            - name: "RUNTIME_AGENT_LOCAL_RUNTIME_ENGINE_KUBERNETES_NAMESPACE"
              value: ${quote(properties.namespace)}
            - name: "RUNTIME_AGENT_LOCAL_RUNTIME_ENGINE_KUBERNETES_SERVICE_ACCOUNT_NAME"
              value: ${quote(properties.runtimeEngineServiceAccountName)}
            - name: "RUNTIME_AGENT_LOCAL_RUNTIME_ENGINE_KUBERNETES_IMAGE"
              value: ${quote(properties.runtimeEngineImage)}
            - name: "RUNTIME_AGENT_LOCAL_RUNTIME_ENGINE_KUBERNETES_IMAGE_PULL_POLICY"
              value: ${quote(properties.runtimeEngineImagePullPolicy)}
            - name: "RUNTIME_AGENT_LOCAL_RUNTIME_ENGINE_KUBERNETES_DATASET_HOST_PATH"
              value: ${quote(properties.runtimeEngineDatasetHostPath)}
            - name: "RUNTIME_AGENT_LOCAL_RUNTIME_ENGINE_KUBERNETES_RUNTIME_HOST_PATH"
              value: ${quote(properties.runtimeEngineWorkHostPath)}
          volumeMounts:
            - name: "runtime-datasets"
              mountPath: "/workspace/datasets"
              readOnly: true
            - name: "runtime-work"
              mountPath: "/workspace/tmp/runtime-engine"
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
      volumes:
        - name: "runtime-datasets"
          hostPath:
            path: ${quote(properties.runtimeEngineDatasetHostPath)}
            type: "Directory"
        - name: "runtime-work"
          hostPath:
            path: ${quote(properties.runtimeEngineWorkHostPath)}
            type: "DirectoryOrCreate"
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

private data class RuntimeAgentDependencyNames(
    val postgres: String,
    val postgresData: String,
    val umadb: String,
    val umadbData: String
)

private fun runtimeAgentDependencyNames(deploymentName: String): RuntimeAgentDependencyNames =
    RuntimeAgentDependencyNames(
        postgres = relatedKubernetesName(deploymentName, "postgres"),
        postgresData = relatedKubernetesName(deploymentName, "postgres-data"),
        umadb = relatedKubernetesName(deploymentName, "umadb"),
        umadbData = relatedKubernetesName(deploymentName, "umadb-data")
    )

private fun relatedKubernetesName(base: String, suffix: String): String {
    val sanitizedBase = kubernetesName(base)
    val sanitizedSuffix = kubernetesName(suffix)
    val direct = "$sanitizedBase-$sanitizedSuffix"
    if (direct.length <= 63) return direct
    val uniqueTail = sanitizedBase.takeLast(13).trim('-')
    val prefixLimit = 63 - sanitizedSuffix.length - uniqueTail.length - 2
    val prefix = sanitizedBase.take(prefixLimit.coerceAtLeast(1)).trim('-')
    return listOf(prefix, uniqueTail, sanitizedSuffix)
        .filter { it.isNotBlank() }
        .joinToString("-")
        .take(63)
        .trimEnd('-')
}

private fun quote(value: String): String =
    "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"") + "\""
