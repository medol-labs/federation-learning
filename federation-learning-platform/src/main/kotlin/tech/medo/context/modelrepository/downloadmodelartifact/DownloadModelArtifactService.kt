package tech.medo.modelrepository.downloadmodelartifact

import java.util.UUID;

interface DownloadModelArtifactService {
    fun supports(input: DownloadModelArtifactInput): Boolean = true
    fun execute(input: DownloadModelArtifactInput): DownloadModelArtifactResult
}

data class DownloadModelArtifactInput(
    val modelId: UUID
)

sealed interface DownloadModelArtifactResult {
    data class Succeeded(
        val modelName: String,
        val modelVersion: String,
        val modelFormat: String,
        val modelArtifactDigest: String,
        val downloadUri: String
    ) : DownloadModelArtifactResult


}
