package tech.medo.dictionarymaintenance.registerdictionary

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.dictionarymaintenance.registerdictionary.RegisterDictionaryCommand



import tech.medo.dictionarymaintenance.dictionary.DictionaryCodeReservationState

@Component
class RegisterDictionaryCommandHandler(
    private val decision: RegisterDictionaryDecision
) {
    @CommandHandler
    fun handle(
        command: RegisterDictionaryCommand,
        @InjectEntity(idProperty = "dictionaryCodeSelection") dictionaryCodeReservation: DictionaryCodeReservationState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, dictionaryCodeReservation))
    }
}
