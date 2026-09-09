package tech.medo.runtimeagentoperations.infrastructure.secondary.roundexecution.routing

import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterstartfailure.ReleaseRuntimeEngineJobAfterStartFailureInput
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterstartfailure.ReleaseRuntimeEngineJobAfterStartFailureService
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterstartfailure.ReleaseRuntimeEngineJobAfterStartFailureResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class ReleaseRuntimeEngineJobAfterStartFailureServiceRouter(private val adapters: ObjectProvider<ReleaseRuntimeEngineJobAfterStartFailureService>) : ReleaseRuntimeEngineJobAfterStartFailureService {
    override fun supports(input: ReleaseRuntimeEngineJobAfterStartFailureInput): Boolean = true

    override fun execute(input: ReleaseRuntimeEngineJobAfterStartFailureInput): ReleaseRuntimeEngineJobAfterStartFailureResult {
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
            0 -> error("No ReleaseRuntimeEngineJobAfterStartFailureService adapter supports the requested input.")
            else -> error("Multiple ReleaseRuntimeEngineJobAfterStartFailureService adapters support the requested input.")
        }
    }
}
