package tech.medo.datasetgovernance.currentrecommendedfeatureschemacatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@CrossOrigin
@RestController
@RequestMapping("/featureschema/currentrecommendedfeatureschemacatalog")
class CurrentRecommendedFeatureSchemaCatalogReadModelResource(private val repository: CurrentRecommendedFeatureSchemaCatalogReadModelRepository) {
    @GetMapping
    fun findAll(
        criteria: CurrentRecommendedFeatureSchemaCatalogReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<CurrentRecommendedFeatureSchemaCatalogReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @GetMapping("/{id}")
    fun findOne(@PathVariable id: String): ResponseEntity<CurrentRecommendedFeatureSchemaCatalogReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
