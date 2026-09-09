package tech.medo.identityaccessmanagement.rolepermissiongrantcatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.identityaccessmanagement.events.PermissionGrantedToRoleEvent



@Component
class RolePermissionGrantCatalogReadModelProjector(private val repository: RolePermissionGrantCatalogReadModelRepository) {
    @EventHandler
    fun on(
        event: PermissionGrantedToRoleEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(RolePermissionGrantCatalogReadModelKey(roleCode = event.roleCode, permissionCode = event.permissionCode)) ?: RolePermissionGrantCatalogReadModelProjection().apply {
                this.roleCode = event.roleCode
                this.permissionCode = event.permissionCode
        }
            entity.roleId = event.roleId
            entity.roleCode = event.roleCode
            entity.permissionCode = event.permissionCode
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

}
