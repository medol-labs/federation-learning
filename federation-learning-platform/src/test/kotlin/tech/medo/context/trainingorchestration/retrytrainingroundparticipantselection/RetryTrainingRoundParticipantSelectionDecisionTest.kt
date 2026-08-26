package tech.medo.trainingorchestration.retrytrainingroundparticipantselection

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.retrytrainingroundparticipantselection.RetryTrainingRoundParticipantSelectionCommand
import tech.medo.trainingorchestration.events.TrainingRoundParticipantSelectionFailedEvent
import tech.medo.trainingorchestration.events.TrainingRoundParticipantSelectionRetryRequestedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState
import java.util.UUID
import tech.medo.trainingorchestration.domain.types.TrainingRoundParticipant

class RetryTrainingRoundParticipantSelectionDecisionTest {
    @Test
    fun RetryParticipantSelection() {
        val state = TrainingRoundState()
        state.evolve(
            TrainingRoundParticipantSelectionFailedEvent(
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray()),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            roundId = java.util.UUID.randomUUID(),
            roundNumber = 0,
            minimumNodesPerRound = 0,
            secureAggregationRequired = false,
            selectedOrganizationIds = emptyList(),
            selectedRuntimeIds = emptyList(),
            selectedParticipants = emptyList(),
            selectedOrganizationCount = 0,
            selectedRuntimeCount = 0,
            failureReason = ""
            )
        )

        val command = RetryTrainingRoundParticipantSelectionCommand(
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray())
        )

        val events = (object : RetryTrainingRoundParticipantSelectionDecision {}).decide(
            command,
            state = state
        )

        val event = events.filterIsInstance<TrainingRoundParticipantSelectionRetryRequestedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("job-1".toByteArray()), event.trainingJobId)
    }
}
