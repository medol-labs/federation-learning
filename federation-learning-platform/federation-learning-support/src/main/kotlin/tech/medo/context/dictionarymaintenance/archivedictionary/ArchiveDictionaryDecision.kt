package tech.medo.dictionarymaintenance.archivedictionary

import tech.medo.dictionarymaintenance.archivedictionary.ArchiveDictionaryCommand


import tech.medo.dictionarymaintenance.events.DictionaryArchivedEvent
import tech.medo.dictionarymaintenance.dictionary.DictionaryState


import tech.medo.dictionarymaintenance.domain.states.DictionaryStateEnum


interface ArchiveDictionaryDecision {
    fun decide(command: ArchiveDictionaryCommand, state: DictionaryState): List<Any> {
        if (state.currentState != DictionaryStateEnum.Registered) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.dictionarymaintenance.archiveDictionary.requiresState",
                args = mapOf(
                    "command" to "ArchiveDictionary",
                    "aggregate" to "Dictionary",
                    "expectedState" to "Registered",
                    "actualState" to state.currentState.toString()
                ),
                message = "ArchiveDictionary requires Dictionary to be Registered."
            )
        }
        return listOf(
            DictionaryArchivedEvent(dictionaryId = command.dictionaryId, archiveReason = command.archiveReason, dictionaryCode = command.dictionaryCode)
        )
    }
}
