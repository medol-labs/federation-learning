package tech.medo.runtimeagentoperations.configureruntimedatasetbinding

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.configureruntimedatasetbinding.ConfigureRuntimeDatasetBindingCommand




@Component
class ConfigureRuntimeDatasetBindingCommandHandler(
    private val decision: ConfigureRuntimeDatasetBindingDecision
) {
    @CommandHandler
    fun handle(
        command: ConfigureRuntimeDatasetBindingCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
