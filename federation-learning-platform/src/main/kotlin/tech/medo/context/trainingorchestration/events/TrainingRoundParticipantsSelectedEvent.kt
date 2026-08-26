package tech.medo.trainingorchestration.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import tech.medo.trainingorchestration.domain.types.TrainingRoundParticipant;



@Event
data class TrainingRoundParticipantsSelectedEvent(
    @EventTag(key = "trainingJobId")
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val featureSchemaId: UUID,
    val roundId: UUID,
    val roundNumber: Int,
    val minimumNodesPerRound: Int,
    val secureAggregationRequired: Boolean,
    val selectedOrganizationIds: List<UUID>,
    val selectedRuntimeIds: List<UUID>,
    val selectedParticipants: List<TrainingRoundParticipant>,
    val selectedOrganizationCount: Int,
    val selectedRuntimeCount: Int
)
