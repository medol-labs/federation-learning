package tech.medo.runtimeagentoperations.infrastructure.secondary.dataset.routing

import tech.medo.runtimeagentoperations.validatedatasetcontract.ValidateDatasetContractInput
import tech.medo.runtimeagentoperations.validatedatasetcontract.ValidateDatasetContractService
import tech.medo.runtimeagentoperations.validatedatasetcontract.ValidateDatasetContractResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class ValidateDatasetContractServiceRouter(private val adapters: ObjectProvider<ValidateDatasetContractService>) : ValidateDatasetContractService {
    override fun supports(input: ValidateDatasetContractInput): Boolean = true

    override fun execute(input: ValidateDatasetContractInput): ValidateDatasetContractResult {
        val candidates = adapters.stream()
            .filter { it !== this }
            .filter { it.supports(input) }
            .toList()
        return when (candidates.size) {
            1 -> try {
                candidates.first().execute(input)
            } catch (ex: Exception) {
                ValidateDatasetContractResult.Unavailable(
                    failureReason = ex.message ?: "ValidateDatasetContractService is unavailable."
                )
            }
            0 -> ValidateDatasetContractResult.Unavailable(
                failureReason = "No ValidateDatasetContractService adapter supports the requested input."
            )
            else -> ValidateDatasetContractResult.Unavailable(
                failureReason = "Multiple ValidateDatasetContractService adapters support the requested input."
            )
        }
    }
}
