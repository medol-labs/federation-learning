package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class RoundExecutionFailedEvent(
    val roundExecutionId: UUID,
    val executionSessionId: UUID,
    @EventTag(key = "executionPlanId")
    val executionPlanId: UUID,
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val roundId: UUID,
    val runtimeId: UUID,
    val runtimeEngineJobId: String?,
    val failureReason: String
)
