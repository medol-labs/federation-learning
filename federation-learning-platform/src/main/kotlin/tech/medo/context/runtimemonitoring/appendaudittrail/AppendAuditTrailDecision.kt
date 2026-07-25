package tech.medo.runtimemonitoring.appendaudittrail

import org.springframework.stereotype.Component
import tech.medo.runtimemonitoring.appendaudittrail.AppendAuditTrailCommand

import tech.medo.runtimemonitoring.events.AuditTrailAppendedEvent
import tech.medo.runtimemonitoring.auditrecord.AuditRecordState





@Component
class AppendAuditTrailDecision {
    fun decide(command: AppendAuditTrailCommand): List<Any> {
        return listOf(
            AuditTrailAppendedEvent(auditRecordId = command.auditRecordId, sourceEventName = command.sourceEventName, sourceEntityId = command.sourceEntityId, severity = command.severity, payloadHash = command.payloadHash)
        )
    }
}
