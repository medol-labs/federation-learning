package tech.medo.infrastructure.secondary.modelrepository.modelartifact.downloadmodelartifact

import org.springframework.stereotype.Component
import tech.medo.modelrepository.domain.states.ModelArtifactStateEnum
import tech.medo.modelrepository.downloadmodelartifact.DownloadModelArtifactInput
import tech.medo.modelrepository.downloadmodelartifact.DownloadModelArtifactResult
import tech.medo.modelrepository.downloadmodelartifact.DownloadModelArtifactService
import tech.medo.modelrepository.modelartifactcatalog.ModelArtifactCatalogReadModelRepository

@Component
class ReadModelDownloadModelArtifactAdapter(
    private val repository: ModelArtifactCatalogReadModelRepository
) : DownloadModelArtifactService {
    override fun execute(input: DownloadModelArtifactInput): DownloadModelArtifactResult {
        val artifact = checkNotNull(repository.findById(input.modelId)) {
            "Model artifact ${input.modelId} was not found."
        }
        check(artifact.state == ModelArtifactStateEnum.REGISTERED) {
            "Model artifact ${input.modelId} is not registered."
        }
        return DownloadModelArtifactResult.Succeeded(
            modelName = checkNotNull(artifact.modelName) { "Model artifact name is required." },
            modelVersion = checkNotNull(artifact.modelVersion) { "Model artifact version is required." },
            modelFormat = checkNotNull(artifact.modelFormat) { "Model artifact format is required." },
            modelArtifactDigest = checkNotNull(artifact.modelArtifactDigest) {
                "Model artifact digest is required."
            },
            downloadUri = checkNotNull(artifact.modelArtifactUri?.takeIf(String::isNotBlank)) {
                "Model artifact content is not available for download."
            }
        )
    }
}
