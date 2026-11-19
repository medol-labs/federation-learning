package tech.medo.trainingorchestration.traininground

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.trainingorchestration.events.TrainingRoundTrainingJobIdPreviousRoundNumberReservedEvent
import java.util.UUID;


@EventSourced(idType = TrainingRoundTrainingJobIdPreviousRoundNumberSelection::class)
class TrainingRoundTrainingJobIdPreviousRoundNumberReservationState @EntityCreator constructor() {

    var reserved: Boolean = false
    var roundId: UUID? = null

    companion object {
        @JvmStatic
        @EventCriteriaBuilder
        fun resolveCriteria(selection: TrainingRoundTrainingJobIdPreviousRoundNumberSelection): EventCriteria = EventCriteria.havingTags(
                Tag.of(TrainingRoundTrainingJobIdPreviousRoundNumberReservationTags.TRAINING_JOB_ID, selection.normalizedTrainingJobId),
                Tag.of(TrainingRoundTrainingJobIdPreviousRoundNumberReservationTags.PREVIOUS_ROUND_NUMBER, selection.normalizedPreviousRoundNumber)
        )
    }

    @EventSourcingHandler
    fun evolve(event: TrainingRoundTrainingJobIdPreviousRoundNumberReservedEvent): TrainingRoundTrainingJobIdPreviousRoundNumberReservationState = apply {
        reserved = true
        roundId = event.roundId
    }
}
