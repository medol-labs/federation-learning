package tech.medo.identityaccessmanagement.rolecatalogs

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.identityaccessmanagement.events.RoleRegisteredEvent



interface RoleCatalogReadModelProjectionUpdater {
    fun update(
        event: RoleRegisteredEvent,
        message: EventMessage
    )
}

@Component
@ConditionalOnMissingBean(RoleCatalogReadModelProjectionUpdater::class)
class DefaultRoleCatalogReadModelProjectionUpdater(
    private val repository: RoleCatalogReadModelRepository
) : RoleCatalogReadModelProjectionUpdater {
    @Transactional
    override fun update(
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

@Namespace("readmodel-role-catalog")
@Component
class RoleCatalogReadModelProjector(
    private val updater: RoleCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: RoleRegisteredEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
