package tech.medo.runtimemonitoring.auditrecord

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.runtimemonitoring.events.AuditTrailAppendedEvent
import tech.medo.runtimemonitoring.domain.states.AuditRecordStateEnum

import java.util.UUID;


@EventSourced(idType = UUID::class, tagKey = AuditRecordTags.AUDIT_RECORD_ID)
class AuditRecordState @EntityCreator constructor() {

    var currentState: AuditRecordStateEnum? = null
    var auditRecordId: UUID? = null
    var sourceEventName: String? = null
    var sourceEntityId: UUID? = null
    var severity: String? = null
    var payloadHash: String? = null

    @EventSourcingHandler
    fun evolve(event: AuditTrailAppendedEvent): AuditRecordState = apply {
        currentState = AuditRecordStateEnum.APPENDED
        auditRecordId = event.auditRecordId
        sourceEventName = event.sourceEventName
        sourceEntityId = event.sourceEntityId
        severity = event.severity
        payloadHash = event.payloadHash
    }
}
