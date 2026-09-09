package tech.medo.datasetgovernance.deprecatefeatureschema

import tech.medo.datasetgovernance.deprecatefeatureschema.DeprecateFeatureSchemaCommand


import tech.medo.datasetgovernance.events.FeatureSchemaDeprecatedEvent
import tech.medo.datasetgovernance.featureschema.FeatureSchemaState


import tech.medo.datasetgovernance.domain.states.FeatureSchemaStateEnum


interface DeprecateFeatureSchemaDecision {
    fun decide(command: DeprecateFeatureSchemaCommand, state: FeatureSchemaState): List<Any> {
        require(state.currentState == FeatureSchemaStateEnum.PUBLISHED) {
            "DeprecateFeatureSchema requires FeatureSchema to be Published."
        }
        return listOf(
            FeatureSchemaDeprecatedEvent(featureSchemaId = command.featureSchemaId, deprecationReason = command.deprecationReason, featureDomain = command.featureDomain, version = command.version)
        )
    }
}
