package tech.medo.runtimeagentoperations.agentruntimenodeinventory

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.runtimeagentoperations.events.AgentRuntimeNodeInventoryReportedEvent
import tech.medo.runtimeagentoperations.domain.states.AgentRuntimeNodeInventoryStateEnum

import java.util.UUID;


@EventSourced(idType = UUID::class, tagKey = AgentRuntimeNodeInventoryTags.RUNTIME_NODE_INVENTORY_REPORT_ID)
class AgentRuntimeNodeInventoryState @EntityCreator constructor() {

    var currentState: AgentRuntimeNodeInventoryStateEnum? = null
    private var runtimeNodeInventoryReportId: UUID? = null
    private var organizationId: UUID? = null
    private var runtimeInfrastructureId: UUID? = null
    private var runtimeAgentId: UUID? = null
    private var runtimeNodeName: String? = null
    private var infrastructureNodeId: String? = null
    private var runtimeNodeRole: String? = null
    private var nodeReady: Boolean? = null
    private var runtimeEngineVersion: String? = null
    private var containerEngineVersion: String? = null
    private var operatingSystem: String? = null
    private var architecture: String? = null
    private var inventoryHash: String? = null

    @EventSourcingHandler
    fun evolve(event: AgentRuntimeNodeInventoryReportedEvent): AgentRuntimeNodeInventoryState = apply {
        currentState = AgentRuntimeNodeInventoryStateEnum.REPORTED
        runtimeNodeInventoryReportId = event.runtimeNodeInventoryReportId
        organizationId = event.organizationId
        runtimeInfrastructureId = event.runtimeInfrastructureId
        runtimeAgentId = event.runtimeAgentId
        runtimeNodeName = event.runtimeNodeName
        infrastructureNodeId = event.infrastructureNodeId
        runtimeNodeRole = event.runtimeNodeRole
        nodeReady = event.nodeReady
        runtimeEngineVersion = event.runtimeEngineVersion
        containerEngineVersion = event.containerEngineVersion
        operatingSystem = event.operatingSystem
        architecture = event.architecture
        inventoryHash = event.inventoryHash
    }
}
