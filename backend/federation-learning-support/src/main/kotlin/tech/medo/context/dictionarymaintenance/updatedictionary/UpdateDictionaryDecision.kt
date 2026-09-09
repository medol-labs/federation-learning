package tech.medo.dictionarymaintenance.updatedictionary

import tech.medo.dictionarymaintenance.updatedictionary.UpdateDictionaryCommand


import tech.medo.dictionarymaintenance.events.DictionaryUpdatedEvent
import tech.medo.dictionarymaintenance.dictionary.DictionaryState





interface UpdateDictionaryDecision {
    fun decide(command: UpdateDictionaryCommand, state: DictionaryState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            DictionaryUpdatedEvent(dictionaryId = command.dictionaryId, dictionaryName = command.dictionaryName, description = command.description, dictionaryCode = command.dictionaryCode)
        )
    }
}
