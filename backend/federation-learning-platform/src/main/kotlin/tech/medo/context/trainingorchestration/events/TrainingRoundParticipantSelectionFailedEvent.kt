package tech.medo.trainingorchestration.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.math.BigDecimal;
import tech.medo.trainingorchestration.domain.types.TrainingRoundParticipant;



@Event
data class TrainingRoundParticipantSelectionFailedEvent(
    @EventTag(key = "trainingJobId")
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val featureSchemaId: UUID,
    val roundId: UUID,
    val roundNumber: Int,
    val maxRounds: Int,
    val minimumAccuracy: BigDecimal,
    val aggregationAlgorithm: String,
    val minimumNodesPerRound: Int,
    val secureAggregationRequired: Boolean,
    val selectedOrganizationIds: List<UUID>,
    val selectedRuntimeIds: List<UUID>,
    val selectedParticipants: List<TrainingRoundParticipant>,
    val selectedOrganizationCount: Int,
    val selectedRuntimeCount: Int,
    val failureReason: String
)
