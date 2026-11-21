package tech.medo.domain.trainingorchestration.submitmodelupdatesubmission

import java.math.BigDecimal
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.events.ModelUpdateSubmissionReceivedEvent
import tech.medo.trainingorchestration.submitmodelupdatesubmission.SubmitModelUpdateSubmissionCommand
import tech.medo.trainingorchestration.traininground.TrainingRoundState

class SubmissionReplayTest {
    @Test
    fun lostAcknowledgementAfterAppendDoesNotReceiveTheSameExecutionTwice() {
        val decision = SubmitModelUpdateSubmissionDecisionComponent()
        val round = UUID.randomUUID()
        val first = submission(round)
        val other = submission(round)
        val state = TrainingRoundState()
        val history = listOf(first, other).map {
            val event = decision.decide(it, state).single() as ModelUpdateSubmissionReceivedEvent
            state.evolve(event)
            event
        }
        val replayed = TrainingRoundState().apply { history.forEach { evolve(it) } }
        assertEquals(emptyList<Any>(), decision.decide(first, replayed))
        assertThrows(IllegalArgumentException::class.java) {
            decision.decide(first.copy(artifactDigest = "another-digest"), replayed)
        }
        assertEquals(1, decision.decide(submission(UUID.randomUUID()), TrainingRoundState()).size)
    }

    private fun submission(round: UUID): SubmitModelUpdateSubmissionCommand {
        val id = UUID.randomUUID()
        return SubmitModelUpdateSubmissionCommand(
            modelUpdateSubmissionId = id, roundExecutionId = id, executionSessionId = id, executionPlanId = id,
            trainingJobId = round, trainingRunConfigurationId = round, roundId = round, runtimeId = id,
            featureSchemaId = round, personalizationMode = null, personalizationScope = null,
            personalizedArtifactPolicy = null, personalizationMetric = null, minimumPersonalizationLift = null,
            personalizationTargetType = null, personalizationTargetId = null, personalizationCohortId = null,
            personalizationProfileId = null, secureAggregationRequired = false, secureAggregationSessionId = null,
            encryptionScheme = null, publicKeyVersion = null, localModelId = id, updateArtifactId = id,
            artifactRef = "http://support/api/files/$id/content", artifactDigest = "digest", updateProtectionType = "PLAIN",
            trainingLoss = BigDecimal.ZERO, personalizedArtifactAvailable = false, personalizedModelId = null,
            personalizedModelArtifactRef = null, personalizedModelArtifactDigest = null, personalizedMetricsArtifactRef = null,
            personalizedAccuracy = null, personalizationLift = null
        )
    }
}
