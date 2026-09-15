package tech.medo.runtimemonitoring.runtimenodeinventoryview

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.organizationmanagement.events.OrganizationRegisteredEvent
import tech.medo.runtimeprovisioning.events.RuntimeInstallationPlanCreatedEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureRegisteredEvent
import tech.medo.runtimemonitoring.events.RuntimeNodeInventoryReportedEvent



@Namespace("readmodel-runtime-node-inventory-view")
@Component
class RuntimeNodeInventoryViewReadModelProjector(private val repository: RuntimeNodeInventoryViewReadModelRepository) {
    @EventHandler
    fun on(event: OrganizationRegisteredEvent) {
        // Skipped: OrganizationRegisteredEvent does not provide enough key fields to locate RuntimeNodeInventoryViewReadModelProjection.
    }

    @EventHandler
    fun on(event: RuntimeInstallationPlanCreatedEvent) {
        // Skipped: RuntimeInstallationPlanCreatedEvent does not provide enough key fields to locate RuntimeNodeInventoryViewReadModelProjection.
    }

    @EventHandler
    fun on(event: RuntimeInfrastructureRegisteredEvent) {
        // Skipped: RuntimeInfrastructureRegisteredEvent does not provide enough key fields to locate RuntimeNodeInventoryViewReadModelProjection.
    }

    @EventHandler
    fun on(
        event: RuntimeNodeInventoryReportedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.nodeId) ?: RuntimeNodeInventoryViewReadModelProjection().apply {
                this.nodeId = event.nodeId
        }
            entity.nodeId = event.nodeId
            entity.runtimeNodeInventoryReportId = event.runtimeNodeInventoryReportId
            entity.organizationId = event.organizationId
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.runtimeAgentId = event.runtimeAgentId
            entity.organizationName = event.organizationName
            entity.runtimeName = event.runtimeName
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
