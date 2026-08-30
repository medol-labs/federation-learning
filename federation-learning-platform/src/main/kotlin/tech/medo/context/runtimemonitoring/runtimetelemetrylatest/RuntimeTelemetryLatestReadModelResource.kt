package tech.medo.runtimemonitoring.runtimetelemetrylatest

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
@RequestMapping("/noderuntimehealth/runtimetelemetrylatest")
class RuntimeTelemetryLatestReadModelResource(private val repository: RuntimeTelemetryLatestReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('runtime_telemetry_latest:list') or hasAuthority('runtime_telemetry_latest:read')")
    @GetMapping
    fun findAll(
        criteria: RuntimeTelemetryLatestReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<RuntimeTelemetryLatestReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('runtime_telemetry_latest:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<RuntimeTelemetryLatestReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
