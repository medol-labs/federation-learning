package tech.medo.modellifecycle.promotemodeltoproduction

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.modellifecycle.modelversion.ModelVersionSelection
import java.util.UUID;


@Command
data class PromoteModelToProductionCommand(
    val modelVersionId: UUID,
    val releaseChannel: String,
    val productionStage: String
) {
    @TargetEntityId
    val selection: ModelVersionSelection = ModelVersionSelection(modelVersionId = modelVersionId)

}
