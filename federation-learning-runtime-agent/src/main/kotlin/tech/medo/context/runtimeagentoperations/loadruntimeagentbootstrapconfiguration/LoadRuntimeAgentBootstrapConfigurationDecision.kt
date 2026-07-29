package tech.medo.runtimeagentoperations.loadruntimeagentbootstrapconfiguration

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.loadruntimeagentbootstrapconfiguration.LoadRuntimeAgentBootstrapConfigurationCommand
import tech.medo.runtimeagentoperations.loadruntimeagentbootstrapconfiguration.LoadRuntimeAgentBootstrapConfigurationResult
import tech.medo.runtimeagentoperations.events.RuntimeAgentBootstrapConfigurationLoadedEvent
import tech.medo.runtimeagentoperations.events.RuntimeAgentBootstrapConfigurationLoadFailedEvent
import tech.medo.runtimeagentoperations.runtimeagentlifecycle.RuntimeAgentLifecycleState





@Component
class LoadRuntimeAgentBootstrapConfigurationDecision {
    fun decide(command: LoadRuntimeAgentBootstrapConfigurationCommand, portResult: LoadRuntimeAgentBootstrapConfigurationResult, now: java.time.LocalDateTime): List<Any> {
        return when (portResult) {
                    is LoadRuntimeAgentBootstrapConfigurationResult.Succeeded -> listOf(RuntimeAgentBootstrapConfigurationLoadedEvent(runtimeAgentId = portResult.runtimeAgentId, runtimeInfrastructureId = portResult.runtimeInfrastructureId, agentVersion = portResult.agentVersion, bootstrapConfigurationLoaded = portResult.bootstrapConfigurationLoaded))
                    is LoadRuntimeAgentBootstrapConfigurationResult.Rejected -> listOf(RuntimeAgentBootstrapConfigurationLoadFailedEvent(bootstrapRequestId = portResult.bootstrapRequestId, failureReason = portResult.failureReason))
                    is LoadRuntimeAgentBootstrapConfigurationResult.Unavailable -> listOf(RuntimeAgentBootstrapConfigurationLoadFailedEvent(bootstrapRequestId = command.bootstrapRequestId, failureReason = portResult.failureReason))
                }
    }
}
