package tech.medo.modelrepository.modelartifactcatalog

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
@RequestMapping("/modelartifact/modelartifactcatalog")
class ModelArtifactCatalogReadModelResource(private val repository: ModelArtifactCatalogReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('model_artifact_catalog:list') or hasAuthority('model_artifact_catalog:read')")
    @GetMapping
    fun findAll(
        criteria: ModelArtifactCatalogReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<ModelArtifactCatalogReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('model_artifact_catalog:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<ModelArtifactCatalogReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
