package tech.medo.systemnotification.markallsystemnotificationsread

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.systemnotification.markallsystemnotificationsread.MarkAllSystemNotificationsReadCommand



import tech.medo.systemnotification.systemnotification.SystemNotificationState



@Component
class MarkAllSystemNotificationsReadCommandHandler(
    private val decision: MarkAllSystemNotificationsReadDecision
) {
    @CommandHandler
    fun handle(
        command: MarkAllSystemNotificationsReadCommand,
        @InjectEntity(idProperty = "notificationId") state: SystemNotificationState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
