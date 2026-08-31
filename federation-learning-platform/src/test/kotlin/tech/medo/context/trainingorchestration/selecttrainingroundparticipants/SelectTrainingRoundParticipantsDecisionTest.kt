package tech.medo.trainingorchestration.selecttrainingroundparticipants

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsCommand
import tech.medo.trainingorchestration.events.TrainingJobSubmittedEvent
import tech.medo.trainingorchestration.events.TrainingRoundParticipantsSelectedEvent
import tech.medo.trainingorchestration.events.TrainingRoundParticipantSelectionFailedEvent
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsResult
import java.util.UUID
import java.math.BigDecimal
import tech.medo.trainingorchestration.domain.types.TrainingRoundParticipant
import java.time.LocalDateTime

class SelectTrainingRoundParticipantsDecisionTest {
    @Test
    fun SelectParticipantsWhenRuntimePoolReachesQuorum() {


        val command = SelectTrainingRoundParticipantsCommand(
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray())
        )

        val events = (object : SelectTrainingRoundParticipantsDecision {}).decide(
            command,
            portResult = SelectTrainingRoundParticipantsResult.Succeeded(
                trainingRunConfigurationId = java.util.UUID.randomUUID(),
                featureSchemaId = java.util.UUID.randomUUID(),
                roundId = java.util.UUID.randomUUID(),
                roundNumber = 0,
                maxRounds = 0,
                minimumAccuracy = java.math.BigDecimal.ZERO,
                minimumNodesPerRound = 0,
                secureAggregationRequired = false,
                selectedOrganizationIds = emptyList(),
                selectedRuntimeIds = emptyList(),
                selectedParticipants = emptyList(),
                selectedOrganizationCount = 0,
                selectedRuntimeCount = 0
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<TrainingRoundParticipantsSelectedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("job-1".toByteArray()), event.trainingJobId)
    }

    @Test
    fun RejectParticipantSnapshotBelowQuorum() {


        val command = SelectTrainingRoundParticipantsCommand(
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray())
        )

        val events = (object : SelectTrainingRoundParticipantsDecision {}).decide(
            command,
            portResult = SelectTrainingRoundParticipantsResult.Rejected(
                trainingRunConfigurationId = java.util.UUID.randomUUID(),
                featureSchemaId = java.util.UUID.randomUUID(),
                roundId = java.util.UUID.randomUUID(),
                roundNumber = 0,
                maxRounds = 0,
                minimumAccuracy = java.math.BigDecimal.ZERO,
                minimumNodesPerRound = 0,
                secureAggregationRequired = false,
                selectedOrganizationIds = emptyList(),
                selectedRuntimeIds = emptyList(),
                selectedParticipants = emptyList(),
                selectedOrganizationCount = 0,
                selectedRuntimeCount = 0,
                failureReason = ""
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<TrainingRoundParticipantSelectionFailedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("job-1".toByteArray()), event.trainingJobId)
    }
}
