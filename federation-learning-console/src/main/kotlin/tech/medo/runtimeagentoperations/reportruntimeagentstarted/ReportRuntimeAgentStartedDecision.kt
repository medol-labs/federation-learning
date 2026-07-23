package tech.medo.runtimeagentoperations.reportruntimeagentstarted

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.reportruntimeagentstarted.ReportRuntimeAgentStartedCommand

import tech.medo.runtimeagentoperations.events.RuntimeAgentStartedEvent
import tech.medo.runtimeagentoperations.runtimeagentlifecycle.RuntimeAgentLifecycleState





@Component
class ReportRuntimeAgentStartedDecision {
    fun decide(command: ReportRuntimeAgentStartedCommand): List<Any> {
        return listOf(
            RuntimeAgentStartedEvent(runtimeAgentId = command.runtimeAgentId, runtimeInfrastructureId = command.runtimeInfrastructureId, agentVersion = command.agentVersion)
        )
    }
}
