package tech.medo.runtimeprovisioning.verifyruntimeinfrastructure

import java.util.UUID;

interface VerifyRuntimeInfrastructureService {
    fun supports(input: RuntimeInfrastructureVerificationInput): Boolean = true
    fun verify(input: RuntimeInfrastructureVerificationInput): RuntimeInfrastructureVerification
}

data class RuntimeInfrastructureVerificationInput(
    val runtimeInfrastructureId: UUID,
    val runtimeInstallationPlanId: UUID,
    val runtimeAgentId: UUID
)

sealed interface RuntimeInfrastructureVerification {
    data class Succeeded(
        val agentInstallMode: String,
        val observedNodeCount: Int
    ) : RuntimeInfrastructureVerification

    data class Rejected(
        val observedNodeCount: Int?,
        val failureReason: String
    ) : RuntimeInfrastructureVerification

    data class Unavailable(
        val failureReason: String
    ) : RuntimeInfrastructureVerification
}
