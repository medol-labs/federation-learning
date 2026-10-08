package tech.medo.systemnotification.infrastructure.secondary.persistence.systemnotificationsummaryreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import tech.medo.systemnotification.domain.types.NotificationSeverity;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import tech.medo.systemnotification.domain.types.NotificationToastPolicy;

import tech.medo.systemnotification.systemnotificationcatalog.SystemNotificationSummaryReadModel
import tech.medo.systemnotification.systemnotificationcatalog.SystemNotificationSummaryReadModelCriteria
import tech.medo.systemnotification.systemnotificationcatalog.SystemNotificationSummaryReadModelProjection
import tech.medo.systemnotification.systemnotificationcatalog.SystemNotificationSummaryReadModelRepository
import tech.medo.systemnotification.systemnotificationcatalog.toReadModel

@Repository
class JpaSystemNotificationSummaryReadModelRepository(
    private val jpaRepository: SpringDataSystemNotificationSummaryReadModelRepository,
    private val queryService: SystemNotificationSummaryReadModelQueryService
) : SystemNotificationSummaryReadModelRepository {
    override fun findAll(pageable: Pageable): Page<SystemNotificationSummaryReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: SystemNotificationSummaryReadModelCriteria?, pageable: Pageable): Page<SystemNotificationSummaryReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: String): SystemNotificationSummaryReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: String): SystemNotificationSummaryReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: SystemNotificationSummaryReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun SystemNotificationSummaryReadModelEntity.toProjection(): SystemNotificationSummaryReadModelProjection =
        SystemNotificationSummaryReadModelProjection().also {
            it.recipientUserId = this@toProjection.recipientUserId
            it.unreadCount = this@toProjection.unreadCount
            it.latestNotificationId = this@toProjection.latestNotificationId
            it.latestSeverity = this@toProjection.latestSeverity
            it.latestPublishedAt = this@toProjection.latestPublishedAt
            it.messageKey = this@toProjection.messageKey
            it.fallbackTitle = this@toProjection.fallbackTitle
            it.fallbackBody = this@toProjection.fallbackBody
            it.toastPolicy = this@toProjection.toastPolicy
            it.hasUnreadError = this@toProjection.hasUnreadError
            it.projectionUpdatedAt = this@toProjection.projectionUpdatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun SystemNotificationSummaryReadModelProjection.toEntity(): SystemNotificationSummaryReadModelEntity =
        SystemNotificationSummaryReadModelEntity().also {
            it.recipientUserId = this@toEntity.recipientUserId
            it.unreadCount = this@toEntity.unreadCount
            it.latestNotificationId = this@toEntity.latestNotificationId
            it.latestSeverity = this@toEntity.latestSeverity
            it.latestPublishedAt = this@toEntity.latestPublishedAt
            it.messageKey = this@toEntity.messageKey
            it.fallbackTitle = this@toEntity.fallbackTitle
            it.fallbackBody = this@toEntity.fallbackBody
            it.toastPolicy = this@toEntity.toastPolicy
            it.hasUnreadError = this@toEntity.hasUnreadError
            it.projectionUpdatedAt = this@toEntity.projectionUpdatedAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
