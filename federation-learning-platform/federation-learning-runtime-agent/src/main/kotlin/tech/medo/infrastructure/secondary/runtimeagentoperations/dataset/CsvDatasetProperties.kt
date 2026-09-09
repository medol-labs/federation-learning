package tech.medo.infrastructure.secondary.runtimeagentoperations.dataset

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("runtime-agent.csv-dataset")
data class CsvDatasetProperties(
    val labelColumns: List<String> = emptyList()
)
