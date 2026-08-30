package tech.medo.runtimeagentoperations.runtimeagentlifecyclecatalog

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
@RequestMapping("/runtimeagentlifecycle/runtimeagentlifecyclecatalog")
class RuntimeAgentLifecycleCatalogReadModelResource(private val repository: RuntimeAgentLifecycleCatalogReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('runtime_agent_lifecycle_catalog:list') or hasAuthority('runtime_agent_lifecycle_catalog:read')")
    @GetMapping
    fun findAll(
        criteria: RuntimeAgentLifecycleCatalogReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<RuntimeAgentLifecycleCatalogReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('runtime_agent_lifecycle_catalog:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<RuntimeAgentLifecycleCatalogReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
