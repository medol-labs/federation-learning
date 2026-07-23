package tech.medo.datasetgovernance.featureschemacatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.datasetgovernance.events.FeatureSchemaDefinedEvent
import tech.medo.datasetgovernance.events.FeatureSchemaPublishedEvent
import tech.medo.datasetgovernance.events.FeatureSchemaDeprecatedEvent
import tech.medo.datasetgovernance.events.FeatureSchemaRetiredEvent
import tech.medo.datasetgovernance.events.FeatureSchemaVersionSupersededEvent
import tech.medo.datasetgovernance.events.CurrentRecommendedFeatureSchemaVersionMarkedEvent


@Component
class FeatureSchemaCatalogReadModelProjector(private val repository: FeatureSchemaCatalogReadModelRepository) {
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
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: FeatureSchemaPublishedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.featureSchemaId) ?: FeatureSchemaCatalogReadModelProjection().apply {
                this.featureSchemaId = event.featureSchemaId
        }
            entity.featureSchemaId = event.featureSchemaId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: FeatureSchemaDeprecatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.featureSchemaId) ?: FeatureSchemaCatalogReadModelProjection().apply {
                this.featureSchemaId = event.featureSchemaId
        }
            entity.featureSchemaId = event.featureSchemaId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: FeatureSchemaRetiredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.featureSchemaId) ?: FeatureSchemaCatalogReadModelProjection().apply {
                this.featureSchemaId = event.featureSchemaId
        }
            entity.featureSchemaId = event.featureSchemaId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

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
    }

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
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }
}
