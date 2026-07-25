package tech.medo.modellifecycle.rollbackmodelversion

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.modellifecycle.modelversion.ModelVersionSelection
import java.util.UUID;


@Command
data class RollbackModelVersionCommand(
    val modelVersionId: UUID,
    val previousModelVersionId: UUID,
    val rollbackReason: String
) {
    @TargetEntityId
    val selection: ModelVersionSelection = ModelVersionSelection(modelVersionId = modelVersionId)

}
