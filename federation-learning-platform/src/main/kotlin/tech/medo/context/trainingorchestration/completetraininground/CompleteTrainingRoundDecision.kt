package tech.medo.trainingorchestration.completetraininground

import tech.medo.trainingorchestration.completetraininground.CompleteTrainingRoundCommand

import tech.medo.trainingorchestration.events.TrainingRoundCompletedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState





interface CompleteTrainingRoundDecision {
    fun decide(command: CompleteTrainingRoundCommand, state: TrainingRoundState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            TrainingRoundCompletedEvent(trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, aggregatedModelVersionId = command.aggregatedModelVersionId, modelFormat = command.modelFormat, modelHash = command.modelHash, globalAccuracy = command.globalAccuracy)
        )
    }
}
