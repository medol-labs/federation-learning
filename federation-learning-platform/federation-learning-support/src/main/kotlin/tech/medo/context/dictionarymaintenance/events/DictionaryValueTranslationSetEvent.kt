package tech.medo.dictionarymaintenance.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import tech.medo.dictionarymaintenance.domain.types.DictionaryCode;
import tech.medo.dictionarymaintenance.domain.types.DictionaryValueCode;
import tech.medo.dictionarymaintenance.domain.types.LocaleCode;



@Event
data class DictionaryValueTranslationSetEvent(
    val dictionaryValueTranslationId: UUID,
    val dictionaryValueId: UUID,
    @EventTag(key = "dictionaryCode")
    val dictionaryCode: DictionaryCode,
    @EventTag(key = "valueCode")
    val valueCode: DictionaryValueCode,
    @EventTag(key = "locale")
    val locale: LocaleCode,
    val displayName: String,
    val description: String?
)
