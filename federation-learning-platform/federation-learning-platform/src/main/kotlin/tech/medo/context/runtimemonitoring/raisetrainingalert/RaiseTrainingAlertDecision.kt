package tech.medo.runtimemonitoring.raisetrainingalert

import tech.medo.runtimemonitoring.raisetrainingalert.RaiseTrainingAlertCommand


import tech.medo.runtimemonitoring.events.TrainingAlertRaisedEvent
import tech.medo.runtimemonitoring.trainingalert.TrainingAlertState





interface RaiseTrainingAlertDecision {
    fun decide(command: RaiseTrainingAlertCommand): List<Any> {
        return listOf(
            TrainingAlertRaisedEvent(alertId = command.alertId, nodeId = command.nodeId, trainingJobId = command.trainingJobId, severity = command.severity, message = command.message)
        )
    }
}
