package tech.medo.runtimeagentoperations.acceptexecutionplan

import java.util.UUID;

interface AcceptExecutionPlanService {
    fun supports(input: AcceptExecutionPlanInput): Boolean = true
    fun execute(input: AcceptExecutionPlanInput): AcceptExecutionPlanResult
}

data class AcceptExecutionPlanInput(
    val executionPlanId: UUID,
    val executionSessionId: UUID,
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val featureSchemaId: UUID,
    val roundId: UUID,
    val roundNumber: Int,
    val runtimeId: UUID,
    val organizationId: UUID,
    val baseModelId: UUID,
    val baseModelArtifactUri: String,
    val baseModelRegistryRef: String,
    val baseModelFormat: String,
    val baseModelArtifactDigest: String,
    val baseModelSignatureUri: String?,
    val secureAggregationRequired: Boolean,
    val secureAggregationSessionId: UUID?,
    val encryptionScheme: String?,
    val publicKeyVersion: String?,
    val publicKeyRef: String?,
    val encryptedParameterScale: Int?
)

sealed interface AcceptExecutionPlanResult {
    data class Succeeded(
        val localExecutionRequirementsSatisfied: Boolean,
        val runtimeIdentityMatched: Boolean,
        val runtimeDatasetBindingAvailable: Boolean,
        val datasetAccessValidated: Boolean,
        val baseModelAvailable: Boolean,
        val trainingConfigurationSupported: Boolean,
        val runtimeResourceAvailable: Boolean,
        val runtimeAgentIdle: Boolean
    ) : AcceptExecutionPlanResult

    data class Rejected(
        val localExecutionRequirementsSatisfied: Boolean,
        val runtimeIdentityMatched: Boolean,
        val runtimeDatasetBindingAvailable: Boolean,
        val datasetAccessValidated: Boolean,
        val baseModelAvailable: Boolean,
        val trainingConfigurationSupported: Boolean,
        val runtimeResourceAvailable: Boolean,
        val runtimeAgentIdle: Boolean,
        val rejectionReasons: List<String>
    ) : AcceptExecutionPlanResult

    data class Unavailable(
        val failureReason: String
    ) : AcceptExecutionPlanResult
}
