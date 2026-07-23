package tech.medo.modellifecycle.approvemodel

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.modellifecycle.modelversion.ModelVersionSelection
import java.util.UUID;


@Command
data class ApproveModelCommand(
    val modelVersionId: UUID,
    val approvalNote: String?
) {
    @TargetEntityId
    val selection: ModelVersionSelection = ModelVersionSelection(modelVersionId = modelVersionId)

}
