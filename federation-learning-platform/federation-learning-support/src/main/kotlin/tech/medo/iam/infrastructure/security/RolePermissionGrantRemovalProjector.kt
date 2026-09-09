package tech.medo.iam.infrastructure.security

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component
import tech.medo.identityaccessmanagement.events.PermissionRevokedFromRoleEvent
import tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.rolepermissiongrantcatalogreadmodel.SpringDataRolePermissionGrantCatalogReadModelRepository
import tech.medo.identityaccessmanagement.rolepermissiongrantcatalog.RolePermissionGrantCatalogReadModelKey

@Component
class RolePermissionGrantRemovalProjector(
    private val repository: SpringDataRolePermissionGrantCatalogReadModelRepository,
) {
    @EventHandler
    fun on(event: PermissionRevokedFromRoleEvent) {
        repository.deleteById(
            RolePermissionGrantCatalogReadModelKey(
                roleCode = event.roleCode,
                permissionCode = event.permissionCode,
            ),
        )
    }
}
