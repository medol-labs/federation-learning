package tech.medo.datasetgovernance.revokedatasettrainingapproval

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.datasetgovernance.revokedatasettrainingapproval.RevokeDatasetTrainingApprovalCommand

import tech.medo.datasetgovernance.dataset.DatasetState



@Component
class RevokeDatasetTrainingApprovalCommandHandler(
    private val decision: RevokeDatasetTrainingApprovalDecision
) {
    @CommandHandler
    fun handle(
        command: RevokeDatasetTrainingApprovalCommand,
        @InjectEntity(idProperty = "selection") state: DatasetState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
