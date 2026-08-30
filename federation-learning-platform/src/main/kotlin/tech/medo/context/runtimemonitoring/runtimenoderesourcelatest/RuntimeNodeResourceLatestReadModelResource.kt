package tech.medo.runtimemonitoring.runtimenoderesourcelatest

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
@RequestMapping("/runtimenoderesourcetelemetry/runtimenoderesourcelatest")
class RuntimeNodeResourceLatestReadModelResource(private val repository: RuntimeNodeResourceLatestReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('runtime_node_resource_latest:list') or hasAuthority('runtime_node_resource_latest:read')")
    @GetMapping
    fun findAll(
        criteria: RuntimeNodeResourceLatestReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<RuntimeNodeResourceLatestReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('runtime_node_resource_latest:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<RuntimeNodeResourceLatestReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
