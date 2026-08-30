package tech.medo.identityaccessmanagement.rolepermissiongrantcatalog

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
@RequestMapping("/rolepermissiongrant/rolepermissiongrantcatalog")
class RolePermissionGrantCatalogReadModelResource(private val repository: RolePermissionGrantCatalogReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('role_permission_grant_catalog:list') or hasAuthority('role_permission_grant_catalog:read')")
    @GetMapping
    fun findAll(
        criteria: RolePermissionGrantCatalogReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<RolePermissionGrantCatalogReadModel> =
        repository.findAllByCriteria(criteria, pageable)


}
