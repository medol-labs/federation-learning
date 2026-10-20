package tech.medo.trainingorchestration.completemodelaggregation

import tech.medo.trainingorchestration.completemodelaggregation.CompleteModelAggregationCommand


import tech.medo.trainingorchestration.events.GlobalModelUpdatedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState


import tech.medo.trainingorchestration.domain.states.TrainingRoundStateEnum


interface CompleteModelAggregationDecision {
    fun decide(command: CompleteModelAggregationCommand, state: TrainingRoundState): List<Any> {
        if (state.currentState != TrainingRoundStateEnum.Completed) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.trainingorchestration.completeModelAggregation.requiresState",
                args = mapOf(
                    "command" to "CompleteModelAggregation",
                    "aggregate" to "TrainingRound",
                    "expectedState" to "Completed",
                    "actualState" to state.currentState.toString()
                ),
                message = "CompleteModelAggregation requires TrainingRound to be Completed."
            )
        }
        return listOf(
            GlobalModelUpdatedEvent(trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, trainingJobObjective = command.trainingJobObjective, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, maxRounds = command.maxRounds, minimumAccuracy = command.minimumAccuracy, secureAggregationSessionId = command.secureAggregationSessionId, modelPlugin = command.modelPlugin, aggregatedModelId = command.aggregatedModelId, aggregatedModelName = command.aggregatedModelName, aggregatedModelVersion = command.aggregatedModelVersion, aggregatedModelDescription = command.aggregatedModelDescription, modelSourceType = command.modelSourceType, aggregatedModelArtifactUri = command.aggregatedModelArtifactUri, aggregatedModelRegistryRef = command.aggregatedModelRegistryRef, modelFormat = command.modelFormat, modelArtifactDigest = command.modelArtifactDigest, aggregatedModelSignatureUri = command.aggregatedModelSignatureUri, aggregatedModelSizeBytes = command.aggregatedModelSizeBytes)
        )
    }
}
