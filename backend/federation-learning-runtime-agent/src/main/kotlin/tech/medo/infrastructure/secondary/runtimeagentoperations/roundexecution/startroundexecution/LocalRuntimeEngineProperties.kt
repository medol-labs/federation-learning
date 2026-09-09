package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties("runtime-agent.local-runtime-engine")
data class LocalRuntimeEngineProperties(
    val enabled: Boolean = true,
    val mode: String = "docker-compose",
    val composeFile: String = "../../federation-learning-runtime-engine/docker-compose.yml",
    val projectName: String = "federation-learning-runtime-engine",
    val serviceName: String = "federation-learning-runtime-engine",
    val endpoint: String = "http://localhost:18080",
    val nodeName: String = "local-runtime",
    val runtimeRoot: String = "/workspace/tmp/runtime-engine",
    val runtimeRootHostRoot: String = "../../volumes/tmp/runtime-engine",
    val datasetHostRoot: String = "../../volumes/datasets",
    val datasetContainerRoot: String = "/workspace/datasets",
    val labelColumn: String = "readmission_risk",
    val idColumn: String = "id",
    val epoch: Int = 1,
    val learningRate: Double = 0.1,
    val commandTimeout: Duration = Duration.ofMinutes(2),
    val healthTimeout: Duration = Duration.ofSeconds(30),
    val healthPollInterval: Duration = Duration.ofSeconds(1),
    val jobObservationTimeout: Duration = Duration.ofMinutes(5),
    val jobObservationPollInterval: Duration = Duration.ofSeconds(2),
    val kubernetesNamespace: String = "default",
    val kubernetesServiceAccountName: String = "federation-learning-runtime-engine-scheduler",
    val kubernetesImage: String = "medol/federation-learning-runtime-engine:0.0.1-SNAPSHOT",
    val kubernetesImagePullPolicy: String = "IfNotPresent",
    val kubernetesContainerPort: Int = 18080,
    val kubernetesRuntimeInfrastructureId: String = "",
    val kubernetesDatasetHostPath: String = "/workspace/datasets",
    val kubernetesRuntimeHostPath: String = "/workspace/tmp/runtime-engine"
) {
    fun usesDockerCompose(): Boolean = mode.equals("docker-compose", ignoreCase = true)

    fun usesKubernetes(): Boolean = mode.equals("kubernetes", ignoreCase = true) || mode.equals("k3s", ignoreCase = true)

    fun endpointFor(jobId: String): String = if (usesKubernetes()) {
        "http://${kubernetesResourceName(jobId)}.$kubernetesNamespace.svc.cluster.local:$kubernetesContainerPort"
    } else {
        endpoint.trim().removeSuffix("/")
    }

    fun kubernetesResourceName(jobId: String): String {
        val safe = jobId.lowercase().replace(Regex("[^a-z0-9-]"), "-").trim('-').take(38).trimEnd('-')
        val suffix = jobId.hashCode().toUInt().toString(16)
        return "runtime-engine-${safe.ifBlank { "job" }}-$suffix".take(63).trimEnd('-')
    }
}
