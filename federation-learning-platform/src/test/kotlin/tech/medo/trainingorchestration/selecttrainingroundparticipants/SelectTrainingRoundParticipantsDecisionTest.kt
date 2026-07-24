package tech.medo.trainingorchestration.selecttrainingroundparticipants

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsCommand
import tech.medo.trainingorchestration.events.TrainingJobSubmittedEvent
import tech.medo.trainingorchestration.events.TrainingRoundParticipantsSelectedEvent



import java.util.UUID;
import tech.medo.trainingorchestration.domain.types.TrainingRoundParticipant;


class SelectTrainingRoundParticipantsDecisionTest {
    @Test
    fun SelectParticipantsWhenRuntimePoolReachesQuorum() {


        val command = SelectTrainingRoundParticipantsCommand(
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray())
        )

        val events = SelectTrainingRoundParticipantsDecision().decide(
            command
        )

        val event = events.filterIsInstance<TrainingRoundParticipantsSelectedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("job-1".toByteArray()), event.trainingJobId)
    }
}
