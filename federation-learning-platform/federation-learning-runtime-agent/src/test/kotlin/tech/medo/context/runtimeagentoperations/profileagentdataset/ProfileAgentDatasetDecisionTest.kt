package tech.medo.runtimeagentoperations.profileagentdataset

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.profileagentdataset.ProfileAgentDatasetCommand
import tech.medo.runtimeagentoperations.events.AgentDatasetMetadataReportedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetProfilingFailedEvent
import tech.medo.runtimeagentoperations.profileagentdataset.ProfileAgentDatasetResult
import java.util.UUID
import java.math.BigDecimal
import java.time.LocalDateTime

class ProfileAgentDatasetDecisionTest {
    @Test
    fun AgentDatasetMetadataReported() {


        val command = ProfileAgentDatasetCommand(
            metadataReportId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
            runtimeDatasetBindingId = UUID.fromString("22222222-2222-4222-8222-222222222222"),
            datasetId = UUID.fromString("33333333-3333-4333-8333-333333333333"),
            organizationId = UUID.fromString("44444444-4444-4444-8444-444444444444"),
            organizationName = null,
            featureSchemaId = UUID.fromString("55555555-5555-4555-8555-555555555555"),
            featureDomain = null,
            featureSchemaVersion = null,
            datasetName = "",
            runtimeId = UUID.fromString("66666666-6666-4666-8666-666666666666"),
            runtimeName = null
        )

        val events = (object : ProfileAgentDatasetDecision {}).decide(
            command,
            portResult = ProfileAgentDatasetResult.Succeeded(
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

        val event = events.filterIsInstance<AgentDatasetMetadataReportedEvent>().single()
        assertEquals(UUID.fromString("11111111-1111-4111-8111-111111111111"), event.metadataReportId)
        assertEquals(UUID.fromString("22222222-2222-4222-8222-222222222222"), event.runtimeDatasetBindingId)
        assertEquals(UUID.fromString("33333333-3333-4333-8333-333333333333"), event.datasetId)
        assertEquals(UUID.fromString("44444444-4444-4444-8444-444444444444"), event.organizationId)
        assertEquals(command.organizationName, event.organizationName)
        assertEquals(UUID.fromString("66666666-6666-4666-8666-666666666666"), event.runtimeId)
        assertEquals(command.runtimeName, event.runtimeName)
        assertEquals(UUID.fromString("55555555-5555-4555-8555-555555555555"), event.featureSchemaId)
        assertEquals(command.featureDomain, event.featureDomain)
        assertEquals(command.featureSchemaVersion, event.featureSchemaVersion)
        assertEquals(command.datasetName, event.datasetName)
    }

    @Test
    fun AgentDatasetProfilingFailed() {


        val command = ProfileAgentDatasetCommand(
            metadataReportId = UUID.fromString("77777777-7777-4777-8777-777777777777"),
            runtimeDatasetBindingId = UUID.fromString("88888888-8888-4888-8888-888888888888"),
            datasetId = UUID.fromString("99999999-9999-4999-8999-999999999999"),
            organizationId = UUID.fromString("aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa"),
            organizationName = null,
            featureSchemaId = UUID.fromString("bbbbbbbb-bbbb-4bbb-8bbb-bbbbbbbbbbbb"),
            featureDomain = null,
            featureSchemaVersion = null,
            datasetName = "",
            runtimeId = UUID.fromString("cccccccc-cccc-4ccc-8ccc-cccccccccccc"),
            runtimeName = null
        )

        val events = (object : ProfileAgentDatasetDecision {}).decide(
            command,
            portResult = ProfileAgentDatasetResult.Rejected(
                failureReason = ""
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<AgentDatasetProfilingFailedEvent>().single()
        assertEquals(UUID.fromString("77777777-7777-4777-8777-777777777777"), event.metadataReportId)
        assertEquals(UUID.fromString("88888888-8888-4888-8888-888888888888"), event.runtimeDatasetBindingId)
        assertEquals(UUID.fromString("99999999-9999-4999-8999-999999999999"), event.datasetId)
        assertEquals(UUID.fromString("aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa"), event.organizationId)
        assertEquals(command.organizationName, event.organizationName)
        assertEquals(UUID.fromString("bbbbbbbb-bbbb-4bbb-8bbb-bbbbbbbbbbbb"), event.featureSchemaId)
        assertEquals(command.featureDomain, event.featureDomain)
        assertEquals(command.featureSchemaVersion, event.featureSchemaVersion)
        assertEquals(command.datasetName, event.datasetName)
        assertEquals(UUID.fromString("cccccccc-cccc-4ccc-8ccc-cccccccccccc"), event.runtimeId)
        assertEquals(command.runtimeName, event.runtimeName)
    }
}
