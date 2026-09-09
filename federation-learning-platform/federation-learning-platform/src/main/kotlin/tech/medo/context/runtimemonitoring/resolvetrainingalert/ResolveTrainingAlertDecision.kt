package tech.medo.runtimemonitoring.resolvetrainingalert

import tech.medo.runtimemonitoring.resolvetrainingalert.ResolveTrainingAlertCommand


import tech.medo.runtimemonitoring.events.TrainingAlertResolvedEvent
import tech.medo.runtimemonitoring.trainingalert.TrainingAlertState


import tech.medo.runtimemonitoring.domain.states.TrainingAlertStateEnum


interface ResolveTrainingAlertDecision {
    fun decide(command: ResolveTrainingAlertCommand, state: TrainingAlertState): List<Any> {
        require(state.currentState == TrainingAlertStateEnum.ACKNOWLEDGED) {
            "ResolveTrainingAlert requires TrainingAlert to be Acknowledged."
        }
        return listOf(
            TrainingAlertResolvedEvent(alertId = command.alertId, resolutionSummary = command.resolutionSummary)
        )
    }
}
