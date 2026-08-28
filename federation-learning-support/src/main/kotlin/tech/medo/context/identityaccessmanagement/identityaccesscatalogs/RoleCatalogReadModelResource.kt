package tech.medo.identityaccessmanagement.identityaccesscatalogs

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


@CrossOrigin
@RestController
@RequestMapping("/useraccount/rolecatalog")
class RoleCatalogReadModelResource(private val repository: RoleCatalogReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('role_catalog:list') or hasAuthority('role_catalog:read')")
    @GetMapping
    fun findAll(
        criteria: RoleCatalogReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<RoleCatalogReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('role_catalog:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: String): ResponseEntity<RoleCatalogReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
