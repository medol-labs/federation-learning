package tech.medo.dictionarymaintenance.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class DictionaryUpdatedEvent(
    @EventTag(key = "dictionaryId")
    val dictionaryId: UUID,
    val dictionaryName: String,
    val description: String?
)
