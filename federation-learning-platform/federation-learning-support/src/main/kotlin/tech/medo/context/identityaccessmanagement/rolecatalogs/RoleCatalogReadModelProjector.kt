package tech.medo.identityaccessmanagement.rolecatalogs

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.identityaccessmanagement.events.RoleRegisteredEvent



@Namespace("readmodel-role-catalog")
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

}
