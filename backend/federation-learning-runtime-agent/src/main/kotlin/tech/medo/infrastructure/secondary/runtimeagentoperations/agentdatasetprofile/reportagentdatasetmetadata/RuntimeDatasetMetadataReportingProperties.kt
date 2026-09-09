package tech.medo.infrastructure.secondary.runtimeagentoperations.agentdatasetprofile.reportagentdatasetmetadata

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("runtime-agent.dataset-metadata-reporting")
data class RuntimeDatasetMetadataReportingProperties(
    val enabled: Boolean = true,
    val platformUrl: String = "http://localhost:8080"
)
