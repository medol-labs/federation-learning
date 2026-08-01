package tech.medo.runtimeagentoperations.retrydatasetcontractvalidation

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.retrydatasetcontractvalidation.RetryDatasetContractValidationCommand
import tech.medo.runtimeagentoperations.retrydatasetcontractvalidation.RetryDatasetContractValidationInput
import tech.medo.runtimeagentoperations.retrydatasetcontractvalidation.RetryDatasetContractValidationService
import tech.medo.runtimeagentoperations.dataset.DatasetState



@Component
class RetryDatasetContractValidationCommandHandler(
    private val decision: RetryDatasetContractValidationDecision,
    private val retryDatasetContractValidationService: RetryDatasetContractValidationService
) {
    @CommandHandler
    fun handle(
        command: RetryDatasetContractValidationCommand,
        @InjectEntity(idProperty = "selection") state: DatasetState,
        eventAppender: EventAppender
    ) {
        val input = RetryDatasetContractValidationInput(datasetId = command.datasetId)
        val portResult = retryDatasetContractValidationService.execute(input)
        val now = java.time.LocalDateTime.now()

        eventAppender.append(decision.decide(command, state, portResult, now))
    }
}
