package tech.medo.dictionarymaintenance.registerdictionary

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Test
import tech.medo.dictionarymaintenance.registerdictionary.RegisterDictionaryCommand

import tech.medo.dictionarymaintenance.dictionary.DictionaryCodeReservationState
import tech.medo.dictionarymaintenance.events.DictionaryCodeReservedEvent


import java.util.UUID;
import tech.medo.dictionarymaintenance.domain.types.DictionaryCode;


class RegisterDictionaryDecisionTest {
    @Test
    fun RejectDuplicateDictionary() {
        val dictionaryCodeReservation = DictionaryCodeReservationState()
        dictionaryCodeReservation.evolve(
            DictionaryCodeReservedEvent(
                dictionaryId = java.util.UUID.randomUUID(),
                dictionaryCode = DictionaryCode(""),
                normalizedName = DictionaryCode("").value.trim().lowercase()
            )
        )

        assertThrows<IllegalArgumentException> {
            (object : RegisterDictionaryDecision {}).decide(
                        RegisterDictionaryCommand(
                        dictionaryId = java.util.UUID.randomUUID(),
                        dictionaryCode = DictionaryCode(""),
                        dictionaryName = "",
                        description = null
                        ),
                            dictionaryCodeReservation = dictionaryCodeReservation
                    )
        }
    }
}
