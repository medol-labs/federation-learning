package tech.medo.modellifecycle.retiremodel

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.modellifecycle.model.ModelSelection
import java.util.UUID;


@Command
data class RetireModelCommand(
    val modelId: UUID,
    val retirementReason: String
) {
    @TargetEntityId
    val selection: ModelSelection = ModelSelection(modelId = modelId)

}
