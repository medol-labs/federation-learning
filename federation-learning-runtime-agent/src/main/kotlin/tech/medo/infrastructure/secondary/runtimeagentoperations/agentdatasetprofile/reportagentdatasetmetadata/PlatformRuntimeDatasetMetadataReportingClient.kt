package tech.medo.infrastructure.secondary.runtimeagentoperations.agentdatasetprofile.reportagentdatasetmetadata

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.math.BigDecimal
import java.util.UUID

@FeignClient(
    name = "federationLearningPlatformRuntimeDatasetMetadataReportingClient",
    url = "\${runtime-agent.dataset-metadata-reporting.platform-url:http://localhost:8080}"
)
interface PlatformRuntimeDatasetMetadataReportingClient {
    @PostMapping("/runtimedatasetmetadata/recordruntimedatasetmetadata")
    fun recordRuntimeDatasetMetadata(
        @RequestBody request: RecordRuntimeDatasetMetadataRequest
    ): RecordRuntimeDatasetMetadataResponse
}

data class RecordRuntimeDatasetMetadataRequest(
    val metadataReportId: UUID,
    val datasetId: UUID,
    val organizationId: UUID,
    val runtimeId: UUID,
    val featureSchemaId: UUID,
    val sampleCount: Int,
    val featureCount: Int,
    val schemaCompatible: Boolean?,
    val labelCompatible: Boolean?,
    val missingValueRate: BigDecimal?,
    val duplicateRate: BigDecimal?,
    val qualityScore: BigDecimal?,
    val nonIidScore: BigDecimal?,
    val classBalanceScore: BigDecimal?
)

data class RecordRuntimeDatasetMetadataResponse(
    val metadataReportId: UUID? = null
)
