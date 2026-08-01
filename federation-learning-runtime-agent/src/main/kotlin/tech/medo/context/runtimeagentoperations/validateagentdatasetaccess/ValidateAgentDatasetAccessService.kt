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
    val featureSchemaId: UUID,
    val runtimeId: UUID,
    val dataSourceType: String,
    val host: String?,
    val port: Int?,
    val url: String?,
    val databaseName: String?,
    val schemaName: String?,
    val tableName: String?,
    val filePath: String?,
    val objectBucket: String?,
    val objectPrefix: String?,
    val dataFormat: String,
    val credentialSecretName: String?
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
