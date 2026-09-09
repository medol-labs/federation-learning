package tech.medo.runtimemonitoring.appendaudittrail

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimemonitoring.appendaudittrail.AppendAuditTrailCommand





@Component
class AppendAuditTrailCommandHandler(
    private val decision: AppendAuditTrailDecision
) {
    @CommandHandler
    fun handle(
        command: AppendAuditTrailCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
