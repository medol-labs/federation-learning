package tech.medo.runtimeagentoperations.validatedatasetcontract

import java.util.UUID;
import java.math.BigDecimal;

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
    data class Succeeded(
        val featureSchemaId: UUID,
        val metadataReportId: UUID,
        val schemaCompatible: Boolean,
        val labelCompatible: Boolean,
        val qualityScore: BigDecimal,
        val nonIidScore: BigDecimal
    ) : ValidateDatasetContractResult

    data class Rejected(
        val featureSchemaId: UUID,
        val metadataReportId: UUID,
        val schemaCompatible: Boolean,
        val labelCompatible: Boolean,
        val qualityScore: BigDecimal,
        val nonIidScore: BigDecimal
    ) : ValidateDatasetContractResult

    data class Unavailable(
        val failureReason: String
    ) : ValidateDatasetContractResult
}
