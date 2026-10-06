package tech.medo.datasetgovernance.retirefeatureschema

import tech.medo.datasetgovernance.retirefeatureschema.RetireFeatureSchemaCommand


import tech.medo.datasetgovernance.events.FeatureSchemaRetiredEvent
import tech.medo.datasetgovernance.featureschema.FeatureSchemaState


import tech.medo.datasetgovernance.domain.states.FeatureSchemaStateEnum


interface RetireFeatureSchemaDecision {
    fun decide(command: RetireFeatureSchemaCommand, state: FeatureSchemaState): List<Any> {
        if (state.currentState != FeatureSchemaStateEnum.Deprecated) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.datasetgovernance.retireFeatureSchema.requiresState",
                args = mapOf(
                    "command" to "RetireFeatureSchema",
                    "aggregate" to "FeatureSchema",
                    "expectedState" to "Deprecated",
                    "actualState" to state.currentState.toString()
                ),
                message = "RetireFeatureSchema requires FeatureSchema to be Deprecated."
            )
        }
        return listOf(
            FeatureSchemaRetiredEvent(featureSchemaId = command.featureSchemaId, retirementReason = command.retirementReason, featureDomain = command.featureDomain, version = command.version)
        )
    }
}
