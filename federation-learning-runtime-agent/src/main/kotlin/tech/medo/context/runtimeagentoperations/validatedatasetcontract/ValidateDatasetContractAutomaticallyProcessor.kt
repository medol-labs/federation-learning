package tech.medo.runtimeagentoperations.validatedatasetcontract

import tech.medo.runtimeagentoperations.events.AgentDatasetMetadataReportedEvent
import tech.medo.runtimeagentoperations.validatedatasetcontract.ValidateDatasetContractCommand
import tech.medo.runtimeagentoperations.datasetcapability.DatasetCapabilityReadModelRepository
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.replay.annotation.DisallowReplay
import org.springframework.stereotype.Component

@Component
class ValidateDatasetContractAutomaticallyProcessor(
    private val commandGateway: CommandGateway,
    private val datasetCapabilityRepository: DatasetCapabilityReadModelRepository
) {
    @DisallowReplay
    @EventHandler
    fun on(event: AgentDatasetMetadataReportedEvent): java.util.concurrent.CompletableFuture<*> {
        val datasetName = datasetCapabilityRepository.findById(event.datasetId)?.datasetName.orEmpty()
        return commandGateway.send(
            ValidateDatasetContractCommand(
                datasetId = event.datasetId,
                metadataReportId = event.metadataReportId,
                featureSchemaId = event.featureSchemaId,
                organizationId = event.organizationId,
                datasetName = datasetName,
                schemaCompatible = event.schemaCompatible,
                labelCompatible = event.labelCompatible,
                qualityScore = event.qualityScore,
                nonIidScore = event.nonIidScore
            )
        ).resultMessage
    }
}
