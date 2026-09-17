package tech.medo.runtimeagentoperations.agentdatasetaccessvalidationcatalog

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
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessValidatedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessValidationFailedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessRevalidatedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessRevalidationFailedEvent



interface AgentDatasetAccessValidationCatalogReadModelProjectionUpdater {
    fun update(
        event: DatasetDeclaredEvent,
        message: EventMessage
    )

    fun update(
        event: AgentDatasetAccessValidatedEvent,
        message: EventMessage
    )

    fun update(
        event: AgentDatasetAccessValidationFailedEvent,
        message: EventMessage
    )

    fun update(
        event: AgentDatasetAccessRevalidatedEvent,
        message: EventMessage
    )

    fun update(
        event: AgentDatasetAccessRevalidationFailedEvent,
        message: EventMessage
    )
}

open class DefaultAgentDatasetAccessValidationCatalogReadModelProjectionUpdater(
    private val repository: AgentDatasetAccessValidationCatalogReadModelRepository
) : AgentDatasetAccessValidationCatalogReadModelProjectionUpdater {
    open override fun update(
        event: DatasetDeclaredEvent,
        message: EventMessage
    ) {
        // Skipped: DatasetDeclaredEvent does not provide enough key fields to locate AgentDatasetAccessValidationCatalogReadModelProjection.
    }

    @Transactional
    open override fun update(
        event: AgentDatasetAccessValidatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetAccessValidationId) ?: AgentDatasetAccessValidationCatalogReadModelProjection().apply {
                this.datasetAccessValidationId = event.datasetAccessValidationId
        }
            entity.datasetAccessValidationId = event.datasetAccessValidationId
            entity.runtimeDatasetBindingId = event.runtimeDatasetBindingId
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.organizationName = event.organizationName
            entity.featureSchemaId = event.featureSchemaId
            entity.featureDomain = event.featureDomain
            entity.featureSchemaVersion = event.featureSchemaVersion
            entity.runtimeId = event.runtimeId
            entity.datasetName = event.datasetName
            entity.runtimeName = event.runtimeName
            entity.readable = event.readable
            entity.schemaReadable = event.schemaReadable
            entity.sampleBatchReadable = event.sampleBatchReadable
            entity.validationStatus = "Checked"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: AgentDatasetAccessValidationFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetAccessValidationId) ?: AgentDatasetAccessValidationCatalogReadModelProjection().apply {
                this.datasetAccessValidationId = event.datasetAccessValidationId
        }
            entity.datasetAccessValidationId = event.datasetAccessValidationId
            entity.runtimeDatasetBindingId = event.runtimeDatasetBindingId
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.organizationName = event.organizationName
            entity.featureSchemaId = event.featureSchemaId
            entity.featureDomain = event.featureDomain
            entity.featureSchemaVersion = event.featureSchemaVersion
            entity.runtimeId = event.runtimeId
            entity.datasetName = event.datasetName
            entity.runtimeName = event.runtimeName
            entity.failureReason = event.failureReason
            entity.validationStatus = "Failed"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: AgentDatasetAccessRevalidatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetAccessValidationId) ?: AgentDatasetAccessValidationCatalogReadModelProjection().apply {
                this.datasetAccessValidationId = event.datasetAccessValidationId
        }
            entity.datasetAccessValidationId = event.datasetAccessValidationId
            entity.runtimeDatasetBindingId = event.runtimeDatasetBindingId
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.organizationName = event.organizationName
            entity.featureSchemaId = event.featureSchemaId
            entity.featureDomain = event.featureDomain
            entity.featureSchemaVersion = event.featureSchemaVersion
            entity.runtimeId = event.runtimeId
            entity.datasetName = event.datasetName
            entity.runtimeName = event.runtimeName
            entity.readable = event.readable
            entity.schemaReadable = event.schemaReadable
            entity.sampleBatchReadable = event.sampleBatchReadable
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: AgentDatasetAccessRevalidationFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetAccessValidationId) ?: AgentDatasetAccessValidationCatalogReadModelProjection().apply {
                this.datasetAccessValidationId = event.datasetAccessValidationId
        }
            entity.datasetAccessValidationId = event.datasetAccessValidationId
            entity.runtimeDatasetBindingId = event.runtimeDatasetBindingId
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.organizationName = event.organizationName
            entity.featureSchemaId = event.featureSchemaId
            entity.featureDomain = event.featureDomain
            entity.featureSchemaVersion = event.featureSchemaVersion
            entity.runtimeId = event.runtimeId
            entity.datasetName = event.datasetName
            entity.runtimeName = event.runtimeName
            entity.failureReason = event.failureReason
            entity.validationStatus = "Failed"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

}

@Configuration(proxyBeanMethods = false)
class AgentDatasetAccessValidationCatalogReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(AgentDatasetAccessValidationCatalogReadModelProjectionUpdater::class)
    fun defaultAgentDatasetAccessValidationCatalogReadModelProjectionUpdater(
        repository: AgentDatasetAccessValidationCatalogReadModelRepository
    ): AgentDatasetAccessValidationCatalogReadModelProjectionUpdater =
        DefaultAgentDatasetAccessValidationCatalogReadModelProjectionUpdater(repository)
}

@Namespace("readmodel-agent-dataset-access-validation-catalog")
@Component
class AgentDatasetAccessValidationCatalogReadModelProjector(
    private val updater: AgentDatasetAccessValidationCatalogReadModelProjectionUpdater
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
        event: AgentDatasetAccessValidatedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: AgentDatasetAccessValidationFailedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: AgentDatasetAccessRevalidatedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: AgentDatasetAccessRevalidationFailedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
