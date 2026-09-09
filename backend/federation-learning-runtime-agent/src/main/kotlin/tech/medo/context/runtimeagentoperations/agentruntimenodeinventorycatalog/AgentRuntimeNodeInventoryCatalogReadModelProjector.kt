package tech.medo.runtimeagentoperations.agentruntimenodeinventorycatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.runtimeagentoperations.events.AgentRuntimeNodeInventoryReportedEvent



@Component
class AgentRuntimeNodeInventoryCatalogReadModelProjector(private val repository: AgentRuntimeNodeInventoryCatalogReadModelRepository) {
    @EventHandler
    fun on(
        event: AgentRuntimeNodeInventoryReportedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeNodeInventoryReportId) ?: AgentRuntimeNodeInventoryCatalogReadModelProjection().apply {
                this.runtimeNodeInventoryReportId = event.runtimeNodeInventoryReportId
        }
            entity.runtimeNodeInventoryReportId = event.runtimeNodeInventoryReportId
            entity.organizationId = event.organizationId
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.runtimeAgentId = event.runtimeAgentId
            entity.runtimeNodeName = event.runtimeNodeName
            entity.infrastructureNodeId = event.infrastructureNodeId
            entity.runtimeNodeRole = event.runtimeNodeRole
            entity.nodeReady = event.nodeReady
            entity.runtimeEngineVersion = event.runtimeEngineVersion
            entity.containerEngineVersion = event.containerEngineVersion
            entity.operatingSystem = event.operatingSystem
            entity.architecture = event.architecture
            entity.inventoryHash = event.inventoryHash
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

}
