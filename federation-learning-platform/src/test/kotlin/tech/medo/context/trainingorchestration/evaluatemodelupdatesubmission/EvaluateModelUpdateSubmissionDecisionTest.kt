package tech.medo.trainingorchestration.evaluatemodelupdatesubmission

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.evaluatemodelupdatesubmission.EvaluateModelUpdateSubmissionCommand
import tech.medo.trainingorchestration.events.TrainingRoundStartedEvent
import tech.medo.trainingorchestration.events.ModelUpdateSubmissionReceivedEvent
import tech.medo.trainingorchestration.events.ModelUpdateSubmissionAcceptedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState
import java.util.UUID
import java.math.BigDecimal
import tech.medo.trainingorchestration.domain.types.TrainingRoundParticipant

class EvaluateModelUpdateSubmissionDecisionTest {
    @Test
    fun AcceptNormalUpdate() {
        val state = TrainingRoundState()
        state.evolve(
            TrainingRoundStartedEvent(
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray()),
            trainingRunConfigurationId = UUID.nameUUIDFromBytes("config-1".toByteArray()),
            featureSchemaId = java.util.UUID.randomUUID(),
            roundId = UUID.nameUUIDFromBytes("round-1".toByteArray()),
            roundNumber = 0,
            selectedOrganizationIds = emptyList(),
            selectedRuntimeIds = emptyList(),
            selectedParticipants = emptyList(),
            selectedOrganizationCount = 0,
            selectedRuntimeCount = 2,
            minimumNodesPerRound = 2,
            secureAggregationRequired = false,
            secureAggregationSessionId = null,
            encryptionScheme = null,
            publicKeyVersion = null,
            publicKeyRef = null,
            encryptedParameterScale = null
            )
        )
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
            secureAggregationRequired = false,
            secureAggregationSessionId = null,
            encryptionScheme = null,
            publicKeyVersion = null,
            localModelId = java.util.UUID.randomUUID(),
            updateArtifactId = UUID.nameUUIDFromBytes("artifact-1".toByteArray()),
            artifactRef = "",
            artifactDigest = "",
            updateProtectionType = "",
            trainingLoss = BigDecimal("0.23")
            )
        )

        val command = EvaluateModelUpdateSubmissionCommand(
            modelUpdateSubmissionId = UUID.nameUUIDFromBytes("submission-1".toByteArray()),
            executionSessionId = java.util.UUID.randomUUID(),
            executionPlanId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            roundId = java.util.UUID.randomUUID(),
            runtimeId = UUID.nameUUIDFromBytes("runtime-1".toByteArray()),
            featureSchemaId = java.util.UUID.randomUUID(),
            secureAggregationRequired = false,
            secureAggregationSessionId = null,
            encryptionScheme = null,
            publicKeyVersion = null,
            updateArtifactId = java.util.UUID.randomUUID(),
            artifactRef = "",
            artifactDigest = "",
            updateProtectionType = "",
            anomalyScore = BigDecimal("0.12")
        )

        val events = (object : EvaluateModelUpdateSubmissionDecision {}).decide(
            command,
            state = state
        )

        val event = events.filterIsInstance<ModelUpdateSubmissionAcceptedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("submission-1".toByteArray()), event.modelUpdateSubmissionId)
        assertEquals(command.executionSessionId, event.executionSessionId)
        assertEquals(command.executionPlanId, event.executionPlanId)
        assertEquals(command.trainingJobId, event.trainingJobId)
        assertEquals(command.trainingRunConfigurationId, event.trainingRunConfigurationId)
        assertEquals(command.roundId, event.roundId)
        assertEquals(UUID.nameUUIDFromBytes("runtime-1".toByteArray()), event.runtimeId)
        assertEquals(command.featureSchemaId, event.featureSchemaId)
        assertEquals(command.secureAggregationRequired, event.secureAggregationRequired)
        assertEquals(command.secureAggregationSessionId, event.secureAggregationSessionId)
        assertEquals(command.encryptionScheme, event.encryptionScheme)
        assertEquals(command.publicKeyVersion, event.publicKeyVersion)
        assertEquals(command.updateArtifactId, event.updateArtifactId)
        assertEquals(command.artifactRef, event.artifactRef)
        assertEquals(command.artifactDigest, event.artifactDigest)
        assertEquals(command.updateProtectionType, event.updateProtectionType)
        assertEquals(BigDecimal("0.12"), event.anomalyScore)
    }
}
