package tech.medo.trainingorchestration.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class TrainingJobPausedEvent(
    @EventTag(key = "trainingJobId")
    val trainingJobId: UUID,
    val pauseReason: String
)
