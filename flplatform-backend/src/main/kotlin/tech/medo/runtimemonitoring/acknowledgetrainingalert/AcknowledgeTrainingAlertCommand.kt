package tech.medo.runtimemonitoring.acknowledgetrainingalert

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimemonitoring.trainingalert.TrainingAlertSelection
import java.util.UUID;


@Command
data class AcknowledgeTrainingAlertCommand(
    val alertId: UUID,
    val acknowledgementNote: String?
) {
    @TargetEntityId
    val selection: TrainingAlertSelection = TrainingAlertSelection(alertId = alertId)

}
