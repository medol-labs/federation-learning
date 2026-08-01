package tech.medo.trainingorchestration.generateparticipantexecutionplan

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.generateparticipantexecutionplan.GenerateParticipantExecutionPlanCommand
import tech.medo.trainingorchestration.events.ParticipantExecutionPlanGeneratedEvent



import java.util.UUID;


class GenerateParticipantExecutionPlanDecisionTest {
    @Test
    fun GenerateParticipantExecutionPlanEmitsParticipantExecutionPlanGeneratedEvent() {
        val events = (object : GenerateParticipantExecutionPlanDecision {}).decide(
            GenerateParticipantExecutionPlanCommand(
            executionPlanId = java.util.UUID.randomUUID(),
            executionSessionId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            roundId = java.util.UUID.randomUUID(),
            roundNumber = 0,
            runtimeId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            baseModelVersionId = java.util.UUID.randomUUID()
            )
        )

        assertTrue(events.any { it is ParticipantExecutionPlanGeneratedEvent })
    }
}
