package tech.medo.runtimeagentoperations.validatedatasetcontract

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.validatedatasetcontract.ValidateDatasetContractCommand

import tech.medo.runtimeagentoperations.dataset.DatasetState



@Component
class ValidateDatasetContractCommandHandler(
    private val decision: ValidateDatasetContractDecision
) {
    @CommandHandler
    fun handle(
        command: ValidateDatasetContractCommand,
        @InjectEntity(idProperty = "selection") state: DatasetState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
