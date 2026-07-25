package tech.medo.dictionarymaintenance.dictionaryvalue

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.dictionarymaintenance.events.DictionaryValueAddedEvent
import tech.medo.dictionarymaintenance.events.DictionaryValueDisabledEvent
import tech.medo.dictionarymaintenance.events.DictionaryValueEnabledEvent
import tech.medo.dictionarymaintenance.domain.states.DictionaryValueStateEnum

import java.util.UUID;
import tech.medo.dictionarymaintenance.domain.types.DictionaryCode;
import tech.medo.dictionarymaintenance.domain.types.DictionaryValueCode;
import tech.medo.dictionarymaintenance.domain.types.DisplayOrder;


@EventSourced(idType = DictionaryValueSelection::class)
class DictionaryValueState @EntityCreator constructor() {
    companion object {
        @JvmStatic
        @EventCriteriaBuilder
        fun resolveCriteria(selection: DictionaryValueSelection): EventCriteria = EventCriteria.either(
                EventCriteria.havingTags(Tag.of(DictionaryValueTags.DICTIONARY_CODE, selection.dictionaryCode.value.toString())),
                EventCriteria.havingTags(Tag.of(DictionaryValueTags.VALUE_CODE, selection.valueCode.value.toString()))
        )
    }


    var currentState: DictionaryValueStateEnum? = null
    private var dictionaryValueId: UUID? = null
    private var dictionaryId: UUID? = null
    private var dictionaryCode: DictionaryCode? = null
    private var valueCode: DictionaryValueCode? = null
    private var displayName: String? = null
    private var displayOrder: DisplayOrder? = null
    private var description: String? = null
    private var active: Boolean? = null
    private var disabledReason: String? = null
    private var enableReason: String? = null

    @EventSourcingHandler
    fun evolve(event: DictionaryValueAddedEvent): DictionaryValueState = apply {
        currentState = DictionaryValueStateEnum.ACTIVE
        dictionaryValueId = event.dictionaryValueId
        dictionaryId = event.dictionaryId
        dictionaryCode = event.dictionaryCode
        valueCode = event.valueCode
        displayName = event.displayName
        displayOrder = event.displayOrder
        description = event.description
        active = event.active
    }

    @EventSourcingHandler
    fun evolve(event: DictionaryValueDisabledEvent): DictionaryValueState = apply {
        currentState = DictionaryValueStateEnum.DISABLED
        dictionaryValueId = event.dictionaryValueId
        disabledReason = event.disabledReason
    }

    @EventSourcingHandler
    fun evolve(event: DictionaryValueEnabledEvent): DictionaryValueState = apply {
        currentState = DictionaryValueStateEnum.ACTIVE
        dictionaryValueId = event.dictionaryValueId
        enableReason = event.enableReason
    }
}
