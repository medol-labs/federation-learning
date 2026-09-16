package tech.medo.datasetgovernance.currentrecommendedfeatureschemacatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.datasetgovernance.events.CurrentRecommendedFeatureSchemaVersionMarkedEvent



interface CurrentRecommendedFeatureSchemaCatalogReadModelProjectionUpdater {
    fun update(
        event: CurrentRecommendedFeatureSchemaVersionMarkedEvent,
        message: EventMessage
    )
}

@Component
@ConditionalOnMissingBean(CurrentRecommendedFeatureSchemaCatalogReadModelProjectionUpdater::class)
class DefaultCurrentRecommendedFeatureSchemaCatalogReadModelProjectionUpdater(
    private val repository: CurrentRecommendedFeatureSchemaCatalogReadModelRepository
) : CurrentRecommendedFeatureSchemaCatalogReadModelProjectionUpdater {
    @Transactional
    override fun update(
        event: CurrentRecommendedFeatureSchemaVersionMarkedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.featureDomain) ?: CurrentRecommendedFeatureSchemaCatalogReadModelProjection().apply {
                this.featureDomain = event.featureDomain
        }
            entity.featureDomain = event.featureDomain
            entity.recommendedVersion = event.recommendedVersion
            entity.recommendationNote = event.recommendationNote
            entity.recommendedFeatureSchemaId = event.featureSchemaId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

}

@Namespace("readmodel-current-recommended-feature-schema-catalog")
@Component
class CurrentRecommendedFeatureSchemaCatalogReadModelProjector(
    private val updater: CurrentRecommendedFeatureSchemaCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: CurrentRecommendedFeatureSchemaVersionMarkedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
