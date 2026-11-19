package tech.medo.domain.trainingorchestration

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import tech.medo.domain.trainingorchestration.completetraininground.CompleteTrainingRoundDecisionComponent
import tech.medo.domain.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsDecisionComponent
import tech.medo.trainingorchestration.completetraininground.CompleteTrainingRoundCommand
import tech.medo.trainingorchestration.events.*
import tech.medo.trainingorchestration.selecttrainingroundparticipants.*
import tech.medo.trainingorchestration.traininground.*

class RoundSchedulingRegressionTest {
    @Test
    fun repeatedEvaluationAndRepeatedSelectionDoNotForkTheRound() {
        val id = UUID.randomUUID()
        val first = UUID.randomUUID()
        val command = CompleteTrainingRoundCommand(id, id, "binary", id, first, 1, 2,
            BigDecimal.ZERO, id, "lr", "result", "registry", "json", "digest", null, BigDecimal.ONE)
        val state = TrainingRoundState().apply { federationId = id; evaluatedSampleCount = 10 }
        val finish = CompleteTrainingRoundDecisionComponent()
        val completed = finish.decide(command, state).single() as TrainingRoundCompletedEvent
        assertNotEquals(first, completed.nextRoundId)
        val mapper = com.fasterxml.jackson.databind.ObjectMapper().findAndRegisterModules()
        val legacy = mapper.valueToTree<com.fasterxml.jackson.databind.node.ObjectNode>(completed)
        legacy.remove("nextRoundId")
        val restored = mapper.treeToValue(legacy, TrainingRoundCompletedEvent::class.java)
        assertNotEquals(first, restored.nextRoundId)
        assertEquals(restored, mapper.treeToValue(legacy, TrainingRoundCompletedEvent::class.java))
        assertEquals("roundId", TrainingRoundState::class.java.getAnnotation(org.axonframework.extension.spring.stereotype.EventSourced::class.java).tagKey)
        state.evolve(completed)
        assertTrue(finish.decide(command, state).isEmpty())

        val select = SelectTrainingRoundParticipantsCommand(id, completed.nextRoundId, 1, id, null, id, null, id, null, null, "binary")
        val reservation = TrainingRoundTrainingJobIdPreviousRoundNumberReservationState()
        val result = SelectTrainingRoundParticipantsResult.Succeeded(2, 2, BigDecimal.ZERO, "avg",
            null, null, null, null, null, 1, false, listOf(id), listOf(id), emptyList(), 1, 1)
        val decision = SelectTrainingRoundParticipantsDecisionComponent()
        val now = LocalDateTime.now()
        val events = decision.decide(select, reservation, result, now)
        assertEquals(completed.nextRoundId, events.filterIsInstance<TrainingRoundParticipantsSelectedEvent>().single().roundId)
        reservation.evolve(events.filterIsInstance<TrainingRoundTrainingJobIdPreviousRoundNumberReservedEvent>().single())
        val duplicate = select.copy(roundId = UUID.randomUUID())
        assertEquals(select.trainingRoundTrainingJobIdPreviousRoundNumberSelection, duplicate.trainingRoundTrainingJobIdPreviousRoundNumberSelection)
        assertTrue(decision.decide(duplicate, reservation, result, now).isEmpty())
    }
}
