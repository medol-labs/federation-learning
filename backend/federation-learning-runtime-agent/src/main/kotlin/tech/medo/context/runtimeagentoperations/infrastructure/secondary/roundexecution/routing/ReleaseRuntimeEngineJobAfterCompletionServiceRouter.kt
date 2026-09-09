package tech.medo.runtimeagentoperations.infrastructure.secondary.roundexecution.routing

import tech.medo.runtimeagentoperations.releaseruntimeenginejobaftercompletion.ReleaseRuntimeEngineJobAfterCompletionInput
import tech.medo.runtimeagentoperations.releaseruntimeenginejobaftercompletion.ReleaseRuntimeEngineJobAfterCompletionService
import tech.medo.runtimeagentoperations.releaseruntimeenginejobaftercompletion.ReleaseRuntimeEngineJobAfterCompletionResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class ReleaseRuntimeEngineJobAfterCompletionServiceRouter(private val adapters: ObjectProvider<ReleaseRuntimeEngineJobAfterCompletionService>) : ReleaseRuntimeEngineJobAfterCompletionService {
    override fun supports(input: ReleaseRuntimeEngineJobAfterCompletionInput): Boolean = true

    override fun execute(input: ReleaseRuntimeEngineJobAfterCompletionInput): ReleaseRuntimeEngineJobAfterCompletionResult {
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
            0 -> error("No ReleaseRuntimeEngineJobAfterCompletionService adapter supports the requested input.")
            else -> error("Multiple ReleaseRuntimeEngineJobAfterCompletionService adapters support the requested input.")
        }
    }
}
