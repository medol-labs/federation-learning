package tech.medo.trainingorchestration.createtrainingjob

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.createtrainingjob.CreateTrainingJobCommand
import tech.medo.trainingorchestration.events.TrainingRunConfigurationDefinedEvent
import tech.medo.trainingorchestration.events.TrainingJobCreatedEvent



import java.util.UUID;
import java.math.BigDecimal;


class CreateTrainingJobDecisionTest {
    @Test
    fun CreateTrainingJobWithRunnableConfiguration() {


        val command = CreateTrainingJobCommand(
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray()),
            federationId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = UUID.nameUUIDFromBytes("config-1".toByteArray()),
            objective = ""
        )

        val events = (object : CreateTrainingJobDecision {}).decide(
            command
        )

        val event = events.filterIsInstance<TrainingJobCreatedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("job-1".toByteArray()), event.trainingJobId)
        assertEquals(command.federationId, event.federationId)
        assertEquals(UUID.nameUUIDFromBytes("config-1".toByteArray()), event.trainingRunConfigurationId)
        assertEquals(command.objective, event.objective)
    }
}
