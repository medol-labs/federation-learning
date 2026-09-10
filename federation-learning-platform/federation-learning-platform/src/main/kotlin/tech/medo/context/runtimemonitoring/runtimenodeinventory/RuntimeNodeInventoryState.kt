package tech.medo.runtimemonitoring.runtimenodeinventory

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.runtimemonitoring.events.RuntimeNodeInventoryReportedEvent
import tech.medo.runtimemonitoring.domain.states.RuntimeNodeInventoryStateEnum

import java.util.UUID;


@EventSourced(idType = UUID::class, tagKey = RuntimeNodeInventoryTags.NODE_ID)
class RuntimeNodeInventoryState @EntityCreator constructor() {

    var currentState: RuntimeNodeInventoryStateEnum? = null
    var nodeId: UUID? = null
    var runtimeNodeInventoryReportId: UUID? = null
    var organizationId: UUID? = null
    var organizationName: String? = null
    var runtimeInfrastructureId: UUID? = null
    var runtimeAgentId: UUID? = null
    var runtimeName: String? = null
    var runtimeNodeName: String? = null
    var infrastructureNodeId: String? = null
    var runtimeNodeRole: String? = null
    var nodeReady: Boolean? = null
    var runtimeEngineVersion: String? = null
    var containerEngineVersion: String? = null
    var operatingSystem: String? = null
    var architecture: String? = null
    var inventoryHash: String? = null

    @EventSourcingHandler
    fun evolve(event: RuntimeNodeInventoryReportedEvent): RuntimeNodeInventoryState = apply {
        currentState = RuntimeNodeInventoryStateEnum.REPORTED
        nodeId = event.nodeId
        runtimeNodeInventoryReportId = event.runtimeNodeInventoryReportId
        organizationId = event.organizationId
        organizationName = event.organizationName
        runtimeInfrastructureId = event.runtimeInfrastructureId
        runtimeAgentId = event.runtimeAgentId
        runtimeName = event.runtimeName
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
