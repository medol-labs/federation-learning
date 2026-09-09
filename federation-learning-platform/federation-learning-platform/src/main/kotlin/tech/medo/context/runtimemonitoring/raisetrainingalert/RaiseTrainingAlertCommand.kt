package tech.medo.runtimemonitoring.raisetrainingalert

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimemonitoring.trainingalert.TrainingAlertSelection
import java.util.UUID;


@Command
data class RaiseTrainingAlertCommand(
    val alertId: UUID = java.util.UUID.randomUUID(),
    val nodeId: UUID,
    val trainingJobId: UUID?,
    val severity: String,
    val message: String
) {
    @TargetEntityId
    val selection: TrainingAlertSelection = TrainingAlertSelection(alertId = alertId)

}
