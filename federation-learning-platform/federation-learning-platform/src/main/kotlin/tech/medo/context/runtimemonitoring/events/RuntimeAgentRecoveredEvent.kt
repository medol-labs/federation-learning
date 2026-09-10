package tech.medo.runtimemonitoring.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class RuntimeAgentRecoveredEvent(
    @EventTag(key = "nodeId")
    val nodeId: UUID,
    val runtimeAgentId: UUID,
    val federationId: UUID?,
    val federationName: String?,
    val trainingJobId: UUID?,
    val trainingJobObjective: String?,
    val roundExecutionId: UUID?,
    val runtimeNodeName: String?,
    val recoveryReason: String?
)
