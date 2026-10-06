package tech.medo.dictionarymaintenance.setdictionaryvaluetranslation

import tech.medo.dictionarymaintenance.setdictionaryvaluetranslation.SetDictionaryValueTranslationCommand


import tech.medo.dictionarymaintenance.events.DictionaryValueTranslationSetEvent
import tech.medo.dictionarymaintenance.events.DictionaryValueTranslationDictionaryCodeValueCodeLocaleReservedEvent
import tech.medo.dictionarymaintenance.dictionaryvaluetranslation.DictionaryValueTranslationState

import tech.medo.dictionarymaintenance.dictionaryvaluetranslation.DictionaryValueTranslationDictionaryCodeValueCodeLocaleReservationState



interface SetDictionaryValueTranslationDecision {
    fun decide(command: SetDictionaryValueTranslationCommand, dictionaryValueTranslationDictionaryCodeValueCodeLocaleReservation: DictionaryValueTranslationDictionaryCodeValueCodeLocaleReservationState): List<Any> {
        require(!dictionaryValueTranslationDictionaryCodeValueCodeLocaleReservation.reserved) {
            "DictionaryCode ValueCode Locale already exists."
        }
        return listOf(
                        DictionaryValueTranslationDictionaryCodeValueCodeLocaleReservedEvent(dictionaryValueTranslationId = command.dictionaryValueTranslationId, dictionaryCode = command.dictionaryCode, valueCode = command.valueCode, locale = command.locale, normalizedDictionaryCode = command.dictionaryCode.value.trim().lowercase(), normalizedValueCode = command.valueCode.value.trim().lowercase(), normalizedLocale = command.locale.value.trim().lowercase()),
            DictionaryValueTranslationSetEvent(dictionaryValueTranslationId = command.dictionaryValueTranslationId, dictionaryValueId = command.dictionaryValueId, dictionaryCode = command.dictionaryCode, valueCode = command.valueCode, locale = command.locale, displayName = command.displayName, description = command.description)
        )
    }
}
