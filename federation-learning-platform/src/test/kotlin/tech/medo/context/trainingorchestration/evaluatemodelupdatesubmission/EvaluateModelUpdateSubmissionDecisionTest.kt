package tech.medo.trainingorchestration.evaluatemodelupdatesubmission

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.evaluatemodelupdatesubmission.EvaluateModelUpdateSubmissionCommand
import tech.medo.trainingorchestration.events.ModelUpdateSubmissionReceivedEvent
import tech.medo.trainingorchestration.events.ModelUpdateSubmissionRejectedEvent

import tech.medo.trainingorchestration.traininground.TrainingRoundState

import java.util.UUID;
import java.math.BigDecimal;


class EvaluateModelUpdateSubmissionDecisionTest {
    @Test
    fun RejectAnomalousUpdate() {
        val state = TrainingRoundState()
        state.evolve(
            ModelUpdateSubmissionReceivedEvent(
            modelUpdateSubmissionId = UUID.nameUUIDFromBytes("submission-1".toByteArray()),
            executionSessionId = java.util.UUID.randomUUID(),
            executionPlanId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            roundId = UUID.nameUUIDFromBytes("round-1".toByteArray()),
            roundExecutionId = java.util.UUID.randomUUID(),
            runtimeId = UUID.nameUUIDFromBytes("runtime-1".toByteArray()),
            featureSchemaId = java.util.UUID.randomUUID(),
            localModelVersionId = java.util.UUID.randomUUID(),
            updateArtifactId = UUID.nameUUIDFromBytes("artifact-1".toByteArray()),
            artifactRef = "",
            artifactDigest = "",
            trainingLoss = BigDecimal("99")
            )
        )

        val command = EvaluateModelUpdateSubmissionCommand(
            modelUpdateSubmissionId = UUID.nameUUIDFromBytes("submission-1".toByteArray()),
            executionSessionId = java.util.UUID.randomUUID(),
            executionPlanId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            roundId = java.util.UUID.randomUUID(),
            runtimeId = java.util.UUID.randomUUID(),
            anomalyScore = BigDecimal("0.98")
        )

        val events = (object : EvaluateModelUpdateSubmissionDecision {}).decide(
            command,
            state = state
        )

        val event = events.filterIsInstance<ModelUpdateSubmissionRejectedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("submission-1".toByteArray()), event.modelUpdateSubmissionId)
        assertEquals(command.trainingJobId, event.trainingJobId)
        assertEquals(command.trainingRunConfigurationId, event.trainingRunConfigurationId)
        assertEquals(command.roundId, event.roundId)
        assertEquals(command.runtimeId, event.runtimeId)
        assertEquals(BigDecimal("0.98"), event.anomalyScore)
    }
}
