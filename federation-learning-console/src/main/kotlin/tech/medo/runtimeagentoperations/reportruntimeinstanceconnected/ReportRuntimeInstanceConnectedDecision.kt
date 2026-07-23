package tech.medo.runtimeagentoperations.reportruntimeinstanceconnected

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.reportruntimeinstanceconnected.ReportRuntimeInstanceConnectedCommand

import tech.medo.runtimeagentoperations.events.AgentRuntimeConnectionEstablishedEvent
import tech.medo.runtimeagentoperations.agentruntimeinfrastructureconnection.AgentRuntimeInfrastructureConnectionState





@Component
class ReportRuntimeInstanceConnectedDecision {
    fun decide(command: ReportRuntimeInstanceConnectedCommand): List<Any> {
        return listOf(
            AgentRuntimeConnectionEstablishedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeAgentId = command.runtimeAgentId, runtimePlatformConnectionReady = command.runtimePlatformConnectionReady, platformApiReachable = command.platformApiReachable, agentAuthenticationSucceeded = command.agentAuthenticationSucceeded, controlChannelEstablished = command.controlChannelEstablished, heartbeatAccepted = command.heartbeatAccepted)
        )
    }
}
