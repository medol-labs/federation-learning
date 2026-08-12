package tech.medo.trainingorchestration.createtrainingjob

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.createtrainingjob.CreateTrainingJobCommand
import tech.medo.trainingorchestration.events.TrainingJobCreatedEvent
import tech.medo.trainingorchestration.trainingrunconfiguration.TrainingRunConfigurationState



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
        val featureSchemaId = UUID.nameUUIDFromBytes("schema-1".toByteArray())
        val trainingRunConfiguration = TrainingRunConfigurationState().apply {
            this.trainingRunConfigurationId = command.trainingRunConfigurationId
            this.federationId = command.federationId
            this.featureSchemaId = featureSchemaId
            this.minimumNodesPerRound = 1
        }

        val events = (object : CreateTrainingJobDecision {}).decide(
            command,
            trainingRunConfiguration
        )

        val event = events.filterIsInstance<TrainingJobCreatedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("job-1".toByteArray()), event.trainingJobId)
        assertEquals(command.federationId, event.federationId)
        assertEquals(featureSchemaId, event.featureSchemaId)
        assertEquals(UUID.nameUUIDFromBytes("config-1".toByteArray()), event.trainingRunConfigurationId)
        assertEquals(command.objective, event.objective)
    }
}
