package tech.medo.runtimegovernance.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class RuntimeCapabilitiesDetectedEvent(
    @EventTag(key = "runtimeId")
    val runtimeId: UUID,
    val capabilityTypes: List<String>
)
