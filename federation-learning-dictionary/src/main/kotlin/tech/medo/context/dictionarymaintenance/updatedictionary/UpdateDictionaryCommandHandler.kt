package tech.medo.dictionarymaintenance.updatedictionary

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.dictionarymaintenance.updatedictionary.UpdateDictionaryCommand

import tech.medo.dictionarymaintenance.dictionary.DictionaryState



@Component
class UpdateDictionaryCommandHandler(
    private val decision: UpdateDictionaryDecision
) {
    @CommandHandler
    fun handle(
        command: UpdateDictionaryCommand,
        @InjectEntity(idProperty = "dictionaryCode") state: DictionaryState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
