package tech.medo.secureaggregation.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class HomomorphicEncryptionContextPreparedEvent(
    @EventTag(key = "secureAggregationSessionId")
    val secureAggregationSessionId: UUID,
    val encryptionScheme: String,
    val publicKeyVersion: String,
    val encryptedParameterScale: Int
)
