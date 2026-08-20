package tech.medo.runtimeagentoperations.reportagentruntimenodeinventory

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.reportagentruntimenodeinventory.ReportAgentRuntimeNodeInventoryCommand





@Component
class ReportAgentRuntimeNodeInventoryCommandHandler(
    private val decision: ReportAgentRuntimeNodeInventoryDecision
) {
    @CommandHandler
    fun handle(
        command: ReportAgentRuntimeNodeInventoryCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
