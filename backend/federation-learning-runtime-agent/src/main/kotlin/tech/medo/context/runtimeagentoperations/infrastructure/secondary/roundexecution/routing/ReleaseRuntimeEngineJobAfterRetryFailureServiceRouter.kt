package tech.medo.runtimeagentoperations.infrastructure.secondary.roundexecution.routing

import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterretryfailure.ReleaseRuntimeEngineJobAfterRetryFailureInput
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterretryfailure.ReleaseRuntimeEngineJobAfterRetryFailureService
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterretryfailure.ReleaseRuntimeEngineJobAfterRetryFailureResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class ReleaseRuntimeEngineJobAfterRetryFailureServiceRouter(private val adapters: ObjectProvider<ReleaseRuntimeEngineJobAfterRetryFailureService>) : ReleaseRuntimeEngineJobAfterRetryFailureService {
    override fun supports(input: ReleaseRuntimeEngineJobAfterRetryFailureInput): Boolean = true

    override fun execute(input: ReleaseRuntimeEngineJobAfterRetryFailureInput): ReleaseRuntimeEngineJobAfterRetryFailureResult {
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
            0 -> error("No ReleaseRuntimeEngineJobAfterRetryFailureService adapter supports the requested input.")
            else -> error("Multiple ReleaseRuntimeEngineJobAfterRetryFailureService adapters support the requested input.")
        }
    }
}
