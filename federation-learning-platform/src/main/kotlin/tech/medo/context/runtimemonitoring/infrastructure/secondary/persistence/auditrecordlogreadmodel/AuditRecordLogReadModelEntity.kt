package tech.medo.runtimemonitoring.infrastructure.secondary.persistence.auditrecordlogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;


@Entity
@Table(name = "audit_record_log")
class AuditRecordLogReadModelEntity : MetadataProjection {
    @Id
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
