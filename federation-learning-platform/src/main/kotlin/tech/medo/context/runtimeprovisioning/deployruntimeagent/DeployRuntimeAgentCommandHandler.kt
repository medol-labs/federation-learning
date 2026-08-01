package tech.medo.runtimeprovisioning.deployruntimeagent

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentCommand
import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentInput
import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentService
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState



@Component
class DeployRuntimeAgentCommandHandler(
    private val decision: DeployRuntimeAgentDecision,
    private val deployRuntimeAgentService: DeployRuntimeAgentService
) {
    @CommandHandler
    fun handle(
        command: DeployRuntimeAgentCommand,
        @InjectEntity(idProperty = "runtimeInfrastructureId") state: RuntimeInfrastructureState,
        eventAppender: EventAppender
    ) {
        val input = DeployRuntimeAgentInput(runtimeAgentId = command.runtimeAgentId, runtimeInfrastructureId = command.runtimeInfrastructureId)
        val portResult = deployRuntimeAgentService.execute(input)
        val now = java.time.LocalDateTime.now()

        eventAppender.append(decision.decide(command, state, portResult, now))
    }
}
