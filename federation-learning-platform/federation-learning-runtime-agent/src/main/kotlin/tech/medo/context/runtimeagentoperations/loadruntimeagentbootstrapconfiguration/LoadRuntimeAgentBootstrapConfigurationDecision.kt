package tech.medo.runtimeagentoperations.loadruntimeagentbootstrapconfiguration

import tech.medo.runtimeagentoperations.loadruntimeagentbootstrapconfiguration.LoadRuntimeAgentBootstrapConfigurationCommand

import tech.medo.runtimeagentoperations.loadruntimeagentbootstrapconfiguration.LoadRuntimeAgentBootstrapConfigurationResult
import tech.medo.runtimeagentoperations.events.RuntimeAgentBootstrapConfigurationLoadedEvent
import tech.medo.runtimeagentoperations.events.RuntimeAgentBootstrapConfigurationLoadFailedEvent
import tech.medo.runtimeagentoperations.runtimeagentlifecycle.RuntimeAgentLifecycleState





interface LoadRuntimeAgentBootstrapConfigurationDecision {
    fun decide(command: LoadRuntimeAgentBootstrapConfigurationCommand, portResult: LoadRuntimeAgentBootstrapConfigurationResult, now: java.time.LocalDateTime): List<Any> {
        return when (portResult) {
                    is LoadRuntimeAgentBootstrapConfigurationResult.Succeeded -> listOf(
            RuntimeAgentBootstrapConfigurationLoadedEvent(runtimeAgentId = portResult.runtimeAgentId, runtimeInfrastructureId = portResult.runtimeInfrastructureId, agentVersion = portResult.agentVersion, runtimeAgentEndpoint = portResult.runtimeAgentEndpoint, endpointScope = portResult.endpointScope, bootstrapConfigurationLoaded = portResult.bootstrapConfigurationLoaded, bootstrapRequestId = command.bootstrapRequestId)
            )
                    is LoadRuntimeAgentBootstrapConfigurationResult.Rejected -> listOf(RuntimeAgentBootstrapConfigurationLoadFailedEvent(bootstrapRequestId = command.bootstrapRequestId, failureReason = portResult.failureReason))
                    is LoadRuntimeAgentBootstrapConfigurationResult.Unavailable -> listOf(RuntimeAgentBootstrapConfigurationLoadFailedEvent(bootstrapRequestId = command.bootstrapRequestId, failureReason = portResult.failureReason))
                }
    }
}
