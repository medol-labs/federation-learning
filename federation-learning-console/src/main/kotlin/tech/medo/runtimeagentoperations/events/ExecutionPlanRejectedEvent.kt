package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class ExecutionPlanRejectedEvent(
    @EventTag(key = "executionPlanId")
    val executionPlanId: UUID,
    val executionSessionId: UUID,
    val trainingJobId: UUID,
    val runtimeId: UUID,
    val localExecutionRequirementsSatisfied: Boolean,
    val runtimeIdentityMatched: Boolean,
    val runtimeDatasetBindingAvailable: Boolean,
    val datasetAccessValidated: Boolean,
    val baseModelAvailable: Boolean,
    val trainingConfigurationSupported: Boolean,
    val runtimeResourceAvailable: Boolean,
    val runtimeAgentIdle: Boolean,
    val rejectionReasons: List<String>
)
