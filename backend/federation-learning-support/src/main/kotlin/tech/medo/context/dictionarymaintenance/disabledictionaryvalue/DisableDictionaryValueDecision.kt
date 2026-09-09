package tech.medo.dictionarymaintenance.disabledictionaryvalue

import tech.medo.dictionarymaintenance.disabledictionaryvalue.DisableDictionaryValueCommand


import tech.medo.dictionarymaintenance.events.DictionaryValueDisabledEvent
import tech.medo.dictionarymaintenance.dictionaryvalue.DictionaryValueState


import tech.medo.dictionarymaintenance.domain.states.DictionaryValueStateEnum


interface DisableDictionaryValueDecision {
    fun decide(command: DisableDictionaryValueCommand, state: DictionaryValueState): List<Any> {
        require(state.currentState == DictionaryValueStateEnum.ACTIVE) {
            "DisableDictionaryValue requires DictionaryValue to be Active."
        }
        return listOf(
            DictionaryValueDisabledEvent(dictionaryValueId = command.dictionaryValueId, disabledReason = command.disabledReason, dictionaryCode = command.dictionaryCode, valueCode = command.valueCode)
        )
    }
}
