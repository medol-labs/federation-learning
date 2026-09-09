package tech.medo.trainingorchestration.selecttrainingroundparticipants

import java.util.UUID;
import java.math.BigDecimal;
import tech.medo.trainingorchestration.domain.types.TrainingRoundParticipant;

interface SelectTrainingRoundParticipantsService {
    fun supports(input: SelectTrainingRoundParticipantsInput): Boolean = true
    fun execute(input: SelectTrainingRoundParticipantsInput): SelectTrainingRoundParticipantsResult
}

data class SelectTrainingRoundParticipantsInput(
    val trainingJobId: UUID
)

sealed interface SelectTrainingRoundParticipantsResult {
    data class Succeeded(
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
        val selectedRuntimeCount: Int
    ) : SelectTrainingRoundParticipantsResult

    data class Rejected(
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
    ) : SelectTrainingRoundParticipantsResult

    data class Unavailable(
        val failureReason: String
    ) : SelectTrainingRoundParticipantsResult
}
