package tech.medo.runtimeagentoperations.reportruntimeagentstarted

import tech.medo.runtimeagentoperations.reportruntimeagentstarted.ReportRuntimeAgentStartedCommand


import tech.medo.runtimeagentoperations.events.RuntimeAgentStartedEvent
import tech.medo.runtimeagentoperations.runtimeagentlifecycle.RuntimeAgentLifecycleState


import tech.medo.runtimeagentoperations.domain.states.RuntimeAgentLifecycleStateEnum


interface ReportRuntimeAgentStartedDecision {
    fun decide(command: ReportRuntimeAgentStartedCommand, state: RuntimeAgentLifecycleState): List<Any> {
        if (state.currentState != RuntimeAgentLifecycleStateEnum.BootstrapLoaded) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.runtimeagentoperations.reportRuntimeAgentStarted.requiresState",
                args = mapOf(
                    "command" to "ReportRuntimeAgentStarted",
                    "aggregate" to "RuntimeAgentLifecycle",
                    "expectedState" to "BootstrapLoaded",
                    "actualState" to state.currentState.toString()
                ),
                message = "ReportRuntimeAgentStarted requires RuntimeAgentLifecycle to be BootstrapLoaded."
            )
        }
        return listOf(
            RuntimeAgentStartedEvent(runtimeAgentId = command.runtimeAgentId, runtimeInfrastructureId = command.runtimeInfrastructureId, agentVersion = command.agentVersion, runtimeAgentEndpoint = command.runtimeAgentEndpoint, endpointScope = command.endpointScope, bootstrapRequestId = command.bootstrapRequestId)
        )
    }
}
