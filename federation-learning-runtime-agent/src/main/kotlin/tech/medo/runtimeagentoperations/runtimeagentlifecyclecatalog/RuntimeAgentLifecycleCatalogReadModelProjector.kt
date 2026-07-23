package tech.medo.runtimeagentoperations.runtimeagentlifecyclecatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.runtimeagentoperations.events.RuntimeAgentStartedEvent
import tech.medo.runtimeagentoperations.events.RuntimeInstanceSelfCheckPassedEvent


@Component
class RuntimeAgentLifecycleCatalogReadModelProjector(private val repository: RuntimeAgentLifecycleCatalogReadModelRepository) {
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
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }
}
