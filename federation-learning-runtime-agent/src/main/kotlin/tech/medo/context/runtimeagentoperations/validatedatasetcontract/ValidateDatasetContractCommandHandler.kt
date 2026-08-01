package tech.medo.runtimeagentoperations.validatedatasetcontract

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.validatedatasetcontract.ValidateDatasetContractCommand
import tech.medo.runtimeagentoperations.validatedatasetcontract.ValidateDatasetContractInput
import tech.medo.runtimeagentoperations.validatedatasetcontract.ValidateDatasetContractService
import tech.medo.runtimeagentoperations.dataset.DatasetState



@Component
class ValidateDatasetContractCommandHandler(
    private val decision: ValidateDatasetContractDecision,
    private val validateDatasetContractService: ValidateDatasetContractService
) {
    @CommandHandler
    fun handle(
        command: ValidateDatasetContractCommand,
        @InjectEntity(idProperty = "selection") state: DatasetState,
        eventAppender: EventAppender
    ) {
        val input = ValidateDatasetContractInput(datasetId = command.datasetId, metadataReportId = command.metadataReportId, featureSchemaId = command.featureSchemaId)
        val portResult = validateDatasetContractService.execute(input)
        val now = java.time.LocalDateTime.now()

        eventAppender.append(decision.decide(command, state, portResult, now))
    }
}
