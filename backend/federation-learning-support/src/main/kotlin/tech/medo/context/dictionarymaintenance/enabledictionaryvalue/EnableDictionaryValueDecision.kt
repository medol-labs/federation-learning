package tech.medo.dictionarymaintenance.enabledictionaryvalue

import tech.medo.dictionarymaintenance.enabledictionaryvalue.EnableDictionaryValueCommand


import tech.medo.dictionarymaintenance.events.DictionaryValueEnabledEvent
import tech.medo.dictionarymaintenance.dictionaryvalue.DictionaryValueState


import tech.medo.dictionarymaintenance.domain.states.DictionaryValueStateEnum


interface EnableDictionaryValueDecision {
    fun decide(command: EnableDictionaryValueCommand, state: DictionaryValueState): List<Any> {
        require(state.currentState == DictionaryValueStateEnum.DISABLED) {
            "EnableDictionaryValue requires DictionaryValue to be Disabled."
        }
        return listOf(
            DictionaryValueEnabledEvent(dictionaryValueId = command.dictionaryValueId, enableReason = command.enableReason, dictionaryCode = command.dictionaryCode, valueCode = command.valueCode)
        )
    }
}
