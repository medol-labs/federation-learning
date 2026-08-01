package tech.medo.trainingorchestration.completetrainingjob

import tech.medo.trainingorchestration.completetrainingjob.CompleteTrainingJobCommand

import tech.medo.trainingorchestration.events.TrainingJobCompletedEvent
import tech.medo.trainingorchestration.trainingjob.TrainingJobState





interface CompleteTrainingJobDecision {
    fun decide(command: CompleteTrainingJobCommand, state: TrainingJobState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            TrainingJobCompletedEvent(trainingJobId = command.trainingJobId, finalRoundId = command.finalRoundId, finalModelVersionId = command.finalModelVersionId, stopReason = command.stopReason)
        )
    }
}
