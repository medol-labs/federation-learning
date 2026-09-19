package tech.medo.shared.application.sync

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("medol.sync")
data class SyncReadModelProperties(
    var enabled: Boolean = true,
    var mode: String = "outbox-delta",
    var sourceBaseUrl: String = "",
    var sourceBaseUrls: Map<String, String> = emptyMap(),
    var pageSize: Int = 200,
    var fixedDelayMs: Long = 30000,
    var parameters: Map<String, String> = emptyMap()
) {
    fun sourceBaseUrlFor(target: SyncReadModelTarget): String =
        sourceBaseUrls.firstMatching(target.sourceContext)
            ?: sourceBaseUrls.firstMatching(target.source)
            ?: sourceBaseUrl

    private fun Map<String, String>.firstMatching(name: String): String? =
        entries.firstOrNull { (key, value) ->
            relaxedKey(key) == relaxedKey(name) && value.isNotBlank()
        }?.value

    private fun relaxedKey(value: String): String =
        value.filter { it.isLetterOrDigit() }.lowercase()
}
