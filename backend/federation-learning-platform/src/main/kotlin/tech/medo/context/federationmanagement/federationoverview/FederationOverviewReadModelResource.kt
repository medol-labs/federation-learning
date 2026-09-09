package tech.medo.federationmanagement.federationoverview

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
@RequestMapping("/federation/federationoverview")
class FederationOverviewReadModelResource(private val repository: FederationOverviewReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('federation_overview:list') or hasAuthority('federation_overview:read')")
    @GetMapping
    fun findAll(
        criteria: FederationOverviewReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<FederationOverviewReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('federation_overview:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<FederationOverviewReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
