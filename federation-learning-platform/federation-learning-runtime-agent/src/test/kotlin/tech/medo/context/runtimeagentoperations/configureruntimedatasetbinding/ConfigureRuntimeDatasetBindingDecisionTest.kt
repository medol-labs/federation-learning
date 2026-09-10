package tech.medo.runtimeagentoperations.configureruntimedatasetbinding

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.configureruntimedatasetbinding.ConfigureRuntimeDatasetBindingCommand
import tech.medo.runtimeagentoperations.runtimedatasetbinding.RuntimeDatasetBindingRuntimeIdDatasetIdReservationState
import tech.medo.runtimeagentoperations.events.RuntimeDatasetBindingRuntimeIdDatasetIdReservedEvent
import java.util.UUID

class ConfigureRuntimeDatasetBindingDecisionTest {
    @Test
    fun RejectDuplicateRuntimeDatasetBinding() {
        val runtimeDatasetBindingRuntimeIdDatasetIdReservation = RuntimeDatasetBindingRuntimeIdDatasetIdReservationState()
        runtimeDatasetBindingRuntimeIdDatasetIdReservation.evolve(
            RuntimeDatasetBindingRuntimeIdDatasetIdReservedEvent(
                runtimeDatasetBindingId = java.util.UUID.randomUUID(),
                runtimeId = UUID.nameUUIDFromBytes("runtime-1".toByteArray()),
                datasetId = UUID.nameUUIDFromBytes("dataset-1".toByteArray()),
                normalizedRuntimeId = UUID.nameUUIDFromBytes("runtime-1".toByteArray()).toString().trim().lowercase(),
                normalizedDatasetId = UUID.nameUUIDFromBytes("dataset-1".toByteArray()).toString().trim().lowercase()
            )
        )

        assertThrows<IllegalArgumentException> {
            (object : ConfigureRuntimeDatasetBindingDecision {}).decide(
                        ConfigureRuntimeDatasetBindingCommand(
                        runtimeDatasetBindingId = java.util.UUID.randomUUID(),
                        datasetId = UUID.nameUUIDFromBytes("dataset-1".toByteArray()),
                        organizationId = java.util.UUID.randomUUID(),
                        featureSchemaId = java.util.UUID.randomUUID(),
                        organizationName = null,
                        featureDomain = null,
                        featureSchemaVersion = null,
                        datasetName = "",
                        runtimeId = UUID.nameUUIDFromBytes("runtime-1".toByteArray()),
                        runtimeName = null,
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
                        ),
                            runtimeDatasetBindingRuntimeIdDatasetIdReservation = runtimeDatasetBindingRuntimeIdDatasetIdReservation
                    )
        }
    }
}
