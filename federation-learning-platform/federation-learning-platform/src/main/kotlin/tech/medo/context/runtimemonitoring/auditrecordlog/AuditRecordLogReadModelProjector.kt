package tech.medo.runtimemonitoring.auditrecordlog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata


import tech.medo.runtimemonitoring.events.AuditTrailAppendedEvent



interface AuditRecordLogReadModelProjectionUpdater {
    fun update(
        event: AuditTrailAppendedEvent,
        message: EventMessage
    )
}

@Component
@ConditionalOnMissingBean(AuditRecordLogReadModelProjectionUpdater::class)
class DefaultAuditRecordLogReadModelProjectionUpdater(
    private val repository: AuditRecordLogReadModelRepository
) : AuditRecordLogReadModelProjectionUpdater {
    @Transactional
    override fun update(
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

@Namespace("readmodel-audit-record-log")
@Component
class AuditRecordLogReadModelProjector(
    private val updater: AuditRecordLogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: AuditTrailAppendedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
