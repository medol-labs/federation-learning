package tech.medo.trainingorchestration.pausetrainingjob

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.trainingorchestration.trainingjob.TrainingJobSelection
import java.util.UUID;


@Command
data class PauseTrainingJobCommand(
    val trainingJobId: UUID,
    val pauseReason: String
) {
    @TargetEntityId
    val selection: TrainingJobSelection = TrainingJobSelection(trainingJobId = trainingJobId)

}
