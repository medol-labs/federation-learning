package tech.medo.runtimemonitoring.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class RuntimeNodeInventoryReportedEvent(
    @EventTag(key = "nodeId")
    val nodeId: UUID,
    val runtimeNodeInventoryReportId: UUID,
    val organizationId: UUID,
    val runtimeInfrastructureId: UUID,
    val runtimeAgentId: UUID,
    val runtimeNodeName: String,
    val infrastructureNodeId: String?,
    val runtimeNodeRole: String,
    val nodeReady: Boolean,
    val runtimeEngineVersion: String?,
    val containerEngineVersion: String?,
    val operatingSystem: String?,
    val architecture: String,
    val inventoryHash: String
)
