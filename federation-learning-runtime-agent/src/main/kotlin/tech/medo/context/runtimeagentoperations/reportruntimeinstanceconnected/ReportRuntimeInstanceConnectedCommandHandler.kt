package tech.medo.runtimeagentoperations.reportruntimeinstanceconnected

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.reportruntimeinstanceconnected.ReportRuntimeInstanceConnectedCommand
import tech.medo.runtimeagentoperations.reportruntimeinstanceconnected.ReportRuntimeInstanceConnectedInput
import tech.medo.runtimeagentoperations.reportruntimeinstanceconnected.ReportRuntimeInstanceConnectedService



@Component
class ReportRuntimeInstanceConnectedCommandHandler(
    private val decision: ReportRuntimeInstanceConnectedDecision,
    private val reportRuntimeInstanceConnectedService: ReportRuntimeInstanceConnectedService
) {
    @CommandHandler
    fun handle(
        command: ReportRuntimeInstanceConnectedCommand,
        eventAppender: EventAppender
    ) {
        val input = ReportRuntimeInstanceConnectedInput(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeAgentId = command.runtimeAgentId, runtimeAgentEndpoint = command.runtimeAgentEndpoint, endpointScope = command.endpointScope, runtimePlatformConnectionReady = command.runtimePlatformConnectionReady, platformApiReachable = command.platformApiReachable, agentAuthenticationSucceeded = command.agentAuthenticationSucceeded, controlChannelEstablished = command.controlChannelEstablished, heartbeatAccepted = command.heartbeatAccepted)
        val portResult = reportRuntimeInstanceConnectedService.execute(input)
        val now = java.time.LocalDateTime.now()

        eventAppender.append(decision.decide(command, portResult, now))
    }
}
