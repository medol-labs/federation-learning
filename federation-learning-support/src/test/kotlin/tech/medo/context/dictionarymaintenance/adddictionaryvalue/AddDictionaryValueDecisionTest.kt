package tech.medo.dictionarymaintenance.adddictionaryvalue

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.dictionarymaintenance.adddictionaryvalue.AddDictionaryValueCommand
import tech.medo.dictionarymaintenance.events.DictionaryValueAddedEvent
import java.util.UUID
import tech.medo.dictionarymaintenance.domain.types.DictionaryCode
import tech.medo.dictionarymaintenance.domain.types.DictionaryValueCode
import tech.medo.dictionarymaintenance.domain.types.DisplayOrder

class AddDictionaryValueDecisionTest {
    @Test
    fun AddDictionaryValueEmitsDictionaryValueAddedEvent() {
        val events = (object : AddDictionaryValueDecision {}).decide(
            AddDictionaryValueCommand(
            dictionaryValueId = java.util.UUID.randomUUID(),
            dictionaryId = java.util.UUID.randomUUID(),
            dictionaryCode = DictionaryCode(""),
            valueCode = DictionaryValueCode(""),
            displayName = "",
            displayOrder = null,
            description = null,
            active = false
            )
        )

        assertTrue(events.any { it is DictionaryValueAddedEvent })
    }
}
