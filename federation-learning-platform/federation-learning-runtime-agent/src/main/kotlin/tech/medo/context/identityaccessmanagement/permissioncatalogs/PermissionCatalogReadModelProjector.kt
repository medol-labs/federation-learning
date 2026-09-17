package tech.medo.identityaccessmanagement.permissioncatalogs

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata


import tech.medo.identityaccessmanagement.events.PermissionRegisteredEvent



interface PermissionCatalogReadModelProjectionUpdater {
    fun update(
        event: PermissionRegisteredEvent,
        message: EventMessage
    )
}

open class DefaultPermissionCatalogReadModelProjectionUpdater(
    private val repository: PermissionCatalogReadModelRepository
) : PermissionCatalogReadModelProjectionUpdater {
    @Transactional
    open override fun update(
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

@Configuration(proxyBeanMethods = false)
class PermissionCatalogReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(PermissionCatalogReadModelProjectionUpdater::class)
    fun defaultPermissionCatalogReadModelProjectionUpdater(
        repository: PermissionCatalogReadModelRepository
    ): PermissionCatalogReadModelProjectionUpdater =
        DefaultPermissionCatalogReadModelProjectionUpdater(repository)
}

@Namespace("readmodel-permission-catalog")
@Component
class PermissionCatalogReadModelProjector(
    private val updater: PermissionCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: PermissionRegisteredEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
