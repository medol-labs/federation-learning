package tech.medo.shared.application.metadata

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
@RequestMapping("/audit-trail")
class AuditTrailResource(private val repository: AuditLogRepository) {
    @GetMapping
    fun findAll(
        @RequestParam(required = false) correlationId: String?,
        @RequestParam(required = false) userId: String?,
        @RequestParam(required = false) sessionId: String?,
        @RequestParam(required = false) traceId: String?,
        @RequestParam(required = false) tenantId: String?,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<AuditLogEntry> =
        when {
            !correlationId.isNullOrBlank() -> repository.findAllByCorrelationId(correlationId, pageable)
            !userId.isNullOrBlank() -> repository.findAllByUserId(userId, pageable)
            !sessionId.isNullOrBlank() -> repository.findAllBySessionId(sessionId, pageable)
            !traceId.isNullOrBlank() -> repository.findAllByTraceId(traceId, pageable)
            !tenantId.isNullOrBlank() -> repository.findAllByTenantId(tenantId, pageable)
            else -> repository.findAll(pageable)
        }
}
