package tech.medo.runtimemonitoring.resolvetrainingalert

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimemonitoring.trainingalert.TrainingAlertSelection
import java.util.UUID;


@Command
data class ResolveTrainingAlertCommand(
    val alertId: UUID,
    val resolutionSummary: String
) {
    @TargetEntityId
    val selection: TrainingAlertSelection = TrainingAlertSelection(alertId = alertId)

}
