package tech.medo.modelrepository.registermodelartifact

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.modelrepository.modelartifact.ModelArtifactSelection
import java.util.UUID;


@Command
data class RegisterModelArtifactCommand(
    val modelId: UUID = java.util.UUID.randomUUID(),
    val modelName: String,
    val modelPlugin: String,
    val modelVersion: String,
    val modelDescription: String?,
    val sourceType: String,
    val fileId: UUID?,
    val modelFormat: String?
) {
    @TargetEntityId
    val selection: ModelArtifactSelection = ModelArtifactSelection(modelName = modelName, modelVersion = modelVersion)

}
