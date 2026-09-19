package tech.medo.shared.application.sync

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SyncReadModelPropertiesTest {
    @Test
    fun `source base url can be overridden by source context`() {
        val properties = SyncReadModelProperties(
            sourceBaseUrl = "http://platform",
            sourceBaseUrls = mapOf("dictionary-maintenance" to "http://support")
        )

        val target = target(sourceContext = "DictionaryMaintenance")

        assertThat(properties.sourceBaseUrlFor(target)).isEqualTo("http://support")
    }

    @Test
    fun `source base url falls back to default`() {
        val properties = SyncReadModelProperties(sourceBaseUrl = "http://platform")

        val target = target(sourceContext = "DatasetGovernance")

        assertThat(properties.sourceBaseUrlFor(target)).isEqualTo("http://platform")
    }

    private fun target(sourceContext: String): SyncReadModelTarget =
        SyncReadModelTarget(
            name = "Target",
            source = "$sourceContext.SourceReadModel",
            sourceContext = sourceContext,
            sourceReadModel = "SourceReadModel",
            sourcePath = "/sync/read-models/source-context/source-read-model",
            fieldMappings = emptyMap(),
            upsert = { _, _ -> }
        )
}
