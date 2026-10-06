package tech.medo.datasetgovernance.publishfeatureschema

import tech.medo.datasetgovernance.publishfeatureschema.PublishFeatureSchemaCommand


import tech.medo.datasetgovernance.events.FeatureSchemaPublishedEvent
import tech.medo.datasetgovernance.featureschema.FeatureSchemaState


import tech.medo.datasetgovernance.domain.states.FeatureSchemaStateEnum


interface PublishFeatureSchemaDecision {
    fun decide(command: PublishFeatureSchemaCommand, state: FeatureSchemaState): List<Any> {
        if (state.currentState != FeatureSchemaStateEnum.Draft) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.datasetgovernance.publishFeatureSchema.requiresState",
                args = mapOf(
                    "command" to "PublishFeatureSchema",
                    "aggregate" to "FeatureSchema",
                    "expectedState" to "Draft",
                    "actualState" to state.currentState.toString()
                ),
                message = "PublishFeatureSchema requires FeatureSchema to be Draft."
            )
        }
        return listOf(
            FeatureSchemaPublishedEvent(featureSchemaId = command.featureSchemaId, publishNote = command.publishNote, featureDomain = command.featureDomain, version = command.version)
        )
    }
}
