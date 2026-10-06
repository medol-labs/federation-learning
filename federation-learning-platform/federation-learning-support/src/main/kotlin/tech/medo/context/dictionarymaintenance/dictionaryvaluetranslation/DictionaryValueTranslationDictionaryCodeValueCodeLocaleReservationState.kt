package tech.medo.dictionarymaintenance.dictionaryvaluetranslation

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.dictionarymaintenance.events.DictionaryValueTranslationDictionaryCodeValueCodeLocaleReservedEvent
import java.util.UUID;


@EventSourced(idType = DictionaryValueTranslationDictionaryCodeValueCodeLocaleSelection::class)
class DictionaryValueTranslationDictionaryCodeValueCodeLocaleReservationState @EntityCreator constructor() {

    var reserved: Boolean = false
    var dictionaryValueTranslationId: UUID? = null

    companion object {
        @JvmStatic
        @EventCriteriaBuilder
        fun resolveCriteria(selection: DictionaryValueTranslationDictionaryCodeValueCodeLocaleSelection): EventCriteria = EventCriteria.havingTags(
                Tag.of(DictionaryValueTranslationDictionaryCodeValueCodeLocaleReservationTags.DICTIONARY_CODE, selection.normalizedDictionaryCode),
                Tag.of(DictionaryValueTranslationDictionaryCodeValueCodeLocaleReservationTags.VALUE_CODE, selection.normalizedValueCode),
                Tag.of(DictionaryValueTranslationDictionaryCodeValueCodeLocaleReservationTags.LOCALE, selection.normalizedLocale)
        )
    }

    @EventSourcingHandler
    fun evolve(event: DictionaryValueTranslationDictionaryCodeValueCodeLocaleReservedEvent): DictionaryValueTranslationDictionaryCodeValueCodeLocaleReservationState = apply {
        reserved = true
        dictionaryValueTranslationId = event.dictionaryValueTranslationId
    }
}
