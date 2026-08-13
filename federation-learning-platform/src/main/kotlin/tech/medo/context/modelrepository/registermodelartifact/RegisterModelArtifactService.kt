package tech.medo.modelrepository.registermodelartifact

import java.util.UUID;

interface RegisterModelArtifactService {
    fun supports(input: RegisterModelArtifactInput): Boolean = true
    fun execute(input: RegisterModelArtifactInput): RegisterModelArtifactResult
}

data class RegisterModelArtifactInput(
    val modelId: UUID,
    val modelName: String,
    val modelVersion: String,
    val sourceType: String,
    val sourceLocation: String?,
    val modelFormat: String?
)

sealed interface RegisterModelArtifactResult {
    data class Succeeded(
        val modelArtifactUri: String,
        val modelRegistryRef: String,
        val modelArtifactDigest: String,
        val modelSignatureUri: String?,
        val modelSizeBytes: Int?
    ) : RegisterModelArtifactResult


}
