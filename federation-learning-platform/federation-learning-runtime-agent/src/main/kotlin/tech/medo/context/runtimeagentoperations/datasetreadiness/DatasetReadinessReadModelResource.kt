package tech.medo.runtimeagentoperations.datasetreadiness

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
@RequestMapping("/dataset/datasetreadiness")
class DatasetReadinessReadModelResource(private val repository: DatasetReadinessReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('dataset_readiness:list') or hasAuthority('dataset_readiness:read')")
    @GetMapping
    fun findAll(
        criteria: DatasetReadinessReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<DatasetReadinessReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('dataset_readiness:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<DatasetReadinessReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
