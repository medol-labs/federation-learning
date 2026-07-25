package tech.medo.dictionarymaintenance.adddictionaryvalue

import org.springframework.stereotype.Component
import tech.medo.dictionarymaintenance.adddictionaryvalue.AddDictionaryValueCommand

import tech.medo.dictionarymaintenance.events.DictionaryValueAddedEvent
import tech.medo.dictionarymaintenance.dictionaryvalue.DictionaryValueState





@Component
class AddDictionaryValueDecision {
    fun decide(command: AddDictionaryValueCommand): List<Any> {
        return listOf(
            DictionaryValueAddedEvent(dictionaryValueId = command.dictionaryValueId, dictionaryId = command.dictionaryId, dictionaryCode = command.dictionaryCode, valueCode = command.valueCode, displayName = command.displayName, displayOrder = command.displayOrder, description = command.description, active = command.active)
        )
    }
}
