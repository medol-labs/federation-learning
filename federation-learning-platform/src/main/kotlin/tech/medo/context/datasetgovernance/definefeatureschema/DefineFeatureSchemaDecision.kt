package tech.medo.datasetgovernance.definefeatureschema

import tech.medo.datasetgovernance.definefeatureschema.DefineFeatureSchemaCommand

import tech.medo.datasetgovernance.events.FeatureSchemaDefinedEvent
import tech.medo.datasetgovernance.events.FeatureSchemaFeatureDomainVersionReservedEvent
import tech.medo.datasetgovernance.featureschema.FeatureSchemaState

import tech.medo.datasetgovernance.featureschema.FeatureSchemaFeatureDomainVersionReservationState



interface DefineFeatureSchemaDecision {
    fun decide(command: DefineFeatureSchemaCommand, featureSchemaFeatureDomainVersionReservation: FeatureSchemaFeatureDomainVersionReservationState): List<Any> {
        require(!featureSchemaFeatureDomainVersionReservation.reserved) {
            "FeatureDomain Version already exists."
        }
        return listOf(
            FeatureSchemaFeatureDomainVersionReservedEvent(featureSchemaId = command.featureSchemaId, featureDomain = command.featureDomain, version = command.version, normalizedFeatureDomain = command.featureDomain.trim().lowercase(), normalizedVersion = command.version.trim().lowercase()),
            FeatureSchemaDefinedEvent(featureSchemaId = command.featureSchemaId, featureDomain = command.featureDomain, version = command.version, dataModality = command.dataModality, features = command.features, labels = command.labels, featureCount = 0 /* TODO: Count entries in the features collection. */)
        )
    }
}
