package tech.medo.runtimemonitoring.runtimenoderesourcelatest

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata


import tech.medo.runtimemonitoring.events.RuntimeNodeResourceTelemetryRecordedEvent



interface RuntimeNodeResourceLatestReadModelProjectionUpdater {
    fun update(
        event: RuntimeNodeResourceTelemetryRecordedEvent,
        message: EventMessage
    )
}

open class DefaultRuntimeNodeResourceLatestReadModelProjectionUpdater(
    private val repository: RuntimeNodeResourceLatestReadModelRepository
) : RuntimeNodeResourceLatestReadModelProjectionUpdater {
    @Transactional
    open override fun update(
        event: RuntimeNodeResourceTelemetryRecordedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.nodeId) ?: RuntimeNodeResourceLatestReadModelProjection().apply {
                this.nodeId = event.nodeId
        }
            entity.nodeId = event.nodeId
            entity.runtimeAgentId = event.runtimeAgentId
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.runtimeNodeName = event.runtimeNodeName
            entity.nodeReady = event.nodeReady
            entity.allocatableCpuCores = event.allocatableCpuCores
            entity.allocatableMemoryGb = event.allocatableMemoryGb
            entity.allocatableGpuCount = event.allocatableGpuCount
            entity.allocatedCpuCores = event.allocatedCpuCores
            entity.allocatedMemoryGb = event.allocatedMemoryGb
            entity.allocatedGpuCount = event.allocatedGpuCount
            entity.availableCpuCores = event.availableCpuCores
            entity.availableMemoryGb = event.availableMemoryGb
            entity.availableGpuCount = event.availableGpuCount
            entity.runningWorkloadCount = event.runningWorkloadCount
            entity.workloadCapacity = event.workloadCapacity
            entity.observedAt = event.observedAt
            entity.lastResourceSnapshotAt = event.lastResourceSnapshotAt
            entity.telemetryRetentionPolicy = event.telemetryRetentionPolicy
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

}

@Configuration(proxyBeanMethods = false)
class RuntimeNodeResourceLatestReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(RuntimeNodeResourceLatestReadModelProjectionUpdater::class)
    fun defaultRuntimeNodeResourceLatestReadModelProjectionUpdater(
        repository: RuntimeNodeResourceLatestReadModelRepository
    ): RuntimeNodeResourceLatestReadModelProjectionUpdater =
        DefaultRuntimeNodeResourceLatestReadModelProjectionUpdater(repository)
}

@Namespace("readmodel-runtime-node-resource-latest")
@Component
class RuntimeNodeResourceLatestReadModelProjector(
    private val updater: RuntimeNodeResourceLatestReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: RuntimeNodeResourceTelemetryRecordedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
