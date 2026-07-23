package tech.medo.trainingorchestration.resumetrainingjob

import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.resumetrainingjob.ResumeTrainingJobCommand

import tech.medo.trainingorchestration.events.TrainingJobResumedEvent
import tech.medo.trainingorchestration.trainingjob.TrainingJobState


import tech.medo.trainingorchestration.domain.states.TrainingJobStateEnum


@Component
class ResumeTrainingJobDecision {
    fun decide(command: ResumeTrainingJobCommand, state: TrainingJobState): List<Any> {
        require(state.currentState == TrainingJobStateEnum.PAUSED) {
            "ResumeTrainingJob requires TrainingJob to be Paused."
        }
        return listOf(
            TrainingJobResumedEvent(trainingJobId = command.trainingJobId, resumeReason = command.resumeReason)
        )
    }
}
