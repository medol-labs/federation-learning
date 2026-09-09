package tech.medo.runtimemonitoring.raisetrainingalert

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimemonitoring.raisetrainingalert.RaiseTrainingAlertCommand





@Component
class RaiseTrainingAlertCommandHandler(
    private val decision: RaiseTrainingAlertDecision
) {
    @CommandHandler
    fun handle(
        command: RaiseTrainingAlertCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
