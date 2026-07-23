package tech.medo.datasetgovernance.approvedatasetfortraining

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.datasetgovernance.approvedatasetfortraining.ApproveDatasetForTrainingCommand

import tech.medo.datasetgovernance.dataset.DatasetState



@Component
class ApproveDatasetForTrainingCommandHandler(
    private val decision: ApproveDatasetForTrainingDecision
) {
    @CommandHandler
    fun handle(
        command: ApproveDatasetForTrainingCommand,
        @InjectEntity(idProperty = "selection") state: DatasetState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
