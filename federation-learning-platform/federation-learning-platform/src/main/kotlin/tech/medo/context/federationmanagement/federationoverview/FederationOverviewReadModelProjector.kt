package tech.medo.federationmanagement.federationoverview

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
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


interface FederationOverviewReadModelProjectionUpdater {
    fun update(
        event: FederationCreatedEvent,
        message: EventMessage
    )

    fun update(
        event: FederationActivatedEvent,
        message: EventMessage
    )

    fun update(
        event: FederationSuspendedEvent,
        message: EventMessage
    )

    fun update(
        event: FederationReactivatedEvent,
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

    fun update(
        event: TrainingJobSubmittedEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeIdentityActivatedEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeIdentityRevokedEvent,
        message: EventMessage
    )
}

@Component
@ConditionalOnMissingBean(FederationOverviewReadModelProjectionUpdater::class)
class DefaultFederationOverviewReadModelProjectionUpdater(
    private val repository: FederationOverviewReadModelRepository
) : FederationOverviewReadModelProjectionUpdater {
    @Transactional
    override fun update(
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

    @Transactional
    override fun update(
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

    @Transactional
    override fun update(
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

    @Transactional
    override fun update(
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

    @Transactional
    override fun update(
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

    @Transactional
    override fun update(
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

    @Transactional
    override fun update(
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

    @Transactional
    override fun update(
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

    @Transactional
    override fun update(
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

    @Transactional
    override fun update(
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

    @Transactional
    override fun update(
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

    override fun update(
        event: RuntimeIdentityActivatedEvent,
        message: EventMessage
    ) {
        // Skipped: RuntimeIdentityActivatedEvent does not provide enough key fields to locate FederationOverviewReadModelProjection.
    }

    override fun update(
        event: RuntimeIdentityRevokedEvent,
        message: EventMessage
    ) {
        // Skipped: RuntimeIdentityRevokedEvent does not provide enough key fields to locate FederationOverviewReadModelProjection.
    }

}

@Namespace("readmodel-federation-overview")
@Component
class FederationOverviewReadModelProjector(
    private val updater: FederationOverviewReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: FederationCreatedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: FederationActivatedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: FederationSuspendedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: FederationReactivatedEvent,
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

    @EventHandler
    fun on(
        event: TrainingJobSubmittedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeIdentityActivatedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeIdentityRevokedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
