package tech.medo.trainingorchestration.completetraininground

import tech.medo.trainingorchestration.completetraininground.CompleteTrainingRoundCommand

import tech.medo.trainingorchestration.events.TrainingRoundCompletedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState





interface CompleteTrainingRoundDecision {
    fun decide(command: CompleteTrainingRoundCommand, state: TrainingRoundState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            TrainingRoundCompletedEvent(trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, maxRounds = command.maxRounds, minimumAccuracy = command.minimumAccuracy, aggregatedModelId = command.aggregatedModelId, aggregatedModelArtifactUri = command.aggregatedModelArtifactUri, aggregatedModelRegistryRef = command.aggregatedModelRegistryRef, modelFormat = command.modelFormat, modelArtifactDigest = command.modelArtifactDigest, aggregatedModelSignatureUri = command.aggregatedModelSignatureUri, globalAccuracy = command.globalAccuracy)
        )
    }
}
