package tech.medo.dictionarymaintenance.disabledictionaryvalue

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.dictionarymaintenance.disabledictionaryvalue.DisableDictionaryValueCommand

import tech.medo.dictionarymaintenance.dictionaryvalue.DictionaryValueState



@Component
class DisableDictionaryValueCommandHandler(
    private val decision: DisableDictionaryValueDecision
) {
    @CommandHandler
    fun handle(
        command: DisableDictionaryValueCommand,
        @InjectEntity(idProperty = "selection") state: DictionaryValueState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
