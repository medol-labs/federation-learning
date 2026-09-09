package tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.preparehomomorphicencryptioncontext

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.secureaggregation.preparehomomorphicencryptioncontext.PrepareHomomorphicEncryptionContextInput
import tech.medo.secureaggregation.preparehomomorphicencryptioncontext.PrepareHomomorphicEncryptionContextResult
import java.math.BigDecimal
import java.util.UUID

class LocalPrepareHomomorphicEncryptionContextAdapterTest {
    @Test
    fun preparesHomomorphicEncryptionContextLocally() {
        val adapter = LocalPrepareHomomorphicEncryptionContextAdapter()
        val input = PrepareHomomorphicEncryptionContextInput(
            secureAggregationSessionId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
            trainingJobId = UUID.fromString("22222222-2222-4222-8222-222222222222"),
            trainingRunConfigurationId = UUID.fromString("33333333-3333-4333-8333-333333333333"),
            featureSchemaId = UUID.fromString("44444444-4444-4444-8444-444444444444"),
            roundId = UUID.fromString("55555555-5555-4555-8555-555555555555"),
            roundNumber = 1,
            selectedOrganizationIds = listOf(UUID.fromString("66666666-6666-4666-8666-666666666666")),
            selectedRuntimeIds = listOf(UUID.fromString("77777777-7777-4777-8777-777777777777")),
            selectedOrganizationCount = 1,
            selectedRuntimeCount = 1,
            minimumNodesPerRound = 1,
            maxRounds = 1,
            minimumAccuracy = BigDecimal("0.90"),
            secureAggregationRequired = true
        )

        val result = adapter.execute(input)

        assertTrue(adapter.supports(input))
        assertTrue(result is PrepareHomomorphicEncryptionContextResult.Succeeded)
    }
}
