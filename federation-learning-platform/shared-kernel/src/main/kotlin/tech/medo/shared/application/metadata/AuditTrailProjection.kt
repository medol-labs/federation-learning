package tech.medo.shared.application.metadata

import com.fasterxml.jackson.databind.ObjectMapper
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.stereotype.Component

@Namespace("audit-trail")
@Component
class AuditTrailProjection(
    private val repository: AuditLogRepository,
    private val objectMapper: ObjectMapper
) {
    @EventHandler
    fun on(event: Any, message: EventMessage) {
        val metadata = message.metadata()
        val entry = AuditLogEntry().apply {
            userId = metadata[MetadataKeys.USER_ID]
            sessionId = metadata[MetadataKeys.SESSION_ID]
            correlationId = metadata[MetadataKeys.CORRELATION_ID]
            causationId = metadata[MetadataKeys.CAUSATION_ID]
            traceId = metadata[MetadataKeys.TRACE_ID]
            tenantId = metadata[MetadataKeys.TENANT_ID]
            eventType = event::class.simpleName ?: event.javaClass.simpleName
            timestamp = message.timestamp()
            payload = objectMapper.writeValueAsString(event)
        }

        repository.save(entry)
    }
}
