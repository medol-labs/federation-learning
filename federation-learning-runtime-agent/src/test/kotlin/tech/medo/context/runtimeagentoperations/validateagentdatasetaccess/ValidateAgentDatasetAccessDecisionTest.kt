package tech.medo.runtimeagentoperations.validateagentdatasetaccess

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.validateagentdatasetaccess.ValidateAgentDatasetAccessCommand
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessValidatedEvent



import java.util.UUID;


class ValidateAgentDatasetAccessDecisionTest {
    @Test
    fun ValidateAgentDatasetAccessEmitsAgentDatasetAccessValidatedEvent() {
        val events = ValidateAgentDatasetAccessDecision().decide(
            ValidateAgentDatasetAccessCommand(
            datasetAccessValidationId = java.util.UUID.randomUUID(),
            runtimeDatasetBindingId = java.util.UUID.randomUUID()
            )
        )

        assertTrue(events.any { it is AgentDatasetAccessValidatedEvent })
    }
}
