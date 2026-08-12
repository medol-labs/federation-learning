package tech.medo.runtimeagentoperations.validatedatasetcontract

import java.util.UUID;
import java.math.BigDecimal

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
        val schemaCompatible: Boolean? = null,
        val labelCompatible: Boolean? = null,
        val qualityScore: BigDecimal? = null,
        val nonIidScore: BigDecimal? = null
    ) : ValidateDatasetContractResult

    data class Rejected(
        val failureReason: String,
        val schemaCompatible: Boolean? = null,
        val labelCompatible: Boolean? = null,
        val qualityScore: BigDecimal? = null,
        val nonIidScore: BigDecimal? = null
    ) : ValidateDatasetContractResult

    data class Unavailable(
        val failureReason: String
    ) : ValidateDatasetContractResult
}
