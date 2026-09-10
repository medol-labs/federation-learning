package tech.medo.infrastructure.secondary.runtimeagentoperations.agentdatasetprofile.reportagentdatasetmetadata

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.events.AgentDatasetMetadataReportedEvent
import java.math.BigDecimal
import java.util.UUID

class ReportAgentDatasetMetadataToPlatformProcessorTest {
    private val metadataReportId = UUID.fromString("11111111-1111-4111-8111-111111111111")
    private val runtimeDatasetBindingId = UUID.fromString("22222222-2222-4222-8222-222222222222")
    private val datasetId = UUID.fromString("33333333-3333-4333-8333-333333333333")
    private val organizationId = UUID.fromString("44444444-4444-4444-8444-444444444444")
    private val runtimeId = UUID.fromString("55555555-5555-4555-8555-555555555555")
    private val featureSchemaId = UUID.fromString("66666666-6666-4666-8666-666666666666")

    @Test
    fun reportsAgentDatasetMetadataToPlatform() {
        val client = CapturingPlatformClient()
        val processor = ReportAgentDatasetMetadataToPlatformProcessor(
            client,
            RuntimeDatasetMetadataReportingProperties(enabled = true)
        )

        processor.on(event())

        val request = client.requests.single()
        assertEquals(metadataReportId, request.metadataReportId)
        assertEquals(datasetId, request.datasetId)
        assertEquals(organizationId, request.organizationId)
        assertEquals(runtimeId, request.runtimeId)
        assertEquals(featureSchemaId, request.featureSchemaId)
        assertEquals("credit-risk", request.datasetName)
        assertEquals(120, request.sampleCount)
        assertEquals(8, request.featureCount)
        assertEquals(true, request.schemaCompatible)
        assertEquals(true, request.labelCompatible)
        assertEquals(BigDecimal("0.0100"), request.missingValueRate)
        assertEquals(BigDecimal("0.0200"), request.duplicateRate)
        assertEquals(BigDecimal("0.9700"), request.qualityScore)
        assertEquals(BigDecimal("0.1000"), request.nonIidScore)
        assertEquals(BigDecimal("0.9000"), request.classBalanceScore)
    }

    @Test
    fun skipsReportingWhenDisabled() {
        val client = CapturingPlatformClient()
        val processor = ReportAgentDatasetMetadataToPlatformProcessor(
            client,
            RuntimeDatasetMetadataReportingProperties(enabled = false)
        )

        processor.on(event())

        assertTrue(client.requests.isEmpty())
    }

    private fun event(): AgentDatasetMetadataReportedEvent =
        AgentDatasetMetadataReportedEvent(
            metadataReportId = metadataReportId,
            runtimeDatasetBindingId = runtimeDatasetBindingId,
            datasetId = datasetId,
            organizationId = organizationId,
            organizationName = "Test Organization",
            runtimeId = runtimeId,
            runtimeName = "local runtime",
            featureSchemaId = featureSchemaId,
            featureDomain = "credit-risk",
            featureSchemaVersion = "v1",
            datasetName = "credit-risk",
            sampleCount = 120,
            featureCount = 8,
            schemaCompatible = true,
            labelCompatible = true,
            missingValueRate = BigDecimal("0.0100"),
            duplicateRate = BigDecimal("0.0200"),
            qualityScore = BigDecimal("0.9700"),
            nonIidScore = BigDecimal("0.1000"),
            classBalanceScore = BigDecimal("0.9000")
        )

    private class CapturingPlatformClient : PlatformRuntimeDatasetMetadataReportingClient {
        val requests = mutableListOf<RecordRuntimeDatasetMetadataRequest>()

        override fun recordRuntimeDatasetMetadata(
            request: RecordRuntimeDatasetMetadataRequest
        ): RecordRuntimeDatasetMetadataResponse {
            requests += request
            return RecordRuntimeDatasetMetadataResponse(metadataReportId = request.metadataReportId)
        }
    }
}
