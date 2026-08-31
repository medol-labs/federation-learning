package tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession

import com.fasterxml.jackson.databind.ObjectMapper
import java.math.BigDecimal
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionInput
import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionResult

class LocalCompleteHomomorphicAggregationSessionAdapterTest {
    @TempDir
    lateinit var tempDir: Path

    @Test
    fun writesGlobalModelArtifactLocally() {
        val adapter = LocalCompleteHomomorphicAggregationSessionAdapter(ObjectMapper(), tempDir)
        val input = CompleteHomomorphicAggregationSessionInput(
            secureAggregationSessionId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
            trainingJobId = UUID.fromString("22222222-2222-4222-8222-222222222222"),
            trainingRunConfigurationId = UUID.fromString("33333333-3333-4333-8333-333333333333"),
            featureSchemaId = UUID.fromString("44444444-4444-4444-8444-444444444444"),
            roundId = UUID.fromString("55555555-5555-4555-8555-555555555555"),
            roundNumber = 1,
            maxRounds = 1,
            minimumAccuracy = BigDecimal("0.90"),
            aggregatedModelId = UUID.fromString("66666666-6666-4666-8666-666666666666")
        )

        val result = adapter.execute(input)

        assertTrue(result is CompleteHomomorphicAggregationSessionResult.Succeeded)
        result as CompleteHomomorphicAggregationSessionResult.Succeeded
        assertEquals("JSON", result.modelFormat)
        assertEquals(64, result.modelArtifactDigest.length)
        assertTrue(result.aggregatedModelArtifactUri.startsWith("file:"))
        assertTrue(Files.exists(Path.of(URI.create(result.aggregatedModelArtifactUri))))
    }
}
