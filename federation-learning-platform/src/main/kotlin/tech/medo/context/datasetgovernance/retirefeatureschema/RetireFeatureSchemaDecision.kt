package tech.medo.datasetgovernance.retirefeatureschema

import tech.medo.datasetgovernance.retirefeatureschema.RetireFeatureSchemaCommand

import tech.medo.datasetgovernance.events.FeatureSchemaRetiredEvent
import tech.medo.datasetgovernance.featureschema.FeatureSchemaState


import tech.medo.datasetgovernance.domain.states.FeatureSchemaStateEnum


interface RetireFeatureSchemaDecision {
    fun decide(command: RetireFeatureSchemaCommand, state: FeatureSchemaState): List<Any> {
        require(state.currentState == FeatureSchemaStateEnum.DEPRECATED) {
            "RetireFeatureSchema requires FeatureSchema to be Deprecated."
        }
        return listOf(
            FeatureSchemaRetiredEvent(featureSchemaId = command.featureSchemaId, retirementReason = command.retirementReason, featureDomain = command.featureDomain, version = command.version)
        )
    }
}
