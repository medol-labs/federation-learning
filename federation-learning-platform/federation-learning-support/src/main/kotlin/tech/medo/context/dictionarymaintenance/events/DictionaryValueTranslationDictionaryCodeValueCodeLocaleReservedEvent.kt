package tech.medo.dictionarymaintenance.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import tech.medo.dictionarymaintenance.domain.types.DictionaryCode;
import tech.medo.dictionarymaintenance.domain.types.DictionaryValueCode;
import tech.medo.dictionarymaintenance.domain.types.LocaleCode;


@Event
data class DictionaryValueTranslationDictionaryCodeValueCodeLocaleReservedEvent(
    val dictionaryValueTranslationId: UUID,
    val dictionaryCode: DictionaryCode,
    val valueCode: DictionaryValueCode,
    val locale: LocaleCode,
    @EventTag(key = "dictionaryCode")
    val normalizedDictionaryCode: String,
    @EventTag(key = "valueCode")
    val normalizedValueCode: String,
    @EventTag(key = "locale")
    val normalizedLocale: String
)
