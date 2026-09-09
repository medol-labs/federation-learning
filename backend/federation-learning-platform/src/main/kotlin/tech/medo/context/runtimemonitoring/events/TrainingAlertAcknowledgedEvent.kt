package tech.medo.runtimemonitoring.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class TrainingAlertAcknowledgedEvent(
    @EventTag(key = "alertId")
    val alertId: UUID,
    val acknowledgementNote: String?
)
