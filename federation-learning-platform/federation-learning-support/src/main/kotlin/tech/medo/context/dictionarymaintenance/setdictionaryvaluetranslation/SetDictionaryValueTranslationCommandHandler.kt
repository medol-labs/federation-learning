package tech.medo.dictionarymaintenance.setdictionaryvaluetranslation

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.dictionarymaintenance.setdictionaryvaluetranslation.SetDictionaryValueTranslationCommand





import tech.medo.dictionarymaintenance.dictionaryvaluetranslation.DictionaryValueTranslationDictionaryCodeValueCodeLocaleReservationState

@Component
class SetDictionaryValueTranslationCommandHandler(
    private val decision: SetDictionaryValueTranslationDecision
) {
    @CommandHandler
    fun handle(
        command: SetDictionaryValueTranslationCommand,
        @InjectEntity(idProperty = "dictionaryValueTranslationDictionaryCodeValueCodeLocaleSelection") dictionaryValueTranslationDictionaryCodeValueCodeLocaleReservation: DictionaryValueTranslationDictionaryCodeValueCodeLocaleReservationState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, dictionaryValueTranslationDictionaryCodeValueCodeLocaleReservation))
    }
}
