package tech.medo.trainingorchestration.failtraininground

import tech.medo.trainingorchestration.failtraininground.FailTrainingRoundCommand

import tech.medo.trainingorchestration.events.TrainingRoundFailedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState





interface FailTrainingRoundDecision {
    fun decide(command: FailTrainingRoundCommand, state: TrainingRoundState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            TrainingRoundFailedEvent(trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, selectedRuntimeCount = command.selectedRuntimeCount, minimumNodesPerRound = command.minimumNodesPerRound, failureReason = command.failureReason)
        )
    }
}
