package tech.medo.runtimemonitoring.runtimenodeinventoryview

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
@RequestMapping("/runtimenodeinventory/runtimenodeinventoryview")
class RuntimeNodeInventoryViewReadModelResource(private val repository: RuntimeNodeInventoryViewReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('runtime_node_inventory_view:list') or hasAuthority('runtime_node_inventory_view:read')")
    @GetMapping
    fun findAll(
        criteria: RuntimeNodeInventoryViewReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<RuntimeNodeInventoryViewReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('runtime_node_inventory_view:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<RuntimeNodeInventoryViewReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
