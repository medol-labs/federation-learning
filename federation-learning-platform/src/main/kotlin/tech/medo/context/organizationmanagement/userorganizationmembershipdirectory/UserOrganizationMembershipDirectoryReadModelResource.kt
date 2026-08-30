package tech.medo.organizationmanagement.userorganizationmembershipdirectory

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
@RequestMapping("/userorganizationmembership/userorganizationmembershipdirectory")
class UserOrganizationMembershipDirectoryReadModelResource(private val repository: UserOrganizationMembershipDirectoryReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('user_organization_membership_directory:list') or hasAuthority('user_organization_membership_directory:read')")
    @GetMapping
    fun findAll(
        criteria: UserOrganizationMembershipDirectoryReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<UserOrganizationMembershipDirectoryReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('user_organization_membership_directory:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<UserOrganizationMembershipDirectoryReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
