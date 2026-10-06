package tech.medo.runtimeagentoperations.retrydatasetcontractvalidation

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.retrydatasetcontractvalidation.RetryDatasetContractValidationCommand
import tech.medo.runtimeagentoperations.retrydatasetcontractvalidation.RetryDatasetContractValidationInput
import tech.medo.runtimeagentoperations.retrydatasetcontractvalidation.RetryDatasetContractValidationService


import tech.medo.runtimeagentoperations.dataset.DatasetState
import tech.medo.runtimeagentoperations.domain.states.DatasetStateEnum


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
        if (state.currentState != DatasetStateEnum.ContractValidationCompleted) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.runtimeagentoperations.retryDatasetContractValidation.requiresState",
                args = mapOf(
                    "command" to "RetryDatasetContractValidation",
                    "aggregate" to "Dataset",
                    "expectedState" to "ContractValidationCompleted",
                    "actualState" to state.currentState.toString()
                ),
                message = "RetryDatasetContractValidation requires Dataset to be ContractValidationCompleted."
            )
        }
        val input = RetryDatasetContractValidationInput(datasetId = command.datasetId)
        val portResult = retryDatasetContractValidationService.execute(input)
        val now = java.time.LocalDateTime.now()

        eventAppender.append(decision.decide(command, state, portResult, now))
    }
}
