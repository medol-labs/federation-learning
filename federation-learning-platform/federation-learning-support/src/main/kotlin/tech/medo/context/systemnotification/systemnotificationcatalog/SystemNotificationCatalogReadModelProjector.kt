package tech.medo.systemnotification.systemnotificationcatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata


import tech.medo.systemnotification.events.SystemNotificationPublishedEvent
import tech.medo.systemnotification.events.SystemNotificationReadEvent
import tech.medo.systemnotification.events.AllSystemNotificationsReadEvent
import tech.medo.systemnotification.domain.states.SystemNotificationStateEnum


interface SystemNotificationCatalogReadModelProjectionUpdater {
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

open class DefaultSystemNotificationCatalogReadModelProjectionUpdater(
    private val repository: SystemNotificationCatalogReadModelRepository
) : SystemNotificationCatalogReadModelProjectionUpdater {
    @Transactional
    open override fun update(
        event: SystemNotificationPublishedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.notificationId) ?: SystemNotificationCatalogReadModelProjection().apply {
                this.notificationId = event.notificationId
        }
            entity.notificationId = event.notificationId
            entity.recipientUserId = event.recipientUserId
            entity.recipientRole = event.recipientRole
            entity.category = event.category
            entity.severity = event.severity
            entity.messageKey = event.messageKey
            entity.messageArgsJson = event.messageArgsJson
            entity.fallbackTitle = event.fallbackTitle
            entity.fallbackBody = event.fallbackBody
            entity.targetType = event.targetType
            entity.targetId = event.targetId
            entity.targetRoute = event.targetRoute
            entity.toastPolicy = event.toastPolicy
            entity.publishedAt = event.publishedAt
            entity.state = SystemNotificationStateEnum.Unread
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: SystemNotificationReadEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.notificationId) ?: SystemNotificationCatalogReadModelProjection().apply {
                this.notificationId = event.notificationId
        }
            entity.notificationId = event.notificationId
            entity.recipientUserId = event.recipientUserId
            entity.readAt = event.readAt
            entity.state = SystemNotificationStateEnum.Read
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    open override fun update(
        event: AllSystemNotificationsReadEvent,
        message: EventMessage
    ) {
        // Skipped: AllSystemNotificationsReadEvent does not provide enough key fields to locate SystemNotificationCatalogReadModelProjection.
    }

}

@Configuration(proxyBeanMethods = false)
class SystemNotificationCatalogReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(SystemNotificationCatalogReadModelProjectionUpdater::class)
    fun defaultSystemNotificationCatalogReadModelProjectionUpdater(
        repository: SystemNotificationCatalogReadModelRepository
    ): SystemNotificationCatalogReadModelProjectionUpdater =
        DefaultSystemNotificationCatalogReadModelProjectionUpdater(repository)
}

@Namespace("readmodel-system-notification-catalog")
@Component
class SystemNotificationCatalogReadModelProjector(
    private val updater: SystemNotificationCatalogReadModelProjectionUpdater
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
