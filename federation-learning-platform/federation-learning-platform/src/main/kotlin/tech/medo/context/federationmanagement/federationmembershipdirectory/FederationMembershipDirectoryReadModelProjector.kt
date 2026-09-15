package tech.medo.federationmanagement.federationmembershipdirectory

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.organizationmanagement.events.OrganizationRegisteredEvent
import tech.medo.organizationmanagement.events.OrganizationActivatedEvent
import tech.medo.federationmanagement.events.FederationCreatedEvent
import tech.medo.federationmanagement.events.ParticipantInvitedEvent
import tech.medo.federationmanagement.events.ParticipantJoinedEvent
import tech.medo.federationmanagement.events.ParticipantRejectedEvent
import tech.medo.federationmanagement.events.ParticipantInvitationRevokedEvent
import tech.medo.federationmanagement.events.ParticipantSuspendedEvent
import tech.medo.federationmanagement.events.ParticipantRemovedEvent



@Namespace("readmodel-federation-membership-directory")
@Component
class FederationMembershipDirectoryReadModelProjector(private val repository: FederationMembershipDirectoryReadModelRepository) {
    @EventHandler
    fun on(
        event: OrganizationRegisteredEvent,
        message: EventMessage
    ) {

        repository.findProjectionsByOrganizationId(event.organizationId).forEach { entity ->
            entity.organizationId = event.organizationId
            entity.organizationName = event.organizationName
            ProjectionMetadata.assign(entity, message)
            repository.save(entity)
        }
    }

    @EventHandler
    fun on(
        event: OrganizationActivatedEvent,
        message: EventMessage
    ) {

        repository.findProjectionsByOrganizationId(event.organizationId).forEach { entity ->
            entity.organizationId = event.organizationId
            ProjectionMetadata.assign(entity, message)
            repository.save(entity)
        }
    }

    @EventHandler
    fun on(
        event: FederationCreatedEvent,
        message: EventMessage
    ) {

        repository.findProjectionsByFederationId(event.federationId).forEach { entity ->
            entity.federationId = event.federationId
            entity.federationName = event.federationName
            ProjectionMetadata.assign(entity, message)
            repository.save(entity)
        }
    }

    @EventHandler
    fun on(
        event: ParticipantInvitedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(FederationMembershipDirectoryReadModelKey(federationId = event.federationId, organizationId = event.organizationId)) ?: FederationMembershipDirectoryReadModelProjection().apply {
                this.federationId = event.federationId
                this.organizationId = event.organizationId
        }
            entity.federationId = event.federationId
            entity.organizationId = event.organizationId
            entity.federationName = event.federationName
            entity.organizationName = event.organizationName
            entity.invitationNote = event.invitationNote
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: ParticipantJoinedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(FederationMembershipDirectoryReadModelKey(federationId = event.federationId, organizationId = event.organizationId)) ?: FederationMembershipDirectoryReadModelProjection().apply {
                this.federationId = event.federationId
                this.organizationId = event.organizationId
        }
            entity.federationId = event.federationId
            entity.organizationId = event.organizationId
            entity.federationName = event.federationName
            entity.organizationName = event.organizationName
            entity.approvalNote = event.approvalNote
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: ParticipantRejectedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(FederationMembershipDirectoryReadModelKey(federationId = event.federationId, organizationId = event.organizationId)) ?: FederationMembershipDirectoryReadModelProjection().apply {
                this.federationId = event.federationId
                this.organizationId = event.organizationId
        }
            entity.federationId = event.federationId
            entity.organizationId = event.organizationId
            entity.federationName = event.federationName
            entity.organizationName = event.organizationName
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: ParticipantInvitationRevokedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(FederationMembershipDirectoryReadModelKey(federationId = event.federationId, organizationId = event.organizationId)) ?: FederationMembershipDirectoryReadModelProjection().apply {
                this.federationId = event.federationId
                this.organizationId = event.organizationId
        }
            entity.federationId = event.federationId
            entity.organizationId = event.organizationId
            entity.federationName = event.federationName
            entity.organizationName = event.organizationName
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: ParticipantSuspendedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(FederationMembershipDirectoryReadModelKey(federationId = event.federationId, organizationId = event.organizationId)) ?: FederationMembershipDirectoryReadModelProjection().apply {
                this.federationId = event.federationId
                this.organizationId = event.organizationId
        }
            entity.federationId = event.federationId
            entity.organizationId = event.organizationId
            entity.federationName = event.federationName
            entity.organizationName = event.organizationName
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: ParticipantRemovedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(FederationMembershipDirectoryReadModelKey(federationId = event.federationId, organizationId = event.organizationId)) ?: FederationMembershipDirectoryReadModelProjection().apply {
                this.federationId = event.federationId
                this.organizationId = event.organizationId
        }
            entity.federationId = event.federationId
            entity.organizationId = event.organizationId
            entity.federationName = event.federationName
            entity.organizationName = event.organizationName
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

}
