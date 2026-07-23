package tech.medo.trainingorchestration.submittrainingjob

import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.submittrainingjob.SubmitTrainingJobCommand

import tech.medo.trainingorchestration.events.TrainingJobSubmittedEvent
import tech.medo.trainingorchestration.trainingjob.TrainingJobState


import tech.medo.trainingorchestration.domain.states.TrainingJobStateEnum


@Component
class SubmitTrainingJobDecision {
    fun decide(command: SubmitTrainingJobCommand, state: TrainingJobState): List<Any> {
        require(state.currentState == TrainingJobStateEnum.DRAFT) {
            "SubmitTrainingJob requires TrainingJob to be Draft."
        }
        return listOf(
            TrainingJobSubmittedEvent(trainingJobId = command.trainingJobId)
        )
    }
}
