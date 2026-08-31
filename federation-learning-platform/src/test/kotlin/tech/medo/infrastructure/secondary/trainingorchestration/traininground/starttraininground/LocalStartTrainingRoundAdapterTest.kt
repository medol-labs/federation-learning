package tech.medo.infrastructure.secondary.trainingorchestration.traininground.starttraininground

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundInput
import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundResult
import java.math.BigDecimal
import java.util.UUID

class LocalStartTrainingRoundAdapterTest {
    private val adapter = LocalStartTrainingRoundAdapter()

    @Test
    fun startsRoundWhenSelectedRuntimeCountMeetsQuorum() {
        val result = adapter.execute(input(selectedRuntimeCount = 1, minimumNodesPerRound = 1))

        assertTrue(result is StartTrainingRoundResult.Succeeded)
    }

    @Test
    fun rejectsRoundWhenSelectedRuntimeCountIsBelowQuorum() {
        val result = adapter.execute(input(selectedRuntimeCount = 1, minimumNodesPerRound = 2))

        assertTrue(result is StartTrainingRoundResult.Rejected)
    }

    private fun input(
        selectedRuntimeCount: Int,
        minimumNodesPerRound: Int
    ): StartTrainingRoundInput =
        StartTrainingRoundInput(
            trainingJobId = uuid("11111111-1111-4111-8111-111111111111"),
            trainingRunConfigurationId = uuid("22222222-2222-4222-8222-222222222222"),
            featureSchemaId = uuid("33333333-3333-4333-8333-333333333333"),
            roundId = uuid("44444444-4444-4444-8444-444444444444"),
            roundNumber = 1,
            selectedOrganizationIds = listOf(uuid("55555555-5555-4555-8555-555555555555")),
            selectedRuntimeIds = listOf(uuid("66666666-6666-4666-8666-666666666666")),
            selectedOrganizationCount = 1,
            selectedRuntimeCount = selectedRuntimeCount,
            minimumNodesPerRound = minimumNodesPerRound,
            maxRounds = 1,
            minimumAccuracy = BigDecimal("0.90"),
            secureAggregationRequired = true,
            secureAggregationSessionId = uuid("88888888-8888-4888-8888-888888888888"),
            encryptionScheme = "PAILLIER",
            publicKeyVersion = "local-dev-v1",
            publicKeyRef = "local://secure-aggregation/public-keys/local-dev-v1",
            encryptedParameterScale = 1000000
        )

    private fun uuid(value: String): UUID = UUID.fromString(value)
}
