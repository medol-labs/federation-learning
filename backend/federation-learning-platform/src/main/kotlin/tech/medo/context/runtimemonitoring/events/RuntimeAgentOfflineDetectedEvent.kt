package tech.medo.runtimemonitoring.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class RuntimeAgentOfflineDetectedEvent(
    @EventTag(key = "nodeId")
    val nodeId: UUID,
    val runtimeAgentId: UUID,
    val offlineReason: String
)
