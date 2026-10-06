package tech.medo.dictionarymaintenance.updatedictionaryvaluetranslation

import tech.medo.dictionarymaintenance.updatedictionaryvaluetranslation.UpdateDictionaryValueTranslationCommand


import tech.medo.dictionarymaintenance.events.DictionaryValueTranslationUpdatedEvent
import tech.medo.dictionarymaintenance.dictionaryvaluetranslation.DictionaryValueTranslationState





interface UpdateDictionaryValueTranslationDecision {
    fun decide(command: UpdateDictionaryValueTranslationCommand, state: DictionaryValueTranslationState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            DictionaryValueTranslationUpdatedEvent(dictionaryValueTranslationId = command.dictionaryValueTranslationId, dictionaryValueId = command.dictionaryValueId, dictionaryCode = command.dictionaryCode, valueCode = command.valueCode, locale = command.locale, displayName = command.displayName, description = command.description)
        )
    }
}
