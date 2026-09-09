package tech.medo.shared.application.metadata

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface AuditLogRepository : JpaRepository<AuditLogEntry, Long> {
    fun findAllByCorrelationId(correlationId: String, pageable: Pageable): Page<AuditLogEntry>
    fun findAllByUserId(userId: String, pageable: Pageable): Page<AuditLogEntry>
    fun findAllBySessionId(sessionId: String, pageable: Pageable): Page<AuditLogEntry>
    fun findAllByTraceId(traceId: String, pageable: Pageable): Page<AuditLogEntry>
    fun findAllByTenantId(tenantId: String, pageable: Pageable): Page<AuditLogEntry>
}
