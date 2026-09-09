package tech.medo.datasetgovernance.currentrecommendedfeatureschemacatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.datasetgovernance.events.CurrentRecommendedFeatureSchemaVersionMarkedEvent



@Component
class CurrentRecommendedFeatureSchemaCatalogReadModelProjector(private val repository: CurrentRecommendedFeatureSchemaCatalogReadModelRepository) {
    @EventHandler
    fun on(
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
