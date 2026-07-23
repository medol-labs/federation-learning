package tech.medo.modellifecycle.retiremodelversion

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.modellifecycle.modelversion.ModelVersionSelection
import java.util.UUID;


@Command
data class RetireModelVersionCommand(
    val modelVersionId: UUID,
    val retirementReason: String
) {
    @TargetEntityId
    val selection: ModelVersionSelection = ModelVersionSelection(modelVersionId = modelVersionId)

}
