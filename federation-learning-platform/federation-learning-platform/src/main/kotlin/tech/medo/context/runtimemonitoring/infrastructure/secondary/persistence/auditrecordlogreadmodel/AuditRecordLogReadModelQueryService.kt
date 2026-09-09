package tech.medo.runtimemonitoring.infrastructure.secondary.persistence.auditrecordlogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Root
import org.hibernate.query.criteria.JpaExpression
import tech.jhipster.service.QueryService
import java.util.function.Function
import java.util.UUID;

import tech.medo.runtimemonitoring.auditrecordlog.AuditRecordLogReadModel
import tech.medo.runtimemonitoring.auditrecordlog.AuditRecordLogReadModelCriteria
import tech.medo.runtimemonitoring.auditrecordlog.AuditRecordLogReadModelProjection
import tech.medo.runtimemonitoring.auditrecordlog.toReadModel

@Service
class AuditRecordLogReadModelQueryService(
    private val repository: SpringDataAuditRecordLogReadModelRepository
) : QueryService<AuditRecordLogReadModelEntity>() {
    fun findByCriteria(criteria: AuditRecordLogReadModelCriteria?, pageable: Pageable): Page<AuditRecordLogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: AuditRecordLogReadModelCriteria?): Specification<AuditRecordLogReadModelEntity> {
        var specification = Specification.where<AuditRecordLogReadModelEntity>(null)
        if (criteria != null) {
            criteria.auditRecordId?.let { specification = specification.and(buildSpecification(it, Function<Root<AuditRecordLogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("auditRecordId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.sourceEventName?.let { specification = specification.and(buildSpecification(it, Function<Root<AuditRecordLogReadModelEntity>, Expression<String>> { root -> root.get("sourceEventName") })) }
            criteria.sourceEntityId?.let { specification = specification.and(buildSpecification(it, Function<Root<AuditRecordLogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("sourceEntityId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.severity?.let { specification = specification.and(buildSpecification(it, Function<Root<AuditRecordLogReadModelEntity>, Expression<String>> { root -> root.get("severity") })) }
            criteria.payloadHash?.let { specification = specification.and(buildSpecification(it, Function<Root<AuditRecordLogReadModelEntity>, Expression<String>> { root -> root.get("payloadHash") })) }
        }
        return specification
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
}
