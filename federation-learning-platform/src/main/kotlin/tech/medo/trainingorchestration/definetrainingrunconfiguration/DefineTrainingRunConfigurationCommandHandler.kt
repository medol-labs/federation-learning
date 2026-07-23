package tech.medo.trainingorchestration.definetrainingrunconfiguration

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.definetrainingrunconfiguration.DefineTrainingRunConfigurationCommand




@Component
class DefineTrainingRunConfigurationCommandHandler(
    private val decision: DefineTrainingRunConfigurationDecision
) {
    @CommandHandler
    fun handle(
        command: DefineTrainingRunConfigurationCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
