package tech.medo.runtimemonitoring.auditrecordlog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.runtimemonitoring.events.AuditTrailAppendedEvent



@Component
class AuditRecordLogReadModelProjector(private val repository: AuditRecordLogReadModelRepository) {
    @EventHandler
    fun on(
        event: AuditTrailAppendedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.auditRecordId) ?: AuditRecordLogReadModelProjection().apply {
                this.auditRecordId = event.auditRecordId
        }
            entity.auditRecordId = event.auditRecordId
            entity.sourceEventName = event.sourceEventName
            entity.sourceEntityId = event.sourceEntityId
            entity.severity = event.severity
            entity.payloadHash = event.payloadHash
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

}
