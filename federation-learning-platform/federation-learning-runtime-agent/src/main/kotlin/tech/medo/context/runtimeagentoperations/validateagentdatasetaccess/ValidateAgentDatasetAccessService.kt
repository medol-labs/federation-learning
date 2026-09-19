package tech.medo.runtimeagentoperations.validateagentdatasetaccess

import java.util.UUID;

interface ValidateAgentDatasetAccessService {
    fun supports(input: ValidateAgentDatasetAccessInput): Boolean = true
    fun execute(input: ValidateAgentDatasetAccessInput): ValidateAgentDatasetAccessResult
}

data class ValidateAgentDatasetAccessInput(
    val datasetAccessValidationId: UUID,
    val runtimeDatasetBindingId: UUID,
    val datasetId: UUID,
    val organizationId: UUID,
    val organizationName: String?,
    val featureSchemaId: UUID,
    val featureDomain: String?,
    val featureSchemaVersion: String?,
    val datasetName: String,
    val runtimeId: UUID,
    val runtimeName: String?,
    val filePath: String,
    val dataFormat: String
)

sealed interface ValidateAgentDatasetAccessResult {
    data class Succeeded(
        val readable: Boolean,
        val schemaReadable: Boolean,
        val sampleBatchReadable: Boolean
    ) : ValidateAgentDatasetAccessResult

    data class Rejected(
        val failureReason: String
    ) : ValidateAgentDatasetAccessResult

    data class Unavailable(
        val failureReason: String
    ) : ValidateAgentDatasetAccessResult
}
