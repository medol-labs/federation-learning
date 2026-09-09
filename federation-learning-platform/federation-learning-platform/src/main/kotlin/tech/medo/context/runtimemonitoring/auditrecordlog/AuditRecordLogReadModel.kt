package tech.medo.runtimemonitoring.auditrecordlog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;

import tech.jhipster.service.filter.StringFilter


class AuditRecordLogReadModelQuery

class AuditRecordLogReadModelCriteria {
    var auditRecordId: StringFilter? = null
    var sourceEventName: StringFilter? = null
    var sourceEntityId: StringFilter? = null
    var severity: StringFilter? = null
    var payloadHash: StringFilter? = null
}


class AuditRecordLogReadModelProjection : MetadataProjection {
    var auditRecordId: UUID? = null
    var sourceEventName: String? = null
    var sourceEntityId: UUID? = null
    var severity: String? = null
    var payloadHash: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun AuditRecordLogReadModelProjection.toReadModel(): AuditRecordLogReadModel =
    AuditRecordLogReadModel(
    auditRecordId = auditRecordId,
    sourceEventName = sourceEventName,
    sourceEntityId = sourceEntityId,
    severity = severity,
    payloadHash = payloadHash,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface AuditRecordLogReadModelRepository {
    fun findAll(pageable: Pageable): Page<AuditRecordLogReadModel>
    fun findAllByCriteria(criteria: AuditRecordLogReadModelCriteria?, pageable: Pageable): Page<AuditRecordLogReadModel>
    fun findById(id: UUID): AuditRecordLogReadModel?
    fun findProjectionById(id: UUID): AuditRecordLogReadModelProjection?
    fun save(projection: AuditRecordLogReadModelProjection)
}

data class AuditRecordLogReadModel(
    val auditRecordId: UUID?,
    val sourceEventName: String?,
    val sourceEntityId: UUID?,
    val severity: String?,
    val payloadHash: String?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
