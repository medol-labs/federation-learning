package tech.medo.runtimeagentoperations.infrastructure.secondary.dataset.routing

import tech.medo.runtimeagentoperations.declaredataset.DeclareDatasetInput
import tech.medo.runtimeagentoperations.declaredataset.DeclareDatasetService
import tech.medo.runtimeagentoperations.declaredataset.DeclareDatasetResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class DeclareDatasetServiceRouter(private val adapters: ObjectProvider<DeclareDatasetService>) : DeclareDatasetService {
    override fun supports(input: DeclareDatasetInput): Boolean = true

    override fun execute(input: DeclareDatasetInput): DeclareDatasetResult {
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
            0 -> error("No DeclareDatasetService adapter supports the requested input.")
            else -> error("Multiple DeclareDatasetService adapters support the requested input.")
        }
    }
}
