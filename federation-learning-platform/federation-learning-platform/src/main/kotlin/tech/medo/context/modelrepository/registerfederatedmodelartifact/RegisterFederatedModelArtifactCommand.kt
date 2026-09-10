package tech.medo.modelrepository.registerfederatedmodelartifact

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.modelrepository.modelartifact.ModelArtifactSelection
import java.util.UUID;


@Command
data class RegisterFederatedModelArtifactCommand(
    val modelId: UUID,
    val modelName: String,
    val modelVersion: String,
    val modelDescription: String?,
    val sourceType: String,
    val modelArtifactUri: String,
    val modelRegistryRef: String,
    val modelFormat: String,
    val modelArtifactDigest: String,
    val modelSignatureUri: String?,
    val modelSizeBytes: Int?,
    val trainingJobId: UUID,
    val trainingJobObjective: String,
    val roundId: UUID
) {
    @TargetEntityId
    val selection: ModelArtifactSelection = ModelArtifactSelection(modelName = modelName, modelVersion = modelVersion)

}
