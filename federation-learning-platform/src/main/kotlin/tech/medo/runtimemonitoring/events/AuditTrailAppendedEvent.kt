package tech.medo.runtimemonitoring.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class AuditTrailAppendedEvent(
    @EventTag(key = "auditRecordId")
    val auditRecordId: UUID,
    val sourceEventName: String,
    val sourceEntityId: UUID?,
    val severity: String,
    val payloadHash: String
)
