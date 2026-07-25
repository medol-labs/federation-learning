package tech.medo.trainingorchestration.pausetrainingjob

import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.pausetrainingjob.PauseTrainingJobCommand

import tech.medo.trainingorchestration.events.TrainingJobPausedEvent
import tech.medo.trainingorchestration.trainingjob.TrainingJobState





@Component
class PauseTrainingJobDecision {
    fun decide(command: PauseTrainingJobCommand, state: TrainingJobState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            TrainingJobPausedEvent(trainingJobId = command.trainingJobId, pauseReason = command.pauseReason)
        )
    }
}
