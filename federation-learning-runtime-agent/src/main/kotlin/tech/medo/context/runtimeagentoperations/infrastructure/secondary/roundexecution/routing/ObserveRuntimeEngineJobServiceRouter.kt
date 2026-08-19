package tech.medo.runtimeagentoperations.infrastructure.secondary.roundexecution.routing

import tech.medo.runtimeagentoperations.observeruntimeenginejob.ObserveRuntimeEngineJobInput
import tech.medo.runtimeagentoperations.observeruntimeenginejob.ObserveRuntimeEngineJobService
import tech.medo.runtimeagentoperations.observeruntimeenginejob.ObserveRuntimeEngineJobResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class ObserveRuntimeEngineJobServiceRouter(private val adapters: ObjectProvider<ObserveRuntimeEngineJobService>) : ObserveRuntimeEngineJobService {
    override fun supports(input: ObserveRuntimeEngineJobInput): Boolean = true

    override fun execute(input: ObserveRuntimeEngineJobInput): ObserveRuntimeEngineJobResult {
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
            0 -> error("No ObserveRuntimeEngineJobService adapter supports the requested input.")
            else -> error("Multiple ObserveRuntimeEngineJobService adapters support the requested input.")
        }
    }
}
