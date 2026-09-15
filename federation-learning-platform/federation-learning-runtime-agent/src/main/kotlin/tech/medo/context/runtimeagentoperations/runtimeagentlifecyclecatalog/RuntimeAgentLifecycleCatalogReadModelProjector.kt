package tech.medo.runtimeagentoperations.runtimeagentlifecyclecatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.runtimeagentoperations.events.RuntimeAgentBootstrapConfigurationLoadedEvent
import tech.medo.runtimeagentoperations.events.RuntimeAgentBootstrapConfigurationLoadFailedEvent
import tech.medo.runtimeagentoperations.events.RuntimeAgentStartedEvent
import tech.medo.runtimeagentoperations.events.RuntimeInstanceSelfCheckPassedEvent

import java.time.LocalDateTime
import java.time.ZoneOffset


@Namespace("readmodel-runtime-agent-lifecycle-catalog")
@Component
class RuntimeAgentLifecycleCatalogReadModelProjector(private val repository: RuntimeAgentLifecycleCatalogReadModelRepository) {
    @EventHandler
    fun on(
        event: RuntimeAgentBootstrapConfigurationLoadedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeAgentId) ?: RuntimeAgentLifecycleCatalogReadModelProjection().apply {
                this.runtimeAgentId = event.runtimeAgentId
        }
            entity.runtimeAgentId = event.runtimeAgentId
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.agentVersion = event.agentVersion
            entity.bootstrapConfigurationLoaded = event.bootstrapConfigurationLoaded
            entity.bootstrapFailureReason = null
            entity.bootstrapFailedAt = null
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(event: RuntimeAgentBootstrapConfigurationLoadFailedEvent) {
        // Skipped: RuntimeAgentBootstrapConfigurationLoadFailedEvent does not provide enough key fields to locate RuntimeAgentLifecycleCatalogReadModelProjection.
    }

    @EventHandler
    fun on(
        event: RuntimeAgentStartedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeAgentId) ?: RuntimeAgentLifecycleCatalogReadModelProjection().apply {
                this.runtimeAgentId = event.runtimeAgentId
        }
            entity.runtimeAgentId = event.runtimeAgentId
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.agentVersion = event.agentVersion
            entity.startedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeInstanceSelfCheckPassedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeAgentId) ?: RuntimeAgentLifecycleCatalogReadModelProjection().apply {
                this.runtimeAgentId = event.runtimeAgentId
        }
            entity.runtimeAgentId = event.runtimeAgentId
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.agentVersion = event.agentVersion
            entity.runtimeAgentSelfCheckPassed = event.runtimeAgentSelfCheckPassed
            entity.configurationLoaded = event.configurationLoaded
            entity.secretStoreAccessible = event.secretStoreAccessible
            entity.runtimeEngineAdapterReady = event.runtimeEngineAdapterReady
            entity.modelRepositoryClientReady = event.modelRepositoryClientReady
            entity.localDatasetBindingStoreReady = event.localDatasetBindingStoreReady
            entity.workingDirectoryWritable = event.workingDirectoryWritable
            entity.bootstrapConfigurationLoaded = event.configurationLoaded
            entity.readyAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    private fun eventTime(message: EventMessage): LocalDateTime =
        LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)

}
