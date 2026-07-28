package tech.medo.runtimeagentoperations.infrastructure.secondary.agentdatasetaccessvalidation.routing

import tech.medo.runtimeagentoperations.validateagentdatasetaccess.ValidateAgentDatasetAccessInput
import tech.medo.runtimeagentoperations.validateagentdatasetaccess.ValidateAgentDatasetAccessService
import tech.medo.runtimeagentoperations.validateagentdatasetaccess.ValidateAgentDatasetAccessResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class ValidateAgentDatasetAccessServiceRouter(private val adapters: ObjectProvider<ValidateAgentDatasetAccessService>) : ValidateAgentDatasetAccessService {
    override fun supports(input: ValidateAgentDatasetAccessInput): Boolean = true

    override fun execute(input: ValidateAgentDatasetAccessInput): ValidateAgentDatasetAccessResult {
        val candidates = adapters.stream()
            .filter { it !== this }
            .filter { it.supports(input) }
            .toList()
        return when (candidates.size) {
            1 -> try {
                candidates.first().execute(input)
            } catch (ex: Exception) {
                ValidateAgentDatasetAccessResult.Unavailable(
                    failureReason = ex.message ?: "ValidateAgentDatasetAccessService is unavailable."
                )
            }
            0 -> ValidateAgentDatasetAccessResult.Unavailable(
                failureReason = "No ValidateAgentDatasetAccessService adapter supports the requested input."
            )
            else -> ValidateAgentDatasetAccessResult.Unavailable(
                failureReason = "Multiple ValidateAgentDatasetAccessService adapters support the requested input."
            )
        }
    }
}
