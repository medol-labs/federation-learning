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
@RequestMapping("/useraccount/permissioncatalog")
class PermissionCatalogReadModelResource(private val repository: PermissionCatalogReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('permission_catalog:list') or hasAuthority('permission_catalog:read')")
    @GetMapping
    fun findAll(
        criteria: PermissionCatalogReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<PermissionCatalogReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('permission_catalog:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: String): ResponseEntity<PermissionCatalogReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
