package tech.medo.trainingorchestration.canceltrainingjob

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.trainingorchestration.trainingjob.TrainingJobSelection
import java.util.UUID;


@Command
data class CancelTrainingJobCommand(
    val trainingJobId: UUID,
    val cancelReason: String?
) {
    @TargetEntityId
    val selection: TrainingJobSelection = TrainingJobSelection(trainingJobId = trainingJobId)

}
