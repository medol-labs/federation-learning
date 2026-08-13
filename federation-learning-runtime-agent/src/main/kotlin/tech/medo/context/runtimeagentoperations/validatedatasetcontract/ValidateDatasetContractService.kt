package tech.medo.runtimeagentoperations.validatedatasetcontract

import java.util.UUID;

interface ValidateDatasetContractService {
    fun supports(input: ValidateDatasetContractInput): Boolean = true
    fun execute(input: ValidateDatasetContractInput): ValidateDatasetContractResult
}

data class ValidateDatasetContractInput(
    val datasetId: UUID,
    val metadataReportId: UUID,
    val featureSchemaId: UUID
)

sealed interface ValidateDatasetContractResult {
    class Succeeded : ValidateDatasetContractResult

    data class Rejected(
        val failureReason: String
    ) : ValidateDatasetContractResult

    data class Unavailable(
        val failureReason: String
    ) : ValidateDatasetContractResult
}
