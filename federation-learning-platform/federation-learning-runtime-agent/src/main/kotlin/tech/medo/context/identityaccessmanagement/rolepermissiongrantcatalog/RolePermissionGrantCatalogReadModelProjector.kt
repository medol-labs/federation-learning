package tech.medo.identityaccessmanagement.rolepermissiongrantcatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata


import tech.medo.identityaccessmanagement.events.PermissionGrantedToRoleEvent



interface RolePermissionGrantCatalogReadModelProjectionUpdater {
    fun update(
        event: PermissionGrantedToRoleEvent,
        message: EventMessage
    )
}

@Component
@ConditionalOnMissingBean(RolePermissionGrantCatalogReadModelProjectionUpdater::class)
class DefaultRolePermissionGrantCatalogReadModelProjectionUpdater(
    private val repository: RolePermissionGrantCatalogReadModelRepository
) : RolePermissionGrantCatalogReadModelProjectionUpdater {
    @Transactional
    override fun update(
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

@Namespace("readmodel-role-permission-grant-catalog")
@Component
class RolePermissionGrantCatalogReadModelProjector(
    private val updater: RolePermissionGrantCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: PermissionGrantedToRoleEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
