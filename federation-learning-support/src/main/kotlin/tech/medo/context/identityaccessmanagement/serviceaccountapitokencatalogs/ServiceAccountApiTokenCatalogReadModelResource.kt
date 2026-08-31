package tech.medo.identityaccessmanagement.serviceaccountapitokencatalogs

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
@RequestMapping("/serviceaccountapitoken/serviceaccountapitokencatalog")
class ServiceAccountApiTokenCatalogReadModelResource(private val repository: ServiceAccountApiTokenCatalogReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('service_account_api_token_catalog:list') or hasAuthority('service_account_api_token_catalog:read')")
    @GetMapping
    fun findAll(
        criteria: ServiceAccountApiTokenCatalogReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<ServiceAccountApiTokenCatalogReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('service_account_api_token_catalog:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<ServiceAccountApiTokenCatalogReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
