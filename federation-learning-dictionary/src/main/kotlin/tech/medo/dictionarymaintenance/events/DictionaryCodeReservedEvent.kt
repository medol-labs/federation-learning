package tech.medo.dictionarymaintenance.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import tech.medo.dictionarymaintenance.domain.types.DictionaryCode;


@Event
data class DictionaryCodeReservedEvent(
    val dictionaryId: UUID,
    val dictionaryCode: DictionaryCode,
    @EventTag(key = "dictionaryCode")
    val normalizedName: String
)
