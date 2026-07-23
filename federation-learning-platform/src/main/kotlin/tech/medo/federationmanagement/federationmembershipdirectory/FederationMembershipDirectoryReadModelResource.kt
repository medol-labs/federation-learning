package tech.medo.federationmanagement.federationmembershipdirectory

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID;


@CrossOrigin
@RestController
@RequestMapping("/federationmembership/federationmembershipdirectory")
class FederationMembershipDirectoryReadModelResource(private val repository: FederationMembershipDirectoryReadModelRepository) {
    @GetMapping
    fun findAll(
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<FederationMembershipDirectoryReadModel> =
        repository.findAll(pageable)


}
