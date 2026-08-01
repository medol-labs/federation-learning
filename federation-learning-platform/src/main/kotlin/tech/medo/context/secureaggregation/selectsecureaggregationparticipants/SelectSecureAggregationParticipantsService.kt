package tech.medo.secureaggregation.selectsecureaggregationparticipants

import java.util.UUID;

interface SelectSecureAggregationParticipantsService {
    fun supports(input: SelectSecureAggregationParticipantsInput): Boolean = true
    fun execute(input: SelectSecureAggregationParticipantsInput): SelectSecureAggregationParticipantsResult
}

data class SelectSecureAggregationParticipantsInput(
    val secureAggregationSessionId: UUID,
    val roundId: UUID,
    val acceptedRuntimeIds: List<UUID>,
    val selectedRuntimeIds: List<UUID>,
    val selectedParticipantCount: Int
)

sealed interface SelectSecureAggregationParticipantsResult {
    class Succeeded : SelectSecureAggregationParticipantsResult


}
