package tech.medo.modelrepository.registermodelartifact

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.modelrepository.modelartifact.ModelArtifactSelection
import java.util.UUID;


@Command
data class RegisterModelArtifactCommand(
    val modelVersionId: UUID = java.util.UUID.randomUUID(),
    val modelArtifactRef: String,
    val modelRepositoryRef: String,
    val modelFormat: String,
    val modelHash: String,
    val modelSignatureRef: String?,
    val modelSizeBytes: Int?,
    val sourceType: String
) {
    @TargetEntityId
    val selection: ModelArtifactSelection = ModelArtifactSelection(modelVersionId = modelVersionId)

}
