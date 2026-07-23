package tech.medo.secureaggregation.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class SecureAggregationParticipantsSelectedEvent(
    @EventTag(key = "secureAggregationSessionId")
    val secureAggregationSessionId: UUID,
    val roundId: UUID,
    val acceptedRuntimeIds: List<UUID>,
    val selectedRuntimeIds: List<UUID>,
    val selectedParticipantCount: Int
)
