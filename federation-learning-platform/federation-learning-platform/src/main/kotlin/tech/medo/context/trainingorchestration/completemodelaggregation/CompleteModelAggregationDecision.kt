package tech.medo.trainingorchestration.completemodelaggregation

import tech.medo.trainingorchestration.completemodelaggregation.CompleteModelAggregationCommand


import tech.medo.trainingorchestration.events.GlobalModelUpdatedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState





interface CompleteModelAggregationDecision {
    fun decide(command: CompleteModelAggregationCommand, state: TrainingRoundState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            GlobalModelUpdatedEvent(trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, trainingJobObjective = command.trainingJobObjective, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, maxRounds = command.maxRounds, minimumAccuracy = command.minimumAccuracy, secureAggregationSessionId = command.secureAggregationSessionId, modelPlugin = command.modelPlugin, aggregatedModelId = command.aggregatedModelId, aggregatedModelName = command.aggregatedModelName, aggregatedModelVersion = command.aggregatedModelVersion, aggregatedModelDescription = command.aggregatedModelDescription, modelSourceType = command.modelSourceType, aggregatedModelArtifactUri = command.aggregatedModelArtifactUri, aggregatedModelRegistryRef = command.aggregatedModelRegistryRef, modelFormat = command.modelFormat, modelArtifactDigest = command.modelArtifactDigest, aggregatedModelSignatureUri = command.aggregatedModelSignatureUri, aggregatedModelSizeBytes = command.aggregatedModelSizeBytes)
        )
    }
}
