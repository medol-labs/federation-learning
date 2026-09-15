package tech.medo.shared.application.sync

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("medol.sync")
data class SyncReadModelProperties(
    var enabled: Boolean = true,
    var mode: String = "outbox-delta",
    var sourceBaseUrl: String = "",
    var pageSize: Int = 200,
    var fixedDelayMs: Long = 30000,
    var parameters: Map<String, String> = emptyMap()
)
