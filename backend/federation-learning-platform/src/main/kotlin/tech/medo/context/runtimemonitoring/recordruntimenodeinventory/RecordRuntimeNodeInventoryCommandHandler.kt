package tech.medo.runtimemonitoring.recordruntimenodeinventory

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimemonitoring.recordruntimenodeinventory.RecordRuntimeNodeInventoryCommand





@Component
class RecordRuntimeNodeInventoryCommandHandler(
    private val decision: RecordRuntimeNodeInventoryDecision
) {
    @CommandHandler
    fun handle(
        command: RecordRuntimeNodeInventoryCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
