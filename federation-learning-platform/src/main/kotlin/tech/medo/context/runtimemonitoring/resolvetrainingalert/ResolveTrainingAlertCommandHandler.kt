package tech.medo.runtimemonitoring.resolvetrainingalert

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimemonitoring.resolvetrainingalert.ResolveTrainingAlertCommand

import tech.medo.runtimemonitoring.trainingalert.TrainingAlertState




@Component
class ResolveTrainingAlertCommandHandler(
    private val decision: ResolveTrainingAlertDecision
) {
    @CommandHandler
    fun handle(
        command: ResolveTrainingAlertCommand,
        @InjectEntity(idProperty = "alertId") state: TrainingAlertState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
