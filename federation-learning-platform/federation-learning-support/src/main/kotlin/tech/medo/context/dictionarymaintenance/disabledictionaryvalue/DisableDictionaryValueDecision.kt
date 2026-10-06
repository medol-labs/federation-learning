package tech.medo.dictionarymaintenance.disabledictionaryvalue

import tech.medo.dictionarymaintenance.disabledictionaryvalue.DisableDictionaryValueCommand


import tech.medo.dictionarymaintenance.events.DictionaryValueDisabledEvent
import tech.medo.dictionarymaintenance.dictionaryvalue.DictionaryValueState


import tech.medo.dictionarymaintenance.domain.states.DictionaryValueStateEnum


interface DisableDictionaryValueDecision {
    fun decide(command: DisableDictionaryValueCommand, state: DictionaryValueState): List<Any> {
        if (state.currentState != DictionaryValueStateEnum.Active) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.dictionarymaintenance.disableDictionaryValue.requiresState",
                args = mapOf(
                    "command" to "DisableDictionaryValue",
                    "aggregate" to "DictionaryValue",
                    "expectedState" to "Active",
                    "actualState" to state.currentState.toString()
                ),
                message = "DisableDictionaryValue requires DictionaryValue to be Active."
            )
        }
        return listOf(
            DictionaryValueDisabledEvent(dictionaryValueId = command.dictionaryValueId, disabledReason = command.disabledReason, dictionaryCode = command.dictionaryCode, valueCode = command.valueCode)
        )
    }
}
