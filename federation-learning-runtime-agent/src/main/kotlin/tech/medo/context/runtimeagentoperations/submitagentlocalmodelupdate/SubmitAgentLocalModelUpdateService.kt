package tech.medo.runtimeagentoperations.submitagentlocalmodelupdate

import java.util.UUID;
import java.math.BigDecimal;

interface SubmitAgentLocalModelUpdateService {
    fun supports(input: SubmitAgentLocalModelUpdateInput): Boolean = true
    fun execute(input: SubmitAgentLocalModelUpdateInput): SubmitAgentLocalModelUpdateResult
}

data class SubmitAgentLocalModelUpdateInput(
    val modelUpdateSubmissionId: UUID,
    val executionSessionId: UUID,
    val executionPlanId: UUID,
    val roundExecutionId: UUID,
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val roundId: UUID,
    val runtimeId: UUID,
    val featureSchemaId: UUID,
    val secureAggregationRequired: Boolean,
    val secureAggregationSessionId: UUID?,
    val encryptionScheme: String?,
    val publicKeyVersion: String?,
    val runtimeEngineJobId: String,
    val localModelId: UUID,
    val updateArtifactId: UUID,
    val artifactRef: String,
    val artifactDigest: String,
    val updateProtectionType: String,
    val trainingLoss: BigDecimal
)

sealed interface SubmitAgentLocalModelUpdateResult {
    class Succeeded : SubmitAgentLocalModelUpdateResult


}
