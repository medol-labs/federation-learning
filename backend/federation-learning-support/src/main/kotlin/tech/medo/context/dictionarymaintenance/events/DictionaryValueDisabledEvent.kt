package tech.medo.dictionarymaintenance.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import tech.medo.dictionarymaintenance.domain.types.DictionaryCode;
import tech.medo.dictionarymaintenance.domain.types.DictionaryValueCode;



@Event
data class DictionaryValueDisabledEvent(
    val dictionaryValueId: UUID,
    val disabledReason: String,
    @EventTag(key = "dictionaryCode")
    val dictionaryCode: DictionaryCode,
    @EventTag(key = "valueCode")
    val valueCode: DictionaryValueCode
)
