package tech.medo.systemnotification.recipientnotificationacknowledgement

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.systemnotification.events.AllSystemNotificationsReadEvent

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@EventSourced(idType = String::class, tagKey = RecipientNotificationAcknowledgementTags.RECIPIENT_USER_ID)
class RecipientNotificationAcknowledgementState @EntityCreator constructor() {

    var recipientUserId: String? = null
    var readAt: LocalDateTime? = null

    @EventSourcingHandler
    fun evolve(event: AllSystemNotificationsReadEvent): RecipientNotificationAcknowledgementState = apply {
        recipientUserId = event.recipientUserId
        readAt = event.readAt
    }
}
