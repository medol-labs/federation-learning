package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class RoundExecutionRuntimeRetryStartedEvent(
    val roundExecutionId: UUID,
    val executionSessionId: UUID,
    @EventTag(key = "executionPlanId")
    val executionPlanId: UUID,
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val roundId: UUID,
    val roundNumber: Int,
    val runtimeId: UUID,
    val organizationId: UUID,
    val featureSchemaId: UUID,
    val baseModelVersionId: UUID,
    val runtimeEngineJobId: String,
    val retryReason: String
)
