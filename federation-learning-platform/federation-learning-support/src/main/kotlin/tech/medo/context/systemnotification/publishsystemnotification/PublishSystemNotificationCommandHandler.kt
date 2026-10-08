package tech.medo.systemnotification.publishsystemnotification

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.systemnotification.publishsystemnotification.PublishSystemNotificationCommand







@Component
class PublishSystemNotificationCommandHandler(
    private val decision: PublishSystemNotificationDecision
) {
    @CommandHandler
    fun handle(
        command: PublishSystemNotificationCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
