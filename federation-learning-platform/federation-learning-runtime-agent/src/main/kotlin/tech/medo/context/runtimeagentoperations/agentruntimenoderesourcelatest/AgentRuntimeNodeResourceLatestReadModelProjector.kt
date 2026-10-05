package tech.medo.runtimeagentoperations.agentruntimenoderesourcelatest

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata


import tech.medo.runtimeagentoperations.events.AgentRuntimeNodeResourceTelemetryReportedEvent



interface AgentRuntimeNodeResourceLatestReadModelProjectionUpdater {
    fun update(
        event: AgentRuntimeNodeResourceTelemetryReportedEvent,
        message: EventMessage
    )
}

open class DefaultAgentRuntimeNodeResourceLatestReadModelProjectionUpdater(
    private val repository: AgentRuntimeNodeResourceLatestReadModelRepository
) : AgentRuntimeNodeResourceLatestReadModelProjectionUpdater {
    @Transactional
    open override fun update(
        event: AgentRuntimeNodeResourceTelemetryReportedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.nodeId) ?: AgentRuntimeNodeResourceLatestReadModelProjection().apply {
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
            entity.telemetryRetentionPolicy = event.telemetryRetentionPolicy
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

}

@Configuration(proxyBeanMethods = false)
class AgentRuntimeNodeResourceLatestReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(AgentRuntimeNodeResourceLatestReadModelProjectionUpdater::class)
    fun defaultAgentRuntimeNodeResourceLatestReadModelProjectionUpdater(
        repository: AgentRuntimeNodeResourceLatestReadModelRepository
    ): AgentRuntimeNodeResourceLatestReadModelProjectionUpdater =
        DefaultAgentRuntimeNodeResourceLatestReadModelProjectionUpdater(repository)
}

@Namespace("readmodel-agent-runtime-node-resource-latest")
@Component
class AgentRuntimeNodeResourceLatestReadModelProjector(
    private val updater: AgentRuntimeNodeResourceLatestReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: AgentRuntimeNodeResourceTelemetryReportedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
