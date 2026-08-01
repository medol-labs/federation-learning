package tech.medo.dictionarymaintenance.archivedictionary

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.dictionarymaintenance.archivedictionary.ArchiveDictionaryCommand

import tech.medo.dictionarymaintenance.dictionary.DictionaryState



@Component
class ArchiveDictionaryCommandHandler(
    private val decision: ArchiveDictionaryDecision
) {
    @CommandHandler
    fun handle(
        command: ArchiveDictionaryCommand,
        @InjectEntity(idProperty = "dictionaryCode") state: DictionaryState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
