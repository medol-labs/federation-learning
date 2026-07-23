package tech.medo.runtimeagentoperations.reportruntimeinstanceconnected

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeagentoperations.agentruntimeinfrastructureconnection.AgentRuntimeInfrastructureConnectionSelection
import java.util.UUID;


@Command
data class ReportRuntimeInstanceConnectedCommand(
    val runtimeInfrastructureId: UUID,
    val runtimeAgentId: UUID,
    val runtimePlatformConnectionReady: Boolean,
    val platformApiReachable: Boolean,
    val agentAuthenticationSucceeded: Boolean,
    val controlChannelEstablished: Boolean,
    val heartbeatAccepted: Boolean
) {
    @TargetEntityId
    val selection: AgentRuntimeInfrastructureConnectionSelection = AgentRuntimeInfrastructureConnectionSelection(runtimeInfrastructureId = runtimeInfrastructureId)

}
