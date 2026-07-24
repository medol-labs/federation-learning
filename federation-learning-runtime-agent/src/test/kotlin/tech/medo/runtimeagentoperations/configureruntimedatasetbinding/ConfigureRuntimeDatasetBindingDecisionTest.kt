package tech.medo.runtimeagentoperations.configureruntimedatasetbinding

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.configureruntimedatasetbinding.ConfigureRuntimeDatasetBindingCommand
import tech.medo.runtimeagentoperations.events.RuntimeDatasetBindingConfiguredEvent



import java.util.UUID;


class ConfigureRuntimeDatasetBindingDecisionTest {
    @Test
    fun ConfigureRuntimeDatasetBindingEmitsRuntimeDatasetBindingConfiguredEvent() {
        val events = ConfigureRuntimeDatasetBindingDecision().decide(
            ConfigureRuntimeDatasetBindingCommand(
            runtimeDatasetBindingId = java.util.UUID.randomUUID(),
            datasetId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            runtimeId = java.util.UUID.randomUUID(),
            dataSourceType = "",
            host = null,
            port = null,
            url = null,
            databaseName = null,
            schemaName = null,
            tableName = null,
            filePath = null,
            objectBucket = null,
            objectPrefix = null,
            dataFormat = "",
            credentialSecretName = null
            )
        )

        assertTrue(events.any { it is RuntimeDatasetBindingConfiguredEvent })
    }
}
