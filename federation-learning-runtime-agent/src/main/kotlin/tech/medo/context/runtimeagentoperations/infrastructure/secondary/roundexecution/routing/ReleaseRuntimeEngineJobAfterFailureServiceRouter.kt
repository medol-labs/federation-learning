package tech.medo.runtimeagentoperations.infrastructure.secondary.roundexecution.routing

import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure.ReleaseRuntimeEngineJobAfterFailureInput
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure.ReleaseRuntimeEngineJobAfterFailureService
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure.ReleaseRuntimeEngineJobAfterFailureResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class ReleaseRuntimeEngineJobAfterFailureServiceRouter(private val adapters: ObjectProvider<ReleaseRuntimeEngineJobAfterFailureService>) : ReleaseRuntimeEngineJobAfterFailureService {
    override fun supports(input: ReleaseRuntimeEngineJobAfterFailureInput): Boolean = true

    override fun execute(input: ReleaseRuntimeEngineJobAfterFailureInput): ReleaseRuntimeEngineJobAfterFailureResult {
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
            0 -> error("No ReleaseRuntimeEngineJobAfterFailureService adapter supports the requested input.")
            else -> error("Multiple ReleaseRuntimeEngineJobAfterFailureService adapters support the requested input.")
        }
    }
}
