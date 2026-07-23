package tech.medo.dictionarymaintenance.adddictionaryvalue

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.dictionarymaintenance.adddictionaryvalue.AddDictionaryValueCommand




@Component
class AddDictionaryValueCommandHandler(
    private val decision: AddDictionaryValueDecision
) {
    @CommandHandler
    fun handle(
        command: AddDictionaryValueCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
