package tech.medo.trainingorchestration.recordpersonalizedmodelresult

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.recordpersonalizedmodelresult.RecordPersonalizedModelResultCommand







@Component
class RecordPersonalizedModelResultCommandHandler(
    private val decision: RecordPersonalizedModelResultDecision
) {
    @CommandHandler
    fun handle(
        command: RecordPersonalizedModelResultCommand,
        eventAppender: EventAppender
    ) {

        eventAppender.append(decision.decide(command))

    }
}
