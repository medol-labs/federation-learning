package tech.medo.runtimeagentoperations.infrastructure.secondary.agentdatasetaccessvalidation.routing

import tech.medo.runtimeagentoperations.revalidateagentdatasetaccess.RevalidateAgentDatasetAccessInput
import tech.medo.runtimeagentoperations.revalidateagentdatasetaccess.RevalidateAgentDatasetAccessService
import tech.medo.runtimeagentoperations.revalidateagentdatasetaccess.RevalidateAgentDatasetAccessResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class RevalidateAgentDatasetAccessServiceRouter(private val adapters: ObjectProvider<RevalidateAgentDatasetAccessService>) : RevalidateAgentDatasetAccessService {
    override fun supports(input: RevalidateAgentDatasetAccessInput): Boolean = true

    override fun execute(input: RevalidateAgentDatasetAccessInput): RevalidateAgentDatasetAccessResult {
        val candidates = adapters.stream()
            .filter { it !== this }
            .filter { it.supports(input) }
            .toList()
        return when (candidates.size) {
            1 -> try {
                candidates.first().execute(input)
            } catch (ex: Exception) {
                RevalidateAgentDatasetAccessResult.Unavailable(
                    failureReason = ex.message ?: "RevalidateAgentDatasetAccessService is unavailable."
                )
            }
            0 -> RevalidateAgentDatasetAccessResult.Unavailable(
                failureReason = "No RevalidateAgentDatasetAccessService adapter supports the requested input."
            )
            else -> RevalidateAgentDatasetAccessResult.Unavailable(
                failureReason = "Multiple RevalidateAgentDatasetAccessService adapters support the requested input."
            )
        }
    }
}
