package tech.medo.datasetgovernance.deprecatefeatureschema

import tech.medo.datasetgovernance.deprecatefeatureschema.DeprecateFeatureSchemaCommand


import tech.medo.datasetgovernance.events.FeatureSchemaDeprecatedEvent
import tech.medo.datasetgovernance.featureschema.FeatureSchemaState


import tech.medo.datasetgovernance.domain.states.FeatureSchemaStateEnum


interface DeprecateFeatureSchemaDecision {
    fun decide(command: DeprecateFeatureSchemaCommand, state: FeatureSchemaState): List<Any> {
        if (state.currentState != FeatureSchemaStateEnum.Published) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.datasetgovernance.deprecateFeatureSchema.requiresState",
                args = mapOf(
                    "command" to "DeprecateFeatureSchema",
                    "aggregate" to "FeatureSchema",
                    "expectedState" to "Published",
                    "actualState" to state.currentState.toString()
                ),
                message = "DeprecateFeatureSchema requires FeatureSchema to be Published."
            )
        }
        return listOf(
            FeatureSchemaDeprecatedEvent(featureSchemaId = command.featureSchemaId, deprecationReason = command.deprecationReason, featureDomain = command.featureDomain, version = command.version)
        )
    }
}
