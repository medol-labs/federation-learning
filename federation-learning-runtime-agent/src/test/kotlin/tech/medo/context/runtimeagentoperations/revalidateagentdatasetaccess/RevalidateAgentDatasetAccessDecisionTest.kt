package tech.medo.runtimeagentoperations.revalidateagentdatasetaccess

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.revalidateagentdatasetaccess.RevalidateAgentDatasetAccessCommand
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessRevalidatedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessRevalidationFailedEvent

import tech.medo.runtimeagentoperations.agentdatasetaccessvalidation.AgentDatasetAccessValidationState
import tech.medo.runtimeagentoperations.revalidateagentdatasetaccess.RevalidateAgentDatasetAccessResult
import java.util.UUID;
import java.time.LocalDateTime

class RevalidateAgentDatasetAccessDecisionTest {
    @Test
    fun AgentDatasetAccessRevalidated() {
        val state = AgentDatasetAccessValidationState()


        val command = RevalidateAgentDatasetAccessCommand(
            datasetAccessValidationId = UUID.fromString("55555555-5555-4555-8555-555555555555"),
            runtimeDatasetBindingId = UUID.fromString("22222222-2222-4222-8222-222222222222")
        )

        val events = RevalidateAgentDatasetAccessDecision().decide(
            command,
            state = state,
            portResult = RevalidateAgentDatasetAccessResult.Succeeded(
                runtimeDatasetBindingId = UUID.fromString("22222222-2222-4222-8222-222222222222"),
                datasetId = java.util.UUID.randomUUID(),
                runtimeId = java.util.UUID.randomUUID(),
                readable = false,
                schemaReadable = false,
                sampleBatchReadable = false
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<AgentDatasetAccessRevalidatedEvent>().single()
        assertEquals(UUID.fromString("55555555-5555-4555-8555-555555555555"), event.datasetAccessValidationId)
        assertEquals(UUID.fromString("22222222-2222-4222-8222-222222222222"), event.runtimeDatasetBindingId)
    }

    @Test
    fun AgentDatasetAccessRevalidationFailed() {
        val state = AgentDatasetAccessValidationState()


        val command = RevalidateAgentDatasetAccessCommand(
            datasetAccessValidationId = UUID.fromString("66666666-6666-4666-8666-666666666666"),
            runtimeDatasetBindingId = UUID.fromString("44444444-4444-4444-8444-444444444444")
        )

        val events = RevalidateAgentDatasetAccessDecision().decide(
            command,
            state = state,
            portResult = RevalidateAgentDatasetAccessResult.Rejected(
                runtimeDatasetBindingId = UUID.fromString("44444444-4444-4444-8444-444444444444"),
                datasetId = java.util.UUID.randomUUID(),
                runtimeId = java.util.UUID.randomUUID(),
                failureReason = ""
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<AgentDatasetAccessRevalidationFailedEvent>().single()
        assertEquals(UUID.fromString("66666666-6666-4666-8666-666666666666"), event.datasetAccessValidationId)
        assertEquals(UUID.fromString("44444444-4444-4444-8444-444444444444"), event.runtimeDatasetBindingId)
    }
}
