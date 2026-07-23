package tech.medo.runtimemonitoring.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class RuntimeNodeCapacityChangedEvent(
    @EventTag(key = "nodeId")
    val nodeId: UUID,
    val runtimeAgentId: UUID,
    val previousCapacityHash: String?,
    val currentCapacityHash: String,
    val allocatableCpuCores: Int,
    val allocatableMemoryGb: Int,
    val allocatableGpuCount: Int
)
