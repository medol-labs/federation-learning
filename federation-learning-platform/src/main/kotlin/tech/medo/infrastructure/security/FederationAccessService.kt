package tech.medo.infrastructure.security

import java.util.UUID
import org.springframework.stereotype.Service
import tech.medo.federationmanagement.federationmembershipdirectory.FederationMembershipDirectoryReadModelKey
import tech.medo.federationmanagement.federationmembershipdirectory.FederationMembershipDirectoryReadModelRepository
import tech.medo.shared.security.CurrentUser
import tech.medo.shared.security.CurrentUserProvider

@Service("federationAccessService")
class FederationAccessService(
    private val currentUserProvider: CurrentUserProvider,
    private val membershipDirectory: FederationMembershipDirectoryReadModelRepository,
) {
    fun canAccess(federationId: UUID): Boolean =
        canAccess(federationId, currentUserProvider.currentUser())

    fun canAccess(federationId: UUID, currentUser: CurrentUser): Boolean {
        if (currentUser.hasPermission("federation:manage")) {
            return true
        }

        val organizationId = currentUser.organizationId ?: return false
        val membership = membershipDirectory
            .findById(FederationMembershipDirectoryReadModelKey(federationId, organizationId))

        return membership?.membershipStatus in setOf(
            "Active",
            "ACTIVE",
            "JOINED",
        )
    }
}
