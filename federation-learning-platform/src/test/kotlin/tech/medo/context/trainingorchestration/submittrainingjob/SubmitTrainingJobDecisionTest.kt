package tech.medo.trainingorchestration.submittrainingjob

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.submittrainingjob.SubmitTrainingJobCommand
import tech.medo.trainingorchestration.events.TrainingJobCreatedEvent
import tech.medo.trainingorchestration.events.TrainingJobSubmittedEvent
import tech.medo.trainingorchestration.trainingjob.TrainingJobState
import java.util.UUID

class SubmitTrainingJobDecisionTest {
    @Test
    fun SubmitCreatedTrainingJob() {
        val state = TrainingJobState()
        state.evolve(
            TrainingJobCreatedEvent(
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray()),
            federationId = java.util.UUID.randomUUID(),
            initialModelId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = UUID.nameUUIDFromBytes("config-1".toByteArray()),
            objective = ""
            )
        )

        val command = SubmitTrainingJobCommand(
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray())
        )

        val events = (object : SubmitTrainingJobDecision {}).decide(
            command,
            state = state
        )

        val event = events.filterIsInstance<TrainingJobSubmittedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("job-1".toByteArray()), event.trainingJobId)
    }
}
