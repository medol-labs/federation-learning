package tech.medo.identityaccessmanagement.userroleassignmentcatalog

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
@RequestMapping("/userroleassignment/userroleassignmentcatalog")
class UserRoleAssignmentCatalogReadModelResource(private val repository: UserRoleAssignmentCatalogReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('user_role_assignment_catalog:list') or hasAuthority('user_role_assignment_catalog:read')")
    @GetMapping
    fun findAll(
        criteria: UserRoleAssignmentCatalogReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<UserRoleAssignmentCatalogReadModel> =
        repository.findAllByCriteria(criteria, pageable)


}
