package tech.medo.trainingorchestration.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.math.BigDecimal;
import tech.medo.trainingorchestration.domain.types.TrainingRoundParticipant;



@Event
data class TrainingRoundParticipantsSelectedEvent(
    @EventTag(key = "trainingJobId")
    val trainingJobId: UUID,
    val federationId: UUID,
    val federationName: String?,
    val trainingRunConfigurationId: UUID,
    val configurationName: String?,
    val featureSchemaId: UUID,
    val featureDomain: String?,
    val featureSchemaVersion: String?,
    val trainingJobObjective: String,
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
    val selectedRuntimeCount: Int
)
