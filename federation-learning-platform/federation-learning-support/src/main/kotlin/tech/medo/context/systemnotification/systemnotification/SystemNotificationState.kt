package tech.medo.systemnotification.systemnotification

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.systemnotification.events.SystemNotificationPublishedEvent
import tech.medo.systemnotification.events.SystemNotificationReadEvent
import tech.medo.systemnotification.events.AllSystemNotificationsReadEvent
import tech.medo.systemnotification.domain.states.SystemNotificationStateEnum

import java.util.UUID;
import tech.medo.systemnotification.domain.types.NotificationCategory;
import tech.medo.systemnotification.domain.types.NotificationSeverity;
import tech.medo.systemnotification.domain.types.NotificationToastPolicy;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@EventSourced(idType = UUID::class, tagKey = SystemNotificationTags.NOTIFICATION_ID)
class SystemNotificationState @EntityCreator constructor() {

    var currentState: SystemNotificationStateEnum? = null
    var notificationId: UUID? = null
    var recipientUserId: String? = null
    var recipientRole: String? = null
    var category: NotificationCategory? = null
    var severity: NotificationSeverity? = null
    var messageKey: String? = null
    var messageArgsJson: String? = null
    var fallbackTitle: String? = null
    var fallbackBody: String? = null
    var targetType: String? = null
    var targetId: String? = null
    var targetRoute: String? = null
    var toastPolicy: NotificationToastPolicy? = null
    var publishedAt: LocalDateTime? = null
    var status: String? = null
    var readAt: LocalDateTime? = null

    @EventSourcingHandler
    fun evolve(event: SystemNotificationPublishedEvent): SystemNotificationState = apply {
        currentState = SystemNotificationStateEnum.Unread
        notificationId = event.notificationId
        recipientUserId = event.recipientUserId
        recipientRole = event.recipientRole
        category = event.category
        severity = event.severity
        messageKey = event.messageKey
        messageArgsJson = event.messageArgsJson
        fallbackTitle = event.fallbackTitle
        fallbackBody = event.fallbackBody
        targetType = event.targetType
        targetId = event.targetId
        targetRoute = event.targetRoute
        toastPolicy = event.toastPolicy
        publishedAt = event.publishedAt
        status = event.status
    }

    @EventSourcingHandler
    fun evolve(event: SystemNotificationReadEvent): SystemNotificationState = apply {
        currentState = SystemNotificationStateEnum.Read
        notificationId = event.notificationId
        recipientUserId = event.recipientUserId
        readAt = event.readAt
    }

    @EventSourcingHandler
    fun evolve(event: AllSystemNotificationsReadEvent): SystemNotificationState = apply {
        currentState = SystemNotificationStateEnum.Read
        recipientUserId = event.recipientUserId
        readAt = event.readAt
    }
}
