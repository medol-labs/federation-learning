package tech.medo.dictionarymaintenance.dictionary

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.dictionarymaintenance.events.DictionaryRegisteredEvent
import tech.medo.dictionarymaintenance.events.DictionaryUpdatedEvent
import tech.medo.dictionarymaintenance.events.DictionaryArchivedEvent
import tech.medo.dictionarymaintenance.domain.states.DictionaryStateEnum

import java.util.UUID;
import tech.medo.dictionarymaintenance.domain.types.DictionaryCode;


@EventSourced(idType = DictionaryCode::class, tagKey = DictionaryTags.DICTIONARY_CODE)
class DictionaryState @EntityCreator constructor() {

    var currentState: DictionaryStateEnum? = null
    var dictionaryId: UUID? = null
    var dictionaryCode: DictionaryCode? = null
    var dictionaryName: String? = null
    var description: String? = null
    var archiveReason: String? = null

    @EventSourcingHandler
    fun evolve(event: DictionaryRegisteredEvent): DictionaryState = apply {
        currentState = DictionaryStateEnum.REGISTERED
        dictionaryId = event.dictionaryId
        dictionaryCode = event.dictionaryCode
        dictionaryName = event.dictionaryName
        description = event.description
    }

    @EventSourcingHandler
    fun evolve(event: DictionaryUpdatedEvent): DictionaryState = apply {
        dictionaryId = event.dictionaryId
        dictionaryName = event.dictionaryName
        description = event.description
    }

    @EventSourcingHandler
    fun evolve(event: DictionaryArchivedEvent): DictionaryState = apply {
        currentState = DictionaryStateEnum.ARCHIVED
        dictionaryId = event.dictionaryId
        archiveReason = event.archiveReason
    }
}
