package tech.medo.datasetgovernance.markcurrentrecommendedfeatureschemaversion

import tech.medo.datasetgovernance.markcurrentrecommendedfeatureschemaversion.MarkCurrentRecommendedFeatureSchemaVersionCommand

import tech.medo.datasetgovernance.events.CurrentRecommendedFeatureSchemaVersionMarkedEvent
import tech.medo.datasetgovernance.featureschema.FeatureSchemaState





interface MarkCurrentRecommendedFeatureSchemaVersionDecision {
    fun decide(command: MarkCurrentRecommendedFeatureSchemaVersionCommand, state: FeatureSchemaState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            CurrentRecommendedFeatureSchemaVersionMarkedEvent(featureSchemaId = command.featureSchemaId, featureDomain = command.featureDomain, recommendedVersion = command.version, recommendationNote = command.recommendationNote, version = command.version)
        )
    }
}
