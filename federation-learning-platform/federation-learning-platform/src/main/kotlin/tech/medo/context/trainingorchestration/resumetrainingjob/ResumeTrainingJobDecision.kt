package tech.medo.trainingorchestration.resumetrainingjob

import tech.medo.trainingorchestration.resumetrainingjob.ResumeTrainingJobCommand


import tech.medo.trainingorchestration.events.TrainingJobResumedEvent
import tech.medo.trainingorchestration.trainingjob.TrainingJobState


import tech.medo.trainingorchestration.domain.states.TrainingJobStateEnum


interface ResumeTrainingJobDecision {
    fun decide(command: ResumeTrainingJobCommand, state: TrainingJobState): List<Any> {
        if (state.currentState != TrainingJobStateEnum.Paused) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.trainingorchestration.resumeTrainingJob.requiresState",
                args = mapOf(
                    "command" to "ResumeTrainingJob",
                    "aggregate" to "TrainingJob",
                    "expectedState" to "Paused",
                    "actualState" to state.currentState.toString()
                ),
                message = "ResumeTrainingJob requires TrainingJob to be Paused."
            )
        }
        return listOf(
            TrainingJobResumedEvent(trainingJobId = command.trainingJobId, resumeReason = command.resumeReason)
        )
    }
}
