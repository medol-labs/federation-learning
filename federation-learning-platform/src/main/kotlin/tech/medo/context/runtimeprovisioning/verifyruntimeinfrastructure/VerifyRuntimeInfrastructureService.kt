package tech.medo.runtimeprovisioning.verifyruntimeinfrastructure

import java.util.UUID;

interface VerifyRuntimeInfrastructureService {
    fun supports(input: RuntimeInfrastructureVerificationInput): Boolean = true
    fun verify(input: RuntimeInfrastructureVerificationInput): RuntimeInfrastructureVerification
}

data class RuntimeInfrastructureVerificationInput(
    val runtimeInfrastructureId: UUID,
    val runtimeAgentId: UUID,
    val agentInstallMode: String,
    val observedNodeCount: Int
)

sealed interface RuntimeInfrastructureVerification {
    class Succeeded : RuntimeInfrastructureVerification

    data class Rejected(
        val failureReason: String
    ) : RuntimeInfrastructureVerification

    data class Unavailable(
        val failureReason: String
    ) : RuntimeInfrastructureVerification
}
