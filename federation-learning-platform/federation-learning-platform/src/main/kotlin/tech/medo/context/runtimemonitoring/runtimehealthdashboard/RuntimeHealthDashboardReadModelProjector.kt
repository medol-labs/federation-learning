package tech.medo.runtimemonitoring.runtimehealthdashboard

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.federationmanagement.events.FederationCreatedEvent
import tech.medo.trainingorchestration.events.TrainingJobCreatedEvent
import tech.medo.runtimemonitoring.events.RuntimeAgentOfflineDetectedEvent
import tech.medo.runtimemonitoring.events.RuntimeAgentRecoveredEvent
import tech.medo.runtimemonitoring.events.RuntimeNodeResourcePressureDetectedEvent
import tech.medo.runtimemonitoring.events.RuntimeNodeInventoryReportedEvent
import tech.medo.runtimemonitoring.events.RuntimeNodeCapacityChangedEvent
import tech.medo.runtimemonitoring.events.TrainingAlertRaisedEvent



@Namespace("readmodel-runtime-health-dashboard")
@Component
class RuntimeHealthDashboardReadModelProjector(private val repository: RuntimeHealthDashboardReadModelRepository) {
    @EventHandler
    fun on(event: FederationCreatedEvent) {
        // Skipped: FederationCreatedEvent does not provide enough key fields to locate RuntimeHealthDashboardReadModelProjection.
    }

    @EventHandler
    fun on(event: TrainingJobCreatedEvent) {
        // Skipped: TrainingJobCreatedEvent does not provide enough key fields to locate RuntimeHealthDashboardReadModelProjection.
    }

    @EventHandler
    fun on(
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

    @EventHandler
    fun on(
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

    @EventHandler
    fun on(
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

    @EventHandler
    fun on(
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

    @EventHandler
    fun on(
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

    @EventHandler
    fun on(
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
