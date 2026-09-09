package tech.medo.trainingorchestration.canceltrainingjob

import tech.medo.trainingorchestration.canceltrainingjob.CancelTrainingJobCommand


import tech.medo.trainingorchestration.events.TrainingJobCanceledEvent
import tech.medo.trainingorchestration.trainingjob.TrainingJobState





interface CancelTrainingJobDecision {
    fun decide(command: CancelTrainingJobCommand, state: TrainingJobState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            TrainingJobCanceledEvent(trainingJobId = command.trainingJobId, cancelReason = command.cancelReason)
        )
    }
}
