package tech.medo.systemnotification.marksystemnotificationread

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.systemnotification.marksystemnotificationread.MarkSystemNotificationReadCommand



import tech.medo.systemnotification.systemnotification.SystemNotificationState



@Component
class MarkSystemNotificationReadCommandHandler(
    private val decision: MarkSystemNotificationReadDecision
) {
    @CommandHandler
    fun handle(
        command: MarkSystemNotificationReadCommand,
        @InjectEntity(idProperty = "notificationId") state: SystemNotificationState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
