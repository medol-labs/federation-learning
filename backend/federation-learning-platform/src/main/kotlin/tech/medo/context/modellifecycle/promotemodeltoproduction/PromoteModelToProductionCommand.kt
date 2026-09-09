package tech.medo.modellifecycle.promotemodeltoproduction

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.modellifecycle.model.ModelSelection
import java.util.UUID;


@Command
data class PromoteModelToProductionCommand(
    val modelId: UUID,
    val releaseChannel: String,
    val productionStage: String
) {
    @TargetEntityId
    val selection: ModelSelection = ModelSelection(modelId = modelId)

}
