package tech.medo.runtimeprovisioning.deployruntimeagent

import java.util.UUID;

interface DeployRuntimeAgentService {
    fun supports(input: DeployRuntimeAgentInput): Boolean = true
    fun execute(input: DeployRuntimeAgentInput): DeployRuntimeAgentResult
}

data class DeployRuntimeAgentInput(
    val runtimeAgentId: UUID,
    val runtimeInfrastructureId: UUID
)

sealed interface DeployRuntimeAgentResult {
    data class Succeeded(
        val runtimeAgentId: UUID,
        val agentVersion: String
    ) : DeployRuntimeAgentResult

    data class Rejected(
        val failureReason: String
    ) : DeployRuntimeAgentResult

    data class Unavailable(
        val failureReason: String
    ) : DeployRuntimeAgentResult
}
