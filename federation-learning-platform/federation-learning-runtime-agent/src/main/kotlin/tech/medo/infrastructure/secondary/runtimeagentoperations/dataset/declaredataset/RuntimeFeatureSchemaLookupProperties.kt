package tech.medo.infrastructure.secondary.runtimeagentoperations.dataset.declaredataset

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("runtime-agent.feature-schema-lookup")
data class RuntimeFeatureSchemaLookupProperties(
    val enabled: Boolean = true,
    val platformUrl: String = "http://localhost:8081"
)
