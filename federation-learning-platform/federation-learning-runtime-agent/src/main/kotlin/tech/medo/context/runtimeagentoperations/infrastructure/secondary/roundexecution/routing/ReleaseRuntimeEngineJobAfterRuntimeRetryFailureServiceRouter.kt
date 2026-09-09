package tech.medo.runtimeagentoperations.infrastructure.secondary.roundexecution.routing

import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterruntimeretryfailure.ReleaseRuntimeEngineJobAfterRuntimeRetryFailureInput
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterruntimeretryfailure.ReleaseRuntimeEngineJobAfterRuntimeRetryFailureService
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterruntimeretryfailure.ReleaseRuntimeEngineJobAfterRuntimeRetryFailureResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class ReleaseRuntimeEngineJobAfterRuntimeRetryFailureServiceRouter(private val adapters: ObjectProvider<ReleaseRuntimeEngineJobAfterRuntimeRetryFailureService>) : ReleaseRuntimeEngineJobAfterRuntimeRetryFailureService {
    override fun supports(input: ReleaseRuntimeEngineJobAfterRuntimeRetryFailureInput): Boolean = true

    override fun execute(input: ReleaseRuntimeEngineJobAfterRuntimeRetryFailureInput): ReleaseRuntimeEngineJobAfterRuntimeRetryFailureResult {
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
            0 -> error("No ReleaseRuntimeEngineJobAfterRuntimeRetryFailureService adapter supports the requested input.")
            else -> error("Multiple ReleaseRuntimeEngineJobAfterRuntimeRetryFailureService adapters support the requested input.")
        }
    }
}
