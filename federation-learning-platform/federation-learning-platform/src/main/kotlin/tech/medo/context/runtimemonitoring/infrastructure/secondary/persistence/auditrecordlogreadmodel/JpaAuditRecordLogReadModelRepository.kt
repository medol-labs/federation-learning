package tech.medo.runtimemonitoring.infrastructure.secondary.persistence.auditrecordlogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;

import tech.medo.runtimemonitoring.auditrecordlog.AuditRecordLogReadModel
import tech.medo.runtimemonitoring.auditrecordlog.AuditRecordLogReadModelCriteria
import tech.medo.runtimemonitoring.auditrecordlog.AuditRecordLogReadModelProjection
import tech.medo.runtimemonitoring.auditrecordlog.AuditRecordLogReadModelRepository
import tech.medo.runtimemonitoring.auditrecordlog.toReadModel

@Repository
class JpaAuditRecordLogReadModelRepository(
    private val jpaRepository: SpringDataAuditRecordLogReadModelRepository,
    private val queryService: AuditRecordLogReadModelQueryService
) : AuditRecordLogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<AuditRecordLogReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: AuditRecordLogReadModelCriteria?, pageable: Pageable): Page<AuditRecordLogReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): AuditRecordLogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): AuditRecordLogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: AuditRecordLogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun AuditRecordLogReadModelEntity.toProjection(): AuditRecordLogReadModelProjection =
        AuditRecordLogReadModelProjection().also {
            it.auditRecordId = this@toProjection.auditRecordId
            it.sourceEventName = this@toProjection.sourceEventName
            it.sourceEntityId = this@toProjection.sourceEntityId
            it.severity = this@toProjection.severity
            it.payloadHash = this@toProjection.payloadHash
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun AuditRecordLogReadModelProjection.toEntity(): AuditRecordLogReadModelEntity =
        AuditRecordLogReadModelEntity().also {
            it.auditRecordId = this@toEntity.auditRecordId
            it.sourceEventName = this@toEntity.sourceEventName
            it.sourceEntityId = this@toEntity.sourceEntityId
            it.severity = this@toEntity.severity
            it.payloadHash = this@toEntity.payloadHash
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
