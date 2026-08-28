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
import java.util.UUID;


@CrossOrigin
@RestController
@RequestMapping("/useraccount/useraccountcatalog")
class UserAccountCatalogReadModelResource(private val repository: UserAccountCatalogReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('user_account_catalog:list') or hasAuthority('user_account_catalog:read')")
    @GetMapping
    fun findAll(
        criteria: UserAccountCatalogReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<UserAccountCatalogReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('user_account_catalog:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<UserAccountCatalogReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
