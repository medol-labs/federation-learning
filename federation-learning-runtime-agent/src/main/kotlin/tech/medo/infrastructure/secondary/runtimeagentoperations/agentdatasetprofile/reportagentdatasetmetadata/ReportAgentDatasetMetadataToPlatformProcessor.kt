package tech.medo.infrastructure.secondary.runtimeagentoperations.agentdatasetprofile.reportagentdatasetmetadata

import feign.FeignException
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.events.AgentDatasetMetadataReportedEvent

@Component
class ReportAgentDatasetMetadataToPlatformProcessor(
    private val client: PlatformRuntimeDatasetMetadataReportingClient,
    private val properties: RuntimeDatasetMetadataReportingProperties
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @EventHandler
    fun on(event: AgentDatasetMetadataReportedEvent) {
        if (!properties.enabled) {
            logger.debug(
                "Skip platform runtime dataset metadata reporting because it is disabled. metadataReportId={}",
                event.metadataReportId
            )
            return
        }

        val request = RecordRuntimeDatasetMetadataRequest(
            metadataReportId = event.metadataReportId,
            datasetId = event.datasetId,
            organizationId = event.organizationId,
            runtimeId = event.runtimeId,
            featureSchemaId = event.featureSchemaId,
            sampleCount = event.sampleCount,
            featureCount = event.featureCount,
            schemaCompatible = event.schemaCompatible,
            labelCompatible = event.labelCompatible,
            missingValueRate = event.missingValueRate,
            duplicateRate = event.duplicateRate,
            qualityScore = event.qualityScore,
            nonIidScore = event.nonIidScore,
            classBalanceScore = event.classBalanceScore
        )

        try {
            logger.debug("Reporting runtime dataset metadata to platform. request={}", request)
            client.recordRuntimeDatasetMetadata(request)
            logger.info(
                "Reported runtime dataset metadata to platform. metadataReportId={}, datasetId={}, runtimeId={}",
                event.metadataReportId,
                event.datasetId,
                event.runtimeId
            )
        } catch (ex: FeignException) {
            logger.warn(
                "Platform rejected runtime dataset metadata report. status={}, metadataReportId={}, datasetId={}, runtimeId={}",
                ex.status(),
                event.metadataReportId,
                event.datasetId,
                event.runtimeId,
                ex
            )
        } catch (ex: Exception) {
            logger.warn(
                "Platform runtime dataset metadata reporting unavailable. metadataReportId={}, datasetId={}, runtimeId={}",
                event.metadataReportId,
                event.datasetId,
                event.runtimeId,
                ex
            )
        }
    }
}
