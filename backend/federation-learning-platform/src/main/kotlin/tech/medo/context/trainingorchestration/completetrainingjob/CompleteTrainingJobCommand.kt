package tech.medo.trainingorchestration.completetrainingjob

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.trainingorchestration.trainingjob.TrainingJobSelection
import java.util.UUID;


@Command
data class CompleteTrainingJobCommand(
    val trainingJobId: UUID,
    val finalRoundId: UUID,
    val finalModelId: UUID,
    val stopReason: String = ""
) {
    @TargetEntityId
    val selection: TrainingJobSelection = TrainingJobSelection(trainingJobId = trainingJobId)

}
