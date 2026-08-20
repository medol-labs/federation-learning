package tech.medo.runtimeagentoperations.declaredataset

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.declaredataset.DeclareDatasetCommand
import tech.medo.runtimeagentoperations.declaredataset.DeclareDatasetInput
import tech.medo.runtimeagentoperations.declaredataset.DeclareDatasetService




@Component
class DeclareDatasetCommandHandler(
    private val decision: DeclareDatasetDecision,
    private val declareDatasetService: DeclareDatasetService
) {
    @CommandHandler
    fun handle(
        command: DeclareDatasetCommand,
        eventAppender: EventAppender
    ) {
        val input = DeclareDatasetInput(datasetId = command.datasetId, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, datasetName = command.datasetName, datasetType = command.datasetType, datasetUsage = command.datasetUsage)
        val portResult = declareDatasetService.execute(input)

        eventAppender.append(decision.decide(command, portResult))
    }
}
