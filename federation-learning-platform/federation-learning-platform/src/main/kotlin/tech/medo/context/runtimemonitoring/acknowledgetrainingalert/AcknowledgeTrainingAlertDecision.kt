package tech.medo.runtimemonitoring.acknowledgetrainingalert

import tech.medo.runtimemonitoring.acknowledgetrainingalert.AcknowledgeTrainingAlertCommand


import tech.medo.runtimemonitoring.events.TrainingAlertAcknowledgedEvent
import tech.medo.runtimemonitoring.trainingalert.TrainingAlertState


import tech.medo.runtimemonitoring.domain.states.TrainingAlertStateEnum


interface AcknowledgeTrainingAlertDecision {
    fun decide(command: AcknowledgeTrainingAlertCommand, state: TrainingAlertState): List<Any> {
        if (state.currentState != TrainingAlertStateEnum.Raised) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.runtimemonitoring.acknowledgeTrainingAlert.requiresState",
                args = mapOf(
                    "command" to "AcknowledgeTrainingAlert",
                    "aggregate" to "TrainingAlert",
                    "expectedState" to "Raised",
                    "actualState" to state.currentState.toString()
                ),
                message = "AcknowledgeTrainingAlert requires TrainingAlert to be Raised."
            )
        }
        return listOf(
            TrainingAlertAcknowledgedEvent(alertId = command.alertId, acknowledgementNote = command.acknowledgementNote)
        )
    }
}
