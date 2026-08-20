package tech.medo.runtimemonitoring.acknowledgetrainingalert

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimemonitoring.acknowledgetrainingalert.AcknowledgeTrainingAlertCommand

import tech.medo.runtimemonitoring.trainingalert.TrainingAlertState




@Component
class AcknowledgeTrainingAlertCommandHandler(
    private val decision: AcknowledgeTrainingAlertDecision
) {
    @CommandHandler
    fun handle(
        command: AcknowledgeTrainingAlertCommand,
        @InjectEntity(idProperty = "alertId") state: TrainingAlertState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
