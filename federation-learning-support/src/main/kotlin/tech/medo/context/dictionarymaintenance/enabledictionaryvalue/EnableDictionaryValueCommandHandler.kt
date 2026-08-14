package tech.medo.dictionarymaintenance.enabledictionaryvalue

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.dictionarymaintenance.enabledictionaryvalue.EnableDictionaryValueCommand

import tech.medo.dictionarymaintenance.dictionaryvalue.DictionaryValueState



@Component
class EnableDictionaryValueCommandHandler(
    private val decision: EnableDictionaryValueDecision
) {
    @CommandHandler
    fun handle(
        command: EnableDictionaryValueCommand,
        @InjectEntity(idProperty = "selection") state: DictionaryValueState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
