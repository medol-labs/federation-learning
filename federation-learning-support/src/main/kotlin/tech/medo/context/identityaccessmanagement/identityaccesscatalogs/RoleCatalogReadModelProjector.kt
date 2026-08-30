package tech.medo.identityaccessmanagement.identityaccesscatalogs

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.identityaccessmanagement.events.RoleRegisteredEvent
import tech.medo.identityaccessmanagement.events.PermissionGrantedToRoleEvent



@Component
class RoleCatalogReadModelProjector(private val repository: RoleCatalogReadModelRepository) {
    @EventHandler
    fun on(
        event: RoleRegisteredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.roleId) ?: RoleCatalogReadModelProjection().apply {
                this.roleId = event.roleId
        }
            entity.roleId = event.roleId
            entity.roleCode = event.roleCode
            entity.roleName = event.roleName
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: PermissionGrantedToRoleEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.roleId) ?: RoleCatalogReadModelProjection().apply {
                this.roleId = event.roleId
        }
            entity.roleId = event.roleId
            entity.roleCode = event.roleCode
            entity.permissionCodes = event.permissionCodes
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

}
