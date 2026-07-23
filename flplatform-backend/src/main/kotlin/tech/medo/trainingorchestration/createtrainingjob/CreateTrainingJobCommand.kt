package tech.medo.trainingorchestration.createtrainingjob

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.trainingorchestration.trainingjob.TrainingJobSelection
import java.util.UUID;


@Command
data class CreateTrainingJobCommand(
    val trainingJobId: UUID = java.util.UUID.randomUUID(),
    val federationId: UUID,
    val trainingRunConfigurationId: UUID,
    val objective: String
) {
    @TargetEntityId
    val selection: TrainingJobSelection = TrainingJobSelection(trainingJobId = trainingJobId)

}
