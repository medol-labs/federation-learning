package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class RuntimeDatasetBindingConfiguredEvent(
    val runtimeDatasetBindingId: UUID,
    @EventTag(key = "datasetId")
    val datasetId: UUID,
    val organizationId: UUID,
    val featureSchemaId: UUID,
    val organizationName: String?,
    val featureDomain: String?,
    val featureSchemaVersion: String?,
    val datasetName: String,
    @EventTag(key = "runtimeId")
    val runtimeId: UUID,
    val runtimeName: String?,
    val dataSourceType: String,
    val host: String?,
    val port: Int?,
    val url: String?,
    val databaseName: String?,
    val schemaName: String?,
    val tableName: String?,
    val filePath: String?,
    val objectBucket: String?,
    val objectPrefix: String?,
    val dataFormat: String,
    val credentialSecretName: String?
)
