package tech.medo.secureaggregation.secureaggregationsessioncatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
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


interface SecureAggregationSessionCatalogReadModelProjectionUpdater {
    fun update(
        event: SecureAggregationSessionCreatedEvent,
        message: EventMessage
    )

    fun update(
        event: SecureAggregationParticipantsSelectedEvent,
        message: EventMessage
    )

    fun update(
        event: HomomorphicEncryptionContextPreparedEvent,
        message: EventMessage
    )

    fun update(
        event: EncryptedModelUpdateReceivedEvent,
        message: EventMessage
    )

    fun update(
        event: SecureAggregationCompletedEvent,
        message: EventMessage
    )

    fun update(
        event: SecureAggregationFailedEvent,
        message: EventMessage
    )
}

open class DefaultSecureAggregationSessionCatalogReadModelProjectionUpdater(
    private val repository: SecureAggregationSessionCatalogReadModelRepository
) : SecureAggregationSessionCatalogReadModelProjectionUpdater {
    @Transactional
    open override fun update(
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

    @Transactional
    open override fun update(
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

    @Transactional
    open override fun update(
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
            entity.encryptionContextPrepared = true
            entity.encryptionContextPreparedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
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
            entity.roundNumber = event.roundNumber
            entity.selectedParticipantCount = event.selectedParticipantCount
            entity.receivedEncryptedUpdateCount = event.receivedEncryptedUpdateCount
            entity.encryptionScheme = event.encryptionScheme
            entity.publicKeyVersion = event.publicKeyVersion
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
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

    @Transactional
    open override fun update(
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

@Configuration(proxyBeanMethods = false)
class SecureAggregationSessionCatalogReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(SecureAggregationSessionCatalogReadModelProjectionUpdater::class)
    fun defaultSecureAggregationSessionCatalogReadModelProjectionUpdater(
        repository: SecureAggregationSessionCatalogReadModelRepository
    ): SecureAggregationSessionCatalogReadModelProjectionUpdater =
        DefaultSecureAggregationSessionCatalogReadModelProjectionUpdater(repository)
}

@Namespace("readmodel-secure-aggregation-session-catalog")
@Component
class SecureAggregationSessionCatalogReadModelProjector(
    private val updater: SecureAggregationSessionCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: SecureAggregationSessionCreatedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: SecureAggregationParticipantsSelectedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: HomomorphicEncryptionContextPreparedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: EncryptedModelUpdateReceivedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: SecureAggregationCompletedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: SecureAggregationFailedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
