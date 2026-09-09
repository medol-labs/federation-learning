package tech.medo.runtimeagentoperations.reprofileagentdataset

import java.util.UUID;
import java.math.BigDecimal;

interface ReprofileAgentDatasetService {
    fun supports(input: ReprofileAgentDatasetInput): Boolean = true
    fun execute(input: ReprofileAgentDatasetInput): ReprofileAgentDatasetResult
}

data class ReprofileAgentDatasetInput(
    val metadataReportId: UUID,
    val runtimeDatasetBindingId: UUID
)

sealed interface ReprofileAgentDatasetResult {
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
    ) : ReprofileAgentDatasetResult

    data class Rejected(
        val failureReason: String
    ) : ReprofileAgentDatasetResult

    data class Unavailable(
        val failureReason: String
    ) : ReprofileAgentDatasetResult
}
