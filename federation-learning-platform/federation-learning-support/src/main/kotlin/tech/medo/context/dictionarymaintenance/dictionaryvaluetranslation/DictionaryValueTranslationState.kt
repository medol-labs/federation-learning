package tech.medo.dictionarymaintenance.dictionaryvaluetranslation

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.dictionarymaintenance.events.DictionaryValueTranslationSetEvent
import tech.medo.dictionarymaintenance.events.DictionaryValueTranslationUpdatedEvent
import tech.medo.dictionarymaintenance.domain.states.DictionaryValueTranslationStateEnum

import java.util.UUID;
import tech.medo.dictionarymaintenance.domain.types.DictionaryCode;
import tech.medo.dictionarymaintenance.domain.types.DictionaryValueCode;
import tech.medo.dictionarymaintenance.domain.types.LocaleCode;


@EventSourced(idType = DictionaryValueTranslationSelection::class)
class DictionaryValueTranslationState @EntityCreator constructor() {
    companion object {
        @JvmStatic
        @EventCriteriaBuilder
        fun resolveCriteria(selection: DictionaryValueTranslationSelection): EventCriteria = EventCriteria.either(
                EventCriteria.havingTags(Tag.of(DictionaryValueTranslationTags.DICTIONARY_CODE, selection.dictionaryCode.value.toString())),
                EventCriteria.havingTags(Tag.of(DictionaryValueTranslationTags.VALUE_CODE, selection.valueCode.value.toString())),
                EventCriteria.havingTags(Tag.of(DictionaryValueTranslationTags.LOCALE, selection.locale.value.toString()))
        )
    }


    var currentState: DictionaryValueTranslationStateEnum? = null
    var dictionaryValueTranslationId: UUID? = null
    var dictionaryValueId: UUID? = null
    var dictionaryCode: DictionaryCode? = null
    var valueCode: DictionaryValueCode? = null
    var locale: LocaleCode? = null
    var displayName: String? = null
    var description: String? = null

    @EventSourcingHandler
    fun evolve(event: DictionaryValueTranslationSetEvent): DictionaryValueTranslationState = apply {
        currentState = DictionaryValueTranslationStateEnum.Active
        dictionaryValueTranslationId = event.dictionaryValueTranslationId
        dictionaryValueId = event.dictionaryValueId
        dictionaryCode = event.dictionaryCode
        valueCode = event.valueCode
        locale = event.locale
        displayName = event.displayName
        description = event.description
    }

    @EventSourcingHandler
    fun evolve(event: DictionaryValueTranslationUpdatedEvent): DictionaryValueTranslationState = apply {
        dictionaryValueTranslationId = event.dictionaryValueTranslationId
        dictionaryValueId = event.dictionaryValueId
        dictionaryCode = event.dictionaryCode
        valueCode = event.valueCode
        locale = event.locale
        displayName = event.displayName
        description = event.description
    }
}
