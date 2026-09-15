package tech.medo.runtimeagentoperations.agentorganizationdirectory

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
@RequestMapping("/agentorganizationdirectory/agentorganizationdirectory")
class AgentOrganizationDirectoryReadModelResource(private val repository: AgentOrganizationDirectoryReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('agent_organization_directory:list') or hasAuthority('agent_organization_directory:read')")
    @GetMapping
    fun findAll(
        criteria: AgentOrganizationDirectoryReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<AgentOrganizationDirectoryReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('agent_organization_directory:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<AgentOrganizationDirectoryReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
