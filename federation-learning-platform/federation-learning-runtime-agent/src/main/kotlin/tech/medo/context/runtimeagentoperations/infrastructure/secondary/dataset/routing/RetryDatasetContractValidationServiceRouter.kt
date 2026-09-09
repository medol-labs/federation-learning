package tech.medo.runtimeagentoperations.infrastructure.secondary.dataset.routing

import tech.medo.runtimeagentoperations.retrydatasetcontractvalidation.RetryDatasetContractValidationInput
import tech.medo.runtimeagentoperations.retrydatasetcontractvalidation.RetryDatasetContractValidationService
import tech.medo.runtimeagentoperations.retrydatasetcontractvalidation.RetryDatasetContractValidationResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class RetryDatasetContractValidationServiceRouter(private val adapters: ObjectProvider<RetryDatasetContractValidationService>) : RetryDatasetContractValidationService {
    override fun supports(input: RetryDatasetContractValidationInput): Boolean = true

    override fun execute(input: RetryDatasetContractValidationInput): RetryDatasetContractValidationResult {
        val candidates = adapters.stream()
            .filter { it !== this }
            .filter { it.supports(input) }
            .toList()
        return when (candidates.size) {
            1 -> try {
                candidates.first().execute(input)
            } catch (ex: Exception) {
                RetryDatasetContractValidationResult.Unavailable(
                    failureReason = ex.message ?: "RetryDatasetContractValidationService is unavailable."
                )
            }
            0 -> RetryDatasetContractValidationResult.Unavailable(
                failureReason = "No RetryDatasetContractValidationService adapter supports the requested input."
            )
            else -> RetryDatasetContractValidationResult.Unavailable(
                failureReason = "Multiple RetryDatasetContractValidationService adapters support the requested input."
            )
        }
    }
}
