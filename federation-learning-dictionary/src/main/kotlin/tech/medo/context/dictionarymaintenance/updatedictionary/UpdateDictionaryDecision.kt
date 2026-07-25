package tech.medo.dictionarymaintenance.updatedictionary

import org.springframework.stereotype.Component
import tech.medo.dictionarymaintenance.updatedictionary.UpdateDictionaryCommand

import tech.medo.dictionarymaintenance.events.DictionaryUpdatedEvent
import tech.medo.dictionarymaintenance.dictionary.DictionaryState





@Component
class UpdateDictionaryDecision {
    fun decide(command: UpdateDictionaryCommand, state: DictionaryState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            DictionaryUpdatedEvent(dictionaryId = command.dictionaryId, dictionaryName = command.dictionaryName, description = command.description)
        )
    }
}
