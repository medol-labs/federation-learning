package tech.medo.runtimeprovisioning.retryruntimeagentdeployment

import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.retryruntimeagentdeployment.RetryRuntimeAgentDeploymentCommand
import tech.medo.runtimeprovisioning.retryruntimeagentdeployment.RetryRuntimeAgentDeploymentResult
import tech.medo.runtimeprovisioning.events.RuntimeAgentDeploymentRetrySucceededEvent
import tech.medo.runtimeprovisioning.events.RuntimeAgentDeploymentRetryFailedEvent
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState





@Component
class RetryRuntimeAgentDeploymentDecision {
    fun decide(command: RetryRuntimeAgentDeploymentCommand, state: RuntimeInfrastructureState, portResult: RetryRuntimeAgentDeploymentResult, now: java.time.LocalDateTime): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return when (portResult) {
                    is RetryRuntimeAgentDeploymentResult.Succeeded -> listOf(RuntimeAgentDeploymentRetrySucceededEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeAgentId = portResult.runtimeAgentId, agentVersion = portResult.agentVersion))
                    is RetryRuntimeAgentDeploymentResult.Rejected -> listOf(RuntimeAgentDeploymentRetryFailedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, failureReason = portResult.failureReason))
                    is RetryRuntimeAgentDeploymentResult.Unavailable -> listOf(RuntimeAgentDeploymentRetryFailedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, failureReason = portResult.failureReason))
                }
    }
}
