package tech.medo.federationmanagement.federationmembershipdirectory

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
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



interface FederationMembershipDirectoryReadModelProjectionUpdater {
    fun update(
        event: OrganizationRegisteredEvent,
        message: EventMessage
    )

    fun update(
        event: OrganizationActivatedEvent,
        message: EventMessage
    )

    fun update(
        event: FederationCreatedEvent,
        message: EventMessage
    )

    fun update(
        event: ParticipantInvitedEvent,
        message: EventMessage
    )

    fun update(
        event: ParticipantJoinedEvent,
        message: EventMessage
    )

    fun update(
        event: ParticipantRejectedEvent,
        message: EventMessage
    )

    fun update(
        event: ParticipantInvitationRevokedEvent,
        message: EventMessage
    )

    fun update(
        event: ParticipantSuspendedEvent,
        message: EventMessage
    )

    fun update(
        event: ParticipantRemovedEvent,
        message: EventMessage
    )
}

@Component
@ConditionalOnMissingBean(FederationMembershipDirectoryReadModelProjectionUpdater::class)
class DefaultFederationMembershipDirectoryReadModelProjectionUpdater(
    private val repository: FederationMembershipDirectoryReadModelRepository
) : FederationMembershipDirectoryReadModelProjectionUpdater {
    @Transactional
    override fun update(
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

    @Transactional
    override fun update(
        event: OrganizationActivatedEvent,
        message: EventMessage
    ) {

        repository.findProjectionsByOrganizationId(event.organizationId).forEach { entity ->
            entity.organizationId = event.organizationId
            ProjectionMetadata.assign(entity, message)
            repository.save(entity)

        }
    }

    @Transactional
    override fun update(
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

    @Transactional
    override fun update(
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

    @Transactional
    override fun update(
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

    @Transactional
    override fun update(
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

    @Transactional
    override fun update(
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

    @Transactional
    override fun update(
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

    @Transactional
    override fun update(
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

@Namespace("readmodel-federation-membership-directory")
@Component
class FederationMembershipDirectoryReadModelProjector(
    private val updater: FederationMembershipDirectoryReadModelProjectionUpdater
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
        event: OrganizationActivatedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: FederationCreatedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: ParticipantInvitedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: ParticipantJoinedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: ParticipantRejectedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: ParticipantInvitationRevokedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: ParticipantSuspendedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: ParticipantRemovedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
