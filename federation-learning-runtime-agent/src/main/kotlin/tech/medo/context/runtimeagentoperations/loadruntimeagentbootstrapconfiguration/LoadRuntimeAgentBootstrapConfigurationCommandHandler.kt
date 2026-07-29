package tech.medo.runtimeagentoperations.loadruntimeagentbootstrapconfiguration

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.loadruntimeagentbootstrapconfiguration.LoadRuntimeAgentBootstrapConfigurationCommand
import tech.medo.runtimeagentoperations.loadruntimeagentbootstrapconfiguration.LoadRuntimeAgentBootstrapConfigurationInput
import tech.medo.runtimeagentoperations.loadruntimeagentbootstrapconfiguration.LoadRuntimeAgentBootstrapConfigurationService



@Component
class LoadRuntimeAgentBootstrapConfigurationCommandHandler(
    private val decision: LoadRuntimeAgentBootstrapConfigurationDecision,
    private val loadRuntimeAgentBootstrapConfigurationService: LoadRuntimeAgentBootstrapConfigurationService
) {
    @CommandHandler
    fun handle(
        command: LoadRuntimeAgentBootstrapConfigurationCommand,
        eventAppender: EventAppender
    ) {
        val input = LoadRuntimeAgentBootstrapConfigurationInput(bootstrapRequestId = command.bootstrapRequestId)
        val portResult = loadRuntimeAgentBootstrapConfigurationService.execute(input)
        val now = java.time.LocalDateTime.now()
        eventAppender.append(decision.decide(command, portResult, now))
    }
}
