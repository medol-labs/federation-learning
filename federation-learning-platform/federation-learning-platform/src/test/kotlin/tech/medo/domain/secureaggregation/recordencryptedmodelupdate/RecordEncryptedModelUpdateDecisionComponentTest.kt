package tech.medo.domain.secureaggregation.recordencryptedmodelupdate

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tech.medo.secureaggregation.events.EncryptedModelUpdateReceivedEvent
import tech.medo.secureaggregation.recordencryptedmodelupdate.RecordEncryptedModelUpdateCommand
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionState
import java.math.BigDecimal
import java.util.UUID

class RecordEncryptedModelUpdateDecisionComponentTest {
    private val decision = RecordEncryptedModelUpdateDecisionComponent()

    @Test
    fun `appends a new runtime to the encrypted update snapshot`() {
        val existingRuntimeId = UUID.randomUUID()
        val command = command(runtimeId = UUID.randomUUID())
        val state = SecureAggregationSessionState().apply {
            selectedParticipantCount = 2
            receivedRuntimeIds = listOf(existingRuntimeId)
        }

        val event = decision.decide(command, state).single() as EncryptedModelUpdateReceivedEvent

        assertEquals(listOf(existingRuntimeId, command.runtimeId), event.receivedRuntimeIds)
        assertEquals(2, event.receivedEncryptedUpdateCount)
        assertEquals(2, event.selectedParticipantCount)
    }

    @Test
    fun `does not count the same runtime twice`() {
        val runtimeId = UUID.randomUUID()
        val command = command(runtimeId)
        val state = SecureAggregationSessionState().apply {
            selectedParticipantCount = 1
            receivedRuntimeIds = listOf(runtimeId)
        }

        val event = decision.decide(command, state).single() as EncryptedModelUpdateReceivedEvent

        assertEquals(listOf(runtimeId), event.receivedRuntimeIds)
        assertEquals(1, event.receivedEncryptedUpdateCount)
    }

    private fun command(runtimeId: UUID) = RecordEncryptedModelUpdateCommand(
        secureAggregationSessionId = UUID.randomUUID(),
        submissionId = UUID.randomUUID(),
        trainingJobId = UUID.randomUUID(),
        trainingRunConfigurationId = UUID.randomUUID(),
        featureSchemaId = UUID.randomUUID(),
        roundId = UUID.randomUUID(),
        roundNumber = 1,
        maxRounds = 3,
        minimumAccuracy = BigDecimal("0.8"),
        runtimeId = runtimeId,
        updateArtifactId = UUID.randomUUID(),
        encryptedUpdateArtifactRef = "file:///tmp/encrypted-update.json",
        encryptedUpdateDigest = "sha256:test",
        encryptionScheme = "PAILLIER",
        publicKeyVersion = "v1"
    )
}
