package tech.medo.runtimeprovisioning.retryruntimeagentdeployment

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.retryruntimeagentdeployment.RetryRuntimeAgentDeploymentCommand
import tech.medo.runtimeprovisioning.retryruntimeagentdeployment.RetryRuntimeAgentDeploymentInput
import tech.medo.runtimeprovisioning.retryruntimeagentdeployment.RetryRuntimeAgentDeploymentService
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState



@Component
class RetryRuntimeAgentDeploymentCommandHandler(
    private val decision: RetryRuntimeAgentDeploymentDecision,
    private val retryRuntimeAgentDeploymentService: RetryRuntimeAgentDeploymentService
) {
    @CommandHandler
    fun handle(
        command: RetryRuntimeAgentDeploymentCommand,
        @InjectEntity(idProperty = "runtimeInfrastructureId") state: RuntimeInfrastructureState,
        eventAppender: EventAppender
    ) {
        val input = RetryRuntimeAgentDeploymentInput(runtimeAgentId = command.runtimeAgentId, runtimeInfrastructureId = command.runtimeInfrastructureId, currentRuntimeInfrastructureState = command.currentRuntimeInfrastructureState, retryReason = command.retryReason)
        val portResult = retryRuntimeAgentDeploymentService.execute(input)
        val now = java.time.LocalDateTime.now()

        eventAppender.append(decision.decide(command, state, portResult, now))
    }
}
