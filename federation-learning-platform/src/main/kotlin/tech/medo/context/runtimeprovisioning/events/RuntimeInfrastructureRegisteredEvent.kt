package tech.medo.runtimeprovisioning.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class RuntimeInfrastructureRegisteredEvent(
    @EventTag(key = "runtimeInfrastructureId")
    val runtimeInfrastructureId: UUID
)
