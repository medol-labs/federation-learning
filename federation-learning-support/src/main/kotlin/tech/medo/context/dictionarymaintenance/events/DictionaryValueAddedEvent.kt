package tech.medo.dictionarymaintenance.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import tech.medo.dictionarymaintenance.domain.types.DictionaryCode;
import tech.medo.dictionarymaintenance.domain.types.DictionaryValueCode;
import tech.medo.dictionarymaintenance.domain.types.DisplayOrder;



@Event
data class DictionaryValueAddedEvent(
    val dictionaryValueId: UUID,
    val dictionaryId: UUID,
    @EventTag(key = "dictionaryCode")
    val dictionaryCode: DictionaryCode,
    @EventTag(key = "valueCode")
    val valueCode: DictionaryValueCode,
    val displayName: String,
    val displayOrder: DisplayOrder?,
    val description: String?,
    val active: Boolean
)
