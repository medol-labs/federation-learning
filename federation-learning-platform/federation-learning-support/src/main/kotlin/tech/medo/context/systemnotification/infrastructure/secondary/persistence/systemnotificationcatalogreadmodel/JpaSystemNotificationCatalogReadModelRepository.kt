package tech.medo.systemnotification.infrastructure.secondary.persistence.systemnotificationcatalogreadmodel

import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import tech.medo.systemnotification.domain.types.NotificationCategory;
import tech.medo.systemnotification.domain.types.NotificationSeverity;
import tech.medo.systemnotification.domain.states.SystemNotificationStateEnum;
import tech.medo.systemnotification.domain.types.NotificationToastPolicy;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.shared.application.access.ReadModelAccessPolicy
import tech.medo.shared.application.access.ReadModelAccessPolicyFilter
import tech.medo.systemnotification.systemnotificationcatalog.SystemNotificationCatalogReadModel
import tech.medo.systemnotification.systemnotificationcatalog.SystemNotificationCatalogReadModelCriteria
import tech.medo.systemnotification.systemnotificationcatalog.SystemNotificationCatalogReadModelProjection
import tech.medo.systemnotification.systemnotificationcatalog.SystemNotificationCatalogReadModelRepository
import tech.medo.systemnotification.systemnotificationcatalog.toReadModel

@Repository
class JpaSystemNotificationCatalogReadModelRepository(
    private val jpaRepository: SpringDataSystemNotificationCatalogReadModelRepository,
    private val accessPolicyFilter: ReadModelAccessPolicyFilter,
    private val queryService: SystemNotificationCatalogReadModelQueryService
) : SystemNotificationCatalogReadModelRepository {
    override fun findAllByFilter(recipientUserId: String?, recipientRole: String?, category: NotificationCategory?, severity: NotificationSeverity?, state: SystemNotificationStateEnum?, pageable: Pageable): Page<SystemNotificationCatalogReadModel> =
        jpaRepository.findAll(filters(recipientUserId, recipientRole, category, severity, state).and(accessSpecification()), pageable).map { it.toProjection().toReadModel() }

    override fun findAllByCriteria(criteria: SystemNotificationCatalogReadModelCriteria?, pageable: Pageable): Page<SystemNotificationCatalogReadModel> =
        jpaRepository.findAll(queryService.createSpecification(criteria).and(accessSpecification()), pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): SystemNotificationCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findByIdWithAccessPolicy(id: UUID): SystemNotificationCatalogReadModel? =
        jpaRepository.findOne(idSpecification(id).and(accessSpecification())).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): SystemNotificationCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: SystemNotificationCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun filters(recipientUserId: String?, recipientRole: String?, category: NotificationCategory?, severity: NotificationSeverity?, state: SystemNotificationStateEnum?): Specification<SystemNotificationCatalogReadModelEntity> =
        Specification { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()
            recipientUserId?.let { predicates.add(criteriaBuilder.equal(root.get<String>("recipientUserId"), it)) }
            recipientRole?.let { predicates.add(criteriaBuilder.equal(root.get<String>("recipientRole"), it)) }
            category?.let { predicates.add(criteriaBuilder.equal(root.get<NotificationCategory>("category"), it)) }
            severity?.let { predicates.add(criteriaBuilder.equal(root.get<NotificationSeverity>("severity"), it)) }
            state?.let { predicates.add(criteriaBuilder.equal(root.get<SystemNotificationStateEnum>("state"), it)) }
            criteriaBuilder.and(*predicates.toTypedArray())
        }


    private fun idSpecification(id: UUID): Specification<SystemNotificationCatalogReadModelEntity> =
        Specification { root, _, criteriaBuilder ->
            criteriaBuilder.equal(root.get<UUID>("notificationId"), id)
        }

    private fun accessSpecification(): Specification<SystemNotificationCatalogReadModelEntity> =
        accessPolicyFilter.specification(
            contextName = "SystemNotification",
            readModelName = "SystemNotificationCatalog",
            policy = ReadModelAccessPolicy(
            name = "SystemNotificationPublicAccess",
            fields = emptyList<String>()
        )
        )

    private fun SystemNotificationCatalogReadModelEntity.toProjection(): SystemNotificationCatalogReadModelProjection =
        SystemNotificationCatalogReadModelProjection().also {
            it.notificationId = this@toProjection.notificationId
            it.recipientUserId = this@toProjection.recipientUserId
            it.recipientRole = this@toProjection.recipientRole
            it.category = this@toProjection.category
            it.severity = this@toProjection.severity
            it.messageKey = this@toProjection.messageKey
            it.messageArgsJson = this@toProjection.messageArgsJson
            it.fallbackTitle = this@toProjection.fallbackTitle
            it.fallbackBody = this@toProjection.fallbackBody
            it.targetType = this@toProjection.targetType
            it.targetId = this@toProjection.targetId
            it.targetRoute = this@toProjection.targetRoute
            it.toastPolicy = this@toProjection.toastPolicy
            it.publishedAt = this@toProjection.publishedAt
            it.readAt = this@toProjection.readAt
            it.state = this@toProjection.state
            it.projectionUpdatedAt = this@toProjection.projectionUpdatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun SystemNotificationCatalogReadModelProjection.toEntity(): SystemNotificationCatalogReadModelEntity =
        SystemNotificationCatalogReadModelEntity().also {
            it.notificationId = this@toEntity.notificationId
            it.recipientUserId = this@toEntity.recipientUserId
            it.recipientRole = this@toEntity.recipientRole
            it.category = this@toEntity.category
            it.severity = this@toEntity.severity
            it.messageKey = this@toEntity.messageKey
            it.messageArgsJson = this@toEntity.messageArgsJson
            it.fallbackTitle = this@toEntity.fallbackTitle
            it.fallbackBody = this@toEntity.fallbackBody
            it.targetType = this@toEntity.targetType
            it.targetId = this@toEntity.targetId
            it.targetRoute = this@toEntity.targetRoute
            it.toastPolicy = this@toEntity.toastPolicy
            it.publishedAt = this@toEntity.publishedAt
            it.readAt = this@toEntity.readAt
            it.state = this@toEntity.state
            it.projectionUpdatedAt = this@toEntity.projectionUpdatedAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
