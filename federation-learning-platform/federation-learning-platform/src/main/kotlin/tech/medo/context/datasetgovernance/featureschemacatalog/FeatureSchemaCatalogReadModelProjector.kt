package tech.medo.datasetgovernance.featureschemacatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.datasetgovernance.events.FeatureSchemaDefinedEvent
import tech.medo.datasetgovernance.events.FeatureSchemaPublishedEvent
import tech.medo.datasetgovernance.events.FeatureSchemaDeprecatedEvent
import tech.medo.datasetgovernance.events.FeatureSchemaRetiredEvent
import tech.medo.datasetgovernance.events.FeatureSchemaVersionSupersededEvent
import tech.medo.datasetgovernance.events.CurrentRecommendedFeatureSchemaVersionMarkedEvent



interface FeatureSchemaCatalogReadModelProjectionUpdater {
    fun update(
        event: FeatureSchemaDefinedEvent,
        message: EventMessage
    )

    fun update(
        event: FeatureSchemaPublishedEvent,
        message: EventMessage
    )

    fun update(
        event: FeatureSchemaDeprecatedEvent,
        message: EventMessage
    )

    fun update(
        event: FeatureSchemaRetiredEvent,
        message: EventMessage
    )

    fun update(
        event: FeatureSchemaVersionSupersededEvent,
        message: EventMessage
    )

    fun update(
        event: CurrentRecommendedFeatureSchemaVersionMarkedEvent,
        message: EventMessage
    )
}

@Component
@ConditionalOnMissingBean(FeatureSchemaCatalogReadModelProjectionUpdater::class)
class DefaultFeatureSchemaCatalogReadModelProjectionUpdater(
    private val repository: FeatureSchemaCatalogReadModelRepository
) : FeatureSchemaCatalogReadModelProjectionUpdater {
    @Transactional
    override fun update(
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
    }

    @Transactional
    override fun update(
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
    }

    @Transactional
    override fun update(
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
    }

    @Transactional
    override fun update(
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
    }

    @Transactional
    override fun update(
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

    @Transactional
    override fun update(
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
    }

}

@Namespace("readmodel-feature-schema-catalog")
@Component
class FeatureSchemaCatalogReadModelProjector(
    private val updater: FeatureSchemaCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: FeatureSchemaDefinedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: FeatureSchemaPublishedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: FeatureSchemaDeprecatedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: FeatureSchemaRetiredEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: FeatureSchemaVersionSupersededEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: CurrentRecommendedFeatureSchemaVersionMarkedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
