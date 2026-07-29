package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class RuntimeAgentBootstrapConfigurationLoadFailedEvent(
    @EventTag(key = "bootstrapRequestId")
    val bootstrapRequestId: UUID,
    val failureReason: String
)
