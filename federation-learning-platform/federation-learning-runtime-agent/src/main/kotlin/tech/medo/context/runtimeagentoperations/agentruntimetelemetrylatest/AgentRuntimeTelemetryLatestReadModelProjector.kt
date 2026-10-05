package tech.medo.runtimeagentoperations.agentruntimetelemetrylatest

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata


import tech.medo.runtimeagentoperations.events.AgentRuntimeTelemetryReportedEvent



interface AgentRuntimeTelemetryLatestReadModelProjectionUpdater {
    fun update(
        event: AgentRuntimeTelemetryReportedEvent,
        message: EventMessage
    )
}

open class DefaultAgentRuntimeTelemetryLatestReadModelProjectionUpdater(
    private val repository: AgentRuntimeTelemetryLatestReadModelRepository
) : AgentRuntimeTelemetryLatestReadModelProjectionUpdater {
    @Transactional
    open override fun update(
        event: AgentRuntimeTelemetryReportedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.nodeId) ?: AgentRuntimeTelemetryLatestReadModelProjection().apply {
                this.nodeId = event.nodeId
        }
            entity.nodeId = event.nodeId
            entity.runtimeAgentId = event.runtimeAgentId
            entity.federationId = event.federationId
            entity.trainingJobId = event.trainingJobId
            entity.roundExecutionId = event.roundExecutionId
            entity.cpuLoad = event.cpuLoad
            entity.gpuLoad = event.gpuLoad
            entity.memoryLoad = event.memoryLoad
            entity.lastHeartbeatAt = event.lastHeartbeatAt
            entity.telemetryRetentionPolicy = event.telemetryRetentionPolicy
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

}

@Configuration(proxyBeanMethods = false)
class AgentRuntimeTelemetryLatestReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(AgentRuntimeTelemetryLatestReadModelProjectionUpdater::class)
    fun defaultAgentRuntimeTelemetryLatestReadModelProjectionUpdater(
        repository: AgentRuntimeTelemetryLatestReadModelRepository
    ): AgentRuntimeTelemetryLatestReadModelProjectionUpdater =
        DefaultAgentRuntimeTelemetryLatestReadModelProjectionUpdater(repository)
}

@Namespace("readmodel-agent-runtime-telemetry-latest")
@Component
class AgentRuntimeTelemetryLatestReadModelProjector(
    private val updater: AgentRuntimeTelemetryLatestReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: AgentRuntimeTelemetryReportedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
