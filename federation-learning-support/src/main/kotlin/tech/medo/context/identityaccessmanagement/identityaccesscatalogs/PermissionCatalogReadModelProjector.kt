package tech.medo.identityaccessmanagement.identityaccesscatalogs

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.identityaccessmanagement.events.PermissionRegisteredEvent



@Component
class PermissionCatalogReadModelProjector(private val repository: PermissionCatalogReadModelRepository) {
    @EventHandler
    fun on(
        event: PermissionRegisteredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.permissionId) ?: PermissionCatalogReadModelProjection().apply {
                this.permissionId = event.permissionId
        }
            entity.permissionId = event.permissionId
            entity.permissionCode = event.permissionCode
            entity.permissionName = event.permissionName
            entity.description = event.description
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

}
