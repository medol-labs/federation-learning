package tech.medo.trainingorchestration.starttraininground

import java.util.UUID;
import java.math.BigDecimal;

interface StartTrainingRoundService {
    fun supports(input: StartTrainingRoundInput): Boolean = true
    fun execute(input: StartTrainingRoundInput): StartTrainingRoundResult
}

data class StartTrainingRoundInput(
    val trainingJobId: UUID,
    val federationId: UUID,
    val federationName: String?,
    val trainingRunConfigurationId: UUID,
    val configurationName: String?,
    val trainingJobObjective: String,
    val featureSchemaId: UUID,
    val featureDomain: String?,
    val featureSchemaVersion: String?,
    val roundId: UUID,
    val roundNumber: Int,
    val selectedOrganizationIds: List<UUID>,
    val selectedRuntimeIds: List<UUID>,
    val selectedOrganizationCount: Int,
    val selectedRuntimeCount: Int,
    val minimumNodesPerRound: Int,
    val maxRounds: Int,
    val minimumAccuracy: BigDecimal,
    val aggregationAlgorithm: String?,
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
