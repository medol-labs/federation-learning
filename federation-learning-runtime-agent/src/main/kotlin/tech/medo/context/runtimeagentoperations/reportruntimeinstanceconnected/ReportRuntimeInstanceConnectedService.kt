package tech.medo.runtimeagentoperations.reportruntimeinstanceconnected

import java.util.UUID;

interface ReportRuntimeInstanceConnectedService {
    fun supports(input: ReportRuntimeInstanceConnectedInput): Boolean = true
    fun execute(input: ReportRuntimeInstanceConnectedInput): ReportRuntimeInstanceConnectedResult
}

data class ReportRuntimeInstanceConnectedInput(
    val runtimeInfrastructureId: UUID,
    val runtimeAgentId: UUID,
    val runtimePlatformConnectionReady: Boolean,
    val platformApiReachable: Boolean,
    val agentAuthenticationSucceeded: Boolean,
    val controlChannelEstablished: Boolean,
    val heartbeatAccepted: Boolean
)

sealed interface ReportRuntimeInstanceConnectedResult {
    class Succeeded : ReportRuntimeInstanceConnectedResult

    data class Rejected(
        val failureReason: String,
        val retryable: Boolean?
    ) : ReportRuntimeInstanceConnectedResult

    data class Unavailable(
        val failureReason: String
    ) : ReportRuntimeInstanceConnectedResult
}
