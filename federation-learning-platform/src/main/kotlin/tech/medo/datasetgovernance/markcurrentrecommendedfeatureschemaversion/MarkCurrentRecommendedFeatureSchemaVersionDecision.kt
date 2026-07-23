package tech.medo.datasetgovernance.markcurrentrecommendedfeatureschemaversion

import org.springframework.stereotype.Component
import tech.medo.datasetgovernance.markcurrentrecommendedfeatureschemaversion.MarkCurrentRecommendedFeatureSchemaVersionCommand

import tech.medo.datasetgovernance.events.CurrentRecommendedFeatureSchemaVersionMarkedEvent
import tech.medo.datasetgovernance.featureschema.FeatureSchemaState





@Component
class MarkCurrentRecommendedFeatureSchemaVersionDecision {
    fun decide(command: MarkCurrentRecommendedFeatureSchemaVersionCommand, state: FeatureSchemaState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            CurrentRecommendedFeatureSchemaVersionMarkedEvent(featureSchemaId = command.featureSchemaId, featureDomain = command.featureDomain, recommendedVersion = "" /* TODO: Expose the recommended schema version for catalog projection. */, recommendationNote = command.recommendationNote)
        )
    }
}
