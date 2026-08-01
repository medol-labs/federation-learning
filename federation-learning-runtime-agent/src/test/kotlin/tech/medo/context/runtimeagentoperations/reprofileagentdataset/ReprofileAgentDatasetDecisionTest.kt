package tech.medo.runtimeagentoperations.reprofileagentdataset

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.reprofileagentdataset.ReprofileAgentDatasetCommand
import tech.medo.runtimeagentoperations.events.AgentDatasetProfilingFailedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetReprofiledEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetReprofilingFailedEvent

import tech.medo.runtimeagentoperations.agentdatasetprofile.AgentDatasetProfileState
import tech.medo.runtimeagentoperations.reprofileagentdataset.ReprofileAgentDatasetResult
import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDateTime

class ReprofileAgentDatasetDecisionTest {
    @Test
    fun AgentDatasetReprofiled() {
        val state = AgentDatasetProfileState()
        state.evolve(
            AgentDatasetProfilingFailedEvent(
            metadataReportId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
            runtimeDatasetBindingId = UUID.fromString("22222222-2222-4222-8222-222222222222"),
            datasetId = UUID.fromString("33333333-3333-4333-8333-333333333333"),
            organizationId = UUID.fromString("44444444-4444-4444-8444-444444444444"),
            featureSchemaId = UUID.fromString("55555555-5555-4555-8555-555555555555"),
            runtimeId = UUID.fromString("66666666-6666-4666-8666-666666666666"),
            failureReason = "CSV header could not be read"
            )
        )

        val command = ReprofileAgentDatasetCommand(
            metadataReportId = UUID.fromString("77777777-7777-4777-8777-777777777777"),
            runtimeDatasetBindingId = UUID.fromString("22222222-2222-4222-8222-222222222222")
        )

        val events = (object : ReprofileAgentDatasetDecision {}).decide(
            command,
            state = state,
            portResult = ReprofileAgentDatasetResult.Succeeded(
                sampleCount = 0,
                featureCount = 0,
                schemaCompatible = null,
                labelCompatible = null,
                missingValueRate = null,
                duplicateRate = null,
                qualityScore = null,
                nonIidScore = null,
                classBalanceScore = null
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<AgentDatasetReprofiledEvent>().single()
        assertEquals(UUID.fromString("77777777-7777-4777-8777-777777777777"), event.metadataReportId)
        assertEquals(UUID.fromString("22222222-2222-4222-8222-222222222222"), event.runtimeDatasetBindingId)
    }

    @Test
    fun AgentDatasetReprofilingFailed() {
        val state = AgentDatasetProfileState()
        state.evolve(
            AgentDatasetProfilingFailedEvent(
            metadataReportId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
            runtimeDatasetBindingId = UUID.fromString("22222222-2222-4222-8222-222222222222"),
            datasetId = UUID.fromString("33333333-3333-4333-8333-333333333333"),
            organizationId = UUID.fromString("44444444-4444-4444-8444-444444444444"),
            featureSchemaId = UUID.fromString("55555555-5555-4555-8555-555555555555"),
            runtimeId = UUID.fromString("66666666-6666-4666-8666-666666666666"),
            failureReason = "CSV header could not be read"
            )
        )

        val command = ReprofileAgentDatasetCommand(
            metadataReportId = UUID.fromString("88888888-8888-4888-8888-888888888888"),
            runtimeDatasetBindingId = UUID.fromString("22222222-2222-4222-8222-222222222222")
        )

        val events = (object : ReprofileAgentDatasetDecision {}).decide(
            command,
            state = state,
            portResult = ReprofileAgentDatasetResult.Rejected(
                failureReason = ""
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<AgentDatasetReprofilingFailedEvent>().single()
        assertEquals(UUID.fromString("88888888-8888-4888-8888-888888888888"), event.metadataReportId)
        assertEquals(UUID.fromString("22222222-2222-4222-8222-222222222222"), event.runtimeDatasetBindingId)
    }
}
