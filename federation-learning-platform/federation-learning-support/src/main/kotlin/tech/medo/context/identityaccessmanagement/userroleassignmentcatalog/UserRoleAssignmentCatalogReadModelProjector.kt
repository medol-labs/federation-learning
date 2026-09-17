package tech.medo.identityaccessmanagement.userroleassignmentcatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata


import tech.medo.identityaccessmanagement.events.RoleAssignedToUserEvent



interface UserRoleAssignmentCatalogReadModelProjectionUpdater {
    fun update(
        event: RoleAssignedToUserEvent,
        message: EventMessage
    )
}

open class DefaultUserRoleAssignmentCatalogReadModelProjectionUpdater(
    private val repository: UserRoleAssignmentCatalogReadModelRepository
) : UserRoleAssignmentCatalogReadModelProjectionUpdater {
    @Transactional
    open override fun update(
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

@Configuration(proxyBeanMethods = false)
class UserRoleAssignmentCatalogReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(UserRoleAssignmentCatalogReadModelProjectionUpdater::class)
    fun defaultUserRoleAssignmentCatalogReadModelProjectionUpdater(
        repository: UserRoleAssignmentCatalogReadModelRepository
    ): UserRoleAssignmentCatalogReadModelProjectionUpdater =
        DefaultUserRoleAssignmentCatalogReadModelProjectionUpdater(repository)
}

@Namespace("readmodel-user-role-assignment-catalog")
@Component
class UserRoleAssignmentCatalogReadModelProjector(
    private val updater: UserRoleAssignmentCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: RoleAssignedToUserEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
