package tech.medo.runtimeprovisioning.deployruntimeagent

import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentCommand
import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentResult
import tech.medo.runtimeprovisioning.events.RuntimeAgentInstallationSucceededEvent
import tech.medo.runtimeprovisioning.events.RuntimeAgentInstallationFailedEvent
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState


import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum


interface DeployRuntimeAgentDecision {
    fun decide(command: DeployRuntimeAgentCommand, state: RuntimeInfrastructureState, portResult: DeployRuntimeAgentResult, now: java.time.LocalDateTime): List<Any> {
        if (state.currentState in setOf(
                RuntimeInfrastructureStateEnum.AGENT_READY,
                RuntimeInfrastructureStateEnum.CONNECTED,
                RuntimeInfrastructureStateEnum.RUNTIME_AGENT_FAILED
            )
        ) {
            return emptyList()
        }

        require(state.currentState == RuntimeInfrastructureStateEnum.VERIFIED) {
            "DeployRuntimeAgent requires RuntimeInfrastructure to be Verified."
        }
        return when (portResult) {
                    is DeployRuntimeAgentResult.Succeeded -> listOf(RuntimeAgentInstallationSucceededEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeAgentId = command.runtimeAgentId, agentVersion = portResult.agentVersion))
                    is DeployRuntimeAgentResult.Rejected -> listOf(RuntimeAgentInstallationFailedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, failureReason = portResult.failureReason))
                    is DeployRuntimeAgentResult.Unavailable -> listOf(RuntimeAgentInstallationFailedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, failureReason = portResult.failureReason))
                }
    }
}
