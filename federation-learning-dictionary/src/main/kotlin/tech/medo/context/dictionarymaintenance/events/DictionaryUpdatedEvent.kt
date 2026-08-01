package tech.medo.dictionarymaintenance.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import tech.medo.dictionarymaintenance.domain.types.DictionaryCode;



@Event
data class DictionaryUpdatedEvent(
    val dictionaryId: UUID,
    val dictionaryName: String,
    val description: String?,
    @EventTag(key = "dictionaryCode")
    val dictionaryCode: DictionaryCode
)
