package tech.medo.secureaggregation.secureaggregationsessioncatalog

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
@RequestMapping("/secureaggregationsession/secureaggregationsessioncatalog")
class SecureAggregationSessionCatalogReadModelResource(private val repository: SecureAggregationSessionCatalogReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('secure_aggregation_session_catalog:list') or hasAuthority('secure_aggregation_session_catalog:read')")
    @GetMapping
    fun findAll(
        criteria: SecureAggregationSessionCatalogReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<SecureAggregationSessionCatalogReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('secure_aggregation_session_catalog:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<SecureAggregationSessionCatalogReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
