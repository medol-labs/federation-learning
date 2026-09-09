package tech.medo.runtimeagentoperations.datasetcapability

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
@RequestMapping("/dataset/datasetcapability")
class DatasetCapabilityReadModelResource(private val repository: DatasetCapabilityReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('dataset_capability:list') or hasAuthority('dataset_capability:read')")
    @GetMapping
    fun findAll(
        criteria: DatasetCapabilityReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<DatasetCapabilityReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('dataset_capability:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<DatasetCapabilityReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
