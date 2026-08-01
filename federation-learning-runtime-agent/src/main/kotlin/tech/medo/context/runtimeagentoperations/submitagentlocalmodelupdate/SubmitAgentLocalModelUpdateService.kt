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
    val localModelVersionId: UUID,
    val updateArtifactId: UUID,
    val artifactRef: String,
    val artifactDigest: String,
    val trainingLoss: BigDecimal
)

sealed interface SubmitAgentLocalModelUpdateResult {
    class Succeeded : SubmitAgentLocalModelUpdateResult


}
