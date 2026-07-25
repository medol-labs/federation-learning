package tech.medo.dictionarymaintenance.dictionary

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import org.slf4j.LoggerFactory
import tech.medo.dictionarymaintenance.events.DictionaryCodeReservedEvent
import java.util.UUID;


@EventSourced(idType = DictionaryCodeSelection::class)
class DictionaryCodeReservationState @EntityCreator constructor() {

    var reserved: Boolean = false
    var dictionaryId: UUID? = null

    companion object {
        private val logger = LoggerFactory.getLogger(DictionaryCodeReservationState::class.java)

        @JvmStatic
        @EventCriteriaBuilder
        fun resolveCriteria(selection: DictionaryCodeSelection): EventCriteria = EventCriteria.havingTags(
                Tag.of(DictionaryCodeReservationTags.DICTIONARY_CODE, selection.normalizedName)
        )
    }

    @EventSourcingHandler
    fun evolve(event: DictionaryCodeReservedEvent): DictionaryCodeReservationState = apply {
        logger.debug("Evolving dictionary code reservation. dictionaryCode={}, normalizedName={}", event.dictionaryCode, event.normalizedName)
        reserved = true
        dictionaryId = event.dictionaryId
    }
}
