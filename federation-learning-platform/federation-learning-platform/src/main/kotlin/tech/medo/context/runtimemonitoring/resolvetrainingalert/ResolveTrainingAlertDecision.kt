package tech.medo.runtimemonitoring.resolvetrainingalert

import tech.medo.runtimemonitoring.resolvetrainingalert.ResolveTrainingAlertCommand


import tech.medo.runtimemonitoring.events.TrainingAlertResolvedEvent
import tech.medo.runtimemonitoring.trainingalert.TrainingAlertState


import tech.medo.runtimemonitoring.domain.states.TrainingAlertStateEnum


interface ResolveTrainingAlertDecision {
    fun decide(command: ResolveTrainingAlertCommand, state: TrainingAlertState): List<Any> {
        if (state.currentState != TrainingAlertStateEnum.Acknowledged) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.runtimemonitoring.resolveTrainingAlert.requiresState",
                args = mapOf(
                    "command" to "ResolveTrainingAlert",
                    "aggregate" to "TrainingAlert",
                    "expectedState" to "Acknowledged",
                    "actualState" to state.currentState.toString()
                ),
                message = "ResolveTrainingAlert requires TrainingAlert to be Acknowledged."
            )
        }
        return listOf(
            TrainingAlertResolvedEvent(alertId = command.alertId, resolutionSummary = command.resolutionSummary)
        )
    }
}
