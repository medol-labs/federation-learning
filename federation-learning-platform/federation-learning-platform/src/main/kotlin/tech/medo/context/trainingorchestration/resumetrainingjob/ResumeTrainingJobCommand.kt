package tech.medo.trainingorchestration.resumetrainingjob

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.trainingorchestration.trainingjob.TrainingJobSelection
import java.util.UUID;


@Command
data class ResumeTrainingJobCommand(
    val trainingJobId: UUID,
    val resumeReason: String?
) {
    @TargetEntityId
    val selection: TrainingJobSelection = TrainingJobSelection(trainingJobId = trainingJobId)

}
