package tech.medo.modellifecycle.rollbackmodel

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.modellifecycle.model.ModelSelection
import java.util.UUID;


@Command
data class RollbackModelCommand(
    val modelId: UUID,
    val previousModelId: UUID,
    val rollbackReason: String
) {
    @TargetEntityId
    val selection: ModelSelection = ModelSelection(modelId = modelId)

}
