package tech.medo.runtimeagentoperations.infrastructure.secondary.agentdatasetprofile.routing

import tech.medo.runtimeagentoperations.reprofileagentdataset.ReprofileAgentDatasetInput
import tech.medo.runtimeagentoperations.reprofileagentdataset.ReprofileAgentDatasetService
import tech.medo.runtimeagentoperations.reprofileagentdataset.ReprofileAgentDatasetResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class ReprofileAgentDatasetServiceRouter(private val adapters: ObjectProvider<ReprofileAgentDatasetService>) : ReprofileAgentDatasetService {
    override fun supports(input: ReprofileAgentDatasetInput): Boolean = true

    override fun execute(input: ReprofileAgentDatasetInput): ReprofileAgentDatasetResult {
        val candidates = adapters.stream()
            .filter { it !== this }
            .filter { it.supports(input) }
            .toList()
        return when (candidates.size) {
            1 -> try {
                candidates.first().execute(input)
            } catch (ex: Exception) {
                ReprofileAgentDatasetResult.Unavailable(
                    failureReason = ex.message ?: "ReprofileAgentDatasetService is unavailable."
                )
            }
            0 -> ReprofileAgentDatasetResult.Unavailable(
                failureReason = "No ReprofileAgentDatasetService adapter supports the requested input."
            )
            else -> ReprofileAgentDatasetResult.Unavailable(
                failureReason = "Multiple ReprofileAgentDatasetService adapters support the requested input."
            )
        }
    }
}
