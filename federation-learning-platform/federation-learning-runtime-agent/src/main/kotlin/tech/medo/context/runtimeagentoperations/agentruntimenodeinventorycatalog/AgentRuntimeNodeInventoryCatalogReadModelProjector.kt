package tech.medo.runtimeagentoperations.agentruntimenodeinventorycatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata


import tech.medo.runtimeagentoperations.events.AgentRuntimeNodeInventoryReportedEvent



interface AgentRuntimeNodeInventoryCatalogReadModelProjectionUpdater {
    fun update(
        event: AgentRuntimeNodeInventoryReportedEvent,
        message: EventMessage
    )
}

@Component
@ConditionalOnMissingBean(AgentRuntimeNodeInventoryCatalogReadModelProjectionUpdater::class)
class DefaultAgentRuntimeNodeInventoryCatalogReadModelProjectionUpdater(
    private val repository: AgentRuntimeNodeInventoryCatalogReadModelRepository
) : AgentRuntimeNodeInventoryCatalogReadModelProjectionUpdater {
    @Transactional
    override fun update(
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
            entity.organizationName = event.organizationName
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

@Namespace("readmodel-agent-runtime-node-inventory-catalog")
@Component
class AgentRuntimeNodeInventoryCatalogReadModelProjector(
    private val updater: AgentRuntimeNodeInventoryCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: AgentRuntimeNodeInventoryReportedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
