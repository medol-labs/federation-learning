package tech.medo.runtimeagentoperations.profileagentdataset

import java.util.UUID;
import java.math.BigDecimal;

interface ProfileAgentDatasetService {
    fun supports(input: ProfileAgentDatasetInput): Boolean = true
    fun execute(input: ProfileAgentDatasetInput): ProfileAgentDatasetResult
}

data class ProfileAgentDatasetInput(
    val metadataReportId: UUID,
    val runtimeDatasetBindingId: UUID,
    val datasetId: UUID,
    val organizationId: UUID,
    val featureSchemaId: UUID,
    val datasetName: String,
    val runtimeId: UUID
)

sealed interface ProfileAgentDatasetResult {
    data class Succeeded(
        val sampleCount: Int,
        val featureCount: Int,
        val schemaCompatible: Boolean?,
        val labelCompatible: Boolean?,
        val missingValueRate: BigDecimal?,
        val duplicateRate: BigDecimal?,
        val qualityScore: BigDecimal?,
        val nonIidScore: BigDecimal?,
        val classBalanceScore: BigDecimal?
    ) : ProfileAgentDatasetResult

    data class Rejected(
        val failureReason: String
    ) : ProfileAgentDatasetResult

    data class Unavailable(
        val failureReason: String
    ) : ProfileAgentDatasetResult
}
