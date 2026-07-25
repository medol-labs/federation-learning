package tech.medo.dictionarymaintenance.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import tech.medo.dictionarymaintenance.domain.types.DictionaryCode;



@Event
data class DictionaryRegisteredEvent(
    @EventTag(key = "dictionaryId")
    val dictionaryId: UUID,
    @EventTag(key = "dictionaryCode")
    val dictionaryCode: DictionaryCode,
    val dictionaryName: String,
    val description: String?
)
