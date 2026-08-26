package tech.medo.secureaggregation.secureaggregationsessioncatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.secureaggregation.events.SecureAggregationSessionCreatedEvent
import tech.medo.secureaggregation.events.SecureAggregationParticipantsSelectedEvent
import tech.medo.secureaggregation.events.HomomorphicEncryptionContextPreparedEvent
import tech.medo.secureaggregation.events.EncryptedModelUpdateReceivedEvent
import tech.medo.secureaggregation.events.SecureAggregationCompletedEvent
import tech.medo.secureaggregation.events.SecureAggregationFailedEvent
import tech.medo.secureaggregation.domain.states.SecureAggregationSessionStateEnum
import java.time.LocalDateTime
import java.time.ZoneOffset


@Component
class SecureAggregationSessionCatalogReadModelProjector(private val repository: SecureAggregationSessionCatalogReadModelRepository) {
    @EventHandler
    fun on(
        event: SecureAggregationSessionCreatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.secureAggregationSessionId) ?: SecureAggregationSessionCatalogReadModelProjection().apply {
                this.secureAggregationSessionId = event.secureAggregationSessionId
        }
            entity.secureAggregationSessionId = event.secureAggregationSessionId
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.featureSchemaId = event.featureSchemaId
            entity.roundId = event.roundId
            entity.roundNumber = event.roundNumber
            entity.requiredParticipantCount = event.requiredParticipantCount
            entity.selectedOrganizationIds = event.selectedOrganizationIds
            entity.selectedRuntimeIds = event.selectedRuntimeIds
            entity.selectedOrganizationCount = event.selectedOrganizationCount
            entity.selectedRuntimeCount = event.selectedRuntimeCount
            entity.state = SecureAggregationSessionStateEnum.PLANNED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: SecureAggregationParticipantsSelectedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.secureAggregationSessionId) ?: SecureAggregationSessionCatalogReadModelProjection().apply {
                this.secureAggregationSessionId = event.secureAggregationSessionId
        }
            entity.secureAggregationSessionId = event.secureAggregationSessionId
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.featureSchemaId = event.featureSchemaId
            entity.roundId = event.roundId
            entity.roundNumber = event.roundNumber
            entity.selectedOrganizationIds = event.selectedOrganizationIds
            entity.selectedRuntimeIds = event.selectedRuntimeIds
            entity.selectedOrganizationCount = event.selectedOrganizationCount
            entity.selectedParticipantCount = event.selectedParticipantCount
            entity.state = SecureAggregationSessionStateEnum.PARTICIPANTS_SELECTED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: HomomorphicEncryptionContextPreparedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.secureAggregationSessionId) ?: SecureAggregationSessionCatalogReadModelProjection().apply {
                this.secureAggregationSessionId = event.secureAggregationSessionId
        }
            entity.secureAggregationSessionId = event.secureAggregationSessionId
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.featureSchemaId = event.featureSchemaId
            entity.roundId = event.roundId
            entity.roundNumber = event.roundNumber
            entity.selectedOrganizationIds = event.selectedOrganizationIds
            entity.selectedRuntimeIds = event.selectedRuntimeIds
            entity.selectedOrganizationCount = event.selectedOrganizationCount
            entity.selectedRuntimeCount = event.selectedRuntimeCount
            entity.encryptionScheme = event.encryptionScheme
            entity.publicKeyVersion = event.publicKeyVersion
            entity.publicKeyRef = event.publicKeyRef
            entity.encryptedParameterScale = event.encryptedParameterScale
            entity.state = SecureAggregationSessionStateEnum.ENCRYPTION_CONTEXT_PREPARED
            entity.encryptionContextPreparedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: EncryptedModelUpdateReceivedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.secureAggregationSessionId) ?: SecureAggregationSessionCatalogReadModelProjection().apply {
                this.secureAggregationSessionId = event.secureAggregationSessionId
        }
            entity.secureAggregationSessionId = event.secureAggregationSessionId
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.featureSchemaId = event.featureSchemaId
            entity.roundId = event.roundId
            entity.encryptionScheme = event.encryptionScheme
            entity.publicKeyVersion = event.publicKeyVersion
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: SecureAggregationCompletedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.secureAggregationSessionId) ?: SecureAggregationSessionCatalogReadModelProjection().apply {
                this.secureAggregationSessionId = event.secureAggregationSessionId
        }
            entity.secureAggregationSessionId = event.secureAggregationSessionId
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.featureSchemaId = event.featureSchemaId
            entity.roundId = event.roundId
            entity.roundNumber = event.roundNumber
            entity.aggregatedModelId = event.aggregatedModelId
            entity.modelFormat = event.modelFormat
            entity.modelArtifactDigest = event.modelArtifactDigest
            entity.state = SecureAggregationSessionStateEnum.COMPLETED
            entity.completedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: SecureAggregationFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.secureAggregationSessionId) ?: SecureAggregationSessionCatalogReadModelProjection().apply {
                this.secureAggregationSessionId = event.secureAggregationSessionId
        }
            entity.secureAggregationSessionId = event.secureAggregationSessionId
            entity.failureReason = event.failureReason
            entity.state = SecureAggregationSessionStateEnum.FAILED
            entity.failedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    private fun eventTime(message: EventMessage): LocalDateTime =
        LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)

}
