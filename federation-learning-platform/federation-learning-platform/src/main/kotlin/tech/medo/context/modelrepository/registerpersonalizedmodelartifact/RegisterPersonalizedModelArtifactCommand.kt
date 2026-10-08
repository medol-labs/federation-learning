package tech.medo.modelrepository.registerpersonalizedmodelartifact

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.modelrepository.modelartifact.ModelArtifactSelection
import java.util.UUID;


@Command
data class RegisterPersonalizedModelArtifactCommand(
    val modelId: UUID,
    val modelName: String = "",
    val modelPlugin: String,
    val modelVersion: String = "",
    val modelDescription: String? = null,
    val sourceType: String = "",
    val modelScope: String,
    val modelArtifactUri: String,
    val modelRegistryRef: String = "",
    val modelFormat: String,
    val modelArtifactDigest: String,
    val modelSignatureUri: String? = null,
    val modelSizeBytes: Int? = null,
    val trainingJobId: UUID,
    val trainingJobObjective: String,
    val roundId: UUID,
    val personalizationMode: String?,
    val personalizationTargetType: String?,
    val personalizationTargetId: UUID?,
    val personalizationCohortId: UUID?,
    val baseGlobalModelId: UUID?
) {
    @TargetEntityId
    val selection: ModelArtifactSelection = ModelArtifactSelection(modelName = modelName, modelVersion = modelVersion)


}
