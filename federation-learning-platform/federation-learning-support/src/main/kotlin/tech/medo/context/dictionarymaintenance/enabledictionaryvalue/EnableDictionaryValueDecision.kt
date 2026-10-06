package tech.medo.dictionarymaintenance.enabledictionaryvalue

import tech.medo.dictionarymaintenance.enabledictionaryvalue.EnableDictionaryValueCommand


import tech.medo.dictionarymaintenance.events.DictionaryValueEnabledEvent
import tech.medo.dictionarymaintenance.dictionaryvalue.DictionaryValueState


import tech.medo.dictionarymaintenance.domain.states.DictionaryValueStateEnum


interface EnableDictionaryValueDecision {
    fun decide(command: EnableDictionaryValueCommand, state: DictionaryValueState): List<Any> {
        if (state.currentState != DictionaryValueStateEnum.Disabled) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.dictionarymaintenance.enableDictionaryValue.requiresState",
                args = mapOf(
                    "command" to "EnableDictionaryValue",
                    "aggregate" to "DictionaryValue",
                    "expectedState" to "Disabled",
                    "actualState" to state.currentState.toString()
                ),
                message = "EnableDictionaryValue requires DictionaryValue to be Disabled."
            )
        }
        return listOf(
            DictionaryValueEnabledEvent(dictionaryValueId = command.dictionaryValueId, enableReason = command.enableReason, dictionaryCode = command.dictionaryCode, valueCode = command.valueCode)
        )
    }
}
