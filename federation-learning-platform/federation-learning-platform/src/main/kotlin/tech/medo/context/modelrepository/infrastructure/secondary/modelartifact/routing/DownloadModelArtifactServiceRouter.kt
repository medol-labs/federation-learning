package tech.medo.modelrepository.infrastructure.secondary.modelartifact.routing

import tech.medo.modelrepository.downloadmodelartifact.DownloadModelArtifactInput
import tech.medo.modelrepository.downloadmodelartifact.DownloadModelArtifactService
import tech.medo.modelrepository.downloadmodelartifact.DownloadModelArtifactResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class DownloadModelArtifactServiceRouter(private val adapters: ObjectProvider<DownloadModelArtifactService>) : DownloadModelArtifactService {
    override fun supports(input: DownloadModelArtifactInput): Boolean = true

    override fun execute(input: DownloadModelArtifactInput): DownloadModelArtifactResult {
        val candidates = adapters.stream()
            .filter { it !== this }
            .filter { it.supports(input) }
            .toList()
        return when (candidates.size) {
            1 -> try {
                candidates.first().execute(input)
            } catch (ex: Exception) {
                throw ex
            }
            0 -> error("No DownloadModelArtifactService adapter supports the requested input.")
            else -> error("Multiple DownloadModelArtifactService adapters support the requested input.")
        }
    }
}
