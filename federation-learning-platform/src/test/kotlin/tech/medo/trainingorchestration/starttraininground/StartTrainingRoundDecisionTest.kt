package tech.medo.trainingorchestration.starttraininground

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundCommand
import tech.medo.trainingorchestration.events.TrainingRoundParticipantsSelectedEvent
import tech.medo.trainingorchestration.events.TrainingRoundStartedEvent
import tech.medo.trainingorchestration.events.TrainingRoundStartFailedEvent

import tech.medo.trainingorchestration.traininground.TrainingRoundState
import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundResult
import java.util.UUID;
import tech.medo.trainingorchestration.domain.types.TrainingRoundParticipant;
import java.time.LocalDateTime

class StartTrainingRoundDecisionTest {
    @Test
    fun StartRoundWithSelectedRuntimeQuorum() {
        val state = TrainingRoundState()
        state.evolve(
            TrainingRoundParticipantsSelectedEvent(
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray()),
            trainingRunConfigurationId = UUID.nameUUIDFromBytes("config-1".toByteArray()),
            featureSchemaId = java.util.UUID.randomUUID(),
            roundId = UUID.nameUUIDFromBytes("round-1".toByteArray()),
            roundNumber = 0,
            minimumNodesPerRound = 3,
            selectedOrganizationIds = emptyList(),
            selectedRuntimeIds = emptyList(),
            selectedParticipants = emptyList(),
            selectedOrganizationCount = 0,
            selectedRuntimeCount = 3
            )
        )

        val command = StartTrainingRoundCommand(
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray()),
            trainingRunConfigurationId = UUID.nameUUIDFromBytes("config-1".toByteArray()),
            featureSchemaId = java.util.UUID.randomUUID(),
            roundId = UUID.nameUUIDFromBytes("round-1".toByteArray()),
            roundNumber = 0,
            selectedOrganizationIds = emptyList(),
            selectedRuntimeIds = emptyList(),
            selectedParticipants = emptyList(),
            selectedOrganizationCount = 0,
            selectedRuntimeCount = 3,
            minimumNodesPerRound = 0
        )

        val events = StartTrainingRoundDecision().decide(
            command,
            state = state,
            portResult = StartTrainingRoundResult.Succeeded(
                trainingRunConfigurationId = UUID.nameUUIDFromBytes("config-1".toByteArray()),
                featureSchemaId = command.featureSchemaId,
                roundId = UUID.nameUUIDFromBytes("round-1".toByteArray()),
                roundNumber = command.roundNumber,
                selectedOrganizationIds = command.selectedOrganizationIds,
                selectedRuntimeIds = command.selectedRuntimeIds,
                selectedParticipants = command.selectedParticipants,
                selectedOrganizationCount = command.selectedOrganizationCount,
                selectedRuntimeCount = 3,
                minimumNodesPerRound = command.minimumNodesPerRound
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<TrainingRoundStartedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("job-1".toByteArray()), event.trainingJobId)
        assertEquals(UUID.nameUUIDFromBytes("config-1".toByteArray()), event.trainingRunConfigurationId)
        assertEquals(command.featureSchemaId, event.featureSchemaId)
        assertEquals(UUID.nameUUIDFromBytes("round-1".toByteArray()), event.roundId)
        assertEquals(command.roundNumber, event.roundNumber)
        assertEquals(command.selectedOrganizationIds, event.selectedOrganizationIds)
        assertEquals(command.selectedRuntimeIds, event.selectedRuntimeIds)
        assertEquals(command.selectedParticipants, event.selectedParticipants)
        assertEquals(command.selectedOrganizationCount, event.selectedOrganizationCount)
        assertEquals(3, event.selectedRuntimeCount)
        assertEquals(command.minimumNodesPerRound, event.minimumNodesPerRound)
    }

    @Test
    fun RecordFailedRoundStartBelowQuorum() {
        val state = TrainingRoundState()
        state.evolve(
            TrainingRoundParticipantsSelectedEvent(
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray()),
            trainingRunConfigurationId = UUID.nameUUIDFromBytes("config-1".toByteArray()),
            featureSchemaId = java.util.UUID.randomUUID(),
            roundId = UUID.nameUUIDFromBytes("round-1".toByteArray()),
            roundNumber = 0,
            minimumNodesPerRound = 3,
            selectedOrganizationIds = emptyList(),
            selectedRuntimeIds = emptyList(),
            selectedParticipants = emptyList(),
            selectedOrganizationCount = 0,
            selectedRuntimeCount = 2
            )
        )

        val command = StartTrainingRoundCommand(
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
            minimumNodesPerRound = 0
        )

        val events = StartTrainingRoundDecision().decide(
            command,
            state = state,
            portResult = StartTrainingRoundResult.Rejected(
                trainingRunConfigurationId = UUID.nameUUIDFromBytes("config-1".toByteArray()),
                featureSchemaId = command.featureSchemaId,
                roundId = UUID.nameUUIDFromBytes("round-1".toByteArray()),
                roundNumber = command.roundNumber,
                selectedOrganizationIds = command.selectedOrganizationIds,
                selectedRuntimeIds = command.selectedRuntimeIds,
                selectedParticipants = command.selectedParticipants,
                selectedOrganizationCount = command.selectedOrganizationCount,
                selectedRuntimeCount = 2,
                minimumNodesPerRound = command.minimumNodesPerRound,
                failureReason = ""
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<TrainingRoundStartFailedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("job-1".toByteArray()), event.trainingJobId)
        assertEquals(UUID.nameUUIDFromBytes("config-1".toByteArray()), event.trainingRunConfigurationId)
        assertEquals(command.featureSchemaId, event.featureSchemaId)
        assertEquals(UUID.nameUUIDFromBytes("round-1".toByteArray()), event.roundId)
        assertEquals(command.roundNumber, event.roundNumber)
        assertEquals(command.selectedOrganizationIds, event.selectedOrganizationIds)
        assertEquals(command.selectedRuntimeIds, event.selectedRuntimeIds)
        assertEquals(command.selectedParticipants, event.selectedParticipants)
        assertEquals(command.selectedOrganizationCount, event.selectedOrganizationCount)
        assertEquals(2, event.selectedRuntimeCount)
        assertEquals(command.minimumNodesPerRound, event.minimumNodesPerRound)
    }
}
