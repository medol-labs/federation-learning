package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties("runtime-agent.local-runtime-engine")
data class LocalRuntimeEngineProperties(
    val enabled: Boolean = true,
    val composeFile: String = "../GemiFL/docker-compose.yml",
    val projectName: String = "gemifl-runtime",
    val serviceName: String = "alice-runtime",
    val endpoint: String = "http://localhost:8081",
    val nodeName: String = "Alice",
    val runtimeRoot: String = "/workspace/tmp/gemifl",
    val datasetHostRoot: String = "../volumes/datasets",
    val datasetContainerRoot: String = "/workspace/datasets",
    val model: String = "linear.LogisticRegression",
    val epoch: Int = 1,
    val learningRate: Double = 0.1,
    val commandTimeout: Duration = Duration.ofMinutes(2),
    val healthTimeout: Duration = Duration.ofSeconds(30),
    val healthPollInterval: Duration = Duration.ofSeconds(1)
)
