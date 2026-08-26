package tech.medo.secureaggregation.selectsecureaggregationparticipants

import java.util.UUID;

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
    val secureAggregationRequired: Boolean
)

sealed interface SelectSecureAggregationParticipantsResult {
    class Succeeded : SelectSecureAggregationParticipantsResult


}
