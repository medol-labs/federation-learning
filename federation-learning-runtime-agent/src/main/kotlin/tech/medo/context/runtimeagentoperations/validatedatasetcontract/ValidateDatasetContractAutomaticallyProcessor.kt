package tech.medo.runtimeagentoperations.validatedatasetcontract

import tech.medo.runtimeagentoperations.events.AgentDatasetMetadataReportedEvent
import tech.medo.runtimeagentoperations.validatedatasetcontract.ValidateDatasetContractCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class ValidateDatasetContractAutomaticallyProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: AgentDatasetMetadataReportedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(ValidateDatasetContractCommand(datasetId = event.datasetId, metadataReportId = event.metadataReportId, featureSchemaId = event.featureSchemaId, organizationId = event.organizationId, datasetName = "" /* TODO: provide datasetName */)).resultMessage
}
