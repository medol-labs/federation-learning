package tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.runtimeagentoperations.events.DatasetDeclaredEvent
import tech.medo.runtimeagentoperations.events.RuntimeDatasetBindingConfiguredEvent

import java.time.LocalDateTime
import java.time.ZoneOffset


@Component
class RuntimeDatasetBindingCatalogReadModelProjector(private val repository: RuntimeDatasetBindingCatalogReadModelRepository) {
    @EventHandler
    fun on(event: DatasetDeclaredEvent) {
        // Skipped: DatasetDeclaredEvent does not provide enough key fields to locate RuntimeDatasetBindingCatalogReadModelProjection.
    }

    @EventHandler
    fun on(
        event: RuntimeDatasetBindingConfiguredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeDatasetBindingId) ?: RuntimeDatasetBindingCatalogReadModelProjection().apply {
                this.runtimeDatasetBindingId = event.runtimeDatasetBindingId
        }
            entity.runtimeDatasetBindingId = event.runtimeDatasetBindingId
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.dataSourceType = event.dataSourceType
            entity.host = event.host
            entity.port = event.port
            entity.url = event.url
            entity.databaseName = event.databaseName
            entity.schemaName = event.schemaName
            entity.tableName = event.tableName
            entity.filePath = event.filePath
            entity.objectBucket = event.objectBucket
            entity.objectPrefix = event.objectPrefix
            entity.dataFormat = event.dataFormat
            entity.credentialSecretName = event.credentialSecretName
            entity.configuredAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    private fun eventTime(message: EventMessage): LocalDateTime =
        LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)

}
