package tech.medo.runtimeagentoperations.reportruntimeinstanceconnected

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.reportruntimeinstanceconnected.ReportRuntimeInstanceConnectedCommand
import tech.medo.runtimeagentoperations.reportruntimeinstanceconnected.ReportRuntimeInstanceConnectedResult
import tech.medo.runtimeagentoperations.events.AgentRuntimeConnectionReportFailedEvent
import tech.medo.runtimeagentoperations.events.AgentRuntimeConnectionEstablishedEvent
import tech.medo.runtimeagentoperations.agentruntimeinfrastructureconnection.AgentRuntimeInfrastructureConnectionState





@Component
class ReportRuntimeInstanceConnectedDecision {
    fun decide(command: ReportRuntimeInstanceConnectedCommand, portResult: ReportRuntimeInstanceConnectedResult, now: java.time.LocalDateTime): List<Any> {
        return when (portResult) {
                    is ReportRuntimeInstanceConnectedResult.Succeeded -> listOf(AgentRuntimeConnectionEstablishedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeAgentId = command.runtimeAgentId, runtimePlatformConnectionReady = command.runtimePlatformConnectionReady, platformApiReachable = command.platformApiReachable, agentAuthenticationSucceeded = command.agentAuthenticationSucceeded, controlChannelEstablished = command.controlChannelEstablished, heartbeatAccepted = command.heartbeatAccepted))
                    is ReportRuntimeInstanceConnectedResult.Rejected -> listOf(AgentRuntimeConnectionReportFailedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeAgentId = command.runtimeAgentId, runtimePlatformConnectionReady = command.runtimePlatformConnectionReady, platformApiReachable = command.platformApiReachable, agentAuthenticationSucceeded = command.agentAuthenticationSucceeded, controlChannelEstablished = command.controlChannelEstablished, heartbeatAccepted = command.heartbeatAccepted, failureReason = portResult.failureReason, retryable = portResult.retryable))
                    is ReportRuntimeInstanceConnectedResult.Unavailable -> listOf(AgentRuntimeConnectionReportFailedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeAgentId = command.runtimeAgentId, runtimePlatformConnectionReady = command.runtimePlatformConnectionReady, platformApiReachable = command.platformApiReachable, agentAuthenticationSucceeded = command.agentAuthenticationSucceeded, controlChannelEstablished = command.controlChannelEstablished, heartbeatAccepted = command.heartbeatAccepted, failureReason = portResult.failureReason, retryable = null /* TODO: provide retryable */))
                }
    }
}
