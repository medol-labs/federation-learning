package tech.medo.modelrepository.infrastructure.secondary.modelartifact.routing

import tech.medo.modelrepository.registermodelartifact.RegisterModelArtifactInput
import tech.medo.modelrepository.registermodelartifact.RegisterModelArtifactService
import tech.medo.modelrepository.registermodelartifact.RegisterModelArtifactResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class RegisterModelArtifactServiceRouter(private val adapters: ObjectProvider<RegisterModelArtifactService>) : RegisterModelArtifactService {
    override fun supports(input: RegisterModelArtifactInput): Boolean = true

    override fun execute(input: RegisterModelArtifactInput): RegisterModelArtifactResult {
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
            0 -> error("No RegisterModelArtifactService adapter supports the requested input.")
            else -> error("Multiple RegisterModelArtifactService adapters support the requested input.")
        }
    }
}
