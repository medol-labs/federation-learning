package tech.medo.datasetgovernance.featureschemacatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata
import tech.medo.shared.application.sync.SyncReadModelOutboxAppender

import tech.medo.datasetgovernance.events.FeatureSchemaDefinedEvent
import tech.medo.datasetgovernance.events.FeatureSchemaPublishedEvent
import tech.medo.datasetgovernance.events.FeatureSchemaDeprecatedEvent
import tech.medo.datasetgovernance.events.FeatureSchemaRetiredEvent
import tech.medo.datasetgovernance.events.FeatureSchemaVersionSupersededEvent
import tech.medo.datasetgovernance.events.CurrentRecommendedFeatureSchemaVersionMarkedEvent



@Namespace("readmodel-feature-schema-catalog")
@Component
class FeatureSchemaCatalogReadModelProjector(
    private val repository: FeatureSchemaCatalogReadModelRepository,
    private val outbox: SyncReadModelOutboxAppender
) {
    @Transactional
    @EventHandler
    fun on(
        event: FeatureSchemaDefinedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.featureSchemaId) ?: FeatureSchemaCatalogReadModelProjection().apply {
                this.featureSchemaId = event.featureSchemaId
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
        repository.save(entity)
        outbox.append(
            sourceContext = "DatasetGovernance",
            sourceReadModel = "FeatureSchemaCatalog",
            readModelKey = event.featureSchemaId.toString(),
            operation = "UPSERT",
            payload = entity.toReadModel(),
            message = message
        )
    }

    @Transactional
    @EventHandler
    fun on(
        event: FeatureSchemaPublishedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.featureSchemaId) ?: FeatureSchemaCatalogReadModelProjection().apply {
                this.featureSchemaId = event.featureSchemaId
        }
            entity.featureSchemaId = event.featureSchemaId
            entity.supersededByFeatureSchemaId = event.featureSchemaId
            entity.schemaStatus = "Published"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
        outbox.append(
            sourceContext = "DatasetGovernance",
            sourceReadModel = "FeatureSchemaCatalog",
            readModelKey = event.featureSchemaId.toString(),
            operation = "UPSERT",
            payload = entity.toReadModel(),
            message = message
        )
    }

    @Transactional
    @EventHandler
    fun on(
        event: FeatureSchemaDeprecatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.featureSchemaId) ?: FeatureSchemaCatalogReadModelProjection().apply {
                this.featureSchemaId = event.featureSchemaId
        }
            entity.featureSchemaId = event.featureSchemaId
            entity.supersededByFeatureSchemaId = event.featureSchemaId
            entity.schemaStatus = "Deprecated"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
        outbox.append(
            sourceContext = "DatasetGovernance",
            sourceReadModel = "FeatureSchemaCatalog",
            readModelKey = event.featureSchemaId.toString(),
            operation = "UPSERT",
            payload = entity.toReadModel(),
            message = message
        )
    }

    @Transactional
    @EventHandler
    fun on(
        event: FeatureSchemaRetiredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.featureSchemaId) ?: FeatureSchemaCatalogReadModelProjection().apply {
                this.featureSchemaId = event.featureSchemaId
        }
            entity.featureSchemaId = event.featureSchemaId
            entity.supersededByFeatureSchemaId = event.featureSchemaId
            entity.schemaStatus = "Retired"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
        outbox.append(
            sourceContext = "DatasetGovernance",
            sourceReadModel = "FeatureSchemaCatalog",
            readModelKey = event.featureSchemaId.toString(),
            operation = "UPSERT",
            payload = entity.toReadModel(),
            message = message
        )
    }

    @Transactional
    @EventHandler
    fun on(
        event: FeatureSchemaVersionSupersededEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.featureSchemaId) ?: FeatureSchemaCatalogReadModelProjection().apply {
                this.featureSchemaId = event.featureSchemaId
        }
            entity.featureSchemaId = event.featureSchemaId
            entity.featureDomain = event.featureDomain
            entity.supersededByFeatureSchemaId = event.supersededByFeatureSchemaId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
        outbox.append(
            sourceContext = "DatasetGovernance",
            sourceReadModel = "FeatureSchemaCatalog",
            readModelKey = event.featureSchemaId.toString(),
            operation = "UPSERT",
            payload = entity.toReadModel(),
            message = message
        )
    }

    @Transactional
    @EventHandler
    fun on(
        event: CurrentRecommendedFeatureSchemaVersionMarkedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.featureSchemaId) ?: FeatureSchemaCatalogReadModelProjection().apply {
                this.featureSchemaId = event.featureSchemaId
        }
            entity.featureSchemaId = event.featureSchemaId
            entity.featureDomain = event.featureDomain
            entity.supersededByFeatureSchemaId = event.featureSchemaId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
        outbox.append(
            sourceContext = "DatasetGovernance",
            sourceReadModel = "FeatureSchemaCatalog",
            readModelKey = event.featureSchemaId.toString(),
            operation = "UPSERT",
            payload = entity.toReadModel(),
            message = message
        )
    }

}
