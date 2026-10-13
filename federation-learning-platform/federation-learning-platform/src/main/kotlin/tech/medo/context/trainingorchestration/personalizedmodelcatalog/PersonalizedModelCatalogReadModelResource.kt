package tech.medo.trainingorchestration.personalizedmodelcatalog

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
import java.util.UUID

@CrossOrigin
@RestController
@RequestMapping("/personalizedmodelresult/personalizedmodelcatalog")
class PersonalizedModelCatalogReadModelResource(
    private val repository: PersonalizedModelCatalogReadModelRepository
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('personalized_model_catalog:list') or hasAuthority('personalized_model_catalog:read')")
    @GetMapping
    fun findAll(
        criteria: PersonalizedModelCatalogReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<PersonalizedModelCatalogReadModel> =
        findPage(criteria, pageable)


    private fun findPage(criteria: PersonalizedModelCatalogReadModelCriteria, pageable: Pageable): Page<PersonalizedModelCatalogReadModel> =
        repository.findAllByCriteria(criteria, pageable)



    @PreAuthorize("hasAuthority('*:*') or hasAuthority('personalized_model_catalog:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<PersonalizedModelCatalogReadModel> =
        repository.findByIdWithAccessPolicy(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
