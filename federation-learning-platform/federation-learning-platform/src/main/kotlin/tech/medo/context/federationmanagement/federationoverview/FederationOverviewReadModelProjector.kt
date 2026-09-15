package tech.medo.federationmanagement.federationoverview

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.federationmanagement.events.FederationCreatedEvent
import tech.medo.federationmanagement.events.FederationActivatedEvent
import tech.medo.federationmanagement.events.FederationSuspendedEvent
import tech.medo.federationmanagement.events.FederationReactivatedEvent
import tech.medo.federationmanagement.events.ParticipantInvitedEvent
import tech.medo.federationmanagement.events.ParticipantJoinedEvent
import tech.medo.federationmanagement.events.ParticipantRejectedEvent
import tech.medo.federationmanagement.events.ParticipantInvitationRevokedEvent
import tech.medo.federationmanagement.events.ParticipantSuspendedEvent
import tech.medo.federationmanagement.events.ParticipantRemovedEvent
import tech.medo.trainingorchestration.events.TrainingJobSubmittedEvent
import tech.medo.runtimegovernance.events.RuntimeIdentityActivatedEvent
import tech.medo.runtimegovernance.events.RuntimeIdentityRevokedEvent
import tech.medo.federationmanagement.domain.states.FederationStateEnum


@Namespace("readmodel-federation-overview")
@Component
class FederationOverviewReadModelProjector(private val repository: FederationOverviewReadModelRepository) {
    @EventHandler
    fun on(
        event: FederationCreatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.federationId) ?: FederationOverviewReadModelProjection().apply {
                this.federationId = event.federationId
        }
            entity.federationId = event.federationId
            entity.federationName = event.federationName
            entity.minimumParticipantCount = event.minimumParticipantCount
            entity.state = FederationStateEnum.DRAFT
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: FederationActivatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.federationId) ?: FederationOverviewReadModelProjection().apply {
                this.federationId = event.federationId
        }
            entity.federationId = event.federationId
            entity.state = FederationStateEnum.ACTIVE
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: FederationSuspendedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.federationId) ?: FederationOverviewReadModelProjection().apply {
                this.federationId = event.federationId
        }
            entity.federationId = event.federationId
            entity.state = FederationStateEnum.SUSPENDED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: FederationReactivatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.federationId) ?: FederationOverviewReadModelProjection().apply {
                this.federationId = event.federationId
        }
            entity.federationId = event.federationId
            entity.state = FederationStateEnum.ACTIVE
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: ParticipantInvitedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.federationId) ?: FederationOverviewReadModelProjection().apply {
                this.federationId = event.federationId
        }
            entity.federationId = event.federationId
            entity.federationName = event.federationName
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: ParticipantJoinedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.federationId) ?: FederationOverviewReadModelProjection().apply {
                this.federationId = event.federationId
        }
            entity.federationId = event.federationId
            entity.federationName = event.federationName
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: ParticipantRejectedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.federationId) ?: FederationOverviewReadModelProjection().apply {
                this.federationId = event.federationId
        }
            entity.federationId = event.federationId
            entity.federationName = event.federationName
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: ParticipantInvitationRevokedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.federationId) ?: FederationOverviewReadModelProjection().apply {
                this.federationId = event.federationId
        }
            entity.federationId = event.federationId
            entity.federationName = event.federationName
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: ParticipantSuspendedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.federationId) ?: FederationOverviewReadModelProjection().apply {
                this.federationId = event.federationId
        }
            entity.federationId = event.federationId
            entity.federationName = event.federationName
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: ParticipantRemovedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.federationId) ?: FederationOverviewReadModelProjection().apply {
                this.federationId = event.federationId
        }
            entity.federationId = event.federationId
            entity.federationName = event.federationName
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: TrainingJobSubmittedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.federationId) ?: FederationOverviewReadModelProjection().apply {
                this.federationId = event.federationId
        }
            entity.federationId = event.federationId
            entity.federationName = event.federationName
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(event: RuntimeIdentityActivatedEvent) {
        // Skipped: RuntimeIdentityActivatedEvent does not provide enough key fields to locate FederationOverviewReadModelProjection.
    }

    @EventHandler
    fun on(event: RuntimeIdentityRevokedEvent) {
        // Skipped: RuntimeIdentityRevokedEvent does not provide enough key fields to locate FederationOverviewReadModelProjection.
    }

}
