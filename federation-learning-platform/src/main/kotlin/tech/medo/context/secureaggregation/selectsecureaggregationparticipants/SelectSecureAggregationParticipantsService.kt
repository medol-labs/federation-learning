package tech.medo.secureaggregation.selectsecureaggregationparticipants

import java.util.UUID;
import java.math.BigDecimal;

interface SelectSecureAggregationParticipantsService {
    fun supports(input: SelectSecureAggregationParticipantsInput): Boolean = true
    fun execute(input: SelectSecureAggregationParticipantsInput): SelectSecureAggregationParticipantsResult
}

data class SelectSecureAggregationParticipantsInput(
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
    val maxRounds: Int,
    val minimumAccuracy: BigDecimal,
    val secureAggregationRequired: Boolean
)

sealed interface SelectSecureAggregationParticipantsResult {
    class Succeeded : SelectSecureAggregationParticipantsResult


}
