package tech.medo.runtimeagentoperations.retrydatasetcontractvalidation

import java.util.UUID;

interface RetryDatasetContractValidationService {
    fun supports(input: RetryDatasetContractValidationInput): Boolean = true
    fun execute(input: RetryDatasetContractValidationInput): RetryDatasetContractValidationResult
}

data class RetryDatasetContractValidationInput(
    val datasetId: UUID
)

sealed interface RetryDatasetContractValidationResult {
    class Succeeded : RetryDatasetContractValidationResult

    data class Rejected(
        val failureReason: String
    ) : RetryDatasetContractValidationResult

    data class Unavailable(
        val failureReason: String
    ) : RetryDatasetContractValidationResult
}
