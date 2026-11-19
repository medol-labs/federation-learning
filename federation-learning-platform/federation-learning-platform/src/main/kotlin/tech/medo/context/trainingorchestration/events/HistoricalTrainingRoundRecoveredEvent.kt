package tech.medo.trainingorchestration.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.math.BigDecimal;
import tech.medo.trainingorchestration.domain.types.TrainingRoundParticipant;



@Event
data class HistoricalTrainingRoundRecoveredEvent(
    @EventTag(key = "roundId")
    val roundId: UUID,
    val trainingJobId: UUID,
    val recoveryDigest: String,
    val canonicalRoundId: UUID,
    val recoverySubmissionIds: List<UUID>,
    val federationId: UUID,
    val trainingRunConfigurationId: UUID,
    val featureSchemaId: UUID,
    val trainingJobObjective: String,
    val roundNumber: Int,
    val maxRounds: Int,
    val minimumAccuracy: BigDecimal,
    val aggregationAlgorithm: String,
    val minimumNodesPerRound: Int,
    val secureAggregationRequired: Boolean,
    val selectedParticipants: List<TrainingRoundParticipant>,
    val selectedOrganizationIds: List<UUID>,
    val selectedRuntimeIds: List<UUID>,
    val selectedOrganizationCount: Int,
    val selectedRuntimeCount: Int
)
