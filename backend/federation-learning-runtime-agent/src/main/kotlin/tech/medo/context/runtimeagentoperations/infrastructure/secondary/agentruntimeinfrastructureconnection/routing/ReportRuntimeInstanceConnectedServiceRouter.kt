package tech.medo.runtimeagentoperations.infrastructure.secondary.agentruntimeinfrastructureconnection.routing

import tech.medo.runtimeagentoperations.reportruntimeinstanceconnected.ReportRuntimeInstanceConnectedInput
import tech.medo.runtimeagentoperations.reportruntimeinstanceconnected.ReportRuntimeInstanceConnectedService
import tech.medo.runtimeagentoperations.reportruntimeinstanceconnected.ReportRuntimeInstanceConnectedResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class ReportRuntimeInstanceConnectedServiceRouter(private val adapters: ObjectProvider<ReportRuntimeInstanceConnectedService>) : ReportRuntimeInstanceConnectedService {
    override fun supports(input: ReportRuntimeInstanceConnectedInput): Boolean = true

    override fun execute(input: ReportRuntimeInstanceConnectedInput): ReportRuntimeInstanceConnectedResult {
        val candidates = adapters.stream()
            .filter { it !== this }
            .filter { it.supports(input) }
            .toList()
        return when (candidates.size) {
            1 -> try {
                candidates.first().execute(input)
            } catch (ex: Exception) {
                ReportRuntimeInstanceConnectedResult.Unavailable(
                    failureReason = ex.message ?: "ReportRuntimeInstanceConnectedService is unavailable."
                )
            }
            0 -> ReportRuntimeInstanceConnectedResult.Unavailable(
                failureReason = "No ReportRuntimeInstanceConnectedService adapter supports the requested input."
            )
            else -> ReportRuntimeInstanceConnectedResult.Unavailable(
                failureReason = "Multiple ReportRuntimeInstanceConnectedService adapters support the requested input."
            )
        }
    }
}
