package tech.medo.dictionarymaintenance.registerdictionary

import tech.medo.dictionarymaintenance.registerdictionary.RegisterDictionaryCommand

import tech.medo.dictionarymaintenance.events.DictionaryRegisteredEvent
import tech.medo.dictionarymaintenance.events.DictionaryCodeReservedEvent
import tech.medo.dictionarymaintenance.dictionary.DictionaryState

import tech.medo.dictionarymaintenance.dictionary.DictionaryCodeReservationState



interface RegisterDictionaryDecision {
    fun decide(command: RegisterDictionaryCommand, dictionaryCodeReservation: DictionaryCodeReservationState): List<Any> {
        require(!dictionaryCodeReservation.reserved) {
            "Code already exists."
        }
        return listOf(
            DictionaryCodeReservedEvent(dictionaryId = command.dictionaryId, dictionaryCode = command.dictionaryCode, normalizedName = command.dictionaryCode.value.trim().lowercase()),
            DictionaryRegisteredEvent(dictionaryId = command.dictionaryId, dictionaryCode = command.dictionaryCode, dictionaryName = command.dictionaryName, description = command.description)
        )
    }
}
