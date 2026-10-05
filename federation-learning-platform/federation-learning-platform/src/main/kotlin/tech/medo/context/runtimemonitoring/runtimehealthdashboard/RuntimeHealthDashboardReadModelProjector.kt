package tech.medo.runtimemonitoring.runtimehealthdashboard

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata


import tech.medo.runtimemonitoring.events.RuntimeTelemetryRecordedEvent
import tech.medo.runtimemonitoring.events.RuntimeAgentOfflineDetectedEvent
import tech.medo.runtimemonitoring.events.RuntimeAgentRecoveredEvent
import tech.medo.runtimemonitoring.events.RuntimeNodeResourcePressureDetectedEvent
import tech.medo.runtimemonitoring.events.RuntimeNodeInventoryReportedEvent
import tech.medo.runtimemonitoring.events.RuntimeNodeResourceTelemetryRecordedEvent
import tech.medo.runtimemonitoring.events.TrainingAlertRaisedEvent



interface RuntimeHealthDashboardReadModelProjectionUpdater {
    fun update(
        event: RuntimeTelemetryRecordedEvent,
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
        event: RuntimeNodeResourceTelemetryRecordedEvent,
        message: EventMessage
    )

    fun update(
        event: TrainingAlertRaisedEvent,
        message: EventMessage
    )
}

open class DefaultRuntimeHealthDashboardReadModelProjectionUpdater(
    private val repository: RuntimeHealthDashboardReadModelRepository
) : RuntimeHealthDashboardReadModelProjectionUpdater {
    @Transactional
    open override fun update(
        event: RuntimeTelemetryRecordedEvent,
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
            entity.cpuLoad = event.cpuLoad
            entity.gpuLoad = event.gpuLoad
            entity.memoryLoad = event.memoryLoad
            entity.healthStatus = event.healthStatus
            entity.lastHeartbeatAt = event.lastHeartbeatAt
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
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
    open override fun update(
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
    open override fun update(
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
    open override fun update(
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
    open override fun update(
        event: RuntimeNodeResourceTelemetryRecordedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.nodeId) ?: RuntimeHealthDashboardReadModelProjection().apply {
                this.nodeId = event.nodeId
        }
            entity.nodeId = event.nodeId
            entity.runtimeAgentId = event.runtimeAgentId
            entity.nodeReady = event.nodeReady
            entity.availableCpuCores = event.availableCpuCores
            entity.availableMemoryGb = event.availableMemoryGb
            entity.availableGpuCount = event.availableGpuCount
            entity.runningWorkloadCount = event.runningWorkloadCount
            entity.workloadCapacity = event.workloadCapacity
            entity.lastResourceSnapshotAt = event.lastResourceSnapshotAt
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
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

@Configuration(proxyBeanMethods = false)
class RuntimeHealthDashboardReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(RuntimeHealthDashboardReadModelProjectionUpdater::class)
    fun defaultRuntimeHealthDashboardReadModelProjectionUpdater(
        repository: RuntimeHealthDashboardReadModelRepository
    ): RuntimeHealthDashboardReadModelProjectionUpdater =
        DefaultRuntimeHealthDashboardReadModelProjectionUpdater(repository)
}

@Namespace("readmodel-runtime-health-dashboard")
@Component
class RuntimeHealthDashboardReadModelProjector(
    private val updater: RuntimeHealthDashboardReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: RuntimeTelemetryRecordedEvent,
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
        event: RuntimeNodeResourceTelemetryRecordedEvent,
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
