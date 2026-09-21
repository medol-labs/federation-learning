package tech.medo.runtimeagentoperations.reportruntimeagentstarted

import tech.medo.runtimeagentoperations.reportruntimeagentstarted.ReportRuntimeAgentStartedCommand


import tech.medo.runtimeagentoperations.events.RuntimeAgentStartedEvent
import tech.medo.runtimeagentoperations.runtimeagentlifecycle.RuntimeAgentLifecycleState


import tech.medo.runtimeagentoperations.domain.states.RuntimeAgentLifecycleStateEnum


interface ReportRuntimeAgentStartedDecision {
    fun decide(command: ReportRuntimeAgentStartedCommand, state: RuntimeAgentLifecycleState): List<Any> {
        require(state.currentState == RuntimeAgentLifecycleStateEnum.BOOTSTRAP_LOADED) {
            "ReportRuntimeAgentStarted requires RuntimeAgentLifecycle to be BootstrapLoaded."
        }
        return listOf(
            RuntimeAgentStartedEvent(runtimeAgentId = command.runtimeAgentId, runtimeInfrastructureId = command.runtimeInfrastructureId, agentVersion = command.agentVersion, runtimeAgentEndpoint = command.runtimeAgentEndpoint, endpointScope = command.endpointScope, bootstrapRequestId = command.bootstrapRequestId)
        )
    }
}
