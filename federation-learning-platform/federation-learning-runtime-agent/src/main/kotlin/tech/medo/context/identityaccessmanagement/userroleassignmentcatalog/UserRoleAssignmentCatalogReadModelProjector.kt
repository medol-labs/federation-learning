package tech.medo.identityaccessmanagement.userroleassignmentcatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.identityaccessmanagement.events.RoleAssignedToUserEvent



@Namespace("readmodel-user-role-assignment-catalog")
@Component
class UserRoleAssignmentCatalogReadModelProjector(private val repository: UserRoleAssignmentCatalogReadModelRepository) {
    @EventHandler
    fun on(
        event: RoleAssignedToUserEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(UserRoleAssignmentCatalogReadModelKey(userAccountId = event.userAccountId, roleCode = event.roleCode)) ?: UserRoleAssignmentCatalogReadModelProjection().apply {
                this.userAccountId = event.userAccountId
                this.roleCode = event.roleCode
        }
            entity.userAccountId = event.userAccountId
            entity.roleCode = event.roleCode
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

}
