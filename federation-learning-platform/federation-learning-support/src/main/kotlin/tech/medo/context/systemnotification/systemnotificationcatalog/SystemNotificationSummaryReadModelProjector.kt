package tech.medo.systemnotification.systemnotificationcatalog

import tech.medo.systemnotification.events.SystemNotificationPublishedEvent
import tech.medo.systemnotification.events.SystemNotificationReadEvent
import tech.medo.systemnotification.events.AllSystemNotificationsReadEvent
import java.util.UUID
import tech.medo.systemnotification.domain.types.NotificationSeverity
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat
import tech.medo.systemnotification.domain.types.NotificationToastPolicy
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

interface SystemNotificationSummaryReadModelProjectionUpdater {
    fun update(
        event: SystemNotificationPublishedEvent,
        message: EventMessage
    )

    fun update(
        event: SystemNotificationReadEvent,
        message: EventMessage
    )

    fun update(
        event: AllSystemNotificationsReadEvent,
        message: EventMessage
    )
}

open class DefaultSystemNotificationSummaryReadModelProjectionUpdater(
    private val repository: SystemNotificationSummaryReadModelRepository
) : SystemNotificationSummaryReadModelProjectionUpdater {
    @Transactional
    open override fun update(
        event: SystemNotificationPublishedEvent,
        message: EventMessage
    ) {
        val entity = repository.findProjectionById(event.recipientUserId) ?: SystemNotificationSummaryReadModelProjection().apply {
            recipientUserId = event.recipientUserId
        }
        entity.unreadCount = (entity.unreadCount ?: 0L) + 1L
        entity.latestNotificationId = event.notificationId
        entity.latestSeverity = event.severity
        entity.latestPublishedAt = event.publishedAt
        entity.messageKey = event.messageKey
        entity.fallbackTitle = event.fallbackTitle
        entity.fallbackBody = event.fallbackBody
        entity.toastPolicy = event.toastPolicy
        entity.hasUnreadError = (entity.hasUnreadError == true) || event.severity == NotificationSeverity.ERROR
        repository.save(entity)
    }

    @Transactional
    open override fun update(
        event: SystemNotificationReadEvent,
        message: EventMessage
    ) {
        val entity = repository.findProjectionById(event.recipientUserId) ?: return
        entity.unreadCount = maxOf(0L, (entity.unreadCount ?: 0L) - 1L)
        if (entity.unreadCount == 0L) {
            entity.hasUnreadError = false
        }
        repository.save(entity)
    }

    @Transactional
    open override fun update(
        event: AllSystemNotificationsReadEvent,
        message: EventMessage
    ) {
        val entity = repository.findProjectionById(event.recipientUserId) ?: return
        entity.unreadCount = 0L
        entity.hasUnreadError = false
        repository.save(entity)
    }
}

@Configuration(proxyBeanMethods = false)
class SystemNotificationSummaryReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(SystemNotificationSummaryReadModelProjectionUpdater::class)
    fun defaultSystemNotificationSummaryReadModelProjectionUpdater(
        repository: SystemNotificationSummaryReadModelRepository
    ): SystemNotificationSummaryReadModelProjectionUpdater =
        DefaultSystemNotificationSummaryReadModelProjectionUpdater(repository)
}

@Namespace("readmodel-system-notification-summary")
@Component
class SystemNotificationSummaryReadModelProjector(
    private val updater: SystemNotificationSummaryReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: SystemNotificationPublishedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: SystemNotificationReadEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: AllSystemNotificationsReadEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
