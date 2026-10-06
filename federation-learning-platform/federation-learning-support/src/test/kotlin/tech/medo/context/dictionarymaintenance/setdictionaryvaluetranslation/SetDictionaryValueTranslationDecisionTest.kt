package tech.medo.dictionarymaintenance.setdictionaryvaluetranslation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Test
import tech.medo.dictionarymaintenance.setdictionaryvaluetranslation.SetDictionaryValueTranslationCommand
import tech.medo.dictionarymaintenance.dictionaryvaluetranslation.DictionaryValueTranslationDictionaryCodeValueCodeLocaleReservationState
import tech.medo.dictionarymaintenance.events.DictionaryValueTranslationDictionaryCodeValueCodeLocaleReservedEvent
import java.util.UUID
import tech.medo.dictionarymaintenance.domain.types.DictionaryCode
import tech.medo.dictionarymaintenance.domain.types.DictionaryValueCode
import tech.medo.dictionarymaintenance.domain.types.LocaleCode

class SetDictionaryValueTranslationDecisionTest {
    @Test
    fun RejectDuplicateDictionaryValueTranslationLocale() {
        val dictionaryValueTranslationDictionaryCodeValueCodeLocaleReservation = DictionaryValueTranslationDictionaryCodeValueCodeLocaleReservationState()
        dictionaryValueTranslationDictionaryCodeValueCodeLocaleReservation.evolve(
            DictionaryValueTranslationDictionaryCodeValueCodeLocaleReservedEvent(
                dictionaryValueTranslationId = java.util.UUID.randomUUID(),
                dictionaryCode = DictionaryCode(""),
                valueCode = DictionaryValueCode(""),
                locale = LocaleCode(""),
                normalizedDictionaryCode = DictionaryCode("").value.trim().lowercase(),
                normalizedValueCode = DictionaryValueCode("").value.trim().lowercase(),
                normalizedLocale = LocaleCode("").value.trim().lowercase()
            )
        )

        assertThrows<IllegalArgumentException> {
            (object : SetDictionaryValueTranslationDecision {}).decide(
                        SetDictionaryValueTranslationCommand(
                        dictionaryValueTranslationId = java.util.UUID.randomUUID(),
                        dictionaryValueId = java.util.UUID.randomUUID(),
                        dictionaryCode = DictionaryCode(""),
                        valueCode = DictionaryValueCode(""),
                        locale = LocaleCode(""),
                        displayName = "",
                        description = null
                        ),
                            dictionaryValueTranslationDictionaryCodeValueCodeLocaleReservation = dictionaryValueTranslationDictionaryCodeValueCodeLocaleReservation
                    )
        }
    }
}
