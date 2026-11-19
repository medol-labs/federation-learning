package tech.medo.trainingorchestration.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;


@Event
data class TrainingRoundTrainingJobIdPreviousRoundNumberReservedEvent(
    val roundId: UUID,
    val trainingJobId: UUID,
    val previousRoundNumber: Int,
    @EventTag(key = "trainingJobId")
    val normalizedTrainingJobId: String,
    @EventTag(key = "previousRoundNumber")
    val normalizedPreviousRoundNumber: String
)
