package tech.medo.trainingorchestration.trainingparticipanteligibility

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



interface TrainingParticipantEligibilityReadModelProjectionUpdater {
    fun update(
        event: OrganizationRegisteredEvent,
        message: EventMessage
    )

    fun update(
        event: FederationCreatedEvent,
        message: EventMessage
    )

    fun update(
        event: ParticipantJoinedEvent,
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
        event: RuntimeConnectionEstablishedEvent,
        message: EventMessage
    )

    fun update(
        event: FeatureSchemaDefinedEvent,
        message: EventMessage
    )

    fun update(
        event: DatasetMetadataReportedEvent,
        message: EventMessage
    )

    fun update(
        event: TrainingJobCreatedEvent,
        message: EventMessage
    )

    fun update(
        event: TrainingJobSubmittedEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeAgentOfflineDetectedEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeAgentRecoveredEvent,
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

    fun update(
        event: RuntimeCapabilitiesDetectedEvent,
        message: EventMessage
    )
}

open class DefaultTrainingParticipantEligibilityReadModelProjectionUpdater(
    private val repository: TrainingParticipantEligibilityReadModelRepository
) : TrainingParticipantEligibilityReadModelProjectionUpdater {
    open override fun update(
        event: OrganizationRegisteredEvent,
        message: EventMessage
    ) {
        // Skipped: OrganizationRegisteredEvent does not provide enough key fields to locate TrainingParticipantEligibilityReadModelProjection.
    }

    open override fun update(
        event: FederationCreatedEvent,
        message: EventMessage
    ) {
        // Skipped: FederationCreatedEvent does not provide enough key fields to locate TrainingParticipantEligibilityReadModelProjection.
    }

    open override fun update(
        event: ParticipantJoinedEvent,
        message: EventMessage
    ) {
        // Skipped: ParticipantJoinedEvent does not provide enough key fields to locate TrainingParticipantEligibilityReadModelProjection.
    }

    open override fun update(
        event: ParticipantSuspendedEvent,
        message: EventMessage
    ) {
        // Skipped: ParticipantSuspendedEvent does not provide enough key fields to locate TrainingParticipantEligibilityReadModelProjection.
    }

    open override fun update(
        event: ParticipantRemovedEvent,
        message: EventMessage
    ) {
        // Skipped: ParticipantRemovedEvent does not provide enough key fields to locate TrainingParticipantEligibilityReadModelProjection.
    }

    open override fun update(
        event: RuntimeConnectionEstablishedEvent,
        message: EventMessage
    ) {
        // Skipped: RuntimeConnectionEstablishedEvent does not provide enough key fields to locate TrainingParticipantEligibilityReadModelProjection.
    }

    open override fun update(
        event: FeatureSchemaDefinedEvent,
        message: EventMessage
    ) {
        // Skipped: FeatureSchemaDefinedEvent does not provide enough key fields to locate TrainingParticipantEligibilityReadModelProjection.
    }

    open override fun update(
        event: DatasetMetadataReportedEvent,
        message: EventMessage
    ) {
        // Skipped: DatasetMetadataReportedEvent does not provide enough key fields to locate TrainingParticipantEligibilityReadModelProjection.
    }

    @Transactional
    open override fun update(
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

    @Transactional
    open override fun update(
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

    @Transactional
    open override fun update(
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

    @Transactional
    open override fun update(
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

    open override fun update(
        event: RuntimeIdentityActivatedEvent,
        message: EventMessage
    ) {
        // Skipped: RuntimeIdentityActivatedEvent does not provide enough key fields to locate TrainingParticipantEligibilityReadModelProjection.
    }

    open override fun update(
        event: RuntimeIdentityRevokedEvent,
        message: EventMessage
    ) {
        // Skipped: RuntimeIdentityRevokedEvent does not provide enough key fields to locate TrainingParticipantEligibilityReadModelProjection.
    }

    open override fun update(
        event: RuntimeCapabilitiesDetectedEvent,
        message: EventMessage
    ) {
        // Skipped: RuntimeCapabilitiesDetectedEvent does not provide enough key fields to locate TrainingParticipantEligibilityReadModelProjection.
    }

}

@Configuration(proxyBeanMethods = false)
class TrainingParticipantEligibilityReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(TrainingParticipantEligibilityReadModelProjectionUpdater::class)
    fun defaultTrainingParticipantEligibilityReadModelProjectionUpdater(
        repository: TrainingParticipantEligibilityReadModelRepository
    ): TrainingParticipantEligibilityReadModelProjectionUpdater =
        DefaultTrainingParticipantEligibilityReadModelProjectionUpdater(repository)
}

@Namespace("readmodel-training-participant-eligibility")
@Component
class TrainingParticipantEligibilityReadModelProjector(
    private val updater: TrainingParticipantEligibilityReadModelProjectionUpdater
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
        event: FederationCreatedEvent,
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
        event: RuntimeConnectionEstablishedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: FeatureSchemaDefinedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: DatasetMetadataReportedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: TrainingJobCreatedEvent,
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
        event: RuntimeAgentOfflineDetectedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeAgentRecoveredEvent,
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

    @EventHandler
    fun on(
        event: RuntimeCapabilitiesDetectedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
