package tech.medo.runtimemonitoring.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class TrainingAlertRaisedEvent(
    @EventTag(key = "alertId")
    val alertId: UUID,
    val nodeId: UUID,
    val trainingJobId: UUID?,
    val runtimeNodeName: String?,
    val trainingJobObjective: String?,
    val severity: String,
    val message: String
)
