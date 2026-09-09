package tech.medo.iam.infrastructure.security

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component
import tech.medo.identityaccessmanagement.events.RoleUnassignedFromUserEvent
import tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.userroleassignmentcatalogreadmodel.SpringDataUserRoleAssignmentCatalogReadModelRepository
import tech.medo.identityaccessmanagement.userroleassignmentcatalog.UserRoleAssignmentCatalogReadModelKey

@Component
class UserRoleAssignmentRemovalProjector(
    private val repository: SpringDataUserRoleAssignmentCatalogReadModelRepository,
) {
    @EventHandler
    fun on(event: RoleUnassignedFromUserEvent) {
        repository.deleteById(
            UserRoleAssignmentCatalogReadModelKey(
                userAccountId = event.userAccountId,
                roleCode = event.roleCode,
            ),
        )
    }
}
