package tech.medo.runtimeprovisioning.runtimeinstallationguide

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
@RequestMapping("/runtimeinstallationplan/runtimeinstallationguide")
class RuntimeInstallationGuideReadModelResource(private val repository: RuntimeInstallationGuideReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('runtime_installation_guide:list') or hasAuthority('runtime_installation_guide:read')")
    @GetMapping
    fun findAll(
        criteria: RuntimeInstallationGuideReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<RuntimeInstallationGuideReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('runtime_installation_guide:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<RuntimeInstallationGuideReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
