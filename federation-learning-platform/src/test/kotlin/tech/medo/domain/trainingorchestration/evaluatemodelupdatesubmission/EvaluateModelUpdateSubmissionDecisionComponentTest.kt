package tech.medo.domain.trainingorchestration.evaluatemodelupdatesubmission

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.evaluatemodelupdatesubmission.EvaluateModelUpdateSubmissionCommand
import tech.medo.trainingorchestration.events.ModelUpdateSubmissionAcceptedEvent
import tech.medo.trainingorchestration.events.TrainingRoundStartedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState
import java.math.BigDecimal
import java.util.UUID

class EvaluateModelUpdateSubmissionDecisionComponentTest {
    @Test
    fun `accepts update by appending unique runtime ids`() {
        val runtimeId = uuid("runtime-1")
        val state = TrainingRoundState().apply {
            evolve(
                TrainingRoundStartedEvent(
                    trainingJobId = uuid("job-1"),
                    trainingRunConfigurationId = uuid("config-1"),
                    featureSchemaId = uuid("schema-1"),
                    roundId = uuid("round-1"),
                    roundNumber = 1,
                    selectedOrganizationIds = emptyList(),
                    selectedRuntimeIds = emptyList(),
                    selectedParticipants = emptyList(),
                    selectedOrganizationCount = 1,
                    selectedRuntimeCount = 1,
                    minimumNodesPerRound = 1,
                    secureAggregationRequired = true,
                    secureAggregationSessionId = uuid("secure-aggregation-session-1"),
                    encryptionScheme = "PAILLIER",
                    publicKeyVersion = "local-dev-v1",
                    publicKeyRef = "local://secure-aggregation/public-keys/local-dev-v1",
                    encryptedParameterScale = 1000000
                )
            )
            acceptedRuntimeIds = listOf(runtimeId)
        }

        val events = EvaluateModelUpdateSubmissionDecisionComponent().decide(
            command = EvaluateModelUpdateSubmissionCommand(
                modelUpdateSubmissionId = uuid("submission-1"),
                executionSessionId = uuid("session-1"),
                executionPlanId = uuid("plan-1"),
                trainingJobId = uuid("job-1"),
                trainingRunConfigurationId = uuid("config-1"),
                roundId = uuid("round-1"),
                runtimeId = runtimeId,
                featureSchemaId = uuid("schema-1"),
                secureAggregationRequired = true,
                secureAggregationSessionId = uuid("secure-aggregation-session-1"),
                encryptionScheme = "PAILLIER",
                publicKeyVersion = "local-dev-v1",
                updateArtifactId = uuid("update-artifact-1"),
                artifactRef = "file:///updates/encrypted-update.json",
                artifactDigest = "sha256:encrypted",
                updateProtectionType = "HOMOMORPHIC_ENCRYPTED",
                anomalyScore = BigDecimal("0.12")
            ),
            state = state
        )

        val event = events.filterIsInstance<ModelUpdateSubmissionAcceptedEvent>().single()
        assertEquals(listOf(runtimeId), event.acceptedRuntimeIds)
        assertEquals(1, event.acceptedModelUpdateCount)
        assertEquals(1, event.minimumNodesPerRound)
    }

    private fun uuid(value: String): UUID = UUID.nameUUIDFromBytes(value.toByteArray())
}
