package tech.medo.runtimeagentoperations.infrastructure.secondary.agentdatasetprofile.routing

import tech.medo.runtimeagentoperations.profileagentdataset.ProfileAgentDatasetInput
import tech.medo.runtimeagentoperations.profileagentdataset.ProfileAgentDatasetService
import tech.medo.runtimeagentoperations.profileagentdataset.ProfileAgentDatasetResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class ProfileAgentDatasetServiceRouter(private val adapters: ObjectProvider<ProfileAgentDatasetService>) : ProfileAgentDatasetService {
    override fun supports(input: ProfileAgentDatasetInput): Boolean = true

    override fun execute(input: ProfileAgentDatasetInput): ProfileAgentDatasetResult {
        val candidates = adapters.stream()
            .filter { it !== this }
            .filter { it.supports(input) }
            .toList()
        return when (candidates.size) {
            1 -> try {
                candidates.first().execute(input)
            } catch (ex: Exception) {
                ProfileAgentDatasetResult.Unavailable(
                    failureReason = ex.message ?: "ProfileAgentDatasetService is unavailable."
                )
            }
            0 -> ProfileAgentDatasetResult.Unavailable(
                failureReason = "No ProfileAgentDatasetService adapter supports the requested input."
            )
            else -> ProfileAgentDatasetResult.Unavailable(
                failureReason = "Multiple ProfileAgentDatasetService adapters support the requested input."
            )
        }
    }
}
