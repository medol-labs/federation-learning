package tech.medo.dictionarymaintenance.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;


/* TODO: provide values for selection tags: dictionaryCode, valueCode */

@Event
data class DictionaryValueDisabledEvent(
    val dictionaryValueId: UUID,
    val disabledReason: String
)
