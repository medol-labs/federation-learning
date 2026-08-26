package tech.medo.secureaggregation.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class SecureAggregationParticipantsSelectedEvent(
    @EventTag(key = "secureAggregationSessionId")
    val secureAggregationSessionId: UUID,
    val roundId: UUID,
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val featureSchemaId: UUID,
    val roundNumber: Int,
    val selectedOrganizationIds: List<UUID>,
    val selectedRuntimeIds: List<UUID>,
    val selectedOrganizationCount: Int,
    val selectedParticipantCount: Int,
    val minimumNodesPerRound: Int,
    val secureAggregationRequired: Boolean
)
