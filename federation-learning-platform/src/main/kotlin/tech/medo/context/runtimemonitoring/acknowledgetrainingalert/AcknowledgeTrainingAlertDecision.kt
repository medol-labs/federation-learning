package tech.medo.runtimemonitoring.acknowledgetrainingalert

import tech.medo.runtimemonitoring.acknowledgetrainingalert.AcknowledgeTrainingAlertCommand

import tech.medo.runtimemonitoring.events.TrainingAlertAcknowledgedEvent
import tech.medo.runtimemonitoring.trainingalert.TrainingAlertState


import tech.medo.runtimemonitoring.domain.states.TrainingAlertStateEnum


interface AcknowledgeTrainingAlertDecision {
    fun decide(command: AcknowledgeTrainingAlertCommand, state: TrainingAlertState): List<Any> {
        require(state.currentState == TrainingAlertStateEnum.RAISED) {
            "AcknowledgeTrainingAlert requires TrainingAlert to be Raised."
        }
        return listOf(
            TrainingAlertAcknowledgedEvent(alertId = command.alertId, acknowledgementNote = command.acknowledgementNote)
        )
    }
}
