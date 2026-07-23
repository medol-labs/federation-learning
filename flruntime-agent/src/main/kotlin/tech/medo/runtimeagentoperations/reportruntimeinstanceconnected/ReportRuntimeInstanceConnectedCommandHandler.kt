package tech.medo.runtimeagentoperations.reportruntimeinstanceconnected

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.reportruntimeinstanceconnected.ReportRuntimeInstanceConnectedCommand




@Component
class ReportRuntimeInstanceConnectedCommandHandler(
    private val decision: ReportRuntimeInstanceConnectedDecision
) {
    @CommandHandler
    fun handle(
        command: ReportRuntimeInstanceConnectedCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
