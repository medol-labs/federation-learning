package tech.medo.trainingorchestration.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class TrainingJobCompletedEvent(
    @EventTag(key = "trainingJobId")
    val trainingJobId: UUID,
    val finalRoundId: UUID,
    val finalModelVersionId: UUID,
    val stopReason: String
)
