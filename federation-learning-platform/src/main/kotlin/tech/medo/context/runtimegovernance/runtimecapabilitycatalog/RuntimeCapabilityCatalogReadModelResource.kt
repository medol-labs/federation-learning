package tech.medo.runtimegovernance.runtimecapabilitycatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID;


@CrossOrigin
@RestController
@RequestMapping("/runtimecapability/runtimecapabilitycatalog")
class RuntimeCapabilityCatalogReadModelResource(private val repository: RuntimeCapabilityCatalogReadModelRepository) {
    @GetMapping
    fun findAll(
        criteria: RuntimeCapabilityCatalogReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<RuntimeCapabilityCatalogReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<RuntimeCapabilityCatalogReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
