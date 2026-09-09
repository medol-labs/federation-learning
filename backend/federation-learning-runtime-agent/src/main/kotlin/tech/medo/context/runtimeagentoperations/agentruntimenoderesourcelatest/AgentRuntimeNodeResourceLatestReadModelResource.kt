package tech.medo.runtimeagentoperations.agentruntimenoderesourcelatest

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
@RequestMapping("/agentruntimenoderesourcetelemetry/agentruntimenoderesourcelatest")
class AgentRuntimeNodeResourceLatestReadModelResource(private val repository: AgentRuntimeNodeResourceLatestReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('agent_runtime_node_resource_latest:list') or hasAuthority('agent_runtime_node_resource_latest:read')")
    @GetMapping
    fun findAll(
        criteria: AgentRuntimeNodeResourceLatestReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<AgentRuntimeNodeResourceLatestReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('agent_runtime_node_resource_latest:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<AgentRuntimeNodeResourceLatestReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
