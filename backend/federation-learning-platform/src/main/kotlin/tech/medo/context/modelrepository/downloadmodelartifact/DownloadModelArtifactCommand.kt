package tech.medo.modelrepository.downloadmodelartifact

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.modelrepository.modelartifact.ModelArtifactSelection
import java.util.UUID;


@Command
data class DownloadModelArtifactCommand(
    val modelId: UUID,
    val modelName: String,
    val modelVersion: String
) {
    @TargetEntityId
    val selection: ModelArtifactSelection = ModelArtifactSelection(modelName = modelName, modelVersion = modelVersion)

}
