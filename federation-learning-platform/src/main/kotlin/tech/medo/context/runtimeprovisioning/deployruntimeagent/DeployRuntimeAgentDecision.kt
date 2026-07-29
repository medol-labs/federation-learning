package tech.medo.runtimeprovisioning.deployruntimeagent

import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentCommand
import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentResult
import tech.medo.runtimeprovisioning.events.RuntimeAgentInstallationSucceededEvent
import tech.medo.runtimeprovisioning.events.RuntimeAgentInstallationFailedEvent
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState


import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum


@Component
class DeployRuntimeAgentDecision {
    fun decide(command: DeployRuntimeAgentCommand, state: RuntimeInfrastructureState, portResult: DeployRuntimeAgentResult, now: java.time.LocalDateTime): List<Any> {
        require(state.currentState == RuntimeInfrastructureStateEnum.VERIFIED) {
            "DeployRuntimeAgent requires RuntimeInfrastructure to be Verified."
        }
        return when (portResult) {
                    is DeployRuntimeAgentResult.Succeeded -> listOf(RuntimeAgentInstallationSucceededEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeAgentId = portResult.runtimeAgentId, agentVersion = portResult.agentVersion))
                    is DeployRuntimeAgentResult.Rejected -> listOf(RuntimeAgentInstallationFailedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, failureReason = "Deploy Runtime Agent rejected."))
                    is DeployRuntimeAgentResult.Unavailable -> listOf(RuntimeAgentInstallationFailedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, failureReason = portResult.failureReason))
                }
    }
}
