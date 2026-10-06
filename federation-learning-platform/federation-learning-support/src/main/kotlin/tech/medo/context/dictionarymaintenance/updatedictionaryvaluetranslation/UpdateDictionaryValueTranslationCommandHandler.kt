package tech.medo.dictionarymaintenance.updatedictionaryvaluetranslation

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.dictionarymaintenance.updatedictionaryvaluetranslation.UpdateDictionaryValueTranslationCommand



import tech.medo.dictionarymaintenance.dictionaryvaluetranslation.DictionaryValueTranslationState



@Component
class UpdateDictionaryValueTranslationCommandHandler(
    private val decision: UpdateDictionaryValueTranslationDecision
) {
    @CommandHandler
    fun handle(
        command: UpdateDictionaryValueTranslationCommand,
        @InjectEntity(idProperty = "selection") state: DictionaryValueTranslationState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
