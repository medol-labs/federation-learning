package tech.medo.trainingorchestration.starttraininground

import java.util.UUID;
import tech.medo.trainingorchestration.domain.types.TrainingRoundParticipant;

interface StartTrainingRoundService {
    fun supports(input: StartTrainingRoundInput): Boolean = true
    fun execute(input: StartTrainingRoundInput): StartTrainingRoundResult
}

data class StartTrainingRoundInput(
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val featureSchemaId: UUID,
    val roundId: UUID,
    val roundNumber: Int,
    val selectedOrganizationIds: List<UUID>,
    val selectedRuntimeIds: List<UUID>,
    val selectedParticipants: List<TrainingRoundParticipant>,
    val selectedOrganizationCount: Int,
    val selectedRuntimeCount: Int,
    val minimumNodesPerRound: Int,
    val secureAggregationRequired: Boolean,
    val secureAggregationSessionId: UUID?,
    val encryptionScheme: String?,
    val publicKeyVersion: String?,
    val publicKeyRef: String?,
    val encryptedParameterScale: Int?
)

sealed interface StartTrainingRoundResult {
    class Succeeded : StartTrainingRoundResult

    data class Rejected(
        val failureReason: String
    ) : StartTrainingRoundResult

    data class Unavailable(
        val failureReason: String
    ) : StartTrainingRoundResult
}
