package tech.medo.runtimeagentoperations.roundexecutioncatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata


import tech.medo.runtimeagentoperations.events.ExecutionPlanReceivedEvent
import tech.medo.runtimeagentoperations.events.ExecutionPlanAcceptedEvent
import tech.medo.runtimeagentoperations.events.ExecutionPlanRejectedEvent
import tech.medo.runtimeagentoperations.events.RoundExecutionStartedEvent
import tech.medo.runtimeagentoperations.events.RoundExecutionStartFailedEvent
import tech.medo.runtimeagentoperations.events.RuntimeEngineJobObservedEvent
import tech.medo.runtimeagentoperations.events.RoundExecutionCompletedEvent
import tech.medo.runtimeagentoperations.events.RoundExecutionFailedEvent
import tech.medo.runtimeagentoperations.events.RoundExecutionStartRetryStartedEvent
import tech.medo.runtimeagentoperations.events.RoundExecutionStartRetryFailedEvent
import tech.medo.runtimeagentoperations.events.RoundExecutionRuntimeRetryStartedEvent
import tech.medo.runtimeagentoperations.events.RoundExecutionRuntimeRetryFailedEvent
import tech.medo.runtimeagentoperations.events.AgentLocalModelUpdateSubmittedEvent
import tech.medo.runtimeagentoperations.events.RuntimeEngineJobReleasedEvent
import tech.medo.runtimeagentoperations.events.RuntimeEngineJobReleaseFailedOrSkippedEvent
import tech.medo.runtimeagentoperations.events.RuntimeEngineJobReleaseFailedOrSkippedAfterStartFailureEvent
import tech.medo.runtimeagentoperations.events.RuntimeEngineJobReleaseFailedOrSkippedAfterRetryEvent
import tech.medo.runtimeagentoperations.events.RuntimeEngineJobReleaseFailedOrSkippedAfterRuntimeRetryEvent
import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum
import java.time.LocalDateTime
import java.time.ZoneOffset


interface RoundExecutionCatalogReadModelProjectionUpdater {
    fun update(
        event: ExecutionPlanReceivedEvent,
        message: EventMessage
    )

    fun update(
        event: ExecutionPlanAcceptedEvent,
        message: EventMessage
    )

    fun update(
        event: ExecutionPlanRejectedEvent,
        message: EventMessage
    )

    fun update(
        event: RoundExecutionStartedEvent,
        message: EventMessage
    )

    fun update(
        event: RoundExecutionStartFailedEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeEngineJobObservedEvent,
        message: EventMessage
    )

    fun update(
        event: RoundExecutionCompletedEvent,
        message: EventMessage
    )

    fun update(
        event: RoundExecutionFailedEvent,
        message: EventMessage
    )

    fun update(
        event: RoundExecutionStartRetryStartedEvent,
        message: EventMessage
    )

    fun update(
        event: RoundExecutionStartRetryFailedEvent,
        message: EventMessage
    )

    fun update(
        event: RoundExecutionRuntimeRetryStartedEvent,
        message: EventMessage
    )

    fun update(
        event: RoundExecutionRuntimeRetryFailedEvent,
        message: EventMessage
    )

    fun update(
        event: AgentLocalModelUpdateSubmittedEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeEngineJobReleasedEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeEngineJobReleaseFailedOrSkippedEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeEngineJobReleaseFailedOrSkippedAfterStartFailureEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeEngineJobReleaseFailedOrSkippedAfterRetryEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeEngineJobReleaseFailedOrSkippedAfterRuntimeRetryEvent,
        message: EventMessage
    )
}

open class DefaultRoundExecutionCatalogReadModelProjectionUpdater(
    private val repository: RoundExecutionCatalogReadModelRepository
) : RoundExecutionCatalogReadModelProjectionUpdater {
    @Transactional
    open override fun update(
        event: ExecutionPlanReceivedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.roundExecutionId) ?: RoundExecutionCatalogReadModelProjection().apply {
                this.roundExecutionId = event.roundExecutionId
        }
            entity.roundExecutionId = event.roundExecutionId
            entity.executionSessionId = event.executionSessionId
            entity.executionPlanId = event.executionPlanId
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.roundId = event.roundId
            entity.roundNumber = event.roundNumber
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.baseModelId = event.baseModelId
            entity.state = RoundExecutionStateEnum.PLAN_RECEIVED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: ExecutionPlanAcceptedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.roundExecutionId) ?: RoundExecutionCatalogReadModelProjection().apply {
                this.roundExecutionId = event.roundExecutionId
        }
            entity.roundExecutionId = event.roundExecutionId
            entity.executionSessionId = event.executionSessionId
            entity.executionPlanId = event.executionPlanId
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.roundId = event.roundId
            entity.roundNumber = event.roundNumber
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.baseModelId = event.baseModelId
            entity.localExecutionRequirementsSatisfied = event.localExecutionRequirementsSatisfied
            entity.runtimeIdentityMatched = event.runtimeIdentityMatched
            entity.runtimeDatasetBindingAvailable = event.runtimeDatasetBindingAvailable
            entity.datasetAccessValidated = event.datasetAccessValidated
            entity.baseModelAvailable = event.baseModelAvailable
            entity.trainingConfigurationSupported = event.trainingConfigurationSupported
            entity.runtimeResourceAvailable = event.runtimeResourceAvailable
            entity.runtimeAgentIdle = event.runtimeAgentIdle
            entity.state = RoundExecutionStateEnum.PLAN_ACCEPTED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: ExecutionPlanRejectedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.roundExecutionId) ?: RoundExecutionCatalogReadModelProjection().apply {
                this.roundExecutionId = event.roundExecutionId
        }
            entity.roundExecutionId = event.roundExecutionId
            entity.executionSessionId = event.executionSessionId
            entity.executionPlanId = event.executionPlanId
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.roundId = event.roundId
            entity.roundNumber = event.roundNumber
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.baseModelId = event.baseModelId
            entity.localExecutionRequirementsSatisfied = event.localExecutionRequirementsSatisfied
            entity.runtimeIdentityMatched = event.runtimeIdentityMatched
            entity.runtimeDatasetBindingAvailable = event.runtimeDatasetBindingAvailable
            entity.datasetAccessValidated = event.datasetAccessValidated
            entity.baseModelAvailable = event.baseModelAvailable
            entity.trainingConfigurationSupported = event.trainingConfigurationSupported
            entity.runtimeResourceAvailable = event.runtimeResourceAvailable
            entity.runtimeAgentIdle = event.runtimeAgentIdle
            entity.rejectionReasons = event.rejectionReasons
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: RoundExecutionStartedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.roundExecutionId) ?: RoundExecutionCatalogReadModelProjection().apply {
                this.roundExecutionId = event.roundExecutionId
        }
            entity.roundExecutionId = event.roundExecutionId
            entity.executionSessionId = event.executionSessionId
            entity.executionPlanId = event.executionPlanId
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.roundId = event.roundId
            entity.roundNumber = event.roundNumber
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.baseModelId = event.baseModelId
            entity.runtimeEngineJobId = event.runtimeEngineJobId
            entity.state = RoundExecutionStateEnum.RUNNING
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: RoundExecutionStartFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.roundExecutionId) ?: RoundExecutionCatalogReadModelProjection().apply {
                this.roundExecutionId = event.roundExecutionId
        }
            entity.roundExecutionId = event.roundExecutionId
            entity.executionSessionId = event.executionSessionId
            entity.executionPlanId = event.executionPlanId
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.roundId = event.roundId
            entity.roundNumber = event.roundNumber
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.baseModelId = event.baseModelId
            entity.runtimeEngineJobId = event.runtimeEngineJobId
            entity.failureReason = event.failureReason
            entity.runtimeEngineReleaseFailureReason = event.failureReason
            entity.state = RoundExecutionStateEnum.START_FAILED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: RuntimeEngineJobObservedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.roundExecutionId) ?: RoundExecutionCatalogReadModelProjection().apply {
                this.roundExecutionId = event.roundExecutionId
        }
            entity.roundExecutionId = event.roundExecutionId
            entity.executionSessionId = event.executionSessionId
            entity.executionPlanId = event.executionPlanId
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.roundId = event.roundId
            entity.roundNumber = event.roundNumber
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.runtimeEngineJobId = event.runtimeEngineJobId
            entity.localUpdateArtifactRef = event.localUpdateArtifactRef
            entity.metricsArtifactRef = event.metricsArtifactRef
            entity.trainingLoss = event.trainingLoss
            entity.failureReason = event.failureReason
            entity.runtimeEngineObservedStatus = event.observedStatus
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: RoundExecutionCompletedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.roundExecutionId) ?: RoundExecutionCatalogReadModelProjection().apply {
                this.roundExecutionId = event.roundExecutionId
        }
            entity.roundExecutionId = event.roundExecutionId
            entity.executionSessionId = event.executionSessionId
            entity.executionPlanId = event.executionPlanId
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.roundId = event.roundId
            entity.roundNumber = event.roundNumber
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.runtimeEngineJobId = event.runtimeEngineJobId
            entity.localUpdateArtifactRef = event.localUpdateArtifactRef
            entity.metricsArtifactRef = event.metricsArtifactRef
            entity.trainingLoss = event.trainingLoss
            entity.state = RoundExecutionStateEnum.COMPLETED
            entity.completedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: RoundExecutionFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.roundExecutionId) ?: RoundExecutionCatalogReadModelProjection().apply {
                this.roundExecutionId = event.roundExecutionId
        }
            entity.roundExecutionId = event.roundExecutionId
            entity.executionSessionId = event.executionSessionId
            entity.executionPlanId = event.executionPlanId
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.roundId = event.roundId
            entity.roundNumber = event.roundNumber
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.runtimeEngineJobId = event.runtimeEngineJobId
            entity.failureReason = event.failureReason
            entity.state = RoundExecutionStateEnum.FAILED
            entity.failedAt = eventTime(message)
            entity.runtimeEngineReleaseFailureReason = event.failureReason
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: RoundExecutionStartRetryStartedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.roundExecutionId) ?: RoundExecutionCatalogReadModelProjection().apply {
                this.roundExecutionId = event.roundExecutionId
        }
            entity.roundExecutionId = event.roundExecutionId
            entity.executionSessionId = event.executionSessionId
            entity.executionPlanId = event.executionPlanId
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.roundId = event.roundId
            entity.roundNumber = event.roundNumber
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.baseModelId = event.baseModelId
            entity.runtimeEngineJobId = event.runtimeEngineJobId
            entity.retryReason = event.retryReason
            entity.state = RoundExecutionStateEnum.RETRIED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: RoundExecutionStartRetryFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.roundExecutionId) ?: RoundExecutionCatalogReadModelProjection().apply {
                this.roundExecutionId = event.roundExecutionId
        }
            entity.roundExecutionId = event.roundExecutionId
            entity.executionSessionId = event.executionSessionId
            entity.executionPlanId = event.executionPlanId
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.roundId = event.roundId
            entity.roundNumber = event.roundNumber
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.baseModelId = event.baseModelId
            entity.runtimeEngineJobId = event.runtimeEngineJobId
            entity.failureReason = event.failureReason
            entity.retryReason = event.retryReason
            entity.runtimeEngineReleaseFailureReason = event.failureReason
            entity.state = RoundExecutionStateEnum.START_FAILED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: RoundExecutionRuntimeRetryStartedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.roundExecutionId) ?: RoundExecutionCatalogReadModelProjection().apply {
                this.roundExecutionId = event.roundExecutionId
        }
            entity.roundExecutionId = event.roundExecutionId
            entity.executionSessionId = event.executionSessionId
            entity.executionPlanId = event.executionPlanId
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.roundId = event.roundId
            entity.roundNumber = event.roundNumber
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.baseModelId = event.baseModelId
            entity.runtimeEngineJobId = event.runtimeEngineJobId
            entity.retryReason = event.retryReason
            entity.state = RoundExecutionStateEnum.RETRIED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: RoundExecutionRuntimeRetryFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.roundExecutionId) ?: RoundExecutionCatalogReadModelProjection().apply {
                this.roundExecutionId = event.roundExecutionId
        }
            entity.roundExecutionId = event.roundExecutionId
            entity.executionSessionId = event.executionSessionId
            entity.executionPlanId = event.executionPlanId
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.roundId = event.roundId
            entity.roundNumber = event.roundNumber
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.baseModelId = event.baseModelId
            entity.runtimeEngineJobId = event.runtimeEngineJobId
            entity.failureReason = event.failureReason
            entity.retryReason = event.retryReason
            entity.runtimeEngineReleaseFailureReason = event.failureReason
            entity.state = RoundExecutionStateEnum.FAILED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: AgentLocalModelUpdateSubmittedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.roundExecutionId) ?: RoundExecutionCatalogReadModelProjection().apply {
                this.roundExecutionId = event.roundExecutionId
        }
            entity.roundExecutionId = event.roundExecutionId
            entity.executionSessionId = event.executionSessionId
            entity.executionPlanId = event.executionPlanId
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.roundId = event.roundId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.runtimeEngineJobId = event.runtimeEngineJobId
            entity.updateArtifactId = event.updateArtifactId
            entity.artifactRef = event.artifactRef
            entity.artifactDigest = event.artifactDigest
            entity.trainingLoss = event.trainingLoss
            entity.state = RoundExecutionStateEnum.UPDATE_SUBMITTED
            entity.localUpdateArtifactRef = event.artifactRef
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: RuntimeEngineJobReleasedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.roundExecutionId) ?: RoundExecutionCatalogReadModelProjection().apply {
                this.roundExecutionId = event.roundExecutionId
        }
            entity.roundExecutionId = event.roundExecutionId
            entity.runtimeEngineJobId = event.runtimeEngineJobId
            entity.state = RoundExecutionStateEnum.RUNTIME_ENGINE_RELEASED
            entity.runtimeEngineReleased = true
            entity.runtimeEngineObservedStatus = "RuntimeEngineReleased"
            entity.runtimeEngineReleaseFailureReason = null
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: RuntimeEngineJobReleaseFailedOrSkippedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.roundExecutionId) ?: RoundExecutionCatalogReadModelProjection().apply {
                this.roundExecutionId = event.roundExecutionId
        }
            entity.roundExecutionId = event.roundExecutionId
            entity.runtimeEngineJobId = event.runtimeEngineJobId
            entity.runtimeEngineReleaseFailureReason = event.runtimeEngineReleaseFailureReason
            entity.state = RoundExecutionStateEnum.RUNTIME_ENGINE_RELEASE_HANDLED
            entity.runtimeEngineObservedStatus = "RuntimeEngineReleaseHandled"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: RuntimeEngineJobReleaseFailedOrSkippedAfterStartFailureEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.roundExecutionId) ?: RoundExecutionCatalogReadModelProjection().apply {
                this.roundExecutionId = event.roundExecutionId
        }
            entity.roundExecutionId = event.roundExecutionId
            entity.runtimeEngineJobId = event.runtimeEngineJobId
            entity.runtimeEngineReleaseFailureReason = event.runtimeEngineReleaseFailureReason
            entity.state = RoundExecutionStateEnum.RUNTIME_ENGINE_RELEASE_HANDLED
            entity.runtimeEngineObservedStatus = "RuntimeEngineReleaseHandled"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: RuntimeEngineJobReleaseFailedOrSkippedAfterRetryEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.roundExecutionId) ?: RoundExecutionCatalogReadModelProjection().apply {
                this.roundExecutionId = event.roundExecutionId
        }
            entity.roundExecutionId = event.roundExecutionId
            entity.runtimeEngineJobId = event.runtimeEngineJobId
            entity.runtimeEngineReleaseFailureReason = event.runtimeEngineReleaseFailureReason
            entity.state = RoundExecutionStateEnum.RUNTIME_ENGINE_RELEASE_HANDLED
            entity.runtimeEngineObservedStatus = "RuntimeEngineReleaseHandled"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: RuntimeEngineJobReleaseFailedOrSkippedAfterRuntimeRetryEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.roundExecutionId) ?: RoundExecutionCatalogReadModelProjection().apply {
                this.roundExecutionId = event.roundExecutionId
        }
            entity.roundExecutionId = event.roundExecutionId
            entity.runtimeEngineJobId = event.runtimeEngineJobId
            entity.runtimeEngineReleaseFailureReason = event.runtimeEngineReleaseFailureReason
            entity.state = RoundExecutionStateEnum.RUNTIME_ENGINE_RELEASE_HANDLED
            entity.runtimeEngineObservedStatus = "RuntimeEngineReleaseHandled"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    private fun eventTime(message: EventMessage): LocalDateTime =
        LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)

}

@Configuration(proxyBeanMethods = false)
class RoundExecutionCatalogReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(RoundExecutionCatalogReadModelProjectionUpdater::class)
    fun defaultRoundExecutionCatalogReadModelProjectionUpdater(
        repository: RoundExecutionCatalogReadModelRepository
    ): RoundExecutionCatalogReadModelProjectionUpdater =
        DefaultRoundExecutionCatalogReadModelProjectionUpdater(repository)
}

@Namespace("readmodel-round-execution-catalog")
@Component
class RoundExecutionCatalogReadModelProjector(
    private val updater: RoundExecutionCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: ExecutionPlanReceivedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: ExecutionPlanAcceptedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: ExecutionPlanRejectedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RoundExecutionStartedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RoundExecutionStartFailedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeEngineJobObservedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RoundExecutionCompletedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RoundExecutionFailedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RoundExecutionStartRetryStartedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RoundExecutionStartRetryFailedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RoundExecutionRuntimeRetryStartedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RoundExecutionRuntimeRetryFailedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: AgentLocalModelUpdateSubmittedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeEngineJobReleasedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeEngineJobReleaseFailedOrSkippedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeEngineJobReleaseFailedOrSkippedAfterStartFailureEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeEngineJobReleaseFailedOrSkippedAfterRetryEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeEngineJobReleaseFailedOrSkippedAfterRuntimeRetryEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
