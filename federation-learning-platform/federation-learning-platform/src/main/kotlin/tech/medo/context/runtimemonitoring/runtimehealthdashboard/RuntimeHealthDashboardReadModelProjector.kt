package tech.medo.runtimemonitoring.runtimehealthdashboard

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.federationmanagement.events.FederationCreatedEvent
import tech.medo.trainingorchestration.events.TrainingJobCreatedEvent
import tech.medo.runtimemonitoring.events.RuntimeAgentOfflineDetectedEvent
import tech.medo.runtimemonitoring.events.RuntimeAgentRecoveredEvent
import tech.medo.runtimemonitoring.events.RuntimeNodeResourcePressureDetectedEvent
import tech.medo.runtimemonitoring.events.RuntimeNodeInventoryReportedEvent
import tech.medo.runtimemonitoring.events.RuntimeNodeCapacityChangedEvent
import tech.medo.runtimemonitoring.events.TrainingAlertRaisedEvent



interface RuntimeHealthDashboardReadModelProjectionUpdater {
    fun update(
        event: FederationCreatedEvent,
        message: EventMessage
    )

    fun update(
        event: TrainingJobCreatedEvent,
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
        event: RuntimeNodeResourcePressureDetectedEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeNodeInventoryReportedEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeNodeCapacityChangedEvent,
        message: EventMessage
    )

    fun update(
        event: TrainingAlertRaisedEvent,
        message: EventMessage
    )
}

@Component
@ConditionalOnMissingBean(RuntimeHealthDashboardReadModelProjectionUpdater::class)
class DefaultRuntimeHealthDashboardReadModelProjectionUpdater(
    private val repository: RuntimeHealthDashboardReadModelRepository
) : RuntimeHealthDashboardReadModelProjectionUpdater {
    override fun update(
        event: FederationCreatedEvent,
        message: EventMessage
    ) {
        // Skipped: FederationCreatedEvent does not provide enough key fields to locate RuntimeHealthDashboardReadModelProjection.
    }

    override fun update(
        event: TrainingJobCreatedEvent,
        message: EventMessage
    ) {
        // Skipped: TrainingJobCreatedEvent does not provide enough key fields to locate RuntimeHealthDashboardReadModelProjection.
    }

    @Transactional
    override fun update(
        event: RuntimeAgentOfflineDetectedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.nodeId) ?: RuntimeHealthDashboardReadModelProjection().apply {
                this.nodeId = event.nodeId
        }
            entity.nodeId = event.nodeId
            entity.runtimeAgentId = event.runtimeAgentId
            entity.federationId = event.federationId
            entity.trainingJobId = event.trainingJobId
            entity.roundExecutionId = event.roundExecutionId
            entity.federationName = event.federationName
            entity.trainingJobObjective = event.trainingJobObjective
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: RuntimeAgentRecoveredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.nodeId) ?: RuntimeHealthDashboardReadModelProjection().apply {
                this.nodeId = event.nodeId
        }
            entity.nodeId = event.nodeId
            entity.runtimeAgentId = event.runtimeAgentId
            entity.federationId = event.federationId
            entity.trainingJobId = event.trainingJobId
            entity.roundExecutionId = event.roundExecutionId
            entity.federationName = event.federationName
            entity.trainingJobObjective = event.trainingJobObjective
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: RuntimeNodeResourcePressureDetectedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.nodeId) ?: RuntimeHealthDashboardReadModelProjection().apply {
                this.nodeId = event.nodeId
        }
            entity.nodeId = event.nodeId
            entity.runtimeAgentId = event.runtimeAgentId
            entity.federationId = event.federationId
            entity.trainingJobId = event.trainingJobId
            entity.roundExecutionId = event.roundExecutionId
            entity.federationName = event.federationName
            entity.trainingJobObjective = event.trainingJobObjective
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: RuntimeNodeInventoryReportedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.nodeId) ?: RuntimeHealthDashboardReadModelProjection().apply {
                this.nodeId = event.nodeId
        }
            entity.nodeId = event.nodeId
            entity.runtimeAgentId = event.runtimeAgentId
            entity.nodeReady = event.nodeReady
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: RuntimeNodeCapacityChangedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.nodeId) ?: RuntimeHealthDashboardReadModelProjection().apply {
                this.nodeId = event.nodeId
        }
            entity.nodeId = event.nodeId
            entity.runtimeAgentId = event.runtimeAgentId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: TrainingAlertRaisedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.nodeId) ?: RuntimeHealthDashboardReadModelProjection().apply {
                this.nodeId = event.nodeId
        }
            entity.nodeId = event.nodeId
            entity.trainingJobId = event.trainingJobId
            entity.trainingJobObjective = event.trainingJobObjective
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

}

@Namespace("readmodel-runtime-health-dashboard")
@Component
class RuntimeHealthDashboardReadModelProjector(
    private val updater: RuntimeHealthDashboardReadModelProjectionUpdater
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
        event: TrainingJobCreatedEvent,
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
        event: RuntimeNodeResourcePressureDetectedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeNodeInventoryReportedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeNodeCapacityChangedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: TrainingAlertRaisedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
