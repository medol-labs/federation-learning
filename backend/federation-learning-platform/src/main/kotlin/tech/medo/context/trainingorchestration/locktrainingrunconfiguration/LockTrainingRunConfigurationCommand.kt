package tech.medo.trainingorchestration.locktrainingrunconfiguration

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.trainingorchestration.trainingrunconfiguration.TrainingRunConfigurationSelection
import java.util.UUID;


@Command
data class LockTrainingRunConfigurationCommand(
    val trainingRunConfigurationId: UUID,
    val trainingJobId: UUID
) {
    @TargetEntityId
    val selection: TrainingRunConfigurationSelection = TrainingRunConfigurationSelection(trainingRunConfigurationId = trainingRunConfigurationId)

}
