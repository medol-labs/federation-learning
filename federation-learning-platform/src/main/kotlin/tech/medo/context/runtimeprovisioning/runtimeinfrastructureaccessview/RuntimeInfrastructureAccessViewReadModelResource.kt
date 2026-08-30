package tech.medo.runtimeprovisioning.runtimeinfrastructureaccessview

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
@RequestMapping("/runtimeinfrastructure/runtimeinfrastructureaccessview")
class RuntimeInfrastructureAccessViewReadModelResource(private val repository: RuntimeInfrastructureAccessViewReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('runtime_infrastructure_access_view:list') or hasAuthority('runtime_infrastructure_access_view:read')")
    @GetMapping
    fun findAll(
        criteria: RuntimeInfrastructureAccessViewReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<RuntimeInfrastructureAccessViewReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('runtime_infrastructure_access_view:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<RuntimeInfrastructureAccessViewReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
