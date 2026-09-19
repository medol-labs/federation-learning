package tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata


import tech.medo.runtimeagentoperations.events.DatasetDeclaredEvent
import tech.medo.runtimeagentoperations.events.RuntimeDatasetBindingConfiguredEvent

import java.time.LocalDateTime
import java.time.ZoneOffset


interface RuntimeDatasetBindingCatalogReadModelProjectionUpdater {
    fun update(
        event: DatasetDeclaredEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeDatasetBindingConfiguredEvent,
        message: EventMessage
    )
}

open class DefaultRuntimeDatasetBindingCatalogReadModelProjectionUpdater(
    private val repository: RuntimeDatasetBindingCatalogReadModelRepository
) : RuntimeDatasetBindingCatalogReadModelProjectionUpdater {
    open override fun update(
        event: DatasetDeclaredEvent,
        message: EventMessage
    ) {
        // Skipped: DatasetDeclaredEvent does not provide enough key fields to locate RuntimeDatasetBindingCatalogReadModelProjection.
    }

    @Transactional
    open override fun update(
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
            entity.organizationName = event.organizationName
            entity.featureSchemaId = event.featureSchemaId
            entity.featureDomain = event.featureDomain
            entity.featureSchemaVersion = event.featureSchemaVersion
            entity.datasetName = event.datasetName
            entity.runtimeName = event.runtimeName
            entity.filePath = event.filePath
            entity.dataFormat = event.dataFormat
            entity.configuredAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    private fun eventTime(message: EventMessage): LocalDateTime =
        LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)

}

@Configuration(proxyBeanMethods = false)
class RuntimeDatasetBindingCatalogReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(RuntimeDatasetBindingCatalogReadModelProjectionUpdater::class)
    fun defaultRuntimeDatasetBindingCatalogReadModelProjectionUpdater(
        repository: RuntimeDatasetBindingCatalogReadModelRepository
    ): RuntimeDatasetBindingCatalogReadModelProjectionUpdater =
        DefaultRuntimeDatasetBindingCatalogReadModelProjectionUpdater(repository)
}

@Namespace("readmodel-runtime-dataset-binding-catalog")
@Component
class RuntimeDatasetBindingCatalogReadModelProjector(
    private val updater: RuntimeDatasetBindingCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: DatasetDeclaredEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeDatasetBindingConfiguredEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
