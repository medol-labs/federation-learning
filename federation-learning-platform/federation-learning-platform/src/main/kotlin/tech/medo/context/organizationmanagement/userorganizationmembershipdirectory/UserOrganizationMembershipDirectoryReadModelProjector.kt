package tech.medo.organizationmanagement.userorganizationmembershipdirectory

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.organizationmanagement.events.OrganizationRegisteredEvent
import tech.medo.organizationmanagement.events.UserAccountBoundToOrganizationEvent
import tech.medo.organizationmanagement.domain.states.UserOrganizationMembershipStateEnum


@Component
class UserOrganizationMembershipDirectoryReadModelProjector(private val repository: UserOrganizationMembershipDirectoryReadModelRepository) {
    @EventHandler
    fun on(event: OrganizationRegisteredEvent) {
        // Skipped: OrganizationRegisteredEvent does not provide enough key fields to locate UserOrganizationMembershipDirectoryReadModelProjection.
    }

    @EventHandler
    fun on(
        event: UserAccountBoundToOrganizationEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.userOrganizationMembershipId) ?: UserOrganizationMembershipDirectoryReadModelProjection().apply {
                this.userOrganizationMembershipId = event.userOrganizationMembershipId
        }
            entity.userOrganizationMembershipId = event.userOrganizationMembershipId
            entity.userAccountId = event.userAccountId
            entity.organizationId = event.organizationId
            entity.organizationUserRole = event.organizationUserRole
            entity.state = UserOrganizationMembershipStateEnum.ACTIVE
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

}
