package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;


/* TODO: provide values for selection tags: executionPlanId = executionPlanId */

@Event
data class RuntimeEngineJobReleaseFailedOrSkippedAfterStartFailureEvent(
    val roundExecutionId: UUID,
    val runtimeEngineJobId: String?,
    val failureReason: String?
)
