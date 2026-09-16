package tech.medo.infrastructure.secondary.datasetgovernance.featureschemacatalog

import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.datasetgovernance.events.CurrentRecommendedFeatureSchemaVersionMarkedEvent
import tech.medo.datasetgovernance.events.FeatureSchemaDefinedEvent
import tech.medo.datasetgovernance.events.FeatureSchemaDeprecatedEvent
import tech.medo.datasetgovernance.events.FeatureSchemaPublishedEvent
import tech.medo.datasetgovernance.events.FeatureSchemaRetiredEvent
import tech.medo.datasetgovernance.events.FeatureSchemaVersionSupersededEvent
import tech.medo.datasetgovernance.featureschemacatalog.FeatureSchemaCatalogReadModelProjection
import tech.medo.datasetgovernance.featureschemacatalog.FeatureSchemaCatalogReadModelProjectionUpdater
import tech.medo.datasetgovernance.featureschemacatalog.FeatureSchemaCatalogReadModelRepository
import tech.medo.datasetgovernance.featureschemacatalog.toReadModel
import tech.medo.shared.application.metadata.ProjectionMetadata
import tech.medo.shared.application.sync.SyncReadModelOutboxAppender

@Component
class FeatureSchemaCatalogReadModelOutboxProjectionUpdater(
    private val repository: FeatureSchemaCatalogReadModelRepository,
    private val outbox: SyncReadModelOutboxAppender
) : FeatureSchemaCatalogReadModelProjectionUpdater {
    @Transactional
    override fun update(
        event: FeatureSchemaDefinedEvent,
        message: EventMessage
    ) {
        val entity = repository.findProjectionById(event.featureSchemaId) ?: FeatureSchemaCatalogReadModelProjection().apply {
            featureSchemaId = event.featureSchemaId
        }
        entity.featureSchemaId = event.featureSchemaId
        entity.featureDomain = event.featureDomain
        entity.version = event.version
        entity.dataModality = event.dataModality
        entity.features = event.features
        entity.labels = event.labels
        entity.featureCount = event.featureCount
        entity.supersededByFeatureSchemaId = event.featureSchemaId
        entity.schemaStatus = "Draft"
        ProjectionMetadata.assign(entity, message)
        saveAndPublish(entity, event.featureSchemaId.toString(), message)
    }

    @Transactional
    override fun update(
        event: FeatureSchemaPublishedEvent,
        message: EventMessage
    ) {
        val entity = repository.findProjectionById(event.featureSchemaId) ?: FeatureSchemaCatalogReadModelProjection().apply {
            featureSchemaId = event.featureSchemaId
        }
        entity.featureSchemaId = event.featureSchemaId
        entity.supersededByFeatureSchemaId = event.featureSchemaId
        entity.schemaStatus = "Published"
        ProjectionMetadata.assign(entity, message)
        saveAndPublish(entity, event.featureSchemaId.toString(), message)
    }

    @Transactional
    override fun update(
        event: FeatureSchemaDeprecatedEvent,
        message: EventMessage
    ) {
        val entity = repository.findProjectionById(event.featureSchemaId) ?: FeatureSchemaCatalogReadModelProjection().apply {
            featureSchemaId = event.featureSchemaId
        }
        entity.featureSchemaId = event.featureSchemaId
        entity.supersededByFeatureSchemaId = event.featureSchemaId
        entity.schemaStatus = "Deprecated"
        ProjectionMetadata.assign(entity, message)
        saveAndPublish(entity, event.featureSchemaId.toString(), message)
    }

    @Transactional
    override fun update(
        event: FeatureSchemaRetiredEvent,
        message: EventMessage
    ) {
        val entity = repository.findProjectionById(event.featureSchemaId) ?: FeatureSchemaCatalogReadModelProjection().apply {
            featureSchemaId = event.featureSchemaId
        }
        entity.featureSchemaId = event.featureSchemaId
        entity.supersededByFeatureSchemaId = event.featureSchemaId
        entity.schemaStatus = "Retired"
        ProjectionMetadata.assign(entity, message)
        saveAndPublish(entity, event.featureSchemaId.toString(), message)
    }

    @Transactional
    override fun update(
        event: FeatureSchemaVersionSupersededEvent,
        message: EventMessage
    ) {
        val entity = repository.findProjectionById(event.featureSchemaId) ?: FeatureSchemaCatalogReadModelProjection().apply {
            featureSchemaId = event.featureSchemaId
        }
        entity.featureSchemaId = event.featureSchemaId
        entity.featureDomain = event.featureDomain
        entity.supersededByFeatureSchemaId = event.supersededByFeatureSchemaId
        ProjectionMetadata.assign(entity, message)
        saveAndPublish(entity, event.featureSchemaId.toString(), message)
    }

    @Transactional
    override fun update(
        event: CurrentRecommendedFeatureSchemaVersionMarkedEvent,
        message: EventMessage
    ) {
        val entity = repository.findProjectionById(event.featureSchemaId) ?: FeatureSchemaCatalogReadModelProjection().apply {
            featureSchemaId = event.featureSchemaId
        }
        entity.featureSchemaId = event.featureSchemaId
        entity.featureDomain = event.featureDomain
        entity.supersededByFeatureSchemaId = event.featureSchemaId
        ProjectionMetadata.assign(entity, message)
        saveAndPublish(entity, event.featureSchemaId.toString(), message)
    }

    private fun saveAndPublish(
        entity: FeatureSchemaCatalogReadModelProjection,
        readModelKey: String,
        message: EventMessage
    ) {
        repository.save(entity)
        outbox.append(
            sourceContext = "DatasetGovernance",
            sourceReadModel = "FeatureSchemaCatalog",
            readModelKey = readModelKey,
            operation = "UPSERT",
            payload = entity.toReadModel(),
            message = message
        )
    }
}
