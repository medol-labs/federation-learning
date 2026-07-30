package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class RuntimeEngineJobReleasedEvent(
    val roundExecutionId: UUID,
    val runtimeEngineJobId: String,
    @EventTag(key = "executionPlanId")
    val executionPlanId: UUID
)
