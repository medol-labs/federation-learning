package tech.medo.runtimeagentoperations.agentruntimeidentitycatalog

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
@RequestMapping("/agentruntimeidentitycatalog/agentruntimeidentitycatalog")
class AgentRuntimeIdentityCatalogReadModelResource(private val repository: AgentRuntimeIdentityCatalogReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('agent_runtime_identity_catalog:list') or hasAuthority('agent_runtime_identity_catalog:read')")
    @GetMapping
    fun findAll(
        criteria: AgentRuntimeIdentityCatalogReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<AgentRuntimeIdentityCatalogReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('agent_runtime_identity_catalog:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<AgentRuntimeIdentityCatalogReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
