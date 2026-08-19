package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties("runtime-agent.local-runtime-engine")
data class LocalRuntimeEngineProperties(
    val enabled: Boolean = true,
    val composeFile: String = "../../federation-learning-runtime-engine/docker-compose.yml",
    val projectName: String = "runtime-engine",
    val serviceName: String = "runtime-engine",
    val endpoint: String = "http://localhost:18080",
    val nodeName: String = "local-runtime",
    val runtimeRoot: String = "/workspace/tmp/runtime-engine",
    val runtimeRootHostRoot: String = "../../volumes/tmp/runtime-engine",
    val datasetHostRoot: String = "../../volumes/datasets",
    val datasetContainerRoot: String = "/workspace/datasets",
    val model: String = "linear.LogisticRegression",
    val epoch: Int = 1,
    val learningRate: Double = 0.1,
    val commandTimeout: Duration = Duration.ofMinutes(2),
    val healthTimeout: Duration = Duration.ofSeconds(30),
    val healthPollInterval: Duration = Duration.ofSeconds(1),
    val jobObservationTimeout: Duration = Duration.ofMinutes(5),
    val jobObservationPollInterval: Duration = Duration.ofSeconds(2)
)
