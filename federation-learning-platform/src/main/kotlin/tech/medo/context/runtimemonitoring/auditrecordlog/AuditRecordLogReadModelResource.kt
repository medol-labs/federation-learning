package tech.medo.runtimemonitoring.auditrecordlog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID;


@CrossOrigin
@RestController
@RequestMapping("/auditrecord/auditrecordlog")
class AuditRecordLogReadModelResource(private val repository: AuditRecordLogReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('audit_record_log:list') or hasAuthority('audit_record_log:read')")
    @GetMapping
    fun findAll(
        criteria: AuditRecordLogReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<AuditRecordLogReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('audit_record_log:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<AuditRecordLogReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
