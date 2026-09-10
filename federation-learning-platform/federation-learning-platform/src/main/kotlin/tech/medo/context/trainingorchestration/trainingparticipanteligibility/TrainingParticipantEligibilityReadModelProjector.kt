package tech.medo.trainingorchestration.trainingparticipanteligibility

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.organizationmanagement.events.OrganizationRegisteredEvent
import tech.medo.federationmanagement.events.FederationCreatedEvent
import tech.medo.federationmanagement.events.ParticipantJoinedEvent
import tech.medo.federationmanagement.events.ParticipantSuspendedEvent
import tech.medo.federationmanagement.events.ParticipantRemovedEvent
import tech.medo.runtimeprovisioning.events.RuntimeConnectionEstablishedEvent
import tech.medo.datasetgovernance.events.FeatureSchemaDefinedEvent
import tech.medo.datasetgovernance.events.DatasetMetadataReportedEvent
import tech.medo.trainingorchestration.events.TrainingJobCreatedEvent
import tech.medo.trainingorchestration.events.TrainingJobSubmittedEvent
import tech.medo.runtimemonitoring.events.RuntimeAgentOfflineDetectedEvent
import tech.medo.runtimemonitoring.events.RuntimeAgentRecoveredEvent
import tech.medo.runtimegovernance.events.RuntimeIdentityActivatedEvent
import tech.medo.runtimegovernance.events.RuntimeIdentityRevokedEvent
import tech.medo.runtimegovernance.events.RuntimeCapabilitiesDetectedEvent



@Component
class TrainingParticipantEligibilityReadModelProjector(private val repository: TrainingParticipantEligibilityReadModelRepository) {
    @EventHandler
    fun on(event: OrganizationRegisteredEvent) {
        // Skipped: OrganizationRegisteredEvent does not provide enough key fields to locate TrainingParticipantEligibilityReadModelProjection.
    }

    @EventHandler
    fun on(event: FederationCreatedEvent) {
        // Skipped: FederationCreatedEvent does not provide enough key fields to locate TrainingParticipantEligibilityReadModelProjection.
    }

    @EventHandler
    fun on(event: ParticipantJoinedEvent) {
        // Skipped: ParticipantJoinedEvent does not provide enough key fields to locate TrainingParticipantEligibilityReadModelProjection.
    }

    @EventHandler
    fun on(event: ParticipantSuspendedEvent) {
        // Skipped: ParticipantSuspendedEvent does not provide enough key fields to locate TrainingParticipantEligibilityReadModelProjection.
    }

    @EventHandler
    fun on(event: ParticipantRemovedEvent) {
        // Skipped: ParticipantRemovedEvent does not provide enough key fields to locate TrainingParticipantEligibilityReadModelProjection.
    }

    @EventHandler
    fun on(event: RuntimeConnectionEstablishedEvent) {
        // Skipped: RuntimeConnectionEstablishedEvent does not provide enough key fields to locate TrainingParticipantEligibilityReadModelProjection.
    }

    @EventHandler
    fun on(event: FeatureSchemaDefinedEvent) {
        // Skipped: FeatureSchemaDefinedEvent does not provide enough key fields to locate TrainingParticipantEligibilityReadModelProjection.
    }

    @EventHandler
    fun on(event: DatasetMetadataReportedEvent) {
        // Skipped: DatasetMetadataReportedEvent does not provide enough key fields to locate TrainingParticipantEligibilityReadModelProjection.
    }

    @EventHandler
    fun on(
        event: TrainingJobCreatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.trainingJobId) ?: TrainingParticipantEligibilityReadModelProjection().apply {
                this.trainingJobId = event.trainingJobId
        }
            entity.trainingJobId = event.trainingJobId
            entity.federationId = event.federationId
            entity.featureSchemaId = event.featureSchemaId
            entity.federationName = event.federationName
            entity.featureDomain = event.featureDomain
            entity.featureSchemaVersion = event.featureSchemaVersion
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: TrainingJobSubmittedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.trainingJobId) ?: TrainingParticipantEligibilityReadModelProjection().apply {
                this.trainingJobId = event.trainingJobId
        }
            entity.trainingJobId = event.trainingJobId
            entity.federationId = event.federationId
            entity.featureSchemaId = event.featureSchemaId
            entity.federationName = event.federationName
            entity.featureDomain = event.featureDomain
            entity.featureSchemaVersion = event.featureSchemaVersion
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeAgentOfflineDetectedEvent,
        message: EventMessage
    ) {
        val key = event.trainingJobId ?: return

        val entity = repository.findProjectionById(key) ?: TrainingParticipantEligibilityReadModelProjection().apply {
                this.trainingJobId = key
        }
            entity.trainingJobId = event.trainingJobId
            entity.federationId = event.federationId
            entity.federationName = event.federationName
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeAgentRecoveredEvent,
        message: EventMessage
    ) {
        val key = event.trainingJobId ?: return

        val entity = repository.findProjectionById(key) ?: TrainingParticipantEligibilityReadModelProjection().apply {
                this.trainingJobId = key
        }
            entity.trainingJobId = event.trainingJobId
            entity.federationId = event.federationId
            entity.federationName = event.federationName
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(event: RuntimeIdentityActivatedEvent) {
        // Skipped: RuntimeIdentityActivatedEvent does not provide enough key fields to locate TrainingParticipantEligibilityReadModelProjection.
    }

    @EventHandler
    fun on(event: RuntimeIdentityRevokedEvent) {
        // Skipped: RuntimeIdentityRevokedEvent does not provide enough key fields to locate TrainingParticipantEligibilityReadModelProjection.
    }

    @EventHandler
    fun on(event: RuntimeCapabilitiesDetectedEvent) {
        // Skipped: RuntimeCapabilitiesDetectedEvent does not provide enough key fields to locate TrainingParticipantEligibilityReadModelProjection.
    }

}
