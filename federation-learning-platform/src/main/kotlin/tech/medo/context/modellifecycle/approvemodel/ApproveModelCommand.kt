package tech.medo.modellifecycle.approvemodel

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.modellifecycle.model.ModelSelection
import java.util.UUID;


@Command
data class ApproveModelCommand(
    val modelId: UUID,
    val approvalNote: String?
) {
    @TargetEntityId
    val selection: ModelSelection = ModelSelection(modelId = modelId)

}
