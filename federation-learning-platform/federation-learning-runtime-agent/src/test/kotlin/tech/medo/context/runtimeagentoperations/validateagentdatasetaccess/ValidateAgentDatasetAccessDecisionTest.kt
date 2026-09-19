package tech.medo.runtimeagentoperations.validateagentdatasetaccess

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.validateagentdatasetaccess.ValidateAgentDatasetAccessCommand
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessValidatedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessValidationFailedEvent
import tech.medo.runtimeagentoperations.validateagentdatasetaccess.ValidateAgentDatasetAccessResult
import java.util.UUID
import java.time.LocalDateTime

class ValidateAgentDatasetAccessDecisionTest {
    @Test
    fun AgentDatasetAccessValidated() {


        val command = ValidateAgentDatasetAccessCommand(
            datasetAccessValidationId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
            runtimeDatasetBindingId = UUID.fromString("22222222-2222-4222-8222-222222222222"),
            datasetId = UUID.fromString("33333333-3333-4333-8333-333333333333"),
            organizationId = UUID.fromString("77777777-7777-4777-8777-777777777777"),
            organizationName = null,
            featureSchemaId = UUID.fromString("88888888-8888-4888-8888-888888888888"),
            featureDomain = null,
            featureSchemaVersion = null,
            datasetName = "",
            runtimeId = UUID.fromString("44444444-4444-4444-8444-444444444444"),
            runtimeName = null,
            filePath = "/data/credit-risk.csv",
            dataFormat = "CSV"
        )

        val events = (object : ValidateAgentDatasetAccessDecision {}).decide(
            command,
            portResult = ValidateAgentDatasetAccessResult.Succeeded(
                readable = false,
                schemaReadable = false,
                sampleBatchReadable = false
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<AgentDatasetAccessValidatedEvent>().single()
        assertEquals(UUID.fromString("11111111-1111-4111-8111-111111111111"), event.datasetAccessValidationId)
        assertEquals(UUID.fromString("22222222-2222-4222-8222-222222222222"), event.runtimeDatasetBindingId)
        assertEquals(UUID.fromString("33333333-3333-4333-8333-333333333333"), event.datasetId)
        assertEquals(UUID.fromString("77777777-7777-4777-8777-777777777777"), event.organizationId)
        assertEquals(command.organizationName, event.organizationName)
        assertEquals(UUID.fromString("88888888-8888-4888-8888-888888888888"), event.featureSchemaId)
        assertEquals(command.featureDomain, event.featureDomain)
        assertEquals(command.featureSchemaVersion, event.featureSchemaVersion)
        assertEquals(command.datasetName, event.datasetName)
        assertEquals(UUID.fromString("44444444-4444-4444-8444-444444444444"), event.runtimeId)
        assertEquals(command.runtimeName, event.runtimeName)
    }

    @Test
    fun AgentDatasetAccessValidationFailed() {


        val command = ValidateAgentDatasetAccessCommand(
            datasetAccessValidationId = UUID.fromString("33333333-3333-4333-8333-333333333333"),
            runtimeDatasetBindingId = UUID.fromString("44444444-4444-4444-8444-444444444444"),
            datasetId = UUID.fromString("55555555-5555-4555-8555-555555555555"),
            organizationId = UUID.fromString("77777777-7777-4777-8777-777777777777"),
            organizationName = null,
            featureSchemaId = UUID.fromString("88888888-8888-4888-8888-888888888888"),
            featureDomain = null,
            featureSchemaVersion = null,
            datasetName = "",
            runtimeId = UUID.fromString("66666666-6666-4666-8666-666666666666"),
            runtimeName = null,
            filePath = "/data/missing.csv",
            dataFormat = "CSV"
        )

        val events = (object : ValidateAgentDatasetAccessDecision {}).decide(
            command,
            portResult = ValidateAgentDatasetAccessResult.Rejected(
                failureReason = ""
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<AgentDatasetAccessValidationFailedEvent>().single()
        assertEquals(UUID.fromString("33333333-3333-4333-8333-333333333333"), event.datasetAccessValidationId)
        assertEquals(UUID.fromString("44444444-4444-4444-8444-444444444444"), event.runtimeDatasetBindingId)
        assertEquals(UUID.fromString("55555555-5555-4555-8555-555555555555"), event.datasetId)
        assertEquals(UUID.fromString("77777777-7777-4777-8777-777777777777"), event.organizationId)
        assertEquals(command.organizationName, event.organizationName)
        assertEquals(UUID.fromString("88888888-8888-4888-8888-888888888888"), event.featureSchemaId)
        assertEquals(command.featureDomain, event.featureDomain)
        assertEquals(command.featureSchemaVersion, event.featureSchemaVersion)
        assertEquals(command.datasetName, event.datasetName)
        assertEquals(UUID.fromString("66666666-6666-4666-8666-666666666666"), event.runtimeId)
        assertEquals(command.runtimeName, event.runtimeName)
    }
}
