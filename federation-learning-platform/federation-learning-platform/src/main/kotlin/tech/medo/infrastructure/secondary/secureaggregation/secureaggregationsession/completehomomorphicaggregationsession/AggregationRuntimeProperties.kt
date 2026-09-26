package tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties("federation-learning.aggregation")
data class AggregationRuntimeProperties(
    val enabled: Boolean = true,
    val mode: String = "embedded",
    val runtimeEngineEndpoint: String = "http://localhost:18080",
    val supportEndpoint: String = "http://localhost:8080",
    val internalToken: String = "local-dev-internal-token",
    val nodeName: String = "platform-aggregator",
    val aggregationAlgorithm: String = "FED_AVG",
    val pollInterval: Duration = Duration.ofSeconds(1),
    val jobTimeout: Duration = Duration.ofMinutes(5)
)
