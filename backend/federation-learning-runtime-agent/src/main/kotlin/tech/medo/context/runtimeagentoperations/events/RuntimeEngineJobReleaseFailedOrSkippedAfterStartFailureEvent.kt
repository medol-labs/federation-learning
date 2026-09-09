package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class RuntimeEngineJobReleaseFailedOrSkippedAfterStartFailureEvent(
    val roundExecutionId: UUID,
    val runtimeEngineJobId: String?,
    val runtimeEngineReleaseFailureReason: String?,
    @EventTag(key = "executionPlanId")
    val executionPlanId: UUID
)
