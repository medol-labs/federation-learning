package tech.medo.dictionarymaintenance.archivedictionary

import tech.medo.dictionarymaintenance.archivedictionary.ArchiveDictionaryCommand


import tech.medo.dictionarymaintenance.events.DictionaryArchivedEvent
import tech.medo.dictionarymaintenance.dictionary.DictionaryState


import tech.medo.dictionarymaintenance.domain.states.DictionaryStateEnum


interface ArchiveDictionaryDecision {
    fun decide(command: ArchiveDictionaryCommand, state: DictionaryState): List<Any> {
        require(state.currentState == DictionaryStateEnum.REGISTERED) {
            "ArchiveDictionary requires Dictionary to be Registered."
        }
        return listOf(
            DictionaryArchivedEvent(dictionaryId = command.dictionaryId, archiveReason = command.archiveReason, dictionaryCode = command.dictionaryCode)
        )
    }
}
