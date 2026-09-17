package tech.medo.organizationmanagement.userorganizationmembershipdirectory

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata


import tech.medo.organizationmanagement.events.OrganizationRegisteredEvent
import tech.medo.organizationmanagement.events.UserAccountBoundToOrganizationEvent
import tech.medo.organizationmanagement.domain.states.UserOrganizationMembershipStateEnum


interface UserOrganizationMembershipDirectoryReadModelProjectionUpdater {
    fun update(
        event: OrganizationRegisteredEvent,
        message: EventMessage
    )

    fun update(
        event: UserAccountBoundToOrganizationEvent,
        message: EventMessage
    )
}

open class DefaultUserOrganizationMembershipDirectoryReadModelProjectionUpdater(
    private val repository: UserOrganizationMembershipDirectoryReadModelRepository
) : UserOrganizationMembershipDirectoryReadModelProjectionUpdater {
    open override fun update(
        event: OrganizationRegisteredEvent,
        message: EventMessage
    ) {
        // Skipped: OrganizationRegisteredEvent does not provide enough key fields to locate UserOrganizationMembershipDirectoryReadModelProjection.
    }

    @Transactional
    open override fun update(
        event: UserAccountBoundToOrganizationEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.userOrganizationMembershipId) ?: UserOrganizationMembershipDirectoryReadModelProjection().apply {
                this.userOrganizationMembershipId = event.userOrganizationMembershipId
        }
            entity.userOrganizationMembershipId = event.userOrganizationMembershipId
            entity.userAccountId = event.userAccountId
            entity.username = event.username
            entity.organizationId = event.organizationId
            entity.organizationName = event.organizationName
            entity.organizationUserRole = event.organizationUserRole
            entity.state = UserOrganizationMembershipStateEnum.ACTIVE
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

}

@Configuration(proxyBeanMethods = false)
class UserOrganizationMembershipDirectoryReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(UserOrganizationMembershipDirectoryReadModelProjectionUpdater::class)
    fun defaultUserOrganizationMembershipDirectoryReadModelProjectionUpdater(
        repository: UserOrganizationMembershipDirectoryReadModelRepository
    ): UserOrganizationMembershipDirectoryReadModelProjectionUpdater =
        DefaultUserOrganizationMembershipDirectoryReadModelProjectionUpdater(repository)
}

@Namespace("readmodel-user-organization-membership-directory")
@Component
class UserOrganizationMembershipDirectoryReadModelProjector(
    private val updater: UserOrganizationMembershipDirectoryReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: OrganizationRegisteredEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: UserAccountBoundToOrganizationEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
