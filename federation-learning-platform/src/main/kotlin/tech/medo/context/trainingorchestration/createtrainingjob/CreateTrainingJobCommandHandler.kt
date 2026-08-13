package tech.medo.trainingorchestration.createtrainingjob

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.createtrainingjob.CreateTrainingJobCommand




@Component
class CreateTrainingJobCommandHandler(
    private val decision: CreateTrainingJobDecision
) {
    @CommandHandler
    fun handle(
        command: CreateTrainingJobCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
